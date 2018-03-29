/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.jbehave;


import java.util.Arrays;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.dockertest.component.BrpOmgeving;
import nl.bzk.brp.dockertest.component.OmgevingBuilder;
import org.jbehave.core.junit.AnnotatedPathRunner;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;

public final class BrpEndToEndRunner extends AnnotatedPathRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final Thread.UncaughtExceptionHandler UNCAUGHT_EXCEPTION_HANDLER = (t, e) -> LOGGER.error("Onverwachte fout opgetreden", e);

    static {
        Thread.setDefaultUncaughtExceptionHandler(UNCAUGHT_EXCEPTION_HANDLER);
    }

    public BrpEndToEndRunner(final Class<?> testClass) throws InitializationError {
        super(testClass);
        LOGGER.info("testClass = {}", testClass);
    }

    @Override
    public final void run(final RunNotifier notifier) {
        final Dockertest dockerTest = getTestClass().getAnnotation(Dockertest.class);
        Thread.currentThread().setName(dockerTest.naam());
        LOGGER.info("Start");
        OmgevingBuilder omgevingBuilder = new OmgevingBuilder();
        omgevingBuilder.metNaam(dockerTest.naam());
        Arrays.asList(dockerTest.componenten()).forEach(omgevingBuilder::addDocker);
        final BrpOmgeving omgeving = omgevingBuilder.build();

        try {
            LOGGER.info("Start omgeving");
            omgeving.start();
            omgeving.autorisaties().laadAutorisatiesEnCustomSql(Arrays.asList(dockerTest.autorisaties()));
            JBehaveState.set(omgeving);
            super.run(notifier);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            notifier.fireTestFailure(new Failure(getDescription(), e));
        } catch (Throwable e) {
            LOGGER.error("Test gefaald", e);
            notifier.fireTestFailure(new Failure(getDescription(), e));
        } finally {
            LOGGER.info("Stop omgeving");
            omgeving.stop();
        }
    }

}
