/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.dal.runner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import junit.framework.AssertionFailedError;
import nl.bzk.migratiebrp.test.common.output.TestOutput;
import nl.bzk.migratiebrp.test.common.resultaat.Foutmelding;
import nl.bzk.migratiebrp.test.common.resultaat.TestRapportage;
import nl.bzk.migratiebrp.test.common.resultaat.TestResultaat;
import nl.bzk.migratiebrp.test.dal.TestCasus;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

import org.apache.commons.io.FileUtils;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;

/**
 * Base jUnit integration class.
 */
public final class TestRunner extends Runner {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static Test description;
    private static TestBuilder builder;

    /**
     * Constructor (for usage in jUnit).
     *
     * @param testConfigurationClass
     *            test class (configuration)
     * @throws InitializationError
     *             if the test could not be initialized
     */
    public TestRunner(final Class<? extends TestConfiguratie> testConfigurationClass) throws InitializationError {
        TestRunner.newInstanceIfNecessary(testConfigurationClass);
    }

    private static void newInstanceIfNecessary(final Class<? extends TestConfiguratie> testConfiguratieClass) throws InitializationError {
        if (description == null && builder == null) {
            final TestConfiguratie configuratie;
            try {
                configuratie = testConfiguratieClass.newInstance();
            } catch (final
                InstantiationException
                | IllegalAccessException e)
            {
                throw new InitializationError(e);
            }
            builder =
                    new TestBuilder(
                        testConfiguratieClass,
                        configuratie.getInputFolder(),
                        configuratie.getThemaFilter(),
                        configuratie.getCasusFilter(),
                        configuratie.getCasusFactory());
            description = builder.build();
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.junit.runner.Runner#getDescription()
     */
    @Override
    public Description getDescription() {
        return description.getDescription();
    }

    @Override
    public void run(final RunNotifier notifier) {
        final TestRapportage rapportage = new TestRapportage(new Date().toString());

        // Delete output directory
        final File outputFolder = builder.getOutputFolder();
        if (outputFolder.exists()) {
            try {
                LOG.info("Output directory verwijderen ...");
                FileUtils.deleteDirectory(outputFolder);
            } catch (final IOException e) {
                LOG.info("Kon output directory niet volledig verwijderen.");
            }
        }

        LOG.info("Start uitvoeren testen");

        rapportage.addResultaat(run(description, notifier));

        LOG.info("Einde uitvoeren testen");

        TestOutput.outputXmlEnHtml(rapportage, new File(outputFolder, "rapport.xml"), new File(builder.getOutputFolder(), "rapport.html"));
    }

    private List<TestResultaat> run(final Test testDescription, final RunNotifier notifier) {
        if (testDescription.isTest()) {
            return runTest(testDescription, notifier);
        } else {
            final List<TestResultaat> result = new ArrayList<>();
            for (final Test child : testDescription.getChildren()) {
                result.addAll(run(child, notifier));
            }
            return result;
        }
    }

    private List<TestResultaat> runTest(final Test test, final RunNotifier notifier) {
        LOG.info("Starting test: " + test.getDescription().getDisplayName());
        notifier.fireTestStarted(test.getDescription());
        try {
            final TestResultaat result = test.getTestCasus().run();
            if (!result.isSucces()) {
                LOG.info("Test assumption failed");
                notifier.fireTestFailure(new Failure(test.getDescription(), new AssertionFailedError("Test resultaat niet OK.")));
            }
            return Collections.singletonList(result);
        } catch (final Exception e) {
            LOG.info("Test exception", e);
            notifier.fireTestFailure(new Failure(test.getDescription(), e));
            final TestResultaat resultaat = new FailureResultaat(test.getTestCasus(), e);
            return Collections.singletonList(resultaat);
        } finally {
            notifier.fireTestFinished(test.getDescription());
            LOG.info("Test finished");
        }
    }

    /**
     * Klasse voor gefaalde testresultaten.
     */
    public static final class FailureResultaat extends TestResultaat {

        /**
         * Default constructor.
         * 
         * @param testCasus
         *            De testcasus.
         * @param cause
         *            De oorzaak van het falen van de test.
         */
        protected FailureResultaat(final TestCasus testCasus, final Exception cause) {
            super(testCasus.getThema(), testCasus.getNaam());
            setFoutmelding(new Foutmelding("", cause));
        }

        @Override
        public boolean isSucces() {
            return false;
        }

    }
}
