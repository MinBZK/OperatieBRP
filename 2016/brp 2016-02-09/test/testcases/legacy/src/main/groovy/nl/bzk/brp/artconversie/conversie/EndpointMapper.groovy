package nl.bzk.brp.artconversie.conversie

import org.slf4j.Logger
import org.slf4j.LoggerFactory
/**
 *
 */
class EndpointMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(EndpointMapper)

    private static map = [
        'endpoint_bhgVerblijfAdres'                    : ['${applications.host}/bijhouding/BijhoudingService/bhgVerblijfAdres', 'http://www.bzk.nl/brp/bijhouding/service'],
        'endpoint_bhgDocumentVerzoekMededeling'        : ['${applications.host}/bijhouding/BijhoudingService/bhgDocumentVerzoekMededeling', 'http://www.bzk.nl/brp/bijhouding/service'],
        'endpoint_bhgHuwelijkGeregistreerdPartnerschap': ['${applications.host}/bijhouding/BijhoudingService/bhgHuwelijkGeregistreerdPartnerschap', 'http://www.bzk.nl/brp/bijhouding/service'],
        'endpoint_bhgAfstamming'                       : ['${applications.host}/bijhouding/BijhoudingService/bhgAfstamming', 'http://www.bzk.nl/brp/bijhouding/service'],
        'endpoint_bhgNationaliteit'                    : ['${applications.host}/bijhouding/BijhoudingService/bhgNationaliteit', 'http://www.bzk.nl/brp/bijhouding/service'],
        'endpoint_bhgReisdocument'                     : ['${applications.host}/bijhouding/BijhoudingService/bhgReisdocument', 'http://www.bzk.nl/brp/bijhouding/service'],
        'endpoint_bhgOverlijden'                       : ['${applications.host}/bijhouding/BijhoudingService/bhgOverlijden', 'http://www.bzk.nl/brp/bijhouding/service'],
        'endpoint_bhgNaamGeslacht'                     : ['${applications.host}/bijhouding/BijhoudingService/bhgNaamGeslacht', 'http://www.bzk.nl/brp/bijhouding/service'],
        'endpoint_bhgVerkiezingen'                     : ['${applications.host}/bijhouding/BijhoudingService/bhgVerkiezingen', 'http://www.bzk.nl/brp/bijhouding/service'],

        'endpointBevraging'                            : ['${applications.host}/bevraging/BijhoudingBevragingService/bhgBevraging', 'http://www.bzk.nl/brp/bijhouding/bevraging/service'],
        'endpointBevraging - Levering'                 : ['${applications.host}/bevraging/LeveringBevragingService/lvgBevraging', 'http://www.bzk.nl/brp/levering/bevraging/service'],
        'endpointBevraging-Levering'                   : ['${applications.host}/bevraging/LeveringBevragingService/lvgBevraging', 'http://www.bzk.nl/brp/levering/bevraging/service'],
        'endpointAfnemerindicatie'                     : ['${applications.host}/afnemerindicaties/AfnemerindicatiesService/lvgAfnemerindicaties', 'http://www.bzk.nl/brp/levering/afnemerindicaties/service'],
        'endpoint_lvgSynchronisatie'                   : ['${applications.host}/synchronisatie/SynchronisatieService/lvgSynchronisatie', 'http://www.bzk.nl/brp/levering/synchronisatie/service']
    ]

    static List map(String input) {
        def result = map.get(input)

        if (!result) {
            LOGGER.error 'Geen endpoint mapping voor {}', input
        }
        return result
    }
}
