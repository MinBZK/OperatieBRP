/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.queue;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;

/**
 * Commands.
 */
public final class Commands {
    private static final Logger LOG = LoggerFactory.getLogger();

    private static final int COUNT_ARGUMENTS = 2;
    private static final int LIST_ARGUMENTS = 2;
    private static final int SHOW_ARGUMENTS = 3;

    private static final int QUEUE_ARGMENT = 1;
    private static final int JMS_ID_ARGUMENT = 2;


    private Commands() {
        // Non-instantiable
    }

    /**
     * Count messages.
     * @param driver driver
     * @param arguments arguments
     */
    public static void count(final Driver driver, final String[] arguments) {
        if (checkNumberOfArguments("count", arguments, COUNT_ARGUMENTS)) {
            driver.count(arguments[QUEUE_ARGMENT]);
        }
    }

    /**
     * List messages.
     * @param driver driver
     * @param arguments arguments
     */
    public static void list(final Driver driver, final String[] arguments) {
        if (checkNumberOfArguments("list", arguments, LIST_ARGUMENTS)) {
            driver.list(arguments[QUEUE_ARGMENT]);
        }
    }

    /**
     * Show message.
     * @param driver driver
     * @param arguments arguments
     */
    public static void show(final Driver driver, final String[] arguments) {
        if (checkNumberOfArguments("show", arguments, SHOW_ARGUMENTS)) {
            driver.show(arguments[QUEUE_ARGMENT], arguments[JMS_ID_ARGUMENT]);
        }
    }

    private static boolean checkNumberOfArguments(final String command, final String[] commandArguments, final int expectedNumber) {
        if (commandArguments.length != expectedNumber) {
            LOG.error("Command '" + command + "' expects " + (expectedNumber - 1) + " arguments.");
            return false;
        }
        return true;
    }

    /**
     * Command handler.
     */
    @FunctionalInterface
    public interface CommandHandler {

        /**
         * Execute command.
         * @param driver driver
         * @param arguments arguments
         */
        void execute(Driver driver, String[] arguments);
    }
}
