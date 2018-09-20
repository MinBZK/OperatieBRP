/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NationaliteitcodeAttribuut;

/**
 * Builder voor unittests waar we een Nationaliteit nodig hebben.
 */
public class TestNationaliteitBuilder {

    private NationaliteitcodeAttribuut nationaliteitcodeAttribuut;
    private NaamEnumeratiewaardeAttribuut naamEnumeratiewaardeAttribuut;

    private TestNationaliteitBuilder() {
        // instantieren via de statische fabrieksmethode
    }

    public static TestNationaliteitBuilder maker() {
        return new TestNationaliteitBuilder();
    }

    public TestNationaliteitBuilder metCode(final NationaliteitcodeAttribuut code) {
        this.nationaliteitcodeAttribuut = code;
        return this;
    }

    public TestNationaliteitBuilder metCode(final Short code) {
        return metCode(new NationaliteitcodeAttribuut(code));
    }

    public TestNationaliteitBuilder metCode(final String code) {
        return metCode(new NationaliteitcodeAttribuut(code));
    }

    public TestNationaliteitBuilder metNaam(final String naam) {
        this.naamEnumeratiewaardeAttribuut = new NaamEnumeratiewaardeAttribuut(naam);
        return this;
    }

    public Nationaliteit maak() {
        Nationaliteit nationaliteit = new Nationaliteit(nationaliteitcodeAttribuut, naamEnumeratiewaardeAttribuut, null, null);
        return nationaliteit;
    }

}
