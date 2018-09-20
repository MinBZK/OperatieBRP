/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein.aut;

import static org.junit.Assert.assertSame;

import nl.bzk.brp.bevraging.domein.Partij;
import nl.bzk.brp.bevraging.domein.SoortPartij;
import org.junit.Test;


/**
 * Unit test voor de {@link AutorisatieBesluit} class.
 */
public class AutorisatieBesluitTest {

    /**
     * Unit test voor de {@link AutorisatieBesluit#AutorisatieBesluit(SoortAutorisatieBesluit, String, Partij)}
     * constructor.
     */
    @Test
    public void testAutorisatieBesluitSoortAutorisatieBesluitStringPartij() {
        Partij partij = new Partij(SoortPartij.SAMENWERKINGSVERBAND);
        String tekst = "Test";

        AutorisatieBesluit besluit = new AutorisatieBesluit(SoortAutorisatieBesluit.LEVERINGSAUTORISATIE, tekst, partij);
        assertSame(SoortAutorisatieBesluit.LEVERINGSAUTORISATIE, besluit.getSoort());
        assertSame(tekst, besluit.getBesluitTekst());
        assertSame(partij, besluit.getAutoriseerder());
    }

}
