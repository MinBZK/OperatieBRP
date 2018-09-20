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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Burgerservicenummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.Betrokkenheid;
import nl.bzk.brp.model.logisch.kern.HuwelijkGeregistreerdPartnerschap;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Een persoon die Nederlandse nationaliteit heeft (op de systeemdatum) mag alleen trouwen
 * (of een geregistreerd partnerschap aangaan) als hij of zij minstens 18 jaar is op de datum aanvang huwelijk
 * (of geregistreerd partnerschap).
 */
public class BRBY0401 implements ActieBedrijfsRegel<HuwelijkGeregistreerdPartnerschap> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BRBY0401.class);

    private static final int MAX_LEEFTIJD = 180000;

    @Inject
    private PersoonRepository persoonRepository;

    @Override
    public String getCode() {
        return "BRBY0401";
    }

    @Override
    public List<Melding> executeer(final HuwelijkGeregistreerdPartnerschap huidigeSituatie,
        final HuwelijkGeregistreerdPartnerschap nieuweSituatie,
        final Actie actie)
    {
        List<Melding> meldingen = new ArrayList<Melding>();

        final Datum huwelijkDatum = nieuweSituatie.getStandaard().getDatumAanvang();

        for (Betrokkenheid betrokkenheid : nieuweSituatie.getBetrokkenheden()) {
            final Burgerservicenummer bsn =
                betrokkenheid.getPersoon().getIdentificatienummers().getBurgerservicenummer();
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
                        meldingen.add(new Melding(SoortMelding.DEBLOKKEERBAAR, MeldingCode.BRBY0401,
                            (Identificeerbaar) betrokkenheid.getPersoon(), "persoon"));
                    }
                }
            }
        }
        return meldingen;
    }
}
