/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf.jmx;

import javax.faces.event.ActionListener;
import nl.bzk.migratiebrp.isc.console.mig4jsf.AbstractTagTest;
import org.junit.Assert;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

@PrepareForTest({JmxServer.class, JmxServerActionListener.class})
public class JmxServerTest extends AbstractTagTest {

    @Test
    public void test() throws Exception {
        final JmxServer jmxServerMocked = PowerMockito.mock(JmxServer.class);
        addTagAttribute("target", null);
        addTagAttribute("url", "url");
        addTagAttribute("username", "username");
        addTagAttribute("password", "password");

        PowerMockito.whenNew(JmxServer.class).withArguments("url", "username", "password").thenReturn(jmxServerMocked);

        // Execute
        final ActionListener subject = initializeBasicSubject(JmxServerHandler.class);
        Assert.assertEquals(JmxServerActionListener.class, subject.getClass());
        subject.processAction(actionEvent);

        final JmxServer jmxServerResult = (JmxServer) getExpressionValues().get("target");
        Assert.assertNotNull(jmxServerResult);
        Assert.assertEquals(jmxServerMocked, jmxServerResult);
    }
}
