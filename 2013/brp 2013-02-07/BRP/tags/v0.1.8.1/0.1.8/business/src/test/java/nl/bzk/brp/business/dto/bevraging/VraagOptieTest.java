/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dto.bevraging;

import junit.framework.Assert;
import org.junit.Test;

/** Unit test voor de {@link VraagOptie} class en dan specifiek alle getters en setters in deze class. */
public class VraagOptieTest {

    @Test
    public void testStandaardGettersEnSetters() {
        VraagOptie vraagOptie = new VraagOptie();

        Assert.assertNull(vraagOptie.getAanschouwerBsn());
        Assert.assertNull(vraagOptie.getHistorieFormeel());
        Assert.assertNull(vraagOptie.getHistorieMaterieel());

        vraagOptie.setAanschouwerBsn("aanschouwerbsn");
        vraagOptie.setHistorieFormeel("historieformeel");
        vraagOptie.setHistorieMaterieel("historiematerieel");

        Assert.assertEquals("aanschouwerbsn", vraagOptie.getAanschouwerBsn());
        Assert.assertEquals("historieformeel", vraagOptie.getHistorieFormeel());
        Assert.assertEquals("historiematerieel", vraagOptie.getHistorieMaterieel());
    }
}
