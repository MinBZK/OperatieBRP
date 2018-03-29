/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.common.logging;

import java.util.Map;

/**
 * Logging interface.
 */
public interface Logger {

    /**
     * Geef de naam van deze logger.
     *
     * @return naam van deze logger
     */
    String getName();

    // ******************************************************************************* //
    // ******************************************************************************* //
    // ***** trace ******************************************************************* //
    // ******************************************************************************* //
    // ******************************************************************************* //

    /**
     * Log een melding op trace niveau.
     *
     * @param melding melding
     */
    void trace(String melding);

    /**
     * Log een melding op trace niveau.
     *
     * @param melding melding
     * @param cause oorzaak welke tot de logmelding leidde
     */
    void trace(String melding, Throwable cause);

    /**
     * Log een melding op trace niveau.
     *
     * @param melding melding
     * @param argumenten melding argumenten
     */
    void trace(String melding, Object... argumenten);

    /**
     * Log een melding op trace niveau.
     *
     * @param data logging gegevens
     * @param melding melding
     */
    void trace(Map<String, String> data, String melding);

    /**
     * Log een melding op trace niveau.
     *
     * @param data logging gegevens
     * @param melding melding
     * @param argumenten melding argumenten
     */
    void trace(Map<String, String> data, String melding, Object... argumenten);


    // ******************************************************************************* //
    // ******************************************************************************* //
    // ***** debug ******************************************************************* //
    // ******************************************************************************* //
    // ******************************************************************************* //

    /**
     * Log een melding op debug niveau.
     *
     * @param melding melding
     */
    void debug(String melding);

    /**
     * Log een melding op debug niveau.
     *
     * @param melding melding
     * @param cause oorzaak welke tot de logmelding leidde
     */
    void debug(String melding, Throwable cause);

    /**
     * Log een melding op debug niveau.
     *
     * @param melding melding
     * @param argumenten melding argumenten
     */
    void debug(String melding, Object... argumenten);

    /**
     * Log een melding op debug niveau.
     *
     * @param data logging gegevens
     * @param melding melding
     */
    void debug(Map<String, String> data, String melding);

    /**
     * Log een melding op debug niveau.
     *
     * @param data logging gegevens
     * @param melding melding
     * @param argumenten melding argumenten
     */
    void debug(Map<String, String> data, String melding, Object... argumenten);

    // ******************************************************************************* //
    // ******************************************************************************* //
    // ***** info ******************************************************************** //
    // ******************************************************************************* //
    // ******************************************************************************* //

    /**
     * Log een melding op info niveau.
     *
     * @param melding melding
     */
    void info(String melding);

    /**
     * Log een melding op info niveau.
     *
     * @param melding melding
     * @param cause oorzaak welke tot de logmelding leidde
     */
    void info(String melding, Throwable cause);

    /**
     * Log een melding op info niveau.
     *
     * @param melding melding
     * @param argumenten melding argumenten
     */
    void info(String melding, Object... argumenten);

    /**
     * Log een melding op info niveau.
     *
     * @param data logging gegevens
     * @param melding melding
     */
    void info(Map<String, String> data, String melding);

    /**
     * Log een melding op info niveau.
     *
     * @param data logging gegevens
     * @param melding melding
     * @param argumenten melding argumenten
     */
    void info(Map<String, String> data, String melding, Object... argumenten);

    /**
     * Log een functionele melding op info niveau.
     *
     * @param functioneleMelding functionele melding
     */
    void info(FunctioneleMelding functioneleMelding);

    /**
     * Log een functionele melding op info niveau.
     *
     * @param functioneleMelding functionele melding
     * @param melding melding
     */
    void info(FunctioneleMelding functioneleMelding, String melding);

    /**
     * Log een functionele melding op info niveau.
     *
     * @param functioneleMelding functionele melding
     * @param melding melding
     * @param argumenten melding argumenten
     */
    void info(FunctioneleMelding functioneleMelding, String melding, Object... argumenten);

    /**
     * Log een functionele melding op info niveau.
     *
     * @param functioneleMelding functionele melding
     * @param data logging gegevens
     */
    void info(FunctioneleMelding functioneleMelding, Map<String, String> data);

    /**
     * Log een functionele melding op info niveau.
     *
     * @param functioneleMelding functionele melding
     * @param data logging gegevens
     * @param melding melding
     */
    void info(FunctioneleMelding functioneleMelding, Map<String, String> data, String melding);

    /**
     * Log een functionele melding op info niveau.
     *
     * @param functioneleMelding functionele melding
     * @param data logging gegevens
     * @param melding melding
     * @param argumenten melding argumenten
     */
    void info(FunctioneleMelding functioneleMelding, Map<String, String> data, String melding, Object... argumenten);

    // ******************************************************************************* //
    // ******************************************************************************* //
    // ***** warn ******************************************************************** //
    // ******************************************************************************* //
    // ******************************************************************************* //

    /**
     * Log een melding op warn niveau.
     *
     * @param melding melding
     */
    void warn(String melding);

    /**
     * Log een melding op warn niveau.
     *
     * @param melding melding
     * @param cause oorzaak welke tot de logmelding leidde
     */
    void warn(String melding, Throwable cause);

    /**
     * Log een melding op warn niveau.
     *
     * @param melding melding
     * @param argumenten melding argumenten
     */
    void warn(String melding, Object... argumenten);

    /**
     * Log een melding op warn niveau.
     *
     * @param data logging gegevens
     * @param melding melding
     */
    void warn(Map<String, String> data, String melding);

    /**
     * Log een melding op warn niveau.
     *
     * @param data logging gegevens
     * @param melding melding
     * @param argumenten melding argumenten
     */
    void warn(Map<String, String> data, String melding, Object... argumenten);

    /**
     * Log een functionele melding op warn niveau.
     *
     * @param functioneleMelding functionele melding
     */
    void warn(FunctioneleMelding functioneleMelding);

    /**
     * Log een functionele melding op warn niveau.
     *
     * @param functioneleMelding functionele melding
     * @param melding melding
     */
    void warn(FunctioneleMelding functioneleMelding, String melding);

    /**
     * Log een functionele melding op warn niveau.
     *
     * @param functioneleMelding functionele melding
     * @param melding melding
     * @param argumenten melding argumenten
     */
    void warn(FunctioneleMelding functioneleMelding, String melding, Object... argumenten);

    /**
     * Log een functionele melding op warn niveau.
     *
     * @param functioneleMelding functionele melding
     * @param data logging gegevens
     */
    void warn(FunctioneleMelding functioneleMelding, Map<String, String> data);

    /**
     * Log een functionele melding op warn niveau.
     *
     * @param functioneleMelding functionele melding
     * @param data logging gegevens
     * @param melding melding
     */
    void warn(FunctioneleMelding functioneleMelding, Map<String, String> data, String melding);

    /**
     * Log een functionele melding op warn niveau.
     *
     * @param functioneleMelding functionele melding
     * @param data logging gegevens
     * @param melding melding
     * @param argumenten melding argumenten
     */
    void warn(FunctioneleMelding functioneleMelding, Map<String, String> data, String melding, Object... argumenten);

    // ******************************************************************************* //
    // ******************************************************************************* //
    // ***** error ******************************************************************* //
    // ******************************************************************************* //
    // ******************************************************************************* //

    /**
     * Log een melding op error niveau.
     *
     * @param melding melding
     */
    void error(String melding);

    /**
     * Log een melding op error niveau.
     *
     * @param melding melding
     * @param cause de oorzaak wellke tot de logmelding leidde.
     */
    void error(String melding, Throwable cause);

    /**
     * Log een melding op error niveau.
     *
     * @param melding melding
     * @param argumenten melding argumenten
     */
    void error(String melding, Object... argumenten);

    /**
     * Log een melding op error niveau.
     *
     * @param data logging gegevens
     * @param melding melding
     */
    void error(Map<String, String> data, String melding);

    /**
     * Log een melding op error niveau.
     *
     * @param data logging gegevens
     * @param melding melding
     * @param argumenten melding argumenten
     */
    void error(Map<String, String> data, String melding, Object... argumenten);

    /**
     * Log een functionele melding op error niveau.
     *
     * @param functioneleMelding functionele melding
     */
    void error(FunctioneleMelding functioneleMelding);

    /**
     * Log een functionele melding op error niveau.
     *
     * @param functioneleMelding functionele melding
     * @param melding melding
     */
    void error(FunctioneleMelding functioneleMelding, String melding);

    /**
     * Log een functionele melding op error niveau.
     *
     * @param functioneleMelding functionele melding
     * @param melding melding
     * @param argumenten melding argumenten
     */
    void error(FunctioneleMelding functioneleMelding, String melding, Object... argumenten);

    /**
     * Log een functionele melding op error niveau.
     *
     * @param functioneleMelding functionele melding
     * @param data logging gegevens
     */
    void error(FunctioneleMelding functioneleMelding, Map<String, String> data);

    /**
     * Log een functionele melding op error niveau.
     *
     * @param functioneleMelding functionele melding
     * @param data logging gegevens
     * @param melding melding
     */
    void error(FunctioneleMelding functioneleMelding, Map<String, String> data, String melding);

    /**
     * Log een functionele melding op error niveau.
     *
     * @param functioneleMelding functionele melding
     * @param data logging gegevens
     * @param melding melding
     * @param argumenten melding argumenten
     */
    void error(FunctioneleMelding functioneleMelding, Map<String, String> data, String melding, Object... argumenten);

    /**
     * Geeft terug of er minstens gelogd wordt op het WARN level.
     *
     * @return True indien er op WARN level wordt gelogd, false in andere gevallen.
     */
    boolean isWarnEnabled();

    /**
     * Geeft terug of er minstens gelogd wordt op het DEBUG level.
     *
     * @return True indien er op DEBUG level wordt gelogd, false in andere gevallen.
     */
    boolean isDebugEnabled();

    /**
     * Geeft terug of er minstens gelogd wordt op het TRACE level.
     *
     * @return True indien er op TRACE level wordt gelogd, false in andere gevallen.
     */
    boolean isTraceEnabled();

    /**
     * Geeft terug of er minstens gelogd wordt op het INFO level.
     *
     * @return True indien er op INFO level wordt gelogd, false in andere gevallen.
     */
    boolean isInfoEnabled();

    /**
     * Geeft terug of er minstens gelogd wordt op het ERROR level.
     *
     * @return True indien er op ERROR level wordt gelogd, false in andere gevallen.
     */
    boolean isErrorEnabled();

}
