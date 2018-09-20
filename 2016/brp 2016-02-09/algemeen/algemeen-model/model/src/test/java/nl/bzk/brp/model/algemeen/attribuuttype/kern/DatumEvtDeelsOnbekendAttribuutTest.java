/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.attribuuttype.kern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Test;

public class DatumEvtDeelsOnbekendAttribuutTest {

    @Test
    public void testIsDatumVolledigDatum() {
        assertTrue(new DatumEvtDeelsOnbekendAttribuut((Integer) null).isVolledigDatumWaarde());

        assertTrue(new DatumEvtDeelsOnbekendAttribuut(19920407).isVolledigDatumWaarde());
        assertFalse(new DatumEvtDeelsOnbekendAttribuut(19920400).isVolledigDatumWaarde());
        assertFalse(new DatumEvtDeelsOnbekendAttribuut(19920000).isVolledigDatumWaarde());
    }

    @Test
    public void zouHetJaarTerugMoetenGeven() {
        assertEquals(new DatumEvtDeelsOnbekendAttribuut(20121212).getJaar(), 2012);
    }

    @Test
    public void zouNulTerugMoetenGevenOmdatJaarOnbekendIs() {
        assertEquals(new DatumEvtDeelsOnbekendAttribuut(1212).getJaar(), 0);
    }

    @Test
    public void zouDeMaandTerugMoetenGeven() {
        assertEquals(new DatumEvtDeelsOnbekendAttribuut(20121212).getMaand(), 12);
    }

    @Test
    public void zouNulTerugMoetenGevenOmdatMaandOnbekendIs() {
        assertEquals(new DatumEvtDeelsOnbekendAttribuut(20120012).getMaand(), 0);
    }

    @Test
    public void zouDagTerugMoetenGeven() {
        assertEquals(new DatumEvtDeelsOnbekendAttribuut(20121212).getDag(), 12);
    }

    @Test
    public void zouNulTerugMoetenGevenOmdatDagOnbekendIs() {
        assertEquals(new DatumEvtDeelsOnbekendAttribuut(20121200).getDag(), 0);
    }

    @Test
    public void testdatumVoorDatumSoepel() {
        assertTrue(new DatumEvtDeelsOnbekendAttribuut(20120101).voorDatumSoepel(new DatumEvtDeelsOnbekendAttribuut(20120102)));
        assertFalse(new DatumEvtDeelsOnbekendAttribuut(20120102).voorDatumSoepel(new DatumEvtDeelsOnbekendAttribuut(20120101)));
        assertTrue(new DatumEvtDeelsOnbekendAttribuut(20090000).voorDatumSoepel(new DatumEvtDeelsOnbekendAttribuut(20100000)));
        assertFalse(new DatumEvtDeelsOnbekendAttribuut(20101010).voorDatumSoepel(new DatumEvtDeelsOnbekendAttribuut(20101010)));
        assertTrue(new DatumEvtDeelsOnbekendAttribuut(20100000).voorDatumSoepel(new DatumEvtDeelsOnbekendAttribuut(20100000)));
    }

    @Test
    public void testdatumVoorOfOpDatumSoepel() {
        assertTrue(new DatumEvtDeelsOnbekendAttribuut(20120101).voorOfOpDatumSoepel(new DatumEvtDeelsOnbekendAttribuut(20120102)));
        assertFalse(new DatumEvtDeelsOnbekendAttribuut(20120102).voorOfOpDatumSoepel(new DatumEvtDeelsOnbekendAttribuut(20120101)));
        assertTrue(new DatumEvtDeelsOnbekendAttribuut(20101010).voorOfOpDatumSoepel(new DatumEvtDeelsOnbekendAttribuut(20101010)));
    }

    @Test
    public void testdatumVoorOfOpDatumSoepelOnbekendDatum1() {
        assertTrue(new DatumEvtDeelsOnbekendAttribuut(20120401).voorOfOpDatumSoepel(new DatumEvtDeelsOnbekendAttribuut(20120401)));
        assertTrue(new DatumEvtDeelsOnbekendAttribuut(20120400).voorOfOpDatumSoepel(new DatumEvtDeelsOnbekendAttribuut(20120401)));
        assertTrue(new DatumEvtDeelsOnbekendAttribuut(20120000).voorOfOpDatumSoepel(new DatumEvtDeelsOnbekendAttribuut(20120401)));
        assertFalse(new DatumEvtDeelsOnbekendAttribuut(20120500).voorOfOpDatumSoepel(new DatumEvtDeelsOnbekendAttribuut(20120401)));
        assertFalse(new DatumEvtDeelsOnbekendAttribuut(20130000).voorOfOpDatumSoepel(new DatumEvtDeelsOnbekendAttribuut(20120401)));
        assertTrue(new DatumEvtDeelsOnbekendAttribuut(20120200).voorOfOpDatumSoepel(new DatumEvtDeelsOnbekendAttribuut(20120201)));
        assertTrue(new DatumEvtDeelsOnbekendAttribuut(20120200).voorOfOpDatumSoepel(new DatumEvtDeelsOnbekendAttribuut(20120228)));
        assertFalse(new DatumEvtDeelsOnbekendAttribuut(20120300).voorOfOpDatumSoepel(new DatumEvtDeelsOnbekendAttribuut(20120228)));
    }

    @Test
    public void testdatumVoorOfOpDatumSoepelOnbekendDatum2() {
        // datum2 dag onbekend: alle datums buiten de maand is false, binnen de maand of VOOR de maand is goed
        assertTrue(new DatumEvtDeelsOnbekendAttribuut(20120406).voorOfOpDatumSoepel(new DatumEvtDeelsOnbekendAttribuut(20120400)));
        assertTrue(new DatumEvtDeelsOnbekendAttribuut(20120430).voorOfOpDatumSoepel(new DatumEvtDeelsOnbekendAttribuut(20120400)));
        assertTrue(new DatumEvtDeelsOnbekendAttribuut(20120401).voorOfOpDatumSoepel(new DatumEvtDeelsOnbekendAttribuut(20120400)));
        assertFalse(new DatumEvtDeelsOnbekendAttribuut(20120501).voorOfOpDatumSoepel(new DatumEvtDeelsOnbekendAttribuut(20120400)));

        // ook datum1 is dag onbekend.
        assertTrue(new DatumEvtDeelsOnbekendAttribuut(20120400).voorOfOpDatumSoepel(new DatumEvtDeelsOnbekendAttribuut(20120400)));
        assertTrue(new DatumEvtDeelsOnbekendAttribuut(20120300).voorOfOpDatumSoepel(new DatumEvtDeelsOnbekendAttribuut(20120400)));
        assertFalse(new DatumEvtDeelsOnbekendAttribuut(20120500).voorOfOpDatumSoepel(new DatumEvtDeelsOnbekendAttribuut(20120400)));

        // ook datum1 maand onbekend.
        assertTrue(new DatumEvtDeelsOnbekendAttribuut(20120000).voorOfOpDatumSoepel(new DatumEvtDeelsOnbekendAttribuut(20120400)));
        assertFalse(new DatumEvtDeelsOnbekendAttribuut(20130000).voorOfOpDatumSoepel(new DatumEvtDeelsOnbekendAttribuut(20120400)));
        // ook datum1 jaar onbekend.
        assertTrue(new DatumEvtDeelsOnbekendAttribuut(0).voorOfOpDatumSoepel(new DatumEvtDeelsOnbekendAttribuut(20120400)));

        // datum2 maand onbekend: alle datums NA het jaar is false, binnen het jaar of VOOR het jaar is goed
        assertTrue(new DatumEvtDeelsOnbekendAttribuut(20120406).voorOfOpDatumSoepel(new DatumEvtDeelsOnbekendAttribuut(20120000)));
        assertTrue(new DatumEvtDeelsOnbekendAttribuut(20121231).voorOfOpDatumSoepel(new DatumEvtDeelsOnbekendAttribuut(20120000)));
        assertTrue(new DatumEvtDeelsOnbekendAttribuut(20120101).voorOfOpDatumSoepel(new DatumEvtDeelsOnbekendAttribuut(20120000)));
        assertTrue(new DatumEvtDeelsOnbekendAttribuut(20111231).voorOfOpDatumSoepel(new DatumEvtDeelsOnbekendAttribuut(20120000)));
        assertFalse(new DatumEvtDeelsOnbekendAttribuut(20130101).voorOfOpDatumSoepel(new DatumEvtDeelsOnbekendAttribuut(20120000)));

        // ook datum1 maand onbekend
        assertTrue(new DatumEvtDeelsOnbekendAttribuut(20120100).voorOfOpDatumSoepel(new DatumEvtDeelsOnbekendAttribuut(20120000)));
        assertTrue(new DatumEvtDeelsOnbekendAttribuut(20121200).voorOfOpDatumSoepel(new DatumEvtDeelsOnbekendAttribuut(20120000)));
        assertTrue(new DatumEvtDeelsOnbekendAttribuut(20111200).voorOfOpDatumSoepel(new DatumEvtDeelsOnbekendAttribuut(20120000)));

        assertTrue(new DatumEvtDeelsOnbekendAttribuut(20120000).voorOfOpDatumSoepel(new DatumEvtDeelsOnbekendAttribuut(20120000)));
        assertFalse(new DatumEvtDeelsOnbekendAttribuut(20130000).voorOfOpDatumSoepel(new DatumEvtDeelsOnbekendAttribuut(20120000)));

        // datum2 jaar onbekend ==> alles is goed; immers jaar kan alle waarden hebben.
        assertTrue(new DatumEvtDeelsOnbekendAttribuut(1231).voorOfOpDatumSoepel(new DatumEvtDeelsOnbekendAttribuut(0)));
        assertTrue(new DatumEvtDeelsOnbekendAttribuut(10101).voorOfOpDatumSoepel(new DatumEvtDeelsOnbekendAttribuut(0)));
        assertTrue(new DatumEvtDeelsOnbekendAttribuut(20120101).voorOfOpDatumSoepel(new DatumEvtDeelsOnbekendAttribuut(0)));

        assertTrue(new DatumEvtDeelsOnbekendAttribuut(0).voorOfOpDatumSoepel(new DatumEvtDeelsOnbekendAttribuut(0)));
        assertTrue(new DatumEvtDeelsOnbekendAttribuut(20120000).voorOfOpDatumSoepel(new DatumEvtDeelsOnbekendAttribuut(0)));
        assertTrue(new DatumEvtDeelsOnbekendAttribuut(20120800).voorOfOpDatumSoepel(new DatumEvtDeelsOnbekendAttribuut(0)));
    }

    @Test
    public void testdatumVoorOfOpDatumSoepelOnbekendDatums() {
        assertTrue(new DatumEvtDeelsOnbekendAttribuut(20120400).voorOfOpDatumSoepel(new DatumEvtDeelsOnbekendAttribuut(20120400)));
        assertTrue(new DatumEvtDeelsOnbekendAttribuut(20120300).voorOfOpDatumSoepel(new DatumEvtDeelsOnbekendAttribuut(20120400)));
        assertFalse(new DatumEvtDeelsOnbekendAttribuut(20120500).voorOfOpDatumSoepel(new DatumEvtDeelsOnbekendAttribuut(20120400)));

        assertTrue(new DatumEvtDeelsOnbekendAttribuut(20120000).voorOfOpDatumSoepel(new DatumEvtDeelsOnbekendAttribuut(20120000)));
        assertTrue(new DatumEvtDeelsOnbekendAttribuut(20120100).voorOfOpDatumSoepel(new DatumEvtDeelsOnbekendAttribuut(20120000)));
        assertTrue(new DatumEvtDeelsOnbekendAttribuut(20121200).voorOfOpDatumSoepel(new DatumEvtDeelsOnbekendAttribuut(20120000)));
        assertTrue(new DatumEvtDeelsOnbekendAttribuut(20110000).voorOfOpDatumSoepel(new DatumEvtDeelsOnbekendAttribuut(20120000)));
        assertFalse(new DatumEvtDeelsOnbekendAttribuut(20130000).voorOfOpDatumSoepel(new DatumEvtDeelsOnbekendAttribuut(20120000)));

        assertTrue(new DatumEvtDeelsOnbekendAttribuut(0).voorOfOpDatumSoepel(new DatumEvtDeelsOnbekendAttribuut(0)));
    }

    @Test
    public void testIsGeldigeKalenderdatum() {
        assertTrue(new DatumEvtDeelsOnbekendAttribuut(20100331).isGeldigeKalenderdatum());
        assertFalse(new DatumEvtDeelsOnbekendAttribuut(20100332).isGeldigeKalenderdatum());

        assertTrue(new DatumEvtDeelsOnbekendAttribuut(20100430).isGeldigeKalenderdatum());
        assertFalse(new DatumEvtDeelsOnbekendAttribuut(20100431).isGeldigeKalenderdatum());

        assertTrue(new DatumEvtDeelsOnbekendAttribuut(20100228).isGeldigeKalenderdatum());
        // Schrikkeljaar
        assertFalse(new DatumEvtDeelsOnbekendAttribuut(20100229).isGeldigeKalenderdatum());

        assertFalse(new DatumEvtDeelsOnbekendAttribuut(20101301).isGeldigeKalenderdatum());

        // Onvolledige datum:
        assertFalse(new DatumEvtDeelsOnbekendAttribuut(20101300).isGeldigeKalenderdatum());
    }

    @Test
    public void testMinDatums() {
        Assert.assertEquals(Integer.valueOf(20110512), new DatumEvtDeelsOnbekendAttribuut(20110512).getMinimaalDeelOnbekendDatum().getWaarde());
        Assert.assertEquals(Integer.valueOf(20110101), new DatumEvtDeelsOnbekendAttribuut(20110000).getMinimaalDeelOnbekendDatum().getWaarde());
        Assert.assertEquals(Integer.valueOf(20110801), new DatumEvtDeelsOnbekendAttribuut(20110800).getMinimaalDeelOnbekendDatum().getWaarde());
        Assert.assertEquals(Integer.valueOf(10101), new DatumEvtDeelsOnbekendAttribuut(0).getMinimaalDeelOnbekendDatum().getWaarde());
    }

    @Test
    public void testMaxDatums() {
        Assert.assertEquals(Integer.valueOf(20110512), new DatumEvtDeelsOnbekendAttribuut(20110512).getMaximaalDeelOnbekendDatum().getWaarde());
        Assert.assertEquals(Integer.valueOf(20111231), new DatumEvtDeelsOnbekendAttribuut(20110000).getMaximaalDeelOnbekendDatum().getWaarde());
        Assert.assertEquals(Integer.valueOf(20110831), new DatumEvtDeelsOnbekendAttribuut(20110800).getMaximaalDeelOnbekendDatum().getWaarde());
        Assert.assertEquals(Integer.valueOf(99991231), new DatumEvtDeelsOnbekendAttribuut(0).getMaximaalDeelOnbekendDatum().getWaarde());
        Assert.assertEquals(Integer.valueOf(20110228), new DatumEvtDeelsOnbekendAttribuut(20110200).getMaximaalDeelOnbekendDatum().getWaarde());
        Assert.assertEquals(Integer.valueOf(20080229), new DatumEvtDeelsOnbekendAttribuut(20080200).getMaximaalDeelOnbekendDatum().getWaarde());
        Assert.assertEquals(Integer.valueOf(21000228), new DatumEvtDeelsOnbekendAttribuut(21000200).getMaximaalDeelOnbekendDatum().getWaarde());
        Assert.assertEquals(Integer.valueOf(24000229), new DatumEvtDeelsOnbekendAttribuut(24000200).getMaximaalDeelOnbekendDatum().getWaarde());
    }

    @Test
    public void testDagToevoegen() {
        final DatumEvtDeelsOnbekendAttribuut datum1 = new DatumEvtDeelsOnbekendAttribuut(20120130);
        datum1.voegDagToe(1);
        assertEquals(datum1, new DatumEvtDeelsOnbekendAttribuut(20120131));

        final DatumEvtDeelsOnbekendAttribuut datum2 = new DatumEvtDeelsOnbekendAttribuut(20121231);
        datum2.voegDagToe(1);
        assertEquals(datum2, new DatumEvtDeelsOnbekendAttribuut(20130101));

        final DatumEvtDeelsOnbekendAttribuut datum3 = new DatumEvtDeelsOnbekendAttribuut(20120101);
        datum3.voegDagToe(-1);
        assertEquals(datum3, new DatumEvtDeelsOnbekendAttribuut(20111231));
    }

    @Test
    public void testMaandToevoegen() {
        final DatumEvtDeelsOnbekendAttribuut datum1 = new DatumEvtDeelsOnbekendAttribuut(20120131);
        datum1.voegMaandToe(1);
        assertEquals(datum1, new DatumEvtDeelsOnbekendAttribuut(20120229));

        final DatumEvtDeelsOnbekendAttribuut datum2 = new DatumEvtDeelsOnbekendAttribuut(20120131);
        datum2.voegMaandToe(2);
        assertEquals(datum2, new DatumEvtDeelsOnbekendAttribuut(20120331));

        final DatumEvtDeelsOnbekendAttribuut datum3 = new DatumEvtDeelsOnbekendAttribuut(20120131);
        datum3.voegMaandToe(3);
        assertEquals(datum3, new DatumEvtDeelsOnbekendAttribuut(20120430));
    }

    @Test
    public void testJaarToevoegen() {
        final DatumEvtDeelsOnbekendAttribuut datum1 = new DatumEvtDeelsOnbekendAttribuut(20120129);
        datum1.voegJaarToe(1);
        assertEquals(datum1, new DatumEvtDeelsOnbekendAttribuut(20130129));

        final DatumEvtDeelsOnbekendAttribuut datum2 = new DatumEvtDeelsOnbekendAttribuut(20120229);
        datum2.voegJaarToe(1);
        assertEquals(datum2, new DatumEvtDeelsOnbekendAttribuut(20130228));

        final DatumEvtDeelsOnbekendAttribuut datum3 = new DatumEvtDeelsOnbekendAttribuut(20120229);
        datum3.voegJaarToe(-1);
        assertEquals(datum3, new DatumEvtDeelsOnbekendAttribuut(20110228));

        final DatumEvtDeelsOnbekendAttribuut datum4 = new DatumEvtDeelsOnbekendAttribuut(20120229);
        datum4.voegJaarToe(18);
        assertEquals(datum4, new DatumEvtDeelsOnbekendAttribuut(20300228));
    }
}
