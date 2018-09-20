/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.jms.service;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.JMSException;
import javax.jms.QueueBrowser;
import javax.jms.QueueConnection;
import javax.jms.QueueSession;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.Message;
import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class QueueBrowserServiceImpl implements QueueBrowserService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(QueueBrowserServiceImpl.class);

	@Inject
	@Named("mutatieActieMessageQueue")
	private ActiveMQQueue mutatieActieMessageQueue;

	@Inject
    @Named("mutatieBerichtMessageQueue")
    private ActiveMQQueue mutatieBerichtMessageQueue;

	@Inject
	@Named("connectionFactory")
	ActiveMQConnectionFactory connFactory;

	public final static String JMS_MESSAGE_AMOUNT = "messageAmount";
	public final static String JMS_MESSAGE_ACTION_BSNS = "actieBsns";

	@Override
    public Integer haalAantalBerichtenInMutatieActieQueueOp() throws JMSException {
		return (Integer) haalQueueStatsOp(mutatieActieMessageQueue).get(JMS_MESSAGE_AMOUNT);
	}

	@Override
    public Integer haalAantalBerichtenInMutatieBerichtQueueOp() throws JMSException {
        return (Integer) haalQueueStatsOp(mutatieBerichtMessageQueue).get(JMS_MESSAGE_AMOUNT);
    }

	private Map<String, Object> haalQueueStatsOp(final ActiveMQQueue queueParam) throws JMSException {
		QueueConnection queueConn = connFactory.createQueueConnection();
		QueueBrowser queueBrowser = creeerQueueBrowser(queueConn, queueParam);
		Map<String, Object> queueStatsMap = haalQueueStatsMapOp(queueBrowser);
		queueConn.close();

		return queueStatsMap;
	}

	private Map<String, Object> haalQueueStatsMapOp(
			final QueueBrowser queueBrowser) throws JMSException {
		Map<String, Object> queueStatsMap = new HashMap<String, Object>();

		Enumeration e = queueBrowser.getEnumeration();
		int numMsgs = 0;
		List<String> actionIds = new ArrayList<String>();
		List<String> betrokkenBsns = new ArrayList<String>();

		while (e.hasMoreElements()) {
			Message message = (Message) e.nextElement();
			actionIds.add("" + message.getLongProperty(MutatieServiceImpl.JMS_MESSAGE_ACTION_ID));
			numMsgs++;
		}

		//TODO: log weghalen
//		LOGGER.debug("Queue {} has {} messages: {}", new Object[] {queueBrowser.getQueue(), numMsgs, actionIds});

		queueStatsMap.put(JMS_MESSAGE_AMOUNT, numMsgs);
		queueStatsMap.put(MutatieServiceImpl.JMS_MESSAGE_ACTION_ID, actionIds);
		queueStatsMap.put(JMS_MESSAGE_ACTION_BSNS, betrokkenBsns);

		return queueStatsMap;
	}

	private QueueBrowser creeerQueueBrowser(final QueueConnection queueConnParam, final ActiveMQQueue queueParam) throws JMSException {
		QueueSession queueSession = queueConnParam.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		QueueBrowser queueBrowser = queueSession.createBrowser(queueParam);

		queueConnParam.start();
		return queueBrowser;
	}


}
