/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AntwoordFormaatType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesUitBrpAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesUitBrpVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.exceptions.OngeldigePersoonslijstException;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.regels.proces.ConverteerBrpNaarLo3Service;
import nl.bzk.migratiebrp.conversie.regels.proces.ConverteerLo3NaarBrpService;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpDalService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LeesUitBrpServiceTest {

    private static final String TEST_DATABASE_FOUT = "Test Database Fout";
    private static final long ANUMMER = 14L;
    private static final BrpPersoonslijst DUMMY_BRP_PL = new BrpPersoonslijstBuilder().build();
    private static final Lo3Persoonslijst DUMMY_LO3_PL = new Lo3PersoonslijstBuilder().build();

    @Mock
    private BrpDalService brpDalService;
    @Mock
    private ConverteerLo3NaarBrpService converteerLo3NaarBrpService;
    @Mock
    private ConverteerBrpNaarLo3Service converteerBrpNaarLo3Service;
    @InjectMocks
    private final LeesUitBrpService leesUitBrpService = new LeesUitBrpService();

    @Before
    public void setup() throws OngeldigePersoonslijstException {
        when(brpDalService.bevraagPersoonslijst(ANUMMER)).thenReturn(DUMMY_BRP_PL);

        when(converteerBrpNaarLo3Service.converteerBrpPersoonslijst(DUMMY_BRP_PL)).thenReturn(DUMMY_LO3_PL);
        when(converteerLo3NaarBrpService.converteerLo3Persoonslijst(DUMMY_LO3_PL)).thenReturn(DUMMY_BRP_PL);
    }

    @Test
    public void testVerwerkLeesUitBrpVerzoekLo3Formaat() {
        final LeesUitBrpVerzoekBericht leesUitBrpVerzoek = new LeesUitBrpVerzoekBericht(ANUMMER);
        leesUitBrpVerzoek.setMessageId(UUID.randomUUID().toString());

        final LeesUitBrpAntwoordBericht leesUitBrpAntwoord = leesUitBrpService.verwerkBericht(leesUitBrpVerzoek);

        verify(converteerBrpNaarLo3Service, times(1)).converteerBrpPersoonslijst(DUMMY_BRP_PL);
        verify(brpDalService, times(1)).bevraagPersoonslijst(ANUMMER);

        assertEquals(leesUitBrpVerzoek.getMessageId(), leesUitBrpAntwoord.getCorrelationId());
        assertNull(leesUitBrpAntwoord.getStartCyclus());
        assertEquals(StatusType.OK, leesUitBrpAntwoord.getStatus());
        assertNotNull(leesUitBrpAntwoord.getMessageId());
    }

    @Test
    public void testVerwerkLeesUitBrpVerzoekBrpFormaat() {
        final LeesUitBrpVerzoekBericht leesUitBrpVerzoek = new LeesUitBrpVerzoekBericht(ANUMMER, AntwoordFormaatType.BRP);
        leesUitBrpVerzoek.setMessageId(UUID.randomUUID().toString());

        final LeesUitBrpAntwoordBericht leesUitBrpAntwoord = leesUitBrpService.verwerkBericht(leesUitBrpVerzoek);

        verify(converteerBrpNaarLo3Service, times(0)).converteerBrpPersoonslijst(DUMMY_BRP_PL);
        verify(brpDalService, times(1)).bevraagPersoonslijst(ANUMMER);

        assertEquals(leesUitBrpVerzoek.getMessageId(), leesUitBrpAntwoord.getCorrelationId());
        assertNull(leesUitBrpAntwoord.getStartCyclus());
        assertEquals(StatusType.OK, leesUitBrpAntwoord.getStatus());
        assertNotNull(leesUitBrpAntwoord.getMessageId());
    }

    @Test
    public void testVerwerkLeesUitBrpVerzoekFout() {
        when(brpDalService.bevraagPersoonslijst(ANUMMER)).thenThrow(new RuntimeException(TEST_DATABASE_FOUT));

        final LeesUitBrpVerzoekBericht leesUitBrpVerzoekBericht = new LeesUitBrpVerzoekBericht(ANUMMER);
        leesUitBrpVerzoekBericht.setMessageId(UUID.randomUUID().toString());

        try {
            leesUitBrpService.verwerkBericht(leesUitBrpVerzoekBericht);
            fail("Er zou een fout op moeten treden.");
        } catch (final Exception e) {
            assertNotNull("Er zou een fout op moeten treden.", e);
        }
        verify(converteerBrpNaarLo3Service, never()).converteerBrpPersoonslijst(DUMMY_BRP_PL);
        verify(brpDalService, times(1)).bevraagPersoonslijst(ANUMMER);

    }
}
