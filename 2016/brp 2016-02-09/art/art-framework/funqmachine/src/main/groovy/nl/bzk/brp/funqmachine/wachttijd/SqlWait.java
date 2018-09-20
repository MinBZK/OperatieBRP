/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.wachttijd;

import groovy.sql.Sql;
import java.util.concurrent.TimeUnit;

/**
 * Wait implementatie voor {@link groovy.sql.Sql}.
 */
public class SqlWait extends FluentWait<Sql> {
    public static final long DEFAULT_SLEEP_TIMEOUT = 1000;

    public SqlWait(final Sql sql, final long timeOutInSeconds) {
        this(sql, new SystemClock(), Sleeper.SYSTEM_SLEEPER, timeOutInSeconds, DEFAULT_SLEEP_TIMEOUT);
    }

    public SqlWait(final Sql sql, final long timeOutInSeconds, final long sleepInMillis) {
        this(sql, new SystemClock(), Sleeper.SYSTEM_SLEEPER, timeOutInSeconds, sleepInMillis);
    }

    protected SqlWait(
        final Sql input,
        final Clock clock, final Sleeper sleeper, final long timeOutInSeconds, final long sleepTimeOut)
    {
        super(input, clock, sleeper);
        withTimeout(timeOutInSeconds, TimeUnit.SECONDS);
        pollingEvery(sleepTimeOut, TimeUnit.MILLISECONDS);
//        ignoring(PSQLException.class);
    }
}
