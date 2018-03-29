/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import org.springframework.util.Assert;

/**
 * LogonConfirmation - Length [5] Operationcode [3] LogonResult [4] MessageEntryDTS [11] SystemManagerMessage [60].
 * TotalLength: 78 (exclusive lengthField).
 */
public final class LogonConfirmation implements OperationRecord {

    private LogonResult result;

    /**
     * Constructor.
     * @param result LogonResult
     */
    public LogonConfirmation(final LogonConfirmation.LogonResult result) {
        this.result = result;

        invariant();
    }

    private LogonConfirmation(final int resultCode) {
        final Optional<LogonResult> optionalResult = LogonResult.fromCode(resultCode);
        Assert.isTrue(optionalResult.isPresent(), "Unknown logon result code: " + resultCode);
        if (optionalResult.isPresent()) {
            this.result = optionalResult.get();
        }

        invariant();
    }

    /**
     * Factory method for creating a LogonConfirmation from the concatenation of operation items.
     * @param operationItems concatenated string of operation items
     * @return LogonConfirmation object
     */
    public static LogonConfirmation fromOperationItems(final String operationItems) {
        return new LogonConfirmation(Integer.parseInt(operationItems.substring(0, SpdConstants.RESULT_LENGTH)));
    }

    @Override
    public Collection<Field<?>> fields() {
        return Collections.singletonList(NumericField.builder().name("Result").length(SpdConstants.RESULT_LENGTH).optional().build());
    }

    public boolean isLoggedOn() {
        return result == LogonResult.OK;
    }

    /**
     * Returns the LogonResult.
     * @return logon result
     */
    public LogonResult result() {
        return result;
    }

    @Override
    public int operationCode() {
        return SpdConstants.OPC_LOGON_CONFIRMATION;
    }

    @Override
    public String toSpdString() {
        return String.format("%04d", result.code());
    }

    /**
     * Enumeration of login confirmation error codes.
     */
    public enum LogonResult {
        /***/
        OK(0),
        /***/
        LOGON_LIMIT_EXCEEDED(1006),
        /***/
        ERROR_LIMIT_EXCEEDED(1007),
        /***/
        SYSTEM_LOCKED_BY_MANAGER(1031),
        /***/
        SECURITY_FAILURE(1033),
        /***/
        UNRECOGNIZED_MAILBOX_NAME(1034),
        /***/
        MAILBOX_BUSY(1035),
        /***/
        MAILBOX_NOT_PRESENT(1036),
        /***/
        MAILBOX_TEMPORARILY_BLOCKED(1037),
        /***/
        MAILBOX_IN_FATAL_ERROR(1038),
        /***/
        SYSTEM_LOCKED(1040);

        private final int code;

        /**
         * @param code error code
         */
        LogonResult(final int code) {
            this.code = code;
        }

        /**
         * Creates LogonError from error code.
         * @param errorCode error code
         * @return LogonError or null if there is no corresponding error code
         */
        public static Optional<LogonResult> fromCode(final int errorCode) {
            return Arrays.stream(values()).filter(result -> result.code() == errorCode).findFirst();
        }

        /**
         * Error code.
         * @return error code
         */
        public int code() {
            return code;
        }
    }
}
