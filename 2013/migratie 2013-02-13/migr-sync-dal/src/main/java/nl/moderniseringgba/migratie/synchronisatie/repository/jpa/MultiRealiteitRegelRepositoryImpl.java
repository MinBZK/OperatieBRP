/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.repository.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.MultiRealiteitRegel;
import nl.moderniseringgba.migratie.synchronisatie.repository.MultiRealiteitRegelRepository;

import org.springframework.stereotype.Repository;

/**
 * Data-access punt voor MultiRealiteitRegel.
 */
@Repository
public final class MultiRealiteitRegelRepositoryImpl implements MultiRealiteitRegelRepository {

    @PersistenceContext(name = "entityManagerFactory", unitName = "entityManagerFactory")
    private EntityManager em;

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(final MultiRealiteitRegel multiRealiteitRegel) {
        em.remove(multiRealiteitRegel);
    }

}
