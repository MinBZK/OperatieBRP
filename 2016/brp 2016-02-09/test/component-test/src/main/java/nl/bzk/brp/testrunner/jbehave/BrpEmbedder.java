/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testrunner.jbehave;

import java.text.SimpleDateFormat;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.LoadFromURL;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.parsers.RegexPrefixCapturingPatternParser;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jbehave.core.steps.ParameterConverters;

/**
 * Abstract class met de configuratie voor de embedder van JBehave.
 */
public abstract class BrpEmbedder extends JUnitStories {

    /**
     * Constructor voor BrpEmbedder.
     */
    public BrpEmbedder() {
        final int aantalThreads = 1;
        configuredEmbedder().embedderControls().useThreads(aantalThreads).doVerboseFiltering(false).doVerboseFailures(true).doIgnoreFailureInStories(true)
            .doIgnoreFailureInView(true);
    }

    /**
     * @return geeft de configuratie terug voor de JBehave story tests
     */
    @Override
    public final Configuration configuration() {
        final Configuration configuration = new MostUsefulConfiguration()
            .useStoryLoader(new LoadFromURL())
            .useStoryReporterBuilder(new BrpStoryReportBuilder()
                .withCodeLocation(CodeLocations.codeLocationFromClass(this.getClass()))
                .withDefaultFormats()
                    .withFormats(Format.CONSOLE, Format.TXT, Format.HTML)
                    .withFailureTrace(true).withFailureTraceCompression(true)
                /*.withCrossReference(new CrossReference())*/)
            .useParameterConverters(new ParameterConverters()
                .addConverters(new ParameterConverters.DateConverter(new SimpleDateFormat("yyyy-MM-dd"))))
            .useStepPatternParser(new RegexPrefixCapturingPatternParser(
                "%"));
//            .useStepMonitor(new SilentStepMonitor()).usePendingStepStrategy(new FailingUponPendingStep());
//        configuration.storyControls().doSkipScenariosAfterFailure(true);
        return configuration;
    }


    /**
     * @return Geeft de stappen terug die in de JBehave stories kunnen voorkomen.
     */
    @Override
    public final InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(), new ComponentTestStappen());
    }

}
