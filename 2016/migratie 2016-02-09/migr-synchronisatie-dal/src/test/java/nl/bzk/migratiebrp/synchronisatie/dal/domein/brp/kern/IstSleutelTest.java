/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.Map;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.EntiteitSleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.IstSleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Stapel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.StapelVoorkomen;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.AbstractDeltaTest;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests voor {@link IstSleutel}.
 */
public class IstSleutelTest extends AbstractDeltaTest {

    private static final String categorie = "05";
    private static final int stapelVolgnummer = 0;
    private Stapel stapel;

    @Before
    public void setUp() {
        stapel = new Stapel(maakPersoon(true), categorie, stapelVolgnummer);
    }

    @Test
    public void testSleutelOpbouwVoorStapel() {
        final IstSleutel sleutel = new IstSleutel(stapel, true);

        assertEquals(categorie, sleutel.getCategorie());
        assertEquals(stapelVolgnummer, sleutel.getStapelnummer());
        assertFalse(sleutel.isVoorkomenSleutel());
        assertNotNull(sleutel.toShortString());
        assertNotNull(sleutel.toString());
        assertNull(sleutel.getId());
        assertNull(sleutel.getVeld());
        assertNull(sleutel.getVoorkomennummer());
        assertTrue(sleutel.getDelen().isEmpty());
    }

    @Test
    public void testSleutelOpbouwVoorStapelMetVeld() {
        final String veldnaam = "veldnaam";
        final IstSleutel sleutel = new IstSleutel(stapel, veldnaam, true);

        assertEquals(categorie, sleutel.getCategorie());
        assertEquals(stapelVolgnummer, sleutel.getStapelnummer());
        assertEquals(veldnaam, sleutel.getVeld());
        assertFalse(sleutel.isVoorkomenSleutel());
        assertNotNull(sleutel.toShortString());
        assertNotNull(sleutel.toString());
        assertNull(sleutel.getId());
        assertNull(sleutel.getVoorkomennummer());
        assertTrue(sleutel.getDelen().isEmpty());
    }

    @Test
    public void testSleutelOpbouwVoorStapelMetDelen() {
        final String deelKey = "tsReg";
        final Timestamp deelValue = Timestamp.valueOf("2015-06-23 16:16:35.000");

        final IstSleutel sleutel = new IstSleutel(stapel, true);
        sleutel.addSleuteldeel(deelKey, deelValue);

        assertEquals(categorie, sleutel.getCategorie());
        assertEquals(stapelVolgnummer, sleutel.getStapelnummer());
        assertFalse(sleutel.isVoorkomenSleutel());
        assertNotNull(sleutel.toShortString());
        assertNotNull(sleutel.toString());
        assertNull(sleutel.getId());
        assertNull(sleutel.getVeld());
        assertNull(sleutel.getVoorkomennummer());

        final Map<String, Object> delen = sleutel.getDelen();
        assertFalse(delen.isEmpty());
        assertTrue(delen.containsKey(deelKey));
        assertEquals(deelValue, delen.get(deelKey));
    }

    @Test
    public void testSleutelOpbouwVoorStapelVoorkomen() {
        final int stapelVoorkomenVolgnummer = 0;

        final StapelVoorkomen stapelVoorkomen =
                new StapelVoorkomen(stapel, stapelVoorkomenVolgnummer, maakAdministratieveHandeling(DATUMTIJD_STEMPEL_NIEUW));
        final String veldnaam = "veldnaam";
        final IstSleutel sleutel = new IstSleutel(stapelVoorkomen, veldnaam, true);

        assertEquals(categorie, sleutel.getCategorie());
        assertEquals(stapelVolgnummer, sleutel.getStapelnummer());
        assertEquals((Integer) stapelVoorkomenVolgnummer, sleutel.getVoorkomennummer());
        assertEquals(veldnaam, sleutel.getVeld());
        assertNotNull(sleutel.toShortString());
        assertNotNull(sleutel.toString());
        assertNull(sleutel.getId());
        assertTrue(sleutel.isVoorkomenSleutel());
        assertTrue(sleutel.getDelen().isEmpty());
    }

    @Test
    public void testEquality() {
        final IstSleutel bestaandeSleutel = new IstSleutel(stapel, true);
        final IstSleutel nieuweSleutel = new IstSleutel(stapel, false);

        assertFalse(nieuweSleutel.equals(bestaandeSleutel));
        assertFalse(bestaandeSleutel.equals(nieuweSleutel));
        assertTrue(bestaandeSleutel.equals(bestaandeSleutel));
        assertTrue(nieuweSleutel.equals(nieuweSleutel));

        final String veldnaam = "veldnaam";
        final IstSleutel bestaandeSleutelMetVeld = new IstSleutel(stapel, veldnaam, true);
        assertFalse(bestaandeSleutel.equals(bestaandeSleutelMetVeld));

        final EntiteitSleutel entiteitSleutel = new EntiteitSleutel(String.class, veldnaam);
        assertFalse(bestaandeSleutel.equals(entiteitSleutel));
    }

    @Test(expected = IllegalStateException.class)
    public void testIdException() {
        final IstSleutel sleutel = new IstSleutel(stapel, true);
        sleutel.setId(1L);
    }

}
