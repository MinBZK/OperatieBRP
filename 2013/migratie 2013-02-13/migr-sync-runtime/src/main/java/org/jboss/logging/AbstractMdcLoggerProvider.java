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

import java.util.HashMap;
import java.util.Map;

abstract class AbstractMdcLoggerProvider extends AbstractLoggerProvider {

    private final ThreadLocal<Map<String, Object>> mdcMap = new ThreadLocal<Map<String, Object>>();

    public Object getMdc(final String key) {
        return mdcMap.get() == null ? null : mdcMap.get().get(key);
    }

    public Map<String, Object> getMdcMap() {
        return mdcMap.get();
    }

    public Object putMdc(final String key, final Object value) {
        Map<String, Object> map = mdcMap.get();
        if (map == null) {
            map = new HashMap<String, Object>();
            mdcMap.set(map);
        }
        return map.put(key, value);
    }

    public void removeMdc(final String key) {
        final Map<String, Object> map = mdcMap.get();
        if (map == null) {
            return;
        }
        map.remove(key);
    }
}
