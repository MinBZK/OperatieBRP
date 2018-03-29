/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common.dao;

import java.sql.Timestamp;
import javax.inject.Inject;
import nl.bzk.migratiebrp.isc.runtime.jbpm.model.VirtueelGerelateerdeGegevens;
import nl.bzk.migratiebrp.isc.runtime.jbpm.model.VirtueelProces;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

/**
 * Virtueel process dao implementatie obv hibernate session factory.
 */
@Component
public final class SessionVirtueelProcesDao implements VirtueelProcesDao {

    private final SessionFactory sessionFactory;

    /**
     * Constructor.
     * @param sessionFactory session factory
     */
    @Inject
    public SessionVirtueelProcesDao(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public long maak() {
        final VirtueelProces virtueelProces = new VirtueelProces();
        virtueelProces.setTijdstip(new Timestamp(System.currentTimeMillis()));

        final VirtueelProces persistent = (VirtueelProces) sessionFactory.getCurrentSession().merge(virtueelProces);

        return persistent.getId();
    }

    @Override
    public void toevoegenGerelateerdeGegevens(final long processInstanceId, final String soortGegeven, final String gegeven) {
        final VirtueelProces virtueelProces = lees(processInstanceId);

        final VirtueelGerelateerdeGegevens gerelateerd = new VirtueelGerelateerdeGegevens();
        gerelateerd.setVirtueelProces(virtueelProces);
        gerelateerd.setSoortGegeven(soortGegeven);
        gerelateerd.setGegeven(gegeven);

        sessionFactory.getCurrentSession().persist(gerelateerd);
    }

    @Override
    public VirtueelProces lees(final long id) {
        return (VirtueelProces) sessionFactory.getCurrentSession().get(VirtueelProces.class, id);
    }

    @Override
    public void verwijder(final long id) {
        sessionFactory.getCurrentSession().delete(lees(id));
    }
}
