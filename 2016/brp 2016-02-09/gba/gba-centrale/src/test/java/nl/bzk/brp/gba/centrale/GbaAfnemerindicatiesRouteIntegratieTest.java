/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.centrale;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import nl.bzk.brp.gba.centrale.berichten.AfnemerindicatieOnderhoudAntwoord;
import nl.bzk.brp.gba.centrale.berichten.AfnemerindicatieOnderhoudOpdracht;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.EffectAfnemerindicaties;
import nl.bzk.brp.serialisatie.JsonStringSerializer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class GbaAfnemerindicatiesRouteIntegratieTest extends AbstractIntegratieTest {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final String REF_WAARDE = "REF-WAARDE";
    private static final String BERICHT_REF = "BERICHT-REF";

    @Autowired
    @Named("gbaQueueAfnemerindicaties")
    private Destination opdrachtQueue;

    @Autowired
    @Named("gbaQueueAfnemerindicatiesAntwoorden")
    private Destination antwoordQueue;

    @Inject
    private JmsTemplate jmsTemplate;

    @Before
    public void cleanQueues() {
        cleanQueues(jmsTemplate, opdrachtQueue, antwoordQueue);
    }

    @Test
    public void test() throws JMSException {
        final JsonStringSerializer<AfnemerindicatieOnderhoudOpdracht> jsonOpdracht = new JsonStringSerializer<>(AfnemerindicatieOnderhoudOpdracht.class);
        final AfnemerindicatieOnderhoudOpdracht opdracht = new AfnemerindicatieOnderhoudOpdracht();
        opdracht.setToegangLeveringsautorisatieId(43443);
        opdracht.setDienstId(100);
        opdracht.setEffectAfnemerindicatie(EffectAfnemerindicaties.PLAATSING);
        opdracht.setPersoonId(6);
        opdracht.setReferentienummer(REF_WAARDE);

        LOGGER.info("Versturen GBA Afnemersindicatie opdracht to:" + opdrachtQueue);
        jmsTemplate.send(opdrachtQueue, new MessageCreator() {
            @Override
            public Message createMessage(final Session session) throws JMSException {
                final Message message = session.createTextMessage(jsonOpdracht.serialiseerNaarString(opdracht));
                message.setStringProperty(GbaMessageListener.BERICHT_REFERENTIE, BERICHT_REF);
                return message;
            }
        });

        LOGGER.info("Ontvangen GBA Afnemersindicatie antwoord");
        jmsTemplate.setReceiveTimeout(10000);
        final Message message = jmsTemplate.receive(antwoordQueue);
        Assert.assertNotNull("Geen bericht ontvangen", message);
        LOGGER.info("message: " + message);
        Assert.assertEquals(BERICHT_REF, message.getStringProperty(GbaMessageListener.CORRELATIE_REFERENTIE));
        Assert.assertNotNull(message.getStringProperty(GbaMessageListener.BERICHT_REFERENTIE));

        final JsonStringSerializer<AfnemerindicatieOnderhoudAntwoord> jsonAntwoord = new JsonStringSerializer<>(AfnemerindicatieOnderhoudAntwoord.class);
        final AfnemerindicatieOnderhoudAntwoord antwoord = jsonAntwoord.deserialiseerVanuitString(((TextMessage) message).getText());

        Assert.assertEquals(REF_WAARDE, antwoord.getReferentienummer());
        // ToegangLeveringsautorisatie niet gevonden
        Assert.assertEquals(Character.valueOf('X'), antwoord.getFoutcode());
    }
}
