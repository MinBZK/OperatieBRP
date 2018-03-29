/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.attribuut.autorisatie;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class BrpProtocolleringsniveauCodeTest {
    @Test
    public void test() {
        final BrpProtocolleringsniveauCode subject = BrpProtocolleringsniveauCode.GEHEIM;
        final BrpProtocolleringsniveauCode equal = new BrpProtocolleringsniveauCode(subject.getCode());

        assertEquals(subject.getCode(), equal.getCode());
        assertFalse(subject.equals(equal.getCode()));
    }
}
