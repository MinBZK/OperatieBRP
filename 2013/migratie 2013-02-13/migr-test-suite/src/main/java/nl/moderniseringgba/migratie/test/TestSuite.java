/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.test;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;
import nl.moderniseringgba.migratie.test.output.TestOutput;
import nl.moderniseringgba.migratie.test.resultaat.TestRapportage;
import nl.moderniseringgba.migratie.test.util.BaseFilter;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;

/**
 * Test suite.
 */
public final class TestSuite {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final String OUTPUT_FOLDER_SUFFIX = "-output";
    private static final String EXPECTED_FOLDER_SUFFIX = "-expected";
    private static final FilenameFilter ACCEPT_ALL_FILTER = new BaseFilter();

    private final String tijdstip;
    private final File inputFolder;
    private final File outputFolder;
    private final File expectedFolder;
    private final FilenameFilter themaFilter;
    private final FilenameFilter casusFilter;

    private final TestCasusFactory testCasusFactory;

    /**
     * Constructor.
     * 
     * @param inputFolder
     *            input
     * @param themaFilter
     *            thema filter
     * @param casusFilter
     *            casus filter
     * @param testCasusFactory
     *            test casus factory
     */
    public TestSuite(
            final File inputFolder,
            final FilenameFilter themaFilter,
            final FilenameFilter casusFilter,
            final TestCasusFactory testCasusFactory) {
        tijdstip = new Date().toString();
        this.inputFolder = inputFolder;
        this.outputFolder = new File(inputFolder.getParent(), inputFolder.getName() + OUTPUT_FOLDER_SUFFIX);
        this.expectedFolder = new File(inputFolder.getParent(), inputFolder.getName() + EXPECTED_FOLDER_SUFFIX);
        this.testCasusFactory = testCasusFactory;
        this.themaFilter = themaFilter == null ? ACCEPT_ALL_FILTER : themaFilter;
        this.casusFilter = casusFilter == null ? ACCEPT_ALL_FILTER : casusFilter;
    }

    /**
     * Run the tests.
     */
    public void run() {
        LOG.info("run(input={})", inputFolder.getPath());

        final TestRapportage rapportage = new TestRapportage(tijdstip);

        try {
            // Delete output directory
            if (outputFolder.exists()) {
                try {
                    LOG.info("Output directory verwijderen ...");
                    FileUtils.deleteDirectory(outputFolder);
                } catch (final IOException e) {
                    LOG.info("Kon output directory niet volledig verwijderen.");
                }
            }

            outputFolder.mkdirs();

            // Run tests
            final List<String> themas = asList(inputFolder.list(DirectoryFileFilter.INSTANCE));
            Collections.sort(themas);

            for (final String thema : themas) {
                if (themaFilter.accept(inputFolder, thema)) {
                    final File themaInputFolder = new File(inputFolder, thema);
                    final File themaOutputFolder = new File(outputFolder, thema);
                    themaOutputFolder.mkdir();
                    final File themaExpectedFolder = new File(expectedFolder, thema);

                    testCasusFactory.setThema(thema);
                    testCasusFactory.setOutputFolder(themaOutputFolder);
                    testCasusFactory.setExpectedFolder(themaExpectedFolder);

                    final TestThema testThema =
                            new TestThema(thema, themaInputFolder, themaOutputFolder, themaExpectedFolder,
                                    casusFilter, testCasusFactory);

                    rapportage.addResultaat(testThema.run());
                } else {
                    LOG.info("Thema '{}' overslaan ...", thema);
                }
            }

            TestOutput.outputXmlEnHtml(rapportage, new File(outputFolder, "rapport.xml"), new File(outputFolder,
                    "rapport.html"));

            // CHECKSTYLE:OFF
        } catch (final Exception e) {
            // CHECKSTYLE:ON
            LOG.error("Fout tijdens uitvoeren test suite.", e);
        }
    }

    private <T> List<T> asList(final T[] list) {
        if (list == null) {
            return Collections.emptyList();
        } else {
            return Arrays.asList(list);
        }
    }
}
