/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.jbehave;

import com.google.common.util.concurrent.MoreExecutors;
import java.util.Properties;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.apache.commons.lang.StringUtils;
import org.jbehave.core.annotations.Configure;
import org.jbehave.core.annotations.UsingEmbedder;
import org.jbehave.core.annotations.UsingSteps;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.failures.FailingUponPendingStep;
import org.jbehave.core.i18n.LocalizedKeywords;
import org.jbehave.core.io.LoadFromClasspath;
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
        OmgevingSteps.class,
        DatabaseSteps.class,
        ArchiveringControleSteps.class,
        ProtocolleringControleSteps.class,
        AutorisatieSteps.class,
        BerichtControleSteps.class,
        SOAPRequestSteps.class,
        PersoonSteps.class,
        SelectieSteps.class,
        BeheerSelectieSteps.class,
        AfnemerindicatieControleSteps.class,
        Lo3Steps.class
})
@UsingEmbedder(
        embedder = EndToEndTestSetup.BrpEmbedder.class,
        verboseFailures = true,
        storyTimeoutInSecs = 600,
        metaFilters = "+status Klaar"
)
@Configure(
        storyLoader = EndToEndTestSetup.BrpStoryLoader.class,
        storyReporterBuilder = EndToEndTestSetup.BrpReportBuilder.class,
        storyParser = EndToEndTestSetup.MyStoryParser.class,
        pendingStepStrategy = FailingUponPendingStep.class,
        viewGenerator = FreemarkerViewGenerator.class
)
@RunWith(BrpEndToEndRunner.class)
public class EndToEndTestSetup {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    public static class BrpStoryLoader extends LoadFromClasspath {
        public BrpStoryLoader() {
            super(BrpEndToEndRunner.class.getClassLoader());
        }
    }

    public static class BrpReportBuilder extends StoryReporterBuilder {

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
            JBehaveState.getScenarioState().setCurrentStory(story);
        }

        @Override
        public void beforeStep(final String step) {
            super.beforeStep(step);
        }

        @Override
        public void beforeScenario(final String title) {
            JBehaveState.getScenarioState().setCurrectScenario(title);
            LOGGER.info("\n\n");
            LOGGER.info("################ START SCENARIO ###############");
            LOGGER.info("Scenario: {}", StringUtils.chomp(title));
            LOGGER.info("Story : {}", StringUtils.chomp(JBehaveState.getScenarioState().getCurrentStory().getName()));
        }

        @Override
        public void failed(final String step, final Throwable cause) {
            LOGGER.error("Step '{}' gefaald", step, cause);
        }
    }

    public static class BrpEmbedder extends Embedder {

        public BrpEmbedder() {
            useEmbedderFailureStrategy(new Embedder.ThrowingRunningStoriesFailed());
            //draai de story op de calling thread anders is de ThreadLocal niet gezet
            useExecutorService(MoreExecutors.newDirectExecutorService());
        }
    }

    public static class MyStoryParser extends RegexStoryParser {
        public MyStoryParser() {
            super(new ExamplesTableFactory(new LocalizedKeywords(),
                    new LoadFromClasspath(BrpEndToEndRunner.class), new ParameterConverters(), new TableTransformers()));
        }
    }
}
