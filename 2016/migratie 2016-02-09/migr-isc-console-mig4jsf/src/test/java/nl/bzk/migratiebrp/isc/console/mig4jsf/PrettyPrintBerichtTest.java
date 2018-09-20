/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf;

import nl.bzk.migratiebrp.isc.console.mig4jsf.dto.Bericht;
import org.jbpm.jsf.JbpmActionListener;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class PrettyPrintBerichtTest extends AbstractTagTest {

    @Test
    public void test() throws Exception {
        final Bericht bericht = new Bericht();
        bericht.setBericht("00000000Wa0152417053985201302190008701082011001021506573130210006Anette0240008Vreehoek0310008197604290320004058003300046030");
        bericht.setKanaal("VOSPG");

        addTagAttribute("bericht", bericht);
        addTagAttribute("target", null);

        // Execute
        final JbpmActionListener subject = initializeSubject(PrettyPrintBerichtHandler.class);
        Assert.assertEquals("prettyPrintBericht", subject.getName());
        subject.handleAction(jbpmJsfContext, actionEvent);

        // Verify
        Mockito.verify(jbpmJsfContext, Mockito.never()).setError(Mockito.anyString());
        Mockito.verify(jbpmJsfContext, Mockito.never()).setError(Mockito.anyString(), Mockito.anyString());
        Mockito.verify(jbpmJsfContext, Mockito.never()).setError(Mockito.anyString(), Mockito.<Throwable>anyObject());

        final String berichtHtml = (String) getExpressionValues().get("target");
        Assert.assertNotNull(berichtHtml);

    }
}
