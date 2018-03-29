/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import java.util.Collection;
import java.util.Collections;
import nl.bzk.migratiebrp.voisc.spd.exception.MessagesCodes;
import nl.bzk.migratiebrp.voisc.spd.exception.SpdProtocolException;

/**
 * NoOperationConfirmation - Length [5] Operationcode [3] NoOperationError [4]. TotalLength: 7 (exclusive lengthField).
 */
public final class NoOperationConfirmation implements OperationRecord {

    private int noOperationError;

    /**
     * Constructor.
     * @param error error code
     */
    public NoOperationConfirmation(final int error) {
        this.noOperationError = error;

        invariant();
    }

    /**
     * Factory method for creating a NoOperationConfirmation from the concatenation of operation items.
     * @param operationItems concatenated string of operation items
     * @return NoOperationConfirmation object
     * @throws SpdProtocolException if length of the operation items doesn't match the expected length
     */
    public static NoOperationConfirmation fromOperationItems(final String operationItems) throws SpdProtocolException {
        if (operationItems.length() != SpdConstants.RESULT_LENGTH) {
            throw new SpdProtocolException(
                    MessagesCodes.ERRMSG_VOSPG_SPD_GETMESSAGE_CONF_LENGTH,
                    new Object[]{SpdConstants.RESULT_LENGTH, operationItems.length()});
        }
        return new NoOperationConfirmation(Integer.parseInt(operationItems.substring(0, SpdConstants.RESULT_LENGTH)));
    }

    @Override
    public Collection<Field<?>> fields() {
        return Collections.singletonList(NumericField.builder().name("NoOperationError").length(SpdConstants.RESULT_LENGTH).optional().build());
    }

    @Override
    public int operationCode() {
        return SpdConstants.OPC_NO_OPERATION_CONFIRMATION;
    }

    @Override
    public String toSpdString() {
        return String.format("%04d", this.noOperationError);
    }
}
