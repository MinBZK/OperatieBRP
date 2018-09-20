/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.centrale;

import java.io.IOException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.sql.DataSource;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.migratiebrp.test.common.vergelijk.VergelijkXml;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class GbaToevalligeGebeurtenisServiceIntegratieTest extends AbstractIntegratieTest {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Autowired
    @Named("gbaQueueToevalligeGebeurtenissen")
    private Destination opdrachtQueue;

    @Autowired
    @Named("gbaQueueToevalligeGebeurtenissenAntwoorden")
    private Destination antwoordQueue;

    @Inject
    private JmsTemplate jmsTemplate;

    @Inject
    private DataSource dataSource;

    @Before
    public void cleanQueues() {
        cleanQueues(jmsTemplate, opdrachtQueue, antwoordQueue);
    }

    @Test
    public void test() throws JMSException, IOException {
        final JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        jdbc.update("delete from kern.his_betr where betr in (select id from kern.betr where rol='3' and pers=?)", 19);
        jdbc.update("delete from kern.betr where rol='3' and pers=?", 19);

        final String toevalligeGebeurtenisXml =
                IOUtils.toString(GbaToevalligeGebeurtenisServiceIntegratieTest.class.getResource("/data/toevalligeGebeurtenisRegistreerHuwelijk.xml"));
        LOGGER.info("Toevallige gebeurtenis:" + toevalligeGebeurtenisXml);

        LOGGER.info("Versturen toevallige gebeurtenis naar:" + opdrachtQueue);
        jmsTemplate.send(opdrachtQueue, new MessageCreator() {
            @Override
            public Message createMessage(final Session session) throws JMSException {
                return session.createTextMessage(toevalligeGebeurtenisXml);
            }
        });

        LOGGER.info("Ontvangen antwoord van: " + antwoordQueue);
        jmsTemplate.setReceiveTimeout(10000);
        final Message message = jmsTemplate.receive(antwoordQueue);
        Assert.assertNotNull("Geen bericht ontvangen", message);
        LOGGER.info("message: " + message);

        Assert.assertTrue("Bericht niet een text message", message instanceof TextMessage);
        final String antwoordXml = ((TextMessage) message).getText();
        LOGGER.info("antwoord: " + antwoordXml);
        final String verwachtAntwoordXml =
                IOUtils.toString(
                    GbaToevalligeGebeurtenisServiceIntegratieTest.class.getResource("/data/toevalligeGebeurtenisRegistreerHuwelijkAntwoord.xml"));
        VergelijkXml.vergelijkXml(verwachtAntwoordXml, antwoordXml);
    }
}
