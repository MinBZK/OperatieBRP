/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import java.math.BigDecimal;
import java.util.Date;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandsePlaatsAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.HuisletterAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.HuisnummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LengteInCmAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Nee;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VersienummerAttribuut;
import nl.bzk.brp.model.basis.AbstractAttribuut;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test voor {@link nl.bzk.brp.levering.lo3.mapper.BrpMapperUtil}: simple type mapping.
 */
public class BrpMapperUtilAlgemeenTest {

    private static final String ABC = "ABC";

    @Test
    public void mapBrpBooleanNeeAttribuut() {
        Assert.assertEquals(Boolean.TRUE, BrpMapperUtil.mapBrpBoolean((NeeAttribuut) null, null).getWaarde());
        Assert.assertEquals(Boolean.TRUE, BrpMapperUtil.mapBrpBoolean(new NeeAttribuut(null), null).getWaarde());
        Assert.assertEquals(Boolean.FALSE, BrpMapperUtil.mapBrpBoolean(new NeeAttribuut(Nee.N), null).getWaarde());
    }

    @Test
    public void mapBrpBooleanJaAttribuut() {
        Assert.assertEquals(Boolean.FALSE, BrpMapperUtil.mapBrpBoolean((JaAttribuut) null, null).getWaarde());
        Assert.assertEquals(Boolean.FALSE, BrpMapperUtil.mapBrpBoolean(new JaAttribuut(null), null).getWaarde());
        Assert.assertEquals(Boolean.TRUE, BrpMapperUtil.mapBrpBoolean(new JaAttribuut(Ja.J), null).getWaarde());
    }

    @Test
    public void mapBrpBoolean() {
        Assert.assertNull(BrpMapperUtil.mapBrpBoolean((JaNeeAttribuut) null, null));
        Assert.assertNull(BrpMapperUtil.mapBrpBoolean(new JaNeeAttribuut(null), null));

        Assert.assertEquals(Boolean.TRUE, BrpMapperUtil.mapBrpBoolean(new JaNeeAttribuut(Boolean.TRUE), null).getWaarde());
    }

    @Test
    public void mapBrpCharacter() {
        Assert.assertNull(BrpMapperUtil.mapBrpCharacter(null, null));
        Assert.assertNull(BrpMapperUtil.mapBrpCharacter(new HuisletterAttribuut(null), null));
        Assert.assertNull(BrpMapperUtil.mapBrpCharacter(new HuisletterAttribuut(""), null));

        Assert.assertEquals(Character.valueOf('A'), BrpMapperUtil.mapBrpCharacter(new HuisletterAttribuut(ABC), null).getWaarde());
    }

    @Test
    public void mapBrpDatum() {
        Assert.assertNull(BrpMapperUtil.mapBrpDatum(null, null));
        // anjaw: Uitgecomment vanwege private constructor DatumAttribuut
        // Assert.assertNull(BrpMapperUtil.mapBrpDatum(new DatumAttribuut(null), null));

        Assert.assertEquals(Integer.valueOf(19030304), BrpMapperUtil.mapBrpDatum(new DatumAttribuut(19030304), null).getWaarde());
    }

    @Test
    public void mapBrpDatumTijd() {
        Assert.assertNull(BrpMapperUtil.mapBrpDatumTijd(null, null));
        Assert.assertNull(BrpMapperUtil.mapBrpDatumTijd(new DatumTijdAttribuut(null), null));

        final Date date = new Date();

        Assert.assertEquals(new BrpDatumTijd(date, null), BrpMapperUtil.mapBrpDatumTijd(new DatumTijdAttribuut(date), null));
    }

    @Test
    public void mapBrpInteger() {
        Assert.assertNull(BrpMapperUtil.mapBrpInteger(null, null));
        Assert.assertNull(BrpMapperUtil.mapBrpInteger(new HuisnummerAttribuut(null), null));

        Assert.assertEquals(Integer.valueOf(123), BrpMapperUtil.mapBrpInteger(new HuisnummerAttribuut(123), null).getWaarde());
    }

    @Test
    public void mapBrpIntegerFromBigDecimal() {
        Assert.assertNull(BrpMapperUtil.mapBrpIntegerFromBigDecimal(null, null));
        Assert.assertNull(BrpMapperUtil.mapBrpIntegerFromBigDecimal(new LengteInCmAttribuut(null), null));

        Assert.assertEquals(Integer.valueOf(123), BrpMapperUtil.mapBrpIntegerFromBigDecimal(new LengteInCmAttribuut(BigDecimal.valueOf(123L)), null)
                                                               .getWaarde());
    }

    @Test
    public void mapBrpLong() {
        Assert.assertNull(BrpMapperUtil.mapBrpLong(null, null));
        Assert.assertNull(BrpMapperUtil.mapBrpLong(new VersienummerAttribuut(null), null));

        Assert.assertEquals(Long.valueOf(123), BrpMapperUtil.mapBrpLong(new VersienummerAttribuut(123L), null).getWaarde());
    }

    @Test
    public void mapBrpString() {
        Assert.assertNull(BrpMapperUtil.mapBrpString((AbstractAttribuut<String>) null, null));
        Assert.assertNull(BrpMapperUtil.mapBrpString(new BuitenlandsePlaatsAttribuut(null), null));

        Assert.assertEquals(ABC, BrpMapperUtil.mapBrpString(new BuitenlandsePlaatsAttribuut(ABC), null).getWaarde());
    }

    @Test
    public void mapBrpStringNaamEnumeratiewaardeAttribuut() {
        Assert.assertNull(BrpMapperUtil.mapBrpString(null, null));
        Assert.assertNull(BrpMapperUtil.mapBrpString(new NaamEnumeratiewaardeAttribuut(null), null));

        Assert.assertEquals(ABC, BrpMapperUtil.mapBrpString(new NaamEnumeratiewaardeAttribuut(ABC), null).getWaarde());
    }
}
