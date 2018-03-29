/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common.jsf;

import java.util.Collection;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Meldingen.
 */
public final class Meldingen implements Map<String, String> {
    private static final String VRAAGTEKENS = "???";
    private final ResourceBundle bundle = ResourceBundle.getBundle("nl.bzk.migratiebrp.isc.jbpm.common.jsf.Messages");

    @Override
    /**
     * squid:S1166 Exception handlers should preserve the original exceptions
     *
     * False positive, exceptie wordt volledig verwerkt. Info is niet nodig.
     */
    public String get(final Object key) {
        String result;
        if (key == null || "".equals(key)) {
            result = "";
        } else {
            try {
                result = bundle.getString((String) key);
            } catch (final MissingResourceException e) {
                result = VRAAGTEKENS + key + VRAAGTEKENS;
            }
        }
        return result;
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsKey(final Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsValue(final Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Map.Entry<String, String>> entrySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<String> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String put(final String key, final String value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putAll(final Map<? extends String, ? extends String> m) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String remove(final Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<String> values() {
        throw new UnsupportedOperationException();
    }

}
