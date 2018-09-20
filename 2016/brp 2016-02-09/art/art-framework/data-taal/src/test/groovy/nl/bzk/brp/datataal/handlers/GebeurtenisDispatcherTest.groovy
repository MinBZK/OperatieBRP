package nl.bzk.brp.datataal.handlers

import nl.bzk.brp.datataal.AbstractDSLIntegratieTest
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder
import org.junit.Test

/**
 *
 */
class GebeurtenisDispatcherTest extends AbstractDSLIntegratieTest{

    @Test
    void kanGeboorteStarten() {
        def builder = new GebeurtenisDispatcher(new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE))

        def acties = builder.geboorte([partij: 36101], {})

        assert acties.size() == 1
    }

    @Test
    void kanGeboorteStartenMetPartijnaam() {
        def builder = new GebeurtenisDispatcher(new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE))

        def acties = builder.geboorte([partij: 'Gemeente Rotterdam'], {})

        assert acties.size() == 1
    }

    @Test(expected = IllegalArgumentException)
    void "dient een illegalArgumentException te hebben indien aanvang niet gezet is op de gebeurtenis"() {
        new GebeurtenisDispatcher(null).curatele([:], {})
    }
}
