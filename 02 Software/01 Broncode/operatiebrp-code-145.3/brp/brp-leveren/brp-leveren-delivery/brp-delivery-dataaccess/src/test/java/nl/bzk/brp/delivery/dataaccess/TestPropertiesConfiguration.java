/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.dataaccess;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Arrays;
import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.Ordered;

@Configuration
public class TestPropertiesConfiguration {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() throws IOException {
        final PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        final Properties props = getProperties();
        propertySourcesPlaceholderConfigurer.setProperties(props);
        propertySourcesPlaceholderConfigurer.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return propertySourcesPlaceholderConfigurer;
    }

    public static Properties getProperties() throws IOException {
        System.out.println("\n\n\n\n\nGET PROPERTIES\nGET PROPERTIES\nGET PROPERTIES\nGET PROPERTIES\nGET PROPERTIES\n\n\n\n\n");

        final Properties props = new Properties();
        final String poort;
        try (ServerSocket databasePort = new ServerSocket(0)) {
            poort = Integer.toString(databasePort.getLocalPort());
        }
        Arrays.asList("test.database.port", "jdbc.archivering.database.port", "brp.selectie.jdbc.port",
                "jdbc.protocollering.database.port", "jdbc.database.port").forEach(key -> props.setProperty(key, poort));
        final String url = String.format("jdbc:hsqldb:hsql://localhost:%s/brp", poort);
        Arrays.asList("jdbc.master.url", "jdbc.protocollering.url", "jdbc.archivering.url", "brp.selectie.jdbc.url").
                forEach(key -> props.setProperty(key, url));
        props.setProperty("brp.selectie.messagebroker.broker.url", "tcp://0.0.0.0:61619");
        props.setProperty("brp.jms.client.url", "tcp://0.0.0.0:61619");

        Arrays.asList(
                "jdbc.master.driverClassName",
                "jdbc.archivering.driverClassName",
                "jdbc.protocollering.driverClassName",
                "brp.selectie.jdbc.driver").forEach(key -> props.setProperty(key, "org.hsqldb.jdbc.JDBCDriver"));

        props.setProperty("jdbc.master.username", "sa");
        props.setProperty("jdbc.archivering.username", "sa");
        props.setProperty("jdbc.protocollering.username", "sa");
        props.setProperty("brp.selectie.jdbc.username", "sa");
        props.setProperty("jdbc.master.password", "");
        props.setProperty("jdbc.archivering.password", "");
        props.setProperty("jdbc.protocollering.password", "");
        props.setProperty("brp.selectie.jdbc.password", "");
        props.setProperty("security.privatekey.alias", "alias");
        props.setProperty("security.keystore.private.password", "alias");
        props.setProperty("security.keystore.private.file", "/");
        props.setProperty("security.privatekey.password", "/");

        try (ServerSocket routeringPort = new ServerSocket(0)) {
            props.setProperty("jmx.port", Integer.toString(routeringPort.getLocalPort()));
        }
        return props;
    }
}
