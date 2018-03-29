/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.naarbrp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.spring.PropertiesPropertySource;
import nl.bzk.migratiebrp.init.naarbrp.service.InitieleVullingService;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;

/**
 * Basis runtime. Start de spring container; de spring container bevat componenten die een live
 * thread opstarten (JMS queue poller).
 */
public final class Main {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final String OPTION_LAAD_PERS = "laad_pers";
    private static final String OPTION_LAAD_AUT = "laad_aut";
    private static final String OPTION_LAAD_AFN = "laad_afn";
    private static final String OPTION_LAAD_PROT = "laad_prot";
    private static final String OPTION_EXCEL_VULLING = "excelvulling";
    private static final String OPTION_SYNC_PERS = "sync_pers";
    private static final String OPTION_SYNC_AUT = "sync_aut";
    private static final String OPTION_SYNC_AFN = "sync_afn";
    private static final String OPTION_SYNC_PROT = "sync_prot";
    private static final String DATUM_TIJD_PATROON = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    private final PropertySource<?> configuration;

    /**
     * Constructor.
     * @param configuration configuratie
     */
    public Main(final PropertySource<?> configuration) {
        this.configuration = configuration;
        if (this.configuration == null) {
            throw new IllegalArgumentException("Configuratie verplicht");
        }
    }

    /**
     * Start de applicatie.
     * @param args argumenten (ongebruikt)
     */
    public static void main(final String[] args) {
        new Main(new PropertiesPropertySource("config", new ClassPathResource("/init-naarbrp.properties"))).start(args);
    }

    /**
     * Run.
     * @param args argumenten
     */
    public void start(final String[] args) {
        LOG.info("Starting application context");
        try (final GenericXmlApplicationContext context = new GenericXmlApplicationContext()) {
            context.load("init-naarbrp.xml");
            context.getEnvironment().getPropertySources().addLast(configuration);
            context.refresh();

            final InitieleVullingService initieleVullingService = (InitieleVullingService) context.getBean("initieleVullingService");
            final Runner runner = new Runner(initieleVullingService);

            context.registerShutdownHook();
            LOG.info("Application started");

            runner.parseAndExecute(args);
        }
    }

    /**
     * Runner voor initiele vulling.
     */
    public static final class Runner {

        private final InitieleVullingService service;

        /**
         * Constructor.
         * @param initieleVullingService initiele vulling service
         */
        public Runner(final InitieleVullingService initieleVullingService) {
            this.service = initieleVullingService;
        }

        /**
         * Parse de commandline opties en execute.
         * @param args command line opties
         */
        void parseAndExecute(final String[] args) {
            final HelpFormatter formatter = new HelpFormatter();
            final Options options = getOptions();
            final CommandLineParser parser = new DefaultParser();
            try {
                final CommandLine cmd = parser.parse(options, args, false);
                if (cmd.getOptions().length == 0 && cmd.getArgs().length == 0) {
                    formatter.printHelp("Geen argument meegegeven", options);
                } else {
                    if (!execute(cmd)) {
                        formatter.printHelp("Onbekend argument", options);
                    }
                }
            } catch (final ParseException e) {
                formatter.printHelp("main", options);
            } catch (final java.text.ParseException ie) {
                formatter.printHelp("Fout opgetreden.\n" + ie.getMessage() + " in main", options);
            }
        }

        private boolean execute(final CommandLine cmd) throws java.text.ParseException {
            return checkExcelOption(cmd) || checkLaadOptions(cmd) || checkSyncOptions(cmd);
        }

        private boolean checkExcelOption(final CommandLine cmd) {
            if (cmd.hasOption(OPTION_EXCEL_VULLING)) {
                final String excelFolder = cmd.getOptionValue(OPTION_EXCEL_VULLING);
                service.vulBerichtenTabelExcel(excelFolder);
                return true;
            }
            return false;
        }

        private boolean checkLaadOptions(final CommandLine cmd) {
            boolean geladen = false;
            if (cmd.hasOption(OPTION_LAAD_PERS)) {
                service.laadInitieleVullingTable();
                geladen = true;
            } else if (cmd.hasOption(OPTION_LAAD_AUT)) {
                service.laadInitAutorisatieRegelTabel();
                geladen = true;
            } else if (cmd.hasOption(OPTION_LAAD_AFN)) {
                service.laadInitAfnemersIndicatieTabel();
                geladen = true;
            } else if (cmd.hasOption(OPTION_LAAD_PROT)) {
                final String vanafDatum = cmd.getOptionValue(OPTION_LAAD_PROT);
                if (vanafDatum == null) {
                    service.laadInitProtocolleringTabel();
                } else {
                    service.laadInitProtocolleringTabel(parseDatum(vanafDatum));
                }
                geladen = true;
            }
            return geladen;
        }

        private boolean checkSyncOptions(final CommandLine cmd) throws java.text.ParseException {
            boolean gesynchroniseerd = false;
            if (cmd.hasOption(OPTION_SYNC_PERS)) {
                while (service.synchroniseerPersonen()) {
                    LOG.info("Personen worden gesynchroniseerd");
                }
                gesynchroniseerd = true;
            } else if (cmd.hasOption(OPTION_SYNC_AUT)) {
                while (service.synchroniseerAutorisaties()) {
                    LOG.info("Autorisaties worden gesynchroniseerd");
                }
                gesynchroniseerd = true;
            } else if (cmd.hasOption(OPTION_SYNC_AFN)) {
                while (service.synchroniseerAfnemerIndicaties()) {
                    LOG.info("Afnemerindicaties worden gesynchroniseerd");
                }
                gesynchroniseerd = true;
            } else if (cmd.hasOption(OPTION_SYNC_PROT)) {
                while (service.synchroniseerProtocollering()) {
                    LOG.info("Protocollering wordt gesynchroniseerd");
                }
                gesynchroniseerd = true;
            }
            return gesynchroniseerd;
        }

        /**
         * Geeft de beschikbare opties.
         * @return Options
         */
        private Options getOptions() {
            final Options options = new Options();

            final Option createTableOption = new Option(OPTION_LAAD_PERS, false, "Maak een tabel met initiele vulling PL data.");
            final Option createAutTableOption = new Option(OPTION_LAAD_AUT, "create-aut", false, "Maak een tabel met initiele vulling autorisatieregel data.");
            final Option createAfnIndTableOption =
                    new Option(OPTION_LAAD_AFN, "create-afnind", false, "Maak een tabel met initiele vulling afnemersindicatie data.");
            final Option createProtTableOption = Option.builder(OPTION_LAAD_PROT).hasArg().numberOfArgs(1).optionalArg(true)
                    .argName("vanafDatum").desc(String.format("Maak een tabel met initiele vulling protocollering data. "
                                    + "Geef optioneel een datum mee vanaf wanneer protocollering " + "data meegenomen dient te worden. Formaat: %s",
                            DATUM_TIJD_PATROON))
                    .build();

            final Option excelVullingOption =
                    new Option(OPTION_EXCEL_VULLING, true, "Vul de GBA-V database tabel 'initvullingresult' met data uit de excelbestanden.");

            final Option runOption = new Option(OPTION_SYNC_PERS, false, "Verwerk de PLen");
            final Option runAutOption = new Option(OPTION_SYNC_AUT, "run-aut", false, "Verwerk de autorisatieregels");
            final Option runAfnIndOption = new Option(OPTION_SYNC_AFN, "run-afnind", false, "Verwerk de afnemersindicaties");
            final Option runProtOption = new Option(OPTION_SYNC_PROT, false, "Verwerk de protocollering data");

            options.addOption(createTableOption);
            options.addOption(createAutTableOption);
            options.addOption(createAfnIndTableOption);
            options.addOption(createProtTableOption);
            options.addOption(excelVullingOption);
            options.addOption(runOption);
            options.addOption(runAutOption);
            options.addOption(runAfnIndOption);
            options.addOption(runProtOption);

            return options;
        }

        private LocalDateTime parseDatum(final String datum) {
            final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATUM_TIJD_PATROON);
            try {
                return LocalDateTime.parse(datum, formatter);
            } catch (final DateTimeParseException ex) {
                throw new IllegalArgumentException(
                        String.format("De opgegeven vanafdatum '%s' voldoet niet aan de formattering (%s).", datum, DATUM_TIJD_PATROON),
                        ex);
            }
        }
    }
}
