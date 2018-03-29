/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.element;

import static nl.bzk.brp.domain.element.ElementHelper.getAttribuutElement;
import static nl.bzk.brp.domain.element.ElementHelper.getElement;
import static nl.bzk.brp.domain.element.ElementHelper.getElementMetId;
import static nl.bzk.brp.domain.element.ElementHelper.getGroepElement;
import static nl.bzk.brp.domain.element.ElementHelper.getObjectElement;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * ElementHelperTest
 */
public class ElementHelperTest {

    private static final int NIET_BESTAAND_ELEMENT_ID = 987654321;
    private static final String NIET_BESTAAND_ELEMENT = "Niet.Bestaand.Element";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testGetObjectElement() {
        ObjectElement element = getObjectElement(Element.PERSOON_VOORNAAM.getId());
        Assert.assertEquals(Element.PERSOON_VOORNAAM, element.getElement());

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("ObjectElement niet gevonden met elementId:" + NIET_BESTAAND_ELEMENT_ID);
        getObjectElement(NIET_BESTAAND_ELEMENT_ID);
    }

    @Test
    public void testGetObjectElementAhvNaam() {
        ObjectElement element = getObjectElement("Persoon.Voornaam");
        Assert.assertEquals("Persoon.Voornaam", element.getNaam());

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage(
                String.format("Het gevonden element met naam %s is geen objectelement", Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE.getNaam()));
        getObjectElement("GerelateerdeGeregistreerdePartner.Persoon.Geboorte");
    }

    @Test
    public void testGetGroepElement() {
        GroepElement element = getGroepElement(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE.getId());
        Assert.assertEquals("GerelateerdeGeregistreerdePartner.Persoon.Geboorte", element.getNaam());
    }

    @Test
    public void testGetGroepElementAhvNaam() {
        GroepElement element = getGroepElement("GerelateerdeGeregistreerdePartner.Persoon.Geboorte");
        Assert.assertEquals(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE, element.getElement());

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage(String.format("Het gevonden element met naam %s is geen groepelement", Element.PERSOON_VOORNAAM.getNaam()));
        getGroepElement("Persoon.Voornaam");
    }

    @Test
    public void testGetAttribuutElement() {
        AttribuutElement element = getAttribuutElement(
                Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_WOONPLAATSNAAM.getId());
        Assert.assertEquals("GerelateerdeGeregistreerdePartner.Persoon.Geboorte.Woonplaatsnaam", element.getNaam());

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("Element niet gevonden met naam:" + NIET_BESTAAND_ELEMENT);
        getElement(NIET_BESTAAND_ELEMENT);
    }

    @Test
    public void testGetAttribuutElementAhvNaam() {
        AttribuutElement element = getAttribuutElement("GerelateerdeGeregistreerdePartner.Persoon.Geboorte.Woonplaatsnaam");
        Assert.assertEquals(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_WOONPLAATSNAAM, element.getElement());

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage(String.format("Het gevonden element met naam %s is geen attribuutelement", Element.PERSOON_VOORNAAM.getNaam()));
        getAttribuutElement("Persoon.Voornaam");
    }

    @Test
    public void testGetElement() {
        GroepElement element = (GroepElement) getElement("GerelateerdeGeregistreerdePartner.Persoon.Geboorte");
        Assert.assertEquals(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE.getId(), element.getId().intValue());
    }

    @Test
    public void testGetElementMetId() {
        Assert.assertEquals(getObjectElement(Element.PERSOON_VOORNAAM.getId()), getElementMetId(3022));

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("Element niet gevonden met id:" + NIET_BESTAAND_ELEMENT_ID);
        getElementMetId(NIET_BESTAAND_ELEMENT_ID);
    }

    @Test
    public void testGetObjectAssociatieCode() {
        ObjectElement objectElement = getObjectElement(Element.PERSOON_VOORNAAM.getId());
        Assert.assertEquals("Voornamen", ElementHelper.getObjectAssociatiecode(objectElement));

        Assert.assertNull(ElementHelper.getObjectAssociatiecode(getObjectElement(Element.REDENEINDERELATIE)));
    }

    @Test
    public void testReset() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method m = ElementHelper.class.getDeclaredMethod("reset");
        m.setAccessible(true);
        Object o = m.invoke(null);
    }

    @Test(expected = IllegalStateException.class)
    public void getObjectElementexception() throws Exception {
        getGroepElement(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_WOONPLAATSNAAM.getId());
    }

    @Test(expected = IllegalStateException.class)
    public void getGroepElementexception() throws Exception {
        getGroepElement(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_WOONPLAATSNAAM.getId());
    }

    @Test(expected = IllegalStateException.class)
    public void getAttribuutElementexception() throws Exception {
        getAttribuutElement(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE.getId());
    }

    @Test
    public void getObjectAssociatiecode() {
        Assert.assertEquals("Voornamen", ElementHelper.getObjectAssociatiecode(getObjectElement(Element.PERSOON_VOORNAAM.getId())));
    }
}
