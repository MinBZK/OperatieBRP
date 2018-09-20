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

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Map;

import org.jboss.logmanager.LogContext;
import org.jboss.logmanager.Logger.AttachmentKey;
import org.jboss.logmanager.MDC;
import org.jboss.logmanager.NDC;

final class JBossLogManagerProvider implements LoggerProvider {

    private static final AttachmentKey<Logger> KEY = new AttachmentKey<Logger>();

    @Override
    public Logger getLogger(final String name) {
        final SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            return AccessController.doPrivileged(new PrivilegedAction<Logger>() {
                @Override
                public Logger run() {
                    return doGetLogger(name);
                }
            });
        } else {
            return doGetLogger(name);
        }
    }

    private static Logger doGetLogger(final String name) {
        Logger l = LogContext.getLogContext().getAttachment(name, KEY);
        if (l != null) {
            return l;
        }
        final org.jboss.logmanager.Logger logger = org.jboss.logmanager.Logger.getLogger(name);
        l = new JBossLogManagerLogger(name, logger);
        final Logger a = logger.attachIfAbsent(KEY, l);
        if (a == null) {
            return l;
        } else {
            return a;
        }
    }

    @Override
    public Object putMdc(final String key, final Object value) {
        return MDC.put(key, String.valueOf(value));
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
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Map<String, Object> getMdcMap() {
        // we can re-define the erasure of this map because MDC does not make further use of the copy
        return (Map) MDC.copy();
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
    public String popNdc() {
        return NDC.pop();
    }

    @Override
    public String peekNdc() {
        return NDC.get();
    }

    @Override
    public void pushNdc(final String message) {
        NDC.push(message);
    }

    @Override
    public void setNdcMaxDepth(final int maxDepth) {
        NDC.trimTo(maxDepth);
    }
}
