/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PredicaatCodeAttribuut;

/**
 * Builder voor unittests waar we een Predicaat nodig hebben.
 */
public class TestPredicaatBuilder {

    private PredicaatCodeAttribuut        predicaatCodeAttribuut;
    private NaamEnumeratiewaardeAttribuut naamMannelijk;
    private NaamEnumeratiewaardeAttribuut naamVrouwelijk;

    private TestPredicaatBuilder() {
        // instantieren via de statische fabrieksmethode
    }

    public static TestPredicaatBuilder maker() {
        return new TestPredicaatBuilder();
    }

    public TestPredicaatBuilder metCode(final String code) {
        this.predicaatCodeAttribuut = new PredicaatCodeAttribuut(code);
        return this;
    }

    public TestPredicaatBuilder metNaamMnl(final String naam) {
        this.naamMannelijk = new NaamEnumeratiewaardeAttribuut(naam);
        return this;
    }

    public TestPredicaatBuilder metNaamVrl(final String naam) {
        this.naamVrouwelijk = new NaamEnumeratiewaardeAttribuut(naam);
        return this;
    }

    public Predicaat maak() {
        Predicaat predicaat = new Predicaat(predicaatCodeAttribuut, naamMannelijk, naamVrouwelijk);
        return predicaat;
    }

}
