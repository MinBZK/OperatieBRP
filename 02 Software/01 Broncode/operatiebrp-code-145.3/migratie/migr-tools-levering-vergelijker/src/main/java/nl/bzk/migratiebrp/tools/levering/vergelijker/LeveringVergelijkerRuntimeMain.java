/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.levering.vergelijker;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.tools.levering.vergelijker.job.CombinatiesJob;
import nl.bzk.migratiebrp.tools.levering.vergelijker.job.KopieerGbavJob;
import nl.bzk.migratiebrp.tools.levering.vergelijker.job.LaadBrpJob;
import nl.bzk.migratiebrp.tools.levering.vergelijker.job.LaadGbavJob;
import nl.bzk.migratiebrp.tools.levering.vergelijker.job.LeveringVergelijkerJob;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Main klasse voor het starten van de levering vergelijker.
 */
public final class LeveringVergelijkerRuntimeMain {

    private static final String[] SPRING_CONFIG = {"classpath:levering-vergelijker-beans.xml"};
    private static final Options OPTIONS = getOptions();

    private static final String OPTION_LAAD_GBAV_BERICHTEN = "laadGbav";
    private static final String OPTION_KOPIEER_GBAV_BERICHTEN = "kopieerGbav";
    private static final String OPTION_LAAD_BRP_BERICHTEN = "laadBrp";
    private static final String OPTION_LAAD_BERICHT_COMBINATIES = "combinaties";
    private static final String OPTION_VERGELIJK_BERICHTEN = "vergelijk";

    private static final Logger LOG = LoggerFactory.getLogger();

    /**
     * No instantiation here.
     */
    private LeveringVergelijkerRuntimeMain() {

    }

    /**
     * Main methode, geen commandline argumenten worden verwerkt.
     * @param args De meegegeven commandline argumenten.
     */
    public static void main(final String... args) {
        LOG.info("Application starting");
        final CommandLineParser parser = new DefaultParser();
        try {
            final CommandLine cmd = parser.parse(OPTIONS, args, true);

            LOG.info("Starting application context");
            final ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(SPRING_CONFIG);

            context.registerShutdownHook();
            LOG.info("Application started");

            boolean doneSomething = false;
            if (cmd.hasOption(OPTIONS.getOption(OPTION_LAAD_GBAV_BERICHTEN).getOpt())) {
                LOG.info("Starten laden GBAV berichten ...");
                new LaadGbavJob().execute(context);
                LOG.info("Laden GBAV berichten gereed ...");
                doneSomething = true;
            }
            if (cmd.hasOption(OPTIONS.getOption(OPTION_LAAD_BRP_BERICHTEN).getOpt())) {
                LOG.info("Starten laden BRP berichten ...");
                new LaadBrpJob().execute(context);
                LOG.info("Laden BRP berichten gereed...");
                doneSomething = true;
            }
            if (cmd.hasOption(OPTIONS.getOption(OPTION_VERGELIJK_BERICHTEN).getOpt())) {
                LOG.info("Starten vergelijking van leveringsberichten ...");
                final LeveringVergelijkerJob job = new LeveringVergelijkerJob();
                job.execute(context);
                LOG.info("Vergelijking van leveringsberichten gereed ...");
                doneSomething = true;
            }

            if (cmd.hasOption(OPTIONS.getOption(OPTION_KOPIEER_GBAV_BERICHTEN).getOpt())) {
                LOG.info("Starten kopieren GBAV berichten ...");
                new KopieerGbavJob().execute(context);
                LOG.info("Kopieren GBAV berichten gereed ...");
                doneSomething = true;
            }
            if (cmd.hasOption(OPTIONS.getOption(OPTION_LAAD_BERICHT_COMBINATIES).getOpt())) {
                LOG.info("Starten maken bericht combinaties ...");
                new CombinatiesJob().execute(context);
                LOG.info("Maken bericht combinaties gereed ...");
                doneSomething = true;
            }
            if (!doneSomething) {
                final HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("Geen argument meegegeven", OPTIONS);
            }

            LOG.info("Closing application context");
            context.close();
            LOG.info("Application ended");

        } catch (final ParseException e) {
            final HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("main\n" + e.getMessage(), OPTIONS);
        }
        LOG.info("Application finished");
    }

    /**
     * Geeft de beschikbare opties.
     * @return Options
     */
    private static Options getOptions() {
        final Options options = new Options();

        // Geeft aan of de init vulling tabel aangemaakt moet worden.
        // Als dit niet opgegeven is en de tabel bestaat niet, wordt deze wel eerst aangemaakt.
        final Option laadGbavOption = new Option(OPTION_LAAD_GBAV_BERICHTEN, false, "Maak een (gbav-)bericht overzicht tabel in GBAV.");
        final Option kopieerGbavOption =
                new Option(OPTION_KOPIEER_GBAV_BERICHTEN, false, "Kopieer de (gbav-)bericht overzicht tabel van de GBAV database naar de SOA database.");
        final Option laadBrpOption = new Option(OPTION_LAAD_BRP_BERICHTEN, false, "Maak een (brp-)bericht overicht tabel in de SOA database.");
        final Option laadBerichtcombinaties =
                new Option(OPTION_LAAD_BERICHT_COMBINATIES, false, "Maak de bericht combinaties tabel in de SOA database obv de (gbav- en brp-)berichten");
        final Option vergelijkBerichten = new Option(OPTION_VERGELIJK_BERICHTEN, false, "Vergelijk de berichten inhoudelijk obv de combinaties tabel");

        options.addOption(laadGbavOption);
        options.addOption(kopieerGbavOption);
        options.addOption(laadBrpOption);
        options.addOption(laadBerichtcombinaties);
        options.addOption(vergelijkBerichten);

        return options;
    }
}
