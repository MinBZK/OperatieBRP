/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dto.bevraging;

import junit.framework.Assert;
import nl.bzk.brp.business.dto.bevraging.zoekcriteria.ZoekCriteriaPersoonOpAdres;
import org.junit.Test;

/**
 * Unit test voor de {@link PersonenOpAdresInclusiefBetrokkenhedenVraag} class en dan specifiek alle getters en setters
 * in deze class.
 */
public class PersonenOpAdresInclusiefBetrokkenhedenVraagTest {

    @Test
    public void testStandaardGettersEnSetters() {
        PersonenOpAdresInclusiefBetrokkenhedenVraag vraag = new PersonenOpAdresInclusiefBetrokkenhedenVraag();
        ZoekCriteriaPersoonOpAdres zoekCriteria = vraag.getZoekCriteria();
        Assert.assertNotNull(zoekCriteria);
        Assert.assertNull(zoekCriteria.getBurgerservicenummer());
        Assert.assertNull(zoekCriteria.getGemeenteCode());
        Assert.assertNull(zoekCriteria.getHuisletter());
        Assert.assertNull(zoekCriteria.getHuisnummer());
        Assert.assertNull(zoekCriteria.getHuisnummertoevoeging());
        Assert.assertNull(zoekCriteria.getNaamOpenbareRuimte());
        Assert.assertNull(zoekCriteria.getPostcode());

        zoekCriteria.setBurgerservicenummer("123456789");
        Assert.assertEquals("123456789", zoekCriteria.getBurgerservicenummer());

        zoekCriteria.setGemeenteCode("1234");
        Assert.assertEquals("1234", zoekCriteria.getGemeenteCode());

        zoekCriteria.setHuisletter("a");
        Assert.assertEquals("a", zoekCriteria.getHuisletter());

        zoekCriteria.setHuisnummer("16");
        Assert.assertEquals("16", zoekCriteria.getHuisnummer());

        zoekCriteria.setHuisnummertoevoeging("boven");
        Assert.assertEquals("boven", zoekCriteria.getHuisnummertoevoeging());

        zoekCriteria.setNaamOpenbareRuimte("brink");
        Assert.assertEquals("brink", zoekCriteria.getNaamOpenbareRuimte());

        zoekCriteria.setPostcode("1234AA");
        Assert.assertEquals("1234AA", zoekCriteria.getPostcode());
    }
}
