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
import java.util.Calendar;
import java.util.List;

import nl.bzk.brp.preview.dataaccess.BerichtenDao;
import nl.bzk.brp.preview.model.Bericht;
import nl.bzk.brp.preview.model.BerichtenResponse;
import nl.bzk.brp.preview.model.DashboardSettings;
import nl.bzk.brp.preview.model.OndersteundeBijhoudingsTypes;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;


public class VerhuisBerichtenServiceOpvraagTest {

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
    public void testGetBerichtenResponse() {

        Mockito.when(berichtenDao.getAlleBerichten()).thenReturn(creeerDummyBerichten());

        // test
        BerichtenResponse response = service.getBerichten();

        List<Bericht> berichtenLijst = response.getBerichten();
        Assert.assertEquals(2L, berichtenLijst.size());
        Bericht[] berichten = berichtenLijst.toArray(new Bericht[berichtenLijst.size()]);

        Assert.assertEquals("Utrecht", berichten[0].getPartij());
        Assert.assertEquals("Rotterdam", berichten[1].getPartij());

    }

    @Test
    public void testGetBerichtenVoorBsn() {

        Mockito.when(berichtenDao.getBerichtenOpBsn(321)).thenReturn(creeerDummyBerichten());

        // test
        BerichtenResponse response = service.getBerichtenVoorBsn("321");

        List<Bericht> berichten = response.getBerichten();
        Assert.assertEquals(2, berichten.size());
        Assert.assertEquals("Utrecht", berichten.get(0).getPartij());

    }

    @Test
    public void testGetBerichtenResponseVanaf() {

        Calendar vanaf = Calendar.getInstance();
        vanaf.set(2012, Calendar.MAY, 15, 10, 9, 7);

        Mockito.when(berichtenDao.getBerichtenVanaf(vanaf)).thenReturn(creeerDummyBerichten());

        // test
        BerichtenResponse response = service.getBerichtenResponseVanaf(vanaf);

        List<Bericht> berichten = response.getBerichten();
        Assert.assertEquals(2, berichten.size());
        Assert.assertEquals("Utrecht", berichten.get(0).getPartij());

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
