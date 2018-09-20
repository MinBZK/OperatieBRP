/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdellijkeTitelCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;

/**
 * Builder voor unittests waar we een AdellijkeTitel nodig hebben.
 */
public class TestAdellijkeTitelBuilder {

    private AdellijkeTitelCodeAttribuut adellijkeTitelCodeAttribuut;
    private NaamEnumeratiewaardeAttribuut naamEnumeratiewaardeAttribuutMannelijk;
    private NaamEnumeratiewaardeAttribuut naamEnumeratiewaardeAttribuutVrouwelijk;

    private TestAdellijkeTitelBuilder() {
        // instantieren via de statische fabrieksmethode
    }

    public static TestAdellijkeTitelBuilder maker() {
        return new TestAdellijkeTitelBuilder();
    }

    public TestAdellijkeTitelBuilder metCode(final String code) {
        this.adellijkeTitelCodeAttribuut = new AdellijkeTitelCodeAttribuut(code);
        return this;
    }

    public TestAdellijkeTitelBuilder metNaamMnl(final String naam) {
        this.naamEnumeratiewaardeAttribuutMannelijk = new NaamEnumeratiewaardeAttribuut(naam);
        return this;
    }

    public TestAdellijkeTitelBuilder metNaamVrl(final String naam) {
        this.naamEnumeratiewaardeAttribuutVrouwelijk = new NaamEnumeratiewaardeAttribuut(naam);
        return this;
    }

    public AdellijkeTitel maak() {
        return new AdellijkeTitel(adellijkeTitelCodeAttribuut, naamEnumeratiewaardeAttribuutMannelijk, naamEnumeratiewaardeAttribuutVrouwelijk);
    }

}
