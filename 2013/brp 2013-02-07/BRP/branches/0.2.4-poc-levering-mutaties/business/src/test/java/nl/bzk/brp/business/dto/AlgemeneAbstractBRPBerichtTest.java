/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dto;

import java.util.Collection;

import junit.framework.Assert;
import nl.bzk.brp.model.attribuuttype.Organisatienaam;
import nl.bzk.brp.model.groep.bericht.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.BerichtBericht;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Soortbericht;
import org.junit.Test;

/**
 * Unit test die de methodes in de abstract class {@link BerichtBericht} test.
 */
public class AlgemeneAbstractBRPBerichtTest {

    @Test
    public void testGetterEnSetterStuurgegevens() {
        BerichtBericht bericht = new ConcreetBRPBericht();
        Assert.assertNull(bericht.getBerichtStuurgegevensGroep());

        BerichtStuurgegevensGroepBericht berichtStuurgegevens = new BerichtStuurgegevensGroepBericht();
        bericht.setBerichtStuurgegevensGroep(berichtStuurgegevens);
        Assert.assertNotNull(bericht.getBerichtStuurgegevensGroep());
        Assert.assertSame(berichtStuurgegevens, bericht.getBerichtStuurgegevensGroep());
    }

    @Test
    public void testGetPartijZonderStuurgegevens() {
        BerichtBericht bericht = new ConcreetBRPBericht();
        Assert.assertNull(bericht.getPartijId());
    }

    @Test
    public void testGetPartijVoorStuurgegevensZonderOrganisatie() {
        BerichtBericht bericht = new ConcreetBRPBericht();
        bericht.setBerichtStuurgegevensGroep(new BerichtStuurgegevensGroepBericht());
        Assert.assertNull(bericht.getPartijId());

        // Ook geen partij te vinden indien organisatie een lege string is.
        ((BerichtStuurgegevensGroepBericht) bericht.getBerichtStuurgegevensGroep()).setOrganisatie(
                new Organisatienaam(""));
        Assert.assertNull(bericht.getPartijId());
    }

    @Test
    public void testGetPartijVoorStuurgegevensMetNietNumeriekeOrganisatie() {
        BerichtBericht bericht = new ConcreetBRPBericht();
        bericht.setBerichtStuurgegevensGroep(new BerichtStuurgegevensGroepBericht());
        ((BerichtStuurgegevensGroepBericht) bericht.getBerichtStuurgegevensGroep()).setOrganisatie(
                new Organisatienaam("Test"));
        Assert.assertNull(bericht.getPartijId());
    }


    @Test
    public void testGetPartijVoorStuurgegevensMetNumeriekeOrganisatie() {
        BerichtBericht bericht = new ConcreetBRPBericht();
        bericht.setBerichtStuurgegevensGroep(new BerichtStuurgegevensGroepBericht());
        ((BerichtStuurgegevensGroepBericht) bericht.getBerichtStuurgegevensGroep()).setOrganisatie(
                new Organisatienaam("12"));
        Assert.assertNotNull(bericht.getPartijId());
        Assert.assertEquals(new Short((short) 12), bericht.getPartijId());
    }

    /**
     * Interne concrete subclass van de te testen {@link BerichtBericht} class.
     */
    private class ConcreetBRPBericht extends BerichtBericht {

        @Override
        public Collection<String> getReadBsnLocks() {
            return null;
        }

        @Override
        public Collection<String> getWriteBsnLocks() {
            return null;
        }

        @Override
        public Soortbericht getSoortBericht() {
            return null;
        }
    }
}
