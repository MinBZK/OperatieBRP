/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.jbehave.validatie;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unittest voor {@link MetaTag}.
 */
public class MetaTagTest {

    @Test
    public void testMetaTag() {
        final MetaTag metaTag = new MetaTag("@test waarde");
        assertEquals("@test", metaTag.getNaam());
        assertEquals("waarde", metaTag.getWaarde());
        assertFalse(metaTag.isStatusTag());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGeenMetaTag() {
        new MetaTag("test waarde");
    }

    @Test
    public void testMetaTagIsStatusTag() {
        final MetaTag metaTag = new MetaTag("@status           waarde,waarde2");
        assertEquals("@status", metaTag.getNaam());
        assertEquals("waarde,waarde2", metaTag.getWaarde());
        assertTrue(metaTag.isStatusTag());
    }

    @Test
    public void testMetaTagAlleenTag() {
        final MetaTag metaTag = new MetaTag("@test");
        assertEquals("@test", metaTag.getNaam());
        assertNull(metaTag.getWaarde());
    }
}
