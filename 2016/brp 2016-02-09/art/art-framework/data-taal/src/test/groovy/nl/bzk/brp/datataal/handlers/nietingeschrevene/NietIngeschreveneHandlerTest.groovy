package nl.bzk.brp.datataal.handlers.nietingeschrevene

import nl.bzk.brp.datataal.AbstractDSLIntegratieTest
import nl.bzk.brp.datataal.execution.PersoonDSLExecutor
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl
import org.junit.Test

class NietIngeschreveneHandlerTest extends AbstractDSLIntegratieTest {

    /**
     * Test die valideert dat voor een niet ingeschrevene er GEEN
     * afgeleid administratief meer is.
     */
    @Test
    void 'opgeslagen niet ingeschreven heeft geen afgeleidadministratief'() {
        String dsl = '''
persoon = Persoon.nietIngeschrevene(aanvang: 19670401, registratieDatum: 19670402) {
    geboorte {
        op '1967/04/01' te 'Barcelona' land 'Spanje\'
    }
    samengesteldeNaam(
        stam: 'Montoya',
        voornamen: 'Inigo'
    )
    geslacht('MAN')
}
persoon = slaOp(persoon)
'''

        def persoon = new PersoonDSLExecutor().execute(dsl).persoon as PersoonHisVolledigImpl

        assert persoon != null
        assert persoon.soort.waarde == SoortPersoon.NIET_INGESCHREVENE
        assert persoon.persoonAfgeleidAdministratiefHistorie.size() == 0
        assert persoon.persoonGeboorteHistorie.actueleRecord.verantwoordingInhoud != null
        assert persoon.persoonGeboorteHistorie.actueleRecord.verantwoordingInhoud.administratieveHandeling.ID != null
    }

    /**
     * Test die valideert dat voor een niet ingeschrevene er WEL
     * afgeleid administratief, omdat die nodig is voor het opslaan van de persoon.
     */
    @Test
    void 'gemaakte niet ingeschrevene heeft een afgeleidadministratief'() {
        String dsl = '''
persoon = Persoon.nietIngeschrevene(aanvang: 19670401, registratieDatum: 19670401) {
    geboorte {
        op '1967/04/01' te 'Barcelona' land 'Spanje\'
    }
    samengesteldeNaam(
        stam: 'Montoya',
        voornamen: 'Inigo'
    )
    geslacht('MAN')
}
'''

        def persoon = new PersoonDSLExecutor().execute(dsl).persoon as PersoonHisVolledigImpl

        assert persoon != null
        assert persoon.soort.waarde == SoortPersoon.NIET_INGESCHREVENE
        assert persoon.persoonAfgeleidAdministratiefHistorie.size() == 1
        assert persoon.persoonGeboorteHistorie.actueleRecord.verantwoordingInhoud != null
        assert persoon.persoonGeboorteHistorie.actueleRecord.verantwoordingInhoud.administratieveHandeling.ID == null
    }

}
