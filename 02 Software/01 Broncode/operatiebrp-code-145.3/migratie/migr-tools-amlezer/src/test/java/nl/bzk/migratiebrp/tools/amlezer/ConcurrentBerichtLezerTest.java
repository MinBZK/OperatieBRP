/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.amlezer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.voisc.database.entities.Bericht;
import nl.bzk.migratiebrp.voisc.mailbox.client.Connection;
import nl.bzk.migratiebrp.voisc.spd.SpdConstants;
import nl.bzk.migratiebrp.voisc.mailbox.client.impl.MailboxServerProxyImpl;

import org.junit.Assert;
import org.junit.Ignore;

//Uit omdat dit faalt bij snelle machines, test gaat dan mis omdat er nog een mailbox draait oid.
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration({ "/embedded-mailbox-simulator.xml", "/inmemory-voisc-database.xml", "/amlezer-test-beans.xml" })
public class ConcurrentBerichtLezerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    @Named("mailboxServerProxy")
    private MailboxServerProxyImpl proxy;

    @Inject
    @Named("mailboxServerProxy1")
    private MailboxServerProxyImpl proxy1;

    @Ignore("Werkt niet als er in de test nog een mailbox draait. Dit faalt bij snelle laptops.")
    public void test() throws IOException {
        proxy.connect();
        proxy1.connect();
        final Thread lezer = new Thread(new Lezer());
        final Thread schrijver = new Thread(new Schrijver());
        schrijver.start();
        lezer.run();

    }

    private class Lezer implements Runnable {

        @Override
        public void run() {
            try {
                final nl.bzk.migratiebrp.voisc.database.entities.Mailbox mailb = new nl.bzk.migratiebrp.voisc.database.entities.Mailbox();
                mailb.setMailboxnr("3000200");
                final Connection mailboxConnection = proxy1.connect();
                proxy1.logOn(mailboxConnection, mailb);
                for (int counter = 0; counter < 10000; counter++) {
                    final List<Integer> sequenceNumbers = new ArrayList<>();
                    final int seq = proxy1.listMessages(mailboxConnection, 0, sequenceNumbers, 10, "001", SpdConstants.Priority.defaultValue());

                    LOGGER.debug("seq: " + seq);
                    LOGGER.debug("seqnr size: " + sequenceNumbers.size());
                    for (int waitCounter = 0; waitCounter < 10000; waitCounter++) {
                    }
                }
            } catch (final Throwable e) {
                e.printStackTrace();
                Assert.fail("Fout opgetreden.");
            }
        }
    }

    private class Schrijver implements Runnable {

        @Override
        public void run() {
            try {
                final nl.bzk.migratiebrp.voisc.database.entities.Mailbox mailb = new nl.bzk.migratiebrp.voisc.database.entities.Mailbox();
                mailb.setMailboxnr("2000100");
                final Connection mailboxConnection = proxy.connect();
                proxy.logOn(mailboxConnection, mailb);
                final Bericht bericht = new Bericht();
                bericht.setBerichtInhoud("sdfsfd");
                bericht.setMessageId("sdfijedi");
                bericht.setOriginator("2000100");
                bericht.setRecipient("3000200");
                bericht.setId(1l);

                for (int counter = 0; counter < 10000; counter++) {
                    proxy.putMessage(mailboxConnection, bericht);
                    LOGGER.debug("Bericht op mb gezet");
                    for (int waitCounter = 0; waitCounter < 10000; waitCounter++) {
                    }
                }
            } catch (final Throwable e) {
                e.printStackTrace();
                Assert.fail("Fout opgetreden.");
            }
        }
    }
}
