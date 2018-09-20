/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.db;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3Lg01BerichtWaarde;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.synchronisatie.dal.util.brpkern.DBUnitBrpUtil;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;
import nl.bzk.migratiebrp.test.common.resultaat.Foutmelding;
import nl.bzk.migratiebrp.test.common.resultaat.TestResultaat;
import nl.bzk.migratiebrp.test.common.resultaat.TestStap;
import nl.bzk.migratiebrp.test.dal.AbstractConversieTestCasus;
import nl.bzk.migratiebrp.test.dal.TestCasusOutputStap;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.util.common.logging.LoggingContext;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Casus implementatie om de database inhoud te kunnen testen mbhv SQL-queries.
 */
@SuppressWarnings("checkstyle:illegalcatch")
public class DbConversieTestCasus extends AbstractConversieTestCasus {
    private static final Logger LOG = LoggerFactory.getLogger();

    private final List<Lo3Lg01BerichtWaarde> lo3Lg01BerichtWaardes;
    private final Lo3SyntaxException lse;
    private final Map<String, List<File>> sqlQueriesPerThema;

    @Inject
    private DBUnitBrpUtil dbUnit;

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
     * @param lo3Lg01BerichtWaardes
     *            lo3 bericht waarden
     * @param sqlQueriesPerThema
     *            sql queries
     */
    protected DbConversieTestCasus(
        final String thema,
        final String naam,
        final File outputFolder,
        final File expectedFolder,
        final List<Lo3Lg01BerichtWaarde> lo3Lg01BerichtWaardes,
        final Map<String, List<File>> sqlQueriesPerThema)
    {
        super(thema, naam, outputFolder, expectedFolder);
        this.lo3Lg01BerichtWaardes = lo3Lg01BerichtWaardes;
        lse = null;
        this.sqlQueriesPerThema = sqlQueriesPerThema;
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
    protected DbConversieTestCasus(
        final String thema,
        final String naam,
        final File outputFolder,
        final File expectedFolder,
        final Lo3SyntaxException lse)
    {
        super(thema, naam, outputFolder, expectedFolder);
        lo3Lg01BerichtWaardes = null;
        sqlQueriesPerThema = null;
        this.lse = lse;
    }

    @Override
    public final TestResultaat run() {
        final long startInMillis = System.currentTimeMillis();
        final DbConversieTestResultaat result = new DbConversieTestResultaat(getThema(), getNaam());

        // Maar eerst de database resetten
        dbUnit.resetDB(this.getClass(), LOG, false);

        if (lse != null) {
            controleerFoutmelding(lse, TestCasusOutputStap.STAP_SYNTAX_PRECONDITIES);
        } else {
            int id = 0;
            for (final Lo3Lg01BerichtWaarde lo3Lg01BerichtWaarde : lo3Lg01BerichtWaardes) {
                id = verwerkBericht(result, id, lo3Lg01BerichtWaarde);
            }
            result.setAantalResultaten(id);
        }

        final long endInMillis = System.currentTimeMillis();
        final float duration = (endInMillis - startInMillis) / (float) MILLIS_IN_SECOND;

        LOG.info("Testcase {} took {} seconds", getNaam(), duration);

        return result;
    }

    @Transactional(value = "syncDalTransactionManager", propagation = Propagation.REQUIRED)
    private int verwerkBericht(final DbConversieTestResultaat result, final int id, final Lo3Lg01BerichtWaarde lo3Lg01BerichtWaarde) {
        final String idString = "" + id;
        final DbConversieTestConversieResultaat conversieResult = new DbConversieTestConversieResultaat(id);
        final List<Lo3CategorieWaarde> lo3CategorieWaarden = lo3Lg01BerichtWaarde.getLo3CategorieWaardeList();

        try {

            final String htmlBron = initializeerLogging(lo3CategorieWaarden);
            result.setBron(htmlBron);

            final Logging logging = Logging.getLogging();
            try {
                // // Test syntax en precondities
                final Pair<Lo3Persoonslijst, TestStap> syntaxPreResult = testSyntaxPrecondities(lo3CategorieWaarden, idString);
                final Lo3Persoonslijst checkedPl = syntaxPreResult.getLeft();
                conversieResult.setSyntaxPrecondities(syntaxPreResult.getRight());

                if (checkedPl != null) {
                    // Test en valideer conversie van lo3 naar brp
                    final Pair<BrpPersoonslijst, TestStap> lo3NaarBrpConversieResult = testLo3NaarBrp(checkedPl, idString);
                    final BrpPersoonslijst brp = lo3NaarBrpConversieResult.getLeft();
                    conversieResult.setLo3NaarBrp(lo3NaarBrpConversieResult.getRight());

                    opslaanLezenTerugConversieBrp(conversieResult, lo3Lg01BerichtWaarde, checkedPl, brp, idString);
                }
            } finally {
                conversieResult.setConversieLog(controleerLogging(logging, idString));
                conversieResult.setKruimelpad(controleerKruimelpad(SynchronisatieLogging.getMelding(), idString));
                LoggingContext.reset();
                Logging.destroyContext();
                SynchronisatieLogging.init();
            }

            // SQL queries uitvoeren.
            result.setSqlControles(testSqlQueries(sqlQueriesPerThema.get(getThema())));
        } catch (final BadSqlGrammarException sqlException) {
            result.setFoutmelding(new Foutmelding(sqlException.getClass().getName(), sqlException));
        } catch (final Exception e) {
            conversieResult.setSyntaxPrecondities(controleerFoutmelding(e, TestCasusOutputStap.STAP_SYNTAX_PRECONDITIES));
        }

        // Set aantal resultaten.
        result.addConversieResultaat(conversieResult);
        return id + 1;
    }
}
