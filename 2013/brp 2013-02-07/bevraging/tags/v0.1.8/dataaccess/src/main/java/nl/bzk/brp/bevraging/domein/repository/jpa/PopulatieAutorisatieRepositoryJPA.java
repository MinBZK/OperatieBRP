/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein.repository.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import nl.bzk.brp.bevraging.domein.repository.PopulatieAutorisatieRepository;
import org.springframework.stereotype.Repository;


/**
 * Standaard JPA implementatie van de {@link PopulatieAutorisatieRepository} interface.
 */
@Repository
public class PopulatieAutorisatieRepositoryJPA implements PopulatieAutorisatieRepository {

    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("unchecked")
    @Override
    public List<Long> filterPersonenBijFilter(final List<Long> persoonIds, final String... filters) {
        List<String> valideFilters = filterValideFilters(filters);

        if (valideFilters.isEmpty()) {
            return persoonIds;
        }

        StringBuilder queryString = new StringBuilder("SELECT p.id FROM Persoon p WHERE p.id IN :persoonIds");
        for (String filter : valideFilters) {
            queryString.append(" AND (").append(filter).append(")");
        }
        Query query = em.createQuery(queryString.toString());
        query.setParameter("persoonIds", persoonIds);

        return query.getResultList();
    }

    /**
     * Retourneert een lijst van de valide opgegeven filters, waarin alleen de niet <code>null</code> en niet lege
     * filters worden opgenomen.
     *
     * @param filters de filters die moeten worde gefilterd.
     * @return een lijst van de valide filters.
     */
    private List<String> filterValideFilters(final String[] filters) {
        List<String> valideFilters = new ArrayList<String>();
        for (String filter : filters) {
            if (filter != null && !filter.trim().isEmpty()) {
                valideFilters.add(filter);
            }
        }
        return valideFilters;
    }

}
