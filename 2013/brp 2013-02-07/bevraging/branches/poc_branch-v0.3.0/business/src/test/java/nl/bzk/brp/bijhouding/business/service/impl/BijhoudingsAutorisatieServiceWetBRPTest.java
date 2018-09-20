/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.service.impl;

import static junit.framework.Assert.assertEquals;

import nl.bzk.brp.bevraging.domein.Partij;
import nl.bzk.brp.bevraging.domein.SoortPartij;
import nl.bzk.brp.bevraging.domein.Verantwoordelijke;
import nl.bzk.brp.bijhouding.domein.SoortActie;
import nl.bzk.brp.bijhouding.domein.aut.SoortBijhouding;
import nl.bzk.brp.bijhouding.business.bijhoudingsautorisatie.BijhoudingsAutorisatieResultaat;
import org.junit.Before;
import org.junit.Test;


/**
 * De tests in deze class testen de bijhoudingsautorisatie die via de wet worden geregeld.
 */
public class BijhoudingsAutorisatieServiceWetBRPTest extends BijhoudingsAutorisatieServiceImplTest {

    private SoortActie dummyActie;

    @Before
    public void init() {
        dummyActie = new SoortActie(1L, "dummy actie", 1L);
    }

    @Test
    public void testBijhoudingNietToegestaanZonderAutorisaties() {
        final Partij bijhoudendePartijX = initPartij(SoortPartij.GEMEENTE, 15L);
        final Partij bijhVerantwoordelijkeVoorafX = bijhoudendePartijX;
        final Partij bijhVerantwoordelijkeAchterafY = initPartij(SoortPartij.GEMEENTE, 22L);

        BijhoudingsAutorisatieResultaat resultaat =
            getBijhoudingsAutorisatieService().bepaalBijhoudingsAutorisatie(bijhoudendePartijX,
                    bijhVerantwoordelijkeVoorafX, bijhVerantwoordelijkeAchterafY, dummyActie, null);

        assertEquals(BijhoudingsAutorisatieResultaat.BIJHOUDING_NIET_TOEGESTAAN, resultaat);
    }

    @Test
    public void testBijhoudingDoorGemeenteXVerantwoordelijkeVoorafXAchterafY() {
        final Partij bijhoudendePartijX = initPartij(SoortPartij.GEMEENTE, 11L);
        final Partij bijhVerantwoordelijkeVoorafX = bijhoudendePartijX;
        final Partij bijhVerantwoordelijkeAchterafY = initPartij(SoortPartij.GEMEENTE, 12L);

        initWettelijkBepaaldeBijhoudingsAutorisaties(bijhoudendePartijX);

        BijhoudingsAutorisatieResultaat resultaat =
            getBijhoudingsAutorisatieService().bepaalBijhoudingsAutorisatie(bijhoudendePartijX,
                    bijhVerantwoordelijkeVoorafX, bijhVerantwoordelijkeAchterafY, dummyActie, null);

        assertEquals(BijhoudingsAutorisatieResultaat.BIJHOUDING_VOORSTEL_TOEGESTAAN, resultaat);

        verleenAutorisatie(bijhoudendePartijX, bijhVerantwoordelijkeAchterafY, SoortBijhouding.AUTOMATISCH_FIATTEREN,
                           Verantwoordelijke.COLLEGE);

        resultaat =
            getBijhoudingsAutorisatieService().bepaalBijhoudingsAutorisatie(bijhoudendePartijX,
                    bijhVerantwoordelijkeVoorafX, bijhVerantwoordelijkeAchterafY, dummyActie, null);

        assertEquals(BijhoudingsAutorisatieResultaat.BIJHOUDING_TOEGESTAAN_OP_BASIS_AUT_FIATTERING_MANDAAT, resultaat);
    }

    @Test
    public void testBijhoudingDoorGemeenteXVerantwoordelijkeVoorafXAchterafX() {
        final Partij bijhoudendePartijX = initPartij(SoortPartij.GEMEENTE, 19L);
        final Partij bijhVerantwoordelijkeVoorafX = bijhoudendePartijX;
        final Partij bijhVerantwoordelijkeAchterafX = bijhoudendePartijX;

        initWettelijkBepaaldeBijhoudingsAutorisaties(bijhoudendePartijX);

        final BijhoudingsAutorisatieResultaat resultaat =
            getBijhoudingsAutorisatieService().bepaalBijhoudingsAutorisatie(bijhoudendePartijX,
                    bijhVerantwoordelijkeVoorafX, bijhVerantwoordelijkeAchterafX, dummyActie, null);

        assertEquals(BijhoudingsAutorisatieResultaat.BIJHOUDING_TOEGESTAAN_OP_BASIS_WET_BRP, resultaat);
    }

    @Test
    public void testBijhoudingDoorGemeenteXVerantwoordelijkeVoorafXAchterafMinister() {
        final Partij bijhoudendePartijX = initPartij(SoortPartij.GEMEENTE, 18L);
        final Partij bijhVerantwoordelijkeVoorafX = bijhoudendePartijX;
        final Partij bijhVerantwoordelijkeAchterafMinister = initPartijMinister();

        initWettelijkBepaaldeBijhoudingsAutorisaties(bijhoudendePartijX);

        final BijhoudingsAutorisatieResultaat resultaat =
            getBijhoudingsAutorisatieService().bepaalBijhoudingsAutorisatie(bijhoudendePartijX,
                    bijhVerantwoordelijkeVoorafX, bijhVerantwoordelijkeAchterafMinister, dummyActie, null);

        assertEquals(BijhoudingsAutorisatieResultaat.BIJHOUDING_TOEGESTAAN_OP_BASIS_WET_BRP, resultaat);
    }

    @Test
    public void testBijhoudingDoorGemeenteXVerantwoordelijkeVoorafMinisterAchterafY() {
        final Partij bijhoudendePartijX = initPartij(SoortPartij.GEMEENTE, 17L);
        final Partij bijhVerantwoordelijkeVoorafMinister = initPartijMinister();
        final Partij bijhVerantwoordelijkeAchterafY = initPartij(SoortPartij.GEMEENTE, 27L);

        initWettelijkBepaaldeBijhoudingsAutorisaties(bijhoudendePartijX);

        BijhoudingsAutorisatieResultaat resultaat =
            getBijhoudingsAutorisatieService().bepaalBijhoudingsAutorisatie(bijhoudendePartijX,
                    bijhVerantwoordelijkeVoorafMinister, bijhVerantwoordelijkeAchterafY, dummyActie, null);

        assertEquals(BijhoudingsAutorisatieResultaat.BIJHOUDING_VOORSTEL_TOEGESTAAN, resultaat);

        verleenAutorisatie(bijhoudendePartijX, bijhVerantwoordelijkeAchterafY, SoortBijhouding.AUTOMATISCH_FIATTEREN,
                           Verantwoordelijke.COLLEGE);

        resultaat =
            getBijhoudingsAutorisatieService().bepaalBijhoudingsAutorisatie(bijhoudendePartijX,
                    bijhVerantwoordelijkeVoorafMinister, bijhVerantwoordelijkeAchterafY, dummyActie, null);

        assertEquals(BijhoudingsAutorisatieResultaat.BIJHOUDING_TOEGESTAAN_OP_BASIS_AUT_FIATTERING_MANDAAT, resultaat);
    }

    @Test
    public void testBijhoudingDoorGemeenteXVerantwoordelijkeVoorafMinisterAchterafX() {
        final Partij bijhoudendePartijX = initPartij(SoortPartij.GEMEENTE, 14L);
        final Partij bijhVerantwoordelijkeVoorafMinister = initPartijMinister();
        final Partij bijhVerantwoordelijkeAchterafX = bijhoudendePartijX;

        initWettelijkBepaaldeBijhoudingsAutorisaties(bijhoudendePartijX);

        final BijhoudingsAutorisatieResultaat resultaat =
            getBijhoudingsAutorisatieService().bepaalBijhoudingsAutorisatie(bijhoudendePartijX,
                    bijhVerantwoordelijkeVoorafMinister, bijhVerantwoordelijkeAchterafX, dummyActie, null);

        assertEquals(BijhoudingsAutorisatieResultaat.BIJHOUDING_TOEGESTAAN_OP_BASIS_WET_BRP, resultaat);
    }

    @Test
    public void testBijhoudingDoorGemeenteXVerantwoordelijkeVoorafMinisterAchterafMinister() {
        final Partij bijhoudendePartijX = initPartij(SoortPartij.GEMEENTE, 15L);
        final Partij bijhVerantwoordelijkeVoorafMinister = initPartijMinister();
        final Partij bijhVerantwoordelijkeAchterafMinister = initPartijMinister();

        initWettelijkBepaaldeBijhoudingsAutorisaties(bijhoudendePartijX);

        final BijhoudingsAutorisatieResultaat resultaat =
            getBijhoudingsAutorisatieService().bepaalBijhoudingsAutorisatie(bijhoudendePartijX,
                    bijhVerantwoordelijkeVoorafMinister, bijhVerantwoordelijkeAchterafMinister, dummyActie, null);

        assertEquals(BijhoudingsAutorisatieResultaat.BIJHOUDING_NIET_TOEGESTAAN, resultaat);
    }

    @Test
    public void testBijhoudingDoorGemeenteXVerantwoordelijkeVoorafYAchterafY() {
        final Partij bijhoudendePartijX = initPartij(SoortPartij.GEMEENTE, 16L);
        final Partij bijhVerantwoordelijkeVoorafY = initPartij(SoortPartij.GEMEENTE, 23L);
        final Partij bijhVerantwoordelijkeAchterafY = bijhVerantwoordelijkeVoorafY;

        initWettelijkBepaaldeBijhoudingsAutorisaties(bijhoudendePartijX);

        BijhoudingsAutorisatieResultaat resultaat =
            getBijhoudingsAutorisatieService().bepaalBijhoudingsAutorisatie(bijhoudendePartijX,
                    bijhVerantwoordelijkeVoorafY, bijhVerantwoordelijkeAchterafY, dummyActie, null);

        assertEquals(BijhoudingsAutorisatieResultaat.BIJHOUDING_VOORSTEL_TOEGESTAAN, resultaat);

        verleenAutorisatie(bijhoudendePartijX, bijhVerantwoordelijkeAchterafY, SoortBijhouding.AUTOMATISCH_FIATTEREN,
                           Verantwoordelijke.COLLEGE);

        resultaat =
            getBijhoudingsAutorisatieService().bepaalBijhoudingsAutorisatie(bijhoudendePartijX,
                    bijhVerantwoordelijkeVoorafY, bijhVerantwoordelijkeAchterafY, dummyActie, null);

        assertEquals(BijhoudingsAutorisatieResultaat.BIJHOUDING_TOEGESTAAN_OP_BASIS_AUT_FIATTERING_MANDAAT, resultaat);
    }

    @Test
    public void testBijhoudingDoorGemeenteXVerantwoordelijkeVoorafYAchterafX() {
        final Partij bijhoudendePartijX = initPartij(SoortPartij.GEMEENTE, 20L);
        final Partij bijhVerantwoordelijkeVoorafY = initPartij(SoortPartij.GEMEENTE, 31L);
        final Partij bijhVerantwoordelijkeAchterafX = bijhoudendePartijX;

        initWettelijkBepaaldeBijhoudingsAutorisaties(bijhoudendePartijX);

        final BijhoudingsAutorisatieResultaat resultaat =
            getBijhoudingsAutorisatieService().bepaalBijhoudingsAutorisatie(bijhoudendePartijX,
                    bijhVerantwoordelijkeVoorafY, bijhVerantwoordelijkeAchterafX, dummyActie, null);

        assertEquals(BijhoudingsAutorisatieResultaat.BIJHOUDING_TOEGESTAAN_OP_BASIS_WET_BRP, resultaat);
    }

    @Test
    public void testBijhoudingDoorGemeenteXVerantwoordelijkeVoorafYAchterafMinister() {
        final Partij bijhoudendePartijX = initPartij(SoortPartij.GEMEENTE, 25L);
        final Partij bijhVerantwoordelijkeVoorafY = initPartij(SoortPartij.GEMEENTE, 29L);
        final Partij bijhVerantwoordelijkeAchterafMinister = initPartijMinister();

        initWettelijkBepaaldeBijhoudingsAutorisaties(bijhoudendePartijX);

        final BijhoudingsAutorisatieResultaat resultaat =
            getBijhoudingsAutorisatieService().bepaalBijhoudingsAutorisatie(bijhoudendePartijX,
                    bijhVerantwoordelijkeVoorafY, bijhVerantwoordelijkeAchterafMinister, dummyActie, null);

        assertEquals(BijhoudingsAutorisatieResultaat.BIJHOUDING_VOORSTEL_TOEGESTAAN, resultaat);
    }

    @Test
    public void testBijhoudingDoorABOXAanMinisterVerantwoordelijkeVoorafMinisterAchterafMinister() {
        final Partij bijhoudendePartijABO = initPartij(SoortPartij.OVERHEIDSORGAAN, 92L);
        final Partij bijhVerantwoordelijkeVoorafMinister = initPartijMinister();
        final Partij bijhVerantwoordelijkeAchterafMinister = initPartijMinister();

        initWettelijkBepaaldeBijhoudingsAutorisaties(bijhoudendePartijABO);

        final BijhoudingsAutorisatieResultaat resultaat =
            getBijhoudingsAutorisatieService().bepaalBijhoudingsAutorisatie(bijhoudendePartijABO,
                    bijhVerantwoordelijkeVoorafMinister, bijhVerantwoordelijkeAchterafMinister, dummyActie, null);

        assertEquals(BijhoudingsAutorisatieResultaat.BIJHOUDING_VOORSTEL_TOEGESTAAN, resultaat);
    }

    @Test
    public void testBijhoudingDoorABOXAanMinisterVerantwoordelijkeVoorafGemeenteAchterafMinister() {
        final Partij bijhoudendePartijABO = initPartij(SoortPartij.OVERHEIDSORGAAN, 95L);
        final Partij bijhVerantwoordelijkeVoorafGemeente = initPartij(SoortPartij.GEMEENTE, 26L);
        final Partij bijhVerantwoordelijkeAchterafMinister = initPartijMinister();

        initWettelijkBepaaldeBijhoudingsAutorisaties(bijhoudendePartijABO);

        final BijhoudingsAutorisatieResultaat resultaat =
            getBijhoudingsAutorisatieService().bepaalBijhoudingsAutorisatie(bijhoudendePartijABO,
                    bijhVerantwoordelijkeVoorafGemeente, bijhVerantwoordelijkeAchterafMinister, dummyActie, null);

        assertEquals(BijhoudingsAutorisatieResultaat.BIJHOUDING_NIET_TOEGESTAAN, resultaat);
    }

    @Test
    public void testBijhoudingDoorABOXAanMinisterVerantwoordelijkeVoorafMinisterAchterafGemeente() {
        final Partij bijhoudendePartijABO = initPartij(SoortPartij.OVERHEIDSORGAAN, 25L);
        final Partij bijhVerantwoordelijkeVoorafMinister = initPartijMinister();
        final Partij bijhVerantwoordelijkeAchterafGemeente = initPartij(SoortPartij.GEMEENTE, 26L);

        initWettelijkBepaaldeBijhoudingsAutorisaties(bijhoudendePartijABO);

        final BijhoudingsAutorisatieResultaat resultaat =
            getBijhoudingsAutorisatieService().bepaalBijhoudingsAutorisatie(bijhoudendePartijABO,
                    bijhVerantwoordelijkeVoorafMinister, bijhVerantwoordelijkeAchterafGemeente, dummyActie, null);

        assertEquals(BijhoudingsAutorisatieResultaat.BIJHOUDING_NIET_TOEGESTAAN, resultaat);
    }
}
