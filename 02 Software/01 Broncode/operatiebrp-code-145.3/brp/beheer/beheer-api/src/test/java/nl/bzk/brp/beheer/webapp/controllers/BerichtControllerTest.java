/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Bericht;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.brp.beheer.webapp.repository.ber.BerichtRepository;
import nl.bzk.brp.beheer.webapp.repository.kern.AdministratieveHandelingRepository;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.autaut.LeveringsautorisatieRepository;
import nl.bzk.brp.beheer.webapp.view.BerichtDetailView;
import nl.bzk.brp.beheer.webapp.view.BerichtView;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Test een aantal methoden die via voorkant nauwelijks te testen zijn.
 */
public class BerichtControllerTest {

    private BerichtController subject;
    private BerichtRepository berichtenRepository;
    private AdministratieveHandelingRepository administratieveHandelingRepository;
    private LeveringsautorisatieRepository leveringsautorisatieRepository;

    @Before
    public void setup() {
        berichtenRepository = Mockito.mock(BerichtRepository.class);
        administratieveHandelingRepository = Mockito.mock(AdministratieveHandelingRepository.class);
        leveringsautorisatieRepository = Mockito.mock(LeveringsautorisatieRepository.class);
        subject = new BerichtController(berichtenRepository, administratieveHandelingRepository, leveringsautorisatieRepository);
    }

    @Test
    public void testConverteerNaarViewGevuld() throws Exception {
        final Bericht bericht = Mockito.mock(Bericht.class);
        final AdministratieveHandeling administratieveHandeling = Mockito.mock(AdministratieveHandeling.class);
        Mockito.when(administratieveHandeling.getId()).thenReturn(1L);
        Mockito.when(administratieveHandelingRepository.findOne(1L)).thenReturn(administratieveHandeling);
        Mockito.when(bericht.getAdministratieveHandeling()).thenReturn(1L);

        final Leveringsautorisatie leveringsautorisatie = Mockito.mock(Leveringsautorisatie.class);
        Mockito.when(leveringsautorisatie.getId()).thenReturn(1);
        Mockito.when(leveringsautorisatieRepository.findOne(1)).thenReturn(leveringsautorisatie);
        Mockito.when(bericht.getLeveringsAutorisatie()).thenReturn(1);

        final BerichtView berichtView = subject.converteerNaarView(bericht);
        Assert.assertNotNull("Er moet een bericht zijn", ((BerichtDetailView) berichtView).getBericht());
        Assert.assertNotNull("Er moet een administratieve handeling zijn", ((BerichtDetailView) berichtView).getAdministratieveHandeling());
        Assert.assertNotNull("Er moet een leveringauthorisatie zijn", ((BerichtDetailView) berichtView).getLeveringsautorisatie());
    }

    @Test
    public void testGeenAdminstratieveHandelingEnLeveringsautorisatie() throws Exception {
        final Bericht bericht = Mockito.mock(Bericht.class);

        final BerichtView berichtView = subject.converteerNaarView(bericht);
        Assert.assertNotNull("Er moet een bericht zijn", ((BerichtDetailView) berichtView).getBericht());
        Assert.assertNull("Er kan geen administratieve handeling zijn", ((BerichtDetailView) berichtView).getAdministratieveHandeling());
        Assert.assertNull("Er kan geen leveringauthorisatie zijn", ((BerichtDetailView) berichtView).getLeveringsautorisatie());
    }

    @Test
    public void testAdministratieveHandelingEnLeveringsAutorisatieNietGevonden() throws Exception {
        final Bericht bericht = Mockito.mock(Bericht.class);
        final AdministratieveHandeling administratieveHandeling = Mockito.mock(AdministratieveHandeling.class);
        Mockito.when(administratieveHandeling.getId()).thenReturn(1L);
        Mockito.when(administratieveHandelingRepository.findOne(1L)).thenReturn(null);
        Mockito.when(bericht.getAdministratieveHandeling()).thenReturn(1L);

        final Leveringsautorisatie leveringsautorisatie = Mockito.mock(Leveringsautorisatie.class);
        Mockito.when(leveringsautorisatie.getId()).thenReturn(1);
        Mockito.when(leveringsautorisatieRepository.findOne(1)).thenReturn(null);
        Mockito.when(bericht.getLeveringsAutorisatie()).thenReturn(1);

        final BerichtView berichtView = subject.converteerNaarView(bericht);
        Assert.assertNotNull("Er moet een bericht zijn", ((BerichtDetailView) berichtView).getBericht());
        Assert.assertNull("Er kan geen administratieve handeling zijn", ((BerichtDetailView) berichtView).getAdministratieveHandeling());
        Assert.assertNull("Er kan geen leveringauthorisatie zijn", ((BerichtDetailView) berichtView).getLeveringsautorisatie());
    }

    @Test
    public void testConverteerNaarViewMetNullObject() {
        Assert.assertTrue("Null geeft lege lijst", subject.converteerNaarView((List<Bericht>) null).isEmpty());
    }
}
