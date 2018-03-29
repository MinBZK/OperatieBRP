/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel;

import static nl.bzk.brp.domain.element.ElementHelper.getAttribuutElement;
import static nl.bzk.brp.domain.element.ElementHelper.getGroepElement;
import static nl.bzk.brp.domain.element.ElementHelper.getObjectElement;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.ElementObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParentFirstModelVisitorTest {


    private List<ElementObject> elements;

    @Before
    public void beforeTest() {
        elements = new ArrayList<>();
    }

    @Test
    public void testChildFirstStrategy() {
        final ModelVisitor visitor = new ChildFirstModelVisitor() {

            @Override
            public void doVisit(final MetaObject o) {
                elements.add(o.getObjectElement());
            }

            @Override
            public void doVisit(final MetaRecord r) {
                for (Map.Entry<AttribuutElement, MetaAttribuut> entry : r.getAttributen().entrySet()) {
                    elements.add(entry.getKey());
                }

            }

            @Override
            public void doVisit(final MetaGroep g) {
                elements.add(g.getGroepElement());
            }
        };
        visitor.visit(maakModel());

        final List<ElementObject> expectedChildFirst = Lists.newArrayList(
                getAttribuutElement(Element.PERSOON_ADRES_GEMEENTECODE.getId()),
                getGroepElement(Element.PERSOON_ADRES_STANDAARD.getId()),
                getObjectElement(Element.PERSOON_ADRES.getId()),
                getAttribuutElement(Element.PERSOON_SOORTCODE.getId()),
                getGroepElement(Element.PERSOON_IDENTITEIT.getId()),
                getObjectElement(Element.PERSOON)
        );
        for (int i = 0; i < elements.size(); i++) {
            Assert.assertEquals(elements.get(i), expectedChildFirst.get(i));
        }
    }

    @Test
    public void testParentFirstStrategy() {
        final ParentFirstModelVisitor visitor = new ParentFirstModelVisitor() {

            @Override
            public void doVisit(final MetaObject o) {
                elements.add(o.getObjectElement());
            }

            @Override
            public void doVisit(final MetaRecord r) {
                for (Map.Entry<AttribuutElement, MetaAttribuut> entry : r.getAttributen().entrySet()) {
                    elements.add(entry.getKey());
                }
            }

            @Override
            public void doVisit(final MetaGroep g) {
                elements.add(g.getGroepElement());
            }
        };
        visitor.visit(maakModel());

        final List<ElementObject> expectedParentFirst = Lists.newArrayList(
                getObjectElement(Element.PERSOON),
                getObjectElement(Element.PERSOON_ADRES.getId()),
                getGroepElement(Element.PERSOON_ADRES_STANDAARD.getId()),
                getAttribuutElement(Element.PERSOON_ADRES_GEMEENTECODE.getId()),
                getGroepElement(Element.PERSOON_IDENTITEIT.getId()),
                getAttribuutElement(Element.PERSOON_SOORTCODE.getId())
        );

        for (int i = 0; i < elements.size(); i++) {
            Assert.assertEquals(elements.get(i), expectedParentFirst.get(i));
        }
    }

    private MetaObject maakModel() {
        //@formatter:off
        final MetaObject model = MetaObject.maakBuilder()
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
                        .metAttribuut(getAttribuutElement(Element.PERSOON_ADRES_GEMEENTECODE.getId()), 123)
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
        .build();
        //@formatter:on
        return model;
    }

}
