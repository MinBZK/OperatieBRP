package nl.bzk.brp.funqmachine.datalezers.excel

import groovy.sql.Sql
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellValue
import org.apache.poi.ss.usermodel.FormulaEvaluator
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Representeert een ART test sheet.
 */
class DataSheets {
    private static final Logger log = LoggerFactory.getLogger(DataSheets)

    private static Map<String, List<String>> data     = new HashMap<>()
    private static String                    filePath = null

            Workbook         workbook
    private List<String>     dataSheets = []
    private FormulaEvaluator evaluator

    /**
     * Constructor.
     * @param file het excelbestand
     * @param dataSheets lijst namen van sheets met data
     */
    DataSheets(File file, boolean useCache = true) {
        this.workbook = WorkbookFactory.create(new FileInputStream(file))
        this.evaluator = workbook.creationHelper.createFormulaEvaluator()

        for (int s = 0; s < workbook.numberOfSheets; s++) {
            def sheet = workbook.getSheetName(s)
            if (sheet != 'ART') {
                this.dataSheets.add sheet
            }
        }

        if (useCache) {
            vulDataCache(file.canonicalPath)
        } else {
            data.clear()
        }
    }

    /*
     * Leest van de datasheets de eerste rij en slaat deze op in een static variable, voor latere toegang.
     * Op deze wijze is het niet steeds nodig om het excelbestand te openen.
     */
    private void vulDataCache(final String filePath) {
        if (this.filePath == null || !this.filePath.equals(filePath)) {
            log.info "Vullen van DataCache"
            data.clear()

            this.dataSheets.each {String sheet ->
                //println "vul cache voor sheet: $sheet"
                Sheet sheet1 = workbook.getSheet(sheet)
                Row row = sheet1.getRow(0)
                def namen = []
                for (int c = 0; c < row.lastCellNum; c++) {
                    namen << getCellContent(row.getCell(c, Row.CREATE_NULL_AS_BLANK))
                }

                data.put(sheet, namen)
            }
        }
    }

    /**
     * Laadt een kolom met data voor een testgeval.
     *
     * @param testGeval de naam van het testgeval
     * @param sql {@link groovy.sql.Sql} instantie om waardes op te halen
     * @param target doel om waardes in te zetten
     */
    void laadKolomVanTestGeval(String testGeval, Sql sql, Map<String, String> target) {
        Sheet sheet
        int kolomNr

        (sheet, kolomNr) = vindDataSheetVoorTestgeval(testGeval)
        Cell[] attrNamen = vindAttribuutNamen(sheet)

        attrNamen.each {attr ->
            Cell cell = sheet.getRow(attr.rowIndex).getCell(kolomNr, Row.CREATE_NULL_AS_BLANK)
            if (cell) {
                String data = convertCellContents(sql, cell)
                target.put(attr.stringCellValue, data)
            }
        }
    }

    /*
     * Zoek naar de juiste sheet en kolom voor een testgeval.
     *
     * @param testGeval het testgeval waarvoor de data gezocht wordt
     * @return een lijst (Sheet, index) voor het gezochte testgeval
     */
    private def vindDataSheetVoorTestgeval(String testGeval) {
        if (data.isEmpty()) {
            zoekInWorkbook(testGeval)
        } else {
            zoekInCache(testGeval)
        }
    }

    /*
     * Zoek naar de juiste sheet en kolom in de 'cache'.
     *
     * @param testGeval het testgeval waarvoor de data gezocht wordt
     * @return een lijst (Sheet, index) voor het gezochte testgeval
     */
    private def zoekInCache(String testGeval) {
        String sheet = null
        int index = -1

        data.any {Map.Entry<String, List<String>> e ->
            sheet = e.key
            index = e.value.indexOf(testGeval)

            index > -1
        }

        if (index > -1) {
            log.info "Data voor [$testGeval] gevonden: $sheet - kol $index"
            return [this.workbook.getSheet(sheet), index]
        }

        throw new TestGevalDataNietGevonden("Geen data voor testGeval [$testGeval]")
    }

    /*
     * Zoek naar de juiste sheet en kolom in het workbook.
     *
     * @param testGeval het testgeval waarvoor de data gezocht wordt
     * @return een lijst (Sheet, index) voor het gezochte testgeval
     */
    private def zoekInWorkbook(String testGeval) {
        Sheet sheet = null
        Cell cell = null
        dataSheets.any {sh ->
            sheet = this.workbook.getSheet(sh)

            sheet.getRow(0).each {Cell c ->
                if (Cell.CELL_TYPE_STRING == c.cellType && c.stringCellValue == testGeval) {
                    cell = c
                    return
                }
            }
            cell != null
        }

        if (cell) {
            log.info "Data voor [$testGeval] gevonden: $sheet.sheetName - kol ${ExcelUtil.indexToColumnLetter(cell.columnIndex)}"
            return [sheet, cell.columnIndex]
        }

        throw new TestGevalDataNietGevonden("Geen data voor testGeval [$testGeval]")
    }

    /*
     * Leest de 1e kolom van een datasheet voor de namen van attributen.
     *
     * @param sheet de te lezen sheet
     * @return lijst van cellen in de kolom
     */
    private Cell[] vindAttribuutNamen(Sheet sheet) {
        List<Cell> attribuutNaamCellen = sheet.collect {Row row -> row.getCell(0, Row.RETURN_BLANK_AS_NULL)}
        List<Cell> goedeCellen = new ArrayList<Cell>()
        Set<String> uniekeAttribuutNamen = new HashSet<String>()

        attribuutNaamCellen.tail().each {Cell entry ->
            def attrNaam = entry?.stringCellValue

            if (attrNaam) {
                if (!attrNaam.startsWith('_') && uniekeAttribuutNamen.contains(attrNaam)) {
                    def err = "Attribuutnaam: '$attrNaam', cell[A${1 + entry.rowIndex}] is niet uniek."
                    log.error err
                    throw new NietUniekDataAttribuut(err)
                }

                uniekeAttribuutNamen.add attrNaam
                goedeCellen.add entry
            }
        }

        goedeCellen.toArray(new Cell[goedeCellen.size()])
    }

    /*
     * Zet waardes van een cell om, evt met behulp van SQL.
     *
     * @param sql een {@link Sql} instantie
     * @param data de waarde uit een cell
     * @return de omgezette waarde
     */
    private String convertCellContents(Sql sql, Cell cell) {

        def tempContents = this.getCellContent(cell)

        if (tempContents.toUpperCase().trim() =~ /^SELECT\s/) {
            log.info tempContents
            try {
                return sql.firstRow(tempContents)[0]
            }
            catch (Exception e) {
                return e.message
            }
        }
        return tempContents
    }

    /*
     * Geeft de waarde van een cell als string.
     *
     * @param cell de cell waaruit de waarde wordt gelezen
     * @return de waarde als string, of een lege string als er geen waarde is
     */
    private String getCellContent(Cell cell) {
        CellValue data = this.evaluator.evaluate(cell)

        if (!data) return ''

        switch (data.cellType) {
            case Cell.CELL_TYPE_NUMERIC:
                return data.numberValue as long
            case Cell.CELL_TYPE_STRING:
                return data.stringValue
            case Cell.CELL_TYPE_BOOLEAN:
                return data.booleanValue as String
            default:
                return ''
        }
    }
}
