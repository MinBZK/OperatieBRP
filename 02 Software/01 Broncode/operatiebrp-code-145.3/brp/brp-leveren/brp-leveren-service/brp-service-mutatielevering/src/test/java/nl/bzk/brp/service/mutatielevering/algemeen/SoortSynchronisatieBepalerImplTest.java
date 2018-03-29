/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.mutatielevering.algemeen;

import static org.junit.Assert.assertEquals;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.brp.domain.algemeen.Populatie;
import org.junit.Test;

/**
 */
public class SoortSynchronisatieBepalerImplTest {

    private SoortSynchronisatieBepaler berichtService = new SoortSynchronisatieBepalerImpl();

    @Test
    public final void testBepaalSoortSynchronisatieMutatieleveringOpAfnemerindicatie() {
        final SoortSynchronisatie soortSynchronisatie =
                berichtService.bepaalSoortSynchronisatie(Populatie.BETREEDT, SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE,
                        SoortAdministratieveHandeling.ACTUALISERING_KIND);

        assertEquals(SoortSynchronisatie.MUTATIE_BERICHT, soortSynchronisatie);
    }

    @Test
    public final void testBepaalSoortSynchronisatieMutatieleveringOpAfnemerindicatieGbaOverig() {
        final SoortSynchronisatie soortSynchronisatie =
                berichtService.bepaalSoortSynchronisatie(Populatie.BETREEDT, SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE,
                        SoortAdministratieveHandeling.GBA_BIJHOUDING_OVERIG);

        assertEquals(SoortSynchronisatie.VOLLEDIG_BERICHT, soortSynchronisatie);
    }


    @Test
    public final void testBepaalSoortSynchronisatieVolledigBerichtDoorSoortAdmHnd() {
        final SoortSynchronisatie soortSynchronisatie =
                berichtService.bepaalSoortSynchronisatie(Populatie.BINNEN, SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING,
                        SoortAdministratieveHandeling.GBA_BIJHOUDING_OVERIG);

        assertEquals(SoortSynchronisatie.VOLLEDIG_BERICHT, soortSynchronisatie);
    }

    @Test
    public final void testBepaalSoortSynchronisatieVolledigBerichtDoorLegePopulatie() {
        final SoortSynchronisatie soortSynchronisatie =
                berichtService.bepaalSoortSynchronisatie(null, null,
                        null);

        assertEquals(SoortSynchronisatie.VOLLEDIG_BERICHT, soortSynchronisatie);
    }

    @Test
    public final void testBepaalSoortSynchronisatieVolledigBerichtDoorCategorieDienst() {
        final SoortSynchronisatie soortSynchronisatie =
                berichtService.bepaalSoortSynchronisatie(Populatie.BINNEN, SoortDienst.ATTENDERING,
                        SoortAdministratieveHandeling.BEEINDIGING_GEREGISTREERD_PARTNERSCHAP_IN_NEDERLAND);

        assertEquals(SoortSynchronisatie.VOLLEDIG_BERICHT, soortSynchronisatie);
    }

    @Test
    public final void testBepaalSoortSynchronisatieVolledigBerichtDoorPopulatieBetreedt() {
        final SoortSynchronisatie soortSynchronisatie =
                berichtService.bepaalSoortSynchronisatie(Populatie.BETREEDT, null,
                        null);

        assertEquals(SoortSynchronisatie.VOLLEDIG_BERICHT, soortSynchronisatie);
    }

    @Test
    public final void testBepaalSoortSynchronisatieMutatieBericht() {
        final SoortSynchronisatie soortSynchronisatie =
                berichtService.bepaalSoortSynchronisatie(Populatie.BINNEN, null,
                        null);

        assertEquals(SoortSynchronisatie.MUTATIE_BERICHT, soortSynchronisatie);
    }
}
