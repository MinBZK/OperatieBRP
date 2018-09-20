/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.WoonplaatscodeAttribuut;

/**
 * Builder voor unittests waar we een Plaats nodig hebben.
 */
public class TestPlaatsBuilder {

    private WoonplaatscodeAttribuut code;

    private NaamEnumeratiewaardeAttribuut naam;

    private TestPlaatsBuilder() {
        // instantieren via de statische fabrieksmethode
    }

    public static TestPlaatsBuilder maker() {
        return new TestPlaatsBuilder();
    }

    public TestPlaatsBuilder metCode(final WoonplaatscodeAttribuut code) {
        this.code = code;
        return this;
    }

    public TestPlaatsBuilder metCode(final int code) {
        return metCode(new WoonplaatscodeAttribuut((short) code));
    }

    public TestPlaatsBuilder metNaam(final NaamEnumeratiewaardeAttribuut naam) {
        this.naam = naam;
        return this;
    }

    public TestPlaatsBuilder metNaam(final String naam) {
        return metNaam(new NaamEnumeratiewaardeAttribuut(naam));
    }

    public Plaats maak() {
        Plaats plaats = new Plaats(code, naam, null, null);
        return plaats;
    }

}
