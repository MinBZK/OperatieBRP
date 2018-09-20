package nl.bzk.brp.datataal.handlers.persoon.afstamming

import nl.bzk.brp.datataal.AbstractDSLIntegratieTest
import nl.bzk.brp.datataal.execution.PersoonDSLExecutor
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl
import org.junit.Test

class AdoptieHandlerTest extends AbstractDSLIntegratieTest {

    @Test
    void doetAdoptie() {
        String dsl = """
kind = Persoon.uitGebeurtenissen {
    geboorte(aanvang: 20150102) {
        namen {
            voornamen 'Piet'
        }
        ouders(moeder:null)
    }
}

def adoptieMoeder = Persoon.uitGebeurtenissen {
    geboorte(partij: 36101, aanvang: 19781023, toelichting: 'moeder') {
        op '1978/10/22' te 'Monster' gemeente 'Westland'
        identificatienummers bsn: 1, anummer: 1
    }
}

Persoon.nieuweGebeurtenissenVoor(kind) {
    geadopteerd(aanvang: 20150110) {
        ouders(moeder: adoptieMoeder)
        namen {
            voornamen 'Jan', 'Klaas', 'Henk'
        }
        nationaliteiten 'Turkse'
    }
}
"""
        // act
        def persoon = new PersoonDSLExecutor().execute(dsl).kind as PersoonHisVolledigImpl

        // assert
        assert persoon != null
        assert persoon.kindBetrokkenheid.relatie.ouderBetrokkenheden.size() == 2
        assert persoon.kindBetrokkenheid.relatie.ouderBetrokkenheden.findAll { !it.betrokkenheidHistorie.heeftActueelRecord() }.size() == 1
        assert persoon.nationaliteiten.size() == 2
    }
}
