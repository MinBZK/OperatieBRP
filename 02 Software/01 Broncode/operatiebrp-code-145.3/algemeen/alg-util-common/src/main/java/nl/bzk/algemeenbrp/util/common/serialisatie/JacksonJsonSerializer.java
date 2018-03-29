/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.common.serialisatie;

/**
 * Definieert (de)serialisatie methodes voor specifieke types.
 */
public interface JacksonJsonSerializer {

    /**
     * Serialiseer een object.
     * @param object het te serialiseren object
     * @return de geserialiseerde bytearray
     */
    byte[] serialiseer(final Object object);

    /**
     * Deserialiseert een bytearray naar een object van type {@link T}.
     * @param bytes de te deserialiseren bytearray
     * @param clazz Het Class type
     * @return het gedeserialiseerde object van type {@link T}
     */
    <T> T deserialiseer(final byte[] bytes, final Class<?> clazz);
}
