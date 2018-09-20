/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.repository.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.conversietabel.entity.AanduidingInhoudingVermissingReisdocument;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.conversietabel.entity.AangifteAdreshouding;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.conversietabel.entity.AdellijkeTitelPredikaat;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.conversietabel.entity.RNIDeelnemer;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.conversietabel.entity.RedenOntbindingHuwelijkPartnerschap;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.conversietabel.entity.RedenOpschorting;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.conversietabel.entity.SoortNlReisdocument;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.conversietabel.entity.VoorvoegselConversie;
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
        return em.createQuery("from AdellijkeTitelPredikaat", AdellijkeTitelPredikaat.class)
                 .getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AangifteAdreshouding> findAllAangifteAdreshouding() {
        return em.createQuery("from AangifteAdreshouding", AangifteAdreshouding.class).getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AanduidingInhoudingVermissingReisdocument> findAllAanduidingInhoudingVermissingReisdocument() {
        return em.createQuery("from AanduidingInhoudingVermissingReisdocument", AanduidingInhoudingVermissingReisdocument.class)
                 .getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RedenOntbindingHuwelijkPartnerschap> findAllRedenOntbindingHuwelijkPartnerschap() {
        return em.createQuery("from RedenOntbindingHuwelijkPartnerschap", RedenOntbindingHuwelijkPartnerschap.class)
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
        return em.createQuery("from RNIDeelnemer", RNIDeelnemer.class).getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SoortNlReisdocument> findAllSoortNlReisdocument() {
        return em.createQuery("from SoortNlReisdocument", SoortNlReisdocument.class).getResultList();
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
