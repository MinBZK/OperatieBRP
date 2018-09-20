package nl.bzk.brp.datataal.handlers.persoon

import nl.bzk.brp.datataal.AbstractDSLIntegratieTest
import nl.bzk.brp.datataal.execution.PersoonDSLExecutor
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl
import org.junit.Test

class WijzigingGezagHandlerTest extends AbstractDSLIntegratieTest  {

    @Test
    void "dient een persoon onder gezag van derde te plaatsen"() {
        String dsl = """
persoon = Persoon.uitGebeurtenissen {
    geboorte() { }

    wijzigingGezag(partij: 36101, aanvang: 19791023, toelichting: 'derdeHeeftGezag') {
        derdeHeeftGezag ja
    }
}
"""
        // act
        def persoon = new PersoonDSLExecutor().execute(dsl).persoon as PersoonHisVolledigImpl

        // assert
        assert persoon != null
        assert persoon.indicatieDerdeHeeftGezag.persoonIndicatieHistorie.aantal == 1
        assert persoon.indicatieDerdeHeeftGezag.persoonIndicatieHistorie.actueleRecord != null
    }

    @Test
    void "dient historie te hebben voor derdeHeeftGezag maar geen actueel record meer"() {
        String dsl = """
persoon = Persoon.uitGebeurtenissen {
    geboorte() { }

    wijzigingGezag(partij: 36101, aanvang: 19791023, toelichting: 'derdeHeeftGezag') {
        derdeHeeftGezag ja
    }
    wijzigingGezag(aanvang: 19951001) {
        derdeHeeftGezag nee
    }
}
"""
        // act
        def persoon = new PersoonDSLExecutor().execute(dsl).persoon as PersoonHisVolledigImpl

        // assert
        assert persoon != null
        assert persoon.indicatieDerdeHeeftGezag.persoonIndicatieHistorie.actueleRecord == null
        assert persoon.indicatieDerdeHeeftGezag.persoonIndicatieHistorie.aantal == 2
    }

    @Test
    void "dient historie te hebben voor ouderlijkGezag"() {
        String dsl = """
def moeder = Persoon.uitGebeurtenissen {
    geboorte() {
        identificatienummers bsn:12345678, anummer:321
    }
}

persoon = Persoon.uitGebeurtenissen {
    geboorte() {
        ouders (moeder, null)
    }

    wijzigingGezag(partij: 36101, aanvang: 19791023, toelichting: 'derdeHeeftGezag') {
        derdeHeeftGezag ja
        ouderlijkGezag 12345678, nee
    }
}
"""
        // act
        def persoon = new PersoonDSLExecutor().execute(dsl).persoon as PersoonHisVolledigImpl

        // assert
        assert persoon != null
        assert persoon.indicatieDerdeHeeftGezag.persoonIndicatieHistorie.actueleRecord.waarde.waarde == Ja.J
        assert persoon.kindBetrokkenheid.relatie.ouderBetrokkenheden.find {it.persoon != null}.ouderOuderlijkGezagHistorie.actueleRecord.indicatieOuderHeeftGezag.waarde == Boolean.FALSE
    }
}
