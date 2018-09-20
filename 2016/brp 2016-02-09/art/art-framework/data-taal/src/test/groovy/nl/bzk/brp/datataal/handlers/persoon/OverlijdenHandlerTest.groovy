package nl.bzk.brp.datataal.handlers.persoon

import nl.bzk.brp.datataal.AbstractDSLIntegratieTest
import nl.bzk.brp.datataal.execution.PersoonDSLExecutor
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl
import org.junit.Test

class OverlijdenHandlerTest extends AbstractDSLIntegratieTest {

    @Test
    void "overlijden wordt correct vastgelegd"() {
        String dsl = """
def partner = Persoon.uitGebeurtenissen { geboorte() { } }

persoon = Persoon.uitGebeurtenissen {
    geboorte() { }

    huwelijk() {
        op '2010/03/09' te 'Naaldwijk' gemeente 'Westland'
        met partner
    }
    overlijden() {
        op '2012/01/01' te 'Hoek van Holland' gemeente 'Rotterdam'
    }
}
"""
        def persoon = new PersoonDSLExecutor().execute(dsl).persoon as PersoonHisVolledigImpl
        assert persoon.partnerBetrokkenheden.size() == 1
        assert persoon.partnerBetrokkenheden[0].relatie.relatieHistorie.actueleRecord.datumAanvang.waarde == 20100309
        assert persoon.partnerBetrokkenheden[0].relatie.relatieHistorie.actueleRecord.datumEinde.waarde == 20120101
        assert persoon.partnerBetrokkenheden[0].relatie.relatieHistorie.actueleRecord.redenEinde.waarde.code.waarde == 'O'

        assert persoon.persoonOverlijdenHistorie.size() == 1
        assert persoon.persoonOverlijdenHistorie.actueleRecord.datumOverlijden.waarde == 20120101

        assert persoon.persoonOverlijdenHistorie.actueleRecord.gemeenteOverlijden.waarde == persoon.partnerBetrokkenheden[0].relatie.relatieHistorie.actueleRecord.gemeenteEinde.waarde
    }
}
