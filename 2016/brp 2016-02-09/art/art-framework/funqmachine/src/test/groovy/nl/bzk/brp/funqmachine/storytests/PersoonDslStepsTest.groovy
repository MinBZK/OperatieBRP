package nl.bzk.brp.funqmachine.storytests

import nl.bzk.brp.funqmachine.AcceptanceTest
import nl.bzk.brp.funqmachine.jbehave.steps.DataTaalContextLader
import nl.bzk.brp.funqmachine.jbehave.steps.HousekeepingSteps
import org.springframework.test.util.ReflectionTestUtils

/**
 *
 */
@AcceptanceTest
class PersoonDslStepsTest extends AbstractStoryTest {

    @Override
    Object[] maakStapAanInclusiefMocks() {
        HousekeepingSteps steps = new HousekeepingSteps()
        ReflectionTestUtils.setField(steps, "dataTaalContextLader", new DataTaalContextLader())
        [steps] as Object[]
    }
}
