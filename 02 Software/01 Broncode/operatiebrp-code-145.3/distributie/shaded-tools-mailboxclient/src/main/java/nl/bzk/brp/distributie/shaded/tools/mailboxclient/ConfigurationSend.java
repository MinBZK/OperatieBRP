package nl.bzk.brp.distributie.shaded.tools.mailboxclient;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

/**
 * Send arguments.
 */
final class ConfigurationSend extends Configuration {

    static final String MESSAGE_ID = "messageid";
    static final String CORRELATION_ID = "correlationid";
    static final String RECIPIENT = "recipient";
    static final String FILE = "file";

    @Override
    protected Options getOptions() {
        final Options options = new Options();

        options.addOption(Option.builder().longOpt(MESSAGE_ID).desc("Message ID").hasArg().required().build());
        options.addOption(Option.builder().longOpt(CORRELATION_ID).desc("Correlation ID").hasArg().build());
        options.addOption(Option.builder().longOpt(RECIPIENT).desc("Mailbox om naar te versturen").hasArg().required().build());
        options.addOption(Option.builder().longOpt(FILE).desc("Bestand met de bericht inhoud").hasArg().build());

        return options;
    }

    @Override
    protected String getCommandDescription() {
        return " send [options] [data]";
    }

}
