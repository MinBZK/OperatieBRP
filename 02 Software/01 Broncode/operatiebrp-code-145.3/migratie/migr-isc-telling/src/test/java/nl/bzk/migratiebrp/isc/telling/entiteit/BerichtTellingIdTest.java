/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.telling.entiteit;

import java.sql.Timestamp;
import org.junit.Assert;
import org.junit.Test;

public class BerichtTellingIdTest {

    private static final String BERICHT_TYPE = "Lg01";
    private static final String KANAAL = "VOISC";
    private static final Timestamp DATUM = new Timestamp(System.currentTimeMillis());

    @Test
    public void testGettersEnSetters() {
        final BerichtTellingId berichtTellingId = new BerichtTellingId(DATUM, BERICHT_TYPE, KANAAL);
        Assert.assertEquals(BERICHT_TYPE, berichtTellingId.getBerichtType());
        Assert.assertEquals(KANAAL, berichtTellingId.getKanaal());
        Assert.assertEquals(DATUM, berichtTellingId.getDatum());
    }

    @Test
    public void testEqualsEnHashCode() {
        final BerichtTellingId berichtTellingId = new BerichtTellingId(DATUM, BERICHT_TYPE, KANAAL);
        final BerichtTellingId berichtTellingIdGelijk = new BerichtTellingId(DATUM, BERICHT_TYPE, KANAAL);
        final BerichtTellingId berichtTellingIdOnGelijk =
                new BerichtTellingId(new Timestamp(System.currentTimeMillis() + 1000), BERICHT_TYPE, KANAAL);
        final ProcesTellingId procesTellingId = new ProcesTellingId();
        Assert.assertTrue(berichtTellingId.equals(berichtTellingId));
        Assert.assertTrue(berichtTellingId.equals(berichtTellingIdGelijk));
        Assert.assertFalse(berichtTellingId.equals(berichtTellingIdOnGelijk));
        Assert.assertFalse(berichtTellingId.equals(procesTellingId));
        Assert.assertEquals(berichtTellingId.hashCode(), berichtTellingIdGelijk.hashCode());
        Assert.assertNotSame(berichtTellingId.hashCode(), berichtTellingIdOnGelijk.hashCode());
        Assert.assertNotSame(berichtTellingId.hashCode(), procesTellingId.hashCode());
    }
}
