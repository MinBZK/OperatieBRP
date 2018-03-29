/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.logging.MDCProcessor;
import nl.bzk.migratiebrp.bericht.model.JMSConstants;
import nl.bzk.migratiebrp.bericht.model.brp.generated.BijhoudingRegistreerHuwelijkGeregistreerdPartnerschapMigVrz;
import nl.bzk.migratiebrp.bericht.model.brp.generated.BijhoudingRegistreerNaamGeslachtMigVrz;
import nl.bzk.migratiebrp.bericht.model.brp.generated.BijhoudingRegistreerOverlijdenMigVrz;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypeBerichtBijhouding;
import nl.bzk.migratiebrp.bericht.model.brp.xml.BrpXml;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

/**
 * Verstuurt een toevallige gebeurtenis naar BRP.
 */
@Component
public class ToevalligeGebeurtenisVerzender {

    private static final ObjectFactory OBJECT_FACTORY = new ObjectFactory();
    private static final Logger LOG = LoggerFactory.getLogger();

    private JmsTemplate jmsTemplate;

    private Destination destination;

    /**
     * Constructor.
     * @param jmsTemplate template om jms berichten te versturen
     * @param destination bestemming waar de berichten naar toe moeten
     */
    @Inject
    public ToevalligeGebeurtenisVerzender(@Named(value = "brpQueueJmsTemplate") final JmsTemplate jmsTemplate,
                                          @Named(value = "gbaToevalligeGebeurtenissen") final Destination destination) {
        this.jmsTemplate = jmsTemplate;
        this.destination = destination;
    }

    /**
     * Verstuurt een BRP toevallige gebeurtenis opdracht naar de betreffende destination.
     * @param opdracht De te versturen opdracht.
     * @param verzoekBerichtId Het messageId van het binnenkomende verzoek.
     */
    public final void verstuurBrpToevalligeGebeurtenisOpdracht(final ObjecttypeBerichtBijhouding opdracht, final String verzoekBerichtId) {
        LOG.debug("Toevallige gebeurtenis opdracht wordt verstuurd.");
        jmsTemplate.send(destination, new ToevalligeGebeurtenisMessageCreator(opdracht, verzoekBerichtId));
    }

    /**
     * MessagCreator voor versturen ToevalligeGebeurtenis.
     */
    private static class ToevalligeGebeurtenisMessageCreator implements MessageCreator {

        private final ObjecttypeBerichtBijhouding opdracht;
        private final String verzoekBerichtId;

        /**
         * Maakt MessagCreator.
         * @param opdracht te versturen opdracht
         * @param verzoekBerichtId bericht id
         */
        ToevalligeGebeurtenisMessageCreator(final ObjecttypeBerichtBijhouding opdracht, final String verzoekBerichtId) {
            this.opdracht = opdracht;
            this.verzoekBerichtId = verzoekBerichtId;
        }

        @Override
        public Message createMessage(final Session session) throws JMSException {
            final String berichtTekst;
            if (opdracht instanceof BijhoudingRegistreerNaamGeslachtMigVrz) {
                berichtTekst =
                        BrpXml.SINGLETON.elementToString(
                                OBJECT_FACTORY.createIscMigRegistreerNaamGeslacht((BijhoudingRegistreerNaamGeslachtMigVrz) opdracht));
            } else if (opdracht instanceof BijhoudingRegistreerHuwelijkGeregistreerdPartnerschapMigVrz) {
                berichtTekst =
                        BrpXml.SINGLETON.elementToString(
                                OBJECT_FACTORY.createIscMigRegistreerHuwelijkGeregistreerdPartnerschap(
                                        (BijhoudingRegistreerHuwelijkGeregistreerdPartnerschapMigVrz) opdracht));
            } else if (opdracht instanceof BijhoudingRegistreerOverlijdenMigVrz) {
                berichtTekst =
                        BrpXml.SINGLETON.elementToString(
                                OBJECT_FACTORY.createIscMigRegistreerOverlijden((BijhoudingRegistreerOverlijdenMigVrz) opdracht));
            } else {
                throw new IllegalStateException("Bericht wordt nog niet ondersteund " + opdracht.getClass().getName());
            }

            LOG.debug("Bericht: " + berichtTekst);
            final Message message = session.createTextMessage(berichtTekst);
            MDCProcessor.registreerVerwerkingsCode(message);
            LOG.debug("Message: " + message);
            message.setStringProperty(JMSConstants.BERICHT_REFERENTIE, verzoekBerichtId);
            return message;
        }
    }
}
