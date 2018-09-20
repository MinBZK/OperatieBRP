/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.jbpm.correlatie;

import nl.moderniseringgba.isc.jbpm.AbstractJbpmDaoTest;

import org.junit.Assert;
import org.junit.Test;

public class JbpmProcessCorrelatieStoreTest extends AbstractJbpmDaoTest {

    private final JbpmProcessCorrelatieStore subject = new JbpmProcessCorrelatieStore();

    public JbpmProcessCorrelatieStoreTest() {
        super("/sql/correlatie.sql");
    }

    @Test
    public void test() {
        Assert.assertNull(subject.zoekProcessCorrelatie("1"));

        subject.bewaarProcessCorrelatie("1", new ProcessData(1L, 2L, 3L, "fi", 4, "0500", "0600"));

        final ProcessData processData = subject.zoekProcessCorrelatie("1");
        Assert.assertNotNull(processData);
        Assert.assertEquals(Long.valueOf(1L), processData.getProcessInstanceId());
        Assert.assertEquals(Long.valueOf(2L), processData.getTokenId());
        Assert.assertEquals(Long.valueOf(3L), processData.getNodeId());
        Assert.assertEquals("fi", processData.getCounterName());
        Assert.assertEquals(Integer.valueOf(4), processData.getCounterValue());
        Assert.assertEquals("0500", processData.getBronGemeente());
        Assert.assertEquals("0600", processData.getDoelGemeente());

        subject.bewaarProcessCorrelatie("1", new ProcessData(5L, 6L, 7L, "qwe", 8, "0700", "0800"));
        final ProcessData processData2 = subject.zoekProcessCorrelatie("1");
        Assert.assertNotNull(processData2);
        Assert.assertEquals(Long.valueOf(5L), processData2.getProcessInstanceId());
        Assert.assertEquals(Long.valueOf(6L), processData2.getTokenId());
        Assert.assertEquals(Long.valueOf(7L), processData2.getNodeId());
        Assert.assertEquals("qwe", processData2.getCounterName());
        Assert.assertEquals(Integer.valueOf(8), processData2.getCounterValue());
        Assert.assertEquals("0700", processData2.getBronGemeente());
        Assert.assertEquals("0800", processData2.getDoelGemeente());

    }
}
