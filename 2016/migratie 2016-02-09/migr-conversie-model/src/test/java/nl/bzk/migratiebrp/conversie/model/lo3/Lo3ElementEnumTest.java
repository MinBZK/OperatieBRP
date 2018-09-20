/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;

import org.junit.Test;

/**
 * Test klasse voor Lo3ElementEnum.
 */
public class Lo3ElementEnumTest {

    @Test
    public void testBestaandeElement() {
        try {
            assertNotNull(Lo3ElementEnum.getLO3Element("0110"));
        } catch (final Lo3SyntaxException lse) {
            fail("Geen Lo3SyntaxException verwacht");
        }
    }

    @Test
    public void testNietBestaandeElement() {
        try {
            Lo3ElementEnum.getLO3Element("9910");
            Lo3ElementEnum.getLO3Element("0140");
            fail("Lo3SyntaxException verwacht");
        } catch (final Lo3SyntaxException lse) {
            assertNotNull(lse.getCause());
            assertNotNull(lse.getMessage());
        }
    }

    @Test
    public void testElement() {
        final String elementNummer = "6310";
        try {
            final Lo3ElementEnum element = Lo3ElementEnum.getLO3Element("6310");
            assertEquals(Lo3ElementEnum.Type.NUMERIEK, element.getType());
            assertEquals(elementNummer, element.getElementNummer());
            assertEquals(3, element.getMaximumLengte());
            assertEquals(3, element.getMinimumLengte());
            assertFalse(element.getAfkappen());
        } catch (final Lo3SyntaxException lse) {
            fail("Geen Lo3SyntaxException verwacht");
        }
    }
}
