/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.archivering.delivery.dal;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Bericht;
import nl.bzk.brp.archivering.service.dal.ArchiefBerichtRepository;
import org.springframework.stereotype.Repository;

/**
 * Implementatie van ArchiefBerichtRepository.
 */
@Repository
public final class ArchiefBerichtJpaRepositoryImpl implements ArchiefBerichtRepository {

    @PersistenceContext(unitName = "nl.bzk.brp.archivering")
    private EntityManager em;

    @Override
    public void accept(Bericht archiefBericht) {
        em.persist(archiefBericht);
    }
}
