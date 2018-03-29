/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.afnemerindicatie.gba;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.EffectAfnemerindicaties;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.serialisatie.JsonStringSerializer;
import nl.bzk.brp.domain.internbericht.verzendingmodel.SynchronisatieBerichtGegevensQueue;
import nl.bzk.brp.gba.domain.afnemerindicatie.AfnemerindicatieOnderhoudAntwoord;
import nl.bzk.brp.gba.domain.afnemerindicatie.AfnemerindicatieOnderhoudOpdracht;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

public class AfnemerindicatieMessageListenerIT extends AbstractIT {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final String REF_WAARDE = "REF-WAARDE";
    private static final String BSN = "003000020";

    @Autowired
    @Named("gbaQueueAfnemerindicatieVerzoek")
    private Destination opdrachtQueue;

    @Autowired
    @Named("gbaQueueAfnemerindicatieAntwoord")
    private Destination antwoordQueue;

    @Inject
    private JmsTemplate jmsTemplate;

    @Test
    public void test() throws JMSException {
        LOGGER.info("==================test==================");
        final JsonStringSerializer jsonOpdracht = new JsonStringSerializer();
        final AfnemerindicatieOnderhoudOpdracht opdracht = new AfnemerindicatieOnderhoudOpdracht();
        opdracht.setPartijCode("001801");
        opdracht.setEffectAfnemerindicatie(EffectAfnemerindicaties.PLAATSING);
        opdracht.setBsn(BSN);
        opdracht.setReferentienummer(REF_WAARDE);

        LOGGER.info("Versturen GBA Afnemersindicatie opdracht to:" + opdrachtQueue);
        jmsTemplate.send(opdrachtQueue, session -> session.createTextMessage(jsonOpdracht.serialiseerNaarString(opdracht)));

        // Antwoord: OK
        LOGGER.info("Ontvangen GBA Afnemersindicatie antwoord");
        jmsTemplate.setReceiveTimeout(5000);
        final Message message = jmsTemplate.receive(antwoordQueue);
        Assert.assertNotNull("Geen bericht ontvangen op GBA Afnemersindicatie antwoord queue", message);
        final String inhoud = ((TextMessage) message).getText();
        Assert.assertEquals("Onverwacht bericht ontvangen op GBA Afnemersindicatie antwoord queue", "{\"referentienummer\":\"REF-WAARDE\"}", inhoud);

        // Ag01
        final Message messageAg01 = jmsTemplate.receive(SynchronisatieBerichtGegevensQueue.NAAM.getQueueNaam());
        Assert.assertNotNull("Geen bericht ontvangen op BRP Verzending queue", messageAg01);
        final String inhoudAg01 = ((TextMessage) messageAg01).getText();
        final Pattern berichtPattern = Pattern.compile("\"data\":\"(.*?)\"");
        final Matcher ag01Matcher = berichtPattern.matcher(inhoudAg01);

        System.out.println("_________________________________________");
        System.out.println("_________________________________________");
        System.out.println(inhoudAg01);
        System.out.println("_________________________________________");

        Assert.assertTrue("Bericht niet gevonden in verzending", ag01Matcher.find());
        Assert.assertEquals("Onverwacht Ag01 bericht ontvangen", "00000000Ag01A0000000000031010260210005Hanna0240007Guirten", ag01Matcher.group(1));

        // Tweede keer, dezelfde opdracht versturen voor foutcode 'I'.
        LOGGER.info("Versturen GBA Afnemersindicatie opdracht to:" + opdrachtQueue);
        jmsTemplate.send(opdrachtQueue, session -> session.createTextMessage(jsonOpdracht.serialiseerNaarString(opdracht)));

        LOGGER.info("Ontvangen GBA Afnemersindicatie antwoord");
        final Message messageFoutcodeI = jmsTemplate.receive(antwoordQueue);
        Assert.assertNotNull("Geen bericht ontvangen op GBA Afnemersindicatie antwoord queue", message);

        final JsonStringSerializer jsonAntwoord = new JsonStringSerializer();
        final AfnemerindicatieOnderhoudAntwoord
                antwoordFoutcodeI =
                jsonAntwoord.deserialiseerVanuitString(((TextMessage) messageFoutcodeI).getText(), AfnemerindicatieOnderhoudAntwoord.class);

        // ToegangLeveringsautorisatie niet gevonden
        Assert.assertEquals(Character.valueOf('I'), antwoordFoutcodeI.getFoutcode());

        // En nu de afnemersindicatie weer verwijderen
        final AfnemerindicatieOnderhoudOpdracht verwijderen = new AfnemerindicatieOnderhoudOpdracht();
        verwijderen.setPartijCode("001801");
        verwijderen.setEffectAfnemerindicatie(EffectAfnemerindicaties.VERWIJDERING);
        verwijderen.setBsn(BSN);
        verwijderen.setReferentienummer(REF_WAARDE);

        LOGGER.info("Versturen GBA Afnemersindicatie opdracht to:" + opdrachtQueue);
        jmsTemplate.send(opdrachtQueue, session -> session.createTextMessage(jsonOpdracht.serialiseerNaarString(verwijderen)));

        // Antwoord: OK
        LOGGER.info("Ontvangen GBA Afnemersindicatie antwoord");
        jmsTemplate.setReceiveTimeout(5000);
        final Message messageVerwijderen = jmsTemplate.receive(antwoordQueue);
        Assert.assertNotNull("Geen bericht ontvangen op GBA Afnemersindicatie antwoord queue", messageVerwijderen);
        final String inhoudVerwijderen = ((TextMessage) messageVerwijderen).getText();
        Assert.assertEquals(
                "Onverwacht bericht ontvangen op GBA Afnemersindicatie antwoord queue",
                "{\"referentienummer\":\"REF-WAARDE\"}",
                inhoudVerwijderen);
    }

    @Test
    public void testX() throws JMSException {
        LOGGER.info("==================testX==================");
        final JsonStringSerializer jsonOpdracht = new JsonStringSerializer();
        final AfnemerindicatieOnderhoudOpdracht opdracht = new AfnemerindicatieOnderhoudOpdracht();
        opdracht.setPartijCode("580002");
        opdracht.setEffectAfnemerindicatie(EffectAfnemerindicaties.PLAATSING);
        opdracht.setBsn(BSN);
        opdracht.setReferentienummer(REF_WAARDE);

        LOGGER.info("Versturen GBA Afnemersindicatie opdracht to:" + opdrachtQueue);
        jmsTemplate.send(opdrachtQueue, session -> session.createTextMessage(jsonOpdracht.serialiseerNaarString(opdracht)));

        LOGGER.info("Ontvangen GBA Afnemersindicatie antwoord");
        jmsTemplate.setReceiveTimeout(5000);
        final Message message = jmsTemplate.receive(antwoordQueue);
        Assert.assertNotNull("Geen bericht ontvangen", message);
        LOGGER.info("message: " + message);

        final JsonStringSerializer jsonAntwoord = new JsonStringSerializer();
        final AfnemerindicatieOnderhoudAntwoord
                antwoord =
                jsonAntwoord.deserialiseerVanuitString(((TextMessage) message).getText(), AfnemerindicatieOnderhoudAntwoord.class);

        Assert.assertEquals(REF_WAARDE, antwoord.getReferentienummer());
        // ToegangLeveringsautorisatie niet gevonden
        Assert.assertEquals(Character.valueOf('X'), antwoord.getFoutcode());
    }

    @Test
    public void testG() throws JMSException {
        LOGGER.info("==================testG==================");
        final JsonStringSerializer jsonOpdracht = new JsonStringSerializer();
        final AfnemerindicatieOnderhoudOpdracht opdracht = new AfnemerindicatieOnderhoudOpdracht();
        opdracht.setPartijCode("001801");
        opdracht.setEffectAfnemerindicatie(EffectAfnemerindicaties.PLAATSING);
        opdracht.setBsn(null);
        opdracht.setReferentienummer(REF_WAARDE);

        LOGGER.info("Versturen GBA Afnemersindicatie opdracht to:" + opdrachtQueue);
        jmsTemplate.send(opdrachtQueue, session -> session.createTextMessage(jsonOpdracht.serialiseerNaarString(opdracht)));

        LOGGER.info("Ontvangen GBA Afnemersindicatie antwoord");
        jmsTemplate.setReceiveTimeout(5000);
        final Message message = jmsTemplate.receive(antwoordQueue);
        Assert.assertNotNull("Geen bericht ontvangen", message);
        LOGGER.info("message: " + message);

        final JsonStringSerializer jsonAntwoord = new JsonStringSerializer();
        final AfnemerindicatieOnderhoudAntwoord
                antwoord =
                jsonAntwoord.deserialiseerVanuitString(((TextMessage) message).getText(), AfnemerindicatieOnderhoudAntwoord.class);

        Assert.assertEquals(REF_WAARDE, antwoord.getReferentienummer());
        // ToegangLeveringsautorisatie niet gevonden
        Assert.assertEquals(Character.valueOf('G'), antwoord.getFoutcode());
    }
}
