/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;
import nl.bzk.migratiebrp.conversie.model.exceptions.OngeldigePersoonslijstException;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.Lo3SyntaxControle;
import nl.bzk.migratiebrp.ggo.viewer.Lo3PersoonslijstTestHelper;
import nl.bzk.migratiebrp.ggo.viewer.converter.Lg01Converter;
import nl.bzk.migratiebrp.ggo.viewer.log.FoutMelder;
import nl.bzk.migratiebrp.util.excel.ExcelAdapter;
import nl.bzk.migratiebrp.util.excel.ExcelAdapterException;
import nl.bzk.migratiebrp.util.excel.ExcelData;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LeesServiceTest {
    private static final String OMZETTING01_XLS = "Omzetting01.xls";
    @Mock
    private ExcelAdapter excelAdapter;
    @Mock
    private Lo3SyntaxControle syntaxControle;
    @Mock
    private Lg01Converter lg01Converter;
    @InjectMocks
    private LeesServiceImpl leesService;

    private final FoutMelder foutMelder = new FoutMelder();

    @Test
    public void testLeesExcelLo3PersoonslijstSucces() throws Exception {
        final String filename = OMZETTING01_XLS;
        final InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(filename);
        Mockito.when(excelAdapter.leesExcelBestand(Matchers.any(InputStream.class))).thenReturn(createEmptyExcelDataList());

        final List<List<Lo3CategorieWaarde>> lo3CategorieWaarde = leesService.leesBestand(filename, IOUtils.toByteArray(inputStream), foutMelder);
        Assert.assertNotNull(lo3CategorieWaarde);
        Assert.assertEquals(0, foutMelder.getFoutRegels().size());
    }

    @Test
    public void testLeesExcelLo3PersoonslijstSyntaxFout() throws Exception {
        final String filename = OMZETTING01_XLS;
        final InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(filename);

        Mockito.when(excelAdapter.leesExcelBestand(Matchers.any(InputStream.class))).thenThrow(new Lo3SyntaxException(null));

        final List<List<Lo3CategorieWaarde>> lo3CategorieWaarde = leesService.leesBestand(filename, IOUtils.toByteArray(inputStream), foutMelder);
        Assert.assertNull(lo3CategorieWaarde);
        Assert.assertEquals(1, foutMelder.getFoutRegels().size());
        Assert.assertEquals("Fout bij het lezen van Excel", foutMelder.getFoutRegels().get(0).getCode());
    }

    @Test
    public void testLeesExcelLo3PersoonslijstExcelFout() throws Exception {
        final String filename = OMZETTING01_XLS;
        final InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(filename);

        Mockito.when(excelAdapter.leesExcelBestand(Matchers.any(InputStream.class))).thenThrow(new ExcelAdapterException("kolom", "message"));

        final List<List<Lo3CategorieWaarde>> lo3CategorieWaarde = leesService.leesBestand(filename, IOUtils.toByteArray(inputStream), foutMelder);
        Assert.assertNull(lo3CategorieWaarde);
        Assert.assertEquals(1, foutMelder.getFoutRegels().size());
        Assert.assertEquals("Fout bij het lezen van Excel", foutMelder.getFoutRegels().get(0).getCode());
    }

    @Test
    public void testLeesExcelEmpty() throws Exception {
        final String filename = OMZETTING01_XLS;
        final InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(filename);
        Mockito.when(excelAdapter.leesExcelBestand(Matchers.any(InputStream.class))).thenReturn(new ArrayList<ExcelData>());

        final List<List<Lo3CategorieWaarde>> lo3CategorieWaarde = leesService.leesBestand(filename, IOUtils.toByteArray(inputStream), foutMelder);
        Assert.assertNull(lo3CategorieWaarde);
        Assert.assertEquals(1, foutMelder.getFoutRegels().size());
        Assert.assertEquals("Fout bij het lezen van Excel", foutMelder.getFoutRegels().get(0).getCode());
    }

    @Test
    public void testLeesLg01Lo3PersoonslijstSuccess() throws Exception {
        final String filename = "Omzetting.txt";
        final InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(filename);

        final List<List<Lo3CategorieWaarde>> lo3CategorieWaarde = leesService.leesBestand(filename, IOUtils.toByteArray(inputStream), foutMelder);
        Assert.assertEquals(1, lo3CategorieWaarde.size());
        Assert.assertEquals(0, foutMelder.getFoutRegels().size());
    }

    @Test
    public void testLeesLg01Lo3PersoonslijstTeletex() throws Exception {
        final String filename = "Omzetting met diakrieten.txt";
        final InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(filename);
        final List<List<Lo3CategorieWaarde>> lo3CategorieWaarde = leesService.leesBestand(filename, IOUtils.toByteArray(inputStream), foutMelder);
        Assert.assertEquals(1, lo3CategorieWaarde.size());
        Assert.assertEquals(0, foutMelder.getFoutRegels().size());
    }

    @Test
    public void testLeesGbaPersoonslijstSuccess() throws Exception {
        final String filename = "mGBA-BCM-TEST.GBA";
        final InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(filename);
        final List<List<Lo3CategorieWaarde>> lo3CategorieWaarde = leesService.leesBestand(filename, IOUtils.toByteArray(inputStream), foutMelder);
        Assert.assertEquals(2, lo3CategorieWaarde.size());
        Assert.assertEquals(0, foutMelder.getFoutRegels().size());
    }

    @Test
    public void testLeesOnbekendeExtensie() throws Exception {
        final String filename = "ggo-viewer.properties";
        final InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(filename);
        final List<List<Lo3CategorieWaarde>> lo3CategorieWaarde = leesService.leesBestand(filename, IOUtils.toByteArray(inputStream), foutMelder);
        Assert.assertNull(lo3CategorieWaarde);
        Assert.assertEquals(1, foutMelder.getFoutRegels().size());
    }

    @Test
    public void testParseLo3PlSuccess() throws Exception {
        final String filename = "Omzetting.txt";
        final List<List<Lo3CategorieWaarde>> lo3CatWaarde = Lo3PersoonslijstTestHelper.retrieveLo3CategorieWaarden(filename, new FoutMelder());
        Mockito.when(syntaxControle.controleer(Matchers.anyList())).thenReturn(lo3CatWaarde.get(0));

        final Lo3Persoonslijst lo3Persoonslijst = leesService.parsePersoonslijstMetSyntaxControle(lo3CatWaarde.get(0), foutMelder);
        Assert.assertNotNull(lo3Persoonslijst);
        Assert.assertEquals(0, foutMelder.getFoutRegels().size());
    }

    @Test
    public void testParseLo3PlOngeldigePl() throws Exception {
        final String filename = "Omzetting.txt";
        final List<List<Lo3CategorieWaarde>> lo3CatWaarde = Lo3PersoonslijstTestHelper.retrieveLo3CategorieWaarden(filename, new FoutMelder());
        Mockito.when(syntaxControle.controleer(Matchers.anyList())).thenThrow(new OngeldigePersoonslijstException(""));

        final Lo3Persoonslijst lo3Persoonslijst = leesService.parsePersoonslijstMetSyntaxControle(lo3CatWaarde.get(0), foutMelder);
        Assert.assertNull(lo3Persoonslijst);
        Assert.assertEquals(1, foutMelder.getFoutRegels().size());
        Assert.assertEquals("Inlezen GBA-PL", foutMelder.getFoutRegels().get(0).getCode());
    }

    private List<ExcelData> createEmptyExcelDataList() {
        final List<ExcelData> excelDataList = new ArrayList<>();
        final ExcelData excelData = new ExcelData();
        excelData.setCategorieWaarden(new ArrayList<Lo3CategorieWaarde>());
        excelDataList.add(excelData);
        return excelDataList;
    }

}
