/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.runtime.listeners.handlers;

import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpAntwoordBericht;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.InitVullingLog;
import nl.bzk.migratiebrp.init.logging.runtime.service.LoggingService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SynchroniseerNaarBrpAntwoordHandlerTest {

    @Mock
    private LoggingService loggingService;

    @InjectMocks
    private SynchroniseerNaarBrpAntwoordHandler subject;

    @Test
    public void test() {
        final String messageId = "msg-id";
        final String correlatieId = "1234567890";
        final SynchroniseerNaarBrpAntwoordBericht antwoordBericht = new SynchroniseerNaarBrpAntwoordBericht();
        antwoordBericht.setCorrelationId(correlatieId);
        antwoordBericht.setStatus(StatusType.FOUT);

        Mockito.when(loggingService.zoekInitVullingLog(correlatieId)).thenReturn(new InitVullingLog());

        // Execute
        subject.verwerk(antwoordBericht, messageId, correlatieId);

        // Verify
        Mockito.verify(loggingService, Mockito.times(1)).zoekInitVullingLog(correlatieId);

        final ArgumentCaptor<InitVullingLog> entityCaptor = ArgumentCaptor.forClass(InitVullingLog.class);
        Mockito.verify(loggingService, Mockito.times(1)).persisteerInitVullingLog(entityCaptor.capture());
        Mockito.verifyNoMoreInteractions(loggingService);

        final InitVullingLog entity = entityCaptor.getValue();
        Assert.assertEquals("FOUT", entity.getConversieResultaat());
        Assert.assertNull("Foutmelding niet leeg", entity.getFoutmelding());
    }

    @Test
    public void testLogNietGevonden() {
        final String messageId = "msg-id";
        final String correlatieId = "1234567890";
        final SynchroniseerNaarBrpAntwoordBericht antwoordBericht = new SynchroniseerNaarBrpAntwoordBericht();
        antwoordBericht.setCorrelationId(correlatieId);

        Mockito.when(loggingService.zoekInitVullingLog(correlatieId)).thenReturn(null);

        // Execute
        subject.verwerk(antwoordBericht, messageId, correlatieId);

        // Verify
        Mockito.verify(loggingService, Mockito.times(1)).zoekInitVullingLog(correlatieId);
        Mockito.verifyNoMoreInteractions(loggingService);
    }
}
