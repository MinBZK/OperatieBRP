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
public final class ListMessagesConfirmation implements OperationRecord {

    private ListError listError;

    /**
     * Constructor.
     * @param error ListError
     */
    public ListMessagesConfirmation(final ListError error) {
        this.listError = error;

        invariant();
    }

    private ListMessagesConfirmation(final int error) {
        final Optional<ListError> optionalResult = ListError.fromCode(error);
        Assert.isTrue(optionalResult.isPresent(), "Unknown listError code: " + error);
        if (optionalResult.isPresent()) {
            this.listError = optionalResult.get();
        }

        invariant();
    }

    /**
     * Factory method for creating a ListMessageConfirmation from the concatenation of operation items.
     * @param operationItems concatenated string of operation items
     * @return ListMessageConfirmation object
     * @throws SpdProtocolException if length of the operation items doesn't match the expected length
     */
    public static ListMessagesConfirmation fromOperationItems(final String operationItems) throws SpdProtocolException {
        if (operationItems.length() != SpdConstants.RESULT_LENGTH) {
            throw new SpdProtocolException(
                    MessagesCodes.ERRMSG_VOSPG_SPD_LISTMESSAGES_LENGTH,
                    new Object[]{SpdConstants.RESULT_LENGTH, operationItems.length()});
        }
        return new ListMessagesConfirmation(Integer.parseInt(operationItems.substring(0, SpdConstants.RESULT_LENGTH)));
    }

    @Override
    public Collection<Field<?>> fields() {
        return Collections.singletonList(NumericField.builder().name("Result").length(SpdConstants.RESULT_LENGTH).optional().build());
    }

    @Override
    public int operationCode() {
        return SpdConstants.OPC_LIST_MESSAGES_CONFIRMATION;
    }

    @Override
    public String toSpdString() {
        return String.format("%04d", listError.code());
    }

    /**
     * Returns the ListError.
     * @return list error
     */
    public ListError listError() {
        return listError;
    }

    /**
     * Enumeration of login confirmation error codes.
     */
    public enum ListError {
        /***/
        INVALID_LIMIT(1111),
        /***/
        INVALID_RANGE(1112),
        /***/
        NO_ENTRIES(1113),
        /***/
        INVALID_FILTER(1114);

        private final int code;

        /**
         * @param code error code
         */
        ListError(final int code) {
            this.code = code;
        }

        /**
         * Creates ListError from error code.
         * @param errorCode error code
         * @return GetError or null if there is no corresponding error code
         */
        public static Optional<ListError> fromCode(final int errorCode) {
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
