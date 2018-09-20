/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dto;

import java.util.Collection;

import junit.framework.Assert;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Soortbericht;
import org.junit.Test;

/**
 * Unit test die de methodes in de abstract class {@link AbstractBRPBericht} test.
 */
public class AlgemeneAbstractBRPBerichtTest {

    @Test
    public void testGetterEnSetterStuurgegevens() {
        AbstractBRPBericht bericht = new ConcreetBRPBericht();
        Assert.assertNull(bericht.getBerichtStuurgegevens());

        BerichtStuurgegevens berichtStuurgegevens = new BerichtStuurgegevens();
        bericht.setBerichtStuurgegevens(berichtStuurgegevens);
        Assert.assertNotNull(bericht.getBerichtStuurgegevens());
        Assert.assertSame(berichtStuurgegevens, bericht.getBerichtStuurgegevens());
    }

    @Test
    public void testGetPartijZonderStuurgegevens() {
        AbstractBRPBericht bericht = new ConcreetBRPBericht();
        Assert.assertNull(bericht.getPartijId());
    }

    @Test
    public void testGetPartijVoorStuurgegevensZonderOrganisatie() {
        AbstractBRPBericht bericht = new ConcreetBRPBericht();
        bericht.setBerichtStuurgegevens(new BerichtStuurgegevens());
        Assert.assertNull(bericht.getPartijId());

        // Ook geen partij te vinden indien organisatie een lege string is.
        bericht.getBerichtStuurgegevens().setOrganisatie("");
        Assert.assertNull(bericht.getPartijId());
    }

    @Test
    public void testGetPartijVoorStuurgegevensMetNietNumeriekeOrganisatie() {
        AbstractBRPBericht bericht = new ConcreetBRPBericht();
        bericht.setBerichtStuurgegevens(new BerichtStuurgegevens());
        bericht.getBerichtStuurgegevens().setOrganisatie("Test");
        Assert.assertNull(bericht.getPartijId());
    }


    @Test
    public void testGetPartijVoorStuurgegevensMetNumeriekeOrganisatie() {
        AbstractBRPBericht bericht = new ConcreetBRPBericht();
        bericht.setBerichtStuurgegevens(new BerichtStuurgegevens());
        bericht.getBerichtStuurgegevens().setOrganisatie("12");
        Assert.assertNotNull(bericht.getPartijId());
        Assert.assertEquals(new Short((short) 12), bericht.getPartijId());
    }

    /**
     * Interne concrete subclass van de te testen {@link AbstractBRPBericht} class.
     */
    private class ConcreetBRPBericht extends AbstractBRPBericht {

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
