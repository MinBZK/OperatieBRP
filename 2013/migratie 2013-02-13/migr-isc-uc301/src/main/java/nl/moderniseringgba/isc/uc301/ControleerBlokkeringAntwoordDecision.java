/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc301;

import java.util.Map;

import nl.moderniseringgba.isc.esb.message.sync.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.sync.impl.BlokkeringAntwoordBericht;
import nl.moderniseringgba.isc.jbpm.spring.SpringDecision;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Controleer blokkering antwoord.
 */
@Component("uc301ControleerBlokkeringAntwoordDecision")
public final class ControleerBlokkeringAntwoordDecision implements SpringDecision {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public String execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final BlokkeringAntwoordBericht blokkeringAntwoord =
                (BlokkeringAntwoordBericht) parameters.get("blokkeringAntwoordBericht");

        final String transitionName;

        if (StatusType.OK.equals(blokkeringAntwoord.getStatus())) {
            transitionName = null;
        } else if (StatusType.GEBLOKKEERD.equals(blokkeringAntwoord.getStatus())) {
            transitionName = "6c. Geblokkeerd";
        } else {
            transitionName = "6b. Fout";
        }

        return transitionName;
    }
}
