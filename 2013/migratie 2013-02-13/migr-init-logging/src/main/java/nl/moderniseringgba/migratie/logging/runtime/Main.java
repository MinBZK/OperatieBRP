/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.logging.runtime;

import java.io.File;

import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Basis runtime. Start de spring container; de spring container bevat componenten die een live thread opstarten (JMS
 * queue poller).
 */
public final class Main {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static String springConfig =
            "classpath:synchronisatie-logging-beans.xml,classpath:synchronisatie-logging-jms.xml";

    private Main() {
        throw new AssertionError("Niet instantieerbaar.");
    }

    /**
     * Run.
     * 
     * De 'config' systeem property is verplicht en moet verwijzen naar de config file.
     * 
     * @param args
     *            argumenten.
     */
    public static void main(final String[] args) {
        try {
            // Looking for system property
            final String configFileProperty = System.getProperty("config");
            final boolean doesConfigFileExist = configFileProperty != null && new File(configFileProperty).exists();
            System.out.println("Using config file: " + configFileProperty + " Exists? " + doesConfigFileExist);
            if (!doesConfigFileExist) {
                throw new IllegalArgumentException("Config file kan niet worden gelezen.");
            }

            LOG.info("Starting application context");
            final ConfigurableApplicationContext context =
                    new ClassPathXmlApplicationContext(springConfig.split(","));

            context.registerShutdownHook();
            LOG.info("Application started");
            // CHECKSTYLE:OFF - Catching exception
        } catch (final Exception e) {
            // CHECKSTYLE:ON
            LOG.error(e.getMessage(), e);
        }

    }

    /**
     * @param springConfig
     *            the springConfig to set
     */
    public static void setSpringConfig(final String springConfig) {
        Main.springConfig = springConfig;
    }

}
