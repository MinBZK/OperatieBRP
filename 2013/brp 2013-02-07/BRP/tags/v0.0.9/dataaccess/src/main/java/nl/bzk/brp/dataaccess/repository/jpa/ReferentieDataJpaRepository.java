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
import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.model.gedeeld.Land;
import nl.bzk.brp.model.gedeeld.Partij;
import nl.bzk.brp.model.gedeeld.Plaats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;


/**
 * Repository voor referentieData en standaard implementatie van de {@link ReferentieDataRepository} class.
 */
@Repository
class ReferentieDataJpaRepository implements ReferentieDataRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReferentieDataJpaRepository.class);

    @PersistenceContext
    private EntityManager       em;

    /**
     * {@inheritDoc}
     */
    @Override
    public Plaats findWoonplaatsOpCode(final String code) {
        // TODO hosing: moet naar een andere repository moet
        final String sql = "SELECT plaats FROM Plaats plaats WHERE plaats.woonplaatscode = :code";
        try {
            return (Plaats) em.createQuery(sql).setParameter("code", code).getSingleResult();
        } catch (NoResultException e) {
            LOGGER.info("Onbekende woonplaatscode '{}' niet gevonden.", code);
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.PLAATSCODE, code, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Partij vindGemeenteOpCode(final String code) {
        // TODO hosing: moet naar een andere repository moet
        final String sql = "SELECT partij FROM Partij partij WHERE partij.gemeentecode = :code";
        try {
            return (Partij) em.createQuery(sql).setParameter("code", code).getSingleResult();
        } catch (NoResultException e) {
            LOGGER.info("Onbekende gemeentecode '{}' niet gevonden.", code);
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.GEMEENTECODE, code, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Land vindLandOpCode(final String code) {
        // TODO hosing: moet naar een andere repository moet
        final String sql = "SELECT land FROM Land land WHERE land.landcode = :code";
        try {
            return (Land) em.createQuery(sql).setParameter("code", code).getSingleResult();
        } catch (NoResultException e) {
            LOGGER.info("Onbekende landcode '{}' niet gevonden.", code);
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.LANDCODE, code, e);
        }
    }
}
