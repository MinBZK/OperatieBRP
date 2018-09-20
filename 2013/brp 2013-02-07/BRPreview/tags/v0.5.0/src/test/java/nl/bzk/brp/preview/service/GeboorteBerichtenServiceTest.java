/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.service;

import java.util.Arrays;
import java.util.Calendar;

import nl.bzk.brp.preview.dataaccess.BerichtenDao;
import nl.bzk.brp.preview.model.Adres;
import nl.bzk.brp.preview.model.Bericht;
import nl.bzk.brp.preview.model.BerichtKenmerken;
import nl.bzk.brp.preview.model.DashboardSettings;
import nl.bzk.brp.preview.model.GeboorteBerichtRequest;
import nl.bzk.brp.preview.model.Gemeente;
import nl.bzk.brp.preview.model.Melding;
import nl.bzk.brp.preview.model.MeldingSoort;
import nl.bzk.brp.preview.model.OndersteundeBijhoudingsTypes;
import nl.bzk.brp.preview.model.Persoon;
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


public class GeboorteBerichtenServiceTest {

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
    public void testOpslaanGeboorte() {

        GeboorteBerichtRequest request = new GeboorteBerichtRequest();

        request.setKenmerken(new BerichtKenmerken(new Gemeente("Amsterdam"), "CentricBZM"));

        Calendar verzondenOp = Calendar.getInstance();
        verzondenOp.clear();
        verzondenOp.set(2004, Calendar.OCTOBER, 19, 10, 23, 54);
        request.setVerwerking(new Verwerking(verzondenOp, VerwerkingStatus.GESLAAGD));

        Persoon nieuwgeborene = new Persoon(198352888, "Johan", "Vlag");
        nieuwgeborene.setDatumGeboorte(20120620);
        nieuwgeborene.setGemeenteGeboorte(new Gemeente("Utrecht"));
        request.setAdresNieuwgeborene(new Adres(new Gemeente("Amsterdam")));
        request.setNieuwgeborene(nieuwgeborene);
        request.setOuder1(new Persoon(210, "Cees", "Vlag"));
        request.setOuder2(new Persoon(297, "Paula", "Prins"));

        // test
        service.opslaan(request);

        ArgumentCaptor<Bericht> argument = ArgumentCaptor.forClass(Bericht.class);
        Mockito.verify(berichtenDao).opslaan(argument.capture());
        Assert.assertEquals(0, argument.getValue().getAantalMeldingen());
        Assert.assertEquals("Amsterdam: inschrijving nieuwgeborene Johan Vlag.", argument.getValue().getBericht());
        Assert.assertEquals("Geboren op 20-06-2012 te Utrecht. BSN: 198352888. Ouders: Cees Vlag en Paula Prins.",
                argument.getValue().getBerichtDetails());
        Assert.assertEquals("CentricBZM", argument.getValue().getBurgerZakenModule());
        Assert.assertEquals("Amsterdam", argument.getValue().getPartij());
        Assert.assertEquals(OndersteundeBijhoudingsTypes.GEBOORTE, argument.getValue().getSoortBijhouding());
        Assert.assertSame(verzondenOp, argument.getValue().getVerzondenOp());
        Assert.assertEquals(3, argument.getValue().getBurgerservicenummers().size());
        Assert.assertEquals(198352888, argument.getValue().getBurgerservicenummers().get(0).intValue());
        Assert.assertEquals(210, argument.getValue().getBurgerservicenummers().get(1).intValue());
        Assert.assertEquals(297, argument.getValue().getBurgerservicenummers().get(2).intValue());
    }

    @Test
    public void testOpslaanGeboortePrevalidatie() {

        GeboorteBerichtRequest request = new GeboorteBerichtRequest();

        BerichtKenmerken kenmerken = new BerichtKenmerken(new Gemeente("Amsterdam"), "CentricBZM");
        kenmerken.setPrevalidatie(true);
        request.setKenmerken(kenmerken);

        Calendar verzondenOp = Calendar.getInstance();
        verzondenOp.clear();
        verzondenOp.set(2004, Calendar.OCTOBER, 19, 10, 23, 54);
        request.setVerwerking(new Verwerking(verzondenOp, VerwerkingStatus.MISLUKT));

        request.setMeldingen(Arrays.asList(new Melding(MeldingSoort.OVERRULEBAAR,
                "Geslachtsnaam kind moet overeenkomen met die van één van de ouders. "
                    + "Als er al een kind is moet het overeenkomen met die eerdere keuze.")));

        Persoon nieuwgeborene = new Persoon(198352888, "Marije", "Jansen");
        nieuwgeborene.setDatumGeboorte(20120620);
        nieuwgeborene.setGemeenteGeboorte(new Gemeente("Amsterdam"));
        request.setAdresNieuwgeborene(new Adres(new Gemeente("Amsterdam")));
        request.setNieuwgeborene(nieuwgeborene);
        request.setOuder1(new Persoon(159, "Cees", "Vlag"));
        request.setOuder2(new Persoon(543, "Paula", "Prins"));

        // test
        service.opslaan(request);

        ArgumentCaptor<Bericht> argument = ArgumentCaptor.forClass(Bericht.class);
        Mockito.verify(berichtenDao).opslaan(argument.capture());
        Assert.assertEquals(1, argument.getValue().getAantalMeldingen());
        Assert.assertEquals("Amsterdam: prevalidatie inschrijving nieuwgeborene Marije Jansen. 1 melding.", argument
                .getValue().getBericht());
        Assert.assertEquals("Negeerbare fout: Geslachtsnaam kind moet overeenkomen met die van één van de ouders. "
            + "Als er al een kind is moet het overeenkomen met die eerdere keuze.", argument.getValue()
                .getBerichtDetails());
        Assert.assertEquals("CentricBZM", argument.getValue().getBurgerZakenModule());
        Assert.assertEquals("Amsterdam", argument.getValue().getPartij());
        Assert.assertEquals(OndersteundeBijhoudingsTypes.GEBOORTE, argument.getValue().getSoortBijhouding());
        Assert.assertSame(verzondenOp, argument.getValue().getVerzondenOp());
    }

    @Test
    public void testOpslaanGeboorteDoorAndereGemeente() {

        GeboorteBerichtRequest request = new GeboorteBerichtRequest();

        request.setKenmerken(new BerichtKenmerken(new Gemeente("Amsterdam"), "CentricBZM"));

        Calendar verzondenOp = Calendar.getInstance();
        verzondenOp.clear();
        verzondenOp.set(2004, Calendar.OCTOBER, 19, 10, 23, 54);
        request.setVerwerking(new Verwerking(verzondenOp, VerwerkingStatus.GESLAAGD));

        Persoon nieuwgeborene = new Persoon(194622987, "Mieke", "Vermeer");
        nieuwgeborene.setDatumGeboorte(20120619);
        nieuwgeborene.setGemeenteGeboorte(new Gemeente("Amsterdam"));
        request.setAdresNieuwgeborene(new Adres(new Gemeente("Haarlem")));
        request.setNieuwgeborene(nieuwgeborene);
        request.setOuder1(new Persoon(43, "Christoffel", "Vermeer"));
        request.setOuder2(new Persoon(44, "Eleonora", "de", "Crooy"));

        // test
        service.opslaan(request);

        ArgumentCaptor<Bericht> argument = ArgumentCaptor.forClass(Bericht.class);
        Mockito.verify(berichtenDao).opslaan(argument.capture());
        Assert.assertEquals(0, argument.getValue().getAantalMeldingen());
        Assert.assertEquals("Amsterdam (voor Haarlem): inschrijving nieuwgeborene Mieke Vermeer.", argument.getValue().getBericht());
        Assert.assertEquals("Geboren op 19-06-2012 te Amsterdam. BSN: 194622987. Ouders: Christoffel Vermeer en Eleonora de Crooy.",
                argument.getValue().getBerichtDetails());
        Assert.assertEquals("CentricBZM", argument.getValue().getBurgerZakenModule());
        Assert.assertEquals("Amsterdam", argument.getValue().getPartij());
        Assert.assertEquals(OndersteundeBijhoudingsTypes.GEBOORTE, argument.getValue().getSoortBijhouding());
        Assert.assertSame(verzondenOp, argument.getValue().getVerzondenOp());
    }

    @Test
    public void testOpslaanGeboorteMetMinimaleInvoer() {

        GeboorteBerichtRequest request = new GeboorteBerichtRequest();

        request.setKenmerken(new BerichtKenmerken(new Gemeente("Amsterdam"), "CentricBZM"));

        Calendar verzondenOp = Calendar.getInstance();
        verzondenOp.clear();
        verzondenOp.set(2004, Calendar.OCTOBER, 19, 10, 23, 54);
        request.setVerwerking(new Verwerking(verzondenOp, VerwerkingStatus.GESLAAGD));

        Persoon nieuwgeborene = new Persoon(198352888, null, null);
        nieuwgeborene.setDatumGeboorte(20120620);
        request.setNieuwgeborene(nieuwgeborene);

        // test
        service.opslaan(request);

        ArgumentCaptor<Bericht> argument = ArgumentCaptor.forClass(Bericht.class);
        Mockito.verify(berichtenDao).opslaan(argument.capture());
        Assert.assertEquals(0, argument.getValue().getAantalMeldingen());
        Assert.assertEquals("Amsterdam: inschrijving nieuwgeborene naam onbekend.", argument.getValue().getBericht());
        Assert.assertEquals("Geboren op 20-06-2012 te ?. BSN: 198352888. Ouders: ?  en ? .",
                argument.getValue().getBerichtDetails());
        Assert.assertEquals("CentricBZM", argument.getValue().getBurgerZakenModule());
        Assert.assertEquals("Amsterdam", argument.getValue().getPartij());
        Assert.assertEquals(OndersteundeBijhoudingsTypes.GEBOORTE, argument.getValue().getSoortBijhouding());
        Assert.assertSame(verzondenOp, argument.getValue().getVerzondenOp());
    }

}
