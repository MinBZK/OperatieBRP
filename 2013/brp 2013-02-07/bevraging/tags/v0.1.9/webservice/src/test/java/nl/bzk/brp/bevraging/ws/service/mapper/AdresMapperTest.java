/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.ws.service.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import nl.bzk.brp.bevraging.domein.FunctieAdres;
import nl.bzk.brp.bevraging.domein.Partij;
import nl.bzk.brp.bevraging.domein.Persoon;
import nl.bzk.brp.bevraging.domein.PersoonAdres;
import nl.bzk.brp.bevraging.domein.SoortPartij;
import nl.bzk.brp.bevraging.domein.SoortPersoon;
import nl.bzk.brp.bevraging.ws.basis.Adres;
import org.junit.Test;


/**
 * Unit test voor de {@link AdresMapper} class.
 */
public class AdresMapperTest {

    @Test
    public void testNullDomeinObject() {
        assertNull(AdresMapper.mapDomeinObjectNaarDTO(null));
    }

    @Test
    public void testLeegDomeinObject() {
        assertNotNull(AdresMapper.mapDomeinObjectNaarDTO(new PersoonAdres(null, null)));
    }

    @Test
    public void testCompleetDomeinObject() {
        PersoonAdres adresDO = getVolledigGevuldPersoonAdres();
        Adres adresDTO = AdresMapper.mapDomeinObjectNaarDTO(adresDO);

        assertEquals("B", adresDTO.getSoortAdresCode());
        assertEquals("Briefadres", adresDTO.getSoortAdresNaam());
        assertEquals("naam openbare ruimte", adresDTO.getAfgekorteNaamOpenbareRuimte());
        assertEquals("gemeentedeel", adresDTO.getGemeenteDeel());
        assertEquals("1234AA", adresDTO.getPostcode());
        assertEquals(12, adresDTO.getHuisnummer().intValue());
        assertEquals("a", adresDTO.getHuisnummerToevoeging());
        assertEquals("X", adresDTO.getHuisletter());
        assertEquals("rechts voor", adresDTO.getLocatieTOVAdres());
    }

    /**
     * {@link AdresMapper} is een utility class en zou daarom niet instantieerbaar moeten zijn. Deze test test dan ook
     * of er een exceptie wordt gegooid indien de class wordt geinstantieerd.
     */
    @Test(expected = IllegalAccessException.class)
    public void testFalenConstructieUtilityClass() throws InstantiationException, IllegalAccessException {
        AdresMapper.class.newInstance();
    }

    /**
     * Test de private constructor en test ook dat deze geen fouten gooit. Dit doen we puur voor de coverage :(
     */
    @Test
    public void testPrivateConstructor() throws NoSuchMethodException, InstantiationException, IllegalAccessException,
            InvocationTargetException
    {
        Constructor<AdresMapper> constructor = AdresMapper.class.getDeclaredConstructor();
        assertFalse(constructor.isAccessible());

        constructor.setAccessible(true);
        assertNotNull(constructor.newInstance());
    }

    /**
     * Genereert een volledig gevulde {@link PersoonAdres} instantie (alle velden hebben een waarde).
     *
     * @return een persoon adres instantie.
     */
    private PersoonAdres getVolledigGevuldPersoonAdres() {
        Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        PersoonAdres adres = new PersoonAdres(persoon, FunctieAdres.BRIEFADRES);

        adres.setAfgekorteNaamOpenbareRuimte("naam openbare ruimte");
        adres.setGemeente(new Partij(SoortPartij.GEMEENTE));
        adres.setGemeenteDeel("gemeentedeel");
        adres.setPostcode("1234AA");
        adres.setHuisNummer(12);
        adres.setHuisnummertoevoeging("a");
        adres.setHuisletter("X");
        adres.setLocatieTOVAdres("rechts voor");

        return adres;
    }

}
