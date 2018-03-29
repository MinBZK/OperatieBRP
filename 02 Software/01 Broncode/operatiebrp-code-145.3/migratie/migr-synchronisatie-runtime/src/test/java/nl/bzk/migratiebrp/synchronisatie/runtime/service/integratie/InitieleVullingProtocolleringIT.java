/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.integratie;

import java.io.IOException;
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
import nl.bzk.migratiebrp.bericht.model.sync.impl.ProtocolleringAntwoordBericht;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

    public class InitieleVullingProtocolleringIT extends AbstractInitieleVullingIT {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    @Named("queueConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Inject
    @Named("syncDalDataSource")
    private DataSource dataSource;

    @Inject
    @Named("queueSyncAntwoord")
    private Destination antwoordQueue;

    public int telProtocolleringenVoorPersoon(final long persoonId) {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        return jdbcTemplate.queryForObject(
                "select count(distinct levsaantek) from prot.levsaantekpers where pers = ?", Integer.class, persoonId);
    }

    private SyncBericht verwerk(final String verzoekResource) throws IOException, JMSException {
        final JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);

        // Initiele vulling afnemersindicaties
        final String input = IOUtils.toString(InitieleVullingProtocolleringIT.class.getResourceAsStream(verzoekResource));
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
        LOGGER.info("Message: {}", text);
        return SyncBerichtFactory.SINGLETON.getBericht(text);
    }

        @Test
        public void testEnkeleProtocollering() throws Exception {
            Assert.assertEquals(0, telProtocolleringenVoorPersoon(2));
            final ProtocolleringAntwoordBericht antwoord = (ProtocolleringAntwoordBericht) verwerk("iv-protocollering-test-enkele-protocollering.xml");

            Assert.assertEquals(1, antwoord.getProtocolleringAntwoord().size());
            Assert.assertEquals(StatusType.OK, antwoord.getProtocolleringAntwoord().get(0).getStatus());

            Assert.assertEquals(1, telProtocolleringenVoorPersoon(2));
        }


        @Test
        public void testOpgeschortW() throws Exception {
            Assert.assertEquals(0, telProtocolleringenVoorPersoon(2));
            final ProtocolleringAntwoordBericht antwoord = (ProtocolleringAntwoordBericht) verwerk("iv-protocollering-test-opgeschort-W.xml");

            Assert.assertEquals(1, antwoord.getProtocolleringAntwoord().size());
            Assert.assertEquals(StatusType.FOUT, antwoord.getProtocolleringAntwoord().get(0).getStatus());
            Assert.assertEquals("Persoon is opgeschort met reden 'W'", antwoord.getProtocolleringAntwoord().get(0).getFoutmelding());

            Assert.assertEquals(0, telProtocolleringenVoorPersoon(2));
        }
    }
