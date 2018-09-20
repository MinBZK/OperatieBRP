/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model;

import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesAutorisatieRegisterAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesGemeenteRegisterAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.testutils.GetterSetterTester;
import org.junit.Test;

public class BerichtenGetterSetterTest {

    @Test
    public void brp() throws ReflectiveOperationException {
        new GetterSetterTester().testBerichten("nl.bzk.migratiebrp.bericht.model.brp");
    }

    @Test
    public void isc() throws ReflectiveOperationException {
        new GetterSetterTester().testBerichten("nl.bzk.migratiebrp.bericht.model.isc");
    }

    @Test
    public void lo3() throws ReflectiveOperationException {
        new GetterSetterTester().testBerichten("nl.bzk.migratiebrp.bericht.model.lo3");
    }

    @Test
    public void sync() throws ReflectiveOperationException {
        new GetterSetterTester().ignore(LeesGemeenteRegisterAntwoordBericht.class, "gemeenteRegister")
                                .ignore(LeesAutorisatieRegisterAntwoordBericht.class, "autorisatieRegister")
                                .testBerichten("nl.bzk.migratiebrp.bericht.model.sync");
    }

}
