/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dto;

import java.util.Collection;

import junit.framework.Assert;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.Organisatienaam;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;
import nl.bzk.brp.model.bericht.ber.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.bericht.kern.HandelingErkenningOngeborenVruchtBericht;
import org.junit.Test;

/**
 * Unit test die de methodes in de abstract class {@link BerichtBericht} test.
 */
public class AlgemeneAbstractBRPBerichtTest {

    @Test
    public void testGetterEnSetterStuurgegevens() {
        BerichtBericht bericht = new ConcreetBRPBericht();
        Assert.assertNull(bericht.getStuurgegevens());

        BerichtStuurgegevensGroepBericht berichtStuurgegevens = new BerichtStuurgegevensGroepBericht();
        bericht.setStuurgegevens(berichtStuurgegevens);
        Assert.assertNotNull(bericht.getStuurgegevens());
        Assert.assertSame(berichtStuurgegevens, bericht.getStuurgegevens());
    }

    @Test
    public void testGetPartijZonderStuurgegevens() {
        BerichtBericht bericht = new ConcreetBRPBericht();
        Assert.assertNull(bericht.getAdministratieveHandeling());
        // PartijId is nu onderdeel van de administratieve handeling
        bericht.setAdministratieveHandeling(new HandelingErkenningOngeborenVruchtBericht());
        Assert.assertNull(bericht.getAdministratieveHandeling().getPartij());
        Assert.assertNull(bericht.getAdministratieveHandeling().getPartijCode());
    }

    @Test
    public void testGetPartijVoorStuurgegevensZonderOrganisatie() {
        BerichtBericht bericht = new ConcreetBRPBericht();
        bericht.setStuurgegevens(new BerichtStuurgegevensGroepBericht());
        Assert.assertNull(bericht.getStuurgegevens().getOrganisatie());

        // Ook geen partij te vinden indien organisatie een lege string is.
        bericht.getStuurgegevens().setOrganisatie(
                new Organisatienaam(""));
        Assert.assertNull(bericht.getAdministratieveHandeling());
    }

    // bolie: Ik zou het niet weten waarom we dit testen.
    // Is het in het verleden deze twee aan elkaar gekoppeled (of per ongeluk geswitched ==> testen)
    // nu zijn ze verdeel in 2 verschillende objecten. (stuurgegevens <-> administratieve handleling)
//    @Test
//    public void testGetPartijVoorStuurgegevensMetNietNumeriekeOrganisatie() {
//        BerichtBericht bericht = new ConcreetBRPBericht();
//        bericht.setBerichtStuurgegevensGroep(new BerichtStuurgegevensGroepBericht());
//        ((BerichtStuurgegevensGroepBericht) bericht.getBerichtStuurgegevensGroep()).setOrganisatie(
//                new Organisatienaam("Test"));
//        Assert.assertNull(bericht.getPartijId());
//    }
//
//
//    @Test
//    public void testGetPartijVoorStuurgegevensMetNumeriekeOrganisatie() {
//        BerichtBericht bericht = new ConcreetBRPBericht();
//        bericht.setBerichtStuurgegevensGroep(new BerichtStuurgegevensGroepBericht());
//        ((BerichtStuurgegevensGroepBericht) bericht.getBerichtStuurgegevensGroep()).setOrganisatie(
//                new Organisatienaam("12"));
//        Assert.assertNotNull(bericht.getPartijId());
//        Assert.assertEquals(new Short((short) 12), bericht.getPartijId());
//    }
//
    /**
     * Interne concrete subclass van de te testen {@link BerichtBericht} class.
     */
    private class ConcreetBRPBericht extends BerichtBericht {

        protected ConcreetBRPBericht() {
            super(null);
        }

        @Override
        public Collection<String> getReadBsnLocks() {
            return null;
        }

        @Override
        public Collection<String> getWriteBsnLocks() {
            return null;
        }

    }
}
