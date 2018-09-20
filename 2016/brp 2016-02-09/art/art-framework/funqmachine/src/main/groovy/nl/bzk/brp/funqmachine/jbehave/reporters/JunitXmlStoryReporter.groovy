package nl.bzk.brp.funqmachine.jbehave.reporters

import groovy.xml.MarkupBuilder
import nl.bzk.brp.funqmachine.schrijvers.FileHandler
import org.jbehave.core.model.Scenario
import org.jbehave.core.model.Story
import org.jbehave.core.reporters.NullStoryReporter
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * JBehave {@link org.jbehave.core.reporters.StoryReporter} die informatie verzamelt en
 * rapporteert in de vorm van een junit xml bestand per story.
 *
 * #see <a href="https://svn.apache.org/repos/asf/ant/core/trunk/src/main/org/apache/tools/ant/taskdefs/optional/junit/XMLJUnitResultFormatter.java">ant XMLJUnitResultFormatter</a>
 */
class JunitXmlStoryReporter extends NullStoryReporter {
    private static final Logger LOGGER = LoggerFactory.getLogger(JunitXmlStoryReporter.class)

    TestStory currentStory
    TestScenario currentScenario

    @Override
    void beforeStory(final Story story, final boolean givenStory) {
        currentStory = new TestStory(name: story.path)
    }

    @Override
    void afterStory(final boolean givenStory) {
        schrijfReport(currentStory)
        currentScenario = null
        currentStory = null
    }

    @Override
    void scenarioNotAllowed(final Scenario scenario, final String filter) {
        currentStory << new TestScenario(name: scenario.title, skipped: true)
    }

    @Override
    void beforeScenario(final String scenarioTitle) {
        currentScenario = new TestScenario(name: scenarioTitle)
        currentStory << currentScenario
    }

    @Override
    void successful(final String step) {
        currentScenario << new TestStep(name: step)
    }

    @Override
    void pending(final String step) {
        currentScenario << new TestStep(name: step, status: TestStep.Status.ERROR)
    }

    @Override
    void notPerformed(final String step) {
        currentScenario << new TestStep(name: step, status: TestStep.Status.SKIPPED)
    }

    @Override
    void failed(final String step, final Throwable cause) {
        cause.printStackTrace();
        LOGGER.error("step gefaald: ", cause)
        if (currentScenario == null) {
            currentScenario = new TestScenario(name: "Fout scenario: " + cause.getMessage())
        }
        currentScenario << new TestStep(name: step, ex: cause.cause, status: TestStep.Status.FAILED)
    }

    /*
     * Schrijft de rapportage obv de verzamelde resultaten.
     * @param story het verzamelde resultaat
     */
    private void schrijfReport(TestStory story) {
        def filename = "../surefire-reports/TEST_${story.name}.xml"

        def file = new FileHandler().geefOutputFile(filename)
        file.write(maakXml(story).toString())
    }

    /*
     * Transformeert het resultaat naar een xml structuur door middel van
     * de Grcoovy {@link MarkupBuilder}.
     *
     * @param story het resultaat
     * @return het getransformeerde resultaat in een writer om te kunnen wegschrijven
     */
    private Writer maakXml(final TestStory story) {
        StringWriter writer = new StringWriter()
        def xml = new MarkupBuilder(writer)

        xml.testsuite(name:story.name, tests: story.size(), errors: story.errors(), skipped: story.skipped(), failures: story.failed()) {
            story.each { TestScenario scn ->
                testcase(name: scn.name, classname: story.name) {
                    if (scn.failed()) {
                        failure(message: scn.error.message, type: scn.error.class.name) {
                            def w = new StringWriter()
                            scn.error.printStackTrace(new PrintWriter(w))
                            xml.mkp.yieldUnescaped("<![CDATA[${w}]]>")
                        }
                    } else if (scn.skipped) {
                        skipped()
                    } else if (scn.error()) {
                        error(message: "pending step found in '${scn.name}'", type: 'java.lang.Exception') {
                            xml.mkp.yieldUnescaped("<![CDATA[pending step in '${scn.name}']]>")
                        }
                    }
                }
            }
        }

        writer
    }
}
