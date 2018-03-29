/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.brpnaarlo3.adapter.property;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenVerliesNLNationaliteit;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.PropertyConverter;
import org.springframework.stereotype.Component;

/**
 * RedenVeerliesNLNationaliteit converter.
 */
@Component
public final class RedenVerliesNLNationaliteitConverter implements PropertyConverter<RedenVerliesNLNationaliteit> {
    @PersistenceContext
    private EntityManager em;

    @Override
    public RedenVerliesNLNationaliteit convert(final String value) {
        return value == null ? null : em.find(RedenVerliesNLNationaliteit.class, Short.valueOf(value));
    }

}
