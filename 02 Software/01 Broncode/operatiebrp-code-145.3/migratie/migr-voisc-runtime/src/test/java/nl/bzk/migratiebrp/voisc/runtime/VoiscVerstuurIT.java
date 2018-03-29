/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.runtime;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import javax.jms.JMSException;
import nl.bzk.migratiebrp.tools.mailbox.impl.Mailbox;
import nl.bzk.migratiebrp.tools.mailbox.impl.MailboxException;
import org.junit.Assert;
import org.junit.Test;

public class VoiscVerstuurIT extends AbstractIT {

    @Test
    public void testVerstuur() throws InterruptedException, MailboxException, JMSException, TimeoutException, ExecutionException {
        // Verstuur bericht
        sendMessageFromIsc("BERICHTINHOUD-VERSTUUR-TEST", "MSG000000001", null, "199902", "059901");

        // Controleer dat bericht op de mailbox staat
        Mailbox mailbox0599 = getMailboxFactory().getMailbox("0599010");
        Assert.assertEquals("BERICHTINHOUD-VERSTUUR-TEST", getEntryBlocking(mailbox0599, "001").getMesg());
    }
}
