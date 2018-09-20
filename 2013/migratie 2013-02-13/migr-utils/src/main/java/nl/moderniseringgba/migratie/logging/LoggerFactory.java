/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.logging;

/**
 * Deze factory voorziet in Logger instanties die gebruikt moeten worden voor al de logging binnen de
 * migratievoorziening.
 * 
 * 
 * @see org.slf4j.Logger
 */
public final class LoggerFactory {

    private LoggerFactory() {
        throw new AssertionError("Niet instantieerbaar");
    }

    /**
     * @return een nieuwe Logger die de class name gebruikt van de aanroepende class.
     */
    public static Logger getLogger() {
        /*
         * 0 = Thread.getStackTrace() 1 = LoggerFactory.getLogger() 2 = [de aanroeper]
         */
        final StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        final StackTraceElement caller = stackTraceElements[2];
        return new Slf4jLoggerWrapper(org.slf4j.LoggerFactory.getLogger(caller.getClassName()));
    }
}
