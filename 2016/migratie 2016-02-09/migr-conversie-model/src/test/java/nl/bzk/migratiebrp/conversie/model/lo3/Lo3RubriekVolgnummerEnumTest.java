/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3;

import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3RubriekVolgnummerEnum;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class voor Lo3RubriekVolgnummerEnum
 */
public class Lo3RubriekVolgnummerEnumTest {
    @Test
    public void testToString() {
        Assert.assertEquals("10", Lo3RubriekVolgnummerEnum.VOLGNR_10.toString());
    }

    @Test
    public void testBestaandeNummers() {
        try {
            Assert.assertNotNull(Lo3RubriekVolgnummerEnum.getLo3RubriekVolgnummer("10"));
            Assert.assertNotNull(Lo3RubriekVolgnummerEnum.getLo3RubriekVolgnummer("15"));
            Assert.assertNotNull(Lo3RubriekVolgnummerEnum.getLo3RubriekVolgnummer("20"));
            Assert.assertNotNull(Lo3RubriekVolgnummerEnum.getLo3RubriekVolgnummer("30"));
            Assert.assertNotNull(Lo3RubriekVolgnummerEnum.getLo3RubriekVolgnummer("40"));
            Assert.assertNotNull(Lo3RubriekVolgnummerEnum.getLo3RubriekVolgnummer("50"));
            Assert.assertNotNull(Lo3RubriekVolgnummerEnum.getLo3RubriekVolgnummer("60"));
            Assert.assertNotNull(Lo3RubriekVolgnummerEnum.getLo3RubriekVolgnummer("70"));
            Assert.assertNotNull(Lo3RubriekVolgnummerEnum.getLo3RubriekVolgnummer("80"));
            Assert.assertNotNull(Lo3RubriekVolgnummerEnum.getLo3RubriekVolgnummer("90"));
        } catch (final Lo3SyntaxException lse) {
            Assert.fail("Exception niet verwacht. Message:" + lse.getMessage());
        }
    }

    @Test
    public void testNietBestaandNummer() {
        try {
            Lo3RubriekVolgnummerEnum.getLo3RubriekVolgnummer("25");
            Assert.fail("Lo3SyntaxException verwacht omdat 25 niet bestaat als rubriekvolgnummer");
        } catch (final Lo3SyntaxException lse) {
            Assert.assertNotNull(lse.getMessage());
            Assert.assertNotNull(lse.getCause());
        }
    }

    @Test
    public void enumRondTest() {
        final Lo3RubriekVolgnummerEnum volgnummerEnum = Lo3RubriekVolgnummerEnum.VOLGNR_10;
        final String rubriekVolgnummer = volgnummerEnum.toString();
        Assert.assertEquals("10", rubriekVolgnummer);
        try {
            Assert.assertEquals(volgnummerEnum, Lo3RubriekVolgnummerEnum.getLo3RubriekVolgnummer(rubriekVolgnummer));
        } catch (final Lo3SyntaxException lse) {
            Assert.fail("Exception niet verwacht. Message:" + lse.getMessage());
        }
    }

}
