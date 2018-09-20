/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.bzk.brp.dataaccess.repository.AdministratieveHandelingRepository;
import nl.bzk.brp.model.logisch.kern.AdministratieveHandeling;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;

import org.springframework.stereotype.Repository;

/**
 * Repository voor de {@link nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel} class en standaard
 * implementatie van de {@link nl.bzk.brp.dataaccess.repository.AdministratieveHandelingRepository} class.
 */
@Repository
public final class AdministratieveHandelingJpaRepository implements AdministratieveHandelingRepository {

    @PersistenceContext(unitName = "nl.bzk.brp.lezenschrijven")
    private EntityManager em;

    @Override
    public AdministratieveHandelingModel opslaanNieuwAdministratieveHandeling(
            final AdministratieveHandeling administratieveHandeling)
    {
        // JIRA Issue SIERRA-458: hoe moeten de bijgehouden documenten opgeslagen/aangepast worden?
        final AdministratieveHandelingModel model = new AdministratieveHandelingModel(administratieveHandeling);
        em.persist(model);

        return model;
    }

    @Override
    public AdministratieveHandelingModel haalAdministratieveHandeling(final Long administratieveHandelingId) {
        return em.find(AdministratieveHandelingModel.class, administratieveHandelingId);
    }

    @Override
    public AdministratieveHandelingModel opslaanAdministratieveHandeling(final AdministratieveHandelingModel administratieveHandeling) {
        final AdministratieveHandelingModel modified;
        if (em.contains(administratieveHandeling)) {
            modified = em.merge(administratieveHandeling);
        } else {
            em.persist(administratieveHandeling);
            modified = administratieveHandeling;
        }

        return modified;
    }
}
