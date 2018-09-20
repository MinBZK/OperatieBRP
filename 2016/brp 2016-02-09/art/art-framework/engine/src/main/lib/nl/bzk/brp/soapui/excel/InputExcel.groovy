package nl.bzk.brp.soapui.excel

import nl.bzk.brp.soapui.excel.exceptions.DataSheetNietGevonden
import nl.bzk.brp.soapui.steps.ControlValues
import org.apache.log4j.Logger
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.WorkbookFactory

/**
 * Representeert een ART test sheet.
 */
class InputExcel extends AbstractExcel {
    private static final Logger log = Logger.getLogger('log')

    /**
     * Constructor.
     * @param file het excelbestand
     * @param controlSheet de naam van de sheet met de controldata
     */
    InputExcel(File file, String controlSheet) {
        this(file, controlSheet, new ArrayList<String>(0))
    }

    /**
     * Constructor.
     * @param file het excelbestand
     * @param controlSheet de naam van de sheet met de controldata
     * @param dataSheets lijst namen van sheets met data
     */
    InputExcel(File file, String controlSheet, List<String> dataSheets) {
        super(WorkbookFactory.create(file))
        this.excelSheet = workbook.getSheet(controlSheet)

        initKolomNrs()

        dataSheets.each { sheet ->
            if (workbook.getSheet(sheet) == null) {
                throw new DataSheetNietGevonden("Kan de data-sheet '${sheet}' niet vinden")
            }
        }
    }

    @Override
    List<String> getKolomnamen() { return InputKolommen.kolomnamen() }

    @Override
    List<String> getOptioneleKolomnamen() { return InputKolommen.nietVerplichteKolomnamen() }

    /**
     * Geeft de cell van het testgeval op de gegeven rij.
     *
     * @param rijIndex index van de te lezen rij
     * @return de eerste cell van de gevraagde rij
     */
    Cell getTestGeval(int rijIndex) {
        excelSheet.getRow(rijIndex).getCell(kolomNrs[0], Row.RETURN_BLANK_AS_NULL)
    }

    /**
     * Leest de waardes op een regel in de excelsheet en zet deze als properties op het doel.
     *
     * @param rijIndex de index (0-based) van de rij om te lezen
     * @param target het doel om de waardes in te zetten
     */
    void laadRegel(int rijIndex, ControlValues target) {
        for (int i = 0; i < kolomNamen.size(); i++) {
            String naam = kolomNamen[i]
            if (kolomNrs[i] != null) {
                Cell cell = excelSheet.getRow(rijIndex).getCell(kolomNrs[i], Row.RETURN_BLANK_AS_NULL)
                if (cell && getCellContent(cell)) {
                    target.setPropertyValue(naam, getCellContent(cell))
                }
            }
        }
    }

    /**
     * Geeft het nummer van de laatste rij.
     *
     * @return de laatste rij. !NB: nummer, geen index.
     */
    int laatsteRij() {
        excelSheet.getLastRowNum() + 1
    }
}
