/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.replicationtest;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.sql.DataSource;

import nl.bzk.brp.testclient.ReplicationTester;
import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * De Class ReplicationTest.
 */
public class ReplicationTest {

    /** De Constante LOG. */
    private static final Logger LOG    = LoggerFactory.getLogger(ReplicationTest.class);

    /** De random. */
    private final Random        random = new Random();

    /**
     * Setup data source.
     *
     * @param connectURI de connect uri
     * @return de data source
     */
    private static DataSource setupDataSource(final String connectURI) {
        ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(connectURI, "brp", "brp");
        ObjectPool connectionPool = new GenericObjectPool(null);
        new PoolableConnectionFactory(connectionFactory, connectionPool, null, "SELECT 42", false, false);
        PoolingDataSource dataSource = new PoolingDataSource(connectionPool);
        return dataSource;
    }

    /**
     * Execute.
     *
     * @throws ClassNotFoundException de class not found exception
     * @throws SQLException de sQL exception
     */
    public void execute() throws ClassNotFoundException, SQLException {
        LOG.info("Starting");
        Class.forName("org.postgresql.Driver");
        DataSource dataSourceBijhouding =
            setupDataSource("jdbc:postgresql://" + ReplicationTester.eigenschappen.getDbHostBijhouding() + "/brp");
        DataSource dataSourceBevraging =
            setupDataSource("jdbc:postgresql://" + ReplicationTester.eigenschappen.getDbHostBevraging() + "/brp");
        // testConnections(dataSourceBijhouding, dataSourceBevraging);
        setupExclusionTable(dataSourceBijhouding);
        testExclusion(dataSourceBijhouding, dataSourceBevraging);
        LOG.info("Done");
    }

    /**
     * Test exclusion.
     *
     * @param dataSourceBijhouding de data source bijhouding
     * @param dataSourceBevraging de data source bevraging
     * @throws SQLException de sQL exception
     */
    private void testExclusion(final DataSource dataSourceBijhouding, final DataSource dataSourceBevraging)
            throws SQLException
    {

        Connection conn = null;

        try {
            int i = 0;
            while (i < ReplicationTester.eigenschappen.getExclusionTests()) {

                int value = random.nextInt();

                // Bijhouding
                conn = dataSourceBijhouding.getConnection();
                conn.rollback();
                new QueryRunner().update(conn,
                        "UPDATE kern.exclusiontest SET value1=?, value2=?, value3=?, value4=?, value5=?", value, value,
                        value, value, value);
                conn.commit();
                conn.close();
                conn = null;

                waitForSynchronisation(dataSourceBijhouding);

                // Bevraging
                conn = dataSourceBevraging.getConnection();
                conn.rollback();
                conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                long count =
                    new QueryRunner()
                            .query(conn,
                                    "SELECT COUNT(*) FROM kern.exclusiontest WHERE value1=? AND value2=? AND value3=? AND value4=? AND value5=?",
                                    new ScalarHandler<Long>(), value, value, value, value, value);
                conn.rollback();
                conn.close();
                conn = null;

                if (count != ReplicationTester.eigenschappen.getExclusionTestRows()) {
                    throw new RuntimeException("value should be " + value + ", count=" + count + ", count should be "
                        + ReplicationTester.eigenschappen.getExclusionTestRows());
                } else {
                    LOG.info("Test " + i + "/" + ReplicationTester.eigenschappen.getExclusionTests() + " succes value="
                        + value + ", count=" + count);
                }
                i++;
            }

        } finally {
            if (conn != null)
                DbUtils.close(conn);
        }

    }

    /**
     * Wait for synchronisation.
     *
     * @param dataSourceBijhouding de data source bijhouding
     * @throws SQLException de sQL exception
     */
    private void waitForSynchronisation(final DataSource dataSourceBijhouding) throws SQLException {
        Connection conn = null;
        try {
            conn = dataSourceBijhouding.getConnection();
            int waited = 1;
            while (!new QueryRunner().query(conn, "SELECT sent_location = write_location FROM pg_stat_replication",
                    new ScalarHandler<Boolean>()))
            {
                try {
                    LOG.info("Not synced, waithing 1ms (waited=" + waited + ")");
                    Thread.sleep(1);
                    if (waited > 1000) {
                        throw new RuntimeException("Waited more then 1 sec for replication to sync, giving up, waited="
                            + waited);
                    }
                } catch (InterruptedException e) {
                }
                waited++;
            }
        } finally {
            if (conn != null) {
                conn.close();
                conn = null;
            }
        }
    }

    /**
     * Instellen van up exclusion table.
     *
     * @param dataSourceBijhouding de nieuwe up exclusion table
     * @throws SQLException de sQL exception
     */
    private void setupExclusionTable(final DataSource dataSourceBijhouding) throws SQLException {
        Connection connBijhouding = dataSourceBijhouding.getConnection();

        final QueryRunner queryRunner = new QueryRunner();

        try {
            queryRunner.update(connBijhouding, "DROP TABLE IF EXISTS kern.exclusiontest");
            queryRunner
                    .update(connBijhouding,
                            "CREATE TABLE IF NOT EXISTS kern.exclusiontest (id integer NOT NULL, value1 integer NOT NULL,value2 integer NOT NULL,value3 integer NOT NULL,value4 integer NOT NULL,value5 integer NOT NULL, CONSTRAINT id PRIMARY KEY (id ))");

            for (int i = 0; i < ReplicationTester.eigenschappen.getExclusionTestRows(); i++) {
                if ((i + 1) % 100 == 0) {
                    LOG.info("Inserting " + (i + 1) + "/" + ReplicationTester.eigenschappen.getExclusionTestRows());
                }
                queryRunner.update(connBijhouding,
                        "INSERT INTO kern.exclusiontest (id, value1, value2, value3, value4, value5) VALUES(" + i
                            + ", 42, 42, 42, 42, 42)");
            };
            connBijhouding.commit();
        } finally {
            DbUtils.close(connBijhouding);
        }
    }

    /**
     * Test connections.
     *
     * @param dataSourceBijhouding de data source bijhouding
     * @param dataSourceBevraging de data source bevraging
     * @throws SQLException de sQL exception
     */
    private void testConnections(final DataSource dataSourceBijhouding, final DataSource dataSourceBevraging)
            throws SQLException
    {
        Connection connBijhouding = dataSourceBijhouding.getConnection();
        Connection connBevraging = dataSourceBevraging.getConnection();

        final QueryRunner queryRunner = new QueryRunner();

        try {

            List<Map<String, Object>> resultListBijhouding =
                queryRunner.query(connBijhouding, "SELECT COUNT(*) FROM Kern.pers", new MapListHandler());

            for (Map<String, Object> row : resultListBijhouding) {
                System.out.println("row bijhouding:" + row);
            }

            List<Map<String, Object>> resultListBevraging =
                queryRunner.query(connBevraging, "SELECT COUNT(*) FROM Kern.pers", new MapListHandler());

            for (Map<String, Object> row : resultListBevraging) {
                System.out.println("row bevraging:" + row);
            }

        } finally {
            DbUtils.close(connBijhouding);
            DbUtils.close(connBevraging);
        }

    }

}
