/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.autaut;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.brp.beheer.webapp.controllers.query.EqualPredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.Filter;
import nl.bzk.brp.beheer.webapp.controllers.query.JaValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.PredicateBuilder;
import nl.bzk.brp.beheer.webapp.controllers.query.PredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.ShortValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.StringValueAdapter;

/**
 * Filter factory voor LeveringsautorisatieController.
 */
public final class LeveringsautorisatieFilterFactory {

    private static final String LEVERINGSAUTORISATIE = "leveringsautorisatie";
    private static final String ID = "id";
    private static final String WILDCARD = "%";
    private static final String UNACCENT = "unaccent";

    /**
     * private constructor.
     */
    private LeveringsautorisatieFilterFactory() {
    }

    /**
     * stelsel filter.
     * @return filter voor stelsel
     */
    public static Filter<Short> getFilterStelsel() {
        return new Filter<>("filterStelsel", new ShortValueAdapter(), new EqualPredicateBuilderFactory<>("stelselId"));
    }

    /**
     * Filter geautoriseerde Partij.
     * @return Filter voor geautoriseerde Partij
     */
    public static Filter<Short> getFilterGeautoriseerdePartij() {
        return new Filter<>(
                "filterGeautoriseerde",
                new ShortValueAdapter(),
                new FilterGeautoriseerdeOfOndertekenaarOfTransporteurPredicateBuilderFactory("geautoriseerde"));
    }

    /**
     * Filter transporteur partij.
     * @return filter voor transporteur
     */
    public static Filter<Short> getFilterTransporteurPartij() {
        return new Filter<>(
                "filterTransporteur",
                new ShortValueAdapter(),
                new FilterGeautoriseerdeOfOndertekenaarOfTransporteurPredicateBuilderFactory("transporteur"));
    }

    /**
     * Filter ondertekenaar partij.
     * @return Filter voor ondertekenaar partij
     */
    public static Filter<Short> getFilterOndertekenaarPartij() {
        return new Filter<>(
                "filterOndertekenaar",
                new ShortValueAdapter(),
                new FilterGeautoriseerdeOfOndertekenaarOfTransporteurPredicateBuilderFactory("ondertekenaar"));
    }

    /**
     * Filter Dienstbundel naam.
     * @return filter voor dienstbundel naam
     */
    public static Filter<String> getFilterDienstbundelNaam() {
        return new Filter<>("filterDienstbundel", new StringValueAdapter(), new FilterDienstBundelPredicatieBuilderFactory());
    }

    /**
     * Filter Soort Dienst.
     * @return filter voor soort dienst
     */
    public static Filter<Short> getFilterSoortDienst() {
        return new Filter<>("filterSoortDienst", new ShortValueAdapter(), new FilterSoortDienstPredicatieBuilderFactory());
    }

    /**
     * Filter geblokkeerd.
     * @return filter voor geblokkeerd
     */
    public static Filter<Boolean> getFilterGeblokkeerd() {
        return new Filter<>("filterGeblokkeerd", new JaValueAdapter(), new EqualPredicateBuilderFactory<>("indicatieGeblokkeerd"));
    }

    /**
     * Predicate Builder Factory voor Bericht ontvangende partijcode.
     */
    public static final class FilterSoortDienstPredicatieBuilderFactory implements PredicateBuilderFactory<Short> {
        @Override
        public PredicateBuilder getPredicateBuilder(final Short value) {
            return new FilterSoortDienstPredicatieBuilder(value);
        }
    }

    /**
     * Predicate Builder voor soort dienst.
     */
    public static final class FilterSoortDienstPredicatieBuilder implements PredicateBuilder {
        private final Object value;

        /**
         * Constructor.
         * @param value waarde voor soortdienst
         */
        public FilterSoortDienstPredicatieBuilder(final Object value) {
            this.value = value;
        }

        @Override
        public Predicate toPredicate(final Root<?> leveringsautorisatieRoot, final CriteriaQuery<?> query, final CriteriaBuilder cb) {
            final Root<Dienstbundel> dienstbundelRoot = query.from(Dienstbundel.class);
            final Root<Dienst> dienstRoot = query.from(Dienst.class);

            // Join dienst op dienstbundel
            final Predicate joinPredicateDienstbundel = dienstbundelRoot.get(ID).in(dienstRoot.get("dienstbundel").get(ID));

            // Join dienst op leveringsautorisatie
            final Predicate joinPredicateLeveringsautorisatie = leveringsautorisatieRoot.get(ID).in(dienstbundelRoot.get(LEVERINGSAUTORISATIE).get(ID));

            // OntvangendePartij
            final Predicate soortDienstPredicate = cb.equal(dienstRoot.get("soortDienstId"), value);

            return cb.and(joinPredicateDienstbundel, joinPredicateLeveringsautorisatie, soortDienstPredicate);
        }
    }

    /**
     * Predicate Builder Factory Dienstbundel naam.
     */
    public static final class FilterDienstBundelPredicatieBuilderFactory implements PredicateBuilderFactory<String> {
        @Override
        public PredicateBuilder getPredicateBuilder(final String value) {
            return new FilterDienstBundelPredicatieBuilder(value);
        }
    }

    /**
     * Predicate Builder voor dienstbundel op naam.
     */
    public static final class FilterDienstBundelPredicatieBuilder implements PredicateBuilder {
        private final Object value;

        /**
         * Constructor.
         * @param value waarde voor soortdienst
         */
        public FilterDienstBundelPredicatieBuilder(final Object value) {
            this.value = value;
        }

        @Override
        public Predicate toPredicate(final Root<?> leveringsautorisatieRoot, final CriteriaQuery<?> query, final CriteriaBuilder cb) {
            final Root<Dienstbundel> dienstbundelRoot = query.from(Dienstbundel.class);

            // Join dienst op leveringsautorisatie
            final Predicate joinPredicateLeveringsautorisatie = leveringsautorisatieRoot.get(ID).in(dienstbundelRoot.get(LEVERINGSAUTORISATIE).get(ID));

            // Dienstbundel
            final Expression<String> attributeExpression = cb.lower(cb.function(UNACCENT, String.class, dienstbundelRoot.get("naam")));
            final Expression<String> valueExpression =
                    cb.lower(
                            cb.function(
                                    UNACCENT,
                                    String.class,
                                    cb.concat(cb.concat(cb.literal(WILDCARD), cb.literal(value.toString())), cb.literal(WILDCARD))));
            final Predicate dienstBundelPredicate = cb.like(attributeExpression, valueExpression);

            return cb.and(joinPredicateLeveringsautorisatie, dienstBundelPredicate);
        }
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
        public FilterGeautoriseerdeOfOndertekenaarOfTransporteurPredicateBuilderFactory(final String field) {
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
        public FilterGeautoriseerdeansporteurOfOndertekenaarOfTrPredicateBuilder(final String field, final Object value) {
            this.field = field;
            this.value = value;
        }

        @Override
        public Predicate toPredicate(final Root<?> leveringsautorisatieRoot, final CriteriaQuery<?> query, final CriteriaBuilder cb) {
            final Root<ToegangLeveringsAutorisatie> toegangLeveringsAutorisaties = query.from(ToegangLeveringsAutorisatie.class);
            final Predicate joinPredicateToegangLeveringsAutorisatie =
                    leveringsautorisatieRoot.get(ID).in(toegangLeveringsAutorisaties.get(LEVERINGSAUTORISATIE).get(ID));
            final Predicate geautoriseerdePredicate = cb.equal(toegangLeveringsAutorisaties.get(field), value);
            return cb.and(joinPredicateToegangLeveringsAutorisatie, geautoriseerdePredicate);
        }
    }
}
