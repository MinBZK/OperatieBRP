/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.mailbox.jmx;

import nl.bzk.migratiebrp.tools.mailbox.MailboxServerWrapper;
import nl.bzk.migratiebrp.tools.mailbox.impl.Mailbox;
import nl.bzk.migratiebrp.tools.mailbox.impl.MailboxException;
import nl.bzk.migratiebrp.util.common.jmx.UseDynamicDomain;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

/**
 * Implementatieklasse voor JMX operaties op de mailbox.
 */
@UseDynamicDomain
@ManagedResource(objectName = "nl.bzk.migratiebrp.mailbox:name=MAILBOX", description = "JMX Service voor Mailbox simulator")
public final class MailboxJmxImpl implements MailboxJmx {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Autowired
    private MailboxServerWrapper mailboxServerWrapper;

    @Override
    @ManagedOperation(description = "Alle mailboxen leeg maken.")
    public void mailboxenLegen() throws MailboxException {
        LOG.info("Mailboxen legen");
        try {
            mailboxServerWrapper.getMailboxServer().getMailboxFactory().deleteAll();
        } catch (final MailboxException e) {
            LOG.error("Probleem bij leeg maken mailboxen.", e);
            throw e;
        }
    }

    @Override
    @ManagedOperation(description = "Mailbox leeg maken.")
    public void leegMailbox(final String mailboxnr) throws MailboxException {
        LOG.info("[Mailbox {}] legen", mailboxnr);
        final Mailbox mailbox = mailboxServerWrapper.getMailboxServer().getMailboxFactory().getMailbox(mailboxnr);
        try {
            mailbox.open();
            mailbox.clear();
            mailbox.save();
        } catch (final MailboxException e) {
            LOG.error("Probleem bij leeg maken mailbox.", e);
            throw e;
        } finally {
            mailbox.close();
        }
    }

    @Override
    @ManagedOperation(description = "Set status op mailbox.")
    public void setMailboxStatus(final String mailboxnr, final String status) throws MailboxException {
        LOG.info("[Mailbox {}] Status wijzigen naar {}", mailboxnr, status);
        final Mailbox mailbox = mailboxServerWrapper.getMailboxServer().getMailboxFactory().getMailbox(mailboxnr);
        try {
            mailbox.open();
            mailbox.setStatus(Integer.parseInt(status));
            mailbox.save();
        } catch (final MailboxException e) {
            LOG.error("Probleem bij wijzigen status mailbox.", e);
            throw e;
        } finally {
            mailbox.close();
        }
    }

    @Override
    @ManagedOperation(description = "Set wachtwoord van mailbox.")
    public void setMailboxWachtwoord(final String mailboxnr, final String wachtwoord) throws MailboxException {
        LOG.info("[Mailbox {}] Wachtwoord wijzigen", mailboxnr);
        final Mailbox mailbox = mailboxServerWrapper.getMailboxServer().getMailboxFactory().getMailbox(mailboxnr);
        try {
            mailbox.open();
            mailbox.setPassword(wachtwoord);
            mailbox.save();
        } catch (final MailboxException e) {
            LOG.error("Probleem bij wijzigen wachtwoord mailbox.", e);
            throw e;
        } finally {
            mailbox.close();
        }
    }

}
