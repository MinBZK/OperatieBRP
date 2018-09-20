/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.jbpm;

import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.jdbc.Work;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;

/**
 * Jbpm base dao.
 */
public class JbpmDao {

    /**
     * Execute sql.
     * 
     * @param sql
     *            sql
     * @param <T>
     *            result type
     * @return result
     */
    protected final <T> T execute(final Sql<T> sql) {

        final JbpmConfiguration jbpmConfiguration = JbpmConfiguration.getInstance();
        final JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();

        try {
            final Worker<T> worker = new Worker<T>(sql);
            jbpmContext.getSession().doWork(worker);
            return worker.getResult();

        } catch (final HibernateException e) {
            throw new RuntimeException(e);
        } finally {
            jbpmContext.close();
        }
    }

    /**
     * Sql interface.
     * 
     * @param <T>
     *            result type
     */
    protected interface Sql<T> {
        /**
         * Use to given connection to execute sql.
         * 
         * @param connection
         *            connection
         * @return result
         * @throws SQLException
         *             on sql errors
         */
        T execute(final Connection connection) throws SQLException;
    }

    /**
     * Worker.
     * 
     * @param <T>
     *            result type
     */
    private static final class Worker<T> implements Work {
        /** result. */
        private T result;
        private final Sql<T> sql;

        protected Worker(final Sql<T> sql) {
            this.sql = sql;
        }

        @Override
        public void execute(final Connection connection) throws SQLException {
            result = sql.execute(connection);
        }

        T getResult() {
            return result;
        }

    }

}
