/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdres;
import nl.bzk.brp.beheer.webapp.controllers.query.BooleanValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.CharacterValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.EqualPredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.Filter;
import nl.bzk.brp.beheer.webapp.controllers.query.IntegerValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.LikePredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.LongValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.OrPredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.PredicateBuilder;
import nl.bzk.brp.beheer.webapp.controllers.query.PredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.ShortValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.StringValueAdapter;

/**
 * Factory voor het maken van filters op {@link nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon} voor de
 * PersoonController.
 */
public final class PersoonFilterFactory {

    public static final String GEMEENTE = "gemeente";
    public static final String PERSOON = "persoon";
    private static final String GESLACHTSNAAMSTAM = "geslachtsnaamstam";
    private static final String SCHEIDINGSTEKEN = "scheidingsteken";
    private static final String VOORVOEGSEL = "voorvoegsel";
    private static final String VOORNAMEN = "voornamen";
    private static final String SOORT = "soort";
    private static final String ID = "id";
    private static final String WILDCARD = "%";
    private static final String UNACCENT = "unaccent";

    /**
     * private util constructor.
     */
    private PersoonFilterFactory() {
    }

    /**
     * id filter.
     * @return filter voor id
     */
    public static Filter<Long> getFilterId() {
        return new Filter<>("persoonId", new LongValueAdapter(), new EqualPredicateBuilderFactory<>("id"));
    }

    /**
     * bsn filter.
     * @return filter voor bsn
     */
    public static Filter<String> getFilterBsn() {
        return new Filter<>(
                "bsn",
                new StringValueAdapter(),
                new OrPredicateBuilderFactory<String>().or(new EqualPredicateBuilderFactory<>("burgerservicenummer"))
                        .or(new EqualPredicateBuilderFactory<>("volgendeBurgerservicenummer"))
                        .or(new EqualPredicateBuilderFactory<>("vorigeBurgerservicenummer")));
    }

    /**
     * anr filter.
     * @return filter voor asn
     */
    public static Filter<String> getFilterAnr() {
        return new Filter<>(
                "anr",
                new StringValueAdapter(),
                new OrPredicateBuilderFactory<String>().or(new EqualPredicateBuilderFactory<>("administratienummer"))
                        .or(new EqualPredicateBuilderFactory<>("volgendeAdministratienummer"))
                        .or(new EqualPredicateBuilderFactory<>("vorigeAdministratienummer")));
    }

    /**
     * soort filter.
     * @return filter voor soort
     */
    public static Filter<Short> getFilterSoort() {
        return new Filter<>(SOORT, new ShortValueAdapter(), new EqualPredicateBuilderFactory<>("soortPersoonId"));
    }

    /**
     * indicatie afgeleid filter.
     * @return filter voor afgeleid
     */
    public static Filter<Boolean> getFilterAfgeleid() {
        return new Filter<>("afgeleid", new BooleanValueAdapter(), new EqualPredicateBuilderFactory<>("indicatieAfgeleid"));
    }

    /**
     * indicatie namenreeks filter.
     * @return filter voor namenreeks
     */
    public static Filter<Boolean> getFilterNamenReeks() {
        return new Filter<>("namenreeks", new BooleanValueAdapter(), new EqualPredicateBuilderFactory<>("indicatieNamenreeks"));
    }

    /**
     * voornamen filter.
     * @return filter voor voornamen
     */
    public static Filter<String> getFilterVoornamen() {
        return new Filter<>(
                VOORNAMEN,
                new StringValueAdapter(),
                new OrPredicateBuilderFactory<String>().or(new LikePredicateBuilderFactory<>(VOORNAMEN))
                        .or(new LikePredicateBuilderFactory<>("voornamenNaamgebruik")));
    }

    /**
     * voorvoegsel filter.
     * @return filter voor voorvoegsel
     */
    public static Filter<String> getFilterVoorvoegsel() {
        return new Filter<>(
                VOORVOEGSEL,
                new StringValueAdapter(),
                new OrPredicateBuilderFactory<String>().or(new LikePredicateBuilderFactory<>(VOORVOEGSEL))
                        .or(new LikePredicateBuilderFactory<>("voorvoegselNaamgebruik")));
    }

    /**
     * scheidingsteken filter.
     * @return filter voor scheidingsteken
     */
    public static Filter<String> getFilterScheidingsteken() {
        return new Filter<>(
                SCHEIDINGSTEKEN,
                new StringValueAdapter(),
                new OrPredicateBuilderFactory<String>().or(new LikePredicateBuilderFactory<>(SCHEIDINGSTEKEN))
                        .or(new LikePredicateBuilderFactory<>("scheidingstekenNaamgebruik")));
    }

    /**
     * geslachtsnaamstam filter.
     * @return filter voor geslachtsnaamstam
     */
    public static Filter<String> getFilterGeslachtsnaamstam() {
        return new Filter<>(
                GESLACHTSNAAMSTAM,
                new StringValueAdapter(),
                new OrPredicateBuilderFactory<String>().or(new LikePredicateBuilderFactory<>(GESLACHTSNAAMSTAM))
                        .or(new LikePredicateBuilderFactory<>("geslachtsnaamstamNaamgebruik")));
    }

    /**
     * geboortedatum filter.
     * @return filter voor geboortedatum
     */
    public static Filter<Integer> getFilterGeboorteDatum() {
        return new Filter<>("geboortedatum", new IntegerValueAdapter(), new EqualPredicateBuilderFactory<>("datumGeboorte"));
    }

    /**
     * geboorte gemeente filter.
     * @return filter voor geboorte gemeente
     */
    public static Filter<Short> getFilterGeboorteGemeente() {
        return new Filter<>("geboortegemeente", new ShortValueAdapter(), new EqualPredicateBuilderFactory<>("gemeenteGeboorte.id"));
    }

    /**
     * geboorte woonplaats filter.
     * @return filter voor geboorte woonplaats
     */
    public static Filter<String> getFilterGeboorteWoonplaats() {
        return new Filter<>("geboortewoonplaats", new StringValueAdapter(), new LikePredicateBuilderFactory<>("woonplaatsnaamGeboorte"));
    }

    /**
     * geboorte buitenlandse plaats filter.
     * @return filter voor geboorte buitenlandse plaats
     */
    public static Filter<String> getFilterGeboorteBuitenlandsePlaats() {
        return new Filter<>("geboortebuitenlandseplaats", new StringValueAdapter(), new LikePredicateBuilderFactory<>("buitenlandsePlaatsGeboorte"));
    }

    /**
     * geboorte buitenlandse regio filter.
     * @return filter voor geboorte buitenlandse regio
     */
    public static Filter<String> getFilterGeboorteBuitenlandseRegio() {
        return new Filter<>("geboortebuitenlandseregio", new StringValueAdapter(), new LikePredicateBuilderFactory<>("buitenlandseRegioGeboorte"));
    }

    /**
     * geboorteland filter.
     * @return filter voor geboorte land
     */
    public static Filter<Short> getFilterGeboorteLand() {
        return new Filter<>("geboorteland", new ShortValueAdapter(), new EqualPredicateBuilderFactory<>("landOfGebiedGeboorte.id"));
    }

    /**
     * adellijke titel filter.
     * @return filter voor adellijke titel
     */
    public static Filter<Short> getFilterAdellijkeTitel() {
        return new Filter<>(
                "adellijketitel",
                new ShortValueAdapter(),
                new OrPredicateBuilderFactory<Short>().or(new EqualPredicateBuilderFactory<>("adellijkeTitelId"))
                        .or(new EqualPredicateBuilderFactory<>("adellijkeTitelNaamgebruikId")));
    }

    /**
     * predicaat filter.
     * @return filter voor predicaat
     */
    public static Filter<Short> getFilterPredicaat() {
        return new Filter<>(
                "predicaat",
                new ShortValueAdapter(),
                new OrPredicateBuilderFactory<Short>().or(new EqualPredicateBuilderFactory<>("predicaatId"))
                        .or(new EqualPredicateBuilderFactory<>("predicaatNaamgebruikId")));
    }

    /**
     * soortadres filter.
     * @return filter voor soort adres
     */
    public static Filter<Short> getFilterSoortAdres() {
        return new Filter<>("soortadres", new ShortValueAdapter(), new FilterOpAdresVeldenPredicateBuilderFactory<>("soortAdresId", false));
    }

    /**
     * Identificatie adresseerbaar object filter.
     * @return filter voor identificatie adresseerbaar object
     */
    public static Filter<String> getFilterIdentificatieAdresseerbaarObject() {
        return new Filter<>(
                "idadresseerbaarobject",
                new StringValueAdapter(),
                new FilterOpAdresVeldenPredicateBuilderFactory<>("identificatiecodeAdresseerbaarObject", true));
    }

    /**
     * Identificatiecode nummeraanduiding filter.
     * @return filter voor identificatiecode nummeraanduiding
     */
    public static Filter<String> getFilterIdentificatiecodeNummeraanduiding() {
        return new Filter<>(
                "idnummeraanduiding",
                new StringValueAdapter(),
                new FilterOpAdresVeldenPredicateBuilderFactory<>("identificatiecodeNummeraanduiding", true));
    }

    /**
     * gemeente filter.
     * @return filter voor gemeente
     */
    public static Filter<Short> getFilterGemeente() {
        return new Filter<>(GEMEENTE, new ShortValueAdapter(), new FilterOpAdresVeldenPredicateBuilderFactory<>(GEMEENTE, false));
    }

    /**
     * Naam openbare ruimte filter.
     * @return filter voor naam openbare ruimte
     */
    public static Filter<String> getFilterNaamOpenbareRuimte() {
        return new Filter<>("naamopenbareruimte", new StringValueAdapter(), new FilterOpAdresVeldenPredicateBuilderFactory<>("naamOpenbareRuimte", true));
    }

    /**
     * Afgekorte naam openbare ruimte filter.
     * @return filter voor naam openbare ruimte
     */
    public static Filter<String> getFilterAfgekorteNaamOpenbareRuimte() {
        return new Filter<>(
                "afgekortenaamopenbareruimte",
                new StringValueAdapter(),
                new FilterOpAdresVeldenPredicateBuilderFactory<>("afgekorteNaamOpenbareRuimte", true));
    }

    /**
     * huisnummer filter.
     * @return filter voor huisnummer
     */
    public static Filter<String> getFilterHuisnummer() {
        return new Filter<>("huisnummer", new StringValueAdapter(), new FilterOpAdresVeldenPredicateBuilderFactory<>("huisnummer", false));
    }

    /**
     * huisletter filter.
     * @return filter voor huisletter
     */
    public static Filter<Character> getFilterHuisletter() {
        return new Filter<>("huisletter", new CharacterValueAdapter(), new FilterOpAdresVeldenPredicateBuilderFactory<>("huisletter", false));
    }

    /**
     * Huisnummertoevoeging filter.
     * @return filter voor Huisnummertoevoeging
     */
    public static Filter<String> getFilterHuisnummertoevoeging() {
        return new Filter<>(
                "huisnummertoevoeging",
                new StringValueAdapter(),
                new FilterOpAdresVeldenPredicateBuilderFactory<>("huisnummertoevoeging", false));
    }

    /**
     * Postcode filter.
     * @return filter voor Postcode
     */
    public static Filter<String> getFilterPostcode() {
        return new Filter<>("postcode", new StringValueAdapter(), new FilterOpAdresVeldenPredicateBuilderFactory<>("postcode", false));
    }

    /**
     * Woonplaatsnaam filter.
     * @return filter voor woonplaatsnaam
     */
    public static Filter<String> getFilterWoonplaatsnaam() {
        return new Filter<>("woonplaatsnaam", new StringValueAdapter(), new FilterOpAdresVeldenPredicateBuilderFactory<>("woonplaatsnaam", false));
    }

    /**
     * buitenlands adres filter.
     * @return filter voor buitenlands adres
     */
    public static Filter<String> getFilterBuitenlandsAdres() {
        return new Filter<>("buitenlandsadres", new StringValueAdapter(), new FilterAdresBuitenlandsePlaatsPredicatieBuilderFactory());
    }

    /**
     * land gebied filter.
     * @return filter voor landgebied
     */
    public static Filter<Integer> getFilterLandGebied() {
        return new Filter<>("landofgebied", new IntegerValueAdapter(), new FilterOpAdresVeldenPredicateBuilderFactory<>("landOfGebied", false));
    }

    /**
     * Predicate Builder Factory voor velden in adres.
     * @param <T> value type
     */
    public static final class FilterOpAdresVeldenPredicateBuilderFactory<T extends Object> implements PredicateBuilderFactory<T> {
        private final String field;
        private final boolean likePredicate;

        /**
         * Constructor.
         * @param field veld waarop wordt gezocht in toegangleveringsautorisatie
         * @param likePredicate indicator of voor het veld een tekst veld betreft
         */
        public FilterOpAdresVeldenPredicateBuilderFactory(final String field, final boolean likePredicate) {
            this.field = field;
            this.likePredicate = likePredicate;
        }

        @Override
        public PredicateBuilder getPredicateBuilder(final T value) {
            return new FilterOpAdresVeldenPredicateBuilder(field, likePredicate, value);
        }
    }

    /**
     * Predicate builder voor geautoriseerde, ondertekenaar of transporteur.
     */
    public static final class FilterOpAdresVeldenPredicateBuilder implements PredicateBuilder {
        private final Object value;
        private final String field;
        private final boolean likePredicate;

        /**
         * Constructor.
         * @param field veld waarop wordt gezocht in toegangleveringsautorisatie
         * @param likePredicate indicator of voor het veld een tekst veld betreft
         * @param value waarde van geautoriseerde
         */
        public FilterOpAdresVeldenPredicateBuilder(final String field, final boolean likePredicate, final Object value) {
            this.field = field;
            this.value = value;
            this.likePredicate = likePredicate;
        }

        @Override
        public Predicate toPredicate(final Root<?> persoonRoot, final CriteriaQuery<?> query, final CriteriaBuilder cb) {
            final Root<PersoonAdres> persoonAdressenRoot = query.from(PersoonAdres.class);
            final Predicate joinPredicateAdressen = persoonRoot.get(ID).in(persoonAdressenRoot.get(PERSOON).get(ID));
            final Predicate adresPredicate;
            if (likePredicate) {
                final Expression<String> attributeExpression = cb.lower(cb.function(UNACCENT, String.class, persoonAdressenRoot.get(field)));
                final Expression<String> valueExpression =
                        cb.lower(
                                cb.function(
                                        UNACCENT,
                                        String.class,
                                        cb.concat(cb.concat(cb.literal(WILDCARD), cb.literal(value.toString())), cb.literal(WILDCARD))));
                adresPredicate = cb.like(attributeExpression, valueExpression);
            } else {
                adresPredicate = cb.equal(persoonAdressenRoot.get(field), value);
            }
            return cb.and(joinPredicateAdressen, adresPredicate);
        }

    }

    /**
     * Predicate Builder Factory voor velden in adres.
     */
    public static final class FilterOpAdresGemeentePredicateBuilderFactory implements PredicateBuilderFactory<Object> {

        @Override
        public PredicateBuilder getPredicateBuilder(final Object value) {
            return new FilterOpAdresGemeentePredicateBuilder(value);
        }
    }

    /**
     * Predicate builder voor geautoriseerde, ondertekenaar of transporteur.
     */
    public static final class FilterOpAdresGemeentePredicateBuilder implements PredicateBuilder {
        private final Object value;

        /**
         * Constructor.
         * @param value waarde van geautoriseerde
         */
        public FilterOpAdresGemeentePredicateBuilder(final Object value) {
            this.value = value;
        }

        @Override
        public Predicate toPredicate(final Root<?> persoonRoot, final CriteriaQuery<?> query, final CriteriaBuilder cb) {
            final Root<PersoonAdres> persoonAdressenRoot = query.from(PersoonAdres.class);
            final Predicate joinPredicateAdressen = persoonRoot.get(ID).in(persoonAdressenRoot.get(PERSOON).get(ID));
            final Predicate adresPredicate = cb.equal(persoonAdressenRoot.get(GEMEENTE).get(ID), value);
            return cb.and(joinPredicateAdressen, adresPredicate);
        }

    }

    /**
     * Predicate Builder Factory Adres buitenlandse plaats.
     */
    public static final class FilterAdresBuitenlandsePlaatsPredicatieBuilderFactory implements PredicateBuilderFactory<String> {
        @Override
        public PredicateBuilder getPredicateBuilder(final String value) {
            return new FilterAdresBuitenlandsePlaatsPredicatieBuilder(value);
        }
    }

    /**
     * Predicate Builder voor dienstbundel op naam.
     */
    public static final class FilterAdresBuitenlandsePlaatsPredicatieBuilder implements PredicateBuilder {
        private final Object value;

        /**
         * Constructor.
         * @param value waarde voor soortdienst
         */
        public FilterAdresBuitenlandsePlaatsPredicatieBuilder(final Object value) {
            this.value = value;
        }

        @Override
        public Predicate toPredicate(final Root<?> persoonRoot, final CriteriaQuery<?> query, final CriteriaBuilder cb) {
            final Root<PersoonAdres> persoonAdressenRoot = query.from(PersoonAdres.class);
            final Predicate joinPredicateAdressen = persoonRoot.get(ID).in(persoonAdressenRoot.get(PERSOON).get(ID));

            final Expression<String> attributeExpression1 =
                    cb.lower(cb.function(UNACCENT, String.class, persoonAdressenRoot.get("buitenlandsAdresRegel1")));
            final Expression<String> attributeExpression2 =
                    cb.lower(cb.function(UNACCENT, String.class, persoonAdressenRoot.get("buitenlandsAdresRegel2")));
            final Expression<String> attributeExpression3 =
                    cb.lower(cb.function(UNACCENT, String.class, persoonAdressenRoot.get("buitenlandsAdresRegel3")));
            final Expression<String> attributeExpression4 =
                    cb.lower(cb.function(UNACCENT, String.class, persoonAdressenRoot.get("buitenlandsAdresRegel4")));
            final Expression<String> attributeExpression5 =
                    cb.lower(cb.function(UNACCENT, String.class, persoonAdressenRoot.get("buitenlandsAdresRegel5")));
            final Expression<String> attributeExpression6 =
                    cb.lower(cb.function(UNACCENT, String.class, persoonAdressenRoot.get("buitenlandsAdresRegel6")));
            final Expression<String> valueExpression =
                    cb.lower(
                            cb.function(
                                    UNACCENT,
                                    String.class,
                                    cb.concat(cb.concat(cb.literal(WILDCARD), cb.literal(value.toString())), cb.literal(WILDCARD))));
            final Predicate adresPredicate1 = cb.like(attributeExpression1, valueExpression);
            final Predicate adresPredicate2 = cb.like(attributeExpression2, valueExpression);
            final Predicate adresPredicate3 = cb.like(attributeExpression3, valueExpression);
            final Predicate adresPredicate4 = cb.like(attributeExpression4, valueExpression);
            final Predicate adresPredicate5 = cb.like(attributeExpression5, valueExpression);
            final Predicate adresPredicate6 = cb.like(attributeExpression6, valueExpression);

            final Predicate buitenlandsAdres = cb.or(adresPredicate1, adresPredicate2, adresPredicate3, adresPredicate4, adresPredicate5, adresPredicate6);
            return cb.and(joinPredicateAdressen, buitenlandsAdres);
        }
    }
}
