/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.service.impl;

import static junit.framework.Assert.assertEquals;

import nl.bzk.brp.bijhouding.business.bijhoudingsautorisatie.BijhoudingsAutorisatieResultaat;
import nl.bzk.brp.domein.DomeinObjectFactory;
import nl.bzk.brp.domein.PersistentDomeinObjectFactory;
import nl.bzk.brp.domein.autaut.SoortBijhouding;
import nl.bzk.brp.domein.kern.CategorieSoortActie;
import nl.bzk.brp.domein.kern.Partij;
import nl.bzk.brp.domein.kern.SoortActie;
import nl.bzk.brp.domein.kern.SoortPartij;
import nl.bzk.brp.domein.kern.Verantwoordelijke;
import org.junit.Before;
import org.junit.Test;


/**
 * De tests in deze class testen de bijhoudingsautorisatie die via de wet worden geregeld.
 */
public class BijhoudingsAutorisatieServiceWetBRPTest extends BijhoudingsAutorisatieServiceImplTest {

    private SoortActie          dummyActie;
    private final DomeinObjectFactory domeinObjectFactory = new PersistentDomeinObjectFactory();

    @Before
    public void init() {
        dummyActie = domeinObjectFactory.createSoortActie();
        dummyActie.setID(1);
        dummyActie.setNaam("Dummy actie");
        dummyActie.setCategorieSoortActie(CategorieSoortActie.SIMPELE_BIJHOUDING);
    }

    @Test
    public void testBijhoudingNietToegestaanZonderAutorisaties() {
        final Partij bijhoudendePartijX = initPartij(SoortPartij.GEMEENTE, 15);
        final Partij bijhVerantwoordelijkeVoorafX = bijhoudendePartijX;
        final Partij bijhVerantwoordelijkeAchterafY = initPartij(SoortPartij.GEMEENTE, 22);

        BijhoudingsAutorisatieResultaat resultaat =
            getBijhoudingsAutorisatieService().bepaalBijhoudingsAutorisatie(bijhoudendePartijX,
                    bijhVerantwoordelijkeVoorafX, bijhVerantwoordelijkeAchterafY, dummyActie, null);

        assertEquals(BijhoudingsAutorisatieResultaat.BIJHOUDING_NIET_TOEGESTAAN, resultaat);
    }

    @Test
    public void testBijhoudingDoorGemeenteXVerantwoordelijkeVoorafXAchterafY() {
        final Partij bijhoudendePartijX = initPartij(SoortPartij.GEMEENTE, 11);
        final Partij bijhVerantwoordelijkeVoorafX = bijhoudendePartijX;
        final Partij bijhVerantwoordelijkeAchterafY = initPartij(SoortPartij.GEMEENTE, 12);

        initWettelijkBepaaldeBijhoudingsAutorisaties(bijhoudendePartijX);

        BijhoudingsAutorisatieResultaat resultaat =
            getBijhoudingsAutorisatieService().bepaalBijhoudingsAutorisatie(bijhoudendePartijX,
                    bijhVerantwoordelijkeVoorafX, bijhVerantwoordelijkeAchterafY, dummyActie, null);

        assertEquals(BijhoudingsAutorisatieResultaat.BIJHOUDING_VOORSTEL_TOEGESTAAN, resultaat);

        verleenAutorisatie(bijhoudendePartijX, bijhVerantwoordelijkeAchterafY, SoortBijhouding.AUTOMATISCH_FIATTEREN,
                Verantwoordelijke.COLLEGE_VAN_BURGEMEESTER_EN_WETHOUDERS);

        resultaat =
            getBijhoudingsAutorisatieService().bepaalBijhoudingsAutorisatie(bijhoudendePartijX,
                    bijhVerantwoordelijkeVoorafX, bijhVerantwoordelijkeAchterafY, dummyActie, null);

        assertEquals(BijhoudingsAutorisatieResultaat.BIJHOUDING_TOEGESTAAN_OP_BASIS_AUT_FIATTERING_MANDAAT, resultaat);
    }

    @Test
    public void testBijhoudingDoorGemeenteXVerantwoordelijkeVoorafXAchterafX() {
        final Partij bijhoudendePartijX = initPartij(SoortPartij.GEMEENTE, 19);
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
        final Partij bijhoudendePartijX = initPartij(SoortPartij.GEMEENTE, 18);
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
        final Partij bijhoudendePartijX = initPartij(SoortPartij.GEMEENTE, 17);
        final Partij bijhVerantwoordelijkeVoorafMinister = initPartijMinister();
        final Partij bijhVerantwoordelijkeAchterafY = initPartij(SoortPartij.GEMEENTE, 27);

        initWettelijkBepaaldeBijhoudingsAutorisaties(bijhoudendePartijX);

        BijhoudingsAutorisatieResultaat resultaat =
            getBijhoudingsAutorisatieService().bepaalBijhoudingsAutorisatie(bijhoudendePartijX,
                    bijhVerantwoordelijkeVoorafMinister, bijhVerantwoordelijkeAchterafY, dummyActie, null);

        assertEquals(BijhoudingsAutorisatieResultaat.BIJHOUDING_VOORSTEL_TOEGESTAAN, resultaat);

        verleenAutorisatie(bijhoudendePartijX, bijhVerantwoordelijkeAchterafY, SoortBijhouding.AUTOMATISCH_FIATTEREN,
                Verantwoordelijke.COLLEGE_VAN_BURGEMEESTER_EN_WETHOUDERS);

        resultaat =
            getBijhoudingsAutorisatieService().bepaalBijhoudingsAutorisatie(bijhoudendePartijX,
                    bijhVerantwoordelijkeVoorafMinister, bijhVerantwoordelijkeAchterafY, dummyActie, null);

        assertEquals(BijhoudingsAutorisatieResultaat.BIJHOUDING_TOEGESTAAN_OP_BASIS_AUT_FIATTERING_MANDAAT, resultaat);
    }

    @Test
    public void testBijhoudingDoorGemeenteXVerantwoordelijkeVoorafMinisterAchterafX() {
        final Partij bijhoudendePartijX = initPartij(SoortPartij.GEMEENTE, 14);
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
        final Partij bijhoudendePartijX = initPartij(SoortPartij.GEMEENTE, 15);
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
        final Partij bijhoudendePartijX = initPartij(SoortPartij.GEMEENTE, 16);
        final Partij bijhVerantwoordelijkeVoorafY = initPartij(SoortPartij.GEMEENTE, 23);
        final Partij bijhVerantwoordelijkeAchterafY = bijhVerantwoordelijkeVoorafY;

        initWettelijkBepaaldeBijhoudingsAutorisaties(bijhoudendePartijX);

        BijhoudingsAutorisatieResultaat resultaat =
            getBijhoudingsAutorisatieService().bepaalBijhoudingsAutorisatie(bijhoudendePartijX,
                    bijhVerantwoordelijkeVoorafY, bijhVerantwoordelijkeAchterafY, dummyActie, null);

        assertEquals(BijhoudingsAutorisatieResultaat.BIJHOUDING_VOORSTEL_TOEGESTAAN, resultaat);

        verleenAutorisatie(bijhoudendePartijX, bijhVerantwoordelijkeAchterafY, SoortBijhouding.AUTOMATISCH_FIATTEREN,
                Verantwoordelijke.COLLEGE_VAN_BURGEMEESTER_EN_WETHOUDERS);

        resultaat =
            getBijhoudingsAutorisatieService().bepaalBijhoudingsAutorisatie(bijhoudendePartijX,
                    bijhVerantwoordelijkeVoorafY, bijhVerantwoordelijkeAchterafY, dummyActie, null);

        assertEquals(BijhoudingsAutorisatieResultaat.BIJHOUDING_TOEGESTAAN_OP_BASIS_AUT_FIATTERING_MANDAAT, resultaat);
    }

    @Test
    public void testBijhoudingDoorGemeenteXVerantwoordelijkeVoorafYAchterafX() {
        final Partij bijhoudendePartijX = initPartij(SoortPartij.GEMEENTE, 20);
        final Partij bijhVerantwoordelijkeVoorafY = initPartij(SoortPartij.GEMEENTE, 31);
        final Partij bijhVerantwoordelijkeAchterafX = bijhoudendePartijX;

        initWettelijkBepaaldeBijhoudingsAutorisaties(bijhoudendePartijX);

        final BijhoudingsAutorisatieResultaat resultaat =
            getBijhoudingsAutorisatieService().bepaalBijhoudingsAutorisatie(bijhoudendePartijX,
                    bijhVerantwoordelijkeVoorafY, bijhVerantwoordelijkeAchterafX, dummyActie, null);

        assertEquals(BijhoudingsAutorisatieResultaat.BIJHOUDING_TOEGESTAAN_OP_BASIS_WET_BRP, resultaat);
    }

    @Test
    public void testBijhoudingDoorGemeenteXVerantwoordelijkeVoorafYAchterafMinister() {
        final Partij bijhoudendePartijX = initPartij(SoortPartij.GEMEENTE, 25);
        final Partij bijhVerantwoordelijkeVoorafY = initPartij(SoortPartij.GEMEENTE, 29);
        final Partij bijhVerantwoordelijkeAchterafMinister = initPartijMinister();

        initWettelijkBepaaldeBijhoudingsAutorisaties(bijhoudendePartijX);

        final BijhoudingsAutorisatieResultaat resultaat =
            getBijhoudingsAutorisatieService().bepaalBijhoudingsAutorisatie(bijhoudendePartijX,
                    bijhVerantwoordelijkeVoorafY, bijhVerantwoordelijkeAchterafMinister, dummyActie, null);

        assertEquals(BijhoudingsAutorisatieResultaat.BIJHOUDING_VOORSTEL_TOEGESTAAN, resultaat);
    }

    @Test
    public void testBijhoudingDoorABOXAanMinisterVerantwoordelijkeVoorafMinisterAchterafMinister() {
        final Partij bijhoudendePartijABO = initPartij(SoortPartij.OVERHEIDSORGAAN, 92);
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
        final Partij bijhoudendePartijABO = initPartij(SoortPartij.OVERHEIDSORGAAN, 95);
        final Partij bijhVerantwoordelijkeVoorafGemeente = initPartij(SoortPartij.GEMEENTE, 26);
        final Partij bijhVerantwoordelijkeAchterafMinister = initPartijMinister();

        initWettelijkBepaaldeBijhoudingsAutorisaties(bijhoudendePartijABO);

        final BijhoudingsAutorisatieResultaat resultaat =
            getBijhoudingsAutorisatieService().bepaalBijhoudingsAutorisatie(bijhoudendePartijABO,
                    bijhVerantwoordelijkeVoorafGemeente, bijhVerantwoordelijkeAchterafMinister, dummyActie, null);

        assertEquals(BijhoudingsAutorisatieResultaat.BIJHOUDING_NIET_TOEGESTAAN, resultaat);
    }

    @Test
    public void testBijhoudingDoorABOXAanMinisterVerantwoordelijkeVoorafMinisterAchterafGemeente() {
        final Partij bijhoudendePartijABO = initPartij(SoortPartij.OVERHEIDSORGAAN, 25);
        final Partij bijhVerantwoordelijkeVoorafMinister = initPartijMinister();
        final Partij bijhVerantwoordelijkeAchterafGemeente = initPartij(SoortPartij.GEMEENTE, 26);

        initWettelijkBepaaldeBijhoudingsAutorisaties(bijhoudendePartijABO);

        final BijhoudingsAutorisatieResultaat resultaat =
            getBijhoudingsAutorisatieService().bepaalBijhoudingsAutorisatie(bijhoudendePartijABO,
                    bijhVerantwoordelijkeVoorafMinister, bijhVerantwoordelijkeAchterafGemeente, dummyActie, null);

        assertEquals(BijhoudingsAutorisatieResultaat.BIJHOUDING_NIET_TOEGESTAAN, resultaat);
    }
}
