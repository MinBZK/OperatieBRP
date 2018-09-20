/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.afstamming;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.business.bedrijfsregels.ActieBedrijfsRegel;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.logisch.Persoon;
import nl.bzk.brp.model.objecttype.logisch.Relatie;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Geslachtsaanduiding;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Soortmelding;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.util.RelatieUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * De persoon die in het bericht als ouder uit wie het kind is geboren is aangeduid (berichtelement ouder uit
 * wie het kind geboren is is aanwezig en heeft de waarde Ja) moet in de BRP op de datum geboorte als vrouw zijn
 * vastgelegd (geslachtsaanduiding uit groep geslachtsaanduiding heeft de waarde V).
 *
 * Let op: De de indicatie 'ouder uit wie het kind geboren is' is geimplementeerd als 'indicatie adres gevend'.
 *
 * @brp.bedrijfsregel BRPUC00112
 */
public class BRPUC00112 implements ActieBedrijfsRegel<Relatie> {
    private static final Logger  LOGGER = LoggerFactory.getLogger(BRPUC00112.class);

    @Inject
    private PersoonRepository persoonRepository;

    @Override
    public String getCode() {
        return "BRPUC00112";
    }

    @Override
    public List<Melding> executeer(final Relatie huidigeSituatie, final Relatie nieuweSituatie,
            final Actie actie)
    {
        List<Melding> meldingen = new ArrayList<Melding>();

        if (null != nieuweSituatie) {
            Persoon moeder = RelatieUtils.haalMoederUitRelatie(nieuweSituatie);
            if (null != moeder) {
                // de xsd zegt dat geslachts aanduiding optioneel is.
                // Als dit ingevuld is, moeten we hierop vertrouwen of uit de db halen?
                PersoonModel pMoeder = persoonRepository.findByBurgerservicenummer(
                        moeder.getIdentificatienummers().getBurgerservicenummer());
                if (null != pMoeder) {
                    if (!pMoeder.getGeslachtsaanduiding().getGeslachtsaanduiding().equals(Geslachtsaanduiding.VROUW)) {
                        meldingen.add(new Melding(Soortmelding.FOUT, MeldingCode.BRPUC00112,
                                (Identificeerbaar) moeder.getGeslachtsaanduiding(), "geslachtsnaamduiding"));
                    }
                } else {
                    LOGGER.error(String.format("Kan de persoon met bsn '%s' niet vinden. Validatie %s niet uitgevoerd.",
                            moeder.getIdentificatienummers().getBurgerservicenummer().getWaarde(), getCode()));
                }
            }
        } else {
            LOGGER.error("De nieuwe situatie zou niet null mogen zijn, kan BRPUC00112 niet valideren.");
        }
        return meldingen;
    }

}
