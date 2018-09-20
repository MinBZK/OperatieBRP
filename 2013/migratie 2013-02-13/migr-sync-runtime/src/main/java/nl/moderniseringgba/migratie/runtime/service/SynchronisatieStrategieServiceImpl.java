/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.runtime.service;

import javax.inject.Inject;

import nl.moderniseringgba.isc.esb.message.sync.generated.SearchResultaatType;
import nl.moderniseringgba.isc.esb.message.sync.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.sync.impl.SynchronisatieStrategieVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.SynchronisatieStrategieAntwoordBericht;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.proces.ConversieService;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;
import nl.moderniseringgba.migratie.synchronisatie.service.BrpDalService;

import org.springframework.stereotype.Service;

/**
 * De default implementatie van de SynchronisatieStrategieService.
 */
@Service
public final class SynchronisatieStrategieServiceImpl implements SynchronisatieStrategieService {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private BrpDalService brpDalService;

    @Inject
    private ConversieService conversieService;

    /**
     * {@inheritDoc}
     */
    @Override
    public SynchronisatieStrategieAntwoordBericht verwerkSynchronisatieStrategieVerzoek(
            final SynchronisatieStrategieVerzoekBericht synchronisatieStrategieVerzoekBericht) {
        LOG.info("[Bericht {}]: Verwerken opvragen huidige situtatie PL ...",
                synchronisatieStrategieVerzoekBericht.getMessageId());
        SynchronisatieStrategieAntwoordBericht result;
        try {
            final BrpPersoonslijst brpActueelObvAnummer =
                    zoekActueelPersoonObvANummer(synchronisatieStrategieVerzoekBericht);
            final BrpPersoonslijst brpHistorischObvAnummer =
                    zoekHistorischPersoonObvANummer(synchronisatieStrategieVerzoekBericht);
            if (synchronisatieStrategieVerzoekBericht.getVorigANummer() == null) {
                result =
                        bepaalSituatieZonderVorigANummer(synchronisatieStrategieVerzoekBericht, brpActueelObvAnummer,
                                brpHistorischObvAnummer);
            } else {
                final BrpPersoonslijst brpActueelObvVorig =
                        zoekActueelPersoonObvVorigANummer(synchronisatieStrategieVerzoekBericht);
                result =
                        bepaalSituatieMetVorigANummer(synchronisatieStrategieVerzoekBericht, brpActueelObvAnummer,
                                brpHistorischObvAnummer, brpActueelObvVorig);
            }
            // CHECKSTYLE:OFF - Catch exception voor het robuust afhandelen van exceptions op applicatie interface
            // niveau
        } catch (final Exception e) { // NOSONAR
            // CHECKSTYLE:ON
            LOG.error(String.format("[Bericht %s]: Fout bij bepalen synchronisatie strategie: %s",
                    synchronisatieStrategieVerzoekBericht.getMessageId(), e.getMessage()), e);
            result =
                    new SynchronisatieStrategieAntwoordBericht(synchronisatieStrategieVerzoekBericht.getMessageId(),
                            "Fout bij bepalen synchronisatie strategie: " + e.getMessage());
        }
        return result;
    }

    private SynchronisatieStrategieAntwoordBericht bepaalSituatieMetVorigANummer(
            final SynchronisatieStrategieVerzoekBericht synchronisatieStrategieVerzoekBericht,
            final BrpPersoonslijst brpActueelObvAnummer,
            final BrpPersoonslijst brpHistorischObvAnummer,
            final BrpPersoonslijst brpActueelObvVorig) {
        SynchronisatieStrategieAntwoordBericht result;
        if (brpActueelObvVorig == null) {
            if (brpActueelObvAnummer == null) {
                if (brpHistorischObvAnummer == null) {
                    result =
                            Strategie.SITUATIE_3A.maakAntwoordBericht(conversieService,
                                    synchronisatieStrategieVerzoekBericht, null);
                } else {
                    result =
                            Strategie.SITUATIE_3B.maakAntwoordBericht(conversieService,
                                    synchronisatieStrategieVerzoekBericht, null);
                }
            } else {
                result =
                        Strategie.SITUATIE_4.maakAntwoordBericht(conversieService,
                                synchronisatieStrategieVerzoekBericht, brpActueelObvAnummer);
            }
        } else {
            if (brpActueelObvAnummer == null) {
                final Long oudBsn =
                        brpActueelObvVorig.getIdentificatienummerStapel().getMeestRecenteElement().getInhoud()
                                .getBurgerservicenummer();

                if (oudBsn == null || !oudBsn.equals(synchronisatieStrategieVerzoekBericht.getBsn())) {
                    result =
                            Strategie.SITUATIE_5A.maakAntwoordBericht(conversieService,
                                    synchronisatieStrategieVerzoekBericht, brpActueelObvVorig);
                } else {
                    result =
                            Strategie.SITUATIE_5B.maakAntwoordBericht(conversieService,
                                    synchronisatieStrategieVerzoekBericht, brpActueelObvVorig);
                }
            } else {
                result =
                        Strategie.SITUATIE_6.maakAntwoordBericht(conversieService,
                                synchronisatieStrategieVerzoekBericht, brpActueelObvAnummer);
            }
        }
        return result;
    }

    private SynchronisatieStrategieAntwoordBericht bepaalSituatieZonderVorigANummer(
            final SynchronisatieStrategieVerzoekBericht synchronisatieStrategieVerzoekBericht,
            final BrpPersoonslijst brpActueelObvAnummer,
            final BrpPersoonslijst brpHistorischObvAnummer) {
        SynchronisatieStrategieAntwoordBericht result;
        if (brpActueelObvAnummer == null) {
            if (brpHistorischObvAnummer == null) {
                result =
                        Strategie.SITUATIE_1A.maakAntwoordBericht(conversieService,
                                synchronisatieStrategieVerzoekBericht, null);
            } else {
                result =
                        Strategie.SITUATIE_1B.maakAntwoordBericht(conversieService,
                                synchronisatieStrategieVerzoekBericht, null);
            }
        } else {
            result =
                    Strategie.SITUATIE_2.maakAntwoordBericht(conversieService, synchronisatieStrategieVerzoekBericht,
                            brpActueelObvAnummer);
        }
        return result;
    }

    private BrpPersoonslijst zoekActueelPersoonObvVorigANummer(
            final SynchronisatieStrategieVerzoekBericht synchronisatieStrategieVerzoekBericht) {
        final BrpPersoonslijst result;
        if (synchronisatieStrategieVerzoekBericht.getVorigANummer() != null) {
            result = brpDalService.zoekPersoonOpAnummer(synchronisatieStrategieVerzoekBericht.getVorigANummer());
        } else {
            result = null;
        }
        return result;
    }

    private BrpPersoonslijst
            zoekHistorischPersoonObvANummer(final SynchronisatieStrategieVerzoekBericht synchronisatieStrategieVerzoekBericht) {
        final BrpPersoonslijst result;
        if (synchronisatieStrategieVerzoekBericht.getANummer() != null) {
            result = brpDalService.zoekPersoonOpHistorischAnummer(synchronisatieStrategieVerzoekBericht.getANummer());
        } else {
            result = null;
        }
        return result;
    }

    private BrpPersoonslijst zoekActueelPersoonObvANummer(final SynchronisatieStrategieVerzoekBericht synchronisatieStrategieVerzoekBericht) {
        return brpDalService.zoekPersoonOpAnummer(synchronisatieStrategieVerzoekBericht.getANummer());
    }

    /**
     * De verschillede strategien.
     */
    private static enum Strategie {
        SITUATIE_1A(SearchResultaatType.TOEVOEGEN, "Situatie 1a: toevoegen PL; Er is geen te vervangen PL gevonden."),
        SITUATIE_1B(SearchResultaatType.NEGEREN,
                "Situatie 1b: toevoegen PL; De PL-Sync is ingehaald door de eerste A-nummer wijziging."),
        SITUATIE_2(SearchResultaatType.VERVANGEN, "Situatie 2: vervangen PL; De gevonden PL is de te vervangen PL."),
        SITUATIE_3A(SearchResultaatType.TOEVOEGEN, "Situatie 3a: toevoegen PL; Er is geen te vervangen PL gevonden."),
        SITUATIE_3B(SearchResultaatType.NEGEREN,
                "Situatie 3b: negeren PL; De PL-Sync is ingehaald door een latere A-nummer wijziging."), SITUATIE_4(
                SearchResultaatType.VERVANGEN,
                "Situatie 4: vervangen PL; Dit betreft een 'normale' wijziging op een PL met een al gesynchroniseerde "
                        + "A-nummer wijziging."), SITUATIE_5A(SearchResultaatType.ONDUIDELIJK,
                "Situatie 5a: onduidelijk; Dit betreft een nog niet gesynchroniseerde A-nummerwijziging op verzoek van "
                        + "GBA-V. De te vervangen PL is mogelijk degene die met vorig A-nummer is gevonden. "
                        + "Dit moet gecontroleerd worden."), SITUATIE_5B(SearchResultaatType.VERVANGEN,
                "Situatie 5b: vervangen; Dit betreft een nog niet gesynchroniseerde  A-nummerwijziging."),
        SITUATIE_6(SearchResultaatType.VERVANGEN,
                "Situatie 6: vervangen PL; Dit duidt op een herstel van een situatie waarbij één persoon twee PL-en "
                        + "met een verschillend A-nummer heeft.");

        private final SearchResultaatType searchResultaatType;
        private final String omschrijving;

        private Strategie(final SearchResultaatType searchResultaatType, final String omschrijving) {
            this.searchResultaatType = searchResultaatType;
            this.omschrijving = omschrijving;
        }

        public SynchronisatieStrategieAntwoordBericht maakAntwoordBericht(
                final ConversieService conversieService,
                final SynchronisatieStrategieVerzoekBericht synchronisatieStrategieVerzoekBericht,
                final BrpPersoonslijst brpPersoonslijst) {
            return maakSearchResponseBericht(conversieService, synchronisatieStrategieVerzoekBericht.getMessageId(),
                    searchResultaatType, omschrijving, brpPersoonslijst);
        }

        private SynchronisatieStrategieAntwoordBericht maakSearchResponseBericht(
                final ConversieService conversieService,
                final String correlationId,
                final SearchResultaatType resultaat,
                final String toelichting,
                final BrpPersoonslijst brpPersoonslijst) {
            final SynchronisatieStrategieAntwoordBericht result = new SynchronisatieStrategieAntwoordBericht();
            result.setCorrelationId(correlationId);
            try {
                result.setLo3Persoonslijst(brpPersoonslijst == null ? null : conversieService
                        .converteerBrpPersoonslijst(brpPersoonslijst));
                result.setStatus(StatusType.OK);
                result.setResultaat(resultaat);
                result.setToelichting(toelichting);
                // CHECKSTYLE:OFF - Catch exception voor het robuust afhandelen van exceptions op applicatie interface
                // niveau
            } catch (final Exception e) { // NOSONAR
                // CHECKSTYLE:ON
                LOG.error(
                        String.format("[Bericht %s]: Fout bij converteren BRP naar LO3: %s", correlationId,
                                e.getMessage()), e);
                result.setStatus(StatusType.FOUT);
                result.setToelichting("Fout bij converteren van BRP naar LO3: " + e.getMessage());
            }
            return result;
        }
    }
}
