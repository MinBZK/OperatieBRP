/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.viewer.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.proces.ConversieService;
import nl.moderniseringgba.migratie.conversie.validatie.InputValidationException;
import nl.moderniseringgba.migratie.conversie.viewer.log.FoutMelder;

import org.apache.poi.util.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class ViewerServiceTest {
    @Mock
    private ConversieService conversieService;

    @InjectMocks
    private ViewerServiceImpl viewerService;

    private final FoutMelder foutMelder = new FoutMelder();

    @Test
    public void testConverteerNaarBrp() throws IOException, InputValidationException {
        final Lo3Persoonslijst lo3Persoonslijst = getValidLo3Persoonslijst();
        final BrpPersoonslijst brpPersoonslijst = createBrpPersoonslijst();
        Mockito.when(conversieService.converteerLo3Persoonslijst(lo3Persoonslijst)).thenReturn(brpPersoonslijst);
        assertEquals(brpPersoonslijst, viewerService.converteerNaarBrp(lo3Persoonslijst, foutMelder));
    }

    @Test
    public void testConverteerNaarBrpException() throws IOException, InputValidationException {
        final Lo3Persoonslijst lo3Persoonslijst = getValidLo3Persoonslijst();
        Mockito.when(conversieService.converteerLo3Persoonslijst(lo3Persoonslijst)).thenThrow(new RuntimeException());
        assertNull(viewerService.converteerNaarBrp(lo3Persoonslijst, foutMelder));
    }

    @Test
    public void testConverteerNaarBrpFout() throws InputValidationException, IOException {
        final Lo3Persoonslijst lo3Persoonslijst = getValidLo3Persoonslijst();
        Mockito.when(conversieService.converteerLo3Persoonslijst(lo3Persoonslijst)).thenThrow(
                new InputValidationException("foutje, bedankt"));
        viewerService.converteerNaarBrp(lo3Persoonslijst, foutMelder);
        assertEquals(1, foutMelder.getFoutRegels().size());
        assertEquals("Validatiefout bij converteren naar BRP", foutMelder.getFoutRegels().get(0).getCode());
    }

    @Test
    public void testConverteerTerug() throws IOException {
        final BrpPersoonslijst brpPersoonslijst = createBrpPersoonslijst();
        final Lo3Persoonslijst lo3Persoonslijst = getValidLo3Persoonslijst();
        Mockito.when(conversieService.converteerBrpPersoonslijst(brpPersoonslijst)).thenReturn(lo3Persoonslijst);
        assertEquals(lo3Persoonslijst, viewerService.converteerTerug(brpPersoonslijst, foutMelder));
    }

    @Test
    public void testConverteerTerugException() throws IOException {
        final BrpPersoonslijst brpPersoonslijst = createBrpPersoonslijst();
        Mockito.when(conversieService.converteerBrpPersoonslijst(brpPersoonslijst)).thenThrow(new RuntimeException());
        assertNull(viewerService.converteerTerug(brpPersoonslijst, foutMelder));
    }

    @Test
    public void testVergelijkLo3OrigineelMetTerugconversie() throws IOException {
        final Lo3Persoonslijst lo3Persoonslijst = getValidLo3Persoonslijst();
        assertNotNull(viewerService.vergelijkLo3OrigineelMetTerugconversie(lo3Persoonslijst, lo3Persoonslijst,
                foutMelder));
    }

    private Lo3Persoonslijst getValidLo3Persoonslijst() throws IOException {
        final String filename = "Omzetting.txt";
        final InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(filename);
        final List<Lo3Persoonslijst> plList =
                new LeesServiceImpl().leesLo3Persoonslijst(filename, IOUtils.toByteArray(inputStream), foutMelder);
        return plList.get(0);
    }

    private BrpPersoonslijst createBrpPersoonslijst() {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        return builder.build();
    }
}
