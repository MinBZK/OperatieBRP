/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.jbehave.stories;

import com.thoughtworks.paranamer.BytecodeReadingParanamer;
import com.thoughtworks.paranamer.CachingParanamer;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.funqmachine.jbehave.context.BevraagbaarContextView;
import nl.bzk.brp.funqmachine.jbehave.reporters.JunitXmlStoryReporter;
import nl.bzk.brp.funqmachine.jbehave.steps.BrpContextStepMonitor;
import nl.bzk.brp.funqmachine.jbehave.validatie.StoryFileValidator;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.Keywords;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.context.Context;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.embedder.EmbedderControls;
import org.jbehave.core.embedder.EmbedderMonitor;
import org.jbehave.core.embedder.MetaFilter;
import org.jbehave.core.embedder.PrintStreamEmbedderMonitor;
import org.jbehave.core.failures.FailingUponPendingStep;
import org.jbehave.core.i18n.LocalizedKeywords;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.io.StoryLoader;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.model.ExamplesTableFactory;
import org.jbehave.core.model.Meta;
import org.jbehave.core.model.TableTransformers;
import org.jbehave.core.parsers.RegexStoryParser;
import org.jbehave.core.reporters.ContextOutput;
import org.jbehave.core.reporters.CrossReference;
import org.jbehave.core.reporters.FilePrintStreamFactory;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.MarkUnmatchedStepsAsPending;
import org.jbehave.core.steps.ParameterControls;
import org.jbehave.core.steps.ParameterConverters;
import org.jbehave.core.steps.spring.SpringStepsFactory;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Basis klasse voor het uitvoeren van JBehave gebaseerde testen.
 * @see <a href="https://github.com/mkuthan/example-jbehave">example-jbehave</a>
 * @see <a href="https://mkuthan.github.io/blog/2014/05/29/acceptance-testing-using-jbehave-spring-framework-and-maven/">Acceptance Testing Using JBehave,
 * Spring Framework and Maven</a>
 */
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class AbstractSpringJBehaveStories extends JUnitStories implements ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final String RANDOM_VALUE = "RANDOM_VALUE";
    private static final String MAAK_BLOBS = "MAAK_BLOBS";
    private static final String KEEP_PREVIOUS_RESULTS = "KEEP_PREVIOUS_RESULTS";
    private static final Logger LOG = LoggerFactory.getLogger();

    private static final int STORY_TIMEOUT_SECONDS = 1800;
    private static final int THREAD_COUNT = 1;

    private final Context context = new Context();
    private final CrossReference xref = new CrossReference().withJsonOnly().withOutputAfterEachStory(true);
    private ApplicationContext applicationContext;
    private BevraagbaarContextView contextView;

    /**
     * Constructor om JBehave Stories te kunnen draaien.
     */
    public AbstractSpringJBehaveStories() {
        final Embedder embedder = new Embedder();
        embedder.useEmbedderControls(embedderControls());
        embedder.useMetaFilters(metaFilters());
        embedder.useEmbedderMonitor(embedderMonitor());
        useEmbedder(embedder);
    }

    @Override
    public void run() throws Throwable {
        try {
            super.run();
        } catch (Throwable t) {
            LOGGER.error("Fout bij het uitvoeren van de testen", t);
            throw t;
        }
    }

    /**
     * Geef de metafilters voor de test.
     * @return lijst van filters, of lege lijst
     */
    protected List<String> metaFilters() {
        return Collections.singletonList("-skip");
    }

    /**
     * Geef de locale waarin de JBehave stories zijn geschreven.
     * @return locale, default is {@link java.util.Locale#ENGLISH}
     */
    private Locale locale() {
        return Locale.ENGLISH;
    }

    @Override
    public final List<String> storyPaths() {
        final List<String> stories = alleStoryPaths();
        final List<String> valideStories = stories.stream().filter(this::isFileStoryFile).filter(this::isStoryValide).collect(Collectors.toList());
        LOG.info(" aantal story files gevonden " + valideStories.size());
        if (valideStories.isEmpty()) {
            throw new IllegalStateException("Er kunnen geen story files gevonden worden (projectnaam jenkins mag geen spaties bevatten!)");
        }

        return valideStories;

    }

    private boolean isFileStoryFile(final String storyLocation) {
        return storyLocation.endsWith(".story");
    }

    private boolean isStoryValide(final String storyLocation) {
        return new StoryFileValidator().valideer(getClass().getResource("/" + storyLocation));
    }

    /**
     * Geeft alle paden naar uit te voeren stories. Deze stories zullen hierna geverifieerd worden.
     * @return lijst van stories
     */
    protected List<String> alleStoryPaths() {
        final String codeLocation = CodeLocations.codeLocationFromClass(this.getClass()).getFile();
        return new StoryFinder().findClassNames(codeLocation, Collections.singletonList("**/*.story"), Collections.singletonList(""));
    }

    @Override
    public final Configuration configuration() {
        final Keywords keywords = new LocalizedKeywords(locale());

        final TableTransformers tableTransformers = new TableTransformers();
        final ExamplesTableFactory tableFactory = new ExamplesTableFactory(keywords, tableTransformers);
        return new MostUsefulConfiguration().useKeywords(keywords)
                .useStoryLoader(storyLoader())
                .useStoryParser(new RegexStoryParser(keywords, tableFactory))
                .useStoryReporterBuilder(storyReporterBuilder(keywords))
                .useStepMonitor(new BrpContextStepMonitor(context, contextView, xref.getStepMonitor()))
                .useStepCollector(new MarkUnmatchedStepsAsPending(keywords))
                .usePendingStepStrategy(new FailingUponPendingStep())
                .useParanamer(new CachingParanamer(new BytecodeReadingParanamer()))
                .useParameterControls(parameterControls())
                .useParameterConverters(parameterConverters(tableTransformers, tableFactory));
    }

    @Override
    public final InjectableStepsFactory stepsFactory() {
        return new SpringStepsFactory(configuration(), applicationContext);
    }

    private EmbedderControls embedderControls() {
        return new EmbedderControls().doIgnoreFailureInView(false)
                .doIgnoreFailureInStories(false)
                .useStoryTimeoutInSecs(STORY_TIMEOUT_SECONDS)
                .useThreads(THREAD_COUNT)
                .doBatch(true)
                .doFailOnStoryTimeout(false)
                .doIgnoreFailureInView(false)
                .doVerboseFailures(true);
    }

    private ParameterControls parameterControls() {
        return new ParameterControls().useDelimiterNamedParameters(true);
    }

    private ParameterConverters parameterConverters(final TableTransformers tableTransformer, final ExamplesTableFactory tableFactory) {
        return new ParameterConverters(tableTransformer).addConverters(new ParameterConverters.ExamplesTableConverter(tableFactory));
    }

    private StoryLoader storyLoader() {
        return new LoadFromClasspath();
    }

    private StoryReporterBuilder storyReporterBuilder(final Keywords keywords) {
        return new StoryReporterBuilder().withCodeLocation(CodeLocations.codeLocationFromClass(this.getClass()))
                .withPathResolver(new FilePrintStreamFactory.ResolveToPackagedName())
                .withFailureTrace(true)
                .withFailureTraceCompression(true)
                .withKeywords(keywords)
                .withCrossReference(xref)
                .withReporters(new JunitXmlStoryReporter())
                .withDefaultFormats()
                .withFormats(Format.HTML, Format.XML, new ContextOutput(context));
    }

    private EmbedderMonitor embedderMonitor() {
        return new PrintStreamEmbedderMonitor() {
            @Override
            public void metaNotAllowed(final Meta meta, final MetaFilter filter) {
                // De PrintStreamEmbedderMonitor logt hier meta excluded by filter. Methode is overschreven om niet te
                // loggen.
            }
        };
    }

    /**
     * Maak BLOBS aan..
     */
    public static void maakBlobs() {
        System.setProperty(MAAK_BLOBS, RANDOM_VALUE);
    }

    /**
     * Geeft aan of het aanmaken van de blobs gedaan moet worden.
     * @return true als het maken van de blobs gedaan moet worden
     */
    public static boolean isBlobsMaken() {
        return RANDOM_VALUE.equals(System.getProperty(MAAK_BLOBS));
    }

    /**
     * Bewaar de vorige resultaten (m.a.w. gooi de target folder niet weg).
     */
    public static void bewaarVorigeOutput() {
        System.setProperty(KEEP_PREVIOUS_RESULTS, RANDOM_VALUE);
    }

    /**
     * Geeft aan of de vorige output bewaard moet blijven.
     * @return true als de vorige output bewaard moet blijven
     */
    public static boolean isBewaarVorigeOutput() {
        return RANDOM_VALUE.equals(System.getProperty(KEEP_PREVIOUS_RESULTS));
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        contextView = applicationContext.getBean(BevraagbaarContextView.class);
    }
}
