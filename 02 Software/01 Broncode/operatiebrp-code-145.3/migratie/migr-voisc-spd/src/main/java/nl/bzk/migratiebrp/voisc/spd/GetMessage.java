/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

/**
 * GetMessage according to the sPd-protocol.
 *
 * <pre>
 * <code>
 * <b>GetMessage:</b>
 *   Length                 [5]
 *   Operationcode          [3]
 *   MSSequenceNumber       [9]
 * </code>
 * </pre>
 */
public final class GetMessage implements OperationRecord, Request {

    private static final int NUMBER_LENGTH = 9;

    private final NumericField msSequenceNumber = (NumericField) NumericField.builder().name("MSSequenceNumber").length(NUMBER_LENGTH).build();

    /**
     * Constructor.
     * @param msSequenceNumber msSequenceNumber
     */
    public GetMessage(final int msSequenceNumber) {
        this.msSequenceNumber.setValue(msSequenceNumber);
        invariant();
    }

    /**
     * Constructor.
     * @param msSequenceNumber msSequenceNumber
     */
    private GetMessage(final String msSequenceNumber) {
        this.msSequenceNumber.setRawValue(msSequenceNumber);
        invariant();
    }

    /**
     * Factory method for creating a GetMessage from the concatenation of operation items.
     * @param operationItems concatenated string of operation items
     * @return GetMessage object
     */
    public static GetMessage fromOperationItems(final String operationItems) {
        return new GetMessage(operationItems.substring(0, NUMBER_LENGTH));
    }

    public Integer getMsSequenceNumber() {
        return msSequenceNumber.getValue();
    }

    @Override
    public int operationCode() {
        return SpdConstants.OPC_GET_MESSAGE;
    }
}
