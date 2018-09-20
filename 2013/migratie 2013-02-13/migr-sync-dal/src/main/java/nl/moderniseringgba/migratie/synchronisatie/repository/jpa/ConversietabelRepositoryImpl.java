/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.repository.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.moderniseringgba.migratie.synchronisatie.domein.conversietabel.entity.AangifteAdreshouding;
import nl.moderniseringgba.migratie.synchronisatie.domein.conversietabel.entity.AdellijkeTitelPredikaat;
import nl.moderniseringgba.migratie.synchronisatie.domein.conversietabel.entity.AutoriteitVanAfgifte;
import nl.moderniseringgba.migratie.synchronisatie.domein.conversietabel.entity.RedenInhoudingVermissingReisdocument;
import nl.moderniseringgba.migratie.synchronisatie.domein.conversietabel.entity.RedenOntbindingHuwelijkPartnerschap;
import nl.moderniseringgba.migratie.synchronisatie.domein.conversietabel.entity.RedenOpschorting;
import nl.moderniseringgba.migratie.synchronisatie.domein.conversietabel.entity.RedenVerkrijgingVerliesNlSchap;
import nl.moderniseringgba.migratie.synchronisatie.domein.conversietabel.entity.SoortNlReisdocument;
import nl.moderniseringgba.migratie.synchronisatie.domein.conversietabel.entity.Verblijfstitel;
import nl.moderniseringgba.migratie.synchronisatie.domein.conversietabel.entity.Voorvoegsel;
import nl.moderniseringgba.migratie.synchronisatie.repository.ConversietabelRepository;

import org.springframework.stereotype.Repository;

/**
 * De implementatie van de ConversietabelRepository interface.
 */
@Repository
public final class ConversietabelRepositoryImpl implements ConversietabelRepository {

    @PersistenceContext(name = "entityManagerFactory", unitName = "entityManagerFactory")
    private EntityManager em;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Voorvoegsel> findAllVoorvoegsels() {
        return em.createQuery("from Voorvoegsel", Voorvoegsel.class).getResultList();
    }

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
        return em.createQuery("from AangifteAdreshouding", AangifteAdreshouding.class).getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AutoriteitVanAfgifte> findAllAutoriteitVanAfgifte() {
        return em.createQuery("from AutoriteitVanAfgifte", AutoriteitVanAfgifte.class).getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RedenInhoudingVermissingReisdocument> findAllRedenInhoudingVermissingReisdocument() {
        return em
                .createQuery("from RedenInhoudingVermissingReisdocument", RedenInhoudingVermissingReisdocument.class)
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
    public List<RedenVerkrijgingVerliesNlSchap> findAllRedenVerkrijgingNlSchap() {
        return em.createQuery("from RedenVerkrijgingVerliesNlSchap where brpRedenVerkrijgingNaam IS NOT NULL",
                RedenVerkrijgingVerliesNlSchap.class).getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RedenVerkrijgingVerliesNlSchap> findAllRedenVerliesNlSchap() {
        return em.createQuery("from RedenVerkrijgingVerliesNlSchap where brpRedenVerliesNaam IS NOT NULL",
                RedenVerkrijgingVerliesNlSchap.class).getResultList();
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
    public List<Verblijfstitel> findAllVerblijfstitel() {
        return em.createQuery("from Verblijfstitel", Verblijfstitel.class).getResultList();
    }
}
