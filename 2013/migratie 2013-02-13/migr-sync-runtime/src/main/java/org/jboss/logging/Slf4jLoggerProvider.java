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

import java.util.Map;

import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.slf4j.spi.LocationAwareLogger;

final class Slf4jLoggerProvider extends AbstractLoggerProvider implements LoggerProvider {

    @Override
    public Logger getLogger(final String name) {
        final org.slf4j.Logger l = LoggerFactory.getLogger(name);
        try {
            return new Slf4jLocationAwareLogger(name, (LocationAwareLogger) l);
        } catch (final Throwable ignored) {
        }
        return new Slf4jLogger(name, l);
    }

    @Override
    public Object putMdc(final String key, final Object value) {
        try {
            return MDC.get(key);
        } finally {
            if (value == null) {
                MDC.remove(key);
            } else {
                MDC.put(key, String.valueOf(value));
            }
        }
    }

    @Override
    public Object getMdc(final String key) {
        return MDC.get(key);
    }

    @Override
    public void removeMdc(final String key) {
        MDC.remove(key);
    }

    @Override
    @SuppressWarnings({ "unchecked" })
    public Map<String, Object> getMdcMap() {
        return MDC.getCopyOfContextMap();
    }
}
