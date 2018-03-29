/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.literal;

import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.expressie.util.TestUtils;
import org.junit.Test;

/**
 */
public class StingLiteralTest {

    @Test(expected = ExpressieException.class)
    public void testDubbeleQuotesMagNiet() throws ExpressieException {
        eval("\"wat een \\\"rare\\\" snuiter\"");
    }

    @Test
    public void testEnkelQuotes() throws ExpressieException {
        eval("\"wat een 'rare' snuiter\"");
    }

    @Test
    public void testOverig() throws ExpressieException {
        eval("\"!@#$%*()~\"");
        eval("\")(~\"");
        eval("\"'\"");
        eval("\"''\"");
        eval("\"*\"");
        eval("\" ''''  ' ' '  '\"");
    }

    private void eval(String x) throws ExpressieException {
        TestUtils.testEvaluatie(x, x);
    }
}
