/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.vrijbericht.gba;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.serialisatie.JsonStringSerializer;
import nl.bzk.brp.domain.internbericht.vrijbericht.VrijBerichtGegevens;
import nl.bzk.brp.domain.internbericht.vrijbericht.VrijBerichtGegevensQueue;
import nl.bzk.brp.gba.domain.vrijbericht.VrijBerichtAntwoord;
import nl.bzk.brp.gba.domain.vrijbericht.VrijBerichtOpdracht;
import nl.bzk.brp.gba.domain.vrijbericht.VrijBerichtQueue;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

public class VrijBerichtMessageListenerIT extends AbstractIT {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Autowired
    @Named("gbaQueueVrijBerichtOpdracht")
    private Destination opdrachtQueue;

    @Autowired
    @Named("gbaQueueVrijBerichtAntwoord")
    private Destination antwoordQueue;

    @Inject
    private JmsTemplate jmsTemplate;

    @Test
    public void testOntvangerGba() throws JMSException {
        LOGGER.info("================== gba ontvangener ==================");

        final VrijBerichtOpdracht opdracht = new VrijBerichtOpdracht();
        opdracht.setVerzendendePartijCode("001801");
        opdracht.setOntvangendePartijCode("059901");
        opdracht.setBericht("Hallo Rotterdam");
        opdracht.setReferentienummer("REF-WAARDE-001");

        final VrijBerichtGegevens gegevens = test(opdracht);
        Assert.assertEquals(Stelsel.GBA, gegevens.getStelsel());
    }

    @Test
    public void testOntvangerBrp() throws JMSException {
        LOGGER.info("================== brp ontvanger ==================");

        final VrijBerichtOpdracht opdracht = new VrijBerichtOpdracht();
        opdracht.setVerzendendePartijCode("001801");
        opdracht.setOntvangendePartijCode("060301");
        opdracht.setBericht("Hallo Rijswijk");
        opdracht.setReferentienummer("REF-WAARDE-002");

        final VrijBerichtGegevens gegevens = test(opdracht);
        Assert.assertEquals(Stelsel.BRP, gegevens.getStelsel());
    }


    @Test
    public void testOntvangerBrpGeenAfleverpunt() throws JMSException {
        LOGGER.info("================== brp ontvanger ==================");

        final VrijBerichtOpdracht opdracht = new VrijBerichtOpdracht();
        opdracht.setVerzendendePartijCode("001801");
        opdracht.setOntvangendePartijCode("060601");
        opdracht.setBericht("Hallo Schiedam");
        opdracht.setReferentienummer("REF-WAARDE-003");

        final JsonStringSerializer json = new JsonStringSerializer();
        LOGGER.info("Versturen GBA VrijBericht opdracht naar: {}", opdrachtQueue);
        final String opdrachtJson = json.serialiseerNaarString(opdracht);
        LOGGER.info("Vrij bericht opdracht: {}", opdrachtJson);
        jmsTemplate.send(opdrachtQueue, session -> session.createTextMessage(opdrachtJson));

        jmsTemplate.setReceiveTimeout(5000);
        final Message message = jmsTemplate.receive(VrijBerichtQueue.ANTWOORD.getQueueNaam());
        Assert.assertNotNull("Geen bericht ontvangen", message);
        final String content = ((TextMessage) message).getText();
        LOGGER.info("Vrij bericht antwoord: {}", content);
        final VrijBerichtAntwoord antwoord = json.deserialiseerVanuitString(content, VrijBerichtAntwoord.class);
        Assert.assertNotNull(antwoord);
        Assert.assertNotEquals("REF-WAARDE-003", antwoord.getReferentienummer());
    }


    private VrijBerichtGegevens test(VrijBerichtOpdracht opdracht) throws JMSException {
        final JsonStringSerializer json = new JsonStringSerializer();
        LOGGER.info("Versturen GBA VrijBericht opdracht naar: {}", opdrachtQueue);
        final String opdrachtJson = json.serialiseerNaarString(opdracht);
        LOGGER.info("Vrij bericht opdracht: {}", opdrachtJson);
        jmsTemplate.send(opdrachtQueue, session -> session.createTextMessage(opdrachtJson));

        jmsTemplate.setReceiveTimeout(5000);
        final Message message = jmsTemplate.receive(VrijBerichtGegevensQueue.NAAM.getQueueNaam());
        Assert.assertNotNull("Geen bericht ontvangen", message);
        final String content = ((TextMessage) message).getText();
        LOGGER.info("Vrij bericht gegevens: {}", content);
        final VrijBerichtGegevens gegevens = json.deserialiseerVanuitString(content, VrijBerichtGegevens.class);
        Assert.assertNotNull(gegevens);

        return gegevens;
    }
}
