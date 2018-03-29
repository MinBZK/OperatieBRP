/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import java.util.Collection;

/**
 * Visitor to transform SpdElements to their sPd-protocol representation.
 */
public final class SpdElementToStringVisitor implements SpdElementVisitor<StringBuilder> {

    @Override
    public StringBuilder visit(final Operation operation) {
        return operation.accept(this);
    }

    @Override
    public StringBuilder visit(final OperationRecord record) {
        return new StringBuilder(record.lengthField()).append(record.operationCodeField()).append(record.toSpdString());
    }

    @Override
    public StringBuilder visit(final TerminationRecord terminationRecord) {
        return new StringBuilder(TerminationRecord.LENGTH_FIELD);
    }

    @Override
    public StringBuilder aggregate(final Collection<StringBuilder> values) {
        return values.stream().reduce(new StringBuilder(), StringBuilder::append);
    }
}
