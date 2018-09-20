/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.brpnaarlo3.adapter.property;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.RedenVerkrijgingNLNationaliteit;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.PropertyConverter;
import org.springframework.stereotype.Component;

/**
 * RedenVerkrijgingNLNationaliteit converter.
 */
@Component
public final class RedenVerkrijgingNLNationaliteitConverter implements PropertyConverter<RedenVerkrijgingNLNationaliteit> {
    @PersistenceContext
    private EntityManager em;

    @Override
    public RedenVerkrijgingNLNationaliteit convert(final String value) {
        return value == null ? null : em.find(RedenVerkrijgingNLNationaliteit.class, Short.valueOf(value));
    }

}
