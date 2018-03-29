/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime.handler;

import java.util.Enumeration;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.isc.runtime.message.Message;

/**
 * Jms Utilities.
 */
public interface JmsUtil {

    /**
     * Lees jms bericht.
     * @param message jms bericht
     * @return bericht
     * @throws JMSException bij lees fouten
     */
    static Message leesMessage(final javax.jms.Message message) throws JMSException {
        final Logger log = LoggerFactory.getLogger();
        log.info("Bericht lezen");

        if (message == null) {
            return null;
        }

        final Message result = new Message();

        // headers
        @SuppressWarnings("unchecked")
        final Enumeration<String> propertyNames = message.getPropertyNames();
        while (propertyNames.hasMoreElements()) {
            final String propertyName = propertyNames.nextElement();
            final String propertyValue = message.getStringProperty(propertyName);
            log.info("Bericht property {} -> {}", propertyName, propertyValue);
            result.setHeader(propertyName, propertyValue);
        }

        // content
        final String content;
        if (message instanceof TextMessage) {
            content = ((TextMessage) message).getText();
        } else {
            throw new IllegalArgumentException("Message type niet ondersteund: " + message.getClass());
        }
        result.setContent(content);

        return result;
    }
}
