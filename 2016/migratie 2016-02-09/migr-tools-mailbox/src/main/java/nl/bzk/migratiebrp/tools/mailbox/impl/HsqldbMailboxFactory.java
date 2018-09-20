/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.mailbox.impl;

import com.mchange.v2.c3p0.DriverManagerDataSource;
import java.io.File;
import java.io.IOException;
import javax.sql.DataSource;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * HSQLDB Mailbox factory.
 */
public final class HsqldbMailboxFactory implements MailboxFactory, DisposableBean {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final String DEFAULT_DATA_LOCATION = "data";

    private final DriverManagerDataSource dataSource;
    private final JdbcTemplate jdbcTemplate;

    /**
     * Constructor.
     *
     * @throws MailboxException
     *             wordt gegooid als de benodigde directory niet bestaato of aangemaakt kan worden.
     */
    public HsqldbMailboxFactory() throws MailboxException {
        this(DEFAULT_DATA_LOCATION);
    }

    /**
     * Constructor.
     *
     * @param dataLocation
     *            data files locatie
     * @throws MailboxException
     *             wordt gegooid als de benodigde directory niet bestaato of aangemaakt kan worden.
     */
    public HsqldbMailboxFactory(final String dataLocation) throws MailboxException {
        LOG.info("Starting HSQLDB mailbox factory...");
        final File dataDirectory = new File(dataLocation);
        String dbFile;
        try {
            dataDirectory.mkdirs();
            if (!dataDirectory.exists() || !dataDirectory.isDirectory()) {
                throw new MailboxException("Pad voor data directory kon niet worden aangemaakt.");
            }

            dbFile = dataDirectory.getCanonicalPath() + File.separatorChar + "hsqldb";
        } catch (final IOException e) {
            throw new MailboxException("Ongeldig pad voor data directory", e);
        }
        dataSource = new DriverManagerDataSource();
        dataSource.setDriverClass("org.hsqldb.jdbc.JDBCDriver");
        dataSource.setJdbcUrl("jdbc:hsqldb:file:" + dbFile);
        dataSource.setUser("SA");
        dataSource.setPassword("");
        jdbcTemplate = new JdbcTemplate(dataSource);

        // createdb
        jdbcTemplate.execute("set database sql syntax pgs true");
        try {
            jdbcTemplate.execute("create sequence ms_sequence_nr");
        } catch (final DataAccessException e) {
            // IGNORE
            LOG.debug("Probleem tijdens create sequence", e);
        }
        try {
            jdbcTemplate.execute("create table mailbox(mailboxnr VARCHAR(7) PRIMARY KEY, status INTEGER, password VARCHAR(8))");
        } catch (final DataAccessException e) {
            // IGNORE
            LOG.debug("Probleem tijdens create mailbox table", e);
        }
        try {
            jdbcTemplate.execute("create table entry("
                                 + "originator_or_recipient VARCHAR(7), "
                                 + "ms_sequence_id BIGINT PRIMARY KEY, "
                                 + "mesg CLOB(20000), "
                                 + "status INTEGER, "
                                 + "message_id VARCHAR(12), "
                                 + "cross_reference VARCHAR(12), "
                                 + "mailboxnr VARCHAR(7))");
        } catch (final DataAccessException e) {
            // IGNORE
            LOG.debug("Probleem tijdens create entry table", e);
        }
    }

    /**
     * Geef de hsqldb datasource.
     *
     * @return datasource
     */
    public DataSource getDataSource() {
        return dataSource;
    }

    @Override
    public Mailbox getMailbox(final String mailboxnr) {
        return new HsqldbMailbox(this, mailboxnr);
    }

    @Override
    public int getNextMsSequenceNr() {
        return jdbcTemplate.queryForObject("select nextval('ms_sequence_nr')", Integer.class);
    }

    @Override
    public void deleteAll() throws MailboxException {
        jdbcTemplate.execute("delete from mailbox");
        jdbcTemplate.execute("delete from entry");
    }

    @Override
    public void destroy() {
        jdbcTemplate.execute("shutdown compact");
    }

}
