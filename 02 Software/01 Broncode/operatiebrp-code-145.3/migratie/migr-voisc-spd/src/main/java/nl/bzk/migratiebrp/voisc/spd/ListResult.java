/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

/**
 * ListResult according to the sPd-protocol.
 */
public final class ListResult implements OperationRecord {

    private static final int NUMBER_LENGTH = 9;

    private final NumericField nextMSSequenceNumber =
            (NumericField) NumericField.builder().name("NextMSSequenceNumber").length(NUMBER_LENGTH).optional().build();

    /**
     * Constructor.
     */
    public ListResult() {
        // empty constructor
    }

    /**
     * Constructor.
     * @param msSequenceNumber nextMSSequenceNumber
     */
    public ListResult(final int msSequenceNumber) {
        this.nextMSSequenceNumber.setValue(msSequenceNumber);
        invariant();
    }

    /**
     * Constructor.
     * @param msSequenceNumber msSequenceNumber
     */
    private ListResult(final String msSequenceNumber) {
        this.nextMSSequenceNumber.setRawValue(msSequenceNumber);
        invariant();
    }

    /**
     * Factory method for creating an MSEntry from the concatenation of operation items.
     * @param operationItems concatenated string of operation items
     * @return MSEntry object
     */
    public static ListResult fromOperationItems(final String operationItems) {
        return new ListResult(operationItems.substring(0, NUMBER_LENGTH));
    }

    public Integer getNextMSSequenceNumber() {
        return nextMSSequenceNumber.getValue();
    }

    @Override
    public int operationCode() {
        return SpdConstants.OPC_LIST_RESULT;
    }
}
