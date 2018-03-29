/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.verzending;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Richting;
import nl.bzk.brp.archivering.domain.algemeen.ArchiveringOpdracht;
import nl.bzk.brp.archivering.service.algemeen.ArchiefService;
import nl.bzk.brp.domain.internbericht.bijhoudingsnotificatie.BijhoudingsplanNotificatieBericht;
import nl.bzk.brp.service.cache.PartijCache;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ArchiveerBerichtStapImplTest {

    @InjectMocks
    private ArchiveerBerichtStapImpl archiveerBerichtStapImpl;

    @Mock
    private ArchiefService archiefService;
    @Mock
    private PartijCache partijCache;

    @Test
    public final void archiveerSynchronisatieBericht() throws Exception {
        final ArchiveringOpdracht archiveringBericht = new ArchiveringOpdracht(Richting.UITGAAND, ZonedDateTime.now());
        archiveerBerichtStapImpl.archiveerSynchronisatieBericht(archiveringBericht);

        Mockito.verify(archiefService).archiveer(archiveringBericht);
    }

    @Test
    public final void archiveerBijhoudingsNotificatieBericht() throws Exception {
        final BijhoudingsplanNotificatieBericht bijhoudingsplanNotificatieBericht = BijhoudingsNotificatieBerichtTestUtil
                .maakBijhoudingsplanNotificatieBericht();
        when(partijCache.geefPartij(bijhoudingsplanNotificatieBericht.getZendendePartijCode()))
                .thenReturn(new Partij("test", bijhoudingsplanNotificatieBericht.getZendendePartijCode()));
        when(partijCache.geefPartij(bijhoudingsplanNotificatieBericht.getOntvangendePartijCode()))
                .thenReturn(new Partij("test", bijhoudingsplanNotificatieBericht.getOntvangendePartijCode()));

        archiveerBerichtStapImpl.archiveerBijhoudingsNotificatieBericht(bijhoudingsplanNotificatieBericht);

        Mockito.verify(archiefService).archiveer(any());
    }

    @Test
    public final void archiveerVrijBericht() {
        final ArchiveringOpdracht archiveringOpdracht = new ArchiveringOpdracht(Richting.UITGAAND, ZonedDateTime.now());
        archiveerBerichtStapImpl.archiveerVrijBericht(archiveringOpdracht);

        Mockito.verify(archiefService).archiveer(archiveringOpdracht);
    }
}
