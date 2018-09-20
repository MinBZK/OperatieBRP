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

import java.util.ArrayList;
import java.util.List;

import nl.moderniseringgba.isc.esb.message.BerichtSyntaxException;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Inhoud;
import nl.moderniseringgba.isc.esb.message.lo3.format.Lo3PersoonslijstFormatter;
import nl.moderniseringgba.isc.esb.message.sync.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.sync.impl.SynchroniseerNaarBrpAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datumtijdstempel;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.moderniseringgba.migratie.conversie.proces.ConversieService;
import nl.moderniseringgba.migratie.conversie.proces.preconditie.Lo3SyntaxControle;
import nl.moderniseringgba.migratie.conversie.proces.preconditie.OngeldigePersoonslijstException;
import nl.moderniseringgba.migratie.conversie.proces.preconditie.PreconditiesService;
import nl.moderniseringgba.migratie.conversie.validatie.InputValidationException;
import nl.moderniseringgba.migratie.synchronisatie.domein.logging.entity.BerichtLog;
import nl.moderniseringgba.migratie.synchronisatie.service.BrpDalService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

@RunWith(MockitoJUnitRunner.class)
public class SynchronisatieServiceImplTest {

    private static final String BRON_TESTCASE = "testcase";
    private static final boolean GEEN_INITIELE_VULLING = false;
    private static final boolean WEL_INITIELE_VULLING = true;
    private static final long ANUMMER = 14L;
    private static final BrpPersoonslijst DUMMY_BRP_PL = new BrpPersoonslijstBuilder().build();
    private static final Lo3Persoonslijst DUMMY_LO3_PL = new Lo3PersoonslijstBuilder().inschrijvingStapel(
            maakInschrijvingStapel(false)).build();
    private static final Lo3Persoonslijst DUMMY_LO3_PL_DEFAULT_GROEP80 = new Lo3PersoonslijstBuilder()
            .inschrijvingStapel(maakInschrijvingStapel(true)).build();
    private static final List<Lo3CategorieWaarde> DUMMY_LO3_CAT_LIST = new Lo3PersoonslijstFormatter()
            .format(DUMMY_LO3_PL);

    @Mock
    private BrpDalService brpDalService;
    @Mock
    private ConversieService conversieService;
    @Mock
    private Lo3SyntaxControle syntaxControle;
    @Mock
    private PreconditiesService preconditieService;
    @InjectMocks
    private SynchronisatieService synchronisatieService = new SynchronisatieServiceImpl();

    @Before
    public void setup() throws OngeldigePersoonslijstException {
        when(brpDalService.bevraagPersoonslijst(ANUMMER)).thenReturn(DUMMY_BRP_PL);
        when(brpDalService.zoekPersoonOpAnummer(ANUMMER)).thenReturn(DUMMY_BRP_PL);

        when(conversieService.converteerBrpPersoonslijst(DUMMY_BRP_PL)).thenReturn(DUMMY_LO3_PL);
        try {
            when(conversieService.converteerLo3Persoonslijst(DUMMY_LO3_PL)).thenReturn(DUMMY_BRP_PL);
            when(conversieService.converteerLo3Persoonslijst(DUMMY_LO3_PL_DEFAULT_GROEP80)).thenReturn(DUMMY_BRP_PL);
        } catch (final InputValidationException e) {
            // DUMMY_LO3_PL veroorzaakt GEEN InputValidationException
        }
        when(syntaxControle.controleer(DUMMY_LO3_CAT_LIST)).thenReturn(DUMMY_LO3_CAT_LIST);
        when(preconditieService.verwerk(DUMMY_LO3_PL)).thenReturn(DUMMY_LO3_PL);
        when(preconditieService.verwerk(DUMMY_LO3_PL_DEFAULT_GROEP80)).thenReturn(DUMMY_LO3_PL_DEFAULT_GROEP80);
    }

    @Test
    public void testVerwerkSynchroniseerNaarBrpVerzoek() throws InputValidationException,
            OngeldigePersoonslijstException {
        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoek =
                new SynchroniseerNaarBrpVerzoekBericht(Lo3Inhoud.formatInhoud(new Lo3PersoonslijstFormatter()
                        .format(DUMMY_LO3_PL)));

        final SynchroniseerNaarBrpAntwoordBericht synchroniseerNaarBrpAntwoord =
                synchronisatieService.verwerkSynchroniseerNaarBrpVerzoek(synchroniseerNaarBrpVerzoek, BRON_TESTCASE,
                        GEEN_INITIELE_VULLING);

        verify(syntaxControle, times(1)).controleer(DUMMY_LO3_CAT_LIST);
        verify(preconditieService, times(1)).verwerk(DUMMY_LO3_PL);
        verify(conversieService, times(1)).converteerLo3Persoonslijst(DUMMY_LO3_PL);
        verify(brpDalService, times(1)).persisteerPersoonslijst(DUMMY_BRP_PL);
        final ArgumentCaptor<BerichtLog> berichtLogArgument = ArgumentCaptor.forClass(BerichtLog.class);
        verify(brpDalService, times(1)).persisteerBerichtLog(berichtLogArgument.capture());

        assertEquals(BRON_TESTCASE, berichtLogArgument.getValue().getBron());
        assertNull(berichtLogArgument.getValue().getGemeenteVanInschrijving());
        assertEquals(synchroniseerNaarBrpVerzoek.getMessageId(), berichtLogArgument.getValue().getReferentie());
        assertEquals(synchroniseerNaarBrpVerzoek.getMessageId(), synchroniseerNaarBrpAntwoord.getCorrelationId());
        assertNull(synchroniseerNaarBrpAntwoord.getFoutmelding());
        assertNull(synchroniseerNaarBrpAntwoord.getStartCyclus());
        assertEquals(StatusType.OK, synchroniseerNaarBrpAntwoord.getStatus());
        assertNotNull(synchroniseerNaarBrpAntwoord.getMessageId());
        assertNull(synchroniseerNaarBrpAntwoord.getLogging());

    }

    @Test
    public void testVerwerkSynchroniseerNaarBrpVerzoekDefaultGroep80() throws InputValidationException,
            OngeldigePersoonslijstException {
        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoek =
                new SynchroniseerNaarBrpVerzoekBericht(Lo3Inhoud.formatInhoud(new Lo3PersoonslijstFormatter()
                        .format(DUMMY_LO3_PL)));

        final SynchroniseerNaarBrpAntwoordBericht synchroniseerNaarBrpAntwoord =
                synchronisatieService.verwerkSynchroniseerNaarBrpVerzoek(synchroniseerNaarBrpVerzoek, BRON_TESTCASE,
                        WEL_INITIELE_VULLING);

        verify(syntaxControle, times(1)).controleer(DUMMY_LO3_CAT_LIST);
        verify(preconditieService, times(1)).verwerk(DUMMY_LO3_PL_DEFAULT_GROEP80);
        verify(conversieService, times(1)).converteerLo3Persoonslijst(DUMMY_LO3_PL_DEFAULT_GROEP80);
        verify(brpDalService, times(1)).persisteerPersoonslijst(DUMMY_BRP_PL);
        final ArgumentCaptor<BerichtLog> berichtLogArgument = ArgumentCaptor.forClass(BerichtLog.class);
        verify(brpDalService, times(1)).persisteerBerichtLog(berichtLogArgument.capture());

        assertEquals(BRON_TESTCASE, berichtLogArgument.getValue().getBron());
        assertNull(berichtLogArgument.getValue().getGemeenteVanInschrijving());
        assertEquals(synchroniseerNaarBrpVerzoek.getMessageId(), berichtLogArgument.getValue().getReferentie());
        assertEquals(synchroniseerNaarBrpVerzoek.getMessageId(), synchroniseerNaarBrpAntwoord.getCorrelationId());
        assertNull(synchroniseerNaarBrpAntwoord.getFoutmelding());
        assertNull(synchroniseerNaarBrpAntwoord.getStartCyclus());
        assertEquals(StatusType.OK, synchroniseerNaarBrpAntwoord.getStatus());
        assertNotNull(synchroniseerNaarBrpAntwoord.getMessageId());
        assertNull(synchroniseerNaarBrpAntwoord.getLogging());

    }

    @Test
    public void testVerwerkSynchroniseerNaarBrpVerzoekFout1() throws OngeldigePersoonslijstException,
            InputValidationException {
        when(syntaxControle.controleer(new Lo3PersoonslijstFormatter().format(DUMMY_LO3_PL))).thenThrow(
                new OngeldigePersoonslijstException());

        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoek =
                new SynchroniseerNaarBrpVerzoekBericht(Lo3Inhoud.formatInhoud(new Lo3PersoonslijstFormatter()
                        .format(DUMMY_LO3_PL)));

        final SynchroniseerNaarBrpAntwoordBericht synchroniseerNaarBrpAntwoord =
                synchronisatieService.verwerkSynchroniseerNaarBrpVerzoek(synchroniseerNaarBrpVerzoek, BRON_TESTCASE,
                        GEEN_INITIELE_VULLING);

        verify(syntaxControle, times(1)).controleer(DUMMY_LO3_CAT_LIST);
        verify(preconditieService, never()).verwerk(DUMMY_LO3_PL);
        verify(conversieService, never()).converteerLo3Persoonslijst(DUMMY_LO3_PL);
        verify(brpDalService, never()).persisteerPersoonslijst(DUMMY_BRP_PL);
        final ArgumentCaptor<BerichtLog> berichtLogArgument = ArgumentCaptor.forClass(BerichtLog.class);
        verify(brpDalService, times(1)).persisteerBerichtLog(berichtLogArgument.capture());

        assertEquals(BRON_TESTCASE, berichtLogArgument.getValue().getBron());
        assertEquals(synchroniseerNaarBrpVerzoek.getMessageId(), berichtLogArgument.getValue().getReferentie());
        assertEquals(synchroniseerNaarBrpVerzoek.getMessageId(), synchroniseerNaarBrpAntwoord.getCorrelationId());
        assertNull(synchroniseerNaarBrpAntwoord.getFoutmelding());
        assertNull(synchroniseerNaarBrpAntwoord.getStartCyclus());
        assertEquals(StatusType.BERICHT_INHOUD_FOUT, synchroniseerNaarBrpAntwoord.getStatus());
        assertNotNull(synchroniseerNaarBrpAntwoord.getMessageId());
        assertNull(synchroniseerNaarBrpAntwoord.getLogging());
    }

    @Test
    public void testVerwerkSynchroniseerNaarBrpVerzoekFout2() throws OngeldigePersoonslijstException,
            InputValidationException {
        final String FOUT_MESSAGE = "bericht syntax exception";
        when(syntaxControle.controleer(new Lo3PersoonslijstFormatter().format(DUMMY_LO3_PL))).thenAnswer(
                new Answer<Object>() {

                    @Override
                    public Object answer(final InvocationOnMock invocation) throws BerichtSyntaxException {
                        throw new BerichtSyntaxException(FOUT_MESSAGE);
                    }
                });

        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoek =
                new SynchroniseerNaarBrpVerzoekBericht(Lo3Inhoud.formatInhoud(new Lo3PersoonslijstFormatter()
                        .format(DUMMY_LO3_PL)));
        final SynchroniseerNaarBrpAntwoordBericht synchroniseerNaarBrpAntwoord =
                synchronisatieService.verwerkSynchroniseerNaarBrpVerzoek(synchroniseerNaarBrpVerzoek, BRON_TESTCASE,
                        GEEN_INITIELE_VULLING);

        verify(syntaxControle, times(1)).controleer(DUMMY_LO3_CAT_LIST);
        verify(preconditieService, never()).verwerk(DUMMY_LO3_PL);
        verify(conversieService, never()).converteerLo3Persoonslijst(DUMMY_LO3_PL);
        verify(brpDalService, never()).persisteerPersoonslijst(DUMMY_BRP_PL);
        final ArgumentCaptor<BerichtLog> berichtLogArgument = ArgumentCaptor.forClass(BerichtLog.class);
        verify(brpDalService, times(1)).persisteerBerichtLog(berichtLogArgument.capture());

        assertEquals(BRON_TESTCASE, berichtLogArgument.getValue().getBron());
        assertEquals(synchroniseerNaarBrpVerzoek.getMessageId(), berichtLogArgument.getValue().getReferentie());
        assertEquals(synchroniseerNaarBrpVerzoek.getMessageId(), synchroniseerNaarBrpAntwoord.getCorrelationId());
        assertEquals(FOUT_MESSAGE, synchroniseerNaarBrpAntwoord.getFoutmelding());
        assertNull(synchroniseerNaarBrpAntwoord.getStartCyclus());
        assertEquals(StatusType.BERICHT_PARSE_FOUT, synchroniseerNaarBrpAntwoord.getStatus());
        assertNotNull(synchroniseerNaarBrpAntwoord.getMessageId());
        assertNull(synchroniseerNaarBrpAntwoord.getLogging());
    }

    @Test
    public void testVerwerkSynchroniseerNaarBrpVerzoekFout3() throws OngeldigePersoonslijstException,
            InputValidationException {
        final String FOUT_MESSAGE = "RuntimeException";
        when(syntaxControle.controleer(new Lo3PersoonslijstFormatter().format(DUMMY_LO3_PL))).thenThrow(
                new RuntimeException(FOUT_MESSAGE));

        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoek =
                new SynchroniseerNaarBrpVerzoekBericht(Lo3Inhoud.formatInhoud(new Lo3PersoonslijstFormatter()
                        .format(DUMMY_LO3_PL)));
        final SynchroniseerNaarBrpAntwoordBericht synchroniseerNaarBrpAntwoord =
                synchronisatieService.verwerkSynchroniseerNaarBrpVerzoek(synchroniseerNaarBrpVerzoek, BRON_TESTCASE,
                        GEEN_INITIELE_VULLING);

        verify(syntaxControle, times(1)).controleer(DUMMY_LO3_CAT_LIST);
        verify(preconditieService, never()).verwerk(DUMMY_LO3_PL);
        verify(conversieService, never()).converteerLo3Persoonslijst(DUMMY_LO3_PL);
        verify(brpDalService, never()).persisteerPersoonslijst(DUMMY_BRP_PL);
        final ArgumentCaptor<BerichtLog> berichtLogArgument = ArgumentCaptor.forClass(BerichtLog.class);
        verify(brpDalService, times(1)).persisteerBerichtLog(berichtLogArgument.capture());

        assertEquals(BRON_TESTCASE, berichtLogArgument.getValue().getBron());
        assertEquals(synchroniseerNaarBrpVerzoek.getMessageId(), berichtLogArgument.getValue().getReferentie());
        assertEquals(synchroniseerNaarBrpVerzoek.getMessageId(), synchroniseerNaarBrpAntwoord.getCorrelationId());
        assertEquals(FOUT_MESSAGE, synchroniseerNaarBrpAntwoord.getFoutmelding());
        assertNull(synchroniseerNaarBrpAntwoord.getStartCyclus());
        assertEquals(StatusType.FOUT, synchroniseerNaarBrpAntwoord.getStatus());
        assertNotNull(synchroniseerNaarBrpAntwoord.getMessageId());
        assertNull(synchroniseerNaarBrpAntwoord.getLogging());
    }

    @Test
    public void testVerwerkSynchroniseerNaarBrpVerzoekFout4() throws OngeldigePersoonslijstException,
            InputValidationException {
        when(preconditieService.verwerk(DUMMY_LO3_PL)).thenThrow(new OngeldigePersoonslijstException());

        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoek =
                new SynchroniseerNaarBrpVerzoekBericht(Lo3Inhoud.formatInhoud(new Lo3PersoonslijstFormatter()
                        .format(DUMMY_LO3_PL)));
        final SynchroniseerNaarBrpAntwoordBericht synchroniseerNaarBrpAntwoord =
                synchronisatieService.verwerkSynchroniseerNaarBrpVerzoek(synchroniseerNaarBrpVerzoek, BRON_TESTCASE,
                        GEEN_INITIELE_VULLING);

        verify(syntaxControle, times(1)).controleer(DUMMY_LO3_CAT_LIST);
        verify(preconditieService, times(1)).verwerk(DUMMY_LO3_PL);
        verify(conversieService, never()).converteerLo3Persoonslijst(DUMMY_LO3_PL);
        verify(brpDalService, never()).persisteerPersoonslijst(DUMMY_BRP_PL);
        final ArgumentCaptor<BerichtLog> berichtLogArgument = ArgumentCaptor.forClass(BerichtLog.class);
        verify(brpDalService, times(1)).persisteerBerichtLog(berichtLogArgument.capture());

        assertEquals(BRON_TESTCASE, berichtLogArgument.getValue().getBron());
        assertEquals(synchroniseerNaarBrpVerzoek.getMessageId(), berichtLogArgument.getValue().getReferentie());
        assertEquals(synchroniseerNaarBrpVerzoek.getMessageId(), synchroniseerNaarBrpAntwoord.getCorrelationId());
        assertNull(synchroniseerNaarBrpAntwoord.getFoutmelding());
        assertNull(synchroniseerNaarBrpAntwoord.getStartCyclus());
        assertEquals(StatusType.BERICHT_INHOUD_FOUT, synchroniseerNaarBrpAntwoord.getStatus());
        assertNotNull(synchroniseerNaarBrpAntwoord.getMessageId());
        assertNull(synchroniseerNaarBrpAntwoord.getLogging());
    }

    private static Lo3Stapel<Lo3InschrijvingInhoud> maakInschrijvingStapel(final boolean defaultGroep80) {
        final List<Lo3Categorie<Lo3InschrijvingInhoud>> stapel = new ArrayList<Lo3Categorie<Lo3InschrijvingInhoud>>();
        Integer versienummer = null;
        Lo3Datumtijdstempel datumtijdstempel = null;
        if (defaultGroep80) {
            versienummer = Integer.valueOf(1);
            datumtijdstempel = new Lo3Datumtijdstempel(20070401000000000L);
        }
        stapel.add(new Lo3Categorie<Lo3InschrijvingInhoud>(new Lo3InschrijvingInhoud(null, null, null, new Lo3Datum(
                20000101), new Lo3GemeenteCode("1899"), null, versienummer, datumtijdstempel, null), null,
                Lo3Historie.NULL_HISTORIE, null));
        return new Lo3Stapel<Lo3InschrijvingInhoud>(stapel);
    }
}
