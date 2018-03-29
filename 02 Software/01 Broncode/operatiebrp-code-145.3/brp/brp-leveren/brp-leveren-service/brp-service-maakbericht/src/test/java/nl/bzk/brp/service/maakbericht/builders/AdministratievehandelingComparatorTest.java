/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.builders;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import org.junit.Assert;
import org.junit.Test;

/**
 * AdministratievehandelingComparatorTest.
 */
public class AdministratievehandelingComparatorTest {

    @Test
    public void testCompareMetTsReg() {
        final ZonedDateTime datumTijdAttribuut1 = LocalDate.of(1900, 10, 10).atStartOfDay(DatumUtil.BRP_ZONE_ID);
        final ZonedDateTime datumTijdAttribuut2 = LocalDate.of(2000, 10, 10).atStartOfDay(DatumUtil.BRP_ZONE_ID);

        final AdministratieveHandeling handeling1 = maakHandeling(datumTijdAttribuut1);
        final AdministratieveHandeling handeling2 = maakHandeling(datumTijdAttribuut2);

        final int compare = AdministratievehandelingComparator.INSTANCE.compare(handeling1, handeling2);
        Assert.assertEquals(1, compare);
    }

    @Test
    public void testCompareActieMetEnZonderTsReg() {
        final ZonedDateTime datumTijdAttribuut2 = LocalDate.of(1900, 10, 10).atStartOfDay(DatumUtil.BRP_ZONE_ID);
        final AdministratieveHandeling handeling1 = maakHandeling(null);
        final AdministratieveHandeling handeling2 = maakHandeling(datumTijdAttribuut2);

        final int compare = AdministratievehandelingComparator.INSTANCE.compare(handeling1, handeling2);
        Assert.assertEquals(1, compare);
        final int compare2 = AdministratievehandelingComparator.INSTANCE.compare(handeling2, handeling1);
        Assert.assertEquals(-1, compare2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCompareZonderActie() {
        final ZonedDateTime datumTijdAttribuut2 = LocalDate.of(2000, 10, 10).atStartOfDay(DatumUtil.BRP_ZONE_ID);

        final AdministratieveHandeling handeling2 = maakHandeling(datumTijdAttribuut2);

        final int compare = AdministratievehandelingComparator.INSTANCE.compare(null, handeling2);
        Assert.assertEquals(1, compare);
    }

    @Test
    public void testCompareActieTsRegGelijk() {
        final ZonedDateTime datumTijdAttribuut1 = LocalDate.of(2000, 10, 10).atStartOfDay(DatumUtil.BRP_ZONE_ID);
        final ZonedDateTime datumTijdAttribuut2 = LocalDate.of(2000, 10, 10).atStartOfDay(DatumUtil.BRP_ZONE_ID);

        final AdministratieveHandeling handeling1 = maakHandeling(datumTijdAttribuut1);
        final AdministratieveHandeling handeling2 = maakHandeling(datumTijdAttribuut2);

        final int compare = AdministratievehandelingComparator.INSTANCE.compare(handeling1, handeling2);
        Assert.assertEquals(0, compare);
    }


    private AdministratieveHandeling maakHandeling(final ZonedDateTime datumTijdAttribuut) {

        final MetaObject ah = TestVerantwoording
                .maakAdministratieveHandeling(1, "000034", datumTijdAttribuut, SoortAdministratieveHandeling.CORRECTIE_ADRES)
                .metObject(TestVerantwoording.maakActieBuilder(1, SoortActie.BEEINDIGING_STAATLOOS, DatumUtil.nuAlsZonedDateTime(), "000001", 0)
                ).build();
        return AdministratieveHandeling.converter().converteer(ah);
    }
}
