/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime;

import java.util.Calendar;
import nl.bzk.migratiebrp.util.common.Version;
import nl.bzk.migratiebrp.util.common.logging.FunctioneleMelding;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configurator;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Basis runtime. Start de spring container; de spring container bevat componenten die een live thread opstarten (JMS
 * queue poller).
 */
public final class Main {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static ConfigurableApplicationContext context;

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
    @SuppressWarnings("checkstyle:illegalcatch")
    public static void main(final String[] args) throws Exception {
        final Version version = Version.readVersion("nl.bzk.migratiebrp.synchronisatie", "migr-synchronisatie-runtime");
        LOG.info(
            FunctioneleMelding.SYNC_STARTEN_APPLICATIE,
            "Starten applicatie ({}) ...\nComponenten:\n{}",
            version.toString(),
            version.toDetailsString());
        // create Options object
        final Options options = new Options();
        final Option modusOption = new Option("modus", true, "'synchronisatie' of 'initielevulling'");
        options.addOption(modusOption);

        final CommandLineParser parser = new DefaultParser();
        try {
            final CommandLine cmd = parser.parse(options, args, true);

            if (!cmd.hasOption(modusOption.getOpt())) {
                LOG.error("Modus niet opgegeven");
                throw new IllegalArgumentException("Modus niet opgegeven.");
            }

            final String modus = cmd.getOptionValue(modusOption.getOpt());
            final String configLocation;
            if ("synchronisatie".equalsIgnoreCase(modus)) {
                configLocation = "classpath:synchronisatie.xml";
            } else if ("initielevulling".equalsIgnoreCase(modus)) {
                configLocation = "classpath:initielevulling.xml";
            } else {
                throw new IllegalArgumentException("Ongeldige modus opgegeven..");
            }

            LOG.info("Starting application context (config={})", configLocation);
            context = new ClassPathXmlApplicationContext(configLocation);
            LOG.info(FunctioneleMelding.SYNC_APPLICATIE_GESTART, "Applicatie ({}) gestart om {}", version.toString(), Calendar.getInstance().getTime());
        } catch (final Exception e) {
            final HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("java -jar synchronisatie-runtime.jar nl.bzk.migratiebrp.synchronisatie.runtime.Main", options);
            throw e;
        }
    }

    /**
     * Geef de applicatie context.
     *
     * @return applicatie context
     */
    public static ConfigurableApplicationContext getContext() {
        return context;
    }

    /**
     * Stop de applicatie.
     */
    public static void stop() {
        context.close();
    }
}
