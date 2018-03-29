/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.listener;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.JMSConstants;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.factory.SyncBerichtFactory;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesPartijRegisterAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesPartijRegisterVerzoekBericht;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.PartijRegisterService;
import nl.bzk.migratiebrp.util.common.logging.FunctioneleMelding;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;

/**
 * Listener om partij register berichten te verwerken.
 */
public final class PartijRegisterBerichtListener implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private final PartijRegisterService partijRegisterService;

    private JmsTemplate jmsTemplate;

    /**
     * Constructor
     * @param partijRegisterService partijregister service
     * @param jmsTemplate jms template
     */
    @Inject
    public PartijRegisterBerichtListener(final PartijRegisterService partijRegisterService,
                                         @Named(value = "brpQueueJmsTemplate") final JmsTemplate jmsTemplate) {
        this.partijRegisterService = partijRegisterService;
        this.jmsTemplate = jmsTemplate;
    }

    /**
     * Zet het JMS Template.
     * @param jmsTemplate Het te zetten JMS template.
     */
    @Required
    public void setJmsTemplate(final JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void onMessage(final Message message) {
        LOGGER.info("Start ophalen partij register.");

        final LeesPartijRegisterAntwoordBericht bericht;
        try {

            final SyncBericht synchronisatieBericht = SyncBerichtFactory.SINGLETON.getBericht(((TextMessage) message).getText());
            LeesPartijRegisterVerzoekBericht verzoek;

            if (synchronisatieBericht instanceof LeesPartijRegisterVerzoekBericht) {
                verzoek = (LeesPartijRegisterVerzoekBericht) synchronisatieBericht;
            } else {
                throw new JMSException("Binnenkomend bericht voor partij register was van een onbekend type. Bericht wordt genegeerd ...");
            }

            bericht = partijRegisterService.verwerkBericht(verzoek);
            bericht.setCorrelationId(verzoek.getMessageId());
            LOGGER.info("[Bericht {}]: Bericht versturen naar queue.", bericht.getMessageId());
            verstuurAntwoord(bericht);

            LOGGER.info("Partij register succesvol opgehaald en verzonden.");
            LOGGER.info(FunctioneleMelding.SYNC_PARTIJREGISTER_VERWERKT);

        } catch (final JmsException je) {
            LOGGER.error("Error opgetreden bij het lezen van het bericht van de queue: " + message, je);
        } catch (final Exception e) {
            // Alle fouten afvangen en een melding teruggeven daarvan, dit om te voorkomen dat de applicatie stopt.
            LOGGER.error("Onverwachte fout opgetreden bij het verwerken van bericht: " + message, e);
        }
    }

    private void verstuurAntwoord(final LeesPartijRegisterAntwoordBericht bericht) {
        try {

            jmsTemplate.send(session -> {
                final Message message = session.createTextMessage(bericht.format());
                message.setStringProperty(JMSConstants.BERICHT_REFERENTIE, bericht.getMessageId());
                message.setStringProperty(JMSConstants.CORRELATIE_REFERENTIE, bericht.getCorrelationId());
                return message;
            });
            LOGGER.info("[Bericht {}]: Bericht succesvol verstuurd naar topic.", bericht.getMessageId());
        } catch (final JmsException e) {
            LOGGER.error("[Bericht {}]: Onverwachte fout bij versturen van bericht naar topic.", bericht.getMessageId(), e);
        }
    }

}
