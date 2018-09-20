/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import nl.bzk.brp.model.hisvolledig.impl.kern.KindHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.OuderHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.BetrokkenheidComparator;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.BetrokkenheidHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.KindHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.OuderHisVolledigView;
import nl.bzk.brp.util.hisvolledig.kern.KindHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.OuderHisVolledigImplBuilder;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


public class BetrokkenheidComparatorTest {

    final BetrokkenheidComparator betrokkenheidComparator = new BetrokkenheidComparator();

    @Test
    public void testCompareOpRol() {
        final OuderHisVolledigImplBuilder ouderHisVolledigImplBuilder = new OuderHisVolledigImplBuilder(null, null);
        final OuderHisVolledigImpl ouderHisVolledig = ouderHisVolledigImplBuilder.build();
        final KindHisVolledigImplBuilder kindHisVolledigImplBuilder = new KindHisVolledigImplBuilder(null, null);
        final KindHisVolledigImpl kindHisVolledig = kindHisVolledigImplBuilder.build();

        final BetrokkenheidHisVolledigView betrokkenheidHisVolledigView1 = new KindHisVolledigView(kindHisVolledig, null);
        final BetrokkenheidHisVolledigView betrokkenheidHisVolledigView2 = new OuderHisVolledigView(ouderHisVolledig, null);

        int resultaat = betrokkenheidComparator.compare(betrokkenheidHisVolledigView1, betrokkenheidHisVolledigView2);

        assertThat(resultaat, is(-4));
    }

    @Test
    public void testCompareOpPersoonId() {
        final PersoonHisVolledigImpl ouder1 = TestPersoonJohnnyJordaan.maak();
        ReflectionTestUtils.setField(ouder1, "iD", 1);
        final PersoonHisVolledigImpl ouder2 = TestPersoonJohnnyJordaan.maak();
        ReflectionTestUtils.setField(ouder2, "iD", 2);

        final OuderHisVolledigImplBuilder ouderHisVolledigImplBuilder1 = new OuderHisVolledigImplBuilder(null, ouder1);
        final OuderHisVolledigImpl ouderHisVolledig1 = ouderHisVolledigImplBuilder1.build();
        final BetrokkenheidHisVolledigView betrokkenheidHisVolledigView1 = new OuderHisVolledigView(ouderHisVolledig1, null);

        final OuderHisVolledigImplBuilder ouderHisVolledigImplBuilder2 = new OuderHisVolledigImplBuilder(null, ouder2);
        final OuderHisVolledigImpl ouderHisVolledig2 = ouderHisVolledigImplBuilder2.build();
        final BetrokkenheidHisVolledigView betrokkenheidHisVolledigView2 = new OuderHisVolledigView(ouderHisVolledig2, null);

        int resultaat = betrokkenheidComparator.compare(betrokkenheidHisVolledigView1, betrokkenheidHisVolledigView2);

        assertThat(resultaat, is(-1));
    }

    @Test
    public void testCompareOpBetrokkenheidId() {
        final PersoonHisVolledigImpl ouder1 = TestPersoonJohnnyJordaan.maak();
        ReflectionTestUtils.setField(ouder1, "iD", null);
        final PersoonHisVolledigImpl ouder2 = TestPersoonJohnnyJordaan.maak();
        ReflectionTestUtils.setField(ouder2, "iD", 2);

        final OuderHisVolledigImplBuilder ouderHisVolledigImplBuilder1 = new OuderHisVolledigImplBuilder(null, ouder1);
        final OuderHisVolledigImpl ouderHisVolledig1 = ouderHisVolledigImplBuilder1.build();
        ReflectionTestUtils.setField(ouderHisVolledig1, "iD", 1);
        final BetrokkenheidHisVolledigView betrokkenheidHisVolledigView1 = new OuderHisVolledigView(ouderHisVolledig1, null);

        final OuderHisVolledigImplBuilder ouderHisVolledigImplBuilder2 = new OuderHisVolledigImplBuilder(null, ouder2);
        final OuderHisVolledigImpl ouderHisVolledig2 = ouderHisVolledigImplBuilder2.build();
        ReflectionTestUtils.setField(ouderHisVolledig2, "iD", 2);
        final BetrokkenheidHisVolledigView betrokkenheidHisVolledigView2 = new OuderHisVolledigView(ouderHisVolledig2, null);

        int resultaat = betrokkenheidComparator.compare(betrokkenheidHisVolledigView1, betrokkenheidHisVolledigView2);

        assertThat(resultaat, is(-1));
    }

    @Test
    public void testCompareZijnGelijk() {
        final PersoonHisVolledigImpl ouder1 = TestPersoonJohnnyJordaan.maak();
        ReflectionTestUtils.setField(ouder1, "iD", null);
        final PersoonHisVolledigImpl ouder2 = TestPersoonJohnnyJordaan.maak();
        ReflectionTestUtils.setField(ouder2, "iD", 2);

        final OuderHisVolledigImplBuilder ouderHisVolledigImplBuilder1 = new OuderHisVolledigImplBuilder(null, ouder1);
        final OuderHisVolledigImpl ouderHisVolledig1 = ouderHisVolledigImplBuilder1.build();
        ReflectionTestUtils.setField(ouderHisVolledig1, "iD", 1);
        final BetrokkenheidHisVolledigView betrokkenheidHisVolledigView1 = new OuderHisVolledigView(ouderHisVolledig1, null);

        final OuderHisVolledigImplBuilder ouderHisVolledigImplBuilder2 = new OuderHisVolledigImplBuilder(null, ouder2);
        final OuderHisVolledigImpl ouderHisVolledig2 = ouderHisVolledigImplBuilder2.build();
        ReflectionTestUtils.setField(ouderHisVolledig2, "iD", 1);
        final BetrokkenheidHisVolledigView betrokkenheidHisVolledigView2 = new OuderHisVolledigView(ouderHisVolledig2, null);

        int resultaat = betrokkenheidComparator.compare(betrokkenheidHisVolledigView1, betrokkenheidHisVolledigView2);

        assertThat(resultaat, is(0));
    }

}
