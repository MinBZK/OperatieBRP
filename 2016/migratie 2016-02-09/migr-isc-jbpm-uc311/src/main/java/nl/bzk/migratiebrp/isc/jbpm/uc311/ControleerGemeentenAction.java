/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc311;

import java.util.Date;
import java.util.Map;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.NoSignal;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
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
        LOG.debug("token: {}", token.getId());
        if (token.getParent() != null) {
            if (token.getName().startsWith(BepaalGemeentenAction.TOKEN_PREFIX)) {
                // Dit child token beeindigen
                LOG.debug("Child token beeindigen");
                token.end(false);

                // Token is nu parent token
                token = token.getParent();
                if (!token.getLockOwner().startsWith(BepaalGemeentenAction.LOCK)) {
                    throw new IllegalStateException("Hoofd token niet correct gelocked!");
                }

                // Update lock op parent token (vanwege concurrency)
                token.forceUnlock();
                token.lock(BepaalGemeentenAction.LOCK + "_" + new Date());
            } else {
                throw new IllegalStateException("Onbekend soort child token!");
            }
        }

        // Controle juiste 'hoofd-token'
        LOG.debug("token (zou parent moeten zijn): {}", token.getId());
        if (!token.getNode().equals(executionContext.getNode())) {
            // Child token (executionContext.getNode) is al gearriveerd, maar hoofd token (token.getNode) nog niet, dan
            // hoeven we niets te doen.
            LOG.debug("Controle hoofd token genegeerd omdat het hoofdtoken nog niet in dit node gearriveerd is.");
        } else {
            if (token.getLockOwner() == null || !token.getLockOwner().startsWith(BepaalGemeentenAction.LOCK)) {
                throw new IllegalStateException("Hoofd token niet correct gelocked!");
            } else {
                if (!token.hasActiveChildren()) {
                    token.forceUnlock();
                    LOG.info("hoofd proces vervolgen");
                    new ExecutionContext(token).leaveNode();
                } else {
                    LOG.info("hoofd proces niet vervolgen, er zijn nog actieve child processen");
                }
            }
        }

        LOG.info("done");
        return null;
    }
}
