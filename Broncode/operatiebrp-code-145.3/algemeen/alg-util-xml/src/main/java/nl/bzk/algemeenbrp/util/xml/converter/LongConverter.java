/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.xml.converter;

import nl.bzk.algemeenbrp.util.xml.context.Context;
import nl.bzk.algemeenbrp.util.xml.exception.DecodeException;

/**
 * Long converter.
 */
public final class LongConverter implements Converter<Long> {

    @Override
    public String encode(final Context context, final Long value) {
        return value == null ? null : value.toString();
    }

    @Override
    public Long decode(final Context context, final String value) throws DecodeException {
        try {
            return value == null ? null : Long.parseLong(value);
        } catch (final NumberFormatException e) {
            throw new DecodeException(context.getElementStack(), e);
        }
    }

}
