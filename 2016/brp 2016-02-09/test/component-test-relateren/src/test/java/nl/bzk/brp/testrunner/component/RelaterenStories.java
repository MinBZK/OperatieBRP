/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testrunner.component;

import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;
import static org.jbehave.core.reporters.Format.CONSOLE;
import static org.jbehave.core.reporters.Format.HTML_TEMPLATE;
import static org.jbehave.core.reporters.Format.TXT;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;
import nl.bzk.brp.testrunner.jbehave.ComponentTestStappen;
import org.jbehave.core.Embeddable;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.context.Context;
import org.jbehave.core.embedder.PropertyBasedEmbedderControls;
import org.jbehave.core.i18n.LocalizedKeywords;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.model.ExamplesTableFactory;
import org.jbehave.core.model.TableTransformers;
import org.jbehave.core.parsers.RegexPrefixCapturingPatternParser;
import org.jbehave.core.parsers.RegexStoryParser;
import org.jbehave.core.reporters.ContextOutput;
import org.jbehave.core.reporters.CrossReference;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jbehave.core.steps.ParameterConverters;
import org.jbehave.core.steps.SilentStepMonitor;
import org.jbehave.core.steps.StepMonitor;

/**
 * Alle relateren stories die gestart zullen worden via JUnit en Maven.
 */
public final class RelaterenStories extends JUnitStories {

    private final CrossReference xref          = new CrossReference();
    private       Context        context       = new Context();
    private       Format         contextFormat = new ContextOutput(context);
    private       StepMonitor    stepMonitor   = new SilentStepMonitor();

    /**
     * Default constructor waarbij de {@link org.jbehave.core.embedder.Embedder} van JBehave wordt geconfigureerd.
     */
    public RelaterenStories() {
        System.out.println("Constructor aangeroepen");
        configuredEmbedder().embedderControls().doGenerateViewAfterStories(true).doIgnoreFailureInStories(false)
            .doIgnoreFailureInView(true).doVerboseFailures(true).useThreads(1);//.useStoryTimeouts("60");
        configuredEmbedder().useEmbedderControls(new PropertyBasedEmbedderControls());
    }

    @Override
    public Configuration configuration() {
        System.out.println("Configuration aangeroepen");
        // avoid re-instantiating configuration for the steps factory
        // alternative use #useConfiguration() in the constructor
        if (super.hasConfiguration()) {
            return super.configuration();
        }
        final Class<? extends Embeddable> embeddableClass = this.getClass();
        final Properties viewResources = new Properties();
        viewResources.put("decorateNonHtml", "true");
        viewResources.put("reports", "ftl/jbehave-reports.ftl");
        // Start from default ParameterConverters instance
        final ParameterConverters parameterConverters = new ParameterConverters();
        // factory to allow parameter conversion and loading from external
        // resources (used by StoryParser too)
        final ExamplesTableFactory examplesTableFactory = new ExamplesTableFactory(new LocalizedKeywords(),
            new LoadFromClasspath(embeddableClass), parameterConverters, new TableTransformers());
        // add custom converters
        parameterConverters.addConverters(new ParameterConverters.DateConverter(new SimpleDateFormat("yyyy-MM-dd")),
            new ParameterConverters.ExamplesTableConverter(examplesTableFactory));

        return new MostUsefulConfiguration()
            .useStoryLoader(new LoadFromClasspath(embeddableClass))
            .useStoryParser(new RegexStoryParser(examplesTableFactory))
            .useStoryReporterBuilder(
                new StoryReporterBuilder()
                    .withCodeLocation(CodeLocations.codeLocationFromClass(embeddableClass))
                    .withDefaultFormats().withViewResources(viewResources)
                    .withFormats(contextFormat, CONSOLE, TXT, HTML_TEMPLATE).withFailureTrace(true)
                    .withFailureTraceCompression(true).withCrossReference(xref))
            .useParameterConverters(parameterConverters)
            // use '%' instead of '$' to identify parameters
            .useStepPatternParser(new RegexPrefixCapturingPatternParser("%"))
            .useStepMonitor(stepMonitor);
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        System.out.println("Steps factory");
        return new InstanceStepsFactory(configuration(), new ComponentTestStappen());
    }

    @Override
    protected List<String> storyPaths() {
        System.out.println("storyPaths");
        final String filter = System.getProperty("story.filter", "**/*.story");
        return new StoryFinder().findPaths(codeLocationFromClass(this.getClass()), filter, "**/failing_before*.story,**/given_relative_path*");
    }
}
