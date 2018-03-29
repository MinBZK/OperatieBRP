/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.dal.runner;

import java.io.File;
import nl.bzk.migratiebrp.test.common.resultaat.Foutmelding;
import nl.bzk.migratiebrp.test.common.resultaat.TestResultaat;
import nl.bzk.migratiebrp.test.dal.TestCasus;

/**
 * Testcasus voor een test build failure.
 */
public final class TestBuildFailure extends TestCasus {

    private final TestResultaat testResultaat;

    /**
     * Constructor.
     * @param thema thema
     * @param naam naam
     * @param outputFolder output folder
     * @param expectedFolder expected folder
     * @param exception De exceptie.
     */
    protected TestBuildFailure(final String thema, final String naam, final File outputFolder, final File expectedFolder, final Exception exception) {
        super(thema, naam, outputFolder, expectedFolder);
        testResultaat = new FailureTestResultaat(thema, naam, exception);
    }

    @Override
    public TestResultaat run() {
        return testResultaat;
    }

    /**
     * Testresultaat voor een build failure.
     */
    public static final class FailureTestResultaat extends TestResultaat {

        /**
         * Constructor.
         * @param thema thema
         * @param naam naam
         * @param exception De exceptie.
         */
        protected FailureTestResultaat(final String thema, final String naam, final Exception exception) {
            super(thema, naam);
            setFoutmelding(new Foutmelding("Test build failure:" + exception.getClass().getName(), exception));
        }

        /*
         * (non-Javadoc)
         *
         * @see nl.bzk.migratiebrp.test.common.resultaat.TestResultaat#isSucces()
         */
        @Override
        public boolean isSucces() {
            return false;
        }

    }

}
