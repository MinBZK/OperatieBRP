/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenWijzigingVerblijfCodeAttribuut;

/**
 * Builder voor unittests waar we een RedenWijzigingVerblijf nodig hebben.
 */
public class TestRedenWijzigingVerblijfBuilder {

    private RedenWijzigingVerblijfCodeAttribuut redenWijzigingVerblijfCodeAttribuut;
    private NaamEnumeratiewaardeAttribuut       naamEnumeratiewaardeAttribuut;

    private TestRedenWijzigingVerblijfBuilder() {
        // instantieren via de statische fabrieksmethode
    }

    public static TestRedenWijzigingVerblijfBuilder maker() {
        return new TestRedenWijzigingVerblijfBuilder();
    }

    public TestRedenWijzigingVerblijfBuilder metCode(RedenWijzigingVerblijfCodeAttribuut code) {
        this.redenWijzigingVerblijfCodeAttribuut = code;
        return this;
    }

    public TestRedenWijzigingVerblijfBuilder metCode(String code) {
        return metCode(new RedenWijzigingVerblijfCodeAttribuut(code));
    }

    public TestRedenWijzigingVerblijfBuilder metNaam(NaamEnumeratiewaardeAttribuut naam) {
        this.naamEnumeratiewaardeAttribuut = naam;
        return this;
    }

    public TestRedenWijzigingVerblijfBuilder metNaam(String naam) {
        return metNaam(new NaamEnumeratiewaardeAttribuut(naam));
    }

    public RedenWijzigingVerblijf maak() {
        RedenWijzigingVerblijf redenWijzigingVerblijf =
            new RedenWijzigingVerblijf(redenWijzigingVerblijfCodeAttribuut, naamEnumeratiewaardeAttribuut);
        return redenWijzigingVerblijf;
    }

}
