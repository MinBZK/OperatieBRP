/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.xml.converter;

import nl.bzk.algemeenbrp.util.xml.context.Context;
import nl.bzk.algemeenbrp.util.xml.exception.DecodeException;

/**
 * Integer converter.
 */
public final class IntegerConverter implements Converter<Integer> {

    @Override
    public String encode(final Context context, final Integer value) {
        return value == null ? null : value.toString();
    }

    @Override
    public Integer decode(final Context context, final String value) throws DecodeException {
        try {
            return value == null ? null : Integer.parseInt(value);
        } catch (final NumberFormatException e) {
            throw new DecodeException(context.getElementStack(), e);
        }
    }

}
