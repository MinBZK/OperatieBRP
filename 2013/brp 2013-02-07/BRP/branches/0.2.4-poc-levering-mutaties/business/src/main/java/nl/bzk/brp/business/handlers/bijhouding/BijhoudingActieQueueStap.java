/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.handlers.bijhouding;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.dto.BerichtVerwerkingsResultaat;
import nl.bzk.brp.business.dto.bijhouding.AbstractBijhoudingsBericht;
import nl.bzk.brp.business.handlers.AbstractBerichtVerwerkingsStap;
import nl.bzk.brp.business.jms.service.MutatieServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * In deze stap wordt een nieuwe transactie gecreeerd voor de bijhouding.
 */
public class BijhoudingActieQueueStap extends AbstractBerichtVerwerkingsStap<AbstractBijhoudingsBericht, BerichtVerwerkingsResultaat> {

	private static final Logger LOGGER = LoggerFactory.getLogger(BijhoudingActieQueueStap.class);

	@Inject
	@Named("mutatieActieTemplate")
	private JmsTemplate mutatieTemplate;

	@Override
	public boolean voerVerwerkingsStapUitVoorBericht(final AbstractBijhoudingsBericht bericht, final BerichtContext context, final BerichtVerwerkingsResultaat resultaat) {
		return DOORGAAN_MET_VERWERKING;
	}

	@Override
	public void naVerwerkingsStapVoorBericht(final AbstractBijhoudingsBericht bericht, final BerichtContext context, final BerichtVerwerkingsResultaat resultaat) {
		// TODO: weg logger
		LOGGER.debug("Plaatsen van zojuist gecreeerde actie met id: " + context.getActieId() + " in JMS queue.");

		// Plaatsen van actie op JMS queue
		MessageCreator messageCreator = new MessageCreator() {
			@Override
			public Message createMessage(final Session session)	throws JMSException {
				Message message = session.createMessage();
				message.setLongProperty(MutatieServiceImpl.JMS_MESSAGE_ACTION_ID, context.getActieId());
				message.setObjectProperty(MutatieServiceImpl.JMS_MESSAGE_ACTION_BSNS, context.getBetrokkenBsns());
				return message;
			}
		};
		mutatieTemplate.send(messageCreator);
	}
}
