/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.business.handlers;

import java.util.*;

import nl.bzk.brp.bevraging.business.dto.BerichtContext;
import nl.bzk.brp.bevraging.business.dto.antwoord.PersoonZoekCriteriaAntwoord;
import nl.bzk.brp.bevraging.business.dto.verzoek.PersoonZoekCriteria;
import nl.bzk.brp.bevraging.business.handlers.BerichtVerwerkingsStap;
import nl.bzk.brp.bevraging.domein.FunctieAdres;
import nl.bzk.brp.bevraging.domein.SoortPersoon;
import nl.bzk.brp.poc.business.dto.antwoord.BijhoudingResultaat;
import nl.bzk.brp.poc.business.dto.verzoek.Bijhouding;
import nl.bzk.brp.poc.business.dto.verzoek.VerhuisVerzoek;
import nl.bzk.brp.poc.business.dto.verzoek.Verhuizing;
//import nl.bzk.brp.poc.business.util.DatumUtility;
import nl.bzk.brp.poc.dal.PocPersoonRepository;
import nl.bzk.brp.poc.domein.PocPersoon;
import nl.bzk.brp.poc.domein.PocPersoonAdres;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import static org.junit.Assert.assertEquals;

/**
 * Unit test class voor de {@link BedrijfsRegelStap} class.
 */
public class BedrijfsRegelStapTest {

    @Mock
    private PocPersoonRepository persoonRepository;
    private BedrijfsRegelStap    stap;

    /**
     * Test de Bedrijfsregelstap voor niet bijhoudings berichten. Deze zou altijd gewoon moeten doorgaan, daar voor
     * niet
     * bijhoudingsberichten er geen bedrijfsregels gecontroleerd hoeven te worden.
     */
    @Test
    public void testNietBijhoudingsBericht() {
        assertEquals(BerichtVerwerkingsStap.DOORGAAN_MET_VERWERKING,
                stap.voerVerwerkingsStapUitVoorBericht(new PersoonZoekCriteria(), new BerichtContext(),
                        new PersoonZoekCriteriaAntwoord()));
    }

    /**
     * Test om te kijken of de bedrijfsregelstap correct werkt indien er een bericht wordt verwerkt dat aan alle te
     * controleren bedrijfsregels voldoet.
     */
    /*@Test
    public void testValideBedrijfsregels() {
        Calendar datum = Calendar.getInstance();
        datum.add(Calendar.DATE, -1);
        VerhuisVerzoek verzoek = bouwVerhuisVerzoek(DatumUtility.zetDatumOmNaarInteger(datum));

        assertEquals(BerichtVerwerkingsStap.DOORGAAN_MET_VERWERKING,
                stap.voerVerwerkingsStapUitVoorBericht(verzoek, new BerichtContext(), new BijhoudingResultaat()));
    }*/

    /**
     * Test om te kijken of de bedrijfsregelstap correct een 'stop verwerking' afgeeft indien een bedrijfsregel faalt
     * voor een ingeschoten bericht.
     */
   /* @Test
    public void testFalendeBedrijfsregels() {
        Calendar datum = Calendar.getInstance();
        datum.add(Calendar.DATE, 1);
        VerhuisVerzoek verzoek = bouwVerhuisVerzoek(DatumUtility.zetDatumOmNaarInteger(datum));

        assertEquals(BerichtVerwerkingsStap.STOP_VERWERKING,
                stap.voerVerwerkingsStapUitVoorBericht(verzoek, new BerichtContext(), new BijhoudingResultaat()));
    }*/


    /**
     * Initialiseert de tests door de {@link BedrijfsRegelStap} te instantieren, de mock te initialiseren en deze mock
     * voor de repository aan de stap toe te voegen.
     */
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        stap = new BedrijfsRegelStap();
        ReflectionTestUtils.setField(stap, "persoonRepository", persoonRepository);
        Mockito.when(persoonRepository.findByBurgerservicenummer(123456789L)).thenReturn(getPersonenLijst(123456789L));
    }

    /**
     * Bouwt een verhuis verzoek op, met daarin een persoon (met alleen een bsn) en een adres (met alleen een
     * postcode).
     * De datum van de aanvang geldigheid wordt op de opgegeven datum gezet.
     *
     * @param datum de datum van aanvang van de verhuizing.
     * @return een verhuis verzoek.
     */
    private VerhuisVerzoek bouwVerhuisVerzoek(final Integer datum) {
        PocPersoon persoon = new PocPersoon(SoortPersoon.INGESCHREVENE);
        persoon.setBurgerservicenummer(123456789L);
        PocPersoonAdres adres = new PocPersoonAdres(null, FunctieAdres.WOONADRES);
        adres.setPostcode("Test2");

        Verhuizing verhuizing = new Verhuizing();
        verhuizing.setDatumAanvangGeldigheid(Calendar.getInstance().getTime());
        verhuizing.setNieuwAdres(adres);
        //verhuizing.setVerhuizer(persoon);

        Bijhouding bijhouding = new Bijhouding();
        bijhouding.setVerhuizing(verhuizing);

        return new VerhuisVerzoek(bijhouding);
    }

    /**
     * Retourneert een lijst van personen, met daarin één persoon, met de opgegeven bsn en een enkel adres (met alleen
     * een postcode).
     *
     * @param bsn burgerservicenummer van de persoon die in de lijst wordt geretourneerd.
     * @return lijst van personen met daarin een enkel persoon.
     */
    private List<PocPersoon> getPersonenLijst(final long bsn) {
        PocPersoon persoon = new PocPersoon(SoortPersoon.INGESCHREVENE);
        persoon.setBurgerservicenummer(bsn);

        PocPersoonAdres adres = new PocPersoonAdres(persoon, FunctieAdres.WOONADRES);
        adres.setPostcode("Test1");

        Set<PocPersoonAdres> adressen = new HashSet<PocPersoonAdres>();
        adressen.add(adres);
        persoon.setAdressen(adressen);

        List<PocPersoon> personen = new ArrayList<PocPersoon>();
        personen.add(persoon);
        return personen;
    }

}
