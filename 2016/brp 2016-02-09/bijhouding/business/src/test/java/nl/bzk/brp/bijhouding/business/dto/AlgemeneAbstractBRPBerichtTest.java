/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.dto;

import nl.bzk.brp.model.bericht.ber.BerichtBericht;
import nl.bzk.brp.model.bericht.ber.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.bericht.kern.HandelingErkenningNaGeboorteBericht;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test die de methodes in de abstract class {@link BerichtBericht} test.
 */
public class AlgemeneAbstractBRPBerichtTest {

    @Test
    public void testGetterEnSetterStuurgegevens() {
        final BerichtBericht bericht = new ConcreetBRPBericht();
        Assert.assertNull(bericht.getStuurgegevens());

        final BerichtStuurgegevensGroepBericht berichtStuurgegevens = new BerichtStuurgegevensGroepBericht();
        bericht.setStuurgegevens(berichtStuurgegevens);
        Assert.assertNotNull(bericht.getStuurgegevens());
        Assert.assertSame(berichtStuurgegevens, bericht.getStuurgegevens());
    }

    @Test
    public void testGetPartijZonderStuurgegevens() {
        final BerichtBericht bericht = new ConcreetBRPBericht();
        Assert.assertNull(bericht.getAdministratieveHandeling());
        // PartijId is nu onderdeel van de administratieve handeling
        bericht.getStandaard().setAdministratieveHandeling(new HandelingErkenningNaGeboorteBericht());
        Assert.assertNull(bericht.getAdministratieveHandeling().getPartij());
        Assert.assertNull(bericht.getAdministratieveHandeling().getPartijCode());
    }

    /**
     * Interne concrete subclass van de te testen {@link BerichtBericht} class.
     */
    private class ConcreetBRPBericht extends BerichtBericht {

        protected ConcreetBRPBericht() {
            super(null);
        }
    }
}
