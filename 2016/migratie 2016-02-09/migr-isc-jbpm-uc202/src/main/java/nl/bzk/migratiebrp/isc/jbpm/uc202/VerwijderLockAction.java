/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc202;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.migratiebrp.isc.jbpm.common.FoutUtil;
import nl.bzk.migratiebrp.isc.jbpm.common.locking.LockException;
import nl.bzk.migratiebrp.isc.jbpm.common.locking.LockService;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringActionHandler;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Maak een unlock bericht voor een bepaald lock id.
 */
@Component("uc202VerwijderLockAction")
public final class VerwijderLockAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final String FOUT = "8b. Technische fout";
    private static final String FOUTMELDING_VARIABELE = "actieFoutmelding";

    @Inject
    private LockService lockingService;

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.debug("execute(parameters={})", parameters);
        final Long lockId = (Long) parameters.get("lockId");

        final Map<String, Object> result = new HashMap<>();
        try {
            lockingService.verwijderLock(lockId);
        } catch (final LockException e) {
            result.put(FOUTMELDING_VARIABELE, FoutUtil.beperkFoutmelding(e.getMessage()));
            result.put(SpringActionHandler.TRANSITION_RESULT, FOUT);
        }

        LOG.debug("result: {}", result);
        return result;
    }

}
