/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.vrijbericht;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.logging.MDCProcessor;
import nl.bzk.migratiebrp.bericht.model.Bericht;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.JMSConstants;
import org.springframework.jms.core.MessageCreator;

/**
 * Message creator voor Adhoc Zoeken antwoorden naar ISC.
 */
public class VrijBerichtAntwoordNaarIscMessageCreator implements MessageCreator {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private Bericht bericht;

    VrijBerichtAntwoordNaarIscMessageCreator(final Bericht bericht) {
        this.bericht = bericht;
    }

    @Override
    public Message createMessage(final Session session) throws JMSException {
        try {
            final Message message = session.createTextMessage(bericht.format());
            MDCProcessor.registreerVerwerkingsCode(message);
            message.setStringProperty(JMSConstants.BERICHT_REFERENTIE, bericht.getMessageId());
            message.setStringProperty(JMSConstants.CORRELATIE_REFERENTIE, bericht.getCorrelationId());
            return message;
        } catch (final BerichtInhoudException exceptie) {
            LOGGER.debug("Fout tijdens maken JMS bericht", exceptie);
            return null;
        }
    }
}
