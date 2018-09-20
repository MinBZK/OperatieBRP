/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.runtime.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import nl.moderniseringgba.isc.esb.message.sync.generated.AntwoordFormaatType;
import nl.moderniseringgba.isc.esb.message.sync.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.sync.impl.LeesUitBrpAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.LeesUitBrpVerzoekBericht;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.proces.ConversieService;
import nl.moderniseringgba.migratie.conversie.proces.preconditie.OngeldigePersoonslijstException;
import nl.moderniseringgba.migratie.conversie.validatie.InputValidationException;
import nl.moderniseringgba.migratie.synchronisatie.service.BrpDalService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LeesUitBrpServiceImplTest {

    private static final String TEST_DATABASE_FOUT = "Test Database Fout";
    private static final long ANUMMER = 14L;
    private static final BrpPersoonslijst DUMMY_BRP_PL = new BrpPersoonslijstBuilder().build();
    private static final Lo3Persoonslijst DUMMY_LO3_PL = new Lo3PersoonslijstBuilder().build();

    @Mock
    private BrpDalService brpDalService;
    @Mock
    private ConversieService conversieService;
    @InjectMocks
    private final LeesUitBrpService leesUitBrpService = new LeesUitBrpServiceImpl();

    @Before
    public void setup() throws OngeldigePersoonslijstException {
        when(brpDalService.bevraagPersoonslijst(ANUMMER)).thenReturn(DUMMY_BRP_PL);

        when(conversieService.converteerBrpPersoonslijst(DUMMY_BRP_PL)).thenReturn(DUMMY_LO3_PL);
        try {
            when(conversieService.converteerLo3Persoonslijst(DUMMY_LO3_PL)).thenReturn(DUMMY_BRP_PL);
        } catch (final InputValidationException e) {
            // DUMMY_LO3_PL veroorzaakt GEEN InputValidationException
        }
    }

    @Test
    public void testVerwerkLeesUitBrpVerzoekLo3Formaat() throws InputValidationException {
        final LeesUitBrpVerzoekBericht leesUitBrpVerzoek = new LeesUitBrpVerzoekBericht(ANUMMER);

        final LeesUitBrpAntwoordBericht leesUitBrpAntwoord =
                leesUitBrpService.verwerkLeesUitBrpVerzoek(leesUitBrpVerzoek);

        verify(conversieService, times(1)).converteerBrpPersoonslijst(DUMMY_BRP_PL);
        verify(brpDalService, times(1)).bevraagPersoonslijst(ANUMMER);

        assertEquals(leesUitBrpVerzoek.getMessageId(), leesUitBrpAntwoord.getCorrelationId());
        assertNull(leesUitBrpAntwoord.getFoutmelding());
        assertNull(leesUitBrpAntwoord.getStartCyclus());
        assertEquals(StatusType.OK, leesUitBrpAntwoord.getStatus());
        assertNotNull(leesUitBrpAntwoord.getMessageId());
    }

    @Test
    public void testVerwerkLeesUitBrpVerzoekBrpFormaat() throws InputValidationException {
        final LeesUitBrpVerzoekBericht leesUitBrpVerzoek =
                new LeesUitBrpVerzoekBericht(ANUMMER, AntwoordFormaatType.BRP);

        final LeesUitBrpAntwoordBericht leesUitBrpAntwoord =
                leesUitBrpService.verwerkLeesUitBrpVerzoek(leesUitBrpVerzoek);

        verify(conversieService, times(0)).converteerBrpPersoonslijst(DUMMY_BRP_PL);
        verify(brpDalService, times(1)).bevraagPersoonslijst(ANUMMER);

        assertEquals(leesUitBrpVerzoek.getMessageId(), leesUitBrpAntwoord.getCorrelationId());
        assertNull(leesUitBrpAntwoord.getFoutmelding());
        assertNull(leesUitBrpAntwoord.getStartCyclus());
        assertEquals(StatusType.OK, leesUitBrpAntwoord.getStatus());
        assertNotNull(leesUitBrpAntwoord.getMessageId());
    }

    @Test
    public void testVerwerkLeesUitBrpVerzoekFout() {
        when(brpDalService.bevraagPersoonslijst(ANUMMER)).thenThrow(new RuntimeException(TEST_DATABASE_FOUT));

        final LeesUitBrpVerzoekBericht synchroniseerNaarBrpVerzoek = new LeesUitBrpVerzoekBericht(ANUMMER);

        final LeesUitBrpAntwoordBericht synchroniseerNaarBrpAntwoord =
                leesUitBrpService.verwerkLeesUitBrpVerzoek(synchroniseerNaarBrpVerzoek);

        verify(conversieService, never()).converteerBrpPersoonslijst(DUMMY_BRP_PL);
        verify(brpDalService, times(1)).bevraagPersoonslijst(ANUMMER);

        assertEquals(synchroniseerNaarBrpVerzoek.getMessageId(), synchroniseerNaarBrpAntwoord.getCorrelationId());
        assertEquals(TEST_DATABASE_FOUT, synchroniseerNaarBrpAntwoord.getFoutmelding());
        assertNull(synchroniseerNaarBrpAntwoord.getStartCyclus());
        assertEquals(StatusType.FOUT, synchroniseerNaarBrpAntwoord.getStatus());
        assertNotNull(synchroniseerNaarBrpAntwoord.getMessageId());
    }
}
