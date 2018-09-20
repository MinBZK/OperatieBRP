/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.repository.jpa;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.Leveringsautorisatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.ToegangLeveringsAutorisatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.DynamischeStamtabel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AanduidingInhoudingOfVermissingReisdocument;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Aangever;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Gemeente;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.LandOfGebied;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Nationaliteit;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Partij;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PartijRol;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.RedenBeeindigingRelatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.RedenVerkrijgingNLNationaliteit;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.RedenVerliesNLNationaliteit;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.RedenWijzigingVerblijf;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Rol;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortDocument;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortNederlandsReisdocument;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortPartij;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Stelsel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Verblijfsrecht;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.conversietabel.entity.Lo3Rubriek;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.DynamischeStamtabelRepository;
import org.hibernate.annotations.QueryHints;
import org.springframework.stereotype.Repository;

/**
 * De implementatie van de DynamischeStamtabel interface.
 */
@Repository
public final class DynamischeStamtabelRepositoryImpl implements DynamischeStamtabelRepository {

    private static final String COL_CODE = "code";
    private static final String COL_NAAM = "naam";
    private static final String COL_PARTIJ = "partij";
    private static final String COL_ROL = "rol";
    private static final Integer DEFAULT_FETCH_SIZE = Integer.valueOf(100);
    @PersistenceContext(name = "syncDalEntityManagerFactory", unitName = "BrpEntities")
    private EntityManager em;

    /**
     * {@inheritDoc}
     */
    @Override
    public Aangever getAangeverByCode(final char code) {
        return getUniqueResult(Aangever.class, COL_CODE, code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LandOfGebied getLandOfGebiedByCode(final Short code) {
        return getUniqueResult(LandOfGebied.class, COL_CODE, code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Nationaliteit getNationaliteitByNationaliteitcode(final Short nationaliteitcode) {
        return getUniqueResult(Nationaliteit.class, COL_CODE, nationaliteitcode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Gemeente getGemeenteByGemeentecode(final short gemeentecode) {
        return getUniqueResult(Gemeente.class, COL_CODE, gemeentecode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Gemeente getGemeenteByPartij(final Partij partij) {
        return getUniqueResult(Gemeente.class, COL_PARTIJ, partij);
    }

    @Override
    public SoortPartij getSoortPartijByNaam(final String soortPartij) {
        return getUniqueResult(SoortPartij.class, COL_NAAM, soortPartij);
    }

    @Override
    public Partij getPartijByCode(final int code) {
        return getUniqueResult(Partij.class, COL_CODE, code);
    }

    @Override
    public Partij findPartijByCode(final int code) {
        final TypedQuery<Partij> query = em.createQuery("from Partij where code = :code", Partij.class);
        query.setParameter(COL_CODE, code);
        final List<Partij> result = query.getResultList();
        if (result.isEmpty()) {
            return null;
        } else {
            return result.get(0);
        }
    }

    @Override
    public Partij findPartijByNaam(final String naam) {
        final TypedQuery<Partij> query = em.createQuery("from Partij where naam = :naam", Partij.class);
        query.setParameter(COL_NAAM, naam);
        final List<Partij> result = query.getResultList();
        if (result.isEmpty()) {
            return null;
        } else {
            return result.get(0);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RedenBeeindigingRelatie getRedenBeeindigingRelatieByCode(final char code) {
        return getUniqueResult(RedenBeeindigingRelatie.class, COL_CODE, code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RedenVerkrijgingNLNationaliteit getRedenVerkrijgingNLNationaliteitByCode(final Short code) {
        return getUniqueResult(RedenVerkrijgingNLNationaliteit.class, COL_CODE, code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RedenVerliesNLNationaliteit getRedenVerliesNLNationaliteitByCode(final Short code) {
        return getUniqueResult(RedenVerliesNLNationaliteit.class, COL_CODE, code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AanduidingInhoudingOfVermissingReisdocument getAanduidingInhoudingOfVermissingReisdocumentByCode(final char code) {
        return getUniqueResult(AanduidingInhoudingOfVermissingReisdocument.class, COL_CODE, code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RedenWijzigingVerblijf getRedenWijzigingVerblijf(final char code) {
        return getUniqueResult(RedenWijzigingVerblijf.class, COL_CODE, code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SoortDocument getSoortDocumentByNaam(final String naam) {
        return getUniqueResult(SoortDocument.class, COL_NAAM, naam);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SoortNederlandsReisdocument getSoortNederlandsReisdocumentByCode(final String code) {
        return getUniqueResult(SoortNederlandsReisdocument.class, COL_CODE, code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Verblijfsrecht getVerblijfsrechtByCode(final Short code) {
        return getUniqueResult(Verblijfsrecht.class, COL_CODE, code);
    }

    /* Private methods */
    private <T extends DynamischeStamtabel, V> T getUniqueResult(final Class<T> resultClass, final String paramName, final V paramValue) {
        final List<String> paramNames = new ArrayList<>();
        paramNames.add(paramName);
        final List<V> paramValues = new ArrayList<>();
        paramValues.add(paramValue);

        return getUniqueResultMultipleParam(resultClass, paramNames, paramValues);
    }

    private <T extends DynamischeStamtabel, V> T getUniqueResultMultipleParam(
        final Class<T> resultClass,
        final List<String> paramNames,
        final List<V> paramValues)
    {
        try {
            // Set hibernate cacheable hint to trigger query-cache (if enabled, like in migr-ggo-viewer).
            final StringBuilder query = new StringBuilder();
            query.append("FROM ").append(resultClass.getSimpleName()).append(" WHERE ");

            for (int counter = 0; counter < paramNames.size(); counter++) {
                if (counter > 0) {
                    query.append(" AND ");
                }
                query.append(paramNames.get(counter)).append(" = :").append(paramNames.get(counter));
            }

            final TypedQuery<T> typedQuery = em.createQuery(query.toString(), resultClass);

            for (int counter = 0; counter < paramNames.size(); counter++) {
                typedQuery.setParameter(paramNames.get(counter), paramValues.get(counter));
            }

            return typedQuery.setHint(QueryHints.CACHEABLE, Boolean.TRUE).setFlushMode(FlushModeType.COMMIT).getSingleResult();
        } catch (final NoResultException nre) {
            throw new IllegalArgumentException(
                String.format("Er is geen %s entity gevonden waarbij %s=%s", resultClass.getSimpleName(), paramNames.get(0), paramValues.get(0)),
                nre);
        } catch (final NonUniqueResultException nure) {
            throw new IllegalArgumentException(
                String.format("Er is geen unieke %s entity gevonden waarbij %s=%s", resultClass.getSimpleName(), paramNames.get(0), paramValues.get(0)),
                nure);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Partij savePartij(final Partij partij) {
        if (partij.getId() == null) {
            em.persist(partij);
            return partij;
        } else {
            return em.merge(partij);
        }
    }

    @Override
    public PartijRol getPartijRolByPartij(final Partij partij, final Rol rol) {
        final TypedQuery<PartijRol> query = em.createQuery("from PartijRol where partij = :partij and rol = :rol", PartijRol.class);
        query.setParameter(COL_PARTIJ, partij);
        query.setParameter(COL_ROL, rol.getId());
        final List<PartijRol> result = query.getResultList();
        if (result.isEmpty()) {
            return null;
        } else {
            return result.get(0);
        }
    }

    @Override
    public PartijRol savePartijRol(final PartijRol partijRol) {
        if (partijRol.getId() == null) {
            em.persist(partijRol);
            return partijRol;
        } else {
            return em.merge(partijRol);
        }
    }

    @Override
    public ToegangLeveringsAutorisatie saveToegangLeveringsAutorisatie(final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie) {
        if (toegangLeveringsAutorisatie.getId() == null) {
            em.persist(toegangLeveringsAutorisatie);
            return toegangLeveringsAutorisatie;
        } else {
            return em.merge(toegangLeveringsAutorisatie);
        }
    }

    @Override
    public List<Leveringsautorisatie> geefAlleGbaLeveringsautorisaties() {
        final TypedQuery<Leveringsautorisatie> query =
                em.createQuery(
                    "from Leveringsautorisatie where stelselId = :stelselId and (datumEinde is null or datumEinde >= :datumEinde)",
                    Leveringsautorisatie.class);
        query.setParameter("stelselId", Stelsel.GBA.getId());
        query.setParameter("datumEinde", vandaag());
        return query.setHint(QueryHints.FETCH_SIZE, DEFAULT_FETCH_SIZE).getResultList();
    }

    private Integer vandaag() {
        return Integer.valueOf(new SimpleDateFormat("yyyyMMdd").format(new Date()));
    }

    @Override
    public List<ToegangLeveringsAutorisatie> getToegangLeveringsautorisatieByPartijEnDatumIngang(
        final PartijRol geautoriseerde,
        final Integer datumIngang)
    {
        final TypedQuery<ToegangLeveringsAutorisatie> query =
                em.createQuery(
                    "from ToegangLeveringsAutorisatie where geautoriseerde = :geautoriseerde and datingang >= :datingang",
                    ToegangLeveringsAutorisatie.class);
        query.setParameter("geautoriseerde", geautoriseerde);
        query.setParameter("datingang", datumIngang);
        return query.getResultList();
    }

    @Override
    public Lo3Rubriek getLo3RubriekByNaam(final String naam) {
        return getUniqueResult(Lo3Rubriek.class, COL_NAAM, naam);
    }
}
