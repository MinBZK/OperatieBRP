package nl.bzk.brp.soapui.excel

import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.WorkbookFactory

/**
 * Representeert een Resultaat sheet.
 */
class OutputExcel extends AbstractExcel {
    File fileOut

    /**
     * Constructor.
     * @param resultFile
     * @param templateFile
     * @param sheetnaam
     */
    OutputExcel(File resultFile, File templateFile, String sheetnaam) {
        super(WorkbookFactory.create(new FileInputStream(templateFile)))

        this.fileOut = resultFile
        this.excelSheet = workbook.getSheet(sheetnaam)

        this.initKolomNrs()
    }

    /**
     * Schrijft de resultaten in het excel bestand.
     * @param resultaten
     */
    void schrijfResultaten(List<Properties> resultaten) {
        int rowNr = excelSheet.getLastRowNum() + 1

        try {
            resultaten.each { Properties prop ->
                Row row = excelSheet.createRow(rowNr)
                kolomNrs.eachWithIndex { kol, i ->
                    row.createCell(kolomNrs[i]).setCellValue(prop.getProperty(kolomNamen[i]))
                }
                rowNr++
            }
        } finally {
            workbook.write(new FileOutputStream(fileOut))
            workbook.close()
        }
    }

    @Override
    List<String> getKolomnamen() { OutputKolommen.kolomnamen() }

    @Override
    List<String> getOptioneleKolomnamen() { return [] }
}
