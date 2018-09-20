/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;

/**
 * Test builder voor {@link BijhoudingsautorisatieSoortAdministratieveHandeling}.
 */
public class TestBijhoudingsautorisatieSoortAdministratieveHandelingBuilder {

    private SoortAdministratieveHandeling soortAdministratieveHandeling;
    private ToegangBijhoudingsautorisatie toegangBijhoudingsautorisatie;

    private TestBijhoudingsautorisatieSoortAdministratieveHandelingBuilder() {
    }

    public static TestBijhoudingsautorisatieSoortAdministratieveHandelingBuilder maker() {
        return new TestBijhoudingsautorisatieSoortAdministratieveHandelingBuilder();
    }

    public TestBijhoudingsautorisatieSoortAdministratieveHandelingBuilder metSoortAdministratieveHandeling(
        SoortAdministratieveHandeling soortAdministratieveHandeling)
    {
        this.soortAdministratieveHandeling = soortAdministratieveHandeling;
        return this;
    }

    public TestBijhoudingsautorisatieSoortAdministratieveHandelingBuilder metToegangBijhoudingsautorisatie(ToegangBijhoudingsautorisatie
        toegangBijhoudingsautorisatie)
    {
        this.toegangBijhoudingsautorisatie = toegangBijhoudingsautorisatie;
        return this;
    }

    public BijhoudingsautorisatieSoortAdministratieveHandeling maak() {
        return new BijhoudingsautorisatieSoortAdministratieveHandeling(toegangBijhoudingsautorisatie, soortAdministratieveHandeling);
    }
}
