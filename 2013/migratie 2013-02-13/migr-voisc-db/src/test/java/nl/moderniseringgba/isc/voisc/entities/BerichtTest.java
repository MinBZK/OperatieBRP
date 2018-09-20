/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.voisc.entities;

import java.text.SimpleDateFormat;

import org.junit.Assert;
import org.junit.Test;

public class BerichtTest {

    private static final SimpleDateFormat DATE = new SimpleDateFormat("dd-MM-yyyy");

    @Test
    public void test() throws Exception {
        final Bericht bericht = new Bericht();
        Assert.assertNull(bericht.getId());
        Assert.assertNull(bericht.getAanduidingInUit());
        Assert.assertNull(bericht.getOriginator());
        Assert.assertNull(bericht.getRecipient());
        Assert.assertNull(bericht.getEref());
        Assert.assertNull(bericht.getBref());
        Assert.assertNull(bericht.getEref2());
        Assert.assertNull(bericht.getTijdstipVerzendingOntvangst());
        Assert.assertNull(bericht.getDispatchSequenceNumber());
        Assert.assertNull(bericht.getReportDeliveryTime());
        Assert.assertNull(bericht.getNonDeliveryReason());
        Assert.assertNull(bericht.getNonReceiptReason());
        Assert.assertNull(bericht.getBerichtInhoud());
        Assert.assertNull(bericht.getRegistratieDt());
        Assert.assertNull(bericht.getEsbCorrelationId());
        Assert.assertNull(bericht.getStatus());
        Assert.assertNull(bericht.getEsbMessageId());
        Assert.assertFalse(bericht.isReport());

        bericht.setId(1234L);
        bericht.setAanduidingInUit("X");
        bericht.setOriginator("orig");
        bericht.setRecipient("recip");
        bericht.setEref("eref");
        bericht.setBref("bref");
        bericht.setEref2("eref2");
        bericht.setTijdstipVerzendingOntvangst(DATE.parse("01-01-2000"));
        bericht.setDispatchSequenceNumber(123);
        bericht.setReportDeliveryTime(DATE.parse("02-03-2000"));
        bericht.setNonDeliveryReason("nonDeliveryReason");
        bericht.setNonReceiptReason("nonReceiptReason");
        bericht.setBerichtInhoud("berichtInhoud");
        bericht.setRegistratieDt(DATE.parse("03-04-2001"));
        bericht.setEsbCorrelationId("esbCorrelatie");
        bericht.setStatus(StatusEnum.QUEUE_SENT);
        bericht.setEsbMessageId("esbMessageId");
        bericht.setReport(true);

        Assert.assertEquals(Long.valueOf(1234L), bericht.getId());
        Assert.assertEquals("X", bericht.getAanduidingInUit());
        Assert.assertEquals("orig", bericht.getOriginator());
        Assert.assertEquals("recip", bericht.getRecipient());
        Assert.assertEquals("eref", bericht.getEref());
        Assert.assertEquals("bref", bericht.getBref());
        Assert.assertEquals("eref2", bericht.getEref2());
        Assert.assertEquals(DATE.parse("01-01-2000"), bericht.getTijdstipVerzendingOntvangst());
        Assert.assertEquals(Integer.valueOf(123), bericht.getDispatchSequenceNumber());
        Assert.assertEquals(DATE.parse("02-03-2000"), bericht.getReportDeliveryTime());
        Assert.assertEquals("nonDeliveryReason", bericht.getNonDeliveryReason());
        Assert.assertEquals("nonReceiptReason", bericht.getNonReceiptReason());
        Assert.assertEquals("berichtInhoud", bericht.getBerichtInhoud());
        Assert.assertEquals(DATE.parse("03-04-2001"), bericht.getRegistratieDt());
        Assert.assertEquals("esbCorrelatie", bericht.getEsbCorrelationId());
        Assert.assertEquals(StatusEnum.QUEUE_SENT, bericht.getStatus());
        Assert.assertEquals("esbMessageId", bericht.getEsbMessageId());
        Assert.assertTrue(bericht.isReport());

        final Bericht herhaling = bericht.createHerhaalBericht();
        Assert.assertNull(herhaling.getId());
        Assert.assertEquals("U", herhaling.getAanduidingInUit());
        Assert.assertEquals("orig", herhaling.getOriginator());
        Assert.assertEquals("recip", herhaling.getRecipient());
        Assert.assertEquals("eref", herhaling.getEref());
        Assert.assertEquals("bref", herhaling.getBref());
        Assert.assertNull(herhaling.getEref2());
        Assert.assertNull(herhaling.getTijdstipVerzendingOntvangst());
        Assert.assertNull(herhaling.getDispatchSequenceNumber());
        Assert.assertNull(herhaling.getReportDeliveryTime());
        Assert.assertNull(herhaling.getNonDeliveryReason());
        Assert.assertNull(herhaling.getNonReceiptReason());
        Assert.assertEquals("berichtInhoud", herhaling.getBerichtInhoud());
        Assert.assertNull(herhaling.getRegistratieDt());
        Assert.assertEquals("esbCorrelatie", herhaling.getEsbCorrelationId());
        Assert.assertEquals(StatusEnum.QUEUE_RECEIVED, herhaling.getStatus());
        Assert.assertEquals("esbMessageId", herhaling.getEsbMessageId());
        Assert.assertFalse(herhaling.isReport());
    }
}
