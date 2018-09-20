/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dto.bevraging;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Unit test voor de {@link PersonenOpAdresInclusiefBetrokkenhedenVraag} class en dan specifiek alle getters en setters
 * in deze class.
 */
public class PersonenOpAdresInclusiefBetrokkenhedenVraagTest {

    @Test
    public void testStandaardGettersEnSetters() {
        PersonenOpAdresInclusiefBetrokkenhedenVraag vraag = new PersonenOpAdresInclusiefBetrokkenhedenVraag();
        Assert.assertNull(vraag.getBurgerservicenummer());
        Assert.assertNull(vraag.getGemeenteCode());
        Assert.assertNull(vraag.getHuisletter());
        Assert.assertNull(vraag.getHuisnummer());
        Assert.assertNull(vraag.getHuisnummertoevoeging());
        Assert.assertNull(vraag.getNaamOpenbareRuimte());
        Assert.assertNull(vraag.getPostcode());

        vraag.setBurgerservicenummer("123456789");
        Assert.assertEquals("123456789", vraag.getBurgerservicenummer());

        vraag.setGemeenteCode("1234");
        Assert.assertEquals("1234", vraag.getGemeenteCode());

        vraag.setHuisletter("a");
        Assert.assertEquals("a", vraag.getHuisletter());

        vraag.setHuisnummer("16");
        Assert.assertEquals("16", vraag.getHuisnummer());

        vraag.setHuisnummertoevoeging("boven");
        Assert.assertEquals("boven", vraag.getHuisnummertoevoeging());

        vraag.setNaamOpenbareRuimte("brink");
        Assert.assertEquals("brink", vraag.getNaamOpenbareRuimte());

        vraag.setPostcode("1234AA");
        Assert.assertEquals("1234AA", vraag.getPostcode());
    }
}
