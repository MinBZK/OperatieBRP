package nl.bzk.brp.datataal.handlers.persoon

import nl.bzk.brp.datataal.AbstractDSLIntegratieTest
import nl.bzk.brp.datataal.execution.PersoonDSLExecutor
import nl.bzk.brp.datataal.model.GebeurtenisAttributen
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder
import org.junit.Test

class HuwelijkHandlerTest extends AbstractDSLIntegratieTest {

    @Test
    void "huwelijk wordt correct geregistreerd"() {
        String dsl = """
def partner = Persoon.uitGebeurtenissen {
  geboorte() {
    namen {
      geslachtsnaam 'Jansen'
      voornamen 'Piet'
    }
  }
}

persoon = Persoon.uitGebeurtenissen {
    geboorte() {
        namen {
            geslachtsnaam 'Vries'
            voornamen 'Marie'
        }
    }

    huwelijk() {
        op '2010/03/09' te "Naaldwijk" gemeente "Westland"
        met partner
        naamgebruik 'PARTNER, EIGEN'
    }
}
"""
        // act
        def persoon = new PersoonDSLExecutor().execute(dsl).persoon as PersoonHisVolledigImpl

        // assert
        assert persoon != null
        assert persoon.partnerBetrokkenheden.size() == 1
        assert persoon.partnerBetrokkenheden[0].relatie.relatieHistorie.actueleRecord.datumAanvang.waarde == 20100309

        assert persoon.persoonNaamgebruikHistorie.size() == 2
        assert persoon.persoonNaamgebruikHistorie.actueleRecord.geslachtsnaamstamNaamgebruik.waarde == 'Jansen Vries'
    }

    @Test(expected = IllegalStateException)
    void "exceptie, als eerst partner wordt beschreven"() {
        String dsl = """
def partner = Persoon.uitGebeurtenissen { geboorte() { } }

persoon = Persoon.uitGebeurtenissen {
    geboorte() { }

    huwelijk() {
        met partner
        op '2010/03/09' te 'Naaldwijk' gemeente 'Westland'
    }
}
"""
        new PersoonDSLExecutor().execute(dsl).persoon as PersoonHisVolledigImpl
    }

    @Test
    void huwelijkInBuitenland() {
        def builder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
        def huwelijkHandler = new HuwelijkHandler(new GebeurtenisAttributen(), builder)

        def te = huwelijkHandler.op(20101010).te
        def land = te('Groteplaats').land
        land('Marokko')

        huwelijkHandler.met(new PersoonHisVolledigImplBuilder(SoortPersoon.DUMMY).build())

        // act
        def persoon = builder.build()

        // assert
        assert persoon != null
        assert persoon.partnerBetrokkenheden.size() == 1
        assert persoon.partnerBetrokkenheden[0].relatie.relatieHistorie.actueleRecord.landGebiedAanvang.waarde.naam.waarde == 'Marokko'
        assert persoon.partnerBetrokkenheden[0].relatie.relatieHistorie.actueleRecord.buitenlandsePlaatsAanvang.waarde == 'Groteplaats'
        assert persoon.partnerBetrokkenheden[0].relatie.relatieHistorie.actueleRecord.datumAanvang.waarde == 20101010
    }
}
