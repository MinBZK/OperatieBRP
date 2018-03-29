/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf;

import nl.bzk.migratiebrp.isc.console.mig4jsf.dto.Bericht;
import nl.bzk.migratiebrp.isc.console.mig4jsf.dto.Bericht.Direction;
import org.jbpm.jsf.JbpmActionListener;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

public class LoadBerichtTest extends AbstractTagTest {

    @Test
    public void test() throws Exception {
        addTagAttribute("id", 42L);
        addTagAttribute("target", null);

        setupDatabase(
                "/sql/hsqldb4postgres.sql",
                "/sql/mig-drop.sql",
                "/sql/jbpm-drop.sql",
                "/sql/jbpm-create.sql",
                "/sql/mig-create.sql",
                "/nl/bzk/migratiebrp/isc/console/mig4jsf/insert-berichten.sql");

        // Execute
        final JbpmActionListener subject = initializeSubject(LoadBerichtHandler.class);
        Assert.assertEquals("loadBericht", subject.getName());
        subject.handleAction(jbpmJsfContext, actionEvent);

        // Verify
        Mockito.verify(jbpmJsfContext, Mockito.never()).setError(Matchers.anyString());
        Mockito.verify(jbpmJsfContext, Mockito.never()).setError(Matchers.anyString(), Matchers.anyString());
        Mockito.verify(jbpmJsfContext, Mockito.never()).setError(Matchers.anyString(), Matchers.<Throwable>anyObject());

        final Bericht bericht = (Bericht) getExpressionValues().get("target");
        Assert.assertNotNull(bericht);
        Assert.assertEquals(Long.valueOf(42L), bericht.getId());
        Assert.assertEquals("VOISC", bericht.getKanaal());
        Assert.assertEquals(Direction.INKOMEND, bericht.getRichting());
        Assert.assertEquals("MSG-ID", bericht.getMessageId());
        Assert.assertEquals("CORR-ID", bericht.getCorrelationId());
        Assert.assertEquals("INHOUD", bericht.getBericht());
        Assert.assertEquals("NAAM", bericht.getNaam());
        Assert.assertEquals(Long.valueOf(4321L), bericht.getProcessInstanceId());
        Assert.assertEquals("0518", bericht.getBronPartijCode());
        Assert.assertEquals("0519", bericht.getDoelPartijCode());
    }
}
