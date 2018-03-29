/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Predicate builder om te zoeken naar verschillende voorwaarden.
 *
 * @param <T>
 *            waarde type
 */
public final class OrPredicateBuilderFactory<T> implements PredicateBuilderFactory<T> {

    private final Collection<PredicateBuilderFactory<T>> builderFactories;

    /**
     * Constructor.
     */
    public OrPredicateBuilderFactory() {
        this.builderFactories = new ArrayList<>();
    }

    /**
     * Voegt een predicate builder factory toe.
     * 
     * @param builderFactory
     *            predicate builder factory
     * @return de huidige instantie van deze class
     */
    public OrPredicateBuilderFactory<T> or(final PredicateBuilderFactory<T> builderFactory) {
        builderFactories.add(builderFactory);
        return this;
    }

    @Override
    public PredicateBuilder getPredicateBuilder(final T value) {
        final List<PredicateBuilder> predicateBuilders =
                builderFactories.stream().map(builderFactory -> builderFactory.getPredicateBuilder(value)).collect(Collectors.toList());

        return new OrPredicateBuilder(predicateBuilders);
    }
}
