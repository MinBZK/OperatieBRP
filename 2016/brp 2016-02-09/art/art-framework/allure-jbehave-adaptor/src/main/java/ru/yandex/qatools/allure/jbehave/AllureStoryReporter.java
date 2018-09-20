/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package ru.yandex.qatools.allure.jbehave;

import static ru.yandex.qatools.allure.config.AllureModelUtils.createFeatureLabel;
import static ru.yandex.qatools.allure.config.AllureModelUtils.createIssueLabel;
import static ru.yandex.qatools.allure.config.AllureModelUtils.createStoryLabel;
import static ru.yandex.qatools.allure.config.AllureModelUtils.createTestFrameworkLabel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.lang.StringUtils;
import org.jbehave.core.failures.UUIDExceptionWrapper;
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
import ru.yandex.qatools.allure.Allure;
import ru.yandex.qatools.allure.events.ClearStepStorageEvent;
import ru.yandex.qatools.allure.events.StepCanceledEvent;
import ru.yandex.qatools.allure.events.StepFailureEvent;
import ru.yandex.qatools.allure.events.StepFinishedEvent;
import ru.yandex.qatools.allure.events.StepStartedEvent;
import ru.yandex.qatools.allure.events.TestCaseFailureEvent;
import ru.yandex.qatools.allure.events.TestCaseFinishedEvent;
import ru.yandex.qatools.allure.events.TestCaseStartedEvent;
import ru.yandex.qatools.allure.events.TestSuiteFinishedEvent;
import ru.yandex.qatools.allure.events.TestSuiteStartedEvent;
import ru.yandex.qatools.allure.model.Label;
import ru.yandex.qatools.allure.utils.AnnotationManager;

/**
 *
 */
public class AllureStoryReporter implements StoryReporter {
    private Allure lifecycle = Allure.LIFECYCLE;
    private String suiteGuid = UUID.randomUUID().toString();
    private Throwable stepThrowable;
    private String    feature;
    private String    issue;
    private String    latestScenarioTitle;

    @Override
    public void storyNotAllowed(final Story story, final String filter) {
    }

    @Override
    public void storyCancelled(final Story story, final StoryDuration storyDuration) {
        TestSuiteFinishedEvent event = new TestSuiteFinishedEvent(suiteGuid);
        getLifecycle().fire(event);
    }

    @Override
    public void beforeStory(final Story story, final boolean givenStory) {
        suiteGuid = UUID.randomUUID().toString();
        String storyName = StringUtils.capitalize(story.getName().split("\\.")[0].replaceAll("_", " "));

        TestSuiteStartedEvent event = new TestSuiteStartedEvent(suiteGuid, storyName).withTitle(storyName);
        new AnnotationManager().update(event);

        event.withLabels(createTestFrameworkLabel("JBehave"), createStoryLabel(storyName));

        this.feature = story.getMeta().getProperty("epic");
        this.issue = story.getMeta().getProperty("jiraIssue");

        getLifecycle().fire(event);
    }

    @Override
    public void afterStory(final boolean givenStory) {
        TestSuiteFinishedEvent event = new TestSuiteFinishedEvent(suiteGuid);
        getLifecycle().fire(event);
    }

    @Override
    public void narrative(final Narrative narrative) {
    }

    @Override
    public void lifecyle(final Lifecycle lifecycle) {
    }

    @Override
    public void scenarioNotAllowed(final Scenario scenario, final String filter) {
    }

    @Override
    public void beforeScenario(final String scenarioTitle) {
        this.latestScenarioTitle = scenarioTitle;
        this.stepThrowable = null;
    }

    @Override
    public void scenarioMeta(final Meta meta) {
        List<Label> labels = new ArrayList<>();

        String scenarioFeature = "";
        if (!meta.getProperty("epic").trim().equals("")) {
            scenarioFeature = meta.getProperty("epic").trim();
        }
        if (!(this.feature.equals("") && scenarioFeature.equals(""))) {
            labels.add(createFeatureLabel(scenarioFeature.equals("") ? this.feature : scenarioFeature));
        }

        String scenarioIssue = "";
        if (!meta.getProperty("jiraIssue").trim().equals("")) {
            scenarioIssue = meta.getProperty("jiraIssue").trim();
        }
        if (!(this.issue.equals("") && scenarioIssue.equals(""))) {
            String[] issues = (scenarioIssue.equals("") ? this.issue : scenarioIssue).split(",");

            for (String s : issues) {
                labels.add(createIssueLabel(s.trim()));
            }
        }

        TestCaseStartedEvent event = new TestCaseStartedEvent(suiteGuid, latestScenarioTitle).withTitle(latestScenarioTitle);
        event.getLabels().addAll(labels);

        new AnnotationManager().update(event);

        fireClearStepStorage();
        getLifecycle().fire(event);
        this.latestScenarioTitle = null;
    }

    @Override
    public void afterScenario() {
        if (this.stepThrowable != null) {
            getLifecycle().fire(new TestCaseFailureEvent().withThrowable(this.stepThrowable));
        }

        getLifecycle().fire(new TestCaseFinishedEvent());
    }

    @Override
    public void givenStories(final GivenStories givenStories) {
    }

    @Override
    public void givenStories(final List<String> storyPaths) {
    }

    @Override
    public void beforeExamples(final List<String> steps, final ExamplesTable table) {
    }

    @Override
    public void example(final Map<String, String> tableRow) {
    }

    @Override
    public void afterExamples() {
    }

    @Override
    public void beforeStep(final String step) {
        String title = getPrettyTitle(step);
        StepStartedEvent event = new StepStartedEvent(title).withTitle(title);
        getLifecycle().fire(event);
    }

    @Override
    public void successful(final String step) {
        getLifecycle().fire(new StepFinishedEvent());
    }

    @Override
    public void ignorable(final String step) {
    }

    @Override
    public void pending(final String step) {
        String title = getPrettyTitle(step);
        getLifecycle().fire(new StepStartedEvent(title).withTitle(title));

        StepFailureEvent event = new StepFailureEvent().withThrowable(new AssertionError("Step is not implemented"));
        getLifecycle().fire(event);

        getLifecycle().fire(new StepFinishedEvent());
    }

    @Override
    public void notPerformed(final String step) {
        String title = getPrettyTitle(step);

        getLifecycle().fire(new StepStartedEvent(title).withTitle(title));
        getLifecycle().fire(new StepCanceledEvent());
        getLifecycle().fire(new StepFinishedEvent());
    }

    @Override
    public void failed(final String step, final Throwable cause) {
        Throwable realCause = (cause instanceof UUIDExceptionWrapper) ? cause.getCause() : cause;
        getLifecycle().fire(new StepFailureEvent().withThrowable(realCause));
        getLifecycle().fire(new StepFinishedEvent());

        this.stepThrowable = realCause;
    }

    @Override
    public void failedOutcomes(final String step, final OutcomesTable table) {
    }

    @Override
    public void restarted(final String step, final Throwable cause) {
    }

    @Override
    public void dryRun() {
    }

    @Override
    public void pendingMethods(final List<String> methods) {
    }

    private Allure getLifecycle() {
        return lifecycle;
    }

    private void fireClearStepStorage() {
        getLifecycle().fire(new ClearStepStorageEvent());
    }

    private String getPrettyTitle(String step) {
        return step.split("\n", 2)[0];
    }
}
