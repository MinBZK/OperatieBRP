/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.component;

import javax.jms.JMSException;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;

/**
 * Abstractie tbv de jms componenten.
 */
abstract class AbstractJmsDocker extends AbstractDocker implements JmsDocker {


    private ActiveMQConnectionFactory activeMQConnectionFactory;

    @Override
    public boolean isFunctioneelBeschikbaar() {
        try {
            final JmsTemplate jmsTemplate = new JmsTemplate(getConnectionFactory());
            jmsTemplate.setReceiveTimeout(100);
            jmsTemplate.
                    send("queue://testqueue", session -> session.createTextMessage("test"));
            return true;
        } catch (JmsException e) {
            return false;
        }
    }

    @Override
    public void voerUit(final JmsDocker.JMSTemplateVerzoek verzoek) throws JMSException {
        verzoek.voerUit(new JmsTemplate(getConnectionFactory()));
    }

    private ActiveMQConnectionFactory getConnectionFactory() {
        if (this.activeMQConnectionFactory == null) {
            this.activeMQConnectionFactory = new ActiveMQConnectionFactory(String.
                    format("failover:(tcp://%s:%d)?maxReconnectAttempts=-1&timeout=5000",
                            getOmgeving().getDockerHostname(), getPoortMap().get(Poorten.JMS_POORT)));
        }
        return this.activeMQConnectionFactory;
    }
}
