/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.berichtenverwerker.dataaccess.impl.jpa;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import nl.bzk.brp.poc.berichtenverwerker.dataaccess.PersoonDAO;
import nl.bzk.brp.poc.berichtenverwerker.model.Pers;


/**
 * JPA/Hibernate specifieke implementatie van de {@link PersoonDAO} interface.
 */
public class PersoonDAOImpl implements PersoonDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public final Pers vindPersoonOpBasisVanId(final long id) {
        return em.find(Pers.class, id);
    }

    @Override
    public final Pers vindPersoonOpBasisVanBsn(final BigDecimal bsn) {
        TypedQuery<Pers> query = em.createQuery("from Pers as p where p.bsn = :bsn", Pers.class);
        query.setParameter("bsn", bsn);
        return query.getSingleResult();
    }

}
