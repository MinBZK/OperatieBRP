/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime.service;

import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht;
import nl.bzk.migratiebrp.isc.runtime.message.Message;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AuditUitgaandActionTest {

    @Mock
    private BerichtenDao berichtenDao;
    @InjectMocks
    private AuditUitgaandAction subject;

    private Bericht bericht;

    @Before
    public void setup() {
        subject.setKanaal("TEST");

        bericht = new Bericht();
        bericht.setId(1L);
        bericht.setBericht("BERICHTINHOUD");
        bericht.setMessageId("MSG-ID");
        bericht.setCorrelationId("CORR-ID");
        bericht.setVerzendendePartij("VERZ-PARTIJ");
        bericht.setOntvangendePartij("ONTV-PARTIJ");

        Mockito.when(berichtenDao.getBericht(1L)).thenReturn(bericht);
    }

    @Test
    public void test() {
        final Message message = new Message();
        message.setContent("1");

        Assert.assertEquals(null, message.getBerichtId());
        Assert.assertEquals("1", message.getContent());

        Assert.assertTrue(subject.verwerk(message));

        Assert.assertEquals(Long.valueOf(1L), message.getBerichtId());
        Assert.assertEquals("BERICHTINHOUD", message.getContent());
        Assert.assertEquals("MSG-ID", message.getMessageId());
        Assert.assertEquals("CORR-ID", message.getCorrelatieId());
        Assert.assertEquals("VERZ-PARTIJ", message.getOriginator());
        Assert.assertEquals("ONTV-PARTIJ", message.getRecipient());
        Assert.assertEquals(false, message.isRequestNonReceiptNotification());

        Assert.assertTrue(subject.verwerk(message));

        Assert.assertEquals(Long.valueOf(1L), message.getBerichtId());
        Assert.assertEquals("BERICHTINHOUD", message.getContent());
    }

    @Test
    public void testMetRequestNonReceipt() {
        bericht.setRequestNonReceipt(true);

        final Message message = new Message();
        message.setContent("1");

        Assert.assertEquals(null, message.getBerichtId());
        Assert.assertEquals("1", message.getContent());
        Assert.assertEquals(false, message.isRequestNonReceiptNotification());

        Assert.assertTrue(subject.verwerk(message));

        Assert.assertEquals(Long.valueOf(1L), message.getBerichtId());
        Assert.assertEquals("BERICHTINHOUD", message.getContent());
        Assert.assertEquals(true, message.isRequestNonReceiptNotification());
    }
}
