/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

//CHECKSTYLE:OFF - Externe code
/*
 * JBoss, Home of Professional Open Source.
 *
 * Copyright 2010 Red Hat, Inc.
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

import java.text.MessageFormat;

final class Slf4jLogger extends Logger {

    private static final long serialVersionUID = 8685757928087758380L;

    private final org.slf4j.Logger logger;

    Slf4jLogger(final String name, final org.slf4j.Logger logger) {
        super(name);
        this.logger = logger;
    }

    @Override
    public boolean isEnabled(final Level level) {
        if (level != null) {
            switch (level) {
                case FATAL:
                    return logger.isErrorEnabled();
                case ERROR:
                    return logger.isErrorEnabled();
                case WARN:
                    return logger.isWarnEnabled();
                case INFO:
                    return logger.isInfoEnabled();
                case DEBUG:
                    return logger.isDebugEnabled();
                case TRACE:
                    return logger.isTraceEnabled();
            }
        }
        return true;
    }

    @Override
    protected void doLog(
            final Level level,
            final String loggerClassName,
            final Object message,
            final Object[] parameters,
            final Throwable thrown) {
        if (isEnabled(level)) {
            try {
                final String text =
                        parameters == null || parameters.length == 0 ? String.valueOf(message) : MessageFormat
                                .format(String.valueOf(message), parameters);
                switch (level) {
                    case FATAL:
                    case ERROR:
                        logger.error(text, thrown);
                        return;
                    case WARN:
                        logger.warn(text, thrown);
                        return;
                    case INFO:
                        logger.info(text, thrown);
                        return;
                    case DEBUG:
                        logger.debug(text, thrown);
                        return;
                    case TRACE:
                        logger.trace(text, thrown);
                        return;
                }
            } catch (final Throwable ignored) {
            }
        }
    }

    @Override
    protected void doLogf(
            final Level level,
            final String loggerClassName,
            final String format,
            final Object[] parameters,
            final Throwable thrown) {
        if (isEnabled(level)) {
            try {
                final String text = parameters == null ? String.format(format) : String.format(format, parameters);
                switch (level) {
                    case FATAL:
                    case ERROR:
                        logger.error(text, thrown);
                        return;
                    case WARN:
                        logger.warn(text, thrown);
                        return;
                    case INFO:
                        logger.info(text, thrown);
                        return;
                    case DEBUG:
                        logger.debug(text, thrown);
                        return;
                    case TRACE:
                        logger.trace(text, thrown);
                        return;
                }
            } catch (final Throwable ignored) {
            }
        }
    }
}
