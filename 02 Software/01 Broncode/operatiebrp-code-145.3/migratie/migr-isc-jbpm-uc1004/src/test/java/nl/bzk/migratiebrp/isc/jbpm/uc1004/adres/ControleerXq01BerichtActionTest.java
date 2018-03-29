/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc1004.adres;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.register.Partij;
import nl.bzk.migratiebrp.bericht.model.sync.register.PartijRegister;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.register.client.PartijService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test voor ControleerXq01BerichtAction.
 */
@RunWith(MockitoJUnitRunner.class)
public class ControleerXq01BerichtActionTest {

    private final String ONGELDIGE_AFNEMER = "1234";
    private final String AFNEMER = "123456";
    private final String CENTRALE_VOORZIENING = "199902";

    private ControleerXq01BerichtAction subject;

    @Mock
    private BerichtenDao berichtenDao;

    @Mock
    private PartijService partijRegisterService;

    @Mock
    private Lo3Bericht bericht;

    @Mock
    private PartijRegister partijRegister;
    @Mock
    private Partij afnemerPartij;
    @Mock
    private Partij ongeldigeartij;
    @Mock
    private Partij centraleVoorzieningPartij;

    @Before
    public void setup() {
        Mockito.when(afnemerPartij.isAfnemer()).thenReturn(true);
        Mockito.when(afnemerPartij.isCentraleVoorziening()).thenReturn(false);
        Mockito.when(ongeldigeartij.isAfnemer()).thenReturn(false);
        Mockito.when(ongeldigeartij.isCentraleVoorziening()).thenReturn(false);
        Mockito.when(centraleVoorzieningPartij.isAfnemer()).thenReturn(false);
        Mockito.when(centraleVoorzieningPartij.isCentraleVoorziening()).thenReturn(true);
        Mockito.when(partijRegisterService.geefRegister()).thenReturn(partijRegister);
        Mockito.when(partijRegister.zoekPartijOpPartijCode(AFNEMER)).thenReturn(afnemerPartij);
        Mockito.when(partijRegister.zoekPartijOpPartijCode(ONGELDIGE_AFNEMER)).thenReturn(ongeldigeartij);
        Mockito.when(partijRegister.zoekPartijOpPartijCode(CENTRALE_VOORZIENING)).thenReturn(centraleVoorzieningPartij);
        subject = new ControleerXq01BerichtAction(berichtenDao, partijRegisterService);
    }

    @Test
    public void testExecuteOK() {
        Mockito.when(berichtenDao.leesBericht(1L)).thenReturn(bericht);
        Mockito.when(bericht.getBronPartijCode()).thenReturn(AFNEMER);
        Mockito.when(bericht.getDoelPartijCode()).thenReturn(CENTRALE_VOORZIENING);

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", 1L);

        final Map<String, Object> resultaat = subject.execute(parameters);
    }

    @Test
    public void afnemerIsGeenAfnemer() {
        Mockito.when(berichtenDao.leesBericht(1L)).thenReturn(bericht);
        Mockito.when(bericht.getBronPartijCode()).thenReturn(ONGELDIGE_AFNEMER);
        Mockito.when(bericht.getDoelPartijCode()).thenReturn(CENTRALE_VOORZIENING);

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", 1L);

        final Map<String, Object> resultaat = subject.execute(parameters);
        Assert.assertFalse("Sleutel mag niet aanwezig zijn", resultaat.containsKey("xa01bevragenpersoondienstid"));
        Assert.assertTrue("Sleutel actieFoutmelding moet voorkomen", resultaat.containsKey("actieFoutmelding"));
        Assert.assertEquals("Waarde moet overeenkomen", "Verzendende partij (originator) is niet een GBA afnemer.", resultaat.get("actieFoutmelding"));
        Assert.assertTrue("Transitie sleutel moeten voorkomen", resultaat.containsKey("transition"));
        Assert.assertEquals("Waarde moet overeenkomen", "1a. Fout verzender", resultaat.get("transition"));
    }

    @Test
    public void ontvangerIsGeenCentrale() {
        Mockito.when(berichtenDao.leesBericht(1L)).thenReturn(bericht);
        Mockito.when(bericht.getBronPartijCode()).thenReturn(AFNEMER);
        Mockito.when(bericht.getDoelPartijCode()).thenReturn(ONGELDIGE_AFNEMER);

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", 1L);

        final Map<String, Object> resultaat = subject.execute(parameters);
        Assert.assertFalse("Sleutel mag niet aanwezig zijn", resultaat.containsKey("xa01bevragenpersoondienstid"));
        Assert.assertTrue("Sleutel actieFoutmelding moet voorkomen", resultaat.containsKey("actieFoutmelding"));
        Assert.assertEquals(
                "Waarde moet overeenkomen",
                "Ontvangende partij (recipient) is niet de centrale voorziening.",
                resultaat.get("actieFoutmelding"));
        Assert.assertTrue("Transitie sleutel moeten voorkomen", resultaat.containsKey("transition"));
        Assert.assertEquals("Waarde moet overeenkomen", "1b. Fout ontvanger", resultaat.get("transition"));
    }

    @Test
    public void zowelOntvangerAlsVerzenderKloppenNiet() {
        Mockito.when(berichtenDao.leesBericht(1L)).thenReturn(bericht);
        Mockito.when(bericht.getBronPartijCode()).thenReturn(CENTRALE_VOORZIENING);
        Mockito.when(bericht.getDoelPartijCode()).thenReturn(AFNEMER);

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", 1L);

        final Map<String, Object> resultaat = subject.execute(parameters);
        Assert.assertTrue("Sleutel actieFoutmelding moet voorkomen", resultaat.containsKey("actieFoutmelding"));
        Assert.assertEquals("Waarde moet overeenkomen", "Verzendende partij (originator) is niet een GBA afnemer.", resultaat.get("actieFoutmelding"));
        Assert.assertTrue("Transitie sleutel moeten voorkomen", resultaat.containsKey("transition"));
        Assert.assertEquals("Waarde moet overeenkomen", "1a. Fout verzender", resultaat.get("transition"));
    }
}
