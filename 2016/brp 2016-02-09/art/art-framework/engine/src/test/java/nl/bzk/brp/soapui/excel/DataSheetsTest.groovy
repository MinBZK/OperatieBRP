package nl.bzk.brp.soapui.excel

import groovy.sql.Sql
import nl.bzk.brp.soapui.excel.exceptions.TestGevalDataNietGevonden
import nl.bzk.brp.soapui.steps.DataSourceValues
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

class DataSheetsTest extends AbstractInputExcelTest {

    File file = new File(getClass().getResource('/VoorbeeldScript/art_input_voorbeeld.xls').toURI())
    File file2 = new File(getClass().getResource('/VoorbeeldScript/art_input_voorbeeld_large.xls').toURI())

    @Rule
    public ExpectedException thrown = ExpectedException.none()

    @BeforeClass
    static void makeDB() {
	def props = [user: 'brp', password: 'brp', allowMultiQueries: 'true'] as Properties

	Sql sql = Sql.newInstance('jdbc:h2:~/mymemdb', props, 'org.h2.Driver')

	sql.execute("""
DROP SCHEMA IF EXISTS kern;
DROP SCHEMA IF EXISTS kern;
CREATE SCHEMA kern;
  CREATE TABLE kern.pers (id integer, bsn integer);

INSERT INTO kern.pers (id, bsn) values (11, 700137038);
INSERT INTO kern.pers (id, bsn) values (12, 800011375);
""")
    }

    @Test
    void excelMetDatasheetIsValide() {
        DataSheets excel = new DataSheets(file, ['Data'])

        assert excel != null
    }

    @Test
    void excelMetNietBestaandeDatasheet() {
        thrown.expectMessage('Kan de data-sheet \'Foo\' niet vinden')

        DataSheets excel = new DataSheets(file, ['Foo'])
    }

    @Test
    void kanDataKolomLaden() {
        DataSheets excel = new DataSheets(file, ['Data'])
        DataSourceValues dataSourceValues = buildDataSourceValues()

        excel.laadKolomVanTestGeval('LT00FT00', null, dataSourceValues)

	assert dataSourceValues.propertyList.size() == 6
        assert dataSourceValues.getPropertyValue('burgerservicenummer') == '637866277'
    }

    @Test
    void kanSQLInSheetOmzetten() {
	DataSheets excel = new DataSheets(file, ['Data'])
	DataSourceValues dataSourceValues = buildDataSourceValues()

	Sql sql = Sql.newInstance('jdbc:h2:~/mymemdb', [user: 'brp', password: 'brp'] as Properties, 'org.h2.Driver')

	excel.laadKolomVanTestGeval('LT00FT00', sql, dataSourceValues)

	assert dataSourceValues.propertyList.size() == 6
	assert dataSourceValues.getPropertyValue('relatie') == '2'
	assert dataSourceValues.getPropertyValue('abonnementnaam') == 'Geselecteerde afnemers'
    }

    @Test
    void kanDataKolomNietVinden() {
        DataSheets excel = new DataSheets(file, ['Data'])
        DataSourceValues dataSourceValues = buildDataSourceValues()

        thrown.expect(TestGevalDataNietGevonden.class)

        excel.laadKolomVanTestGeval('Foo-LT00FT00', null, dataSourceValues)
    }

    @Test
    void laadFileMetMeerdereDataSheets() {
        file = new File(getClass().getResource('/VoorbeeldScript/art_input_voorbeeld_large.xls').toURI())
        DataSheets excel = new DataSheets(file, ['Data', 'MeerData'])

        assert excel != null
    }

    @Test
    void gebruiktCache() {
        DataSheets dataSheets = new DataSheets(file2, ['Data'])

        DataSourceValues values = buildDataSourceValues()

        dataSheets.laadKolomVanTestGeval('LT01FT192', null, values)

        assert !dataSheets.data.isEmpty()
        assert values.getPropertyValue('burgerservicenummer') == '120345678'
    }

    @Test
    void gebruiktGeenCache() {
        DataSheets dataSheets = new DataSheets(file2, ['Data'], false)
        DataSourceValues values = buildDataSourceValues()

        dataSheets.laadKolomVanTestGeval('LT01FT192', null, values)

        assert dataSheets.data.isEmpty()
        assert values.getPropertyValue('burgerservicenummer') == '120345678'
    }

    @Test
    void maaktCacheLeegIndienNodig() {
        DataSheets dataSheets = new DataSheets(file, ['Data'])
        assert !dataSheets.data.isEmpty()

        dataSheets = new DataSheets(file, ['Data'], false)
        assert dataSheets.data.isEmpty()
    }
}
