/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.viewer.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import nl.gba.gbav.impl.checker.Checker;
import nl.gba.gbav.impl.util.configuration.ServiceLocatorSpringImpl;
import nl.gba.gbav.lo3.LO3PL;
import nl.gba.gbav.util.configuration.ServiceLocator;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.viewer.log.FoutMelder;
import nl.moderniseringgba.migratie.conversie.viewer.log.FoutRegel;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.util.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BCMServiceTest {
    @Mock
    private Checker checker;

    @InjectMocks
    private BcmServiceImpl bcmService;

    private final FoutMelder foutMelder = new FoutMelder();

    @Test
    public void testControleerDoorBCMUitgeschakeld() throws IOException {
        ((ServiceLocatorSpringImpl) ServiceLocator.getInstance()).setSystemProperty("bcmEnabled", "false");
        final Lo3Persoonslijst lo3Persoonslijst = getValidLo3Persoonslijst();

        final List<FoutRegel> bcmSignaleringen = bcmService.controleerDoorBCM(lo3Persoonslijst, foutMelder);
        assertNull("BCM Signaleringen moeten leeg zijn, BCM uitgeschakeld", bcmSignaleringen);
    }

    @Test
    public void testControleerDoorBCMIngeschakeld() throws IOException {
        ((ServiceLocatorSpringImpl) ServiceLocator.getInstance()).setSystemProperty("bcmEnabled", "true");
        final Lo3Persoonslijst lo3Persoonslijst = getValidLo3Persoonslijst();

        final Set<String> bcmResultaten = new TreeSet<String>();
        bcmResultaten.add("L3001,[1;0;0],test_controle(01.20.10;01.20.30)");
        Mockito.when(checker.checkOnePL(Matchers.any(LO3PL.class))).thenReturn(bcmResultaten);

        final List<FoutRegel> bcmSignaleringen = bcmService.controleerDoorBCM(lo3Persoonslijst, foutMelder);
        assertNotNull("BCM Signaleringen mag niet leeg zijn, BCM ingeschakeld", bcmSignaleringen);
    }

    @Test
    public void testControleerDoorBCMIngeschakeldIOException() throws IOException {
        ((ServiceLocatorSpringImpl) ServiceLocator.getInstance()).setSystemProperty("bcmEnabled", "true");
        final Lo3Persoonslijst lo3Persoonslijst = getValidLo3Persoonslijst();

        Mockito.when(checker.checkOnePL(Matchers.any(LO3PL.class))).thenThrow(new IOException("error"));

        Mockito.when(checker.checkOnePL(Matchers.any(String.class))).thenThrow(new IOException("error"));

        final List<FoutRegel> bcmSignaleringen = bcmService.controleerDoorBCM(lo3Persoonslijst, foutMelder);

        assertNull("BCM Signaleringen moet leeg zijn, BCM ingeschakeld", bcmSignaleringen);
    }

    @Test
    public void getBcmControleOmschrijvingBestandNietGevonden() {
        // Bestandslocatie property aanpassen zodat bestand niet gevonden kan worden.
        ((ServiceLocatorSpringImpl) ServiceLocator.getInstance()).setSystemProperty("bcmDescriptionsPath",
                File.separator + "niet-bestaande-map");

        assertEquals("Omschrijving kan niet gevonden worden", "Omschrijving onbekend",
                bcmService.getBcmControleOmschrijving("L3000"));
    }

    @Test
    public void getBcmControleOmschrijvingSuccess() throws UnsupportedEncodingException {
        // Bestandslocatie property aanpassen zodat bestand wel gevonden kan worden.
        final String filename = "bcmCheckDescriptions.properties";
        final String filepath =
                URLDecoder.decode(this.getClass().getClassLoader().getResource(filename).getPath(), "UTF-8");
        final String path = StringUtils.removeEnd(filepath, filename);
        ((ServiceLocatorSpringImpl) ServiceLocator.getInstance()).setSystemProperty("bcmDescriptionsPath", path);

        assertEquals("Omschrijving moet gevonden worden",
                "Rubriek Datum opneming Persoon BEVAT EEN GELDIGE KALENDERDATUM",
                bcmService.getBcmControleOmschrijving("L3000"));
    }

    @Test
    public void useBCMDisabled() {
        ((ServiceLocatorSpringImpl) ServiceLocator.getInstance()).setSystemProperty("bcmEnabled",
                Boolean.FALSE.toString());
        assertFalse("BCM is uitgeschakeld", bcmService.useBCM());
    }

    @Test
    public void useBCMEnabled() {
        ((ServiceLocatorSpringImpl) ServiceLocator.getInstance()).setSystemProperty("bcmEnabled",
                Boolean.TRUE.toString());
        assertTrue("BCM is uitgeschakeld", bcmService.useBCM());
    }

    @Test
    public void setBCMDetailSystemPropertyTest() {
        bcmService.setBCMDetailSystemProperty();
        assertEquals("Moet True zijn", Boolean.TRUE.toString(),
                ServiceLocator.getInstance().getSystemProperty("showDetails"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void converteerBCMResultaatFail01() {
        final Set<String> bcmResultaat = new TreeSet<String>();
        bcmResultaat.add("L3001,1;0;0,test_controle(01.20.10;01.20.30)");
        bcmService.converteerBCMResultaat(bcmResultaat);
    }

    @Test(expected = IllegalArgumentException.class)
    public void converteerBCMResultaatFail02() {
        final Set<String> bcmResultaat = new TreeSet<String>();
        bcmResultaat.add("L3001,[1;0;twee],test_controle(01.20.10;01.20.30)");
        bcmService.converteerBCMResultaat(bcmResultaat);
    }

    @Test(expected = IllegalArgumentException.class)
    public void converteerBCMResultaatFail03() {
        final Set<String> bcmResultaat = new TreeSet<String>();
        bcmResultaat.add("L3001");
        bcmService.converteerBCMResultaat(bcmResultaat);
    }

    @Test(expected = IllegalArgumentException.class)
    public void converteerBCMResultaatFail04() {
        final Set<String> bcmResultaat = new TreeSet<String>();
        bcmResultaat.add("L3001,[1;0],test_controle(01.20.10;01.20.30)");
        bcmService.converteerBCMResultaat(bcmResultaat);
    }

    @Test
    public void converteerBCMResultaatSuccess01() {
        final Set<String> bcmResultaat = new TreeSet<String>();
        bcmResultaat.add("L3001,[1;0;0],test_controle(01.20.10;01.20.30)");
        final List<FoutRegel> foutRegels = bcmService.converteerBCMResultaat(bcmResultaat);
        assertNotNull(foutRegels);
        assertEquals("Kan maar 1 rij bevatten", 1, foutRegels.size());
        assertEquals("L3001", foutRegels.get(0).getCode());
        assertNotNull("Moet een omschrijving bevatten, al dan niet een dummy", foutRegels.get(0).getOmschrijving());
        assertNotNull("Herkomst kan niet null zijn", foutRegels.get(0).getLo3Herkomst());
        assertEquals(Lo3CategorieEnum.CATEGORIE_01, foutRegels.get(0).getLo3Herkomst().getCategorie());
        assertEquals(0, foutRegels.get(0).getLo3Herkomst().getStapel());
        assertEquals(0, foutRegels.get(0).getLo3Herkomst().getVoorkomen());
    }

    @Test
    public void converteerBCMResultaatSuccess02() {
        final Set<String> bcmResultaat = new TreeSet<String>();
        bcmResultaat.add("L3001,[14;10;0],test_controle(01.20.10;01.20.30)");
        bcmResultaat.add("L3002,[-1;-1;-1],test_controle_pl");
        final List<FoutRegel> foutRegels = bcmService.converteerBCMResultaat(bcmResultaat);
        assertNotNull(foutRegels);
        assertEquals("Kan maar 2 rijen bevatten", 2, foutRegels.size());
        assertNotNull("Herkomst kan niet null zijn", foutRegels.get(1).getLo3Herkomst());
    }

    private Lo3Persoonslijst getValidLo3Persoonslijst() throws IOException {
        final String filename = "Omzetting.txt";
        final InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(filename);
        final List<Lo3Persoonslijst> plList =
                new LeesServiceImpl().leesLo3Persoonslijst(filename, IOUtils.toByteArray(inputStream), foutMelder);
        return plList.get(0);
    }
}
