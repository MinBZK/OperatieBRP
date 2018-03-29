/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.xml.converter;

import nl.bzk.algemeenbrp.util.xml.context.Context;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 * String converter.
 */
public final class StringConverter implements Converter<String> {

    @Override
    public String encode(final Context context, final String value) {
        return StringEscapeUtils.escapeXml11(value);
    }

    @Override
    public String decode(final Context context, final String value) {
        return value;
    }
}
