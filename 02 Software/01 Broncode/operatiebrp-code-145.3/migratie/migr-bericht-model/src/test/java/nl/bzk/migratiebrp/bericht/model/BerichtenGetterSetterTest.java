/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model;

import nl.bzk.migratiebrp.bericht.model.lo3.impl.Hq01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Xq01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesPartijRegisterAntwoordBericht;
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
        new GetterSetterTester().ignore(Hq01Bericht.class, "categorieen").ignore(Xq01Bericht.class, "categorieen")
                .testBerichten("nl.bzk.migratiebrp.bericht.model.lo3");
    }

    @Test
    public void sync() throws ReflectiveOperationException {
        new GetterSetterTester().ignore(LeesPartijRegisterAntwoordBericht.class, "partijRegister")
                .testBerichten("nl.bzk.migratiebrp.bericht.model.sync");
    }

}
