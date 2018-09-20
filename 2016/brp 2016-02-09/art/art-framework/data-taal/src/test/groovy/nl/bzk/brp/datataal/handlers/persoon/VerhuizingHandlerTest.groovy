package nl.bzk.brp.datataal.handlers.persoon

import nl.bzk.brp.datataal.AbstractDSLIntegratieTest
import nl.bzk.brp.datataal.execution.PersoonDSLExecutor
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl
import org.junit.Test

class VerhuizingHandlerTest extends AbstractDSLIntegratieTest {

    @Test
    void "dient een persoon naar het buitenland te verhuizen"() {
        String dsl = """
persoon = Persoon.uitGebeurtenissen {
    geboorte(partij: 36101, aanvang: 19781023, toelichting: '1e kind') {
    }

    verhuizing(partij: 'Gemeente Rotterdam', aanvang: 19930731) {
        naarBuitenland 'Mali',
            adres: 'adresRegel1, adresRegel2'
    }
}
"""
        // act
        def persoon = new PersoonDSLExecutor().execute(dsl).persoon as PersoonHisVolledigImpl

        // assert
        assert persoon != null
        assert persoon.adressen.size() == 1
        persoon.adressen.each{
            assert it.persoonAdresHistorie.actueleRecord.buitenlandsAdresRegel1.waarde.trim()  == "adresRegel1"
            assert it.persoonAdresHistorie.actueleRecord.buitenlandsAdresRegel2.waarde.trim()  == "adresRegel2"
        }
    }

    @Test
    void "dient een persoon naar het buitenland te verhuizen obv list"() {
        String dsl = """
persoon = Persoon.uitGebeurtenissen {
    geboorte(partij: 36101, aanvang: 19781023, toelichting: '1e kind') {
    }

    verhuizing(partij: 'Gemeente Rotterdam', aanvang: 19930731) {
        naarBuitenland 'Mali',
            adres: ['adresRegel1', 'adresRegel2']
    }
}
"""
        // act
        def persoon = new PersoonDSLExecutor().execute(dsl).persoon as PersoonHisVolledigImpl

        // assert
        assert persoon != null
        assert persoon.adressen.size() == 1
        persoon.adressen.each{
            assert it.persoonAdresHistorie.actueleRecord.buitenlandsAdresRegel1.waarde.trim()  == "adresRegel1"
            assert it.persoonAdresHistorie.actueleRecord.buitenlandsAdresRegel2.waarde.trim()  == "adresRegel2"
        }
    }



    @Test
    void "dient een verstrekkingsbeperking toe te voegen voor de verhuizing"() {
        String dsl = """
persoon = Persoon.uitGebeurtenissen {
    geboorte(partij: 36101, aanvang: 19781023, toelichting: '1e kind') {
    }

    verhuizing(partij: 'Gemeente Rotterdam', aanvang: 19930731) {
        verstrekkingsbeperking {
            volledig ja
        }
    }
}
"""
        // act
        def persoon = new PersoonDSLExecutor().execute(dsl).persoon as PersoonHisVolledigImpl

        // assert
        assert persoon != null
        assert persoon.indicatieVolledigeVerstrekkingsbeperking.persoonIndicatieHistorie.aantal == 1
    }

    @Test
    void "dient een verstrekkingsbeperking toe te voegen maar niet meer als actueel wegens verval"() {
        String dsl = """
persoon = Persoon.uitGebeurtenissen {
    geboorte(partij: 36101, aanvang: 19781023, toelichting: '1e kind') {
    }

    verhuizing(partij: 'Gemeente Rotterdam', aanvang: 19930731) {
        verstrekkingsbeperking { volledig ja }
    }

    verhuizing(partij: 'Gemeente Rotterdam', aanvang: 19930731) {
        verstrekkingsbeperking { volledig nee }
    }
}
"""
        // act
        def persoon = new PersoonDSLExecutor().execute(dsl).persoon as PersoonHisVolledigImpl

        // assert
        assert persoon != null
        assert persoon.indicatieVolledigeVerstrekkingsbeperking.persoonIndicatieHistorie?.actueleRecord == null
    }

    @Test
    void "dient niet te crashen als er de eerste verstrekkingsbeperking vervallen is"() {
        String dsl = """
persoon = Persoon.uitGebeurtenissen {
    geboorte(partij: 36101, aanvang: 19781023, toelichting: '1e kind') {
    }

    verhuizing(partij: 'Gemeente Rotterdam', aanvang: 19930731) {
        verstrekkingsbeperking { volledig nee }
    }
}
"""
        // act
        def persoon = new PersoonDSLExecutor().execute(dsl).persoon as PersoonHisVolledigImpl

        // assert
        assert persoon != null
        assert persoon.indicatieVolledigeVerstrekkingsbeperking?.persoonIndicatieHistorie?.actueleRecord == null
    }
}
