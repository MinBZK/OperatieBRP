package nl.bzk.brp.datataal

import javax.inject.Inject
import nl.bzk.brp.dataaccess.test.Data
import nl.bzk.brp.datataal.dataaccess.SpringBeanProvider
import nl.bzk.brp.datataal.test.AbstractDBUnitIntegratieTest
import org.junit.After
import org.junit.Before
import org.springframework.context.ApplicationContext

/**
 * Ten behoeve van integratie tests met de DSL is de set data voor deze integratie test
 * aangepast. Het aantal personen is nog maar 8, 2 ingeschrevenen en 6 niet ingeschreven
 * personen. De ID's van alle KERN tabellen beginnen boven de 900 zodat insert geen conflicten
 * geven met de door DBUnit toegevoegde data. (dynamische) stamgegevens worden gebruikt zoals
 * aanwezig in het BMR en behoren niet tot de testset.
 */
@Data(resources = [
        "classpath:/data/dsl/dataset.xml",
        "classpath:/data/blob/cleanup.xml"] )
abstract class AbstractDSLIntegratieTest extends AbstractDBUnitIntegratieTest {

        @Inject
        ApplicationContext applicationContextBla;

        @Before
        public void voorMijnTestBegint() {
                SpringBeanProvider.setContext(applicationContextBla)
        }

        @After
        public void alsMijnTestAfgelopenIs() {
                SpringBeanProvider.setContext(null)
        }
}
