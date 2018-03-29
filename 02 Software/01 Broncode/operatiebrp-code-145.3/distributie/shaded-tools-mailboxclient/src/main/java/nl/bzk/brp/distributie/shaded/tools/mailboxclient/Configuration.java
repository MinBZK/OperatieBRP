package nl.bzk.brp.distributie.shaded.tools.mailboxclient;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Configuratie.
 */
abstract class Configuration {

    private static final Logger LOG = LoggerFactory.getLogger();

    protected abstract Options getOptions();

    protected abstract String getCommandDescription();

    /**
     * Parse.
     * @param args arguments
     * @return command line
     */
    public CommandLine parse(final String[] args) {
        final Options options = getOptions();
        final CommandLineParser parser = new DefaultParser();
        try {
            return parser.parse(options, args, true);
        } catch (final ParseException e) {
            LOG.warn("Kan argumenten niet verwerken.", e);
        }
        return null;
    }

    /**
     * Print help.
     */
    public void printHelp() {
        final String location = Main.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        final String jarFile = location.substring(location.lastIndexOf('/') + 1);

        final HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp(Integer.MAX_VALUE, "java -jar " + jarFile + getCommandDescription(), null, getOptions(), null, false);
    }
}
