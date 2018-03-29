/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import nl.bzk.migratiebrp.voisc.spd.exception.MailboxServerPasswordException;
import nl.bzk.migratiebrp.voisc.spd.exception.MessagesCodes;
import org.springframework.util.Assert;

/**
 * Creates a ChangePasswordRequest with given paramaters according to the sPd-protocol.
 *
 * <pre>
 * <code>
 * <b>ChangePasswordRequest:</b>
 * Length           [5]
 * Operationcode    [3]
 * OldPassword      [8]
 * NewPassword      [8]
 * </code>
 * </pre>
 */
public final class ChangePasswordRequest implements OperationRecord, Request {

    private static final int PASSWORD_LENGTH = 8;
    private static final int OPERATION_ITEMS_LENGTH = PASSWORD_LENGTH * 2;

    private final StringField oldPassword = (StringField) StringField.builder().name("OldPassword").length(PASSWORD_LENGTH).build();
    private final StringField newPassword = (StringField) StringField.builder().name("NewPassword").length(PASSWORD_LENGTH).build();

    /**
     * Creates a LogonRequest with given paramaters according to the sPd-protocol.
     * @param oldPassword the MailboxNumber where the program should logon to. This number should have a length of 7.
     * @param newPassword the MailboxServer password belonging to the MailboxNumber. This number should have a max length of 8. Shorter passwords will be left
     * padded with spaces.
     * @throws MailboxServerPasswordException if password doesn't match specifications
     */
    public ChangePasswordRequest(final String oldPassword, final String newPassword) throws MailboxServerPasswordException {
        try {
            this.oldPassword.setRawValue(oldPassword);
            this.newPassword.setRawValue(newPassword);
        } catch (final IllegalArgumentException ex) {
            throw new MailboxServerPasswordException(
                    MessagesCodes.ERRMSG_VOSPG_SPD_NEW_PASSWORD_LENGTH,
                    new Object[]{newPassword == null ? 0 : newPassword.length()},
                    ex);
        }

        invariant();
    }

    /**
     * Factory method that creates a LogonRequest from a string concatenation of operation items.
     * @param operationItems string concatenation of operation items
     * @return LogonRequest object
     * @throws MailboxServerPasswordException if password doesn't match specifications
     */
    public static ChangePasswordRequest fromOperationItems(final String operationItems) throws MailboxServerPasswordException {
        Assert.isTrue(
                operationItems.length() == OPERATION_ITEMS_LENGTH,
                String.format("operationItems expected length: %d, actual: %d", OPERATION_ITEMS_LENGTH, operationItems.length()));

        return new ChangePasswordRequest(operationItems.substring(0, PASSWORD_LENGTH), operationItems.substring(PASSWORD_LENGTH));
    }

    @Override
    public int operationCode() {
        return SpdConstants.OPC_CHANGE_PASSWORD_REQUEST;
    }

    public String getOldPassword() {
        return oldPassword.getValue();
    }

    public String getNewPassword() {
        return newPassword.getValue();
    }
}
