/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.algemeen.service;

import static junit.framework.TestCase.assertEquals;

import java.util.List;

import nl.bzk.brp.levering.algemeen.service.impl.AdministratieveHandelingenOverslaanServiceImpl;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;

import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class AdministratieveHandelingenOverslaanServiceImplTest {

    private static final String SOORT_ADMINISTRATIEVE_HANDELINGEN_OVERSLAAN = "soortAdministratieveHandelingenOverslaan";

    private final AdministratieveHandelingenOverslaanService administratieveHandelingenOverslaanService =
            new AdministratieveHandelingenOverslaanServiceImpl();

    @Test
    public final void testGeefLijstVanAdministratieveHandelingDieOvergeslagenMoetenWorden() {
        final String soortAdministratieveHandelingenOverslaan = "G_B_A_INITIELE_VULLING";

        ReflectionTestUtils.setField(
            administratieveHandelingenOverslaanService,
            SOORT_ADMINISTRATIEVE_HANDELINGEN_OVERSLAAN,
            soortAdministratieveHandelingenOverslaan);

        final List<SoortAdministratieveHandeling> resultaat =
                administratieveHandelingenOverslaanService.geefLijstVanAdministratieveHandelingDieOvergeslagenMoetenWorden();

        assertEquals(SoortAdministratieveHandeling.G_B_A_INITIELE_VULLING, resultaat.get(0));
        assertEquals(1, resultaat.size());
    }

    @Test
    public final void testGeefLijstVanAdministratieveHandelingDieOvergeslagenMoetenWordenMeerdereHandelingen() {
        final String soortAdministratieveHandelingenOverslaan = "G_B_A_INITIELE_VULLING,ADOPTIE_INGEZETENE";

        ReflectionTestUtils.setField(
            administratieveHandelingenOverslaanService,
            SOORT_ADMINISTRATIEVE_HANDELINGEN_OVERSLAAN,
            soortAdministratieveHandelingenOverslaan);

        final List<SoortAdministratieveHandeling> resultaat =
                administratieveHandelingenOverslaanService.geefLijstVanAdministratieveHandelingDieOvergeslagenMoetenWorden();

        assertEquals(SoortAdministratieveHandeling.G_B_A_INITIELE_VULLING, resultaat.get(0));
        assertEquals(SoortAdministratieveHandeling.ADOPTIE_INGEZETENE, resultaat.get(1));
        assertEquals(2, resultaat.size());
    }

    @Test
    public final void testGeefLijstVanAdministratieveHandelingDieOvergeslagenMoetenWordenMetSpaties() {
        final String soortAdministratieveHandelingenOverslaan = " G_B_A_INITIELE_VULLING , ADOPTIE_INGEZETENE ";

        ReflectionTestUtils.setField(
            administratieveHandelingenOverslaanService,
            SOORT_ADMINISTRATIEVE_HANDELINGEN_OVERSLAAN,
            soortAdministratieveHandelingenOverslaan);

        final List<SoortAdministratieveHandeling> resultaat =
                administratieveHandelingenOverslaanService.geefLijstVanAdministratieveHandelingDieOvergeslagenMoetenWorden();

        assertEquals(SoortAdministratieveHandeling.G_B_A_INITIELE_VULLING, resultaat.get(0));
        assertEquals(SoortAdministratieveHandeling.ADOPTIE_INGEZETENE, resultaat.get(1));
        assertEquals(2, resultaat.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testGeefLijstVanAdministratieveHandelingDieOvergeslagenMoetenWordenOnbekendeHandeling() {
        final String soortAdministratieveHandelingenOverslaan = "ONBEKENDE_HANDELING";

        ReflectionTestUtils.setField(
            administratieveHandelingenOverslaanService,
            SOORT_ADMINISTRATIEVE_HANDELINGEN_OVERSLAAN,
            soortAdministratieveHandelingenOverslaan);

        administratieveHandelingenOverslaanService.geefLijstVanAdministratieveHandelingDieOvergeslagenMoetenWorden();
    }

    @Test
    public final void testGeefLijstVanAdministratieveHandelingDieOvergeslagenMoetenWordenGeenHandelingen() {
        final String soortAdministratieveHandelingenOverslaan = "";

        ReflectionTestUtils.setField(
            administratieveHandelingenOverslaanService,
            SOORT_ADMINISTRATIEVE_HANDELINGEN_OVERSLAAN,
            soortAdministratieveHandelingenOverslaan);

        final List<SoortAdministratieveHandeling> resultaat =
                administratieveHandelingenOverslaanService.geefLijstVanAdministratieveHandelingDieOvergeslagenMoetenWorden();

        assertEquals(0, resultaat.size());
    }

    @Test
    public final void testGeefLijstVanAdministratieveHandelingDieOvergeslagenMoetenWordenHandelingenNull() {
        ReflectionTestUtils.setField(administratieveHandelingenOverslaanService, SOORT_ADMINISTRATIEVE_HANDELINGEN_OVERSLAAN, null);

        final List<SoortAdministratieveHandeling> resultaat =
                administratieveHandelingenOverslaanService.geefLijstVanAdministratieveHandelingDieOvergeslagenMoetenWorden();

        assertEquals(0, resultaat.size());
    }

    @Test
    public final void testGeefLijstVanAdministratieveHandelingDieOvergeslagenMoetenWordenGevuldeEnLegeHandeling() {
        final String soortAdministratieveHandelingenOverslaan = "G_B_A_INITIELE_VULLING,";

        ReflectionTestUtils.setField(
            administratieveHandelingenOverslaanService,
            SOORT_ADMINISTRATIEVE_HANDELINGEN_OVERSLAAN,
            soortAdministratieveHandelingenOverslaan);

        final List<SoortAdministratieveHandeling> resultaat =
                administratieveHandelingenOverslaanService.geefLijstVanAdministratieveHandelingDieOvergeslagenMoetenWorden();

        assertEquals(SoortAdministratieveHandeling.G_B_A_INITIELE_VULLING, resultaat.get(0));
        assertEquals(1, resultaat.size());
    }

}
