package nl.bzk.brp.soapui.excel

import com.carrotsearch.junitbenchmarks.BenchmarkOptions
import com.carrotsearch.junitbenchmarks.BenchmarkRule
import nl.bzk.brp.soapui.steps.DataSourceValues
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

/**
 *
 */
@BenchmarkOptions(warmupRounds = 0, benchmarkRounds = 15)
class DataSheetsPerfTest extends AbstractInputExcelTest {

    File file = new File(getClass().getResource('/VoorbeeldScript/art_input_voorbeeld_large.xls').toURI())

    @Rule
    public ExpectedException thrown = ExpectedException.none()

    @Rule
    public BenchmarkRule benchmark = new BenchmarkRule()

    @Test
    void kanDataKolomLaden() {
        DataSheets excel = new DataSheets(file, ['Data'])
        DataSourceValues dataSourceValues = buildDataSourceValues()

        excel.laadKolomVanTestGeval('LT00FT00', null, dataSourceValues)

        assert dataSourceValues.propertyList.size() == 4
        assert dataSourceValues.getPropertyValue('burgerservicenummer') == '637866277'
    }
}
