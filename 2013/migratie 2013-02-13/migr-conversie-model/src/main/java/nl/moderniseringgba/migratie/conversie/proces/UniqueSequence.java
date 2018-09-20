/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces;

import java.util.concurrent.atomic.AtomicLong;

/**
 * A unique sequence.
 * 
 */
public final class UniqueSequence {

    private static final AtomicLong SEQUENCE = new AtomicLong();

    private UniqueSequence() {
        throw new AssertionError("Niet instantieerbaar");
    }

    /**
     * Get next value (thread-safe).
     * 
     * @return next sequence value
     */
    public static long next() {
        return SEQUENCE.incrementAndGet();
    }
}
