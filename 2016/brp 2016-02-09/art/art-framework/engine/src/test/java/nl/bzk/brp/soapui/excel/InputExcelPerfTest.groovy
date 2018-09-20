package nl.bzk.brp.soapui.excel

import com.carrotsearch.junitbenchmarks.BenchmarkOptions
import com.carrotsearch.junitbenchmarks.BenchmarkRule
import nl.bzk.brp.soapui.steps.ControlValues
import org.apache.poi.ss.usermodel.Cell
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

/**
 *
 */
@BenchmarkOptions(warmupRounds = 0, benchmarkRounds = 15)
class InputExcelPerfTest extends AbstractInputExcelTest {

    File file = new File(getClass().getResource('/VoorbeeldScript/art_input_voorbeeld_large.xls').toURI())

    @Rule
    public ExpectedException thrown = ExpectedException.none()

    @Rule
    public BenchmarkRule benchmark = new BenchmarkRule()

    @Test
    void excelIsValide() {
        InputExcel excel = new InputExcel(file, 'ART')

        assert excel != null
        assert excel.kolomnamen.size() == 20
        assert excel.optioneleKolomnamen.size() == 10
        assert excel.laatsteRij() == 557
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
}
