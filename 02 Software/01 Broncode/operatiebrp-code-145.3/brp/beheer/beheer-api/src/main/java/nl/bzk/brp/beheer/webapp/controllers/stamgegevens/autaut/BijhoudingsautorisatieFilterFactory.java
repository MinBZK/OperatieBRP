/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.autaut;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangBijhoudingsautorisatie;
import nl.bzk.brp.beheer.webapp.controllers.query.Filter;
import nl.bzk.brp.beheer.webapp.controllers.query.PredicateBuilder;
import nl.bzk.brp.beheer.webapp.controllers.query.PredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.ShortValueAdapter;

/**
 * Filter factory voor LeveringsautorisatieController.
 */
final class BijhoudingsautorisatieFilterFactory {

    private static final String BIJHOUDINGSAUTORISATIE = "bijhoudingsautorisatie";
    private static final String ID = "id";

    /**
     * private constructor.
     */
    private BijhoudingsautorisatieFilterFactory() {
    }

    /**
     * Filter geautoriseerde Partij.
     * @return Filter voor geautoriseerde Partij
     */
    static Filter<Short> getFilterGeautoriseerdePartij() {
        return new Filter<>(
                "filterGeautoriseerde",
                new ShortValueAdapter(),
                new BijhoudingsautorisatieFilterFactory.FilterGeautoriseerdeOfOndertekenaarOfTransporteurPredicateBuilderFactory("geautoriseerde"));
    }

    /**
     * Filter transporteur partij.
     * @return filter voor transporteur
     */
    static Filter<Short> getFilterTransporteurPartij() {
        return new Filter<>(
                "filterTransporteur",
                new ShortValueAdapter(),
                new BijhoudingsautorisatieFilterFactory.FilterGeautoriseerdeOfOndertekenaarOfTransporteurPredicateBuilderFactory("transporteur"));
    }

    /**
     * Filter ondertekenaar partij.
     * @return Filter voor ondertekenaar partij
     */
    static Filter<Short> getFilterOndertekenaarPartij() {
        return new Filter<>(
                "filterOndertekenaar",
                new ShortValueAdapter(),
                new BijhoudingsautorisatieFilterFactory.FilterGeautoriseerdeOfOndertekenaarOfTransporteurPredicateBuilderFactory("ondertekenaar"));
    }

    /**
     * Predicate Builder Factory voor geautoriseerde, ondertekenaar of transporteur in Leveringsautorisatie.
     */
    public static final class FilterGeautoriseerdeOfOndertekenaarOfTransporteurPredicateBuilderFactory implements PredicateBuilderFactory<Short> {
        private final String field;

        /**
         * Constructor.
         * @param field veld waarop wordt gezocht in toegangleveringsautorisatie
         */
        FilterGeautoriseerdeOfOndertekenaarOfTransporteurPredicateBuilderFactory(final String field) {
            this.field = field;
        }

        @Override
        public PredicateBuilder getPredicateBuilder(final Short value) {
            return new FilterGeautoriseerdeansporteurOfOndertekenaarOfTrPredicateBuilder(field, value);
        }
    }

    /**
     * Predicate builder voor geautoriseerde, ondertekenaar of transporteur.
     */
    public static final class FilterGeautoriseerdeansporteurOfOndertekenaarOfTrPredicateBuilder implements PredicateBuilder {
        private final Object value;
        private final String field;

        /**
         * Constructor.
         * @param field veld waarop wordt gezocht in toegangleveringsautorisatie
         * @param value waarde van geautoriseerde
         */
        FilterGeautoriseerdeansporteurOfOndertekenaarOfTrPredicateBuilder(final String field, final Object value) {
            this.field = field;
            this.value = value;
        }

        @Override
        public Predicate toPredicate(final Root<?> leveringsautorisatieRoot, final CriteriaQuery<?> query, final CriteriaBuilder cb) {
            final Root<ToegangBijhoudingsautorisatie> toegangLeveringsAutorisaties = query.from(ToegangBijhoudingsautorisatie.class);
            final Predicate joinPredicateToegangLeveringsAutorisatie =
                    leveringsautorisatieRoot.get(ID).in(toegangLeveringsAutorisaties.get(BIJHOUDINGSAUTORISATIE).get(ID));
            final Predicate geautoriseerdePredicate = cb.equal(toegangLeveringsAutorisaties.get(field), value);
            return cb.and(joinPredicateToegangLeveringsAutorisatie, geautoriseerdePredicate);
        }

    }
}
