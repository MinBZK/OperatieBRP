/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.dal.runner;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.test.common.util.BaseFilter;
import nl.bzk.migratiebrp.test.common.util.FilterType;
import nl.bzk.migratiebrp.test.dal.TestCasus;
import nl.bzk.migratiebrp.test.dal.TestCasusFactory;

import org.junit.runners.model.InitializationError;

/**
 * Builder to constructor the compleet test from the files on the filesystem.
 */
public final class TestBuilder {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final String OUTPUT_FOLDER_SUFFIX = "-output";
    private static final String EXPECTED_FOLDER_SUFFIX = "-expected";
    private static final FilenameFilter ACCEPT_ALL_DIRECTORIES_FILTER = new BaseFilter(FilterType.DIRECTORY);
    private static final FilenameFilter ACCEPT_ALL_FILTER = new BaseFilter(FilterType.ANY);

    private final Class<?> testClass;
    private final File inputFolder;
    private final File outputFolder;
    private final File expectedFolder;
    private final FilenameFilter themaFilter;
    private final FilenameFilter casusFilter;

    private final TestCasusFactory testCasusFactory;

    /**
     * Constructor.
     * @param testClass test class
     * @param inputFolder input folder
     * @param themaFilter thema filter
     * @param casusFilter casus filter
     * @param testCasusFactory casus factory
     */
    TestBuilder(
            final Class<?> testClass,
            final File inputFolder,
            final FilenameFilter themaFilter,
            final FilenameFilter casusFilter,
            final TestCasusFactory testCasusFactory) {
        this.testClass = testClass;
        this.inputFolder = inputFolder;
        outputFolder = new File(inputFolder.getParent(), inputFolder.getName() + OUTPUT_FOLDER_SUFFIX);
        expectedFolder = new File(inputFolder.getParent(), inputFolder.getName() + EXPECTED_FOLDER_SUFFIX);
        this.testCasusFactory = testCasusFactory;
        this.themaFilter = themaFilter == null ? ACCEPT_ALL_DIRECTORIES_FILTER : themaFilter;
        this.casusFilter = casusFilter == null ? ACCEPT_ALL_FILTER : casusFilter;
    }

    /**
     * Build the test.
     * @return the test
     * @throws InitializationError when a problem arrises in the construction of the test
     */
    public Test build() throws InitializationError {
        final Test suiteTest = new Test(testClass == null ? "Test" : testClass.getName());

        // Input directory contains thema directories
        LOG.info("Test suite opbouwen: " + inputFolder.getAbsolutePath());
        final List<String> themas = asList(inputFolder.list(themaFilter));
        Collections.sort(themas);

        for (final String thema : themas) {
            LOG.info("Verwerk thema: " + thema);
            // final Test themaTest = new Test(thema);
            // suiteTest.addChild(themaTest);

            final File themaInputFolder = new File(inputFolder, thema);
            final File themaOutputFolder = new File(outputFolder, thema);
            final File themaExpectedFolder = new File(expectedFolder, thema);

            testCasusFactory.setThema(thema);
            testCasusFactory.setThemaFolder(themaInputFolder);
            testCasusFactory.setOutputFolder(themaOutputFolder);
            testCasusFactory.setExpectedFolder(themaExpectedFolder);

            // Thema directory contains tests (can be files or directories)
            final List<String> tests = asList(themaInputFolder.list(casusFilter));
            Collections.sort(tests);

            for (final String test : tests) {
                LOG.info("Verwerk test: " + test);

                final File inputFile = new File(themaInputFolder, test);
                try {
                    final List<TestCasus> casussen = testCasusFactory.leesTestCasussen(inputFile);
                    for (final TestCasus casus : casussen) {
                        LOG.info("Test: " + casus.getNaam());
                        final Test casusTest = new Test(casus.getNaam(), casus);
                        // themaTest.addChild(casusTest);
                        suiteTest.addChild(casusTest);
                    }
                } catch (final Exception e /* Catch exception to make builder robust */) {
                    LOG.info("Test build exception", e);
                    // Gefakede test toevoegen zodat het opbouwen van de suite niet faalt, maar alleen deze input
                    final TestCasus casus = new TestBuildFailure(thema, inputFile.getName(), themaOutputFolder, themaExpectedFolder, e);
                    final Test casusTest = new Test(casus.getNaam(), casus);
                    // themaTest.addChild(casusTest);
                    suiteTest.addChild(casusTest);
                }

            }
        }

        return suiteTest;
    }

    /**
     * Geef de waarde van output folder.
     * @return output folder
     */
    public File getOutputFolder() {
        return outputFolder;
    }

    private <T> List<T> asList(final T[] list) {
        if (list == null) {
            return Collections.emptyList();
        } else {
            return Arrays.asList(list);
        }
    }
}
