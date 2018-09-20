/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/**
 *
 */
package nl.bzk.brp.preview.service;

import java.util.Arrays;
import java.util.Calendar;

import nl.bzk.brp.preview.dataaccess.BerichtenDao;
import nl.bzk.brp.preview.model.Adres;
import nl.bzk.brp.preview.model.Bericht;
import nl.bzk.brp.preview.model.BerichtKenmerken;
import nl.bzk.brp.preview.model.DashboardSettings;
import nl.bzk.brp.preview.model.Gemeente;
import nl.bzk.brp.preview.model.Melding;
import nl.bzk.brp.preview.model.MeldingSoort;
import nl.bzk.brp.preview.model.OndersteundeBijhoudingsTypes;
import nl.bzk.brp.preview.model.Persoon;
import nl.bzk.brp.preview.model.Plaats;
import nl.bzk.brp.preview.model.VerhuisBerichtRequest;
import nl.bzk.brp.preview.model.Verwerking;
import nl.bzk.brp.preview.model.VerwerkingStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;


public class VerhuisBerichtenServiceOpslaanTest {

    private BerichtenService  service;

    private DashboardSettings settings;

    @Mock
    private BerichtenDao      berichtenDao;

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

        request.setKenmerken(new BerichtKenmerken(new Gemeente("Rotterdam"), "CentricBZM"));

        Calendar verzondenOp = Calendar.getInstance();
        verzondenOp.clear();
        verzondenOp.set(2004, Calendar.OCTOBER, 19, 10, 23, 54);
        request.setVerwerking(new Verwerking(verzondenOp, VerwerkingStatus.GESLAAGD));

        Persoon persoon = new Persoon(29, "Bas", "de", "Cuykelaer");
        request.setPersoon(persoon);

        request.setOudAdres(new Adres(new Gemeente("Hellevoetsluis")));

        Adres nieuwAdres = new Adres();
        nieuwAdres.setGemeente(new Gemeente("Hellevoetsluis"));
        nieuwAdres.setStraat("Grote Markt");
        nieuwAdres.setHuisnummer("34");
        nieuwAdres.setPlaats(new Plaats("Nieuwenhoorn"));
        nieuwAdres.setDatumAanvangAdreshouding(20120620);
        request.setNieuwAdres(nieuwAdres);

        // test
        service.opslaan(request);

        ArgumentCaptor<Bericht> argument = ArgumentCaptor.forClass(Bericht.class);
        Mockito.verify(berichtenDao).opslaan(argument.capture());
        Assert.assertEquals(0, argument.getValue().getAantalMeldingen());
        Assert.assertEquals("Rotterdam: verhuizing binnen gemeente van Bas de Cuykelaer.", argument.getValue()
                .getBericht());
        Assert.assertEquals("Adres per 20-06-2012: Grote Markt 34 (Nieuwenhoorn).", argument.getValue()
                .getBerichtDetails());
        Assert.assertEquals("CentricBZM", argument.getValue().getBurgerZakenModule());
        Assert.assertEquals("Rotterdam", argument.getValue().getPartij());
        Assert.assertEquals(OndersteundeBijhoudingsTypes.VERHUIZING, argument.getValue().getSoortBijhouding());
        Assert.assertFalse(argument.getValue().isPrevalidatie());
        Assert.assertSame(verzondenOp, argument.getValue().getVerzondenOp());
        Assert.assertEquals(1, argument.getValue().getBurgerservicenummers().size());
        Assert.assertEquals(29, argument.getValue().getBurgerservicenummers().get(0).intValue());
    }

    @Test
    public void testOpslaanVerhuizingNaarAndereGemeente() {

        VerhuisBerichtRequest request = new VerhuisBerichtRequest();

        request.setKenmerken(new BerichtKenmerken(new Gemeente("Groningen"), "CentricBZM"));

        request.setVerwerking(new Verwerking(Calendar.getInstance(), VerwerkingStatus.GESLAAGD));

        request.setPersoon(new Persoon(159, "Maria", "Ophuijsen"));

        request.setOudAdres(new Adres(new Gemeente("Amersfoort")));

        Adres nieuwAdres = new Adres();
        nieuwAdres.setGemeente(new Gemeente("Assen"));
        nieuwAdres.setStraat("Herestraat");
        nieuwAdres.setHuisnummer("1");
        nieuwAdres.setHuisnummertoevoeging("A");
        nieuwAdres.setPlaats(new Plaats("Assen"));
        nieuwAdres.setDatumAanvangAdreshouding(20120600);
        request.setNieuwAdres(nieuwAdres);

        // test
        service.opslaan(request);

        ArgumentCaptor<Bericht> argument = ArgumentCaptor.forClass(Bericht.class);
        Mockito.verify(berichtenDao).opslaan(argument.capture());
        Assert.assertEquals(0, argument.getValue().getAantalMeldingen());
        Assert.assertEquals("Groningen: verhuizing vanuit Amersfoort van Maria Ophuijsen.", argument.getValue()
                .getBericht());
        Assert.assertEquals("Adres per 00-06-2012: Herestraat 1 A (Assen).", argument.getValue().getBerichtDetails());
        Assert.assertEquals("CentricBZM", argument.getValue().getBurgerZakenModule());
        Assert.assertEquals("Groningen", argument.getValue().getPartij());
        Assert.assertEquals(OndersteundeBijhoudingsTypes.VERHUIZING, argument.getValue().getSoortBijhouding());
        Assert.assertFalse(argument.getValue().isPrevalidatie());
    }

    @Test
    public void testOpslaanVerhuizingMet1Melding() {

        VerhuisBerichtRequest request = new VerhuisBerichtRequest();

        request.setKenmerken(new BerichtKenmerken(new Gemeente("Groningen"), "CentricBZM"));

        request.setVerwerking(new Verwerking(Calendar.getInstance(), VerwerkingStatus.GESLAAGD));

        request.setMeldingen(Arrays.asList(new Melding(MeldingSoort.WAARSCHUWING,
                "Er zijn al mensen ingeschreven op het nieuwe adres.")));

        request.setPersoon(new Persoon(159, "Hendrika", "de", "Roos"));

        request.setOudAdres(new Adres(new Gemeente("Groningen")));

        Adres nieuwAdres = new Adres();
        nieuwAdres.setGemeente(new Gemeente("Groningen"));
        nieuwAdres.setStraat("Akerkhof");
        nieuwAdres.setHuisnummer("2");
        nieuwAdres.setPlaats(new Plaats("Groningen"));
        nieuwAdres.setDatumAanvangAdreshouding(20120620);
        request.setNieuwAdres(nieuwAdres);

        // test
        service.opslaan(request);

        ArgumentCaptor<Bericht> argument = ArgumentCaptor.forClass(Bericht.class);
        Mockito.verify(berichtenDao).opslaan(argument.capture());
        Assert.assertEquals(1, argument.getValue().getAantalMeldingen());
        Assert.assertEquals("Groningen: verhuizing binnen gemeente van Hendrika de Roos. 1 melding.", argument
                .getValue().getBericht());
        Assert.assertEquals("Adres per 20-06-2012: Akerkhof 2 (Groningen).\nWaarschuwing: "
            + "Er zijn al mensen ingeschreven op het nieuwe adres.", argument.getValue().getBerichtDetails());
        Assert.assertEquals("CentricBZM", argument.getValue().getBurgerZakenModule());
        Assert.assertEquals("Groningen", argument.getValue().getPartij());
        Assert.assertEquals(OndersteundeBijhoudingsTypes.VERHUIZING, argument.getValue().getSoortBijhouding());
        Assert.assertFalse(argument.getValue().isPrevalidatie());
    }

    @Test
    public void testOpslaanVerhuizingMet2Meldingen() {

        VerhuisBerichtRequest request = new VerhuisBerichtRequest();

        request.setKenmerken(new BerichtKenmerken(new Gemeente("Groningen"), "CentricBZM"));

        request.setVerwerking(new Verwerking(Calendar.getInstance(), VerwerkingStatus.GESLAAGD));

        request.setMeldingen(Arrays.asList(new Melding(MeldingSoort.FOUT,
                "De bijhouding van deze persoon is opgeschort."), new Melding(MeldingSoort.WAARSCHUWING,
                    "Er zijn al mensen ingeschreven op het nieuwe adres.")));

        request.setPersoon(new Persoon(543, "Hendrika", "de", "Roos"));

        request.setOudAdres(new Adres(new Gemeente("Groningen")));

        Adres nieuwAdres = new Adres();
        nieuwAdres.setGemeente(new Gemeente("Groningen"));
        nieuwAdres.setStraat("Akerkhof");
        nieuwAdres.setHuisnummer("2");
        nieuwAdres.setPlaats(new Plaats("Groningen"));
        nieuwAdres.setDatumAanvangAdreshouding(20120620);
        request.setNieuwAdres(nieuwAdres);

        // test
        service.opslaan(request);

        ArgumentCaptor<Bericht> argument = ArgumentCaptor.forClass(Bericht.class);
        Mockito.verify(berichtenDao).opslaan(argument.capture());
        Assert.assertEquals(2, argument.getValue().getAantalMeldingen());
        Assert.assertEquals("Groningen: verhuizing binnen gemeente van Hendrika de Roos. 2 meldingen.", argument
                .getValue().getBericht());
        Assert.assertEquals("Adres per 20-06-2012: Akerkhof 2 (Groningen)."
            + "\nFout: De bijhouding van deze persoon is opgeschort."
            + "\nWaarschuwing: Er zijn al mensen ingeschreven op het nieuwe adres.", argument.getValue()
                .getBerichtDetails());
        Assert.assertEquals("CentricBZM", argument.getValue().getBurgerZakenModule());
        Assert.assertEquals("Groningen", argument.getValue().getPartij());
        Assert.assertEquals(OndersteundeBijhoudingsTypes.VERHUIZING, argument.getValue().getSoortBijhouding());
        Assert.assertFalse(argument.getValue().isPrevalidatie());
    }

    @Test
    public void testOpslaanVerhuizingPrevalidateMet1Melding() {

        VerhuisBerichtRequest request = new VerhuisBerichtRequest();

        BerichtKenmerken kenmerken = new BerichtKenmerken(new Gemeente("Groningen"), "CentricBZM");
        kenmerken.setPrevalidatie(true);
        request.setKenmerken(kenmerken);

        request.setVerwerking(new Verwerking(Calendar.getInstance(), VerwerkingStatus.GESLAAGD));

        request.setMeldingen(Arrays.asList(new Melding(MeldingSoort.WAARSCHUWING,
                "Er zijn al mensen ingeschreven op het nieuwe adres.")));

        request.setPersoon(new Persoon(210, "Hendrika", "de", "Roos"));

        request.setOudAdres(new Adres(new Gemeente("Groningen")));

        Adres nieuwAdres = new Adres();
        nieuwAdres.setGemeente(new Gemeente("Groningen"));
        nieuwAdres.setStraat("Akerkhof");
        nieuwAdres.setHuisnummer("2");
        nieuwAdres.setPlaats(new Plaats("Groningen"));
        nieuwAdres.setDatumAanvangAdreshouding(20120620);
        request.setNieuwAdres(nieuwAdres);

        // test
        service.opslaan(request);

        ArgumentCaptor<Bericht> argument = ArgumentCaptor.forClass(Bericht.class);
        Mockito.verify(berichtenDao).opslaan(argument.capture());
        Assert.assertEquals(1, argument.getValue().getAantalMeldingen());
        Assert.assertEquals("Groningen: prevalidatie verhuizing binnen gemeente van Hendrika de Roos. 1 melding.",
                argument.getValue().getBericht());
        Assert.assertEquals("Adres per 20-06-2012: Akerkhof 2 (Groningen).\nWaarschuwing: "
            + "Er zijn al mensen ingeschreven op het nieuwe adres.", argument.getValue().getBerichtDetails());
        Assert.assertEquals("CentricBZM", argument.getValue().getBurgerZakenModule());
        Assert.assertEquals("Groningen", argument.getValue().getPartij());
        Assert.assertEquals(OndersteundeBijhoudingsTypes.VERHUIZING, argument.getValue().getSoortBijhouding());
        Assert.assertTrue(argument.getValue().isPrevalidatie());
    }

    @Test
    public void testOpslaanVerhuizingNietSuccesvolMet1Melding() {

        VerhuisBerichtRequest request = new VerhuisBerichtRequest();

        request.setKenmerken(new BerichtKenmerken(new Gemeente("Groningen"), "CentricBZM"));

        request.setVerwerking(new Verwerking(Calendar.getInstance(), VerwerkingStatus.MISLUKT));

        request.setMeldingen(Arrays.asList(new Melding(MeldingSoort.FOUT,
                "De bijhouding van deze persoon is opgeschort.")));

        request.setPersoon(new Persoon(297, "Jan", "Moes"));

        request.setOudAdres(new Adres(new Gemeente("Groningen")));

        Adres nieuwAdres = new Adres();
        nieuwAdres.setGemeente(new Gemeente("Groningen"));
        nieuwAdres.setStraat("Akerkhof");
        nieuwAdres.setHuisnummer("2");
        nieuwAdres.setPlaats(new Plaats("Groningen"));
        nieuwAdres.setDatumAanvangAdreshouding(20120620);
        request.setNieuwAdres(nieuwAdres);

        // test
        service.opslaan(request);

        ArgumentCaptor<Bericht> argument = ArgumentCaptor.forClass(Bericht.class);
        Mockito.verify(berichtenDao).opslaan(argument.capture());
        Assert.assertEquals(1, argument.getValue().getAantalMeldingen());
        Assert.assertEquals("Groningen: verhuizing binnen gemeente van Jan Moes. 1 melding.", argument.getValue()
                .getBericht());
        Assert.assertEquals("Fout: De bijhouding van deze persoon is opgeschort.", argument.getValue()
                .getBerichtDetails());
        Assert.assertEquals("CentricBZM", argument.getValue().getBurgerZakenModule());
        Assert.assertEquals("Groningen", argument.getValue().getPartij());
        Assert.assertEquals(OndersteundeBijhoudingsTypes.VERHUIZING, argument.getValue().getSoortBijhouding());
        Assert.assertFalse(argument.getValue().isPrevalidatie());
    }

    @Test
    public void testOpslaanVerhuizingRudimentairBericht() {

        VerhuisBerichtRequest request = new VerhuisBerichtRequest();

        request.setKenmerken(new BerichtKenmerken(new Gemeente("Groningen"), "CentricBZM"));

        request.setVerwerking(new Verwerking(Calendar.getInstance(), VerwerkingStatus.GESLAAGD));

        request.setPersoon(new Persoon(80, "Ophuijsen"));

        Adres oudAdres = new Adres();
        Gemeente gemeente = new Gemeente("Amersfoort");
        oudAdres.setGemeente(gemeente);
        request.setOudAdres(oudAdres);

        Adres nieuwAdres = new Adres();
        nieuwAdres.setGemeente(new Gemeente("Assen"));
        nieuwAdres.setStraat("Herestraat");
        nieuwAdres.setDatumAanvangAdreshouding(20120600);
        request.setNieuwAdres(nieuwAdres);

        // test
        service.opslaan(request);

        ArgumentCaptor<Bericht> argument = ArgumentCaptor.forClass(Bericht.class);
        Mockito.verify(berichtenDao).opslaan(argument.capture());
        Assert.assertEquals(0, argument.getValue().getAantalMeldingen());
        Assert.assertEquals("Groningen: verhuizing vanuit Amersfoort van persoon Ophuijsen.", argument.getValue()
                .getBericht());
        Assert.assertEquals("Adres per 00-06-2012: Herestraat nr. onbekend.", argument.getValue().getBerichtDetails());
        Assert.assertEquals("CentricBZM", argument.getValue().getBurgerZakenModule());
        Assert.assertEquals("Groningen", argument.getValue().getPartij());
        Assert.assertEquals(OndersteundeBijhoudingsTypes.VERHUIZING, argument.getValue().getSoortBijhouding());
        Assert.assertFalse(argument.getValue().isPrevalidatie());
    }

}
