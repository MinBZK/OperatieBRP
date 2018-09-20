/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.centrale;

import java.util.Arrays;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import nl.bzk.brp.gba.centrale.berichten.GbaBijhoudingNotificatieBericht;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.internbericht.AdministratieveHandelingVerwerktOpdracht;
import nl.bzk.brp.serialisatie.JsonStringSerializer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class GbaBijhoudingenRouteIntegratieTest extends AbstractIntegratieTest {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Autowired
    @Named("gbaQueueBijhoudingen")
    private Destination opdrachtQueue;

    @Autowired
    @Named("brpQueueAdministratieveHandelingen")
    private Destination antwoordQueue;

    @Inject
    private JmsTemplate jmsTemplate;

    @Before
    public void cleanQueues() {
        cleanQueues(jmsTemplate, opdrachtQueue, antwoordQueue);
    }

    @Test
    public void test() throws JMSException {
        final JsonStringSerializer<GbaBijhoudingNotificatieBericht> gbaBijhoudingJSON = new JsonStringSerializer<>(GbaBijhoudingNotificatieBericht.class);

        final GbaBijhoudingNotificatieBericht gbaBijhouding = new GbaBijhoudingNotificatieBericht();
        gbaBijhouding.setAdministratieveHandelingId(1L);
        gbaBijhouding.setBijgehoudenPersoonIds(Arrays.asList(4, 5));

        LOGGER.info("Versturen GBA Bijhouding naar:" + opdrachtQueue);
        jmsTemplate.send(opdrachtQueue, new MessageCreator() {
            @Override
            public Message createMessage(final Session session) throws JMSException {
                return session.createTextMessage(gbaBijhoudingJSON.serialiseerNaarString(gbaBijhouding));
            }
        });

        LOGGER.info("Ontvangen BRP Bijhouding van: " + antwoordQueue);
        jmsTemplate.setReceiveTimeout(10000);
        final Message message = jmsTemplate.receive(antwoordQueue);
        Assert.assertNotNull("Geen bericht ontvangen", message);
        LOGGER.info("message: " + message);

        Assert.assertTrue("Bericht niet een text message", message instanceof TextMessage);

        final JsonStringSerializer<AdministratieveHandelingVerwerktOpdracht> brpBijhoudingJSON =
                new JsonStringSerializer<>(AdministratieveHandelingVerwerktOpdracht.class);
        final AdministratieveHandelingVerwerktOpdracht brpBijhouding = brpBijhoudingJSON.deserialiseerVanuitString(((TextMessage) message).getText());
        Assert.assertEquals(Long.valueOf(1L), brpBijhouding.getAdministratieveHandelingId());
        Assert.assertEquals(2, brpBijhouding.getBijgehoudenPersoonIds().size());
        Assert.assertTrue(brpBijhouding.getBijgehoudenPersoonIds().contains(4));
        Assert.assertTrue(brpBijhouding.getBijgehoudenPersoonIds().contains(5));
        Assert.assertNull(brpBijhouding.getPartijCode());
    }
}
