package nl.bzk.brp.datataal.handlers.persoon.afstamming

import nl.bzk.brp.datataal.AbstractDSLIntegratieTest
import nl.bzk.brp.datataal.execution.PersoonDSLExecutor
import nl.bzk.brp.datataal.model.GebeurtenisAttributen
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder
import org.junit.Before
import org.junit.Test

class ErkenningNaGeboorteHandlerTest extends AbstractDSLIntegratieTest {


    @Test
    void 'erkenning kan gedaan worden'() {
        def dsl = """
def truus = Persoon.metId(1002)
def piet  = Persoon.metId(1001)

persoon = Persoon.uitGebeurtenissen {
    geboorte(partij: 36101, aanvang: 19781023, toelichting: '1e kind') {
        op '1978/10/22' te 'Monster' gemeente 'Westland'
        namen {
            voornamen 'Petrus', 'Matheus'
            geslachtsnaam 'Smith'
        }
        identificatienummers bsn: 410217360, anummer: 2489643026
        ouders(moeder:truus)
    }

    erkend(aanvang: 19781030) {
        door piet
    }
}
persoon = slaOp(persoon)
"""

        // act
        def persoon = new PersoonDSLExecutor().execute(dsl).persoon as PersoonHisVolledigImpl

        // assert
        assert persoon != null
        assert persoon.kindBetrokkenheid.relatie.ouderBetrokkenheden.size() == 2
        assert persoon.kindBetrokkenheid.relatie.ouderBetrokkenheden.findAll { it.betrokkenheidHistorie.heeftActueelRecord()}.size() == 2
    }


    @Test
    void 'erkenning kan gecorrigeerd worden'() {
        def dsl = """
def truus = Persoon.metId(1002)
def piet  = Persoon.metId(1001)

persoon = Persoon.uitGebeurtenissen {
    geboorte(partij: 36101, aanvang: 19781023, toelichting: '1e kind') {
        op '1978/10/22' te 'Monster' gemeente 'Westland'
        namen {
            voornamen 'Petrus', 'Matheus'
            geslachtsnaam 'Smith'
        }
        identificatienummers bsn: 410217360, anummer: 2489643026
        ouders(moeder:truus)
    }

    erkend(aanvang: 19781030) {
        door piet
    }

    erkend(aanvang: 19781023) {
        door piet
    }
}
persoon = slaOp(persoon)
"""

        // act
        def persoon = new PersoonDSLExecutor().execute(dsl).persoon as PersoonHisVolledigImpl

        // assert
        assert persoon != null
        assert persoon.kindBetrokkenheid.relatie.ouderBetrokkenheden.size() == 3
        assert persoon.kindBetrokkenheid.relatie.ouderBetrokkenheden.findAll { (!it.betrokkenheidHistorie.heeftActueelRecord())}.size() == 1
        assert persoon.kindBetrokkenheid.relatie.ouderBetrokkenheden.findAll { it.betrokkenheidHistorie.heeftActueelRecord()}.size() == 2
    }

    @Test
    void 'erkenning bij geboorte kan gecorrigeerd worden'() {
        def dsl = """
def truus = Persoon.metId(1002)
def piet  = Persoon.metId(1001)

persoon = Persoon.uitGebeurtenissen {
    geboorte(partij: 36101, aanvang: 19781023, toelichting: '1e kind') {
        op '1978/10/22' te 'Monster' gemeente 'Westland'
        namen {
            voornamen 'Petrus', 'Matheus'
            geslachtsnaam 'Smith'
        }
        identificatienummers bsn: 410217360, anummer: 2489643026
        ouders(moeder:truus)
        erkendDoor(piet)
    }
    erkend(aanvang: 19781022) {
        door piet
    }
}
persoon = slaOp(persoon)
"""

        // act
        def persoon = new PersoonDSLExecutor().execute(dsl).persoon as PersoonHisVolledigImpl

        // assert
        assert persoon != null
        assert persoon.kindBetrokkenheid.relatie.ouderBetrokkenheden.size() == 3
        assert persoon.kindBetrokkenheid.relatie.ouderBetrokkenheden.findAll { (!it.betrokkenheidHistorie.heeftActueelRecord())}.size() == 1
        assert persoon.kindBetrokkenheid.relatie.ouderBetrokkenheden.findAll { it.betrokkenheidHistorie.heeftActueelRecord()}.size() == 2
    }

    @Test(expected = IllegalStateException)
    void 'erkenning kan alleen als er 1 ouder is'() {
        def dsl = """
def truus = Persoon.metId(1002)
def piet  = Persoon.metId(1001)

persoon = Persoon.uitGebeurtenissen {
    geboorte(partij: 36101, aanvang: 19781023, toelichting: '1e kind') {
        op '1978/10/22' te 'Monster' gemeente 'Westland'
        namen {
            voornamen 'Petrus', 'Matheus'
            geslachtsnaam 'Smith'
        }
        identificatienummers bsn: 410217360, anummer: 2489643026
        ouders(moeder:truus, vader:null)
    }

    erkend(aanvang: 19781030) {
        door piet
    }
}
persoon = slaOp(persoon)
"""

        // act
        new PersoonDSLExecutor().execute(dsl)
    }
}

