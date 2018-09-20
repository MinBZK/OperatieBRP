/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.foutafhandeling;

import java.util.Date;
import javax.inject.Inject;
import nl.bzk.migratiebrp.isc.runtime.jbpm.model.Fout;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jbpm.graph.exe.ProcessInstance;
import org.springframework.stereotype.Component;

/**
 * Registreren van fouten via Hibernate Session.
 */
@Component
public final class SessionFoutenDao implements FoutenDao {

    @Inject
    private SessionFactory sessionFactory;

    @Override
    public long registreerFout(
        final String foutcode,
        final String foutmelding,
        final String proces,
        final long processId,
        final String bronGemeente,
        final String doelGemeente)
    {
        final Session session = sessionFactory.getCurrentSession();

        final Fout fout = new Fout();
        fout.setTijdstip(new Date());
        fout.setCode(foutcode);
        fout.setMelding(foutmelding);
        fout.setProces(proces);
        fout.setProcessInstance((ProcessInstance) session.load(ProcessInstance.class, processId));
        fout.setProcesInitGemeente(bronGemeente);
        fout.setProcesDoelGemeente(doelGemeente);

        session.save(fout);

        return fout.getId();
    }

    @Override
    public String haalFoutcodeOp(final long foutId) {
        final Session session = sessionFactory.getCurrentSession();

        return ((Fout) session.get(Fout.class, foutId)).getCode();
    }

    @Override
    public void voegResolutieToe(final long id, final String resolutie) {
        final Session session = sessionFactory.getCurrentSession();
        final Fout fout = (Fout) session.get(Fout.class, id);
        fout.setResolutie(resolutie);
        session.save(fout);

    }

}
