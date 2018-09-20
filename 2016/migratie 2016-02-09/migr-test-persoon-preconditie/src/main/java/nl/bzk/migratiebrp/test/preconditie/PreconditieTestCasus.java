/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.preconditie;

import java.io.File;
import java.util.List;
import nl.bzk.migratiebrp.bericht.model.lo3.parser.Lo3PersoonslijstParser;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.test.common.resultaat.TestStap;
import nl.bzk.migratiebrp.test.dal.AbstractConversieTestCasus;
import nl.bzk.migratiebrp.test.dal.TestCasusOutputStap;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.util.common.logging.LoggingContext;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Test casus: precondities.
 */
@SuppressWarnings("checkstyle:illegalcatch")
public final class PreconditieTestCasus extends AbstractConversieTestCasus {

    private static final Logger LOG = LoggerFactory.getLogger();
    private final Lo3PersoonslijstParser parser = new Lo3PersoonslijstParser();
    private final List<Lo3CategorieWaarde> lo3CategorieWaarden;
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
     * @param lo3CategorieWaarden
     *            categorieen
     */
    protected PreconditieTestCasus(
        final String thema,
        final String naam,
        final File outputFolder,
        final File expectedFolder,
        final List<Lo3CategorieWaarde> lo3CategorieWaarden)
    {
        super(thema, naam, outputFolder, expectedFolder);
        this.lo3CategorieWaarden = lo3CategorieWaarden;
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
     *            de Lo3SyntaxException die gegenereerd is bij het inlezen
     */
    protected PreconditieTestCasus(
        final String thema,
        final String naam,
        final File outputFolder,
        final File expectedFolder,
        final Lo3SyntaxException lse)
    {
        super(thema, naam, outputFolder, expectedFolder);
        lo3CategorieWaarden = null;
        this.lse = lse;
    }

    @Override
    public PreconditieTestResultaat run() {
        final long startInMillis = System.currentTimeMillis();
        final PreconditieTestResultaat result = new PreconditieTestResultaat(getThema(), getNaam());

        if (lse != null) {
            result.setSyntaxPrecondities(controleerFoutmelding(lse, TestCasusOutputStap.STAP_SYNTAX_PRECONDITIES));
        } else {
            try {
                final Lo3Persoonslijst uncheckedPl = parser.parse(lo3CategorieWaarden);
                result.setBron(debugOutputXmlEnHtml(uncheckedPl, TestCasusOutputStap.STAP_LO3));
                debugOutputLg01(uncheckedPl, TestCasusOutputStap.STAP_LO3);
                Logging.initContext();
                LoggingContext.registreerActueelAdministratienummer(uncheckedPl.getActueelAdministratienummer());
                final Logging logging = Logging.getLogging();

                try {
                    // Test syntax en precondities
                    final Pair<Lo3Persoonslijst, TestStap> syntaxPreResult = testSyntaxPrecondities(lo3CategorieWaarden);
                    result.setSyntaxPrecondities(syntaxPreResult.getRight());
                    final Lo3Persoonslijst checkedPl = syntaxPreResult.getLeft();
                    if (checkedPl != null) {
                        // Test en valideer conversie van lo3 naar brp
                        final Pair<BrpPersoonslijst, TestStap> lo3NaarBrpConversieResult = testLo3NaarBrp(checkedPl);
                        result.setLo3NaarBrp(lo3NaarBrpConversieResult.getRight());
                    }
                } finally {
                    result.setConversieLog(controleerLogging(logging));
                    LoggingContext.reset();
                    Logging.destroyContext();
                }
            } catch (final Exception e) {
                result.setSyntaxPrecondities(controleerFoutmelding(e, TestCasusOutputStap.STAP_SYNTAX_PRECONDITIES));
            }
        }

        final long endInMillis = System.currentTimeMillis();
        final float duration = (endInMillis - startInMillis) / (float) MILLIS_IN_SECOND;

        LOG.info("Testcase {} took {} seconds", getNaam(), duration);

        return result;
    }
}
