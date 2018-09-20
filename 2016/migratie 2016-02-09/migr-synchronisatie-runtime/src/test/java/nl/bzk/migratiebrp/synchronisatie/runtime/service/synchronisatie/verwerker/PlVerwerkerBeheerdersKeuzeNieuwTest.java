/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker;

import java.sql.Timestamp;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3Bericht;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3BerichtenBron;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.exception.SynchronisatieVerwerkerException;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.pl.PlService;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.VerwerkingsContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PlVerwerkerBeheerdersKeuzeNieuwTest {

    @Mock
    private PlService plService;

    @InjectMocks
    private PlVerwerkerBeheerdersKeuzeNieuw subject;

    @Before
    public void setupLogging() {
        SynchronisatieLogging.init();
        Logging.initContext();
    }

    @After
    public void destroyLogging() {
        Logging.destroyContext();
    }

    @Test
    public void testOk() throws Exception {
        // Parameters
        final SynchroniseerNaarBrpVerzoekBericht verzoek = new SynchroniseerNaarBrpVerzoekBericht();
        verzoek.setMessageId("verzoek-msg-id");
        final Lo3Bericht loggingBericht = new Lo3Bericht("ref", Lo3BerichtenBron.SYNCHRONISATIE, new Timestamp(System.currentTimeMillis()), "data", true);
        final BrpPersoonslijst brpPersoonslijst = maakPl();

        // Setup
        Mockito.when(plService.zoekNietFoutievePersoonslijstOpActueelAnummer(123456789L)).thenReturn(null);

        // Execute
        final VerwerkingsContext context = new VerwerkingsContext(verzoek, loggingBericht, null, brpPersoonslijst);
        final SynchroniseerNaarBrpAntwoordBericht antwoord = subject.verwerk(context);

        // Verify
        Mockito.verify(plService).zoekNietFoutievePersoonslijstOpActueelAnummer(123456789L);

        Mockito.verify(plService).persisteerPersoonslijst(brpPersoonslijst, loggingBericht);

        Mockito.verifyNoMoreInteractions(plService);

        Assert.assertNotNull(antwoord);
        Assert.assertEquals("verzoek-msg-id", antwoord.getCorrelationId());
        Assert.assertEquals(StatusType.TOEGEVOEGD, antwoord.getStatus());
    }

    @Test
    public void testNokActueelAnummerGevonden() {
        // Parameters
        final SynchroniseerNaarBrpVerzoekBericht verzoek = new SynchroniseerNaarBrpVerzoekBericht();
        verzoek.setMessageId("verzoek-msg-id");
        final Lo3Bericht loggingBericht = new Lo3Bericht("ref", Lo3BerichtenBron.SYNCHRONISATIE, new Timestamp(System.currentTimeMillis()), "data", true);
        final BrpPersoonslijst brpPersoonslijst = maakPl();

        // Setup
        Mockito.when(plService.zoekNietFoutievePersoonslijstOpActueelAnummer(123456789L)).thenReturn(new BrpPersoonslijstBuilder().build());

        // Execute
        try {
            final VerwerkingsContext context = new VerwerkingsContext(verzoek, loggingBericht, null, brpPersoonslijst);
            final SynchroniseerNaarBrpAntwoordBericht antwoord = subject.verwerk(context);
            Assert.fail("SynchronisatieVerwerkerException expected");
        } catch (final SynchronisatieVerwerkerException e) {
            Assert.assertEquals(StatusType.FOUT, e.getStatus());
        }

        // Verify
        Mockito.verify(plService).zoekNietFoutievePersoonslijstOpActueelAnummer(123456789L);

        Mockito.verifyNoMoreInteractions(plService);
    }

    private BrpPersoonslijst maakPl() {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();

        builder.identificatienummersStapel(BrpStapelHelper.stapel(BrpStapelHelper.groep(
            BrpStapelHelper.identificatie(123456789L, null),
            BrpStapelHelper.his(20010101),
            BrpStapelHelper.act(1, 20010101))));

        return builder.build();
    }
}
