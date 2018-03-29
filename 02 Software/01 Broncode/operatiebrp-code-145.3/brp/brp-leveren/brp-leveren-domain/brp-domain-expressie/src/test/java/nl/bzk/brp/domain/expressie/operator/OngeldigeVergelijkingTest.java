/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.operator;

import java.util.Arrays;
import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.expressie.util.TestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 */
@RunWith(Parameterized.class)
public class OngeldigeVergelijkingTest {

    private final String expressie;
    private final String foutmelding;

    public OngeldigeVergelijkingTest(final String expressie, final String foutmelding) {
        this.expressie = expressie;
        this.foutmelding = foutmelding;
    }

    @Parameterized.Parameters(name = "{index}: {0} = {1}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"{} - {}", "De berekening 'Lijst - Lijst' wordt niet ondersteund"},
                {"2 > TRUE", "Numerieke expressie verwacht"},
                {"TRUE > 2", "Boolean expressie verwacht"},
                {"10 > 1999/06/11", "Numerieke expressie verwacht"},
                {"1999/06/11 < 11", "Datumexpressie verwacht"},
                {"1999/06/11 < TRUE", "Datumexpressie verwacht"},


//tav robuustheid is het beter om geen foutmelding te gooien bij null waarden.
//            { "{} E= {}", "Ongeldig rechteroperand in expressie E= : Operand moet een literal zijn" },
//            { "{} E= NULL", "Ongeldig rechteroperand in expressie E= : Operand mag niet NULL zijn" },
//            { "{} EIN NULL", "Ongeldig rechteroperand in expressie EIN : Operand moet een LijstExpressie zijn" },
//            { "{} EIN {NULL}", "Ongeldig rechteroperand in expressie EIN : LijstExpressie mag geen null bevatten" },
//            { "{} AIN {}", "Ongeldig rechteroperand in expressie AIN : LijstExpressie mag niet leeg zijn" },
//            { "{} AIN {NULL}", "Ongeldig rechteroperand in expressie AIN : LijstExpressie mag geen null bevatten" },
//            { "{2} AIN {1, \"A\"}", "Ongeldig rechteroperand in expressie AIN : LijstExpressie moet homogeen zijn." },
//            { "{} AIN% {\"2345XD\"}", "Ongeldig linkeroperand in expressie AIN% : LijstExpressie mag niet leeg zijn" },
//            { "{} EIN% {\"2345XD\"}", "Ongeldig linkeroperand in expressie EIN% : LijstExpressie mag niet leeg zijn" },

                //onderstaand een aantal foutieve vergelijkingen die we nu parsetijd er
                //niet uithalen. Dit soort expressies worden zeer waarschijnlijk niet
                //gebruikt
                //{"1999/06/11 < {1}", ""},
                //{"1999/06/11 < {WAAR}", ""},
                //{"1 < {1, 1}", ""}
        });
    }


    @Test
    public void testVergelijkingen() throws ExpressieException {
        TestUtils.assertExceptie(expressie, foutmelding);
    }
}
