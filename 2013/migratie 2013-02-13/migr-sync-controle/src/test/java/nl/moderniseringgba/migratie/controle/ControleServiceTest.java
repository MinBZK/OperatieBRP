/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.controle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import nl.moderniseringgba.isc.migratie.domein.entities.Gemeente;
import nl.moderniseringgba.isc.migratie.repository.GemeenteRepository;
import nl.moderniseringgba.migratie.controle.impl.ControleServiceImpl;
import nl.moderniseringgba.migratie.controle.rapport.ControleNiveauEnum;
import nl.moderniseringgba.migratie.controle.rapport.ControleRapport;
import nl.moderniseringgba.migratie.controle.rapport.HerstelActieEnum;
import nl.moderniseringgba.migratie.controle.rapport.Opties;
import nl.moderniseringgba.migratie.logging.domein.entities.InitVullingLog;
import nl.moderniseringgba.migratie.logging.service.LoggingService;
import nl.moderniseringgba.migratie.synchronisatie.domein.logging.entity.BerichtLog;
import nl.moderniseringgba.migratie.synchronisatie.service.BrpDalService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ControleServiceTest {

    private static final String LO3PL =
            "00697011640110010817238743501200092995889450210004Mart0240005Vries03100081990010103200040599033000460300410001M6110001E8110004059981200071 A9102851000819900101861000819900102021720110010192829389501200099911223340210006Jannie0240004Smit03100081969010103200041901033000460300410001M6210008199001018110004059981200071 A9102851000819900101861000819900102031750110010172625463201200093827261340210008Mitchell0240005Vries03100081970010103200041900033000460300410001M6210008199001018110004059981200071 A910285100081990010186100081990010207055681000819900101701000108010001180200172012010100000000008106091000405990920008199001011010001W102000405991030008199001011110001.7210001G851000819900101861000819900102";
    private static final String GEWIJZIGDE_LO3_PL =
            "00697011640110010817238743501200092995889450210004Mara0240005Vries03100081990010103200040599033000460300410001M6110001E8110004059981200071 A9102851000819900101861000819900102021720110010192829389501200099911223340210006Jannie0240004Smit03100081969010103200041901033000460300410001M6210008199001018110004059981200071 A9102851000819900101861000819900102031750110010172625463201200093827261340210008Mitchell0240005Vries03100081970010103200041900033000460300410001M6210008199001018110004059981200071 A910285100081990010186100081990010207055681000819900101701000108010001280200172012010100000000008106091000405990920008199001011010001W102000405991030008199001011110001.7210001G851000819900101861000819900102";
    private static final String LO3PL_NEWER_VERSION =
            "00697011640110010817238743501200092995889450210004Mart0240005Vries03100081990010103200040599033000460300410001M6110001E8110004059981200071 A9102851000819900101861000819900102021720110010192829389501200099911223340210006Jannie0240004Smit03100081969010103200041901033000460300410001M6210008199001018110004059981200071 A9102851000819900101861000819900102031750110010172625463201200093827261340210008Mitchell0240005Vries03100081970010103200041900033000460300410001M6210008199001018110004059981200071 A910285100081990010186100081990010207055681000819900101701000108010001380200172012010100000000008106091000405990920008199001011010001W102000405991030008199001011110001.7210001G851000819900101861000819900102";
    private static final String LO3PL_NEWER_DATE =
            "00697011640110010817238743501200092995889450210004Mart0240005Vries03100081990010103200040599033000460300410001M6110001E8110004059981200071 A9102851000819900101861000819900102021720110010192829389501200099911223340210006Jannie0240004Smit03100081969010103200041901033000460300410001M6210008199001018110004059981200071 A9102851000819900101861000819900102031750110010172625463201200093827261340210008Mitchell0240005Vries03100081970010103200041900033000460300410001M6210008199001018110004059981200071 A910285100081990010186100081990010207055681000819900101701000108010001180200172012010200000000008106091000405990920008199001011010001W102000405991030008199001011110001.7210001G851000819900101861000819900102";

    @Mock
    private BrpDalService brpDalService;

    @Mock
    private LoggingService loggingService;

    @Mock
    private GemeenteRepository gemeenteRepo;

    @InjectMocks
    private ControleServiceImpl controleService;

    @Test
    public void controleerPLenGeenGbavPl() {
        final Opties opties = new Opties();
        final List<Long> anummers = createAnummerLijst1Pers();
        final BerichtLog berichtLog = createBerichtLog(LO3PL);

        Mockito.when(brpDalService.zoekBerichtLogOpAnummer(anummers.get(0))).thenReturn(berichtLog);
        Mockito.when(gemeenteRepo.findGemeente(Matchers.anyInt())).thenReturn(new Gemeente());

        final ControleRapport rapport = new ControleRapport();
        controleService.controleerPLen(anummers, opties, rapport);

        assertNotNull("Mag niet leeg zijn.", rapport);
        assertTrue("Er zou 1 verschillende PL moeten zijn.", rapport.getAantalVerschillen() == 1);
        assertNotNull("Er zou een herstelactie moeten zijn.", rapport.getHerstellijst().getHerstelActies().get(0));
        assertEquals("Het zou een sync naar GBA moeten zijn.", rapport.getHerstellijst().getHerstelActies().get(0)
                .getActie(), HerstelActieEnum.SYNC_NAAR_GBA);
    }

    @Test
    public void controleerPLenGeenHash() {
        final Opties opties = new Opties();
        final List<Long> anummers = createAnummerLijst1Pers();
        final InitVullingLog gbavLogRegel = createGbavLogRegel(LO3PL);
        final BerichtLog berichtLog =
                new BerichtLog("test_ref", "test_bron", new Timestamp(System.currentTimeMillis()));

        Mockito.when(loggingService.findLog(anummers.get(0))).thenReturn(gbavLogRegel);
        Mockito.when(brpDalService.zoekBerichtLogOpAnummer(anummers.get(0))).thenReturn(berichtLog);
        final ControleRapport rapport = new ControleRapport();
        controleService.controleerPLen(anummers, opties, rapport);

        assertNotNull("Mag niet leeg zijn.", rapport);
        assertTrue("Er zou 1 verschillende PL moeten zijn.", rapport.getAantalVerschillen() == 1);
        assertNotNull("Er zou een herstelactie moeten zijn.", rapport.getHerstellijst().getHerstelActies().get(0));
        assertEquals("Het zou een sync naar GBA moeten zijn.", rapport.getHerstellijst().getHerstelActies().get(0)
                .getActie(), HerstelActieEnum.SYNC_NAAR_BRP);
    }

    @Test
    public void controleerPLenGeenBrpPl() {
        final Opties opties = new Opties();
        final List<Long> anummers = createAnummerLijst1Pers();
        final InitVullingLog gbavLogRegel = createGbavLogRegel(LO3PL);

        Mockito.when(loggingService.findLog(anummers.get(0))).thenReturn(gbavLogRegel);

        final ControleRapport rapport = new ControleRapport();
        controleService.controleerPLen(anummers, opties, rapport);

        assertNotNull("Mag niet leeg zijn.", rapport);
        assertTrue("Er zou 1 verschillende PL moeten zijn.", rapport.getAantalVerschillen() == 1);
        assertNotNull("Er zou een herstelactie moeten zijn.", rapport.getHerstellijst().getHerstelActies().get(0));
        assertEquals("Het zou een sync naar GBA moeten zijn.", rapport.getHerstellijst().getHerstelActies().get(0)
                .getActie(), HerstelActieEnum.SYNC_NAAR_BRP);
    }

    @Test
    public void controleerPLenGeenBerichtLog() {
        final Opties opties = new Opties();
        final List<Long> anummers = createAnummerLijst1Pers();
        final InitVullingLog gbavLogRegel = createGbavLogRegel(LO3PL);

        Mockito.when(loggingService.findLog(anummers.get(0))).thenReturn(gbavLogRegel);
        Mockito.when(brpDalService.zoekBerichtLogOpAnummer(anummers.get(0))).thenReturn(null);
        final ControleRapport rapport = new ControleRapport();
        controleService.controleerPLen(anummers, opties, rapport);

        assertNotNull("Mag niet leeg zijn.", rapport);
        assertTrue("Er zou 1 verschillende PL moeten zijn.", rapport.getAantalVerschillen() == 1);
        assertNotNull("Er zou een herstelactie moeten zijn.", rapport.getHerstellijst().getHerstelActies().get(0));
        assertEquals("Het zou een sync naar GBA moeten zijn.", rapport.getHerstellijst().getHerstelActies().get(0)
                .getActie(), HerstelActieEnum.SYNC_NAAR_BRP);
    }

    @Test
    public void controleerPLenGeenFouteOptie() {
        final Opties opties = new Opties();
        final List<Long> anummers = createAnummerLijst1Pers();
        final InitVullingLog gbavLogRegel = createGbavLogRegel(LO3PL);
        final BerichtLog berichtLog = createBerichtLog(LO3PL_NEWER_DATE);

        Mockito.when(loggingService.findLog(anummers.get(0))).thenReturn(gbavLogRegel);
        Mockito.when(brpDalService.zoekBerichtLogOpAnummer(anummers.get(0))).thenReturn(berichtLog);
        final ControleRapport rapport = new ControleRapport();
        opties.setControleNiveau(ControleNiveauEnum.VERSIE_EN_DATUM);
        controleService.controleerPLen(anummers, opties, rapport);

        assertNotNull("Mag niet leeg zijn.", rapport);
        assertTrue("Er zou 1 verschillende PL moeten zijn.", rapport.getAantalVerschillen() == 1);
        assertNotNull("Er zou een herstelactie moeten zijn.", rapport.getHerstellijst().getHerstelActies().get(0));
        assertEquals("Het zou een sync naar GBA moeten zijn.", rapport.getHerstellijst().getHerstelActies().get(0)
                .getActie(), HerstelActieEnum.SYNC_NAAR_BRP);
    }

    @Test
    public void controleerPLenSimpleGelijkePL() {
        final Opties opties = new Opties();
        opties.setControleNiveau(ControleNiveauEnum.VERSIE_EN_DATUM);

        final List<Long> anummers = createAnummerLijst1Pers();
        final InitVullingLog gbavLogRegel = createGbavLogRegel(LO3PL);
        final BerichtLog berichtLog = createBerichtLog(LO3PL);

        Mockito.when(loggingService.findLog(anummers.get(0))).thenReturn(gbavLogRegel);
        Mockito.when(brpDalService.zoekBerichtLogOpAnummer(anummers.get(0))).thenReturn(berichtLog);

        final ControleRapport rapport = new ControleRapport();
        controleService.controleerPLen(anummers, opties, rapport);

        assertNotNull("Mag niet leeg zijn.", rapport);
        assertTrue("Er zou geen verschillende PL moeten zijn.", rapport.getAantalVerschillen() == 0);
        assertTrue("Er zou geen herstelactie moeten zijn.", rapport.getHerstellijst().getHerstelActies().size() == 0);
    }

    @Test
    public void controleerPLenGelijkePLGeenHash() {
        final Opties opties = new Opties();
        opties.setControleNiveau(ControleNiveauEnum.VERSIE_EN_DATUM);

        final List<Long> anummers = createAnummerLijst1Pers();
        final InitVullingLog gbavLogRegel = createGbavLogRegel(LO3PL);
        final BerichtLog berichtLog = createBerichtLog(LO3PL);

        Mockito.when(loggingService.findLog(anummers.get(0))).thenReturn(gbavLogRegel);
        Mockito.when(brpDalService.zoekBerichtLogOpAnummer(anummers.get(0))).thenReturn(berichtLog);

        final ControleRapport rapport = new ControleRapport();
        controleService.controleerPLen(anummers, opties, rapport);

        assertNotNull("Mag niet leeg zijn.", rapport);
        assertTrue("Er zou geen verschillende PL moeten zijn.", rapport.getAantalVerschillen() == 0);
        assertTrue("Er zou geen herstelactie moeten zijn.", rapport.getHerstellijst().getHerstelActies().size() == 0);
    }

    @Test
    public void controleerPLenVolledigGelijkePL() {
        final Opties opties = new Opties();
        opties.setControleNiveau(ControleNiveauEnum.VOLLEDIGE_PL);

        final List<Long> anummers = createAnummerLijst1Pers();
        final InitVullingLog gbavLogRegel = createGbavLogRegel(LO3PL);
        final BerichtLog berichtLog = createBerichtLog(LO3PL);

        Mockito.when(loggingService.findLog(anummers.get(0))).thenReturn(gbavLogRegel);
        Mockito.when(brpDalService.zoekBerichtLogOpAnummer(anummers.get(0))).thenReturn(berichtLog);

        final ControleRapport rapport = new ControleRapport();
        controleService.controleerPLen(anummers, opties, rapport);

        assertNotNull("Mag niet leeg zijn.", rapport);
        assertTrue("Er zou geen verschillende PL moeten zijn.", rapport.getAantalVerschillen() == 0);
        assertTrue("Er zou geen herstelactie moeten zijn.", rapport.getHerstellijst().getHerstelActies().size() == 0);
    }

    @Test
    public void controleerPLenSimpleVerschillendePL() {
        final Opties opties = new Opties();
        opties.setControleNiveau(ControleNiveauEnum.VERSIE_EN_DATUM);

        final List<Long> anummers = createAnummerLijst1Pers();
        final InitVullingLog gbavLogRegel = createGbavLogRegel(GEWIJZIGDE_LO3_PL);
        final BerichtLog berichtLog = createBerichtLog(LO3PL);

        Mockito.when(loggingService.findLog(anummers.get(0))).thenReturn(gbavLogRegel);
        Mockito.when(brpDalService.zoekBerichtLogOpAnummer(anummers.get(0))).thenReturn(berichtLog);

        final ControleRapport rapport = new ControleRapport();
        controleService.controleerPLen(anummers, opties, rapport);

        assertNotNull("Mag niet leeg zijn.", rapport);
        assertTrue("Er zou een verschillende PL moeten zijn.", rapport.getAantalVerschillen() == 1);
        assertTrue("Er zou een herstelactie moeten zijn.", rapport.getHerstellijst().getHerstelActies().size() == 1);
        assertEquals("Het zou een sync naar GBA moeten zijn.", rapport.getHerstellijst().getHerstelActies().get(0)
                .getActie(), HerstelActieEnum.SYNC_NAAR_BRP);
    }

    @Test
    public void controleerPLenVolledigVerschillendePL() {
        final Opties opties = new Opties();
        opties.setControleNiveau(ControleNiveauEnum.VOLLEDIGE_PL);

        final List<Long> anummers = createAnummerLijst1Pers();
        final InitVullingLog gbavLogRegel = createGbavLogRegel(LO3PL);
        final BerichtLog berichtLog = createBerichtLog(GEWIJZIGDE_LO3_PL);

        Mockito.when(loggingService.findLog(anummers.get(0))).thenReturn(gbavLogRegel);
        Mockito.when(brpDalService.zoekBerichtLogOpAnummer(anummers.get(0))).thenReturn(berichtLog);

        final ControleRapport rapport = new ControleRapport();
        controleService.controleerPLen(anummers, opties, rapport);

        assertNotNull("Mag niet leeg zijn.", rapport);
        assertTrue("Er zou een verschillende PL moeten zijn.", rapport.getAantalVerschillen() == 1);
        assertTrue("Er zou een herstelactie moeten zijn.", rapport.getHerstellijst().getHerstelActies().size() == 1);
        assertEquals("Het zou een sync naar GBA moeten zijn.", rapport.getHerstellijst().getHerstelActies().get(0)
                .getActie(), HerstelActieEnum.SYNC_NAAR_BRP);
    }

    @Test
    public void controleerPLenGeenBrpPlWrongSync() {
        final Opties opties = new Opties();
        final List<Long> anummers = createAnummerLijst1Pers();
        final InitVullingLog gbavLogRegel = createGbavLogRegel(LO3PL);

        Mockito.when(loggingService.findLog(anummers.get(0))).thenReturn(gbavLogRegel);
        Mockito.when(gemeenteRepo.findGemeente(Matchers.anyInt())).thenReturn(new Gemeente());
        final ControleRapport rapport = new ControleRapport();
        controleService.controleerPLen(anummers, opties, rapport);

        assertNotNull("Mag niet leeg zijn.", rapport);
        assertTrue("Er zou 1 verschillende PL moeten zijn.", rapport.getAantalVerschillen() == 1);
        assertNotNull("Er zou een herstelactie moeten zijn.", rapport.getHerstellijst().getHerstelActies().get(0));
        assertEquals("Het zou een sync naar GBA moeten zijn.", rapport.getHerstellijst().getHerstelActies().get(0)
                .getActie(), HerstelActieEnum.SYNC_NAAR_GBA);
        assertNotNull("Er zou een bijzondere situatie moeten zijn.", rapport.getBijzondereSituaties().get(0));
        assertTrue("Er zou een bijzondere situatie moeten zijn.", rapport
                .getBijzondereSituaties()
                .get(0)
                .getFoutmelding()
                .equals("De BRP persoonslijst is niet aanwezig maar de sync zou wel van BRP naar GBA-V moeten"
                        + " gaan. Dit is een gekke situatie!"));
    }

    @Test
    public void controleerPLenGeenGbavPlWrongSync() {
        final Opties opties = new Opties();
        final List<Long> anummers = createAnummerLijst1Pers();
        final BerichtLog berichtLog = createBerichtLog(LO3PL);

        Mockito.when(brpDalService.zoekBerichtLogOpAnummer(anummers.get(0))).thenReturn(berichtLog);

        final ControleRapport rapport = new ControleRapport();
        controleService.controleerPLen(anummers, opties, rapport);

        assertNotNull("Mag niet leeg zijn.", rapport);
        assertTrue("Er zou 1 verschillende PL moeten zijn.", rapport.getAantalVerschillen() == 1);
        assertNotNull("Er zou een herstelactie moeten zijn.", rapport.getHerstellijst().getHerstelActies().get(0));
        assertEquals("Het zou een sync naar BRP moeten zijn.", rapport.getHerstellijst().getHerstelActies().get(0)
                .getActie(), HerstelActieEnum.SYNC_NAAR_BRP);
        assertNotNull("Er zou een bijzondere situatie moeten zijn.", rapport.getBijzondereSituaties().get(0));
        assertTrue("Er zou een bijzondere situatie moeten zijn.", rapport
                .getBijzondereSituaties()
                .get(0)
                .getFoutmelding()
                .equals("De GBA-V persoonslijst is niet aanwezig maar de sync zou wel van GBA-V naar BRP moeten "
                        + "gaan. Dit is een gekke situatie!"));
    }

    @Test
    public void controleerPLenVolledigVerschillendePLWrongOpnameDatumGBAV() {
        final Opties opties = new Opties();
        opties.setControleNiveau(ControleNiveauEnum.VOLLEDIGE_PL);

        final List<Long> anummers = createAnummerLijst1Pers();
        final InitVullingLog gbavLogRegel = createGbavLogRegel(LO3PL);
        final BerichtLog berichtLog = createBerichtLog(LO3PL_NEWER_DATE);

        Mockito.when(loggingService.findLog(anummers.get(0))).thenReturn(gbavLogRegel);
        Mockito.when(brpDalService.zoekBerichtLogOpAnummer(anummers.get(0))).thenReturn(berichtLog);

        final ControleRapport rapport = new ControleRapport();
        controleService.controleerPLen(anummers, opties, rapport);

        assertNotNull("Mag niet leeg zijn.", rapport);
        assertTrue("Er zou een verschillende PL moeten zijn.", rapport.getAantalVerschillen() == 1);
        assertTrue("Er zou een herstelactie moeten zijn.", rapport.getHerstellijst().getHerstelActies().size() == 1);
        assertEquals("Het zou een sync naar BRP moeten zijn.", rapport.getHerstellijst().getHerstelActies().get(0)
                .getActie(), HerstelActieEnum.SYNC_NAAR_BRP);
        assertNotNull("Er zou een bijzondere situatie moeten zijn.", rapport.getBijzondereSituaties().get(0));
        assertTrue("Er zou een bijzondere situatie moeten zijn.", rapport
                .getBijzondereSituaties()
                .get(0)
                .getFoutmelding()
                .equals("De GBA-V persoonslijst zou nieuwer moeten zijn maar dit is niet het geval. Dit is een "
                        + "gekke situatie aangzien de sync naar BRP gaat."));
    }

    @Test
    public void controleerPLenVolledigVerschillendePLWrongVersionGBAV() {
        final Opties opties = new Opties();
        opties.setControleNiveau(ControleNiveauEnum.VOLLEDIGE_PL);

        final List<Long> anummers = createAnummerLijst1Pers();
        final InitVullingLog gbavLogRegel = createGbavLogRegel(LO3PL);
        final BerichtLog berichtLog = createBerichtLog(LO3PL_NEWER_VERSION);

        Mockito.when(loggingService.findLog(anummers.get(0))).thenReturn(gbavLogRegel);
        Mockito.when(brpDalService.zoekBerichtLogOpAnummer(anummers.get(0))).thenReturn(berichtLog);

        final ControleRapport rapport = new ControleRapport();
        controleService.controleerPLen(anummers, opties, rapport);

        assertNotNull("Mag niet leeg zijn.", rapport);
        assertTrue("Er zou een verschillende PL moeten zijn.", rapport.getAantalVerschillen() == 1);
        assertTrue("Er zou een herstelactie moeten zijn.", rapport.getHerstellijst().getHerstelActies().size() == 1);
        assertEquals("Het zou een sync naar BRP moeten zijn.", rapport.getHerstellijst().getHerstelActies().get(0)
                .getActie(), HerstelActieEnum.SYNC_NAAR_BRP);
        assertNotNull("Er zou een bijzondere situatie moeten zijn.", rapport.getBijzondereSituaties().get(0));
        assertTrue("Er zou een bijzondere situatie moeten zijn.", rapport
                .getBijzondereSituaties()
                .get(0)
                .getFoutmelding()
                .equals("De GBA-V persoonslijst zou nieuwer moeten zijn maar dit is niet het geval. Dit is een "
                        + "gekke situatie aangzien de sync naar BRP gaat."));
    }

    @Test
    public void controleerPLenVolledigVerschillendePLWrongVersionBRP() {
        final Opties opties = new Opties();
        opties.setControleNiveau(ControleNiveauEnum.VOLLEDIGE_PL);

        final List<Long> anummers = createAnummerLijst1Pers();
        final InitVullingLog gbavLogRegel = createGbavLogRegel(LO3PL_NEWER_VERSION);
        final BerichtLog berichtLog = createBerichtLog(LO3PL);

        Mockito.when(loggingService.findLog(anummers.get(0))).thenReturn(gbavLogRegel);
        Mockito.when(brpDalService.zoekBerichtLogOpAnummer(anummers.get(0))).thenReturn(berichtLog);
        Mockito.when(gemeenteRepo.findGemeente(Matchers.anyInt())).thenReturn(new Gemeente());

        final ControleRapport rapport = new ControleRapport();
        controleService.controleerPLen(anummers, opties, rapport);

        assertNotNull("Mag niet leeg zijn.", rapport);
        assertTrue("Er zou een verschillende PL moeten zijn.", rapport.getAantalVerschillen() == 1);
        assertTrue("Er zou een herstelactie moeten zijn.", rapport.getHerstellijst().getHerstelActies().size() == 1);
        assertEquals("Het zou een sync naar GBA moeten zijn.", rapport.getHerstellijst().getHerstelActies().get(0)
                .getActie(), HerstelActieEnum.SYNC_NAAR_GBA);
        assertNotNull("Er zou een bijzondere situatie moeten zijn.", rapport.getBijzondereSituaties().get(0));
        assertTrue("Er zou een bijzondere situatie moeten zijn.", rapport
                .getBijzondereSituaties()
                .get(0)
                .getFoutmelding()
                .equals("De BRP persoonslijst zou nieuwer moeten zijn maar dit is niet het geval. Dit is een "
                        + "gekke situatie aangzien de sync naar GBA-V gaat."));
    }

    @Test
    public void controleerPLenVolledigVerschillendePLWrongOpnameDatumBRP() {
        final Opties opties = new Opties();
        opties.setControleNiveau(ControleNiveauEnum.VOLLEDIGE_PL);

        final List<Long> anummers = createAnummerLijst1Pers();
        final InitVullingLog gbavLogRegel = createGbavLogRegel(LO3PL_NEWER_DATE);
        final BerichtLog berichtLog = createBerichtLog(LO3PL);

        Mockito.when(loggingService.findLog(anummers.get(0))).thenReturn(gbavLogRegel);
        Mockito.when(brpDalService.zoekBerichtLogOpAnummer(anummers.get(0))).thenReturn(berichtLog);
        Mockito.when(gemeenteRepo.findGemeente(Matchers.anyInt())).thenReturn(new Gemeente());

        final ControleRapport rapport = new ControleRapport();
        controleService.controleerPLen(anummers, opties, rapport);

        assertNotNull("Mag niet leeg zijn.", rapport);
        assertTrue("Er zou een verschillende PL moeten zijn.", rapport.getAantalVerschillen() == 1);
        assertTrue("Er zou een herstelactie moeten zijn.", rapport.getHerstellijst().getHerstelActies().size() == 1);
        assertEquals("Het zou een sync naar BRP moeten zijn.", rapport.getHerstellijst().getHerstelActies().get(0)
                .getActie(), HerstelActieEnum.SYNC_NAAR_GBA);
        assertNotNull("Er zou een bijzondere situatie moeten zijn.", rapport.getBijzondereSituaties().get(0));
        assertTrue("Er zou een bijzondere situatie moeten zijn.", rapport
                .getBijzondereSituaties()
                .get(0)
                .getFoutmelding()
                .equals("De BRP persoonslijst zou nieuwer moeten zijn maar dit is niet het geval. Dit is een gekke"
                        + " situatie aangzien de sync naar GBA-V gaat."));
    }

    @Test
    public void controleerPLenWrongBrpPl() {
        final Opties opties = new Opties();
        opties.setControleNiveau(ControleNiveauEnum.VOLLEDIGE_PL);

        final List<Long> anummers = createAnummerLijst1Pers();
        final BerichtLog berichtLog =
                new BerichtLog("test_ref", "test_bron", new Timestamp(System.currentTimeMillis()));
        berichtLog.setBerichtData("wrong");

        Mockito.when(brpDalService.zoekBerichtLogOpAnummer(anummers.get(0))).thenReturn(berichtLog);
        Mockito.when(gemeenteRepo.findGemeente(Matchers.anyInt())).thenReturn(new Gemeente());

        final ControleRapport rapport = new ControleRapport();
        controleService.controleerPLen(anummers, opties, rapport);

        // Assert 2 bijzondere situaties
        assertTrue("Er zouden 2 bijzondere situaties moeten zijn.", rapport.getBijzondereSituaties().size() == 2);
        // Assert 1 bijzondere situatie = "Onverwachte fout opgetreden!"
        assertEquals(rapport.getBijzondereSituaties().get(0).getFoutmelding(),
                "Onverwachte fout opgetreden!Berichtlengte moet numeriek zijn, maar is:wrong");
        // Assert 1 bijzondere situatie = "Beide PL-en zijn null! Anummer: " + anummers.get(0);
        assertEquals(rapport.getBijzondereSituaties().get(1).getFoutmelding(), "Beide PL-en zijn null! Anummer: "
                + anummers.get(0));
    }

    @Test
    public void controleerPLenWrongGbavPl() {
        final Opties opties = new Opties();
        opties.setControleNiveau(ControleNiveauEnum.VOLLEDIGE_PL);

        final List<Long> anummers = createAnummerLijst1Pers();
        final InitVullingLog gbavLogRegel = createGbavLogRegel(LO3PL);
        gbavLogRegel.setLo3Bericht("Fout");
        final BerichtLog berichtLog = createBerichtLog(GEWIJZIGDE_LO3_PL);

        Mockito.when(loggingService.findLog(anummers.get(0))).thenReturn(gbavLogRegel);
        Mockito.when(brpDalService.zoekBerichtLogOpAnummer(anummers.get(0))).thenReturn(berichtLog);
        Mockito.when(gemeenteRepo.findGemeente(Matchers.anyInt())).thenReturn(new Gemeente());

        final ControleRapport rapport = new ControleRapport();
        controleService.controleerPLen(anummers, opties, rapport);

        // Assert 1 bijzondere situatie
        assertTrue("Er zou 1 bijzondere situatie moeten zijn.", rapport.getBijzondereSituaties().size() == 1);
        // Assert 1 bijzondere situatie = "Onverwachte fout opgetreden!"
        assertEquals(rapport.getBijzondereSituaties().get(0).getFoutmelding(),
                "Onverwachte fout opgetreden!Berichtinhoud is te kort om een berichtlengte te bevatten.");
    }

    private List<Long> createAnummerLijst1Pers() {
        final List<Long> anummers = new ArrayList<Long>();
        final long anummer = 123456789l;

        anummers.add(new Long(anummer));
        return anummers;
    }

    private BerichtLog createBerichtLog(final String pl) {
        final BerichtLog berichtLog =
                new BerichtLog("test_ref", "test_bron", new Timestamp(System.currentTimeMillis()));
        berichtLog.setBerichtData(pl);
        return berichtLog;
    }

    private InitVullingLog createGbavLogRegel(final String pl) {
        final InitVullingLog gbavLogRegel = new InitVullingLog();
        gbavLogRegel.setLo3Bericht(pl);

        return gbavLogRegel;
    }

}
