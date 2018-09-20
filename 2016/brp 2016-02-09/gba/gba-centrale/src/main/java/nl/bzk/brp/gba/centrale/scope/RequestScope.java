/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.centrale.scope;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

/**
 * Request scope om bijhouding te kunnen doen.
 */
public final class RequestScope implements Scope {

    private final ThreadLocal<Map<String, Object>> objectMap = new ThreadLocal<Map<String, Object>>() {
        @Override
        protected Map<String, Object> initialValue() {
            return Collections.synchronizedMap(new HashMap<String, Object>());
        }
    };

    @Override
    public Object get(final String name, final ObjectFactory<?> objectFactory) {
        if (!objectMap.get().containsKey(name)) {
            objectMap.get().put(name, objectFactory.getObject());
        }
        return objectMap.get().get(name);

    }

    @Override
    public Object remove(final String name) {
        return objectMap.get().remove(name);
    }

    @Override
    public void registerDestructionCallback(final String name, final Runnable callback) {
        // do nothing
    }

    @Override
    public Object resolveContextualObject(final String key) {
        return null;
    }

    @Override
    public String getConversationId() {
        return "RequestScope";
    }

    /**
     * reset the scope.
     */
    public void reset() {
        objectMap.get().clear();
    }
}
