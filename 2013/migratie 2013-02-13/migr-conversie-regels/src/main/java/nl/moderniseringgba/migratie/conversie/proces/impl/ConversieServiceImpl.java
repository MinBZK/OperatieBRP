/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.impl;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.conversie.model.Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratiePersoonslijst;
import nl.moderniseringgba.migratie.conversie.proces.ConversieService;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.BrpBepalenAdellijkeTitel;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.BrpBepalenGegevensSet;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.BrpBepalenMaterieleHistorie;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.BrpConverteerder;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.BrpEmigratieOpschortingVuller;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.BrpOudersSamenvoegen;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.BrpOudersToevoegen;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.BrpRelatiesOpschonen;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.BrpSorterenLo3;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.BrpVerbintenissenOpschonen;
import nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.Lo3BepalenAfgeleideGegevens;
import nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.Lo3HistorieConversie;
import nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.Lo3InhoudNaarBrpConversieStap;
import nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.Lo3InhoudRedundantie;
import nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.Lo3SplitsenGerelateerdeOuders;
import nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.Lo3SplitsenVerbintenis;
import nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.Lo3ToevoegenExtraDocumentatie;
import nl.moderniseringgba.migratie.conversie.proces.logging.Logging;
import nl.moderniseringgba.migratie.conversie.validatie.InputValidationException;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;
import nl.moderniseringgba.migratie.logging.LoggingContext;

import org.springframework.stereotype.Service;

/**
 * Deze class implementeert de services die geleverd worden voor de conversie.
 */
// CHECKSTYLE:OFF - Class Fan-Out Complexity - groot aantal stappen. Geen probleem
@Service
public final class ConversieServiceImpl implements ConversieService {
    // CHECKSTYLE:ON

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final ConversieHook NULL_HOOK = new ConversieHook() {
        @Override
        public void stap(final ConversieStap stap, final Persoonslijst persoonslijst) {
            // Niets
        }

    };

    @Inject
    private Lo3ToevoegenExtraDocumentatie lo3ToevoegenExtraDocumentatie;
    @Inject
    private Lo3SplitsenVerbintenis lo3SplitsenVerbintenis;
    @Inject
    private Lo3SplitsenGerelateerdeOuders lo3SplitsenGerelateerdeOuders;
    @Inject
    private Lo3InhoudNaarBrpConversieStap lo3InhoudNaarBrpConversieStap;
    @Inject
    private Lo3InhoudRedundantie lo3InhoudRedundantie;
    @Inject
    private Lo3HistorieConversie lo3HistorieConversie;
    @Inject
    private Lo3BepalenAfgeleideGegevens lo3BepalenAfgeleideGegevens;

    @Inject
    private BrpVerbintenissenOpschonen brpVerbintenissenOpschonen;
    @Inject
    private BrpBepalenGegevensSet brpBepalenGegevensSet;
    @Inject
    private BrpBepalenMaterieleHistorie brpBepalenMaterieleHistorie;
    @Inject
    private BrpConverteerder brpConverteerder;
    @Inject
    private BrpOudersSamenvoegen brpOudersSamenvoegen;
    @Inject
    private BrpRelatiesOpschonen brpRelatiesOpschonen;
    @Inject
    private BrpOudersToevoegen brpOudersToevoegen;
    @Inject
    private BrpBepalenAdellijkeTitel brpBepalenAdellijkeTitel;
    @Inject
    private BrpEmigratieOpschortingVuller brpEmigratieOpschortingVuller;
    @Inject
    private BrpSorterenLo3 brpSorterenLo3;

    /**
     * Converteert een Lo3Persoonslijst naar een BrpPersoonslijst. Hiervoor worden de volgende stappen uitgevoerd:
     * 
     * <ul>
     * <li>Stap 0: valideer LO3 model
     * <li>Stap 1a: extra documentatie ({@link Lo3ToevoegenExtraDocumentatie})</li>
     * <li>Stap 1b: splitsen verbintenissen ({@link Lo3SplitsenVerbintenis})</li>
     * <li>Stap 1c: splitsen ouders ({@link Lo3SplitsenGerelateerdeOuders})</li>
     * <li>Stap 2a: conversie inhoud ({@link Lo3InhoudNaarBrpConversieStap})</li>
     * <li>Stap 2b: verwijder redundantie ({@link Lo3InhoudRedundantie})</li>
     * <li>Stap 3: conversie historie ({@link Lo3HistorieConversie})</li>
     * <li>Stap 4: afgeleide gegevens ({@link Lo3BepalenAfgeleideGegevens})</li>
     * </ul>
     * 
     * @param lo3Persoonslijst
     *            de LO3 persoonslijst
     * 
     * @return een BrpPersoonslijst
     * @throws InputValidationException
     *             bij inhoudelijke fouten waardoor de brp persoonlijst niet gemaakt kan worden
     */
    @Override
    public BrpPersoonslijst converteerLo3Persoonslijst(final Lo3Persoonslijst lo3Persoonslijst)
            throws InputValidationException {
        return converteerLo3Persoonslijst(lo3Persoonslijst, NULL_HOOK);
    }

    /**
     * Converteert een Lo3Persoonslijst naar een BrpPersoonslijst.
     * 
     * @param lo3Persoonslijst
     *            de LO3 persoonslijst
     * @param hook
     *            hook
     * @return een BrpPersoonslijst
     * @throws InputValidationException
     *             bij inhoudelijke fouten waardoor de brp persoonlijst niet gemaakt kan worden
     */
    // CHECKSTYLE:OFF - Executable statement count
    public final BrpPersoonslijst converteerLo3Persoonslijst(
    // CHECKSTYLE:ON
            final Lo3Persoonslijst lo3Persoonslijst,
            final ConversieHook hook) throws InputValidationException {
        try {
            LOG.debug("converteerLo3Persoonslijst(lo3Persoonslijst.anummer={})",
                    lo3Persoonslijst.getActueelAdministratienummer());

            // Log4j logging
            LoggingContext.registreerActueelAdministratienummer(lo3Persoonslijst.getActueelAdministratienummer());

            LOG.debug("Stap 1a: Extra documentatie toevoegen");
            final Lo3Persoonslijst gedocumenteerdeLijst = lo3ToevoegenExtraDocumentatie.converteer(lo3Persoonslijst);
            hook.stap(ConversieStap.LO3_NAAR_BRP_EXTRA_DOCUMENTATIE, gedocumenteerdeLijst);

            LOG.debug("Stap 1b: Splitsen (verbintenissen)");
            final Lo3Persoonslijst gesplitseVerbintenissen = lo3SplitsenVerbintenis.converteer(gedocumenteerdeLijst);
            hook.stap(ConversieStap.LO3_NAAR_BRP_SPLITSEN_VERBINTENISSEN, gesplitseVerbintenissen);

            LOG.debug("Stap 1c: Splitsen (ouders)");
            final Lo3Persoonslijst gesplitsteOuder =
                    lo3SplitsenGerelateerdeOuders.converteer(gesplitseVerbintenissen);
            hook.stap(ConversieStap.LO3_NAAR_BRP_SPLITSEN_OUDERS, gesplitsteOuder);

            LOG.debug("Stap 2a: Conversie inhoud");
            final MigratiePersoonslijst migratiePersoonslijst =
                    lo3InhoudNaarBrpConversieStap.converteer(gesplitsteOuder);
            hook.stap(ConversieStap.LO3_NAAR_BRP_CONVERSIE_INHOUD, migratiePersoonslijst);

            LOG.debug("Stap 2b: Verwijder redundantie");
            final MigratiePersoonslijst gereduceerdePersoonslijst =
                    lo3InhoudRedundantie.converteer(migratiePersoonslijst);
            hook.stap(ConversieStap.LO3_NAAR_BRP_VERWIJDER_REDUNDANTIE, gereduceerdePersoonslijst);

            LOG.debug("Stap 3: Conversie historie");
            final BrpPersoonslijst brpPersoonslijst = lo3HistorieConversie.converteer(gereduceerdePersoonslijst);
            hook.stap(ConversieStap.LO3_NAAR_BRP_CONVERSIE_HISTORIE, brpPersoonslijst);

            LOG.debug("Stap 4: Afgeleide gegevens");
            final BrpPersoonslijst completeBrpPersoonslijst =
                    lo3BepalenAfgeleideGegevens.converteer(brpPersoonslijst);
            hook.stap(ConversieStap.LO3_NAAR_BRP_AFGELEIDE_GEGEVENS, completeBrpPersoonslijst);

            // Result
            LOG.debug("Done");
            return completeBrpPersoonslijst;
        } finally {
            // Log4j logging
            LoggingContext.reset();
        }
    }

    /**
     * Converteert een BrpPersoonslijst naar een Lo3Persoonslijst. Hiervoor worden de volgende stappen uitgevoerd:
     * 
     * <ul>
     * <li>stap 1: bepalen gegevens in gegevens set</li>
     * <li>stap 2: bepalen materiele historie</li>
     * <li>stap 3: converteer inhoud & historie</li>
     * <li>stap 4: ouders samenvoegen</li>
     * <li>Stap 5: Opschonen relaties</li>
     * <li>stap 6: juridisch geen ouder toevoegen</li>
     * <li>stap 7: adellijke titel / predikaat bijwerken voor geslacht</li>
     * <li>Stap 8: Opschorten in geval van emigratie</li>
     * <li>Stap 9: Sorteren</li>
     * </ul>
     * 
     * @param teConverterenPersoonslijst
     *            de te converteren BRP persoonslijst
     * @return een Lo3Persoonslijst
     */
    @Override
    public Lo3Persoonslijst converteerBrpPersoonslijst(final BrpPersoonslijst teConverterenPersoonslijst) {
        return converteerBrpPersoonslijst(teConverterenPersoonslijst, NULL_HOOK);
    }

    /**
     * Converteert een BrpPersoonslijst naar een Lo3Persoonslijst.
     * 
     * @param teConverterenPersoonslijst
     *            de te converteren BRP persoonslijst
     * @param hook
     *            hook
     * @return een Lo3Persoonslijst
     */
    // CHECKSTYLE:OFF - Executable statement count
    public final Lo3Persoonslijst converteerBrpPersoonslijst(
    // CHECKSTYLE:ON
            final BrpPersoonslijst teConverterenPersoonslijst,
            final ConversieHook hook) {
        LOG.debug("converteerBrpPersoonslijst(brpPersoonslijst.anummer={})",
                teConverterenPersoonslijst.getActueelAdministratienummer());
        try {
            // Log4j logging
            LoggingContext.registreerActueelAdministratienummer(teConverterenPersoonslijst
                    .getActueelAdministratienummer());

            LOG.debug("Stap 0a: Valideren BRP persoonslijst");
            teConverterenPersoonslijst.valideer();
            hook.stap(ConversieStap.BRP_NAAR_LO3_VALIDEER, teConverterenPersoonslijst);

            LOG.debug("Stap 0b: Opschonen verbintenissen");
            BrpPersoonslijst brpPersoonslijst = brpVerbintenissenOpschonen.converteer(teConverterenPersoonslijst);
            hook.stap(ConversieStap.BRP_NAAR_LO3_OPSCHONEN_VERBINTENISSEN, brpPersoonslijst);

            LOG.debug("Stap 1: Bepalen gegevens in gegevens set");
            brpPersoonslijst = brpBepalenGegevensSet.converteer(brpPersoonslijst);
            hook.stap(ConversieStap.BRP_NAAR_LO3_BEPALEN_GEGEVENS_SET, brpPersoonslijst);

            LOG.debug("Stap 2: Bepalen materiele historie");
            brpPersoonslijst = brpBepalenMaterieleHistorie.converteer(brpPersoonslijst);
            hook.stap(ConversieStap.BRP_NAAR_LO3_BEPALEN_MATERIELE_HISTORIE, brpPersoonslijst);

            LOG.debug("Stap 3: Converteer");
            Lo3Persoonslijst lo3Persoonslijst = brpConverteerder.converteer(brpPersoonslijst);
            hook.stap(ConversieStap.BRP_NAAR_LO3_CONVERTEREN, lo3Persoonslijst);

            LOG.debug("Stap 4: Ouders samenvoegen");
            lo3Persoonslijst = brpOudersSamenvoegen.converteer(lo3Persoonslijst);
            hook.stap(ConversieStap.BRP_NAAR_LO3_OUDERS_SAMENVOEGEN, lo3Persoonslijst);

            LOG.debug("Stap 5: Opschonen relaties");
            lo3Persoonslijst = brpRelatiesOpschonen.converteer(lo3Persoonslijst);
            hook.stap(ConversieStap.BRP_NAAR_LO3_OPSCHONEN_RELATIES, lo3Persoonslijst);

            LOG.debug("Stap 6: juridisch geen ouder toevoegen");
            lo3Persoonslijst = brpOudersToevoegen.converteer(lo3Persoonslijst);
            hook.stap(ConversieStap.BRP_NAAR_LO3_JURIDISCH_GEEN_OUDER_TOEVOEGEN, lo3Persoonslijst);

            LOG.debug("Stap 7: Adellijke titel / Predikaat bepalen");
            lo3Persoonslijst = brpBepalenAdellijkeTitel.converteer(lo3Persoonslijst);
            hook.stap(ConversieStap.BRP_NAAR_LO3_ADELLIJKE_TITEL_PREDIKAAT_BEPALEN, lo3Persoonslijst);

            LOG.debug("Stap 8: Opschorten in geval van emigratie");
            lo3Persoonslijst = brpEmigratieOpschortingVuller.converteer(lo3Persoonslijst, brpPersoonslijst);
            hook.stap(ConversieStap.BRP_NAAR_LO3_OPSCHORTEN_VOOR_EMIGRATIE, lo3Persoonslijst);

            LOG.debug("Stap 9: Sorteren");
            lo3Persoonslijst = brpSorterenLo3.converteer(lo3Persoonslijst);
            hook.stap(ConversieStap.BRP_NAAR_LO3_SORTEREN, lo3Persoonslijst);

            return lo3Persoonslijst;
        } finally {
            // Reset Logging van a-nummer in logfiles
            LoggingContext.reset();
            // Reset Logging van pre-condities en conversie.
            Logging.destroyContext();
        }
    }
}
