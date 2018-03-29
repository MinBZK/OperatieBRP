/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.element;

import static nl.bzk.brp.domain.element.ElementHelper.getAttribuutElement;
import static nl.bzk.brp.domain.element.ElementHelper.getGroepElement;
import static nl.bzk.brp.domain.element.ElementHelper.getObjectElement;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import java.util.HashSet;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import org.junit.Assert;
import org.junit.Test;

/**
 * ObjectElementTest.
 */
public class ObjectElementTest {


    @Test
    public void testToString() throws Exception {
        Assert.assertEquals("Persoon.Kind [23689]", getObjectElement(Element.PERSOON_KIND.getId()).toString());
    }

    @Test
    public void testIsVanType() throws Exception {
        Assert.assertTrue(getObjectElement(Element.PERSOON_KIND.getId()).isVanType(getObjectElement(Element.BETROKKENHEID.getId())));
        Assert.assertFalse(getObjectElement(Element.BETROKKENHEID.getId()).isVanType(getObjectElement(Element.PERSOON_KIND.getId())));

        //direct supertype
        Assert.assertTrue(getObjectElement(Element.HUWELIJK.getId()).isVanType(getObjectElement(Element.HUWELIJKGEREGISTREERDPARTNERSCHAP.getId())));
        Assert.assertTrue(
                getObjectElement(Element.GEREGISTREERDPARTNERSCHAP.getId()).isVanType(getObjectElement(Element.HUWELIJKGEREGISTREERDPARTNERSCHAP.getId())));

        //indirect supertype
        Assert.assertTrue(getObjectElement(Element.HUWELIJK.getId()).isVanType(getObjectElement(Element.RELATIE.getId())));
        Assert.assertTrue(getObjectElement(Element.GEREGISTREERDPARTNERSCHAP.getId()).isVanType(getObjectElement(Element.RELATIE.getId())));

    }

    @Test
    public void testIsAliasVan() throws Exception {
        Assert.assertTrue(getObjectElement(Element.GERELATEERDEKIND.getId()).isAliasVan(getObjectElement(Element.KIND.getId())));
        Assert.assertTrue(getObjectElement(Element.PERSOON_KIND.getId()).isAliasVan(getObjectElement(Element.KIND.getId())));
    }

    @Test
    public void testGetTypeObjectElement() throws Exception {
        Assert.assertTrue(getObjectElement(Element.PERSOON_KIND.getId()).getTypeObjectElement().equals(getObjectElement(Element.BETROKKENHEID.getId())));
    }

    @Test
    public void testGetGroepen() throws Exception {

        final ObjectElement persoon = getObjectElement(Element.PERSOON.getId());
        final HashSet<GroepElement> groepElements = Sets.newHashSet(persoon.getGroepen());
        Assert.assertEquals(17, groepElements.size());

        final ObjectElement ouder = getObjectElement(Element.PERSOON_OUDER.getId());
        final HashSet<GroepElement> ouderGroepen = Sets.newHashSet(ouder.getGroepen());
        Assert.assertEquals(2, ouderGroepen.size());
        Assert.assertTrue(ouderGroepen.contains(getGroepElement(Element.PERSOON_OUDER_IDENTITEIT.getId())));
        Assert.assertTrue(ouderGroepen.contains(getGroepElement(Element.PERSOON_OUDER_OUDERSCHAP.getId())));
    }

    @Test
    public void testSorteerAttributen() {
        final ObjectElement nationaliteitObject = getObjectElement(Element.PERSOON_NATIONALITEIT.getId());
        Assert.assertEquals(Element.PERSOON_NATIONALITEIT_IDENTITEIT, nationaliteitObject.getSorteerGroep().getElement());
        Assert.assertEquals(1, nationaliteitObject.getSorteerAttributen().size());
        final AttribuutElement attribuutElement = Iterables.getOnlyElement(nationaliteitObject.getSorteerAttributen());
        Assert.assertEquals(Element.PERSOON_NATIONALITEIT_NATIONALITEITCODE, attribuutElement.getElement());
    }

    @Test
    public void testAttributen() {
        final AttribuutElement voornaamObjectElement = getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMER.getId());
        Assert.assertTrue(voornaamObjectElement.inBericht());
        Assert.assertTrue(voornaamObjectElement.heeftExpressie());
        Assert.assertEquals("Huisnr", voornaamObjectElement.getElement().getElementWaarde().getIdentdb());
    }

    @Test
    public void testAlleType() throws Exception {
        for (ObjectElement objectElement : ElementHelper.getObjecten()) {
            final Integer type = objectElement.getType();
            final ObjectElement typeObjectElement = objectElement.getTypeObjectElement();
            if (typeObjectElement != null && type == null) {
                Assert.assertEquals(objectElement.getId(), typeObjectElement.getId());
            } else if (type != null) {
                Assert.assertEquals(type, typeObjectElement.getId());
            }
            Assert.assertNotNull(objectElement.getTypeObject());
        }

    }
}
