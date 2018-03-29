/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.neoload;


import nl.bzk.migratiebrp.voisc.spd.exception.VoaException;
import org.junit.Assert;
import org.junit.Test;

public class MailboxFacadeIT extends AbstractIT {

    @Test
    public void test() throws VoaException {
        System.out.println("test");
        MailboxFacade mailbox = new MailboxFacade("localhost", getMailboxPort());

        // Check geen bericht
        BerichtFacade bericht = mailbox.ontvang("3000200", "password");
        Assert.assertNull(bericht);

        // Verstuur
        mailbox.verstuur("password", bericht("0626010", "3000200", "test0000001", null, "MijnEersteLg01"));
        mailbox.verstuur("password", bericht("0627010", "3000200", "test0000001", null, "MijnTweedeLg01"));

        // Ontvang
        bericht = mailbox.ontvang("3000200", "password");
        Assert.assertNotNull(bericht);
        Assert.assertEquals("0626010", bericht.getVerzendendeMailbox());
        Assert.assertEquals("MijnEersteLg01", bericht.getInhoud());

        bericht = mailbox.ontvang("3000200", "password");
        Assert.assertNotNull(bericht);
        Assert.assertEquals("0627010", bericht.getVerzendendeMailbox());
        Assert.assertEquals("MijnTweedeLg01", bericht.getInhoud());

        bericht = mailbox.ontvang("3000200", "password");
        Assert.assertNull(bericht);
    }

    private BerichtFacade bericht(final String verzender, final String ontvanger, final String messageId, final String correlationId, final String inhoud) {
        final BerichtFacade bericht = new BerichtFacade();
        bericht.setVerzendendeMailbox(verzender);
        bericht.setOntvangendeMailbox(ontvanger);
        bericht.setMessageId(messageId);
        bericht.setCorrelationId(correlationId);
        bericht.setInhoud(inhoud);
        return bericht;
    }

}
