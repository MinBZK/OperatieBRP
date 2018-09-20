/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.voisc.repository;

import java.util.Date;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.moderniseringgba.isc.voisc.entities.Bericht;
import nl.moderniseringgba.isc.voisc.entities.StatusEnum;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

public class VoaRepositoryTest extends AbstractDbTest {

    @Inject
    private VoaRepository voaRepository;

    @PersistenceContext(name = "voiscEntityManagerFactory", unitName = "voiscEntityManagerFactory")
    private EntityManager em;

    @Test
    @Transactional
    public void test() {
        Bericht bericht = new Bericht();
        bericht.setAanduidingInUit(Bericht.AANDUIDING_IN_UIT_IN);
        bericht.setOriginator("1234567");
        bericht.setRecipient("7654321");
        bericht.setStatus(StatusEnum.QUEUE_SENT);
        bericht.setBerichtInhoud("Bla");
        bericht.setEref("EREF-123123");
        bericht.setBref("Bref-133223");
        bericht.setEsbMessageId("ESB-123123");
        bericht.setDispatchSequenceNumber(654321);
        bericht.setRegistratieDt(new Date());

        bericht = voaRepository.save(bericht);
        em.flush();

        final Bericht check = voaRepository.getLo3Bericht(bericht.getId());
        equalsBericht(bericht, check);

        final Bericht eref = voaRepository.getBerichtByEref("EREF-123123");
        equalsBericht(bericht, eref);

        final Bericht esb = voaRepository.getBerichtByEsbMessageId("ESB-123123");
        equalsBericht(bericht, esb);

        final Bericht dispatch = voaRepository.getBerichtByDispatchSeqNr(654321);
        equalsBericht(bericht, dispatch);

        final Bericht herhaling = voaRepository.getBerichtByBrefAndOrginator("Bref-133223", "1234567");
        equalsBericht(bericht, herhaling);

    }

    private void equalsBericht(final Bericht bericht, final Bericht check) {
        Assert.assertEquals(bericht.getId(), check.getId());
        Assert.assertEquals(bericht.getAanduidingInUit(), check.getAanduidingInUit());
        Assert.assertEquals(bericht.getOriginator(), check.getOriginator());
        Assert.assertEquals(bericht.getRecipient(), check.getRecipient());
        Assert.assertEquals(bericht.getEref(), check.getEref());
        Assert.assertEquals(bericht.getBref(), check.getBref());
        Assert.assertEquals(bericht.getEref2(), check.getEref2());
        Assert.assertEquals(bericht.getTijdstipVerzendingOntvangst(), check.getTijdstipVerzendingOntvangst());
        Assert.assertEquals(bericht.getDispatchSequenceNumber(), check.getDispatchSequenceNumber());
        Assert.assertEquals(bericht.getReportDeliveryTime(), check.getReportDeliveryTime());
        Assert.assertEquals(bericht.getNonDeliveryReason(), check.getNonDeliveryReason());
        Assert.assertEquals(bericht.getNonReceiptReason(), check.getNonReceiptReason());
        Assert.assertEquals(bericht.getBerichtInhoud(), check.getBerichtInhoud());
        Assert.assertEquals(bericht.getEsbCorrelationId(), check.getEsbCorrelationId());
        Assert.assertEquals(bericht.getStatus(), check.getStatus());
        Assert.assertEquals(bericht.getEsbMessageId(), check.getEsbMessageId());
        Assert.assertEquals(bericht.isReport(), check.isReport());
    }
}
