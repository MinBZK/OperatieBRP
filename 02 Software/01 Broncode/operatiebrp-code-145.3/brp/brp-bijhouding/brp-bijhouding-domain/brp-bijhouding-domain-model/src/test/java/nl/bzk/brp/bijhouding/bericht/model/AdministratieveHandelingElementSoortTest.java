/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import org.junit.Test;

/**
 * Testen voor {@link AdministratieveHandelingElementSoort}.
 */
public class AdministratieveHandelingElementSoortTest extends AbstractElementTest {

    @Test
    public void testGetElementNamen() throws Exception {
        List<String> list = AdministratieveHandelingElementSoort.getElementNamen();
        assertTrue(list.size() > 0);
    }

    @Test(expected = OngeldigeWaardeException.class)
    public void parseWaardeOngGeldig() throws OngeldigeWaardeException {
        AdministratieveHandelingElementSoort.parseElementNaam("onzin");
    }

    @Test
    public void parseWaardeGeldig() throws OngeldigeWaardeException {
        AdministratieveHandelingElementSoort
                .parseElementNaam(AdministratieveHandelingElementSoort.AANGAAN_GEREGISTREERD_PARTNERSCHAP_IN_BUITENLAND.getElementNaam());
    }


    @Test
    public void isGBA() throws OngeldigeWaardeException {
        assertTrue(AdministratieveHandelingElementSoort.GBA_OMZETTING_GEREGISTREERD_PARTNERSCHAP_IN_HUWELIJK.gbaAdministratieveHandeling());
    }

    @Test
    public void bijhoudingspartijMoetGecontroleerdWorden () {
        assertTrue(AdministratieveHandelingElementSoort.WIJZIGING_VERBLIJFSRECHT.isControleerZendendePartijIsBijhoudendePartij());
    }

    @Test
    public void testIsCorrectieHandeling() {
        assertFalse(AdministratieveHandelingElementSoort.VERHUIZING_INTERGEMEENTELIJK.isCorrectie());
        assertTrue(AdministratieveHandelingElementSoort.CORRECTIE_HUWELIJK.isCorrectie());
        assertTrue(AdministratieveHandelingElementSoort.ONGEDAANMAKING_HUWELIJK.isCorrectie());
    }
}
