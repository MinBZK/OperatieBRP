/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import nl.bzk.brp.preview.dataaccess.BerichtenDao;
import nl.bzk.brp.preview.model.Bericht;
import nl.bzk.brp.preview.service.AdministratieveHandelingService;
import nl.bzk.brp.preview.service.AdministratieveHandelingServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * TODO: Add documentation.
 */
@Component
public class MutatieActieMessageListener implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(MutatieActieMessageListener.class);

    @Autowired
    private AdministratieveHandelingService administratieveHandelingService;

    @Override
    public void onMessage(final Message message) {
        try {
            Long handelingId = message.getLongProperty(AdministratieveHandelingServiceImpl.JMS_MESSAGE_ACTION_ID);

            LOGGER.debug("ontvangen id '{}'", handelingId);

            Bericht bericht = administratieveHandelingService.maakBericht(handelingId);
            administratieveHandelingService.opslaan(bericht);

            LOGGER.debug("gevonden bericht: {}", bericht);
        } catch (JMSException e) {
            // no-op
        }
    }

    public void setAdministratieveHandelingService(
            final AdministratieveHandelingService administratieveHandelingService)
    {
        this.administratieveHandelingService = administratieveHandelingService;
    }
}
