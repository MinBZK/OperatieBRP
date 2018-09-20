/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.huwelijkpartnerschap;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.business.bedrijfsregels.ActieBedrijfsRegel;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.logisch.Betrokkenheid;
import nl.bzk.brp.model.objecttype.logisch.Relatie;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Soortmelding;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Een persoon die Nederlandse nationaliteit heeft (op de systeemdatum) mag alleen trouwen
 * (of een geregistreerd partnerschap aangaan) als hij of zij minstens 18 jaar is op de datum aanvang huwelijk
 * (of geregistreerd partnerschap).
 */
public class BRBY0401 implements ActieBedrijfsRegel<Relatie> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BRBY0401.class);

    private static final int MAX_LEEFTIJD = 180000;

    @Inject
    private PersoonRepository persoonRepository;

    @Override
    public String getCode() {
        return "BRBY0401";
    }

    @Override
    public List<Melding> executeer(final Relatie huidigeSituatie, final Relatie nieuweSituatie,
                             final Actie actie)
    {
        List<Melding> meldingen = new ArrayList<Melding>();

        final Datum huwelijkDatum = nieuweSituatie.getGegevens().getDatumAanvang();

        for (Betrokkenheid betrokkenheid : nieuweSituatie.getBetrokkenheden()) {
            final Burgerservicenummer bsn =
                    betrokkenheid.getBetrokkene().getIdentificatienummers().getBurgerservicenummer();
            final PersoonModel partner = persoonRepository.findByBurgerservicenummer(bsn);
            if (partner == null) {
                LOGGER.info("Kan de persoon met bsn %s niet vinden", bsn.getWaarde());
            } else {
                //Heeft de partner de Nederlandse Naionaliteit:
                if (partner.heeftActueleNederlandseNationaliteit()) {
                    //Check leeftijd
                    Datum geboorteDatum = partner.getGeboorte().getDatumGeboorte();

                    int leeftijd = huwelijkDatum.getWaarde() - geboorteDatum.getWaarde();

                    if (leeftijd < MAX_LEEFTIJD) {
                        meldingen.add(new Melding(Soortmelding.OVERRULEBAAR, MeldingCode.BRBY0401,
                                (Identificeerbaar) betrokkenheid.getBetrokkene(), "persoon"));
                    }
                }
            }
        }
        return meldingen;
    }
}
