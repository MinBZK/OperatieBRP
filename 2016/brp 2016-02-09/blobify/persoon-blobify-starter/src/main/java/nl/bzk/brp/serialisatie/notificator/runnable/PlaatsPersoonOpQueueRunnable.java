/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie.notificator.runnable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import nl.bzk.brp.serialisatie.notificator.exceptions.CommandException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * Plaats persoon op queue runnable klasse die parallel verwerkt kan worden.
 */
public class PlaatsPersoonOpQueueRunnable implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlaatsPersoonOpQueueRunnable.class);

    /**
     * De JMS property naam gebruikt om de sleutel voor een persoon te communiceren.
     */
    private static final String PERSOON_SLEUTEL = "persoonSleutel";

    private Integer persoonId;

    private JmsTemplate persoonSerialisatieJmsTemplate;

    @Override
    public final void run() {
        final MessageCreator jmsBericht = creeerJmsBericht(persoonId);

        if (voldoetAanVoorwaarden(jmsBericht)) {
            verstuurJmsBericht(jmsBericht);
        } else {
            throw new CommandException("Het JMS bericht kan niet gepubliceerd worden aangezien het JMS bericht, "
                                       + "de JMS template en de persoon id gevuld dienen te zijn.");
        }
    }

    /**
     * Verstuurt het jms bericht naar de queue.
     *
     * @param jmsBericht JMS bericht
     */
    private void verstuurJmsBericht(final MessageCreator jmsBericht) {
        try {
            LOGGER.debug("Thread#" + Thread.currentThread().getId()
                                 + ": Het volgende id wordt op de queue gezet: " + persoonId);
            persoonSerialisatieJmsTemplate.send(jmsBericht);
        } catch (org.springframework.jms.JmsException e) {
            throw new CommandException("Het publiceren van de persoon identifier is mislukt ivm een JMS exceptie.",
                                       e);
        }
    }

    /**
     * Controleert of aan de voorwaarden wordt voldaan om het JMS bericht opd e queue te plaatsen.
     *
     * @param jmsBericht jms bericht
     * @return true als voldoet, anders false
     */
    private boolean voldoetAanVoorwaarden(final MessageCreator jmsBericht) {
        return jmsBericht != null
                && persoonSerialisatieJmsTemplate != null
                && persoonId != null;
    }

    /**
     * Creeert JMS bericht op basis van persoon identifier.
     *
     * @param persoonIdentifier de persoon identifier
     * @return de message creator
     */
    private MessageCreator creeerJmsBericht(final Integer persoonIdentifier) {
        try {
            final MessageCreator messageCreator = new MessageCreator() {

                @Override
                public Message createMessage(final Session session) throws JMSException {
                    final Message message = session.createMessage();
                    message.setIntProperty(PERSOON_SLEUTEL, persoonIdentifier);
                    return message;
                }
            };

            return messageCreator;
        } catch (Exception e) {
            throw new CommandException("Het aanmaken van een JMS bericht is mislukt.", e);
        }
    }

    /**
     * Zet de persoon id.
     *
     * @param persoonId persoon id
     */
    public final void setPersoonId(final Integer persoonId) {
        this.persoonId = persoonId;
    }

    /**
     * Zet de persoon serialisatie jms template.
     *
     * @param persoonSerialisatieJmsTemplate persoon serialisatie jms template
     */
    public final void setPersoonSerialisatieJmsTemplate(final JmsTemplate persoonSerialisatieJmsTemplate) {
        this.persoonSerialisatieJmsTemplate = persoonSerialisatieJmsTemplate;
    }
}
