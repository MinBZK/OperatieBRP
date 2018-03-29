/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.naarbrp.service;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.init.naarbrp.repository.PersoonRepository;
import nl.bzk.migratiebrp.init.naarbrp.service.impl.ExcelBerichtenService;
import nl.bzk.migratiebrp.util.excel.ExcelAdapter;
import nl.bzk.migratiebrp.util.excel.ExcelAdapterException;
import nl.bzk.migratiebrp.util.excel.ExcelData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.test.context.ContextConfiguration;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ExcelData.class)
@ContextConfiguration("classpath:runtime-test-beans.xml")
@PowerMockIgnore("javax.management.*")
public class ExcelBerichtenServiceTest {

    @Mock
    private ExcelAdapter excelAdapter;

    @Mock
    private PersoonRepository persoonRepository;

    private ExcelBerichtenService excelService;

    @Before
    public void setUp() throws Exception {
        excelService = new ExcelBerichtenService(excelAdapter, persoonRepository);
    }

    @Test
    public void testVulBerichtenTabelExcel() throws BerichtSyntaxException, ExcelAdapterException, Lo3SyntaxException {
        final String
                lo3_pl =
                "00697011640110010817238743501200092995889450210004Mart0240005Vries03100081990010103200040599033000460300410001M6110001E8110004059981200071 "
                        +
                        "A9102851000819900101861000819900102021720110010192829389501200099911223340210006Jannie0240004Smit03100081969010103200041901033000460300410001M6210008199001018110004059981200071 A9102851000819900101861000819900102031750110010172625463201200093827261340210008Mitchell0240005Vries03100081970010103200041900033000460300410001M6210008199001018110004059981200071 A910285100081990010186100081990010207055681000819900101701000108010001180200170000000000000000008106091000405990920008199001011010001W102000405991030008199001011110001.7210001G851000819900101861000819900102";
        final List<Lo3CategorieWaarde> categorieen = Lo3Inhoud.parseInhoud(lo3_pl);

        final ExcelData data = Mockito.mock(ExcelData.class);
        final List<ExcelData> excelData = new ArrayList<>();
        excelData.add(data);

        Mockito.when(data.getCategorieLijst()).thenReturn(categorieen);
        Mockito.when(excelAdapter.leesExcelBestand(Matchers.any(InputStream.class))).thenReturn(excelData);

        final String excelLocatie = "src/test/resources/initvullingservice/excel";
        excelService.verwerkFolder(excelLocatie);

        verify(excelAdapter).leesExcelBestand(any(InputStream.class));
        verify(persoonRepository).saveLg01(anyString(), anyString(), anyString(), any());

        verifyNoMoreInteractions(persoonRepository);
        verifyNoMoreInteractions(excelAdapter);
    }
}
