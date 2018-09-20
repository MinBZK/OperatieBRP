/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime.jbpm.persistence;

import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jbpm.db.ContextSession;
import org.jbpm.db.GraphSession;
import org.jbpm.db.JobSession;
import org.jbpm.db.LoggingSession;
import org.jbpm.db.TaskMgmtSession;
import org.jbpm.persistence.JbpmPersistenceException;
import org.jbpm.persistence.PersistenceService;
import org.jbpm.svc.Services;
import org.jbpm.tx.TxService;

/**
 * Persistence service die werkt via hibernate 4.
 *
 * @see org.jbpm.persistence.db.DbPersistenceService
 */
public final class Hibernate4PersistenceService implements PersistenceService {
    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LoggerFactory.getLogger();

    private final SessionFactory sessionFactory;

    private transient Session session;
    private transient GraphSession graphSession;
    private transient TaskMgmtSession taskMgmtSession;
    private transient JobSession jobSession;
    private transient ContextSession contextSession;
    private transient LoggingSession loggingSession;

    /**
     * Constructor.
     *
     * @param sessionFactory
     *            session factory
     */
    public Hibernate4PersistenceService(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void close() {
    }

    @Override
    public void assignId(final Object object) {
        LOG.debug("Hibernate4PersistenceService.assignId(object={})", object);
        try {
            getSession().save(object);
        } catch (final HibernateException e) {
            throw new JbpmPersistenceException("could not assign id to " + object, e);
        }
    }

    private static TxService getTxService() {
        return (TxService) Services.getCurrentService(Services.SERVICENAME_TX, false);
    }

    private Session getSession() {
        if (session == null) {
            session = sessionFactory.getCurrentSession();
        }
        return session;
    }

    @Override
    public GraphSession getGraphSession() {
        LOG.debug("getGraphSession())");
        if (graphSession == null) {
            graphSession = new GraphSession(getSession());
        }
        return graphSession;
    }

    @Override
    public LoggingSession getLoggingSession() {
        LOG.debug("getLoggingSession())");
        if (loggingSession == null) {
            loggingSession = new LoggingSession(getSession());
        }
        return loggingSession;
    }

    @Override
    public JobSession getJobSession() {
        LOG.debug("getJobSession())");
        if (jobSession == null) {
            jobSession = new JobSession(getSession());
        }
        return jobSession;
    }

    @Override
    public ContextSession getContextSession() {
        LOG.debug("getContextSession())");
        if (contextSession == null) {
            contextSession = new ContextSession(getSession());
        }
        return contextSession;
    }

    @Override
    public TaskMgmtSession getTaskMgmtSession() {
        LOG.debug("getTaskMgmtSession())");
        if (taskMgmtSession == null) {
            taskMgmtSession = new TaskMgmtSession(getSession());
        }
        return taskMgmtSession;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Object getCustomSession(final Class sessionClass) {
        LOG.debug("getCustomSession())");
        if (Session.class.isAssignableFrom(sessionClass)) {
            return getSession();
        } else {
            throw new UnsupportedOperationException("Hiberante4PersistenceService.getCustomSession niet ondersteund.");
        }
    }

    /**
     * @deprecated use {@link TxService#isRollbackOnly()} instead
     * @return {@link txService#isRollbackOnly()}
     */
    @Deprecated
    @Override
    public boolean isRollbackOnly() {
        final TxService txService = getTxService();
        return txService != null ? txService.isRollbackOnly() : false;
    }

    /**
     * @deprecated use {@link TxService#setRollbackOnly()} instead
     */
    @Deprecated
    @Override
    public void setRollbackOnly() {
        final TxService txService = getTxService();
        if (txService != null) {
            txService.setRollbackOnly();
        }
    }

    /**
     * @deprecated use {@link TxService#setRollbackOnly()} instead
     * @param isRollbackOnly
     *            true, to set to rollback only
     * @throws IllegalArgumentException
     *             if <code>rollbackOnly</code> is <code>false</code>
     */
    @Deprecated
    @Override
    public void setRollbackOnly(final boolean isRollbackOnly) {
        if (!isRollbackOnly) {
            throw new IllegalArgumentException("cannot unmark transaction for rollback");
        }
        setRollbackOnly();
    }

    @Override
    public void setGraphSession(final GraphSession graphSession) {
        throw new UnsupportedOperationException("Hiberante4PersistenceService.setGraphSession niet ondersteund.");
    }

    @Override
    public void setLoggingSession(final LoggingSession loggingSession) {
        throw new UnsupportedOperationException("Hiberante4PersistenceService.setLoggingSession niet ondersteund.");
    }

    @Override
    public void setJobSession(final JobSession jobSession) {
        throw new UnsupportedOperationException("Hiberante4PersistenceService.setJobSession niet ondersteund.");
    }

    @Override
    public void setTaskMgmtSession(final TaskMgmtSession taskMgmtSession) {
        throw new UnsupportedOperationException("Hiberante4PersistenceService.setTaskMgmtSession niet ondersteund.");
    }

}
