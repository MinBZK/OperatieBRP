package nl.bzk.brp.distributie.shaded.tools.mailboxclient;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.tools.neoload.MailboxFacade;
import org.apache.commons.cli.CommandLine;

/**
 * Main class voor mailbox client.
 */
public final class Main {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final Map<String, Command> COMMANDS;
    private static final int DEFAULT_PORT = 1212;

    static {
        COMMANDS = new HashMap<>();
        COMMANDS.put("send", new CommandSend());
        COMMANDS.put("receive", new CommandReceive());
    }

    private Main() {
    }

    /**
     * Main.
     * @param args arguments
     */
    public static void main(final String[] args) {
        final Configuration configuration = new ConfigurationMain();
        final CommandLine mainOptions = configuration.parse(args);
        if (mainOptions == null) {
            configuration.printHelp();
            return;
        }

        final String[] commandAndArguments = mainOptions.getArgs();
        if (commandAndArguments == null || commandAndArguments.length == 0) {
            LOG.error("No command given");
            return;
        }

        final Command command = COMMANDS.get(commandAndArguments[0]);
        if (command == null) {
            throw new IllegalArgumentException("Unknown command '" + commandAndArguments[0] + "' (supported commands=" + COMMANDS.keySet() + ").");
        } else {
            final MailboxFacade mailbox = getMailboxFacade(mainOptions);
            command.execute(mailbox, mainOptions, removeCommand(commandAndArguments));
        }
    }

    private static String[] removeCommand(final String[] commandAndArguments) {
        final String[] result = new String[commandAndArguments.length - 1];
        System.arraycopy(commandAndArguments, 1, result,0, commandAndArguments.length - 1);
        return result;
    }

    private static MailboxFacade getMailboxFacade(final CommandLine commandLine) {
        final String host  =commandLine.getOptionValue(ConfigurationMain.HOST_OPTION);
        final String port = commandLine.getOptionValue(ConfigurationMain.PORT_OPTION);

        return new MailboxFacade(host , port == null ? DEFAULT_PORT : Integer.valueOf(port));
    }
}
