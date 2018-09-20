package nl.bzk.brp.datataal.handlers.persoon

import nl.bzk.brp.datataal.AbstractDSLIntegratieTest
import nl.bzk.brp.datataal.execution.PersoonDSLExecutor
import nl.bzk.brp.model.hisvolledig.impl.kern.BetrokkenheidHisVolledigImpl
import nl.bzk.brp.model.hisvolledig.impl.kern.OuderHisVolledigImpl
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl
import org.junit.Test

class VestigingInNederlandHandlerTest extends AbstractDSLIntegratieTest  {

    @Test
    void kanPersoonInNederlandVestigen() {
        String dsl = '''
ni = Persoon.nietIngeschrevene(aanvang: 19670401, registratieDatum: 19670402) {
    geboorte {
        op '1967/04/01' te 'Barcelona' land 'Spanje'
    }
    samengesteldeNaam(
        stam: 'Montoya',
        voornamen: 'Inigo'
    )
    geslacht('MAN')
}
persoon = Persoon.ingeschreveneVan(ni) {
    vestigingInNederland(aanvang: 20150420, partij: 'Gemeente Rotterdam', toelichting: 'import') {
        identificatienummers bsn: 123456789, anummer: 98765431
    }
}
'''

        def result = new PersoonDSLExecutor().execute(dsl)
        def persoon = result.persoon as PersoonHisVolledigImpl
        def ni = result.ni as PersoonHisVolledigImpl

        assert ni.persoonAfgeleidAdministratiefHistorie.size() == 1

        assert persoon.persoonAfgeleidAdministratiefHistorie.size() == 1
    }

    @Test
    void "niet ingeschrevene wordt vader van een ingeschrevene" () {
        String dsl =  """
def moeder_Jimmy   = Persoon.uitGebeurtenissen {
  geboorte() {
    namen {
      geslachtsnaam 'Mama'
      voornamen 'mama'
    }
  }
}
moeder_Jimmy = slaOp(moeder_Jimmy)

def vader_Jimmy_NI = Persoon.nietIngeschrevene(aanvang: 19720101, registratieDatum: 19720101) {
  geboorte {
    op '1972/01/01' te 'Kyoto' land 'Japan'
  }
  samengesteldeNaam(
    stam: 'Kenshin',
    voornamen: 'Rourouni'
  )
  geslacht('MAN')
}
vader_Jimmy_NI = slaOp(vader_Jimmy_NI)

def Jimmy = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 19800203, toelichting: '1e kind') {
        op '1980/02/01' te 'Delft' gemeente 'Delft'
        geslacht 'MAN'
        namen {
            voornamen 'Jimmy'
            geslachtsnaam 'Scott'
        }
        ouders moeder: moeder_Jimmy, vader: vader_Jimmy_NI
        identificatienummers bsn: 290972954, anummer: 2000603257
    }
 }
Jimmy = slaOp(Jimmy)

def vader_Jimmy_I = Persoon.ingeschreveneVan(vader_Jimmy_NI)     {
  vestigingInNederland(partij: 34401, aanvang: 19800403, toelichting: 'Verkrijging NL Nationaliteit') {
            identificatienummers(bsn: 290650136, anummer: 2000603925)
              nationaliteiten 'Japanse'
              ouderVan(Jimmy)
              partnerVan(moeder_Jimmy)
  }
}
vader = slaOp(vader_Jimmy_I)
"""
        def result = new PersoonDSLExecutor().execute(dsl)
        def vader = result.vader as PersoonHisVolledigImpl

        assert vader.ouderBetrokkenheden.size() == 1

        vader.ouderBetrokkenheden.each { OuderHisVolledigImpl ouder ->
            assert ouder.relatie.betrokkenheden.size() == 4
            ouder.relatie.betrokkenheden.findAll {
                it.persoon.soort.waarde.name() == "NIET_INGESCHREVENE"
            }.each { BetrokkenheidHisVolledigImpl betrokkenheid ->
                assert betrokkenheid.betrokkenheidHistorie.actueleRecord == null
            }
        }
    }


}
