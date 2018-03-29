/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.runtime.listeners.handlers;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AfnemersindicatieAntwoordRecordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AfnemersindicatiesAntwoordBericht;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.InitVullingAfnemersindicatie;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.InitVullingAfnemersindicatieStapel;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.InitVullingAfnemersindicatieStapelPk;
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
public class AfnemersindicatieAntwoordHandlerTest {

    @Mock
    private LoggingService loggingService;

    @InjectMocks
    private AfnemersindicatieAntwoordHandler subject;

    @Test
    public void test() {
        final String messageId = "msg-id";
        final String correlatieId = "IV.IND-0123456789";
        final AfnemersindicatiesAntwoordBericht antwoordBericht = new AfnemersindicatiesAntwoordBericht();
        antwoordBericht.setCorrelationId(correlatieId);
        AfnemersindicatieAntwoordRecordType afnemersindicatieAntwoord1 = new AfnemersindicatieAntwoordRecordType();
        afnemersindicatieAntwoord1.setStapelNummer(1);
        afnemersindicatieAntwoord1.setStatus(StatusType.TOEGEVOEGD);
        AfnemersindicatieAntwoordRecordType afnemersindicatieAntwoord2 = new AfnemersindicatieAntwoordRecordType();
        afnemersindicatieAntwoord2.setStapelNummer(2);
        afnemersindicatieAntwoord2.setStatus(StatusType.FOUT);
        antwoordBericht.getAfnemersindicaties().add(afnemersindicatieAntwoord1);
        antwoordBericht.getAfnemersindicaties().add(afnemersindicatieAntwoord2);

        InitVullingAfnemersindicatie initVullingAfnemersindicatie = new InitVullingAfnemersindicatie(123456789L);
        initVullingAfnemersindicatie.getStapels().add(new InitVullingAfnemersindicatieStapel(new InitVullingAfnemersindicatieStapelPk(1L, (short) 1)));
        initVullingAfnemersindicatie.getStapels().add(new InitVullingAfnemersindicatieStapel(new InitVullingAfnemersindicatieStapelPk(1L, (short) 2)));
        initVullingAfnemersindicatie.getStapels().add(new InitVullingAfnemersindicatieStapel(new InitVullingAfnemersindicatieStapelPk(1L, (short) 3)));
        Mockito.when(loggingService.zoekInitVullingAfnemerindicatie("0123456789")).thenReturn(initVullingAfnemersindicatie);

        // Execute
        subject.verwerk(antwoordBericht, messageId, correlatieId);

        // Verify
        Mockito.verify(loggingService, Mockito.times(1)).zoekInitVullingAfnemerindicatie("0123456789");

        final ArgumentCaptor<InitVullingAfnemersindicatie> entityCaptor =
                ArgumentCaptor.forClass(InitVullingAfnemersindicatie.class);
        Mockito.verify(loggingService,
                Mockito.times(1)).persisteerInitVullingAfnemerindicatie(entityCaptor.capture());
        Mockito.verifyNoMoreInteractions(loggingService);

        final InitVullingAfnemersindicatie entity = entityCaptor.getValue();

        Map<Short, InitVullingAfnemersindicatieStapel> stapels =
                entity.getStapels().stream().collect(Collectors.toMap(
                        stapel -> stapel.getStapelPk().getStapelNr()
                        ,Function.identity()));

        Assert.assertNotNull(stapels.get((short)1));
        Assert.assertEquals(StatusType.TOEGEVOEGD.toString(), stapels.get((short)1).getConversieResultaat());
        Assert.assertNotNull(stapels.get((short)2));
        Assert.assertEquals(StatusType.FOUT.toString(), stapels.get((short)2).getConversieResultaat());
        Assert.assertNotNull(stapels.get((short)3));
        Assert.assertEquals(null, stapels.get((short)3).getConversieResultaat());
    }

    @Test
    public void testLogNietGevonden() {
        final String messageId = "msg-id";
        final String correlatieId = "IV.IND-0123456789";
        final AfnemersindicatiesAntwoordBericht antwoordBericht = new AfnemersindicatiesAntwoordBericht();
        antwoordBericht.setCorrelationId(correlatieId);

        Mockito.when(loggingService.zoekInitVullingAfnemerindicatie("0123456789")).thenReturn(null);

        // Execute
        subject.verwerk(antwoordBericht, messageId, correlatieId);

        // Verify
        Mockito.verify(loggingService, Mockito.times(1)).zoekInitVullingAfnemerindicatie("0123456789");
        Mockito.verifyNoMoreInteractions(loggingService);
    }
}
