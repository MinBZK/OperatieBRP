/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.test.brpnaarlo3.adapter.property;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.AutoriteitVanAfgifteReisdocument;
import nl.moderniseringgba.migratie.test.brpnaarlo3.adapter.PropertyConverter;

import org.springframework.stereotype.Component;

/**
 * AutoriteitVanAfgifteReisdocument converter.
 */
@Component
public final class AutoriteitVanAfgifteReisdocumentConverter implements
        PropertyConverter<AutoriteitVanAfgifteReisdocument> {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Class<AutoriteitVanAfgifteReisdocument> getType() {
        return AutoriteitVanAfgifteReisdocument.class;
    }

    @Override
    public AutoriteitVanAfgifteReisdocument convert(final String value) {
        return value == null ? null : em.find(AutoriteitVanAfgifteReisdocument.class, Integer.valueOf(value));
    }

}
