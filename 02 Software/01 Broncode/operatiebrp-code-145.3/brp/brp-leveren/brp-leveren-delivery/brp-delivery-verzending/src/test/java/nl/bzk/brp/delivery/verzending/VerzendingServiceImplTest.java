/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.verzending;

import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Protocolleringsniveau;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Richting;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.brp.archivering.domain.algemeen.ArchiveringOpdracht;
import nl.bzk.brp.domain.internbericht.bijhoudingsnotificatie.BijhoudingsplanNotificatieBericht;
import nl.bzk.brp.domain.internbericht.verzendingmodel.SynchronisatieBerichtGegevens;
import nl.bzk.brp.domain.internbericht.vrijbericht.VrijBerichtGegevens;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class VerzendingServiceImplTest {

    @InjectMocks
    private VerzendingServiceImpl verzendingService;

    @Mock
    private Verzending.ArchiveerBerichtStap archiveerBerichtStap;

    @Mock
    private Verzending.ProtocolleringService protocolleerStap;

    @Mock
    private Verzending.BrpStelselService BRPStelselService;

    @Mock
    private Verzending.GBAStelselService GBAStelselService;


    @Test
    public void testBRPStappenSynchronisatie() throws Exception {
        final SynchronisatieBerichtGegevens berichtGegevens = maakSynchronisatieBerichtGegevens().metStelsel(Stelsel.BRP).build();

        verzendingService.verwerkSynchronisatieBericht(berichtGegevens);

        Mockito.verify(archiveerBerichtStap, Mockito.times(1)).archiveerSynchronisatieBericht(berichtGegevens.getArchiveringOpdracht());
        Mockito.verify(protocolleerStap, Mockito.times(1)).verwerkProtocollering(berichtGegevens);
        Mockito.verify(BRPStelselService, Mockito.times(1)).verzendSynchronisatieBericht(berichtGegevens);
        Mockito.verifyZeroInteractions(GBAStelselService);
    }


    @Test
    public void testBRPStappenNotificatie() throws Exception {
        final BijhoudingsplanNotificatieBericht bijhoudingsplanNotificatieBericht = BijhoudingsNotificatieBerichtTestUtil
                .maakBijhoudingsplanNotificatieBericht();
        bijhoudingsplanNotificatieBericht.setOntvangendeSysteem("Bijhoudingsysteem");

        verzendingService.verwerkBijhoudingsNotificatieBericht(bijhoudingsplanNotificatieBericht);

        Mockito.verify(archiveerBerichtStap, Mockito.times(1)).archiveerBijhoudingsNotificatieBericht(bijhoudingsplanNotificatieBericht);
        Mockito.verify(BRPStelselService, Mockito.times(1)).verzendBijhoudingsNotificatieBericht(bijhoudingsplanNotificatieBericht);
        Mockito.verifyZeroInteractions(GBAStelselService);
    }

    @Test
    public void testBRPStappenVrijBericht() throws Exception {
        final ArchiveringOpdracht archiveringOpdracht = new ArchiveringOpdracht(Richting.UITGAAND, ZonedDateTime.now());
        archiveringOpdracht.setData("Bericht");
        archiveringOpdracht.setOntvangendePartijId((short) 10100);
        final VrijBerichtGegevens vrijBerichtGegevens = VrijBerichtGegevens.builder()
                .metStelsel(Stelsel.BRP)
                .metBrpEndpointUrl("htpp://endpoint")
                .metArchiveringOpdracht(archiveringOpdracht)
                .build();

        verzendingService.verwerkVrijBericht(vrijBerichtGegevens);

        Mockito.verify(archiveerBerichtStap, Mockito.times(1)).archiveerVrijBericht(vrijBerichtGegevens.getArchiveringOpdracht());
        Mockito.verify(BRPStelselService, Mockito.times(1)).verzendVrijBericht(vrijBerichtGegevens);
        Mockito.verifyZeroInteractions(GBAStelselService);
    }

    @Test
    public void testBRPStappenVrijBericht_Gba() throws Exception {
        final ArchiveringOpdracht archiveringOpdracht = new ArchiveringOpdracht(Richting.UITGAAND, ZonedDateTime.now());
        archiveringOpdracht.setData("Bericht");
        archiveringOpdracht.setOntvangendePartijId((short) 10100);
        final VrijBerichtGegevens vrijBerichtGegevens = VrijBerichtGegevens.builder()
                .metStelsel(Stelsel.GBA)
                .metBrpEndpointUrl("htpp://endpoint")
                .metArchiveringOpdracht(archiveringOpdracht)
                .build();

        verzendingService.verwerkVrijBericht(vrijBerichtGegevens);

        Mockito.verify(archiveerBerichtStap, Mockito.times(1)).archiveerVrijBericht(vrijBerichtGegevens.getArchiveringOpdracht());
        Mockito.verifyZeroInteractions(BRPStelselService);
        Mockito.verify(GBAStelselService, Mockito.times(1)).verzendVrijBericht(vrijBerichtGegevens);
    }

    @Test
    public void testBRPStappenNotificatieOntvangendSysteemOnbekend() throws Exception {
        final BijhoudingsplanNotificatieBericht bijhoudingsplanNotificatieBericht = BijhoudingsNotificatieBerichtTestUtil
                .maakBijhoudingsplanNotificatieBericht();
        bijhoudingsplanNotificatieBericht.setOntvangendeSysteem("Onbekend");

        verzendingService.verwerkBijhoudingsNotificatieBericht(bijhoudingsplanNotificatieBericht);

        Mockito.verifyZeroInteractions(BRPStelselService);
        Mockito.verifyZeroInteractions(GBAStelselService);  //zolang er nog geen implementatie voor GBA is...
    }


    @Test
    public void testGBAStappen() throws Exception {
        final SynchronisatieBerichtGegevens berichtGegevens = maakSynchronisatieBerichtGegevens().metStelsel(Stelsel.GBA).build();

        verzendingService.verwerkSynchronisatieBericht(berichtGegevens);

        Mockito.verifyZeroInteractions(archiveerBerichtStap);
        Mockito.verify(protocolleerStap, Mockito.times(1)).verwerkProtocollering(berichtGegevens);
        Mockito.verify(GBAStelselService, Mockito.times(1)).verzendLo3Bericht(berichtGegevens);
        Mockito.verifyZeroInteractions(BRPStelselService);
    }

    private SynchronisatieBerichtGegevens.Builder maakSynchronisatieBerichtGegevens() {
        final ArchiveringOpdracht archiveringOpdracht = new ArchiveringOpdracht(Richting.UITGAAND, ZonedDateTime.now());
        archiveringOpdracht.setData("XML");
        archiveringOpdracht.setOntvangendePartijId((short) 12345);
        archiveringOpdracht.setLeveringsAutorisatieId(12345);
        return SynchronisatieBerichtGegevens.builder()
                .metArchiveringOpdracht(archiveringOpdracht)
                .metProtocolleringsniveau(Protocolleringsniveau.GEEN_BEPERKINGEN);
    }

}
