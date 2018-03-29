/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.CompositeDatabasePopulator;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@Configuration
@PropertySource("classpath:beheer.properties")
public class DatabaseConfiguration {

    private final Environment environment;

    /**
     * Constructor
     * @param environment Spring environment
     */
    public DatabaseConfiguration(final Environment environment) {
        this.environment = environment;
    }

    @Inject
    @Named(RepositoryConfiguratie.DATA_SOURCE_MASTER)
    private DataSource dataSourceMaster;

    @Bean
    public Object getDatabaseInitializer() {
        final DataSourceInitializer result = new DataSourceInitializer();
        result.setDataSource(dataSourceMaster);

        if ("true".equals(environment.getProperty("gebruik.postgres", "false"))) {
            final PostgresqlResourceDatabasePopulator populator = new PostgresqlResourceDatabasePopulator();
            populator.setSqlScriptEncoding("UTF-8");
            populator.addScript(new ClassPathResource("bmr/Postgres/Alg/Algemeen_BRP_structuur_aanvullend.sql"));
            populator.addScript(new ClassPathResource("bmr/Postgres/Alg/Algemeen_BRP_custom_changes.sql"));
            populator.addScript(new ClassPathResource("bmr/Postgres/Kern/Kern_BRP_structuur.sql"));
            populator.addScript(new ClassPathResource("bmr/Postgres/Kern/Kern_BRP_indexen.sql"));
            populator.addScript(new ClassPathResource("bmr/Postgres/Kern/Kern_BRP_statische_stamgegevens.sql"));
            populator.addScript(new ClassPathResource("bmr/Postgres/Kern/Kern_BRP_dynamische_stamgegevens.sql"));
            populator.addScript(new ClassPathResource("bmr/Postgres/Kern/Kern_BRP_structuur_aanvullend.sql"));
            populator.addScript(new ClassPathResource("bmr/Postgres/Kern/Kern_BRP_rechten.sql"));
            populator.addScript(new ClassPathResource("bmr/Postgres/Kern/Kern_BRP_custom_changes.sql"));
            populator.addScript(new ClassPathResource("bmr/Postgres/Ber/Bericht_BRP_structuur.sql"));
            populator.addScript(new ClassPathResource("bmr/Postgres/Ber/Bericht_BRP_indexen.sql"));
            populator.addScript(new ClassPathResource("bmr/Postgres/Ber/Bericht_BRP_statische_stamgegevens.sql"));
            populator.addScript(new ClassPathResource("bmr/Postgres/Ber/Bericht_BRP_structuur_aanvullend.sql"));
            populator.addScript(new ClassPathResource("bmr/Postgres/Ber/Bericht_BRP_rechten.sql"));
            populator.addScript(new ClassPathResource("bmr/Postgres/Ber/Bericht_BRP_custom_changes.sql"));
            populator.addScript(new ClassPathResource("bmr/Postgres/Prot/Protocol_BRP_structuur.sql"));
            populator.addScript(new ClassPathResource("bmr/Postgres/Prot/Protocol_BRP_indexen.sql"));
            populator.addScript(new ClassPathResource("bmr/Postgres/Prot/Protocol_BRP_statische_stamgegevens.sql"));
            populator.addScript(new ClassPathResource("bmr/Postgres/Prot/Protocol_BRP_structuur_aanvullend.sql"));
            populator.addScript(new ClassPathResource("bmr/Postgres/Prot/Protocol_BRP_rechten.sql"));
            populator.addScript(new ClassPathResource("bmr/Postgres/Prot/Protocol_BRP_custom_changes.sql"));
            result.setDatabasePopulator(populator);
        } else {
            final CompositeDatabasePopulator populator = new CompositeDatabasePopulator();
            populator.addPopulators(createPopulator("bmr/HSQLDB/HSQLDB.sql"));
            populator.addPopulators(createPopulator("bmr/HSQLDB/PENDING_BMR_CHANGES.sql"));
            result.setDatabasePopulator(populator);
        }
        return result;
    }

    private DatabasePopulator createPopulator(final String resource) {
        final ResourceDatabasePopulator result = new ResourceDatabasePopulator(new ClassPathResource(resource));
        result.setSqlScriptEncoding("UTF-8");
        return result;
    }
}
