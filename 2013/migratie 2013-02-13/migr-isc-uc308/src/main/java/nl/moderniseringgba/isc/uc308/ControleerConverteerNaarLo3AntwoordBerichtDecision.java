/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc308;

import java.util.Map;

import nl.moderniseringgba.isc.esb.message.sync.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.sync.impl.ConverteerNaarLo3AntwoordBericht;
import nl.moderniseringgba.isc.jbpm.spring.SpringDecision;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Controleer de conversie van de persoonslijst.
 */
@Component("uc308ControleerConverteerNaarLo3AntwoordBerichtDecision")
public final class ControleerConverteerNaarLo3AntwoordBerichtDecision implements SpringDecision {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public String execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final ConverteerNaarLo3AntwoordBericht converteerNaarLo3AntwoordBericht =
                (ConverteerNaarLo3AntwoordBericht) parameters.get(UC308Constants.CONVERTEER_NAAR_LO3_ANTWOORD);

        if (!StatusType.OK.equals(converteerNaarLo3AntwoordBericht.getStatus())) {
            return UC308Constants.CONVERSIE_MISLUKT;
        }

        return null;
    }
}
