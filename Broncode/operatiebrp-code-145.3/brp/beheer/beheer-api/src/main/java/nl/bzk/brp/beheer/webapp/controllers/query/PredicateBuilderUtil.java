/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.query;

import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;

/**
 * Predicate builder utilities.
 */
public final class PredicateBuilderUtil {

    private PredicateBuilderUtil() {
        // Niet instantieerbaar
    }

    /**
     * Geef de Path voor de gegeven naam (kan punten bevatten om door objecten te lopen).
     *
     * @param base
     *            basis
     * @param naam
     *            naam
     * @param <T>
     *            attribuut type
     * @return path
     */
    public static <T> Path<T> getPath(final Path<?> base, final String naam) {
        final Path<T> result;
        final int index = naam.indexOf('.');
        if (index == -1) {
            result = base.get(naam);
        } else {
            final String part = naam.substring(0, index);
            final String rest = naam.substring(index + 1);

            final Path<?> partPath = base.get(part);
            if (partPath.getModel() == null) {
                // Dan kunnen we hier niet door, maar moeten we via een join
                final Join<?, ?> join = ((From<?, ?>) base).join(part);
                result = getPath(join, rest);
            } else {
                result = getPath(partPath, rest);
            }
        }
        return result;
    }
}
