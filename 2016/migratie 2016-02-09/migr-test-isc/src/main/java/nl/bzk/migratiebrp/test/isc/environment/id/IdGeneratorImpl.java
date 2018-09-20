/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.id;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Component;

/**
 * ID generator.
 */
@Component
public final class IdGeneratorImpl implements IdGenerator {

    private static final DecimalFormat EREF_FORMAT = new DecimalFormat("000000");
    private static final SimpleDateFormat PREFIX_FORMAT = new SimpleDateFormat("HHmmss");
    private static final String EREF_PREFIX = PREFIX_FORMAT.format(new Date());

    private static final AtomicInteger COUNTER = new AtomicInteger();

    /**
     * Genereer (max lengte 12) ID. Let op: enkel uniek binnen JVM (implementatie dmv AtomicInteger).
     *
     * @return id
     */
    @Override
    public String generateId() {
        return EREF_PREFIX + EREF_FORMAT.format(COUNTER.getAndIncrement());
    }
}
