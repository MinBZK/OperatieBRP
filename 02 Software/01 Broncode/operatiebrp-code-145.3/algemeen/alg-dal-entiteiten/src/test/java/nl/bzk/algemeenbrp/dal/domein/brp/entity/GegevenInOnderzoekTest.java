/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import org.junit.Before;
import org.junit.Test;

/**
 * Unittest voor {@link GegevenInOnderzoek}.
 */
public class GegevenInOnderzoekTest {

    private GegevenInOnderzoek gegevenInOnderzoek;

    @Before
    public void setUp() {
        gegevenInOnderzoek = new GegevenInOnderzoek(new Onderzoek(), Element.RELATIE_DATUMAANVANG);
    }

    @Test
    public void testConstructor() {
        assertNotNull(gegevenInOnderzoek.getOnderzoek());
        assertNotNull(gegevenInOnderzoek.getSoortGegeven());
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestDefaultConstructor() {
        final GegevenInOnderzoek gio = new GegevenInOnderzoek();
        assertNull(gio.getOnderzoek());
        assertNull(gio.getSoortGegeven());
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorNullOnderzoek() {
        new GegevenInOnderzoek(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorNullSoortGegeven() {
        new GegevenInOnderzoek(new Onderzoek(), null);
    }

    @Test
    public void testGetSetId() {
        assertNull(gegevenInOnderzoek.getId());
        gegevenInOnderzoek.setId(null);
        assertNull(gegevenInOnderzoek.getId());

        gegevenInOnderzoek.setId(1L);
        assertTrue(1 == gegevenInOnderzoek.getId());
    }

    @Test
    public void testGetSetEntiteitOfVoorkomen() {
        assertNull(gegevenInOnderzoek.getEntiteitOfVoorkomen());

        final Relatie relatie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        final FormeleHistorie historie = new RelatieHistorie(relatie);

        gegevenInOnderzoek.setEntiteitOfVoorkomen(historie);
        assertEquals(historie, gegevenInOnderzoek.getEntiteitOfVoorkomen());

        gegevenInOnderzoek.setEntiteitOfVoorkomen(relatie);
        assertEquals(relatie, gegevenInOnderzoek.getEntiteitOfVoorkomen());
    }

    @Test(expected = NullPointerException.class)
    public void testSetEntiteitOfVoorkomenNull() {
        gegevenInOnderzoek.setEntiteitOfVoorkomen(null);
    }

    @Test
    public void testToString() {
        assertEquals("GegevenInOnderzoek -> null", gegevenInOnderzoek.toString());
        gegevenInOnderzoek.setEntiteitOfVoorkomen(new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING));
        assertEquals("GegevenInOnderzoek -> Relatie", gegevenInOnderzoek.toString());
    }
}
