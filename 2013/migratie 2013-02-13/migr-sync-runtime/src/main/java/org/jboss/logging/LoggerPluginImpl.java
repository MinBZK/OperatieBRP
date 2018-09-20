/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jboss.logging;

/**
 * LoggerPlugin als bridge naar de 2.x versies.
 */
public final class LoggerPluginImpl implements LoggerPlugin {

    private final Logger logger;

    /**
     * Constructor.
     * 
     * @param logger
     *            logger
     */
    protected LoggerPluginImpl(final Logger logger) {
        this.logger = logger;
    }

    @Override
    public void init(final String name) {
        // Don't know what to do here :-)
    }

    @Override
    public boolean isTraceEnabled() {
        return logger.isTraceEnabled();
    }

    @Override
    public void trace(final Object message) {
        logger.trace(message);

    }

    @Override
    public void trace(final Object message, final Throwable t) {
        logger.trace(message, t);
    }

    @Override
    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();

    }

    @Override
    public void debug(final Object message) {
        logger.debug(message);

    }

    @Override
    public void debug(final Object message, final Throwable t) {
        logger.debug(message, t);
    }

    @Override
    public boolean isInfoEnabled() {
        return logger.isInfoEnabled();
    }

    @Override
    public void info(final Object message) {
        logger.info(message);
    }

    @Override
    public void info(final Object message, final Throwable t) {
        logger.info(message, t);
    }

    @Override
    public void warn(final Object message) {
        logger.warn(message);
    }

    @Override
    public void warn(final Object message, final Throwable t) {
        logger.warn(message, t);
    }

    @Override
    public void error(final Object message) {
        logger.error(message);
    }

    @Override
    public void error(final Object message, final Throwable t) {
        logger.error(message, t);
    }

    @Override
    public void fatal(final Object message) {
        logger.fatal(message);
    }

    @Override
    public void fatal(final Object message, final Throwable t) {
        logger.fatal(message, t);
    }

}
