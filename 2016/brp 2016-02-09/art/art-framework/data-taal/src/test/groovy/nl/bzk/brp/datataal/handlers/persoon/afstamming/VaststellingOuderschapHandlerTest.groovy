package nl.bzk.brp.datataal.handlers.persoon.afstamming

import nl.bzk.brp.datataal.AbstractDSLIntegratieTest
import nl.bzk.brp.datataal.execution.PersoonDSLExecutor
import nl.bzk.brp.model.hisvolledig.impl.kern.OuderHisVolledigImpl
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl
import org.junit.Test

class VaststellingOuderschapHandlerTest extends AbstractDSLIntegratieTest {

    @Test
    void "vaststelling ouderschap"() {

        String dsl = """
def moeder = Persoon.uitGebeurtenissen {
    geboorte() {
        op '1978/10/22' te 'Monster' gemeente 'Westland'
        identificatienummers bsn: 1, anummer: 1
    }
}
kind = Persoon.uitGebeurtenissen {
    geboorte() {
        op '1998/10/22' te 'Monster' gemeente 'Westland'
        ouders(moeder: moeder)
    }
}

def vader = Persoon.uitGebeurtenissen {
    geboorte() {
        op '1978/10/22' te 'Monster' gemeente 'Westland'
        identificatienummers bsn: 2, anummer: 2
    }
}
Persoon.nieuweGebeurtenissenVoor(kind) {
    vaderVastgesteld(partij: 36101, aanvang: 19781023, toelichting: 'actualiseer ouderschap') {
        ouder(vader)
    }
}
"""
        // act
        def persoon = new PersoonDSLExecutor().execute(dsl).kind as PersoonHisVolledigImpl

        // assert
        assert persoon != null
        assert persoon.betrokkenheden.size() == 1
        assert persoon.betrokkenheden[0].relatie.betrokkenheden.size() == 3
        assert persoon.betrokkenheden[0].relatie.betrokkenheden.findAll{
            it.persoon != null
        }.size() == 3
    }

    @Test
    void "verwijdering ouderbetrokkenheid vader"() {

        String dsl = """
def truus = slaOp(Persoon.uitGebeurtenissen { geboorte() { } })
def piet = slaOp(Persoon.uitGebeurtenissen { geboorte() { } })

kind = Persoon.uitGebeurtenissen {
    geboorte() {
        op '1998/10/22' te 'Monster' gemeente 'Westland'
        ouders(moeder: truus, vader:piet)
    }

    vaderVastgesteld(partij: 36101, aanvang: 19781023, toelichting: 'actualiseer ouderschap') {
        nooitOuderGeweest(piet)
    }
}
slaOp kind
"""
        // act
        def persoon = new PersoonDSLExecutor().execute(dsl).kind as PersoonHisVolledigImpl

        // assert
        assert persoon.betrokkenheden[0].relatie.betrokkenheden.size() == 3

        assert persoon.betrokkenheden[0].relatie.ouderBetrokkenheden.find {
            OuderHisVolledigImpl it -> it.ouderOuderschapHistorie.actueleRecord == null
        }.ouderOuderschapHistorie[0].datumTijdVerval != null
    }

    @Test
    void "definieer een geboorte zonder ouders, zou wel een familierechtelijke betrekking moeten opleveren"() {
        String dsl = """

kind = Persoon.uitGebeurtenissen {
    geboorte() {
        op '1998/10/22' te 'Monster' gemeente 'Westland'
        ouders()
    }
}
slaOp kind
"""
        // act
        def persoon = new PersoonDSLExecutor().execute(dsl).kind as PersoonHisVolledigImpl

        // assert
        assert persoon.betrokkenheden[0].relatie.betrokkenheden.size() == 1

    }
}
