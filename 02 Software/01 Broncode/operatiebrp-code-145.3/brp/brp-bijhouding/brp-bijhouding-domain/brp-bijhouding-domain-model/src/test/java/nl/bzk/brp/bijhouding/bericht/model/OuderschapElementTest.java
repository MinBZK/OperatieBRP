/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Testen voor {@link OuderschapElement}.
 */
public class OuderschapElementTest {

    @Test
    public void getIndicatieOuderUitWieKindIsGeboren() {
        assertEquals(BooleanElement.NEE, OuderschapElement.getInstance(Boolean.FALSE).getIndicatieOuderUitWieKindIsGeboren());
        assertNull(OuderschapElement.getInstance(null).getIndicatieOuderUitWieKindIsGeboren());
    }

    @Test
    public void valideerInhoud() {
        assertTrue(OuderschapElement.getInstance(null).valideerInhoud().isEmpty());
    }
}
