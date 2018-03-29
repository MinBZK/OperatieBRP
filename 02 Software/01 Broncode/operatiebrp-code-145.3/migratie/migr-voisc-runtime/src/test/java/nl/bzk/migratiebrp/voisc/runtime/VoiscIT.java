/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.runtime;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import javax.jms.JMSException;
import javax.jms.Message;
import nl.bzk.migratiebrp.bericht.model.JMSConstants;
import nl.bzk.migratiebrp.tools.mailbox.impl.Mailbox;
import nl.bzk.migratiebrp.tools.mailbox.impl.MailboxException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Let op: deze test is afhankelijk van de functionaliteit die in de mailbox simulator is gebouwd.
 */
public class VoiscIT extends AbstractIT {

    @Test
    public void sendReceive() throws InterruptedException, MailboxException, JMSException, TimeoutException, ExecutionException {
        // Verstuur bericht
        sendMessageFromIsc("BERICHTINHOUD-SEND-RECEIVE-001", "MSG000000001", null, "199902", "059901");

        final Mailbox mailbox0599 = getMailboxFactory().getMailbox("0599010");
        Assert.assertEquals("BERICHTINHOUD-SEND-RECEIVE-001", getEntryBlocking(mailbox0599, "001").getMesg());

        // Controleer dat bericht op de mailbox staat

        // Verstuur antwoord
        sendToMailbox("0599010", "3000200", "MSG000000002", "MSG000000001", "BERICHTINHOUD-SEND-RECEIVE-002");

        // Controleer dat bericht op voisc.ontvangst queue staat
        Message message = expectMessage(VOISC_ONTVANGST_QUEUE);
        Assert.assertEquals("MSG000000001", message.getStringProperty(JMSConstants.CORRELATIE_REFERENTIE));
        Assert.assertEquals("MSG000000002", message.getStringProperty(JMSConstants.BERICHT_REFERENTIE));
        Assert.assertEquals("059901", message.getStringProperty(JMSConstants.BERICHT_ORIGINATOR));
        Assert.assertEquals("199902", message.getStringProperty(JMSConstants.BERICHT_RECIPIENT));
        Assert.assertEquals("BERICHTINHOUD-SEND-RECEIVE-002", getContent(message));

        // Verstuur bericht
        sendMessageFromIsc("BERICHTINHOUD-SEND-RECEIVE-FAILURE-001", "MSG0000000F1", null, "199902", "059901");

        // Controleer dat bericht op de mailbox staat
        Assert.assertEquals("BERICHTINHOUD-SEND-RECEIVE-FAILURE-001", getEntryBlocking(mailbox0599, "003").getMesg());

        // Verstuur antwoord
        sendToMailbox("0599010", "3000200", "MSG0000000F2", "MSG0000000F1", "BERICHTINHOUD-SEND-RECEIVE-FAILURE-002");

        // Controleer dat bericht op voisc.ontvangst queue staat
        message = expectMessage(VOISC_ONTVANGST_QUEUE);
        Assert.assertEquals("MSG0000000F1", message.getStringProperty(JMSConstants.CORRELATIE_REFERENTIE));
        Assert.assertEquals("MSG0000000F2", message.getStringProperty(JMSConstants.BERICHT_REFERENTIE));
        Assert.assertEquals("059901", message.getStringProperty(JMSConstants.BERICHT_ORIGINATOR));
        Assert.assertEquals("199902", message.getStringProperty(JMSConstants.BERICHT_RECIPIENT));
        Assert.assertEquals("BERICHTINHOUD-SEND-RECEIVE-FAILURE-002", getContent(message));

        // Verstuur bericht
        sendMessageFromIsc("BERICHTINHOUD-SEND-RECEIVE-FAILURE-SHUTDOWN-001", "MSG000000FS1", null, "199902", "059901");

        // Controleer dat bericht op de mailbox staat
        Assert.assertEquals("BERICHTINHOUD-SEND-RECEIVE-FAILURE-SHUTDOWN-001", getEntryBlocking(mailbox0599, "005").getMesg());
    }

    @Test
    public void sendReceiveStaRep() throws InterruptedException, MailboxException, JMSException, TimeoutException, ExecutionException {
        // Verstuur bericht
        sendMessageFromIsc("BERICHTINHOUD-STAREP-001", "MSG000010001", null, "199902", "059901");

        final Mailbox mailbox0599 = getMailboxFactory().getMailbox("0599010");

        // Controleer dat bericht op de mailbox staat
        Assert.assertEquals("BERICHTINHOUD-STAREP-001", getEntryBlocking(mailbox0599, "001").getMesg());

        // Verstuur starep
        sendToMailbox("0599010", "3000200", "MSG000010002", "MSG000010001", "StaRep;1");

        // Controleer dat bericht op voisc.ontvangst queue staat
        final Message message = expectMessage(VOISC_ONTVANGST_QUEUE);
        Assert.assertEquals("MSG000010001", message.getStringProperty(JMSConstants.CORRELATIE_REFERENTIE));
        Assert.assertEquals(null, message.getStringProperty(JMSConstants.BERICHT_REFERENTIE));
        Assert.assertEquals("059901", message.getStringProperty(JMSConstants.BERICHT_ORIGINATOR));
        Assert.assertEquals("199902", message.getStringProperty(JMSConstants.BERICHT_RECIPIENT));
        Assert.assertEquals("00000000StaR1", getContent(message));
    }

    @Test
    public void sendReceiveDelRep() throws InterruptedException, MailboxException, JMSException, TimeoutException, ExecutionException {
        final String berichtInhoud = "BERICHTINHOUD-DELREP-001";
        // Verstuur bericht
        sendMessageFromIsc(berichtInhoud, "MSG000020001", null, "199902", "059901");

        // Controleer dat bericht op de mailbox staat; en bepaal mssequencenummer
        final Mailbox mailbox0599 = getMailboxFactory().getMailbox("0599010");

        Assert.assertEquals("BERICHTINHOUD-DELREP-001", getEntryBlocking(mailbox0599, "001").getMesg());

        final JdbcTemplate jdbcTemplate = new JdbcTemplate(getVoiscDataSource());
        final int msSequenceNumber = jdbcTemplate.queryForObject("select dispatch_sequence_number from voisc.bericht where bericht_data = :berichtData",
                Integer.class, berichtInhoud);

        Assert.assertEquals("3000200",
                jdbcTemplate.queryForObject("select originator from voisc.bericht where bericht_data = :berichtData", String.class, berichtInhoud));
        Assert.assertEquals("0599010",
                jdbcTemplate.queryForObject("select recipient from voisc.bericht where bericht_data = :berichtData", String.class, berichtInhoud));

        // Verstuur starep
        sendToMailbox("0599010", "3000200", "MSG000020002", "MSG000020001", "DelRep;1247;" + msSequenceNumber);

        // Controleer dat bericht op voisc.ontvangst queue staat
        final Message message = expectMessage(VOISC_ONTVANGST_QUEUE);
        Assert.assertEquals("MSG000020001", message.getStringProperty(JMSConstants.CORRELATIE_REFERENTIE));
        Assert.assertEquals(null, message.getStringProperty(JMSConstants.BERICHT_REFERENTIE));
        Assert.assertEquals("059901", message.getStringProperty(JMSConstants.BERICHT_ORIGINATOR));
        Assert.assertEquals("199902", message.getStringProperty(JMSConstants.BERICHT_RECIPIENT));
        Assert.assertEquals("00000000DelR1247", getContent(message));
    }
}
