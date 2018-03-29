/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.archivering.gba;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;
import javax.jms.Destination;
import javax.jms.JMSException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath:brp-delivery-archivering-gba-test-context.xml"}, initializers = AbstractIT.PortInitializer.class)
public class ArchiveringIT extends AbstractIT {

    @Autowired
    private Destination archiefQueue;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testMinimaal() throws JMSException, InterruptedException, IOException, URISyntaxException {
        test("data/archiefVerzoekMinimaal.json");
    }

    @Test
    public void testVolledig() throws JMSException, InterruptedException, IOException, URISyntaxException {
        test("data/archiefVerzoekVolledig.json");
    }

    @Test
    public void testFout() throws JMSException, InterruptedException, IOException, URISyntaxException {
        test("data/archiefVerzoekFout.json");
    }

    private void test(final String verzoekResource) throws JMSException, InterruptedException, IOException, URISyntaxException {
        final String verzoek = new String(Files.readAllBytes(Paths.get(ClassLoader.getSystemResource(verzoekResource).toURI())));

        final long aantalBerichtenVoor = jdbcTemplate.queryForObject("select count(id) from ber.ber", Long.class);

        jmsTemplate.send(archiefQueue, session -> session.createTextMessage(verzoek));

        TimeUnit.MILLISECONDS.sleep(3000);

        final long aantalBerichtenNa = jdbcTemplate.queryForObject("select count(id) from ber.ber", Long.class);
        Assert.assertEquals(aantalBerichtenVoor + 1, aantalBerichtenNa);
    }
}
