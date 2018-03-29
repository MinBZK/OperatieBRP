/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.format;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test voor {@link Sv01Formatter}
 */
public class Sv01FormatterTest {

    private final Sv01Formatter subject = new Sv01Formatter();

    @Test
    public void test() {
        Assert.assertEquals("Sv01", subject.getBerichtType());
    }
}
