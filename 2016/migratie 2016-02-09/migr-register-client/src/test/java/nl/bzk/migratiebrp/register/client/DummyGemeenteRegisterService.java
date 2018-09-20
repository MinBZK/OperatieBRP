/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.register.client;

import java.math.BigInteger;
import java.util.UUID;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.factory.SyncBerichtFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.GemeenteRegisterType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.GemeenteType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.LeesGemeenteRegisterAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesGemeenteRegisterAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesGemeenteRegisterVerzoekBericht;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class DummyGemeenteRegisterService implements MessageListener {

    private static final Logger LOG = LoggerFactory.getLogger();

    private JmsTemplate jmsTemplate;

    @Required
    public void setJmsTemplate(final JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void onMessage(final Message message) {
        LOG.info("Gemeente register verzoek ontvangen");
        try {
            final SyncBericht syncBericht = SyncBerichtFactory.SINGLETON.getBericht(leesMessage(message));
            if (syncBericht instanceof LeesGemeenteRegisterVerzoekBericht) {
                final LeesGemeenteRegisterAntwoordType type = new LeesGemeenteRegisterAntwoordType();
                type.setStatus(StatusType.OK);
                type.setGemeenteRegister(new GemeenteRegisterType());
                type.getGemeenteRegister().getGemeente().add(maakGemeenteType("0599", "580599", null));
                type.getGemeenteRegister().getGemeente().add(maakGemeenteType("0429", "580429", null));
                type.getGemeenteRegister().getGemeente().add(maakGemeenteType("0699", "580699", 20090101));
                type.getGemeenteRegister().getGemeente().add(maakGemeenteType("0717", "580717", null));

                final LeesGemeenteRegisterAntwoordBericht result = new LeesGemeenteRegisterAntwoordBericht(type);
                result.setMessageId(generateMessageId());

                LOG.info("Versturen gemeente register antwoord");
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

    private GemeenteType maakGemeenteType(final String gemeenteCode, final String partijCode, final Integer datumOvergangNaarBrp) {
        final GemeenteType gemeente = new GemeenteType();
        gemeente.setGemeenteCode(gemeenteCode);
        gemeente.setPartijCode(partijCode);
        gemeente.setDatumBrp(datumOvergangNaarBrp == null ? null : BigInteger.valueOf(datumOvergangNaarBrp));
        return gemeente;
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
