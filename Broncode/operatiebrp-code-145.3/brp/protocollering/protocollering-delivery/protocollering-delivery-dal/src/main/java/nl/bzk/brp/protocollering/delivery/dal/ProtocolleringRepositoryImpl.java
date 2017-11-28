/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.protocollering.delivery.dal;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsaantekening;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LeveringsaantekeningPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ScopePatroon;
import nl.bzk.brp.protocollering.service.dal.ProtocolleringRepository;
import org.springframework.stereotype.Repository;

/**
 * Implementatie van de LeveringRepository.
 */
@Repository
public final class ProtocolleringRepositoryImpl implements ProtocolleringRepository {

    @PersistenceContext(unitName = "nl.bzk.brp.protocollering")
    private EntityManager em;

    @Override
    public void opslaanNieuweLevering(final Leveringsaantekening leveringsaantekening) {
        if (leveringsaantekening.getScopePatroon() != null) {
            final ScopePatroon scopePatroon = em.getReference(ScopePatroon.class, leveringsaantekening.getScopePatroon().getId());
            leveringsaantekening.setScopePatroon(scopePatroon);
        }
        em.persist(leveringsaantekening);
    }

    @Override
    public void opslaanNieuwScopePatroon(final ScopePatroon scopePatroon) {
        em.persist(scopePatroon);
    }

    @Override
    public List<ScopePatroon> getScopePatronen() {
        //inner join fetch om alles in één keer op te halen, overrides de eager annotatie voor de 1-N relatie.
        return em.createQuery("SELECT c from ScopePatroon c inner join fetch c.scopePatroonElementSet", ScopePatroon.class).getResultList();
    }

    @Override
    public void slaBulkPersonenOpBijLeveringsaantekening(final Leveringsaantekening leveringsaantekening,
                                                         final List<LeveringsaantekeningPersoon> persoonList) {
        //Leveringsaantekening nooit opnieuw ophalen, en in de verleiding komen personen hier aan toe te voegen.
        //potentieel haal je dan onnodig alle personen op.
        final Leveringsaantekening reference = em.getReference(Leveringsaantekening.class, leveringsaantekening.getId());
        persoonList.forEach(leveringsaantekeningPersoon -> leveringsaantekeningPersoon.setLeveringsaantekening(reference));
        persoonList.forEach(leveringsaantekeningPersoon -> em.persist(leveringsaantekeningPersoon));
    }
}
