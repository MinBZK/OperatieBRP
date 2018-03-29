/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common.dao;

import java.util.Collection;
import javax.inject.Inject;
import nl.bzk.migratiebrp.isc.runtime.jbpm.model.TaakGerelateerd;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.springframework.stereotype.Component;

/**
 * Taak gerelateerde informatie DAO via Hibernate session.
 */
@Component
public final class SessionTaakGerelateerdeInformatieDao implements TaakGerelateerdeInformatieDao {

    private final SessionFactory sessionFactory;

    /**
     * Constructor.
     * @param sessionFactory session factory
     */
    @Inject
    public SessionTaakGerelateerdeInformatieDao(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void registreerAdministratienummers(final TaskInstance taskInstance, final String... administratienummers) {
        final Session session = sessionFactory.getCurrentSession();

        for (final String administratienummer : administratienummers) {
            final TaakGerelateerd gerelateerdAnummer = new TaakGerelateerd();
            gerelateerdAnummer.setTaskInstance(taskInstance);
            gerelateerdAnummer.setAdministratienummer(administratienummer);
            session.save(gerelateerdAnummer);
        }
    }

    @Override
    public Collection<TaskInstance> zoekOpAdministratienummers(final String... administratienummers) {
        final Session session = sessionFactory.getCurrentSession();

        final Query query =
                session.createQuery(
                        "select distinct taskInstance from nl.bzk.migratiebrp.isc.runtime.jbpm.model.TaakGerelateerd "
                                + "where administratienummer in (:administratienummers)");
        query.setParameterList("administratienummers", administratienummers);
        return query.list();
    }

}
