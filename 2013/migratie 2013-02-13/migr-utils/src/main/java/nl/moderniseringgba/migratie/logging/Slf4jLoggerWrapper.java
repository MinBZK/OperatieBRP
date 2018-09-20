/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.logging;

import org.slf4j.Marker;

/**
 * Wrapper class voor de Slf4jLogger class. Deze class voegt onder andere context logging toe.
 * 
 * 
 * @see LoggingContext
 */
final class Slf4jLoggerWrapper implements Logger {

    private final org.slf4j.Logger log;

    /**
     * Maakt een nieuw Slf4jLoggerWrapper object.
     * 
     * @param log
     *            de slf4j logger
     */
    public Slf4jLoggerWrapper(final org.slf4j.Logger log) {
        this.log = log;
    }

    @Override
    public void debug(final Marker marker, final String format, final Object arg1, final Object arg2) {
        log.debug(marker, addContextLogging(format), arg1, arg2);
    }

    @Override
    public void debug(final Marker marker, final String format, final Object arg) {
        log.debug(marker, addContextLogging(format), arg);
    }

    @Override
    public void debug(final Marker marker, final String format, final Object[] argArray) {
        log.debug(marker, addContextLogging(format), argArray);
    }

    @Override
    public void debug(final Marker marker, final String msg, final Throwable t) {
        log.debug(marker, addContextLogging(msg), t);
    }

    @Override
    public void debug(final Marker marker, final String msg) {
        log.debug(marker, addContextLogging(msg));
    }

    @Override
    public void debug(final String format, final Object arg1, final Object arg2) {
        log.debug(addContextLogging(format), arg1, arg2);
    }

    @Override
    public void debug(final String format, final Object arg) {
        log.debug(addContextLogging(format), arg);
    }

    @Override
    public void debug(final String format, final Object[] argArray) {
        log.debug(addContextLogging(format), argArray);
    }

    @Override
    public void debug(final String msg, final Throwable t) {
        log.debug(addContextLogging(msg), t);
    }

    @Override
    public void debug(final String msg) {
        log.debug(addContextLogging(msg));
    }

    @Override
    public void error(final Marker marker, final String format, final Object arg1, final Object arg2) {
        log.error(marker, addContextLogging(format), arg1, arg2);
    }

    @Override
    public void error(final Marker marker, final String format, final Object arg) {
        log.error(marker, addContextLogging(format), arg);
    }

    @Override
    public void error(final Marker marker, final String format, final Object[] argArray) {
        log.error(marker, addContextLogging(format), argArray);
    }

    @Override
    public void error(final Marker marker, final String msg, final Throwable t) {
        log.error(marker, addContextLogging(msg), t);
    }

    @Override
    public void error(final Marker marker, final String msg) {
        log.error(marker, addContextLogging(msg));
    }

    @Override
    public void error(final String format, final Object arg1, final Object arg2) {
        log.error(addContextLogging(format), arg1, arg2);
    }

    @Override
    public void error(final String format, final Object arg) {
        log.error(addContextLogging(format), arg);
    }

    @Override
    public void error(final String format, final Object[] argArray) {
        log.error(addContextLogging(format), argArray);
    }

    @Override
    public void error(final String msg, final Throwable t) {
        log.error(addContextLogging(msg), t);
    }

    @Override
    public void error(final String msg) {
        log.error(addContextLogging(msg));
    }

    @Override
    public String getName() {
        return log.getName();
    }

    @Override
    public void info(final Marker marker, final String format, final Object arg1, final Object arg2) {
        log.info(marker, addContextLogging(format), arg1, arg2);
    }

    @Override
    public void info(final Marker marker, final String format, final Object arg) {
        log.info(marker, addContextLogging(format), arg);
    }

    @Override
    public void info(final Marker marker, final String format, final Object[] argArray) {
        log.info(marker, addContextLogging(format), argArray);
    }

    @Override
    public void info(final Marker marker, final String msg, final Throwable t) {
        log.info(marker, addContextLogging(msg), t);
    }

    @Override
    public void info(final Marker marker, final String msg) {
        log.info(marker, addContextLogging(msg));
    }

    @Override
    public void info(final String format, final Object arg1, final Object arg2) {
        log.info(addContextLogging(format), arg1, arg2);
    }

    @Override
    public void info(final String format, final Object arg) {
        log.info(addContextLogging(format), arg);
    }

    @Override
    public void info(final String format, final Object[] argArray) {
        log.info(addContextLogging(format), argArray);
    }

    @Override
    public void info(final String msg, final Throwable t) {
        log.info(addContextLogging(msg), t);
    }

    @Override
    public void info(final String msg) {
        log.info(addContextLogging(msg));
    }

    @Override
    public boolean isDebugEnabled() {
        return log.isDebugEnabled();
    }

    @Override
    public boolean isDebugEnabled(final Marker marker) {
        return log.isDebugEnabled(marker);
    }

    @Override
    public boolean isErrorEnabled() {
        return log.isErrorEnabled();
    }

    @Override
    public boolean isErrorEnabled(final Marker marker) {
        return log.isErrorEnabled(marker);
    }

    @Override
    public boolean isInfoEnabled() {
        return log.isInfoEnabled();
    }

    @Override
    public boolean isInfoEnabled(final Marker marker) {
        return log.isInfoEnabled(marker);
    }

    @Override
    public boolean isTraceEnabled() {
        return log.isTraceEnabled();
    }

    @Override
    public boolean isTraceEnabled(final Marker marker) {
        return log.isTraceEnabled(marker);
    }

    @Override
    public boolean isWarnEnabled() {
        return log.isWarnEnabled();
    }

    @Override
    public boolean isWarnEnabled(final Marker marker) {
        return log.isWarnEnabled(marker);
    }

    @Override
    public void trace(final Marker marker, final String format, final Object arg1, final Object arg2) {
        log.trace(marker, addContextLogging(format), arg1, arg2);
    }

    @Override
    public void trace(final Marker marker, final String format, final Object arg) {
        log.trace(marker, addContextLogging(format), arg);
    }

    @Override
    public void trace(final Marker marker, final String format, final Object[] argArray) {
        log.trace(marker, addContextLogging(format), argArray);
    }

    @Override
    public void trace(final Marker marker, final String msg, final Throwable t) {
        log.trace(marker, addContextLogging(msg), t);
    }

    @Override
    public void trace(final Marker marker, final String msg) {
        log.trace(marker, addContextLogging(msg));
    }

    @Override
    public void trace(final String format, final Object arg1, final Object arg2) {
        log.trace(addContextLogging(format), arg1, arg2);
    }

    @Override
    public void trace(final String format, final Object arg) {
        log.trace(addContextLogging(format), arg);
    }

    @Override
    public void trace(final String format, final Object[] argArray) {
        log.trace(addContextLogging(format), argArray);
    }

    @Override
    public void trace(final String msg, final Throwable t) {
        log.trace(addContextLogging(msg), t);
    }

    @Override
    public void trace(final String msg) {
        log.trace(addContextLogging(msg));
    }

    @Override
    public void warn(final Marker marker, final String format, final Object arg1, final Object arg2) {
        log.warn(marker, addContextLogging(format), arg1, arg2);
    }

    @Override
    public void warn(final Marker marker, final String format, final Object arg) {
        log.warn(marker, addContextLogging(format), arg);
    }

    @Override
    public void warn(final Marker marker, final String format, final Object[] argArray) {
        log.warn(marker, addContextLogging(format), argArray);
    }

    @Override
    public void warn(final Marker marker, final String msg, final Throwable t) {
        log.warn(marker, addContextLogging(msg), t);
    }

    @Override
    public void warn(final Marker marker, final String msg) {
        log.warn(marker, addContextLogging(msg));
    }

    @Override
    public void warn(final String format, final Object arg1, final Object arg2) {
        log.warn(addContextLogging(format), arg1, arg2);
    }

    @Override
    public void warn(final String format, final Object arg) {
        log.warn(addContextLogging(format), arg);
    }

    @Override
    public void warn(final String format, final Object[] argArray) {
        log.warn(addContextLogging(format), argArray);
    }

    @Override
    public void warn(final String msg, final Throwable t) {
        log.warn(addContextLogging(msg), t);
    }

    @Override
    public void warn(final String msg) {
        log.warn(addContextLogging(msg));
    }

    /* Private methods */
    /**
     * @param msg
     *            het oorspronkelijk bericht, of null
     * @return logging context + oorspronkelijke bericht, of null als msg null is
     */
    private String addContextLogging(final String msg) {
        if (msg == null) {
            return null;
        }
        String result;
        if (!LoggingContext.isKlaarVoorGebruik()) {
            result = msg;
        } else {
            result = new StringBuilder().append(LoggingContext.getLogPrefix()).append(" ").append(msg).toString();
        }
        return result;
    }
}
