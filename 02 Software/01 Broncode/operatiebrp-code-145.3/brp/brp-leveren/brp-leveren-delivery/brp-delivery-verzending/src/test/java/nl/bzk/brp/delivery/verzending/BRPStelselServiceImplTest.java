/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.verzending;

import com.google.common.collect.Lists;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.ZonedDateTime;
import javax.xml.ws.WebServiceException;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangBijhoudingsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Richting;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.brp.archivering.domain.algemeen.ArchiveringOpdracht;
import nl.bzk.brp.bijhouding.bericht.parser.ParseException;
import nl.bzk.brp.domain.internbericht.bijhoudingsnotificatie.BijhoudingsplanNotificatieBericht;
import nl.bzk.brp.domain.internbericht.verzendingmodel.SynchronisatieBerichtGegevens;
import nl.bzk.brp.domain.internbericht.vrijbericht.VrijBerichtGegevens;
import nl.bzk.brp.protocollering.domain.algemeen.ProtocolleringOpdracht;
import nl.bzk.brp.service.cache.PartijCache;
import nl.bzk.brp.service.dalapi.ToegangBijhoudingautorisatieRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * BRPStelselServiceImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class BRPStelselServiceImplTest {

    private static final String TEST_BERICHT_XML_BESTAND = "test_bericht.xml";

    @InjectMocks
    private BRPStelselServiceImpl verzendBRPStap;
    @Mock
    private Verzending.VerwerkPersoonWebServiceClient webServiceClientVerwerkPersoon;
    @Mock
    private Verzending.VerwerkBijhoudingsNotificatieWebServiceClient webServiceClientBijhoudingsNotificatie;
    @Mock
    private Verzending.VerwerkVrijBerichtWebServiceClient webServiceClientVrijBericht;
    @Mock
    private ToegangBijhoudingautorisatieRepository toegangBijhoudingautorisatieRepository;
    @Mock
    private PartijCache partijCache;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testVerzendLeverberichtHappyFlow() throws Exception {
        SynchronisatieBerichtGegevens synchronisatieBerichtGegevens = maakSynchronisatieBerichtgegevens();

        Mockito.doNothing().when(webServiceClientVerwerkPersoon).verstuurRequest(Mockito.any(), Mockito.anyString());

        verzendBRPStap.verzendSynchronisatieBericht(synchronisatieBerichtGegevens);

        Mockito.verify(webServiceClientVerwerkPersoon).verstuurRequest(Mockito.any(), Mockito.anyString());
    }

    @Test
    public void testVerzendLeverberichtGeenBericht() throws Exception {
        SynchronisatieBerichtGegevens synchronisatieBerichtGegevens = SynchronisatieBerichtGegevens.builder()
                .metArchiveringOpdracht(new ArchiveringOpdracht(Richting.UITGAAND, ZonedDateTime.now())).metBrpEndpointURI("uri").build();
        expectedException.expect(VerzendExceptie.class);
        expectedException.expectMessage("Bericht is niet gevonden op de context en niet verstuurd! Endpoint: uri");

        verzendBRPStap.verzendSynchronisatieBericht(synchronisatieBerichtGegevens);

        Mockito.verifyZeroInteractions(webServiceClientVerwerkPersoon);
    }


    @Test
    public void testVerzendLeverberichtWebserviceExceptie() throws Exception {
        SynchronisatieBerichtGegevens synchronisatieBerichtGegevens = maakSynchronisatieBerichtgegevens();
        expectedException.expect(VerzendExceptie.class);
        expectedException.expectMessage("Het is niet gelukt om het bericht te verzenden voor toegang leveringsautorisatie 1 : " +
                synchronisatieBerichtGegevens.getArchiveringOpdracht().getData());

        Mockito.doThrow(WebServiceException.class).when(webServiceClientVerwerkPersoon).verstuurRequest(Mockito.any(), Mockito.any());

        verzendBRPStap.verzendSynchronisatieBericht(synchronisatieBerichtGegevens);
    }

    @Test
    public void testVerzendBijhoudingsNotificatieBerichtHappyFlow() throws IOException, ParseException {
        BijhoudingsplanNotificatieBericht verwerkBijhoudingsplanBericht = BijhoudingsNotificatieBerichtTestUtil.maakBijhoudingsplanNotificatieBericht();
        ToegangBijhoudingsautorisatie toegangBijhoudingsautorisatie = Mockito.mock(ToegangBijhoudingsautorisatie.class);
        Mockito.when(toegangBijhoudingsautorisatie.getAfleverpunt()).thenReturn("endpointDummy");
        Mockito.when(toegangBijhoudingautorisatieRepository.findByGeautoriseerde(Mockito.any())).thenReturn(Lists
                .newArrayList(toegangBijhoudingsautorisatie));
        Mockito.doNothing().when(webServiceClientBijhoudingsNotificatie).verstuurRequest(Mockito.any(), Mockito.anyString());

        verzendBRPStap.verzendBijhoudingsNotificatieBericht(verwerkBijhoudingsplanBericht);

        Mockito.verify(partijCache).geefPartijMetId(Mockito.anyShort());
        Mockito.verify(webServiceClientBijhoudingsNotificatie).verstuurRequest(Mockito.any(), Mockito.anyString());
    }

    @Test
    public void testVerzendBijhoudingsNotificatieBerichtGeenAfleverpunten() throws IOException, ParseException {
        BijhoudingsplanNotificatieBericht verwerkBijhoudingsplanBericht = BijhoudingsNotificatieBerichtTestUtil.maakBijhoudingsplanNotificatieBericht();
        ToegangBijhoudingsautorisatie toegangBijhoudingsautorisatie = Mockito.mock(ToegangBijhoudingsautorisatie.class);
        Mockito.when(toegangBijhoudingsautorisatie.getAfleverpunt()).thenReturn("endpointDummy");
        Mockito.when(toegangBijhoudingautorisatieRepository.findByGeautoriseerde(Mockito.any())).thenReturn(Lists
                .newArrayList());
        Mockito.doNothing().when(webServiceClientBijhoudingsNotificatie).verstuurRequest(Mockito.any(), Mockito.anyString());

        verzendBRPStap.verzendBijhoudingsNotificatieBericht(verwerkBijhoudingsplanBericht);

        Mockito.verify(partijCache).geefPartijMetId(Mockito.anyShort());
        Mockito.verifyZeroInteractions(webServiceClientBijhoudingsNotificatie);
    }

    @Test
    public void testVerzendBijhoudingsNotificatieBerichtGeenLeveringsBericht() throws IOException, ParseException {
        expectedException.expect(VerzendExceptie.class);
        expectedException.expectMessage("Bericht is niet gevonden op de context en niet verstuurd! Endpoint: endpointDummy");

        BijhoudingsplanNotificatieBericht verwerkBijhoudingsplanBericht = BijhoudingsNotificatieBerichtTestUtil
                .maakBijhoudingsplanNotificatieBerichtZonderLeveringsbericht();
        ToegangBijhoudingsautorisatie toegangBijhoudingsautorisatie = Mockito.mock(ToegangBijhoudingsautorisatie.class);
        Mockito.when(toegangBijhoudingsautorisatie.getAfleverpunt()).thenReturn("endpointDummy");
        Mockito.when(toegangBijhoudingautorisatieRepository.findByGeautoriseerde(Mockito.any())).thenReturn(Lists
                .newArrayList(toegangBijhoudingsautorisatie));
        Mockito.doNothing().when(webServiceClientBijhoudingsNotificatie).verstuurRequest(Mockito.any(), Mockito.anyString());

        verzendBRPStap.verzendBijhoudingsNotificatieBericht(verwerkBijhoudingsplanBericht);

        Mockito.verify(partijCache).geefPartijMetId(Mockito.anyShort());
        Mockito.verifyZeroInteractions(webServiceClientBijhoudingsNotificatie);
    }


    @Test
    public void testVerzendBijhoudingsNotificatieBerichtWebserviceExceptie() throws IOException, ParseException {
        BijhoudingsplanNotificatieBericht verwerkBijhoudingsplanBericht = BijhoudingsNotificatieBerichtTestUtil.maakBijhoudingsplanNotificatieBericht();
        ToegangBijhoudingsautorisatie toegangBijhoudingsautorisatie = Mockito.mock(ToegangBijhoudingsautorisatie.class);
        Mockito.when(toegangBijhoudingsautorisatie.getAfleverpunt()).thenReturn("endpointDummy");
        Mockito.when(toegangBijhoudingautorisatieRepository.findByGeautoriseerde(Mockito.any())).thenReturn(Lists
                .newArrayList(toegangBijhoudingsautorisatie));
        Mockito.doThrow(WebServiceException.class).when(webServiceClientBijhoudingsNotificatie)
                .verstuurRequest(Mockito.any(), Mockito.anyString());

        expectedException.expect(VerzendExceptie.class);
        expectedException.expectMessage("Het is niet gelukt om het notificatiebericht te verzenden voor partij 1 : " +
                verwerkBijhoudingsplanBericht.getVerwerkBijhoudingsplanBericht());

        verzendBRPStap.verzendBijhoudingsNotificatieBericht(verwerkBijhoudingsplanBericht);
    }

    @Test
    public void testVerzendVrijBerichtHappyFlow() throws Exception {
        ArchiveringOpdracht archiveringOpdracht = new ArchiveringOpdracht(Richting.UITGAAND, ZonedDateTime.now());
        archiveringOpdracht.setData("Bericht");
        //@formatter:off
        VrijBerichtGegevens vrijBerichtGegevens =
                VrijBerichtGegevens.builder()
                        .metStelsel(Stelsel.BRP)
                        .metBrpEndpointUrl("url")
                        .metArchiveringOpdracht(archiveringOpdracht).build();
        //@formatter:on

        Mockito.doNothing().when(webServiceClientVrijBericht).verstuurRequest(Mockito.any(), Mockito.anyString());

        verzendBRPStap.verzendVrijBericht(vrijBerichtGegevens);

        Mockito.verify(webServiceClientVrijBericht).verstuurRequest(Mockito.any(), Mockito.anyString());
    }

    @Test
    public void testVerzendVrijBerichtGeenBericht() throws Exception {
        ArchiveringOpdracht archiveringOpdracht = new ArchiveringOpdracht(Richting.UITGAAND, ZonedDateTime.now());
        //@formatter:off
        VrijBerichtGegevens vrijBerichtGegevens =
                VrijBerichtGegevens.builder()
                        .metStelsel(Stelsel.BRP)
                        .metBrpEndpointUrl("uri")
                        .metArchiveringOpdracht(archiveringOpdracht).build();
        //@formatter:on
        Mockito.doNothing().when(webServiceClientVrijBericht).verstuurRequest(Mockito.any(), Mockito.anyString());

        expectedException.expect(VerzendExceptie.class);
        expectedException.expectMessage("Bericht is niet gevonden op de context en niet verstuurd! Endpoint: uri");
        verzendBRPStap.verzendVrijBericht(vrijBerichtGegevens);

        Mockito.verify(webServiceClientVrijBericht).verstuurRequest(Mockito.any(), Mockito.anyString());
    }

    @Test
    public void testVerzendVrijBerichtWebserviceExceptie() throws Exception {
        ArchiveringOpdracht archiveringOpdracht = new ArchiveringOpdracht(Richting.UITGAAND, ZonedDateTime.now());
        archiveringOpdracht.setData("Bericht");
        archiveringOpdracht.setOntvangendePartijId((short) 1);

        //@formatter:off
        VrijBerichtGegevens vrijBerichtGegevens =
                VrijBerichtGegevens.builder()
                        .metStelsel(Stelsel.BRP)
                        .metBrpEndpointUrl("uri")
                        .metArchiveringOpdracht(archiveringOpdracht).build();
        //@formatter:on
        Mockito.doThrow(WebServiceException.class).when(webServiceClientVrijBericht).verstuurRequest(Mockito.any(), Mockito.anyString());

        expectedException.expect(VerzendExceptie.class);
        expectedException
                .expectMessage(String.format("Het is niet gelukt om het vrij bericht te verzenden voor partij %1d : %2s",
                        archiveringOpdracht.getOntvangendePartijId(),
                        archiveringOpdracht.getData()));
        verzendBRPStap.verzendVrijBericht(vrijBerichtGegevens);

        Mockito.verify(webServiceClientVrijBericht).verstuurRequest(Mockito.any(), Mockito.anyString());
    }


    /**
     * Leest een bestand als string.
     * @param bestandsnaam bestandsnaam
     * @return string representatie van bestand
     * @throws IOException the iO exception
     */
    private String leesBestandAlsString(final String bestandsnaam) throws IOException {
        final StringBuilder resultaat = new StringBuilder();
        final BufferedReader lezer = new BufferedReader(
                new InputStreamReader(
                        Thread.currentThread().getContextClassLoader().getResourceAsStream(bestandsnaam))
        );
        String line;
        while ((line = lezer.readLine()) != null) {
            resultaat.append(line);
        }
        lezer.close();
        return resultaat.toString();
    }

    private SynchronisatieBerichtGegevens maakSynchronisatieBerichtgegevens() throws Exception {
        final ProtocolleringOpdracht protocolleringOpdracht = new ProtocolleringOpdracht();
        protocolleringOpdracht.setAdministratieveHandelingId(1L);
        protocolleringOpdracht.setToegangLeveringsautorisatieId(1);
        final ArchiveringOpdracht archiveringOpdracht = new ArchiveringOpdracht(Richting.UITGAAND, ZonedDateTime.now());
        archiveringOpdracht.setData(leesBestandAlsString(TEST_BERICHT_XML_BESTAND));
        archiveringOpdracht.setOntvangendePartijId((short) 12345);
        archiveringOpdracht.setLeveringsAutorisatieId(123455);
        archiveringOpdracht.addTeArchiverenPersoon(1L);
        archiveringOpdracht.addTeArchiverenPersoon(2L);
        archiveringOpdracht.addTeArchiverenPersoon(3L);

        final SynchronisatieBerichtGegevens synchronisatieBerichtGegevens = SynchronisatieBerichtGegevens.builder()
                .metArchiveringOpdracht(archiveringOpdracht)
                .metProtocolleringOpdracht(protocolleringOpdracht)
                .metBrpEndpointURI("uri")
                .build();
        return synchronisatieBerichtGegevens;
    }
}
