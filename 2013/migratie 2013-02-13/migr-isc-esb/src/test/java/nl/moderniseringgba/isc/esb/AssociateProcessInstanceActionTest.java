/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb;

import nl.moderniseringgba.isc.jbpm.berichten.BerichtenDao;

import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Body;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.Properties;
import org.jboss.soa.esb.services.jbpm.Constants;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class AssociateProcessInstanceActionTest {

    // Subject
    private AssociateProcessInstanceAction subject;

    // Dependencies
    private ConfigTree configTree;
    private BerichtenDao berichtenDao;

    @Before
    public void setup() {
        configTree = Mockito.mock(ConfigTree.class);
        berichtenDao = Mockito.mock(BerichtenDao.class);

        subject = new AssociateProcessInstanceAction(configTree);
        subject.setBerichtenDao(berichtenDao);
    }

    @Test
    public void test() {
        // Expect
        final Message message = Mockito.mock(Message.class);
        final Properties properties = Mockito.mock(Properties.class);
        final Body body = Mockito.mock(Body.class);

        Mockito.when(message.getProperties()).thenReturn(properties);
        Mockito.when(properties.getProperty(EsbConstants.PROPERTY_BERICHT)).thenReturn(42L);

        Mockito.when(message.getBody()).thenReturn(body);
        Mockito.when(body.get(Constants.PROCESS_INSTANCE_ID)).thenReturn(14L);

        // Execute
        final Message result = subject.process(message);
        Assert.assertSame(message, result);

        // Verify
        Mockito.verify(berichtenDao).updateProcessInstance(42L, 14L);

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
