/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import org.junit.Before;
import org.junit.Test;

/**
 * Unittest voor {@link SoortActieBrongebruikSleutel}.
 */
public class SoortActieBrongebruikSleutelTest {

    private final SoortDocument SOORT_DOCUMENT = new SoortDocument("Huwelijksakte", "Huwelijksakte");
    private SoortActieBrongebruikSleutel sleutel;
    private SoortActieBrongebruikSleutel andereSleutel;

    @Before
    public void setUp() {
        sleutel =
                new SoortActieBrongebruikSleutel(
                    SoortActie.REGISTRATIE_AANVANG_HUWELIJK,
                    SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_NEDERLAND,
                    SOORT_DOCUMENT);
        andereSleutel =
                new SoortActieBrongebruikSleutel(
                    SoortActie.REGISTRATIE_AANVANG_HUWELIJK,
                    SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_BUITENLAND,
                    SOORT_DOCUMENT);
    }

    @Test
    public void testEquals() {
        assertFalse(sleutel.equals(null));
        assertFalse(sleutel.equals("test"));
        assertFalse(sleutel.equals(andereSleutel));
        assertFalse(andereSleutel.equals(sleutel));
        assertTrue(sleutel.equals(sleutel));
    }

    @Test
    public void testHashCode() {
        final SoortActieBrongebruikSleutel tempSleutel =
                new SoortActieBrongebruikSleutel(
                    SoortActie.REGISTRATIE_AANVANG_HUWELIJK,
                    SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_NEDERLAND,
                    SOORT_DOCUMENT);
        assertNotNull(sleutel.hashCode());
        assertEquals(sleutel.hashCode(), tempSleutel.hashCode());
        assertNotSame(sleutel.hashCode(), andereSleutel.hashCode());
    }
}
