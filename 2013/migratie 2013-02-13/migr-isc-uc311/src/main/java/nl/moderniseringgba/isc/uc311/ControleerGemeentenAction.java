/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc311;

import java.util.Map;

import nl.moderniseringgba.isc.jbpm.spring.NoSignal;
import nl.moderniseringgba.isc.jbpm.spring.SpringAction;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;
import org.springframework.stereotype.Component;

/**
 * Deze actionhandler controleert dat alle wa01 bericht zijn afgehandeld. Dit is de tegenhanger van
 * {@link BepaalGemeentenAction}; wanneer alle child tokens zijn afgehandeld wordt de default transition genomen om het
 * proces verder af te handelen.
 */
@Component("uc311ControleerGemeentenAction")
public final class ControleerGemeentenAction implements SpringAction, NoSignal {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final ExecutionContext executionContext = ExecutionContext.currentExecutionContext();
        Token token = executionContext.getToken();

        // Eerst kijken of dit om een child token gaat
        if (token.getParent() != null && BepaalGemeentenAction.LOCK.equals(token.getParent().getLockOwner())) {
            // Dit child token beeindigen
            token.end(false);
            token = token.getParent();
        }

        // Nu het parent token waarop is geforked bekijken
        if (BepaalGemeentenAction.LOCK.equals(token.getLockOwner())) {
            if (!token.hasActiveChildren()) {
                token.unlock(BepaalGemeentenAction.LOCK);
                new ExecutionContext(token).leaveNode();
            }
        }

        return null;
    }
}
