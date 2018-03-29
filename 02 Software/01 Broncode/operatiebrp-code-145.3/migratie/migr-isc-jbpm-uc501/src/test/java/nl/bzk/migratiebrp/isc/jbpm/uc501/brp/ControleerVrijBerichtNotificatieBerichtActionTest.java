/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc501.brp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.xml.bind.JAXBException;
import nl.bzk.migratiebrp.bericht.model.notificatie.generated.VrijBerichtVerwerkVrijBericht;
import nl.bzk.migratiebrp.bericht.model.notificatie.impl.VrijBerichtNotificatieBericht;
import nl.bzk.migratiebrp.bericht.model.notificatie.xml.NotificatieXml;
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
 * Test voor ControleerVrijBerichtNotificatieBerichtAction.
 */
@RunWith(MockitoJUnitRunner.class)
public class ControleerVrijBerichtNotificatieBerichtActionTest {

    private final String ONTVANGENDE_PARTIJ = "432101";
    private final String ONGELDIGE_ONTVANGENDE_PARTIJ = "123401";
    private final String VERZENDENDE_PARTIJ = "654321";
    private final String ONGELDIGE_VERZENDENDE_PARTIJ = "123456";

    private ControleerVrijBerichtNotificatieBerichtAction subject;

    @Mock
    private BerichtenDao berichtenDao;

    @Mock
    private PartijService partijRegisterService;

    private VrijBerichtNotificatieBericht bericht;

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
        Mockito.when(geldigeVerzendendePartij.getStelsel()).thenReturn(Stelsel.BRP);
        Mockito.when(ongeldigeVerzendendePartij.isAfnemer()).thenReturn(false);
        Mockito.when(ongeldigeVerzendendePartij.isBijhouder()).thenReturn(false);
        Mockito.when(ongeldigeVerzendendePartij.getStelsel()).thenReturn(Stelsel.GBA);
        Mockito.when(ongeldigeVerzendendePartij.isCentraleVoorziening()).thenReturn(false);
        Mockito.when(geldigeOntvangendePartij.isAfnemer()).thenReturn(true);
        Mockito.when(geldigeOntvangendePartij.isBijhouder()).thenReturn(true);
        Mockito.when(geldigeOntvangendePartij.isCentraleVoorziening()).thenReturn(false);
        Mockito.when(geldigeOntvangendePartij.getStelsel()).thenReturn(Stelsel.GBA);
        Mockito.when(ongeldigeOntvangendePartij.isAfnemer()).thenReturn(false);
        Mockito.when(ongeldigeOntvangendePartij.isBijhouder()).thenReturn(false);
        Mockito.when(ongeldigeOntvangendePartij.getStelsel()).thenReturn(Stelsel.BRP);
        Mockito.when(ongeldigeOntvangendePartij.isCentraleVoorziening()).thenReturn(false);
        Mockito.when(partijRegisterService.geefRegister()).thenReturn(partijRegister);
        Mockito.when(partijRegister.zoekPartijOpPartijCode(ONTVANGENDE_PARTIJ)).thenReturn(geldigeOntvangendePartij);
        Mockito.when(partijRegister.zoekPartijOpPartijCode(ONGELDIGE_ONTVANGENDE_PARTIJ)).thenReturn(ongeldigeOntvangendePartij);
        Mockito.when(partijRegister.zoekPartijOpPartijCode(VERZENDENDE_PARTIJ)).thenReturn(geldigeVerzendendePartij);
        Mockito.when(partijRegister.zoekPartijOpPartijCode(ONGELDIGE_VERZENDENDE_PARTIJ)).thenReturn(ongeldigeVerzendendePartij);
        subject = new ControleerVrijBerichtNotificatieBerichtAction(berichtenDao, partijRegisterService);
    }

    @Test
    public void testExecuteOK() throws JAXBException {
        bericht = new VrijBerichtNotificatieBericht((VrijBerichtVerwerkVrijBericht) NotificatieXml.SINGLETON.stringToElement(
                new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("VerwerkVrijBericht.xml"))).lines().parallel().collect(
                        Collectors.joining("\n"))).getValue());

        Mockito.when(berichtenDao.leesBericht(1L)).thenReturn(bericht);

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", 1L);

        final Map<String, Object> resultaat = subject.execute(parameters);
    }

    @Test
    public void ontvangerIsGeenGbaPartij() throws JAXBException {
        bericht = new VrijBerichtNotificatieBericht((VrijBerichtVerwerkVrijBericht) NotificatieXml.SINGLETON.stringToElement(
                new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("VerwerkVrijBerichtOngeldigeOntvanger.xml"))).lines().parallel()
                        .collect(
                                Collectors.joining("\n"))).getValue());

        Mockito.when(berichtenDao.leesBericht(1L)).thenReturn(bericht);

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", 1L);

        final Map<String, Object> resultaat = subject.execute(parameters);
        Assert.assertEquals("Waarde moet overeenkomen", "Ontvangende partij (recipient) is niet een GBA partij.", resultaat.get("actieFoutmelding"));
        Assert.assertTrue("Transitie sleutel moeten voorkomen", resultaat.containsKey("transition"));
        Assert.assertEquals("Waarde moet overeenkomen", "1b. Fout ontvanger", resultaat.get("transition"));
    }

    @Test
    public void verzenderIsGeenBrpPartij() throws JAXBException {
        bericht = new VrijBerichtNotificatieBericht((VrijBerichtVerwerkVrijBericht) NotificatieXml.SINGLETON.stringToElement(
                new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("VerwerkVrijBerichtOngeldigeVerzender.xml"))).lines().parallel()
                        .collect(Collectors.joining("\n"))).getValue());

        Mockito.when(berichtenDao.leesBericht(1L)).thenReturn(bericht);

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", 1L);

        final Map<String, Object> resultaat = subject.execute(parameters);
        Assert.assertEquals("Waarde moet overeenkomen", "Verzendende partij (originator) is niet een BRP partij.", resultaat.get("actieFoutmelding"));
        Assert.assertTrue("Transitie sleutel moeten voorkomen", resultaat.containsKey("transition"));
        Assert.assertEquals("Waarde moet overeenkomen", "1a. Fout verzender", resultaat.get("transition"));
    }

    @Test
    public void zowelOntvangerAlsVerzenderKloppenNiet() throws JAXBException {
        bericht = new VrijBerichtNotificatieBericht((VrijBerichtVerwerkVrijBericht) NotificatieXml.SINGLETON.stringToElement(
                new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("VerwerkVrijBerichtOngeldigeVerzenderEnOntvanger.xml"))).lines()
                        .parallel().collect(Collectors.joining("\n"))).getValue());

        Mockito.when(berichtenDao.leesBericht(1L)).thenReturn(bericht);

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", 1L);

        final Map<String, Object> resultaat = subject.execute(parameters);
        Assert.assertTrue("Sleutel actieFoutmelding moet voorkomen", resultaat.containsKey("actieFoutmelding"));
        Assert.assertEquals("Waarde moet overeenkomen", "Verzendende partij (originator) is niet een BRP partij.", resultaat.get("actieFoutmelding"));
        Assert.assertTrue("Transitie sleutel moeten voorkomen", resultaat.containsKey("transition"));
        Assert.assertEquals("Waarde moet overeenkomen", "1a. Fout verzender", resultaat.get("transition"));
    }
}
