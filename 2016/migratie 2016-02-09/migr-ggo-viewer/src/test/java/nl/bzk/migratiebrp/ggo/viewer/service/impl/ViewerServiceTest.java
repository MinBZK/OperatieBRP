/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;
import nl.bzk.migratiebrp.conversie.model.exceptions.OngeldigePersoonslijstException;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.regels.proces.ConverteerBrpNaarLo3Service;
import nl.bzk.migratiebrp.conversie.regels.proces.ConverteerLo3NaarBrpService;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.PreconditiesService;
import nl.bzk.migratiebrp.ggo.viewer.Lo3PersoonslijstTestHelper;
import nl.bzk.migratiebrp.ggo.viewer.log.FoutMelder;
import nl.bzk.migratiebrp.util.excel.ExcelAdapterException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Gebruik Spring om Lo3PersoonslijstPrecondities in de ViewerServiceImpl te injecteren. We kunnen deze helaas niet
 * mocken, want deze is final.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@ContextConfiguration(locations = {"classpath:test-viewer-beans.xml" })
public class ViewerServiceTest {
    @Mock
    private PreconditiesService preconditieService;
    @Mock
    private ConverteerLo3NaarBrpService converteerLo3NaarBrpService;
    @Mock
    private ConverteerBrpNaarLo3Service converteerBrpNaarLo3Service;

    @Inject
    @InjectMocks
    private ViewerServiceImpl viewerService;

    private final FoutMelder foutMelder = new FoutMelder();

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testConverteerNaarBrp() throws IOException, BerichtSyntaxException, ExcelAdapterException, Lo3SyntaxException {
        final Lo3Persoonslijst lo3Persoonslijst = Lo3PersoonslijstTestHelper.retrieveLo3Persoonslijsten("Omzetting.txt", new FoutMelder()).get(0);
        final BrpPersoonslijst brpPersoonslijst = createBrpPersoonslijst();
        Mockito.when(converteerLo3NaarBrpService.converteerLo3Persoonslijst(lo3Persoonslijst)).thenReturn(brpPersoonslijst);
        assertEquals(brpPersoonslijst, viewerService.converteerNaarBrp(lo3Persoonslijst, foutMelder));
    }

    @Test
    public void testConverteerNaarBrpException() throws IOException, BerichtSyntaxException, ExcelAdapterException, Lo3SyntaxException {
        final Lo3Persoonslijst lo3Persoonslijst = Lo3PersoonslijstTestHelper.retrieveLo3Persoonslijsten("Omzetting.txt", new FoutMelder()).get(0);
        Mockito.when(converteerLo3NaarBrpService.converteerLo3Persoonslijst(lo3Persoonslijst)).thenThrow(new RuntimeException());
        assertNull(viewerService.converteerNaarBrp(lo3Persoonslijst, foutMelder));
    }

    @Test
    public void testConverteerNaarBrpFout() throws IOException, BerichtSyntaxException, ExcelAdapterException, Lo3SyntaxException {
        final Lo3Persoonslijst lo3Persoonslijst = Lo3PersoonslijstTestHelper.retrieveLo3Persoonslijsten("Omzetting.txt", new FoutMelder()).get(0);
        Mockito.when(converteerLo3NaarBrpService.converteerLo3Persoonslijst(lo3Persoonslijst)).thenThrow(new NullPointerException("foutje, bedankt"));
        viewerService.converteerNaarBrp(lo3Persoonslijst, foutMelder);
        assertEquals(2, foutMelder.getFoutRegels().size());
        assertEquals("Conversie (GBA > BRP)", foutMelder.getFoutRegels().get(0).getCode());
    }

    @Test
    public void testVerwerkPrecondities() throws IOException, BerichtSyntaxException, ExcelAdapterException, Lo3SyntaxException,
        OngeldigePersoonslijstException
    {
        final Lo3Persoonslijst lo3Persoonslijst = Lo3PersoonslijstTestHelper.retrieveLo3Persoonslijsten("Precondities.xls", new FoutMelder()).get(0);
        Mockito.when(preconditieService.verwerk(lo3Persoonslijst)).thenReturn(lo3Persoonslijst);
        final Lo3Persoonslijst lo3PlOpgeschoond = viewerService.verwerkPrecondities(lo3Persoonslijst, foutMelder);

        assertTrue(foutMelder.getFoutRegels().size() == 0);
        assertNotNull(lo3PlOpgeschoond);
    }

    @Test
    public void testVerwerkPreconditiesFout() throws IOException, BerichtSyntaxException, ExcelAdapterException, Lo3SyntaxException,
        OngeldigePersoonslijstException
    {
        final Lo3Persoonslijst lo3Persoonslijst = Lo3PersoonslijstTestHelper.retrieveLo3Persoonslijsten("Precondities.xls", new FoutMelder()).get(0);
        Mockito.when(preconditieService.verwerk(lo3Persoonslijst)).thenThrow(new RuntimeException());
        final Lo3Persoonslijst lo3PlOpgeschoond = viewerService.verwerkPrecondities(lo3Persoonslijst, foutMelder);

        assertEquals(1, foutMelder.getFoutRegels().size());
        assertEquals("Fout bij verwerken precondities", foutMelder.getFoutRegels().get(0).getCode());
        assertNull(lo3PlOpgeschoond);
    }

    @Test
    public void testVerwerkPreconditiesOngeldig() throws IOException, BerichtSyntaxException, ExcelAdapterException, Lo3SyntaxException,
        OngeldigePersoonslijstException
    {
        final Lo3Persoonslijst lo3Persoonslijst = Lo3PersoonslijstTestHelper.retrieveLo3Persoonslijsten("Precondities.xls", new FoutMelder()).get(0);
        Mockito.when(preconditieService.verwerk(lo3Persoonslijst)).thenThrow(new OngeldigePersoonslijstException(""));
        final Lo3Persoonslijst lo3PlOpgeschoond = viewerService.verwerkPrecondities(lo3Persoonslijst, foutMelder);

        assertTrue(foutMelder.getFoutRegels().size() == 0);
        assertNull(lo3PlOpgeschoond);
    }

    @Test
    public void testConverteerTerug() throws IOException, BerichtSyntaxException, ExcelAdapterException, Lo3SyntaxException,
        OngeldigePersoonslijstException
    {
        final BrpPersoonslijst brpPersoonslijst = createBrpPersoonslijst();
        final Lo3Persoonslijst lo3Persoonslijst = Lo3PersoonslijstTestHelper.retrieveLo3Persoonslijsten("Omzetting.txt", new FoutMelder()).get(0);
        Mockito.when(converteerBrpNaarLo3Service.converteerBrpPersoonslijst(brpPersoonslijst)).thenReturn(lo3Persoonslijst);
        assertEquals(lo3Persoonslijst, viewerService.converteerTerug(brpPersoonslijst, foutMelder));
    }

    @Test
    public void testConverteerTerugException() throws IOException, BerichtSyntaxException, ExcelAdapterException, Lo3SyntaxException,
        OngeldigePersoonslijstException
    {
        final BrpPersoonslijst brpPersoonslijst = createBrpPersoonslijst();
        Mockito.when(converteerBrpNaarLo3Service.converteerBrpPersoonslijst(brpPersoonslijst)).thenThrow(new RuntimeException());
        assertNull(viewerService.converteerTerug(brpPersoonslijst, foutMelder));
    }

    @Test
    public void testVergelijkLo3OrigineelMetTerugconversie() throws IOException, BerichtSyntaxException, ExcelAdapterException, Lo3SyntaxException,
        OngeldigePersoonslijstException
    {
        final Lo3Persoonslijst lo3Persoonslijst = Lo3PersoonslijstTestHelper.retrieveLo3Persoonslijsten("Omzetting.txt", new FoutMelder()).get(0);
        assertNotNull(viewerService.vergelijkLo3OrigineelMetTerugconversie(lo3Persoonslijst, lo3Persoonslijst, foutMelder));
    }

    @Test
    public void testVergelijkLo3OrigineelMetTerugconversieFout() throws IOException, BerichtSyntaxException, ExcelAdapterException, Lo3SyntaxException,
        OngeldigePersoonslijstException
    {
        assertNull(viewerService.vergelijkLo3OrigineelMetTerugconversie(null, null, foutMelder));

        assertEquals(1, foutMelder.getFoutRegels().size());
        assertEquals("Fout bij het vergelijken", foutMelder.getFoutRegels().get(0).getCode());
    }

    @Test
    public void testVoegLo3HerkomstToe() throws IOException, BerichtSyntaxException, ExcelAdapterException, Lo3SyntaxException,
        OngeldigePersoonslijstException
    {
        final Lo3Persoonslijst lo3Persoonslijst = Lo3PersoonslijstTestHelper.retrieveLo3Persoonslijsten("Omzetting.txt", new FoutMelder()).get(0);
        assertNotNull(viewerService.voegLo3HerkomstToe(lo3Persoonslijst, lo3Persoonslijst, foutMelder));
    }

    @Test
    public void testVoegLo3HerkomstToeFout() throws IOException, BerichtSyntaxException, ExcelAdapterException, Lo3SyntaxException,
        OngeldigePersoonslijstException
    {
        assertNull(viewerService.voegLo3HerkomstToe(null, null, foutMelder));

        assertEquals(1, foutMelder.getFoutRegels().size());
        assertEquals("Fout bij het bepalen van de lo3Herkomst van de teruggeconverteerde Persoonslijst", foutMelder.getFoutRegels().get(0).getCode());
    }

    private BrpPersoonslijst createBrpPersoonslijst() {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        return builder.build();
    }
}
