/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.jms.service;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.naming.NamingException;

import nl.bzk.brp.dataaccess.special.ActieRepository;
import nl.bzk.brp.model.objecttype.operationeel.ActieModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class MutatieServiceImpl implements MutatieService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MutatieServiceImpl.class);

    private final long COLLECT_DELAY = 10 * 1000;

    public final static String JMS_MESSAGE_ACTION_ID = "actieId";
    public final static String JMS_MESSAGE_ACTION_BSNS = "actieBsns";

    @Inject
    @Named("mutatieActieTemplate")
    private JmsTemplate mutatieTemplate;

    @Inject
    private ActieRepository actieRepository;

    @Scheduled(fixedDelay = COLLECT_DELAY)
    public void collectOnverwerkteActies() throws JMSException, NamingException {
        int i = 0;
        for (final ActieModel actieModel : actieRepository.findByTijdstipVerwerkingMutatieIsNull()) {
            MessageCreator messageCreator = new MessageCreator() {
                @Override
                public Message createMessage(final Session session) throws JMSException {
                    final String text = "ActieMessage met actieId=" + actieModel.getId();
                    Message message = session.createMessage();
                    message.setLongProperty(JMS_MESSAGE_ACTION_ID, actieModel.getId());
                    //LOGGER.debug("Sending message: {}, actieId={}", text, actieModel.getId());
                    return message;
                }
            };

            mutatieTemplate.send(messageCreator);

            if (i++ > 30) {
                break;
            }
        }
    }

}
