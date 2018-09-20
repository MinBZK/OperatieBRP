/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf.jmx;

import java.util.Collections;
import java.util.List;
import javax.faces.event.ActionListener;
import nl.bzk.migratiebrp.isc.console.mig4jsf.AbstractTagTest;
import org.junit.Assert;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

@PrepareForTest(JmxServer.class)
public class BepaalEsbNaamTest extends AbstractTagTest {

    @Test
    public void test() throws Exception {
        final JmxServer jmxServer = PowerMockito.mock(JmxServer.class);
        addTagAttribute("target", null);
        addTagAttribute("server", jmxServer);
        addTagAttribute("prefix", "migr-isc-esb");

        final List<String> names = Collections.singletonList("migr-isc-esb-naam01");
        PowerMockito.when(jmxServer.listEsbDeployments("migr-isc-esb")).thenReturn(names);

        // Execute
        final ActionListener subject = initializeBasicSubject(BepaalEsbNaamHandler.class);
        Assert.assertEquals(BepaalEsbNaamActionListener.class, subject.getClass());
        subject.processAction(actionEvent);

        final String esbNaam = getExpressionValues().get("target").toString();
        Assert.assertNotNull(esbNaam);
        Assert.assertEquals("migr-isc-esb-naam01", esbNaam);
    }
}
