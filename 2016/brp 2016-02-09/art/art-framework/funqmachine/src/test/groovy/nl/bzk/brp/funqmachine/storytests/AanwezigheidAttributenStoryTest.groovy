package nl.bzk.brp.funqmachine.storytests

import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

import nl.bzk.brp.funqmachine.AcceptanceTest
import nl.bzk.brp.funqmachine.jbehave.context.BevraagbaarContextView
import nl.bzk.brp.funqmachine.jbehave.context.DefaultScenarioRunContext
import nl.bzk.brp.funqmachine.jbehave.context.StepResult
import nl.bzk.brp.funqmachine.jbehave.steps.AsynchroonBerichtenSteps
import nl.bzk.brp.funqmachine.ontvanger.HttpLeveringOntvanger
import org.junit.Ignore
import org.springframework.test.util.ReflectionTestUtils

@AcceptanceTest
public class AanwezigheidAttributenStoryTest extends AbstractStoryTest {

    @Override
    Object[] maakStapAanInclusiefMocks() {
        final AsynchroonBerichtenSteps asynchroonBerichtenSteps = new AsynchroonBerichtenSteps()

        final HttpLeveringOntvanger ontvanger = mock(HttpLeveringOntvanger)
        ReflectionTestUtils.setField(asynchroonBerichtenSteps, "ontvanger", ontvanger)

        final runContext = new DefaultScenarioRunContext()
        ReflectionTestUtils.setField(asynchroonBerichtenSteps, "runContext", runContext)

        def mockResult = new StepResult(StepResult.Soort.LEVERING)
        mockResult.response = '<brp:mock xmlns:brp="http://www.bzk.nl/brp/brp0200"><brp:message>foo</brp:message></brp:mock>'
        runContext << mockResult

        [asynchroonBerichtenSteps] as Object[]
    }

}
