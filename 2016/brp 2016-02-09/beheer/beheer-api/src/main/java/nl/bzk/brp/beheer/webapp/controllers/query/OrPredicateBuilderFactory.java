/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.query;

import java.util.ArrayList;
import java.util.List;

/**
 * Predicate builder om te zoeken naar verschillende voorwaarden.
 *
 * @param <T> waarde type
 */
public final class OrPredicateBuilderFactory<T> implements PredicateBuilderFactory<T> {

    private final PredicateBuilderFactory<T>[] builderFactories;

    /**
     * Constructor.
     *
     * @param builderFactories builder factories
     */
    @SafeVarargs
    public OrPredicateBuilderFactory(final PredicateBuilderFactory<T>... builderFactories) {
        this.builderFactories = builderFactories;
    }

    @Override
    public PredicateBuilder getPredicateBuilder(final T value) {
        final List<PredicateBuilder> predicateBuilders = new ArrayList<>();

        for (final PredicateBuilderFactory<T> builderFactory : builderFactories) {
            predicateBuilders.add(builderFactory.getPredicateBuilder(value));
        }

        return new OrPredicateBuilder(predicateBuilders);
    }
}
