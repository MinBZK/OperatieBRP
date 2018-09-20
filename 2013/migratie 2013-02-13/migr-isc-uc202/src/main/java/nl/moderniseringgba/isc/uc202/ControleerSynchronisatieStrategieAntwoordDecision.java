/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc202;

import java.util.Map;

import nl.moderniseringgba.isc.esb.message.sync.generated.SearchResultaatType;
import nl.moderniseringgba.isc.esb.message.sync.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.sync.impl.SynchronisatieStrategieAntwoordBericht;
import nl.moderniseringgba.isc.jbpm.spring.SpringDecision;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Controleer search response.
 */
@Component("uc202ControleerSynchronisatieStrategieAntwoordDecision")
public final class ControleerSynchronisatieStrategieAntwoordDecision implements SpringDecision {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public String execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final SynchronisatieStrategieAntwoordBericht searchResponse =
                (SynchronisatieStrategieAntwoordBericht) parameters.get("synchronisatieStrategieAntwoordBericht");

        final String result;
        if (searchResponse.getStatus() == StatusType.OK) {
            if (searchResponse.getResultaat() == SearchResultaatType.NEGEREN) {
                result = "4e. Negeren";
            } else if (searchResponse.getResultaat() == SearchResultaatType.ONDUIDELIJK) {
                // Bekijken of er al een beheerderkeuze is gemaakt. Zo ja, die doorgeven.
                final SearchResultaatType beheerderKeuze = (SearchResultaatType) parameters.get("beheerderKeuze");
                if (beheerderKeuze != null) {
                    result = null;
                } else {
                    result = "4c. Onduidelijke situatie";
                }
            } else {
                result = null;
            }
        } else {
            result = "4b. Fout";
        }

        return result;
    }
}
