/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.test.brpnaarlo3.adapter.property;

import java.math.BigDecimal;

import nl.moderniseringgba.migratie.test.brpnaarlo3.adapter.PropertyConverter;

import org.springframework.stereotype.Component;

/**
 * BigDecimal converter.
 */
@Component
public final class BigDecimalConverter implements PropertyConverter<BigDecimal> {

    @Override
    public Class<BigDecimal> getType() {
        return BigDecimal.class;
    }

    @Override
    public BigDecimal convert(final String value) {
        return value == null ? null : new BigDecimal(value);
    }

}
