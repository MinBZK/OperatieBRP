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
import nl.bzk.migratiebrp.voisc.database.entities.Bericht;
import nl.bzk.migratiebrp.voisc.spd.exception.MessagesCodes;
import nl.bzk.migratiebrp.voisc.spd.exception.SpdProtocolException;
import org.springframework.util.Assert;

/**
 * GetMessageConfirmation - Length [5] Operationcode [3] GetError [4]. TotalLength: 7 (exclusive lengthField).
 */
public final class GetMessageConfirmation implements OperationRecord {

    private GetError getError;

    private GetMessageConfirmation(final int error) {
        final Optional<GetError> optionalResult = GetError.fromCode(error);
        Assert.isTrue(optionalResult.isPresent(), "Unknown listError code: " + error);
        if (optionalResult.isPresent()) {
            this.getError = optionalResult.get();
        }

        invariant();
    }

    /**
     * Factory method for creating a GetMessageConfirmation from the concatenation of operation items.
     * @param operationItems concatenated string of operation items
     * @return GetMessageConfirmation object
     * @throws SpdProtocolException if the length of the operation items doesn't match the expected length
     */
    public static GetMessageConfirmation fromOperationItems(final String operationItems) throws SpdProtocolException {
        if (operationItems.length() != SpdConstants.RESULT_LENGTH) {
            throw new SpdProtocolException(
                    MessagesCodes.ERRMSG_VOSPG_SPD_GETMESSAGE_CONF_LENGTH,
                    new Object[]{SpdConstants.RESULT_LENGTH, operationItems.length()});
        }
        return new GetMessageConfirmation(Integer.parseInt(operationItems.substring(0, SpdConstants.RESULT_LENGTH)));
    }

    @Override
    public Collection<Field<?>> fields() {
        return Collections.singletonList(NumericField.builder().name("Result").length(SpdConstants.RESULT_LENGTH).optional().build());
    }

    @Override
    public int operationCode() {
        return SpdConstants.OPC_GET_MESSAGE_CONFIRMATION;
    }

    @Override
    public String toSpdString() {
        return String.format("%04d", getError.code());
    }

    @Override
    public void vulBericht(final Bericht bericht) throws SpdProtocolException {
        throw new SpdProtocolException(
                MessagesCodes.ERRMSG_VOSPG_SPD_GETMESSAGE_ERROR,
                new Object[]{getError().code()});
    }

    /**
     * Returns the GetError.
     * @return get error
     */
    public GetError getError() {
        return getError;
    }

    /**
     * Enumeration of get error codes.
     */
    public enum GetError {
        /***/
        INVALID_MS_SEQUENCE_NUMBER(1071),
        /***/
        NO_ENTRIES(1072);

        private final int code;

        /**
         * @param code error code
         */
        GetError(final int code) {
            this.code = code;
        }

        /**
         * Creates GetError from error code.
         * @param errorCode error code
         * @return GetError or null if there is no corresponding error code
         */
        public static Optional<GetError> fromCode(final int errorCode) {
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
