package nl.bzk.brp.funqmachine.storytests

import static org.jbehave.core.reporters.Format.HTML
import static org.jbehave.core.reporters.Format.XML

import com.thoughtworks.paranamer.BytecodeReadingParanamer
import com.thoughtworks.paranamer.CachingParanamer
import org.jbehave.core.configuration.Configuration
import org.jbehave.core.configuration.Keywords
import org.jbehave.core.configuration.MostUsefulConfiguration
import org.jbehave.core.failures.FailingUponPendingStep
import org.jbehave.core.i18n.LocalizedKeywords
import org.jbehave.core.io.CodeLocations
import org.jbehave.core.io.LoadFromClasspath
import org.jbehave.core.io.StoryLoader
import org.jbehave.core.junit.JUnitStory
import org.jbehave.core.model.ExamplesTableFactory
import org.jbehave.core.parsers.RegexStoryParser
import org.jbehave.core.reporters.CrossReference
import org.jbehave.core.reporters.FilePrintStreamFactory
import org.jbehave.core.reporters.StoryReporterBuilder
import org.jbehave.core.steps.InjectableStepsFactory
import org.jbehave.core.steps.InstanceStepsFactory
import org.jbehave.core.steps.MarkUnmatchedStepsAsPending
import org.jbehave.core.steps.ParameterControls
import org.jbehave.core.steps.ParameterConverters

public abstract class AbstractStoryTest extends JUnitStory {

    private final CrossReference xref = new CrossReference();

    public AbstractStoryTest() {
        configuredEmbedder().embedderControls().doGenerateViewAfterStories(true).doIgnoreFailureInStories(false)
            .doIgnoreFailureInView(true).doVerboseFailures(true).useThreads(2).useStoryTimeoutInSecs(60);
    }

    @Override
    public Configuration configuration() {
        final Keywords keywords = new LocalizedKeywords(Locale.ENGLISH);

        return new MostUsefulConfiguration()
            .useKeywords(keywords)
            .useStoryLoader(storyLoader())
            .useStoryParser(new RegexStoryParser(keywords))
            .useStoryReporterBuilder(storyReporterBuilder(keywords))
            .useStepMonitor(xref.getStepMonitor())
            .useStepCollector(new MarkUnmatchedStepsAsPending(keywords))
            .usePendingStepStrategy(new FailingUponPendingStep())
            .useParanamer(new CachingParanamer(new BytecodeReadingParanamer()))
            .useParameterControls(parameterControls())
            .useParameterConverters(parameterConverters(keywords));
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(), maakStapAanInclusiefMocks());
    }

    abstract Object[] maakStapAanInclusiefMocks();


    private ParameterControls parameterControls() {
        return new ParameterControls()
            .useDelimiterNamedParameters(true);
    }

    private ParameterConverters parameterConverters(final Keywords keywords) {
        return new ParameterConverters()
            .addConverters(new ParameterConverters.ExamplesTableConverter(new ExamplesTableFactory(keywords)));
    }


    private StoryLoader storyLoader() {
        return new LoadFromClasspath();
    }

    private StoryReporterBuilder storyReporterBuilder(final Keywords keywords) {
        return new StoryReporterBuilder()
            .withCodeLocation(CodeLocations.codeLocationFromClass(this.getClass()))
            .withPathResolver(new FilePrintStreamFactory.ResolveToPackagedName())
            .withFailureTrace(true)
            .withFailureTraceCompression(true)
            .withDefaultFormats()
            .withKeywords(keywords)
            .withCrossReference(xref)
            .withFormats(HTML, XML);
    }


}
