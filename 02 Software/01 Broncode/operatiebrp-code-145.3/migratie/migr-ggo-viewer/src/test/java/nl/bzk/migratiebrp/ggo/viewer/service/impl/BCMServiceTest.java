/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.ggo.viewer.Lo3PersoonslijstTestHelper;
import nl.bzk.migratiebrp.ggo.viewer.log.FoutMelder;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoFoutRegel;
import nl.bzk.migratiebrp.util.excel.ExcelAdapterException;
import nl.gba.gbav.impl.checker.Checker;
import nl.gba.gbav.impl.util.configuration.ServiceLocatorSpringImpl;
import nl.gba.gbav.lo3.LO3PL;
import nl.gba.gbav.util.configuration.ServiceLocator;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BCMServiceTest {
    private static final String BCM_ENABLED = "bcmEnabled";

    @Mock
    private Checker checker;

    @InjectMocks
    private BcmServiceImpl bcmService;

    private final FoutMelder foutMelder = new FoutMelder();

    @Test
    public void testControleerDoorBCMUitgeschakeld() throws BerichtSyntaxException, ExcelAdapterException, Lo3SyntaxException, IOException {
        ((ServiceLocatorSpringImpl) ServiceLocator.getInstance()).setSystemProperty(BCM_ENABLED, "false");
        final Lo3Persoonslijst lo3Persoonslijst = Lo3PersoonslijstTestHelper.retrieveLo3Persoonslijsten("Omzetting.txt", new FoutMelder()).get(0);

        final List<GgoFoutRegel> bcmSignaleringen = bcmService.controleerDoorBCM(lo3Persoonslijst, foutMelder);
        Assert.assertNull("BCM Signaleringen moeten leeg zijn, BCM uitgeschakeld", bcmSignaleringen);
    }

    @Test
    public void testControleerDoorBCMIngeschakeld() throws BerichtSyntaxException, ExcelAdapterException, Lo3SyntaxException, IOException {
        ((ServiceLocatorSpringImpl) ServiceLocator.getInstance()).setSystemProperty(BCM_ENABLED, "true");
        final Lo3Persoonslijst lo3Persoonslijst = Lo3PersoonslijstTestHelper.retrieveLo3Persoonslijsten("Omzetting.txt", new FoutMelder()).get(0);

        final Set<String> bcmResultaten = new TreeSet<>();
        bcmResultaten.add("L3001,[1;0;0],test_controle(01.20.10;01.20.30)");
        Mockito.when(checker.checkOnePL(Matchers.any(LO3PL.class))).thenReturn(bcmResultaten);

        final List<GgoFoutRegel> bcmSignaleringen = bcmService.controleerDoorBCM(lo3Persoonslijst, foutMelder);
        Assert.assertNotNull("BCM Signaleringen mag niet leeg zijn, BCM ingeschakeld", bcmSignaleringen);
    }

    @Test
    public void testControleerDoorBCMIngeschakeldIOException() throws BerichtSyntaxException, ExcelAdapterException, Lo3SyntaxException, IOException {
        ((ServiceLocatorSpringImpl) ServiceLocator.getInstance()).setSystemProperty(BCM_ENABLED, "true");
        final Lo3Persoonslijst lo3Persoonslijst = Lo3PersoonslijstTestHelper.retrieveLo3Persoonslijsten("Omzetting.txt", new FoutMelder()).get(0);

        Mockito.when(checker.checkOnePL(Matchers.any(LO3PL.class))).thenThrow(new IOException("error"));

        Mockito.when(checker.checkOnePL(Matchers.any(String.class))).thenThrow(new IOException("error"));

        final List<GgoFoutRegel> bcmSignaleringen = bcmService.controleerDoorBCM(lo3Persoonslijst, foutMelder);

        Assert.assertNull("BCM Signaleringen moet leeg zijn, BCM ingeschakeld", bcmSignaleringen);
    }

    @Test
    public void getBcmControleOmschrijvingBestandNietGevonden() throws NoSuchFieldException, IllegalAccessException {
        // Cache leeg maken zodat test volgorde (zie #getBcmControleOmschrijvingSuccess) niet uit maakt
        final Field descriptionsCacheField = BcmServiceImpl.class.getDeclaredField("BCM_CHECK_DESCRIPTIONS");
        descriptionsCacheField.setAccessible(true);
        final Properties descriptionsCache = (Properties) descriptionsCacheField.get(null);
        descriptionsCache.clear();

        // Bestandslocatie property aanpassen zodat bestand niet gevonden kan worden.
        ((ServiceLocatorSpringImpl) ServiceLocator.getInstance()).setSystemProperty("bcmDescriptionsPath", File.separator + "niet-bestaande-map");

        Assert.assertEquals("Omschrijving kan niet gevonden worden", "Omschrijving onbekend", bcmService.getBcmControleOmschrijving("L3000"));
    }

    @Test
    public void getBcmControleOmschrijvingSuccess() throws UnsupportedEncodingException {
        // Bestandslocatie property aanpassen zodat bestand wel gevonden kan worden.
        final String filename = "bcmCheckDescriptions.properties";
        final String filepath = URLDecoder.decode(this.getClass().getClassLoader().getResource(filename).getPath(), "UTF-8");
        final String path = StringUtils.removeEnd(filepath, filename);
        ((ServiceLocatorSpringImpl) ServiceLocator.getInstance()).setSystemProperty("bcmDescriptionsPath", path);

        Assert.assertEquals(
                "Omschrijving moet gevonden worden",
                "Rubriek Datum opneming Persoon BEVAT EEN GELDIGE KALENDERDATUM",
                bcmService.getBcmControleOmschrijving("L3000"));
    }

    @Test
    public void useBCMDisabled() {
        ((ServiceLocatorSpringImpl) ServiceLocator.getInstance()).setSystemProperty(BCM_ENABLED, Boolean.FALSE.toString());
        Assert.assertFalse("BCM is uitgeschakeld", bcmService.useBCM());
    }

    @Test
    public void useBCMEnabled() {
        ((ServiceLocatorSpringImpl) ServiceLocator.getInstance()).setSystemProperty(BCM_ENABLED, Boolean.TRUE.toString());
        Assert.assertTrue("BCM is uitgeschakeld", bcmService.useBCM());
    }

    @Test
    public void setBCMDetailSystemPropertyTest() {
        bcmService.setBCMDetailSystemProperty();
        Assert.assertEquals("Moet True zijn", Boolean.TRUE.toString(), ServiceLocator.getInstance().getSystemProperty("showDetails"));
    }

    @Test
    public void setBCMTappSystemPropertyTest() {
        bcmService.setBCMTappSystemProperty();
        Assert.assertEquals("Moet False zijn", Boolean.FALSE.toString(), ServiceLocator.getInstance().getSystemProperty("useTappDatasource"));
    }

    @Test(expected = BcmServiceImpl.BCMResultaatException.class)
    public void converteerBCMResultaatFail01() throws BcmServiceImpl.BCMResultaatException {
        final Set<String> bcmResultaat = new TreeSet<>();
        bcmResultaat.add("L3001,1;0;0,test_controle(01.20.10;01.20.30)");
        bcmService.converteerBCMResultaat(bcmResultaat);
    }

    @Test(expected = BcmServiceImpl.BCMResultaatException.class)
    public void converteerBCMResultaatFail02() throws BcmServiceImpl.BCMResultaatException {
        final Set<String> bcmResultaat = new TreeSet<>();
        bcmResultaat.add("L3001,[1;0;twee],test_controle(01.20.10;01.20.30)");
        bcmService.converteerBCMResultaat(bcmResultaat);
    }

    @Test(expected = BcmServiceImpl.BCMResultaatException.class)
    public void converteerBCMResultaatFail03() throws BcmServiceImpl.BCMResultaatException {
        final Set<String> bcmResultaat = new TreeSet<>();
        bcmResultaat.add("L3001");
        bcmService.converteerBCMResultaat(bcmResultaat);
    }

    @Test(expected = BcmServiceImpl.BCMResultaatException.class)
    public void converteerBCMResultaatFail04() throws BcmServiceImpl.BCMResultaatException {
        final Set<String> bcmResultaat = new TreeSet<>();
        bcmResultaat.add("L3001,[1;0],test_controle(01.20.10;01.20.30)");
        bcmService.converteerBCMResultaat(bcmResultaat);
    }

    @Test
    public void converteerBCMResultaatSuccess01() throws BcmServiceImpl.BCMResultaatException {
        final Set<String> bcmResultaat = new TreeSet<>();
        bcmResultaat.add("L3001,[1;0;0],test_controle(01.20.10;01.20.30)");
        final List<GgoFoutRegel> foutRegels = bcmService.converteerBCMResultaat(bcmResultaat);
        Assert.assertNotNull(foutRegels);
        Assert.assertEquals("Kan maar 1 rij bevatten", 1, foutRegels.size());
        Assert.assertEquals("L3001", foutRegels.get(0).getCode());
        Assert.assertNotNull("Moet een omschrijving bevatten, al dan niet een dummy", foutRegels.get(0).getOmschrijving());
        Assert.assertNotNull("Herkomst kan niet null zijn", foutRegels.get(0).getHerkomst());
        Assert.assertEquals(Lo3CategorieEnum.CATEGORIE_01.getCategorieAsInt(), foutRegels.get(0).getHerkomst().getCategorieNr());
        Assert.assertEquals(0, foutRegels.get(0).getHerkomst().getStapelNr());
        Assert.assertEquals(0, foutRegels.get(0).getHerkomst().getVoorkomenNr());
    }

    /**
     * BCM controle kan gedaan worden over een gehele pl, dan zal het resultaat hiervan een -1 als categorie geven. Deze
     * moeten we kunnen parsen.
     */
    @Test
    public void converteerBCMResultaatSuccess02() throws BcmServiceImpl.BCMResultaatException {
        final Set<String> bcmResultaat = new TreeSet<>();
        bcmResultaat.add("L3001,[14;10;0],test_controle(01.20.10;01.20.30)");
        bcmResultaat.add("L3002,[-1;-1;-1],test_controle_pl");
        final List<GgoFoutRegel> foutRegels = bcmService.converteerBCMResultaat(bcmResultaat);
        Assert.assertNotNull(foutRegels);
        Assert.assertEquals("Kan maar 2 rijen bevatten", 2, foutRegels.size());
        Assert.assertNotNull("Herkomst kan niet null zijn", foutRegels.get(1).getHerkomst());
    }
}
