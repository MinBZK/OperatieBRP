/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Predicaat builder om naar een exacte waarde te zoeken.
 */
public final class EqualPredicateBuilder implements PredicateBuilder {

    private final String naam;
    private final Object value;

    /**
     * Constructor.
     *
     * @param naam attribuut naam
     * @param value attribuut waarde
     */
    EqualPredicateBuilder(final String naam, final Object value) {
        this.naam = naam;
        this.value = value;
    }

    @Override
    public Predicate toPredicate(final Root<?> root, final CriteriaQuery<?> query, final CriteriaBuilder cb) {
        return cb.equal(PredicateBuilderUtil.getPath(root, naam), value);
    }
}
