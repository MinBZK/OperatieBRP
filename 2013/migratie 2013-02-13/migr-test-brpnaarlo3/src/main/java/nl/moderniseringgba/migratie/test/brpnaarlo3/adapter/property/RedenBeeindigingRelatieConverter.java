/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.test.brpnaarlo3.adapter.property;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.RedenBeeindigingRelatie;
import nl.moderniseringgba.migratie.test.brpnaarlo3.adapter.PropertyConverter;

import org.springframework.stereotype.Component;

/**
 * RedenBeeindigingRelatie converter.
 */
@Component
public final class RedenBeeindigingRelatieConverter implements PropertyConverter<RedenBeeindigingRelatie> {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Class<RedenBeeindigingRelatie> getType() {
        return RedenBeeindigingRelatie.class;
    }

    @Override
    public RedenBeeindigingRelatie convert(final String value) {
        return value == null ? null : em.find(RedenBeeindigingRelatie.class, Integer.valueOf(value));
    }

}
