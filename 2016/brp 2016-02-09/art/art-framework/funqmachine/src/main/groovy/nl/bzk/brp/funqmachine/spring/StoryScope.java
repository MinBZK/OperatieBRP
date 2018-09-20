/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.spring;

import java.util.concurrent.ConcurrentHashMap;
import org.jbehave.core.annotations.BeforeStory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

/**
 * Story-backed {@link org.springframework.beans.factory.config.Scope}
 * implementation.
 */
public class StoryScope implements Scope {
    @BeforeStory
    public void startStory() {
        cache.clear();
    }

    @Override
    public Object get(final String name, final ObjectFactory<?> objectFactory) {
        if (!cache.containsKey(name)) {
            cache.putIfAbsent(name, objectFactory.getObject());
        }

        return cache.get(name);
    }

    @Override
    public Object remove(final String name) {
        return cache.remove(name);
    }

    @Override
    public void registerDestructionCallback(final String name, final Runnable callback) {
        // no-op
    }

    @Override
    public Object resolveContextualObject(final String key) {
        return null;
    }

    @Override
    public String getConversationId() {
        return "story";
    }

    private final ConcurrentHashMap<String, Object> cache = new ConcurrentHashMap<String, Object>();
}
