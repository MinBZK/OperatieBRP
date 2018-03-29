/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc501.gba;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.sync.register.Partij;
import nl.bzk.migratiebrp.bericht.model.sync.register.PartijRegister;
import nl.bzk.migratiebrp.bericht.model.sync.register.Stelsel;
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
 * Test voor ControleerVb01BerichtAction.
 */
@RunWith(MockitoJUnitRunner.class)
public class ControleerVb01BerichtActionTest {

    private static final String BERICHT_INHOUD = "Vrij bericht inhoud";
    private final String ONTVANGENDE_PARTIJ = "4321";
    private final String ONGELDIGE_ONTVANGENDE_PARTIJ = "1234";
    private final String VERZENDENDE_PARTIJ = "654321";
    private final String ONGELDIGE_VERZENDENDE_PARTIJ = "123456";

    private ControleerVb01BerichtAction subject;

    @Mock
    private BerichtenDao berichtenDao;

    @Mock
    private PartijService partijRegisterService;

    @Mock
    private Lo3Bericht bericht;

    @Mock
    private PartijRegister partijRegister;
    @Mock
    private Partij geldigeOntvangendePartij;
    @Mock
    private Partij ongeldigeOntvangendePartij;
    @Mock
    private Partij geldigeVerzendendePartij;
    @Mock
    private Partij ongeldigeVerzendendePartij;

    @Before
    public void setup() {
        Mockito.when(geldigeVerzendendePartij.isAfnemer()).thenReturn(true);
        Mockito.when(geldigeVerzendendePartij.isBijhouder()).thenReturn(true);
        Mockito.when(geldigeVerzendendePartij.isCentraleVoorziening()).thenReturn(false);
        Mockito.when(geldigeVerzendendePartij.getStelsel()).thenReturn(Stelsel.GBA);
        Mockito.when(ongeldigeVerzendendePartij.isAfnemer()).thenReturn(false);
        Mockito.when(ongeldigeVerzendendePartij.isBijhouder()).thenReturn(false);
        Mockito.when(ongeldigeVerzendendePartij.getStelsel()).thenReturn(Stelsel.BRP);
        Mockito.when(ongeldigeVerzendendePartij.isCentraleVoorziening()).thenReturn(false);
        Mockito.when(geldigeOntvangendePartij.isAfnemer()).thenReturn(true);
        Mockito.when(geldigeOntvangendePartij.isBijhouder()).thenReturn(true);
        Mockito.when(geldigeOntvangendePartij.isCentraleVoorziening()).thenReturn(false);
        Mockito.when(geldigeOntvangendePartij.getStelsel()).thenReturn(Stelsel.BRP);
        Mockito.when(ongeldigeOntvangendePartij.isAfnemer()).thenReturn(false);
        Mockito.when(ongeldigeOntvangendePartij.isBijhouder()).thenReturn(false);
        Mockito.when(ongeldigeOntvangendePartij.getStelsel()).thenReturn(Stelsel.GBA);
        Mockito.when(ongeldigeOntvangendePartij.isCentraleVoorziening()).thenReturn(false);
        Mockito.when(partijRegisterService.geefRegister()).thenReturn(partijRegister);
        Mockito.when(partijRegister.zoekPartijOpPartijCode(ONTVANGENDE_PARTIJ)).thenReturn(geldigeOntvangendePartij);
        Mockito.when(partijRegister.zoekPartijOpPartijCode(ONGELDIGE_ONTVANGENDE_PARTIJ)).thenReturn(ongeldigeOntvangendePartij);
        Mockito.when(partijRegister.zoekPartijOpPartijCode(VERZENDENDE_PARTIJ)).thenReturn(geldigeVerzendendePartij);
        Mockito.when(partijRegister.zoekPartijOpPartijCode(ONGELDIGE_VERZENDENDE_PARTIJ)).thenReturn(ongeldigeVerzendendePartij);
        subject = new ControleerVb01BerichtAction(berichtenDao, partijRegisterService);
    }

    @Test
    public void testExecuteOK() {
        Mockito.when(berichtenDao.leesBericht(1L)).thenReturn(bericht);
        Mockito.when(bericht.getBronPartijCode()).thenReturn(VERZENDENDE_PARTIJ);
        Mockito.when(bericht.getDoelPartijCode()).thenReturn(ONTVANGENDE_PARTIJ);
        Mockito.when(bericht.getHeaderWaarde(Lo3HeaderVeld.BERICHT)).thenReturn(BERICHT_INHOUD);

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", 1L);

        final Map<String, Object> resultaat = subject.execute(parameters);
        Assert.assertNull("Waarde moet niet overeenkomen", resultaat.get("actieFoutmelding"));
        Assert.assertFalse("Transitie sleutel moet voorkomen", resultaat.containsKey("transition"));
    }

    @Test
    public void testGeenBerichInhoud() {
        Mockito.when(berichtenDao.leesBericht(1L)).thenReturn(bericht);
        Mockito.when(bericht.getBronPartijCode()).thenReturn(VERZENDENDE_PARTIJ);
        Mockito.when(bericht.getDoelPartijCode()).thenReturn(ONTVANGENDE_PARTIJ);

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", 1L);

        final Map<String, Object> resultaat = subject.execute(parameters);
        Assert.assertEquals("Waarde moet overeenkomen", "Vb01 bericht bevat geen inhoud.", resultaat.get("actieFoutmelding"));
        Assert.assertTrue("Transitie sleutel moeten voorkomen", resultaat.containsKey("transition"));
        Assert.assertEquals("Waarde moet overeenkomen", "1a. Fout verzender", resultaat.get("transition"));
    }

    @Test
    public void verzenderIsGeenGbaPartij() {
        Mockito.when(berichtenDao.leesBericht(1L)).thenReturn(bericht);
        Mockito.when(bericht.getBronPartijCode()).thenReturn(ONGELDIGE_VERZENDENDE_PARTIJ);
        Mockito.when(bericht.getDoelPartijCode()).thenReturn(ONTVANGENDE_PARTIJ);
        Mockito.when(bericht.getHeaderWaarde(Lo3HeaderVeld.BERICHT)).thenReturn(BERICHT_INHOUD);

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", 1L);

        final Map<String, Object> resultaat = subject.execute(parameters);
        Assert.assertEquals("Waarde moet overeenkomen", "Verzendende partij (originator) is niet een GBA partij.", resultaat.get("actieFoutmelding"));
        Assert.assertTrue("Transitie sleutel moeten voorkomen", resultaat.containsKey("transition"));
        Assert.assertEquals("Waarde moet overeenkomen", "1a. Fout verzender", resultaat.get("transition"));
    }

    @Test
    public void ontvangerIsGeenBrpPartij() {
        Mockito.when(berichtenDao.leesBericht(1L)).thenReturn(bericht);
        Mockito.when(bericht.getBronPartijCode()).thenReturn(VERZENDENDE_PARTIJ);
        Mockito.when(bericht.getDoelPartijCode()).thenReturn(ONGELDIGE_ONTVANGENDE_PARTIJ);
        Mockito.when(bericht.getHeaderWaarde(Lo3HeaderVeld.BERICHT)).thenReturn(BERICHT_INHOUD);

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", 1L);

        final Map<String, Object> resultaat = subject.execute(parameters);
        Assert.assertEquals("Waarde moet overeenkomen", "Ontvangende partij (recipient) is niet een BRP partij.", resultaat.get("actieFoutmelding"));
        Assert.assertTrue("Transitie sleutel moeten voorkomen", resultaat.containsKey("transition"));
        Assert.assertEquals("Waarde moet overeenkomen", "1b. Fout ontvanger", resultaat.get("transition"));
    }

    @Test
    public void verzenderNietInPartijRegister() {
        Mockito.when(berichtenDao.leesBericht(1L)).thenReturn(bericht);
        Mockito.when(bericht.getBronPartijCode()).thenReturn(null);
        Mockito.when(bericht.getDoelPartijCode()).thenReturn(ONTVANGENDE_PARTIJ);
        Mockito.when(bericht.getHeaderWaarde(Lo3HeaderVeld.BERICHT)).thenReturn(BERICHT_INHOUD);

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", 1L);

        final Map<String, Object> resultaat = subject.execute(parameters);
        Assert.assertEquals("Waarde moet overeenkomen", "Verzendende partij (originator) is niet een GBA partij.", resultaat.get("actieFoutmelding"));
        Assert.assertTrue("Transitie sleutel moeten voorkomen", resultaat.containsKey("transition"));
        Assert.assertEquals("Waarde moet overeenkomen", "1a. Fout verzender", resultaat.get("transition"));
    }

    @Test
    public void ontvangerNietInPartijRegister() {
        Mockito.when(berichtenDao.leesBericht(1L)).thenReturn(bericht);
        Mockito.when(bericht.getBronPartijCode()).thenReturn(VERZENDENDE_PARTIJ);
        Mockito.when(bericht.getDoelPartijCode()).thenReturn(null);
        Mockito.when(bericht.getHeaderWaarde(Lo3HeaderVeld.BERICHT)).thenReturn(BERICHT_INHOUD);

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", 1L);

        final Map<String, Object> resultaat = subject.execute(parameters);
        Assert.assertEquals("Waarde moet overeenkomen", "Ontvangende partij (recipient) is niet een BRP partij.", resultaat.get("actieFoutmelding"));
        Assert.assertTrue("Transitie sleutel moeten voorkomen", resultaat.containsKey("transition"));
        Assert.assertEquals("Waarde moet overeenkomen", "1b. Fout ontvanger", resultaat.get("transition"));
    }

    @Test
    public void zowelOntvangerAlsVerzenderKloppenNiet() {
        Mockito.when(berichtenDao.leesBericht(1L)).thenReturn(bericht);
        Mockito.when(bericht.getBronPartijCode()).thenReturn(ONGELDIGE_VERZENDENDE_PARTIJ);
        Mockito.when(bericht.getDoelPartijCode()).thenReturn(ONGELDIGE_ONTVANGENDE_PARTIJ);
        Mockito.when(bericht.getHeaderWaarde(Lo3HeaderVeld.BERICHT)).thenReturn(BERICHT_INHOUD);

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", 1L);

        final Map<String, Object> resultaat = subject.execute(parameters);
        Assert.assertTrue("Sleutel actieFoutmelding moet voorkomen", resultaat.containsKey("actieFoutmelding"));
        Assert.assertEquals("Waarde moet overeenkomen", "Verzendende partij (originator) is niet een GBA partij.", resultaat.get("actieFoutmelding"));
        Assert.assertTrue("Transitie sleutel moeten voorkomen", resultaat.containsKey("transition"));
        Assert.assertEquals("Waarde moet overeenkomen", "1a. Fout verzender", resultaat.get("transition"));
    }
}
