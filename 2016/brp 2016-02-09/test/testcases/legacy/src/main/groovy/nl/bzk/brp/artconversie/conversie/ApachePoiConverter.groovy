package nl.bzk.brp.artconversie.conversie

import jxl.write.Label
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 *
 */
class ApachePoiConverter implements XlsDataConverter {
    private final Logger logger = LoggerFactory.getLogger(ApachePoiConverter)

    @Override
    void convert(final File xlsFile) {
        Workbook workbook = WorkbookFactory.create(new FileInputStream(xlsFile))

        if (workbook.numberOfSheets == 1) {
            def heeftArtSheet = workbook.getSheetName(0) == 'ART'

            workbook.close()
            if (heeftArtSheet) {
                xlsFile.delete()
            }

            return
        }

        (1..<workbook.numberOfSheets).each { int index ->
            Sheet sheet = workbook.getSheetAt(index)

            int rowEnd = Math.max(3, sheet.lastRowNum)
            for (int r = 0; r < rowEnd; r++) {
                Row row = sheet.getRow(r)

                if (row) {
                    int lastColumn = Math.max(row.lastCellNum, 10)

                    // rename values in first column
                    Cell firstInRow = row.getCell(0, Row.RETURN_BLANK_AS_NULL)
                    if (firstInRow && Cell.CELL_TYPE_STRING == firstInRow.cellType) {
                        firstInRow.setCellValue(KeyNamerStrategy.rename(firstInRow.getStringCellValue()))
                    }

                    for (int c = 1; c < lastColumn; c++) {
                        Cell cell = row.getCell(c, Row.RETURN_BLANK_AS_NULL)

                        if (cell && Cell.CELL_TYPE_STRING == cell.cellType) {
                            def matcher = cell.stringCellValue =~ /\[(\w+(\(.*\))?)\]/

                            if (matcher.matches() && 'spatie' != matcher[0][1]) {
                                String value = matcher[0][1]

                                if (!value.endsWith(')')) {
                                    value = "$value()"
                                }
                                cell.setCellValue("\${$value}")
                            }
                        }
                        else if (cell && Cell.CELL_TYPE_FORMULA == cell.cellType) {
                            if (cell.cellFormula =~ /.*ART[!]?\$.*/) {
                                cell.cellType = Cell.CELL_TYPE_STRING
                                cell.setCellValue(cell.stringCellValue)
                            }
                        }
                    }
                }
            }
        }

        def formulaEvaluator = workbook.creationHelper.createFormulaEvaluator()
        formulaEvaluator.evaluateAll()

        def artIndex = workbook.getSheetIndex('ART')
        if (artIndex > -1) { workbook.removeSheetAt(artIndex) }

        FileOutputStream fileOut = new FileOutputStream(xlsFile)
        workbook.write(fileOut)
        fileOut.close()
    }
}
