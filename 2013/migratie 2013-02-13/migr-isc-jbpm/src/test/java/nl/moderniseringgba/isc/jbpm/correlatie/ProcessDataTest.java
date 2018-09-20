/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.jbpm.correlatie;

import org.junit.Assert;
import org.junit.Test;

public class ProcessDataTest {

    @Test
    public void test() {
        final ProcessData processData = new ProcessData(1L, 2L, 3L, "fi", 4, "0500", "0600");
        Assert.assertEquals(Long.valueOf(1L), processData.getProcessInstanceId());
        Assert.assertEquals(Long.valueOf(2L), processData.getTokenId());
        Assert.assertEquals(Long.valueOf(3L), processData.getNodeId());
        Assert.assertEquals("fi", processData.getCounterName());
        Assert.assertEquals(Integer.valueOf(4), processData.getCounterValue());
        Assert.assertEquals("0500", processData.getBronGemeente());
        Assert.assertEquals("0600", processData.getDoelGemeente());

    }

}
