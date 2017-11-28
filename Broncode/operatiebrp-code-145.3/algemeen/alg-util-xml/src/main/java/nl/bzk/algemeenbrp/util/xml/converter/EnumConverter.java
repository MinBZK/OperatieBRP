/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.xml.converter;

import nl.bzk.algemeenbrp.util.xml.context.Context;
import nl.bzk.algemeenbrp.util.xml.exception.DecodeException;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 * Enum converter.
 * @param <T> type enumeratie
 */
public final class EnumConverter<T extends Enum<T>> implements Converter<T> {

    private final Class<T> enumClazz;

    /**
     * Constructor.
     * @param enumClazz enumeratie klasse
     */
    EnumConverter(final Class<T> enumClazz) {
        this.enumClazz = enumClazz;
    }

    @Override
    public String encode(final Context context, final T value) {
        return StringEscapeUtils.escapeXml11(value.name());
    }

    @Override
    public T decode(final Context context, final String value) throws DecodeException {
        return Enum.valueOf(enumClazz, value);
    }
}
