/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.berichtmodel;

import java.time.LocalDate;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import org.junit.Assert;
import org.junit.Test;

/**
 * PersoonComparatorFactoryTest.
 */
public class DefaultPersoonComparatorTest {
    private final Actie actieInhoud = TestVerantwoording.maakActie(1, LocalDate.of(2010, 1, 1).atStartOfDay(DatumUtil.BRP_ZONE_ID));

    @Test
    public void testCompareSorteerVolgorde() {
        final BijgehoudenPersoon bijgehoudenPersoon1 = bouwPersoon(1, 2);
        final BijgehoudenPersoon bijgehoudenPersoon2 = bouwPersoon(2, 1);
        final int compare = DefaultPersoonComparator.INSTANCE.compare(bijgehoudenPersoon1, bijgehoudenPersoon2);
        Assert.assertEquals(-1, compare);
    }

    @Test
    public void testCompareSorteerObjectSleutel() {
        final BijgehoudenPersoon bijgehoudenPersoon1 = bouwPersoon(1, 2);
        final BijgehoudenPersoon bijgehoudenPersoon2 = bouwPersoon(1, 1);
        final int compare = DefaultPersoonComparator.INSTANCE.compare(bijgehoudenPersoon1, bijgehoudenPersoon2);
        Assert.assertEquals(1, compare);
    }

    private BijgehoudenPersoon bouwPersoon(int sorteerVolgorde, long objectSleutel) {
        final MetaObject metaObject = TestBuilders.maakIngeschrevenPersoon().metId(objectSleutel)
                .metGroep().metGroepElement(Element.PERSOON_AFGELEIDADMINISTRATIEF).metRecord().metActieInhoud(actieInhoud)
                .metAttribuut().metType(Element.PERSOON_AFGELEIDADMINISTRATIEF_SORTEERVOLGORDE.getId()).metWaarde(sorteerVolgorde).eindeAttribuut()
                .eindeRecord()
                .eindeGroep().eindeObject().build();
        final Persoonslijst persoonslijst = new Persoonslijst(metaObject, 0L);

        return new BijgehoudenPersoon.Builder(persoonslijst, new BerichtElement("test")).build();
    }

    private BijgehoudenPersoon bouwPersoonMetBsn(final int bsn) {
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
