/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.runtime.listeners.handlers;

import nl.bzk.migratiebrp.bericht.model.sync.generated.AutorisatieAntwoordRecordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AutorisatieAntwoordBericht;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.InitVullingAutorisatie;
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
public class AutorisatieAntwoordHandlerTest {

    @Mock
    private LoggingService loggingService;

    @InjectMocks
    private AutorisatieAntwoordHandler subject;

    @Test
    public void test() {
        final String messageId = "msg-id";
        final String correlatieId = "IV.AUT-123456789";
        final AutorisatieAntwoordBericht antwoordBericht = new AutorisatieAntwoordBericht();
        antwoordBericht.setMessageId(messageId);
        antwoordBericht.setCorrelationId(correlatieId);

        AutorisatieAntwoordRecordType record = new AutorisatieAntwoordRecordType();
        record.setStatus(StatusType.FOUT);
        record.setAutorisatieId(54335L);

        record.setFoutmelding("Foutje! Bedankt!");
        antwoordBericht.getAutorisatieTabelRegels().add(record);

        final InitVullingAutorisatie autorisatie = new InitVullingAutorisatie();
        Mockito.when(loggingService.zoekInitVullingAutorisatie(54335L)).thenReturn(autorisatie);

        // Execute
        subject.verwerk(antwoordBericht, messageId, correlatieId);

        // Verify
        Mockito.verify(loggingService, Mockito.times(1)).zoekInitVullingAutorisatie(54335L);
        final ArgumentCaptor<InitVullingAutorisatie> entityCaptor = ArgumentCaptor.forClass(InitVullingAutorisatie.class);
        Mockito.verify(loggingService, Mockito.times(1)).persisteerInitVullingAutorisatie(entityCaptor.capture());
        Mockito.verifyNoMoreInteractions(loggingService);

        final InitVullingAutorisatie entity = entityCaptor.getValue();
        Assert.assertEquals("FOUT", entity.getConversieResultaat());
        Assert.assertEquals("Foutje! Bedankt!", entity.getConversieMelding());
    }

    @Test
    public void testLogNietGevonden() {
        final String messageId = "msg-id";
        final String correlatieId = "IV.AUT-123456789";
        final AutorisatieAntwoordBericht antwoordBericht = new AutorisatieAntwoordBericht();
        antwoordBericht.setMessageId(messageId);
        antwoordBericht.setCorrelationId(correlatieId);

        AutorisatieAntwoordRecordType record = new AutorisatieAntwoordRecordType();
        record.setStatus(StatusType.FOUT);
        record.setAutorisatieId(54335L);

        record.setFoutmelding("Foutje! Bedankt!");
        antwoordBericht.getAutorisatieTabelRegels().add(record);

        Mockito.when(loggingService.zoekInitVullingAutorisatie(54335L)).thenReturn(null);

        // Execute
        subject.verwerk(antwoordBericht, messageId, correlatieId);

        // Verify
        Mockito.verify(loggingService, Mockito.times(1)).zoekInitVullingAutorisatie(54335L);
        Mockito.verifyNoMoreInteractions(loggingService);
    }
}
