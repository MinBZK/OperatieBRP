/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.builders;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import org.junit.Assert;
import org.junit.Test;

/**
 * ActieComparatorTest.
 */
public class ActieComparatorTest {

    @Test
    public void testCompareActieTsReg() {
        final ZonedDateTime datumTijdAttribuut1 = LocalDate.of(1900, 10, 10).atStartOfDay(DatumUtil.BRP_ZONE_ID);
        final ZonedDateTime datumTijdAttribuut2 = LocalDate.of(2000, 10, 10).atStartOfDay(DatumUtil.BRP_ZONE_ID);
        final Actie actie1 = maakActie(datumTijdAttribuut1);
        final Actie actie2 = maakActie(datumTijdAttribuut2);

        final int compare = ActieComparator.INSTANCE.compare(actie1, actie2);
        Assert.assertEquals(1, compare);
    }

    @Test
    public void testCompareActieMetEnZonderTsReg() {
        final ZonedDateTime datumTijdAttribuut2 = LocalDate.of(1900, 10, 10).atStartOfDay(DatumUtil.BRP_ZONE_ID);
        final Actie actie1 = maakActie(null);
        final Actie actie2 = maakActie(datumTijdAttribuut2);

        final int compare = ActieComparator.INSTANCE.compare(actie1, actie2);
        Assert.assertEquals(1, compare);
        final int compare2 = ActieComparator.INSTANCE.compare(actie2, actie1);
        Assert.assertEquals(-1, compare2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCompareZonderActie() {
        final ZonedDateTime datumTijdAttribuut1 = LocalDate.of(1900, 10, 10).atStartOfDay(DatumUtil.BRP_ZONE_ID);
        final ZonedDateTime datumTijdAttribuut2 = LocalDate.of(2000, 10, 10).atStartOfDay(DatumUtil.BRP_ZONE_ID);
        final Actie actie1 = maakActie(datumTijdAttribuut1);
        final Actie actie2 = maakActie(datumTijdAttribuut2);

        final int compare = ActieComparator.INSTANCE.compare(null, actie2);
        Assert.assertEquals(1, compare);
    }

    @Test
    public void testCompareActieTsRegGelijk() {
        final ZonedDateTime datumTijdAttribuut1 = LocalDate.of(2000, 10, 10).atStartOfDay(DatumUtil.BRP_ZONE_ID);
        final ZonedDateTime datumTijdAttribuut2 = LocalDate.of(2000, 10, 10).atStartOfDay(DatumUtil.BRP_ZONE_ID);
        final Actie actie1 = maakActie(datumTijdAttribuut1);
        final Actie actie2 = maakActie(datumTijdAttribuut2);

        final int compare = ActieComparator.INSTANCE.compare(actie1, actie2);
        Assert.assertEquals(0, compare);
    }


    private Actie maakActie(final ZonedDateTime datumTijdAttribuut) {
        return TestVerantwoording.maakActie(1, datumTijdAttribuut);
    }
}
