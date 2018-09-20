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
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.FamilierechtelijkeBetrekking;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
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
public class BRBY0033 implements ActieBedrijfsRegel<FamilierechtelijkeBetrekking> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BRBY0033.class);

    @Inject
    private PersoonRepository   persoonRepository;

    @Inject
    private KandidaatVader      kandidaatVader;

    @Override
    public String getCode() {
        return "BRBY0033";
    }

    @Override
    public List<Melding> executeer(final FamilierechtelijkeBetrekking huidigeSituatie,
            final FamilierechtelijkeBetrekking nieuweSituatie, final Actie actie)
    {
        List<Melding> meldingen = new ArrayList<Melding>();

        if (null != nieuweSituatie) {
            PersoonBericht kind = (PersoonBericht) RelatieUtils.haalKindUitRelatie(nieuweSituatie);
            Persoon moeder = RelatieUtils.haalMoederUitRelatie(nieuweSituatie);

            // TODO: bolie, moet omgezet worden naar technische sleutel
            if (moeder != null && moeder.getIdentificatienummers() != null
                && moeder.getIdentificatienummers().getBurgerservicenummer() != null)
            {
                PersoonModel moederModel =
                    persoonRepository.findByBurgerservicenummer(moeder.getIdentificatienummers()
                            .getBurgerservicenummer());

                if (moederModel != null) {
                    Persoon vader = RelatieUtils.haalNietMoederUitRelatie(nieuweSituatie);

                    List<PersoonModel> kandidatenVaders =
                        kandidaatVader.bepaalKandidatenVader(moederModel,
                                ((FamilierechtelijkeBetrekkingBericht) nieuweSituatie).getKindBetrokkenheid()
                                        .getPersoon().getGeboorte().getDatumGeboorte());

                    if (kandidatenVaders.size() > 0 && vader == null) {
                        meldingen.add(new Melding(SoortMelding.DEBLOKKEERBAAR, MeldingCode.BRBY0033,
                                MeldingCode.BRBY0033_1.getOmschrijving(), kind, "ouder"));
                    } else if (vader != null && vader.getIdentificatienummers() != null
                        && vader.getIdentificatienummers().getBurgerservicenummer() != null)
                    {
                        boolean isInKandidaatLijst = false;
                        for (PersoonModel kv : kandidatenVaders) {

                            if (kv.getIdentificatienummers().getBurgerservicenummer().getWaarde()
                                    .equals(vader.getIdentificatienummers().getBurgerservicenummer().getWaarde()))
                            {
                                isInKandidaatLijst = true;
                                break;
                            }
                        }

                        if (!isInKandidaatLijst) {
                            meldingen.add(new Melding(SoortMelding.DEBLOKKEERBAAR, MeldingCode.BRBY0033,
                                    MeldingCode.BRBY0033.getOmschrijving(), (Identificeerbaar) vader,
                                    "identificatienummers"));
                        }
                    } else {
                        // TODO op het moment niet in scope
                        LOGGER.warn("Persoon vader niet aanwezig of heeft geen BSN. Bedrijfsregel {} "
                            + "wordt dus niet uitgevoerd.", getCode());
                    }
                } else {
                    // Er wordt vanuit gegaan dat er een andere business regel is die controlleert of de persoon bestaat
                    // met de bsn.
                    // TODO: bolie, moet omgezet worden naar technische sleutel
                    LOGGER.warn("Persoon moeder kan niet gevonden worden met BSN {}. Bedrijfsregel {} "
                        + "wordt dus niet uitgevoerd.", new Object[] {
                        moeder.getIdentificatienummers().getBurgerservicenummer().toString(), getCode() });
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
