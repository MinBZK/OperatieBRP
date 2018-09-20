/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testrunner.jbehave;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import org.jbehave.core.model.ExamplesTable;
import org.jbehave.core.model.GivenStories;
import org.jbehave.core.model.Lifecycle;
import org.jbehave.core.model.Meta;
import org.jbehave.core.model.Narrative;
import org.jbehave.core.model.OutcomesTable;
import org.jbehave.core.model.Scenario;
import org.jbehave.core.model.Story;
import org.jbehave.core.model.StoryDuration;
import org.jbehave.core.reporters.StoryReporter;

/**
 * Eigen implementatie van de {@link StoryReporter} zodat we bv bij de locatie van de JBehave scripts kunnen komen.
 */
public final class BrpStoryReporter implements StoryReporter {

    private StoryReporter delegate;

    /**
     * Constructor voor de BrpStoryReporter. Deze class delegeert alle methoden naar de meegeven parameter.
     *
     * @param delegate de {@link StoryReporter} waar alle calls worden uitgevoerd
     */
    public BrpStoryReporter(final StoryReporter delegate) {
        this.delegate = delegate;
    }

    @Override
    public void storyNotAllowed(final Story story, final String s) {
        delegate.storyNotAllowed(story, s);
    }

    @Override
    public void storyCancelled(final Story story, final StoryDuration storyDuration) {
        delegate.storyCancelled(story, storyDuration);
    }

    @Override
    public void beforeStory(final Story story, final boolean givenStory) {
        final StoryController storyController = StoryController.get();
        final Path path = Paths.get(story.getPath());
        storyController.setStoryPath(path.getParent());
        delegate.beforeStory(story, givenStory);
    }

    @Override
    public void afterStory(final boolean b) {
        delegate.afterStory(b);
    }

    @Override
    public void narrative(final Narrative narrative) {
        delegate.narrative(narrative);
    }

    @Override
    public void lifecyle(final Lifecycle lifecycle) {
        delegate.lifecyle(lifecycle);
    }

    @Override
    public void scenarioNotAllowed(final Scenario scenario, final String s) {
        delegate.scenarioNotAllowed(scenario, s);
    }

    @Override
    public void beforeScenario(final String scenarioTitle) {
        delegate.beforeScenario(scenarioTitle);
    }

    @Override
    public void scenarioMeta(final Meta meta) {
        final String scenarioNaam = meta.getProperty("scenarioNaam");
        StoryController.get().setScenarioNaam(scenarioNaam);
        System.out.println("Scenarionaam: " + scenarioNaam);
        delegate.scenarioMeta(meta);
    }

    @Override
    public void afterScenario() {
        delegate.afterScenario();
    }

    @Override
    public void givenStories(final GivenStories givenStories) {
        delegate.givenStories(givenStories);
    }

    @Override
    public void givenStories(final List<String> list) {
        delegate.givenStories(list);
    }

    @Override
    public void beforeExamples(final List<String> list, final ExamplesTable examplesTable) {
        delegate.beforeExamples(list, examplesTable);
    }

    @Override
    public void example(final Map<String, String> map) {
        delegate.example(map);
    }

    @Override
    public void afterExamples() {
        delegate.afterExamples();
    }

    @Override
    public void beforeStep(final String s) {
        delegate.beforeStep(s);
    }

    @Override
    public void successful(final String s) {
        delegate.successful(s);
    }

    @Override
    public void ignorable(final String s) {
        delegate.ignorable(s);
    }

    @Override
    public void pending(final String s) {
        delegate.pending(s);
    }

    @Override
    public void notPerformed(final String s) {
        delegate.notPerformed(s);
    }

    @Override
    public void failed(final String s, final Throwable throwable) {
        delegate.failed(s, throwable);
    }

    @Override
    public void failedOutcomes(final String s, final OutcomesTable outcomesTable) {
        delegate.failedOutcomes(s, outcomesTable);
    }

    @Override
    public void restarted(final String s, final Throwable throwable) {
        delegate.restarted(s, throwable);
    }

    @Override
    public void dryRun() {
        delegate.dryRun();
    }

    @Override
    public void pendingMethods(final List<String> list) {
        delegate.pendingMethods(list);
    }
}
