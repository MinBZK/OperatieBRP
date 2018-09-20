/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.jms.service;

import javax.inject.Inject;
import javax.jms.Message;
import javax.jms.MessageListener;

import nl.bzk.brp.business.levering.LEVLeveringBijgehoudenPersoonLv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.support.converter.SimpleMessageConverter;
import org.springframework.stereotype.Component;

@Component
public class MutatieBerichtMessageListener implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(MutatieBerichtMessageListener.class);

    public static final String JMS_MESSAGE_BERICHT_ID = "berichtID";

    private static final SimpleMessageConverter converter = new SimpleMessageConverter();

    @Inject
    MutatieBerichtVerwerker mutatieBerichtVerwerker;

    @Override
    public void onMessage(final Message message) {
	Long berichtId = null;
	try {
	    berichtId = message.getLongProperty(JMS_MESSAGE_BERICHT_ID);
	    LEVLeveringBijgehoudenPersoonLv bericht = (LEVLeveringBijgehoudenPersoonLv) converter.fromMessage(message);
	    mutatieBerichtVerwerker.verwerkBericht(bericht, berichtId);
	    message.acknowledge();
	} catch (Exception ex) {
	    //het niet verwerken van een Message in deze queue is niet erg
	    LOGGER.error("Kan actie " + berichtId + " niet verwerken ", ex);
	}
    }
}
