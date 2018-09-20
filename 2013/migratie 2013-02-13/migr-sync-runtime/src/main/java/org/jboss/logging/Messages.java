/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

//CHECKSTYLE:OFF - Externe code
/*
 * JBoss, Home of Professional Open Source.
 *
 * Copyright 2010 Red Hat, Inc., and individual contributors
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

import java.lang.reflect.Field;
import java.util.Locale;

/**
 * A factory class to produce message bundle implementations.
 * 
 * @author <a href="mailto:david.lloyd@redhat.com">David M. Lloyd</a>
 */
public final class Messages {

    private Messages() {
    }

    /**
     * Get a message bundle of the given type. Equivalent to
     * <code>{@link #getBundle(Class, java.util.Locale) getBundle}(type, Locale.getDefault())</code>.
     * 
     * @param type
     *            the bundle type class
     * @param <T>
     *            the bundle type
     * @return the bundle
     */
    public static <T> T getBundle(final Class<T> type) {
        return getBundle(type, Locale.getDefault());
    }

    /**
     * Get a message bundle of the given type.
     * 
     * @param type
     *            the bundle type class
     * @param locale
     *            the message locale to use
     * @param <T>
     *            the bundle type
     * @return the bundle
     */
    public static <T> T getBundle(final Class<T> type, final Locale locale) {
        final String language = locale.getLanguage();
        final String country = locale.getCountry();
        final String variant = locale.getVariant();

        Class<? extends T> bundleClass = null;
        if (variant != null && variant.length() > 0) {
            try {
                bundleClass =
                        Class.forName(join(type.getName(), "$bundle", language, country, variant), true,
                                type.getClassLoader()).asSubclass(type);
            } catch (final ClassNotFoundException e) {
                // ignore
            }
        }
        if (bundleClass == null && country != null && country.length() > 0) {
            try {
                bundleClass =
                        Class.forName(join(type.getName(), "$bundle", language, country, null), true,
                                type.getClassLoader()).asSubclass(type);
            } catch (final ClassNotFoundException e) {
                // ignore
            }
        }
        if (bundleClass == null && language != null && language.length() > 0) {
            try {
                bundleClass =
                        Class.forName(join(type.getName(), "$bundle", language, null, null), true,
                                type.getClassLoader()).asSubclass(type);
            } catch (final ClassNotFoundException e) {
                // ignore
            }
        }
        if (bundleClass == null) {
            try {
                bundleClass =
                        Class.forName(join(type.getName(), "$bundle", null, null, null), true, type.getClassLoader())
                                .asSubclass(type);
            } catch (final ClassNotFoundException e) {
                throw new IllegalArgumentException("Invalid bundle " + type + " (implementation not found)");
            }
        }
        final Field field;
        try {
            field = bundleClass.getField("INSTANCE");
        } catch (final NoSuchFieldException e) {
            throw new IllegalArgumentException("Bundle implementation " + bundleClass + " has no instance field");
        }
        try {
            return type.cast(field.get(null));
        } catch (final IllegalAccessException e) {
            throw new IllegalArgumentException("Bundle implementation " + bundleClass + " could not be instantiated",
                    e);
        }
    }

    private static String join(
            final String interfaceName,
            final String a,
            final String b,
            final String c,
            final String d) {
        final StringBuilder build = new StringBuilder();
        build.append(interfaceName).append('_').append(a);
        if (b != null && b.length() > 0) {
            build.append('_');
            build.append(b);
        }
        if (c != null && c.length() > 0) {
            build.append('_');
            build.append(c);
        }
        if (d != null && d.length() > 0) {
            build.append('_');
            build.append(d);
        }
        return build.toString();
    }
}
