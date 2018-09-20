/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.bzk.brp.dataaccess.repository.ActieRepository;
import nl.bzk.brp.model.operationeel.kern.ActieModel;

import org.springframework.stereotype.Repository;


/**
 * JPA implementatie van de {@link ActieRepository}. Middels deze repository worden met behulp van JPA acties
 * opgeslagen.
 */
@Repository
public class ActieJparepository implements ActieRepository {

    @PersistenceContext(unitName = "nl.bzk.brp.lezenschrijven")
    private EntityManager em;

    @Override
    public ActieModel opslaanNieuwActie(final ActieModel actieModel) {
        em.persist(actieModel);
        return actieModel;
    }
}
