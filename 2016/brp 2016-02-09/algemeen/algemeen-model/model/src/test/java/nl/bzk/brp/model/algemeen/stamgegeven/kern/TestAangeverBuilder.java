/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.AangeverCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;

/**
 * Builder voor unittests waar we een Aangever nodig hebben.
 */
public class TestAangeverBuilder {

    private AangeverCodeAttribuut aangeverCodeAttribuut;

    private NaamEnumeratiewaardeAttribuut naamEnumeratiewaardeAttribuut;

    private TestAangeverBuilder() {
        // instantieren via de statische fabrieksmethode
    }

    public static TestAangeverBuilder maker() {
        return new TestAangeverBuilder();
    }

    public TestAangeverBuilder metCode(final String code) {
        this.aangeverCodeAttribuut = new AangeverCodeAttribuut(code);
        return this;
    }

    public TestAangeverBuilder metCode(final AangeverCodeAttribuut aangeverCodeAttribuut) {
        this.aangeverCodeAttribuut = aangeverCodeAttribuut;
        return this;
    }

    public TestAangeverBuilder metNaam(final NaamEnumeratiewaardeAttribuut naam) {
        this.naamEnumeratiewaardeAttribuut = naam;
        return this;
    }

    public TestAangeverBuilder metNaam(final String naam) {
        return metNaam(new NaamEnumeratiewaardeAttribuut(naam));
    }

    public Aangever maak() {
        return new Aangever(aangeverCodeAttribuut, naamEnumeratiewaardeAttribuut, null);
    }

}
