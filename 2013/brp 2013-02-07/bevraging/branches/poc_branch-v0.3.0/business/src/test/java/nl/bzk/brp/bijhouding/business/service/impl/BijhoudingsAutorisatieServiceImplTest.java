/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.service.impl;

import static junit.framework.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.bzk.brp.bevraging.domein.Partij;
import nl.bzk.brp.bevraging.domein.SoortPartij;
import nl.bzk.brp.bevraging.domein.Verantwoordelijke;
import nl.bzk.brp.bevraging.domein.aut.AutorisatieBesluit;
import nl.bzk.brp.bevraging.domein.aut.SoortAutorisatieBesluit;
import nl.bzk.brp.bevraging.domein.repository.PartijRepository;
import nl.bzk.brp.bijhouding.domein.SoortActie;
import nl.bzk.brp.bijhouding.domein.SoortDocument;
import nl.bzk.brp.bijhouding.domein.aut.BeperkingPopulatie;
import nl.bzk.brp.bijhouding.domein.aut.BijhoudingsAutorisatie;
import nl.bzk.brp.bijhouding.domein.aut.BijhoudingsSituatie;
import nl.bzk.brp.bijhouding.domein.aut.SoortBijhouding;
import nl.bzk.brp.bijhouding.domein.repository.BijhoudingsAutorisatieRepository;
import nl.bzk.brp.bijhouding.business.bijhoudingsautorisatie.BijhoudingsAutorisatieResultaat;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;


public class BijhoudingsAutorisatieServiceImplTest {

    private BijhoudingsAutorisatieServiceImpl bijhoudingsAutorisatieService;

    private SoortActie dummyActie;

    @Mock
    private BijhoudingsAutorisatieRepository    bijhoudingsAutorisatieRepository;

    @Test
    public void testVerleendeAutorisatieDoorGemeenteXAanY() {
        final Partij bijhoudendePartijY = initPartij(SoortPartij.GEMEENTE, 10L);
        final Partij bijhoudingsVerantwoordelijkeVoorAfY = bijhoudendePartijY;
        final Partij bijhoudingsVerantwoordelijkeAchterX = initPartij(SoortPartij.GEMEENTE, 11L);

        initWettelijkBepaaldeBijhoudingsAutorisaties(bijhoudendePartijY);

        //Wettelijk mag alleen een voorstel gedaan worden:
        BijhoudingsAutorisatieResultaat resultaat =
            bijhoudingsAutorisatieService.bepaalBijhoudingsAutorisatie(bijhoudendePartijY,
                    bijhoudingsVerantwoordelijkeVoorAfY, bijhoudingsVerantwoordelijkeAchterX, dummyActie, null);

        assertEquals(BijhoudingsAutorisatieResultaat.BIJHOUDING_VOORSTEL_TOEGESTAAN, resultaat);

        //De autorisatie van X aan Y om toch te mogen bijhouden:
        verleenAutorisatie(bijhoudendePartijY, bijhoudingsVerantwoordelijkeAchterX, SoortBijhouding.BIJHOUDEN,
                           Verantwoordelijke.COLLEGE);

        resultaat =
            bijhoudingsAutorisatieService.bepaalBijhoudingsAutorisatie(bijhoudendePartijY,
                    bijhoudingsVerantwoordelijkeVoorAfY, bijhoudingsVerantwoordelijkeAchterX, dummyActie, null);

        assertEquals(BijhoudingsAutorisatieResultaat.BIJHOUDING_TOEGESTAAN_OP_BASIS_VERLEENDE_AUTORISATIE, resultaat);
    }

    @Test
    public void testMeerdereAutorisatiesAanwezig() {
        final Partij bijhoudendePartij = initPartij(SoortPartij.GEMEENTE, 10L);
        final Partij bijhoudingsVerantwoordelijkeVoorAf = bijhoudendePartij;
        final Partij bijhoudingsVerantwoordelijkeAchterAf = initPartij(SoortPartij.GEMEENTE, 11L);

        initWettelijkBepaaldeBijhoudingsAutorisaties(bijhoudendePartij);

        verleenAutorisatie(bijhoudendePartij, bijhoudingsVerantwoordelijkeAchterAf,
                           SoortBijhouding.AUTOMATISCH_FIATTEREN, Verantwoordelijke.COLLEGE);

        final BijhoudingsAutorisatieResultaat resultaat =
            bijhoudingsAutorisatieService.bepaalBijhoudingsAutorisatie(bijhoudendePartij,
                    bijhoudingsVerantwoordelijkeVoorAf, bijhoudingsVerantwoordelijkeAchterAf, dummyActie, null);

        // Bijhoudingsvoorstellen zijn toegestaan op basis van de wet BRP, en autormatisch fiatteren is toegestaan
        // door de gemeeente. Het minst restrictieve moet het resultaat worden.
        assertEquals(BijhoudingsAutorisatieResultaat.BIJHOUDING_TOEGESTAAN_OP_BASIS_AUT_FIATTERING_MANDAAT, resultaat);
    }

    @Test
    public void testAutorisatiesMetBijhoudingsSituatieBeperkingOpBasisSoortDocument() {
        final Partij bijhoudendePartij = initPartij(SoortPartij.GEMEENTE, 10L);
        final Partij bijhoudingsVerantwoordelijkeVoorAf = bijhoudendePartij;
        final Partij bijhoudingsVerantwoordelijkeAchterAf = initPartij(SoortPartij.GEMEENTE, 11L);

        initWettelijkBepaaldeBijhoudingsAutorisaties(bijhoudendePartij);

        BijhoudingsAutorisatie autorisatie =
            verleenAutorisatie(bijhoudendePartij, bijhoudingsVerantwoordelijkeAchterAf,
                               SoortBijhouding.AUTOMATISCH_FIATTEREN, Verantwoordelijke.COLLEGE);

        SoortActie soortActie = new SoortActie(1L, "Een actie", 1L);
        SoortDocument soortDocument = new SoortDocument(1L, "Een soort document", 1L);

        voegBijhoudingsSituatieAanAutorisatie(soortActie, soortDocument, null, null, autorisatie);

        BijhoudingsAutorisatieResultaat resultaat =
            bijhoudingsAutorisatieService
                    .bepaalBijhoudingsAutorisatie(bijhoudendePartij, bijhoudingsVerantwoordelijkeVoorAf,
                            bijhoudingsVerantwoordelijkeAchterAf, soortActie, soortDocument);

        // Bijhoudingsvoorstellen zijn toegestaan op basis van de wet BRP, en autormatisch fiatteren is toegestaan
        // door de gemeeente. Het minst restrictieve moet het resultaat worden.
        assertEquals(BijhoudingsAutorisatieResultaat.BIJHOUDING_TOEGESTAAN_OP_BASIS_AUT_FIATTERING_MANDAAT, resultaat);

        // Bepaal het resultaat als een heel ander document wordt gebruikt
        SoortDocument soortDocument2 = new SoortDocument(2L, "Een ander document", 1L);
        resultaat =
            bijhoudingsAutorisatieService.bepaalBijhoudingsAutorisatie(bijhoudendePartij,
                    bijhoudingsVerantwoordelijkeVoorAf, bijhoudingsVerantwoordelijkeAchterAf, soortActie,
                    soortDocument2);

        assertEquals(BijhoudingsAutorisatieResultaat.BIJHOUDING_VOORSTEL_TOEGESTAAN, resultaat);
    }

    @Test
    public void testAutorisatiesMetBijhoudingsSituatieBeperkingOpBasisCategorieDocument() {
        final Partij bijhoudendePartij = initPartij(SoortPartij.GEMEENTE, 10L);
        final Partij bijhoudingsVerantwoordelijkeVoorAf = bijhoudendePartij;
        final Partij bijhoudingsVerantwoordelijkeAchterAf = initPartij(SoortPartij.GEMEENTE, 11L);

        initWettelijkBepaaldeBijhoudingsAutorisaties(bijhoudendePartij);

        BijhoudingsAutorisatie autorisatie =
            verleenAutorisatie(bijhoudendePartij, bijhoudingsVerantwoordelijkeAchterAf,
                               SoortBijhouding.AUTOMATISCH_FIATTEREN, Verantwoordelijke.COLLEGE);

        SoortActie soortActie = new SoortActie(1L, "Een actie", 1L);
        final Long categorieDocument = 1L;
        SoortDocument soortDocument = new SoortDocument(1L, "Een soort document", categorieDocument);

        voegBijhoudingsSituatieAanAutorisatie(soortActie, null, null, categorieDocument, autorisatie);

        BijhoudingsAutorisatieResultaat resultaat =
            bijhoudingsAutorisatieService
                    .bepaalBijhoudingsAutorisatie(bijhoudendePartij, bijhoudingsVerantwoordelijkeVoorAf,
                            bijhoudingsVerantwoordelijkeAchterAf, soortActie, soortDocument);

        // Bijhoudingsvoorstellen zijn toegestaan op basis van de wet BRP, en autormatisch fiatteren is toegestaan
        // door de gemeeente. Het minst restrictieve moet het resultaat worden.
        assertEquals(BijhoudingsAutorisatieResultaat.BIJHOUDING_TOEGESTAAN_OP_BASIS_AUT_FIATTERING_MANDAAT, resultaat);

        // Bepaal het resultaat als een document wordt gebruikt van een andere categorie.
        SoortDocument soortDocument2 = new SoortDocument(2L, "Een ander document", 2L);
        resultaat =
            bijhoudingsAutorisatieService.bepaalBijhoudingsAutorisatie(bijhoudendePartij,
                    bijhoudingsVerantwoordelijkeVoorAf, bijhoudingsVerantwoordelijkeAchterAf, soortActie,
                    soortDocument2);

        assertEquals(BijhoudingsAutorisatieResultaat.BIJHOUDING_VOORSTEL_TOEGESTAAN, resultaat);

        // Bepaal het resultaat als een heel andere actie wordt gedaan.
        SoortActie soortActie2 = new SoortActie(2L, "Een andere actie", 1L);
        resultaat =
            bijhoudingsAutorisatieService.bepaalBijhoudingsAutorisatie(bijhoudendePartij,
                    bijhoudingsVerantwoordelijkeVoorAf, bijhoudingsVerantwoordelijkeAchterAf, soortActie2,
                    soortDocument2);

        //Automatisch fiatteren mag niet want er is een beperking voor soort actie.
        assertEquals(BijhoudingsAutorisatieResultaat.BIJHOUDING_VOORSTEL_TOEGESTAAN, resultaat);
    }

    protected Partij initPartij(final SoortPartij soort, final Long id) {
        final Partij partij = new Partij(soort);
        ReflectionTestUtils.setField(partij, "id", id);
        return partij;
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        bijhoudingsAutorisatieService = new BijhoudingsAutorisatieServiceImpl();

        ReflectionTestUtils.setField(bijhoudingsAutorisatieService, "bijhoudingsAutorisatieRepository",
                bijhoudingsAutorisatieRepository);

        dummyActie = new SoortActie(1L, "Dummy", 1L);
    }

    protected void initWettelijkBepaaldeBijhoudingsAutorisaties(final Partij bijhoudendePartij) {
        // Autorisatie besluit gebaseerd op de WET BRP:
        final AutorisatieBesluit autorisatieBesluit =
            new AutorisatieBesluit(SoortAutorisatieBesluit.BIJHOUDINGSAUTORISATIE, "WET BRP",
                    initPartijRegeringEnStatenGeneraal());

        // Bijhouden gemeenten:
        final BijhoudingsAutorisatie autorisatieBijhoudenGemeenten = new BijhoudingsAutorisatie();
        ReflectionTestUtils.setField(autorisatieBijhoudenGemeenten, "autorisatieBesluit", autorisatieBesluit);
        ReflectionTestUtils.setField(autorisatieBijhoudenGemeenten, "soortBijhouding", SoortBijhouding.BIJHOUDEN);
        ReflectionTestUtils.setField(autorisatieBijhoudenGemeenten, "geautoriseerdeSoortPartij", SoortPartij.GEMEENTE);
        ReflectionTestUtils.setField(autorisatieBijhoudenGemeenten, "verantwoordelijke", Verantwoordelijke.COLLEGE);
        ReflectionTestUtils.setField(autorisatieBijhoudenGemeenten, "beperkingPopulatie", BeperkingPopulatie.ONTVANGER);

        // Bijhouden minister
        final BijhoudingsAutorisatie autorisatieBijhoudenMinister = new BijhoudingsAutorisatie();
        ReflectionTestUtils.setField(autorisatieBijhoudenMinister, "autorisatieBesluit", autorisatieBesluit);
        ReflectionTestUtils.setField(autorisatieBijhoudenMinister, "soortBijhouding", SoortBijhouding.BIJHOUDEN);
        ReflectionTestUtils.setField(autorisatieBijhoudenMinister, "geautoriseerdePartij", initPartijMinister());
        ReflectionTestUtils.setField(autorisatieBijhoudenMinister, "verantwoordelijke", Verantwoordelijke.MINISTER);

        // Bijhoudingsvoorstellen Aangewezen Bestuursorganen (ABO) aan minister.
        final BijhoudingsAutorisatie autorisatieVoorstellenABOAanMinister = new BijhoudingsAutorisatie();
        ReflectionTestUtils.setField(autorisatieVoorstellenABOAanMinister, "autorisatieBesluit", autorisatieBesluit);
        ReflectionTestUtils.setField(autorisatieVoorstellenABOAanMinister, "soortBijhouding",
                SoortBijhouding.BIJHOUDINGSVOORSTEL_DOEN);
        ReflectionTestUtils.setField(autorisatieVoorstellenABOAanMinister, "geautoriseerdeSoortPartij",
                SoortPartij.OVERHEIDSORGAAN);
        ReflectionTestUtils.setField(autorisatieVoorstellenABOAanMinister, "verantwoordelijke",
                Verantwoordelijke.MINISTER);

        // Bijhoudingsvoorstellen gemeenten onderling:
        final BijhoudingsAutorisatie autorisatieGemeentenVoorstellenOnderling = new BijhoudingsAutorisatie();
        ReflectionTestUtils
                .setField(autorisatieGemeentenVoorstellenOnderling, "autorisatieBesluit", autorisatieBesluit);
        ReflectionTestUtils.setField(autorisatieGemeentenVoorstellenOnderling, "soortBijhouding",
                SoortBijhouding.BIJHOUDINGSVOORSTEL_DOEN);
        ReflectionTestUtils.setField(autorisatieGemeentenVoorstellenOnderling, "geautoriseerdeSoortPartij",
                SoortPartij.GEMEENTE);
        ReflectionTestUtils.setField(autorisatieGemeentenVoorstellenOnderling, "verantwoordelijke",
                Verantwoordelijke.COLLEGE);

        List<BijhoudingsAutorisatie> bijhGemeenten = new ArrayList<BijhoudingsAutorisatie>();
        bijhGemeenten.add(autorisatieBijhoudenGemeenten);
        List<BijhoudingsAutorisatie> bijhMinister = new ArrayList<BijhoudingsAutorisatie>();
        bijhMinister.add(autorisatieBijhoudenMinister);
        List<BijhoudingsAutorisatie> bijhVoorstellenABOs = new ArrayList<BijhoudingsAutorisatie>();
        bijhVoorstellenABOs.add(autorisatieVoorstellenABOAanMinister);
        List<BijhoudingsAutorisatie> bijhVoorstellenGemeenten = new ArrayList<BijhoudingsAutorisatie>();
        bijhVoorstellenGemeenten.add(autorisatieGemeentenVoorstellenOnderling);

        if (SoortPartij.GEMEENTE == bijhoudendePartij.getSoort()) {
            Mockito.when(
                    bijhoudingsAutorisatieRepository.zoekBijhoudingsAutorisaties(SoortBijhouding.BIJHOUDEN,
                            bijhoudendePartij, bijhoudendePartij.getSoort())).thenReturn(bijhGemeenten);

            Mockito.when(
                    bijhoudingsAutorisatieRepository.zoekBijhoudingsAutorisaties(
                            SoortBijhouding.BIJHOUDINGSVOORSTEL_DOEN, bijhoudendePartij, bijhoudendePartij.getSoort()))
                    .thenReturn(bijhVoorstellenGemeenten);
        } else if (SoortPartij.VERTEGENWOORDIGER_REGERING == bijhoudendePartij.getSoort()) {
            Mockito.when(
                    bijhoudingsAutorisatieRepository.zoekBijhoudingsAutorisaties(SoortBijhouding.BIJHOUDEN,
                            bijhoudendePartij, bijhoudendePartij.getSoort())).thenReturn(bijhMinister);
        } else if (SoortPartij.OVERHEIDSORGAAN == bijhoudendePartij.getSoort()) {
            Mockito.when(
                    bijhoudingsAutorisatieRepository.zoekBijhoudingsAutorisaties(
                            SoortBijhouding.BIJHOUDINGSVOORSTEL_DOEN, bijhoudendePartij, bijhoudendePartij.getSoort()))
                    .thenReturn(bijhVoorstellenABOs);
        }
    }

    protected BijhoudingsAutorisatie verleenAutorisatie(final Partij bijhoudendePartij,
            final Partij bijhVerantwoordelijke, final SoortBijhouding soortBijhouding,
            final Verantwoordelijke verantwoordelijke)
    {
        final AutorisatieBesluit autorisatieBesluit =
            new AutorisatieBesluit(SoortAutorisatieBesluit.BIJHOUDINGSAUTORISATIE, "Automatisch fiatteren",
                    bijhVerantwoordelijke);

        final BijhoudingsAutorisatie bijhoudingsAutorisatie = new BijhoudingsAutorisatie();
        ReflectionTestUtils.setField(bijhoudingsAutorisatie, "autorisatieBesluit", autorisatieBesluit);
        ReflectionTestUtils.setField(bijhoudingsAutorisatie, "soortBijhouding", soortBijhouding);
        ReflectionTestUtils.setField(bijhoudingsAutorisatie, "verantwoordelijke", verantwoordelijke);
        ReflectionTestUtils.setField(bijhoudingsAutorisatie, "geautoriseerdePartij", bijhoudendePartij);
        // ReflectionTestUtils.setField(bijhoudingsAutorisatie, "beperkingPopulatie", BeperkingPopulatie.ONTVANGER);

        final List<BijhoudingsAutorisatie> autorisatieLijst = new ArrayList<BijhoudingsAutorisatie>();
        autorisatieLijst.add(bijhoudingsAutorisatie);

        Mockito.when(
                bijhoudingsAutorisatieRepository.zoekBijhoudingsAutorisaties(soortBijhouding,
                        bijhoudendePartij, bijhoudendePartij.getSoort())).thenReturn(autorisatieLijst);
        return bijhoudingsAutorisatie;

    }

    private void voegBijhoudingsSituatieAanAutorisatie(final SoortActie soortActie, final SoortDocument soortDocument,
            final Long soortCategorieActie, final Long soortCategorieDocument,
            final BijhoudingsAutorisatie bijhoudingsAutorisatie)
    {
        final BijhoudingsSituatie bijhoudingsSituatie = new BijhoudingsSituatie();
        ReflectionTestUtils.setField(bijhoudingsSituatie, "soortActie", soortActie);
        ReflectionTestUtils.setField(bijhoudingsSituatie, "soortDocument", soortDocument);
        ReflectionTestUtils.setField(bijhoudingsSituatie, "categorieSrtActie", soortCategorieActie);
        ReflectionTestUtils.setField(bijhoudingsSituatie, "categorieSoortDocument", soortCategorieDocument);

        if (bijhoudingsAutorisatie.getBijhoudingsSituaties() == null) {
            Set<BijhoudingsSituatie> situaties = new HashSet<BijhoudingsSituatie>();
            ReflectionTestUtils.setField(bijhoudingsAutorisatie, "bijhoudingsSituaties", situaties);
        }

        bijhoudingsAutorisatie.getBijhoudingsSituaties().add(bijhoudingsSituatie);
    }

    protected Partij initPartijRegeringEnStatenGeneraal() {
        final Partij minister = new Partij(SoortPartij.WETGEVER);
        ReflectionTestUtils.setField(minister, "id", PartijRepository.ID_PARTIJ_REGERING_EN_STATEN_GENERAAL);
        ReflectionTestUtils.setField(minister, "naam", "Regering en Staten-generaal");
        return minister;
    }

    protected Partij initPartijMinister() {
        final Partij minister = new Partij(SoortPartij.VERTEGENWOORDIGER_REGERING);
        ReflectionTestUtils.setField(minister, "id", PartijRepository.ID_PARTIJ_MINISTER);
        ReflectionTestUtils.setField(minister, "naam", "Minister van BZK");
        return minister;
    }

    protected BijhoudingsAutorisatieServiceImpl getBijhoudingsAutorisatieService() {
        return bijhoudingsAutorisatieService;
    }
}
