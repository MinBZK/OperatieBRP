/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.logisch.ist.Stapel;
import org.springframework.stereotype.Repository;

/**
 * IST repository.
 */
@Repository("gbaIstTabelRepository")
public class IstTabelRepositoryImpl implements IstTabelRepository {

    @PersistenceContext(unitName = "nl.bzk.brp.alleenlezen")
    private EntityManager entityManager;

    @Override
    public final List<Stapel> leesIstStapels(final PersoonHisVolledig persoon) {
        final TypedQuery<Stapel> tQuery =
                entityManager.createQuery("select s from StapelModel s " + "WHERE s.persoon.id = :persoonId", Stapel.class)
                             .setParameter("persoonId", persoon.getID());

        return tQuery.getResultList();
    }
}
