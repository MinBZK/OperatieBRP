/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatieverwerker.jms;

import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import nl.bzk.brp.levering.mutatieverwerker.service.AdministratieveHandelingVerwerkerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Toehoorder voor berichten op een JMS queue/topic met administratieve handeling id's.
 */
@Component
public class AdministratieveHandelingListener implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdministratieveHandelingListener.class);

    /**
     * De sleutel van administratieve handeling in het JMS bericht.
     */
    private static final String ADMINISTRATIEVE_HANDELING_ID = "administratieveHandelingId";

    @Inject
    private AdministratieveHandelingVerwerkerService administratieveHandelingVerwerkerService;

    @Override
    public void onMessage(final Message message) {
        try {
            final Long handelingId = message.getLongProperty(ADMINISTRATIEVE_HANDELING_ID);
            LOGGER.debug("ontvangen id '{}'", handelingId);

            administratieveHandelingVerwerkerService.verwerkAdministratieveHandeling(handelingId);
        } catch (JMSException e) {
            LOGGER.error("Het ontvangen van een JMS bericht met een Administratieve Handeling ID is mislukt.", e);
        }
    }

}
