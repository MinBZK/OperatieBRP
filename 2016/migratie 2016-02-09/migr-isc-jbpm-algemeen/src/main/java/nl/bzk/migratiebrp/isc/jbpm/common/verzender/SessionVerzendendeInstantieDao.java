/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common.verzender;

import javax.inject.Inject;
import nl.bzk.migratiebrp.isc.runtime.jbpm.model.Verzender;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

/**
 * Verzendende partij DAO implementatie obv connectie uit JBPM.
 */
@Component
public final class SessionVerzendendeInstantieDao implements VerzendendeInstantieDao {

    @Inject
    private SessionFactory sessionFactory;

    @Override
    public Long bepaalVerzendendeInstantie(final long instantiecode) {
        final Session session = sessionFactory.getCurrentSession();
        final Verzender verzender = (Verzender) session.get(Verzender.class, instantiecode);
        return verzender == null ? null : verzender.getVerzendendeInstantiecode();
    }

}
