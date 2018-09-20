/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.init.runtime.service;

import static org.junit.Assert.assertTrue;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.sql.DataSource;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import nl.moderniseringgba.isc.esb.message.JMSConstants;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mockrunner.jms.DestinationManager;
import com.mockrunner.mock.jms.MockQueue;
import com.mockrunner.mock.jms.MockTextMessage;

/**
 * Test de service voor het versturen van LO3-berichten.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:runtime-test-beans.xml")
public class InitieleVullingServiceTest {

    @Resource
    private DestinationManager manager;

    @Inject
    private InitieleVullingService service;

    @Inject
    private DataSource dataSource;

    @Before
    public void before() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update("UPDATE initvullingresult SET conversie_resultaat='TE_VERZENDEN'");
    }

    @Test
    public void leesAlleLo3BerichtenEnVerstuur() {
        final Properties config = new Properties();
        config.put("datum.start", "01-01-1990");
        config.put("datum.eind", "01-01-2013");

        try {
            service.leesLo3BerichtenEnVerstuur(config);
        } catch (final ParseException e) {
            Assert.fail("Lezen berichten mislukt.");
        }
        final MockQueue queue = manager.getQueue("/queue/init.vulling.naarlo3");
        @SuppressWarnings("rawtypes")
        final List messages = queue.getCurrentMessageList();
        Assert.assertTrue("Er zijn geen berichten!", messages.size() == 28);
        queue.clear();
    }

    @Test
    public void leesAlleLo3BerichtenEnVerstuurInMeerdereBatches() {
        final Properties config = new Properties();
        config.put("datum.start", "01-01-1990");
        config.put("datum.eind", "01-01-2013");
        // Batch van 15, in 2 batches zijn alle berichten verwerkt.
        config.put("batch.aantal", "15");

        try {
            service.leesLo3BerichtenEnVerstuur(config);
        } catch (final ParseException e) {
            Assert.fail("Lezen berichten mislukt.");
        }
        final MockQueue queue = manager.getQueue("/queue/init.vulling.naarlo3");
        @SuppressWarnings("rawtypes")
        final List messages = queue.getCurrentMessageList();
        Assert.assertTrue("Er zijn geen berichten!", messages.size() == 28);
        queue.clear();
    }

    @Test
    public void leesAlleLo3BerichtenEnControleerIds() throws Exception {
        final Properties config = new Properties();
        config.put("datum.start", "01-01-1990");
        config.put("datum.eind", "01-01-2013");
        // Batch van 15, in 2 batches zijn alle berichten verwerkt.
        config.put("batch.aantal", "15");

        try {
            service.leesLo3BerichtenEnVerstuur(config);
        } catch (final ParseException e) {
            Assert.fail("Lezen berichten mislukt.");
        }
        final MockQueue queue = manager.getQueue("/queue/init.vulling.naarlo3");
        @SuppressWarnings("unchecked")
        final List<MockTextMessage> messages = queue.getCurrentMessageList();
        Assert.assertTrue("Er zijn geen berichten!", messages.size() == 28);
        final List<String> berichtIds = new ArrayList<String>();
        for (final MockTextMessage message : messages) {
            berichtIds.add(message.getStringProperty(JMSConstants.BERICHT_REFERENTIE));
        }

        final List<String> verwachteIds = new ArrayList<String>();

        verwachteIds.add(String.valueOf(3832803548L));
        verwachteIds.add(String.valueOf(5054783237L));
        verwachteIds.add(String.valueOf(7489487289L));
        verwachteIds.add(String.valueOf(4692587979L));
        verwachteIds.add(String.valueOf(5460975298L));
        verwachteIds.add(String.valueOf(4568204852L));
        verwachteIds.add(String.valueOf(8234912384L));
        verwachteIds.add(String.valueOf(8234912103L));
        verwachteIds.add(String.valueOf(7915932521L));
        verwachteIds.add(String.valueOf(5783071526L));
        verwachteIds.add(String.valueOf(8234912690L));
        verwachteIds.add(String.valueOf(7696126369L));
        verwachteIds.add(String.valueOf(6929678748L));
        verwachteIds.add(String.valueOf(6860742407L));
        verwachteIds.add(String.valueOf(7343505746L));
        verwachteIds.add(String.valueOf(6579659649L));
        verwachteIds.add(String.valueOf(7692704965L));
        verwachteIds.add(String.valueOf(2102970797L));
        verwachteIds.add(String.valueOf(2102894756L));
        verwachteIds.add(String.valueOf(2102832762L));
        verwachteIds.add(String.valueOf(2102823690L));
        verwachteIds.add(String.valueOf(2503828934L));
        verwachteIds.add(String.valueOf(9757279162L));
        verwachteIds.add(String.valueOf(1010141626L));
        verwachteIds.add(String.valueOf(7234370783L));
        verwachteIds.add(String.valueOf(3968904934L));
        verwachteIds.add(String.valueOf(7560314837L));
        verwachteIds.add(String.valueOf(6064295705L));

        for (final String id : verwachteIds) {
            assertTrue("Missend id: " + id, berichtIds.contains(id));
        }
        queue.clear();
    }

    @Ignore("Werkt niet op Jenkins: excel folder kan niet worden gevonden")
    @Test
    public void testVulBerichtenTabelExcel() throws Exception {
        final String excelLocatie = InitieleVullingServiceTest.class.getResource("/excel").getFile();
        System.out.println(excelLocatie);
        service.vulBerichtenTabelExcel(null, excelLocatie);
    }
}
