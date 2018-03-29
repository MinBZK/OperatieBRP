/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Is null predicate builder.
 */
public final class IsNullPredicateBuilder implements PredicateBuilder {

    private final String naam;

    /**
     * Constructor.
     *
     * @param naam attribuut naam
     */
    IsNullPredicateBuilder(final String naam) {
        this.naam = naam;
    }

    @Override
    public Predicate toPredicate(final Root<?> root, final CriteriaQuery<?> query, final CriteriaBuilder cb) {
        return cb.isNull(PredicateBuilderUtil.getPath(root, naam));
    }

}
