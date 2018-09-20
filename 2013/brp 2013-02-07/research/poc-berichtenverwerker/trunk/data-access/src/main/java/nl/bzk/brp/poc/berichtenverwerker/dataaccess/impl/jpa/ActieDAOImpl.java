/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.berichtenverwerker.dataaccess.impl.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.bzk.brp.poc.berichtenverwerker.dataaccess.ActieDAO;
import nl.bzk.brp.poc.berichtenverwerker.model.Actie;


/**
 * JPA/Hibernate specifieke implementatie van de {@link ActieDAO} interface.
 */
public class ActieDAOImpl implements ActieDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public final Actie vindActieOpBasisVanId(final long id) {
        return em.find(Actie.class, id);
    }

    @Override
    public final void voegToeActie(final Actie actie) {
        em.persist(actie);
        em.flush();
        em.refresh(actie);
    }

}
