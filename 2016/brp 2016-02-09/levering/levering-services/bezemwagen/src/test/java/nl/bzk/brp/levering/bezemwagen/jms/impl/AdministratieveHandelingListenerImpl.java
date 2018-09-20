/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.bezemwagen.jms.impl;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import nl.bzk.brp.levering.bezemwagen.jms.AdministratieveHandelingListener;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Toehoorder voor berichten op een JMS queue/topic met administratieve handeling id's.
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class AdministratieveHandelingListenerImpl implements AdministratieveHandelingListener, MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private int verwerkteAdministratieveHandelingen;

    @Override
    public final void onMessage(final Message message) {
        try {
            final String body = ((TextMessage) message).getText();
            LOGGER.debug("Bericht ontvangen: " + body);

            verwerkteAdministratieveHandelingen++;

        } catch (final JMSException e) {

            LOGGER.error("Het ontvangen van een JMS bericht met een Administratieve Handeling ID is mislukt.", e);

        }

    }

    @Override
    public final int getVerwerkteAdministratieveHandelingen() {
        return verwerkteAdministratieveHandelingen;
    }

}
