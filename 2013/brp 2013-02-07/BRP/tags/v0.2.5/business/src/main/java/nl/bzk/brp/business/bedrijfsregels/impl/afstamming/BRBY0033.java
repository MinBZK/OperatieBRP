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
import nl.bzk.brp.business.definities.impl.afstamming.KandidaatVader;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.logisch.Persoon;
import nl.bzk.brp.model.objecttype.logisch.Relatie;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Soortmelding;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.util.RelatieUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Vader moet voldoen aan kandidaatregels.
 *
 * @brp.bedrijfsregel BRBY0033
 */
public class BRBY0033 implements ActieBedrijfsRegel<Relatie> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BRBY0033.class);

    @Inject
    PersoonRepository           persoonRepository;

    @Inject
    KandidaatVader              kandidaatVader;

    @Override
    public String getCode() {
        return "BRBY0033";
    }

    @Override
    public List<Melding> executeer(final Relatie huidigeSituatie, final Relatie nieuweSituatie, final Actie actie) {
        List<Melding> meldingen = new ArrayList<Melding>();

        if (null != nieuweSituatie) {
            Persoon moeder = RelatieUtils.haalMoederUitRelatie(nieuweSituatie);

            if (moeder != null && moeder.getIdentificatienummers() != null
                && moeder.getIdentificatienummers().getBurgerservicenummer() != null)
            {
                PersoonModel moederModel =
                    persoonRepository.findByBurgerservicenummer(moeder.getIdentificatienummers()
                            .getBurgerservicenummer());

                if (moederModel != null) {
                    Persoon vader = RelatieUtils.haalNietMoederUitRelatie(nieuweSituatie);

                    List<PersoonModel> kandidatenVader =
                        kandidaatVader.bepaalKandidatenVader(moederModel, nieuweSituatie.getKindBetrokkenheid()
                                .getBetrokkene().getGeboorte().getDatumGeboorte());

                    if (kandidatenVader.size() > 0 && vader == null) {
                        meldingen.add(new Melding(Soortmelding.OVERRULEBAAR, MeldingCode.BRBY0033,
                                MeldingCode.BRBY0033_1.getOmschrijving()));
                    } else if (vader != null && vader.getIdentificatienummers() != null
                        && vader.getIdentificatienummers().getBurgerservicenummer() != null)
                    {
                        boolean isInKandidaatLijst = false;
                        for (PersoonModel kandidaatVader : kandidatenVader) {

                            if (kandidaatVader.getIdentificatienummers().getBurgerservicenummer().getWaarde()
                                    .equals(vader.getIdentificatienummers().getBurgerservicenummer().getWaarde()))
                            {
                                isInKandidaatLijst = true;
                                break;
                            }
                        }

                        if (!isInKandidaatLijst) {
                            meldingen.add(new Melding(Soortmelding.OVERRULEBAAR, MeldingCode.BRBY0033,
                                    MeldingCode.BRBY0033.getOmschrijving()));
                        }
                    } else {
                        // TODO op het moment niet in scope
                        LOGGER.warn("Persoon vader niet aanwezig of heeft geen BSN. Bedrijfsregel {} "
                            + "wordt dus niet uitgevoerd.", getCode());
                    }
                } else {
                    // Er wordt vanuit gegaan dat er een andere business regel is die controlleert of de persoon bestaat
                    // met de bsn.
                    LOGGER.warn("Persoon moeder kan niet gevonden worden met BSN {}. Bedrijfsregel {} "
                        + "wordt dus niet uitgevoerd.", new Object[] {
                        moeder.getIdentificatienummers().getBurgerservicenummer().getWaarde(), getCode() });
                }
            } else {
                LOGGER.warn("Persoon moeder niet aanwezig of heeft geen BSN. Bedrijfsregel {} "
                    + "wordt dus niet uitgevoerd.", getCode());
            }
        } else {
            LOGGER.error("De nieuwe situatie zou niet null mogen zijn, kan {} niet valideren.", getCode());
        }
        return meldingen;
    }

}
