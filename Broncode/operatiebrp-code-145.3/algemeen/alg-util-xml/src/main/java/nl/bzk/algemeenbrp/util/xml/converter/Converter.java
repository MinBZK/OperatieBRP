/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.xml.converter;

import nl.bzk.algemeenbrp.util.xml.context.Context;
import nl.bzk.algemeenbrp.util.xml.exception.DecodeException;
import nl.bzk.algemeenbrp.util.xml.exception.EncodeException;

/**
 * Converter.
 *
 * @param <T> te converteren type
 */
public interface Converter<T> {

    /**
     * Encodeer een waarde naar XML (string).
     *
     * @param context context
     * @param value waarde
     * @return geencodeerde waarde
     * @throws EncodeException bij encodeer problemen
     */
    String encode(Context context, T value) throws EncodeException;

    /**
     * Decodeer een waarde van XML (string).
     *
     * @param context context
     * @param value XML waarde
     * @return gedecodeerde waarde
     * @throws DecodeException bij decodeer problemen
     */
    T decode(Context context, String value) throws DecodeException;

}
