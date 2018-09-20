/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.logging;

import org.junit.Test;


public class LoggerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Test
    public void debug() {
        MDC.put(MDCVeld.MDC_APPLICATIE_NAAM, "test-debug");
        try {
            LOGGER.debug("melding");
            LOGGER.debug("melding met {}", "argument");
            LOGGER.debug(Data.asMap("key1", "value1", "key2", "value2"), "melding");
            LOGGER.debug(Data.asMap("key1", "value1", "key2", "value2"), "melding met {}", "argument");
        } finally {
            MDC.remove(MDCVeld.MDC_APPLICATIE_NAAM);
        }
    }

    @Test
    public void info() {
        MDC.put(MDCVeld.MDC_APPLICATIE_NAAM, "test-info");
        try {
            LOGGER.info("melding");
            LOGGER.info("melding met {}", "argument");
            LOGGER.info(Data.asMap("key1", "value1", "key2", "value2"), "melding");
            LOGGER.info(Data.asMap("key1", "value1", "key2", "value2"), "melding met {}", "argument");
            LOGGER.info(FunctioneleMelding.DUMMY);
            LOGGER.info(FunctioneleMelding.DUMMY, "melding");
            LOGGER.info(FunctioneleMelding.DUMMY, "melding met {}", "argument");
            LOGGER.info(FunctioneleMelding.DUMMY, Data.asMap("key1", "value1", "key2", "value2"));
            LOGGER.info(FunctioneleMelding.DUMMY, Data.asMap("key1", "value1", "key2", "value2"), "melding");
            LOGGER.info(FunctioneleMelding.DUMMY, Data.asMap("key1", "value1", "key2", "value2"), "melding met {}",
                    "argument");
        } finally {
            MDC.remove(MDCVeld.MDC_APPLICATIE_NAAM);
        }
    }

    @Test
    public void warn() {
        MDC.put(MDCVeld.MDC_APPLICATIE_NAAM, "test-warn");
        try {
            LOGGER.warn("melding");
            LOGGER.warn("melding met {}", "argument");
            LOGGER.warn(Data.asMap("key1", "value1", "key2", "value2"), "melding");
            LOGGER.warn(Data.asMap("key1", "value1", "key2", "value2"), "melding met {}", "argument");
            LOGGER.warn(FunctioneleMelding.DUMMY);
            LOGGER.warn(FunctioneleMelding.DUMMY, "melding");
            LOGGER.warn(FunctioneleMelding.DUMMY, "melding met {}", "argument");
            LOGGER.warn(FunctioneleMelding.DUMMY, Data.asMap("key1", "value1", "key2", "value2"));
            LOGGER.warn(FunctioneleMelding.DUMMY, Data.asMap("key1", "value1", "key2", "value2"), "melding");
            LOGGER.warn(FunctioneleMelding.DUMMY, Data.asMap("key1", "value1", "key2", "value2"), "melding met {}",
                    "argument");
        } finally {
            MDC.remove(MDCVeld.MDC_APPLICATIE_NAAM);
        }
    }

    @Test
    public void error() {
        MDC.put(MDCVeld.MDC_APPLICATIE_NAAM, "test-error");
        try {
            LOGGER.error("melding");
            LOGGER.error("melding met {}", "argument");
            LOGGER.error(Data.asMap("key1", "value1", "key2", "value2"), "melding");
            LOGGER.error(Data.asMap("key1", "value1", "key2", "value2"), "melding met {}", "argument");
            LOGGER.error(FunctioneleMelding.DUMMY);
            LOGGER.error(FunctioneleMelding.DUMMY, "melding");
            LOGGER.error(FunctioneleMelding.DUMMY, "melding met {}", "argument");
            LOGGER.error(FunctioneleMelding.DUMMY, Data.asMap("key1", "value1", "key2", "value2"));
            LOGGER.error(FunctioneleMelding.DUMMY, Data.asMap("key1", "value1", "key2", "value2"), "melding");
            LOGGER.error(FunctioneleMelding.DUMMY, Data.asMap("key1", "value1", "key2", "value2"), "melding met {}",
                    "argument");
        } finally {
            MDC.remove(MDCVeld.MDC_APPLICATIE_NAAM);
        }
    }
}
