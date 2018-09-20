/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc201;

import java.util.Map;

import nl.moderniseringgba.isc.jbpm.spring.SpringDecision;

import org.springframework.stereotype.Component;

/**
 * Controleer synchronisatie.
 */
@Component("uc201ControleerSynchronisatieDecision")
public final class ControleerSynchronisatieDecision implements SpringDecision {

    @Override
    public String execute(final Map<String, Object> parameters) {
        return null;
    }

}
