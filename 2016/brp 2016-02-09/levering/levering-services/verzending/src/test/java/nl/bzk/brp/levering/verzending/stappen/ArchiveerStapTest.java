/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.verzending.stappen;

import javax.jms.Message;
import nl.bzk.brp.levering.verzending.context.BerichtContext;
import nl.bzk.brp.levering.verzending.service.impl.VerwerkContext;
import nl.bzk.brp.model.internbericht.SynchronisatieBerichtGegevens;
import nl.bzk.brp.model.operationeel.ber.BerichtModel;
import nl.bzk.brp.webservice.business.service.ArchiveringService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.util.Assert;

@RunWith(MockitoJUnitRunner.class)
public class ArchiveerStapTest {

    @InjectMocks
    private final ArchiveerStap archiveerStap = new ArchiveerStap();

    @Mock
    private ArchiveringService archiveringService;

    @Test
    public void testProces() throws Exception {
        final Message jmsBericht = Mockito.mock(Message.class);
        final BerichtContext berichtContext = new BerichtContext(new VerwerkContext(0), jmsBericht);
        berichtContext.setSynchronisatieBerichtGegevens(new SynchronisatieBerichtGegevens());

        final BerichtModel berichtModel = new BerichtModel();
        Mockito.when(archiveringService.archiveer(berichtContext.getSynchronisatieBerichtGegevens())).thenReturn(berichtModel);
        archiveerStap.process(berichtContext);

        Assert.isTrue(berichtContext.getBerichtArchiefModel() == berichtModel);
    }

    @Test(expected = RuntimeException.class)
    public final void testProcessMetArchiveerFout() throws Exception {

        final Message jmsBericht = Mockito.mock(Message.class);
        final BerichtContext berichtContext = new BerichtContext(new VerwerkContext(0), jmsBericht);
        berichtContext.setSynchronisatieBerichtGegevens(new SynchronisatieBerichtGegevens());

        Mockito.when(archiveringService.archiveer(berichtContext.getSynchronisatieBerichtGegevens())).thenThrow(new RuntimeException());
        archiveerStap.process(berichtContext);
    }

}
