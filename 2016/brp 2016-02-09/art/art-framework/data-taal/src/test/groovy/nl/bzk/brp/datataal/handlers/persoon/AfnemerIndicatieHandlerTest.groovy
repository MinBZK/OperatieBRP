package nl.bzk.brp.datataal.handlers.persoon

import nl.bzk.brp.datataal.AbstractDSLIntegratieTest
import nl.bzk.brp.datataal.execution.PersoonDSLExecutor
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl
import org.junit.Ignore
import org.junit.Test

@Ignore // TODO inmemory geeft deze test een fout
class AfnemerIndicatieHandlerTest extends AbstractDSLIntegratieTest {

    @Test
    void "dient een afnemerindicatie te plaatsen"() {
        String dsl = """
persoon = Persoon.uitGebeurtenissen {
    geboorte(partij: 36101, aanvang: 19781023, toelichting: '1e kind') { }

    afnemerindicaties {
        plaatsVoor(afnemer: 36101, abonnement: 'Abonnement 1') {
            datumAanvangMaterielePeriode '2000/02/02'
            eindeVolgen '2010/12/31'
        }
        verwijderVan afnemer: 36101, abonnement: 'Abonnement 1'
        plaatsVoor(afnemer: 36101, abonnement: 'Abonnement 1') {
            datumAanvangMaterielePeriode '2010/10/10'
            eindeVolgen '2015/12/31'
        }
        plaatsVoor(afnemer: 199901, abonnement: 'Abonnement 2')
    }
}
"""
        // act
        def persoon = new PersoonDSLExecutor().execute(dsl).persoon as PersoonHisVolledigImpl

        // assert
        assert persoon != null
        assert persoon.afnemerindicaties.size() == 2
        assert persoon.afnemerindicaties.sort { it.afnemer.waarde.code }[0].persoonAfnemerindicatieHistorie.size() == 2
    }
}
