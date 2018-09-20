/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.repository.jpa;

import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Gemeente;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortDocument;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Verblijfsrecht;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Voorvoegsel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.conversietabel.entity.RedenBeeindigingNationaliteit;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.conversietabel.entity.RedenOpnameNationaliteit;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.StamtabelRepository;
import org.hibernate.annotations.QueryHints;
import org.springframework.stereotype.Repository;

/**
 * De implementatie van de StamtabelRepository interface.
 */
@Repository
public class StamtabelRepositoryImpl implements StamtabelRepository {

    private static final int STAMGEGEVEN_LIJST_FETCH_SIZE = 100;

    @PersistenceContext(name = "syncDalEntityManagerFactory", unitName = "BrpEntities")
    private EntityManager em;

    @Override
    public final Collection<Gemeente> findAllGemeentes() {
        return em.createQuery("from Gemeente", Gemeente.class)
                 .setHint(QueryHints.CACHEABLE, Boolean.TRUE)
                 .setHint(QueryHints.FETCH_SIZE, STAMGEGEVEN_LIJST_FETCH_SIZE)
                 .getResultList();
    }

    @Override
    public final Collection<Short> findAllGemeenteCodes() {
        return em.createQuery("select code from Gemeente", Short.class)
                 .setHint(QueryHints.CACHEABLE, Boolean.TRUE)
                 .setHint(QueryHints.FETCH_SIZE, STAMGEGEVEN_LIJST_FETCH_SIZE)
                 .getResultList();
    }

    @Override
    public final Collection<Short> findAllLandOfGebiedCodes() {
        return em.createQuery("select code from LandOfGebied", Short.class)
                 .setHint(QueryHints.CACHEABLE, Boolean.TRUE)
                 .setHint(QueryHints.FETCH_SIZE, STAMGEGEVEN_LIJST_FETCH_SIZE)
                 .getResultList();
    }

    @Override
    public final Collection<Short> findAllNationaliteitCodes() {
        return em.createQuery("select code from Nationaliteit", Short.class)
                 .setHint(QueryHints.CACHEABLE, Boolean.TRUE)
                 .setHint(QueryHints.FETCH_SIZE, STAMGEGEVEN_LIJST_FETCH_SIZE)
                 .getResultList();
    }

    @Override
    public final Collection<String> findAllPlaatsnamen() {
        return em.createQuery("select naam from Plaats", String.class)
                 .setHint(QueryHints.CACHEABLE, Boolean.TRUE)
                 .setHint(QueryHints.FETCH_SIZE, STAMGEGEVEN_LIJST_FETCH_SIZE)
                 .getResultList();
    }

    @Override
    public final Collection<String> findAllNamenOpenbareRuimte() {
        throw new UnsupportedOperationException("BAG Lijst naam openbare ruimte nog niet beschikbaar");
    }

    @Override
    public final List<Verblijfsrecht> findAllVerblijfsrecht() {
        return em.createQuery("FROM Verblijfsrecht", Verblijfsrecht.class)
                 .setHint(QueryHints.CACHEABLE, Boolean.TRUE)
                 .setHint(QueryHints.FETCH_SIZE, STAMGEGEVEN_LIJST_FETCH_SIZE)
                 .getResultList();
    }

    @Override
    public final List<RedenOpnameNationaliteit> findAllRedenOpnameNationaliteit() {
        return em.createQuery("FROM RedenOpnameNationaliteit", RedenOpnameNationaliteit.class)
                 .setHint(QueryHints.CACHEABLE, Boolean.TRUE)
                 .setHint(QueryHints.FETCH_SIZE, STAMGEGEVEN_LIJST_FETCH_SIZE)
                 .getResultList();
    }

    @Override
    public final List<RedenBeeindigingNationaliteit> findAllRedenBeeindigingNationaliteit() {
        return em.createQuery("FROM RedenBeeindigingNationaliteit", RedenBeeindigingNationaliteit.class)
                 .setHint(QueryHints.CACHEABLE, Boolean.TRUE)
                 .setHint(QueryHints.FETCH_SIZE, STAMGEGEVEN_LIJST_FETCH_SIZE)
                 .getResultList();
    }

    @Override
    public final List<Voorvoegsel> findAllVoorvoegsels() {
        return em.createQuery("FROM Voorvoegsel", Voorvoegsel.class)
                 .setHint(QueryHints.CACHEABLE, Boolean.TRUE)
                 .setHint(QueryHints.FETCH_SIZE, STAMGEGEVEN_LIJST_FETCH_SIZE)
                 .getResultList();
    }

    @Override
    public final SoortDocument findSoortDocumentConversie(final String naam) {
        return em.createQuery("FROM SoortDocument WHERE naam = :naam", SoortDocument.class)
                 .setParameter("naam", naam)
                 .setHint(QueryHints.CACHEABLE, Boolean.TRUE)
                 .setFlushMode(FlushModeType.COMMIT)
                 .getSingleResult();
    }
}
