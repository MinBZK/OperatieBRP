/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.literal;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.expressie.DatumLiteral;
import nl.bzk.brp.domain.expressie.Datumdeel;
import nl.bzk.brp.domain.expressie.DatumtijdLiteral;
import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.expressie.util.TestUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * DateTimeLiteralExpressieTest.
 */
public class DatumtijdLiteralTest {

    private static final ZonedDateTime DATE_TIME = ZonedDateTime.of(2010, 1, 2, 0, 0, 0, 0, DatumUtil.NL_ZONE_ID);

    @Test
    public void test() {
        final DatumtijdLiteral datumtijdLiteral = new DatumtijdLiteral(DATE_TIME);
        Assert.assertEquals(2010, datumtijdLiteral.getJaar().getWaarde());
        Assert.assertEquals(1, datumtijdLiteral.getMaand().getWaarde());
        Assert.assertEquals(2, datumtijdLiteral.getDag().getWaarde());
    }

    @Test
    public void testMetJaarMaandDagUurMinuutSeconde() throws ExpressieException {
        final DatumtijdLiteral datumtijdLiteral = new DatumtijdLiteral(Datumdeel.valueOf(2010), Datumdeel.valueOf(10),
                Datumdeel.valueOf(10), 0, 0, 0);
        Assert.assertEquals(2010, datumtijdLiteral.getJaar().getWaarde());
        Assert.assertEquals(10, datumtijdLiteral.getMaand().getWaarde());
        Assert.assertEquals(10, datumtijdLiteral.getDag().getWaarde());

        TestUtils.assertExceptie("-1/12/01", "Jaar incorrect: 1");
        TestUtils.assertExceptie("1550/12/01", "Jaar incorrect: 1550");
        TestUtils.assertExceptie("4550/12/01", "Jaar incorrect: 4550");
        TestUtils.assertExceptie("1980/-1/01", "Maand incorrect: -1");
        TestUtils.assertExceptie("1980/13/01", "Maand incorrect: 13");
        TestUtils.assertExceptie("1980/12/-1", "Dag incorrect: -1");
        TestUtils.assertExceptie("1980/12/33", "Dag incorrect: 33");
        TestUtils.assertExceptie("1970/2/29", "Dag incorrect: 29");
        TestUtils.assertExceptie("1980/12/12/-1/01/01", "Uur incorrect: -1");
        TestUtils.assertExceptie("1980/12/12/24/01/01", "Uur incorrect: 24");
        TestUtils.assertExceptie("1980/12/12/25/01/01", "Uur incorrect: 25");
        TestUtils.assertExceptie("1980/12/12/12/-1/12", "Minuut incorrect: -1");
        TestUtils.assertExceptie("1980/12/12/12/60/12", "Minuut incorrect: 60");
        TestUtils.assertExceptie("1980/12/12/12/61/12", "Minuut incorrect: 61");
        TestUtils.assertExceptie("1980/12/12/12/01/-1", "Seconde incorrect: -1");
        TestUtils.assertExceptie("1980/12/12/12/01/60", "Seconde incorrect: 60");
        TestUtils.assertExceptie("1980/12/12/12/01/61", "Seconde incorrect: 61");
    }

    @Test
    public void testAlsString() {
        final DatumtijdLiteral datumtijdLiteral = new DatumtijdLiteral(DATE_TIME);
        Assert.assertEquals("2010/01/02/00/00/00", datumtijdLiteral.toString());
    }

    @Test
    public void testEquals() {
        final DatumtijdLiteral datumtijdLiteral1 =
                new DatumtijdLiteral(Datumdeel.valueOf(2017), Datumdeel.valueOf(12), Datumdeel.valueOf(31), 12, 21, 11);
        final DatumtijdLiteral datumtijdLiteral2 =
                new DatumtijdLiteral(Datumdeel.valueOf(2017), Datumdeel.valueOf(12), Datumdeel.valueOf(31), 12, 21, 11);
        final DatumtijdLiteral datumtijdLiteral3 =
                new DatumtijdLiteral(Datumdeel.valueOf(2016), Datumdeel.valueOf(12), Datumdeel.valueOf(31), 12, 21, 11);

        assertTrue(datumtijdLiteral1.equals(datumtijdLiteral2));
        assertFalse(datumtijdLiteral1.equals(datumtijdLiteral3));
        assertFalse(datumtijdLiteral1.equals(null));
        assertFalse(datumtijdLiteral1.equals(new DatumLiteral(20170101)));
    }
}
