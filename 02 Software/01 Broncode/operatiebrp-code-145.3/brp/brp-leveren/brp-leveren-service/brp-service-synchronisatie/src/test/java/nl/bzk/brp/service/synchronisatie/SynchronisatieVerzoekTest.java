/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.synchronisatie;

import org.junit.Assert;
import org.junit.Test;

public class SynchronisatieVerzoekTest {

    @Test
    public void test() {
        final SynchronisatieVerzoek synchronisatieVerzoek = new SynchronisatieVerzoek();
        synchronisatieVerzoek.getParameters().setLeveringsAutorisatieId("1");
        synchronisatieVerzoek.getParameters().setStamgegeven("stamgegeven");
        synchronisatieVerzoek.getZoekCriteriaPersoon().setBsn("123456789");

        Assert.assertNotNull(synchronisatieVerzoek.getParameters());
        Assert.assertEquals("1", synchronisatieVerzoek.getParameters().getLeveringsAutorisatieId());
        Assert.assertEquals("stamgegeven", synchronisatieVerzoek.getParameters().getStamgegeven());
        Assert.assertNotNull(synchronisatieVerzoek.getZoekCriteriaPersoon());
        Assert.assertEquals("123456789", synchronisatieVerzoek.getZoekCriteriaPersoon().getBsn());
    }

    @Test
    public void testInitieelGeenWaardenParametersEnZoekCriteria() {
        final SynchronisatieVerzoek synchronisatieVerzoek = new SynchronisatieVerzoek();

        Assert.assertNotNull(synchronisatieVerzoek.getParameters());
        Assert.assertNull(synchronisatieVerzoek.getParameters().getLeveringsAutorisatieId());
        Assert.assertNull(synchronisatieVerzoek.getParameters().getStamgegeven());
        Assert.assertNotNull(synchronisatieVerzoek.getZoekCriteriaPersoon());
        Assert.assertNull(synchronisatieVerzoek.getZoekCriteriaPersoon().getBsn());
    }
}