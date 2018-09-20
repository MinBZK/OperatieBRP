/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.runtime;

import java.io.File;

import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Basis runtime. Start de spring container; de spring container bevat componenten die een live thread opstarten (JMS
 * queue poller).
 */
public final class Main {

    private static final Logger LOG = LoggerFactory.getLogger();

    private Main() {
        throw new AssertionError("Niet instantieerbaar.");
    }

    /**
     * Run.
     * 
     * Het enige argument wat wordt ondersteund is het argument -config Hiermee kan een property file worden aangegeven
     * die wordt ingelezen als System properties.
     * 
     * De configuratie in de spring bean definities is als volgt {$prop:default}. Hiermee kan een zinnige default worden
     * gezet voor properties.
     * 
     * @param args
     *            argumenten.
     * @throws Exception
     *             exception
     */
    public static void main(final String[] args) throws Exception {
        // create Options object
        final Options options = new Options();
        final Option configOption = new Option("config", true, "configuratie (.xml)");
        options.addOption(configOption);

        final CommandLineParser parser = new GnuParser();
        try {
            final CommandLine cmd = parser.parse(options, args, true);

            if (!cmd.hasOption(configOption.getOpt())) {
                LOG.error("Configuratie file niet opgegeven");
                throw new RuntimeException();
            }

            final String configFilename = cmd.getOptionValue(configOption.getOpt());
            final File configFile = new File(configFilename);
            if (!configFile.exists()) {
                LOG.error("Configuratie bestand '{}' bestaat niet.");
                throw new RuntimeException();
            }

            LOG.info("Starting application context");
            final ConfigurableApplicationContext context =
                    new ClassPathXmlApplicationContext(new String[] { "classpath:runtime-beans.xml",
                            "file:" + configFile, });

            context.registerShutdownHook();
            LOG.info("Application started");

            // CHECKSTYLE:OFF - Catching exception
        } catch (final Exception e) {
            // CHECKSTYLE:ON
            e.printStackTrace();

            final HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("main", options);
        }

    }
}
