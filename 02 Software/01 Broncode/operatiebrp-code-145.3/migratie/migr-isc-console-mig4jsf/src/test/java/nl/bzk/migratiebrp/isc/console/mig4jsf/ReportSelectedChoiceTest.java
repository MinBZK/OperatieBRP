/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.isc.jbpm.common.jsf.FoutafhandelingPaden;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.core.impl.UpdatesHashMap;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mockito;

public class ReportSelectedChoiceTest extends AbstractTagTest {

    @Test
    public void test() throws Exception {
        final Map<String, Object> backingMap = new HashMap<>();
        backingMap.put("restart", "testRestartWaarde");

        final FoutafhandelingPaden paden = new FoutafhandelingPaden();
        paden.put("testRestartWaarde", "De omschrijving", false, false);
        backingMap.put("foutafhandelingPaden", paden);
        final UpdatesHashMap variableMap = new UpdatesHashMap(backingMap);

        addTagAttribute("variableMap", variableMap);

        // Execute
        final JbpmActionListener subject = initializeSubject(ReportSelectedChoiceHandler.class);
        Assert.assertEquals("reportSelectedChoice", subject.getName());
        subject.handleAction(jbpmJsfContext, actionEvent);

        // Verify
        Mockito.verify(jbpmJsfContext, Mockito.never()).setError(Matchers.anyString());
        Mockito.verify(jbpmJsfContext, Mockito.never()).setError(Matchers.anyString(), Matchers.anyString());
        Mockito.verify(jbpmJsfContext, Mockito.never()).setError(Matchers.anyString(), Matchers.<Throwable>anyObject());

        final ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(jbpmJsfContext).addSuccessMessage(messageCaptor.capture());
        final String message = messageCaptor.getValue();

        Assert.assertNotNull(message);
        Assert.assertTrue(message.contains("De omschrijving"));
    }
}
