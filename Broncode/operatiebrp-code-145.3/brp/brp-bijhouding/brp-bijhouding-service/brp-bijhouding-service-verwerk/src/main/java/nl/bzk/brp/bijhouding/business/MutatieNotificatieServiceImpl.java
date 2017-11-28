/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business;

import javax.inject.Inject;
import javax.jms.TextMessage;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.serialisatie.JsonStringSerializer;
import nl.bzk.brp.domain.internbericht.bijhoudingsnotificatie.BijhoudingsplanNotificatieBericht;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

/**
 * Implementatie voor {@link MutatieNotificatieService}.
 */
@Service
public final class MutatieNotificatieServiceImpl implements MutatieNotificatieService {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private final JsonStringSerializer serialiseerderBijhoudingsplan = new JsonStringSerializer();

    private final JmsTemplate bijhoudingsplanJmsTemplate;

    /**
     * Implementatie van de {@link MutatieNotificatieService}.
     * @param bijhoudingsplanJmsTemplate de JMS template voor het versturen van notificatie berichten
     */
    @Inject
    public MutatieNotificatieServiceImpl(final JmsTemplate bijhoudingsplanJmsTemplate) {
        this.bijhoudingsplanJmsTemplate = bijhoudingsplanJmsTemplate;
    }

    @Override
    public void verstuurBijhoudingsNotificatie(final BijhoudingsplanNotificatieBericht notificatieBericht) {
        try {
            final MessageCreator messageCreator = session -> {
                final TextMessage message = session.createTextMessage();
                // Header voor message group mechanisme van ActiveMq
                message.setStringProperty("JMSXGroupID", String.valueOf(notificatieBericht.getOntvangendePartijCode()));
                message.setText(serialiseerderBijhoudingsplan.serialiseerNaarString(notificatieBericht));
                return message;
            };
            bijhoudingsplanJmsTemplate.send(messageCreator);
        } catch (final JmsException e) {
            LOGGER.error("Het publiceren van het verwerk BijhoudingsplanBericht is mislukt ivm een JMS exceptie.", e);
            throw e;
        }
    }
}
