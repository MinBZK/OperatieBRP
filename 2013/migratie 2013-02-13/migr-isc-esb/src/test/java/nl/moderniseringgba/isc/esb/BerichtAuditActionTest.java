/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb;

import java.util.ArrayList;

import nl.moderniseringgba.isc.jbpm.berichten.Bericht;
import nl.moderniseringgba.isc.jbpm.berichten.BerichtenDao;
import nl.moderniseringgba.isc.jbpm.berichten.Direction;

import org.jboss.soa.esb.actions.ActionLifecycleException;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Body;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.Properties;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class BerichtAuditActionTest {

    // Subject
    private BerichtAuditAction subject;

    // Dependencies
    private ConfigTree configTree;
    private BerichtenDao berichtenDao;

    @Before
    public void setup() {
        configTree = Mockito.mock(ConfigTree.class);
        berichtenDao = Mockito.mock(BerichtenDao.class);

        subject = new BerichtAuditAction(configTree);
        subject.setBerichtenDao(berichtenDao);
    }

    @Test(expected = ActionLifecycleException.class)
    public void configErrorKanaal() throws ActionLifecycleException {
        Mockito.when(configTree.getAttribute("kanaal")).thenReturn(null);
        Mockito.when(configTree.getAttribute("richting")).thenReturn("UITGAAND");

        subject.initialise();
    }

    @Test(expected = ActionLifecycleException.class)
    public void configErrorRichtingNull() throws ActionLifecycleException {
        Mockito.when(configTree.getAttribute("kanaal")).thenReturn("TEST");
        Mockito.when(configTree.getAttribute("richting")).thenReturn(null);

        subject.initialise();
    }

    @Test(expected = ActionLifecycleException.class)
    public void configErrorRichtingOnbekend() throws ActionLifecycleException {
        Mockito.when(configTree.getAttribute("kanaal")).thenReturn("TEST");
        Mockito.when(configTree.getAttribute("richting")).thenReturn("DOORGAAND");

        subject.initialise();
    }

    @Test
    public void testInkomend() throws ActionLifecycleException, ActionProcessingException {
        Mockito.when(configTree.getAttribute("kanaal")).thenReturn("TEST");
        Mockito.when(configTree.getAttribute("richting")).thenReturn("INKOMEND");

        // Expect
        final Message message = Mockito.mock(Message.class);
        final Properties properties = Mockito.mock(Properties.class);
        final Body body = Mockito.mock(Body.class);

        Mockito.when(message.getProperties()).thenReturn(properties);
        Mockito.when(properties.getProperty(EsbConstants.PROPERTY_MESSAGE_ID)).thenReturn("M00000000002");
        Mockito.when(properties.getProperty(EsbConstants.PROPERTY_CORRELATIE_ID)).thenReturn("M00000000001");

        Mockito.when(message.getBody()).thenReturn(body);
        Mockito.when(body.get()).thenReturn("berichtInhoud");

        Mockito.when(berichtenDao.findBerichtenByMessageId("M00000000002", "TEST", Direction.INKOMEND)).thenReturn(
                new ArrayList<Bericht>());
        Mockito.when(
                berichtenDao.saveBericht("TEST", Direction.INKOMEND, "M00000000002", "M00000000001", "berichtInhoud"))
                .thenReturn(42L);

        // Execute
        subject.initialise();
        final Message result = subject.process(message);
        Assert.assertSame(message, result);

        // Verify
        Mockito.verify(berichtenDao).findBerichtenByMessageId("M00000000002", "TEST", Direction.INKOMEND);
        Mockito.verify(properties).setProperty(EsbConstants.PROPERTY_INDICATIE_HERHALING, Boolean.FALSE);

        Mockito.verify(berichtenDao).saveBericht("TEST", Direction.INKOMEND, "M00000000002", "M00000000001",
                "berichtInhoud");
        Mockito.verify(properties).setProperty(EsbConstants.PROPERTY_BERICHT, 42L);
    }

    @Test
    public void testUitgaand() throws ActionLifecycleException, ActionProcessingException {
        Mockito.when(configTree.getAttribute("kanaal")).thenReturn("TEST");
        Mockito.when(configTree.getAttribute("richting")).thenReturn("UITGAAND");

        // Expect
        final Message message = Mockito.mock(Message.class);
        final Properties properties = Mockito.mock(Properties.class);
        final Body body = Mockito.mock(Body.class);

        Mockito.when(message.getProperties()).thenReturn(properties);
        Mockito.when(properties.getProperty(EsbConstants.PROPERTY_MESSAGE_ID)).thenReturn("M00000000002");
        Mockito.when(properties.getProperty(EsbConstants.PROPERTY_CORRELATIE_ID)).thenReturn("M00000000001");

        Mockito.when(properties.getProperty(EsbConstants.PROPERTY_NAAM)).thenReturn("TestBericht");
        Mockito.when(properties.getProperty(EsbConstants.PROPERTY_BRON_GEMEENTE)).thenReturn("0512");
        Mockito.when(properties.getProperty(EsbConstants.PROPERTY_DOEL_GEMEENTE)).thenReturn("0560");
        Mockito.when(properties.getProperty(EsbConstants.PROPERTY_HERHALING)).thenReturn(1);

        Mockito.when(message.getBody()).thenReturn(body);
        Mockito.when(body.get()).thenReturn("berichtInhoud");

        Mockito.when(
                berichtenDao.saveBericht("TEST", Direction.UITGAAND, "M00000000002", "M00000000001", "berichtInhoud"))
                .thenReturn(42L);

        // Execute
        subject.initialise();
        final Message result = subject.process(message);
        Assert.assertSame(message, result);

        // Verify
        Mockito.verify(berichtenDao).saveBericht("TEST", Direction.UITGAAND, "M00000000002", "M00000000001",
                "berichtInhoud");
        Mockito.verify(properties).setProperty(EsbConstants.PROPERTY_BERICHT, 42L);

        Mockito.verify(berichtenDao).updateNaam(42L, "TestBericht");
        Mockito.verify(berichtenDao).updateGemeenten(42L, "0512", "0560");
        Mockito.verify(berichtenDao).updateHerhaling(42L, 1);
    }

    @Test
    public void testNoOps() throws Exception {
        final Message message = Mockito.mock(Message.class);
        final Throwable throwable = Mockito.mock(Throwable.class);

        subject.processSuccess(message);
        subject.processException(message, throwable);
        subject.destroy();
    }
}
