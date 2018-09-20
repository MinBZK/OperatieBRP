/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.verzending.service.impl;

import javax.jms.Message;
import nl.bzk.brp.levering.verzending.context.BerichtContext;
import nl.bzk.brp.levering.verzending.stappen.ArchiveerStap;
import nl.bzk.brp.levering.verzending.stappen.ProtocolleerStap;
import nl.bzk.brp.levering.verzending.stappen.VerrijkBerichtStap;
import nl.bzk.brp.levering.verzending.stappen.VerzendBRPStap;
import nl.bzk.brp.levering.verzending.stappen.VerzendLO3Stap;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Stelsel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class StappenServiceImplTest {

    @InjectMocks
    private StappenServiceImpl stappenService = new StappenServiceImpl();

    @Mock
    private VerrijkBerichtStap verrijkBerichtStap;

    @Mock
    private ArchiveerStap archiveerStap;

    @Mock
    private ProtocolleerStap protocolleerStap;

    @Mock
    private VerzendBRPStap verzendBRPStap;

    @Mock
    private VerzendLO3Stap verzendLO3Stap;


    @Test
    public void testBRPStappen() throws Exception {

        final Message jmsBericht = Mockito.mock(Message.class);
        Mockito.when(jmsBericht.getJMSType()).thenReturn(Stelsel.BRP.name());
        final BerichtContext berichtContext = new BerichtContext(new VerwerkContext(0), jmsBericht);
        stappenService.voerStappenUit(berichtContext);

        Mockito.verify(verrijkBerichtStap, Mockito.times(1)).process(berichtContext);
        Mockito.verify(archiveerStap, Mockito.times(1)).process(berichtContext);
        Mockito.verify(protocolleerStap, Mockito.times(1)).process(berichtContext);
        Mockito.verify(verzendBRPStap, Mockito.times(1)).process(berichtContext);
        Mockito.verify(verzendLO3Stap, Mockito.times(0)).process(berichtContext);
    }

    @Test
    public void testLO3Stappen() throws Exception {

        final Message jmsBericht = Mockito.mock(Message.class);
        Mockito.when(jmsBericht.getJMSType()).thenReturn(Stelsel.GBA.name());
        final BerichtContext berichtContext = new BerichtContext(new VerwerkContext(0), jmsBericht);
        stappenService.voerStappenUit(berichtContext);

        Mockito.verify(verrijkBerichtStap, Mockito.times(1)).process(berichtContext);
        Mockito.verify(archiveerStap, Mockito.times(1)).process(berichtContext);
        Mockito.verify(protocolleerStap, Mockito.times(1)).process(berichtContext);
        Mockito.verify(verzendBRPStap, Mockito.times(0)).process(berichtContext);
        Mockito.verify(verzendLO3Stap, Mockito.times(1)).process(berichtContext);
    }

    @Test
    public void testNietOndersteundeVerzendStap() throws Exception {

        final Message jmsBericht = Mockito.mock(Message.class);
        Mockito.when(jmsBericht.getJMSType()).thenReturn(Stelsel.DUMMY.name());
        final BerichtContext berichtContext = new BerichtContext(new VerwerkContext(0), jmsBericht);
        stappenService.voerStappenUit(berichtContext);

        Mockito.verify(verrijkBerichtStap, Mockito.times(1)).process(berichtContext);
        Mockito.verify(archiveerStap, Mockito.times(1)).process(berichtContext);
        Mockito.verify(protocolleerStap, Mockito.times(1)).process(berichtContext);
        Mockito.verify(verzendBRPStap, Mockito.times(0)).process(berichtContext);
        Mockito.verify(verzendLO3Stap, Mockito.times(0)).process(berichtContext);
    }
}
