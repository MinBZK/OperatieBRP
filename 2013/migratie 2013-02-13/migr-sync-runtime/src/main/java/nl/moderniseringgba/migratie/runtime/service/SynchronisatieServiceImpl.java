/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.runtime.service;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;

import nl.moderniseringgba.isc.esb.message.BerichtSyntaxException;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Inhoud;
import nl.moderniseringgba.isc.esb.message.lo3.parser.Lo3PersoonslijstParser;
import nl.moderniseringgba.isc.esb.message.sync.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.sync.impl.SynchroniseerNaarBrpAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.moderniseringgba.migratie.conversie.model.logging.LogRegel;
import nl.moderniseringgba.migratie.conversie.proces.ConversieService;
import nl.moderniseringgba.migratie.conversie.proces.logging.Logging;
import nl.moderniseringgba.migratie.conversie.proces.preconditie.Lo3SyntaxControle;
import nl.moderniseringgba.migratie.conversie.proces.preconditie.OngeldigePersoonslijstException;
import nl.moderniseringgba.migratie.conversie.proces.preconditie.PreconditiesService;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Persoon;
import nl.moderniseringgba.migratie.synchronisatie.domein.logging.entity.BerichtLog;
import nl.moderniseringgba.migratie.synchronisatie.service.BrpDalService;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.LoggingMapper;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * De default implementatie van de SynchronisatieService.
 */
@Service
public final class SynchronisatieServiceImpl implements SynchronisatieService {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private Lo3SyntaxControle syntaxControle;

    @Inject
    private PreconditiesService preconditieService;

    @Inject
    private ConversieService conversieService;

    @Inject
    private BrpDalService brpDalService;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public SynchroniseerNaarBrpAntwoordBericht verwerkSynchroniseerNaarBrpVerzoek(
            final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoekBericht,
            final String bron,
            final boolean isInitieelVullingProces) {
        final BerichtLog berichtLog =
                BerichtLog.newInstance(synchroniseerNaarBrpVerzoekBericht.getMessageId(), bron,
                        synchroniseerNaarBrpVerzoekBericht.getLo3BerichtAsTeletexString());

        SynchroniseerNaarBrpAntwoordBericht result;
        try {
            Logging.initContext();
            try {

                final Lo3Persoonslijst lo3Persoonslijst =
                        parseLo3PersoonslijstString(
                                synchroniseerNaarBrpVerzoekBericht.getLo3BerichtAsTeletexString(),
                                isInitieelVullingProces);

                SynchronisatieServiceImpl.aanvullenBerichtLog(berichtLog, lo3Persoonslijst);

                final Persoon persoon =
                        SynchronisatieServiceImpl.saveBrpPersoonslijst(brpDalService,
                                synchroniseerNaarBrpVerzoekBericht.getMessageId(), synchroniseerNaarBrpVerzoekBericht
                                        .getANummerTeVervangenPl(), conversieService
                                        .converteerLo3Persoonslijst(preconditieService.verwerk(lo3Persoonslijst)));

                mapLogRegelsOpBerichtLog(berichtLog, persoon);
                brpDalService.persisteerBerichtLog(berichtLog);

                result =
                        new SynchroniseerNaarBrpAntwoordBericht(synchroniseerNaarBrpVerzoekBericht.getMessageId(),
                                Logging.getLogging().getRegels());
            } catch (final BerichtSyntaxException bse) {
                result =
                        FoutAfhandeling.PARSE_FOUT.verwerkFoutEnMaakAntwoord(synchroniseerNaarBrpVerzoekBericht,
                                berichtLog, bse, this);
            } catch (final OngeldigePersoonslijstException ope) {
                result =
                        FoutAfhandeling.INHOUDELIJKE_FOUT.verwerkFoutEnMaakAntwoord(
                                synchroniseerNaarBrpVerzoekBericht, berichtLog, ope, this);
            }
            // CHECKSTYLE:OFF - Catch exception voor het robuust afhandelen van exceptions op applicatie interface
            // niveau
        } catch (final Exception e) { // NOSONAR
            // CHECKSTYLE:ON
            result =
                    FoutAfhandeling.ONBEKENDE_FOUT.verwerkFoutEnMaakAntwoord(synchroniseerNaarBrpVerzoekBericht,
                            berichtLog, e, this);
        } finally {
            Logging.destroyContext();
        }
        return result;
    }

    // ---------------------------------- Private methods

    private void mapLogRegelsOpBerichtLog(final BerichtLog berichtLog) {
        mapLogRegelsOpBerichtLog(berichtLog, null);
    }

    private void mapLogRegelsOpBerichtLog(final BerichtLog berichtLog, final Persoon persoon) {
        new LoggingMapper().mapLogging(Logging.getLogging(), berichtLog, persoon);
    }

    private Lo3Persoonslijst parseLo3PersoonslijstString(
            final String lo3BerichtAsTeletexString,
            final boolean isInitieelVullingProces) throws BerichtSyntaxException, OngeldigePersoonslijstException {
        final List<Lo3CategorieWaarde> lo3Inhoud = Lo3Inhoud.parseInhoud(lo3BerichtAsTeletexString);
        final List<Lo3CategorieWaarde> lo3InhoudNaSyntaxControle = syntaxControle.controleer(lo3Inhoud);
        Lo3Persoonslijst lo3Persoonslijst = new Lo3PersoonslijstParser().parse(lo3InhoudNaSyntaxControle);
        if (isInitieelVullingProces && lo3Persoonslijst.getInschrijvingStapel() != null
                && lo3Persoonslijst.isGroep80VanInschrijvingStapelLeeg()) {
            lo3Persoonslijst = lo3Persoonslijst.maakKopieMetDefaultGroep80VoorInschrijvingStapel();
        }
        return lo3Persoonslijst;
    }

    /**
     * De verschillende strategien om fouten af te handelen.
     */
    private enum FoutAfhandeling {
        PARSE_FOUT("[Bericht {}]: Fout bij het parsen van het LO3 PL bericht.", StatusType.BERICHT_PARSE_FOUT, false),
        INHOUDELIJKE_FOUT("[Bericht {}]: Fout bij inhoudelijk interpreteren van het LO3 PL bericht.",
                StatusType.BERICHT_INHOUD_FOUT, false), ONBEKENDE_FOUT(
                "[Bericht {}]: Onbekende fout bij synchroniseren naar de BRP.", StatusType.FOUT, true);

        private final String logMessage;
        private final StatusType statusType;
        private final boolean isError;

        private FoutAfhandeling(final String logMessage, final StatusType statusType, final boolean isError) {
            this.logMessage = logMessage;
            this.statusType = statusType;
            this.isError = isError;
        }

        public SynchroniseerNaarBrpAntwoordBericht verwerkFoutEnMaakAntwoord(
                final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoekBericht,
                final BerichtLog berichtLog,
                final Exception exception,
                final SynchronisatieServiceImpl service) {
            final String correlatieId = synchroniseerNaarBrpVerzoekBericht.getMessageId();
            if (isError) {
                LOG.error(logMessage, correlatieId, exception);
            } else {
                LOG.info(logMessage, correlatieId, exception);
            }
            service.mapLogRegelsOpBerichtLog(berichtLog);
            try {
                service.brpDalService.persisteerBerichtLog(berichtLog);
                // CHECKSTYLE:OFF - Catch exception voor het robuust afhandelen van exceptions tijdens foutafhandeling
            } catch (final Exception e) {
                // CHECKSTYLE:ON
                LOG.error("FoutAfhandeling: fout opgetreden bij opslaan BerichtLog", e);
            }

            return maakAntwoordBericht(correlatieId, exception, Logging.getLogging().getRegels());
        }

        private SynchroniseerNaarBrpAntwoordBericht maakAntwoordBericht(
                final String correlatieId,
                final Exception exception,
                final List<LogRegel> logRegels) {
            return new SynchroniseerNaarBrpAntwoordBericht(correlatieId, statusType, exception.getMessage(),
                    logRegels);
        }
    }

    /**
     * Helper methode voor het opslaan van een BRP persoon m.b.v. de BrpDalService.
     * 
     * Wordt voorlopig gebruikt door de OpslaanInBrpService om code duplicatie te voorkomen. Deze methode kan weer
     * private worden zodra de OpslaanInBrpService is verwijderd.
     * 
     * @param brpDalService
     *            de DAL voor het opslaan van BRP personen
     * @param messageId
     *            het message id van het bericht
     * @param aNummerTeVervangenPl
     *            het anummer dat moet worden vervangen of null als er geen sprake is van anummer wijziging
     * @param brpPersoonslijst
     *            de BRP persoonslijst die moet worden opgeslagen
     * @return de BRP persoon die is opgeslagen
     */
    static Persoon saveBrpPersoonslijst(
            final BrpDalService brpDalService,
            final String messageId,
            final Long aNummerTeVervangenPl,
            final BrpPersoonslijst brpPersoonslijst) {
        LOG.info("[Bericht {}]: Persisteer BRP conversie model ...", messageId);
        final Persoon result;
        if (aNummerTeVervangenPl != null) {
            LOG.info("[Bericht {}]: Bericht met a-nummer wijziging.", messageId);
            result =
                    brpDalService.persisteerPersoonslijst(brpPersoonslijst, BigDecimal.valueOf(aNummerTeVervangenPl));
        } else {
            result = brpDalService.persisteerPersoonslijst(brpPersoonslijst);
        }
        return result;
    }

    /**
     * Vult het berichtlog object met het a-nummer en gemeente van inschrijving uit de lo3 persoonslijst.
     * 
     * Wordt voorlopig gebruikt door de OpslaanInBrpService om code duplicatie te voorkomen. Deze methode kan weer
     * private worden zodra de OpslaanInBrpService is verwijderd.
     * 
     * @param berichtLog
     *            het BerichtLog object dat moet worden aangevuld
     * @param lo3Persoonslijst
     *            de lo3 persoonslijst waaruit de gegevens moeten worden gehaald
     */
    static void aanvullenBerichtLog(final BerichtLog berichtLog, final Lo3Persoonslijst lo3Persoonslijst) {
        berichtLog.setGemeenteVanInschrijving(SynchronisatieServiceImpl
                .bepaalGemeenteVanInschrijving(lo3Persoonslijst));
        berichtLog.setAdministratienummer(lo3Persoonslijst.getActueelAdministratienummerAsBigDecimal());
    }

    /**
     * Zoekt de gemeente van inschrijving in de meegegeven lo3 persoonslijst.
     * 
     * @param lo3Persoonslijst
     *            de lo3 persooonslijst waarin gezocht wordt naar de gemeente van inschrijving
     * @return de code van de gemeente van inschrijving of null als deze niet gevonden kan worden
     */
    private static Integer bepaalGemeenteVanInschrijving(final Lo3Persoonslijst lo3Persoonslijst) {
        Integer result = null;
        if (lo3Persoonslijst.getVerblijfplaatsStapel() != null
                && lo3Persoonslijst.getVerblijfplaatsStapel().getMeestRecenteElement().getInhoud()
                        .getGemeenteInschrijving() != null) {
            result =
                    Integer.parseInt(lo3Persoonslijst.getVerblijfplaatsStapel().getMeestRecenteElement().getInhoud()
                            .getGemeenteInschrijving().getCode());
        }
        return result;
    }
}
