/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.centrale;

import java.io.IOException;
import java.io.InputStream;
import javax.inject.Named;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.sql.DataSource;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class GbaArchiefRouteIntegratieTest extends AbstractIntegratieTest {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Autowired
    @Named("gbaQueueArchief")
    private Destination opdrachtQueue;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Before
    public void cleanQueues() {
        cleanQueues(jmsTemplate, opdrachtQueue);
    }

    @Test
    public void testMinimaal() throws JMSException, InterruptedException {
        test("/data/archiefVerzoekMinimaal.json");
    }

    @Test
    public void testVolledig() throws JMSException, InterruptedException {
        test("/data/archiefVerzoekVolledig.json");
    }

    private void test(final String verzoekResource) throws JMSException, InterruptedException {
        final String verzoek;
        try (InputStream is = getClass().getResourceAsStream(verzoekResource)) {
            Assert.assertNotNull("Verzoek niet gevonden", is);
            verzoek = IOUtils.toString(is);
        } catch (final IOException e) {
            throw new IllegalArgumentException("Kan verzoek niet lezen", e);
        }
        LOGGER.info("GBA Archivering verzoek: {}", verzoek);

        final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        final long aantalBerichtenVoor = jdbcTemplate.queryForObject("select count(id) from ber.ber", Long.class);
        LOGGER.info("Aantal berichten in archief voor verzoek: {}", aantalBerichtenVoor);

        LOGGER.info("Queue: {}", opdrachtQueue);
        jmsTemplate.send(opdrachtQueue, new MessageCreator() {
            @Override
            public Message createMessage(final Session session) throws JMSException {
                final Message message = session.createTextMessage(verzoek);
                return message;
            }
        });

        Thread.sleep(10000);

        final long aantalBerichtenNa = jdbcTemplate.queryForObject("select count(id) from ber.ber", Long.class);
        Assert.assertEquals(aantalBerichtenVoor + 1, aantalBerichtenNa);

    }
}
