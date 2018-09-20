/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc202;

import java.util.Map;

import nl.moderniseringgba.isc.esb.message.sync.impl.BlokkeringInfoAntwoordBericht;
import nl.moderniseringgba.isc.jbpm.spring.SpringDecision;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Controleer verhuizing.
 */
@Component("uc202ControleerVerhuizingDecision")
public final class ControleerVerhuizingDecision implements SpringDecision {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public String execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final BlokkeringInfoAntwoordBericht blokkeringInfoAntwoord =
                (BlokkeringInfoAntwoordBericht) parameters.get("blokkeringInfoAntwoordBericht");

        if (blokkeringInfoAntwoord == null || blokkeringInfoAntwoord.getPersoonsaanduiding() == null) {
            return "8f. Geen verhuizing";
        } else {
            // Als persoonsaanduiding gevuld is dan moet dit over een verhuizing van BRP naar LO3 (gba/rni) gaan.
            // Anders was het proces al eerder geknald.
            return null;
        }
    }
}
