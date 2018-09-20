package nl.bzk.brp.datataal.model

import nl.bzk.brp.datataal.AbstractDSLIntegratieTest
import org.junit.Test

class PersoonDatabaseIntegratieTest extends AbstractDSLIntegratieTest {

    @Test
    void leesVanId() {
        def persoon = Persoon.uitDatabase(persoon: 1001)

        assert persoon != null
        assert persoon.persoonIdentificatienummersHistorie.actueleRecord.burgerservicenummer.waarde == 302533928
    }

    @Test
    void leesVanBsn() {
        def persoon = Persoon.uitDatabase(bsn: 302533928)

        assert persoon != null
        assert persoon.ID == 1001
    }

}
