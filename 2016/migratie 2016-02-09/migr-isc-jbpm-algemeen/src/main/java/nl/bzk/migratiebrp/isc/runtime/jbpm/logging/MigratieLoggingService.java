/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime.jbpm.logging;

import java.util.Iterator;
import java.util.List;
import org.jbpm.JbpmContext;
import org.jbpm.JbpmException;
import org.jbpm.graph.log.TransitionLog;
import org.jbpm.logging.LoggingService;
import org.jbpm.logging.log.MessageLog;
import org.jbpm.logging.log.ProcessLog;
import org.jbpm.persistence.PersistenceService;

/**
 * Migratie logging service (logt enkel de transities).
 */
public final class MigratieLoggingService implements LoggingService {

    private static final long serialVersionUID = 2L;

    private final PersistenceService persistenceService;

    /**
     * Default constructor.
     */
    public MigratieLoggingService() {
        final JbpmContext jbpmContext = JbpmContext.getCurrentJbpmContext();
        if (jbpmContext == null) {
            throw new JbpmException("no active jbpm context");
        }
        persistenceService = jbpmContext.getServices().getPersistenceService();
    }

    @Override
    public void log(final ProcessLog processLog) {
        final ProcessLog filteredLog = filter(processLog);
        if (filteredLog != null) {
            persistenceService.getLoggingSession().saveProcessLog(filteredLog);
        }

    }

    private ProcessLog filter(final ProcessLog processLog) {
        filterChildren(processLog);

        if (processLog instanceof TransitionLog || processLog instanceof MessageLog || hasChildren(processLog)) {
            return processLog;
        } else {
            return null;
        }
    }

    private void filterChildren(final ProcessLog processLog) {
        final List<?> children = processLog.getChildren();
        if (children != null) {
            final Iterator<?> childrenIterator = children.iterator();
            while (childrenIterator.hasNext()) {
                final Object child = childrenIterator.next();
                boolean remove = true;
                if (child instanceof ProcessLog) {
                    remove = filter((ProcessLog) child) == null;
                }

                if (remove) {
                    childrenIterator.remove();
                }
            }
        }
    }

    private boolean hasChildren(final ProcessLog processLog) {
        final List<?> children = processLog.getChildren();
        return children != null && !children.isEmpty();
    }

    @Override
    public void close() {
    }
}
