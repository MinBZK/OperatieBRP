/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import java.util.Collection;

/**
 * Generic SpdElement visitor.
 * @param <V> the type of which results are returned.
 */
interface SpdElementVisitor<V> {
    /**
     * Visit an Operation.
     * @param operation operation
     * @return result of type V
     */
    V visit(Operation operation);

    /**
     * Visit an OperationRecord.
     * @param operationRecord operation record
     * @return result of type V
     */
    V visit(OperationRecord operationRecord);

    /**
     * Visit a TerminationRecord.
     * @param terminationRecord termination record
     * @return result of type V
     */
    V visit(TerminationRecord terminationRecord);

    /**
     * Aggregates various results for composite structures.
     * @param values results of type V
     * @return the aggregates result
     */
    V aggregate(Collection<V> values);
}
