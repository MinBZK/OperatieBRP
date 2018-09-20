package nl.bzk.brp.funqmachine.storytests

import static org.mockito.Mockito.mock
import static org.mockito.Mockito.spy
import static org.mockito.Mockito.when

import nl.bzk.brp.funqmachine.AcceptanceTest
import nl.bzk.brp.funqmachine.jbehave.context.BevraagbaarContextView
import nl.bzk.brp.funqmachine.jbehave.context.DefaultScenarioRunContext
import nl.bzk.brp.funqmachine.jbehave.context.ScenarioRunContext
import nl.bzk.brp.funqmachine.jbehave.steps.AsynchroonBerichtenSteps
import nl.bzk.brp.funqmachine.ontvanger.HttpLeveringOntvanger
import org.springframework.test.util.ReflectionTestUtils

@AcceptanceTest
public class ResetLeveringenStepsStoryTest extends AbstractStoryTest {

    @Override
    Object[] maakStapAanInclusiefMocks() {
        final AsynchroonBerichtenSteps asynchroonBerichtenSteps = new AsynchroonBerichtenSteps()

        final HttpLeveringOntvanger ontvanger = mock(HttpLeveringOntvanger)
        ReflectionTestUtils.setField(asynchroonBerichtenSteps, "ontvanger", ontvanger)

        final runContext = spy(new DefaultScenarioRunContext())
        ReflectionTestUtils.setField(asynchroonBerichtenSteps, "runContext", runContext)

        [asynchroonBerichtenSteps] as Object[]
    }

}
