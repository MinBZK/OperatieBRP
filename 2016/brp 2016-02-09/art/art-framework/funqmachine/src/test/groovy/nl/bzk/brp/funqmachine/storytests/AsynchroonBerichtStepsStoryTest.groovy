package nl.bzk.brp.funqmachine.storytests

import static org.mockito.Mockito.mock

import nl.bzk.brp.funqmachine.AcceptanceTest
import nl.bzk.brp.funqmachine.jbehave.context.DefaultScenarioRunContext
import nl.bzk.brp.funqmachine.jbehave.context.StepResult
import nl.bzk.brp.funqmachine.jbehave.steps.AsynchroonBerichtenSteps
import nl.bzk.brp.funqmachine.ontvanger.HttpLeveringOntvanger
import org.springframework.test.util.ReflectionTestUtils

@AcceptanceTest
public class AsynchroonBerichtStepsStoryTest extends AbstractStoryTest {

    @Override
    Object[] maakStapAanInclusiefMocks() {
        final AsynchroonBerichtenSteps asynchroonBerichtenSteps = new AsynchroonBerichtenSteps()

        final HttpLeveringOntvanger ontvanger = mock(HttpLeveringOntvanger)
        ReflectionTestUtils.setField(asynchroonBerichtenSteps, "ontvanger", ontvanger)

        def step = new StepResult(StepResult.Soort.LEVERING)
        step.response = new File(getClass().getResource("/xml/3-actual.xml").toURI()).text

        final runContext = new DefaultScenarioRunContext()
        runContext << step

        ReflectionTestUtils.setField(asynchroonBerichtenSteps, "runContext", runContext)

        [asynchroonBerichtenSteps] as Object[]
    }

}
