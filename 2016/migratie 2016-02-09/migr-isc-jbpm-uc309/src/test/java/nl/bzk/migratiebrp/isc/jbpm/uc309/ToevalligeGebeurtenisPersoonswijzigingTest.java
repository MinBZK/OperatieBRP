/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc309;

import junit.framework.Assert;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisVerzoekBericht;

import org.junit.Before;
import org.junit.Test;

/**
 *
 */
public class ToevalligeGebeurtenisPersoonswijzigingTest {

    private final Tb02Factory tb02Factory = new Tb02Factory();
    private ToevalligeGebeurtenisPersoonswijziging wijziging;

    @Before
    public void setUp() {
        wijziging = new ToevalligeGebeurtenisPersoonswijziging();
    }

    @Test
    public void testVerwerkInput() throws Exception {
        final VerwerkToevalligeGebeurtenisVerzoekBericht bericht = wijziging.verwerkInput(tb02Factory.maakTb02Bericht(Tb02Factory.Soort.NAAMWIJZING));
        Assert.assertNotNull("Persoon moet gevuld zijn", bericht.getPersoon());
        Assert.assertNotNull("Updatepersoon moet gevuld zijn", bericht.getUpdatePersoon());
    }
}
