/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd.exception;

/**
 * IllegalLengthException.
 */
public final class IllegalLengthException extends ParseException {

    private static final long serialVersionUID = 5630543402558574327L;

    private final int expectedLength;
    private final int actualLength;

    /**
     * IllegalLengthException.
     * @param expected expected length
     * @param actual actual length
     */
    public IllegalLengthException(final int expected, final int actual) {
        expectedLength = expected;
        actualLength = actual;
    }

    public int getExpectedLength() {
        return expectedLength;
    }

    public int getActualLength() {
        return actualLength;
    }

    @Override
    public String getMessage() {
        return String.format("Expected length: %d, actual: %d.", expectedLength, actualLength);
    }
}
