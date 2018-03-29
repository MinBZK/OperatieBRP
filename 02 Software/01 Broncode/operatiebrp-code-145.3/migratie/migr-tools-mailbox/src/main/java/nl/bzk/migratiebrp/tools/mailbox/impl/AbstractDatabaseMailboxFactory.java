/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.mailbox.impl;

import javax.inject.Inject;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Database mailbox factory voor HSQL-DB/PostgreSQL/etc..
 */
public abstract class AbstractDatabaseMailboxFactory implements MailboxFactory, DisposableBean {

    private static final Object MAILBOX_CREATION_LOCK = new Object();

    private JdbcTemplate jdbcTemplate;

    @Override
    public Mailbox getMailbox(final String mailboxnr) {
        synchronized (MAILBOX_CREATION_LOCK) {
            // De constructor van de DatabaseMailbox doet een select om te bepalen of de mailbox 'bestaat'.
            // Indien de mailbox niet bestaat wordt een insert gedaan om de mailbox te 'creeeren'. Als dit
            // multi-threaded gebeurt en dezelfde mailbox wordt 'tegelijk' aangevraagd dan krijgen we een MAILBOX_PK
            // constraint violation.
            return new DatabaseMailbox(this, mailboxnr);
        }
    }

    @Override
    public int getNextMsSequenceNr() {
        return jdbcTemplate.queryForObject("select nextval('mailbox.ms_sequence_nr')", Integer.class);
    }

    @Override
    public void deleteAll() throws MailboxException {
        jdbcTemplate.execute("update mailbox.mailbox set password=null, status=null");
        jdbcTemplate.execute("delete from mailbox.entry");
    }

    /**
     * Geeft het JDBC Template.
     * @return Het JDBC Template
     */
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    /**
     * Zet het JDBC Template.
     * @param jdbcTemplate Het JDBC Template
     */
    @Inject
    public void setJdbcTemplate(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
