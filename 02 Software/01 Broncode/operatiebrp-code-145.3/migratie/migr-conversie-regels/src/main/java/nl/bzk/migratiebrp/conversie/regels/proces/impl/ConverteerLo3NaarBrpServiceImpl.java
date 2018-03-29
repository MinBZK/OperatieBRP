/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.impl;

import java.util.List;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.logging.LoggingContext;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAfnemersindicaties;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAutorisatie;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenis;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3ToevalligeGebeurtenis;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Afnemersindicatie;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Autorisatie;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.tussen.autorisatie.TussenAfnemersindicaties;
import nl.bzk.migratiebrp.conversie.model.tussen.autorisatie.TussenAutorisatie;
import nl.bzk.migratiebrp.conversie.regels.proces.ConversieHook;
import nl.bzk.migratiebrp.conversie.regels.proces.ConversieStap;
import nl.bzk.migratiebrp.conversie.regels.proces.ConverteerLo3NaarBrpService;
import nl.bzk.migratiebrp.conversie.regels.proces.NullHook;
import nl.bzk.migratiebrp.conversie.regels.proces.foutmelding.Foutmelding;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.Lo3BepalenAfgeleideGegevens;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.Lo3HistorieConversie;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.Lo3InhoudNaarBrpConversieStap;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.Lo3VerzamelBrpOnderzoeken;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.Lo3VerzamelLo3Onderzoeken;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.BepaalVoornamen;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.autorisatie.AfnemersIndicatieConversie;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.toevalligegebeurtenis.ToevalligeGebeurtenisConversie;
import org.springframework.stereotype.Service;

/**
 * Deze class implementeert de services die geleverd worden voor de conversie van Lo3 naar BRP.
 */

@Service
public final class ConverteerLo3NaarBrpServiceImpl implements ConverteerLo3NaarBrpService {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final String CONVERSIE_AFNEMERSINDICATIE_GEREED = "Conversie afnemersindicatie gereed";

    private final Lo3InhoudNaarBrpConversieStap lo3InhoudNaarBrpConversieStap;
    private final Lo3VerzamelLo3Onderzoeken lo3VerzamelLo3Onderzoeken;
    private final Lo3VerzamelBrpOnderzoeken lo3VerzamelBrpOnderzoeken;
    private final Lo3HistorieConversie lo3HistorieConversie;
    private final Lo3BepalenAfgeleideGegevens lo3BepalenAfgeleideGegevens;
    private final AfnemersIndicatieConversie afnemersIndicatieConversie;
    private final ToevalligeGebeurtenisConversie toevalligeGebeurtenisConversie;
    private BepaalVoornamen bepaalVoornamen;

    /**
     * Constructor.
     * @param lo3InhoudNaarBrpConversieStap lo3 inhoud
     * @param lo3VerzamelLo3Onderzoeken lo3 onderzoeken
     * @param lo3VerzamelBrpOnderzoeken brp onderzoeken
     * @param lo3HistorieConversie historie conversie
     * @param afnemersIndicatieConversie indicatie conversie
     * @param toevalligeGebeurtenisConversie toevallige gebeurtenis
     */
    @Inject
    public ConverteerLo3NaarBrpServiceImpl(final Lo3InhoudNaarBrpConversieStap lo3InhoudNaarBrpConversieStap,
                                           final Lo3VerzamelLo3Onderzoeken lo3VerzamelLo3Onderzoeken,
                                           final Lo3VerzamelBrpOnderzoeken lo3VerzamelBrpOnderzoeken, final Lo3HistorieConversie lo3HistorieConversie,
                                           final AfnemersIndicatieConversie afnemersIndicatieConversie,
                                           final ToevalligeGebeurtenisConversie toevalligeGebeurtenisConversie){
        bepaalVoornamen = new BepaalVoornamen();
        lo3BepalenAfgeleideGegevens = new Lo3BepalenAfgeleideGegevens(bepaalVoornamen);
        this.lo3InhoudNaarBrpConversieStap = lo3InhoudNaarBrpConversieStap;
        this.lo3VerzamelLo3Onderzoeken = lo3VerzamelLo3Onderzoeken;
        this.lo3VerzamelBrpOnderzoeken = lo3VerzamelBrpOnderzoeken;
        this.lo3HistorieConversie = lo3HistorieConversie;
        this.afnemersIndicatieConversie = afnemersIndicatieConversie;
        this.toevalligeGebeurtenisConversie = toevalligeGebeurtenisConversie;
    }

    /**
     * Converteert een Lo3Persoonslijst naar een BrpPersoonslijst. Hiervoor worden de volgende stappen uitgevoerd:
     *
     * <ul>
     * <li>Stap 2: conversie inhoud ({@link Lo3InhoudNaarBrpConversieStap})</li>
     * <li>Stap 3: conversie historie ({@link Lo3HistorieConversie})</li>
     * <li>Stap 4: afgeleide gegevens ({@link Lo3BepalenAfgeleideGegevens})</li>
     * </ul>
     * @param lo3Persoonslijst de LO3 persoonslijst
     * @return een BrpPersoonslijst
     */
    @Override
    public BrpPersoonslijst converteerLo3Persoonslijst(final Lo3Persoonslijst lo3Persoonslijst) {
        return converteerLo3Persoonslijst(lo3Persoonslijst, new NullHook());
    }

    /**
     * Converteert een Lo3Persoonslijst naar een BrpPersoonslijst.
     * @param lo3Persoonslijst de LO3 persoonslijst
     * @param hook hook
     * @return een BrpPersoonslijst
     */
    public BrpPersoonslijst converteerLo3Persoonslijst(final Lo3Persoonslijst lo3Persoonslijst, final ConversieHook hook) {
        try {
            LOG.debug("converteerLo3Persoonslijst(lo3Persoonslijst.anummer={})", lo3Persoonslijst.getActueelAdministratienummer());

            // Log4j logging
            LoggingContext.registreerActueelAdministratienummer(lo3Persoonslijst.getActueelAdministratienummer());

            LOG.debug("Stap 1: Conversie inhoud");
            final TussenPersoonslijst tussenPersoonslijst = lo3InhoudNaarBrpConversieStap.converteer(lo3Persoonslijst);
            hook.stap(ConversieStap.LO3_NAAR_BRP_CONVERSIE_INHOUD, tussenPersoonslijst);

            LOG.debug("Stap 2: Conversie historie");
            final BrpPersoonslijst brpPersoonslijst = lo3HistorieConversie.converteer(tussenPersoonslijst);
            hook.stap(ConversieStap.LO3_NAAR_BRP_CONVERSIE_HISTORIE, brpPersoonslijst);

            LOG.debug("Stap 3: Afgeleide gegevens");
            final BrpPersoonslijst completeBrpPersoonslijst = lo3BepalenAfgeleideGegevens.converteer(brpPersoonslijst);
            hook.stap(ConversieStap.LO3_NAAR_BRP_AFGELEIDE_GEGEVENS, completeBrpPersoonslijst);

            // Bepalen Bijzondere situatie LB039
            final List<Lo3Herkomst> verzameldeLo3OnderzoekHerkomsten = lo3VerzamelLo3Onderzoeken.verzamelHerkomstenBijOnderzoek(lo3Persoonslijst);
            final List<Lo3Herkomst> verzameldeBrpOnderzoekHerkomsten = lo3VerzamelBrpOnderzoeken.verzamelHerkomstenBijOnderzoek(brpPersoonslijst);
            for (final Lo3Herkomst verzameldeLo3OnderzoekHerkomst : verzameldeLo3OnderzoekHerkomsten) {
                if (!verzameldeBrpOnderzoekHerkomsten.contains(verzameldeLo3OnderzoekHerkomst)) {
                    Foutmelding.logMeldingFout(verzameldeLo3OnderzoekHerkomst, LogSeverity.INFO, SoortMeldingCode.BIJZ_CONV_LB039, null);
                }
            }

            // Result
            LOG.debug("Conversie persoonlijst gereed");
            return completeBrpPersoonslijst;
        } finally {
            // Log4j logging
            LoggingContext.reset();
        }
    }

    @Override
    public BrpAutorisatie converteerLo3Autorisatie(final Lo3Autorisatie lo3Autorisatie) {
        return converteerLo3Autorisatie(lo3Autorisatie, new NullHook());
    }

    /**
     * Converteert een lo3Autorisatie naar een BrpAutorisatie.
     * @param lo3Autorisatie de te converteren lo3Autorisatie
     * @param hook hook
     * @return een BrpAutorisatie
     */
    public BrpAutorisatie converteerLo3Autorisatie(final Lo3Autorisatie lo3Autorisatie, final ConversieHook hook) {
        LOG.debug("converteerLo3Autorisatie(lo3Autorisatie.afnemerindicatie={})", lo3Autorisatie.getAfnemersindicatie());

        LOG.debug("Stap 1: Converteer inhoud");
        final TussenAutorisatie tussenAutorisatie = lo3InhoudNaarBrpConversieStap.converteer(lo3Autorisatie);
        hook.stap(ConversieStap.LO3_NAAR_BRP_CONVERSIE_INHOUD, tussenAutorisatie);

        LOG.debug("Stap 2: Converteer historie");
        BrpAutorisatie brpAutorisatie = lo3HistorieConversie.converteer(tussenAutorisatie);
        hook.stap(ConversieStap.LO3_NAAR_BRP_CONVERSIE_HISTORIE, brpAutorisatie);

        LOG.debug("Stap 2b: Verwijder redundantie");
        brpAutorisatie = lo3HistorieConversie.opschonenVerantwooding(brpAutorisatie);
        hook.stap(ConversieStap.LO3_NAAR_BRP_VERWIJDER_REDUNDANTIE, brpAutorisatie);

        // Result
        LOG.debug("Conversie autorisatie gereed");
        return brpAutorisatie;
    }

    @Override
    public BrpAfnemersindicaties converteerLo3Afnemersindicaties(final Lo3Afnemersindicatie lo3Afnemersindicaties) {
        return converteerLo3Afnemersindicaties(lo3Afnemersindicaties, new NullHook());
    }

    /**
     * Converteert een lo3Afnemersindicaties naar een BrpAfnemersindicaties.
     * @param input de te converteren lo3Afnemersindicaties
     * @param hook hook
     * @return een BrpAfnemersindicaties
     */
    public BrpAfnemersindicaties converteerLo3Afnemersindicaties(final Lo3Afnemersindicatie input, final ConversieHook hook) {
        LOG.debug("converteerLo3Afnemersindicaties(lo3Afnemersindicaties={})", input);

        LOG.debug("Stap 1: Filter stapels (met dezelfde partijcode)");
        final Lo3Afnemersindicatie lo3Afnemersindicaties = afnemersIndicatieConversie.filterStapels(input);
        hook.stap(ConversieStap.LO3_NAAR_BRP_VERWIJDER_REDUNDANTIE, input);

        LOG.debug("Stap 2: Conversie inhoud (afnemersindicatie)");
        final TussenAfnemersindicaties tussenAfnemersindicaties = lo3InhoudNaarBrpConversieStap.converteer(lo3Afnemersindicaties);
        hook.stap(ConversieStap.LO3_NAAR_BRP_CONVERSIE_INHOUD, tussenAfnemersindicaties);

        LOG.debug("Stap 3: Conversie historie (afnemersindicatie)");
        BrpAfnemersindicaties brpAfnemersindicaties = lo3HistorieConversie.converteer(tussenAfnemersindicaties);
        hook.stap(ConversieStap.LO3_NAAR_BRP_CONVERSIE_HISTORIE, brpAfnemersindicaties);

        LOG.debug("Stap 4: Vullen afgeleide gegevens");
        brpAfnemersindicaties = afnemersIndicatieConversie.vulAfgeleideGegevens(brpAfnemersindicaties);
        hook.stap(ConversieStap.LO3_NAAR_BRP_AFGELEIDE_GEGEVENS, brpAfnemersindicaties);

        // Result
        LOG.debug(CONVERSIE_AFNEMERSINDICATIE_GEREED);
        return brpAfnemersindicaties;
    }

    @Override
    public BrpToevalligeGebeurtenis converteerLo3ToevalligeGebeurtenis(final Lo3ToevalligeGebeurtenis lo3ToevalligeGebeurtenis) {
        LOG.debug("Stap 1: Conversie inhoud (toevallige gebeurtenis)");
        final BrpToevalligeGebeurtenis brpToevalligeGebeurtenis = toevalligeGebeurtenisConversie.converteer(lo3ToevalligeGebeurtenis);
        // Result
        LOG.debug(CONVERSIE_AFNEMERSINDICATIE_GEREED);
        return brpToevalligeGebeurtenis;
    }
}
