/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.runtime;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import junit.framework.Assert;
import nl.ictu.spg.common.util.conversion.GBACharacterSet;
import nl.moderniseringgba.isc.esb.message.BerichtId;
import nl.moderniseringgba.isc.esb.message.JMSConstants;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3BerichtFactory;
import nl.moderniseringgba.isc.esb.message.lo3.impl.NullBericht;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@org.junit.Ignore("Test tegen de container aan en kan dus neit op de buildserver worden uitgevoerd")
@RunWith(SpringJUnit4ClassRunner.class)
// HORNETQ: @ContextConfiguration(locations = { "classpath:/runtime-test-hornetq.xml" })
// JBOSS: @ContextConfiguration(locations = { "classpath:/runtime-test-jboss.xml" })
@ContextConfiguration(locations = { "classpath:/runtime-test-jboss.xml" })
public class SendLg01Test {

    private JmsTemplate jmsTemplate;

    @Inject
    @Named("queueInbound")
    private Destination syncRequest;

    @Inject
    @Named("queueOutbound")
    private Destination syncResponse;

    @Inject
    @Named("queueConnectionFactory")
    public void setConnectionFactory(final ConnectionFactory connectionFactory) {
        jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setReceiveTimeout(15000); // 15 seconds
    }

    @Test
    public void test() throws Exception {
        final byte[] lg01 = IOUtils.toByteArray(this.getClass().getResourceAsStream("lg01.txt"));
        final String lg01Bericht = GBACharacterSet.convertTeletexByteArrayToString(lg01);
        final String messageId = BerichtId.generateMessageId();

        jmsTemplate.send(syncRequest, new MessageCreator() {
            @Override
            public Message createMessage(final Session session) throws JMSException {
                final Message message = session.createTextMessage(lg01Bericht);
                message.setStringProperty(JMSConstants.BERICHT_REFERENTIE, messageId);

                return message;
            }
        });

        final Message message =
                jmsTemplate.receiveSelected(syncResponse, "iscCorrelatieReferentie = '" + messageId + "'");
        Assert.assertNotNull(message);
        Assert.assertTrue(message instanceof TextMessage);

        final String returnMessage = ((TextMessage) message).getText();
        final Lo3Bericht returnBericht = new Lo3BerichtFactory().getBericht(returnMessage);
        Assert.assertNotNull(returnBericht);
        Assert.assertTrue(returnBericht instanceof NullBericht);
    }
}
