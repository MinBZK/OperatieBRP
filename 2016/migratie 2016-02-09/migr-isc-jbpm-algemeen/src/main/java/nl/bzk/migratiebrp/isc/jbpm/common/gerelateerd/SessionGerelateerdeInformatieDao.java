/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common.gerelateerd;

import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.GerelateerdeInformatie;
import nl.bzk.migratiebrp.isc.runtime.jbpm.model.GerelateerdeGegevens;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jbpm.graph.exe.ProcessInstance;
import org.springframework.stereotype.Component;

/**
 * Gerelateerde informatie DAO via Hibernate session.
 */
@Component
public final class SessionGerelateerdeInformatieDao implements GerelateerdeInformatieDao {

    @Inject
    private SessionFactory sessionFactory;

    /**
     * Toevoegen van gerelateerde informatie aan een proces.
     *
     * @param processInstanceId
     *            proces instance id
     * @param gerelateerdeInformatie
     *            gerelateerd informatie
     */
    @Override
    public void toevoegenGerelateerdeGegevens(final long processInstanceId, final GerelateerdeInformatie gerelateerdeInformatie) {
        final Session session = sessionFactory.getCurrentSession();

        final ProcessInstance processInstance = (ProcessInstance) session.load(ProcessInstance.class, processInstanceId);

        for (final String aNummer : gerelateerdeInformatie.getaNummers()) {
            final GerelateerdeGegevens gerelateerdAnummer = new GerelateerdeGegevens();
            gerelateerdAnummer.setProcessInstance(processInstance);
            gerelateerdAnummer.setSoortGegeven("ANR");
            gerelateerdAnummer.setGegeven(aNummer);
            session.save(gerelateerdAnummer);
        }

        for (final String partij : gerelateerdeInformatie.getPartijen()) {
            final GerelateerdeGegevens gerelateerdePartij = new GerelateerdeGegevens();
            gerelateerdePartij.setProcessInstance(processInstance);
            gerelateerdePartij.setSoortGegeven("PTY");
            gerelateerdePartij.setGegeven(partij);
            session.save(gerelateerdePartij);
        }

        for (final Long administratieveHandelingId : gerelateerdeInformatie.getAdministratieveHandelingIds()) {
            final GerelateerdeGegevens gerelateerdeAdmHnd = new GerelateerdeGegevens();
            gerelateerdeAdmHnd.setProcessInstance(processInstance);
            gerelateerdeAdmHnd.setSoortGegeven("ADH");
            gerelateerdeAdmHnd.setGegeven(Long.toString(administratieveHandelingId));
            session.save(gerelateerdeAdmHnd);
        }
    }

}
