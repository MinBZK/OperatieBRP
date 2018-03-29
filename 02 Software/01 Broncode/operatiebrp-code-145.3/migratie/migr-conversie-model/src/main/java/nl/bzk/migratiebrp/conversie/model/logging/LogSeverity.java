/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.logging;

import java.util.HashMap;
import java.util.Map;

/**
 * Log severity.
 */
public enum LogSeverity implements Comparable<LogSeverity> {

    /**
     * Info.
     */
    INFO(100),
    /**
     * Warning.
     */
    WARNING(200),
    /**
     * Suppressed.
     */
    SUPPRESSED(250),
    /**
     * Error.
     */
    ERROR(300),
    /**
     * Kritiek.
     */
    CRITICAL(400);

    private static final Map<Integer, LogSeverity> CODE_MAP = new HashMap<>();

    static {
        final LogSeverity[] values = LogSeverity.values();
        for (final LogSeverity value : values) {
            CODE_MAP.put(value.getSeverity(), value);
        }
    }

    private final int severity;

    /**
     * Constructor.
     * @param severity severity
     */
    LogSeverity(final int severity) {
        this.severity = severity;
    }

    /**
     * Geef de waarde van severity.
     * @return severity
     */
    public int getSeverity() {
        return severity;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Geef de enumeratie waarde voor de gegeven severity.
     * @param severity severity
     * @return de enumeratie waarde, null als de code niet gevonden kan worden
     */
    public static LogSeverity valueOfSeverity(final Integer severity) {
        return CODE_MAP.get(severity);
    }
}
