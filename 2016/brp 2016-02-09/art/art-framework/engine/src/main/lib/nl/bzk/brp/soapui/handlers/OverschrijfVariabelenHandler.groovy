package nl.bzk.brp.soapui.handlers

import groovy.transform.CompileStatic
import nl.bzk.brp.soapui.steps.DataSourceValues
import org.apache.commons.lang.StringUtils
import org.apache.log4j.Logger

/**
 * Handler die variabelen overschrijft door een csv-key-value string om te zetten naar variabelen.
 */
@CompileStatic
class OverschrijfVariabelenHandler {

    static final Logger log = Logger.getLogger(FileHandler.class)

    /**
     * De methode die de variabelen uit de excel-kolom omzet naar de datasourceValues.
     * @param step_DATASOURCE_VALUES De datasourceValues, hierin zitten alle art-variabelen uit de data-sheets.
     * @param overschijfVariabelenString De csv-key-value string uit de excel-sheet.
     */
    static void laadOverschrijfVariabelen(
            DataSourceValues step_DATASOURCE_VALUES, String overschijfVariabelenString) {
        if (!StringUtils.isEmpty(overschijfVariabelenString)) {
            log.info "overschrijf variabelen: $overschijfVariabelenString"

            Properties props = new Properties();
            props.load(new StringReader(overschijfVariabelenString))

            for(Map.Entry property : props.entrySet()){
                String variabeleSleutel = property.getKey()
                String variabeleWaarde = property.getValue()
                if(variabeleWaarde.endsWith(",")){
                    variabeleWaarde = variabeleWaarde.substring(0, variabeleWaarde.size() - 1)
                }

                log.info "overschrijf variabele: $variabeleSleutel, waarde: '$variabeleWaarde'"
                step_DATASOURCE_VALUES.setPropertyValue(variabeleSleutel, variabeleWaarde)
            }
        }
    }

}
