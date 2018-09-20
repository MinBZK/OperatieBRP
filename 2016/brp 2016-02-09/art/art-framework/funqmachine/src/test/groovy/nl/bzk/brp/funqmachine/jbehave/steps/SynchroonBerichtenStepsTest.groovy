package nl.bzk.brp.funqmachine.jbehave.steps

import org.junit.Ignore
import org.junit.Test

class SynchroonBerichtenStepsTest {

    SynchroonBerichtenSteps steps = new SynchroonBerichtenSteps() {}

    @Test
    @Ignore
    void kanExtraDataLaden() {
        def data = steps.data
        data.put('k', [:])

        def extras = [new SynchroonBerichtenSteps.PropertyRow(sleutel: 'kAA', waarde: 'v01'), new SynchroonBerichtenSteps.PropertyRow(sleutel: 'k.a', waarde: 'v02')]
        steps.deExtraWaardes(extras)

        assert data.toString() == '[k:[a:v02], kAA:v01]'
    }

    @Test
    void testCheckExpected() {
        def input = '/testcases/art/data/expected.xml'

        def output = steps.checkExpected(input)

        assert output == '/testcases/art/data/watanders.xml'
    }

}
