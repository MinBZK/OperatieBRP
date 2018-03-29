/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.builders;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.leveringmodel.Actiebron;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import org.junit.Assert;
import org.junit.Test;

/**
 * ActiebronComparatorTest.
 */
public class ActiebronComparatorTest {

    @Test
    public void testCompareMet2Documenten() {
        final Actiebron actieBron1 = maakBron(TestVerantwoording.maakDocumentBuilder(2), null, null);
        final Actiebron actieBron2 = maakBron(TestVerantwoording.maakDocumentBuilder(1), null, null);
        final int compare = ActiebronComparator.INSTANCE.compare(actieBron1, actieBron2);
        Assert.assertEquals(1, compare);
    }

    @Test
    public void testCompareMetEnZonderDocument() {
        final Actiebron actieBron1 = maakBron(null, null);
        final MetaObject.Builder document2 = TestVerantwoording.maakDocumentBuilder(1);
        final Actiebron actieBron2 = maakBron(document2, null, null);

        int compare = ActiebronComparator.INSTANCE.compare(actieBron1, actieBron2);
        Assert.assertEquals(-1, compare);
        compare = ActiebronComparator.INSTANCE.compare(actieBron2, actieBron1);
        Assert.assertEquals(1, compare);
    }

    @Test
    public void testCompareMetRechtsgrond() {
        final Actiebron actieBron1 = maakBron("02", null);
        final Actiebron actieBron2 = maakBron("01", null);

        final int compare = ActiebronComparator.INSTANCE.compare(actieBron1, actieBron2);
        Assert.assertEquals(1, compare);
    }

    @Test
    public void testCompareMetEnZonderRechtsgrond() {
        final Actiebron actieBron1 = maakBron(null, null);
        final Actiebron actieBron2 = maakBron("01", null);

        int compare = ActiebronComparator.INSTANCE.compare(actieBron1, actieBron2);
        Assert.assertEquals(-1, compare);
        compare = ActiebronComparator.INSTANCE.compare(actieBron2, actieBron1);
        Assert.assertEquals(1, compare);
    }

    @Test
    public void testCompareMetRechtsgrondOmschrijving() {
        final Actiebron actieBron1 = maakBron(null, "C");
        final Actiebron actieBron2 = maakBron(null, "B");

        final int compare = ActiebronComparator.INSTANCE.compare(actieBron1, actieBron2);
        Assert.assertEquals(1, compare);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCompareFout() {
        ActiebronComparator.INSTANCE.compare(null, null);
    }

    @Test
    public void testCompareAlleArgsNull() {
        final Actiebron actieBron1 = maakBron(null, null);
        final Actiebron actieBron2 = maakBron(null, null);

        final int compare = ActiebronComparator.INSTANCE.compare(actieBron1, actieBron2);
        Assert.assertEquals(0, compare);
    }

    private Actiebron maakBron(final MetaObject.Builder document, final String rechtsgrondCode, final String rechtsgrondOmschrijving) {

        final MetaObject ah = TestVerantwoording
                .maakAdministratieveHandeling(1, "000123", null, SoortAdministratieveHandeling.CORRECTIE_ADRES)
                .metObject(TestVerantwoording.maakActieBuilder(1, SoortActie.BEEINDIGING_VOORNAAM, DatumUtil.nuAlsZonedDateTime(), "000001", 0)
                        .metObject(TestVerantwoording.maakActiebronBuilder(1, rechtsgrondCode, rechtsgrondOmschrijving)
                                .metObject(document))
                ).build();
        final AdministratieveHandeling administratieveHandeling = AdministratieveHandeling.converter().converteer(ah);
        return administratieveHandeling.getActies().iterator().next().getBronnen().iterator().next();
    }

    private Actiebron maakBron(final String rechtsgrondCode, final String rechtsgrondOmschrijving) {

        final MetaObject ah = TestVerantwoording
                .maakAdministratieveHandeling(1, "000123", null, SoortAdministratieveHandeling.CORRECTIE_ADRES)
                .metObject(TestVerantwoording.maakActieBuilder(1, SoortActie.BEEINDIGING_VOORNAAM, DatumUtil.nuAlsZonedDateTime(), "000001", 0)
                        .metObject(TestVerantwoording.maakActiebronBuilder(1, rechtsgrondCode, rechtsgrondOmschrijving)))
                .build();
        final AdministratieveHandeling administratieveHandeling = AdministratieveHandeling.converter().converteer(ah);
        return administratieveHandeling.getActies().iterator().next().getBronnen().iterator().next();
    }
}
