package nl.bzk.brp.datataal
import nl.bzk.brp.datataal.execution.PersoonDSLExecutor
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl
import nl.bzk.brp.utils.junit.OverslaanBijInMemoryDatabase
import org.junit.Test
import org.junit.experimental.categories.Category
import org.springframework.core.io.DefaultResourceLoader

import static junit.framework.Assert.assertEquals
import static junit.framework.Assert.assertNull

@Category(OverslaanBijInMemoryDatabase)
class OpslaanPersoonIntegratieTest extends AbstractDSLIntegratieTest {

    @Test
    void kanPersoonOpslaan() {
        def source = new DefaultResourceLoader().getResource('opslaanpersoon.groovy')
        def result = new PersoonDSLExecutor().execute(source)

        assert result.size() > 0
        assert result['tester'].getID() > 0

        PersoonHisVolledigImpl persoon = (PersoonHisVolledigImpl) result['tester']
        assertEquals(1, persoon.adressen.size())

        assertEquals("-", persoon.getPersoonSamengesteldeNaamHistorie().actueleRecord.scheidingsteken.waarde)
        assertEquals("Override", persoon.getPersoonSamengesteldeNaamHistorie().actueleRecord.geslachtsnaamstam.waarde)
        assertEquals("Manuèl", persoon.getPersoonSamengesteldeNaamHistorie().actueleRecord.voornamen.waarde)

        persoon.geslachtsnaamcomponenten.each {
            def record = it.persoonGeslachtsnaamcomponentHistorie.actueleRecord
            assertNull(record.scheidingsteken)
            assertEquals("Vorstenhove", record.stam.waarde)
            assertEquals("van", record.voorvoegsel.waarde)
        }

        assertEquals(persoon.voornamen.collect{ it.persoonVoornaamHistorie.actueleRecord.naam.waarde}, ["Willem", "Johannes"] as List)
    }

}
