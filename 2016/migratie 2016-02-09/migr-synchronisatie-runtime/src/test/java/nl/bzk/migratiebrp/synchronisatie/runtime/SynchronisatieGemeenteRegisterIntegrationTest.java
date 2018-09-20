/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime;

import nl.bzk.migratiebrp.bericht.model.sync.register.GemeenteRegister;
import nl.bzk.migratiebrp.register.client.GemeenteService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SynchronisatieGemeenteRegisterIntegrationTest extends AbstractIntegrationTest {

    public SynchronisatieGemeenteRegisterIntegrationTest() {
        super("synchronisatie");
    }

    @Autowired
    private GemeenteService gemeenteService;

    @Test
    public void gemeenteService() throws InterruptedException {
        gemeenteService.refreshRegister();
        final GemeenteRegister register = gemeenteService.geefRegister();
        Assert.assertNotNull(register);
        System.out.println("Gemeenten:\n" + register.geefAlleGemeenten());
    }
}
