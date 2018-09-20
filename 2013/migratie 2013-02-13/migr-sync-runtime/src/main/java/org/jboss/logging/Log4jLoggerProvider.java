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

import org.apache.log4j.MDC;
import org.apache.log4j.NDC;

final class Log4jLoggerProvider implements LoggerProvider {

    @Override
    public Logger getLogger(final String name) {
        return new Log4jLogger("".equals(name) ? "ROOT" : name);
    }

    @Override
    public Object getMdc(final String key) {
        return MDC.get(key);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Object> getMdcMap() {
        return MDC.getContext();
    }

    @Override
    public Object putMdc(final String key, final Object val) {
        try {
            return MDC.get(key);
        } finally {
            MDC.put(key, val);
        }
    }

    @Override
    public void removeMdc(final String key) {
        MDC.remove(key);
    }

    @Override
    public void clearNdc() {
        NDC.clear();
    }

    @Override
    public String getNdc() {
        return NDC.get();
    }

    @Override
    public int getNdcDepth() {
        return NDC.getDepth();
    }

    @Override
    public String peekNdc() {
        return NDC.peek();
    }

    @Override
    public String popNdc() {
        return NDC.pop();
    }

    @Override
    public void pushNdc(final String message) {
        NDC.push(message);
    }

    @Override
    public void setNdcMaxDepth(final int maxDepth) {
        NDC.setMaxDepth(maxDepth);
    }
}
