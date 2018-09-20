/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein.repository.jpa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import javax.inject.Inject;

import nl.bzk.brp.bevraging.domein.FunctieAdres;
import nl.bzk.brp.bevraging.domein.GeslachtsAanduiding;
import nl.bzk.brp.bevraging.domein.Land;
import nl.bzk.brp.bevraging.domein.Partij;
import nl.bzk.brp.bevraging.domein.Persoon;
import nl.bzk.brp.bevraging.domein.PersoonAdres;
import nl.bzk.brp.bevraging.domein.RedenOpschorting;
import nl.bzk.brp.bevraging.domein.SoortPersoon;
import nl.bzk.brp.bevraging.domein.repository.PersoonRepository;
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
        Collection<Persoon> personen = persoonRepository.findByBurgerservicenummer(123456789L);

        Assert.assertEquals(1, personen.size());

        Persoon persoon = personen.iterator().next();

        Assert.assertNotNull(persoon);
        Assert.assertEquals("Wittgenstein", persoon.getGeslachtsnaam());
        Assert.assertSame(SoortPersoon.INGESCHREVENE, persoon.getSoort());
        Assert.assertEquals(1234567890, persoon.getAdministratienummer().longValue());
        Assert.assertNull(persoon.getBuitenlandseGeboorteplaats());
        Assert.assertNull(persoon.getBuitenlandsePlaatsOverlijden());
        Assert.assertNull(persoon.getBuitenlandseRegioGeboorte());
        Assert.assertEquals(123456789, persoon.getBurgerservicenummer().intValue());
        Assert.assertEquals(18890426, persoon.getGeboorteDatum().intValue());
        // Datum opschorting zit (nog) niet in de brp.sql
        Assert.assertSame(GeslachtsAanduiding.MAN, persoon.getGeslachtsAanduiding());
        Assert.assertEquals("M", persoon.getGeslachtsAanduiding().getCode());
        Assert.assertNotNull(persoon.getGemeenteGeboorte());
        Assert.assertEquals("52.429222,2.790527", persoon.getOmschrijvingGeboorteLocatie());
        Assert.assertSame(RedenOpschorting.OVERLEDEN, persoon.getRedenOpschortingBijhouding());
        Assert.assertEquals("O", persoon.getRedenOpschortingBijhouding().getCode());
        Assert.assertEquals(",", persoon.getScheidingsTeken());
        Assert.assertEquals("Ludwig Josef Johann", persoon.getVoornamen());
        Assert.assertNull(persoon.getVoorvoegsel());
        Assert.assertEquals(1, persoon.getWoonplaatsGeboorte().getId().longValue());

        /*
         * Sparse indicaties.
         */
        Assert.assertFalse(persoon.behandeldAlsNederlander());
        Assert.assertNull(persoon.belemmeringVerstrekkingReisdocument());
        Assert.assertNull(persoon.bezitBuitenlandsReisdocument());
        Assert.assertNull(persoon.deelnameEUVerkiezingen());
        Assert.assertTrue(persoon.derdeHeeftGezag());
        Assert.assertFalse(persoon.gepriviligeerde());
        Assert.assertTrue(persoon.onderCuratele());
        Assert.assertNull(persoon.uitsluitingNLKiesrecht());
        Assert.assertFalse(persoon.vastgesteldNietNederlander());
        Assert.assertTrue(persoon.verstrekkingsBeperking());

        Partij bijhoudingsGemeente = persoon.getBijhoudingsGemeente();
        Assert.assertNotNull(bijhoudingsGemeente);
        Assert.assertEquals(1, bijhoudingsGemeente.getId().longValue());
        Assert.assertEquals(34, bijhoudingsGemeente.getGemeenteCode().intValue());

        Land land = persoon.getLandGeboorte();
        Assert.assertNotNull(land);
        Assert.assertEquals(2, land.getId().longValue());
        Assert.assertEquals("Nederland", land.getNaam());
        Assert.assertEquals("NL", land.getIso31661Alpha2());

        Set<PersoonAdres> adressen = persoon.getAdressen();
        Assert.assertNotNull(adressen);
        Assert.assertEquals(1, adressen.size());
        PersoonAdres adres = new ArrayList<PersoonAdres>(adressen).get(0);
        Assert.assertNotNull(adres);
        Assert.assertEquals("New Yorkweg", adres.getNaamOpenbareRuimte());
        Assert.assertEquals("New Yorkweg", adres.getAfgekorteNaamOpenbareRuimte());
        Assert.assertEquals("Almere", adres.getGemeenteDeel());
        Assert.assertEquals("A", adres.getHuisletter());
        Assert.assertEquals(73, adres.getHuisNummer().intValue());
        Assert.assertEquals("sous", adres.getHuisnummertoevoeging());
        Assert.assertEquals("to", adres.getLocatieTOVAdres());
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
        Assert.assertNotNull(persoonRepository.findByBurgerservicenummer(234567891L));
        Assert.assertNotNull(persoonRepository.findByBurgerservicenummer(345678912L));
    }

    /**
     * Laat zien dat er een lege lijst wordt teruggegeven als er op een niet bestaand BSN wordt gezocht.
     */
    @Test
    public void testZoekOpBurgerservicenummerNietBestaand() {
        Assert.assertEquals(0, persoonRepository.findByBurgerservicenummer(456789123L).size());
    }
}
