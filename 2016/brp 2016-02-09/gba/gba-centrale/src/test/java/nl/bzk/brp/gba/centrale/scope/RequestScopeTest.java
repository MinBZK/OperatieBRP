/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.centrale.scope;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;

public class RequestScopeTest {

    private final RequestScope subject = new RequestScope();

    @Test
    public void test() {
        final MyObjectFactory objectFactory = new MyObjectFactory();

        final Object object1 = subject.get("BLA", objectFactory);
        final Object object2 = subject.get("BLA", objectFactory);
        Assert.assertSame(object1, object2);

        subject.remove("BLA");
        final Object object3 = subject.get("BLA", objectFactory);
        Assert.assertNotSame(object1, object3);
        final Object object4 = subject.get("BLA", objectFactory);
        Assert.assertSame(object3, object4);

        subject.reset();
        final Object object5 = subject.get("BLA", objectFactory);
        Assert.assertNotSame(object3, object5);
        final Object object6 = subject.get("BLA", objectFactory);
        Assert.assertSame(object5, object6);

        subject.registerDestructionCallback(null, null);
        Assert.assertNull(subject.resolveContextualObject(null));
        Assert.assertEquals("RequestScope", subject.getConversationId());

    }

    private static final class MyObjectFactory implements ObjectFactory<MyObject> {

        @Override
        public MyObject getObject() throws BeansException {
            return new MyObject();
        }
    }

    private static final class MyObject {

    }
}
