/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common.rapportage;

import java.util.Date;
import nl.bzk.migratiebrp.isc.runtime.jbpm.model.ExtractieProces;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jbpm.graph.exe.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Berichten DAO implementatie obv connectie uit JBPM.
 */
@Component
public final class SessionRapportageDao implements RapportageDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void voegStartProcesInstantieToe(
            final String procesNaam,
            final String berichtType,
            final String kanaal,
            final Long procesInstantieId,
            final Date startdatum) {
        final Session session = sessionFactory.getCurrentSession();

        final ExtractieProces extractieProces = new ExtractieProces();
        extractieProces.setProcesNaam(procesNaam);
        extractieProces.setBerichtType(berichtType);
        extractieProces.setKanaal(kanaal);
        extractieProces.setProcessInstance((ProcessInstance) session.load(ProcessInstance.class, procesInstantieId));
        extractieProces.setStartdatum(startdatum);

        session.save(extractieProces);
    }

    @Override
    public void updateFoutreden(final Long procesInstantieId, final Date wachtStartdatum, final String foutreden) {
        final Session session = sessionFactory.getCurrentSession();

        final ExtractieProces extractieProces = (ExtractieProces) session.load(ExtractieProces.class, procesInstantieId);
        extractieProces.setWachtStartdatum(wachtStartdatum);
        extractieProces.setFoutreden(foutreden);
    }

    @Override
    public void updateWachtBeheerderEinde(final Long procesInstantieId, final Date wachtEinddatum) {
        final Session session = sessionFactory.getCurrentSession();

        final ExtractieProces extractieProces = (ExtractieProces) session.load(ExtractieProces.class, procesInstantieId);
        extractieProces.setWachtEinddatum(wachtEinddatum);
    }

    @Override
    public void updateEindeProcesInstantie(final Long procesInstantieId, final Date einddatum) {
        final Session session = sessionFactory.getCurrentSession();

        final ExtractieProces extractieProces = (ExtractieProces) session.load(ExtractieProces.class, procesInstantieId);
        extractieProces.setEinddatum(einddatum);
    }
}
