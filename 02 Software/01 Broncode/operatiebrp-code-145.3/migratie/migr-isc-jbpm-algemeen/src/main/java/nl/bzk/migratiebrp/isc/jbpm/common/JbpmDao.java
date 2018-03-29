/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common;

import java.sql.Connection;
import java.sql.SQLException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.jbpm.JbpmContext;

/**
 * Jbpm base dao.
 */
public class JbpmDao {

    /**
     * Execute sql.
     * @param sql sql
     * @param <T> result type
     * @return result
     */
    protected final <T> T execute(final Sql<T> sql) {
        return execute(sql, false);
    }

    /**
     * Execute sql.
     * @param sql sql
     * @param <T> result type
     * @param useSeparateTransaction Worker SQL in aparte transactie uitvoeren
     * @return result
     */
    protected final <T> T execute(final Sql<T> sql, final boolean useSeparateTransaction) {

        final JbpmContext jbpmContext = JbpmContext.getCurrentJbpmContext();
        if (jbpmContext == null) {
            throw new IllegalStateException("Geen JBPM context aanwezig voor JbpmDao");
        }

        try {
            final Session session = (Session) jbpmContext.getServices().getPersistenceService().getCustomSession(Session.class);
            // Zorg ervoor dat de database is bijgewerkt aangezien we hier met JDBC direct op tabellen werken
            session.flush();
            final Worker<T> worker = new Worker<>(sql);
            if (useSeparateTransaction) {
                session.beginTransaction();
                session.doWork(worker);
                session.getTransaction().commit();
            } else {
                session.doWork(worker);
            }
            return worker.getResult();

        } catch (final HibernateException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Sql interface.
     * @param <T> result type
     */
    protected interface Sql<T> {
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
