/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.service;

import static junit.framework.Assert.assertEquals;

import nl.bzk.brp.preview.dataaccess.BerichtenDao;
import nl.bzk.brp.preview.integratie.AbstractIntegratieTest;
import nl.bzk.brp.preview.model.Bericht;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Service voor toegang tot adminstratieve handeling.
 */
public class AdministratieveHandelingServiceTest extends AbstractIntegratieTest {

    @Autowired
    private AdministratieveHandelingService administratieveHandelingService;

    @Autowired
    private BerichtenDao berichtenDao;

    @Test
    public void maakBerichtTest() {
        Bericht bericht = administratieveHandelingService.maakBericht(1L);

        // verify
        Assert.assertNotNull(bericht);
    }

    @Test
    public void opslaanTest() {
        Bericht bericht = administratieveHandelingService.maakBericht(1L);
        int aantalBerichtenVoorOpslaan = berichtenDao.getAlleBerichten().size();

        //test
        berichtenDao.opslaan(bericht);
        int aantalBerichtenNaOpslaan = berichtenDao.getAlleBerichten().size();

        //verify
        Assert.assertNotSame(aantalBerichtenVoorOpslaan, aantalBerichtenNaOpslaan);
        Assert.assertTrue(aantalBerichtenVoorOpslaan < aantalBerichtenNaOpslaan);
    }

    @Test
    public void maakBerichtTestDetailTekst() {
        final Bericht bericht = administratieveHandelingService.maakBericht(1L);

        // verify
        assertEquals("Waarschuwing: test melding 2\n"
                + "Informatie: test melding", bericht.getBerichtDetails());
    }

    @Test
    public void maakBerichtTestBerichtTekst() {
        final Bericht bericht = administratieveHandelingService.maakBericht(1L);

        // verify
        assertEquals("Onbekende administratieve handeling op 02-10-1981 in Migratievoorziening van het soort "
                + "onbekende handeling.", bericht.getBericht());
    }

    @Test
    public void maakBerichtTestGeboorte() {
        final Bericht bericht = administratieveHandelingService.maakBericht(1001L);

        // verify
        assertEquals("Cees de Vries geboren op 02-10-1981 te 's-Gravenzande. BSN: 677080426. Ouders: Adam-Cees van "
                + "Modernodam en Eva-Cees van Modernodam.", bericht.getBericht());

        assertEquals(3, bericht.getBurgerservicenummers().size());
    }

    @Test
    public void maakBerichtTestHuwelijk() {
        final Bericht bericht = administratieveHandelingService.maakBericht(630L);

        // verify
        assertEquals("Huwelijk op 27-03-2003 te Gouda tussen Cees Vlag en Paula de Prins.", bericht.getBericht());

        assertEquals(2, bericht.getBurgerservicenummers().size());
    }

    @Test
    public void maakBerichtTestOverlijden() {
        final Bericht bericht = administratieveHandelingService.maakBericht(506L);

        // verify
        assertEquals("Jan Moes overleden op 11-11-2011 te Haarlemmermeer. BSN: 912345603.", bericht.getBericht());

        assertEquals(1, bericht.getBurgerservicenummers().size());
    }

    @Test
    public void maakBerichtTestCorrectieAdres() {
        final Bericht bericht = administratieveHandelingService.maakBericht(9000L);

        // verify
        assertEquals("Adrescorrectie voor persoon Cees Vlag. Periode: 28-03-2005 tot heden. Adres: Vredenburg 3 (Utrecht).", bericht.getBericht());

        assertEquals(1, bericht.getBurgerservicenummers().size());
    }

    @Test
    public void maakBerichtTestVerhuizing() {
        final Bericht bericht = administratieveHandelingService.maakBericht(5000L);

        // verify
        assertEquals("Verhuizing van Cees_p1 voorvoeg Vlag per 28-03-2005 naar Vredenburg 3 II (Utrecht).", bericht.getBericht());

        assertEquals(1, bericht.getBurgerservicenummers().size());
    }
}
