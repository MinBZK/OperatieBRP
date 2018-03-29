/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.autaut;

import nl.bzk.brp.beheer.webapp.controllers.query.EqualPredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.Filter;
import nl.bzk.brp.beheer.webapp.controllers.query.GreaterOrEqualPredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.IntegerValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.JaValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.LessOrEqualPredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.ShortValueAdapter;

/**
 * Filter factory voor LeveringsautorisatieController.
 */
public final class BijhouderFiatteringsuitzonderingFilterFactory {

    private static final String SUB_ID = ".id";
    private static final String INDICATIE_GEBLOKKEERD = "indicatieGeblokkeerd";
    private static final String BIJHOUDER_BIJHOUDINGSVOORSTEL = "bijhouderBijhoudingsvoorstel";
    private static final String BIJHOUDER = "bijhouder";
    private static final String DATUM_EINDE = "datumEinde";
    private static final String DATUM_INGANG = "datumIngang";
    private static final String SOORT_DOCUMENT = "soortDocument";
    private static final String SOORT_ADMINISTRATIEVE_HANDELING = "soortAdministratieveHandelingId";

    /**
     * private constructor.
     */
    private BijhouderFiatteringsuitzonderingFilterFactory() {
    }

    /**
     * Datum ingang filter.
     *
     * @return filter voor datum ingang
     */
    public static Filter<Integer> getFilterDatumIngang() {
        return new Filter<>(DATUM_INGANG, new IntegerValueAdapter(), new GreaterOrEqualPredicateBuilderFactory<Integer>(DATUM_INGANG));
    }

    /**
     * Datum Einde filter.
     *
     * @return filter voor datum einde
     */
    public static Filter<Integer> getFilterDatumEinde() {
        return new Filter<>(DATUM_EINDE, new IntegerValueAdapter(), new LessOrEqualPredicateBuilderFactory<>(DATUM_EINDE));
    }

    /**
     * Filter geautoriseerde Partij.
     *
     * @return Filter voor geautoriseerde Partij
     */
    public static Filter<Short> getFilterSoortDocument() {
        return new Filter<>(SOORT_DOCUMENT, new ShortValueAdapter(), new EqualPredicateBuilderFactory<>(SOORT_DOCUMENT));
    }

    /**
     * Filter geautoriseerde Partij.
     *
     * @return Filter voor geautoriseerde Partij
     */
    public static Filter<Integer> getFilterSoortAdministratieveHandeling() {
        return new Filter<>(SOORT_ADMINISTRATIEVE_HANDELING, new IntegerValueAdapter(), new EqualPredicateBuilderFactory<>(SOORT_ADMINISTRATIEVE_HANDELING));
    }

    /**
     * Filter geautoriseerde Partij.
     *
     * @return Filter voor geautoriseerde Partij
     */
    public static Filter<Short> getFilterBijhouder() {
        return new Filter<>(BIJHOUDER, new ShortValueAdapter(), new EqualPredicateBuilderFactory<>(BIJHOUDER + SUB_ID));
    }

    /**
     * Filter geautoriseerde Partij.
     *
     * @return Filter voor geautoriseerde Partij
     */
    public static Filter<Short> getFilterBijhouderBijhoudingsvoorstel() {
        return new Filter<>(
            BIJHOUDER_BIJHOUDINGSVOORSTEL,
            new ShortValueAdapter(),
            new EqualPredicateBuilderFactory<>(BIJHOUDER_BIJHOUDINGSVOORSTEL + SUB_ID));
    }

    /**
     * Filter geblokkeerd.
     *
     * @return filter voor geblokkeerd
     */
    public static Filter<Boolean> getFilterGeblokkeerd() {
        return new Filter<>(INDICATIE_GEBLOKKEERD, new JaValueAdapter(), new EqualPredicateBuilderFactory<>(INDICATIE_GEBLOKKEERD));
    }

}
