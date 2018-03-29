/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Stapel;
import org.springframework.stereotype.Repository;

/**
 * IST repository.
 */
@Repository("gbaIstTabelRepository")
public class IstTabelRepositoryImpl implements IstTabelRepository {

    @PersistenceContext(unitName = "nl.bzk.brp.master")
    private EntityManager entityManager;

    @Override
    public final List<Stapel> leesIstStapels(final Long persoonId) {
        final TypedQuery<Stapel> tQuery =
                entityManager.createQuery("select s from Stapel s " + "WHERE s.persoon.id = :persoonId", Stapel.class)
                        .setParameter("persoonId", persoonId);

        return tQuery.getResultList();
    }
}
