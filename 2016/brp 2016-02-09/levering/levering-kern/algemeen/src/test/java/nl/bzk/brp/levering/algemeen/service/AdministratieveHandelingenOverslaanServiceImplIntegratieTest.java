/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.algemeen.service;

import static junit.framework.TestCase.assertEquals;

import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.levering.dataaccess.AbstractIntegratieTest;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;

import org.junit.Test;

public class AdministratieveHandelingenOverslaanServiceImplIntegratieTest extends AbstractIntegratieTest {

    @Inject
    private AdministratieveHandelingenOverslaanService administratieveHandelingenOverslaanService;

    @Test
    public final void testGeefLijstVanAdministratieveHandelingDieOvergeslagenMoetenWorden() {
        final List<SoortAdministratieveHandeling> resultaat =
                administratieveHandelingenOverslaanService.geefLijstVanAdministratieveHandelingDieOvergeslagenMoetenWorden();

        assertEquals(SoortAdministratieveHandeling.G_B_A_INITIELE_VULLING, resultaat.get(0));
        assertEquals(1, resultaat.size());
    }

}
