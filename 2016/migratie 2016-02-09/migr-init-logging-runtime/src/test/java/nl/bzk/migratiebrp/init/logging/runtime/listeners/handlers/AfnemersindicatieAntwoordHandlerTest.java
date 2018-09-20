/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.runtime.listeners.handlers;

import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.factory.SyncBerichtFactory;
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
        final String correlatieId = "IV.IND-123456789";
        final AfnemersindicatiesAntwoordBericht antwoordBericht = new AfnemersindicatiesAntwoordBericht();
        antwoordBericht.setCorrelationId(correlatieId);
        antwoordBericht.setStatus(StatusType.FOUT);
        antwoordBericht.setFoutmelding("Foutje! Bedankt!");

        Mockito.when(loggingService.zoekInitVullingAfnemerindicatie(123456789L)).thenReturn(new InitVullingAfnemersindicatie(123456789L));

        // Execute
        subject.verwerk(antwoordBericht, messageId, correlatieId);

        // Verify
        Mockito.verify(loggingService, Mockito.times(1)).zoekInitVullingAfnemerindicatie(123456789L);

        // final ArgumentCaptor<InitVullingAfnemersindicatie> entityCaptor =
        // ArgumentCaptor.forClass(InitVullingAfnemersindicatie.class);
        // Mockito.verify(loggingService,
        // Mockito.times(1)).persisteerInitVullingAfnemerindicatie(entityCaptor.capture());
        // Mockito.verifyNoMoreInteractions(loggingService);
        //
        // final InitVullingAfnemersindicatie entity = entityCaptor.getValue();
        // Assert.assertEquals("Conversieresultaat ongelijk aan FOUT", "FOUT", entity.getConversieResultaat());
        // Assert.assertEquals("Foutmelding niet correct", "Foutje! Bedankt!", entity.getFoutmelding());
    }

    @Test
    public void testLogNietGevonden() {
        final String messageId = "msg-id";
        final String correlatieId = "IV.IND-123456789";
        final AfnemersindicatiesAntwoordBericht antwoordBericht = new AfnemersindicatiesAntwoordBericht();
        antwoordBericht.setCorrelationId(correlatieId);

        Mockito.when(loggingService.zoekInitVullingAfnemerindicatie(123456789L)).thenReturn(null);

        // Execute
        subject.verwerk(antwoordBericht, messageId, correlatieId);

        // Verify
        Mockito.verify(loggingService, Mockito.times(1)).zoekInitVullingAfnemerindicatie(123456789L);
        Mockito.verifyNoMoreInteractions(loggingService);
    }

    @Test
    public void testMetStapelResultaat() {
        final String antwoordBericht =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><afnemersindicatiesAntwoord xmlns=\"http://www.bzk"
                                       + ".nl/migratiebrp/SYNC/0001\"><status>Waarschuwing</status><logging><logRegel><lo3Categorie>14</lo3Categorie"
                                       + "><lo3Stapel>1</lo3Stapel><lo3Voorkomen>0</lo3Voorkomen><severity>300</severity><code>AFN009</code"
                                       + "></logRegel><logRegel><lo3Categorie>14</lo3Categorie><lo3Stapel>4</lo3Stapel><lo3Voorkomen>0</lo3Voorkomen"
                                       + "><severity>300</severity><code>AFN009</code></logRegel><logRegel><lo3Categorie>14</lo3Categorie"
                                       + "><lo3Stapel>10</lo3Stapel><lo3Voorkomen>0</lo3Voorkomen><severity>300</severity><code>AFN009</code"
                                       + "></logRegel><logRegel><lo3Categorie>14</lo3Categorie><lo3Stapel>9</lo3Stapel><lo3Voorkomen>0</lo3Voorkomen"
                                       + "><severity>300</severity><code>AFN009</code></logRegel><logRegel><lo3Categorie>14</lo3Categorie"
                                       + "><lo3Stapel>11</lo3Stapel><lo3Voorkomen>0</lo3Voorkomen><severity>300</severity><code>AFN009</code"
                                       + "></logRegel><logRegel><lo3Categorie>14</lo3Categorie><lo3Stapel>6</lo3Stapel><lo3Voorkomen>0</lo3Voorkomen"
                                       + "><severity>300</severity><code>AFN009</code></logRegel><logRegel><lo3Categorie>14</lo3Categorie"
                                       + "><lo3Stapel>2</lo3Stapel><lo3Voorkomen>0</lo3Voorkomen><severity>300</severity><code>AFN009</code"
                                       + "></logRegel><logRegel><lo3Categorie>14</lo3Categorie><lo3Stapel>5</lo3Stapel><lo3Voorkomen>0</lo3Voorkomen"
                                       + "><severity>300</severity><code>AFN009</code></logRegel><logRegel><lo3Categorie>14</lo3Categorie"
                                       + "><lo3Stapel>8</lo3Stapel><lo3Voorkomen>0</lo3Voorkomen><severity>300</severity><code>AFN009</code"
                                       + "></logRegel><logRegel><lo3Categorie>14</lo3Categorie><lo3Stapel>7</lo3Stapel><lo3Voorkomen>0</lo3Voorkomen"
                                       + "><severity>300</severity><code>AFN009</code></logRegel><logRegel><lo3Categorie>14</lo3Categorie"
                                       + "><lo3Stapel>3</lo3Stapel><lo3Voorkomen>0</lo3Voorkomen><severity>300</severity><code>AFN009</code"
                                       + "></logRegel><logRegel><lo3Categorie>14</lo3Categorie><lo3Stapel>0</lo3Stapel><lo3Voorkomen>0</lo3Voorkomen"
                                       + "><severity>300</severity><code>AFN009</code></logRegel></logging></afnemersindicatiesAntwoord>";

        final SyncBericht syncBericht = SyncBerichtFactory.SINGLETON.getBericht(antwoordBericht);
        Assert.assertEquals("Aantal logregels moet 12 zijn", 12, ((AfnemersindicatiesAntwoordBericht) syncBericht).getLogging().size());

        final String messageId = "msg-id";
        final String correlatieId = "IV.IND-123456789";
        final InitVullingAfnemersindicatie initVullingAfnemersindicatie = new InitVullingAfnemersindicatie(123456789L);
        for (short x = 0; x < 12; x++) {
            final InitVullingAfnemersindicatieStapel stapel =
                    new InitVullingAfnemersindicatieStapel(new InitVullingAfnemersindicatieStapelPk(3452671879L, x));
            stapel.setConversieResultaat("TE_VERWERKEN");
            initVullingAfnemersindicatie.getStapels().add(stapel);
        }
        Mockito.when(loggingService.zoekInitVullingAfnemerindicatie(123456789L)).thenReturn(initVullingAfnemersindicatie);

        subject.verwerk((AfnemersindicatiesAntwoordBericht) syncBericht, messageId, correlatieId);

        // Verify
        Mockito.verify(loggingService, Mockito.times(1)).zoekInitVullingAfnemerindicatie(123456789L);

        final ArgumentCaptor<InitVullingAfnemersindicatie> entityCaptor = ArgumentCaptor.forClass(InitVullingAfnemersindicatie.class);
        Mockito.verify(loggingService, Mockito.times(1)).persisteerInitVullingAfnemerindicatie(entityCaptor.capture());
        Mockito.verifyNoMoreInteractions(loggingService);

        final InitVullingAfnemersindicatie entity = entityCaptor.getValue();
        Assert.assertEquals("Conversieresultaat ongelijk aan WAARSCHUWING", "WAARSCHUWING", entity.getConversieResultaat());
        Assert.assertNull("Foutmelding zou leeg moeten zijn", entity.getFoutmelding());
        Assert.assertEquals("Aantal stapels dient 12 te zijn", 12, entity.getStapels().size());
        Assert.assertEquals("Reden moet AFN009 zijn", "AFN009", entity.getStapels().iterator().next().getConversieResultaat());
    }
}
