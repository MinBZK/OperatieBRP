package nl.bzk.brp.soapui.excel

import nl.bzk.brp.soapui.excel.exceptions.HeaderCellNietAanwezig
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellValue
import org.apache.poi.ss.usermodel.FormulaEvaluator
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook

/**
 * Abstracte klasse voor abstractie over een ART Excel bestand.
 */
abstract class AbstractExcel {
    protected List<String> kolomNamen = []
    protected List<String> mogelijkeLegeKolommen = []

    protected Sheet excelSheet
    Workbook workbook
    Integer[] kolomNrs
    FormulaEvaluator       evaluator

    /**
     * Constructor.
     */
    AbstractExcel(Workbook workbook) {
        this.workbook = workbook
        this.evaluator = workbook.creationHelper.createFormulaEvaluator()

        kolomNamen = getKolomnamen()
        mogelijkeLegeKolommen = getOptioneleKolomnamen()
    }

    /**
     * Geeft de kolomnamen voor een excel bestand.
     * @return lijst van kolomnamen
     */
    abstract List<String> getKolomnamen();

    /**
     * Geeft de kolomnamen die mogen ontbreken in de ART sheet.
     * @return lijst van kolomnamen
     */
    abstract List<String> getOptioneleKolomnamen();

    /*
     * Maakt een interne lijst van de index van de kolommen in de excelsheet.
     */
    protected void initKolomNrs() {
        kolomNrs = new Integer[kolomNamen.size()]
        for (int i = 0; i < kolomNamen.size(); i++) {
	    Cell cell = this.zoekHeaderCell(excelSheet, kolomNamen[i])
            if (cell) {
                kolomNrs[i] = cell.columnIndex
            }
        }
    }

    /*
     * Zoekt de juiste kolom bij de header waarde.
     *
     * @param sheet sheet waarin wordt gezocht
     * @param headerNaam waarde van de cel
     * @return Cell die de waarde bevat
     */
    protected Cell zoekHeaderCell(Sheet sheet, String headerNaam) {
        Cell cell = sheet.first().find { Cell it -> it && it.cellType == Cell.CELL_TYPE_STRING && headerNaam.equals(getCellContent(it)) }

        if (cell) {
            return cell
        } else {
            if (headerNaam in mogelijkeLegeKolommen) {
                log.debug "deze cell [${headerNaam}] mag ontbreken"
                return null
            }
            throw new HeaderCellNietAanwezig("Kan geen Cell vinden: ${headerNaam}")
        }
    }

    protected String getCellContent(Cell cell) {
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
