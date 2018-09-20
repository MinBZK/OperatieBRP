/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.register.client;

import java.util.UUID;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.factory.SyncBerichtFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AutorisatieRegisterType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AutorisatieRegisterVoorkomenType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.LeesAutorisatieRegisterAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesAutorisatieRegisterAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesAutorisatieRegisterVerzoekBericht;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class DummyAutorisatieRegisterService implements MessageListener {

    private static final Logger LOG = LoggerFactory.getLogger();

    private JmsTemplate jmsTemplate;

    @Required
    public void setJmsTemplate(final JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void onMessage(final Message message) {
        try {
            final SyncBericht syncBericht = SyncBerichtFactory.SINGLETON.getBericht(leesMessage(message));
            if (syncBericht instanceof LeesAutorisatieRegisterVerzoekBericht) {
                final LeesAutorisatieRegisterAntwoordType type = new LeesAutorisatieRegisterAntwoordType();
                type.setStatus(StatusType.OK);
                type.setAutorisatieRegister(new AutorisatieRegisterType());
                type.getAutorisatieRegister().getAutorisatie().add(maakAutorisatieRegisterVoorkomenType("059901", 35423, 123, 123, null, null));

                final LeesAutorisatieRegisterAntwoordBericht result = new LeesAutorisatieRegisterAntwoordBericht(type);
                result.setMessageId(generateMessageId());

                jmsTemplate.send(new MessageCreator() {
                    @Override
                    public Message createMessage(final Session session) throws JMSException {
                        return session.createTextMessage(result.format());
                    }
                });
            }
        } catch (final JMSException e) {
            LOG.warn("Kon binnenkomend bericht voor register niet verwerken. Bericht wordt genegeerd ...", e);
        }
    }

    protected String generateMessageId() {
        return UUID.randomUUID().toString();
    }

    private AutorisatieRegisterVoorkomenType maakAutorisatieRegisterVoorkomenType(
        final String partijCode,
        final int toegangLeveringsautorisatieId,
        final Integer plaatsenAfnIndDienstId,
        final Integer verwijderenAfnIndDienstId,
        final Integer bevragenPersoonDienstId,
        final Integer bevragenAdresDienstId)
    {
        final AutorisatieRegisterVoorkomenType autorisatie = new AutorisatieRegisterVoorkomenType();
        autorisatie.setPartijCode(partijCode);
        autorisatie.setToegangLeveringsautorisatieId(toegangLeveringsautorisatieId);
        autorisatie.setPlaatsenAfnemersindicatiesDienstId(plaatsenAfnIndDienstId);
        autorisatie.setVerwijderenAfnemersindicatiesDienstId(verwijderenAfnIndDienstId);
        autorisatie.setBevragenPersoonDienstId(bevragenPersoonDienstId);
        autorisatie.setBevragenAdresDienstId(bevragenAdresDienstId);
        return autorisatie;
    }

    private static String leesMessage(final Message message) throws JMSException {
        if (message == null) {
            return null;
        }

        // content
        final String content;
        if (message instanceof TextMessage) {
            content = ((TextMessage) message).getText();
        } else {
            throw new IllegalArgumentException("Message type niet ondersteund: " + message.getClass());
        }

        return content;
    }
}
