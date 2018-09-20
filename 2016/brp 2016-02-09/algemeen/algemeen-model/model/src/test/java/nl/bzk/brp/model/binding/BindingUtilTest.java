/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.binding;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieTenOpzichteVanAdres;
import org.junit.Assert;
import org.junit.Test;


/**
 * Unit test voor de methodes in de {@link BindingUtil} klasse.
 */
public class BindingUtilTest {

    @Test
    public void testLijstInstantiatie() {
        List<?> nieuweLijst = BindingUtil.newListInstance();
        Assert.assertNotNull(nieuweLijst);
        Assert.assertTrue(nieuweLijst.isEmpty());
    }

    @Test
    public void testSetInstantiatie() {
        Set<?> nieuweSet = BindingUtil.newSetInstance();
        Assert.assertNotNull(nieuweSet);
        Assert.assertTrue(nieuweSet.isEmpty());
    }

    @Test
    public void testTreeSetInstantiatie() {
        Set<?> nieuweSet = BindingUtil.newTreeSetInstance();
        Assert.assertNotNull(nieuweSet);
        Assert.assertTrue(nieuweSet.isEmpty());
    }

    @Test
    public void testGetalNaarStringZonderVoorloopnullen() {
        Assert.assertNull(BindingUtil.getalNaarString(null));
        Assert.assertEquals("1234", BindingUtil.getalNaarString(1234));
        Assert.assertEquals("34", BindingUtil.getalNaarString(new Short((short) 34)));
        Assert.assertEquals("34", BindingUtil.getalNaarString(new Long(34L)));

        Assert.assertEquals("100", BindingUtil.getalNaarString(new BigDecimal(100)));
    }

    @Test
    public void testGetalNaarStringMetVoorloopnullen() {
        Assert.assertNull(BindingUtil.getalNaarStringMetVoorloopnullen(null, 2));
        Assert.assertEquals("1234", BindingUtil.getalNaarStringMetVoorloopnullen(1234, 2));
        Assert.assertEquals("34", BindingUtil.getalNaarStringMetVoorloopnullen(34, 2));
        Assert.assertEquals("0034", BindingUtil.getalNaarStringMetVoorloopnullen(34, 4));

        Assert.assertEquals("0100", BindingUtil.getalNaarStringMetVoorloopnullen(new BigDecimal(100), 4));
    }

    @Test
    public void testStringNaarGetal() {
        // Test met nulls
        Assert.assertNull(BindingUtil.stringNaarLong(null));
        Assert.assertNull(BindingUtil.stringNaarInteger(null));
        Assert.assertNull(BindingUtil.stringNaarShort(null));

        // Test met lege/blank string
        Assert.assertNull(BindingUtil.stringNaarLong(" "));
        Assert.assertNull(BindingUtil.stringNaarInteger(" "));
        Assert.assertNull(BindingUtil.stringNaarShort(" "));

        // Test met getal
        Assert.assertEquals(Long.valueOf(2L), BindingUtil.stringNaarLong("2"));
        Assert.assertEquals(Integer.valueOf(2), BindingUtil.stringNaarInteger("2"));
        Assert.assertEquals(Short.valueOf((short) 2), BindingUtil.stringNaarShort("2"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testStringNaarLongMetFout() {
        BindingUtil.stringNaarLong("a");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testStringNaarIntegerMetFout() {
        BindingUtil.stringNaarInteger("a");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testStringNaarShortMetFout() {
        BindingUtil.stringNaarShort("a");
    }

    @Test
    public void testBooleanConversie() {
        Assert.assertNull(BindingUtil.booleanNaarString(null));
        Assert.assertNull(BindingUtil.stringNaarBoolean(null));
        Assert.assertNull(BindingUtil.stringNaarBoolean(" "));

        Assert.assertEquals("J", BindingUtil.booleanNaarString(Boolean.TRUE));
        Assert.assertEquals("N", BindingUtil.booleanNaarString(Boolean.FALSE));

        Assert.assertEquals(Boolean.TRUE, BindingUtil.stringNaarBoolean("J"));
        Assert.assertEquals(Boolean.FALSE, BindingUtil.stringNaarBoolean("N"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testStringNaarBooleanMetFout() {
        BindingUtil.stringNaarBoolean("FALSE");
    }

    @Test
    public void testEnumeratieWaardeConversie() {
        Assert.assertNull(BindingUtil.stringNaarEnumeratiewaarde(null, LocatieTenOpzichteVanAdres.class));
        Assert.assertNull(BindingUtil.enumeratiewaardeNaarString(null));

        Assert.assertEquals(LocatieTenOpzichteVanAdres.BY,
                BindingUtil.stringNaarEnumeratiewaarde("by", LocatieTenOpzichteVanAdres.class));
        Assert.assertEquals(LocatieTenOpzichteVanAdres.TO,
                BindingUtil.stringNaarEnumeratiewaarde("to", LocatieTenOpzichteVanAdres.class));
        Assert.assertEquals("by", BindingUtil.enumeratiewaardeNaarString(LocatieTenOpzichteVanAdres.BY));
        Assert.assertEquals("to", BindingUtil.enumeratiewaardeNaarString(LocatieTenOpzichteVanAdres.TO));

        Assert.assertNull(BindingUtil.stringNaarEnumeratiewaarde("FALSE", LocatieTenOpzichteVanAdres.class));
    }

    @Test
    public void testJaConversie() {
        Assert.assertNull(BindingUtil.jaNaarString(null));
        Assert.assertNull(BindingUtil.stringNaarJa(null));
        Assert.assertNull(BindingUtil.stringNaarJa(" "));

        Assert.assertEquals(Ja.J, BindingUtil.stringNaarJa("J"));
        Assert.assertEquals("J", BindingUtil.jaNaarString(Ja.J));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testJaConversieMetFout() {
        BindingUtil.stringNaarJa("N");
    }

    @Test
    public void testDatumGetalConversie() {
        Assert.assertNull(BindingUtil.datumAlsGetalNaarDatumAlsString(null));
        Assert.assertNull(BindingUtil.datumAlsStringNaarDatumAlsGetal(null));
        Assert.assertNull(BindingUtil.datumAlsStringNaarDatumAlsGetal(" "));

        Assert.assertEquals(Integer.valueOf(20130201), BindingUtil.datumAlsStringNaarDatumAlsGetal("2013-02-01"));
        Assert.assertEquals(Integer.valueOf(20130000), BindingUtil.datumAlsStringNaarDatumAlsGetal("2013-00-00"));
        Assert.assertEquals("2013-02-01", BindingUtil.datumAlsGetalNaarDatumAlsString(20130201));
        Assert.assertEquals("2013", BindingUtil.datumAlsGetalNaarDatumAlsString(20130000));
        Assert.assertEquals("0000", BindingUtil.datumAlsGetalNaarDatumAlsString(0));
    }

    @Test
    public void testW3CDatumTijdConversie() {
        Assert.assertEquals("", BindingUtil.javaDateNaarW3cDatumString(null));
        Assert.assertNull(BindingUtil.w3cDatumStringNaarJavaDate(null));
        Assert.assertNull(BindingUtil.w3cDatumStringNaarJavaDate(" "));

        Assert.assertEquals("2013-02-01T11:11:11.011+01:00",
                BindingUtil.javaDateNaarW3cDatumString(bouwDatum(2013, 2, 1)));
        Assert.assertEquals(bouwDatum(2013, 2, 1),
                BindingUtil.w3cDatumStringNaarJavaDate("2013-02-01T11:11:11.011+01:00"));
    }

    /**
     * Maak een Java Date object aan met de meegegeven jaar, maand dag.
     * De tijd wordt altijd op 11:11:11.11 gezet.
     * De timezone is de default van de JVM.
     *
     * @param jaar  het jaar
     * @param maand de maand
     * @param dag   de dag
     * @return een date object van het gewenste moment
     */
    private Date bouwDatum(final int jaar, final int maand, final int dag) {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, jaar);
        calendar.set(Calendar.MONTH, maand - 1);
        calendar.set(Calendar.DAY_OF_MONTH, dag);
        calendar.set(Calendar.HOUR, 11);
        calendar.set(Calendar.AM_PM, Calendar.AM);
        calendar.set(Calendar.MINUTE, 11);
        calendar.set(Calendar.SECOND, 11);
        calendar.set(Calendar.MILLISECOND, 11);
        calendar.setTimeZone(TimeZone.getDefault());
        return calendar.getTime();
    }

}
