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

@PrepareForTest(JmxServer.class)
public class JmxAttributeTest extends AbstractTagTest {

    @Test
    public void test() throws Exception {
        final JmxServer jmxServer = PowerMockito.mock(JmxServer.class);
        addTagAttribute("target", null);
        addTagAttribute("server", jmxServer);
        addTagAttribute("object", "Queue");
        addTagAttribute("attribute", "MessageCount");
        addTagAttribute("ignoreErrors", false);

        PowerMockito.when(jmxServer.getAttribute("Queue", "MessageCount")).thenReturn("100");

        // Execute
        final ActionListener subject = initializeBasicSubject(JmxAttributeHandler.class);
        Assert.assertEquals(JmxAttributeActionListener.class, subject.getClass());
        subject.processAction(actionEvent);

        final Object jmxObject = getExpressionValues().get("target");
        Assert.assertNotNull(jmxObject);
        Assert.assertEquals("100", jmxObject);
    }
}
