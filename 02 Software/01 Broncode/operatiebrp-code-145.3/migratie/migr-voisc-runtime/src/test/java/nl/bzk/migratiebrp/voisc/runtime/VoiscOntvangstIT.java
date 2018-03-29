/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.runtime;

import javax.jms.JMSException;
import javax.jms.Message;
import nl.bzk.migratiebrp.tools.mailbox.impl.MailboxException;
import org.junit.Assert;
import org.junit.Test;

public class VoiscOntvangstIT extends AbstractIT {

    @Test
    public void testOntvangst() throws InterruptedException, MailboxException, JMSException {
        cleanQueue(VOISC_ONTVANGST_QUEUE);

        // Verstuur bericht naar 3000200 mailbox
        sendToMailbox("0599010", "3000200", "MSG000000001", null, "BERICHTINHOUD");

        // Controleer dat bericht op voisc.ontvangst queue staat
        final Message message = expectMessage(VOISC_ONTVANGST_QUEUE);
        Assert.assertEquals("BERICHTINHOUD", getContent(message));
    }
}
