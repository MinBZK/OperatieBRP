/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.foutafhandeling;

import java.util.Map;

import nl.moderniseringgba.isc.esb.message.sync.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.sync.impl.DeblokkeringAntwoordBericht;
import nl.moderniseringgba.isc.jbpm.spring.SpringDecision;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Controleer deblokkering antwoord.
 */
@Component("foutafhandelingControleerDeblokkeringAntwoordDecision")
public final class ControleerDeblokkeringAntwoordDecision implements SpringDecision {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public String execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final DeblokkeringAntwoordBericht deblokkeringAntwoord =
                (DeblokkeringAntwoordBericht) parameters.get(FoutafhandelingConstants.BERICHT_ANTWOORD_DEBLOKKERING);

        return deblokkeringAntwoord.getStatus() == StatusType.OK ? null : "fout";
    }
}
