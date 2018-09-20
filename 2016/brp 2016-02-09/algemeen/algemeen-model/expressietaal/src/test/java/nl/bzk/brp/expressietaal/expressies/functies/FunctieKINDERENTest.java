/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.expressies.functies;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.expressietaal.Context;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.ExpressieType;
import nl.bzk.brp.expressietaal.expressies.LijstExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.BrpObjectExpressie;
import nl.bzk.brp.expressietaal.parser.BRPExpressies;
import nl.bzk.brp.expressietaal.parser.ParserResultaat;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.junit.Test;

public class FunctieKINDERENTest {

    @Test
    public void testKinderen() {
        final PersoonHisVolledigImpl testPersoon1 = TestPersoonJohnnyJordaan.maak();
        final PersoonView testPersoonView1 = new PersoonView(testPersoon1);

        final Context context = new Context();
        context.definieer("persoon", new BrpObjectExpressie(testPersoonView1, ExpressieType.PERSOON));
        final ParserResultaat geparsdeExpressie = BRPExpressies.parse("KINDEREN(persoon)");
        final Expressie expressieResultaat = BRPExpressies.evalueer(geparsdeExpressie.getExpressie(), context);
        assertTrue(expressieResultaat.isLijstExpressie());

        final LijstExpressie lijst = (LijstExpressie) expressieResultaat;
        assertEquals(2, lijst.getElementen().size());

        final List<Integer> actual = new ArrayList<>();
        actual.add(((PersoonView) ((BrpObjectExpressie) lijst.getElement(0)).getBrpObject()).getID());
        actual.add(((PersoonView) ((BrpObjectExpressie) lijst.getElement(1)).getBrpObject()).getID());

        assertThat(actual, containsInAnyOrder(TestPersoonJohnnyJordaan.ANITA_ID, TestPersoonJohnnyJordaan.PRISCILLA_ID));
    }

}
