/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.mailbox.impl;

import com.mchange.v2.c3p0.DriverManagerDataSource;
import java.io.File;
import java.io.IOException;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * HSQLDB Mailbox factory.
 */
public final class HsqldbMailboxFactory extends AbstractDatabaseMailboxFactory {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final String DEFAULT_DATA_LOCATION = "data";

    /**
     * Constructor.
     * @throws MailboxException wordt gegooid als de benodigde directory niet bestaato of aangemaakt kan worden.
     */
    public HsqldbMailboxFactory() throws MailboxException {
        this(DEFAULT_DATA_LOCATION);
    }

    /**
     * Constructor.
     * @param dataLocation data files locatie
     * @throws MailboxException wordt gegooid als de benodigde directory niet bestaato of aangemaakt kan worden.
     */
    public HsqldbMailboxFactory(final String dataLocation) throws MailboxException {
        LOG.info("Starting HSQLDB mailbox factory...");
        final File dataDirectory = new File(dataLocation);
        final String dbFile;
        try {
            if (!dataDirectory.mkdirs() && !dataDirectory.exists()) {
                throw new MailboxException("Pad voor data directory kon niet worden aangemaakt.");
            }

            dbFile = dataDirectory.getCanonicalPath() + File.separatorChar + "hsqldb";
        } catch (final IOException e) {
            throw new MailboxException("Ongeldig pad voor data directory", e);
        }
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClass("org.hsqldb.jdbc.JDBCDriver");
        dataSource.setJdbcUrl("jdbc:hsqldb:file:" + dbFile);
        dataSource.setUser("SA");
        dataSource.setPassword("");
        setJdbcTemplate(new JdbcTemplate(dataSource));

        try {
            final String postgresqlCompatibility = "set database sql syntax pgs true;";
            getJdbcTemplate().execute(postgresqlCompatibility);
        } catch (final DataAccessException e) {
            // IGNORE
            LOG.debug("Probleem tijdens zetten postgresql compatibility", e);
        }

        try {
            getJdbcTemplate().execute("drop schema if exists mailbox cascade");
            getJdbcTemplate().execute("create schema mailbox");
            getJdbcTemplate().execute("create sequence mailbox.ms_sequence_nr");
        } catch (final DataAccessException e) {
            // IGNORE
            LOG.debug("Probleem tijdens create sequence", e);
        }

        try {
            getJdbcTemplate().execute("create table mailbox.mailbox(mailboxnr VARCHAR(7) PRIMARY KEY, status INTEGER, password VARCHAR(8))");
        } catch (final DataAccessException e) {
            // IGNORE
            LOG.debug("Probleem tijdens create mailbox table", e);
        }
        try {
            getJdbcTemplate().execute(
                    "create table mailbox.entry("
                            + "originator_or_recipient VARCHAR(7), "
                            + "ms_sequence_id BIGINT PRIMARY KEY, "
                            + "mesg CLOB(20000), "
                            + "status INTEGER, "
                            + "message_id VARCHAR(12), "
                            + "cross_reference VARCHAR(12), "
                            + "notification_request INTEGER, "
                            + "mailboxnr VARCHAR(7))");
        } catch (final DataAccessException e) {
            // IGNORE
            LOG.debug("Probleem tijdens create entry table", e);
        }
    }

    @Override
    public void destroy() {
        getJdbcTemplate().execute("shutdown compact");
    }
}
