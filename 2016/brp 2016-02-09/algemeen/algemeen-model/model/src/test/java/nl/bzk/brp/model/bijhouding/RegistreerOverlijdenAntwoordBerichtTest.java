/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bijhouding;

import nl.bzk.brp.model.bericht.ber.BerichtStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.HandelingOverlijdenInBuitenlandBericht;
import nl.bzk.brp.model.bericht.kern.HandelingOverlijdenInNederlandBericht;

import org.junit.Assert;
import org.junit.Test;


/**
 * Unit test voor de {@link RegistreerOverlijdenAntwoordBericht} klasse.
 */
public class RegistreerOverlijdenAntwoordBerichtTest {

    @Test
    public void testIsRegistratieOverlijdenNederland() {
        RegistreerOverlijdenAntwoordBericht bericht = new RegistreerOverlijdenAntwoordBericht();

        // Test zonder adm. handeling
        bericht.setStandaard(new BerichtStandaardGroepBericht());
        bericht.getStandaard().setAdministratieveHandeling(null);
        Assert.assertFalse(bericht.isOverlijdenInNederland());

        // Zet adm. handeling
        bericht.setStandaard(new BerichtStandaardGroepBericht());
        bericht.getStandaard().setAdministratieveHandeling(new HandelingOverlijdenInNederlandBericht());
        Assert.assertTrue(bericht.isOverlijdenInNederland());

        // Zet adm. handeling
        bericht.setStandaard(new BerichtStandaardGroepBericht());
        bericht.getStandaard().setAdministratieveHandeling(new HandelingOverlijdenInBuitenlandBericht());
        Assert.assertFalse(bericht.isOverlijdenInNederland());
    }

    @Test
    public void testIsRegistratieOverlijdenBuitenland() {
        RegistreerOverlijdenAntwoordBericht bericht = new RegistreerOverlijdenAntwoordBericht();

        // Test zonder adm. handeling
        bericht.setStandaard(new BerichtStandaardGroepBericht());
        bericht.getStandaard().setAdministratieveHandeling(null);
        Assert.assertFalse(bericht.isOverlijdenInBuitenland());

        // Zet adm. handeling
        bericht.setStandaard(new BerichtStandaardGroepBericht());
        bericht.getStandaard().setAdministratieveHandeling(new HandelingOverlijdenInBuitenlandBericht());
        Assert.assertTrue(bericht.isOverlijdenInBuitenland());

        // Zet adm. handeling
        bericht.setStandaard(new BerichtStandaardGroepBericht());
        bericht.getStandaard().setAdministratieveHandeling(new HandelingOverlijdenInNederlandBericht());
        Assert.assertFalse(bericht.isOverlijdenInBuitenland());
    }
}
