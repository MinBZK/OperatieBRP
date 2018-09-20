/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import nl.bzk.brp.bevraging.domein.aut.AutorisatieBesluit;
import nl.bzk.brp.bevraging.domein.aut.SoortAutorisatieBesluit;
import org.junit.Test;


/**
 * Unit test voor de {@link Partij} class.
 */
public class PartijTest {

    @Test(expected = IllegalArgumentException.class)
    public void testCreateDoelBindingMetNullArgument() {
        Partij partij = new Partij(SoortPartij.GEMEENTE);
        partij.createDoelbinding(null);
    }

    @Test
    public void testCreateDoelBindingMetValideArgument() {
        Partij partij = new Partij(SoortPartij.GEMEENTE);

        assertEquals(0, partij.getDoelBindingen().size());
        partij.createDoelbinding(new AutorisatieBesluit(SoortAutorisatieBesluit.LEVERINGSAUTORISATIE, "Test", null));
        assertEquals(1, partij.getDoelBindingen().size());
        assertSame(partij, partij.getDoelBindingen().iterator().next().getGeautoriseerde());
    }

}
