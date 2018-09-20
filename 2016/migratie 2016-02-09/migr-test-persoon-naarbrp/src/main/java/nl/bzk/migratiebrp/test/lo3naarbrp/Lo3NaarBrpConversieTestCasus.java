/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.lo3naarbrp;

import java.io.File;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3Lg01BerichtWaarde;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.test.common.resultaat.TestResultaat;
import nl.bzk.migratiebrp.test.common.resultaat.TestStap;
import nl.bzk.migratiebrp.test.dal.AbstractConversieTestCasus;
import nl.bzk.migratiebrp.test.dal.TestCasusOutputStap;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.util.common.logging.LoggingContext;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Test casus: conversie lo3 naar brp.
 */
public final class Lo3NaarBrpConversieTestCasus extends AbstractConversieTestCasus {
    private static final Logger LOG = LoggerFactory.getLogger();

    private final Lo3Lg01BerichtWaarde lo3Lg01BerichtWaarde;
    private final Lo3SyntaxException lse;

    /**
     * Constructor.
     *
     * @param thema
     *            thema
     * @param naam
     *            naam
     * @param outputFolder
     *            output folder
     * @param expectedFolder
     *            expected folder
     * @param lo3Lg01BerichtWaarde
     *            lo3 bericht waarden
     */
    protected Lo3NaarBrpConversieTestCasus(
        final String thema,
        final String naam,
        final File outputFolder,
        final File expectedFolder,
        final Lo3Lg01BerichtWaarde lo3Lg01BerichtWaarde)
    {
        super(thema, naam, outputFolder, expectedFolder);
        this.lo3Lg01BerichtWaarde = lo3Lg01BerichtWaarde;
        lse = null;
    }

    /**
     * Constructor.
     *
     * @param thema
     *            thema
     * @param naam
     *            naam
     * @param outputFolder
     *            output folder
     * @param expectedFolder
     *            expected folder
     * @param lse
     *            de syntax exception die kan optreden bij het inlezen van de PL
     */
    protected Lo3NaarBrpConversieTestCasus(
        final String thema,
        final String naam,
        final File outputFolder,
        final File expectedFolder,
        final Lo3SyntaxException lse)
    {
        super(thema, naam, outputFolder, expectedFolder);
        lo3Lg01BerichtWaarde = null;
        this.lse = lse;
    }

    /**
     * Stappen van deze test.
     * <UL>
     * <LI>Parsen van de inkomende persoonslijst naar Java objecten</LI>
     * <LI>Controle op syntax en precondities</LI>
     * <LI>Converteer de LO3 persoonslijst naar een BRP persoonslijst</LI>
     * <LI>Converteer de BRP persoonslijst terug naar een LO3 persoonslijst</LI>
     * <LI>Sla de BRP persoonslijst op in de database</LI>
     * <LI>Lees de BRP persoonslijst uit de database</LI>
     * <LI>Converteer de gelezen BRP persoonslijst naar een LO3 persoonslijst</LI>
     * </UL>
     *
     * Als er een {@link Lo3SyntaxException} optreedt bij het inlezen van de testgevallen, dan wordt deze getoond in de
     * stap {@link TestCasusOutputStap#STAP_SYNTAX_PRECONDITIES}.<br>
     * Als uit de controle op syntax en precondities een dummy persoonslijst komt, dan wordt deze alleen naar BRP
     * geconverteerd en opgeslagen in de database.
     *
     * @return {@link TestResultaat} met daarin de resultaat van deze testrun.
     */
    @Override
    public TestResultaat run() {
        final long startInMillis = System.currentTimeMillis();
        final Lo3NaarBrpConversieTestResultaat result = new Lo3NaarBrpConversieTestResultaat(getThema(), getNaam());

        try {
            if (lse != null) {
                throw lse;
            } else {
                final String htmlBron = initializeerLogging(lo3Lg01BerichtWaarde.getLo3CategorieWaardeList());
                result.setBron(htmlBron);

                final Logging logging = Logging.getLogging();
                try {
                    // Test syntax en precondities
                    final Pair<Lo3Persoonslijst, TestStap> syntaxPreResult = testSyntaxPrecondities(lo3Lg01BerichtWaarde.getLo3CategorieWaardeList());
                    final Lo3Persoonslijst checkedPl = syntaxPreResult.getLeft();
                    result.setSyntaxPrecondities(syntaxPreResult.getRight());

                    if (checkedPl != null) {
                        // Test en valideer conversie van lo3 naar brp
                        final Pair<BrpPersoonslijst, TestStap> lo3NaarBrpConversieResult = testLo3NaarBrp(checkedPl);
                        final BrpPersoonslijst brp = lo3NaarBrpConversieResult.getLeft();
                        result.setLo3NaarBrp(lo3NaarBrpConversieResult.getRight());

                        // Test rondverteer
                        final Pair<TestStap, TestStap> rondVerteerStap = testTerugconversie(brp, checkedPl, TestCasusOutputStap.STAP_ROND, null);

                        result.setRondverteer(rondVerteerStap.getLeft());
                        result.setRondverteerVerschilAnalyse(rondVerteerStap.getRight());

                        opslaanLezenTerugConversieBrp(result, lo3Lg01BerichtWaarde, checkedPl, brp);
                    }
                } finally {
                    result.setConversieLog(controleerLogging(logging));
                    LoggingContext.reset();
                    Logging.destroyContext();
                }
            }
            // Alle exception worden hier opgevangen en netjes getoond aan de gebruiker ipv dat de tests crashed
        } catch (final Lo3SyntaxException e) {
            result.setSyntaxPrecondities(controleerFoutmelding(e, TestCasusOutputStap.STAP_SYNTAX_PRECONDITIES));
        }

        final long endInMillis = System.currentTimeMillis();
        final double duration = (endInMillis - startInMillis) / (double) MILLIS_IN_SECOND;

        LOG.info("Testcase {} took {} seconds", getNaam(), duration);

        return result;
    }
}
