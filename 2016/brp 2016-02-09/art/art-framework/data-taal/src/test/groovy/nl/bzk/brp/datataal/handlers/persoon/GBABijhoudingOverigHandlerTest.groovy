package nl.bzk.brp.datataal.handlers.persoon

import nl.bzk.brp.datataal.AbstractDSLIntegratieTest
import nl.bzk.brp.datataal.execution.PersoonDSLExecutor
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl
import nl.bzk.brp.model.operationeel.kern.HisPersoonAfgeleidAdministratiefModel
import org.junit.Test

class GBABijhoudingOverigHandlerTest extends AbstractDSLIntegratieTest {

    @Test
    void "dient een administratieve handeling GBABijhoudingOverig te zijn"() {
        String dsl = """
persoon = Persoon.uitGebeurtenissen {
    geboorte(partij: 36101, aanvang: 19781023, toelichting: '1e kind') {
    }

    verhuizing(partij: 'Gemeente Gorinchem', aanvang: 19841023) {
        naarGemeente 'Gorinchem', straat: 'Dorpelstraat', nummer: 15, postcode: '4208AA', woonplaats: 'Gorinchem'
    }

    GBABijhoudingOverig(partij: 'Gemeente Rotterdam', aanvang: 19930731) {
        naarBuitenland 'Mali',
            adres: 'adresRegel1, adresRegel2'
    }
}
"""
        // act
        def persoon = new PersoonDSLExecutor().execute(dsl).persoon as PersoonHisVolledigImpl

        // assert
        assert persoon != null
        assert persoon.persoonAfgeleidAdministratiefHistorie.size() == 3
        HisPersoonAfgeleidAdministratiefModel afgeleidAdm = (HisPersoonAfgeleidAdministratiefModel)persoon.persoonAfgeleidAdministratiefHistorie.find {
            it.administratieveHandeling.acties.find {
                it.datumAanvangGeldigheid.waarde == 19930731
            }
        }
        assert afgeleidAdm.administratieveHandeling.soort.waarde.equals(SoortAdministratieveHandeling.G_B_A_BIJHOUDING_OVERIG)
    }

}
