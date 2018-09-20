/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie;

/**
 * Definieert (de)serialisatie methodes voor specifieke types.
 * @param <T>
 */
public interface JacksonJsonSerializer<T> {

    byte[] serialiseer(final T object);

    T deserialiseer(final byte[] bytes);

}
