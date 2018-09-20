/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.ws.service.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import nl.bzk.brp.bevraging.domein.GeslachtsAanduiding;
import nl.bzk.brp.bevraging.domein.Land;
import nl.bzk.brp.bevraging.domein.Partij;
import nl.bzk.brp.bevraging.domein.Persoon;
import nl.bzk.brp.bevraging.domein.PersoonIndicatie;
import nl.bzk.brp.bevraging.domein.Plaats;
import nl.bzk.brp.bevraging.domein.RedenOpschorting;
import nl.bzk.brp.bevraging.domein.SoortIndicatie;
import nl.bzk.brp.bevraging.domein.SoortPartij;
import nl.bzk.brp.bevraging.domein.SoortPersoon;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


/**
 * Unit test voor de {@link PersoonMapper} class.
 */
public class PersoonMapperTest {

    @Test
    public void testNullDomeinObject() {
        assertNull(PersoonMapper.mapDomeinObjectNaarDTO(null));
    }

    @Test
    public void testLeegDomeinObject() {
        assertNotNull(PersoonMapper.mapDomeinObjectNaarDTO(new Persoon(SoortPersoon.INGESCHREVENE)));
    }

    @Test
    public void testCompleetDomeinObjectZonderAdres() {
        Persoon persoonDO = getVolledigGevuldPersoonZonderAdres();
        nl.bzk.brp.bevraging.ws.basis.Persoon persoonDTO = PersoonMapper.mapDomeinObjectNaarDTO(persoonDO);

        assertEquals(123456789L, persoonDTO.getBsn().longValue());
        assertEquals(17, persoonDTO.getBijhoudingsGemeenteId());
        assertEquals("Gemeente X", persoonDTO.getBijhoudingsGemeente());
        assertEquals("New York", persoonDTO.getBuitenlandsePlaatsOverlijden());
        assertEquals("regio Z", persoonDTO.getBuitenlandseGeboorteRegio());
        assertEquals(new BigDecimal("20000203"), persoonDTO.getDatumGeboorte());
        assertEquals("Gemeente Y", persoonDTO.getGemeenteGeboorte());
        assertEquals(3, persoonDTO.getGemeenteIdGeboorte());
        assertEquals("V", persoonDTO.getGeslachtsAanduidingCode());
        assertEquals("Vrouw", persoonDTO.getGeslachtsAanduiding());
        assertEquals("Tester", persoonDTO.getGeslachtsNaam());
        assertEquals("Frankrijk", persoonDTO.getLandGeboorte());
        assertEquals(13, persoonDTO.getLandIdGeboorte());
        assertEquals("in vliegtuig", persoonDTO.getLocatieOmschrijvingGeboorte());
        assertEquals("Onbekend", persoonDTO.getRedenOpschorting());
        assertEquals("?", persoonDTO.getRedenOpschortingCode());
        // assertEquals("true", persoonDTO.getVerstrekkingsBeperking());
        assertEquals("Jan Peter Maria", persoonDTO.getVoornamen());
        assertEquals("tst", persoonDTO.getVoorvoegsel());
        assertEquals("Brugge", persoonDTO.getWoonplaatsGeboorte());
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
     * Genereert een volledig gevulde {@link Persoon} instantie (alle velden hebben een waarde), behalve het adres.
     *
     * @return een persoon instantie.
     */
    private Persoon getVolledigGevuldPersoonZonderAdres() {
        Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);

        persoon.setBurgerservicenummer(123456789L);
        persoon.setAdministratienummer(1234567890L);
        persoon.setBijhoudingsGemeente(bouwNieuweGemeente(17, "Gemeente X"));
        persoon.setBuitenlandseGeboorteplaats("Parijs");
        persoon.setBuitenlandsePlaatsOverlijden("New York");
        persoon.setBuitenlandseRegioGeboorte("regio Z");
        persoon.setGemeenteGeboorte(bouwNieuweGemeente(3, "Gemeente Y"));
        persoon.setGeslachtsnaam("Tester");
        persoon.setLandGeboorte(bouwNieuwLand(13, "Frankrijk"));
        persoon.setOmschrijvingGeboorteLocatie("in vliegtuig");
        persoon.setRedenOpschortingBijhouding(RedenOpschorting.ONBEKEND);
        persoon.setWoonplaatsGeboorte(bouwNieuweWoonplaats(9, "Brugge"));

        ReflectionTestUtils.setField(persoon, "geboorteDatum", 20000203);
        ReflectionTestUtils.setField(persoon, "geslachtsAanduiding", GeslachtsAanduiding.VROUW);
        ReflectionTestUtils.setField(persoon, "voornamen", "Jan Peter Maria");
        ReflectionTestUtils.setField(persoon, "voorvoegsel", "tst");

        Map<SoortIndicatie, PersoonIndicatie> indicaties = new HashMap<SoortIndicatie, PersoonIndicatie>();
        PersoonIndicatie indicatie = new PersoonIndicatie(persoon, SoortIndicatie.VERSTREKKINGSBEPERKING);
        indicatie.setWaarde(Boolean.TRUE);
        indicaties.put(SoortIndicatie.VERSTREKKINGSBEPERKING, indicatie);
        ReflectionTestUtils.setField(persoon, "indicaties", indicaties);

        return persoon;
    }

    /**
     * Creeert een nieuwe gemeente op basis van de opgegeven id en naam.
     *
     * @param id de id van de nieuwe gemeente.
     * @param naam de naam van de nieuwe gemeente.
     * @return de nieuwe gemeente.
     */
    private Partij bouwNieuweGemeente(final long id, final String naam) {
        Partij gemeente = new Partij(SoortPartij.GEMEENTE);
        ReflectionTestUtils.setField(gemeente, "id", id);
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
    private Land bouwNieuwLand(final long id, final String naam) {
        Land land = new Land(naam);
        ReflectionTestUtils.setField(land, "id", id);
        return land;
    }

    /**
     * Creeert een nieuwe plaats op basis van de opgegeven id en naam.
     *
     * @param id de id van de nieuwe plaats.
     * @param naam de naam van de nieuwe plaats.
     * @return de nieuwe plaats.
     */
    private Plaats bouwNieuweWoonplaats(final long id, final String naam) {
        Plaats plaats = new Plaats(naam);
        ReflectionTestUtils.setField(plaats, "id", id);
        return plaats;
    }

}
