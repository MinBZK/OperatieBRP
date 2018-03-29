/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf;

import java.sql.Connection;
import java.sql.SQLException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.jbpm.JbpmContext;

/**
 * JBPM SQL Helper.
 */
public final class JbpmSqlHelper {

    private JbpmSqlHelper() {
        // Niet instantieerbaar
    }

    /**
     * Execute sql.
     * @param jbpmContext jbpm context
     * @param sql sql
     * @param <T> result type
     * @return result
     */
    public static <T> T execute(final JbpmContext jbpmContext, final Sql<T> sql) {

        try {
            final Worker<T> worker = new Worker<>(sql);
            final Session hibernateSession = (Session) jbpmContext.getServices().getPersistenceService().getCustomSession(Session.class);
            hibernateSession.doWork(worker);
            return worker.getResult();

        } catch (final HibernateException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Sql interface.
     * @param <T> result type
     */
    public interface Sql<T> {
        /**
         * Use to given connection to execute sql.
         * @param connection connection
         * @return result
         * @throws SQLException on sql errors
         */
        T execute(final Connection connection) throws SQLException;
    }

    /**
     * Worker.
     * @param <T> result type
     */
    private static final class Worker<T> implements Work {
        /**
         * result.
         */
        private T result;
        private final Sql<T> sql;

        protected Worker(final Sql<T> sql) {
            this.sql = sql;
        }

        @Override
        public void execute(final Connection connection) throws SQLException {
            result = sql.execute(connection);
        }

        /**
         * Gets the result.
         * @return the result
         */
        T getResult() {
            return result;
        }

    }
}
