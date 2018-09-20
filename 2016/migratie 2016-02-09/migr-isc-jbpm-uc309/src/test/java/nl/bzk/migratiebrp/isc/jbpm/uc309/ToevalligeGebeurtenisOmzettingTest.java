/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc309;

import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisVerzoekBericht;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class ToevalligeGebeurtenisOmzettingTest {

    private final Tb02Factory tb02Factory = new Tb02Factory();
    private ToevalligeGebeurtenisOmzetting omzetting;

    @Before
    public void setUp() {
        omzetting = new ToevalligeGebeurtenisOmzetting();
    }

    @Test
    public void testVerwerkInput() throws Exception {
        final VerwerkToevalligeGebeurtenisVerzoekBericht bericht = omzetting.verwerkInput(tb02Factory.maakTb02Bericht(Tb02Factory.Soort.OMZETTING));
        assertNotNull("Persoon dient gevuld zijn", bericht.getPersoon());
        assertNotNull("Persoon waarmee relatie is moet gevuld zijn", bericht.getRelatie().getPersoon());
        assertNotNull("Sluiting dient gevuld zijn", bericht.getRelatie().getSluiting());
        assertNotNull("Omzetting dient gevuld te zijn", bericht.getRelatie().getOmzetting());
        assertNull("Ontbinding mag niet gevuld zijn", bericht.getRelatie().getOntbinding());
    }
}