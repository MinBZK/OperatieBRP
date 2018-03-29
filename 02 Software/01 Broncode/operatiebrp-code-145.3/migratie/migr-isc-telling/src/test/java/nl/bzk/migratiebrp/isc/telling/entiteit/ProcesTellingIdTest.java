/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.telling.entiteit;

import java.sql.Timestamp;
import org.junit.Assert;
import org.junit.Test;

public class ProcesTellingIdTest {

    private static final String PROCES_NAAM = "uc202";
    private static final String KANAAL = "VOISC";
    private static final String BERICHT_TYPE = "Lg01";
    private static final Timestamp DATUM = new Timestamp(System.currentTimeMillis());

    @Test
    public void testGettersEnSetters() {
        final ProcesTellingId procesTellingId = new ProcesTellingId(DATUM, PROCES_NAAM, BERICHT_TYPE, KANAAL);
        Assert.assertEquals(PROCES_NAAM, procesTellingId.getProcesnaam());
        Assert.assertEquals(DATUM, procesTellingId.getDatum());
    }

    @Test
    public void testEqualsEnHashCode() {

        final ProcesTellingId procesTellingId = new ProcesTellingId(DATUM, PROCES_NAAM, BERICHT_TYPE, KANAAL);
        final ProcesTellingId procesTellingIdGelijk = new ProcesTellingId(DATUM, PROCES_NAAM, BERICHT_TYPE, KANAAL);
        final ProcesTellingId procesTellingIdOnGelijk =
                new ProcesTellingId(new Timestamp(System.currentTimeMillis() + 1000), PROCES_NAAM, BERICHT_TYPE, KANAAL);
        final BerichtTellingId berichtTellingId = new BerichtTellingId();
        Assert.assertTrue(procesTellingId.equals(procesTellingId));
        Assert.assertTrue(procesTellingId.equals(procesTellingIdGelijk));
        Assert.assertFalse(procesTellingId.equals(procesTellingIdOnGelijk));
        Assert.assertFalse(procesTellingId.equals(berichtTellingId));
        Assert.assertEquals(procesTellingId.hashCode(), procesTellingIdGelijk.hashCode());
        Assert.assertNotSame(procesTellingId.hashCode(), procesTellingIdOnGelijk.hashCode());
        Assert.assertNotSame(procesTellingId.hashCode(), berichtTellingId.hashCode());
    }
}
