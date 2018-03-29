/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.register.client.util;

import org.junit.Assert;
import org.junit.Test;

public class RefbeanFactoryTest {

    @Test
    public void test() {
        final RefBeanFactory<Object> subject = new RefBeanFactory<>();
        Assert.assertNull(subject.getObject());
        Assert.assertNull(subject.getObjectType());
        Assert.assertTrue(subject.isSingleton());
        final Object object = new Object();
        subject.setBean(object);
        Assert.assertSame(object, subject.getObject());
        Assert.assertEquals(Object.class, subject.getObjectType());
        Assert.assertTrue(subject.isSingleton());
    }

}
