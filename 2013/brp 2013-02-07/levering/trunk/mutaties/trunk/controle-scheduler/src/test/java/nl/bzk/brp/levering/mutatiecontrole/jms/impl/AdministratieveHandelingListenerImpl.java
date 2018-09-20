/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatiecontrole.jms.impl;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import nl.bzk.brp.levering.mutatiecontrole.jms.AdministratieveHandelingListener;
import nl.bzk.brp.levering.mutatiecontrole.service.AdministratieveHandelingVerwerkerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Toehoorder voor berichten op een JMS queue/topic met administratieve handeling id's.
 */
@Component
public class AdministratieveHandelingListenerImpl implements AdministratieveHandelingListener, MessageListener {

    private final Logger logger = LoggerFactory.getLogger(AdministratieveHandelingListenerImpl.class);

    @Autowired
    private AdministratieveHandelingVerwerkerService administratieveHandelingVerwerkerService;

    private static int verwerkteAdministratieveHandelingen = 0;

    @Override
    public void onMessage(final Message message) {
        try {
            final Long handelingId = message.getLongProperty(AdministratieveHandelingVerwerkerImpl.JMS_MESSAGE_ADMINISTRATIEVE_HANDELING_ID);

            administratieveHandelingVerwerkerService.verwerkAdministratieveHandeling(handelingId);
            verwerkteAdministratieveHandelingen++;

        } catch (JMSException e) {

            logger.error("Het ontvangen van een JMS bericht met een Administratieve Handeling ID is mislukt.", e);

        }

    }

    @Override
    public int getVerwerkteAdministratieveHandelingen() {
        return verwerkteAdministratieveHandelingen;
    }

}
