/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc311;

import java.util.Map;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringDecision;

import org.jbpm.graph.exe.ExecutionContext;
import org.springframework.stereotype.Component;

/**
 * Controleer de herhalingen voor Wa01. Dit moet in een decision gebeuren omdat er gebruik wordt gemaakt van token scope
 * variabele en dat blijkbaar niet lekker werkt in een condition.
 */
@Component("uc311ControleerHerhalingenWa01Decision")
public final class ControleerHerhalingenWa01Decision implements SpringDecision {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public String execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        // Herhaling
        final ExecutionContext executionContext = ExecutionContext.currentExecutionContext();
        final Number herhaling = (Number) executionContext.getVariable("wa01Herhaling");
        final Number maxHerhaling = (Number) executionContext.getVariable("wa01HerhalingMaxHerhalingen");

        if (herhaling != null && herhaling.intValue() > maxHerhaling.intValue()) {
            return "4a. Maximum herhalingen";
        } else {
            return null;
        }

    }

}
