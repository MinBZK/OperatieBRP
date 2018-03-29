/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel;

import static nl.bzk.brp.domain.element.ElementConstants.PERSOON;
import static nl.bzk.brp.domain.element.ElementConstants.PERSOON_GEBOORTE;
import static nl.bzk.brp.domain.element.ElementHelper.getGroepElement;
import static nl.bzk.brp.domain.element.ElementHelper.getObjectElement;

import com.google.common.collect.Lists;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import org.junit.Assert;
import org.junit.Test;

/**
 */
public class MetaObjectTest {


    @Test
    public void testGetters() {
        //@formatter:off
        final MetaObject persoon = TestBuilders.maakIngeschrevenPersoon()
            .metId(10)
            .metObjectElement(Element.PERSOON)
            .metObject()
                .metId(5)
                .metObjectElement(getObjectElement(Element.PERSOON_PARTNER.getId()))
                .metObject()
                    .metObjectElement(getObjectElement(Element.HUWELIJK.getId()))
                    .metObject()
                        .metObjectElement(getObjectElement(Element.GERELATEERDEHUWELIJKSPARTNER.getId()))
                        .metObject()
                            .metObjectElement(getObjectElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON.getId()))
                        .eindeObject()
                    .eindeObject()
                .eindeObject()
            .eindeObject()
        .build();
        //@formatter:on

        Assert.assertEquals(10L, persoon.getObjectsleutel());
        Assert.assertEquals(3, persoon.getGroepen().size());

        Assert.assertEquals(getGroepElement(Element.PERSOON_IDENTITEIT.getId()),
                persoon.getGroep(getGroepElement(Element.PERSOON_IDENTITEIT.getId())).getGroepElement());

        Assert.assertEquals(getGroepElement(Element.PERSOON_VERSIE.getId()),
                persoon.getGroep(getGroepElement(Element.PERSOON_VERSIE.getId())).getGroepElement());

        Assert.assertEquals(1, persoon.getObjecten().size());
        Assert.assertEquals(getObjectElement(Element.PERSOON_PARTNER.getId()),
                persoon.getObjecten(getObjectElement(Element.PERSOON_PARTNER.getId())).iterator().next().getObjectElement
                        ());

        Assert.assertEquals("Persoon [3010]", persoon.toString());

        Assert.assertTrue(persoon.equals(persoon));
        Assert.assertTrue(persoon.deepEquals(persoon));
    }

    @Test
    public void testBuilderMetObject() {
        //@formatter:off
        final MetaObject persoon = TestBuilders.maakLeegPersoon()
            .metObject()
                .metObjectElement(Element.PERSOON_PARTNER.getId())
            .eindeObject()
        .build();
        //@formatter:on
        Assert.assertEquals(1, persoon.getObjecten(getObjectElement(Element.PERSOON_PARTNER.getId())).size());
    }

    @Test
    public void testBuilderMetObjectBuilder() {
        //@formatter:off
        final MetaObject persoon = TestBuilders.maakLeegPersoon()
            .metObject(MetaObject.maakBuilder().metObjectElement(getObjectElement(Element.PERSOON_PARTNER.getId())))
        .build();
        //@formatter:on
        Assert.assertEquals(1, persoon.getObjecten(getObjectElement(Element.PERSOON_PARTNER.getId())).size());
    }

    @Test
    public void testBuilderMetObjectBuilders() {
        //@formatter:off
        final MetaObject persoon = TestBuilders.maakLeegPersoon()
            .metObjecten(Lists.newArrayList(
                MetaObject.maakBuilder().metObjectElement(getObjectElement(Element.PERSOON_PARTNER.getId())),
                MetaObject.maakBuilder().metObjectElement(getObjectElement(Element.PERSOON_PARTNER.getId()))
            ))
        .build();
        //@formatter:on
        Assert.assertEquals(2, persoon.getObjecten(getObjectElement(Element.PERSOON_PARTNER.getId())).size());
    }

    @Test
    public void testBuilderMetGroep() {
        //@formatter:off
        final MetaObject persoon = TestBuilders.maakLeegPersoon()
            .metGroep()
                .metGroepElement(PERSOON_GEBOORTE)
            .eindeGroep()
        .build();
        //@formatter:on
        Assert.assertNotNull(persoon.getGroep(PERSOON_GEBOORTE));
    }

    @Test
    public void testBuilderMetGroepBuilder() {
        //@formatter:off
        final MetaObject persoon = TestBuilders.maakLeegPersoon()
            .metGroep(new MetaGroep.Builder().metGroepElement(PERSOON_GEBOORTE))
        .build();
        //@formatter:on
        Assert.assertNotNull(persoon.getGroep(PERSOON_GEBOORTE));
    }

    @Test
    public void testBuilderMetGroepen() {
        //@formatter:off
        final MetaObject persoon = TestBuilders.maakLeegPersoon()
            .metGroepen(Lists.newArrayList(new MetaGroep.Builder().metGroepElement(PERSOON_GEBOORTE)))
        .build();
        //@formatter:on
        Assert.assertNotNull(persoon.getGroep(PERSOON_GEBOORTE));
    }

    /**
     * Meta object structuren zijn nooit gelijk, ook al zijn ze inhoudelijk wel gelijk.
     */
    @Test
    public void testNietGelijk() {
        //@formatter:off
        final MetaObject metaObject1 = MetaObject.maakBuilder()
            .metId(1)
            .metObjectElement(PERSOON)
        .build();
        final MetaObject metaObject2 = MetaObject.maakBuilder()
            .metId(1)
            .metObjectElement(PERSOON)
        .build();
        //@formatter:on

        Assert.assertFalse(metaObject1.equals(metaObject2));
    }

    @Test
    public void testGetParentObject() {
        //@formatter:off
        final MetaObject persoon = MetaObject.maakBuilder()
            .metObjectElement(Element.PERSOON)
            .metObject()
                .metId(5)
                .metObjectElement(getObjectElement(Element.PERSOON_PARTNER.getId()))
                .metObject()
                    .metObjectElement(getObjectElement(Element.HUWELIJK.getId()))
                    .metObject()
                        .metObjectElement(getObjectElement(Element.GERELATEERDEHUWELIJKSPARTNER.getId()))
                        .metObject()
                            .metObjectElement(getObjectElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON.getId()))
                        .eindeObject()
                    .eindeObject()
                .eindeObject()
            .eindeObject()
        .build();
        //@formatter:on

        ModelIndex modelIndex = new ModelIndex(persoon);
        final MetaObject gerelateerdPersoon = modelIndex.geefObjecten(getObjectElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON.getId())).iterator()
                .next();

        MetaObject parent = gerelateerdPersoon.getParentObject();
        Assert.assertEquals(getObjectElement(Element.GERELATEERDEHUWELIJKSPARTNER.getId()), parent.getObjectElement());

        parent = parent.getParentObject();
        Assert.assertEquals(getObjectElement(Element.HUWELIJK.getId()), parent.getObjectElement());

        parent = parent.getParentObject();
        Assert.assertEquals(getObjectElement(Element.PERSOON_PARTNER.getId()), parent.getObjectElement());

        parent = parent.getParentObject();
        Assert.assertEquals(getObjectElement(Element.PERSOON.getId()), parent.getObjectElement());
    }


    @Test
    public void testGetParentObject2() {
        //@formatter:off
        final MetaObject persoon = MetaObject.maakBuilder()
            .metObjectElement(Element.PERSOON)
            .metObjecten(
                Lists.newArrayList(
                    MetaObject.maakBuilder()
                    .metId(5)
                    .metObjectElement(getObjectElement(Element.PERSOON_PARTNER.getId()))
                    .metObject()
                        .metObjectElement(getObjectElement(Element.HUWELIJK.getId()))
                            .metObject()
                                .metObjectElement(getObjectElement(Element.GERELATEERDEHUWELIJKSPARTNER.getId()))
                                .metObject()
                                    .metObjectElement(getObjectElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON.getId()))
                                    .eindeObject()
                                .eindeObject()
                            .eindeObject()
                        .eindeObject()
                )
            )
        .build();
        //@formatter:on

        ModelIndex modelIndex = new ModelIndex(persoon);
        final MetaObject gerelateerdPersoon = modelIndex.geefObjecten(getObjectElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON.getId())).iterator()
                .next();

        MetaObject parent = gerelateerdPersoon.getParentObject();
        Assert.assertEquals(getObjectElement(Element.GERELATEERDEHUWELIJKSPARTNER.getId()), parent.getObjectElement());

        parent = parent.getParentObject();
        Assert.assertEquals(getObjectElement(Element.HUWELIJK.getId()), parent.getObjectElement());

        parent = parent.getParentObject();
        Assert.assertEquals(getObjectElement(Element.PERSOON_PARTNER.getId()), parent.getObjectElement());

        parent = parent.getParentObject();
        Assert.assertEquals(getObjectElement(Element.PERSOON.getId()), parent.getObjectElement());
    }
}
