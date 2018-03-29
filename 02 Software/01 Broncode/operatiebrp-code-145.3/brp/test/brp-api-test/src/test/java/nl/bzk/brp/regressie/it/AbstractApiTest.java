/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.regressie.it;


import com.google.common.util.concurrent.MoreExecutors;
import java.util.Properties;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.tooling.apitest.jbehave.AntwoordberichtSteps;
import nl.bzk.brp.tooling.apitest.jbehave.ArchiveringSteps;
import nl.bzk.brp.tooling.apitest.jbehave.BasisSteps;
import nl.bzk.brp.tooling.apitest.jbehave.BeheerSelectieSteps;
import nl.bzk.brp.tooling.apitest.jbehave.LeverberichtSteps;
import nl.bzk.brp.tooling.apitest.jbehave.MutatieleveringSteps;
import nl.bzk.brp.tooling.apitest.jbehave.ProtocolleringSteps;
import nl.bzk.brp.tooling.apitest.jbehave.SelectieSteps;
import nl.bzk.brp.tooling.apitest.jbehave.StoryController;
import nl.bzk.brp.tooling.apitest.jbehave.VerzoekSteps;
import nl.bzk.brp.tooling.apitest.jbehave.VolledigberichtSteps;
import nl.bzk.brp.tooling.apitest.jbehave.VrijBerichtSteps;
import nl.bzk.brp.tooling.apitest.service.basis.StoryService;
import org.apache.commons.lang.StringUtils;
import org.jbehave.core.annotations.Configure;
import org.jbehave.core.annotations.UsingEmbedder;
import org.jbehave.core.annotations.UsingSteps;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.failures.FailingUponPendingStep;
import org.jbehave.core.i18n.LocalizedKeywords;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.junit.AnnotatedPathRunner;
import org.jbehave.core.model.ExamplesTableFactory;
import org.jbehave.core.model.Story;
import org.jbehave.core.model.TableTransformers;
import org.jbehave.core.parsers.RegexStoryParser;
import org.jbehave.core.reporters.FreemarkerViewGenerator;
import org.jbehave.core.reporters.NullStoryReporter;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.ParameterConverters;
import org.junit.runner.RunWith;

@UsingSteps(instances = {
        BasisSteps.class,
        VerzoekSteps.class,
        AntwoordberichtSteps.class,
        LeverberichtSteps.class,
        MutatieleveringSteps.class,
        ArchiveringSteps.class,
        ProtocolleringSteps.class,
        VrijBerichtSteps.class,
        SelectieSteps.class,
        BeheerSelectieSteps.class,
        VolledigberichtSteps.class,
})
@UsingEmbedder(
        embedder = AbstractApiTest.BrpEmbedder.class,
        verboseFailures = true,
        storyTimeoutInSecs = 600,
        metaFilters = "+status Klaar"
)
@Configure(
        storyLoader = AbstractApiTest.BrpStoryLoader.class,
        storyReporterBuilder = AbstractApiTest.BrpReportBuilder.class,
        storyParser = AbstractApiTest.MyStoryParser.class,
        pendingStepStrategy = FailingUponPendingStep.class,
        viewGenerator = FreemarkerViewGenerator.class
)
@RunWith(AnnotatedPathRunner.class)
public abstract class AbstractApiTest {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    static {
        System.setProperty("ster", "");
    }

    public static final class BrpStoryLoader extends LoadFromClasspath {
        public BrpStoryLoader() {
            super(AbstractApiTest.class.getClassLoader());
        }
    }

    public static final class BrpReportBuilder extends StoryReporterBuilder {

        public BrpReportBuilder() {
            withDefaultFormats();
            withFormats(
                    org.jbehave.core.reporters.Format.CONSOLE,
                    org.jbehave.core.reporters.Format.HTML,
                    org.jbehave.core.reporters.Format.XML
            );

            final Properties viewResources = new Properties();
            viewResources.put("decorateNonHtml", "false");
            viewResources.put("reports", "ftl/jbehave-reports.ftl");
            withViewResources(viewResources);

            withFailureTrace(true);
            withFailureTraceCompression(false);
            withMultiThreading(false);

            withReporters(new BrpStoryReporter());
        }
    }

    private static final class BrpStoryReporter extends NullStoryReporter {
        @Override
        public void beforeStory(final Story story, final boolean givenStory) {
            StoryController.getOmgeving().getStoryService().setStory(story.getPath());
        }

        @Override
        public void beforeStep(final String step) {
            StoryController.getOmgeving().getStoryService().setStep(step);
        }

        @Override
        public void beforeScenario(final String title) {
            final StoryService storyService = StoryController.getOmgeving().getStoryService();
            storyService.setScenario(title);
            LOGGER.info("\n\n");
            LOGGER.info("################ START SCENARIO ###############");
            LOGGER.info("Scenario: {}", StringUtils.chomp(title));
            LOGGER.info("Story : {}", StringUtils.chomp(storyService.getStory()));
        }

        @Override
        public void failed(final String step, final Throwable cause) {
            LOGGER.error("Step '{}' gefaald", step, cause);
        }
    }

    public static final class BrpEmbedder extends Embedder {

        public BrpEmbedder() {
            useEmbedderFailureStrategy(new Embedder.ThrowingRunningStoriesFailed());
            //draai de story op de calling thread anders is de ThreadLocal niet gezet
            useExecutorService(MoreExecutors.newDirectExecutorService());
        }
    }

    public static final class MyStoryParser extends RegexStoryParser {
        public MyStoryParser() {
            super(new ExamplesTableFactory(new LocalizedKeywords(),
                    new LoadFromClasspath(AbstractApiTest.class), new ParameterConverters(), new TableTransformers()));
        }
    }
}