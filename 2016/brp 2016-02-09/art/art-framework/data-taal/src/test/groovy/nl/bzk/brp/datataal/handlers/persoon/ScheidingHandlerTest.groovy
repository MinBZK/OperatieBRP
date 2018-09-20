package nl.bzk.brp.datataal.handlers.persoon

import nl.bzk.brp.datataal.dataaccess.SpringBeanProvider
import nl.bzk.brp.datataal.execution.PersoonDSLExecutor
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl
import org.junit.Test
import org.springframework.context.support.ClassPathXmlApplicationContext

class ScheidingHandlerTest {

    @Test
    void "Scheiding kan plaatsvinden"() {
        String dsl = """
def partner = Persoon.uitGebeurtenissen { geboorte() { } }

persoon = Persoon.uitGebeurtenissen {
    geboorte() { }

    huwelijk() {
        op '2010/03/09' te 'Naaldwijk' gemeente 'Westland'
        met partner
    }
    scheiding() {
        van partner
        op '2012/01/01' te 'Hoek van Holland' gemeente 'Rotterdam'
    }
}
"""

        SpringBeanProvider.setContext(new ClassPathXmlApplicationContext('classpath:datataal-context.xml'))
        def persoon = new PersoonDSLExecutor().execute(dsl).persoon as PersoonHisVolledigImpl

        assert persoon.partnerBetrokkenheden.size() == 1
        assert persoon.partnerBetrokkenheden[0].relatie.relatieHistorie.actueleRecord.datumEinde.waarde == 20120101
        assert persoon.partnerBetrokkenheden[0].relatie.relatieHistorie.actueleRecord.redenEinde.waarde.code.waarde == 'S'

    }
}
