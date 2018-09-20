/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.brpnaarlo3.adapter.property;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortIndicatie;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.PropertyConverter;
import org.springframework.stereotype.Component;

/**
 * SoortIndicatie converter.
 */
@Component
public final class SoortIndicatieConverter implements PropertyConverter<SoortIndicatie> {

    @Override
    public SoortIndicatie convert(final String value) {
        return value == null ? null : SoortIndicatie.parseId(Short.valueOf(value));
    }

}
