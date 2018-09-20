/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domein.repository.jpa;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.domein.kern.FunctieAdres;
import nl.bzk.brp.domein.kern.Geslachtsaanduiding;
import nl.bzk.brp.domein.kern.Land;
import nl.bzk.brp.domein.kern.Partij;
import nl.bzk.brp.domein.kern.Persoon;
import nl.bzk.brp.domein.kern.PersoonAdres;
import nl.bzk.brp.domein.kern.RedenOpschorting;
import nl.bzk.brp.domein.kern.SoortPersoon;
import nl.bzk.brp.domein.repository.PersoonRepository;

import org.junit.Assert;
import org.junit.Test;


/**
 * Testcases voor de {@link PersoonRepositoryJPA} class.
 */
public class PersoonRepositoryJPATest extends AbstractRepositoryTestCase {

    @Inject
    private PersoonRepository persoonRepository;

    /**
     * Test of zoekOpBurgerservicenummer method, of class PersoonRepositoryJPA.
     */
    @Test
    public void testZoekOpBurgerservicenummer() {
        Collection<Persoon> personen = persoonRepository.findByBurgerservicenummer("123456789");

        Assert.assertEquals(1, personen.size());

        Persoon persoon = personen.iterator().next();

        Assert.assertNotNull(persoon);
        Assert.assertEquals("Wittgenstein", persoon.getGeslachtsnaam());
        Assert.assertSame(SoortPersoon.INGESCHREVENE, persoon.getSoort());
        Assert.assertEquals("1234567890", persoon.getAdministratienummer());
        Assert.assertNull(persoon.getBuitenlandseGeboorteplaats());
        Assert.assertNull(persoon.getBuitenlandsePlaatsOverlijden());
        Assert.assertNull(persoon.getBuitenlandseRegioGeboorte());
        Assert.assertEquals("123456789", persoon.getBurgerservicenummer());
        Assert.assertEquals(18890426L, persoon.getDatumGeboorte().longValue());
        // Datum opschorting zit (nog) niet in de brp.sql
        Assert.assertSame(Geslachtsaanduiding.MAN, persoon.getGeslachtsaanduiding());
        Assert.assertEquals("M", persoon.getGeslachtsaanduiding().getCode());
        Assert.assertNotNull(persoon.getGemeenteGeboorte());
        Assert.assertEquals("52.429222,2.790527", persoon.getOmschrijvingGeboortelocatie());
        Assert.assertSame(RedenOpschorting.OVERLIJDEN, persoon.getRedenOpschortingBijhouding());
        Assert.assertEquals("O", persoon.getRedenOpschortingBijhouding().getCode());
        Assert.assertEquals(",", persoon.getScheidingsteken());
        Assert.assertEquals("Ludwig Josef Johann", persoon.getVoornamen());
        Assert.assertNull(persoon.getVoorvoegsel());
        Assert.assertEquals(1, persoon.getWoonplaatsGeboorte().getID().longValue());

        /*
         * Sparse indicaties.
         */
        Assert.assertFalse(persoon.behandeldAlsNederlander());
        Assert.assertNull(persoon.belemmeringVerstrekkingReisdocument());
        Assert.assertNull(persoon.bezitBuitenlandsReisdocument());
        // TODO: onderstaande assertions moeten wachten totdat OneToMany associaties gemodelleerd zijn.
        Assert.assertTrue(persoon.derdeHeeftGezag());
        Assert.assertFalse(persoon.gepriviligeerde());
        Assert.assertTrue(persoon.onderCuratele());
        Assert.assertFalse(persoon.vastgesteldNietNederlander());
        Assert.assertTrue(persoon.verstrekkingsBeperking());

        Partij bijhoudingsGemeente = persoon.getBijhoudingsgemeente();
        Assert.assertNotNull(bijhoudingsGemeente);
        Assert.assertEquals(1, bijhoudingsGemeente.getID().longValue());
        Assert.assertEquals("34", bijhoudingsGemeente.getGemeentecode());

        Land land = persoon.getLandGeboorte();
        Assert.assertNotNull(land);
        Assert.assertEquals(2, land.getID().longValue());
        Assert.assertEquals("Nederland", land.getNaam());
        Assert.assertEquals("NL", land.getISO31661Alpha2());

        List<PersoonAdres> adressen = persoon.getPersoonAdresen();
        Assert.assertNotNull(adressen);
        Assert.assertEquals(1, adressen.size());
        PersoonAdres adres = adressen.get(0);
        Assert.assertNotNull(adres);
        Assert.assertEquals("New Yorkweg", adres.getNaamOpenbareRuimte());
        Assert.assertEquals("New Yorkweg", adres.getAfgekorteNaamOpenbareRuimte());
        Assert.assertEquals("Almere", adres.getGemeentedeel());
        Assert.assertEquals("A", adres.getHuisletter());
        Assert.assertEquals("73", adres.getHuisnummer());
        Assert.assertEquals("sous", adres.getHuisnummertoevoeging());
        Assert.assertEquals("to", adres.getLocatietovAdres());
        Assert.assertEquals("1334NA", adres.getPostcode());
        Assert.assertSame(FunctieAdres.WOONADRES, adres.getSoort());
        Assert.assertEquals("W", adres.getSoort().getCode());
        Assert.assertEquals("Woonadres", adres.getSoort().getNaam());
        Assert.assertNotNull(adres.getGemeente());
        Assert.assertEquals("Almere", adres.getGemeente().getNaam());
    }

    /**
     * Laat zien dat ook personen met andere BSN's worden gevonden.
     */
    @Test
    public void testZoekOpBurgerservicenummerBestaand() {
        Assert.assertNotNull(persoonRepository.findByBurgerservicenummer("234567891"));
        Assert.assertNotNull(persoonRepository.findByBurgerservicenummer("345678912"));
    }

    /**
     * Laat zien dat er een lege lijst wordt teruggegeven als er op een niet bestaand BSN wordt gezocht.
     */
    @Test
    public void testZoekOpBurgerservicenummerNietBestaand() {
        Assert.assertEquals(0, persoonRepository.findByBurgerservicenummer("456789123").size());
    }
}
