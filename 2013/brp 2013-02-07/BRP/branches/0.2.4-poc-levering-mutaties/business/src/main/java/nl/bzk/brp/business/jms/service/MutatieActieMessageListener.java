/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.jms.service;

import javax.inject.Inject;
import javax.jms.Message;
import javax.jms.MessageListener;

import nl.bzk.brp.business.jms.LevMutAdmHandBerichtContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MutatieActieMessageListener implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(MutatieActieMessageListener.class);

    @Inject
    private LevMutAdmHandBerichtVerwerker levMutAdmHandBerichtVerwerker;

    @Override
    public void onMessage(final Message message) {
	Long actieId = null;
        try {
            actieId = message.getLongProperty(MutatieServiceImpl.JMS_MESSAGE_ACTION_ID);
            LevMutAdmHandBerichtContext context = new LevMutAdmHandBerichtContext(actieId);

            levMutAdmHandBerichtVerwerker.verwerkBericht(context);
        } catch (Exception ex) {
	    //het niet verwerken van een Message in deze queue is niet erg, de onverwerkte actie komt vanzelf wel weer terug in de queue
	    LOGGER.error("Kan actie met actieId " + actieId + " niet verwerken ", ex);
        }
    }
}
