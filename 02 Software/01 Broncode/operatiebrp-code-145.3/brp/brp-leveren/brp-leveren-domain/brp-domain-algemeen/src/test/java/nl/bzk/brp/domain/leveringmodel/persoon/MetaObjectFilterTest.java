/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel.persoon;

import static nl.bzk.brp.domain.element.ElementHelper.getAttribuutElement;
import static nl.bzk.brp.domain.element.ElementHelper.getGroepElement;
import static nl.bzk.brp.domain.element.ElementHelper.getObjectElement;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.leveringmodel.ModelIndex;
import nl.bzk.brp.domain.leveringmodel.ParentFirstModelVisitor;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import org.junit.Assert;
import org.junit.Test;

/**
 */
public class MetaObjectFilterTest {

    @Test
    public void testFilterLegeGroepenEnObjecten() {
        final MetaObject metaObject = maakDummyPersoonObject();
        final MetaObjectFilter filter = new MetaObjectFilter(metaObject);
        final MetaObject filtered = filter.filter();
        final FilterVisitor visitor = new FilterVisitor();
        visitor.visit(filtered);
        //assert
        final Set<Long> valideObjecten = new HashSet<>();
        valideObjecten.add(1L);
        valideObjecten.add(2L);
        valideObjecten.add(7L);
        final Set<Long> valideGroepen = new HashSet<>();
        valideGroepen.add(5L);
        valideGroepen.add(8L);
        visitor.objecten.removeAll(valideObjecten);
        visitor.groepen.removeAll(valideGroepen);
        Assert.assertEquals(1, visitor.objecten.size());
        Assert.assertEquals(0, visitor.groepen.size());
    }

    @Test
    public void testFilterObjecten() {

        //@formatter:off
        final MetaObject expected = TestBuilders.maakLeegPersoon(1)
            .metGroep()
                .metGroepElement(getGroepElement(Element.PERSOON_IDENTITEIT.getId()))
                .metRecord()
                    .metId(5)
                    .metAttribuut(getAttribuutElement(Element.PERSOON_SOORTCODE.getId()), SoortPersoon.INGESCHREVENE.getCode())
                .eindeRecord()
            .eindeGroep()
            .metObject()
                .metId(7)
                .metObjectElement(getObjectElement(Element.PERSOON_ADRES.getId()))
                .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_ADRES_STANDAARD.getId()))
                        .metRecord()
                            .metId(8)
                            .metAttribuut(getAttribuutElement(Element.PERSOON_ADRES_GEMEENTECODE.getId()), 123)
                     .eindeRecord()
                .eindeGroep()
            .eindeObject()
        .build();
        //@formatter:on

        final MetaObjectFilter filter = new MetaObjectFilter(expected);
        filter.addObjectFilter(metaObject1 -> !(metaObject1.getObjectElement()
                .equals(getObjectElement(Element.PERSOON_ADRES.getId()))));
        final MetaObject filtered = filter.filter();
        ModelIndex modelIndex = new ModelIndex(filtered);
        Assert.assertTrue(modelIndex.geefObjecten(ElementHelper.getObjectElement(Element.PERSOON_ADRES.getId())).isEmpty());
    }


    @Test
    public void testFilterGroepen() {

        //@formatter:off
        final MetaObject expected = TestBuilders.maakLeegPersoon(1)
            .metGroep()
                .metGroepElement(getGroepElement(Element.PERSOON_IDENTITEIT.getId()))
                .metRecord()
                    .metId(5)
                    .metAttribuut(getAttribuutElement(Element.PERSOON_SOORTCODE.getId()), SoortPersoon.INGESCHREVENE.getCode())
                .eindeRecord()
            .eindeGroep()
            .metObject()
                .metId(7)
                .metObjectElement(getObjectElement(Element.PERSOON_ADRES.getId()))
                .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_ADRES_STANDAARD.getId()))
                        .metRecord()
                            .metId(8)
                            .metAttribuut(getAttribuutElement(Element.PERSOON_ADRES_GEMEENTECODE.getId()), 123)
                     .eindeRecord()
                .eindeGroep()
            .eindeObject()
        .build();
        //@formatter:on

        final MetaObjectFilter filter = new MetaObjectFilter(expected);
        filter.addGroepFilter(metaObject1 -> !(metaObject1.getGroepElement()
                .equals(getGroepElement(Element.PERSOON_ADRES_STANDAARD.getId()))));
        final MetaObject filtered = filter.filter();
        ModelIndex modelIndex = new ModelIndex(filtered);
        Assert.assertTrue(modelIndex.geefGroepenVanElement(ElementHelper.getGroepElement(Element.PERSOON_ADRES_STANDAARD.getId())).isEmpty());
    }


    @Test
    public void testFilterRecords() {

        //@formatter:off
        final MetaObject expected = TestBuilders.maakLeegPersoon(1)
            .metGroep()
                .metGroepElement(getGroepElement(Element.PERSOON_IDENTITEIT.getId()))
                .metRecord()
                    .metId(5)
                    .metAttribuut(getAttribuutElement(Element.PERSOON_SOORTCODE.getId()), SoortPersoon.INGESCHREVENE.getCode())
                .eindeRecord()
            .eindeGroep()
            .metObject()
                .metId(7)
                .metObjectElement(getObjectElement(Element.PERSOON_ADRES.getId()))
                .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_ADRES_STANDAARD.getId()))
                        .metRecord()
                            .metId(8)
                            .metAttribuut(getAttribuutElement(Element.PERSOON_ADRES_GEMEENTECODE.getId()), 123)
                     .eindeRecord()
                .eindeGroep()
            .eindeObject()
        .build();
        //@formatter:on

        final MetaObjectFilter filter = new MetaObjectFilter(expected);
        filter.addRecordFilter(metaObject1 -> !(metaObject1.getParentGroep().
                getGroepElement().getId() == Element.PERSOON_ADRES_STANDAARD.getId()));
        final MetaObject filtered = filter.filter();
        ModelIndex modelIndex = new ModelIndex(filtered);
        Assert.assertTrue(modelIndex.geefGroepenVanElement(ElementHelper.getGroepElement(Element.PERSOON_ADRES_STANDAARD.getId())).isEmpty());
    }

    @Test
    public void testFilterAttributen() {
        //@formatter:off
        final MetaObject expected = TestBuilders.maakLeegPersoon(1)
            .metGroep()
                .metGroepElement(getGroepElement(Element.PERSOON_IDENTITEIT.getId()))
                .metRecord()
                    .metId(5)
                    .metAttribuut(getAttribuutElement(Element.PERSOON_SOORTCODE.getId()), SoortPersoon.INGESCHREVENE.getCode())
                .eindeRecord()
            .eindeGroep()
            .metObject()
                .metId(7)
                .metObjectElement(getObjectElement(Element.PERSOON_ADRES.getId()))
                .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_ADRES_STANDAARD.getId()))
                        .metRecord()
                            .metId(8)
                            .metAttribuut(getAttribuutElement(Element.PERSOON_ADRES_GEMEENTECODE.getId()), 123)
                            .metAttribuut(getAttribuutElement(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL1.getId()), 123)
                     .eindeRecord()
                .eindeGroep()
            .eindeObject()
        .build();
        //@formatter:on

        final MetaObjectFilter filter = new MetaObjectFilter(expected);
        filter.addAttribuutFilter(metaObject1 -> !(metaObject1.getAttribuutElement().getId() == Element.PERSOON_ADRES_GEMEENTECODE.getId()));
        final MetaObject filtered = filter.filter();
        ModelIndex modelIndex = new ModelIndex(filtered);
        Assert.assertTrue(modelIndex.geefAttributen(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_GEMEENTECODE.getId())).isEmpty());
    }


    private static class FilterVisitor extends ParentFirstModelVisitor {

        private final Set<Long> objecten = new HashSet<>();
        private final Set<Long> groepen = new HashSet<>();

        @Override
        public void doVisit(final MetaObject o) {
            objecten.add(o.getObjectsleutel());
        }

        @Override
        public void doVisit(final MetaRecord r) {
            groepen.add(r.getVoorkomensleutel());
        }

        @Override
        public void doVisit(final MetaGroep g) {
            Assert.assertTrue(!g.getRecords().isEmpty());
        }

    }

    private MetaObject maakDummyPersoonObject() {
        final Actie actie = TestVerantwoording.maakActie(1, ZonedDateTime.now());
        //@formatter:off
        return MetaObject.maakBuilder()
            .metObjectElement(Element.PERSOON.getId())
            .metGroep()
                .metGroepElement(getGroepElement(Element.PERSOON_IDENTITEIT.getId()))
                .metRecord()
                    .metId(5)
                    .metAttribuut(getAttribuutElement(Element.PERSOON_SOORTCODE.getId()), SoortPersoon.INGESCHREVENE.getCode())
                .eindeRecord()
            .eindeGroep()
            .metObject()
                .metObjectElement(getObjectElement(Element.PERSOON_ADRES.getId()))
                .metId(2)
                .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_ADRES_STANDAARD.getId()))
                    .metRecord()
                        .metId(5)
                        .metActieInhoud(actie)
                        .metActieVerval(actie)
                        .metDatumAanvangGeldigheid(20140101)
                        .metDatumEindeGeldigheid(20140101)
                        .metNadereAanduidingVerval("S")
                        .metActieAanpassingGeldigheid(actie)
                        .metActieVervalTbvLeveringMutaties(actie)
                        .metAttribuut(getAttribuutElement(Element.PERSOON_ADRES_GEMEENTECODE.getId()), 123)
                        .metAttribuut(getAttribuutElement(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL1.getId()), "abc")
                        .metDatumAanvangGeldigheid(20100203)
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
            .metObject()
                .metId(7)
                .metObjectElement(getObjectElement(Element.PERSOON_AFNEMERINDICATIE.getId()))
                .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_AFNEMERINDICATIE_STANDAARD.getId()))
                        .metRecord()
                            .metId(8)
                            .metAttribuut(getAttribuutElement(Element.PERSOON_ADRES_GEMEENTECODE.getId()), 123)
                     .eindeRecord()
                .eindeGroep()
            .eindeObject()
        .build();
        //@formatter:on
    }

    private MetaObject maakPersoonObject() {
        final Actie actie = TestVerantwoording.maakActie(1, ZonedDateTime.now());
        //@formatter:off
        return TestBuilders.maakLeegPersoon(1)
            .metGroep()
                .metGroepElement(getGroepElement(Element.PERSOON_IDENTITEIT.getId()))
                .metRecord()
                    .metId(5)
                    .metAttribuut(getAttribuutElement(Element.PERSOON_SOORTCODE.getId()), SoortPersoon.INGESCHREVENE.getCode())
                .eindeRecord()
            .eindeGroep()
            .metObject()
                .metObjectElement(getObjectElement(Element.PERSOON_ADRES.getId()))
                .metId(2)
                .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_ADRES_STANDAARD.getId()))
                    .metRecord()
                        .metId(5)
                        .metAttribuut(getAttribuutElement(Element.PERSOON_ADRES_GEMEENTECODE.getId()), 123)
                        .metAttribuut(getAttribuutElement(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL1.getId()), "abc")
                        .metDatumAanvangGeldigheid(20100203)
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
            .metObject()
                .metId(7)
                .metObjectElement(getObjectElement(Element.PERSOON_AFNEMERINDICATIE.getId()))
                .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_AFNEMERINDICATIE_STANDAARD.getId()))
                        .metRecord()
                            .metId(8)
                            .metAttribuut(getAttribuutElement(Element.PERSOON_ADRES_GEMEENTECODE.getId()), 123)
                     .eindeRecord()
                .eindeGroep()
            .eindeObject()
        .build();
        //@formatter:on
    }
}
