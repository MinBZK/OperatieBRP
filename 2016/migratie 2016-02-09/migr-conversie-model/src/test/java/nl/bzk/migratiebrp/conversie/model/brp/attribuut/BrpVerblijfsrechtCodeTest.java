/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.attribuut;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BrpVerblijfsrechtCodeTest {

    @Test
    public void testGetFormattedStringCode() throws Exception {
        BrpVerblijfsrechtCode code = new BrpVerblijfsrechtCode(Short.parseShort("4"));
        assertEquals("04", code.getFormattedStringCode());
    }
}
