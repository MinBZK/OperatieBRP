/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.voisc.jms;

import java.util.Calendar;

import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import nl.moderniseringgba.isc.esb.message.JMSConstants;
import nl.moderniseringgba.isc.voisc.constants.VoiscConstants;
import nl.moderniseringgba.isc.voisc.entities.Bericht;
import nl.moderniseringgba.isc.voisc.entities.LogboekRegel;
import nl.moderniseringgba.isc.voisc.entities.Mailbox;
import nl.moderniseringgba.isc.voisc.entities.StatusEnum;
import nl.moderniseringgba.isc.voisc.exceptions.VoaException;
import nl.moderniseringgba.isc.voisc.mailbox.VoiscDbProxy;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Verwerk een terug conversie bericht.
 */
public final class VerzendenMessageHandler implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private VoiscDbProxy voiscDbProxy;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void onMessage(final Message message) {
        final LogboekRegel regel = new LogboekRegel();
        regel.setStartDatumTijd(Calendar.getInstance().getTime());
        try {
            try {
                final String messageId = message.getStringProperty(JMSConstants.BERICHT_REFERENTIE);
                final String correlationId = message.getStringProperty(JMSConstants.CORRELATIE_REFERENTIE);
                if (messageId == null || messageId.isEmpty() || !(message instanceof TextMessage)) {
                    LOGGER.error("JMS Bericht is niet valide.\n{}", message);
                    voiscDbProxy.sendEsbErrorBericht(null, "JMS bericht is niet valide.");
                } else {
                    LOGGER.debug("[Bericht {}]: Parse bericht ...", messageId);
                    verwerkBericht(message, messageId, correlationId);
                }
            } catch (final JMSException e1) {
                LOGGER.error("Kan message-id niet lezen van bericht, of geen valide bericht!\n{}", message);
                voiscDbProxy.sendEsbErrorBericht(null, "JMS bericht heeft geen messageID!");
            }
        } catch (final VoaException ve) {
            LOGGER.error("Error opgetreden bij het opslaan van het bericht: " + message, ve);
            regel.setFoutmelding(ve.getMessage());
            // CHECKSTYLE:OFF - Alle fouten afvangen en een melding teruggeven daarvan, anders crasht de app.
        } catch (final Throwable t) { // NOSONAR
            LOGGER.error("Error opgetreden bij het verwerken van bericht: " + message, t);
            regel.setFoutmelding(t.getMessage());
            // CHECKSTYLE:ON
        } finally {
            // Alleen de logboekregel opslaan als er een foutmelding is. Het gaat om de signalering naar beheer.
            if (regel != null && regel.getFoutmelding() != null && !regel.getFoutmelding().isEmpty()) {
                voiscDbProxy.saveLogboekRegel(regel);
            }
        }
    }

    /**
     * Verwerk het bericht en persist de log.
     */
    private void verwerkBericht(final Message message, final String messageId, final String correlationId)
            throws JMSException, VoaException {

        final String jmsBericht;
        final String originator = message.getStringProperty(JMSConstants.BERICHT_ORIGINATOR);
        final String recipient = message.getStringProperty(JMSConstants.BERICHT_RECIPIENT);

        final Mailbox originatorMailbox = voiscDbProxy.getMailboxByGemeentecode(originator);
        final Mailbox recipientMailbox = voiscDbProxy.getMailboxByGemeentecode(recipient);

        jmsBericht = ((TextMessage) message).getText();
        // Store message in database
        final Bericht voaBericht = new Bericht();
        voaBericht.setBerichtInhoud(jmsBericht);
        voaBericht.setAanduidingInUit(Bericht.AANDUIDING_IN_UIT_UIT);
        voaBericht.setEsbCorrelationId(correlationId);
        voaBericht.setEsbMessageId(messageId);
        voaBericht.setOriginator(originatorMailbox.getMailboxnr());
        voaBericht.setRecipient(recipientMailbox.getMailboxnr());
        voaBericht.setStatus(StatusEnum.QUEUE_RECEIVED);

        final Bericht eerderBericht = voiscDbProxy.getBerichtByEsbMessageId(correlationId);
        if (eerderBericht != null) {
            voaBericht.setBref(eerderBericht.getBref());
            voaBericht.setEref2(eerderBericht.getEref());
        }
        voiscDbProxy.saveBericht(voaBericht, VoiscConstants.RECONNECT, !VoiscConstants.INKOMEND_MB_BERICHT);
    }
}
