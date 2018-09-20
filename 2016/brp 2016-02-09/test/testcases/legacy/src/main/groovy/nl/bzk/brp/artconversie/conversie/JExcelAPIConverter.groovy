package nl.bzk.brp.artconversie.conversie

import jxl.CellType
import jxl.StringFormulaCell
import jxl.Workbook
import jxl.write.Label
import jxl.write.WritableCell
import jxl.write.WritableSheet
import jxl.write.WritableWorkbook
import nl.bzk.brp.funqmachine.datalezers.excel.ExcelUtil
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Converteer een ART excelbestand naar gebruik voor de Funqmachine.
 * Vervang keys in de datakolom naar Freemarker compatible formaat, vervang excel-formules door
 * waardes waar deze verwijzen naar de ART-sheet. Gooi de ART-sheet weg.
 */
class JExcelAPIConverter implements XlsDataConverter {
    private final Logger logger = LoggerFactory.getLogger(JExcelAPIConverter)

    void convert(File xlsFile) {
        // open stuff
        Workbook orig = Workbook.getWorkbook(xlsFile)
        WritableWorkbook copy = Workbook.createWorkbook(new File(xlsFile.parent, "${xlsFile.name}"), orig)

        // process
        if (copy.numberOfSheets == 1) {
            copy.close()
            orig.close()
            xlsFile.delete()

            return
        }

        (1..<copy.numberOfSheets).each {int index ->
            WritableSheet sheet = copy.getSheet(index)
            for (int r = 0; r < sheet.rows; r++) {
                for (int c = 0; c < sheet.columns; c++) {
                    WritableCell cell = sheet.getWritableCell(c, r)

                    if (c == 0) {
                        if (CellType.LABEL == cell.type) {
                            Label l = (Label) cell
                            l.setString(KeyNamerStrategy.rename(cell.contents))
                        }
                    } else {
                        if (CellType.LABEL == cell.type) {
                            def matcher = cell.contents =~ /\[(\w+(\(.*\))?)\]/
                            if (matcher.matches() && 'spatie' != matcher[0][1]) {
                                Label l = (Label) cell
                                String value = matcher[0][1]

                                if (!value.endsWith(')')) { value = "$value()" }
                                l.setString("\${$value}")
                            }
                        }
                        if (CellType.STRING_FORMULA == cell.type) {
                            StringFormulaCell f = (StringFormulaCell) cell

                            logger.info('String formula ({}{}), waarde={}, formula= {}', ExcelUtil.indexToColumnLetter(f.column), f.row, f.contents, f.formula)

                            if (f.formula =~ /.*ART[!]?\$.*/) {
                                sheet.addCell(new Label(f.column, f.row, f.contents))
                            }
                        }
                    }
                }
            }
        }

        // verwijder de eerste sheet (ART)
        copy.removeSheet(0)

        // close stuff
        copy.write()
        copy.close()
    }
}
