/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.query;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Predicate builder om te zoeken naar verschillende voorwaarden.
 */
public final class OrPredicateBuilder implements PredicateBuilder {

    private final List<PredicateBuilder> predicateBuilders;

    /**
     * Constructor.
     *
     * @param predicateBuilders predicate builders
     */
    public OrPredicateBuilder(final List<PredicateBuilder> predicateBuilders) {
        this.predicateBuilders = predicateBuilders;
    }

    @Override
    public Predicate toPredicate(final Root<?> root, final CriteriaQuery<?> query, final CriteriaBuilder cb) {
        final List<Predicate> predicates = new ArrayList<>();

        for (final PredicateBuilder predicateBuilder : predicateBuilders) {
            final Predicate predicate = predicateBuilder.toPredicate(root, query, cb);
            if (predicate != null) {
                predicates.add(predicate);
            }
        }

        return cb.or(predicates.toArray(new Predicate[] {}));
    }

}
