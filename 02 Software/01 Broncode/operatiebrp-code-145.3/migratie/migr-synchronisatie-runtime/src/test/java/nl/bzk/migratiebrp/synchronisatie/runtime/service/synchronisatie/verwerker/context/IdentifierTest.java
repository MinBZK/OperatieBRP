/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context;

import org.junit.Assert;
import org.junit.Test;

public class IdentifierTest {

    @Test
    public void test() {
        final Identifier subject = new Identifier("a", 123L);
        final Identifier equals = new Identifier("a", 123L);
        final Identifier different = new Identifier("b", 123L);
        Assert.assertTrue(subject.equals(subject));
        Assert.assertTrue(subject.equals(equals));
        Assert.assertFalse(subject.equals(null));
        Assert.assertFalse(subject.equals(new Object()));
        Assert.assertFalse(subject.equals(different));

        Assert.assertEquals(subject.hashCode(), equals.hashCode());
        Assert.assertEquals(subject.toString(), equals.toString());
    }

}
