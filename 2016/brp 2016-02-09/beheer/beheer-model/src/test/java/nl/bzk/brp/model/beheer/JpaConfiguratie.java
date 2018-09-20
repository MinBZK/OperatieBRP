/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.beheer;

import java.util.Properties;
import javax.inject.Inject;
import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.ejb.HibernatePersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Repository configuratie.
 */
@Configuration
@EnableTransactionManagement
@PropertySource("classpath:beheer.properties")
public class JpaConfiguratie {

    private static final String BRP_MODEL_PACKAGE = "nl.bzk.brp.model";
    private static final String PROPERTY_NAME_DATABASE_DRIVER = "database.driver";
    private static final String PROPERTY_NAME_DATABASE_PASSWORD = "database.password";
    private static final String PROPERTY_NAME_DATABASE_URL = "database.url";
    private static final String PROPERTY_NAME_DATABASE_USERNAME = "database.username";

    private static final String PROPERTY_NAME_POOL_INITIAL_SIZE = "jdbc.pool.initialSize";
    private static final String PROPERTY_NAME_POOL_MAX_ACTIVE = "jdbc.pool.maxActive";

    private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";

    private static final String PROPERTY_NAME_HIBERNATE_SQL_COMMENTS = "hibernate.use_sql_comments";
    private static final String PROPERTY_NAME_HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
    private static final String PROPERTY_NAME_HIBERNATE_HBM2DDL = "hibernate.hbm2ddl.auto";
    private static final String PROPERTY_NAME_JAVAX_PERSISTENCE_VALIDATION = "javax.persistence.validation.mode";

    @Inject
    private Environment environment;

    /**
     * @return datasource
     */
    @Bean
    public DataSource dataSource() {
        final BasicDataSource dataSource = new BasicDataSource();

        // Connection
        dataSource.setDriverClassName(environment.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
        dataSource.setUrl(environment.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));
        dataSource.setUsername(environment.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));
        dataSource.setPassword(environment.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));

        // Pool
        dataSource.setInitialSize(Integer.parseInt(environment.getRequiredProperty(PROPERTY_NAME_POOL_INITIAL_SIZE)));
        dataSource.setMaxActive(Integer.parseInt(environment.getRequiredProperty(PROPERTY_NAME_POOL_MAX_ACTIVE)));

        return dataSource;
    }

    /**
     * @return entity manager factory
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean lezenEntityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPersistenceUnitName("nl.bzk.brp.alleenlezen");
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistence.class);
        entityManagerFactoryBean.setPackagesToScan(BRP_MODEL_PACKAGE);

        entityManagerFactoryBean.setJpaProperties(hibernateProperties());

        return entityManagerFactoryBean;
    }

    /**
     * @return entity manager factory
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean schrijvenEntityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPersistenceUnitName("nl.bzk.brp.lezenschrijven");
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistence.class);
        entityManagerFactoryBean.setPackagesToScan(BRP_MODEL_PACKAGE);

        entityManagerFactoryBean.setJpaProperties(hibernateProperties());

        return entityManagerFactoryBean;
    }

    private Properties hibernateProperties() {
        final Properties properties = new Properties();
        properties.put(PROPERTY_NAME_HIBERNATE_DIALECT, environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
        properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));

        properties.put(PROPERTY_NAME_HIBERNATE_SQL_COMMENTS, environment.getProperty(PROPERTY_NAME_HIBERNATE_SQL_COMMENTS));
        properties.put(PROPERTY_NAME_HIBERNATE_FORMAT_SQL, environment.getProperty(PROPERTY_NAME_HIBERNATE_FORMAT_SQL));
        properties.put(PROPERTY_NAME_HIBERNATE_HBM2DDL, environment.getProperty(PROPERTY_NAME_HIBERNATE_HBM2DDL));

        properties.put(PROPERTY_NAME_JAVAX_PERSISTENCE_VALIDATION, environment.getProperty(PROPERTY_NAME_JAVAX_PERSISTENCE_VALIDATION));
        properties.put("hibernate.temp.use_jdbc_metadata_defaults", Boolean.FALSE);
        properties.put("hibernate.cache.use_second_level_cache", Boolean.TRUE);
        properties.put("hibernate.cache.use_query_cache", Boolean.FALSE);
        properties.put("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory");

        return properties;
    }

    /**
     * @return transaction manager
     */
    @Bean
    public JpaTransactionManager transactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(lezenEntityManagerFactory().getObject());
        return transactionManager;
    }
    //
    // @Bean
    // public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
    // return new PersistenceExceptionTranslationPostProcessor();
    // }
}
