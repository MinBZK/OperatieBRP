/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.jbpm.berichten;

import java.util.List;

import nl.moderniseringgba.isc.jbpm.AbstractJbpmDaoTest;

import org.junit.Assert;
import org.junit.Test;

public class JbpmBerichtenDaoTest extends AbstractJbpmDaoTest {

    private final JbpmBerichtenDao subject = new JbpmBerichtenDao();

    public JbpmBerichtenDaoTest() {
        super("/sql/berichten.sql");
    }

    @Test
    public void test() {
        Assert.assertNull(subject.getBericht(-1L));

        final Long id = subject.saveBericht("BRP", Direction.INKOMEND, "0001", "0000", "inhoud1");
        subject.updateGemeenten(id, "0512", "0630");
        subject.updateNaam(id, "testbericht");
        subject.updateProcessInstance(id, 12343432L);
        subject.updateHerhaling(id, 0);

        final Bericht bericht = subject.getBericht(id);
        Assert.assertEquals(Long.valueOf(1L), bericht.getId());
        Assert.assertNotNull(bericht.getTijdstip());
        Assert.assertEquals("BRP", bericht.getKanaal());
        Assert.assertEquals(Direction.INKOMEND, bericht.getRichting());
        Assert.assertEquals("0001", bericht.getMessageId());
        Assert.assertEquals("0000", bericht.getCorrelationId());
        Assert.assertEquals("0512", bericht.getBronGemeente());
        Assert.assertEquals("0630", bericht.getDoelGemeente());
        Assert.assertEquals("inhoud1", bericht.getBericht());
        Assert.assertEquals("testbericht", bericht.getNaam());
        Assert.assertEquals(Long.valueOf(12343432L), bericht.getProcessInstanceId());
        Assert.assertEquals(Integer.valueOf(0), bericht.getHerhaling());

        final List<Bericht> berichtenViaProcessId = subject.findBerichtenByProcessInstanceId(12343432L);
        Assert.assertEquals(1, berichtenViaProcessId.size());
        Assert.assertEquals(bericht, berichtenViaProcessId.get(0));

        final List<Bericht> berichtenViaMessageId =
                subject.findBerichtenByMessageId("0001", "BRP", Direction.INKOMEND);
        Assert.assertEquals(1, berichtenViaMessageId.size());
        Assert.assertEquals(bericht, berichtenViaMessageId.get(0));
    }
}
