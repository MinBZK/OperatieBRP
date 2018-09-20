/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.util.common.jmx;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.jmx.export.naming.ObjectNamingStrategy;

public class DynamicDomainNamingStrategyTest {

    private ObjectNamingStrategy delegate;
    private DynamicDomainNamingStrategy subject;

    @Before
    public void setup() {
        delegate = Mockito.mock(ObjectNamingStrategy.class);
        subject = new DynamicDomainNamingStrategy();
        subject.setDomain("nl.bzk.migratiebrp.test.nieuw");
        subject.setDelegate(delegate);
    }

    @Test
    public void shouldReplace() throws MalformedObjectNameException {
        final Object managedBean = new ObjectMetAnnotatie();
        Mockito.when(delegate.getObjectName(managedBean, "ignored")).thenReturn(new ObjectName("nl.bzk.iets.anders", "name", "TEST"));

        final ObjectName result = subject.getObjectName(managedBean, "ignored");
        Assert.assertEquals("nl.bzk.migratiebrp.test.nieuw:name=TEST", result.getCanonicalName());
    }

    @Test
    public void shouldNotReplace() throws MalformedObjectNameException {
        final Object managedBean = new ObjectZonderAnnotatie();
        Mockito.when(delegate.getObjectName(managedBean, "ignored")).thenReturn(new ObjectName("nl.bzk.iets.anders", "name", "TEST"));

        final ObjectName result = subject.getObjectName(managedBean, "ignored");
        Assert.assertEquals("nl.bzk.iets.anders:name=TEST", result.getCanonicalName());
    }

    @UseDynamicDomain
    private final static class ObjectMetAnnotatie {
    }

    private final static class ObjectZonderAnnotatie {
    }

}
