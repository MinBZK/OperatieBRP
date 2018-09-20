package nl.bzk.brp.artconversie.reader

import jxl.Cell
import jxl.Sheet
import jxl.Workbook
import nl.bzk.brp.artconversie.model.art.ArtSheet
import nl.bzk.brp.artconversie.model.art.ExcelRegel
import nl.bzk.brp.artconversie.model.art.Kolommen

/**
 *
 */
class JExcelArtControlReader implements ArtControlReader {
    private Map<Integer, Kolommen> kolomMap = [:]

    Workbook workbook
    Sheet sheet

    /**
     * Constructor voor de reader.
     * @param xls de excel file om te lezen
     * @param sheetName naam van de sheet in het bestand
     */
    JExcelArtControlReader(File xls, String sheetName) {
        workbook = Workbook.getWorkbook(xls)
        sheet = workbook.getSheet(sheetName)

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

        while (++index < sheet.rows)  {
            def cells = sheet.getRow(index)
            ExcelRegel regel = new ExcelRegel(result)
            regel.setRegel(index + 1)
            cells.each { Cell cell ->
                if (cell.contents) { regel.put kolomMap[cell.column], cell.contents }
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
            Kolommen kolom = Kolommen.voorWaarde(cell.contents.trim())
            if (kolom) {
                kolomMap.put cell.column, kolom
            }
        }
    }
}
