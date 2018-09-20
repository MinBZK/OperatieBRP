/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import nl.bzk.brp.dataaccess.exceptie.OnbekendeReferentieExceptie;
import nl.bzk.brp.dataaccess.repository.ReferentieDataMdlRepository;
import nl.bzk.brp.model.attribuuttype.AangeverAdreshoudingCode;
import nl.bzk.brp.model.attribuuttype.GemeenteCode;
import nl.bzk.brp.model.attribuuttype.LandCode;
import nl.bzk.brp.model.attribuuttype.NationaliteitCode;
import nl.bzk.brp.model.attribuuttype.PlaatsCode;
import nl.bzk.brp.model.attribuuttype.RedenWijzigingAdresCode;
import nl.bzk.brp.model.objecttype.statisch.AangeverAdreshouding;
import nl.bzk.brp.model.objecttype.statisch.Land;
import nl.bzk.brp.model.objecttype.statisch.Nationaliteit;
import nl.bzk.brp.model.objecttype.statisch.Partij;
import nl.bzk.brp.model.objecttype.statisch.Plaats;
import nl.bzk.brp.model.objecttype.statisch.RedenWijzigingAdres;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;


/**
 * Repository voor referentieData en standaard implementatie van de {@link ReferentieDataMdlRepository} class.
 */
@Repository
class ReferentieDataMdlJpaRepository implements ReferentieDataMdlRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReferentieDataMdlJpaRepository.class);

    @PersistenceContext
    private EntityManager       em;

    /**
     * {@inheritDoc}
     */
    @Override
    public Plaats vindWoonplaatsOpCode(final PlaatsCode code) {
        // TODO hosing: moet naar een andere repository moet
        final String sql = "SELECT plaats FROM PlaatsMdl plaats WHERE plaats.code = :code";
        try {
            return (Plaats) em.createQuery(sql).setParameter("code", code).getSingleResult();
        } catch (NoResultException e) {
            LOGGER.info("Onbekende woonplaatscode '{}' niet gevonden.", code);
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.PLAATSCODE,
                    code.getWaarde(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Partij vindGemeenteOpCode(final GemeenteCode code) {
        // TODO hosing: moet naar een andere repository moet
        final String sql = "SELECT partij FROM PartijMdl partij WHERE partij.gemeenteCode = :code";
        try {
            return (Partij) em.createQuery(sql).setParameter("code", code).getSingleResult();
        } catch (NoResultException e) {
            LOGGER.info("Onbekende gemeentecode '{}' niet gevonden.", code);
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.GEMEENTECODE,
                    code.getWaarde(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Land vindLandOpCode(final LandCode code) {
        // TODO hosing: moet naar een andere repository moet
        final String sql = "SELECT land FROM LandMdl land WHERE land.landCode = :code";
        try {
            return (Land) em.createQuery(sql).setParameter("code", code).getSingleResult();
        } catch (NoResultException e) {
            LOGGER.info("Onbekende landcode '{}' niet gevonden.", code);
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.LANDCODE,
                    code.getWaarde(), e);
        }
    }

    @Override
    public Nationaliteit vindNationaliteitOpCode(final NationaliteitCode code) {
        final String sql = "SELECT nation FROM NationaliteitMdl nation WHERE nation.nationaliteitCode = :code";
        try {
            return (Nationaliteit) em.createQuery(sql).setParameter("code", code).getSingleResult();
        } catch (NoResultException e) {
            LOGGER.info("Onbekende nationaliteit code, '{}' niet gevonden.", code);
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.NATIONALITITEITCODE,
                    code.getWaarde(), e);
        }
    }

    @Override
    public RedenWijzigingAdres vindRedenWijzingAdresOpCode(final RedenWijzigingAdresCode code) {
        final String sql = "SELECT rdnWijzAdres FROM RedenWijzigingAdres rdnWijzAdres WHERE "
                + " rdnWijzAdres.redenWijzigingAdresCode = :code";
        try {
            return (RedenWijzigingAdres) em.createQuery(sql).setParameter("code", code).getSingleResult();
        } catch (NoResultException e) {
            LOGGER.info("Onbekende RedenWijzigingAdres code, '{}' niet gevonden.", code);
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.REDENWIJZINGADRES,
                    code.getWaarde(), e);
        }
    }

    @Override
    public AangeverAdreshouding vindAangeverAdreshoudingOpCode(final AangeverAdreshoudingCode code) {
        final String sql = "SELECT aangAdresh FROM AangeverAdreshouding aangAdresh WHERE "
                + " aangAdresh.aangeverAdreshoudingCode = :code";
        try {
            return (AangeverAdreshouding) em.createQuery(sql).setParameter("code", code).getSingleResult();
        } catch (NoResultException e) {
            LOGGER.info("Onbekende AangeverAdreshouding code, '{}' niet gevonden.", code);
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.AAANGEVERADRESHOUDING,
                    code.getWaarde(), e);
        }
    }
}
