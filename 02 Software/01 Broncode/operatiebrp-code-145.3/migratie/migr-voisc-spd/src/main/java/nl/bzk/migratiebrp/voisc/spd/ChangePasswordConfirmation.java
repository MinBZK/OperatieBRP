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
import nl.bzk.migratiebrp.voisc.spd.exception.MessagesCodes;
import nl.bzk.migratiebrp.voisc.spd.exception.SpdProtocolException;
import org.springframework.util.Assert;

/**
 * ListMessageConfirmation - Length [5] Operationcode [3] ListError [4]. TotalLength: 7 (exclusive lengthField).
 */
public final class ChangePasswordConfirmation implements OperationRecord {

    private ChangePasswordResult changePasswordResult;

    /**
     * Constructor.
     * @param result ChangePasswordResult
     */
    public ChangePasswordConfirmation(final ChangePasswordResult result) {
        changePasswordResult = result;
        invariant();
    }

    private ChangePasswordConfirmation(final int code) {
        final Optional<ChangePasswordResult> optionalResult = ChangePasswordResult.fromCode(code);
        Assert.isTrue(optionalResult.isPresent(), "Unknown ChangePasswordResult: " + code);
        if (optionalResult.isPresent()) {
            this.changePasswordResult = optionalResult.get();
        }

        invariant();
    }

    /**
     * Factory method for creating a ChangePasswordConfirmation from the concatenation of operation items.
     * @param operationItems concatenated string of operation items
     * @return ListMessageConfirmation object
     * @throws SpdProtocolException if length of th operationItems doesn't match the length of the result field
     */
    public static ChangePasswordConfirmation fromOperationItems(final String operationItems) throws SpdProtocolException {
        if (operationItems.length() != SpdConstants.RESULT_LENGTH) {
            throw new SpdProtocolException(
                    MessagesCodes.ERRMSG_VOSPG_SPD_CHANGEPASSWORD_CONF_LENGTH,
                    new Object[]{SpdConstants.RESULT_LENGTH, operationItems.length()});
        }
        return new ChangePasswordConfirmation(Integer.parseInt(operationItems.substring(0, SpdConstants.RESULT_LENGTH)));
    }

    @Override
    public Collection<Field<?>> fields() {
        return Collections.singletonList(NumericField.builder().name("Result").length(SpdConstants.RESULT_LENGTH).optional().build());
    }

    @Override
    public int operationCode() {
        return SpdConstants.OPC_CHANGE_PASSWORD_CONFIRMATION;
    }

    @Override
    public String toSpdString() {
        return String.format("%04d", changePasswordResult.code());
    }

    /**
     * Returns the ChangePasswordResult.
     * @return changePasswordResult
     */
    public ChangePasswordResult changePasswordResult() {
        return changePasswordResult;
    }

    /**
     * Enumeration of ChangePasswordResults.
     */
    public enum ChangePasswordResult {
        /***/
        OK(0),
        /***/
        OLD_PASSWORD_MISSING(1131),
        /***/
        OLD_PASSWORD_INVALID(1132),
        /***/
        NEW_PASSWORD_MISSING(1133),
        /***/
        NEW_PASSWORD_UNACCEPTABLE(1134),
        /***/
        FATAL_ERROR(1205);

        private final int code;

        /**
         * @param code code
         */
        ChangePasswordResult(final int code) {
            this.code = code;
        }

        /**
         * Creates ChangePasswordResult from code.
         * @param code code
         * @return ChangePasswordResult or null if there is no corresponding code
         */
        public static Optional<ChangePasswordResult> fromCode(final int code) {
            return Arrays.stream(values()).filter(result -> result.code() == code).findFirst();
        }

        /**
         * Code.
         * @return code
         */
        public int code() {
            return code;
        }
    }
}
