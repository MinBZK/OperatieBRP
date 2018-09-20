/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.service;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.brp.bijhouding.business.dto.bijhouding.BijhoudingResultaat;
import nl.bzk.brp.bijhouding.business.stappen.LockStap;
import nl.bzk.brp.bijhouding.business.stappen.administratievehandeling.PubliceerAdministratieveHandelingStap;
import nl.bzk.brp.bijhouding.business.stappen.bijhouding.BerichtVerrijkingsStap;
import nl.bzk.brp.bijhouding.business.stappen.bijhouding.BerichtVerwerkingStap;
import nl.bzk.brp.bijhouding.business.stappen.bijhouding.BijhoudingBerichtVerplichteObjectenValidatieStap;
import nl.bzk.brp.bijhouding.business.stappen.bijhouding.BijhoudingGegevensValidatieStap;
import nl.bzk.brp.bijhouding.business.stappen.bijhouding.BijhoudingObjectSleutelVerificatieStap;
import nl.bzk.brp.bijhouding.business.stappen.bijhouding.BijhoudingRootObjectenOpHaalStap;
import nl.bzk.brp.bijhouding.business.stappen.bijhouding.BijhoudingRootObjectenOpslagStap;
import nl.bzk.brp.bijhouding.business.stappen.bijhouding.BijhoudingRootObjectenSerialisatieStap;
import nl.bzk.brp.bijhouding.business.stappen.bijhouding.BijhoudingTransactieStap;
import nl.bzk.brp.bijhouding.business.stappen.bijhouding.GedeblokkeerdeMeldingenOverschotControleStap;
import nl.bzk.brp.bijhouding.business.stappen.bijhouding.GedeblokkeerdeMeldingenValidatieStap;
import nl.bzk.brp.bijhouding.business.stappen.bijhouding.MaakNotificatieBerichtStap;
import nl.bzk.brp.bijhouding.business.stappen.bijhouding.NaBerichtRegelStap;
import nl.bzk.brp.bijhouding.business.stappen.bijhouding.VoorBerichtRegelStap;
import nl.bzk.brp.bijhouding.business.stappen.bijhouding.ZetNotificatieBerichtOpQueueStap;
import nl.bzk.brp.bijhouding.business.stappen.context.BijhoudingBerichtContext;
import nl.bzk.brp.bijhouding.business.stappen.resultaat.Resultaat;
import nl.bzk.brp.bijhouding.business.stappen.resultaat.ResultaatMelding;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.model.validatie.Melding;
import org.springframework.stereotype.Service;


/**
 * Service voor het verwerken van bijhoudingsberichten.
 */
@Service
public class BijhoudingsBerichtVerwerker {

    private static final Logger LOGGER                    = LoggerFactory.getLogger();

    @Inject
    private BijhoudingBerichtVerplichteObjectenValidatieStap bijhoudingBerichtVerplichteObjectenValidatieStap;
    @Inject
    private VoorBerichtRegelStap                             voorBerichtRegelStap;
    @Inject
    private GedeblokkeerdeMeldingenValidatieStap             gedeblokkeerdeMeldingenValidatieStap;
    @Inject
    private BerichtVerrijkingsStap                           berichtVerrijkingsStap;
    @Inject
    private BijhoudingGegevensValidatieStap                  bijhoudingGegevensValidatieStap;
    @Inject
    private BijhoudingObjectSleutelVerificatieStap           bijhoudingObjectSleutelVerificatieStap;
    @Inject
    private LockStap                                         lockStap;
    @Inject
    private BijhoudingTransactieStap                         bijhoudingTransactieStap;
    @Inject
    private BijhoudingRootObjectenOpHaalStap                 bijhoudingRootObjectenOpHaalStap;
    @Inject
    private BerichtVerwerkingStap                            berichtVerwerkingStap;
    @Inject
    private MaakNotificatieBerichtStap                       maakNotificatieBerichtStap;
    @Inject
    private ZetNotificatieBerichtOpQueueStap                 zetNotificatieBerichtOpQueueStap;
    @Inject
    private NaBerichtRegelStap                               naBerichtRegelStap;
    @Inject
    private GedeblokkeerdeMeldingenOverschotControleStap     gedeblokkeerdeMeldingenOverschotControleStap;
    @Inject
    private BijhoudingRootObjectenOpslagStap                 bijhoudingRootObjectenOpslagStap;
    @Inject
    private BijhoudingRootObjectenSerialisatieStap           bijhoudingRootObjectenSerialisatieStap;
    @Inject
    private PubliceerAdministratieveHandelingStap            publiceerAdministratieveHandelingStap;
    @Inject
    private DeblokkeerService                                deblokkeerService;

    /**
     * Verwerkt het bericht door achtereenvolgens een aantal stappen uit te voeren.
     *
     * @param bericht het bijhoudingsbericht
     * @param context de bijhoudingsberichtcontext
     * @return het bijhoudingsresultaat
     */
    public BijhoudingResultaat verwerkBericht(final BijhoudingsBericht bericht, final BijhoudingBerichtContext context) {
        LOGGER.debug("Berichtverwerker start verwerking bericht: {} {}", bericht.getSoort(), bericht);

        final BijhoudingResultaat berichtResultaat = new BijhoudingResultaat(null);
        berichtResultaat.setTijdstipRegistratie(context.getTijdstipVerwerking());

        Resultaat resultaat = voerNietTransactioneleStappenUit(bericht, context);

        if (!resultaat.bevatVerwerkingStoppendeMelding()) {
            resultaat = resultaat.voegToe(voerLockUit(bericht, context));
            publiceerAdministratieveHandelingStap.voerUit(context);
        }

        // Zolang TEAMBRP-4705 niet klaar is moeten we het Resultaat weer verwerken in het BijhoudingResultaat
        verwerkResultaatInBijhoudingResultaat(berichtResultaat, resultaat);

        return berichtResultaat;
    }

    private void verwerkResultaatInBijhoudingResultaat(final BijhoudingResultaat berichtResultaat, final Resultaat resultaat) {
        berichtResultaat.voegMeldingenToe(maakBerichtMeldingen(resultaat));
        berichtResultaat.setAdministratieveHandeling(resultaat.getAdministratieveHandeling());
        for (Integer teArchiverenPersoonId : resultaat.getTeArchiverenPersoonIdsIngaandBericht()) {
            berichtResultaat.getTeArchiverenPersonenIngaandBericht().add(teArchiverenPersoonId);
        }
    }

    @SuppressWarnings("checkstyle:illegalcatch")
    private Resultaat voerLockUit(final BijhoudingsBericht bericht, final BijhoudingBerichtContext context) {
        Resultaat resultaat = Resultaat.LEEG;

        try {
            resultaat = resultaat.voegToe(lockStap.vergrendel(context));
            verifieerVerwerkingStoppendeFouten(resultaat);
            resultaat = resultaat.voegToe(voerTransactieUit(bericht, context));
        } catch (VerwerkingStoppendeFoutException e) {
            // fout reeds verwerkt
            LOGGER.error("Verwerking lockstap beëindigd");
        } catch (Exception e) {
            resultaat = resultaat.voegToe(ResultaatMelding.builder().build());
            LOGGER.error("Onbekende fout in gelockte fase.", e);
        } finally {
            try {
                lockStap.ontgrendel();
            } catch (Exception e) {
                resultaat = resultaat.voegToe(ResultaatMelding.builder().build());
                LOGGER.error("Onbekende fout bij ontgrendelen lock.", e);
            }
        }

        return resultaat;
    }

    @SuppressWarnings("checkstyle:illegalcatch")
    private Resultaat voerTransactieUit(final BijhoudingsBericht bericht, final BijhoudingBerichtContext context) {
        Resultaat resultaat = Resultaat.LEEG;
        boolean transactieSuccesvol = false;
        try {
            bijhoudingTransactieStap.startTransactie(context);
            resultaat = resultaat.voegToe(voerTransactioneleStappenUit(bericht, context));
            transactieSuccesvol = !resultaat.bevatVerwerkingStoppendeMelding();
        } catch (VerwerkingStoppendeFoutException e) {
            // fout reeds verwerkt
            LOGGER.error("Verwerking transactionele stappen beëindigd");
        } catch (Exception t) {
            resultaat = resultaat.voegToe(ResultaatMelding.builder().build());
            LOGGER.error("Onbekende fout bij uitvoeren transactionele stappen.", t);
        } finally {
            try {
                resultaat = resultaat.voegToe(bijhoudingTransactieStap.stopTransactie(bericht, context, !transactieSuccesvol));
            } catch (Exception e) {
                resultaat = resultaat.voegToe(ResultaatMelding.builder().build());
                LOGGER.error("Onbekende fout bij stoppen transactie.", e);
            }
        }

        return resultaat;
    }

    @SuppressWarnings("checkstyle:illegalcatch")
    private Resultaat voerNietTransactioneleStappenUit(final BijhoudingsBericht bericht, final BijhoudingBerichtContext context) {
        Resultaat resultaat = Resultaat.LEEG;

        try {
            resultaat = resultaat.voegToe(bijhoudingBerichtVerplichteObjectenValidatieStap.voerStapUit(bericht));
            verifieerVerwerkingStoppendeFouten(resultaat);

            // Fix voor archivering. Als de voorBerichtRegelStap faalt, dan is de partij nodig voor de archivering.
            bericht.getStuurgegevens().setZendendePartij(context.getPartij());

            resultaat = resultaat.voegToe(voorBerichtRegelStap.voerStapUit(bericht));
            resultaat = deblokkeerService.deblokkeerResultaatMeldingen(bericht, context, resultaat);
            verifieerVerwerkingStoppendeFouten(resultaat);

            resultaat = resultaat.voegToe(gedeblokkeerdeMeldingenValidatieStap.voerStapUit(bericht));
            resultaat = deblokkeerService.deblokkeerResultaatMeldingen(bericht, context, resultaat);
            verifieerVerwerkingStoppendeFouten(resultaat);

            resultaat = resultaat.voegToe(berichtVerrijkingsStap.voerStapUit(bericht, context));
            resultaat = deblokkeerService.deblokkeerResultaatMeldingen(bericht, context, resultaat);
            verifieerVerwerkingStoppendeFouten(resultaat);

            resultaat = resultaat.voegToe(bijhoudingGegevensValidatieStap.voerStapUit(bericht));
            resultaat = deblokkeerService.deblokkeerResultaatMeldingen(bericht, context, resultaat);
            verifieerVerwerkingStoppendeFouten(resultaat);

            resultaat = resultaat.voegToe(bijhoudingObjectSleutelVerificatieStap.voerStapUit(bericht, context));
            resultaat = deblokkeerService.deblokkeerResultaatMeldingen(bericht, context, resultaat);
            verifieerVerwerkingStoppendeFouten(resultaat);

        } catch (VerwerkingStoppendeFoutException e) {
            // fout reeds verwerkt
            LOGGER.error("Verwerking niet-transactionele stappen beëindigd");
        } catch (Exception e) {
            resultaat = resultaat.voegToe(ResultaatMelding.builder().build());
            LOGGER.error("Onbekende fout bij uitvoeren niet-transactionele stappen.", e);
        }

        return resultaat;
    }

    @SuppressWarnings("checkstyle:illegalcatch")
    private Resultaat voerTransactioneleStappenUit(final BijhoudingsBericht bericht, final BijhoudingBerichtContext context) {
        Resultaat resultaat = Resultaat.LEEG;

        try {
            resultaat = resultaat.voegToe(bijhoudingRootObjectenOpHaalStap.voerStapUit(bericht, context));
            resultaat = deblokkeerService.deblokkeerResultaatMeldingen(bericht, context, resultaat);
            verifieerVerwerkingStoppendeFouten(resultaat);

            resultaat = resultaat.voegToe(berichtVerwerkingStap.voerStapUit(bericht, context));
            resultaat = deblokkeerService.deblokkeerResultaatMeldingen(bericht, context, resultaat);
            verifieerVerwerkingStoppendeFouten(resultaat);

            resultaat = resultaat.voegToe(maakNotificatieBerichtStap.voerStapUit(bericht, context));
            resultaat = deblokkeerService.deblokkeerResultaatMeldingen(bericht, context, resultaat);
            verifieerVerwerkingStoppendeFouten(resultaat);

            resultaat = resultaat.voegToe(zetNotificatieBerichtOpQueueStap.voerStapUit(context));
            resultaat = deblokkeerService.deblokkeerResultaatMeldingen(bericht, context, resultaat);
            verifieerVerwerkingStoppendeFouten(resultaat);

            resultaat = resultaat.voegToe(naBerichtRegelStap.voerStapUit(bericht, context));
            resultaat = deblokkeerService.deblokkeerResultaatMeldingen(bericht, context, resultaat);
            verifieerVerwerkingStoppendeFouten(resultaat);

            resultaat = resultaat.voegToe(gedeblokkeerdeMeldingenOverschotControleStap.voerStapUit(bericht));
            resultaat = deblokkeerService.deblokkeerResultaatMeldingen(bericht, context, resultaat);
            verifieerVerwerkingStoppendeFouten(resultaat);

            resultaat = resultaat.voegToe(bijhoudingRootObjectenOpslagStap.voerStapUit(bericht, context));

            resultaat = resultaat.voegToe(bijhoudingRootObjectenSerialisatieStap.voerStapUit(bericht, context));
        } catch (VerwerkingStoppendeFoutException e) {
            // fout reeds verwerkt
            LOGGER.error("Verwerking transactionele stappen beëindigd");
        } catch (Exception e) {
            resultaat = resultaat.voegToe(ResultaatMelding.builder().build());
            LOGGER.error("Onbekende fout bij uitvoeren transactionele stappen.", e);
        }

        return resultaat;
    }

    private void verifieerVerwerkingStoppendeFouten(final Resultaat resultaat) {
        if (resultaat.bevatVerwerkingStoppendeMelding()) {
            LOGGER.warn("Logische fout/stop in uitvoerende stap gedetecteerd.");
            for (ResultaatMelding m : resultaat.getMeldingen()) {
                LOGGER.warn("{}:{}", m.getRegel().getCode(), m.getMeldingTekst());
            }
            throw new VerwerkingStoppendeFoutException();
        }
    }

    private List<Melding> maakBerichtMeldingen(final Resultaat resultaat) {
        final List<Melding> meldingen = new ArrayList<>(resultaat.getMeldingen().size());
        for (ResultaatMelding resultaatMelding : resultaat.getMeldingen()) {
            final Melding melding = new Melding(resultaatMelding.getSoort(), resultaatMelding.getRegel(), resultaatMelding.getMeldingTekst());
            melding.setAttribuutNaam(resultaatMelding.getAttribuutNaam());
            melding.setReferentieID(resultaatMelding.getReferentieID());
            meldingen.add(melding);
        }
        return meldingen;
    }

    /**
     * Exception die wordt geworpen bij het het aantreffen van een verwerking stoppende fout.
     */
    static class VerwerkingStoppendeFoutException extends RuntimeException {

    }
}
