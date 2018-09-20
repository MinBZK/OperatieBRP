/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.query;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;


/**
 * Date value adapter.
 */
public final class DateValueAdapter implements ValueAdapter<Date> {

    private static final Logger   LOGGER       = LoggerFactory.getLogger();

    private static final String[] DATE_FORMATS = new String[] { "yyyyMMddHHmmssSSS", "yyyyMMddHHmmss", "yyyyMMddHHmm",
        "yyyyMMddHH", "yyyyMMdd", "yyyyMM", "yyyy", };

    @Override
    public Date adaptValue(final String value) {
        for (final String dateFormat : DATE_FORMATS) {
            final SimpleDateFormat parser = new SimpleDateFormat(dateFormat);
            try {
                return parser.parse(value);
            } catch (final ParseException e) {
                // Try next
                LOGGER.warn("Datum '{}' voldoet niet aan pattern '{}'", value, dateFormat);
            }
        }
        throw new IllegalArgumentException("Ongeldige datum '" + value + "'.");
    }
}
