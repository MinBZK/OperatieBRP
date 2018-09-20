package nl.bzk.brp.funqmachine.datalezers.excel

import groovy.sql.Sql
import nl.bzk.brp.funqmachine.configuratie.Database
import nl.bzk.brp.funqmachine.processors.SqlProcessor
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

class DataSheetsTest {

    File input_normaal = new File(getClass().getResource('/excelfiles/art_input_voorbeeld.xls').toURI())
    File input_groot = new File(getClass().getResource('/excelfiles/art_input_voorbeeld_large.xls').toURI())

    def dataSourceValues = [:]

    @Rule
    public ExpectedException thrown = ExpectedException.none()

    @BeforeClass
    static void makeDB() {
        def props = [user: 'brp', password: 'brp', allowMultiQueries: 'true'] as Properties

        Sql sql = new SqlProcessor(Database.KERN).sql

        sql.execute("""
DELETE FROM kern.pers;
INSERT INTO kern.pers (id, bsn, srt) values (11, 700137038, 1);
INSERT INTO kern.pers (id, bsn, srt) values (12, 800011375, 1);
""")
    }

    @Test
    void excelMetDatasheetIsValide() {
        DataSheets excel = new DataSheets(input_normaal)

        assert excel != null
    }

    @Test
    void kanDataKolomLaden() {
        DataSheets excel = new DataSheets(input_normaal)

        excel.laadKolomVanTestGeval('LT00FT00', null, dataSourceValues)

        assert dataSourceValues.size() == 6
        assert dataSourceValues.get('burgerservicenummer') == '637866277'
    }

    @Test
    void kanSQLInSheetOmzetten() {
        DataSheets excel = new DataSheets(input_normaal)

        Sql sql = new SqlProcessor(Database.KERN).sql

        excel.laadKolomVanTestGeval('LT00FT00', sql, dataSourceValues)

        assert dataSourceValues.size() == 6
        assert dataSourceValues.get('relatie') == '2'
        assert dataSourceValues.get('abonnementnaam') == 'Geselecteerde afnemers'
    }

    @Test
    void kanDataKolomNietVinden() {
        DataSheets excel = new DataSheets(input_normaal)

        thrown.expect(TestGevalDataNietGevonden.class)

        excel.laadKolomVanTestGeval('Foo-LT00FT00', null, dataSourceValues)
    }

    @Test
    void laadFileMetMeerdereDataSheets() {
        DataSheets excel = new DataSheets(input_groot)

        assert excel != null
    }

    @Test
    void gebruiktCache() {
        DataSheets dataSheets = new DataSheets(input_groot)

        dataSheets.laadKolomVanTestGeval('LT01FT192', null, dataSourceValues)

        assert !dataSheets.data.isEmpty()
        assert dataSourceValues.get('burgerservicenummer') == '120345678'
    }

    @Test
    void gebruiktGeenCache() {
        DataSheets dataSheets = new DataSheets(input_groot, false)

        dataSheets.laadKolomVanTestGeval('LT01FT192', null, dataSourceValues)

        assert dataSheets.data.isEmpty()
        assert dataSourceValues.get('burgerservicenummer') == '120345678'
    }

    @Test
    void maaktCacheLeegIndienNodig() {
        DataSheets dataSheets = new DataSheets(input_normaal)
        assert !dataSheets.data.isEmpty()

        dataSheets = new DataSheets(input_normaal, false)
        assert dataSheets.data.isEmpty()
    }
}
