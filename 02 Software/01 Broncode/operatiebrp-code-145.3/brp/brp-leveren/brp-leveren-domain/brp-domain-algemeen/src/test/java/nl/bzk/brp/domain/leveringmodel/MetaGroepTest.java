/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel;

import com.google.common.collect.Lists;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import org.junit.Assert;
import org.junit.Test;

/**
 */
public class MetaGroepTest {


    @Test
    public void testGetters() {
        //@formatter:off
        final MetaObject persoon = TestBuilders.maakLeegPersoon()
            .metGroep()
                .metGroepElement(Element.PERSOON_GEBOORTE.getId())
                .metRecord(MetaRecord.maakBuilder())
            .eindeGroep()
        .build();
        //@formatter:on

        final MetaGroep groep = persoon.getGroep(ElementHelper.getGroepElement(Element.PERSOON_GEBOORTE.getId()));
        Assert.assertEquals(persoon, groep.getParentObject());
        Assert.assertEquals("Persoon.Geboorte [3514]", groep.toString());
    }

    @Test
    public void testBuilderMetRecord() {
        //@formatter:off
        final MetaObject persoon = TestBuilders.maakLeegPersoon()
            .metGroep()
                .metGroepElement(Element.PERSOON_GEBOORTE.getId())
                .metRecord(MetaRecord.maakBuilder())
            .eindeGroep()
        .build();
        //@formatter:on
        Assert.assertEquals(1, persoon.getGroep(ElementHelper.getGroepElement(Element.PERSOON_GEBOORTE.getId())).getRecords().size());
    }

    @Test
    public void testBuilderMetRecords() {
        //@formatter:off
        final MetaObject persoon = TestBuilders.maakLeegPersoon()
            .metGroep()
                .metGroepElement(Element.PERSOON_GEBOORTE.getId())
                .metRecords(Lists.newArrayList(MetaRecord.maakBuilder()))
            .eindeGroep()
        .build();
        //@formatter:on
        Assert.assertEquals(1, persoon.getGroep(ElementHelper.getGroepElement(Element.PERSOON_GEBOORTE.getId())).getRecords().size());
    }

    /**
     * Een groep is uniek binnen een object, maar niet over objecten heen
     */
    @Test
    public void testGroepInHetzelfdeObject() {
        //@formatter:off
        final MetaObject metaObject = MetaObject.maakBuilder()
            .metObjectElement(Element.PERSOON)
                .metGroep().metGroepElement(Element.PERSOON_GEBOORTE.getId())
                    .metRecord().metId(1).eindeRecord()
                .eindeGroep()
                .metGroep().metGroepElement(Element.PERSOON_GEBOORTE.getId())
                    .metRecord().metId(2).eindeRecord()
                .eindeGroep()
            .build();
        //@formatter:on
        Assert.assertEquals(1, metaObject.getGroepen().size());
    }

    @Test
    public void testGroepInVerschillendeObjecten() {
        //@formatter:off
        final MetaObject metaObject = MetaObject.maakBuilder()
                .metObjectElement(Element.PERSOON.getId())
                .metId(1)
                .metObject()
                    .metId(6)
                    .metObjectElement(Element.PERSOON_AFNEMERINDICATIE.getId())
                        .metGroep()
                        .metGroepElement(Element.PERSOON_AFNEMERINDICATIE_STANDAARD.getId())
                        .eindeGroep()
                    .eindeObject()
                .metObject()
                    .metId(7)
                    .metObjectElement(Element.PERSOON_AFNEMERINDICATIE.getId())
                    .metGroep()
                        .metGroepElement(Element.PERSOON_AFNEMERINDICATIE_STANDAARD.getId())
                    .eindeGroep()
                .eindeObject()
                .build();
        //@formatter:on
        Assert.assertEquals(2, new ModelIndex(metaObject).geefGroepen().size());
    }


    /**
     * Een groep is uniek binnen een object, maar niet over objecten heen
     */
    @Test
    public void testMeerdereGroepInHetzelfdeObject() {
        //@formatter:off
        final MetaObject metaObject = MetaObject.maakBuilder()
            .metObjectElement(Element.PERSOON)
            .metId(1)
                .metGroep()
                    .metGroepElement(Element.PERSOON_IDENTIFICATIENUMMERS.getId())
                        .metRecord().metId(1).eindeRecord()
                .eindeGroep()
                .metGroep().metGroepElement(Element.PERSOON_PERSOONSKAART.getId()).
                    metRecord().metId(2).eindeRecord()
                .eindeGroep()
                .metGroep().metGroepElement(Element.PERSOON_GEBOORTE.getId()).
                    metRecord().metId(3).eindeRecord()
                .eindeGroep()
            .build();
        //@formatter:on
        Assert.assertEquals(3, metaObject.getGroepen().size());
    }
}
