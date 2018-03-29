/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.enums;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * Testen voor {@link Element}.
 */
public class ElementTest {

    @Test
    public void testParseId() {
        assertEquals(Element.PERSOON_INDICATIE, Element.parseId(Element.PERSOON_INDICATIE.getId()));
    }

    @Test
    public void testGetSoort() {
        assertEquals(SoortElement.OBJECTTYPE, Element.PERSOON_INDICATIE.getSoort());
    }

    @Test
    public void testGetGroep() {
        assertEquals(
            Element.PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_STANDAARD,
            Element.PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_TIJDSTIPREGISTRATIE.getGroep());
    }

    @Test
    public void testGetSoortAutorisatie() {
        assertEquals(
            SoortElementAutorisatie.VIA_GROEPSAUTORISATIE,
            Element.PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_TIJDSTIPREGISTRATIE.getSoortAutorisatie());
    }

    @Test
    public void testGetElementNaam() {
        assertEquals("Persoon \\ Indicatie", Element.PERSOON_INDICATIE.getElementNaam());
    }

    @Test
    public void testGetVolgnummer() {
        assertEquals(Integer.valueOf(1), Element.PERSOON_INDICATIE.getVolgnummer());
    }

    @Test
    public void testGetHistoriePatroon() {
        assertEquals(HistoriePatroon.F_M, Element.PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER.getHistoriePatroon());
    }

    @Test
    public void testGetType() {
        assertEquals(Element.HUWELIJKGEREGISTREERDPARTNERSCHAP, Element.GEREGISTREERDPARTNERSCHAP.getType());
    }

    @Test
    public void testGetTypeNull() {
        assertNull(Element.GEMEENTE.getType());
    }

    @Test
    public void testGetBasisType() {
        assertEquals(ElementBasisType.GROOTGETAL, Element.HUWELIJKGEREGISTREERDPARTNERSCHAP.getBasisType());
    }

    @Test
    public void testGetLaatsteNaamDeel() {
        assertEquals("BehandeldAlsNederlander", Element.PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER.getLaatsteNaamDeel());
    }
}
