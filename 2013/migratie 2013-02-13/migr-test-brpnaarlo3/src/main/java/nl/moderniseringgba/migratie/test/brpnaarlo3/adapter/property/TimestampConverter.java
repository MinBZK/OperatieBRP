/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.test.brpnaarlo3.adapter.property;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import nl.moderniseringgba.migratie.test.brpnaarlo3.adapter.PropertyConverter;

import org.springframework.stereotype.Component;

/**
 * Timestamp converter.
 */
@Component
public final class TimestampConverter implements PropertyConverter<Timestamp> {

    private static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
    static {
        TIMESTAMP_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT+0:00"));
    }

    @Override
    public Class<Timestamp> getType() {
        return Timestamp.class;
    }

    @Override
    public Timestamp convert(final String value) {
        if (value == null) {
            return null;
        }

        try {
            return new Timestamp(TIMESTAMP_FORMAT.parse(value).getTime());
        } catch (final ParseException e) {
            throw new RuntimeException("Invalid timestamp '" + value + "'.", e);
        }
    }

}
