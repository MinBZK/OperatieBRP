/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import java.util.Collection;
import java.util.Collections;

/**
 * LogoffConfirmation - Length [5] Operationcode [3] LogoffResult [4]. TotalLength: 7 (exclusive lengthField).
 */
public final class LogoffConfirmation implements OperationRecord {

    private final int logoffResult;

    /**
     * Constructor.
     */
    public LogoffConfirmation() {
        this.logoffResult = 0;

        invariant();
    }

    private LogoffConfirmation(final int result) {
        this.logoffResult = result;

        invariant();
    }

    /**
     * Factory method for creating a LogoffConfirmation from the concatenation of operation items.
     * @param operationItems concatenated string of operation items
     * @return LogoffConfirmation object
     */
    public static LogoffConfirmation fromOperationItems(final String operationItems) {
        return new LogoffConfirmation(Integer.parseInt(operationItems.substring(0, SpdConstants.RESULT_LENGTH)));
    }

    @Override
    public Collection<Field<?>> fields() {
        return Collections.singletonList(NumericField.builder().name("Result").length(SpdConstants.RESULT_LENGTH).optional().build());
    }

    @Override
    public int operationCode() {
        return SpdConstants.OPC_LOGOFF_CONFIRMATION;
    }

    @Override
    public String toSpdString() {
        return String.format("%04d", logoffResult);
    }
}
