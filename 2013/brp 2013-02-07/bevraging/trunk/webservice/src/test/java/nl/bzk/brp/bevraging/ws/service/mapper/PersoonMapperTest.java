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
import java.math.BigDecimal;

import nl.bzk.brp.domein.DomeinObjectFactory;
import nl.bzk.brp.domein.PersistentDomeinObjectFactory;
import nl.bzk.brp.domein.kern.Geslachtsaanduiding;
import nl.bzk.brp.domein.kern.Land;
import nl.bzk.brp.domein.kern.Partij;
import nl.bzk.brp.domein.kern.Persoon;
import nl.bzk.brp.domein.kern.Plaats;
import nl.bzk.brp.domein.kern.RedenOpschorting;
import nl.bzk.brp.domein.kern.SoortPartij;
import nl.bzk.brp.domein.kern.SoortPersoon;

import org.junit.Test;


/**
 * Unit test voor de {@link PersoonMapper} class.
 */
public class PersoonMapperTest {

    private DomeinObjectFactory factory = new PersistentDomeinObjectFactory();

    @Test
    public void testNullDomeinObject() {
        assertNull(PersoonMapper.mapDomeinObjectNaarDTO(null));
    }

    @Test
    public void testLeegDomeinObject() {
        Persoon persoon = factory.createPersoon();
        persoon.setSoort(SoortPersoon.INGESCHREVENE);
        assertNotNull(PersoonMapper.mapDomeinObjectNaarDTO(persoon));
    }

    @Test
    public void testCompleetDomeinObjectZonderAdres() {
        Persoon persoonDO = getVolledigGevuldPersoonZonderAdres();
        nl.bzk.brp.bevraging.ws.basis.Persoon persoonDTO = PersoonMapper.mapDomeinObjectNaarDTO(persoonDO);

        assertEquals("123456789", persoonDTO.getBsn());
        assertEquals(Long.valueOf(17), persoonDTO.getBijhoudingsGemeenteId());
        assertEquals("New York", persoonDTO.getBuitenlandsePlaatsOverlijden());
        assertEquals("regio Z", persoonDTO.getBuitenlandseGeboorteRegio());
        assertEquals(new BigDecimal("20000203"), persoonDTO.getDatumGeboorte());
        assertEquals(Long.valueOf(3), persoonDTO.getGemeenteIdGeboorte());
        assertEquals("V", persoonDTO.getGeslachtsAanduidingCode());
        assertEquals("Tester", persoonDTO.getGeslachtsNaam());
        assertEquals(Long.valueOf(13), persoonDTO.getLandIdGeboorte());
        assertEquals("in vliegtuig", persoonDTO.getLocatieOmschrijvingGeboorte());
        assertEquals("?", persoonDTO.getRedenOpschortingCode());
        assertEquals("true", persoonDTO.getVerstrekkingsBeperking());
        assertEquals("Jan Peter Maria", persoonDTO.getVoornamen());
        assertEquals("tst", persoonDTO.getVoorvoegsel());
        assertNull(persoonDTO.getWoonplaatsGeboorte());
        assertEquals(new Long(9), persoonDTO.getWoonplaatsIdGeboorte());
    }

    /**
     * {@link PersoonMapper} is een utility class en zou daarom niet instantieerbaar moeten zijn. Deze test test dan
     * ook of er een exceptie wordt gegooid indien de class wordt geinstantieerd.
     */
    @Test(expected = IllegalAccessException.class)
    public void testFalenConstructieUtilityClass() throws InstantiationException, IllegalAccessException {
        PersoonMapper.class.newInstance();
    }

    /**
     * Test de private constructor en test ook dat deze geen fouten gooit. Dit doen we puur voor de coverage :(
     */
    @Test
    public void testPrivateConstructor() throws NoSuchMethodException, InstantiationException, IllegalAccessException,
            InvocationTargetException
    {
        Constructor<PersoonMapper> constructor = PersoonMapper.class.getDeclaredConstructor();
        assertFalse(constructor.isAccessible());

        constructor.setAccessible(true);
        assertNotNull(constructor.newInstance());
    }

    /**
     * Genereert een volledig gevulde {@link Persoon} instantie (alle velden hebben een waarde), behalve het adres.
     *
     * @return een persoon instantie.
     */
    private Persoon getVolledigGevuldPersoonZonderAdres() {
        Persoon persoon = factory.createPersoon();
        persoon.setSoort(SoortPersoon.INGESCHREVENE);

        persoon.setBurgerservicenummer("123456789");
        persoon.setAdministratienummer("1234567890");
        persoon.setBijhoudingsgemeente(bouwNieuweGemeente(17, "Gemeente X"));
        persoon.setBuitenlandseGeboorteplaats("Parijs");
        persoon.setBuitenlandsePlaatsOverlijden("New York");
        persoon.setBuitenlandseRegioGeboorte("regio Z");
        persoon.setGemeenteGeboorte(bouwNieuweGemeente(3, "Gemeente Y"));
        persoon.setGeslachtsnaam("Tester");
        persoon.setLandGeboorte(bouwNieuwLand(13, "Frankrijk"));
        persoon.setOmschrijvingGeboortelocatie("in vliegtuig");
        persoon.setRedenOpschortingBijhouding(RedenOpschorting.ONBEKEND);
        persoon.setWoonplaatsGeboorte(bouwNieuweWoonplaats(9, "Brugge"));
        persoon.setDatumGeboorte(20000203);
        persoon.setGeslachtsaanduiding(Geslachtsaanduiding.VROUW);
        persoon.setVoornamen("Jan Peter Maria");
        persoon.setVoorvoegsel("tst");
        persoon.setVerstrekkingsBeperking(true);

        return persoon;
    }

    /**
     * Creeert een nieuwe gemeente op basis van de opgegeven id en naam.
     *
     * @param id de id van de nieuwe gemeente.
     * @param naam de naam van de nieuwe gemeente.
     * @return de nieuwe gemeente.
     */
    private Partij bouwNieuweGemeente(final int id, final String naam) {
        Partij gemeente = factory.createPartij();
        gemeente.setSoort(SoortPartij.GEMEENTE);
        gemeente.setID(id);
        gemeente.setNaam(naam);
        return gemeente;
    }

    /**
     * Creeert een nieuw land op basis van de opgegeven id en naam.
     *
     * @param id de id van het nieuwe land.
     * @param naam de naam van het nieuwe land.
     * @return het nieuwe land.
     */
    private Land bouwNieuwLand(final int id, final String naam) {
        Land land = factory.createLand();
        land.setID(id);
        return land;
    }

    /**
     * Creeert een nieuwe plaats op basis van de opgegeven id en naam.
     *
     * @param id de id van de nieuwe plaats.
     * @param naam de naam van de nieuwe plaats.
     * @return de nieuwe plaats.
     */
    private Plaats bouwNieuweWoonplaats(final int id, final String naam) {
        Plaats plaats = factory.createPlaats();
        plaats.setID(id);
        return plaats;
    }

}
