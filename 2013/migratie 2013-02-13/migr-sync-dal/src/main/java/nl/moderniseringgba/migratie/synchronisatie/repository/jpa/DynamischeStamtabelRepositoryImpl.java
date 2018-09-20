/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.repository.jpa;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.DynamischeStamtabel;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.AangeverAdreshouding;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.AutoriteitVanAfgifteReisdocument;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Land;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Nationaliteit;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Partij;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Plaats;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.RedenBeeindigingRelatie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.RedenVerkrijgingNLNationaliteit;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.RedenVerliesNLNationaliteit;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.RedenVervallenReisdocument;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.RedenWijzigingAdres;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.SoortDocument;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.SoortNederlandsReisdocument;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.SoortVerificatie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Verblijfsrecht;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Verdrag;
import nl.moderniseringgba.migratie.synchronisatie.repository.DynamischeStamtabelRepository;

import org.springframework.stereotype.Repository;

/**
 * De implementatie van de DynamischeStamtabel interface.
 */
// CHECKSTYLE:OFF Class Fan-Out Complexity is hier groter dan 20 maar maakt deze class niet onnodig complex
@Repository
public final class DynamischeStamtabelRepositoryImpl implements DynamischeStamtabelRepository {
    // CHECKSTYLE:ON

    private static final String COL_OMSCHRIJVING = "omschrijving";
    private static final String COL_NAAM = "naam";
    private static final String COL_CODE = "code";

    @PersistenceContext(name = "entityManagerFactory", unitName = "entityManagerFactory")
    private EntityManager em;

    /**
     * {@inheritDoc}
     */
    @Override
    public AangeverAdreshouding findAangeverAdreshoudingByCode(final String code) {
        return getUniqueResult(AangeverAdreshouding.class, COL_CODE, code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AutoriteitVanAfgifteReisdocument findAutoriteitVanAfgifteReisdocumentByCode(final String code) {
        return getUniqueResult(AutoriteitVanAfgifteReisdocument.class, COL_CODE, code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Land findLandByLandcode(final BigDecimal landcode) {
        return getUniqueResult(Land.class, "landcode", landcode, BigDecimal.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Nationaliteit findNationaliteitByNationaliteitcode(final BigDecimal nationaliteitcode) {
        return getUniqueResult(Nationaliteit.class, "nationaliteitcode", nationaliteitcode, BigDecimal.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Partij findPartijByGemeentecode(final BigDecimal gemeentecode) {
        return getUniqueResult(Partij.class, "gemeentecode", gemeentecode, BigDecimal.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Partij findPartijByNaam(final String naam) {
        return getUniqueResult(Partij.class, COL_NAAM, naam);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Plaats findPlaatsByWoonplaatscode(final String woonplaatscode) {
        return getUniqueResult(Plaats.class, "woonplaatscode", woonplaatscode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Plaats findPlaatsByNaam(final String naam) {
        return getUniqueResult(Plaats.class, COL_NAAM, naam);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RedenBeeindigingRelatie findRedenBeeindigingRelatieByCode(final String code) {
        return getUniqueResult(RedenBeeindigingRelatie.class, COL_CODE, code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RedenVerkrijgingNLNationaliteit findRedenVerkrijgingNLNationaliteitByNaam(final BigDecimal naam) {
        return getUniqueResult(RedenVerkrijgingNLNationaliteit.class, COL_NAAM, naam, BigDecimal.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RedenVerliesNLNationaliteit findRedenVerliesNLNationaliteitByNaam(final BigDecimal naam) {
        return getUniqueResult(RedenVerliesNLNationaliteit.class, COL_NAAM, naam, BigDecimal.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RedenVervallenReisdocument findRedenVervallenReisdocumentByCode(final String code) {
        return getUniqueResult(RedenVervallenReisdocument.class, COL_CODE, code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RedenWijzigingAdres findRedenWijzigingAdres(final String code) {
        return getUniqueResult(RedenWijzigingAdres.class, COL_CODE, code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SoortDocument findSoortDocumentByOmschrijving(final String omschrijving) {
        return getUniqueResult(SoortDocument.class, COL_OMSCHRIJVING, omschrijving);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SoortNederlandsReisdocument findSoortNederlandsReisdocumentByCode(final String code) {
        return getUniqueResult(SoortNederlandsReisdocument.class, COL_CODE, code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SoortVerificatie findSoortVerificatieByNaam(final String naam) {
        return getUniqueResult(SoortVerificatie.class, COL_NAAM, naam);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Verblijfsrecht findVerblijfsrechtByOmschrijving(final String omschrijving) {
        return getUniqueResult(Verblijfsrecht.class, COL_OMSCHRIJVING, omschrijving);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Verdrag findVerdragByOmschrijving(final String omschrijving) {
        return getUniqueResult(Verdrag.class, COL_OMSCHRIJVING, omschrijving);
    }

    /* Private methods */
    private <T extends DynamischeStamtabel> T getUniqueResult(
            final Class<T> resultClass,
            final String paramName,
            final String paramValue) {
        return getUniqueResult(resultClass, paramName, paramValue, String.class);
    }

    /* Private methods */
    private <T extends DynamischeStamtabel, V extends Object> T getUniqueResult(
            final Class<T> resultClass,
            final String paramName,
            final V paramValue,
            final Class<V> paramClass) {
        final CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        final CriteriaQuery<T> query = criteriaBuilder.createQuery(resultClass);
        final Root<T> root = query.from(resultClass);
        final ParameterExpression<V> param = criteriaBuilder.parameter(paramClass);
        try {
            return em.createQuery(query.select(root).where(criteriaBuilder.equal(root.get(paramName), param)))
                    .setParameter(param, paramValue).getSingleResult();
        } catch (final NoResultException nre) {
            throw new IllegalArgumentException(String.format("Er is geen %s entity gevonden waarbij %s=%s",
                    resultClass.getSimpleName(), paramName, paramValue), nre);
        } catch (final NonUniqueResultException nure) {
            throw new IllegalArgumentException(String.format("Er is geen unieke %s entity gevonden waarbij %s=%s",
                    resultClass.getSimpleName(), paramName, paramValue), nure);
        }
    }
}
