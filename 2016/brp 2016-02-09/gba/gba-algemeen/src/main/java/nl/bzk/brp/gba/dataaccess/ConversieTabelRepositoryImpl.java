/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nl.bzk.brp.model.algemeen.stamgegeven.conv.ConversieAanduidingInhoudingVermissingReisdocument;
import nl.bzk.brp.model.algemeen.stamgegeven.conv.ConversieAangifteAdreshouding;
import nl.bzk.brp.model.algemeen.stamgegeven.conv.ConversieAdellijkeTitelPredikaat;
import nl.bzk.brp.model.algemeen.stamgegeven.conv.ConversieRNIDeelnemer;
import nl.bzk.brp.model.algemeen.stamgegeven.conv.ConversieRedenOntbindingHuwelijkGeregistreerdPartnerschap;
import nl.bzk.brp.model.algemeen.stamgegeven.conv.ConversieRedenOpschorting;
import nl.bzk.brp.model.algemeen.stamgegeven.conv.ConversieSoortNLReisdocument;
import nl.bzk.brp.model.algemeen.stamgegeven.conv.ConversieVoorvoegsel;
import org.springframework.stereotype.Repository;


/**
 * ConversieTabel repository.
 */
@Repository("gbaConversieTabelRepository")
public class ConversieTabelRepositoryImpl implements ConversieTabelRepository {

    @PersistenceContext(unitName = "nl.bzk.brp.alleenlezen")
    private EntityManager entityManager;

    @Override
    public final List<ConversieAangifteAdreshouding> findAllAangifteAdreshouding() {
        return entityManager.createQuery("from ConversieAangifteAdreshouding", ConversieAangifteAdreshouding.class).getResultList();
    }

    @Override
    public final List<ConversieAdellijkeTitelPredikaat> findAllAdellijkeTitelPredikaat() {
        return entityManager.createQuery("from ConversieAdellijkeTitelPredikaat", ConversieAdellijkeTitelPredikaat.class).getResultList();
    }

    @Override
    public final List<ConversieAanduidingInhoudingVermissingReisdocument> findAllAanduidingInhoudingVermissingReisdocument() {
        return entityManager.createQuery("from ConversieAanduidingInhoudingVermissingReisdocument",
                ConversieAanduidingInhoudingVermissingReisdocument.class).getResultList();
    }

    @Override
    public final List<ConversieRedenOntbindingHuwelijkGeregistreerdPartnerschap> findAllRedenOntbindingHuwelijkPartnerschap() {
        return entityManager.createQuery("from ConversieRedenOntbindingHuwelijkGeregistreerdPartnerschap",
                ConversieRedenOntbindingHuwelijkGeregistreerdPartnerschap.class).getResultList();
    }

    @Override
    public final List<ConversieRedenOpschorting> findAllRedenOpschorting() {
        return entityManager.createQuery("from ConversieRedenOpschorting", ConversieRedenOpschorting.class).getResultList();
    }

    @Override
    public final List<ConversieSoortNLReisdocument> findAllSoortNlReisdocument() {
        return entityManager.createQuery("from ConversieSoortNLReisdocument", ConversieSoortNLReisdocument.class).getResultList();
    }

    @Override
    public final List<ConversieVoorvoegsel> findAllVoorvoegsel() {
        return entityManager.createQuery("from ConversieVoorvoegsel", ConversieVoorvoegsel.class).getResultList();
    }

    @Override
    public final List<ConversieRNIDeelnemer> findAllRNIDeelnemer() {
        return entityManager.createQuery("from ConversieRNIDeelnemer", ConversieRNIDeelnemer.class).getResultList();
    }
}
