/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.integratie;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.sql.DataSource;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.factory.SyncBerichtFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AutorisatieAntwoordBericht;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class InitieleVullingAutorisatieIT extends AbstractInitieleVullingIT {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final String PARTIJ_CODE = "250001";
    private static final String PARTIJ_CODE_BIJHOUDER = "000301";

    @Inject
    @Named("queueConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Inject
    @Named("syncDalDataSource")
    private DataSource dataSource;

    @Inject
    @Named("queueSyncAntwoord")
    private Destination antwoordQueue;


    private SyncBericht verwerk(final String verzoekResource) throws IOException, JMSException {
        final JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);

        // Initiele vulling autorisatie
        final String input = IOUtils.toString(InitieleVullingAutorisatieIT.class.getResourceAsStream(verzoekResource), "UTF-8");
        LOGGER.info("input: {}", input);
        jmsTemplate.send("iv.request", new MessageCreator() {
            @Override
            public Message createMessage(final Session session) throws JMSException {
                final Message message = session.createTextMessage(input);
                message.setStringProperty("iscBerichtReferentie", "messageId001");
                return message;
            }
        });

        try {
            TimeUnit.MILLISECONDS.sleep(5000);
        } catch (final InterruptedException e) {
            // Ignore
        }
        jmsTemplate.setReceiveTimeout(10000);
        final Message message = jmsTemplate.receive(antwoordQueue);
        Assert.assertNotNull(message);
        LOGGER.info("Message: {}", message);
        final String text = ((TextMessage) message).getText();
        LOGGER.info("Inhoud: {}", text);
        return SyncBerichtFactory.SINGLETON.getBericht(text);
    }


    private int telAutorisatiesVoorPartij(final String partijCode) {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        List<Map<String, Object>> rijen =
                jdbcTemplate.queryForList("select * "
                                + "from   autaut.levsautorisatie "
                                + "join   autaut.toeganglevsautorisatie on autaut.toeganglevsautorisatie.levsautorisatie = levsautorisatie.id "
                                + "join   kern.partijrol on partijrol.id = toeganglevsautorisatie.geautoriseerde "
                                + "join   kern.partij on partij.id = partijrol.partij "
                                + "where  partij.code = ?",
                        partijCode);

        for (Map<String, Object> rij : rijen) {
            System.out.println(rij);
        }

        return jdbcTemplate.queryForObject(
                "select count(levsautorisatie.id) "
                        + "from   autaut.levsautorisatie "
                        + "join   autaut.toeganglevsautorisatie on autaut.toeganglevsautorisatie.levsautorisatie = levsautorisatie.id "
                        + "join   kern.partijrol on partijrol.id = toeganglevsautorisatie.geautoriseerde "
                        + "join   kern.partij on partij.id = partijrol.partij "
                        + "where  partij.code = ?",
                Integer.class,
                partijCode);
    }

    @Test
    public void testEnkeleAtr() throws Exception {
        Assert.assertEquals(0, telAutorisatiesVoorPartij(PARTIJ_CODE));
        final AutorisatieAntwoordBericht antwoord = (AutorisatieAntwoordBericht) verwerk("iv-autorisaties-test-enkele-atr.xml");

        Assert.assertEquals(1, antwoord.getAutorisatieTabelRegels().size());
        Assert.assertEquals(2551, antwoord.getAutorisatieTabelRegels().get(0).getAutorisatieId());
        Assert.assertEquals(StatusType.TOEGEVOEGD, antwoord.getAutorisatieTabelRegels().get(0).getStatus());
        Assert.assertEquals(null, antwoord.getAutorisatieTabelRegels().get(0).getFoutmelding());

        Assert.assertEquals(1, telAutorisatiesVoorPartij(PARTIJ_CODE));
    }

    @Test
    public void testEnkeleAtrBijhouder() throws Exception {
        Assert.assertEquals(0, telAutorisatiesVoorPartij(PARTIJ_CODE_BIJHOUDER));
        final AutorisatieAntwoordBericht antwoord = (AutorisatieAntwoordBericht) verwerk("iv-autorisaties-test-enkele-atr-bijhouder.xml");

        Assert.assertEquals(1, antwoord.getAutorisatieTabelRegels().size());
        Assert.assertEquals(301, antwoord.getAutorisatieTabelRegels().get(0).getAutorisatieId());
        Assert.assertEquals(StatusType.TOEGEVOEGD, antwoord.getAutorisatieTabelRegels().get(0).getStatus());
        Assert.assertEquals(null, antwoord.getAutorisatieTabelRegels().get(0).getFoutmelding());

        Assert.assertEquals(2, telAutorisatiesVoorPartij(PARTIJ_CODE_BIJHOUDER));
    }

    @Test
    public void testMeerdereAtr() throws Exception {
        Assert.assertEquals(0, telAutorisatiesVoorPartij(PARTIJ_CODE));
        final AutorisatieAntwoordBericht antwoord = (AutorisatieAntwoordBericht) verwerk("iv-autorisaties-test-meerdere-atr.xml");

        Assert.assertEquals(3, antwoord.getAutorisatieTabelRegels().size());
        Assert.assertEquals(2553, antwoord.getAutorisatieTabelRegels().get(0).getAutorisatieId());
        Assert.assertEquals(StatusType.TOEGEVOEGD, antwoord.getAutorisatieTabelRegels().get(0).getStatus());
        Assert.assertEquals(null, antwoord.getAutorisatieTabelRegels().get(0).getFoutmelding());
        Assert.assertEquals(2552, antwoord.getAutorisatieTabelRegels().get(1).getAutorisatieId());
        Assert.assertEquals(StatusType.TOEGEVOEGD, antwoord.getAutorisatieTabelRegels().get(1).getStatus());
        Assert.assertEquals(null, antwoord.getAutorisatieTabelRegels().get(1).getFoutmelding());
        Assert.assertEquals(2551, antwoord.getAutorisatieTabelRegels().get(2).getAutorisatieId());
        Assert.assertEquals(StatusType.TOEGEVOEGD, antwoord.getAutorisatieTabelRegels().get(2).getStatus());
        Assert.assertEquals(null, antwoord.getAutorisatieTabelRegels().get(2).getFoutmelding());

        Assert.assertEquals(3, telAutorisatiesVoorPartij(PARTIJ_CODE));
    }

    @Test
    public void testMeerdereAtrBijhouder() throws Exception {
        Assert.assertEquals(0, telAutorisatiesVoorPartij(PARTIJ_CODE_BIJHOUDER));
        final AutorisatieAntwoordBericht antwoord = (AutorisatieAntwoordBericht) verwerk("iv-autorisaties-test-meerdere-atr-bijhouder.xml");

        Assert.assertEquals(3, antwoord.getAutorisatieTabelRegels().size());
        Assert.assertEquals(303, antwoord.getAutorisatieTabelRegels().get(0).getAutorisatieId());
        Assert.assertEquals(StatusType.TOEGEVOEGD, antwoord.getAutorisatieTabelRegels().get(0).getStatus());
        Assert.assertEquals(null, antwoord.getAutorisatieTabelRegels().get(0).getFoutmelding());
        Assert.assertEquals(302, antwoord.getAutorisatieTabelRegels().get(1).getAutorisatieId());
        Assert.assertEquals(StatusType.TOEGEVOEGD, antwoord.getAutorisatieTabelRegels().get(1).getStatus());
        Assert.assertEquals(null, antwoord.getAutorisatieTabelRegels().get(1).getFoutmelding());
        Assert.assertEquals(301, antwoord.getAutorisatieTabelRegels().get(2).getAutorisatieId());
        Assert.assertEquals(StatusType.TOEGEVOEGD, antwoord.getAutorisatieTabelRegels().get(2).getStatus());
        Assert.assertEquals(null, antwoord.getAutorisatieTabelRegels().get(2).getFoutmelding());

        Assert.assertEquals(6, telAutorisatiesVoorPartij(PARTIJ_CODE_BIJHOUDER));
    }

    @Test
    public void testMeerdereAtrEnkeleFout() throws Exception {
        Assert.assertEquals(0, telAutorisatiesVoorPartij(PARTIJ_CODE));
        final AutorisatieAntwoordBericht antwoord = (AutorisatieAntwoordBericht) verwerk("iv-autorisaties-test-meerdere-atr-enkele-fout.xml");

        Assert.assertEquals(3, antwoord.getAutorisatieTabelRegels().size());
        Assert.assertEquals(2553, antwoord.getAutorisatieTabelRegels().get(0).getAutorisatieId());
        Assert.assertEquals(StatusType.TOEGEVOEGD, antwoord.getAutorisatieTabelRegels().get(0).getStatus());
        Assert.assertEquals(null, antwoord.getAutorisatieTabelRegels().get(0).getFoutmelding());
        Assert.assertEquals(2552, antwoord.getAutorisatieTabelRegels().get(1).getAutorisatieId());
        Assert.assertEquals(StatusType.FOUT, antwoord.getAutorisatieTabelRegels().get(1).getStatus());
        Assert.assertEquals("AUT002", antwoord.getAutorisatieTabelRegels().get(1).getFoutmelding());
        Assert.assertEquals(2551, antwoord.getAutorisatieTabelRegels().get(2).getAutorisatieId());
        Assert.assertEquals(StatusType.TOEGEVOEGD, antwoord.getAutorisatieTabelRegels().get(2).getStatus());
        Assert.assertEquals(null, antwoord.getAutorisatieTabelRegels().get(2).getFoutmelding());

        Assert.assertEquals(2, telAutorisatiesVoorPartij(PARTIJ_CODE));
    }

    @Test
    public void testMeerdereAtrBijhouderEnkeleFout() throws Exception {
        Assert.assertEquals(0, telAutorisatiesVoorPartij(PARTIJ_CODE_BIJHOUDER));
        final AutorisatieAntwoordBericht antwoord = (AutorisatieAntwoordBericht) verwerk("iv-autorisaties-test-meerdere-atr-bijhouder-enkele-fout.xml");

        Assert.assertEquals(3, antwoord.getAutorisatieTabelRegels().size());
        Assert.assertEquals(303, antwoord.getAutorisatieTabelRegels().get(0).getAutorisatieId());
        Assert.assertEquals(StatusType.TOEGEVOEGD, antwoord.getAutorisatieTabelRegels().get(0).getStatus());
        Assert.assertEquals(null, antwoord.getAutorisatieTabelRegels().get(0).getFoutmelding());
        Assert.assertEquals(302, antwoord.getAutorisatieTabelRegels().get(1).getAutorisatieId());
        Assert.assertEquals(StatusType.FOUT, antwoord.getAutorisatieTabelRegels().get(1).getStatus());
        Assert.assertEquals("AUT002", antwoord.getAutorisatieTabelRegels().get(1).getFoutmelding());
        Assert.assertEquals(301, antwoord.getAutorisatieTabelRegels().get(2).getAutorisatieId());
        Assert.assertEquals(StatusType.TOEGEVOEGD, antwoord.getAutorisatieTabelRegels().get(2).getStatus());
        Assert.assertEquals(null, antwoord.getAutorisatieTabelRegels().get(2).getFoutmelding());

        Assert.assertEquals(4, telAutorisatiesVoorPartij(PARTIJ_CODE_BIJHOUDER));
    }

}
