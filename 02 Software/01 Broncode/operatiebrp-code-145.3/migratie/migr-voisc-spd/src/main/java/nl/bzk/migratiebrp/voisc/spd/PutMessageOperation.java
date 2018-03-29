/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import java.util.List;
import org.springframework.util.Assert;

/**
 * Specific Operation type for the sPd PutMessage operation.
 */
public final class PutMessageOperation extends Operation {

    private static final int NR_OF_RECORDS = 3;
    private static final int PUT_ENVELOPE_RECORD_INDEX = 0;
    private static final int PUT_MESSAGE_HEADING_RECORD_INDEX = 1;
    private static final int PUT_MESSAGE_BODY_RECORD_INDEX = 2;

    /**
     * Creates a new PutMessageOperation.
     * @param operationRecords a list of operation records. Should contain PutEnvelope, PutMessageHeading and PutMessageBody operation records respectively.
     */
    PutMessageOperation(final List<OperationRecord> operationRecords) {
        super(operationRecords);
        Assert.isTrue(operationRecords.size() == NR_OF_RECORDS, "PutMessage should contain 3 OperationRecords");
        Assert.isTrue(operationRecords.get(PUT_ENVELOPE_RECORD_INDEX) instanceof PutEnvelope, "PutMessage should contain PutEnvelope");
        Assert.isTrue(operationRecords.get(PUT_MESSAGE_HEADING_RECORD_INDEX) instanceof PutMessageHeading, "PutMessage should contain PutMessageHeading");
        Assert.isTrue(operationRecords.get(PUT_MESSAGE_BODY_RECORD_INDEX) instanceof PutMessageBody, "PutMessage should contain PutMessageBody");
    }

    /**
     * Returns the PutEnvelope operation record.
     * @return PutEnvelope
     */
    public PutEnvelope envelope() {
        return (PutEnvelope) records().get(0);
    }

    /**
     * Returns the PutMessageHeading operation record.
     * @return PutMessageHeading
     */
    public PutMessageHeading heading() {
        return (PutMessageHeading) records().get(1);
    }

    /**
     * Returns the PutMessageBody operation record.
     * @return PutMessageBody
     */
    public PutMessageBody body() {
        return (PutMessageBody) records().get(PUT_MESSAGE_BODY_RECORD_INDEX);
    }
}
