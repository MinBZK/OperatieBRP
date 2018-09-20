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
import nl.moderniseringgba.isc.esb.message.sync.generated.SearchResultaatType;
import nl.moderniseringgba.isc.esb.message.sync.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.sync.impl.SynchronisatieStrategieVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.SynchronisatieStrategieAntwoordBericht;
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
public class SynchronisatieStrategieServiceImplTest {

    private static final String TEST_DATABASE_FOUT = "Test Database Fout";
    private static final long ANUMMER = 14L;
    private static final BrpPersoonslijst DUMMY_BRP_PL = new BrpPersoonslijstBuilder().build();
    private static final Lo3Persoonslijst DUMMY_LO3_PL = new Lo3PersoonslijstBuilder().build();

    @Mock
    private BrpDalService brpDalService;
    @Mock
    private ConversieService conversieService;
    @InjectMocks
    private SynchronisatieStrategieService synchronisatieStrategieService = new SynchronisatieStrategieServiceImpl();

    @Before
    public void setup() throws OngeldigePersoonslijstException {
        when(brpDalService.zoekPersoonOpAnummer(ANUMMER)).thenReturn(DUMMY_BRP_PL);
        when(conversieService.converteerBrpPersoonslijst(DUMMY_BRP_PL)).thenReturn(DUMMY_LO3_PL);
    }

    @Test
    public void testVerwerkSynchronisatieStrategieVerzoek() throws InputValidationException {
        final SynchronisatieStrategieVerzoekBericht synchronisatieStrategieVerzoek = new SynchronisatieStrategieVerzoekBericht(ANUMMER, null, null);

        final SynchronisatieStrategieAntwoordBericht synchroniseerNaarBrpAntwoord =
                synchronisatieStrategieService.verwerkSynchronisatieStrategieVerzoek(synchronisatieStrategieVerzoek);

        verify(conversieService, times(1)).converteerBrpPersoonslijst(DUMMY_BRP_PL);
        verify(brpDalService, times(1)).zoekPersoonOpAnummer(ANUMMER);

        assertEquals(synchronisatieStrategieVerzoek.getMessageId(), synchroniseerNaarBrpAntwoord.getCorrelationId());
        assertEquals("Situatie 2: vervangen PL; De gevonden PL is de te vervangen PL.",
                synchroniseerNaarBrpAntwoord.getToelichting());
        assertEquals(SearchResultaatType.VERVANGEN, synchroniseerNaarBrpAntwoord.getResultaat());
        assertNull(synchroniseerNaarBrpAntwoord.getStartCyclus());
        assertEquals(StatusType.OK, synchroniseerNaarBrpAntwoord.getStatus());
        assertNotNull(synchroniseerNaarBrpAntwoord.getMessageId());
    }

    @Test
    public void testVerwerkSynchronisatieStrategieVerzoekFout() {
        when(brpDalService.zoekPersoonOpAnummer(ANUMMER)).thenThrow(new RuntimeException(TEST_DATABASE_FOUT));

        final SynchronisatieStrategieVerzoekBericht synchronisatieStrategieVerzoek = new SynchronisatieStrategieVerzoekBericht(ANUMMER, null, null);

        final SynchronisatieStrategieAntwoordBericht synchroniseerNaarBrpAntwoord =
                synchronisatieStrategieService.verwerkSynchronisatieStrategieVerzoek(synchronisatieStrategieVerzoek);

        verify(conversieService, never()).converteerBrpPersoonslijst(DUMMY_BRP_PL);
        verify(brpDalService, times(1)).zoekPersoonOpAnummer(ANUMMER);

        assertEquals(synchronisatieStrategieVerzoek.getMessageId(), synchroniseerNaarBrpAntwoord.getCorrelationId());
        assertEquals("Fout bij bepalen synchronisatie strategie: " + TEST_DATABASE_FOUT,
                synchroniseerNaarBrpAntwoord.getToelichting());
        assertNull(synchroniseerNaarBrpAntwoord.getResultaat());
        assertNull(synchroniseerNaarBrpAntwoord.getStartCyclus());
        assertEquals(StatusType.FOUT, synchroniseerNaarBrpAntwoord.getStatus());
        assertNotNull(synchroniseerNaarBrpAntwoord.getMessageId());
    }

    @Test
    public void testVerwerkSynchronisatieStrategieVerzoekFout2() {
        when(conversieService.converteerBrpPersoonslijst(DUMMY_BRP_PL)).thenThrow(
                new RuntimeException(TEST_DATABASE_FOUT));

        final SynchronisatieStrategieVerzoekBericht synchronisatieStrategieVerzoek = new SynchronisatieStrategieVerzoekBericht(ANUMMER, null, null);

        final SynchronisatieStrategieAntwoordBericht synchroniseerNaarBrpAntwoord =
                synchronisatieStrategieService.verwerkSynchronisatieStrategieVerzoek(synchronisatieStrategieVerzoek);

        verify(conversieService, times(1)).converteerBrpPersoonslijst(DUMMY_BRP_PL);
        verify(brpDalService, times(1)).zoekPersoonOpAnummer(ANUMMER);

        assertEquals(synchronisatieStrategieVerzoek.getMessageId(), synchroniseerNaarBrpAntwoord.getCorrelationId());
        assertEquals("Fout bij converteren van BRP naar LO3: " + TEST_DATABASE_FOUT,
                synchroniseerNaarBrpAntwoord.getToelichting());
        assertNull(synchroniseerNaarBrpAntwoord.getResultaat());
        assertNull(synchroniseerNaarBrpAntwoord.getStartCyclus());
        assertEquals(StatusType.FOUT, synchroniseerNaarBrpAntwoord.getStatus());
        assertNotNull(synchroniseerNaarBrpAntwoord.getMessageId());
    }
}
