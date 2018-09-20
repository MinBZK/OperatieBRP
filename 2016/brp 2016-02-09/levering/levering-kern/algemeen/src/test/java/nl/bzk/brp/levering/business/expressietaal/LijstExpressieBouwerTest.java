/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.expressietaal;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.levering.excepties.ExpressieExceptie;
import org.junit.Test;

public class LijstExpressieBouwerTest {

    private static final String[] TEST_EXPRESSIES = {"$persoon.bsn", "$geboorte.datum", "$geboorte.datum_tijd_registratie", };
    private static final String CURLY_START = "{";
    private static final String CURLY_END = "}";
    private static final String COMMA = ",";

    private LijstExpressieBouwer lijstExpressieBouwer = new LijstExpressieBouwer();

    @Test
    public void testMet1ExpressieDeel() throws ExpressieExceptie {
        lijstExpressieBouwer.voegExpressieDeelToe(TEST_EXPRESSIES[0]);

        final String resultaatAlsString = lijstExpressieBouwer.geefTotaleExpressie();
        final Expressie resultaatAlsExpressie = lijstExpressieBouwer.geefTotaleGeparsdeExpressie();

        assertEquals(CURLY_START + TEST_EXPRESSIES[0] + CURLY_END, resultaatAlsString);
        assertEquals(1, resultaatAlsExpressie.aantalElementen());
    }

    @Test
    public final void testMetMeerdereExpressieDelen() throws ExpressieExceptie {
        lijstExpressieBouwer.voegExpressieDelenToe(Arrays.asList(TEST_EXPRESSIES));

        final String resultaatAlsString = lijstExpressieBouwer.geefTotaleExpressie();
        final Expressie resultaatAlsExpressie = lijstExpressieBouwer.geefTotaleGeparsdeExpressie();

        assertEquals(CURLY_START + TEST_EXPRESSIES[0] + COMMA + TEST_EXPRESSIES[1] + COMMA + TEST_EXPRESSIES[2] + CURLY_END, resultaatAlsString);
        assertEquals(3, resultaatAlsExpressie.aantalElementen());
    }

    @Test
    public final void testLegeLijst() throws ExpressieExceptie {
        final String resultaatAlsString = lijstExpressieBouwer.geefTotaleExpressie();
        final Expressie resultaatAlsExpressie = lijstExpressieBouwer.geefTotaleGeparsdeExpressie();

        assertEquals("{}", resultaatAlsString);
        assertEquals(0, resultaatAlsExpressie.aantalElementen());
    }

    @Test(expected = ExpressieExceptie.class)
    public final void testMetOnparsebareExpressie() throws ExpressieExceptie {
        lijstExpressieBouwer.voegExpressieDeelToe("persoon.paardenstaart");
        lijstExpressieBouwer.geefTotaleGeparsdeExpressie();
    }

}
