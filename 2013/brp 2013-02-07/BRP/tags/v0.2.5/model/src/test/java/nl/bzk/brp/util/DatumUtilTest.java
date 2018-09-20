/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util;

import nl.bzk.brp.model.attribuuttype.Datum;
import org.junit.Assert;
import org.junit.Test;

public class DatumUtilTest {

    @Test
    public void testIsDatumVolledigDatum() {
        Assert.assertEquals(true, DatumUtil.isVolledigDatum(new Datum(19920407)));
        Assert.assertEquals(false, DatumUtil.isVolledigDatum(new Datum(19920400)));
        Assert.assertEquals(false, DatumUtil.isVolledigDatum(new Datum(19920000)));
    }

    @Test
    public void testIsDatumsGeldigOpPeriode() {
        Assert.assertTrue (DatumUtil.isDatumsGeldigOpPeriode(               null, null                , new Datum(20120303) , new Datum(20120404)));
        Assert.assertTrue (DatumUtil.isDatumsGeldigOpPeriode(new Datum(19000101), new Datum(25000101) , new Datum(20120303) , new Datum(20120404)));
        Assert.assertTrue (DatumUtil.isDatumsGeldigOpPeriode(new Datum(19000101), null                , new Datum(20120303) , new Datum(20120404)));
        Assert.assertTrue (DatumUtil.isDatumsGeldigOpPeriode(null               , null                , null                , null));
        Assert.assertFalse(DatumUtil.isDatumsGeldigOpPeriode(new Datum(19000101), null                , null                , null));
        Assert.assertTrue (DatumUtil.isDatumsGeldigOpPeriode(new Datum(19000101), null                , new Datum(20010101) , null));
        Assert.assertTrue (DatumUtil.isDatumsGeldigOpPeriode(new Datum(19000101), null                , new Datum(19000101) , null));
        Assert.assertFalse(DatumUtil.isDatumsGeldigOpPeriode(new Datum(20000101), null                , new Datum(19911231) , null));
        Assert.assertFalse(DatumUtil.isDatumsGeldigOpPeriode(new Datum(20000101), null                , new Datum(19911231) , new Datum(19911231)));
        Assert.assertFalse(DatumUtil.isDatumsGeldigOpPeriode(new Datum(19920101), new Datum(200020101), new Datum(19911231) , null));
        Assert.assertFalse(DatumUtil.isDatumsGeldigOpPeriode(new Datum(19920101), new Datum(200020101), new Datum(19910231) , new Datum(19911231)));
        Assert.assertFalse(DatumUtil.isDatumsGeldigOpPeriode(new Datum(19920101), new Datum(200020101), new Datum(19910231) , new Datum(19970231)));
        Assert.assertFalse(DatumUtil.isDatumsGeldigOpPeriode(new Datum(19920101), new Datum(200020101), new Datum(19910231) , new Datum(20070231)));
        Assert.assertTrue (DatumUtil.isDatumsGeldigOpPeriode(new Datum(19920101), new Datum(200020101), new Datum(19920101) , new Datum(200020101)));
        Assert.assertFalse(DatumUtil.isDatumsGeldigOpPeriode(new Datum(19920101), new Datum(200020101), new Datum(200020102), new Datum(200020103)));
        Assert.assertFalse(DatumUtil.isDatumsGeldigOpPeriode(new Datum(19920101), new Datum(200020101), new Datum(19911230) , new Datum(19911231)));
    }

    @Test
    public void testIsGeldigOp() {
        Assert.assertTrue (DatumUtil.isGeldigOp(null               , null               , new Datum(20121003)));
        Assert.assertTrue (DatumUtil.isGeldigOp(new Datum(0)       , null               , new Datum(20121003)));
        Assert.assertFalse(DatumUtil.isGeldigOp(new Datum(0)       , new Datum(0)       , new Datum(20121003)));
        Assert.assertTrue (DatumUtil.isGeldigOp(new Datum(20121003), null               , new Datum(20121003)));
        Assert.assertFalse(DatumUtil.isGeldigOp(new Datum(20121004), null               , new Datum(20121003)));
        Assert.assertFalse(DatumUtil.isGeldigOp(new Datum(20121003), new Datum(0), new Datum(20121003)));
        Assert.assertTrue (DatumUtil.isGeldigOp(new Datum(20121003), new Datum(20121004), new Datum(20121003)));
        Assert.assertFalse(DatumUtil.isGeldigOp(new Datum(20121003), new Datum(20121003), new Datum(20121003)));
    }
}
