/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.filterstappen;

import static nl.bzk.brp.domain.element.ElementHelper.getGroepElement;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import org.junit.Assert;
import org.junit.Test;

/**
 */
public class ConversieHistoriecorrectiePredicateTest {

    private ConversieHistoriecorrectiePredicate predikaat = new ConversieHistoriecorrectiePredicate();

    @Test
    public void metIndicatieTbvLeveringMutatiesTrue() {

        //@formatter:off
        final MetaObject object = MetaObject.maakBuilder()
            .metObjectElement(Element.PERSOON.getId())
            .metGroep()
                .metGroepElement(getGroepElement(Element.PERSOON_GEBOORTE))
                .metRecord()
                    .metId(1)
                    .metIndicatieTbvLeveringMutaties(true)
                .eindeRecord()
            .eindeGroep()
        .build();
        //@formatter:on

        //niet leveren
        Assert.assertFalse(predikaat.test(object.getGroepen().iterator().next().getRecords().iterator().next()));
    }

    @Test
    public void metIndicatieTbvLeveringMutatiesFalse() {

        //@formatter:off
        final MetaObject object = TestBuilders.maakLeegPersoon()
            .metGroep()
                .metGroepElement(getGroepElement(Element.PERSOON_GEBOORTE))
                .metRecord()
                    .metId(1)
                    .metIndicatieTbvLeveringMutaties(false)
                .eindeRecord()
            .eindeGroep()
        .build();
        //@formatter:on

        //wel leveren
        Assert.assertTrue(predikaat.test(object.getGroepen().iterator().next().getRecords().iterator().next()));
    }

    @Test
    public void metIndicatieTbvLeveringMutatiesNietGezet() {

        //@formatter:off
        final MetaObject object = TestBuilders.maakLeegPersoon()
            .metGroep()
                .metGroepElement(getGroepElement(Element.PERSOON_GEBOORTE))
                .metRecord()
                    .metId(1)
                .eindeRecord()
            .eindeGroep()
        .build();
        //@formatter:on

        //wel leveren
        Assert.assertTrue(predikaat.test(object.getGroepen().iterator().next().getRecords().iterator().next()));
    }
}
