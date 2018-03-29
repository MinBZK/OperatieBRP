/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.queue;


import java.util.HashMap;
import java.util.Map;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.apache.commons.cli.CommandLine;


/**
 * Queue tool.
 */
public final class Main {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final Map<String, Commands.CommandHandler> COMMANDS;

    static {
        COMMANDS = new HashMap<>();
        COMMANDS.put("count", Commands::count);
        COMMANDS.put("list", Commands::list);
        COMMANDS.put("show", Commands::show);
    }

    private Main() {
    }

    /**
     * Main.
     * @param args arguments
     */
    public static void main(final String[] args) {
        final CommandLine commandLine = Configuration.parse(args);
        if (commandLine == null) {
            Configuration.printHelp();
            return;
        }

        final Driver driver = getDriver(commandLine);

        final String[] commandArguments = commandLine.getArgs();
        if (commandArguments == null || commandArguments.length == 0) {
            LOG.error("No command given");
            return;
        }

        final Commands.CommandHandler command = COMMANDS.get(commandArguments[0]);
        if (command == null) {
            throw new IllegalArgumentException("Unknown command '" + commandArguments[0] + "' (supported commands=" + COMMANDS.keySet() + ").");
        } else {
            command.execute(driver, commandArguments);
        }
    }

    private static Driver getDriver(final CommandLine commandLine) {
        final String type = commandLine.getOptionValue(Configuration.TYPE);
        final String host = commandLine.getOptionValue(Configuration.HOST);
        final String port = commandLine.getOptionValue(Configuration.PORT);
        final boolean quiet = commandLine.hasOption(Configuration.QUIET);

        return Drivers.createDriver(type, host, port == null ? null : Integer.valueOf(port), quiet);
    }


}
