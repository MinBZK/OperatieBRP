/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.mailbox.impl;

/**
 * In memory mailbox implementatie van de abstracte mailbox.
 */
public class MemoryMailbox extends AbstractMailbox {

    /**
     * Default constructor.
     * 
     * @param factory
     *            De gebruikte mailboxfactory
     * @param mailboxnr
     *            Het mailbox nummer
     */
    protected MemoryMailbox(final MailboxFactory factory, final String mailboxnr) {
        super(factory, mailboxnr);
    }

    @Override
    public void open() throws MailboxException {
    }

    @Override
    public void save() throws MailboxException {
    }

    @Override
    public void close() {
    }

}
