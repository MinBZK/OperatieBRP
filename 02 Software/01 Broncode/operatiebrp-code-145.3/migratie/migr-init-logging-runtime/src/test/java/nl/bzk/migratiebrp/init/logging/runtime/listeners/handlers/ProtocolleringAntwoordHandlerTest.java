/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.runtime.listeners.handlers;

import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ProtocolleringAntwoord;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ProtocolleringAntwoordBericht;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.InitVullingProtocollering;
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
public class ProtocolleringAntwoordHandlerTest {

    @Mock
    private LoggingService loggingService;

    @InjectMocks
    private ProtocolleringAntwoordHandler subject;

    @Test
    public void test() {
        final String messageId = "msg-id";
        final String correlatieId = "IV.PROT-123456789";
        final ProtocolleringAntwoordBericht antwoordBericht = new ProtocolleringAntwoordBericht();
        final ProtocolleringAntwoord antwoord = new ProtocolleringAntwoord(123456789);
        antwoordBericht.setCorrelationId(correlatieId);
        antwoord.setStatus(StatusType.FOUT);
        antwoord.setFoutmelding("Foutje! Bedankt!");
        antwoordBericht.addProtocolleringAntwoord(antwoord);
        final InitVullingProtocollering initvulling = new InitVullingProtocollering();

        Mockito.when(loggingService.zoekInitVullingProtocollering(123456789)).thenReturn(initvulling);

        // Execute
        subject.verwerk(antwoordBericht, messageId, correlatieId);

        // Verify
        Mockito.verify(loggingService, Mockito.times(1)).zoekInitVullingProtocollering(123456789);
        final ArgumentCaptor<InitVullingProtocollering> entityCaptor = ArgumentCaptor.forClass(InitVullingProtocollering.class);
        Mockito.verify(loggingService, Mockito.times(1)).persisteerInitVullingProtocollering(entityCaptor.capture());
        Mockito.verifyNoMoreInteractions(loggingService);

        final InitVullingProtocollering entity = entityCaptor.getValue();
        Assert.assertEquals("FOUT", entity.getConversieResultaat());
        Assert.assertEquals("Foutje! Bedankt!", entity.getFoutmelding());
    }

    @Test
    public void testLogNietGevonden() {
        final String messageId = "msg-id";
        final String correlatieId = "IV.PROT-123456789";
        final ProtocolleringAntwoordBericht antwoordBericht = new ProtocolleringAntwoordBericht();
        final ProtocolleringAntwoord antwoord = new ProtocolleringAntwoord(123456789);
        antwoordBericht.addProtocolleringAntwoord(antwoord);
        antwoordBericht.setCorrelationId(correlatieId);

        Mockito.when(loggingService.zoekInitVullingProtocollering(123456789)).thenReturn(null);

        // Execute
        subject.verwerk(antwoordBericht, messageId, correlatieId);

        // Verify
        Mockito.verify(loggingService, Mockito.times(1)).zoekInitVullingProtocollering(123456789);
        Mockito.verifyNoMoreInteractions(loggingService);
    }
}
