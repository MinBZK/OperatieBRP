/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.sierratestdatagenerator;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
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
    public static final char OPTION_SQL = 's';
    /** . */
//    public static final char OPTION_PROJECTS = 'p';
    /** . */
//    public static final char MODE_WIJZIG = 'w';
    /** . */
//    public static final char OPTION_OUTPUT_DIR = 'd';
    /** . */
    public static final char OPTION_FILENAME = 'f';

    /**
     * .
     * @return .
     */
    public static Options createOptions() {
        // create the Options
        Options options = new Options();
        options.addOption(OptionBuilder
                .withLongOpt("file")
                .hasArg()
                .withDescription("Het te verwerken excel bestand.")
                .create(OPTION_FILENAME));
        options.addOption(OptionBuilder
                .withLongOpt("sql")
                .hasArg()
                .withDescription("Het te genereren bestand bevat alle sql statement om de database te vullen.")
                .create(OPTION_SQL));
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
        return options;
    }

    /**
     * .
     * @param options .
     */
    public static void printUsage(final Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("DataGenerator parameters: -f exce-filename -s sql-filename\n"
            + "Leest het excel bestand en genereert een sql dat een database kan vullen met de data uit het excel bestand.\n"
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
        if (!line.hasOption(OPTION_FILENAME) || null == line.getOptionValue(OPTION_FILENAME)) {
            printUsage(options);
            System.exit(2);
        }
        if (!line.hasOption(OPTION_SQL) && null == line.getOptionValue(OPTION_SQL)) {
            printUsage(options);
            System.exit(2);
        }
        return line;
    }
}
