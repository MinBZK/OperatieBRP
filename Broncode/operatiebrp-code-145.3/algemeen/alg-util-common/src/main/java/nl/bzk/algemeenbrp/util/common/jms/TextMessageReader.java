/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.common.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

/**
 *
 */
public class TextMessageReader {

    private final Message message;

    /**
     * Constructor.
     * @param message JMS Message welke gelzen moet worden.
     */
    public TextMessageReader(Message message) {
        this.message = message;
    }

    /**
     * Leest van de TextMessage de tekst
     * @return de tekst van het bericht
     */
    public String readMessage() {
        String content;
        if (message instanceof TextMessage) {
            try {
                content = ((TextMessage) message).getText();
            } catch (final JMSException e) {
                throw new JmsException(e);
            }
        } else {
            throw new IllegalArgumentException("Message type niet ondersteund voor vrij bericht: " + message.getClass());
        }
        return content;
    }
}
