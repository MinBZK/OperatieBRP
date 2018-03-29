package nl.bzk.brp.distributie.shaded.tools.mailboxclient;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.tools.neoload.BerichtFacade;
import nl.bzk.migratiebrp.tools.neoload.MailboxFacade;
import nl.bzk.migratiebrp.voisc.spd.exception.VoaException;
import org.apache.commons.cli.CommandLine;

/**
 * Ontvang berichten.
 */
final class CommandReceive implements Command {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public void execute(final MailboxFacade mailbox, final CommandLine mainOptions, final  String[] arguments) {
        try {
            final BerichtFacade bericht =
                    mailbox.ontvang(mainOptions.getOptionValue(ConfigurationMain.MAILBOX_OPTION), mainOptions.getOptionValue(ConfigurationMain.PASS_OPTION));
            if (bericht == null) {
                LOG.info("Geen bericht ontvangen");
            } else {
                LOG.info("Bericht ontvangen van mailbox {}", bericht.getVerzendendeMailbox());
                LOG.info("Message id: {}", bericht.getMessageId());
                LOG.info("Correlatie id: {}", bericht.getCorrelationId());
                LOG.info("{}", bericht.getInhoud());
            }
        } catch (VoaException e) {
            LOG.error("Onverwachte fout tijdens ontvangen bericht", e);
        }
    }
}
