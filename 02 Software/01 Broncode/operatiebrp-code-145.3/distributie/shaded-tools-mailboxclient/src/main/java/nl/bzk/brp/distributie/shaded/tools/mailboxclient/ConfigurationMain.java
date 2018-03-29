package nl.bzk.brp.distributie.shaded.tools.mailboxclient;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

/**
 * Main arguments.
 */
final class ConfigurationMain extends Configuration {

    static final String HOST_OPTION = "host";
    static final String PORT_OPTION = "port";
    static final String MAILBOX_OPTION = "mailbox";
    static final String PASS_OPTION = "password";

    @Override
    protected Options getOptions() {
        final Options options = new Options();

        options.addOption(Option.builder().longOpt(HOST_OPTION).desc("Host").hasArg().required().build());
        options.addOption(Option.builder().longOpt(PORT_OPTION).desc("Port").hasArg().build());
        options.addOption(Option.builder().longOpt(MAILBOX_OPTION).desc("Mailbox om mee te verbinden").hasArg().required().build());
        options.addOption(Option.builder().longOpt(PASS_OPTION).desc("Mailbox wachtwoord").hasArg().required().build());

        return options;
    }

    @Override
    protected String getCommandDescription() {
        return " [options] commando [arguments]";
    }

}
