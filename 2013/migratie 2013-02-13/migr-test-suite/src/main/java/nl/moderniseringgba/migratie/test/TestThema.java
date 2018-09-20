/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.test;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;
import nl.moderniseringgba.migratie.test.resultaat.Foutmelding;
import nl.moderniseringgba.migratie.test.resultaat.TestResultaat;

/**
 * Test thema.
 */
public final class TestThema {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final String thema;
    private final File inputFolder;
    private final FilenameFilter casusFilter;
    private final TestCasusFactory testCasusFactory;

    /**
     * Construcor.
     * 
     * @param thema
     *            thema
     * @param inputFolder
     *            input folder
     * @param outputFolder
     *            output folder
     * @param expectedFolder
     *            expected folder
     * @param casusFilter
     *            casus filter
     * @param testCasusFactory
     *            test casus factory
     */
    TestThema(
            final String thema,
            final File inputFolder,
            final File outputFolder,
            final File expectedFolder,
            final FilenameFilter casusFilter,
            final TestCasusFactory testCasusFactory) {
        this.thema = thema;
        this.inputFolder = inputFolder;
        this.casusFilter = casusFilter;
        this.testCasusFactory = testCasusFactory;
    }

    /**
     * Run alle testen in dit thema.
     * 
     * @return resultaten
     */
    public List<TestResultaat> run() {
        LOG.info("run(thema={})", thema);
        final List<TestResultaat> result = new ArrayList<TestResultaat>();

        try {
            // Run tests
            final List<String> files = Arrays.asList(inputFolder.list());
            Collections.sort(files);

            for (final String file : files) {
                if (casusFilter.accept(inputFolder, file)) {
                    final File inputFile = new File(inputFolder, file);

                    try {
                        final List<TestCasus> casussen = testCasusFactory.leesTestCasussen(inputFile);
                        if (casussen == null) {
                            LOG.warn("Geen testcasussen gevonden in {}", inputFile.getPath());
                        } else {
                            for (final TestCasus casus : casussen) {
                                result.add(casus.run());
                            }
                        }
                        // CHECKSTYLE:OFF - Test moet robust zijn en niet knallen bij exceptions. Dus alles opvangen
                    } catch (final Exception e) {
                        LOG.info("Fout bij inlezen of uitvoeren test casus. ", e);
                        // CHECKSTYLE:ON
                        final TestResultaat testResultaat = new TestResultaat(thema, file) {
                        };
                        testResultaat.setFoutmelding(new Foutmelding(
                                "Onverwachte fout bij inlezen en uitvoeren test casus.", e));
                        result.add(testResultaat);
                    }
                } else {
                    LOG.info("Casus '{}' overslaan ...", file);
                }
            }

            // CHECKSTYLE:OFF - Test moet robust zijn en niet knallen bij exceptions. Dus alles opvangen
        } catch (final Exception e) {
            // CHECKSTYLE:ON
            LOG.warn("Fout tijdens uitvoeren test thema.", e);
        }

        return result;
    }
}
