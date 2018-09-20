/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.core.impl;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Collections;
import java.util.AbstractSet;
import java.util.Iterator;
import java.io.Serializable;

/**
 *
 */
public final class UpdatesHashMap extends AbstractMap<String,Object> implements Serializable {

    private static final long serialVersionUID = 1L;

    final Map<String,Object> backingMap;
    final Set<String> updates;
    final Set<String> deletes;

    public UpdatesHashMap() {
        backingMap = new HashMap<String, Object>();
        updates = new HashSet<String>();
        deletes = new HashSet<String>();
    }

    public UpdatesHashMap(final Map<String, Object> backingMap) {
        if (backingMap == null) {
            this.backingMap = new HashMap<String, Object>();
        } else {
            this.backingMap = new HashMap<String, Object>(backingMap);
        }
        updates = new HashSet<String>();
        deletes = new HashSet<String>();
    }

    public boolean containsKey(final Object key) {
        return backingMap.containsKey(key);
    }

    public Object get(final Object key) {
        return backingMap.get(key);
    }

    public boolean isEmpty() {
        return backingMap.isEmpty();
    }

    public int size() {
        return backingMap.size();
    }

    public Object put(final String key, final Object value) {
        if (key == null) {
            throw new NullPointerException("key is null");
        }
        deletes.remove(key);
        final Object old = backingMap.put(key, value);
        if (old == value) {
            // this branch is also taken if both are null
            // no update
            return old;
        } else if ((old == null) != (value == null)) {
            // this branch is taken if one of them is null
            // update
            updates.add(key);
            return old;
        } else if (value.equals(old)) {
            // niether one can possibly be null here
            // no update
            return old;
        } else {
            // niether one can possibly be null here
            // update
            updates.add(key);
            return old;
        }
    }

    public Object remove(final Object key) {
        if (key instanceof String && backingMap.containsKey(key)) {
            deletes.add((String) key);
            updates.remove(key);
            return backingMap.remove(key);
        } else {
            return null;
        }
    }

    public void clear() {
        updates.clear();
        deletes.clear();
        deletes.addAll(backingMap.keySet());
        backingMap.clear();
    }

    public Set<Entry<String, Object>> entrySet() {
        return new EntrySet(this);
    }

    public Set<String> updatesSet() {
        return Collections.unmodifiableSet(updates);
    }

    public Set<String> deletesSet() {
        return Collections.unmodifiableSet(deletes);
    }

    public static final class EntrySet extends AbstractSet<Entry<String, Object>> implements Serializable {
        private static final long serialVersionUID = 1L;

        private final UpdatesHashMap updatesHashMap;

        public EntrySet(final UpdatesHashMap updatesHashMap) {
            this.updatesHashMap = updatesHashMap;
        }

        public Iterator<Entry<String, Object>> iterator() {
            return new EntrySetIterator(updatesHashMap);
        }

        public int size() {
            return updatesHashMap.backingMap.size();
        }

        public boolean isEmpty() {
            return updatesHashMap.backingMap.isEmpty();
        }
    }

    public static final class EntrySetIterator implements Iterator<Map.Entry<String, Object>> {
        private final Iterator<Map.Entry<String,Object>> iterator;
        private final UpdatesHashMap updatesHashMap;
        private Map.Entry<String,Object> current;

        public EntrySetIterator(final UpdatesHashMap updatesHashMap) {
            iterator = updatesHashMap.backingMap.entrySet().iterator();
            this.updatesHashMap = updatesHashMap;
        }

        public boolean hasNext() {
            return iterator.hasNext();
        }

        public Map.Entry<String, Object> next() {
            current = iterator.next();
            return current;
        }

        public void remove() {
            iterator.remove();
            final String key = current.getKey();
            current = null;
            updatesHashMap.deletes.add(key);
            updatesHashMap.updates.remove(key);
        }
    }
}
