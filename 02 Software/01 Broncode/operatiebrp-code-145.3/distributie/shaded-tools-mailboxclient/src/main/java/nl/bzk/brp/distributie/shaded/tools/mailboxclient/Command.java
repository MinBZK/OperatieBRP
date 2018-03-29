package nl.bzk.brp.distributie.shaded.tools.mailboxclient;


import nl.bzk.migratiebrp.tools.neoload.MailboxFacade;
import org.apache.commons.cli.CommandLine;

/**
 * Command handler.
 */
@FunctionalInterface
interface Command {

    /**
     * Execute command.
     * @param mailbox mailbox
     * @param mainOptions main options
     * @param arguments command arguments
     */
    void execute(final MailboxFacade mailbox, final CommandLine mainOptions, final String[] arguments);
}
