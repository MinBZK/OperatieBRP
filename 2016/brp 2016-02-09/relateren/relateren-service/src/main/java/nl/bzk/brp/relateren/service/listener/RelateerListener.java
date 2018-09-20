/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.relateren.service.listener;

import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.relateren.service.RelateerService;
import nl.bzk.brp.relateren.service.bericht.RelateerPersoonBericht;
import org.springframework.stereotype.Component;

/**
 * Handelt binnenkomende relateer berichten af en delegeert de verwerking naar de bijbehorende relateer service.
 */
@Component
public final class RelateerListener implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private RelateerService relateerService;

    @Override
    public void onMessage(final Message message) {
        relateerService.verwerkPersoonRelateerBericht(RelateerPersoonBericht.readValue(leesBericht(message)));
    }

    private static String leesBericht(final Message message) {
        final String result;
        if (message instanceof TextMessage) {
            try {
                result = ((TextMessage) message).getText();
            } catch (JMSException e) {
                throw new IllegalArgumentException("Kan text uit bericht niet lezen.", e);
            }
        } else {
            throw new IllegalArgumentException("Message type niet ondersteund: " + message.getClass());
        }
        return result;
    }
}
