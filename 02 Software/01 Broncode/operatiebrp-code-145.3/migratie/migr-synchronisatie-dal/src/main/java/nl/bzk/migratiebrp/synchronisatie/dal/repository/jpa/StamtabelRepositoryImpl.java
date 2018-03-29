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
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenBeeindigingNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenOpnameNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortDocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Verblijfsrecht;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Voorvoegsel;
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
        return em.createQuery(
                "from Gemeente g join fetch g.partij p left join fetch p.partijRolSet left join fetch p.hisPartijen left join fetch p"
                        + ".partijBijhoudingHistorieSet",
                Gemeente.class).setHint(QueryHints.CACHEABLE, Boolean.TRUE).setHint(QueryHints.FETCH_SIZE, STAMGEGEVEN_LIJST_FETCH_SIZE).getResultList();
    }

    @Override
    public final Collection<Partij> findAllPartijen() {
        return em.createQuery("select distinct p from Partij p left join fetch p.partijRolSet left join fetch p.gemeenteSet", Partij.class)
                .setHint(QueryHints.CACHEABLE, Boolean.TRUE)
                .getResultList();
    }

    @Override
    public final Partij findPartijByCode(final String partijCode) {
        return em.createQuery("from Partij p left join fetch p.partijRolSet where p.code = :code", Partij.class)
                .setParameter("code", partijCode)
                .setHint(QueryHints.CACHEABLE, Boolean.TRUE)
                .getSingleResult();
    }

    @Override
    public final Collection<String> findAllGemeenteCodes() {
        return em.createQuery("select code from Gemeente", String.class)
                .setHint(QueryHints.CACHEABLE, Boolean.TRUE)
                .setHint(QueryHints.FETCH_SIZE, STAMGEGEVEN_LIJST_FETCH_SIZE)
                .getResultList();
    }

    @Override
    public final Collection<String> findAllLandOfGebiedCodes() {
        return em.createQuery("select code from LandOfGebied", String.class)
                .setHint(QueryHints.CACHEABLE, Boolean.TRUE)
                .setHint(QueryHints.FETCH_SIZE, STAMGEGEVEN_LIJST_FETCH_SIZE)
                .getResultList();
    }

    @Override
    public final Collection<String> findAllNationaliteitCodes() {
        return em.createQuery("select code from Nationaliteit", String.class)
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
        return em.createQuery("from Verblijfsrecht", Verblijfsrecht.class)
                .setHint(QueryHints.CACHEABLE, Boolean.TRUE)
                .setHint(QueryHints.FETCH_SIZE, STAMGEGEVEN_LIJST_FETCH_SIZE)
                .getResultList();
    }

    @Override
    public final List<RedenOpnameNationaliteit> findAllRedenOpnameNationaliteit() {
        return em.createQuery("from RedenOpnameNationaliteit r left join fetch r.redenVerkrijgingNLNationaliteit", RedenOpnameNationaliteit.class)
                .setHint(QueryHints.CACHEABLE, Boolean.TRUE)
                .setHint(QueryHints.FETCH_SIZE, STAMGEGEVEN_LIJST_FETCH_SIZE)
                .getResultList();
    }

    @Override
    public final List<RedenBeeindigingNationaliteit> findAllRedenBeeindigingNationaliteit() {
        return em.createQuery("from RedenBeeindigingNationaliteit r left join fetch r.redenVerliesNLNationaliteit", RedenBeeindigingNationaliteit.class)
                .setHint(QueryHints.CACHEABLE, Boolean.TRUE)
                .setHint(QueryHints.FETCH_SIZE, STAMGEGEVEN_LIJST_FETCH_SIZE)
                .getResultList();
    }

    @Override
    public final List<Voorvoegsel> findAllVoorvoegsels() {
        return em.createQuery("from Voorvoegsel", Voorvoegsel.class)
                .setHint(QueryHints.CACHEABLE, Boolean.TRUE)
                .setHint(QueryHints.FETCH_SIZE, STAMGEGEVEN_LIJST_FETCH_SIZE)
                .getResultList();
    }

    @Override
    public final SoortDocument findSoortDocumentConversie(final String naam) {
        return em.createQuery("from SoortDocument WHERE naam = :naam", SoortDocument.class)
                .setParameter("naam", naam)
                .setHint(QueryHints.CACHEABLE, Boolean.TRUE)
                .setFlushMode(FlushModeType.COMMIT)
                .getSingleResult();
    }


    @Override
    public final Collection<String> findAllAutorisatiesVanAfgifteBuitenlandsPersoonsnummer() {
        return em.createQuery("select nationaliteit.code from AutoriteitAfgifteBuitenlandsPersoonsnummer", String.class)
                .setHint(QueryHints.CACHEABLE, Boolean.TRUE)
                .setHint(QueryHints.FETCH_SIZE, STAMGEGEVEN_LIJST_FETCH_SIZE)
                .getResultList();
    }
}
