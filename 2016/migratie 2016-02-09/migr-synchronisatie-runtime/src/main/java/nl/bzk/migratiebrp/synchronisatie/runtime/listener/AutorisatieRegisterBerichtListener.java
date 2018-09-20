/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.listener;

import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import nl.bzk.migratiebrp.bericht.model.JMSConstants;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.factory.SyncBerichtFactory;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesAutorisatieRegisterAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesAutorisatieRegisterVerzoekBericht;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.AutorisatieRegisterService;
import nl.bzk.migratiebrp.util.common.logging.FunctioneleMelding;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.util.common.logging.MDC;
import nl.bzk.migratiebrp.util.common.logging.MDC.MDCCloser;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * Listener om autorisatie register berichten te verwerken.
 */
public final class AutorisatieRegisterBerichtListener implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private AutorisatieRegisterService autorisatieRegisterService;

    @Inject
    private JmsTemplate jmsTemplate;

    /**
     * Zet het JMS Template.
     *
     * @param jmsTemplate
     *            Het te zetten JMS template.
     */
    @Required
    public void setJmsTemplate(final JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    @SuppressWarnings("checkstyle:illegalcatch")
    public void onMessage(final Message message) {
        try (MDCCloser verwerkingCloser = MDC.startVerwerking()) {
            LOGGER.info("Start ophalen autorisatie register.");

            final LeesAutorisatieRegisterAntwoordBericht bericht;
            try {
                final SyncBericht synchronisatieBericht = SyncBerichtFactory.SINGLETON.getBericht(((TextMessage) message).getText());
                LeesAutorisatieRegisterVerzoekBericht verzoek;

                if (synchronisatieBericht instanceof LeesAutorisatieRegisterVerzoekBericht) {
                    verzoek = (LeesAutorisatieRegisterVerzoekBericht) synchronisatieBericht;
                } else {
                    throw new JMSException("Binnenkomend bericht voor autorisatie register was van een onbekend type. Bericht wordt genegeerd ...");
                }

                bericht = autorisatieRegisterService.verwerkBericht(verzoek);
                bericht.setCorrelationId(verzoek.getMessageId());
                LOGGER.info("[Bericht {}]: Bericht versturen naar queue.", bericht.getMessageId());
                verstuurAntwoord(bericht);
                LOGGER.info(FunctioneleMelding.SYNC_AUTORISATIEREGISTER_VERWERKT);
            } catch (final JmsException je) {
                LOGGER.error("Error opgetreden bij het lezen van het bericht van de queue: " + message, je);
            } catch (final Exception e) {
                // Alle fouten afvangen en een melding teruggeven daarvan, dit om te voorkomen dat de applicatie stopt.
                LOGGER.error("Onverwachte fout opgetreden bij het verwerken van bericht: " + message, e);
            }

            LOGGER.info("Autorisatie register succesvol opgehaald en verzonden.");
        }
    }

    private void verstuurAntwoord(final LeesAutorisatieRegisterAntwoordBericht bericht) {
        try {

            jmsTemplate.send(new MessageCreator() {
                @Override
                public Message createMessage(final Session session) throws JMSException {
                    final Message message = session.createTextMessage(bericht.format());
                    message.setStringProperty(JMSConstants.BERICHT_REFERENTIE, bericht.getMessageId());
                    message.setStringProperty(JMSConstants.CORRELATIE_REFERENTIE, bericht.getCorrelationId());
                    return message;
                }
            });
            LOGGER.info("[Bericht {}]: Bericht succesvol verstuurd naar topic.", bericht.getMessageId());
        } catch (final JmsException e) {
            LOGGER.error("[Bericht {}]: Onverwachte fout bij versturen van bericht naar topic.", bericht.getMessageId(), e);
        }
    }

}
