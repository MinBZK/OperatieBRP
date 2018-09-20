/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb;

import nl.moderniseringgba.isc.esb.message.lo3.Lo3Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3HeaderVeld;

import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Body;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.Properties;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class BerichtFormatActionTest {

    // Subject
    private BerichtFormatAction subject;

    // Dependencies
    private ConfigTree configTree;

    @Before
    public void setup() {
        configTree = Mockito.mock(ConfigTree.class);

        subject = new BerichtFormatAction(configTree);
    }

    @Test
    public void test() throws Exception {
        // Expect
        final Message message = Mockito.mock(Message.class);
        final Properties properties = Mockito.mock(Properties.class);
        final Body body = Mockito.mock(Body.class);
        final Lo3Bericht bericht = Mockito.mock(Lo3Bericht.class);

        Mockito.when(message.getProperties()).thenReturn(properties);

        Mockito.when(message.getBody()).thenReturn(body);
        Mockito.when(body.get()).thenReturn(bericht);

        Mockito.when(bericht.getMessageId()).thenReturn("M00000000002");
        Mockito.when(bericht.getCorrelationId()).thenReturn("M00000000001");
        Mockito.when(bericht.getBerichtType()).thenReturn("TestBericht");
        Mockito.when(bericht.getBronGemeente()).thenReturn("0512");
        Mockito.when(bericht.getDoelGemeente()).thenReturn("0560");
        Mockito.when(bericht.getHeader(Lo3HeaderVeld.HERHALING)).thenReturn("3");
        Mockito.when(bericht.format()).thenReturn("berichtInhoud");

        // Execute
        final Message result = subject.process(message);
        Assert.assertSame(message, result);

        // Verify
        Mockito.verify(properties).setProperty(EsbConstants.PROPERTY_MESSAGE_ID, "M00000000002");
        Mockito.verify(properties).setProperty(EsbConstants.PROPERTY_CORRELATIE_ID, "M00000000001");
        Mockito.verify(properties).setProperty(EsbConstants.PROPERTY_NAAM, "TestBericht");
        Mockito.verify(properties).setProperty(EsbConstants.PROPERTY_BRON_GEMEENTE, "0512");
        Mockito.verify(properties).setProperty(EsbConstants.PROPERTY_DOEL_GEMEENTE, "0560");
        Mockito.verify(properties).setProperty(EsbConstants.PROPERTY_HERHALING, 3);
        Mockito.verify(body).add("berichtInhoud");

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
