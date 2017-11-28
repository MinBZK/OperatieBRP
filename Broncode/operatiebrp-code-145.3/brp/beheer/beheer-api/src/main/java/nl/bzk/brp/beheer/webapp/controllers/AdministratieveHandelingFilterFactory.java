/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers;

import java.util.Date;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAfgeleidAdministratiefHistorie;
import nl.bzk.brp.beheer.webapp.controllers.query.DateValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.EqualPredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.Filter;
import nl.bzk.brp.beheer.webapp.controllers.query.GreaterOrEqualPredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.LessOrEqualPredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.LikePredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.PredicateBuilder;
import nl.bzk.brp.beheer.webapp.controllers.query.PredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.ShortValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.StringValueAdapter;

/**
 * Filter factory voor LeveringsautorisatieController.
 */
public final class AdministratieveHandelingFilterFactory {

    private static final String ID = "id";
    private static final String ADMINISTRATIEVE_HANDELING = "administratieveHandeling";
    private static final String BURGERSERVICE_NUMMER = "burgerservicenummer";
    private static final String ADMINISTRATIE_NUMMER = "administratienummer";
    private static final String SOORT = "soort";
    private static final String PARAMETER_BSN = "bsn";
    private static final String PARAMETER_ANUMMER = "anr";
    private static final String PARAMETER_PARTIJ = "partij";
    private static final String PARAMETER_PARTIJ_CODE = "partijCode";
    private static final String PARAMETER_PARTIJ_FIELD = "partij.id";
    private static final String PARAMETER_PARTIJ_CODE_FIELD = "partij.code";
    private static final String PARAMETER_PARTIJ_NAAM = "partijNaam";
    private static final String PARAMETER_PARTIJ_NAAM_FIELD = "partij.naam";
    private static final String PARAMETER_TIJDSTIP_REGISTRATIE_BEGIN = "tijdstipRegistratieBegin";
    private static final String PARAMETER_TIJDSTIP_REGISTRATIE_EINDE = "tijdstipRegistratieEinde";
    private static final String PARAMETER_TIJDSTIP_REGISTRATIE_FIELD = "datumTijdRegistratie";
    private static final String PARAMETER_TIJDSTIP_LEVERING_BEGIN = "tijdstipLeveringBegin";
    private static final String PARAMETER_TIJDSTIP_LEVERING_EINDE = "tijdstipLeveringEinde";
    private static final String PARAMETER_TIJDSTIP_LEVERING_FIELD = "datumTijdLevering";
    private static final String PERSOON = "persoon";

    /**
     * private constructor.
     */
    private AdministratieveHandelingFilterFactory() {
    }

    /**
     * soort filter.
     * @return filter voor soort
     */
    public static Filter<Short> getFilterSoort() {
        return new Filter<>(SOORT, new ShortValueAdapter(), new EqualPredicateBuilderFactory<>(SOORT));
    }

    /**
     * soort filter.
     * @return filter voor soort
     */
    public static Filter<Short> getFilterPartij() {
        return new Filter<>(PARAMETER_PARTIJ, new ShortValueAdapter(), new EqualPredicateBuilderFactory<>(PARAMETER_PARTIJ_FIELD));
    }

    /**
     * partijcode filter.
     * @return filter voor partijcode
     */
    public static Filter<String> getFilterPartijCode() {
        return new Filter<>(PARAMETER_PARTIJ_CODE, new StringValueAdapter(), new EqualPredicateBuilderFactory<>(PARAMETER_PARTIJ_CODE_FIELD));
    }

    /**
     * partijnaam filter.
     * @return filter voor partijnaam
     */
    public static Filter<String> getFilterPartijNaam() {
        return new Filter<>(PARAMETER_PARTIJ_NAAM, new StringValueAdapter(), new LikePredicateBuilderFactory<>(PARAMETER_PARTIJ_NAAM_FIELD));
    }

    /**
     * tijdstip registratie begin filter.
     * @return filter voor tijdstip registratie begin
     */
    public static Filter<Date> getFilterTijdstipRegistratieBegin() {
        return new Filter<>(
                PARAMETER_TIJDSTIP_REGISTRATIE_BEGIN,
                new DateValueAdapter(),
                new GreaterOrEqualPredicateBuilderFactory<>(PARAMETER_TIJDSTIP_REGISTRATIE_FIELD));
    }

    /**
     * tijdstip registratie einde filter.
     * @return filter voor tijdstip registratie einde
     */
    public static Filter<Date> getFilterTijdstipRegistratieEinde() {
        return new Filter<>(
                PARAMETER_TIJDSTIP_REGISTRATIE_EINDE,
                new DateValueAdapter(),
                new LessOrEqualPredicateBuilderFactory<>(PARAMETER_TIJDSTIP_REGISTRATIE_FIELD));
    }

    /**
     * tijdstip levering begin filter.
     * @return filter voor tijdstip levering begin
     */
    public static Filter<Date> getFilterTijdstipLeveringBegin() {
        return new Filter<>(
                PARAMETER_TIJDSTIP_LEVERING_BEGIN,
                new DateValueAdapter(),
                new GreaterOrEqualPredicateBuilderFactory<>(PARAMETER_TIJDSTIP_LEVERING_FIELD));
    }

    /**
     * tijdstip levering einde filter.
     * @return filter voor tijdstip levering einde
     */
    public static Filter<Date> getFilterTijdstipLeveringEinde() {
        return new Filter<>(
                PARAMETER_TIJDSTIP_LEVERING_EINDE,
                new DateValueAdapter(),
                new LessOrEqualPredicateBuilderFactory<>(PARAMETER_TIJDSTIP_LEVERING_FIELD));
    }

    /**
     * BSN filter.
     * @return filter voor BSN
     */
    public static Filter<String> getFilterBurgerServiceNummer() {
        return new Filter<>(PARAMETER_BSN, new StringValueAdapter(), new FilterAdministratieveHandelingOpPersoonBsnPredicatieBuilderFactory());
    }

    /**
     * ANummer filter.
     * @return filter voor Nnummer
     */
    public static Filter<String> getFilterANummer() {
        return new Filter<>(PARAMETER_ANUMMER, new StringValueAdapter(), new FilterAdministratieveHandelingOpPersoonAnrPredicatieBuilderFactory());
    }

    /**
     * Predicate Builder Factory voor Persoon Administratienummer.
     */
    public static final class FilterAdministratieveHandelingOpPersoonAnrPredicatieBuilderFactory implements PredicateBuilderFactory<String> {
        @Override
        public PredicateBuilder getPredicateBuilder(final String value) {
            return new FilterAdministratieveHandelingOpPersoonAnrPredicatieBuilder(value);
        }
    }

    /**
     * Predicate Builder voor Persoon Administratienummer.
     */
    public static final class FilterAdministratieveHandelingOpPersoonAnrPredicatieBuilder implements PredicateBuilder {
        private final Object value;

        /**
         * Constructor.
         * @param value waarde voor administratienummer
         */
        public FilterAdministratieveHandelingOpPersoonAnrPredicatieBuilder(final Object value) {
            this.value = value;
        }

        @Override
        public Predicate toPredicate(final Root<?> administratieveHandelingRoot, final CriteriaQuery<?> query, final CriteriaBuilder cb) {
            final Root<PersoonAfgeleidAdministratiefHistorie> persoonHistoryRoot = query.from(PersoonAfgeleidAdministratiefHistorie.class);

            // Join persoon
            final Predicate joinPredicate = administratieveHandelingRoot.get(ID).in(persoonHistoryRoot.get(ADMINISTRATIEVE_HANDELING).get(ID));

            // Bsn
            final Predicate bsnPredicate = cb.equal(persoonHistoryRoot.get(PERSOON).get(ADMINISTRATIE_NUMMER), value);

            return cb.and(bsnPredicate, joinPredicate);
        }
    }

    /**
     * Predicate Builder Factory voor Persoon Burgerservicenummer.
     */
    public static final class FilterAdministratieveHandelingOpPersoonBsnPredicatieBuilderFactory implements PredicateBuilderFactory<String> {
        @Override
        public PredicateBuilder getPredicateBuilder(final String value) {
            return new FilterAdministratieveHandelingOpPersoonBsnPredicatieBuilder(value);
        }
    }

    /**
     * Predicate Builder voor Persoon Burgerservicenummer.
     */
    public static final class FilterAdministratieveHandelingOpPersoonBsnPredicatieBuilder implements PredicateBuilder {
        private final Object value;

        /**
         * Constructor.
         * @param value waarde voor burgerservicenummer
         */
        public FilterAdministratieveHandelingOpPersoonBsnPredicatieBuilder(final Object value) {
            this.value = value;
        }

        @Override
        public Predicate toPredicate(final Root<?> administratieveHandelingRoot, final CriteriaQuery<?> query, final CriteriaBuilder cb) {
            final Root<PersoonAfgeleidAdministratiefHistorie> persoonHistoryRoot = query.from(PersoonAfgeleidAdministratiefHistorie.class);

            // Join persoon
            final Predicate joinPredicate = administratieveHandelingRoot.get(ID).in(persoonHistoryRoot.get(ADMINISTRATIEVE_HANDELING).get(ID));

            // Bsn
            final Predicate bsnPredicate = cb.equal(persoonHistoryRoot.get(PERSOON).get(BURGERSERVICE_NUMMER), value);

            return cb.and(bsnPredicate, joinPredicate);
        }
    }

}
