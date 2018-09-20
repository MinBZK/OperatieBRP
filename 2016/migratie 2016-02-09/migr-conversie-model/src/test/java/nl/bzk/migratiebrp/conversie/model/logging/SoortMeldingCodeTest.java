/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.logging;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;

import org.junit.Test;

public class SoortMeldingCodeTest {

    @Test
    public void testIsPreconditie() {
        assertTrue(SoortMeldingCode.PRE001.isPreconditie());
        assertFalse(SoortMeldingCode.ELEMENT.isPreconditie());
    }

}
