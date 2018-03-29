/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.queue;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Command line arguments.
 */
public final class Configuration {

    private static final Logger LOG = LoggerFactory.getLogger();

    public static final String TYPE = "type";
    public static final String HOST = "host";
    public static final String PORT = "port";
    public static final String QUIET = "quiet";

    private Configuration() {
    }

    private static Options getOptions() {
        final Options options = new Options();

        options.addOption(Option.builder(TYPE)
                .desc("Queue type (required)")
                .hasArg()
                .argName("type")
                .required()
                .build());

        options.addOption(Option.builder(HOST)
                .desc("Queue host")
                .hasArg()
                .argName("host")
                .build());

        options.addOption(Option.builder(PORT)
                .desc("Queue port")
                .hasArg()
                .argName("port")
                .build());

        options.addOption(Option.builder(QUIET)
                .desc("Quiet")
                .build());
        return options;
    }

    /**
     * Parse.
     * @param args arguments
     * @return command line
     */
    public static CommandLine parse(final String[] args) {
        final Options options = getOptions();
        final CommandLineParser parser = new DefaultParser();
        try {
            return parser.parse(options, args, false);
        } catch (final ParseException e) {
            LOG.warn("Kan argumenten niet verwerken.", e);
        }
        return null;
    }

    /**
     * Print helper
     */
    public static void printHelp() {

        final String location = Main.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        final String jarFile = location.substring(location.lastIndexOf('/') + 1);

        final HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp(Integer.MAX_VALUE, "java -jar " + jarFile + " [options] commando [arguments]", null, getOptions(), null, false);
    }
}
