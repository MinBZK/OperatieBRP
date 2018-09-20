package nl.bzk.brp.artconversie.reader

import nl.bzk.brp.artconversie.model.art.ArtSheet
import nl.bzk.brp.artconversie.model.art.ExcelRegel
import nl.bzk.brp.artconversie.model.art.Kolommen
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellValue
import org.apache.poi.ss.usermodel.FormulaEvaluator
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory

/**
 *
 */
class ApachePoiArtControlReader implements ArtControlReader {
    private Map<Integer, Kolommen> kolomMap = [:]

    Workbook workbook
    Sheet sheet
    FormulaEvaluator evaluator

    /**
     * Constructor voor de reader.
     * @param xls de excel file om te lezen
     * @param sheetName naam van de sheet in het bestand
     */
    ApachePoiArtControlReader(File xls, String sheetName) {
        workbook = WorkbookFactory.create(xls)
        sheet = workbook.getSheet(sheetName)

        evaluator = workbook.creationHelper.createFormulaEvaluator()
        leesHeaders()
    }

    /**
     * Leest een xls sheet.
     *
     * @return een model van de art control sheet
     */
    ArtSheet read() {
        ArtSheet result = new ArtSheet()
        int index = 0

        while (++index <= sheet.lastRowNum)  {
            def cells = sheet.getRow(index)
            ExcelRegel regel = new ExcelRegel(result)
            regel.setRegel(index + 1)

            if (cells) {
                for (int c = 0; c < cells.lastCellNum; c++) {
                    Cell cell = cells.getCell(c, Row.RETURN_BLANK_AS_NULL)

                    if (cell) {
                        CellValue data = evaluator.evaluate(cell)
                        def value
                        switch (data.cellType) {
                            case Cell.CELL_TYPE_NUMERIC:
                                value = cell.numericCellValue as int
                                break
                            case Cell.CELL_TYPE_STRING:
                                value = cell.stringCellValue
                                break
                            case Cell.CELL_TYPE_BOOLEAN:
                                value = cell.booleanCellValue
                                break
                            default:
                                value = null
                        }
                        regel.put kolomMap[cell.columnIndex], value as String
                    }
                }
            }

            result << regel
        }

        return result
    }

    /**
     * Leest eerste rij uit de sheet, en slaat waarde-index op.
     */
    private void leesHeaders() {
        def headers = sheet.getRow(0)
        headers.each { Cell cell ->
            Kolommen kolom = Kolommen.voorWaarde(cell.stringCellValue.trim())
            if (kolom) {
                kolomMap.put cell.columnIndex, kolom
            }
        }
    }
}
