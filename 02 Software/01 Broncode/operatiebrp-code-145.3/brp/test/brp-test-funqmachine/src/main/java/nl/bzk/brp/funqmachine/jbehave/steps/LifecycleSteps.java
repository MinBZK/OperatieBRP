/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.jbehave.steps;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.funqmachine.jbehave.context.BevraagbaarContextView;
import nl.bzk.brp.funqmachine.jbehave.context.ScenarioRunContext;
import nl.bzk.brp.funqmachine.jbehave.stories.AbstractSpringJBehaveStories;
import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.BeforeStories;

/**
 * JBehave steps voor de Lifecycle van de test-tooling.
 */
@Steps
public class LifecycleSteps {
    private static final Logger LOGGER = LoggerFactory.getLogger();

    private final ScenarioRunContext runContext;
    private final BevraagbaarContextView contextView;

    /**
     * Constructor.
     * @param runContext de scenario run context
     * @param contextView de context view
     */
    @Inject
    public LifecycleSteps(final ScenarioRunContext runContext, final BevraagbaarContextView contextView) {
        this.runContext = runContext;
        this.contextView = contextView;
    }

    /**
     * Gooit, voordat de nieuwe test-run wordt opgestart, de vorige resultaten weg.
     */
    @BeforeStories
    public void deletePreviousResults() throws IOException {
        if (AbstractSpringJBehaveStories.isBewaarVorigeOutput()) {
            return;
        }

        final Path targetPath = FileSystems.getDefault().getPath("./target");

        // Verwijderen van de testgevallen in de jbehave folder
        final Path jbehavePath = targetPath.resolve("jbehave");
        Files.walkFileTree(jbehavePath, new JBehaveFolderCleaner());

        // Verwijderen van de testgevallen in de funqmachine folder
        final Path funqmachinePath = targetPath.resolve("funqmachine");
        if (funqmachinePath.toFile().exists()) {
            Files.walkFileTree(funqmachinePath, new FunqMachineFolderRemoval());
        }
    }

    /**
     * Start de scenario run context (zie {@link ScenarioRunContext#start()}.
     */
    @BeforeScenario
    public final void startRunContext() {
        runContext.start();
    }

    /**
     * Schrijft de resultaten weg als het scenario klaar is.
     */
    @AfterScenario(uponOutcome = AfterScenario.Outcome.ANY)
    public final void schrijfResultatenWeg() {
        LOGGER.info(String.format("Beschikbare resultaten weggeschreven voor story=%s scenario=%s", contextView.getStory(), contextView.getScenario()));
        runContext.schrijfResultaat(contextView);
    }

    /**
     * Logt dat het scenario klaar is.
     */
    @AfterScenario(uponOutcome = AfterScenario.Outcome.ANY)
    public void logKlaar() {
        LOGGER.info(String.format("Klaar met story=%s scenario=%s", contextView.getStory(), contextView.getScenario()));
    }

    private static class FunqMachineFolderRemoval extends SimpleFileVisitor<Path> {
        @Override
        public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
            Files.delete(file);
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(final Path dir, final IOException exc) throws IOException {
            Files.delete(dir);
            return FileVisitResult.CONTINUE;
        }
    }

    private static class JBehaveFolderCleaner extends SimpleFileVisitor<Path> {
        private final PathMatcher matcher;

        JBehaveFolderCleaner() {
            matcher = FileSystems.getDefault().getPathMatcher("glob:testcases.*");
        }

        @Override
        public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
            final Path name = file.getFileName();
            if (name != null && matcher.matches(name)) {
                Files.delete(file);
            }
            return FileVisitResult.CONTINUE;
        }
    }
}
