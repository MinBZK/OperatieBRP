package nl.bzk.brp.funqmachine.jbehave.stories

import static java.util.Arrays.asList
import static org.jbehave.core.reporters.Format.HTML
import static org.jbehave.core.reporters.Format.XML

import com.google.common.collect.Collections2
import com.google.common.collect.Lists
import com.thoughtworks.paranamer.BytecodeReadingParanamer
import com.thoughtworks.paranamer.CachingParanamer
import nl.bzk.brp.funqmachine.jbehave.context.BevraagbaarContextView
import nl.bzk.brp.funqmachine.jbehave.reporters.JunitXmlStoryReporter
import nl.bzk.brp.funqmachine.jbehave.steps.BrpContextStepMonitor
import nl.bzk.brp.funqmachine.jbehave.validatie.ValideStoryPredicate
import org.jbehave.core.configuration.Configuration
import org.jbehave.core.configuration.Keywords
import org.jbehave.core.configuration.MostUsefulConfiguration
import org.jbehave.core.context.Context
import org.jbehave.core.embedder.Embedder
import org.jbehave.core.embedder.EmbedderControls
import org.jbehave.core.embedder.EmbedderMonitor
import org.jbehave.core.embedder.MetaFilter
import org.jbehave.core.embedder.PrintStreamEmbedderMonitor
import org.jbehave.core.failures.FailingUponPendingStep
import org.jbehave.core.i18n.LocalizedKeywords
import org.jbehave.core.io.CodeLocations
import org.jbehave.core.io.LoadFromClasspath
import org.jbehave.core.io.StoryFinder
import org.jbehave.core.io.StoryLoader
import org.jbehave.core.junit.JUnitStories
import org.jbehave.core.model.ExamplesTableFactory
import org.jbehave.core.model.Meta
import org.jbehave.core.parsers.RegexStoryParser
import org.jbehave.core.reporters.ContextOutput
import org.jbehave.core.reporters.CrossReference
import org.jbehave.core.reporters.FilePrintStreamFactory.ResolveToPackagedName
import org.jbehave.core.reporters.StoryReporterBuilder
import org.jbehave.core.steps.ContextStepMonitor
import org.jbehave.core.steps.InjectableStepsFactory
import org.jbehave.core.steps.MarkUnmatchedStepsAsPending
import org.jbehave.core.steps.ParameterControls
import org.jbehave.core.steps.ParameterConverters
import org.jbehave.core.steps.spring.SpringStepsFactory
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

/**
 * Basis klasse voor het uitvoeren van JBehave gebaseerde testen.
 *
 * @see <a href="https://github.com/mkuthan/example-jbehave">example-jbehave</a>
 * @see <a href="https://mkuthan.github.io/blog/2014/05/29/acceptance-testing-using-jbehave-spring-framework-and-maven/">Acceptance Testing Using JBehave,
 * Spring Framework and Maven</a>
 */
@RunWith(SpringJUnit4ClassRunner)
public abstract class AbstractSpringJBehaveStories extends JUnitStories {
    private static final int STORY_TIMEOUT_SECONDS = 1800;
    private static final int THREAD_COUNT  = 1;

    private final Context        context = new Context();
    private final CrossReference xref    = new CrossReference()
        .withJsonOnly()
        .withOutputAfterEachStory(true);

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private BevraagbaarContextView contextView;

    public AbstractSpringJBehaveStories() {
        Embedder embedder = new Embedder();
        embedder.useEmbedderControls(embedderControls());
        embedder.useMetaFilters(metaFilters());
        embedder.useEmbedderMonitor(embedderMonitor())
        useEmbedder(embedder);
    }

    /**
     * Geef de metafilters voor de test.
     *
     * @return lijst van filters, of lege lijst
     */
    protected List<String> metaFilters() {
        return asList("-skip");
    }

    /**
     * Geef de locale waarin de JBehave stories zijn geschreven.
     *
     * @return locale, default is {@link java.util.Locale#ENGLISH}
     */
    protected Locale locale() {
        return Locale.ENGLISH;
    }

    @Override
    public final List<String> storyPaths() {
        List<String> stories = alleStoryPaths();
        def list = Lists.newArrayList(Collections2.filter(stories, new ValideStoryPredicate()))
        if (list.size() == 0) {
            throw new RuntimeException("Er kunnen geen story files gevonden worden")
        }
        return list;

    }

    /**
     * Geeft alle paden naar uit te voeren stories. Deze stories zullen hierna geverifieerd worden.
     *
     * @return lijst van stories
     */
    protected List<String> alleStoryPaths() {
        String codeLocation = CodeLocations.codeLocationFromClass(this.getClass()).getFile();
        return new StoryFinder().findClassNames(codeLocation, asList("**/*.story"), asList(""));
    }

    @Override
    final public Configuration configuration() {
        final Keywords keywords = new LocalizedKeywords(locale());

        final ContextStepMonitor stepMonitor = new BrpContextStepMonitor(context, contextView, xref.getStepMonitor());

        return new MostUsefulConfiguration()
            .useKeywords(keywords)
            .useStoryLoader(storyLoader())
            .useStoryParser(new RegexStoryParser(keywords))
            .useStoryReporterBuilder(storyReporterBuilder(keywords))
            .useStepMonitor(stepMonitor)
            .useStepCollector(new MarkUnmatchedStepsAsPending(keywords))
            .usePendingStepStrategy(new FailingUponPendingStep())
            .useParanamer(new CachingParanamer(new BytecodeReadingParanamer()))
            .useParameterControls(parameterControls())
            .useParameterConverters(parameterConverters(keywords));
    }

    @Override
    final public InjectableStepsFactory stepsFactory() {
        return new SpringStepsFactory(configuration(), applicationContext);
    }

    private EmbedderControls embedderControls() {
        return new EmbedderControls()
            .doIgnoreFailureInView(false)
            .doIgnoreFailureInStories(true)
            .useStoryTimeoutInSecs(STORY_TIMEOUT_SECONDS)
            .useThreads(THREAD_COUNT)
            .doBatch(true)
            .doFailOnStoryTimeout(true)
    }

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
            .withPathResolver(new ResolveToPackagedName())
            .withFailureTrace(true)
            .withFailureTraceCompression(true)
            .withKeywords(keywords)
            .withCrossReference(xref)
            .withReporters(new JunitXmlStoryReporter())
            .withDefaultFormats()
            .withFormats(HTML, XML, new ContextOutput(context));
    }

    private EmbedderMonitor embedderMonitor() {
        return new PrintStreamEmbedderMonitor(){
            public void metaNotAllowed(Meta meta, MetaFilter filter) {
                // De PrintStreamEmbedderMonitor logt hier meta excluded by filter. Methode is overschreven om niet te loggen.
            }
        }
    }
}
