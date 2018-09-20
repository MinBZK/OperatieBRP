/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dto.bevraging;

import junit.framework.Assert;
import nl.bzk.brp.business.dto.bevraging.zoekcriteria.ZoekCriteriaPersoonOpAdres;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Burgerservicenummer;
import org.junit.Test;

/**
 * Unit test voor de {@link PersonenOpAdresInclusiefBetrokkenhedenVraag} class en dan specifiek alle getters en setters
 * in deze class.
 */
public class PersonenOpAdresInclusiefBetrokkenhedenVraagTest {

    @Test
    public void testStandaardGettersEnSetters() {
        PersonenOpAdresInclusiefBetrokkenhedenVraag vraag = new PersonenOpAdresInclusiefBetrokkenhedenVraag();

        ZoekCriteriaPersoonOpAdres zoekCriteria = new ZoekCriteriaPersoonOpAdres();
        vraag.setZoekCriteria(zoekCriteria);

        Assert.assertNotNull(vraag.getZoekCriteria());
        Assert.assertNull(vraag.getZoekCriteria().getBurgerservicenummer());
        Assert.assertNull(vraag.getZoekCriteria().getGemeentecode());
        Assert.assertNull(vraag.getZoekCriteria().getHuisletter());
        Assert.assertNull(vraag.getZoekCriteria().getHuisnummer());
        Assert.assertNull(vraag.getZoekCriteria().getHuisnummertoevoeging());
        Assert.assertNull(vraag.getZoekCriteria().getNaamOpenbareRuimte());
        Assert.assertNull(vraag.getZoekCriteria().getPostcode());

        vraag.getZoekCriteria().setBurgerservicenummer(new Burgerservicenummer("123456789"));
        Assert.assertEquals("123456789", vraag.getZoekCriteria().getBurgerservicenummer().toString());

        vraag.getZoekCriteria().setGemeentecode("1234");
        Assert.assertEquals("1234", vraag.getZoekCriteria().getGemeentecode());

        vraag.getZoekCriteria().setHuisletter("a");
        Assert.assertEquals("a", vraag.getZoekCriteria().getHuisletter());

        vraag.getZoekCriteria().setHuisnummer(16);
        Assert.assertEquals(Integer.valueOf(16), vraag.getZoekCriteria().getHuisnummer());

        vraag.getZoekCriteria().setHuisnummertoevoeging("boven");
        Assert.assertEquals("boven", vraag.getZoekCriteria().getHuisnummertoevoeging());

        vraag.getZoekCriteria().setNaamOpenbareRuimte("brink");
        Assert.assertEquals("brink", vraag.getZoekCriteria().getNaamOpenbareRuimte());

        vraag.getZoekCriteria().setPostcode("1234AA");
        Assert.assertEquals("1234AA", vraag.getZoekCriteria().getPostcode());
    }

    @Test
    public void testGetBsnForLocks() {
        PersonenOpAdresInclusiefBetrokkenhedenVraag vraag = new PersonenOpAdresInclusiefBetrokkenhedenVraag();

        ZoekCriteriaPersoonOpAdres zoekCriteria = new ZoekCriteriaPersoonOpAdres();
        vraag.setZoekCriteria(zoekCriteria);

        vraag.getZoekCriteria().setBurgerservicenummer(new Burgerservicenummer("123456"));
        Assert.assertEquals(vraag.getBurgerservicenummerForLocks(), "000123456");
    }

    @Test
    public void testGeenBurgerservicenummerBeschikbaar() {
        PersonenOpAdresInclusiefBetrokkenhedenVraag vraag = new PersonenOpAdresInclusiefBetrokkenhedenVraag();
        Assert.assertNull(vraag.getBurgerservicenummerForLocks());

        ZoekCriteriaPersoonOpAdres zoekCriteria = new ZoekCriteriaPersoonOpAdres();
        vraag.setZoekCriteria(zoekCriteria);

        Assert.assertNull(vraag.getBurgerservicenummerForLocks());
    }
}
