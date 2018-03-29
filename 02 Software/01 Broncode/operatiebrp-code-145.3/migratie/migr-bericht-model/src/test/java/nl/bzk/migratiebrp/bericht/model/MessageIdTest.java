/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model;

import org.junit.Assert;
import org.junit.Test;

public class MessageIdTest {

    @Test(expected = IllegalArgumentException.class)
    public void testTeLang() {
        MessageId.bepaalMessageId(Long.MAX_VALUE);
    }

    @Test
    public void test() {
        Assert.assertEquals("MM00000009ix", MessageId.bepaalMessageId(12345L));
        Assert.assertEquals("MM17rf9km92x", MessageId.bepaalMessageId(123456789012345L));
    }
}
