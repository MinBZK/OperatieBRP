/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb;

import nl.moderniseringgba.isc.jbpm.actionhandler.EsbNotifier;
import nl.moderniseringgba.isc.jbpm.berichten.BerichtenDao;
import nl.moderniseringgba.isc.jbpm.correlatie.ProcessCorrelatieStore;
import nl.moderniseringgba.isc.jbpm.correlatie.ProcessData;

import org.jboss.soa.esb.addressing.Call;
import org.jboss.soa.esb.addressing.EPR;
import org.jboss.soa.esb.addressing.PortReference;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Body;
import org.jboss.soa.esb.message.Header;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.Properties;
import org.jboss.soa.esb.services.jbpm.Constants;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mockito;

public class CorrelatieBewarenActionTest {

    // Subject
    private CorrelatieBewarenAction subject;

    // Dependencies
    private ConfigTree configTree;
    private ProcessCorrelatieStore processCorrelatieStore;
    private BerichtenDao berichtenDao;

    @Before
    public void setup() {
        configTree = Mockito.mock(ConfigTree.class);
        processCorrelatieStore = Mockito.mock(ProcessCorrelatieStore.class);
        berichtenDao = Mockito.mock(BerichtenDao.class);

        subject = new CorrelatieBewarenAction(configTree);
        subject.setProcessCorrelatieStore(processCorrelatieStore);
        subject.setBerichtenDao(berichtenDao);
    }

    @Test
    public void testGeenCorrelatie() throws Exception {
        // Expect
        final Message message = Mockito.mock(Message.class);
        final Header header = Mockito.mock(Header.class);
        final Call call = Mockito.mock(Call.class);
        final Properties properties = Mockito.mock(Properties.class);
        final Body body = Mockito.mock(Body.class);

        Mockito.when(message.getHeader()).thenReturn(header);
        Mockito.when(header.getCall()).thenReturn(call);

        Mockito.when(message.getProperties()).thenReturn(properties);
        Mockito.when(properties.getProperty(EsbConstants.PROPERTY_BERICHT)).thenReturn(42L);

        Mockito.when(message.getBody()).thenReturn(body);

        // Execute
        final Message result = subject.process(message);
        Assert.assertSame(message, result);
    }

    @Test
    public void testNotifierCorrelatie() throws Exception {
        // Expect
        final Message message = Mockito.mock(Message.class);
        final Header header = Mockito.mock(Header.class);
        final Call call = Mockito.mock(Call.class);
        final Properties properties = Mockito.mock(Properties.class);
        final Body body = Mockito.mock(Body.class);

        Mockito.when(message.getHeader()).thenReturn(header);
        Mockito.when(header.getCall()).thenReturn(call);

        Mockito.when(message.getProperties()).thenReturn(properties);
        Mockito.when(properties.getProperty(EsbConstants.PROPERTY_BERICHT)).thenReturn(42L);

        Mockito.when(message.getBody()).thenReturn(body);
        Mockito.when(body.get(EsbNotifier.PROPERTY_PROCESS_INSTANCE_ID)).thenReturn("14");

        // Execute
        final Message result = subject.process(message);
        Assert.assertSame(message, result);

        // Verify
        Mockito.verify(berichtenDao).updateProcessInstance(42L, 14L);
    }

    @Test
    public void testEsbActionCorrelatie() throws Exception {
        // Expect
        final Message message = Mockito.mock(Message.class);
        final Header header = Mockito.mock(Header.class);
        final Call call = Mockito.mock(Call.class);
        final EPR epr = Mockito.mock(EPR.class);
        final PortReference portReference = Mockito.mock(PortReference.class);
        final Properties properties = Mockito.mock(Properties.class);
        final Body body = Mockito.mock(Body.class);

        Mockito.when(message.getHeader()).thenReturn(header);
        Mockito.when(header.getCall()).thenReturn(call);
        Mockito.when(call.getReplyTo()).thenReturn(epr);
        Mockito.when(epr.getAddr()).thenReturn(portReference);
        Mockito.when(portReference.getExtensionValue(Constants.PROCESS_INSTANCE_ID)).thenReturn("23");
        Mockito.when(portReference.getExtensionValue(Constants.TOKEN_ID)).thenReturn("3334");
        Mockito.when(portReference.getExtensionValue(Constants.NODE_ID)).thenReturn("332");
        final String counterName = Constants.PROCESS_NODE_VERSION_COUNTER + "332_3334";
        Mockito.when(portReference.getExtensionValue(counterName)).thenReturn("1");

        Mockito.when(message.getProperties()).thenReturn(properties);
        Mockito.when(properties.getProperty(EsbConstants.PROPERTY_BERICHT)).thenReturn(42L);
        Mockito.when(properties.getProperty(EsbConstants.PROPERTY_MESSAGE_ID)).thenReturn("M00000000002");

        Mockito.when(message.getBody()).thenReturn(body);

        // Execute
        final Message result = subject.process(message);
        Assert.assertSame(message, result);

        // Verify
        final ArgumentCaptor<ProcessData> processDataCaptor = ArgumentCaptor.forClass(ProcessData.class);
        Mockito.verify(processCorrelatieStore).bewaarProcessCorrelatie(Matchers.eq("M00000000002"),
                processDataCaptor.capture());
        final ProcessData processData = processDataCaptor.getValue();
        Assert.assertNotNull(processData);
        Assert.assertEquals(Long.valueOf(23L), processData.getProcessInstanceId());
        Assert.assertEquals(Long.valueOf(3334L), processData.getTokenId());
        Assert.assertEquals(Long.valueOf(332L), processData.getNodeId());
        Assert.assertEquals(counterName, processData.getCounterName());
        Assert.assertEquals(Integer.valueOf(1), processData.getCounterValue());

        Mockito.verify(berichtenDao).updateProcessInstance(42L, 23L);
    }

    @Test
    public void testNoOps() throws Exception {
        final Message message = Mockito.mock(Message.class);
        final Throwable throwable = Mockito.mock(Throwable.class);

        subject.initialise();
        subject.processSuccess(message);
        subject.processException(message, throwable);
        subject.destroy();
    }
}
