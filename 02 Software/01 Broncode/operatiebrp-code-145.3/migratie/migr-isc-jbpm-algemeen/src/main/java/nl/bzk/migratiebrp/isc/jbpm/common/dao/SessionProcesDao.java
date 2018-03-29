/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common.dao;

import javax.inject.Inject;
import nl.bzk.migratiebrp.isc.runtime.jbpm.model.GerelateerdeGegevens;
import nl.bzk.migratiebrp.isc.runtime.jbpm.model.ProcesRelatie;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jbpm.graph.exe.ProcessInstance;
import org.springframework.stereotype.Component;

/**
 * Proces dao implementatie obv hibernate session factory.
 */
@Component
public final class SessionProcesDao implements ProcesDao {

    private final SessionFactory sessionFactory;

    /**
     * Constructor.
     * @param sessionFactory session factory
     */
    @Inject
    public SessionProcesDao(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void registreerProcesRelatie(final long processInstanceIdEen, final long processInstanceIdTwee) {
        final Session currentSession = sessionFactory.getCurrentSession();
        final ProcessInstance procesInstanceEen = (ProcessInstance) currentSession.get(ProcessInstance.class, processInstanceIdEen);
        if (procesInstanceEen == null) {
            throw new IllegalArgumentException("Process instance " + processInstanceIdEen + " onbekend");
        }

        final ProcessInstance procesInstanceTwee = (ProcessInstance) currentSession.get(ProcessInstance.class, processInstanceIdTwee);
        if (procesInstanceTwee == null) {
            throw new IllegalArgumentException("Process instance " + processInstanceIdTwee + " onbekend");
        }

        final ProcesRelatie procesRelatie = new ProcesRelatie();
        procesRelatie.setProcessInstanceEen(procesInstanceEen);
        procesRelatie.setProcessInstanceTwee(procesInstanceTwee);

        currentSession.merge(procesRelatie);

    }

    @Override
    public void toevoegenGerelateerdGegeven(final long processInstanceId, final String soortGegeven, final String gegeven) {
        final Session currentSession = sessionFactory.getCurrentSession();
        final ProcessInstance procesInstance = (ProcessInstance) currentSession.get(ProcessInstance.class, processInstanceId);

        final GerelateerdeGegevens gerelateerd = new GerelateerdeGegevens();
        gerelateerd.setProcessInstance(procesInstance);
        gerelateerd.setGegeven(gegeven);
        gerelateerd.setSoortGegeven(soortGegeven);

        currentSession.merge(gerelateerd);
    }
}
