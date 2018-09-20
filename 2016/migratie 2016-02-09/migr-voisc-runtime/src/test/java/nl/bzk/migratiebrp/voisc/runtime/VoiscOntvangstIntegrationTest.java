/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.runtime;

import javax.jms.JMSException;
import javax.jms.Message;
import nl.bzk.migratiebrp.tools.mailbox.impl.MailboxException;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.junit.Assert;
import org.junit.Test;

public class VoiscOntvangstIntegrationTest extends AbstractIntegrationTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Test
    public void testOntvangst() throws InterruptedException, MailboxException, JMSException {
        // Verstuur bericht naar 3000200 mailbox
        sendToMailbox("0599010", "3000200", "MSG000000001", null, "BERICHTINHOUD");

        // Wacht op verwerking
        for (int i = 13; i > 0; i--) {
            LOG.info("Sleeping for " + i + " seconds...");
            Thread.sleep(1000);
        }

        // Controleer dat bericht op vospg.ontvangst queue staat
        final Message message = expectMessage(VOISC_ONTVANGST_QUEUE);
        Assert.assertEquals("BERICHTINHOUD", getContent(message));
    }
}
