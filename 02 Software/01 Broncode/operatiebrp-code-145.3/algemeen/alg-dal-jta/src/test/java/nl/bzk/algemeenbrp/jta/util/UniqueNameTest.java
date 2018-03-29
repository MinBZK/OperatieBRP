/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.jta.util;

import org.junit.Assert;
import org.junit.Test;

public class UniqueNameTest {

    @Test
    public void test() {
        final UniqueName subject = new UniqueName();
        subject.setBaseName("TEST-");
        Assert.assertFalse(subject.getObject().equals(subject.getObject()));
        Assert.assertEquals(String.class, subject.getObjectType());
        Assert.assertFalse(subject.isSingleton());
    }
}
