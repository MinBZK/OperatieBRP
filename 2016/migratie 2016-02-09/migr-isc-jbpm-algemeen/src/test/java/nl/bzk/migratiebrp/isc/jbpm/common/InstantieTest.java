/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common;

import junit.framework.Assert;
import nl.bzk.migratiebrp.isc.jbpm.common.Instantie.Type;
import org.junit.Test;

public class InstantieTest {

    @Test
    public void test() {
        Assert.assertEquals(null, Instantie.bepaalInstantieType(null));
        Assert.assertEquals(null, Instantie.bepaalInstantieType("999"));
        Assert.assertEquals(Type.GEMEENTE, Instantie.bepaalInstantieType("9999"));
        Assert.assertEquals(null, Instantie.bepaalInstantieType("99999"));
        Assert.assertEquals(Type.AFNEMER, Instantie.bepaalInstantieType("999999"));
        Assert.assertEquals(Type.CENTRALE, Instantie.bepaalInstantieType("9999999"));
        Assert.assertEquals(null, Instantie.bepaalInstantieType("99999999"));
    }
}
