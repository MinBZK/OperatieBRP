/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.query;

/**
 * Filter definitie.
 *
 * @param <T> type van de waarde waarop gequeried wordt
 */
public class Filter<T> {
    private final String parameterName;
    private final ValueAdapter<? extends T> valueAdapter;
    private final PredicateBuilderFactory<? super T> predicateBuilderFactory;

    /**
     * Constructor.
     *
     * @param parameterName parameter name
     * @param valueAdapter value adapter
     * @param predicateBuilderFactory predicaat builder factory
     */
    public Filter(
        final String parameterName,
        final ValueAdapter<? extends T> valueAdapter,
        final PredicateBuilderFactory<? super T> predicateBuilderFactory)
    {
        super();
        this.parameterName = parameterName;
        this.valueAdapter = valueAdapter;
        this.predicateBuilderFactory = predicateBuilderFactory;
    }

    /**
     * Geef de parameter naam.
     *
     * @return parameter naam
     */
    public final String getParameterName() {
        return parameterName;
    }

    /**
     * Geef de value adapter.
     *
     * @return value adapter
     */
    public final ValueAdapter<? extends T> getValueAdapter() {
        return valueAdapter;
    }

    /**
     * Geef de predicate builder factory.
     *
     * @return predicate builder factory
     */
    public final PredicateBuilderFactory<? super T> getPredicateBuilderFactory() {
        return predicateBuilderFactory;
    }

}
