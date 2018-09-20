/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.util;

import java.lang.reflect.Constructor;
import nl.bzk.brp.model.basis.Attribuut;

/**
 * Attribuut utilities.
 */
public final class AttribuutUtils {

    private AttribuutUtils() {
        // Niet instantieerbaar
    }

    /**
     * Geef als attribuut.
     *
     * @param clazz attribuut klasse
     * @param value waarde voor het attribuut
     * @param <T> het attribuut type
     * @param <V> het attribuut waarde type
     * @return het attribuut, null als value null is.
     */
    public static <T extends Attribuut<V>, V> T getAsAttribuut(final Class<T> clazz, final V value) {
        if (value == null) {
            return null;
        }

        try {
            final Constructor<T> constructor = clazz.getConstructor(value.getClass());
            return constructor.newInstance(value);
        } catch (final ReflectiveOperationException e) {
            throw new IllegalArgumentException("Kan atribuut niet maken", e);
        }
    }

    /**
     * Geef attribuut waarde.
     *
     * @param attribuut attribuut
     * @param <T> waarde type
     * @return attribuut waarde, null als attribuut null is
     */
    public static <T> T getWaarde(final Attribuut<T> attribuut) {
        if (attribuut == null) {
            return null;
        }
        return attribuut.getWaarde();
    }
}
