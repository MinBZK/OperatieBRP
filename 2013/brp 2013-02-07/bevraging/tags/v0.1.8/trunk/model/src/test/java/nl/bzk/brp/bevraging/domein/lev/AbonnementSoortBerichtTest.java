/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein.lev;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import nl.bzk.brp.bevraging.domein.ber.SoortBericht;
import org.junit.Test;


/**
 * Unit test voor de {@link AbonnementSoortBericht} class.
 */
public class AbonnementSoortBerichtTest {

    @Test
    public void testEqualsObject() {
        Abonnement abonnement = new Abonnement(null, SoortAbonnement.LEVERING);

        AbonnementSoortBericht asb1 = new AbonnementSoortBericht(abonnement, SoortBericht.OPVRAGEN_PERSOON_ANTWOORD);
        AbonnementSoortBericht asb2 = new AbonnementSoortBericht(abonnement, SoortBericht.OPVRAGEN_PERSOON_ANTWOORD);
        AbonnementSoortBericht asb3 = new AbonnementSoortBericht(abonnement, SoortBericht.OPVRAGEN_PERSOON_VRAAG);
        AbonnementSoortBericht asb4 = new AbonnementSoortBericht(new Abonnement(null, SoortAbonnement.ORDINAL_NUL_NIET_GEBRUIKEN),
                SoortBericht.OPVRAGEN_PERSOON_ANTWOORD);
        assertFalse(asb1.equals(null));
        assertFalse(asb1.equals(new Object()));
        assertTrue(asb1.equals(asb1));
        assertTrue(asb1.equals(asb2));
        assertTrue(asb2.equals(asb1));
        assertFalse(asb1.equals(asb3));
        assertFalse(asb1.equals(asb4));
    }
}
