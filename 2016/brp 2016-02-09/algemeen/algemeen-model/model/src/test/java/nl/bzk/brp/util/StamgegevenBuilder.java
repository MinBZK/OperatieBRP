/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


/**
 * Uitility klasse voor stamgegevens.
 */
public final class StamgegevenBuilder {

    /**
     * Utility klasse, dus private constructor.
     */
    private StamgegevenBuilder() {
    }

    /**
     * Methode die van een klasse en een logische id een stamgegeven kan 'bakken'.
     * Warning: Gebruikt reflectie magic.
     *
     * @param klasse                   de klasse
     * @param logischeIdentiteitWaarde de waarde van het logische identiteit attribuut
     * @param <T>                      het type stamgegeven
     * @return het stamgegev
     */
    @SuppressWarnings("unchecked")
    public static <T> T bouwDynamischStamgegeven(final Class<T> klasse, final Object logischeIdentiteitWaarde) {
        T stamgegeven = null;
        for (Constructor<?> constructor : klasse.getDeclaredConstructors()) {
            // Zoek de constructor met parameters.
            int aantalParameters = constructor.getParameterTypes().length;
            if (constructor.getParameterTypes().length > 0) {
                Object[] constructorArgumenten = new Object[aantalParameters];
                for (int i = 0; i < aantalParameters; i++) {
                    if (constructor.getParameterTypes()[i].equals(logischeIdentiteitWaarde.getClass())) {
                        constructorArgumenten[i] = logischeIdentiteitWaarde;
                    } else {
                        constructorArgumenten[i] = null;
                    }
                }
                try {
                    constructor.setAccessible(true);
                    stamgegeven = (T) constructor.newInstance(constructorArgumenten);
                } catch (InstantiationException e) {
                    throw new IllegalStateException("Exceptie bij aanmaken stamgegeven.", e);
                } catch (IllegalAccessException e) {
                    throw new IllegalStateException("Exceptie bij aanmaken stamgegeven.", e);
                } catch (InvocationTargetException e) {
                    throw new IllegalStateException("Exceptie bij aanmaken stamgegeven.", e);
                }
            }
        }
        return stamgegeven;
    }

}
