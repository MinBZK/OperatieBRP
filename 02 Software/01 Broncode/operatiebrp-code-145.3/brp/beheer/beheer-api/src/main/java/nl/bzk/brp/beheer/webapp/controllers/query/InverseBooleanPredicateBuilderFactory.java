/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.query;

import java.util.Arrays;

/**
 * Predicate builder factory om te matchen met de inverse boolean waarde. Als de inverse false is, wordt er ook op null gematcht.
 */
public final class InverseBooleanPredicateBuilderFactory implements PredicateBuilderFactory<Boolean> {
    private final String attribuutNaam;

    /**
     * Constructor.
     *
     * @param attribuutNaam
     *            attribuut naam
     */
    public InverseBooleanPredicateBuilderFactory(final String attribuutNaam) {
        this.attribuutNaam = attribuutNaam;
    }

    @Override
    public PredicateBuilder getPredicateBuilder(final Boolean value) {
        boolean inverseValue = value == null || !value;
        final EqualPredicateBuilder equalPredicateBuilder = new EqualPredicateBuilder(attribuutNaam, inverseValue);
        PredicateBuilder returnBuilder = equalPredicateBuilder;
        if (!inverseValue) {
            returnBuilder = new OrPredicateBuilder(Arrays.asList(new IsNullPredicateBuilder(attribuutNaam), equalPredicateBuilder));
        }
        return returnBuilder;
    }

}
