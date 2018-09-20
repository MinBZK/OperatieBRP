package scripts.teststeps

import nl.bzk.brp.soapui.ARTVariabelen
import nl.bzk.brp.soapui.excel.InputExcel
import nl.bzk.brp.soapui.handlers.FileHandler
import nl.bzk.brp.soapui.handlers.PropertiesHandler
import nl.bzk.brp.soapui.handlers.TestRequestTestStepHandler
import nl.bzk.brp.soapui.steps.ControlValues
import nl.bzk.brp.soapui.steps.Counters
import org.apache.poi.ss.usermodel.Cell

/**
 * Lees een regel uit de ART worksheet en zet deze in de property-step 'Control Values'.
 * De volgende stap zet de correcte stappen aan/uit adhv. vlaggetjes op deze regel (en evt. lege cellen)
 */

FileHandler fileHandler = FileHandler.fromContext(context)
ControlValues step_CONTROL_VALUES = ControlValues.fromContext(context)
Counters step_COUNTERS = Counters.fromContext(context)

step_CONTROL_VALUES.clearProperties()

// geen check op datasheets, als die overschreven kunnen zijn door een redirection van ART
InputExcel artSheet = fileHandler.geefArtExcel()

loadControlregelFromExcel(artSheet, step_COUNTERS, step_CONTROL_VALUES, log)
laadPropertiesFile(ARTVariabelen.getScriptPath(context), step_CONTROL_VALUES.getAltTestProperties())

overruleTestProperties(step_CONTROL_VALUES)

TestRequestTestStepHandler.resetOperation(testRunner, context, log)

/* Zet het executie moment. */

def now = new Date()
def before = new Date(now.getTime()-120000)
System.out.println("Current time: " + now)
System.out.println("2 minutes old: " + before)
step_CONTROL_VALUES.setExecutionTimestamp(before)
System.out.println("Done setting time in Read_Excel_and_Write_Control_Values.groovy")


// -- Private methods --------------------------------------------------------
/*
 * Haalt een regel uit de excelsheet, of te wel een testcase.
 */
private boolean loadControlregelFromExcel(InputExcel artSheet, Counters counters, ControlValues controlValues, def log) {
    int huidigRijIndex = counters.getExecuteRow() - 1
    int maxRij = artSheet.laatsteRij()

    if (huidigRijIndex > 0) {
        // zoek de eerste niet lege rij OP of NA de huidigRij en (kleiner dan de MaxRij)
        Cell cellTestgeval = artSheet.getTestGeval(huidigRijIndex)
        while ((cellTestgeval == null || cellTestgeval.stringCellValue == null) && huidigRijIndex < maxRij) {
            cellTestgeval = artSheet.getTestGeval(huidigRijIndex)
            huidigRijIndex = huidigRijIndex + 1;
        }

        if (cellTestgeval == null || cellTestgeval.stringCellValue == null) {
            log.error 'Kan geen controle cel meer vinden.'
            counters.setExecuteRow(0)
            return false
        }

        artSheet.laadRegel(huidigRijIndex, controlValues)
        counters.setExecuteRow(huidigRijIndex + 1)
    }

    return true
}

/*
 * Laadt een .properties file
 *
 * @param scriptPath
 * @param altFile
 */
private void laadPropertiesFile(String scriptPath, String altFile) {
    log.info("ALT_TP_PATH 1 = " + altFile);
    File path = new File(scriptPath)
    if (altFile) {
        //overruled test input sheet
        log.info("ALT_TP_PATH 2 = " + altFile);
        path = new File(scriptPath, altFile)
    } else {
        path = new File(scriptPath)
    }
    loadAlternativePropertiesFromFile(path.absolutePath, 'test.properties');

}

/*
 * Lees properties van file en stop deze in de testCase properties.
 */
private void loadAlternativePropertiesFromFile(String dir, String filename) {
    if (dir.length() > 3) {
        Properties props = PropertiesHandler.loadProperties(dir, filename)
        log.info("[TestCase Setup Script] Goed geladen bestand: ${dir}, ${filename}");

        props.put(ARTVariabelen.alt_test_script_path, dir)
        if (props.keySet().contains(ARTVariabelen.ART_INPUT_FILE)) {
            props.put(ARTVariabelen.alt_art_input_file, props.getProperty(ARTVariabelen.ART_INPUT_FILE))
            props.remove(ARTVariabelen.ART_INPUT_FILE)
        }

        PropertiesHandler.setProperties(props, testRunner.testCase)
    }
}

/*
 * Set properties als die zijn opgeslagen in ART input sheet
 */
private void overruleTestProperties(ControlValues controlValues) {
    String soapInterface = controlValues.getSoapInterface()
    String soapOperation = controlValues.getSoapOperation()
    String soapEndpoint = controlValues.getSoapEndpoint()
    String requestTemplate = controlValues.getRequestTemplate()

    if (soapInterface && soapOperation && soapEndpoint && requestTemplate) {
        log.info "$soapInterface :: $soapOperation :: $soapEndpoint :: $requestTemplate"

        if (soapInterface.length() > 5 && soapOperation.length() > 5 && soapEndpoint.length() > 5 && requestTemplate.length() > 5) {
            Properties props = new Properties()
            props.put(ARTVariabelen.SOAP_INTERFACE, soapInterface)
            props.put(ARTVariabelen.SOAP_OPERATION, soapOperation)
            props.put(ARTVariabelen.SOAP_ENDPOINT, soapEndpoint)
            props.put(ARTVariabelen.REQUEST_TEMPLATE_FILE, requestTemplate)

            PropertiesHandler.setProperties(props, testRunner.testCase)
        }
    }
}
