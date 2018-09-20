/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import nl.bzk.brp.preview.dataaccess.BerichtenDao;
import nl.bzk.brp.preview.model.Adres;
import nl.bzk.brp.preview.model.AdrescorrectieBerichtRequest;
import nl.bzk.brp.preview.model.Bericht;
import nl.bzk.brp.preview.model.BerichtKenmerken;
import nl.bzk.brp.preview.model.Gemeente;
import nl.bzk.brp.preview.model.Persoon;
import nl.bzk.brp.preview.model.Plaats;
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


public class AdrescorrectieBerichtenServiceTest {

    private BerichtenService service;

    @Mock
    private BerichtenDao     berichtenDao;

    /**
     * Initialiseert de mocks en de service.
     */
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        service = new BerichtenServiceImpl();
        ReflectionTestUtils.setField(service, "berichtenDao", berichtenDao);
    }

    @Test
    public void testOpslaan1AdrescorrectieTotHeden() {
        AdrescorrectieBerichtRequest request = new AdrescorrectieBerichtRequest();

        request.setKenmerken(new BerichtKenmerken(new Gemeente("Amsterdam"), "CentricBZM"));

        Calendar verzondenOp = Calendar.getInstance();
        verzondenOp.clear();
        verzondenOp.set(2004, Calendar.OCTOBER, 19, 10, 23, 54);
        request.setVerwerking(new Verwerking(verzondenOp, VerwerkingStatus.GESLAAGD));

        request.setPersoon(new Persoon(210, "Ab", "Vis"));

        List<Adres> adressen = new ArrayList<Adres>();
        Adres nieuwAdres = new Adres();
        nieuwAdres.setGemeente(new Gemeente("Hellevoetsluis"));
        nieuwAdres.setStraat("Grote Markt");
        nieuwAdres.setHuisnummer("34");
        nieuwAdres.setPlaats(new Plaats("Nieuwenhoorn"));
        nieuwAdres.setDatumAanvang(20120101);
        adressen.add(nieuwAdres);
        request.setAdressen(adressen);

        // test
        service.opslaan(request);

        ArgumentCaptor<Bericht> argument = ArgumentCaptor.forClass(Bericht.class);
        Mockito.verify(berichtenDao).opslaan(argument.capture());
        Assert.assertEquals(0, argument.getValue().getAantalMeldingen());
        Assert.assertEquals(
                "Adrescorrectie voor persoon Ab Vis. Periode: 01-01-2012 tot heden. Adres: Grote Markt 34 (Nieuwenhoorn).",
                argument.getValue().getBericht());

    }

    @Test
    public void testOpslaan1AdrescorrectieMetEinddatum() {
        AdrescorrectieBerichtRequest request = new AdrescorrectieBerichtRequest();

        request.setKenmerken(new BerichtKenmerken(new Gemeente("Amsterdam"), "CentricBZM"));

        Calendar verzondenOp = Calendar.getInstance();
        verzondenOp.clear();
        verzondenOp.set(2004, Calendar.OCTOBER, 19, 10, 23, 54);
        request.setVerwerking(new Verwerking(verzondenOp, VerwerkingStatus.GESLAAGD));

        request.setPersoon(new Persoon(210, "Ab", "Vis"));

        List<Adres> adressen = new ArrayList<Adres>();
        Adres adres = new Adres();
        adres.setGemeente(new Gemeente("Hellevoetsluis"));
        adres.setStraat("Grote Markt");
        adres.setHuisnummer("34");
        adres.setPlaats(new Plaats("Nieuwenhoorn"));
        adres.setDatumAanvang(20120101);
        adres.setDatumEinde(20120202);
        adressen.add(adres);
        request.setAdressen(adressen);

        // test
        service.opslaan(request);

        ArgumentCaptor<Bericht> argument = ArgumentCaptor.forClass(Bericht.class);
        Mockito.verify(berichtenDao).opslaan(argument.capture());
        Assert.assertEquals(0, argument.getValue().getAantalMeldingen());
        Assert.assertEquals(
                "Adrescorrectie voor persoon Ab Vis. Periode: 01-01-2012 tot 02-02-2012. Adres: Grote Markt 34 (Nieuwenhoorn).",
                argument.getValue().getBericht());

    }

    @Test
    public void testOpslaanMeerdereAdrescorrecties() {
        AdrescorrectieBerichtRequest request = new AdrescorrectieBerichtRequest();

        request.setKenmerken(new BerichtKenmerken(new Gemeente("Amsterdam"), "CentricBZM"));

        Calendar verzondenOp = Calendar.getInstance();
        verzondenOp.clear();
        verzondenOp.set(2004, Calendar.OCTOBER, 19, 10, 23, 54);
        request.setVerwerking(new Verwerking(verzondenOp, VerwerkingStatus.GESLAAGD));

        request.setPersoon(new Persoon(210, "Ab", "Vis"));

        List<Adres> adressen = new ArrayList<Adres>();
        Adres adres1 = new Adres();
        adres1.setGemeente(new Gemeente("Hellevoetsluis"));
        adres1.setStraat("Grote Markt");
        adres1.setHuisnummer("34");
        adres1.setPlaats(new Plaats("Nieuwenhoorn"));
        adres1.setDatumAanvang(20120101);
        adres1.setDatumEinde(20120202);
        adressen.add(adres1);

        Adres adres2 = new Adres();
        adres2.setGemeente(new Gemeente("Ormink"));
        adres2.setStraat("Grote Markt");
        adres2.setHuisnummer("34");
        adres2.setPlaats(new Plaats("Ormink"));
        adres2.setDatumAanvang(20110101);
        adres2.setDatumEinde(20110202);
        adressen.add(adres2);

        request.setAdressen(adressen);

        // test
        service.opslaan(request);

        ArgumentCaptor<Bericht> argument = ArgumentCaptor.forClass(Bericht.class);
        Mockito.verify(berichtenDao).opslaan(argument.capture());
        Assert.assertEquals(0, argument.getValue().getAantalMeldingen());
        Assert.assertEquals("Adrescorrectie voor persoon Ab Vis."
            + " Periode: 01-01-2012 tot 02-02-2012. Adres: Grote Markt 34 (Nieuwenhoorn)."
            + " Periode: 01-01-2011 tot 02-02-2011. Adres: Grote Markt 34 (Ormink).", argument.getValue()
                .getBericht());

    }

}
