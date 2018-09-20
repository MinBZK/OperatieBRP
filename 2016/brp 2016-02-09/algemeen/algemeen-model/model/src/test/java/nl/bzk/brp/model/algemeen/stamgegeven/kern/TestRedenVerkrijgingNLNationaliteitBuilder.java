/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenVerkrijgingCodeAttribuut;

/**
 * Builder voor unittests waar we een RedenVerkrijgingNLNationaliteit nodig hebben.
 */
public class TestRedenVerkrijgingNLNationaliteitBuilder {

    private RedenVerkrijgingCodeAttribuut redenVerkrijgingCodeAttribuut;

    private TestRedenVerkrijgingNLNationaliteitBuilder() {
        // instantieren via de statische fabrieksmethode
    }

    public static TestRedenVerkrijgingNLNationaliteitBuilder maker() {
        return new TestRedenVerkrijgingNLNationaliteitBuilder();
    }

    public TestRedenVerkrijgingNLNationaliteitBuilder metCode(Short code) {
        this.redenVerkrijgingCodeAttribuut = new RedenVerkrijgingCodeAttribuut(code);
        return this;
    }

    public RedenVerkrijgingNLNationaliteit maak() {
        RedenVerkrijgingNLNationaliteit redenVerkrijgingNLNationaliteit = new RedenVerkrijgingNLNationaliteit(redenVerkrijgingCodeAttribuut, null, null,
            null);
        return redenVerkrijgingNLNationaliteit;
    }

}
