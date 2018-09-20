package nl.bzk.brp.datataal.handlers.persoon

import nl.bzk.brp.datataal.AbstractDSLIntegratieTest
import nl.bzk.brp.datataal.execution.PersoonDSLExecutor
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl
import org.junit.Test

class NietigVerklaringHuwelijkHandlerTest extends AbstractDSLIntegratieTest  {

    @Test(expected = IllegalArgumentException.class)
    void "foutmelding indien geen huwlijk"() {
        String dsl = """
persoon = Persoon.uitGebeurtenissen {
    geboorte() { }

    nietigVerklaringHuwelijk() {
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
    void "huwelijk wordt nietig verklaard"() {
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

    nietigVerklaringHuwelijk() {
    }
}
"""
        // act
        def persoon = new PersoonDSLExecutor().execute(dsl).persoon as PersoonHisVolledigImpl

        // assert
        assert persoon != null
        assert persoon.partnerBetrokkenheden.size() == 1
        assert !persoon.partnerBetrokkenheden[0].relatie.relatieHistorie.heeftActueelRecord()
        assert persoon.partnerBetrokkenheden[0].relatie.relatieHistorie[0].datumAanvang.waarde == 20100309

        // Niet gespecificeerd:
        assert persoon.persoonNaamgebruikHistorie.size() == 2
        assert persoon.persoonNaamgebruikHistorie.actueleRecord.geslachtsnaamstamNaamgebruik.waarde == 'Jansen Vries'
    }
}
