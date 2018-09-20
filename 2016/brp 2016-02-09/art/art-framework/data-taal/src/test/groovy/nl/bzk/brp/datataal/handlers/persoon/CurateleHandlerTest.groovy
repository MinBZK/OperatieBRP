package nl.bzk.brp.datataal.handlers.persoon

import nl.bzk.brp.datataal.AbstractDSLIntegratieTest
import nl.bzk.brp.datataal.execution.PersoonDSLExecutor
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl
import org.junit.Test

class CurateleHandlerTest extends AbstractDSLIntegratieTest  {

    @Test
    void "dient een persoon onder curatele te plaatsen"() {
        String dsl = """
persoon = Persoon.uitGebeurtenissen {
    geboorte(partij: 36101, aanvang: 19781023, toelichting: '1e kind') {
    }

    curatele(partij: 36101, aanvang: 19791023, toelichting: 'curatele') {
      ja
    }
}

"""
        // act
        def persoon = new PersoonDSLExecutor().execute(dsl).persoon as PersoonHisVolledigImpl

        // assert
        assert persoon != null
        assert persoon.indicatieOnderCuratele.persoonIndicatieHistorie.aantal == 1
        assert persoon.indicatieOnderCuratele.persoonIndicatieHistorie.actueleRecord != null
    }

    @Test
    void "dient historie te hebben voor curatele maar geen actueel record meer"() {
        String dsl = """
persoon = Persoon.uitGebeurtenissen {
    geboorte(partij: 36101, aanvang: 19781023, toelichting: '1e kind') {
    }

    curatele(partij: 36101, aanvang: 19791023, toelichting: 'curatele') {
      ja
    }
    curatele(aanvang: 19951001) {
      nee
    }
}

"""
        // act
        def persoon = new PersoonDSLExecutor().execute(dsl).persoon as PersoonHisVolledigImpl

        // assert
        assert persoon != null
        assert persoon.indicatieOnderCuratele.persoonIndicatieHistorie.actueleRecord == null
        assert persoon.indicatieOnderCuratele.persoonIndicatieHistorie.aantal == 2
    }
}
