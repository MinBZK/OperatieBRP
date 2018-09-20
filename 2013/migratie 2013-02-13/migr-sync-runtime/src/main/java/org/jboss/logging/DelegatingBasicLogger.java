/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

//CHECKSTYLE:OFF - Externe code
/*
 * JBoss, Home of Professional Open Source.
 *
 * Copyright 2011 Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jboss.logging;

import java.io.Serializable;

/**
 * A serializable, delegating basic logger instance.
 * 
 * @author <a href="mailto:david.lloyd@redhat.com">David M. Lloyd</a>
 */
public class DelegatingBasicLogger implements BasicLogger, Serializable {

    private static final long serialVersionUID = -5774903162389601853L;

    /**
     * The cached logger class name.
     */
    private static final String FQCN = DelegatingBasicLogger.class.getName();

    /**
     * The delegate logger.
     */
    protected final Logger log;

    /**
     * Construct a new instance.
     * 
     * @param log
     *            the logger to which calls should be delegated
     */
    public DelegatingBasicLogger(final Logger log) {
        this.log = log;
    }

    @Override
    public boolean isTraceEnabled() {
        return log.isTraceEnabled();
    }

    @Override
    public void trace(final Object message) {
        log.trace(FQCN, message, null);
    }

    @Override
    public void trace(final Object message, final Throwable t) {
        log.trace(FQCN, message, t);
    }

    @Override
    public void trace(final String loggerFqcn, final Object message, final Throwable t) {
        log.trace(loggerFqcn, message, t);
    }

    @Override
    public void trace(final String loggerFqcn, final Object message, final Object[] params, final Throwable t) {
        log.trace(loggerFqcn, message, params, t);
    }

    @Override
    public void tracev(final String format, final Object... params) {
        log.logv(FQCN, Logger.Level.TRACE, null, format, params);
    }

    @Override
    public void tracev(final String format, final Object param1) {
        log.logv(FQCN, Logger.Level.TRACE, null, format, param1);
    }

    @Override
    public void tracev(final String format, final Object param1, final Object param2) {
        log.logv(FQCN, Logger.Level.TRACE, null, format, param1, param2);
    }

    @Override
    public void tracev(final String format, final Object param1, final Object param2, final Object param3) {
        log.logv(FQCN, Logger.Level.TRACE, null, format, param1, param2, param3);
    }

    @Override
    public void tracev(final Throwable t, final String format, final Object... params) {
        log.logv(FQCN, Logger.Level.TRACE, t, format, params);
    }

    @Override
    public void tracev(final Throwable t, final String format, final Object param1) {
        log.logv(FQCN, Logger.Level.TRACE, t, format, param1);
    }

    @Override
    public void tracev(final Throwable t, final String format, final Object param1, final Object param2) {
        log.logv(FQCN, Logger.Level.TRACE, t, format, param1, param2);
    }

    @Override
    public void tracev(
            final Throwable t,
            final String format,
            final Object param1,
            final Object param2,
            final Object param3) {
        log.logv(FQCN, Logger.Level.TRACE, t, format, param1, param2, param3);
    }

    @Override
    public void tracef(final String format, final Object... params) {
        log.logf(FQCN, Logger.Level.TRACE, null, format, params);
    }

    @Override
    public void tracef(final String format, final Object param1) {
        log.logf(FQCN, Logger.Level.TRACE, null, format, param1);
    }

    @Override
    public void tracef(final String format, final Object param1, final Object param2) {
        log.logf(FQCN, Logger.Level.TRACE, null, format, param1, param2);
    }

    @Override
    public void tracef(final String format, final Object param1, final Object param2, final Object param3) {
        log.logf(FQCN, Logger.Level.TRACE, null, format, param1, param2, param3);
    }

    @Override
    public void tracef(final Throwable t, final String format, final Object... params) {
        log.logf(FQCN, Logger.Level.TRACE, t, format, params);
    }

    @Override
    public void tracef(final Throwable t, final String format, final Object param1) {
        log.logf(FQCN, Logger.Level.TRACE, t, format, param1);
    }

    @Override
    public void tracef(final Throwable t, final String format, final Object param1, final Object param2) {
        log.logf(FQCN, Logger.Level.TRACE, t, format, param1, param2);
    }

    @Override
    public void tracef(
            final Throwable t,
            final String format,
            final Object param1,
            final Object param2,
            final Object param3) {
        log.logf(FQCN, Logger.Level.TRACE, t, format, param1, param2, param3);
    }

    @Override
    public boolean isDebugEnabled() {
        return log.isDebugEnabled();
    }

    @Override
    public void debug(final Object message) {
        log.debug(FQCN, message, null);
    }

    @Override
    public void debug(final Object message, final Throwable t) {
        log.debug(FQCN, message, t);
    }

    @Override
    public void debug(final String loggerFqcn, final Object message, final Throwable t) {
        log.debug(loggerFqcn, message, t);
    }

    @Override
    public void debug(final String loggerFqcn, final Object message, final Object[] params, final Throwable t) {
        log.debug(loggerFqcn, message, params, t);
    }

    @Override
    public void debugv(final String format, final Object... params) {
        log.logv(FQCN, Logger.Level.DEBUG, null, format, params);
    }

    @Override
    public void debugv(final String format, final Object param1) {
        log.logv(FQCN, Logger.Level.DEBUG, null, format, param1);
    }

    @Override
    public void debugv(final String format, final Object param1, final Object param2) {
        log.logv(FQCN, Logger.Level.DEBUG, null, format, param1, param2);
    }

    @Override
    public void debugv(final String format, final Object param1, final Object param2, final Object param3) {
        log.logv(FQCN, Logger.Level.DEBUG, null, format, param1, param2, param3);
    }

    @Override
    public void debugv(final Throwable t, final String format, final Object... params) {
        log.logv(FQCN, Logger.Level.DEBUG, t, format, params);
    }

    @Override
    public void debugv(final Throwable t, final String format, final Object param1) {
        log.logv(FQCN, Logger.Level.DEBUG, t, format, param1);
    }

    @Override
    public void debugv(final Throwable t, final String format, final Object param1, final Object param2) {
        log.logv(FQCN, Logger.Level.DEBUG, t, format, param1, param2);
    }

    @Override
    public void debugv(
            final Throwable t,
            final String format,
            final Object param1,
            final Object param2,
            final Object param3) {
        log.logv(FQCN, Logger.Level.DEBUG, t, format, param1, param2, param3);
    }

    @Override
    public void debugf(final String format, final Object... params) {
        log.logf(FQCN, Logger.Level.DEBUG, null, format, params);
    }

    @Override
    public void debugf(final String format, final Object param1) {
        log.logf(FQCN, Logger.Level.DEBUG, null, format, param1);
    }

    @Override
    public void debugf(final String format, final Object param1, final Object param2) {
        log.logf(FQCN, Logger.Level.DEBUG, null, format, param1, param2);
    }

    @Override
    public void debugf(final String format, final Object param1, final Object param2, final Object param3) {
        log.logf(FQCN, Logger.Level.DEBUG, null, format, param1, param2, param3);
    }

    @Override
    public void debugf(final Throwable t, final String format, final Object... params) {
        log.logf(FQCN, Logger.Level.DEBUG, t, format, params);
    }

    @Override
    public void debugf(final Throwable t, final String format, final Object param1) {
        log.logf(FQCN, Logger.Level.DEBUG, t, format, param1);
    }

    @Override
    public void debugf(final Throwable t, final String format, final Object param1, final Object param2) {
        log.logf(FQCN, Logger.Level.DEBUG, t, format, param1, param2);
    }

    @Override
    public void debugf(
            final Throwable t,
            final String format,
            final Object param1,
            final Object param2,
            final Object param3) {
        log.logf(FQCN, Logger.Level.DEBUG, t, format, param1, param2, param3);
    }

    @Override
    public boolean isInfoEnabled() {
        return log.isInfoEnabled();
    }

    @Override
    public void info(final Object message) {
        log.info(FQCN, message, null);
    }

    @Override
    public void info(final Object message, final Throwable t) {
        log.info(FQCN, message, t);
    }

    @Override
    public void info(final String loggerFqcn, final Object message, final Throwable t) {
        log.info(loggerFqcn, message, t);
    }

    @Override
    public void info(final String loggerFqcn, final Object message, final Object[] params, final Throwable t) {
        log.info(loggerFqcn, message, params, t);
    }

    @Override
    public void infov(final String format, final Object... params) {
        log.logv(FQCN, Logger.Level.INFO, null, format, params);
    }

    @Override
    public void infov(final String format, final Object param1) {
        log.logv(FQCN, Logger.Level.INFO, null, format, param1);
    }

    @Override
    public void infov(final String format, final Object param1, final Object param2) {
        log.logv(FQCN, Logger.Level.INFO, null, format, param1, param2);
    }

    @Override
    public void infov(final String format, final Object param1, final Object param2, final Object param3) {
        log.logv(FQCN, Logger.Level.INFO, null, format, param1, param2, param3);
    }

    @Override
    public void infov(final Throwable t, final String format, final Object... params) {
        log.logv(FQCN, Logger.Level.INFO, t, format, params);
    }

    @Override
    public void infov(final Throwable t, final String format, final Object param1) {
        log.logv(FQCN, Logger.Level.INFO, t, format, param1);
    }

    @Override
    public void infov(final Throwable t, final String format, final Object param1, final Object param2) {
        log.logv(FQCN, Logger.Level.INFO, t, format, param1, param2);
    }

    @Override
    public void infov(
            final Throwable t,
            final String format,
            final Object param1,
            final Object param2,
            final Object param3) {
        log.logv(FQCN, Logger.Level.INFO, t, format, param1, param2, param3);
    }

    @Override
    public void infof(final String format, final Object... params) {
        log.logf(FQCN, Logger.Level.INFO, null, format, params);
    }

    @Override
    public void infof(final String format, final Object param1) {
        log.logf(FQCN, Logger.Level.INFO, null, format, param1);
    }

    @Override
    public void infof(final String format, final Object param1, final Object param2) {
        log.logf(FQCN, Logger.Level.INFO, null, format, param1, param2);
    }

    @Override
    public void infof(final String format, final Object param1, final Object param2, final Object param3) {
        log.logf(FQCN, Logger.Level.INFO, null, format, param1, param2, param3);
    }

    @Override
    public void infof(final Throwable t, final String format, final Object... params) {
        log.logf(FQCN, Logger.Level.INFO, t, format, params);
    }

    @Override
    public void infof(final Throwable t, final String format, final Object param1) {
        log.logf(FQCN, Logger.Level.INFO, t, format, param1);
    }

    @Override
    public void infof(final Throwable t, final String format, final Object param1, final Object param2) {
        log.logf(FQCN, Logger.Level.INFO, t, format, param1, param2);
    }

    @Override
    public void infof(
            final Throwable t,
            final String format,
            final Object param1,
            final Object param2,
            final Object param3) {
        log.logf(FQCN, Logger.Level.INFO, t, format, param1, param2, param3);
    }

    @Override
    public void warn(final Object message) {
        log.warn(FQCN, message, null);
    }

    @Override
    public void warn(final Object message, final Throwable t) {
        log.warn(FQCN, message, t);
    }

    @Override
    public void warn(final String loggerFqcn, final Object message, final Throwable t) {
        log.warn(loggerFqcn, message, t);
    }

    @Override
    public void warn(final String loggerFqcn, final Object message, final Object[] params, final Throwable t) {
        log.warn(loggerFqcn, message, params, t);
    }

    @Override
    public void warnv(final String format, final Object... params) {
        log.logv(FQCN, Logger.Level.WARN, null, format, params);
    }

    @Override
    public void warnv(final String format, final Object param1) {
        log.logv(FQCN, Logger.Level.WARN, null, format, param1);
    }

    @Override
    public void warnv(final String format, final Object param1, final Object param2) {
        log.logv(FQCN, Logger.Level.WARN, null, format, param1, param2);
    }

    @Override
    public void warnv(final String format, final Object param1, final Object param2, final Object param3) {
        log.logv(FQCN, Logger.Level.WARN, null, format, param1, param2, param3);
    }

    @Override
    public void warnv(final Throwable t, final String format, final Object... params) {
        log.logv(FQCN, Logger.Level.WARN, t, format, params);
    }

    @Override
    public void warnv(final Throwable t, final String format, final Object param1) {
        log.logv(FQCN, Logger.Level.WARN, t, format, param1);
    }

    @Override
    public void warnv(final Throwable t, final String format, final Object param1, final Object param2) {
        log.logv(FQCN, Logger.Level.WARN, t, format, param1, param2);
    }

    @Override
    public void warnv(
            final Throwable t,
            final String format,
            final Object param1,
            final Object param2,
            final Object param3) {
        log.logv(FQCN, Logger.Level.WARN, t, format, param1, param2, param3);
    }

    @Override
    public void warnf(final String format, final Object... params) {
        log.logf(FQCN, Logger.Level.WARN, null, format, params);
    }

    @Override
    public void warnf(final String format, final Object param1) {
        log.logf(FQCN, Logger.Level.WARN, null, format, param1);
    }

    @Override
    public void warnf(final String format, final Object param1, final Object param2) {
        log.logf(FQCN, Logger.Level.WARN, null, format, param1, param2);
    }

    @Override
    public void warnf(final String format, final Object param1, final Object param2, final Object param3) {
        log.logf(FQCN, Logger.Level.WARN, null, format, param1, param2, param3);
    }

    @Override
    public void warnf(final Throwable t, final String format, final Object... params) {
        log.logf(FQCN, Logger.Level.WARN, t, format, params);
    }

    @Override
    public void warnf(final Throwable t, final String format, final Object param1) {
        log.logf(FQCN, Logger.Level.WARN, t, format, param1);
    }

    @Override
    public void warnf(final Throwable t, final String format, final Object param1, final Object param2) {
        log.logf(FQCN, Logger.Level.WARN, t, format, param1, param2);
    }

    @Override
    public void warnf(
            final Throwable t,
            final String format,
            final Object param1,
            final Object param2,
            final Object param3) {
        log.logf(FQCN, Logger.Level.WARN, t, format, param1, param2, param3);
    }

    @Override
    public void error(final Object message) {
        log.error(FQCN, message, null);
    }

    @Override
    public void error(final Object message, final Throwable t) {
        log.error(FQCN, message, t);
    }

    @Override
    public void error(final String loggerFqcn, final Object message, final Throwable t) {
        log.error(loggerFqcn, message, t);
    }

    @Override
    public void error(final String loggerFqcn, final Object message, final Object[] params, final Throwable t) {
        log.error(loggerFqcn, message, params, t);
    }

    @Override
    public void errorv(final String format, final Object... params) {
        log.logv(FQCN, Logger.Level.ERROR, null, format, params);
    }

    @Override
    public void errorv(final String format, final Object param1) {
        log.logv(FQCN, Logger.Level.ERROR, null, format, param1);
    }

    @Override
    public void errorv(final String format, final Object param1, final Object param2) {
        log.logv(FQCN, Logger.Level.ERROR, null, format, param1, param2);
    }

    @Override
    public void errorv(final String format, final Object param1, final Object param2, final Object param3) {
        log.logv(FQCN, Logger.Level.ERROR, null, format, param1, param2, param3);
    }

    @Override
    public void errorv(final Throwable t, final String format, final Object... params) {
        log.logv(FQCN, Logger.Level.ERROR, t, format, params);
    }

    @Override
    public void errorv(final Throwable t, final String format, final Object param1) {
        log.logv(FQCN, Logger.Level.ERROR, t, format, param1);
    }

    @Override
    public void errorv(final Throwable t, final String format, final Object param1, final Object param2) {
        log.logv(FQCN, Logger.Level.ERROR, t, format, param1, param2);
    }

    @Override
    public void errorv(
            final Throwable t,
            final String format,
            final Object param1,
            final Object param2,
            final Object param3) {
        log.logv(FQCN, Logger.Level.ERROR, t, format, param1, param2, param3);
    }

    @Override
    public void errorf(final String format, final Object... params) {
        log.logf(FQCN, Logger.Level.ERROR, null, format, params);
    }

    @Override
    public void errorf(final String format, final Object param1) {
        log.logf(FQCN, Logger.Level.ERROR, null, format, param1);
    }

    @Override
    public void errorf(final String format, final Object param1, final Object param2) {
        log.logf(FQCN, Logger.Level.ERROR, null, format, param1, param2);
    }

    @Override
    public void errorf(final String format, final Object param1, final Object param2, final Object param3) {
        log.logf(FQCN, Logger.Level.ERROR, null, format, param1, param2, param3);
    }

    @Override
    public void errorf(final Throwable t, final String format, final Object... params) {
        log.logf(FQCN, Logger.Level.ERROR, t, format, params);
    }

    @Override
    public void errorf(final Throwable t, final String format, final Object param1) {
        log.logf(FQCN, Logger.Level.ERROR, t, format, param1);
    }

    @Override
    public void errorf(final Throwable t, final String format, final Object param1, final Object param2) {
        log.logf(FQCN, Logger.Level.ERROR, t, format, param1, param2);
    }

    @Override
    public void errorf(
            final Throwable t,
            final String format,
            final Object param1,
            final Object param2,
            final Object param3) {
        log.logf(FQCN, Logger.Level.ERROR, t, format, param1, param2, param3);
    }

    @Override
    public void fatal(final Object message) {
        log.fatal(FQCN, message, null);
    }

    @Override
    public void fatal(final Object message, final Throwable t) {
        log.fatal(FQCN, message, t);
    }

    @Override
    public void fatal(final String loggerFqcn, final Object message, final Throwable t) {
        log.fatal(loggerFqcn, message, t);
    }

    @Override
    public void fatal(final String loggerFqcn, final Object message, final Object[] params, final Throwable t) {
        log.fatal(loggerFqcn, message, params, t);
    }

    @Override
    public void fatalv(final String format, final Object... params) {
        log.logv(FQCN, Logger.Level.FATAL, null, format, params);
    }

    @Override
    public void fatalv(final String format, final Object param1) {
        log.logv(FQCN, Logger.Level.FATAL, null, format, param1);
    }

    @Override
    public void fatalv(final String format, final Object param1, final Object param2) {
        log.logv(FQCN, Logger.Level.FATAL, null, format, param1, param2);
    }

    @Override
    public void fatalv(final String format, final Object param1, final Object param2, final Object param3) {
        log.logv(FQCN, Logger.Level.FATAL, null, format, param1, param2, param3);
    }

    @Override
    public void fatalv(final Throwable t, final String format, final Object... params) {
        log.logv(FQCN, Logger.Level.FATAL, t, format, params);
    }

    @Override
    public void fatalv(final Throwable t, final String format, final Object param1) {
        log.logv(FQCN, Logger.Level.FATAL, t, format, param1);
    }

    @Override
    public void fatalv(final Throwable t, final String format, final Object param1, final Object param2) {
        log.logv(FQCN, Logger.Level.FATAL, t, format, param1, param2);
    }

    @Override
    public void fatalv(
            final Throwable t,
            final String format,
            final Object param1,
            final Object param2,
            final Object param3) {
        log.logv(FQCN, Logger.Level.FATAL, t, format, param1, param2, param3);
    }

    @Override
    public void fatalf(final String format, final Object... params) {
        log.logf(FQCN, Logger.Level.FATAL, null, format, params);
    }

    @Override
    public void fatalf(final String format, final Object param1) {
        log.logf(FQCN, Logger.Level.FATAL, null, format, param1);
    }

    @Override
    public void fatalf(final String format, final Object param1, final Object param2) {
        log.logf(FQCN, Logger.Level.FATAL, null, format, param1, param2);
    }

    @Override
    public void fatalf(final String format, final Object param1, final Object param2, final Object param3) {
        log.logf(FQCN, Logger.Level.FATAL, null, format, param1, param2, param3);
    }

    @Override
    public void fatalf(final Throwable t, final String format, final Object... params) {
        log.logf(FQCN, Logger.Level.FATAL, t, format, params);
    }

    @Override
    public void fatalf(final Throwable t, final String format, final Object param1) {
        log.logf(FQCN, Logger.Level.FATAL, t, format, param1);
    }

    @Override
    public void fatalf(final Throwable t, final String format, final Object param1, final Object param2) {
        log.logf(FQCN, Logger.Level.FATAL, t, format, param1, param2);
    }

    @Override
    public void fatalf(
            final Throwable t,
            final String format,
            final Object param1,
            final Object param2,
            final Object param3) {
        log.logf(FQCN, Logger.Level.FATAL, t, format, param1, param2, param3);
    }

    @Override
    public void log(final Logger.Level level, final Object message) {
        log.log(FQCN, level, message, null, null);
    }

    @Override
    public void log(final Logger.Level level, final Object message, final Throwable t) {
        log.log(FQCN, level, message, null, t);
    }

    @Override
    public void log(final Logger.Level level, final String loggerFqcn, final Object message, final Throwable t) {
        log.log(level, loggerFqcn, message, t);
    }

    @Override
    public void log(
            final String loggerFqcn,
            final Logger.Level level,
            final Object message,
            final Object[] params,
            final Throwable t) {
        log.log(loggerFqcn, level, message, params, t);
    }

    @Override
    public void logv(final Logger.Level level, final String format, final Object... params) {
        log.logv(FQCN, level, null, format, params);
    }

    @Override
    public void logv(final Logger.Level level, final String format, final Object param1) {
        log.logv(FQCN, level, null, format, param1);
    }

    @Override
    public void logv(final Logger.Level level, final String format, final Object param1, final Object param2) {
        log.logv(FQCN, level, null, format, param1, param2);
    }

    @Override
    public void logv(
            final Logger.Level level,
            final String format,
            final Object param1,
            final Object param2,
            final Object param3) {
        log.logv(FQCN, level, null, format, param1, param2, param3);
    }

    @Override
    public void logv(final Logger.Level level, final Throwable t, final String format, final Object... params) {
        log.logv(FQCN, level, t, format, params);
    }

    @Override
    public void logv(final Logger.Level level, final Throwable t, final String format, final Object param1) {
        log.logv(FQCN, level, t, format, param1);
    }

    @Override
    public void logv(
            final Logger.Level level,
            final Throwable t,
            final String format,
            final Object param1,
            final Object param2) {
        log.logv(FQCN, level, t, format, param1, param2);
    }

    @Override
    public void logv(
            final Logger.Level level,
            final Throwable t,
            final String format,
            final Object param1,
            final Object param2,
            final Object param3) {
        log.logv(FQCN, level, t, format, param1, param2, param3);
    }

    @Override
    public void logv(
            final String loggerFqcn,
            final Logger.Level level,
            final Throwable t,
            final String format,
            final Object... params) {
        log.logv(loggerFqcn, level, t, format, params);
    }

    @Override
    public void logv(
            final String loggerFqcn,
            final Logger.Level level,
            final Throwable t,
            final String format,
            final Object param1) {
        log.logv(loggerFqcn, level, t, format, param1);
    }

    @Override
    public void logv(
            final String loggerFqcn,
            final Logger.Level level,
            final Throwable t,
            final String format,
            final Object param1,
            final Object param2) {
        log.logv(loggerFqcn, level, t, format, param1, param2);
    }

    @Override
    public void logv(
            final String loggerFqcn,
            final Logger.Level level,
            final Throwable t,
            final String format,
            final Object param1,
            final Object param2,
            final Object param3) {
        log.logv(loggerFqcn, level, t, format, param1, param2, param3);
    }

    @Override
    public void logf(final Logger.Level level, final String format, final Object... params) {
        log.logf(FQCN, level, null, format, params);
    }

    @Override
    public void logf(final Logger.Level level, final String format, final Object param1) {
        log.logf(FQCN, level, null, format, param1);
    }

    @Override
    public void logf(final Logger.Level level, final String format, final Object param1, final Object param2) {
        log.logf(FQCN, level, null, format, param1, param2);
    }

    @Override
    public void logf(
            final Logger.Level level,
            final String format,
            final Object param1,
            final Object param2,
            final Object param3) {
        log.logf(FQCN, level, null, format, param1, param2, param3);
    }

    @Override
    public void logf(final Logger.Level level, final Throwable t, final String format, final Object... params) {
        log.logf(FQCN, level, t, format, params);
    }

    @Override
    public void logf(final Logger.Level level, final Throwable t, final String format, final Object param1) {
        log.logf(FQCN, level, t, format, param1);
    }

    @Override
    public void logf(
            final Logger.Level level,
            final Throwable t,
            final String format,
            final Object param1,
            final Object param2) {
        log.logf(FQCN, level, t, format, param1, param2);
    }

    @Override
    public void logf(
            final Logger.Level level,
            final Throwable t,
            final String format,
            final Object param1,
            final Object param2,
            final Object param3) {
        log.logf(FQCN, level, t, format, param1, param2, param3);
    }

    @Override
    public void logf(
            final String loggerFqcn,
            final Logger.Level level,
            final Throwable t,
            final String format,
            final Object param1) {
        log.logf(loggerFqcn, level, t, format, param1);
    }

    @Override
    public void logf(
            final String loggerFqcn,
            final Logger.Level level,
            final Throwable t,
            final String format,
            final Object param1,
            final Object param2) {
        log.logf(loggerFqcn, level, t, format, param1, param2);
    }

    @Override
    public void logf(
            final String loggerFqcn,
            final Logger.Level level,
            final Throwable t,
            final String format,
            final Object param1,
            final Object param2,
            final Object param3) {
        log.logf(loggerFqcn, level, t, format, param1, param2, param3);
    }

    @Override
    public void logf(
            final String loggerFqcn,
            final Logger.Level level,
            final Throwable t,
            final String format,
            final Object... params) {
        log.logf(loggerFqcn, level, t, format, params);
    }

    @Override
    public boolean isEnabled(final Logger.Level level) {
        return log.isEnabled(level);
    }
}
