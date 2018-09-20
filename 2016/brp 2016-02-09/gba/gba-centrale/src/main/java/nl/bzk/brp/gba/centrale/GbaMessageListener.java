/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.centrale;

import java.util.UUID;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import nl.bzk.brp.gba.centrale.services.GbaService;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * Default message listener.
 */
public final class GbaMessageListener implements MessageListener {

    /**
     * JMS property waarop de bericht referentie wordt gezet.
     */
    public static final String BERICHT_REFERENTIE = "iscBerichtReferentie";

    /**
     * JMS property waarop de correlatie referentie wordt gezet.
     */
    public static final String CORRELATIE_REFERENTIE = "iscCorrelatieReferentie";

    private JmsTemplate antwoordTemplate;
    private GbaService gbaService;

    public void setAntwoordTemplate(final JmsTemplate antwoordTemplate) {
        this.antwoordTemplate = antwoordTemplate;
    }

    @Required
    public void setService(final GbaService pGbaService) {
        gbaService = pGbaService;
    }

    @Override
    public void onMessage(final Message message) {
        // Read message
        final String verzoek = readMessage(message);
        final String berichtReferentie;
        try {
            berichtReferentie = message.getStringProperty(BERICHT_REFERENTIE);
        } catch (final JMSException e) {
            throw new JmsException("Kan berichtreferentie niet lezen", e) {
                private static final long serialVersionUID = 1L;
            };
        }

        final String antwoord = gbaService.verwerk(verzoek, berichtReferentie);
        if (antwoord != null) {
            // Verstuur
            antwoordTemplate.send(new MessageCreator() {
                @Override
                public Message createMessage(final Session session) throws JMSException {
                    final Message resultaat = session.createTextMessage(antwoord);
                    resultaat.setStringProperty(BERICHT_REFERENTIE, UUID.randomUUID().toString());
                    resultaat.setStringProperty(CORRELATIE_REFERENTIE, berichtReferentie);
                    return resultaat;
                }
            });
        }
    }

    private static String readMessage(final Message message) {
        String content;
        if (message instanceof TextMessage) {
            try {
                content = ((TextMessage) message).getText();
            } catch (final JMSException e) {
                throw new JmsException("Kan bericht niet lezen.", e) {
                    private static final long serialVersionUID = 1L;
                };
            }
        } else {
            throw new IllegalArgumentException("Message type niet ondersteund: " + message.getClass());
        }
        return content;
    }

}
