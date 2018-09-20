/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.jms.stap;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import nl.bzk.brp.business.jms.LevMutAdmHandBerichtContext;
import nl.bzk.brp.business.jms.service.MutatieBerichtMessageListener;
import nl.bzk.brp.business.levering.LEVLeveringBijgehoudenPersoonLv;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.support.converter.SimpleMessageConverter;


public class PlaatsBerichtenOpQueueStap extends AbstractBerichtVerwerkingsStap {

    private static final Logger                 LOGGER    = LoggerFactory.getLogger(PlaatsBerichtenOpQueueStap.class);

    private static final SimpleMessageConverter converter = new SimpleMessageConverter();

    @Inject
    @Named("mutatieBerichtTemplate")
    private JmsTemplate                         mutatieBerichtTemplate;

    @Override
    public StapResultaat voerVerwerkingsStapUitVoorBericht(final LevMutAdmHandBerichtContext context) {
        if (context.getUitBerichten() != null) {
            for (final LEVLeveringBijgehoudenPersoonLv uitBericht : context.getUitBerichten().values()) {

                final long berichtId = Long.valueOf(RandomStringUtils.randomNumeric(5));

                MessageCreator messageCreator = new MessageCreator() {

                    @Override
                    public Message createMessage(final Session session) throws JMSException {
                        Message message = converter.toMessage(uitBericht, session);
                        message.setLongProperty(MutatieBerichtMessageListener.JMS_MESSAGE_BERICHT_ID, berichtId);
                        return message;
                    }
                };

                mutatieBerichtTemplate.send(messageCreator);
            }
        } else {
            LOGGER.debug("Er is geen uit bericht voor actieId " + context.getActieModel().getId());
        }

        return StapResultaat.DOORGAAN_MET_VERWERKING;
    }
}
