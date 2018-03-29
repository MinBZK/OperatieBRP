/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Predicaat builder om hoofdletterongevoelig naar een gedeelte van een waarde te zoeken.
 */
public final class LikePredicateBuilder implements PredicateBuilder {

    private static final String WILDCARD = "%";
    private static final String UNACCENT = "unaccent";

    private final String naam;
    private final Object value;

    /**
     * Constructor.
     *
     * @param naam attribuut naam
     * @param value attribuut waarde
     */
    LikePredicateBuilder(final String naam, final Object value) {
        this.naam = naam;
        this.value = value;
    }

    @Override
    public Predicate toPredicate(final Root<?> root, final CriteriaQuery<?> query, final CriteriaBuilder cb) {
        // Like en case insensitive
        final Path<String> path = PredicateBuilderUtil.<String>getPath(root, naam);
        final Expression<String> attributeExpression = cb.lower(cb.function(UNACCENT, String.class, path));
        final Expression<String> valueExpression =
                cb.lower(cb.function(UNACCENT, String.class, cb.concat(cb.concat(cb.literal(WILDCARD), cb.literal(value.toString())), cb.literal(WILDCARD))));

        return cb.like(attributeExpression, valueExpression);
    }
}
