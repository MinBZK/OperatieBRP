/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc302;

import junit.framework.Assert;

import org.junit.Test;

public class ControleerIb01DecisionTest {

    private final ControleerIb01Decision subject = new ControleerIb01Decision();

    @Test
    public void testOk() {
        Assert.assertEquals(null, subject.execute(null));
    }
}
