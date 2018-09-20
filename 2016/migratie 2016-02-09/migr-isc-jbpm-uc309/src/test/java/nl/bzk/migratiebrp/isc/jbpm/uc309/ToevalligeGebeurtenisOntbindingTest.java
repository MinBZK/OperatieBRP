/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc309;

import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisVerzoekBericht;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 *
 */
public class ToevalligeGebeurtenisOntbindingTest {

    private final Tb02Factory tb02Factory = new Tb02Factory();
    private ToevalligeGebeurtenisOntbinding ontbinding;

    @Before
    public void setUp() {
        ontbinding = new ToevalligeGebeurtenisOntbinding();
    }

    @Test
    public void testVerwerkInput() throws Exception {
        VerwerkToevalligeGebeurtenisVerzoekBericht bericht = ontbinding.verwerkInput(tb02Factory.maakTb02Bericht(Tb02Factory.Soort.ONTBINDING));
        assertNotNull("Ontbinding dient gevuld te zijn", bericht.getRelatie().getOntbinding());
        assertNotNull("Bij ontbinding moet sluiting ook gevuld zijn", bericht.getRelatie().getSluiting());
        assertNotNull("Persoon van de relatie moet gevuld zijn", bericht.getRelatie().getPersoon());
        assertNull("Bij ontbinding geen omzetting mogelijk", bericht.getRelatie().getOmzetting());
        assertNotNull("Akte dient aanwezig te zijn", bericht.getAkte());
        assertNotNull("Persoon moet gevuld zijn", bericht.getPersoon());
        assertNotNull("Geldigheid moet gevuld zijn", bericht.getGeldigheid());
    }
}
