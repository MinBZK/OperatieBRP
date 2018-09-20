/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.bepalers.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import nl.bzk.brp.levering.business.bepalers.SoortSynchronisatieBepaler;
import nl.bzk.brp.levering.model.Populatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import org.junit.Test;

/**
 */
public class SoortSynchronisatieBepalerImplTest {

    private SoortSynchronisatieBepaler berichtService = new SoortSynchronisatieBepalerImpl();
    @Test
    public final void testBepaalSoortSynchronisatieVolledigBerichtDoorSoortAdmHnd() {
        final SoortSynchronisatie soortSynchronisatie =
            berichtService.bepaalSoortSynchronisatie(null, null,
                SoortAdministratieveHandeling.SYNCHRONISATIE_PERSOON);

        assertEquals(SoortSynchronisatie.VOLLEDIGBERICHT, soortSynchronisatie);
    }

    @Test
    public final void testBepaalSoortSynchronisatieVolledigBerichtDoorLegePopulatie() {
        final SoortSynchronisatie soortSynchronisatie =
            berichtService.bepaalSoortSynchronisatie(null, null,
                null);

        assertEquals(SoortSynchronisatie.VOLLEDIGBERICHT, soortSynchronisatie);
    }

    @Test
    public final void testBepaalSoortSynchronisatieVolledigBerichtDoorCategorieDienst() {
        final SoortSynchronisatie soortSynchronisatie =
            berichtService.bepaalSoortSynchronisatie(null, SoortDienst.ATTENDERING,
                null);

        assertEquals(SoortSynchronisatie.VOLLEDIGBERICHT, soortSynchronisatie);
    }

    @Test
    public final void testBepaalSoortSynchronisatieVolledigBerichtDoorPopulatieBetreedt() {
        final SoortSynchronisatie soortSynchronisatie =
            berichtService.bepaalSoortSynchronisatie(Populatie.BETREEDT, null,
                null);

        assertEquals(SoortSynchronisatie.VOLLEDIGBERICHT, soortSynchronisatie);
    }

    @Test
    public final void testBepaalSoortSynchronisatieMutatieBericht() {
        final SoortSynchronisatie soortSynchronisatie =
            berichtService.bepaalSoortSynchronisatie(Populatie.BINNEN, null,
                null);

        assertEquals(SoortSynchronisatie.MUTATIEBERICHT, soortSynchronisatie);
    }

    @Test
    public final void testGeefHandelingenMetVolledigBericht() {
        final List<SoortAdministratieveHandeling> resultaat =
            berichtService.geefHandelingenMetVolledigBericht();
        assertNotNull(resultaat);
        assertEquals(2, resultaat.size());
        assertTrue(resultaat.contains(SoortAdministratieveHandeling.SYNCHRONISATIE_PERSOON));
        assertTrue(resultaat.contains(SoortAdministratieveHandeling.G_B_A_BIJHOUDING_OVERIG));
    }
}
