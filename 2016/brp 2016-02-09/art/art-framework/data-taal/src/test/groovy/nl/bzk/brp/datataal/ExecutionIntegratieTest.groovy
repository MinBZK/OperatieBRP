package nl.bzk.brp.datataal

import nl.bzk.brp.datataal.execution.PersoonDSLExecutor
import org.junit.Test
import org.springframework.core.io.DefaultResourceLoader

class ExecutionIntegratieTest extends AbstractDSLIntegratieTest {

    @Test
    void dslUitvoerMetIngeschrevenPersoon() {
        //arrange
        def source = new DefaultResourceLoader().getResource('testpersoon.groovy')

        //act
        new PersoonDSLExecutor().execute(source)
    }
}
