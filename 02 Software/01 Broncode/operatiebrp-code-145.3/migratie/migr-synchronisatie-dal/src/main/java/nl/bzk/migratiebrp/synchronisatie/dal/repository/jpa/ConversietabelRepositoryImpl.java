/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.repository.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.AanduidingInhoudingVermissingReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AangifteAdreshouding;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdellijkeTitelPredikaat;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RNIDeelnemer;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenOntbindingHuwelijkPartnerschap;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenOpschorting;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortNlReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.VoorvoegselConversie;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.ConversietabelRepository;

import org.springframework.stereotype.Repository;

/**
 * De implementatie van de ConversietabelRepository interface.
 */
@Repository
public final class ConversietabelRepositoryImpl implements ConversietabelRepository {

    @PersistenceContext(name = "syncDalEntityManagerFactory", unitName = "BrpEntities")
    private EntityManager em;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AdellijkeTitelPredikaat> findAllAdellijkeTitelPredikaat() {
        return em.createQuery("from AdellijkeTitelPredikaat", AdellijkeTitelPredikaat.class).getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AangifteAdreshouding> findAllAangifteAdreshouding() {
        return em.createQuery(
                "from AangifteAdreshouding a left join fetch a.aangever left join fetch a.redenWijzigingVerblijf",
                AangifteAdreshouding.class).getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AanduidingInhoudingVermissingReisdocument> findAllAanduidingInhoudingVermissingReisdocument() {
        return em.createQuery(
                "from AanduidingInhoudingVermissingReisdocument a join fetch a.aanduidingInhoudingOfVermissingReisdocument",
                AanduidingInhoudingVermissingReisdocument.class).getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RedenOntbindingHuwelijkPartnerschap> findAllRedenOntbindingHuwelijkPartnerschap() {
        return em.createQuery("from RedenOntbindingHuwelijkPartnerschap r join fetch r.redenBeeindigingRelatie", RedenOntbindingHuwelijkPartnerschap.class)
                .getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RedenOpschorting> findAllRedenOpschorting() {
        return em.createQuery("from RedenOpschorting", RedenOpschorting.class).getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RNIDeelnemer> findAllRNIDeelnemer() {
        return em.createQuery(
                "from RNIDeelnemer d join fetch d.partij p left join fetch p.partijRolSet left join fetch p.hisPartijen "
                        + "left join fetch p.partijBijhoudingHistorieSet",
                RNIDeelnemer.class).getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SoortNlReisdocument> findAllSoortNlReisdocument() {
        return em.createQuery("from SoortNlReisdocument s join fetch s.soortNederlandsReisdocument", SoortNlReisdocument.class).getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<VoorvoegselConversie> findAllVoorvoegselConversie() {
        return em.createQuery("from VoorvoegselConversie", VoorvoegselConversie.class).getResultList();
    }

    @Override
    public List<String> findAllLo3Rubrieken() {
        return em.createQuery("select naam from Lo3Rubriek", String.class).getResultList();
    }
}
