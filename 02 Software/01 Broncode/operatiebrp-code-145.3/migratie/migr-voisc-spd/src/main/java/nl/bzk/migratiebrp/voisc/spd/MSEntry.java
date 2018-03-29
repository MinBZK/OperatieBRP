/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import nl.bzk.migratiebrp.voisc.database.entities.Bericht;
import nl.bzk.migratiebrp.voisc.spd.exception.SpdProtocolException;

/**
 * MSEntry according to the sPd-protocol.
 */
public final class MSEntry implements OperationRecord {

    private static final int NUMBER_LENGTH = 9;

    private final NumericField msSequenceNumber = (NumericField) NumericField.builder().name("MSSequenceNumber").length(NUMBER_LENGTH).build();

    /**
     * Constructor.
     * @param msSequenceNumber msSequenceNumber
     */
    public MSEntry(final int msSequenceNumber) {
        this.msSequenceNumber.setValue(msSequenceNumber);
        invariant();
    }

    /**
     * Constructor.
     * @param msSequenceNumber msSequenceNumber
     */
    public MSEntry(final String msSequenceNumber) {
        this.msSequenceNumber.setRawValue(msSequenceNumber);
        invariant();
    }

    /**
     * Factory method for creating an MSEntry from the concatenation of operation items.
     * @param operationItems concatenated string of operation items
     * @return MSEntry object
     */
    public static MSEntry fromOperationItems(final String operationItems) {
        return new MSEntry(operationItems.substring(0, NUMBER_LENGTH));
    }

    public int getMsSequenceNumber() {
        return msSequenceNumber.getValue();
    }

    @Override
    public int operationCode() {
        return SpdConstants.OPC_MSENTRY;
    }

    @Override
    public void vulBericht(final Bericht bericht) throws SpdProtocolException {
        bericht.setDispatchSequenceNumber(getMsSequenceNumber());
    }
}
