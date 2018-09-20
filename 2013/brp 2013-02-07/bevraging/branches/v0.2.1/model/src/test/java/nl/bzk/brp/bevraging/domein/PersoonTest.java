/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.bzk.brp.bevraging.domein;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


/**
 *
 * @author emalotau
 */
public class PersoonTest {

    /**
     * Test of toString method, of class Persoon.
     */
    @Test
    public void testToString() {
        Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        persoon.setBurgerservicenummer(123456789L);
        persoon.setGeslachtsnaam("Einstein");
        Assert.assertEquals("Persoon[id=<null>,soort=INGESCHREVENE,BSN=123456789,geslachtsnaam=Einstein]",
                persoon.toString());
    }

    /**
     * Test voor de indicaties van een persoon als indicaties null is.
     */
    @Test
    public void testNullVoorIndicaties() {
        Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);

        Assert.assertNull(persoon.behandeldAlsNederlander());
        Assert.assertNull(persoon.belemmeringVerstrekkingReisdocument());
        Assert.assertNull(persoon.bezitBuitenlandsReisdocument());
        Assert.assertNull(persoon.deelnameEUVerkiezingen());
        Assert.assertNull(persoon.derdeHeeftGezag());
        Assert.assertNull(persoon.gepriviligeerde());
        Assert.assertNull(persoon.onderCuratele());
        Assert.assertNull(persoon.uitsluitingNLKiesrecht());
        Assert.assertNull(persoon.verstrekkingsBeperking());
        Assert.assertNull(persoon.vastgesteldNietNederlander());
    }

    /**
     * Test voor de indicaties van een persoon als indicaties null is.
     */
    @Test
    public void testLegeMapVoorIndicaties() {
        Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        Map<SoortIndicatie, PersoonIndicatie> indicaties = new HashMap<SoortIndicatie, PersoonIndicatie>();
        ReflectionTestUtils.setField(persoon, "indicaties", indicaties);

        Assert.assertNull(persoon.behandeldAlsNederlander());
        Assert.assertNull(persoon.belemmeringVerstrekkingReisdocument());
        Assert.assertNull(persoon.bezitBuitenlandsReisdocument());
        Assert.assertNull(persoon.deelnameEUVerkiezingen());
        Assert.assertNull(persoon.derdeHeeftGezag());
        Assert.assertNull(persoon.gepriviligeerde());
        Assert.assertNull(persoon.onderCuratele());
        Assert.assertNull(persoon.uitsluitingNLKiesrecht());
        Assert.assertNull(persoon.verstrekkingsBeperking());
        Assert.assertNull(persoon.vastgesteldNietNederlander());
    }

    /**
     * Test voor de indicaties van een persoon als indicaties enkele indicaties bevatten.
     */
    @Test
    public void testEnkeleIndicatiesGevuld() {
        Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        Map<SoortIndicatie, PersoonIndicatie> indicaties = new HashMap<SoortIndicatie, PersoonIndicatie>();
        indicaties.put(SoortIndicatie.BEZIT_BUITENLANDS_REISDOCUMENT,
                getPersoonIndicatie(persoon, SoortIndicatie.BEZIT_BUITENLANDS_REISDOCUMENT, true));
        indicaties.put(SoortIndicatie.ONDER_CURATELE,
                getPersoonIndicatie(persoon, SoortIndicatie.ONDER_CURATELE, false));
        indicaties.put(SoortIndicatie.UITSLUITING_NL_KIESRECHT,
                getPersoonIndicatie(persoon, SoortIndicatie.UITSLUITING_NL_KIESRECHT, true));
        ReflectionTestUtils.setField(persoon, "indicaties", indicaties);

        Assert.assertNull(persoon.behandeldAlsNederlander());
        Assert.assertNull(persoon.belemmeringVerstrekkingReisdocument());
        Assert.assertTrue(persoon.bezitBuitenlandsReisdocument());
        Assert.assertNull(persoon.deelnameEUVerkiezingen());
        Assert.assertNull(persoon.derdeHeeftGezag());
        Assert.assertNull(persoon.gepriviligeerde());
        Assert.assertFalse(persoon.onderCuratele());
        Assert.assertTrue(persoon.uitsluitingNLKiesrecht());
        Assert.assertNull(persoon.verstrekkingsBeperking());
        Assert.assertNull(persoon.vastgesteldNietNederlander());
    }

    /**
     * Test voor de indicaties van een persoon als indicaties alle indicaties bevatten.
     */
    @Test
    public void testAlleIndicatiesGevuld() {
        Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        Map<SoortIndicatie, PersoonIndicatie> indicaties = new HashMap<SoortIndicatie, PersoonIndicatie>();
        for (SoortIndicatie soort : SoortIndicatie.values()) {
            indicaties.put(soort, getPersoonIndicatie(persoon, soort, true));
        }
        ReflectionTestUtils.setField(persoon, "indicaties", indicaties);

        Assert.assertTrue(persoon.behandeldAlsNederlander());
        Assert.assertTrue(persoon.belemmeringVerstrekkingReisdocument());
        Assert.assertTrue(persoon.bezitBuitenlandsReisdocument());
        Assert.assertTrue(persoon.deelnameEUVerkiezingen());
        Assert.assertTrue(persoon.derdeHeeftGezag());
        Assert.assertTrue(persoon.gepriviligeerde());
        Assert.assertTrue(persoon.onderCuratele());
        Assert.assertTrue(persoon.uitsluitingNLKiesrecht());
        Assert.assertTrue(persoon.verstrekkingsBeperking());
        Assert.assertTrue(persoon.vastgesteldNietNederlander());
    }

    private PersoonIndicatie getPersoonIndicatie(final Persoon persoon, final SoortIndicatie soort,
            final boolean waarde)
    {
        PersoonIndicatie indicatie = new PersoonIndicatie(persoon, soort);
        indicatie.setWaarde(waarde);
        return indicatie;
    }

}
