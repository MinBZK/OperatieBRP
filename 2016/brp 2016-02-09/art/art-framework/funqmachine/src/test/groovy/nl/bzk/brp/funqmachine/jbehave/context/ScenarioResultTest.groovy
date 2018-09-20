package nl.bzk.brp.funqmachine.jbehave.context

import org.junit.Test

class ScenarioResultTest {

    @Test
    void geeftPathGoed() {
        ScenarioResult result = new ScenarioResult('foo')
        result.story = ['path1','path2', 'filename.ext'].join('/')

        assert result.path == 'filename/foo'
    }
}
