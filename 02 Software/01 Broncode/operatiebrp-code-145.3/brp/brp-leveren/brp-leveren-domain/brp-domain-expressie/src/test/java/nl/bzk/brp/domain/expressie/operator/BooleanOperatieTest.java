/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.operator;

import static nl.bzk.brp.domain.expressie.util.TestUtils.ONWAAR;
import static nl.bzk.brp.domain.expressie.util.TestUtils.WAAR;

import java.util.Arrays;
import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.expressie.util.TestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 */
@RunWith(Parameterized.class)
public class BooleanOperatieTest {

    private final String expressie;
    private final String resultaat;

    public BooleanOperatieTest(final String expressie, final String resultaat) {
        this.expressie = expressie;
        this.resultaat = resultaat;
    }

    @Parameterized.Parameters(name = "{index}: ({0}) = {1}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                // bekende datum
                {"WAAR = WAAR", WAAR},
                {"WAAR = ONWAAR", ONWAAR},
                {"ONWAAR = WAAR", ONWAAR},
                {"ONWAAR = ONWAAR", WAAR},
                {"WAAR > WAAR", ONWAAR},
                {"WAAR > ONWAAR", WAAR},
                {"ONWAAR > WAAR", ONWAAR},
                {"ONWAAR > ONWAAR", ONWAAR},
                {"WAAR < WAAR", ONWAAR},
                {"WAAR < ONWAAR", ONWAAR},
                {"ONWAAR < WAAR", WAAR},
                {"ONWAAR < ONWAAR", ONWAAR},
                {"WAAR <> WAAR", ONWAAR},
                {"WAAR <> ONWAAR", WAAR},
                {"ONWAAR <> WAAR", WAAR},
                {"ONWAAR <> ONWAAR", ONWAAR},
                {"WAAR >= WAAR", WAAR},
                {"WAAR >= ONWAAR", WAAR},
                {"ONWAAR >= WAAR", ONWAAR},
                {"ONWAAR >= ONWAAR", WAAR},
                {"WAAR <= WAAR", WAAR},
                {"WAAR <= ONWAAR", ONWAAR},
                {"ONWAAR <= WAAR", WAAR},
                {"ONWAAR <= ONWAAR", WAAR}
        });
    }

    @Test
    public void testBooleanVergelijkingen() throws ExpressieException {
        TestUtils.testEvaluatie(expressie, resultaat);

    }
}
