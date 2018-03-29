/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.query;

/**
 * Predicate builder factory om naar een waarde kleiner of gelijk te zoeken.
 *
 * @param <T> waarde type
 */
public final class LessOrEqualPredicateBuilderFactory<T extends Comparable<? super T>> implements PredicateBuilderFactory<T> {
    private final String attribuutNaam;

    /**
     * Constructor.
     *
     * @param attribuutNaam attribuut naam
     */
    public LessOrEqualPredicateBuilderFactory(final String attribuutNaam) {
        this.attribuutNaam = attribuutNaam;
    }

    @Override
    public PredicateBuilder getPredicateBuilder(final T value) {
        return new LessOrEqualPredicateBuilder<>(attribuutNaam, value);
    }

}
