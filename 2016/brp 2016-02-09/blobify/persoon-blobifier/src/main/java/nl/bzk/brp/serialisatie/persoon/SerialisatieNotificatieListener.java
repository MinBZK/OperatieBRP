/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie.persoon;

import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import nl.bzk.brp.vergrendeling.VergrendelFout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * De listener die luistert naar een queue waar persoon sleutels op worden gezet voor personen die geserialiseert
 * moeten worden naar een blob.
 */
@Component
public class SerialisatieNotificatieListener implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(SerialisatieNotificatieListener.class);

    /**
     * De JMS property naam gebruikt om de sleutel voor een persoon te communiceren.
     */
    private static final String PERSOON_SLEUTEL = "persoonSleutel";

    @Inject
    private PersoonSerialiseerder persoonSerialiseerder;

    @Override
    public final void onMessage(final Message message) {
        Integer persoonSleutel = null;
        try {
            persoonSleutel = message.getIntProperty(PERSOON_SLEUTEL);
            LOGGER.debug("Ontvangen persoon sleutel voor serialisatie '{}'", persoonSleutel);

            persoonSerialiseerder.serialiseerPersoon(persoonSleutel);
        } catch (JMSException e) {
            LOGGER.error("Het ontvangen van een JMS bericht met een persoon sleutel is mislukt.{}",
                         message.toString(), e);
        } catch (VergrendelFout vergendelFout) {
            LOGGER.error("Fout bij het vergrendelen van de sleutel '{}', persoon kon niet geblobified worden.",
                         persoonSleutel, vergendelFout);
        }
    }
}
