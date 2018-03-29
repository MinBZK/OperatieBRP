/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Represents an Operation according to the sPd-protocol.
 */
public class Operation implements SpdElement {

    private List<OperationRecord> operationRecords;
    private TerminationRecord terminationRecord = new TerminationRecord();

    /**
     * Creates a new Operation based on the provided operation records.
     * @param operationRecords operation records.
     */
    protected Operation(final List<OperationRecord> operationRecords) {
        this.operationRecords = operationRecords;
    }

    /**
     * Indicates whether this operation contains any operation records of a certain type.
     * @param clazz the type of operation record which this operation should contain
     * @return whether this operation contains any operation record of the provided type
     */
    public final boolean containsRecordOfType(final Class<? extends OperationRecord> clazz) {
        return !operationRecords.isEmpty() && operationRecords.stream().anyMatch(record -> clazz.isAssignableFrom(record.getClass()));
    }

    /**
     * Retrieves the operation record of the provided type, if any.
     * @param clazz the type of operation record to get the record for
     * @return an optional operation record of the specified type
     */
    public final Optional<OperationRecord> getRecordOfType(final Class<? extends OperationRecord> clazz) {
        return operationRecords.stream().filter(record -> clazz.isAssignableFrom(record.getClass())).findFirst();
    }

    /**
     * Returns the operation record which is marked as a request from this operation. There can be only one.
     * @return the operation record which is marked as a request
     */
    public final Optional<Request> getRequest() {
        final List<Request> requests =
                operationRecords.stream().filter(record -> record instanceof Request).map(Request.class::cast).collect(Collectors.toList());
        if (requests.size() > 1) {
            throw new IllegalStateException("Multiple Request records found.");
        } else {
            return requests.stream().findFirst();
        }
    }

    /**
     * Returns the list of operation records for this operation.
     * @return the list of operation records
     */
    public final List<OperationRecord> records() {
        return Collections.unmodifiableList(operationRecords);
    }

    @Override
    public final <V> V accept(final SpdElementVisitor<V> visitor) {
        final List<V> visited = operationRecords.stream().map(record -> record.accept(visitor)).collect(Collectors.toList());
        visited.add(terminationRecord.accept(visitor));
        return visitor.aggregate(visited);
    }

    /**
     * Builder for an Operation.
     */
    public static final class Builder {
        private List<OperationRecord> operationRecords;
        private boolean isPutMessage;

        /**
         * Instantiates a new Builder.
         */
        public Builder() {
            operationRecords = new ArrayList<>();
        }

        /**
         * Adds an OperationRecord to this builder.
         * @param record OperationRecord
         * @return this builder instance
         */
        public Builder add(final OperationRecord record) {
            operationRecords.add(record);
            if (record instanceof PutEnvelope) {
                isPutMessage = true;
            }
            return this;
        }

        /**
         * Construct a new Operation instance from this Builder.
         * @return a new Operation instance
         */
        public Operation build() {
            if (isPutMessage) {
                return new PutMessageOperation(operationRecords);
            } else {
                return new Operation(operationRecords);
            }
        }
    }
}
