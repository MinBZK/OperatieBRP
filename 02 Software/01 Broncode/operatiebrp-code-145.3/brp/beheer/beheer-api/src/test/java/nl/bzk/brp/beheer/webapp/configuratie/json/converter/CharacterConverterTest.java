/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.converter;

import org.junit.Assert;
import org.junit.Test;

/**
 * test voor CharacterConverter.
 */
public class CharacterConverterTest {

    private final CharacterConverter conv = new CharacterConverter();

    /** test normale covert. */
    @Test
    public final void testConvert() {
        Assert.assertEquals("String C moet karakter C zijn", Character.valueOf('C'), conv.convert("C"));
    }

    /** test normale covert. */
    @Test
    public final void testConvertLangeString() {
        Assert.assertEquals("String C moet karakter C zijn", Character.valueOf('C'), conv.convert("Charactertje"));
    }

    /** test null config. */
    @Test
    public final void testConvertNull() {
        Assert.assertNull("Null waarde moet leiden tot null", conv.convert(null));
    }
}
