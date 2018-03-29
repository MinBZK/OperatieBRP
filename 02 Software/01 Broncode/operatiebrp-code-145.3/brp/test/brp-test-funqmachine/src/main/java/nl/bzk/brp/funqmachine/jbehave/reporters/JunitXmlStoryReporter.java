/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.jbehave.reporters;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Path;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import nl.bzk.brp.funqmachine.schrijvers.FileHandler;
import org.jbehave.core.model.Scenario;
import org.jbehave.core.model.Story;
import org.jbehave.core.reporters.NullStoryReporter;
import org.jbehave.core.reporters.StoryReporter;
import org.jbehave.core.steps.AbstractStepResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * JBehave {@link StoryReporter} die informatie verzamelt en rapporteert in de vorm van een junit xml bestand per story.
 * <p>
 * #see <a href=
 * "https://svn.apache.org/repos/asf/ant/core/trunk/src/main/org/apache/tools/ant/taskdefs/optional/junit/XMLJUnitResultFormatter.java">ant
 * XMLJUnitResultFormatter</a>
 */
public final class JunitXmlStoryReporter extends NullStoryReporter {
    private static final String NAME_ATTRIBUUT_LABEL = "name";

    private static final Logger LOGGER = LoggerFactory.getLogger(JunitXmlStoryReporter.class);

    private ThreadLocal<TestStory> currentStoryStore = new ThreadLocal<>();
    private ThreadLocal<TestScenario> currentScenarioStore = new ThreadLocal<>();

    @Override
    public void beforeStory(final Story story, final boolean givenStory) {
        this.currentScenarioStore.remove();
        this.currentStoryStore.remove();
        final TestStory currentStory = new TestStory();
        currentStory.setName(story.getPath());
        this.currentStoryStore.set(currentStory);
    }

    @Override
    public void afterStory(final boolean givenStory) {
        schrijfReport(getCurrentStory());
        this.currentScenarioStore.remove();
        this.currentStoryStore.remove();
    }

    @Override
    public void scenarioNotAllowed(final Scenario scenario, final String filter) {
        final TestScenario testScenario = new TestScenario();
        testScenario.setName(scenario.getTitle());
        testScenario.setSkipped(true);
        getCurrentStory().voegScenarioToe(testScenario);
    }

    @Override
    public void beforeScenario(final String scenarioTitle) {
        final TestScenario currentScenario = new TestScenario();
        currentScenario.setName(scenarioTitle);
        this.currentScenarioStore.set(currentScenario);
        getCurrentStory().voegScenarioToe(currentScenario);
    }

    @Override
    public void successful(final String step) {
        final TestStep testStep = new TestStep();
        testStep.setName(step);
        getCurrentScenario().voegStapToe(testStep);
    }

    @Override
    public void pending(final String step) {
        final TestStep testStep = new TestStep();
        testStep.setName(step);
        testStep.setStatus(TestStep.Status.ERROR);
        getCurrentScenario().voegStapToe(testStep);
    }

    @Override
    public void notPerformed(final String step) {
        final TestStep testStep = new TestStep();
        testStep.setName(step);
        testStep.setStatus(TestStep.Status.SKIPPED);
        getCurrentScenario().voegStapToe(testStep);
    }

    @Override
    public void failed(final String step, final Throwable cause) {
        LOGGER.error("step gefaald: ", cause);
        if (getCurrentScenario() == null) {
            final TestScenario currentScenario = new TestScenario();
            currentScenario.setName("Fout scenario: " + cause.getMessage());
            this.currentScenarioStore.set(currentScenario);
        }

        final TestStep testStep = new TestStep();
        testStep.setName(step);
        testStep.setStatus(TestStep.Status.FAILED);
        testStep.setEx(cause.getCause());
        getCurrentScenario().voegStapToe(testStep);
    }

    private void schrijfReport(final TestStory story) {
        try {
            if (!"BeforeStories".equals(story.getName()) && !"AfterStories".equals(story.getName())) {
                final String filename = "../surefire-reports/TEST_" + story.getName() + ".xml";
                final Path path = new FileHandler().geefOutputFile(filename);
                final Document document = maakXml(story);
                TransformerFactory.newInstance().newTransformer().transform(new DOMSource(document), new StreamResult(path.toFile()));
            }
        } catch (ParserConfigurationException | TransformerException e) {
            LOGGER.error("Kan testrapport niet wegschrijven", e);
        }
    }

    private Document maakXml(final TestStory story) throws ParserConfigurationException {
        final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        final Document document = documentBuilder.newDocument();
        final Element rootElement = document.createElement("testsuite");
        rootElement.setAttribute(NAME_ATTRIBUUT_LABEL, story.getName());
        rootElement.setAttribute("tests", String.valueOf(story.getScenarios().size()));
        rootElement.setAttribute("errors", String.valueOf(story.geefAantalErrors()));
        rootElement.setAttribute("skipped", String.valueOf(story.geefAantalSkipped()));
        rootElement.setAttribute("failures", String.valueOf(story.geefAantalFailed()));
        document.appendChild(rootElement);

        voegScenariosToeAanXml(story, rootElement, document);

        return document;
    }

    private void voegScenariosToeAanXml(final TestStory story, final Element rootElement, final Document document) {
        for (final TestScenario scenario : story.getScenarios()) {
            final Element testcaseNode = document.createElement("testcase");
            rootElement.appendChild(testcaseNode);
            testcaseNode.setAttribute(NAME_ATTRIBUUT_LABEL, scenario.getName());
            testcaseNode.setAttribute("classname", story.getName());

            final String messageAttribuutLabel = "message";
            final String typeAttribuutLabel = "type";
            if (scenario.isFailed()) {
                final Element failureNode = document.createElement("failure");
                testcaseNode.appendChild(failureNode);
                final Throwable exception = scenario.getException();
                if (exception != null) {
                    failureNode.setAttribute(messageAttribuutLabel, exception.getMessage());
                    failureNode.setAttribute(typeAttribuutLabel, exception.getClass().getName());
                    final Writer cdataWriter = new StringWriter();
                    exception.printStackTrace(new PrintWriter(cdataWriter));
                    final CDATASection cdataSection = document.createCDATASection(cdataWriter.toString());
                    failureNode.appendChild(cdataSection);
                }
            } else if (scenario.isSkipped()) {
                AbstractStepResult.skipped();
            } else if (scenario.isError()) {
                final Element errorNode = document.createElement("error");
                testcaseNode.appendChild(errorNode);
                errorNode.setAttribute(messageAttribuutLabel, String.format("pending step found in '%s'", scenario.getName()));
                errorNode.setAttribute(typeAttribuutLabel, "java.lang.Exception");
                errorNode.appendChild(document.createCDATASection(String.format("<![CDATA[pending step in '%s']]>", scenario.getName())));
            }
        }
    }

    /**
     * Geeft de huidige story terug.
     * @return de huidige story
     */
    private TestStory getCurrentStory() {
        return this.currentStoryStore.get();
    }

    /**
     * Geeft de huidige scenario.
     * @return de huidige scenario
     */
    private TestScenario getCurrentScenario() {
        return this.currentScenarioStore.get();
    }
}
