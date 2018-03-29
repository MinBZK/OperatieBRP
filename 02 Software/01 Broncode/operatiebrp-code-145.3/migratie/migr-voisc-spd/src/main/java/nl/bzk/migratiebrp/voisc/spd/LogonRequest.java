/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

/**
 * LogonRequest according to the sPd-protocol.
 *
 * <pre>
 * <code>
 * <b>LogonRequest:</b>
 * Length           [5]
 * Operationcode    [3]
 * UserName         [7]
 * </code>
 * </pre>
 */
public final class LogonRequest implements OperationRecord, Request {

    private static final int NAME_LENGTH = 7;
    private static final int PASSWORD_LENGTH = 8;

    private final StringField username = (StringField) StringField.builder().name("Username").length(NAME_LENGTH).build();
    private final StringField password = (StringField) StringField.builder().name("Password").length(PASSWORD_LENGTH).optional().build();

    /**
     * Creates a LogonRequest with given paramaters according to the sPd-protocol.
     * @param username the MailboxNumber where the program should logon to. This number should have a length of 7.
     * @param password the MailboxServer password belonging to the MailboxNumber. This number should have a max length of 8. Shorter passwords will be left
     * padded with spaces.
     */
    public LogonRequest(final String username, final String password) {
        this.username.setRawValue(username);
        this.password.setRawValue(password);

        invariant();
    }

    /**
     * Factory method that creates a LogonRequest from a string concatenation of operation items.
     * @param operationItems string concatenation of operation items
     * @return LogonRequest object
     */
    public static LogonRequest fromOperationItems(final String operationItems) {
        return new LogonRequest(operationItems.substring(0, NAME_LENGTH), operationItems.substring(NAME_LENGTH));
    }

    @Override
    public int operationCode() {
        return SpdConstants.OPC_LOGON_REQUEST;
    }

    public String getUsername() {
        return username.getValue();
    }

    public String getPassword() {
        return password.getValue();
    }
}
