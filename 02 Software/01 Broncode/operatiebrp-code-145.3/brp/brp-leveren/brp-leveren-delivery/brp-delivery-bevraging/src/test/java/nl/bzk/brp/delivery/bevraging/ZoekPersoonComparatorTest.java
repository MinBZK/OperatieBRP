/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging;

import java.time.LocalDate;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.berichtmodel.BerichtElement;
import nl.bzk.brp.domain.berichtmodel.BijgehoudenPersoon;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import org.junit.Assert;
import org.junit.Test;

/**
 */
public class ZoekPersoonComparatorTest {

    private final Actie actieInhoud = TestVerantwoording.maakActie(1, LocalDate.of(2010, 1, 1).atStartOfDay(DatumUtil.BRP_ZONE_ID));

    @Test
    public void testCompareZoekPersoon() {
        final BijgehoudenPersoon bijgehoudenPersoon1 = bouwPersoonMetBsn("987654321");
        final BijgehoudenPersoon bijgehoudenPersoon2 = bouwPersoonMetBsn("123456789");
        final int compare = ZoekPersoonComparator.INSTANCE.compare(bijgehoudenPersoon1, bijgehoudenPersoon2);
        Assert.assertTrue(compare > 0);
    }

    @Test
    public void testCompareZoekPersoonBsnOntbreekt() {
        final BijgehoudenPersoon bijgehoudenPersoon1 = bouwPersoonMetBsn("987654321");
        final BijgehoudenPersoon bijgehoudenPersoon2 = bouwPersoonMetBsn(null);

        final int compare = ZoekPersoonComparator.INSTANCE.compare(bijgehoudenPersoon1, bijgehoudenPersoon2);
        Assert.assertTrue(compare > 0);
    }

    @Test
    public void testCompareZoekPersoonOpAdresgegegevens() {
        final BijgehoudenPersoon bijgehoudenPersoon1 = bouwPersoonMetBsn("123456789");
        final BijgehoudenPersoon bijgehoudenPersoon2 = bouwPersoonMetBsn("987654321");
        final int compare = ZoekPersoonComparator.INSTANCE.compare(bijgehoudenPersoon1, bijgehoudenPersoon2);
        Assert.assertTrue(compare < 0);
    }

    private BijgehoudenPersoon bouwPersoonMetBsn(final String bsn) {
        //@formatter:off
        final MetaObject metaObject = TestBuilders.maakLeegPersoon()
            .metGroep()
                .metGroepElement(Element.PERSOON_IDENTIFICATIENUMMERS.getId())
                    .metRecord()
                        .metActieInhoud(actieInhoud)
                        .metAttribuut(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getId(), bsn)
                    .eindeRecord()
            .eindeGroep().build();
        //@formatter:on
        final Persoonslijst persoonslijst = new Persoonslijst(metaObject, 0L);

        return new BijgehoudenPersoon.Builder(persoonslijst, new BerichtElement("test")).build();
    }
}
