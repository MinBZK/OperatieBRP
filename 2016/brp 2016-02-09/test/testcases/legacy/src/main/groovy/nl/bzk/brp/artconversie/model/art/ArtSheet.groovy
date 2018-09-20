package nl.bzk.brp.artconversie.model.art

import static nl.bzk.brp.artconversie.model.art.Kolommen.Send_Request
import static nl.bzk.brp.artconversie.model.art.Kolommen.TestGeval

/**
 *
 */
class ArtSheet implements Iterable<ExcelRegel> {
    String defaultRequestTemplate
    String defaultEndpoint
    String defaultInterface
    String defaultOperation

    String name

    @Delegate
    List<ExcelRegel> regels = []

    /**
     * Geeft scenario's. Deze groepering is gebaseerd op de aanname
     * dat een scenario begint met een regel met een soapRequest.
     *
     * @return
     */
    def getScenarios() {
        def result = [:]

        def value = [], count = 0
        def key = "Scenario_${count}"
        regels.each { r ->
            if (r.isVerzenden()) {
                result.put(key, value)
                key = "Scenario_${count++}"
                value = []
            }
            value << r
        }
        result.put(key, value)

        return result
    }
}
