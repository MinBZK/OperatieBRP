/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine;

import java.sql.Connection;
import javax.sql.DataSource;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.funqmachine.configuratie.DatabaseConfig;
import nl.bzk.brp.funqmachine.configuratie.Omgeving;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring configuratie klasse voor de datasource. Dit is de koppeling tussen spring configuratie en het door de
 * FunqMachine gebruikte {@link Omgeving}.
 * @see Omgeving
 */
@Configuration
public class DataSourceConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    static {
        final String aantalSeconden = "10";
        System.setProperty("brp.database.timeout.seconden", aantalSeconden);
        System.setProperty("brp.locking.timeout.seconden", aantalSeconden);

    }

    /**
     * Maakt een {@link DataSource} Spring bean aan.
     * @return een {@link DataSource} bean
     */
    @Bean(name = "lezenSchrijvenDataSource")
    public DataSource lezenSchrijvenDataSource() {
        final DatabaseConfig databaseConfig = Omgeving.getOmgeving().getGetDatabaseConfig("kern");
        LOGGER.debug("config lezenSchrijvenDataSource = " + databaseConfig);

        final BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(databaseConfig.getDriverClassName());
        dataSource.setUsername(databaseConfig.getUsername());
        dataSource.setPassword(databaseConfig.getPassword());
        dataSource.setUrl(databaseConfig.getUrl());

        dataSource.setDefaultTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

        final int maxWait = 3000;
        final int maxActive = 10;
        dataSource.setInitialSize(1);
        dataSource.setMaxWait(maxWait);
        dataSource.setMaxActive(maxActive);
        dataSource.setTestWhileIdle(true);
        dataSource.setValidationQuery("SELECT 41+1");

        return dataSource;
    }

    /**
     * Geeft de master datasource terug.
     * @return de master datasource
     */
    @Bean(name = "masterDataSource")
    public DataSource masterDataSource() {
        return lezenSchrijvenDataSource();
    }

    /**
     * Geeft de bericht lezen/schrijven datasource terug.
     * @return de bericht lezen/schrijven datasource
     */
    @Bean(name = "berlezenSchrijvenDataSource")
    public DataSource berlezenSchrijvenDataSource() {
        final DatabaseConfig databaseConfig = Omgeving.getOmgeving().getGetDatabaseConfig("ber");
        LOGGER.debug("config berlezenSchrijvenDataSource = " + databaseConfig);

        final BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(databaseConfig.getDriverClassName());
        dataSource.setUsername(databaseConfig.getUsername());
        dataSource.setPassword(databaseConfig.getPassword());
        dataSource.setUrl(databaseConfig.getUrl());

        dataSource.setDefaultTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

        final int maxWait = 3000;
        final int maxActive = 10;
        dataSource.setInitialSize(1);
        dataSource.setMaxWait(maxWait);
        dataSource.setMaxActive(maxActive);
        dataSource.setTestWhileIdle(true);
        dataSource.setValidationQuery("SELECT 41+1");

        return dataSource;
    }

    /**
     * Geeft de Active MQ Conenction Factory terug.
     * @return de Active MQ Conenction Factory
     */
    @Bean(name = "brpQueueConnectionFactory")
    public ActiveMQConnectionFactory actualConnectionFactory() {
        return new ActiveMQConnectionFactory(Omgeving.getOmgeving().getBrokerUrl());
    }


    /**
     * Geeft de verwerk bijhoudingsplan queue terug.
     * @return de verwerk bijhoudingsplan queue
     */
    @Bean(name = "verwerkBijhoudingsplanQueue")
    public ActiveMQQueue verwerkBijhoudingsplanQueue() {
        return new ActiveMQQueue(Omgeving.getOmgeving().getNotificatieBerichtQueue());
    }
}
