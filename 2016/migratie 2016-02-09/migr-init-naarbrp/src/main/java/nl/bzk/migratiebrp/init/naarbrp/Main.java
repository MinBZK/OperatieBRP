/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.naarbrp;

import nl.bzk.migratiebrp.init.naarbrp.service.InitieleVullingService;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
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
 * Basis runtime. Start de spring container; de spring container bevat componenten die een live thread opstarten (JMS
 * queue poller).
 */
public final class Main {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final String OPTION_LAAD_PERS = "laad_pers";
    private static final String OPTION_LAAD_AUT = "laad_aut";
    private static final String OPTION_LAAD_AFN = "laad_afn";
    private static final String OPTION_EXCEL_VULLING = "excelvulling";
    private static final String OPTION_SYNC_PERS = "sync_pers";
    private static final String OPTION_SYNC_AUT = "sync_aut";
    private static final String OPTION_SYNC_AFN = "sync_afn";
    private static final Options OPTIONS = getOptions();

    private Main() {
        throw new AssertionError("Niet instantieerbaar.");
    }

    /**
     * Run.
     *
     * @param args
     *            argumenten
     */
    public static void main(final String[] args) {
        final CommandLineParser parser = new DefaultParser();
        try {
            final CommandLine cmd = parser.parse(OPTIONS, args, true);

            execute(cmd);
        } catch (final ParseException e) {
            final HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("main", OPTIONS);
        } catch (final java.text.ParseException ie) {
            final HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("Fout opgetreden.\n" + ie.getMessage() + " in main", OPTIONS);
        }
    }

    /**
     * Execute the program.
     */
    private static void execute(final CommandLine cmd) throws java.text.ParseException {
        LOG.info("Starting application context");
        try (final ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("init-naarbrp.xml")) {
            context.registerShutdownHook();
            LOG.info("Application started");

            final InitieleVullingService initieleVullingService = (InitieleVullingService) context.getBean("initieleVullingService");

            if (cmd.hasOption(OPTION_EXCEL_VULLING)) {
                final String excelFolder = cmd.getOptionValue(OPTION_EXCEL_VULLING);
                initieleVullingService.vulBerichtenTabelExcel(excelFolder);
            } else if (cmd.hasOption(OPTION_LAAD_PERS)) {
                initieleVullingService.laadInitieleVullingTable();
            } else if (cmd.hasOption(OPTION_LAAD_AUT)) {
                initieleVullingService.laadInitAutorisatieRegelTabel();
            } else if (cmd.hasOption(OPTION_LAAD_AFN)) {
                initieleVullingService.laadInitAfnemersIndicatieTabel();
            } else if (cmd.hasOption(OPTION_SYNC_PERS)) {
                while (initieleVullingService.synchroniseerPersonen()) {
                    LOG.info("Personen worden gesynchroniseerd");
                }
            } else if (cmd.hasOption(OPTION_SYNC_AUT)) {
                while (initieleVullingService.synchroniseerAutorisaties()) {
                    LOG.info("Autorisaties worden gesynchroniseerd");
                }
            } else if (cmd.hasOption(OPTION_SYNC_AFN)) {
                while (initieleVullingService.synchroniseerAfnemerIndicaties()) {
                    LOG.info("Afnemerindicaties worden gesynchroniseerd");
                }
            } else {
                final HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("Geen argument meegegeven", OPTIONS);
            }
        }
    }

    /**
     * Geeft de beschikbare opties.
     *
     * @return Options
     */
    private static Options getOptions() {
        final Options options = new Options();

        final Option createTableOption = new Option(OPTION_LAAD_PERS, false, "Maak een tabel met initiele vulling PL data.");
        final Option createAutTableOption = new Option(OPTION_LAAD_AUT, "create-aut", false, "Maak een tabel met initiele vulling autorisatieregel data.");
        final Option createAfnIndTableOption =
                new Option(OPTION_LAAD_AFN, "create-afnind", false, "Maak een tabel met initiele vulling afnemersindicatie data.");

        final Option excelVullingOption =
                new Option(OPTION_EXCEL_VULLING, true, "Vul de GBA-V database tabel 'initvullingresult' met data uit de excelbestanden.");

        final Option runOption = new Option(OPTION_SYNC_PERS, false, "Verwerk de PLen");
        final Option runAutOption = new Option(OPTION_SYNC_AUT, "run-aut", false, "Verwerk de autorisatieregels");
        final Option runAfnIndOption = new Option(OPTION_SYNC_AFN, "run-afnind", false, "Verwerk de afnemersindicaties");

        options.addOption(createTableOption);
        options.addOption(createAutTableOption);
        options.addOption(createAfnIndTableOption);
        options.addOption(excelVullingOption);
        options.addOption(runOption);
        options.addOption(runAutOption);
        options.addOption(runAfnIndOption);

        return options;
    }
}
