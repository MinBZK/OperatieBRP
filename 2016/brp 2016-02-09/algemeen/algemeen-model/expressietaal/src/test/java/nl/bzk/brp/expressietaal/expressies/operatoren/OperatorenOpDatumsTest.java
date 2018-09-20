/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.expressies.operatoren;

import static nl.bzk.brp.expressietaal.expressies.ExpressieEvaluator.ONBEKEND;
import static nl.bzk.brp.expressietaal.expressies.ExpressieEvaluator.ONWAAR;
import static nl.bzk.brp.expressietaal.expressies.ExpressieEvaluator.WAAR;
import static nl.bzk.brp.expressietaal.expressies.ExpressieEvaluator.testEvaluatie;

import org.junit.Test;

public class OperatorenOpDatumsTest {
    @Test
    public void testDatumVergelijkingen() {
        // bekende datum
        testEvaluatie("1970/12/10 < 1980/JUN/20", WAAR);
        testEvaluatie("1970/12/10 > 1980/JUN/20", ONWAAR);
        testEvaluatie("1970/12/10 <= 1980/JUN/20", WAAR);
        testEvaluatie("1990/JAN/01 > 1989/DEC/31", WAAR);
        testEvaluatie("1990/JAN/01 >= 1989/DEC/31", WAAR);
        testEvaluatie("1975/AUG/20 <> 1980/AUG/20", WAAR);
        testEvaluatie("1975/AUG/20 = 1975/08/20", WAAR);
        testEvaluatie("1975/AUG/20 <= 1975/08/20", WAAR);
        testEvaluatie("1975/AUG/20 < 1975/08/20", ONWAAR);
        testEvaluatie("1975/AUG/20 <= 1975/08/25", WAAR);

        // bekende datum met datumtijd
        testEvaluatie("1970/12/10 < 1980/JUN/20/12/00/00", WAAR);
        testEvaluatie("1970/12/10 > 1980/JUN/20/12/00/00", ONWAAR);
        testEvaluatie("1970/12/10 <= 1980/JUN/20/12/00/00", WAAR);
        testEvaluatie("1990/JAN/01 > 1989/DEC/31/12/00/00", WAAR);
        testEvaluatie("1990/JAN/01 >= 1989/DEC/31/12/00/00", WAAR);
        testEvaluatie("1975/AUG/20 <> 1980/AUG/20/12/00/00", WAAR);
        testEvaluatie("1975/AUG/20 = 1975/08/20/12/00/00", WAAR);
        testEvaluatie("1975/AUG/20 <= 1975/08/20/12/00/00", WAAR);
        testEvaluatie("1975/AUG/20 < 1975/08/20/12/00/00", ONWAAR);
        testEvaluatie("1975/AUG/20 <= 1975/08/25/12/00/00", WAAR);

        // (Deels)onbekende datums
        testEvaluatie("1975/AUG/20 = 1975/AUG/?", ONBEKEND);
        testEvaluatie("1990/JAN/01 = 1990/JAN/?", ONBEKEND);
        testEvaluatie("1990/JAN/15 <> 1990/JAN/?", ONBEKEND);

        testEvaluatie("1990/JAN/15 > 1990/JAN/?", ONBEKEND);
        testEvaluatie("1990/JAN/? > 1990/?/?", ONBEKEND);
        testEvaluatie("1990/JAN/? > 1970/?/?", WAAR);
        testEvaluatie("1990/JAN/01 < 1990/JAN/?", ONBEKEND);
        testEvaluatie("1990/JAN/01 < 1990/FEB/?", WAAR);
        testEvaluatie("1990/JAN/01 < ?/?/?", ONBEKEND);

        // praktijk gevallen
        testEvaluatie("1997/11/? < 1999/09/01", WAAR);
        testEvaluatie("1997/11/? < 1999/11/01", WAAR);
        testEvaluatie("1997/11/? < 1999/12/01", WAAR);

        testEvaluatie("1997/11/? > 1999/09/01", ONWAAR);
        testEvaluatie("1997/11/? > 1999/11/01", ONWAAR);
        testEvaluatie("1997/11/? > 1999/12/01", ONWAAR);
    }

    @Test
    public void testDatumWildcards() {
        testEvaluatie("1990/JAN/01 %= 1990/JAN/01", WAAR);
        testEvaluatie("1990/JAN/01 %= 1990/JAN/?", WAAR);
        testEvaluatie("1990/JAN/01 %= 1990/?/?", WAAR);
        testEvaluatie("1990/JAN/? %= 1990/JAN/?", WAAR);
        testEvaluatie("1990/JAN/? %= 1990/JAN/01", ONWAAR);
        testEvaluatie("1990/?/? %= 1990/JAN/01", ONWAAR);
        testEvaluatie("1990/?/? %= 1990/JAN/?", ONWAAR);
        testEvaluatie("1990/JAN/? %= 1990/FEB/?", ONWAAR);
        testEvaluatie("1990/JAN/? %= 1980/JAN/?", ONWAAR);
    }
}
