/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.converter;

import java.lang.reflect.Constructor;

import nl.bzk.brp.model.basis.Attribuut;

/**
 * Abstract converter (uses unknown types).
 *
 * @param <O> OUT
 */
public abstract class AbstractAttribuutEnumConverter<O extends Attribuut<?>> extends AbstractConverter<Integer, O> {

    private final Constructor<O> attribuutConstructor;
    private final Class<?> enumClazz;

    /**
     * Constructor.
     *
     * @param attribuutClazz attribuut klasse
     * @param enumClazz waarde klasse
     */
    protected AbstractAttribuutEnumConverter(final Class<O> attribuutClazz, final Class<?> enumClazz) {
        this.enumClazz = enumClazz;
        try {
            attribuutConstructor = attribuutClazz.getConstructor(enumClazz);
        } catch (final ReflectiveOperationException e) {
            throw new IllegalArgumentException("Kan attribuut constructor niet vinden", e);
        }
    }

    @Override
    public final O convert(final Integer input) {
        try {
            return attribuutConstructor.newInstance(enumClazz.getEnumConstants()[input]);
        } catch (final ReflectiveOperationException e) {
            throw new IllegalArgumentException("Kan attribuut niet instantieren", e);
        }
    }
}
