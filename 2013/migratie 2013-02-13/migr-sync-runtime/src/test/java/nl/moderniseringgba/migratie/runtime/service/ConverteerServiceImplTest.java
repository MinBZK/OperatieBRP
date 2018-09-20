/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.runtime.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import nl.moderniseringgba.isc.esb.message.lo3.format.Lo3PersoonslijstFormatter;
import nl.moderniseringgba.isc.esb.message.sync.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.sync.impl.ConverteerNaarBrpAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.ConverteerNaarBrpVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.ConverteerNaarLo3AntwoordBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.ConverteerNaarLo3VerzoekBericht;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.moderniseringgba.migratie.conversie.proces.ConversieService;
import nl.moderniseringgba.migratie.conversie.proces.preconditie.Lo3SyntaxControle;
import nl.moderniseringgba.migratie.conversie.proces.preconditie.OngeldigePersoonslijstException;
import nl.moderniseringgba.migratie.conversie.validatie.InputValidationException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ConverteerServiceImplTest {

    private static final String CONVERSIE_FOUT = "Conversie Fout";
    private static final BrpPersoonslijst DUMMY_BRP_PL = new BrpPersoonslijstBuilder().build();
    private static final Lo3Persoonslijst DUMMY_LO3_PL = new Lo3PersoonslijstBuilder().build();
    private static final List<Lo3CategorieWaarde> DUMMY_LO3_CAT_LIST = new Lo3PersoonslijstFormatter()
            .format(DUMMY_LO3_PL);

    @Mock
    private Lo3SyntaxControle syntaxControle;

    @Mock
    private ConversieService conversieService;

    @InjectMocks
    private ConverteerService converteerService = new ConverteerServiceImpl();

    @Before
    public void setup() throws OngeldigePersoonslijstException {
        when(conversieService.converteerBrpPersoonslijst(DUMMY_BRP_PL)).thenReturn(DUMMY_LO3_PL);
        try {
            when(conversieService.converteerLo3Persoonslijst(DUMMY_LO3_PL)).thenReturn(DUMMY_BRP_PL);
        } catch (final InputValidationException e) {
            // DUMMY_LO3_PL veroorzaakt GEEN InputValidationException
        }
        when(syntaxControle.controleer(DUMMY_LO3_CAT_LIST)).thenReturn(DUMMY_LO3_CAT_LIST);
    }

    @Test
    public void testVerwerkConverteerNaarBrpVerzoekBericht() throws OngeldigePersoonslijstException,
            InputValidationException {
        final ConverteerNaarBrpVerzoekBericht converteerNaarBrpVerzoek =
                new ConverteerNaarBrpVerzoekBericht(DUMMY_LO3_PL);

        final ConverteerNaarBrpAntwoordBericht converteerNaarBrpAntwoord =
                converteerService.verwerkConverteerNaarBrpVerzoek(converteerNaarBrpVerzoek);

        verify(syntaxControle, times(1)).controleer(DUMMY_LO3_CAT_LIST);
        verify(conversieService, times(1)).converteerLo3Persoonslijst(DUMMY_LO3_PL);

        assertEquals(converteerNaarBrpVerzoek.getMessageId(), converteerNaarBrpAntwoord.getCorrelationId());
        assertNull(converteerNaarBrpAntwoord.getFoutmelding());
        assertNull(converteerNaarBrpAntwoord.getStartCyclus());
        assertEquals(StatusType.OK, converteerNaarBrpAntwoord.getStatus());
        assertNotNull(converteerNaarBrpAntwoord.getMessageId());
    }

    @Test
    public void testVerwerkConverteerNaarBrpVerzoekBerichtFout() throws OngeldigePersoonslijstException,
            InputValidationException {
        try {
            when(conversieService.converteerLo3Persoonslijst(DUMMY_LO3_PL)).thenThrow(
                    new RuntimeException(CONVERSIE_FOUT));
        } catch (final InputValidationException e) {
            // DUMMY_LO3_PL veroorzaakt GEEN InputValidationException
        }
        final ConverteerNaarBrpVerzoekBericht converteerNaarBrpVerzoek =
                new ConverteerNaarBrpVerzoekBericht(DUMMY_LO3_PL);

        final ConverteerNaarBrpAntwoordBericht converteerNaarBrpAntwoord =
                converteerService.verwerkConverteerNaarBrpVerzoek(converteerNaarBrpVerzoek);

        verify(syntaxControle, times(1)).controleer(DUMMY_LO3_CAT_LIST);
        verify(conversieService, times(1)).converteerLo3Persoonslijst(DUMMY_LO3_PL);

        assertEquals(converteerNaarBrpVerzoek.getMessageId(), converteerNaarBrpAntwoord.getCorrelationId());
        assertEquals(CONVERSIE_FOUT, converteerNaarBrpAntwoord.getFoutmelding());
        assertNull(converteerNaarBrpAntwoord.getStartCyclus());
        assertEquals(StatusType.FOUT, converteerNaarBrpAntwoord.getStatus());
        assertNotNull(converteerNaarBrpAntwoord.getMessageId());
    }

    @Test
    public void testVerwerkConverteerNaarLo3Verzoek() throws OngeldigePersoonslijstException,
            InputValidationException {
        final ConverteerNaarLo3VerzoekBericht converteerNaarLo3Verzoek =
                new ConverteerNaarLo3VerzoekBericht(DUMMY_BRP_PL);

        final ConverteerNaarLo3AntwoordBericht converteerNaarLo3Antwoord =
                converteerService.verwerkConverteerNaarLo3Verzoek(converteerNaarLo3Verzoek);

        verify(conversieService, times(1)).converteerBrpPersoonslijst(DUMMY_BRP_PL);

        assertEquals(converteerNaarLo3Verzoek.getMessageId(), converteerNaarLo3Antwoord.getCorrelationId());
        assertNull(converteerNaarLo3Antwoord.getFoutmelding());
        assertNull(converteerNaarLo3Antwoord.getStartCyclus());
        assertEquals(StatusType.OK, converteerNaarLo3Antwoord.getStatus());
        assertNotNull(converteerNaarLo3Antwoord.getMessageId());
    }

    @Test
    public void testVerwerkConverteerNaarLo3VerzoekFout() {
        when(conversieService.converteerBrpPersoonslijst(DUMMY_BRP_PL)).thenThrow(
                new RuntimeException(CONVERSIE_FOUT));
        final ConverteerNaarLo3VerzoekBericht converteerNaarLo3Verzoek =
                new ConverteerNaarLo3VerzoekBericht(DUMMY_BRP_PL);

        final ConverteerNaarLo3AntwoordBericht converteerNaarLo3Antwoord =
                converteerService.verwerkConverteerNaarLo3Verzoek(converteerNaarLo3Verzoek);

        verify(conversieService, times(1)).converteerBrpPersoonslijst(DUMMY_BRP_PL);

        assertEquals(converteerNaarLo3Verzoek.getMessageId(), converteerNaarLo3Antwoord.getCorrelationId());
        assertEquals(CONVERSIE_FOUT, converteerNaarLo3Antwoord.getFoutmelding());
        assertNull(converteerNaarLo3Antwoord.getStartCyclus());
        assertEquals(StatusType.FOUT, converteerNaarLo3Antwoord.getStatus());
        assertNotNull(converteerNaarLo3Antwoord.getMessageId());
    }
}
