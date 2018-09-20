/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein.aut;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import nl.bzk.brp.bevraging.domein.Partij;
import nl.bzk.brp.bevraging.domein.SoortPartij;
import nl.bzk.brp.bevraging.domein.lev.SoortAbonnement;
import org.junit.Test;


/**
 * Unit test voor de {@link DoelBinding} class.
 */
public class DoelBindingTest {

    /**
     * Unit test voor de {@link DoelBinding#DoelBinding(AutorisatieBesluit, Partij)} constructor.
     */
    @Test
    public void testDoelBindingAutorisatieBesluitPartij() {
        Partij partij = new Partij(SoortPartij.VERTEGENWOORDIGER_REGERING);
        AutorisatieBesluit besluit = new AutorisatieBesluit(SoortAutorisatieBesluit.LEVERINGSAUTORISATIE, "besluitTekst", partij);
        DoelBinding doelBinding = new DoelBinding(besluit, partij);
        assertSame(besluit, doelBinding.getLeveringsAutorisatieBesluit());
        assertSame(partij, doelBinding.getGeautoriseerde());
    }

    @Test
    public void testCreateAbonnement() {
        DoelBinding doelBinding = new DoelBinding(null, null);

        assertEquals(0, doelBinding.getAbonnementen().size());
        doelBinding.createAbonnement(SoortAbonnement.LEVERING);
        assertEquals(1, doelBinding.getAbonnementen().size());
        assertSame(SoortAbonnement.LEVERING, doelBinding.getAbonnementen().iterator().next().getSoort());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExceptieVoorNullArgumentVoorCreateAbonnement() {
        DoelBinding doelBinding = new DoelBinding(null, null);
        doelBinding.createAbonnement(null);
    }
}
