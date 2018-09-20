/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc307;

import java.util.Map;

import nl.moderniseringgba.isc.esb.message.Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tf11Bericht;
import nl.moderniseringgba.isc.jbpm.spring.SpringDecision;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Controleert het antwoord bericht op een verzonden Tv01-bericht.
 */
@Component("uc307ControleerTv01AntwoordDecision")
public final class ControleerTv01AntwoordDecision implements SpringDecision {

    /**
     * Variabele voor logging.
     */
    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public String execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final Bericht bericht = (Bericht) parameters.get("verzendenTv01AntwoordBericht");

        if (bericht instanceof Tf11Bericht) {
            return UC307Constants.TF11_BERICHT;
        }
        // happy flow ;-)
        return null;
    }
}
