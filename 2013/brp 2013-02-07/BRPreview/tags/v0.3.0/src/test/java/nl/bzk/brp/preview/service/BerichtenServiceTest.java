/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/**
 *
 */
package nl.bzk.brp.preview.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import nl.bzk.brp.preview.dataaccess.BerichtenDao;
import nl.bzk.brp.preview.model.Adres;
import nl.bzk.brp.preview.model.Bericht;
import nl.bzk.brp.preview.model.BerichtenResponse;
import nl.bzk.brp.preview.model.Bijhoudingsverzoek;
import nl.bzk.brp.preview.model.DashboardSettings;
import nl.bzk.brp.preview.model.Gemeente;
import nl.bzk.brp.preview.model.Geslachtsnaamcomponent;
import nl.bzk.brp.preview.model.OndersteundeBijhoudingsTypes;
import nl.bzk.brp.preview.model.Persoon;
import nl.bzk.brp.preview.model.VerhuisBerichtRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;


public class BerichtenServiceTest {

    private BerichtenService service;

    private DashboardSettings settings;

    @Mock
    private BerichtenDao berichtenDao;

    /**
     * Initialiseert de mocks en de service.
     */
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        service = new BerichtenServiceImpl();
        settings = new DashboardSettings();
        ReflectionTestUtils.setField(service, "settings", settings);
        ReflectionTestUtils.setField(service, "berichtenDao", berichtenDao);
    }

    @Test
    public void testOpslaanVerhuizingBinnenGemeente() {

        VerhuisBerichtRequest request = new VerhuisBerichtRequest();

        request.setBijhoudingsverzoek(new Bijhoudingsverzoek(new Gemeente("Groningen"), "CentricBZM"));

        Persoon persoon = new Persoon();
        persoon.setVoornamen(Arrays.asList("Bas"));
        persoon.setGeslachtsnaamcomponenten(Arrays.<Geslachtsnaamcomponent> asList(new Geslachtsnaamcomponent("de", "Cuykelaer")));
        Adres nieuwAdres = new Adres();
        nieuwAdres.setGemeente(new Gemeente("Groningen"));
        nieuwAdres.setStraat("Grote Markt");
        nieuwAdres.setHuisnummer("34");
        nieuwAdres.setDatumAanvangAdreshouding(20120620);
        persoon.setAdres(nieuwAdres);
        request.setPersoon(persoon);

        Adres oudAdres = new Adres();
        oudAdres.setGemeente(new Gemeente("Groningen"));
        request.setOudAdres(oudAdres);

        // test
        service.opslaan(request);

        ArgumentCaptor<Bericht> argument = ArgumentCaptor.forClass(Bericht.class);
        Mockito.verify(berichtenDao).opslaan(argument.capture());
        Assert.assertEquals(0, argument.getValue().getAantalMeldingen());
        Assert.assertEquals("Groningen: verhuizing binnen gemeente van Bas de Cuykelaer.", argument.getValue().getBericht());
        Assert.assertEquals("Adres per 20-06-2012: Grote Markt 34 (Groningen).", argument.getValue().getBerichtDetails());
        Assert.assertEquals("CentricBZM", argument.getValue().getBurgerZakenModule());
        Assert.assertEquals("Groningen", argument.getValue().getPartij());
        Assert.assertEquals(OndersteundeBijhoudingsTypes.VERHUIZING, argument.getValue().getSoortBijhouding());
    }

    @Test
    public void testOpslaanVerhuizingNaarAndereGemeente() {

        VerhuisBerichtRequest request = new VerhuisBerichtRequest();

        request.setBijhoudingsverzoek(new Bijhoudingsverzoek(new Gemeente("Groningen"), "CentricBZM"));

        Persoon persoon = new Persoon();
        persoon.setVoornamen(Arrays.asList("Maria"));
        persoon.setGeslachtsnaamcomponenten(Arrays.<Geslachtsnaamcomponent> asList(new Geslachtsnaamcomponent("Ophuijsen")));
        Adres nieuwAdres = new Adres();
        nieuwAdres.setGemeente(new Gemeente("Assen"));
        nieuwAdres.setStraat("Herestraat");
        nieuwAdres.setHuisnummer("1");
        nieuwAdres.setDatumAanvangAdreshouding(20120621);
        persoon.setAdres(nieuwAdres);
        request.setPersoon(persoon);

        Adres oudAdres = new Adres();
        oudAdres.setGemeente(new Gemeente("Amersfoort"));
        request.setOudAdres(oudAdres);

        // test
        service.opslaan(request);

        ArgumentCaptor<Bericht> argument = ArgumentCaptor.forClass(Bericht.class);
        Mockito.verify(berichtenDao).opslaan(argument.capture());
        Assert.assertEquals(0, argument.getValue().getAantalMeldingen());
        Assert.assertEquals("Groningen: verhuizing vanuit Amersfoort van Maria Ophuijsen.", argument.getValue().getBericht());
        Assert.assertEquals("Adres per 21-06-2012: Herestraat 1 (Assen).", argument.getValue().getBerichtDetails());
        Assert.assertEquals("CentricBZM", argument.getValue().getBurgerZakenModule());
        Assert.assertEquals("Groningen", argument.getValue().getPartij());
        Assert.assertEquals(OndersteundeBijhoudingsTypes.VERHUIZING, argument.getValue().getSoortBijhouding());
    }

    @Test
    public void testGetBerichtenResponse() {

        Mockito.when(berichtenDao.getAlleBerichten()).thenReturn(creeerDummyBerichten());

        // test
        BerichtenResponse response = service.getBerichtenResponse();

        List<Bericht> berichtenLijst = response.getBerichten();
        Assert.assertEquals(2L, berichtenLijst.size());
        Bericht[] berichten = berichtenLijst.toArray(new Bericht[berichtenLijst.size()]);

        Assert.assertEquals("Utrecht", berichten[0].getPartij());
        Assert.assertEquals("Rotterdam", berichten[1].getPartij());

    }

    private List<Bericht> creeerDummyBerichten() {
        List<Bericht> berichten = new ArrayList<Bericht>();
        Bericht bericht;

        bericht = new Bericht();
        bericht.setPartij("Utrecht");
        bericht.setBericht("Utrecht heeft een verhuizing geprevalideerd.");
        bericht.setBerichtDetails("Johan (BSN 111222333) heeft per 1 mei 2012 als adres:"
            + " Lange Vijverberg 11 (Den Haag).");
        bericht.setAantalMeldingen(0);
        bericht.setVerzondenOp(Calendar.getInstance());
        bericht.setSoortBijhouding(OndersteundeBijhoudingsTypes.VERHUIZING);
        bericht.setPrevalidatie(true);
        bericht.setBurgerZakenModule("CentricBZM");
        berichten.add(bericht);

        bericht = new Bericht();
        bericht.setPartij("Rotterdam");
        bericht.setBericht("Rotterdam heeft een geboorte geprevalideerd.");
        bericht.setBerichtDetails("Zie politierapport inschrijving door woongemeente Rotterdam"
            + " waarbij verwezen zal worden naar wereldvreemde moeder.");
        bericht.setAantalMeldingen(0);
        bericht.setVerzondenOp(Calendar.getInstance());
        bericht.setPrevalidatie(true);
        bericht.setSoortBijhouding(OndersteundeBijhoudingsTypes.GEBOORTE);
        bericht.setBurgerZakenModule("ProcuraBZM");
        berichten.add(bericht);

        return berichten;
    }

}
