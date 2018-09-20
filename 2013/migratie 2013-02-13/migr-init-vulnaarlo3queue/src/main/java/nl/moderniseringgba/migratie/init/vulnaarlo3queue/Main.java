/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.init.vulnaarlo3queue;

import java.io.IOException;
import java.util.Properties;

import nl.moderniseringgba.migratie.init.vulnaarlo3queue.service.VulNaarLo3QueueService;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Basis runtime. Start de spring container.
 */
public final class Main {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static String springConfig = "vulnaarlo3queue-beans.xml";

    private Main() {
        throw new AssertionError("Niet instantieerbaar.");
    }

    /**
     * Run.
     * 
     * De enige 2 argumenten welke ondersteund worden zijn: -config Hiermee kan een property file worden aangegeven die
     * wordt ingelezen als System properties. -create Dit geeft aan of de initiele vulling tabel aangemaakt moet worden.
     * Als dit argument meegegeven is wordt alleen een initiele vulling tabel aangemaakt en geen initiele vulling
     * gedaan.
     * 
     * De configuratie in de spring bean definities is als volgt {$prop:default}. Hiermee kan een zinnige default worden
     * gezet voor properties.
     * 
     * @param args
     *            argumenten.
     */
    public static void main(final String[] args) {
        final Properties config = new Properties();
        // create Options object
        final Options options = new Options();
        final Option configOption = new Option("config", true, "configuratie (.properties)");
        configOption.setRequired(true);
        options.addOption(configOption);

        final CommandLineParser parser = new GnuParser();
        try {
            final CommandLine cmd = parser.parse(options, args, true);

            if (cmd.hasOption(configOption.getOpt())) {
                final String configFilename = cmd.getOptionValue(configOption.getOpt());
                LOG.info("Configuratie bestanden laden:{} ", configFilename);

                config.load(ClassLoader.getSystemResourceAsStream(configFilename));
                if (config.isEmpty()) {
                    throw new IOException("Configuratie file: '" + configFilename + "' bestaat niet.");
                }
            }

            LOG.info("Starting application context");
            final ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(springConfig);

            context.registerShutdownHook();
            LOG.info("Application started");

            final VulNaarLo3QueueService vulNaarLo3QueueService =
                    (VulNaarLo3QueueService) context.getBean("vulNaarLo3QueueService");

            vulNaarLo3QueueService.leesIngeschrevenenInBrpEnVulQueue(config);
        } catch (final ParseException e) {
            final HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("main", options);
        } catch (final IOException e) {
            final HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("Fout opgetreden bij het lezen van het config bestand of inhoud.\n" + e.getMessage()
                    + "/nmain", options);
        } catch (final java.text.ParseException ie) {
            final HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("Fout opgetreden.\n" + ie.getMessage() + "/n main", options);
        }
    }

    public static void setSpringConfig(final String springConfig) {
        Main.springConfig = springConfig;
    }
}
