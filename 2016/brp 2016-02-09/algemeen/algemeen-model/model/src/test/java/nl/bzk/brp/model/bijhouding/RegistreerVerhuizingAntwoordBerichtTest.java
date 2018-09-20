/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bijhouding;

import nl.bzk.brp.model.bericht.ber.BerichtStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.HandelingVerhuizingBinnengemeentelijkBericht;
import nl.bzk.brp.model.bericht.kern.HandelingVerhuizingIntergemeentelijkBericht;

import org.junit.Assert;
import org.junit.Test;


/**
 * Unit tests voor de {@link RegistreerVerhuizingAntwoordBericht} klasse.
 */
public class RegistreerVerhuizingAntwoordBerichtTest {

    @Test
    public void testIsRegistratieBinnengemeentelijkeVerhuizing() {
        RegistreerVerhuizingAntwoordBericht bericht = new RegistreerVerhuizingAntwoordBericht();

        // Test zonder adm. handeling
        bericht.setStandaard(new BerichtStandaardGroepBericht());
        bericht.getStandaard().setAdministratieveHandeling(null);
        Assert.assertFalse(bericht.isVerhuizingBinnengemeentelijk());

        // Zet adm. handeling
        bericht.setStandaard(new BerichtStandaardGroepBericht());
        bericht.getStandaard().setAdministratieveHandeling(new HandelingVerhuizingBinnengemeentelijkBericht());
        Assert.assertTrue(bericht.isVerhuizingBinnengemeentelijk());

        // Zet adm. handeling
        bericht.setStandaard(new BerichtStandaardGroepBericht());
        bericht.getStandaard().setAdministratieveHandeling(new HandelingVerhuizingIntergemeentelijkBericht());
        Assert.assertFalse(bericht.isVerhuizingBinnengemeentelijk());
    }

    @Test
    public void testIsRegistratieIntergemeentelijkeVerhuizing() {
        RegistreerVerhuizingAntwoordBericht bericht = new RegistreerVerhuizingAntwoordBericht();

        // Test zonder adm. handeling
        bericht.setStandaard(new BerichtStandaardGroepBericht());
        bericht.getStandaard().setAdministratieveHandeling(null);
        Assert.assertFalse(bericht.isVerhuizingIntergemeentelijk());

        // Zet adm. handeling
        bericht.setStandaard(new BerichtStandaardGroepBericht());
        bericht.getStandaard().setAdministratieveHandeling(new HandelingVerhuizingIntergemeentelijkBericht());
        Assert.assertTrue(bericht.isVerhuizingIntergemeentelijk());

        // Zet adm. handeling
        bericht.setStandaard(new BerichtStandaardGroepBericht());
        bericht.getStandaard().setAdministratieveHandeling(new HandelingVerhuizingBinnengemeentelijkBericht());
        Assert.assertFalse(bericht.isVerhuizingIntergemeentelijk());
    }
}
