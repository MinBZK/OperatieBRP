/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.sql.DataSource;

import nl.bzk.brp.dataaccess.repository.ToegangLeveringsautorisatieRepository;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import org.springframework.stereotype.Repository;


/**
 * Toegang- leveringsautorisatie repository.
 * TODO verplaats naar levering-kern dan kunnen gebruik maken van caches
 */
@Repository
public final class ToegangLeveringsautorisatieRepositoryImpl implements ToegangLeveringsautorisatieRepository {

    @PersistenceContext(unitName = "nl.bzk.brp.alleenlezen")
    private EntityManager entityManager;

    @Inject
    @Named("alleenLezenDataSource")
    private DataSource alleenLezenDs;

    @Override
    public ToegangLeveringsautorisatie haalToegangLeveringsautorisatieOp(final Integer id) {
        return entityManager.find(ToegangLeveringsautorisatie.class, id);
    }

    @Override
    public List<ToegangLeveringsautorisatie> haalAlleToegangLeveringsautorisatieOp() {
        final TypedQuery<ToegangLeveringsautorisatie> typedQuery =
            entityManager.createQuery("SELECT tla FROM ToegangLeveringsautorisatie tla", ToegangLeveringsautorisatie.class);
        typedQuery.setLockMode(LockModeType.NONE);
        return typedQuery.getResultList();
    }

    @Override
    public List<Leveringsautorisatie> haalAlleLeveringsautorisatieOp() {
        final TypedQuery<Leveringsautorisatie> typedQuery = entityManager.createQuery("SELECT la FROM Leveringsautorisatie la", Leveringsautorisatie.class);
        typedQuery.setLockMode(LockModeType.NONE);
        return typedQuery.getResultList();
    }

    @Override
    public List<ToegangLeveringsautorisatie> geefToegangLeveringsautorisaties(final Integer partijCode) {
        final TypedQuery<ToegangLeveringsautorisatie> typedQuery =
                entityManager.createQuery("SELECT tl FROM ToegangLeveringsautorisatie tl WHERE tl.geautoriseerde.partij.code.waarde = :partijcode",
                        ToegangLeveringsautorisatie.class);
        typedQuery.setParameter("partijcode", partijCode);
        return typedQuery.getResultList();
    }
}
