/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.service;

import java.util.Calendar;

import nl.bzk.brp.preview.dataaccess.BerichtenDao;
import nl.bzk.brp.preview.model.Bericht;
import nl.bzk.brp.preview.model.BerichtKenmerken;
import nl.bzk.brp.preview.model.Gemeente;
import nl.bzk.brp.preview.model.HuwelijkBerichtRequest;
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


public class HuwelijkBerichtenServiceTest {

    private BerichtenService service;

    @Mock
    private BerichtenDao      berichtenDao;

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
    public void testOpslaanHuwelijk1() {

        HuwelijkBerichtRequest request = new HuwelijkBerichtRequest();

        request.setKenmerken(new BerichtKenmerken(new Gemeente("Amsterdam"), "CentricBZM"));

        Calendar verzondenOp = Calendar.getInstance();
        verzondenOp.clear();
        verzondenOp.set(2004, Calendar.OCTOBER, 19, 10, 23, 54);
        request.setVerwerking(new Verwerking(verzondenOp, VerwerkingStatus.GESLAAGD));

        request.setDatumAanvang(20120821);
        request.setPlaats(new Plaats("Delft"));
        request.setPersoon1(new Persoon(210, "Ab", "Vis"));
        request.setPersoon2(new Persoon(297, "An", "Dijk"));

        // test
        service.opslaan(request);

        ArgumentCaptor<Bericht> argument = ArgumentCaptor.forClass(Bericht.class);
        Mockito.verify(berichtenDao).opslaan(argument.capture());
        Assert.assertEquals(0, argument.getValue().getAantalMeldingen());
        Assert.assertEquals("Huwelijk op 21-08-2012 te Delft tussen Ab Vis en An Dijk.", argument.getValue().getBericht());
    }

    @Test
    public void testOpslaanHuwelijk2() {

        HuwelijkBerichtRequest request = new HuwelijkBerichtRequest();

        request.setKenmerken(new BerichtKenmerken(new Gemeente("Amsterdam"), "CentricBZM"));

        Calendar verzondenOp = Calendar.getInstance();
        verzondenOp.clear();
        verzondenOp.set(2004, Calendar.OCTOBER, 19, 10, 23, 54);
        request.setVerwerking(new Verwerking(verzondenOp, VerwerkingStatus.GESLAAGD));

        request.setDatumAanvang(20120920);
        request.setPlaats(new Plaats("Katwijk"));
        request.setPersoon1(new Persoon(210, "Jan", "van", "Hout"));
        request.setPersoon2(new Persoon(297, "Marie", "de", "Visser"));

        // test
        service.opslaan(request);

        ArgumentCaptor<Bericht> argument = ArgumentCaptor.forClass(Bericht.class);
        Mockito.verify(berichtenDao).opslaan(argument.capture());
        Assert.assertEquals(0, argument.getValue().getAantalMeldingen());
        Assert.assertEquals("Huwelijk op 20-09-2012 te Katwijk tussen Jan van Hout en Marie de Visser.", argument.getValue().getBericht());
    }

}
