/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.amlezer;

import java.text.DecimalFormat;
import javax.inject.Inject;
import nl.bzk.migratiebrp.tools.mailbox.MailboxServer;
import nl.bzk.migratiebrp.tools.mailbox.MailboxServerWrapper;
import nl.bzk.migratiebrp.tools.mailbox.impl.Mailbox;
import nl.bzk.migratiebrp.tools.mailbox.impl.MailboxEntry;
import nl.bzk.migratiebrp.tools.mailbox.impl.MailboxException;
import nl.bzk.migratiebrp.tools.mailbox.impl.MailboxFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/voisc-mailbox-simulator.xml", "/lezer.xml" })
public class BerichtLezerTest {

    @Inject
    private BerichtLezer subject;

    @Inject
    private MailboxServerWrapper mailboxServerWrapper;

    @Before
    public void before() throws MailboxException {
        final MailboxFactory mailboxFactory = mailboxServerWrapper.getMailboxServer().getMailboxFactory();
        mailboxFactory.deleteAll();
    }

    @Test
    public void test() throws MailboxException {
        final MailboxServer mailboxServer = mailboxServerWrapper.getMailboxServer();
        final MailboxFactory mailboxFactory = mailboxServer.getMailboxFactory();

        final Mailbox mailbox2000010 = mailboxFactory.getMailbox("2000010");
        try {
            mailbox2000010.open();

            final MailboxEntry messageFrom1901010 = new MailboxEntry();
            messageFrom1901010.setOriginatorOrRecipient("1901010");
            messageFrom1901010.setMessageId("000000000001");
            messageFrom1901010.setMesg("00000000Lq010000220101701100105054783237");
            messageFrom1901010.setStatus(MailboxEntry.STATUS_NEW);
            mailbox2000010.addEntry(messageFrom1901010);

            mailbox2000010.save();
        } finally {
            mailbox2000010.close();
        }

        subject.execute();
    }

    @Test
    public void testMeerDanMailboxLimit() throws MailboxException {
        final MailboxFactory mailboxFactory = mailboxServerWrapper.getMailboxServer().getMailboxFactory();

        final DecimalFormat df = new DecimalFormat("000000000000");
        final Mailbox mailbox2000010 = mailboxFactory.getMailbox("2000010");
        try {
            mailbox2000010.open();

            for (int i = 1; i <= 200; i++) {

                final MailboxEntry messageFrom1901010 = new MailboxEntry();
                messageFrom1901010.setOriginatorOrRecipient("1901010");
                messageFrom1901010.setMessageId(df.format(i));
                messageFrom1901010.setMesg("00000000Lq010000220101701100105054783237");
                messageFrom1901010.setStatus(MailboxEntry.STATUS_NEW);
                mailbox2000010.addEntry(messageFrom1901010);
            }
            mailbox2000010.save();
        } finally {
            mailbox2000010.close();
        }

        subject.execute();

        // TODO: check resultaten, dit wordt indirect gedaan door de berichtvergelijker ;-)

        // controleer
        // - bepaalMailboxen
        // - output MailboxVerwerker
        //
        // voor iedere mailbox wordt er, als er berichten voor die mailbox zijn, een am bestand aangemaakt
        // met een start-record, 1 of meerdere 'bericht'-record(s) en een end-record

        // de mailboxen worden bepaald op basis van de afnemers property in 'config.properties' |

    }

}
