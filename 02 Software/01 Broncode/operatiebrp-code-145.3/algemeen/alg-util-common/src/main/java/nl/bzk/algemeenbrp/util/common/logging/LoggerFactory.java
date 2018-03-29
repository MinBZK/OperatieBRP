/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.common.logging;

/**
 * Deze factory voorziet in Logger instanties die gebruikt moeten worden voor al de logging binnen
 * de migratievoorziening.
 *
 *
 * @see org.slf4j.Logger
 */
public final class LoggerFactory {

    private static final int CALL_STACK_DIEPTE_CORRECTIE = 3;

    private LoggerFactory() {
        throw new AssertionError("Niet instantieerbaar");
    }

    /**
     * Geef de waarde van logger.
     *
     * @return een nieuwe Logger die de class name gebruikt van de aanroepende class.
     */
    public static Logger getLogger() {
        final String callerClassName = getCallerClassName();

        return new LoggerImpl(org.slf4j.LoggerFactory.getLogger(callerClassName));
    }

    /**
     * Geef de waarde van bericht verkeer logger.
     *
     * @return een nieuwe Logger voor bericht verkeer details die de class name gebruikt van de
     *         aanroepende class.
     */
    public static Logger getBerichtVerkeerLogger() {
        final String callerClassName = getCallerClassName();

        return new LoggerImpl(org.slf4j.LoggerFactory.getLogger("BerichtVerkeer." + callerClassName));
    }

    /**
     * Geef de waarde van caller class name.
     *
     * @return caller class name
     */
    private static String getCallerClassName() {
        /*
         * 0 = Thread.getStackTrace() 1 = LoggerFactory.getCallerClassName() 2 =
         * LoggerFactory.getLogger() 3 = [de aanroeper]
         */
        final StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        final StackTraceElement caller = stackTraceElements[CALL_STACK_DIEPTE_CORRECTIE];
        return caller.getClassName();
    }
}
