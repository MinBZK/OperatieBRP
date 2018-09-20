/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

//CHECKSTYLE:OFF - Externe code
/*
 * JBoss, Home of Professional Open Source.
 *
 * Copyright 2011 Red Hat, Inc.
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

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.logging.LogManager;

final class LoggerProviders {
    static final String LOGGING_PROVIDER_KEY = "org.jboss.logging.provider";

    static final LoggerProvider PROVIDER = find();

    private static LoggerProvider find() {
        final LoggerProvider result = findProvider();
        // Log a debug message indicating which logger we are using
        result.getLogger("org.jboss.logging").debugf("Logging Provider: %s", result.getClass().getName());
        return result;
    }

    private static LoggerProvider findProvider() {
        // Since the impl classes refer to the back-end frameworks directly, if this classloader can't find the target
        // log classes, then it doesn't really matter if they're possibly available from the TCCL because we won't be
        // able to find it anyway
        final ClassLoader cl = LoggerProviders.class.getClassLoader();
        try {
            // Check the system property
            final String loggerProvider = AccessController.doPrivileged(new PrivilegedAction<String>() {
                @Override
                public String run() {
                    return System.getProperty(LOGGING_PROVIDER_KEY);
                }
            });
            if (loggerProvider != null) {
                if ("jboss".equalsIgnoreCase(loggerProvider)) {
                    return tryJBossLogManager(cl);
                } else if ("jdk".equalsIgnoreCase(loggerProvider)) {
                    return tryJDK();
                } else if ("log4j".equalsIgnoreCase(loggerProvider)) {
                    return tryLog4j(cl);
                } else if ("slf4j".equalsIgnoreCase(loggerProvider)) {
                    return trySlf4j();
                }
            }
        } catch (final Throwable t) {
        }
        try {
            return tryJBossLogManager(cl);
        } catch (final Throwable t) {
            // nope...
        }
        try {
            return tryLog4j(cl);
        } catch (final Throwable t) {
            // nope...
        }
        try {
            // only use slf4j if Logback is in use
            Class.forName("ch.qos.logback.classic.Logger", false, cl);
            return trySlf4j();
        } catch (final Throwable t) {
            // nope...
        }
        return tryJDK();
    }

    private static JDKLoggerProvider tryJDK() {
        return new JDKLoggerProvider();
    }

    private static LoggerProvider trySlf4j() {
        return new Slf4jLoggerProvider();
    }

    private static LoggerProvider tryLog4j(final ClassLoader cl) throws ClassNotFoundException {
        Class.forName("org.apache.log4j.LogManager", true, cl);
        // JBLOGGING-65 - slf4j can disguise itself as log4j. Test for a class that slf4j doesn't provide.
        Class.forName("org.apache.log4j.Hierarchy", true, cl);
        return new Log4jLoggerProvider();
    }

    private static LoggerProvider tryJBossLogManager(final ClassLoader cl) throws ClassNotFoundException {
        final Class<? extends LogManager> logManagerClass = LogManager.getLogManager().getClass();
        if (logManagerClass == Class.forName("org.jboss.logmanager.LogManager", false, cl)
                && Class.forName("org.jboss.logmanager.Logger$AttachmentKey", true, cl).getClassLoader() == logManagerClass
                        .getClassLoader()) {
            return new JBossLogManagerProvider();
        }
        throw new IllegalStateException();
    }

    private LoggerProviders() {
    }
}
