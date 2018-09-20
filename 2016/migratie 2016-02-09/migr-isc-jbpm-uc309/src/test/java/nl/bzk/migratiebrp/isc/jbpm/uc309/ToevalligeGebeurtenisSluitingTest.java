/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc309;

import java.math.BigInteger;

import nl.bzk.migratiebrp.bericht.model.sync.generated.RelatieSluitingGroepType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisVerzoekBericht;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 */
public class ToevalligeGebeurtenisSluitingTest {

    private final Tb02Factory tb02Factory = new Tb02Factory();
    private ToevalligeGebeurtenisSluiting sluiting;

    @Before
    public void setUp() {
        sluiting = new ToevalligeGebeurtenisSluiting();
    }

    @Test
    public void testVerwerkInput() throws Exception {
        final VerwerkToevalligeGebeurtenisVerzoekBericht bericht = sluiting.verwerkInput(tb02Factory.maakTb02Bericht(Tb02Factory.Soort.SLUITING));
        final RelatieSluitingGroepType sluiting = bericht.getRelatie().getSluiting().getSluiting();
        Assert.assertEquals("Sluitingdatum moet zelde zijn als in tb02 bericht", new BigInteger("19990101"), sluiting.getDatum());
        Assert.assertEquals("Plaats van sluiting moet overeenkomen", "5555", sluiting.getPlaats());
        Assert.assertEquals("Land van sluiting moet overeenkomen", "1", sluiting.getLand());
        Assert.assertNull("Bij sluiting geen ontbinding mogelijk", bericht.getRelatie().getOntbinding());
        Assert.assertNull("Bij sluiting geen omzetting mogelijk", bericht.getRelatie().getOmzetting());
        Assert.assertNotNull("Akte dient aanwezig te zijn", bericht.getAkte());
        Assert.assertNotNull("Persoon moet gevuld zijn", bericht.getPersoon());
        Assert.assertNotNull("Geldigheid moet gevuld zijn", bericht.getGeldigheid());
    }
}
