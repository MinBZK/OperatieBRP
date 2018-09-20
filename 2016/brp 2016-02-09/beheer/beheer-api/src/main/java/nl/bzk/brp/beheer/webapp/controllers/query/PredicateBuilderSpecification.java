/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.query;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

/**
 * JPA Specification builder obv predicate builders.
 *
 * @param <T> root entity type
 */
public final class PredicateBuilderSpecification<T> implements Specification<T> {

    private final List<PredicateBuilder> predicateBuilders = new ArrayList<>();

    /**
     * Voeg een predicate builder toe.
     *
     * @param predicateBuilder predicate builder
     */
    public void addPredicateBuilder(final PredicateBuilder predicateBuilder) {
        predicateBuilders.add(predicateBuilder);
    }

    @Override
    public Predicate toPredicate(final Root<T> root, final CriteriaQuery<?> query, final CriteriaBuilder cb) {
        final List<Predicate> predicates = new ArrayList<>();

        for (final PredicateBuilder predicateBuilder : predicateBuilders) {
            final Predicate predicate = predicateBuilder.toPredicate(root, query, cb);
            if (predicate != null) {
                predicates.add(predicate);
            }
        }

        return cb.and(predicates.toArray(new Predicate[] {}));
    }

    /**
     * controleer of er predicates zijn. Soms is het nodig om geen resultaten te retourneren indien
     * er geen geldige parameters zijn opgegeven.
     * @return indicatie of er predicates zijn
     */
    public boolean isPredicateListEmpty() {
        return predicateBuilders.isEmpty();
    }
}
