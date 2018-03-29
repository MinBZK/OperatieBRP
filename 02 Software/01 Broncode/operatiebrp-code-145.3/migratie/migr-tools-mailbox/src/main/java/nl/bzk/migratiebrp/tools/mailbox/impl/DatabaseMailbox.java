/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.mailbox.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import nl.bzk.migratiebrp.tools.mailbox.impl.AbstractMailbox.FilterResultImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 * PostgreSQL Mailbox.
 */
public final class DatabaseMailbox implements Mailbox {
    private final MailboxFactory factory;
    private final String mailboxnr;
    private final JdbcTemplate jdbcTemplate;

    /**
     * Constructor.
     * @param factory factory
     * @param mailboxnr mailboxnr
     */
    protected DatabaseMailbox(final AbstractDatabaseMailboxFactory factory, final String mailboxnr) {
        this.factory = factory;
        this.mailboxnr = mailboxnr;
        jdbcTemplate = factory.getJdbcTemplate();

        final Integer exists =
                jdbcTemplate.queryForObject("select count(*) from mailbox.mailbox where mailboxnr = ?", new Object[]{mailboxnr}, Integer.class);
        if (exists == 0) {
            jdbcTemplate.update("insert into mailbox.mailbox(mailboxnr) values (?)", mailboxnr);
        }
    }

    @Override
    public String getMailboxnr() {
        return mailboxnr;
    }

    @Override
    public MailboxStatus getStatus() {
        final Integer status =
                jdbcTemplate.queryForObject("select status from mailbox.mailbox where mailboxnr = ?", new Object[]{mailboxnr}, Integer.class);
        return status == null ? MailboxStatus.STATUS_OPEN : MailboxStatus.fromValue(status.intValue());
    }

    @Override
    public void setStatus(final MailboxStatus status) {
        jdbcTemplate.update("update mailbox.mailbox set status = ? where mailboxnr = ?", status.getValue(), mailboxnr);
    }

    @Override
    public boolean checkPassword(final String givenPassword) {
        final String password =
                jdbcTemplate.queryForObject("select password from mailbox.mailbox where mailboxnr = ?", new Object[]{mailboxnr}, String.class);
        return password == null || "".equals(password) || password.equals(givenPassword);
    }

    @Override
    public void setPassword(final String password) {
        jdbcTemplate.update("update mailbox.mailbox set password = ? where mailboxnr = ?", password, mailboxnr);
    }

    @Override
    public void open() throws MailboxException {
        // Niets
    }

    @Override
    public void clear() throws MailboxException {
        jdbcTemplate.update("update mailbox.mailbox set password=null, status=null where mailboxnr = ?", mailboxnr);
        jdbcTemplate.update("delete from mailbox.entry where mailboxnr = ?", mailboxnr);
    }

    @Override
    public void save() throws MailboxException {
        // Niets
    }

    @Override
    public void close() {
        // Niets
    }

    @Override
    public void addEntry(final MailboxEntry entry) {
        entry.setMsSequenceNr(factory.getNextMsSequenceNr());
        jdbcTemplate.update(
                "insert into mailbox.entry(originator_or_recipient, ms_sequence_id, mesg, status, message_id, cross_reference, notification_request, "
                        + "mailboxnr) "
                        + "values(?, ?, ?, ?, ?, ?, ?, ?)",
                entry.getOriginatorOrRecipient(),
                entry.getMsSequenceId(),
                entry.getMesg(),
                entry.getStatus(),
                entry.getMessageId(),
                entry.getCrossReference(),
                entry.getNotificationRequest(),
                mailboxnr);
    }

    @Override
    public MailboxEntry getEntry(final Integer msSequenceNr) {
        jdbcTemplate.update(
                "update mailbox.entry set status = ? where ms_sequence_id = ? and mailboxnr = ?",
                MailboxEntry.STATUS_PROCESSED,
                msSequenceNr,
                mailboxnr);

        return jdbcTemplate.queryForObject(
                "select originator_or_recipient, ms_sequence_id, mesg, status, message_id, cross_reference, notification_request "
                        + "from mailbox.entry where ms_sequence_id = ? and mailboxnr = ?",
                new Object[]{msSequenceNr, mailboxnr},
                new EntryRowMapper());
    }

    @Override
    public FilterResult filterInbox(final String msStatus, final int startAtMsSequenceId, final int limit) {
        final boolean fetchNew = msStatus.contains(Integer.toString(MailboxEntry.STATUS_NEW));
        final boolean fetchListed = msStatus.contains(Integer.toString(MailboxEntry.STATUS_LISTED));
        final boolean fetchProcessed = msStatus.contains(Integer.toString(MailboxEntry.STATUS_PROCESSED));

        final List<MailboxEntry> list =
                jdbcTemplate.query(
                        "select originator_or_recipient, ms_sequence_id, mesg, status, message_id, cross_reference, notification_request from mailbox.entry "
                                + "where "
                                + "(status = ? or status = ? or status = ?) and ms_sequence_id >= ? and mailboxnr = ? "
                                + "order by ms_sequence_id asc limit ?",
                        new Object[]{fetchNew ? MailboxEntry.STATUS_NEW : -1,
                                fetchListed ? MailboxEntry.STATUS_LISTED : -1,
                                fetchProcessed ? MailboxEntry.STATUS_PROCESSED : -1,
                                startAtMsSequenceId,
                                mailboxnr,
                                limit + 1,},
                        new EntryRowMapper());
        final MailboxEntry nextSearchEntry;
        if (list.size() == limit + 1) {
            nextSearchEntry = list.remove(limit);
        } else {
            nextSearchEntry = null;
        }

        for (final MailboxEntry entry : list) {
            jdbcTemplate.update("update mailbox.entry set status = ? where ms_sequence_id = ?", MailboxEntry.STATUS_LISTED, entry.getMsSequenceId());
        }

        return new FilterResultImpl(list, nextSearchEntry == null ? 0 : nextSearchEntry.getMsSequenceId());
    }

    /**
     * Entry mapper.
     */
    public static final class EntryRowMapper implements RowMapper<MailboxEntry> {

        @Override
        public MailboxEntry mapRow(final ResultSet rs, final int rowNum) throws SQLException {
            final MailboxEntry entry = new MailboxEntry();
            entry.setOriginatorOrRecipient(rs.getString("originator_or_recipient"));
            entry.setMsSequenceNr(rs.getInt("ms_sequence_id"));
            entry.setMesg(rs.getString("mesg"));
            entry.setStatus(rs.getInt("status"));
            entry.setMessageId(rs.getString("message_id"));
            entry.setCrossReference(rs.getString("cross_reference"));
            entry.setNotificationRequest(rs.getInt("notification_request"));
            return entry;
        }

    }

}
