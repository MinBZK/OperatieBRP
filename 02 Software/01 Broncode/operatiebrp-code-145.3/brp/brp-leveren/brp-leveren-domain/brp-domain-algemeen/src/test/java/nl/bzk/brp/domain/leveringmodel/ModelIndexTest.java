/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel;

import static nl.bzk.brp.domain.element.ElementHelper.getAttribuutElement;
import static nl.bzk.brp.domain.element.ElementHelper.getGroepElement;
import static nl.bzk.brp.domain.element.ElementHelper.getObjectElement;

import java.util.Collection;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.ObjectElement;
import org.junit.Assert;
import org.junit.Test;

/**
 * ModelIndexTest
 */
public class ModelIndexTest {

    private final MetaObject metaObject = maakDummyPersoonObject();
    private ModelIndex modelAanwijzer = new ModelIndex(metaObject);

    @Test
    public void testGetMetaObject() {
        Assert.assertEquals(metaObject, modelAanwijzer.getMetaObject());
    }

    @Test
    public void testGeefGroepenVanElement() throws Exception {
        Collection<MetaGroep> groepenVanElement = modelAanwijzer.geefGroepenVanElement(getGroepElement(Element.PERSOON_ADRES_STANDAARD.getId()));
        Assert.assertFalse(groepenVanElement.isEmpty());
    }

    @Test
    public void testGeefGroepenVanElement_emptySet() throws Exception {
        Collection<MetaGroep> groepenVanElement = modelAanwijzer.geefGroepenVanElement(getGroepElement(Element.PERSOON_ADRES_IDENTITEIT.getId()));
        Assert.assertTrue(groepenVanElement.isEmpty());
    }

    @Test
    public void testGeefGroepenVanElement_nullArg() throws Exception {
        Collection<MetaGroep> groepenVanElement = modelAanwijzer.geefGroepenVanElement(null);
        Assert.assertTrue(groepenVanElement.isEmpty());
    }

    @Test
    public void testGeefGroepen() throws Exception {
        Collection<MetaGroep> alleGroepen = modelAanwijzer.geefGroepen();
        Assert.assertFalse(alleGroepen.isEmpty());
    }

    @Test
    public void testGeefObjecten() throws Exception {
        Collection<MetaObject> alleObjecten = modelAanwijzer.geefObjecten();
        Assert.assertFalse(alleObjecten.isEmpty());
    }

    @Test
    public void testGeefObjectenVanElement() throws Exception {
        Collection<MetaObject> alleAdresObjecten = modelAanwijzer.geefObjecten(getObjectElement(Element.PERSOON_ADRES.getId()));
        Assert.assertFalse(alleAdresObjecten.isEmpty());
    }

    @Test
    public void testGeefObjectenVanElement_emptySet() throws Exception {
        Collection<MetaObject> alleAdresObjecten = modelAanwijzer.geefObjecten(getObjectElement(Element.PERSOON_NATIONALITEIT.getId()));
        Assert.assertTrue(alleAdresObjecten.isEmpty());
    }

    @Test
    public void testGeefObjectenVanElement_nullArg() throws Exception {
        Collection<MetaObject> alleAdresObjecten = modelAanwijzer.geefObjecten((ObjectElement) null);
        Assert.assertTrue(alleAdresObjecten.isEmpty());
    }

    @Test
    public void testGeefAttribuutElementen() throws Exception {
        Collection<AttribuutElement> alleAttribuutElementen = modelAanwijzer.geefAttribuutElementen();
        Assert.assertFalse(alleAttribuutElementen.isEmpty());
    }

    @Test
    public void testGeefAttributen() throws Exception {
        Collection<MetaAttribuut> groepenVanElement = modelAanwijzer.geefAttributen(getAttribuutElement(Element.PERSOON_ADRES_GEMEENTECODE.getId()));
        Assert.assertEquals(1, groepenVanElement.size());
    }

    @Test
    public void testGeefAttributen_emptySet() throws Exception {
        Collection<MetaAttribuut> groepenVanElement = modelAanwijzer.geefAttributen(getAttribuutElement(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL2.getId()));
        Assert.assertTrue(groepenVanElement.isEmpty());
    }

    @Test
    public void testGeefAttributen_nullArg() throws Exception {
        Collection<MetaAttribuut> groepenVanElement = modelAanwijzer.geefAttributen((AttribuutElement) null);
        Assert.assertTrue(groepenVanElement.isEmpty());
    }

    private MetaObject maakDummyPersoonObject() {
        //@formatter:off
        return MetaObject.maakBuilder()
            .metObjectElement(Element.PERSOON.getId())
            .metId(1)
            .metObject()
                .metObjectElement(Element.PERSOON_ADRES.getId())
                .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_ADRES_STANDAARD.getId()))
                    .metRecord()
                        .metId(1)
                        .metAttribuut(getAttribuutElement(Element.PERSOON_ADRES_GEMEENTECODE.getId()), 123)
                        .metAttribuut(getAttribuutElement(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL1.getId()), "abc")
                        .metAttribuut(getAttribuutElement(Element.PERSOON_ADRES_DATUMAANVANGGELDIGHEID.getId()), 20100203)
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
        .build();
        //@formatter:on
    }
}
