/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
// JBOSS: @ContextConfiguration(locations = { "classpath:/runtime-test-jboss.xml" })
// HORNETQ: @ContextConfiguration(locations = { "classpath:/runtime-test-hornetq.xml" })
// LOCAL: @ContextConfiguration(locations = { "classpath:/runtime-test-infra.xml" })
@ContextConfiguration(locations = {"classpath:/runtime-test-infra.xml" })
public class ClearQueuesTest {

    private JmsTemplate jmsTemplate;

    @Inject
    @Named("queueInbound")
    private Destination syncRequest;

    @Inject
    @Named("queueOutbound")
    private Destination syncResponse;

    /**
     * Zet de waarde van connection factory.
     *
     * @param connectionFactory
     *            connection factory
     */
    @Inject
    @Named("queueConnectionFactory")
    public void setConnectionFactory(final ConnectionFactory connectionFactory) {
        jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setReceiveTimeout(15000); // 15 seconds
    }

    @Test
    public void clearQueues() {
        System.out.println("Opschonen queues");

        clearQueue(syncRequest);
        clearQueue(syncResponse);
    }

    private void clearQueue(final Destination destination) {
        final long timeout = jmsTemplate.getReceiveTimeout();

        try {
            jmsTemplate.setReceiveTimeout(250);
            while (jmsTemplate.receive(destination) != null) {
                System.out.println("Cleared message");
            }
        } finally {
            jmsTemplate.setReceiveTimeout(timeout);
        }

    }

}
