/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenEindeRelatieCodeAttribuut;

/**
 * Builder voor unittests waar we een RedenEindeRelatie nodig hebben.
 */
public class TestRedenEindeRelatieBuilder {

    private RedenEindeRelatieCodeAttribuut redenEindeRelatieCodeAttribuut;
    private OmschrijvingEnumeratiewaardeAttribuut omschrijvingEnumeratiewaardeAttribuut;

    private TestRedenEindeRelatieBuilder() {
        // instantieren via de statische fabrieksmethode
    }

    public static TestRedenEindeRelatieBuilder maker() {
        return new TestRedenEindeRelatieBuilder();
    }

    public TestRedenEindeRelatieBuilder metCode(RedenEindeRelatieCodeAttribuut code) {
        this.redenEindeRelatieCodeAttribuut = code;
        return this;
    }

    public TestRedenEindeRelatieBuilder metCode(String code) {
        return metCode(new RedenEindeRelatieCodeAttribuut(code));
    }

    public TestRedenEindeRelatieBuilder metOmschrijving(String omschrijving) {
        this.omschrijvingEnumeratiewaardeAttribuut = new OmschrijvingEnumeratiewaardeAttribuut(omschrijving);
        return this;
    }

    public RedenEindeRelatie maak() {
        RedenEindeRelatie redenEindeRelatie = new RedenEindeRelatie(redenEindeRelatieCodeAttribuut, omschrijvingEnumeratiewaardeAttribuut);
        return redenEindeRelatie;
    }

}
