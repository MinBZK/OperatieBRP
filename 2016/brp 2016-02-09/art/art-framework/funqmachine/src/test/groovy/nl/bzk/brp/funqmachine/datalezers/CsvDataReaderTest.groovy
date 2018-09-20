package nl.bzk.brp.funqmachine.datalezers

import org.junit.Test

class CsvDataReaderTest {
    DataLezer reader = new CsvDataLezer()

    @Test
    void canReadCsvFile() {
        File f = new File(getClass().getResource('test.csv').toURI())

        assert [Key: 'waarde', Sleutel: 'waarde2'] == reader.lees(f)
    }
}
