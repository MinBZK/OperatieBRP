/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bijhouding;

import nl.bzk.brp.model.bericht.ber.BerichtStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.HandelingGeboorteInNederlandBericht;
import nl.bzk.brp.model.bericht.kern.HandelingGeboorteInNederlandMetErkenningBericht;

import org.junit.Assert;
import org.junit.Test;


/**
 * Unit test voor de {@link RegistreerGeboorteAntwoordBericht} klasse.
 */
public class RegistreerGeboorteAntwoordBerichtTest {

    @Test
    public void testIsInschrijvingDoorGeboorte() {
        RegistreerGeboorteAntwoordBericht bericht = new RegistreerGeboorteAntwoordBericht();

        // Test zonder adm. handeling
        bericht.setStandaard(new BerichtStandaardGroepBericht());
        bericht.getStandaard().setAdministratieveHandeling(null);
        Assert.assertFalse(bericht.isGeboorteInNederland());

        // Zet adm. handeling
        bericht.setStandaard(new BerichtStandaardGroepBericht());
        bericht.getStandaard().setAdministratieveHandeling(new HandelingGeboorteInNederlandBericht());
        Assert.assertTrue(bericht.isGeboorteInNederland());

        // Zet adm. handeling
        bericht.setStandaard(new BerichtStandaardGroepBericht());
        bericht.getStandaard().setAdministratieveHandeling(new HandelingGeboorteInNederlandMetErkenningBericht());
        Assert.assertFalse(bericht.isGeboorteInNederland());
    }

    @Test
    public void testIsInschrijvingDoorGeboorteMetErkenning() {
        RegistreerGeboorteAntwoordBericht bericht = new RegistreerGeboorteAntwoordBericht();

        // Test zonder adm. handeling
        bericht.setStandaard(new BerichtStandaardGroepBericht());
        bericht.getStandaard().setAdministratieveHandeling(null);
        Assert.assertFalse(bericht.isGeboorteInNederlandMetErkenning());

        // Zet adm. handeling
        bericht.setStandaard(new BerichtStandaardGroepBericht());
        bericht.getStandaard().setAdministratieveHandeling(new HandelingGeboorteInNederlandMetErkenningBericht());
        Assert.assertTrue(bericht.isGeboorteInNederlandMetErkenning());

        // Zet adm. handeling
        bericht.setStandaard(new BerichtStandaardGroepBericht());
        bericht.getStandaard().setAdministratieveHandeling(new HandelingGeboorteInNederlandBericht());
        Assert.assertFalse(bericht.isGeboorteInNederlandMetErkenning());
    }
}
