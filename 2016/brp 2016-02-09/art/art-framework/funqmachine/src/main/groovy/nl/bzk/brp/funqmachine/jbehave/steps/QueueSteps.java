/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.jbehave.steps;

import javax.jms.Message;
import nl.bzk.brp.funqmachine.configuratie.Environment;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.jbehave.core.annotations.Then;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public final class QueueSteps {

    static final Logger LOGGER = LoggerFactory.getLogger(QueueSteps.class);

    /**
     * Controleert of er een bericht op de GBALeveringen queue is gezet
     */
    @Then("is er een LO3 levering gedaan")
    public void thenIsErEenLO3leveringGedaan() {

        final ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(Environment.instance().getBrokerURL());
        final JmsTemplate jms = new JmsTemplate(factory);
        jms.setReceiveTimeout(2500);

        final Message bericht = jms.receive("GbaLeveringen");
        if (bericht == null) {
            throw new RuntimeException("Geen LO3 levering gevonden");
        }

        LOGGER.info("LO3bericht = " + bericht);
    }
}
