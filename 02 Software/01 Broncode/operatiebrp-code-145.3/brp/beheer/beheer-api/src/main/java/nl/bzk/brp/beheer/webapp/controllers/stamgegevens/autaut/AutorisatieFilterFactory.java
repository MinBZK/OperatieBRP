/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.autaut;

import nl.bzk.brp.beheer.webapp.controllers.query.BooleanValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.EqualPredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.Filter;
import nl.bzk.brp.beheer.webapp.controllers.query.GreaterOrEqualPredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.IntegerValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.JaValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.LessOrEqualPredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.LikePredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.StringValueAdapter;

interface AutorisatieFilterFactory {

    /**
     * naam filter.
     * @return filter voor naam
     */
    static Filter<String> getFilterNaam() {
        return new Filter<>("filterNaam", new StringValueAdapter(), new LikePredicateBuilderFactory<>("naam"));
    }

    /**
     * Datum ingang filter.
     * @return filter voor datum ingang
     */
    static Filter<Integer> getFilterDatumIngang() {
        return new Filter<>("filterDatumIngang", new IntegerValueAdapter(), new GreaterOrEqualPredicateBuilderFactory<>("datumIngang"));
    }

    /**
     * Datum Einde filter.
     * @return filter voor datum einde
     */
    static Filter<Integer> getFilterDatumEinde() {
        return new Filter<>("filterDatumEinde", new IntegerValueAdapter(), new LessOrEqualPredicateBuilderFactory<>("datumEinde"));
    }

    /**
     * Filter Indicatie Model Autorisatie.
     * @return filter voor indicatie model autorisatie
     */
    static Filter<Boolean> getFilterIndicatieModelAutorisatie() {
        return new Filter<>("filterModelAutorisatie", new BooleanValueAdapter(), new EqualPredicateBuilderFactory<>("indicatieModelautorisatie"));
    }

    /**
     * Filter geblokkeerd.
     * @return filter voor geblokkeerd
     */
    static Filter<Boolean> getFilterGeblokkeerd() {
        return new Filter<>("filterGeblokkeerd", new JaValueAdapter(), new EqualPredicateBuilderFactory<>("indicatieGeblokkeerd"));
    }
}
