/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import org.junit.Test;

public class DatumUtilTest {

    @Test
    public void testIsDatumsGeldigOpPeriode() {
        assertTrue(DatumAttribuut.isDatumsGeldigOpPeriode(null, null, new DatumAttribuut(20120303), new DatumAttribuut(20120404)));
        assertTrue(DatumAttribuut.isDatumsGeldigOpPeriode(new DatumAttribuut(19000101), new DatumAttribuut(25000101), new DatumAttribuut(20120303),
                                                     new DatumAttribuut(20120404)));
        assertTrue(DatumAttribuut.isDatumsGeldigOpPeriode(new DatumAttribuut(19000101), null, new DatumAttribuut(20120303), new DatumAttribuut(20120404)));
        assertTrue(DatumAttribuut.isDatumsGeldigOpPeriode(null, null, null, null));
        assertFalse(DatumAttribuut.isDatumsGeldigOpPeriode(new DatumAttribuut(19000101), null, null, null));
        assertTrue(DatumAttribuut.isDatumsGeldigOpPeriode(new DatumAttribuut(19000101), null, new DatumAttribuut(20010101), null));
        assertTrue(DatumAttribuut.isDatumsGeldigOpPeriode(new DatumAttribuut(19000101), null, new DatumAttribuut(19000101), null));
        assertFalse(DatumAttribuut.isDatumsGeldigOpPeriode(new DatumAttribuut(20000101), null, new DatumAttribuut(19911231), null));
        assertFalse(DatumAttribuut.isDatumsGeldigOpPeriode(new DatumAttribuut(20000101), null, new DatumAttribuut(19911231), new DatumAttribuut(19911231)));
        assertFalse(DatumAttribuut.isDatumsGeldigOpPeriode(new DatumAttribuut(19920101), new DatumAttribuut(200020101), new DatumAttribuut(19911231), null));
        assertFalse(DatumAttribuut.isDatumsGeldigOpPeriode(new DatumAttribuut(19920101), new DatumAttribuut(200020101), new DatumAttribuut(19910231),
                                                      new DatumAttribuut(19911231)));
        assertFalse(DatumAttribuut.isDatumsGeldigOpPeriode(new DatumAttribuut(19920101), new DatumAttribuut(200020101), new DatumAttribuut(19910231),
                                                      new DatumAttribuut(19970231)));
        assertFalse(DatumAttribuut.isDatumsGeldigOpPeriode(new DatumAttribuut(19920101), new DatumAttribuut(200020101), new DatumAttribuut(19910231),
                                                      new DatumAttribuut(20070231)));
        assertTrue(DatumAttribuut.isDatumsGeldigOpPeriode(new DatumAttribuut(19920101), new DatumAttribuut(200020101), new DatumAttribuut(19920101),
                                                     new DatumAttribuut(200020101)));
        assertFalse(DatumAttribuut.isDatumsGeldigOpPeriode(new DatumAttribuut(19920101), new DatumAttribuut(200020101), new DatumAttribuut(200020102),
                                                      new DatumAttribuut(200020103)));
        assertFalse(DatumAttribuut.isDatumsGeldigOpPeriode(new DatumAttribuut(19920101), new DatumAttribuut(200020101), new DatumAttribuut(19911230),
                                                      new DatumAttribuut(19911231)));
    }

    @Test
    public void zouEenMetVijfJarenVerhoogdeDatumMoetenTeruggeven() {
        assertEquals(20110101, DatumAttribuut.verhoogMetJaren(new DatumAttribuut(20010101), 10).getWaarde().intValue());
        assertEquals(20160229, DatumAttribuut.verhoogMetJaren(new DatumAttribuut(20120229), 4).getWaarde().intValue());
        assertEquals(20140228, DatumAttribuut.verhoogMetJaren(new DatumAttribuut(20120229), 2).getWaarde().intValue());
    }
}
