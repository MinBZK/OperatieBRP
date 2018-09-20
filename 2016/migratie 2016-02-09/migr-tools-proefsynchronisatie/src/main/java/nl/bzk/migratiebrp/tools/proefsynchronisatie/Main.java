/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.proefsynchronisatie;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import nl.bzk.migratiebrp.tools.proefsynchronisatie.service.ProefSynchronisatieService;
import nl.bzk.migratiebrp.util.common.Version;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Service voor het uitvoeren van een proef sync.
 */
public final class Main {

    /**
     * Standaard parameter batch grootte.
     */
    public static final String PARAMETER_BATCH_GROOTTE = "batchSize";

    /**
     * Standaard parameter time out.
     */
    public static final String PARAMETER_TIME_OUT = "timeout";

    private static final Logger LOG = LoggerFactory.getLogger();

    private static String[] springConfig = {"proefSynchronisatie-beans.xml" };
    private static final String PARAMETER_CONFIG = "config";
    private static final String PARAMETER_CREATE = "create";
    private static final String PARAMETER_DATUM_VANAF = "datumVanaf";
    private static final String PARAMETER_DATUM_TOT = "datumTot";
    private static final Long DEFAULT_BATCH_SIZE = (long) 10000;
    private static final Long DEFAULT_TIME_OUT = (long) 3600000;
    private static final String GETAL_PATROON = "[0-9]*";
    private static final String DATUM_TIJD_PATROON = "[0-9]{4}-[0-9]{2}-[0-9]{2}|[0-9]{4}-[0-9]{2}-[0-9]{2}\\s[0-9]{2}:[0-9]{2}:[0-9]{2}\\.[0-9]{3}";

    private Main() {
        throw new AssertionError("Niet instantieerbaar.");
    }

    /**
     * Maakt de command line options aan welke meegegevens kunnen worden. Dit zijn: -config, -create en -datumVanaf.
     *
     * @return Options object met daarin de options.
     */
    private static Options createOptions() {
        final Options options = new Options();
        final Option batchSizeOption = new Option(PARAMETER_BATCH_GROOTTE, true, "Grootte van de batches.");
        final Option configOption = new Option(PARAMETER_CONFIG, true, "configuratie (.properties)");
        final Option datumVanafOption = new Option(PARAMETER_DATUM_VANAF, true, "Lees alleen Lg01 en La01 berichten vanaf de opgegeven datum");
        final Option datumTotOption = new Option(PARAMETER_DATUM_TOT, true, "Lees alleen Lg01 en La01 berichten tot de opgegeven datum");
        final Option initOption = new Option(PARAMETER_CREATE, false, "Maak een tabel met proef sync data.");
        final Option timeoutOption = new Option(PARAMETER_TIME_OUT, true, "Time out tussen batches in milliseconde.");
        batchSizeOption.setRequired(false);
        configOption.setRequired(true);
        datumVanafOption.setRequired(false);
        datumTotOption.setRequired(false);
        initOption.setRequired(false);
        timeoutOption.setRequired(false);
        options.addOption(batchSizeOption);
        options.addOption(configOption);
        options.addOption(datumVanafOption);
        options.addOption(datumTotOption);
        options.addOption(initOption);
        options.addOption(timeoutOption);
        return options;
    }

    /**
     * Run.
     *
     * Er zijn vier argumenten die ondersteund worden: -config Hiermee kan een property file worden aangegeven die wordt
     * ingelezen als System properties.
     *
     * -create Hiermee kan aangegeven worden dat er eerst een nieuwe creatie gedaan dient te worden.
     *
     * -datumVanaf Met dit argument kan aangegeven worden vanaf wanneer de berichten worden meegenomen.
     *
     * -datumTot Met dit argument kan aangegeven worden tot wanneer de berichten worden meegenomen.
     *
     * -batchSize De grootte van de batch.
     *
     * -timeout De wachtperiode in millisecondes tussen de batches.
     *
     * De configuratie in de spring bean definities is als volgt {$prop:default}. Hiermee kan een zinnige default worden
     * gezet voor properties.
     *
     * @param args
     *            De command line argumenten.
     */
    public static void main(final String[] args) {
        final Version version = Version.readVersion("nl.bzk.migratiebrp.tools", "migr-tools-proefsynchronisatie");
        LOG.info("Starten applicatie ({}) ...\nComponenten:\n{}", version.toString(), version.toDetailsString());

        final Properties config = new Properties();
        // create Options object
        final Options options = createOptions();

        final CommandLineParser parser = new DefaultParser();
        try {
            final CommandLine cmd = parser.parse(options, args, true);

            if (cmd.hasOption(options.getOption(PARAMETER_CONFIG).getOpt())) {
                final String configFilename = cmd.getOptionValue(options.getOption(PARAMETER_CONFIG).getOpt());
                LOG.info("Configuratie bestanden laden:{} ", configFilename);

                config.load(new FileInputStream(configFilename));
                if (config.isEmpty()) {
                    throw new IOException("Configuratie file: '" + configFilename + "' bestaat niet.");
                }
            }

            if (cmd.hasOption(options.getOption(PARAMETER_BATCH_GROOTTE).getOpt())) {
                final String batchGrootte = cmd.getOptionValue(options.getOption(PARAMETER_BATCH_GROOTTE).getOpt());
                LOG.info("Batch grootte is ingesteld op:{} ", batchGrootte);

                final Pattern pattern = Pattern.compile(GETAL_PATROON);
                final Matcher matcher = pattern.matcher(batchGrootte);

                if (!matcher.matches()) {
                    throw new IllegalArgumentException("De opgegeven batch grootte '" + batchGrootte + "' is geen geldig getal.");
                }

                config.put(options.getOption(PARAMETER_BATCH_GROOTTE).getOpt(), batchGrootte);
            } else {
                config.put(options.getOption(PARAMETER_BATCH_GROOTTE).getOpt(), DEFAULT_BATCH_SIZE);
            }

            if (cmd.hasOption(options.getOption(PARAMETER_TIME_OUT).getOpt())) {
                final String timeout = cmd.getOptionValue(options.getOption(PARAMETER_TIME_OUT).getOpt());
                LOG.info("Time out tussen batches is ingesteld op:{} ms", timeout);

                final Pattern pattern = Pattern.compile(GETAL_PATROON);
                final Matcher matcher = pattern.matcher(timeout);

                if (!matcher.matches()) {
                    throw new IllegalArgumentException("De opgegeven time out '" + timeout + "' is geen geldig getal. ");
                }

                config.put(options.getOption(PARAMETER_TIME_OUT).getOpt(), timeout);
            } else {
                config.put(options.getOption(PARAMETER_TIME_OUT).getOpt(), DEFAULT_TIME_OUT);
            }

            boolean voerCreatieUit = false;

            if (cmd.hasOption(options.getOption(PARAMETER_CREATE).getOpt())) {
                voerCreatieUit = true;
            }

            if (cmd.hasOption(options.getOption(PARAMETER_DATUM_VANAF).getOpt())) {

                final String datumVanaf = cmd.getOptionValue(options.getOption(PARAMETER_DATUM_VANAF).getOpt());
                final Pattern patternTime = Pattern.compile(DATUM_TIJD_PATROON);
                final Matcher matcher = patternTime.matcher(datumVanaf);

                if (!matcher.matches()) {
                    throw new IllegalArgumentException("De opgegeven vanafdatum '"
                                                       + datumVanaf
                                                       + "' voldoet niet aan de formattering (jjjj-MM-dd) of (jjjj-MM-dd hh:mm:ss.mmm). ");
                }

                config.put(options.getOption(PARAMETER_DATUM_VANAF).getOpt(), datumVanaf);
                LOG.info("Lees Lg01/La01 berichten vanaf: " + datumVanaf);
            } else if (voerCreatieUit) {
                throw new IllegalArgumentException("Voor initialisatie dient een vanaf datum (jjjj-MM-dd) of "
                                                   + "(jjjj-MM-dd hh:mm:ss.mmm) opgegeven te worden.");
            }

            if (cmd.hasOption(options.getOption(PARAMETER_DATUM_TOT).getOpt())) {

                final String datumTot = cmd.getOptionValue(options.getOption(PARAMETER_DATUM_TOT).getOpt());
                final Pattern patternTime = Pattern.compile(DATUM_TIJD_PATROON);
                final Matcher matcher = patternTime.matcher(datumTot);

                if (!matcher.matches()) {
                    throw new IllegalArgumentException("De opgegeven totdatum '"
                                                       + datumTot
                                                       + "' voldoet niet aan de formattering (jjjj-MM-dd) of (jjjj-MM-dd hh:mm:ss.mmm).");
                }

                config.put(options.getOption(PARAMETER_DATUM_TOT).getOpt(), datumTot);
                LOG.info("Lees Lg01/La01 berichten tot: " + datumTot);
            }

            startService(config, voerCreatieUit);
        } catch (final
            ParseException
            | InterruptedException e)
        {
            final HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("main\n" + e.getMessage(), options);
        } catch (final IOException e) {
            final HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("Fout opgetreden bij het lezen van het config bestand of inhoud.\n" + e.getMessage() + "\nmain ", options);
        } catch (final IllegalArgumentException e) {
            final HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("Fout opgetreden bij het lezen van de meegegeven parameters.\n" + e.getMessage() + "\nmain", options);
        }
    }

    /**
     * Start de service op basis van de meegegeven configuratie.
     *
     * @param config
     *            De meegegeven configuratie.
     * @param voerInitialisatieUit
     *            Indicator of er een initialisatie wordt uitgevoerd of niet.
     * @throws InterruptedException
     *             Wanneer de sleep wordt onderbroken.
     */
    private static void startService(final Properties config, final boolean voerInitialisatieUit) throws InterruptedException {
        LOG.info("Starting application context");
        final ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(springConfig);

        context.registerShutdownHook();
        LOG.info("Application started");

        final ProefSynchronisatieService proefSynchronisatieService = (ProefSynchronisatieService) context.getBean("proefSynchronisatieService");

        if (voerInitialisatieUit) {
            proefSynchronisatieService.laadInitProefSynchronisatieBerichtenTabel(config);
        } else {
            proefSynchronisatieService.voerProefSynchronisatieUit(config);
        }

        LOG.info("Stopping application context");
        context.close();
        LOG.info("Application done");
    }

    /**
     * Zet het bestand met de spring configuratie.
     *
     * @param springConfig
     *            Het te gebruiken bestand met de spring configuratie.
     */
    public static void setSpringConfig(final String[] springConfig) {
        Main.springConfig = ArrayUtils.clone(springConfig);
    }

}
