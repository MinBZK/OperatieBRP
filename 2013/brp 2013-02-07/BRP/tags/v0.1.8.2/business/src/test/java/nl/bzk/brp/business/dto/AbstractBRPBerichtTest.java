/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dto;

import java.util.Collection;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Unit test die de methodes in de abstract class {@link AbstractBRPBericht} test.
 */
public abstract class AbstractBRPBerichtTest {

    @Test
    public void testGetterEnSetterStuurgegevens() {
        AbstractBRPBericht bericht = new ConcreetBRPBericht();
        Assert.assertNull(bericht.getBerichtStuurgegevens());

        BerichtStuurgegevens berichtStuurgegevens = new BerichtStuurgegevens();
        bericht.setBerichtStuurgegevens(berichtStuurgegevens);
        Assert.assertNotNull(bericht.getBerichtStuurgegevens());
        Assert.assertSame(berichtStuurgegevens, bericht.getBerichtStuurgegevens());
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
    }
}
