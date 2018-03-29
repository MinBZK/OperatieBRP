/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.register.client;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.factory.SyncBerichtFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.LeesPartijRegisterAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.PartijRegisterType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.PartijType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.RolType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesPartijRegisterAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesPartijRegisterVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.register.Rol;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class DummyPartijRegisterService implements MessageListener {

    private static final Logger LOG = LoggerFactory.getLogger();

    private JmsTemplate jmsTemplate;

    @Required
    public void setJmsTemplate(final JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void onMessage(final Message message) {
        LOG.info("Partij register verzoek ontvangen");
        try {
            final SyncBericht syncBericht = SyncBerichtFactory.SINGLETON.getBericht(leesMessage(message));
            if (syncBericht instanceof LeesPartijRegisterVerzoekBericht) {
                final LeesPartijRegisterAntwoordType type = new LeesPartijRegisterAntwoordType();
                type.setStatus(StatusType.OK);
                type.setPartijRegister(new PartijRegisterType());
                type.getPartijRegister().getPartij().add(maakPartijType("0599", "059901", null, Collections.singletonList(Rol.AFNEMER)));
                type.getPartijRegister().getPartij().add(maakPartijType("0429", "042901", null, Arrays.asList(Rol.AFNEMER, Rol.BIJHOUDINGSORGAAN_COLLEGE)));
                type.getPartijRegister().getPartij().add(maakPartijType("0699", "069901", 20090101, Collections.singletonList(Rol.AFNEMER)));
                type.getPartijRegister().getPartij().add(maakPartijType("0717", "071701", null, Collections.emptyList()));
                type.getPartijRegister().getPartij().add(maakPartijType("1992", "199902", 20000101, Collections.emptyList()));

                final LeesPartijRegisterAntwoordBericht result = new LeesPartijRegisterAntwoordBericht(type);
                result.setMessageId(generateMessageId());

                LOG.info("Versturen partij register antwoord");
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

    private PartijType maakPartijType(final String gemeenteCode, final String partijCode, final Integer datumOvergangNaarBrp, final List<Rol> rollen) {
        final PartijType partij = new PartijType();
        partij.setGemeenteCode(gemeenteCode);
        partij.setPartijCode(partijCode);
        partij.setDatumBrp(datumOvergangNaarBrp == null ? null : BigInteger.valueOf(datumOvergangNaarBrp));
        partij.getRollen().addAll(rollen.stream().map(rol -> RolType.valueOf(rol.toString())).collect(Collectors.toList()));
        return partij;
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
