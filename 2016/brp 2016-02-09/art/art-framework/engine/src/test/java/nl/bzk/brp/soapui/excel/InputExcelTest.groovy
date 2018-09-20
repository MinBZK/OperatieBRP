package nl.bzk.brp.soapui.excel

import nl.bzk.brp.soapui.excel.exceptions.DataSheetNietGevonden
import nl.bzk.brp.soapui.steps.ControlValues
import org.apache.poi.ss.usermodel.Cell
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

/**
 *
 */
class InputExcelTest extends AbstractInputExcelTest {

    File file = new File(getClass().getResource('/VoorbeeldScript/art_input_voorbeeld.xls').toURI())

    @Rule
    public ExpectedException thrown = ExpectedException.none()

    @Test
    void excelIsValide() {
        InputExcel excel = new InputExcel(file, 'ART')

        assert excel != null
        assert excel.kolomnamen.size() == 20
        assert excel.optioneleKolomnamen.size() == 10
    }

    @Test
    void kanTestGevalOphalen() {
        InputExcel excel = new InputExcel(file, 'ART')

        Cell cell = excel.getTestGeval(1)
        assert cell.stringCellValue == 'LT00FT00'

        cell = excel.getTestGeval(5)
        assert cell.stringCellValue == 'LT00FT01'
    }

    @Test
    void kanControlDataLaden() {
        InputExcel excel = new InputExcel(file, 'ART')
        ControlValues controlValues = buildControlValues()

        excel.laadRegel(1, controlValues)

        assert controlValues.propertyList.size() == 6
        assert controlValues.isSendRequest()
    }

    @Test
    void controleertDataSheets() {
        file = new File(getClass().getResource('/VoorbeeldScript/art_input_voorbeeld_large.xls').toURI())

        InputExcel excel = new InputExcel(file, 'ART', ['Data', 'MeerData'])
        assert excel != null
    }

    @Test
    void controleertNietBestaandeDataSheets() {
        thrown.expect(DataSheetNietGevonden.class)
        thrown.expectMessage('Kan de data-sheet \'Foo\' niet vinden')

        file = new File(getClass().getResource('/VoorbeeldScript/art_input_voorbeeld_large.xls').toURI())

        new InputExcel(file, 'ART', ['Data', 'Foo'])
    }

}
