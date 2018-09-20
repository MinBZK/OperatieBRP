package nl.bzk.brp.funqmachine.datalezers

import org.junit.Test

class ExcelDataReaderTest {

    ExcelDataLezer reader

    @Test
    void kanExcelLezen() {
        reader = new ExcelDataLezer(null, 'VR00009-TC0101')

        def result = reader.lees(new File(getClass().getResource('test.xls').toURI()))
        assert result.size() == 13
        assert result['regelCode_ggi0'] == '[-2]'
    }
}
