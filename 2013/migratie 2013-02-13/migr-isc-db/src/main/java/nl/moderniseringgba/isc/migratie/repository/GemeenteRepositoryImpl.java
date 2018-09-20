/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.migratie.repository;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.moderniseringgba.isc.migratie.domein.entities.Gemeente;

import org.springframework.stereotype.Repository;

/**
 * Data-access-punt voor alles omtrent BRP-betrokkenheden.
 */
@Repository
public final class GemeenteRepositoryImpl implements GemeenteRepository {

    @PersistenceContext(name = "iscEntityManagerFactory", unitName = "iscEntityManagerFactory")
    private EntityManager em;

    /**
     * {@inheritDoc}
     */
    @Override
    public Gemeente findGemeente(final int gemeenteCode) {
        return em.find(Gemeente.class, Integer.valueOf(gemeenteCode));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Gemeente> getBrpActiveGemeente() {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        final Integer vandaag = new Integer(sdf.format(Calendar.getInstance().getTime()));

        return em.createQuery("from Gemeente where datumbrp <= :vandaag").setParameter("vandaag", vandaag)
                .getResultList();
    }
}
