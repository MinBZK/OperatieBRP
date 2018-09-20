/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.expressies;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.parser.BRPExpressies;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.junit.Test;

/**
 *
 */
public class GrootGetalEvaluatieTest {

    @Test
    public void evalueertLongNummersCorrect() {
        final PersoonHisVolledig persoon = TestPersoonJohnnyJordaan.maak();
        final PersoonView view = new PersoonView(persoon);

        final Expressie resultaat = BRPExpressies.evalueer("persoon.identificatienummers.administratienummer", view);

        final Expressie anummerExpressie = resultaat.getElementen().iterator().next();

        assertEquals(view.getIdentificatienummers().getAdministratienummer().getWaarde(),
                     Long.valueOf(anummerExpressie.alsLong()));
    }

    @Test
    public void rekenFunctiesMetGrootGetal() {
        final PersoonHisVolledig persoon = TestPersoonJohnnyJordaan.maak();

        Expressie resultaat = BRPExpressies.evalueer("3333333333 + 1", persoon);
        assertEquals(3333333334L, resultaat.alsLong());

        resultaat = BRPExpressies.evalueer("1 + 3333333333", persoon);
        assertEquals(3333333334L, resultaat.alsLong());

        resultaat = BRPExpressies.evalueer("3333333333 - 1", persoon);
        assertEquals(3333333332L, resultaat.alsLong());

        resultaat = BRPExpressies.evalueer("-33", persoon);
        assertEquals(-33, resultaat.alsInteger());

        resultaat = BRPExpressies.evalueer("-3333333333", persoon);
        assertEquals(-3333333333L, resultaat.alsLong());
    }

    @Test
    public void booleanFunctiesMetGetalEnGrootGetal() {
        final PersoonHisVolledig persoon = TestPersoonJohnnyJordaan.maak();

        Expressie resultaat = BRPExpressies.evalueer("3333333333 < 1", persoon);
        assertEquals("ONWAAR", resultaat.alsString());

        resultaat = BRPExpressies.evalueer("1 < 3333333333", persoon);
        assertEquals(true, resultaat.alsBoolean());
    }

    @Test
    public void booleanFunctiesMetAlleenGrootGetal() {
        final PersoonHisVolledig persoon = TestPersoonJohnnyJordaan.maak();

        Expressie resultaat = BRPExpressies.evalueer("3333333334 < 3333333333", persoon);
        assertFalse(resultaat instanceof FoutExpressie);
        assertEquals("ONWAAR", resultaat.alsString());

        resultaat = BRPExpressies.evalueer("3333333334 >= 3333333333", persoon);
        assertEquals(true, resultaat.alsBoolean());

        resultaat = BRPExpressies.evalueer("3333333333 = 3333333333", persoon);
        assertFalse(resultaat instanceof FoutExpressie);
        assertEquals("WAAR", resultaat.alsString());
    }

    @Test
    public void rekenFunctiesMetDatum() {
        final PersoonHisVolledig persoon = TestPersoonJohnnyJordaan.maak();

        Expressie resultaat = BRPExpressies.evalueer("2014/10/12 + 1", persoon);
        assertFalse(resultaat instanceof FoutExpressie);

        resultaat = BRPExpressies.evalueer("2014/10/12 + 4444444444", persoon);
        assertTrue(resultaat instanceof FoutExpressie);
    }
}
