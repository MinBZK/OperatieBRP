/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.service.impl;

import static junit.framework.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.bevraging.domein.repository.PartijRepository;
import nl.bzk.brp.bijhouding.business.bijhoudingsautorisatie.BijhoudingsAutorisatieResultaat;
import nl.bzk.brp.bijhouding.domein.repository.BijhoudingsAutorisatieRepository;
import nl.bzk.brp.domein.DomeinObjectFactory;
import nl.bzk.brp.domein.PersistentDomeinObjectFactory;
import nl.bzk.brp.domein.autaut.Autorisatiebesluit;
import nl.bzk.brp.domein.autaut.BeperkingPopulatie;
import nl.bzk.brp.domein.autaut.Bijhoudingsautorisatie;
import nl.bzk.brp.domein.autaut.Bijhoudingssituatie;
import nl.bzk.brp.domein.autaut.SoortAutorisatiebesluit;
import nl.bzk.brp.domein.autaut.SoortBijhouding;
import nl.bzk.brp.domein.kern.CategorieSoortActie;
import nl.bzk.brp.domein.kern.CategorieSoortDocument;
import nl.bzk.brp.domein.kern.Partij;
import nl.bzk.brp.domein.kern.SoortActie;
import nl.bzk.brp.domein.kern.SoortDocument;
import nl.bzk.brp.domein.kern.SoortPartij;
import nl.bzk.brp.domein.kern.Verantwoordelijke;
import nl.bzk.brp.util.DatumUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;


public class BijhoudingsAutorisatieServiceImplTest {

    private BijhoudingsAutorisatieServiceImpl bijhoudingsAutorisatieService;

    private SoortActie                        dummyActie;

    @Mock
    private BijhoudingsAutorisatieRepository  bijhoudingsAutorisatieRepository;

    private final DomeinObjectFactory               domeinObjectFactory = new PersistentDomeinObjectFactory();

    @Test
    public void testVerleendeAutorisatieDoorGemeenteXAanY() {
        final Partij bijhoudendePartijY = initPartij(SoortPartij.GEMEENTE, 10);
        final Partij bijhoudingsVerantwoordelijkeVoorAfY = bijhoudendePartijY;
        final Partij bijhoudingsVerantwoordelijkeAchterX = initPartij(SoortPartij.GEMEENTE, 11);

        initWettelijkBepaaldeBijhoudingsAutorisaties(bijhoudendePartijY);

        // Wettelijk mag alleen een voorstel gedaan worden:
        BijhoudingsAutorisatieResultaat resultaat =
            bijhoudingsAutorisatieService.bepaalBijhoudingsAutorisatie(bijhoudendePartijY,
                    bijhoudingsVerantwoordelijkeVoorAfY, bijhoudingsVerantwoordelijkeAchterX, dummyActie, null);

        assertEquals(BijhoudingsAutorisatieResultaat.BIJHOUDING_VOORSTEL_TOEGESTAAN, resultaat);

        // De autorisatie van X aan Y om toch te mogen bijhouden:
        verleenAutorisatie(bijhoudendePartijY, bijhoudingsVerantwoordelijkeAchterX, SoortBijhouding.BIJHOUDEN,
                Verantwoordelijke.COLLEGE_VAN_BURGEMEESTER_EN_WETHOUDERS);

        resultaat =
            bijhoudingsAutorisatieService.bepaalBijhoudingsAutorisatie(bijhoudendePartijY,
                    bijhoudingsVerantwoordelijkeVoorAfY, bijhoudingsVerantwoordelijkeAchterX, dummyActie, null);

        assertEquals(BijhoudingsAutorisatieResultaat.BIJHOUDING_TOEGESTAAN_OP_BASIS_VERLEENDE_AUTORISATIE, resultaat);
    }

    @Test
    public void testMeerdereAutorisatiesAanwezig() {
        final Partij bijhoudendePartij = initPartij(SoortPartij.GEMEENTE, 10);
        final Partij bijhoudingsVerantwoordelijkeVoorAf = bijhoudendePartij;
        final Partij bijhoudingsVerantwoordelijkeAchterAf = initPartij(SoortPartij.GEMEENTE, 11);

        initWettelijkBepaaldeBijhoudingsAutorisaties(bijhoudendePartij);

        verleenAutorisatie(bijhoudendePartij, bijhoudingsVerantwoordelijkeAchterAf,
                SoortBijhouding.AUTOMATISCH_FIATTEREN, Verantwoordelijke.COLLEGE_VAN_BURGEMEESTER_EN_WETHOUDERS);

        final BijhoudingsAutorisatieResultaat resultaat =
            bijhoudingsAutorisatieService.bepaalBijhoudingsAutorisatie(bijhoudendePartij,
                    bijhoudingsVerantwoordelijkeVoorAf, bijhoudingsVerantwoordelijkeAchterAf, dummyActie, null);

        // Bijhoudingsvoorstellen zijn toegestaan op basis van de wet BRP, en autormatisch fiatteren is toegestaan
        // door de gemeeente. Het minst restrictieve moet het resultaat worden.
        assertEquals(BijhoudingsAutorisatieResultaat.BIJHOUDING_TOEGESTAAN_OP_BASIS_AUT_FIATTERING_MANDAAT, resultaat);
    }

    @Test
    public void testAutorisatiesMetBijhoudingsSituatieBeperkingOpBasisSoortDocument() {
        final Partij bijhoudendePartij = initPartij(SoortPartij.GEMEENTE, 10);
        final Partij bijhoudingsVerantwoordelijkeVoorAf = bijhoudendePartij;
        final Partij bijhoudingsVerantwoordelijkeAchterAf = initPartij(SoortPartij.GEMEENTE, 11);

        initWettelijkBepaaldeBijhoudingsAutorisaties(bijhoudendePartij);

        Bijhoudingsautorisatie autorisatie =
            verleenAutorisatie(bijhoudendePartij, bijhoudingsVerantwoordelijkeAchterAf,
                    SoortBijhouding.AUTOMATISCH_FIATTEREN, Verantwoordelijke.COLLEGE_VAN_BURGEMEESTER_EN_WETHOUDERS);

        SoortActie soortActie = domeinObjectFactory.createSoortActie();
        soortActie.setID(1);
        soortActie.setNaam("Dummy actie");
        soortActie.setCategorieSoortActie(CategorieSoortActie.SIMPELE_BIJHOUDING);
        SoortDocument soortDocument = domeinObjectFactory.createSoortDocument();
        soortDocument.setID(1);
        soortDocument.setOmschrijving("Een soort document");
        soortDocument.setCategorieSoortDocument(CategorieSoortDocument.NEDERLANDSE_AKTE);

        voegBijhoudingsSituatieAanAutorisatie(soortActie, soortDocument, null, null, autorisatie);

        BijhoudingsAutorisatieResultaat resultaat =
            bijhoudingsAutorisatieService
                    .bepaalBijhoudingsAutorisatie(bijhoudendePartij, bijhoudingsVerantwoordelijkeVoorAf,
                            bijhoudingsVerantwoordelijkeAchterAf, soortActie, soortDocument);

        // Bijhoudingsvoorstellen zijn toegestaan op basis van de wet BRP, en autormatisch fiatteren is toegestaan
        // door de gemeeente. Het minst restrictieve moet het resultaat worden.
        assertEquals(BijhoudingsAutorisatieResultaat.BIJHOUDING_TOEGESTAAN_OP_BASIS_AUT_FIATTERING_MANDAAT, resultaat);

        // Bepaal het resultaat als een heel ander document wordt gebruikt
        SoortDocument soortDocument2 = domeinObjectFactory.createSoortDocument();
        soortDocument2.setID(2);
        soortDocument2.setOmschrijving("Een ander document");
        soortDocument2.setCategorieSoortDocument(CategorieSoortDocument.NEDERLANDSE_AKTE);
        resultaat =
            bijhoudingsAutorisatieService.bepaalBijhoudingsAutorisatie(bijhoudendePartij,
                    bijhoudingsVerantwoordelijkeVoorAf, bijhoudingsVerantwoordelijkeAchterAf, soortActie,
                    soortDocument2);

        assertEquals(BijhoudingsAutorisatieResultaat.BIJHOUDING_VOORSTEL_TOEGESTAAN, resultaat);
    }

    @Test
    public void testAutorisatiesMetBijhoudingsSituatieBeperkingOpBasisCategorieDocument() {
        final Partij bijhoudendePartij = initPartij(SoortPartij.GEMEENTE, 10);
        final Partij bijhoudingsVerantwoordelijkeVoorAf = bijhoudendePartij;
        final Partij bijhoudingsVerantwoordelijkeAchterAf = initPartij(SoortPartij.GEMEENTE, 11);

        initWettelijkBepaaldeBijhoudingsAutorisaties(bijhoudendePartij);

        Bijhoudingsautorisatie autorisatie =
            verleenAutorisatie(bijhoudendePartij, bijhoudingsVerantwoordelijkeAchterAf,
                    SoortBijhouding.AUTOMATISCH_FIATTEREN, Verantwoordelijke.COLLEGE_VAN_BURGEMEESTER_EN_WETHOUDERS);

        SoortActie soortActie = domeinObjectFactory.createSoortActie();
        soortActie.setID(1);
        soortActie.setNaam("Een actie");
        soortActie.setCategorieSoortActie(CategorieSoortActie.SIMPELE_BIJHOUDING);
        final CategorieSoortDocument categorieDocument = CategorieSoortDocument.NEDERLANDSE_AKTE;
        SoortDocument soortDocument = domeinObjectFactory.createSoortDocument();
        soortDocument.setID(1);
        soortDocument.setOmschrijving("Een soort document");
        soortDocument.setCategorieSoortDocument(categorieDocument);

        voegBijhoudingsSituatieAanAutorisatie(soortActie, null, null, categorieDocument, autorisatie);

        BijhoudingsAutorisatieResultaat resultaat =
            bijhoudingsAutorisatieService
                    .bepaalBijhoudingsAutorisatie(bijhoudendePartij, bijhoudingsVerantwoordelijkeVoorAf,
                            bijhoudingsVerantwoordelijkeAchterAf, soortActie, soortDocument);

        // Bijhoudingsvoorstellen zijn toegestaan op basis van de wet BRP, en autormatisch fiatteren is toegestaan
        // door de gemeeente. Het minst restrictieve moet het resultaat worden.
        assertEquals(BijhoudingsAutorisatieResultaat.BIJHOUDING_TOEGESTAAN_OP_BASIS_AUT_FIATTERING_MANDAAT, resultaat);

        // Bepaal het resultaat als een document wordt gebruikt van een andere categorie.
        SoortDocument soortDocument2 = domeinObjectFactory.createSoortDocument();
        soortDocument2.setID(2);
        soortDocument2.setOmschrijving("Een ander document");
        soortDocument2.setCategorieSoortDocument(CategorieSoortDocument.DUMMY);
        resultaat =
            bijhoudingsAutorisatieService.bepaalBijhoudingsAutorisatie(bijhoudendePartij,
                    bijhoudingsVerantwoordelijkeVoorAf, bijhoudingsVerantwoordelijkeAchterAf, soortActie,
                    soortDocument2);

        assertEquals(BijhoudingsAutorisatieResultaat.BIJHOUDING_VOORSTEL_TOEGESTAAN, resultaat);

        // Bepaal het resultaat als een heel andere actie wordt gedaan.
        SoortActie soortActie2 = domeinObjectFactory.createSoortActie();
        soortActie2.setID(2);
        soortActie2.setNaam("Een andere actie");
        soortActie2.setCategorieSoortActie(CategorieSoortActie.SIMPELE_BIJHOUDING);
        resultaat =
            bijhoudingsAutorisatieService.bepaalBijhoudingsAutorisatie(bijhoudendePartij,
                    bijhoudingsVerantwoordelijkeVoorAf, bijhoudingsVerantwoordelijkeAchterAf, soortActie2,
                    soortDocument2);

        // Automatisch fiatteren mag niet want er is een beperking voor soort actie.
        assertEquals(BijhoudingsAutorisatieResultaat.BIJHOUDING_VOORSTEL_TOEGESTAAN, resultaat);
    }

    protected Partij initPartij(final SoortPartij soort, final Integer id) {
        final Partij partij = domeinObjectFactory.createPartij();
        partij.setSoort(soort);
        partij.setID(id);
        return partij;
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        bijhoudingsAutorisatieService = new BijhoudingsAutorisatieServiceImpl();

        ReflectionTestUtils.setField(bijhoudingsAutorisatieService, "bijhoudingsAutorisatieRepository",
                bijhoudingsAutorisatieRepository);

        dummyActie = domeinObjectFactory.createSoortActie();
        dummyActie.setID(1);
        dummyActie.setNaam("Dummy actie");
        dummyActie.setCategorieSoortActie(CategorieSoortActie.SIMPELE_BIJHOUDING);
    }

    protected void initWettelijkBepaaldeBijhoudingsAutorisaties(final Partij bijhoudendePartij) {
        // Autorisatie besluit gebaseerd op de WET BRP:
        final Autorisatiebesluit autorisatieBesluit = domeinObjectFactory.createAutorisatiebesluit();
        autorisatieBesluit.setSoort(SoortAutorisatiebesluit.BIJHOUDINGSAUTORISATIE);
        autorisatieBesluit.setBesluittekst("WET BRP");
        autorisatieBesluit.setAutoriseerder(initPartijRegeringEnStatenGeneraal());

        // Bijhouden gemeenten:
        final Bijhoudingsautorisatie autorisatieBijhoudenGemeenten = domeinObjectFactory.createBijhoudingsautorisatie();
        autorisatieBijhoudenGemeenten.setBijhoudingsautorisatiebesluit(autorisatieBesluit);
        autorisatieBijhoudenGemeenten.setSoortBijhouding(SoortBijhouding.BIJHOUDEN);
        autorisatieBijhoudenGemeenten.setGeautoriseerdeSoortPartij(SoortPartij.GEMEENTE);
        autorisatieBijhoudenGemeenten.setVerantwoordelijke(Verantwoordelijke.COLLEGE_VAN_BURGEMEESTER_EN_WETHOUDERS);
        autorisatieBijhoudenGemeenten.setBeperkingPopulatie(BeperkingPopulatie.ONTVANGER);

        // Bijhouden minister
        final Bijhoudingsautorisatie autorisatieBijhoudenMinister = domeinObjectFactory.createBijhoudingsautorisatie();
        autorisatieBijhoudenMinister.setBijhoudingsautorisatiebesluit(autorisatieBesluit);
        autorisatieBijhoudenMinister.setSoortBijhouding(SoortBijhouding.BIJHOUDEN);
        autorisatieBijhoudenMinister.setGeautoriseerdePartij(initPartijMinister());
        autorisatieBijhoudenMinister.setVerantwoordelijke(Verantwoordelijke.MINISTER);

        // Bijhoudingsvoorstellen Aangewezen Bestuursorganen (ABO) aan minister.
        final Bijhoudingsautorisatie autorisatieVoorstellenABOAanMinister =
            domeinObjectFactory.createBijhoudingsautorisatie();
        autorisatieVoorstellenABOAanMinister.setBijhoudingsautorisatiebesluit(autorisatieBesluit);
        autorisatieVoorstellenABOAanMinister.setSoortBijhouding(SoortBijhouding.BIJHOUDINGSVOORSTEL_DOEN);
        autorisatieVoorstellenABOAanMinister.setGeautoriseerdeSoortPartij(SoortPartij.OVERHEIDSORGAAN);
        autorisatieVoorstellenABOAanMinister.setVerantwoordelijke(Verantwoordelijke.MINISTER);

        // Bijhoudingsvoorstellen gemeenten onderling:
        final Bijhoudingsautorisatie autorisatieGemeentenVoorstellenOnderling =
            domeinObjectFactory.createBijhoudingsautorisatie();
        autorisatieGemeentenVoorstellenOnderling.setBijhoudingsautorisatiebesluit(autorisatieBesluit);
        autorisatieGemeentenVoorstellenOnderling.setSoortBijhouding(SoortBijhouding.BIJHOUDINGSVOORSTEL_DOEN);
        autorisatieGemeentenVoorstellenOnderling.setGeautoriseerdeSoortPartij(SoortPartij.GEMEENTE);
        autorisatieGemeentenVoorstellenOnderling
                .setVerantwoordelijke(Verantwoordelijke.COLLEGE_VAN_BURGEMEESTER_EN_WETHOUDERS);

        List<Bijhoudingsautorisatie> bijhGemeenten = new ArrayList<Bijhoudingsautorisatie>();
        bijhGemeenten.add(autorisatieBijhoudenGemeenten);
        List<Bijhoudingsautorisatie> bijhMinister = new ArrayList<Bijhoudingsautorisatie>();
        bijhMinister.add(autorisatieBijhoudenMinister);
        List<Bijhoudingsautorisatie> bijhVoorstellenABOs = new ArrayList<Bijhoudingsautorisatie>();
        bijhVoorstellenABOs.add(autorisatieVoorstellenABOAanMinister);
        List<Bijhoudingsautorisatie> bijhVoorstellenGemeenten = new ArrayList<Bijhoudingsautorisatie>();
        bijhVoorstellenGemeenten.add(autorisatieGemeentenVoorstellenOnderling);

        if (SoortPartij.GEMEENTE == bijhoudendePartij.getSoort()) {
            Mockito.when(
                    bijhoudingsAutorisatieRepository.zoekBijhoudingsAutorisaties(SoortBijhouding.BIJHOUDEN,
                            bijhoudendePartij, bijhoudendePartij.getSoort(), DatumUtil.getDatumVandaagInteger()))
                    .thenReturn(bijhGemeenten);

            Mockito.when(
                    bijhoudingsAutorisatieRepository.zoekBijhoudingsAutorisaties(
                            SoortBijhouding.BIJHOUDINGSVOORSTEL_DOEN, bijhoudendePartij, bijhoudendePartij.getSoort(),
                            DatumUtil.getDatumVandaagInteger())).thenReturn(bijhVoorstellenGemeenten);
        } else if (SoortPartij.VERTEGENWOORDIGER_REGERING == bijhoudendePartij.getSoort()) {
            Mockito.when(
                    bijhoudingsAutorisatieRepository.zoekBijhoudingsAutorisaties(SoortBijhouding.BIJHOUDEN,
                            bijhoudendePartij, bijhoudendePartij.getSoort(), DatumUtil.getDatumVandaagInteger()))
                    .thenReturn(bijhMinister);
        } else if (SoortPartij.OVERHEIDSORGAAN == bijhoudendePartij.getSoort()) {
            Mockito.when(
                    bijhoudingsAutorisatieRepository.zoekBijhoudingsAutorisaties(
                            SoortBijhouding.BIJHOUDINGSVOORSTEL_DOEN, bijhoudendePartij, bijhoudendePartij.getSoort(),
                            DatumUtil.getDatumVandaagInteger())).thenReturn(bijhVoorstellenABOs);
        }
    }

    protected Bijhoudingsautorisatie verleenAutorisatie(final Partij bijhoudendePartij,
            final Partij bijhVerantwoordelijke, final SoortBijhouding soortBijhouding,
            final Verantwoordelijke verantwoordelijke)
    {
        final Autorisatiebesluit autorisatieBesluit = domeinObjectFactory.createAutorisatiebesluit();
        autorisatieBesluit.setSoort(SoortAutorisatiebesluit.BIJHOUDINGSAUTORISATIE);
        autorisatieBesluit.setBesluittekst("Automatisch fiatteren");
        autorisatieBesluit.setAutoriseerder(bijhVerantwoordelijke);

        final Bijhoudingsautorisatie bijhoudingsAutorisatie = domeinObjectFactory.createBijhoudingsautorisatie();
        bijhoudingsAutorisatie.setBijhoudingsautorisatiebesluit(autorisatieBesluit);
        bijhoudingsAutorisatie.setSoortBijhouding(soortBijhouding);
        bijhoudingsAutorisatie.setVerantwoordelijke(verantwoordelijke);
        bijhoudingsAutorisatie.setGeautoriseerdePartij(bijhoudendePartij);
        // ReflectionTestUtils.setField(bijhoudingsAutorisatie, "beperkingPopulatie", BeperkingPopulatie.ONTVANGER);

        final List<Bijhoudingsautorisatie> autorisatieLijst = new ArrayList<Bijhoudingsautorisatie>();
        autorisatieLijst.add(bijhoudingsAutorisatie);

        Mockito.when(
                bijhoudingsAutorisatieRepository.zoekBijhoudingsAutorisaties(soortBijhouding, bijhoudendePartij,
                        bijhoudendePartij.getSoort(), DatumUtil.getDatumVandaagInteger())).thenReturn(autorisatieLijst);
        return bijhoudingsAutorisatie;

    }

    private void voegBijhoudingsSituatieAanAutorisatie(final SoortActie soortActie, final SoortDocument soortDocument,
            final CategorieSoortActie soortCategorieActie, final CategorieSoortDocument soortCategorieDocument,
            final Bijhoudingsautorisatie bijhoudingsAutorisatie)
    {
        final Bijhoudingssituatie bijhoudingsSituatie = domeinObjectFactory.createBijhoudingssituatie();
        bijhoudingsSituatie.setSoortActie(soortActie);
        bijhoudingsSituatie.setSoortActie(soortActie);
        bijhoudingsSituatie.setSoortDocument(soortDocument);
        bijhoudingsSituatie.setCategorieSoortActie(soortCategorieActie);
        bijhoudingsSituatie.setCategorieSoortDocument(soortCategorieDocument);
        bijhoudingsAutorisatie.addBijhoudingssituatie(bijhoudingsSituatie);
    }

    protected Partij initPartijRegeringEnStatenGeneraal() {
        final Partij minister = domeinObjectFactory.createPartij();
        minister.setSoort(SoortPartij.WETGEVER);
        ReflectionTestUtils.setField(minister, "id", PartijRepository.ID_PARTIJ_REGERING_EN_STATEN_GENERAAL);
        ReflectionTestUtils.setField(minister, "naam", "Regering en Staten-generaal");
        return minister;
    }

    protected Partij initPartijMinister() {
        final Partij minister = domeinObjectFactory.createPartij();
        minister.setSoort(SoortPartij.VERTEGENWOORDIGER_REGERING);
        ReflectionTestUtils.setField(minister, "id", PartijRepository.ID_PARTIJ_MINISTER);
        ReflectionTestUtils.setField(minister, "naam", "Minister van BZK");
        return minister;
    }

    protected BijhoudingsAutorisatieServiceImpl getBijhoudingsAutorisatieService() {
        return bijhoudingsAutorisatieService;
    }
}
