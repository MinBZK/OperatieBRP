/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.viewer.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import nl.moderniseringgba.migratie.adapter.excel.ExcelAdapter;
import nl.moderniseringgba.migratie.adapter.excel.ExcelAdapterException;
import nl.moderniseringgba.migratie.adapter.excel.ExcelData;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.moderniseringgba.migratie.conversie.viewer.log.FoutMelder;

import org.apache.poi.util.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LeesServiceTest {
    @Mock
    private ExcelAdapter excelAdapter;

    @InjectMocks
    private LeesServiceImpl leesService;

    private final FoutMelder foutMelder = new FoutMelder();

    @Test
    public void testLeesExcelLo3PersoonslijstSucces() throws IOException, ExcelAdapterException {
        final String filename = "Omzetting01.xls";
        final InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(filename);
        Mockito.when(excelAdapter.leesExcelBestand(Matchers.any(InputStream.class))).thenReturn(
                createEmptyExcelDataList());

        assertNotNull(leesService.leesLo3Persoonslijst(filename, IOUtils.toByteArray(inputStream), foutMelder));
        assertEquals(0, foutMelder.getFoutRegels().size());
    }

    @Test
    public void testLeesExcelLo3PersoonslijstIOFout() throws ExcelAdapterException, IOException {
        final String filename = "Omzetting01.xls";
        final InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(filename);
        // IOException, ExcelAdapterException
        Mockito.when(excelAdapter.leesExcelBestand(Matchers.any(InputStream.class))).thenThrow(new IOException());

        leesService.leesLo3Persoonslijst(filename, IOUtils.toByteArray(inputStream), foutMelder);
        assertEquals(1, foutMelder.getFoutRegels().size());
        assertEquals("Bestandsfout bij uploaden Excel", foutMelder.getFoutRegels().get(0).getCode());
    }

    @Test
    public void testLeesExcelLo3PersoonslijstExcelFout() throws ExcelAdapterException, IOException {
        final String filename = "Omzetting01.xls";
        final InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(filename);

        Mockito.when(excelAdapter.leesExcelBestand(Matchers.any(InputStream.class))).thenThrow(
                new ExcelAdapterException("kolom", "message"));

        leesService.leesLo3Persoonslijst(filename, IOUtils.toByteArray(inputStream), foutMelder);
        assertEquals(1, foutMelder.getFoutRegels().size());
        assertEquals("Fout bij het lezen van Excel", foutMelder.getFoutRegels().get(0).getCode());
    }

    @Test
    public void testLeesLg01Lo3PersoonslijstSuccess() throws IOException {
        final String filename = "Omzetting.txt";
        final InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(filename);

        leesService.leesLo3Persoonslijst(filename, IOUtils.toByteArray(inputStream), foutMelder);
    }

    @Test
    public void testLeesLg01Lo3PersoonslijstBerichtExcelAdapterException() throws IOException, ExcelAdapterException {
        final String filename = "Omzetting01.xls";
        final InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(filename);

        Mockito.when(excelAdapter.leesExcelBestand(Matchers.any(InputStream.class))).thenThrow(
                new ExcelAdapterException("1", "ExcelAdapterException"));

        leesService.leesLo3Persoonslijst(filename, IOUtils.toByteArray(inputStream), foutMelder);
        assertEquals(1, foutMelder.getFoutRegels().size());
        assertEquals("Fout bij het lezen van Excel", foutMelder.getFoutRegels().get(0).getCode());
    }

    @Test
    public void testLeesLg01Lo3PersoonslijstOnbekendBericht() throws IOException {
        final String filename = "Bericht onbekend.txt";
        final InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(filename);
        leesService.leesLo3Persoonslijst(filename, IOUtils.toByteArray(inputStream), foutMelder);
        assertEquals(1, foutMelder.getFoutRegels().size());
        assertEquals("Obekend bericht", foutMelder.getFoutRegels().get(0).getCode());
    }

    @Test
    public void testLeesGbaPersoonslijstSuccess() throws IOException {
        final String filename = "mGBA-BCM-TEST.GBA";
        final InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(filename);
        final List<Lo3Persoonslijst> persoonslijsten =
                leesService.leesLo3Persoonslijst(filename, IOUtils.toByteArray(inputStream), foutMelder);
        assertEquals(2, persoonslijsten.size());
        assertEquals(0, foutMelder.getFoutRegels().size());
    }

    @Test
    public void testLeesOnbekendeExtensie() throws IOException {
        final String filename = "ggo-viewer.properties";
        final InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(filename);
        final List<Lo3Persoonslijst> persoonslijsten =
                leesService.leesLo3Persoonslijst(filename, IOUtils.toByteArray(inputStream), foutMelder);
        assertEquals(0, persoonslijsten.size());
        assertEquals(1, foutMelder.getFoutRegels().size());
    }

    private List<ExcelData> createEmptyExcelDataList() {
        final List<ExcelData> excelDataList = new ArrayList<ExcelData>();
        final ExcelData excelData = new ExcelData();
        excelData.setCategorieWaarden(new ArrayList<Lo3CategorieWaarde>());
        excelDataList.add(excelData);
        return excelDataList;
    }

}
