/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;

/**
 * Builder voor unittests waar we een SoortDocument nodig hebben.
 */
@SuppressWarnings("unused")
public class TestSoortDocumentBuilder {

    private NaamEnumeratiewaardeAttribuut       naamEnumeratiewaardeAttribuut;

    private TestSoortDocumentBuilder() {
        // instantieren via de statische fabrieksmethode
    }

    public static TestSoortDocumentBuilder maker() {
        return new TestSoortDocumentBuilder();
    }

    public TestSoortDocumentBuilder metNaam(NaamEnumeratiewaardeAttribuut naam) {
        this.naamEnumeratiewaardeAttribuut = naam;
        return this;
    }

    public TestSoortDocumentBuilder metNaam(String naam) {
        return metNaam(new NaamEnumeratiewaardeAttribuut(naam));
    }

    public SoortDocument maak() {
        return new SoortDocument(naamEnumeratiewaardeAttribuut, null, null);
    }

}
