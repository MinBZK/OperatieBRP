/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.amlezer;

import nl.bzk.migratiebrp.voisc.database.entities.Mailbox;
import nl.bzk.migratiebrp.voisc.mailbox.client.Connection;
import nl.bzk.migratiebrp.voisc.mailbox.client.MailboxClient;
import nl.bzk.migratiebrp.voisc.spd.exception.SpdProtocolException;
import nl.bzk.migratiebrp.voisc.spd.exception.VoaException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Deze test test alleen 'negatieve' flows. De 'positieve' flow wordt in zijn geheel getest in BerichtLezerTest.
 */
public class MailboxVerwerkerTest {
    private MailboxVerwerker subject;

    private MailboxClient mailboxServerProxy;
    private BerichtCallback berichtCallback;
    private Mailbox mailbox;
    private Connection mailboxConnection;

    @Before
    public void setup() {
        mailboxServerProxy = Mockito.mock(MailboxClient.class);
        berichtCallback = Mockito.mock(BerichtCallback.class);
        mailboxConnection = Mockito.mock(Connection.class);
        mailbox = new Mailbox();

        subject = new MailboxVerwerker();
        ReflectionTestUtils.setField(subject, "mailboxServerProxy", mailboxServerProxy);
    }

    @Test
    public void testVoaException() throws Exception {
        Mockito.when(mailboxServerProxy.connect()).thenReturn(mailboxConnection);
        Mockito.doThrow(VoaException.class).when(mailboxServerProxy).logOn(mailboxConnection, mailbox);
        Mockito.doThrow(SpdProtocolException.class).when(mailboxServerProxy).logOff(mailboxConnection);

        subject.verwerkBerichten(mailbox, berichtCallback);

        Mockito.verify(mailboxServerProxy).connect();
        Mockito.verify(mailboxServerProxy).logOn(mailboxConnection, mailbox);
        Mockito.verify(mailboxServerProxy).logOff(mailboxConnection);
        Mockito.verifyNoMoreInteractions(mailboxServerProxy);
    }

    @Test
    public void testBerichtCallbackException() throws Exception {
        Mockito.when(mailboxServerProxy.connect()).thenReturn(mailboxConnection);
        Mockito.doThrow(BerichtCallbackException.class).when(mailboxServerProxy).logOn(mailboxConnection, mailbox);

        subject.verwerkBerichten(mailbox, berichtCallback);

        Mockito.verify(mailboxServerProxy).connect();
        Mockito.verify(mailboxServerProxy).logOn(mailboxConnection, mailbox);
        Mockito.verify(mailboxServerProxy).logOff(mailboxConnection);
        Mockito.verifyNoMoreInteractions(mailboxServerProxy);
    }
}
