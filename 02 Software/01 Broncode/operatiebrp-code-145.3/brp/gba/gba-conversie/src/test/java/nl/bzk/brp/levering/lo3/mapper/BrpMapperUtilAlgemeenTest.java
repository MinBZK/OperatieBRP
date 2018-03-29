/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test voor {@link nl.bzk.brp.levering.lo3.mapper.BrpMapperUtil}: simple type mapping.
 */
public class BrpMapperUtilAlgemeenTest {

    private static final String ABC = "ABC";

    @Test
    public void mapBrpBooleanJaAttribuut() {
        Assert.assertEquals(null, BrpMapperUtil.mapBrpBoolean(null, null));
        Assert.assertEquals(Boolean.TRUE, BrpMapperUtil.mapBrpBoolean(Boolean.TRUE, null).getWaarde());
    }

    @Test
    public void mapBrpCharacter() {
        Assert.assertNull(BrpMapperUtil.mapBrpCharacter((Character) null, null));

        Assert.assertEquals(Character.valueOf('A'), BrpMapperUtil.mapBrpCharacter('A', null).getWaarde());
    }

    @Test
    public void mapBrpDatum() {
        Assert.assertNull(BrpMapperUtil.mapBrpDatum(null, null));
        Assert.assertEquals(Integer.valueOf(19030304), BrpMapperUtil.mapBrpDatum(19030304, null).getWaarde());
    }

    @Test
    public void mapBrpDatumTijd() {
        Assert.assertNull(BrpMapperUtil.mapBrpDatumTijd(null, null));

        final Date date = new Date();
        Instant instant = Instant.ofEpochMilli(date.getTime());
        ZonedDateTime ldt = ZonedDateTime.ofInstant(instant, ZoneOffset.UTC);
        Assert.assertEquals(new BrpDatumTijd(date, null), BrpMapperUtil.mapBrpDatumTijd(ldt, null));
    }

    @Test
    public void mapBrpInteger() {
        Assert.assertNull(BrpMapperUtil.mapBrpInteger((Integer) null, null));

        Assert.assertEquals(Integer.valueOf(123), BrpMapperUtil.mapBrpInteger(123, null).getWaarde());
    }

    @Test
    public void mapBrpLong() {
        Assert.assertNull(BrpMapperUtil.mapBrpLong((Long) null, null));

        Assert.assertEquals(Long.valueOf(123), BrpMapperUtil.mapBrpLong(123L, null).getWaarde());
    }

    // @Test
    // public void mapBrpString() {
    // Assert.assertNull(BrpMapperUtil.mapBrpString(null, null));
    // Assert.assertNull(BrpMapperUtil.mapBrpString(new MetaAttribuut(), null));
    //
    // Assert.assertEquals(ABC, BrpMapperUtil.mapBrpString(new BuitenlandsePlaatsAttribuut(ABC), null).getWaarde());
    // }
    //
    // @Test
    // public void mapBrpStringNaamEnumeratiewaardeAttribuut() {
    // Assert.assertNull(BrpMapperUtil.mapBrpString((NaamEnumeratiewaardeAttribuut) null, null));
    // Assert.assertNull(BrpMapperUtil.mapBrpString(new NaamEnumeratiewaardeAttribuut(null), null));
    //
    // Assert.assertEquals(ABC, BrpMapperUtil.mapBrpString(new NaamEnumeratiewaardeAttribuut(ABC), null).getWaarde());
    // }
}
