/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.autconv.postgres;

import java.io.IOException;
import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.Ordered;

@Configuration
public class PostgresTestPropertiesConfiguration {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() throws IOException {
        final PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        final Properties props = getProperties();
        propertySourcesPlaceholderConfigurer.setProperties(props);
        propertySourcesPlaceholderConfigurer.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return propertySourcesPlaceholderConfigurer;
    }

    public static Properties getProperties() throws IOException {
        final Properties props = new Properties();
        final String poort;

        props.setProperty("jdbc.master.url", "jdbc:postgresql://localhost/brp");
        props.setProperty("jdbc.master.driverClassName", "org.postgresql.Driver");
        props.setProperty("jdbc.master.username", "brp");
        props.setProperty("jdbc.master.password", "brp");

        return props;
    }
}
