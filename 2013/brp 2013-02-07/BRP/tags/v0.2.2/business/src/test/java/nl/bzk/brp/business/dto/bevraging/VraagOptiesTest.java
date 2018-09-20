/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dto.bevraging;

import junit.framework.Assert;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.attribuuttype.Ja;
import org.junit.Test;

/** Unit test voor de {@link VraagOpties} class en dan specifiek alle getters en setters in deze class. */
public class VraagOptiesTest {

    @Test
    public void testStandaardGettersEnSetters() {
        VraagOpties vraagOptie = new VraagOpties();

        Assert.assertNull(vraagOptie.getAanschouwer());
        Assert.assertNull(vraagOptie.getHistorieFormeel());
        Assert.assertNull(vraagOptie.getHistorieMaterieel());

        vraagOptie.setAanschouwer(new Burgerservicenummer("aanschouwerbsn"));
        vraagOptie.setHistorieFormeel(Ja.Ja);
        vraagOptie.setHistorieMaterieel(Ja.Ja);

        Assert.assertEquals("aanschouwerbsn", vraagOptie.getAanschouwer().getWaarde());
        Assert.assertEquals(Ja.Ja, vraagOptie.getHistorieFormeel());
        Assert.assertEquals(Ja.Ja, vraagOptie.getHistorieMaterieel());
    }
}
