/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.impl;

import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.regels.proces.ConversieHook;
import nl.bzk.migratiebrp.conversie.regels.proces.ConversieStap;
import nl.bzk.migratiebrp.conversie.regels.proces.ConverteerBrpNaarLo3Service;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.BrpBepalenAdellijkeTitel;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.BrpBepalenMaterieleHistorie;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.BrpConverteerder;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.BrpOnderzoekLo3;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.BrpSorterenLo3;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.util.common.logging.LoggingContext;
import org.springframework.stereotype.Service;

/**
 * Deze class implementeert de services die geleverd worden voor de terugconversie, van BRP naar Lo3.
 */

@Service
public class ConverteerBrpNaarLo3ServiceImpl implements ConverteerBrpNaarLo3Service {
    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private BrpBepalenMaterieleHistorie brpBepalenMaterieleHistorie;
    @Inject
    private BrpConverteerder brpConverteerder;
    @Inject
    private BrpBepalenAdellijkeTitel brpBepalenAdellijkeTitel;
    @Inject
    private BrpOnderzoekLo3 brpOnderzoekLo3;
    @Inject
    private BrpSorterenLo3 brpSorterenLo3;

    /**
     * Converteert een BrpPersoonslijst naar een Lo3Persoonslijst. Hiervoor worden de volgende stappen uitgevoerd:
     * 
     * <ul>
     * <li>stap 1: bepalen gegevens in gegevens set</li>
     * <li>stap 2: bepalen materiele historie</li>
     * <li>stap 3: converteer inhoud & historie</li>
     * <li>stap 4: adellijke titel / predikaat bijwerken voor geslacht</li>
     * <li>Stap 5: Sorteren</li>
     * </ul>
     * 
     * @param teConverterenPersoonslijst
     *            de te converteren BRP persoonslijst
     * @return een Lo3Persoonslijst
     */
    @Override
    public final Lo3Persoonslijst converteerBrpPersoonslijst(final BrpPersoonslijst teConverterenPersoonslijst) {
        return converteerBrpPersoonslijst(teConverterenPersoonslijst, ConversieHook.NULL_HOOK);
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
    public final Lo3Persoonslijst converteerBrpPersoonslijst(final BrpPersoonslijst teConverterenPersoonslijst, final ConversieHook hook) {
        LOG.debug("converteerBrpPersoonslijst(brpPersoonslijst.anummer={})", teConverterenPersoonslijst.getActueelAdministratienummer());
        try {
            // Log4j logging
            LoggingContext.registreerActueelAdministratienummer(teConverterenPersoonslijst.getActueelAdministratienummer());

            LOG.debug("Stap 0: Valideren BRP persoonslijst");
            teConverterenPersoonslijst.valideer();
            hook.stap(ConversieStap.BRP_NAAR_LO3_VALIDEER, teConverterenPersoonslijst);
            BrpPersoonslijst brpPersoonslijst = teConverterenPersoonslijst;

            LOG.debug("Stap 1: Bepalen materiele historie");
            brpPersoonslijst = brpBepalenMaterieleHistorie.converteer(brpPersoonslijst);
            hook.stap(ConversieStap.BRP_NAAR_LO3_BEPALEN_MATERIELE_HISTORIE, brpPersoonslijst);

            LOG.debug("Stap 2: Converteer");
            Lo3Persoonslijst lo3Persoonslijst = brpConverteerder.converteer(brpPersoonslijst);
            hook.stap(ConversieStap.BRP_NAAR_LO3_CONVERTEREN, lo3Persoonslijst);

            LOG.debug("Stap 3: Adellijke titel / Predikaat bepalen");
            lo3Persoonslijst = brpBepalenAdellijkeTitel.converteer(lo3Persoonslijst);
            hook.stap(ConversieStap.BRP_NAAR_LO3_ADELLIJKE_TITEL_PREDIKAAT_BEPALEN, lo3Persoonslijst);

            LOG.debug("Stap 4: Onderzoek bepalen");
            lo3Persoonslijst = brpOnderzoekLo3.converteer(lo3Persoonslijst);
            hook.stap(ConversieStap.BRP_NAAR_LO3_ONDERZOEK, lo3Persoonslijst);

            LOG.debug("Stap 5: Sorteren");
            lo3Persoonslijst = brpSorterenLo3.converteer(lo3Persoonslijst);
            hook.stap(ConversieStap.BRP_NAAR_LO3_SORTEREN, lo3Persoonslijst);

            return lo3Persoonslijst;
        } finally {
            // Reset Logging van a-nummer in logfiles
            LoggingContext.reset();
        }
    }
}
