/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.stappen.administratievehandeling;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import nl.bzk.brp.bijhouding.business.stappen.context.BijhoudingBerichtContext;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.internbericht.AdministratieveHandelingVerwerktOpdracht;
import nl.bzk.brp.serialisatie.JsonStringSerializer;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;


/**
 * Deze stap zal de Administratieve Handeling op de JMS Queue tbv mutaties publiceren.
 *
 */
@Component
public class PubliceerAdministratieveHandelingStap {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final String QUEUE_ONGECONFIGUREERD = "queue://''";

    /**
     * De administratieve handeling jms template.
     */
    @Inject
    @Named("administratieveHandelingJmsTemplate")
    private JmsTemplate administratieveHandelingJmsTemplate;

    private final JsonStringSerializer<AdministratieveHandelingVerwerktOpdracht> serialiseerder
        = new JsonStringSerializer<>(AdministratieveHandelingVerwerktOpdracht.class);

    /**
     * Voer de stap uit.
     * @param context de bijhoudingberichtcontext
     */
    public void voerUit(final BijhoudingBerichtContext context) {
        if (isConfiguratieBijhoudingNotificatorGeconfigureerd()) {
            creeerEnPubliceerJmsBericht(context);
        } else {
            LOGGER.warn("Er is geen configuratie ingesteld voor de BijhoudingNotificator,"
                + " daarom wordt er geen Administratieve Handeling Id gepubliceerd op de levering JMS queue.");
        }
    }


    /**
     * Controleert of de configuratie bijhouding notificator geconfigureerd is.
     *
     * @return true, als er valide waarden geconfigureerd zijn voor de bijhouding notificator
     */
    private boolean isConfiguratieBijhoudingNotificatorGeconfigureerd() {
        return administratieveHandelingJmsTemplate.getConnectionFactory() != null
                && administratieveHandelingJmsTemplate.getDefaultDestination() != null
                && !administratieveHandelingJmsTemplate.getDefaultDestination().toString()
                .equals(QUEUE_ONGECONFIGUREERD);
    }

    /**
     * Creeer en publiceer jms bericht.
     *
     * @param context de context van het stappenmechanisme
     */
    @SuppressWarnings("checkstyle:illegalcatch")
    private void creeerEnPubliceerJmsBericht(final BijhoudingBerichtContext context) {
        try {
            final Long administratieveHandelingId = context.getResultaatId();

            if (administratieveHandelingId == null) {
                LOGGER.warn(
                        "De stappen context bevat geen Administratieve Handeling id, er wordt dus niet gepubliceerd");
            } else {
                // TODO meer informatie in het bericht stoppen
                final AdministratieveHandelingVerwerktOpdracht opdracht =
                        new AdministratieveHandelingVerwerktOpdracht(administratieveHandelingId, null, null);
                final MessageCreator messageCreator = maakMessageCreator(opdracht);
                publiceerJmsBericht(messageCreator);

                LOGGER.info("Administratieve handeling '{}' gepubliceerd", administratieveHandelingId);
            }
        } catch (final JmsException exceptie) {
            LOGGER.error("Het publiceren van de administratieve handeling is mislukt ivm een JMS exceptie.", exceptie);
        } catch (final RuntimeException exceptie) {
            LOGGER.error("Het publiceren van de administratieve handeling is mislukt door een onbekende fout.",
                    exceptie);
        }
    }

    /**
     * Creeer jms bericht.
     *
     * @param verwerkingsOpdracht de administratieve handeling verwerkings opdracht
     * @return MessageCreator object dat een JMS bericht voorstelt.
     */
    private MessageCreator maakMessageCreator(final AdministratieveHandelingVerwerktOpdracht verwerkingsOpdracht) {

        return new MessageCreator() {

            @Override
            public Message createMessage(final Session session) throws JMSException {
                final TextMessage message = session.createTextMessage();
                message.setText(serialiseerder.serialiseerNaarString(verwerkingsOpdracht));
                return message;
            }
        };
    }

    /**
     * Publiceer het JMS bericht naar de JMS queue.
     *
     * @param messageCreator het jms bericht
     */
    private void publiceerJmsBericht(final MessageCreator messageCreator) {
        administratieveHandelingJmsTemplate.send(messageCreator);
    }
}
