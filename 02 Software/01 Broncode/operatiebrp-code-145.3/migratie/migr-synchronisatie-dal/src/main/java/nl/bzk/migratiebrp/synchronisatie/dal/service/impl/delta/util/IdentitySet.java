/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.util;

import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Set implementatie van een IdentityHashMap.
 * @param <E> object waar de inhoud van de set uit bestaat
 */
final class IdentitySet<E> implements Set<E> {

    private final Map<E, Void> identityMap = new IdentityHashMap<>();

    /**
     * Default constructor.
     */
    IdentitySet() {
        super();
    }

    @Override
    public int size() {
        return identityMap.size();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Set#isEmpty()
     */
    @Override
    public boolean isEmpty() {
        return identityMap.isEmpty();
    }

    @Override
    public boolean contains(final Object o) {
        return identityMap.containsKey(o);
    }

    @Override
    public Iterator<E> iterator() {
        return identityMap.keySet().iterator();
    }

    @Override
    public Object[] toArray() {
        return identityMap.keySet().toArray();
    }

    @Override
    public <T> T[] toArray(final T[] a) {
        return identityMap.keySet().toArray(a);
    }

    @Override
    public boolean add(final E e) {
        if (identityMap.containsKey(e)) {
            return false;
        } else {
            identityMap.put(e, null);
            return true;
        }
    }

    @Override
    public boolean remove(final Object o) {
        if (identityMap.containsKey(o)) {
            identityMap.remove(o);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean containsAll(final Collection<?> c) {
        for (final Object t : c) {
            if (!contains(t)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(final Collection<? extends E> c) {
        boolean changed = false;
        for (final E e : c) {
            if (!contains(e)) {
                add(e);
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public boolean retainAll(final Collection<?> c) {
        boolean changed = false;
        for (final Iterator<Map.Entry<E, Void>> iterator = identityMap.entrySet().iterator(); iterator.hasNext(); ) {
            final Map.Entry<E, Void> entry = iterator.next();
            if (!c.contains(entry.getKey())) {
                iterator.remove();
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public boolean removeAll(final Collection<?> c) {
        boolean changed = false;
        for (final Object t : c) {
            if (contains(t)) {
                remove(t);
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public void clear() {
        identityMap.clear();
    }
}
