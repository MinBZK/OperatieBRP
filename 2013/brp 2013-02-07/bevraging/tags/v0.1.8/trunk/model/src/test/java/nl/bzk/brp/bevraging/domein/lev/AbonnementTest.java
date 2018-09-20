/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein.lev;

import nl.bzk.brp.bevraging.domein.ber.SoortBericht;
import org.junit.Assert;
import org.junit.Test;


/**
 * Unit test voor de {@link Abonnement} class.
 */
public class AbonnementTest {

    @Test
    public void testVoegSoortBerichtToeMetNull() {
        Abonnement abonnement = new Abonnement(null, SoortAbonnement.LEVERING);
        abonnement.voegSoortBerichtToe(null);
        Assert.assertEquals(0, abonnement.getSoortBerichten().size());
    }

    @Test
    public void testVoegSoortBerichtToeMetGeldigeWaarde() {
        Abonnement abonnement = new Abonnement(null, SoortAbonnement.LEVERING);
        abonnement.voegSoortBerichtToe(SoortBericht.OPVRAGEN_PERSOON_ANTWOORD);
        Assert.assertEquals(1, abonnement.getSoortBerichten().size());
        Assert.assertEquals(SoortBericht.OPVRAGEN_PERSOON_ANTWOORD, abonnement.getSoortBerichten().iterator().next()
                .getSoortBericht());
    }

    @Test
    public void testVoegSoortBerichtToeMetTweeMaalGeldigeWaarde() {
        Abonnement abonnement = new Abonnement(null, SoortAbonnement.LEVERING);
        abonnement.voegSoortBerichtToe(SoortBericht.OPVRAGEN_PERSOON_ANTWOORD);
        abonnement.voegSoortBerichtToe(SoortBericht.OPVRAGEN_PERSOON_VRAAG);
        Assert.assertEquals(2, abonnement.getSoortBerichten().size());
    }

    @Test
    public void testVoegSoortBerichtToeMetTweeMaalZelfdeGeldigeWaarde() {
        Abonnement abonnement = new Abonnement(null, SoortAbonnement.LEVERING);
        abonnement.voegSoortBerichtToe(SoortBericht.OPVRAGEN_PERSOON_VRAAG);
        abonnement.voegSoortBerichtToe(SoortBericht.OPVRAGEN_PERSOON_VRAAG);
        Assert.assertEquals(1, abonnement.getSoortBerichten().size());
    }
}
