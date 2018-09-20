/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.test.brpnaarlo3.adapter.property;

import nl.moderniseringgba.migratie.test.brpnaarlo3.adapter.PropertyConverter;

import org.springframework.stereotype.Component;

/**
 * Long converter.
 */
@Component
public final class LongConverter implements PropertyConverter<Long> {

    @Override
    public Class<Long> getType() {
        return Long.class;
    }

    @Override
    public Long convert(final String value) {

        return value == null ? null : Long.valueOf(value);
    }

}
