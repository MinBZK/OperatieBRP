/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common.locking;

import java.util.Set;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Default lock service implementatie.
 */
@Component
public final class LockServiceImpl implements LockService {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final LockingDao aNummerLockingDao = new JbpmLockingDao();

    @Override
    @SuppressWarnings("checkstyle:illegalcatch")
    public Long verkrijgLockVoorAnummers(final Set<Long> aNummers, final Long processInstanceId) throws LockException, LockedException {
        if (aNummers == null || aNummers.isEmpty()) {
            throw new LockException("Geen a-nummers meegegeven voor lock.", null);
        }

        // Controleer of er geen ANummers al gelocked zijn.
        try {
            return aNummerLockingDao.lock(processInstanceId, aNummers);
            // De JbpmDao kan een RuntimeException of andere exceptie gooien. Voor de functionele
            // werking willen we deze omzetten naar een LockException.
        } catch (final Exception exception) {
            LOG.error("Onverwachte fout tijdens verkrijgen lock", exception);
            throw new LockedException("Lock kon niet worden verkregen: " + exception.getMessage(), exception);
        }
    }

    @Override
    @SuppressWarnings("checkstyle:illegalcatch")
    public void verwijderLock(final Long lockId) throws LockException {

        try {
            aNummerLockingDao.unlock(lockId);
            // De JbpmDao kan alleen een Runtime exception gooien. Voor de functionele werking willen we
            // deze omzetten naar een LockException.
        } catch (final Exception exception) {
            throw new LockException("Technische fout: Verwijderen van lock is mislukt: ", exception);
        }

    }
}
