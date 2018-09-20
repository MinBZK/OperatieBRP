package nl.bzk.brp.funqmachine.datalezers.excel

import com.carrotsearch.junitbenchmarks.BenchmarkOptions
import com.carrotsearch.junitbenchmarks.BenchmarkRule
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

@BenchmarkOptions(warmupRounds = 0, benchmarkRounds = 15)
class DataSheetsPerfTest {

    File file = new File(getClass().getResource('/excelfiles/art_input_voorbeeld_large.xls').toURI())

    @Rule
    public ExpectedException thrown = ExpectedException.none()

    @Rule
    public BenchmarkRule benchmark = new BenchmarkRule()

    @Test
    void kanDataKolomLaden_MetCache() {
        DataSheets excel = new DataSheets(file, true)
        def dataSourceValues = [:]

        excel.laadKolomVanTestGeval('LT00FT00', null, dataSourceValues)

        assert dataSourceValues.size() == 4
        assert dataSourceValues.get('burgerservicenummer') == '637866277'
    }

    @Test
    void kanDataKolomLaden_ZonderCache() {
        DataSheets excel = new DataSheets(file, false)
        def dataSourceValues = [:]

        excel.laadKolomVanTestGeval('LT00FT00', null, dataSourceValues)

        assert dataSourceValues.size() == 4
        assert dataSourceValues.get('burgerservicenummer') == '637866277'
    }
}
