/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.enums;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import org.junit.Test;

/**
 * Test de ElementWaarde class.
 */
public class ElementWaardeTest {

    @Test
    public void testSplit() {
        final String input = "AanduidingInhoudingVermissingReisdocument=3813,OBJECTTYPE,,,Aanduiding inhouding/vermissing reisdocument";
        assertEquals(5, Arrays.copyOf(input.split(","), 5).length);
    }

    @Test
    public void testInitialisatie() {
        assertFalse(ElementWaarde.getIds().isEmpty());
        assertFalse(ElementWaarde.getNamen().isEmpty());
    }

    @Test
    public void testVerwijzing() {
        for (final String elementNaam : ElementWaarde.getNamen()) {
            assertEquals(ElementWaarde.getInstance(elementNaam).getNaam(), elementNaam);
            assertEquals(ElementWaarde.getInstance(elementNaam), ElementWaarde.getInstance(ElementWaarde.getInstance(elementNaam).getId()));
        }
    }

    @Test
    public void testSteekProef() {
        final ElementWaarde element3294 = ElementWaarde.getInstance(3294);
        final ElementWaarde element14146 = ElementWaarde.getInstance(14146);

        assertEquals("Aangever", element3294.getNaam());
        assertEquals(SoortElement.OBJECTTYPE, element3294.getSoort());
        assertEquals("Aangever", element3294.getElementNaam());

        assertEquals("Persoon.Indicatie.VolledigeVerstrekkingsbeperking.ActieInhoud", element14146.getNaam());
        assertEquals(SoortElement.ATTRIBUUT, element14146.getSoort());
        assertEquals("Actie inhoud", element14146.getElementNaam());
        assertNotNull(element14146.getVolgnummer());
        assertEquals(Element.ACTIE.getId(), (int) element14146.getType());
        assertEquals(ElementBasisType.GROOTGETAL, element14146.getBasisType());

        assertEquals("Persoon.Indicatie.VolledigeVerstrekkingsbeperking.Standaard", element14146.getGroep().getNaam());
        assertEquals(HistoriePatroon.F_M, element14146.getGroep().getHistoriePatroon());
    }

    @Test
    public void print() {
        System.out.println(ElementWaarde.getInstance(3310).toDetailsString());
        System.out.println(ElementWaarde.getInstance(21315).toDetailsString());
        System.out.println(ElementWaarde.getInstance(3325).toDetailsString());
        System.out.println(ElementWaarde.getInstance(3481).toDetailsString());


    }
}
