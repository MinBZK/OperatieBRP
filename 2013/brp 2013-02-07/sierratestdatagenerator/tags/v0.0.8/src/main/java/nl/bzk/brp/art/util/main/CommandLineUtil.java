/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.art.util.main;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * .
 *
 */
public final class CommandLineUtil {

    /**
     * .
     */
    private CommandLineUtil() {
    };
    /** . */
    public static final char COMMAND_BACKUP = 'b';
    /** . */
    public static final char COMMAND_CONVERT = 'c';
    /** . */
    public static final char OPTION_SQL = 's';
    /** . */
    public static final char OPTION_PASSWORD = 'p';
    /** . */
    public static final char OPTION_USERNAME = 'u';
    /** . */
    public static final char OPTION_DATABASE = 'd';
    /** . */
    public static final char OPTION_EXCEL_DIR = 'x';
//    /** . */
//    public static final char OPTION_FILENAME = 'f';


    // optionGroup [-c | -b ]

    /**
     * .
     * @return .
     */
    public static Options createOptions() {
        // create the Options
        Options options = new Options();
//        options.addOption(OptionBuilder
//                .hasArg()
//                .withLongOpt("file")
//                .withDescription("Het te verwerken excel bestand.")
//                .create(OPTION_FILENAME));
        options.addOption(OptionBuilder
                .withLongOpt("sql")
                .hasArg()
                .withArgName("bestand")
                .withDescription("Het te genereren bestand bevat alle sql statement om de database te vullen.")
                .create(OPTION_SQL));
        options.addOption(OptionBuilder
                .withLongOpt("dir")
                .hasArg()
                .withArgName("directory")
                .withDescription("Directory (in geval van -c: directory met excel bestanden).")
                .create(OPTION_EXCEL_DIR));
        options.addOption(OptionBuilder
                .withLongOpt("database")
                .hasArgs()
                .withArgName("url")
                .withDescription("Database connecties")
                .create(OPTION_DATABASE));
        options.addOption(OptionBuilder
                .withLongOpt("user")
                .hasArgs()
                .withArgName("username")
                .withDescription("Database username")
                .create(OPTION_USERNAME));
        options.addOption(OptionBuilder
                .withLongOpt("user")
                .hasArgs()
                .withArgName("password")
                .withDescription("Database password")
                .create(OPTION_PASSWORD));

//        options.addOption(OptionBuilder
//                .withLongOpt("props")
//                .hasArg()
//                .withDescription("Property bestand bestand met alle ART projecten.")
//                .create(OPTION_PROJECTS));
//        options.addOption(OptionBuilder
//                .withLongOpt("dir")
//                .hasArg()
//                .withArgName("directory")
//                .withDescription("Directory naam voor de uitvoerbestanden, default 'output'.")
//                .create(OPTION_OUTPUT_DIR));
        OptionGroup optionGroep = new OptionGroup();
        optionGroep.addOption(OptionBuilder
                .hasArg(false)
                .withLongOpt("conv")
                .withDescription("commando voor conversie; Leest een of meerdere excel bestanden "
                        + "en genereert een sql waarmee de database gevuld kan worden.\n"
                        + "verplichte parameters [-d, -s].\n"
                        )
                .create('c'));
        optionGroep.addOption(OptionBuilder
                .hasArg(false)
                .withLongOpt("backup")
                .withDescription("commando voor backup. Maakt een logische backup van een net "
                        + "gevulde database in de test schema.\n"
                        + "verplichte parameters [-d, -u, -p].\n"
                        )
                .create('b'));
        options.addOptionGroup(optionGroep);
        return options;
    }

    /**
     * .
     * @param options .
     */
    public static void printUsage(final Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.setLeftPadding(0);
        formatter.printHelp("DataGenerator parameters: {-c | -b} [options] \n"
//            + " -c commando voor conversie; Leest een of meerdere excel bestanden en genereert een sql waarmee de database gevuld kan worden.\n"
//            + "   -d --dir <arg>      De directory met de excelbestanden.\n"
//            + "   -s --sql <arg>      Het te produceren sql bestand.\n"
//            + " -b commando voor backup; maakt een logische backup van een net gevulde database in de test schema.\n"
//            + "   -d --database <arg> De volledige jdbc url van de te vullen database.\n"
//            + "   -u --user <arg>     De username voor de database.\n."
//            + "   -p --pass <arg>     De password voor de database.\n."
            , options);
    }

    /**
     * .
     * @param options .
     * @param args .
     * @return .
     * @throws ParseException .
     */
    public static CommandLine parse(final Options options, final String[] args) throws ParseException {
        CommandLineParser cmd = new GnuParser();
        CommandLine line = cmd.parse(options, args);
        if (line.hasOption(COMMAND_CONVERT)) {
            if (!line.hasOption(OPTION_EXCEL_DIR) || null == line.getOptionValue(OPTION_EXCEL_DIR)) {
                printUsage(options);
                System.exit(2);
            }
            if (!line.hasOption(OPTION_SQL) && null == line.getOptionValue(OPTION_SQL)) {
                printUsage(options);
                System.exit(2);
            }
        } else if (line.hasOption(COMMAND_BACKUP)) {
            if (!line.hasOption(OPTION_DATABASE) && null == line.getOptionValue(OPTION_DATABASE)) {
                printUsage(options);
                System.exit(2);
            }
            if (!line.hasOption(OPTION_USERNAME) && null == line.getOptionValue(OPTION_USERNAME)) {
                printUsage(options);
                System.exit(2);
            }
            if (!line.hasOption(OPTION_PASSWORD) && null == line.getOptionValue(OPTION_PASSWORD)) {
                printUsage(options);
                System.exit(2);
            }
        } else {
            printUsage(options);
            System.exit(2);
        }
        return line;
    }
}
