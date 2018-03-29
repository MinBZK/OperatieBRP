/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc1004.persoon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Ha01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Hf01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Hq01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AdHocZoekPersoonAntwoordBericht;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.uc1004.AdHocVragenConstanten;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test voor MaakZoekPersoonBerichtAction.
 */
@RunWith(MockitoJUnitRunner.class)
public class MaakHa01BerichtActionTest {

    private static final String LO3_HEADER = "00000000Ha01A00000000";
    private static final String LO3_EMPTY = "00000000Ha01A0000000000000";

    private static final String LO3_BERICHT_INHOUD =
            "00695011590110010817238743501200092995889450210004Mart024000003100081990010103200040599033000460300410001M6110001E811"
                    + "0004059981200071 A91028510008199001018610008199001020217201100101928293895012000999"
                    + "11223340210006Jannie0240004Smit031000819690101032"
                    + "00041901033000460300410001M6210008199001018110004059981200071 A91028510008199001018"
                    + "61000819900102031750110010172625463201200093827261340210008Mitchell0240005Vries0310"
                    + "0081970010103200041900033000460300410001M6210008199001018110004059981200071 A910285"
                    + "10008199001018610008199001020705568100081990010170100010801000400018020017000000000000"
                    + "0000008106091000405990920008199001011010001W102000405991030008199001011110001.72100"
                    + "01G851000819900101861000819900102";

    private static final String LO3_BERICHT = LO3_HEADER + LO3_BERICHT_INHOUD;

    @InjectMocks
    private MaakHa01BerichtAction subject;
    @Mock
    private BerichtenDao berichtenDao;
    private Hq01Bericht hq01Bericht;
    @Captor
    private ArgumentCaptor<Ha01Bericht> berichtArgument;

    @Test
    public void testExecuteOk() throws BerichtSyntaxException, BerichtInhoudException {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", 1L);
        parameters.put(AdHocVragenConstanten.ZOEK_PERSOON_ANTWOORD_BERICHT_SLEUTEL, 2L);
        parameters.put(AdHocVragenConstanten.HF01_FOUTREDEN, "G");
        hq01Bericht = new Hq01Bericht();
        hq01Bericht.setHeader(Lo3HeaderVeld.GEVRAAGDE_RUBRIEKEN, "011010011020");
        final Lo3CategorieWaarde eerste = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
        eerste.addElement(Lo3ElementEnum.ELEMENT_0110, "1234567890");
        final List<Lo3CategorieWaarde> lijstCategorieen = new ArrayList<>();
        lijstCategorieen.add(eerste);
        hq01Bericht.setCategorieen(lijstCategorieen);
        final AdHocZoekPersoonAntwoordBericht antwoordBericht = new AdHocZoekPersoonAntwoordBericht();
        antwoordBericht.setInhoud(LO3_BERICHT);

        Mockito.when(berichtenDao.leesBericht(1L)).thenReturn(hq01Bericht);
        Mockito.when(berichtenDao.leesBericht(2L)).thenReturn(antwoordBericht);
        Mockito.when(berichtenDao.bewaarBericht(Mockito.any(Hf01Bericht.class))).thenReturn(1L);

        subject.execute(parameters);
        Mockito.verify(berichtenDao).bewaarBericht(berichtArgument.capture());
        Assert.assertEquals(
                "Waarde van identificerende categorieen moet overeenkomen",
                Lo3Inhoud.parseInhoud(LO3_BERICHT_INHOUD),
                berichtArgument.getValue().getCategorieen());
        Assert.assertEquals("Waarde status moet overeenkoment", "A", berichtArgument.getValue().getHeaderWaarde(Lo3HeaderVeld.STATUS));
        Assert.assertEquals("Datum dient niet gevuld te zijn", "00000000", berichtArgument.getValue().getHeaderWaarde(Lo3HeaderVeld.DATUM));
    }

    @Test
    public void testExecuteGeenResultaatRubrieken() throws BerichtSyntaxException, BerichtInhoudException {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", 1L);
        parameters.put(AdHocVragenConstanten.ZOEK_PERSOON_ANTWOORD_BERICHT_SLEUTEL, 2L);
        parameters.put(AdHocVragenConstanten.HF01_FOUTREDEN, "G");
        hq01Bericht = new Hq01Bericht();
        hq01Bericht.setHeader(Lo3HeaderVeld.GEVRAAGDE_RUBRIEKEN, "011010011020");
        final Lo3CategorieWaarde eerste = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
        eerste.addElement(Lo3ElementEnum.ELEMENT_0110, "1234567890");
        final List<Lo3CategorieWaarde> lijstCategorieen = new ArrayList<>();
        lijstCategorieen.add(eerste);
        hq01Bericht.setCategorieen(lijstCategorieen);
        final AdHocZoekPersoonAntwoordBericht antwoordBericht = new AdHocZoekPersoonAntwoordBericht();
        antwoordBericht.setInhoud(LO3_EMPTY);

        Mockito.when(berichtenDao.leesBericht(1L)).thenReturn(hq01Bericht);
        Mockito.when(berichtenDao.leesBericht(2L)).thenReturn(antwoordBericht);
        Mockito.when(berichtenDao.bewaarBericht(Mockito.any(Ha01Bericht.class))).thenReturn(1L);

        subject.execute(parameters);
        Mockito.verify(berichtenDao).bewaarBericht(berichtArgument.capture());
        Assert.assertTrue("Waarde van identificerende categorieen moet overeenkomen", berichtArgument.getValue().getCategorieen().isEmpty());
        Assert.assertEquals("Datum dient niet gevuld te zijn", "00000000", berichtArgument.getValue().getHeaderWaarde(Lo3HeaderVeld.DATUM));
        Assert.assertEquals("Waarde status moet overeenkoment", "A", berichtArgument.getValue().getHeaderWaarde(Lo3HeaderVeld.STATUS));
    }

    @Test
    public void testExecuteLegeResultaatRubrieken() throws BerichtSyntaxException, BerichtInhoudException {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", 1L);
        parameters.put(AdHocVragenConstanten.ZOEK_PERSOON_ANTWOORD_BERICHT_SLEUTEL, 2L);
        parameters.put(AdHocVragenConstanten.HF01_FOUTREDEN, "G");
        hq01Bericht = new Hq01Bericht();
        hq01Bericht.setHeader(Lo3HeaderVeld.GEVRAAGDE_RUBRIEKEN, "011010011020");
        final Lo3CategorieWaarde eerste = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
        eerste.addElement(Lo3ElementEnum.ELEMENT_0110, "1234567890");
        final List<Lo3CategorieWaarde> lijstCategorieen = new ArrayList<>();
        lijstCategorieen.add(eerste);
        hq01Bericht.setCategorieen(lijstCategorieen);
        final AdHocZoekPersoonAntwoordBericht antwoordBericht = new AdHocZoekPersoonAntwoordBericht();
        antwoordBericht.setInhoud("00000000Ha01E2016010100000");

        Mockito.when(berichtenDao.leesBericht(1L)).thenReturn(hq01Bericht);
        Mockito.when(berichtenDao.leesBericht(2L)).thenReturn(antwoordBericht);
        Mockito.when(berichtenDao.bewaarBericht(Mockito.any(Ha01Bericht.class))).thenReturn(1L);

        subject.execute(parameters);
        Mockito.verify(berichtenDao).bewaarBericht(berichtArgument.capture());
        Assert.assertTrue("Waarde van identificerende categorieen moet overeenkomen", berichtArgument.getValue().getCategorieen().isEmpty());
        Assert.assertEquals("Datum dient gevuld te zijn", "20160101", berichtArgument.getValue().getHeaderWaarde(Lo3HeaderVeld.DATUM));
        Assert.assertEquals("Waarde status moet overeenkoment", "E", berichtArgument.getValue().getHeaderWaarde(Lo3HeaderVeld.STATUS));
        Assert.assertEquals("Waarde status moet overeenkoment", "20160101", berichtArgument.getValue().getHeaderWaarde(Lo3HeaderVeld.DATUM));
    }

    @Test(expected = IllegalStateException.class)
    public void testExecuteGeenSyntaxExceptionParsenRubrieken() throws BerichtSyntaxException, BerichtInhoudException {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", 1L);
        parameters.put(AdHocVragenConstanten.ZOEK_PERSOON_ANTWOORD_BERICHT_SLEUTEL, 2L);
        parameters.put(AdHocVragenConstanten.HF01_FOUTREDEN, "G");
        hq01Bericht = new Hq01Bericht();
        hq01Bericht.setHeader(Lo3HeaderVeld.GEVRAAGDE_RUBRIEKEN, "011010011020");
        final Lo3CategorieWaarde eerste = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
        eerste.addElement(Lo3ElementEnum.ELEMENT_0110, "1234567890");
        final List<Lo3CategorieWaarde> lijstCategorieen = new ArrayList<>();
        lijstCategorieen.add(eerste);
        hq01Bericht.setCategorieen(lijstCategorieen);
        final AdHocZoekPersoonAntwoordBericht antwoordBericht = new AdHocZoekPersoonAntwoordBericht();
        antwoordBericht.setInhoud("00000000Ha01A00000000011010011020");

        Mockito.when(berichtenDao.leesBericht(1L)).thenReturn(hq01Bericht);
        Mockito.when(berichtenDao.leesBericht(2L)).thenReturn(antwoordBericht);
        Mockito.when(berichtenDao.bewaarBericht(Mockito.any(Ha01Bericht.class))).thenReturn(1L);

        subject.execute(parameters);
    }

    @Test
    public void testExecuteGeenInhoudZoekPersoonAntwoord() throws BerichtSyntaxException, BerichtInhoudException {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", 1L);
        parameters.put(AdHocVragenConstanten.ZOEK_PERSOON_ANTWOORD_BERICHT_SLEUTEL, 2L);
        parameters.put(AdHocVragenConstanten.HF01_FOUTREDEN, "G");
        hq01Bericht = new Hq01Bericht();
        hq01Bericht.setHeader(Lo3HeaderVeld.GEVRAAGDE_RUBRIEKEN, "011010011020");
        final Lo3CategorieWaarde eerste = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
        eerste.addElement(Lo3ElementEnum.ELEMENT_0110, "1234567890");
        final List<Lo3CategorieWaarde> lijstCategorieen = new ArrayList<>();
        lijstCategorieen.add(eerste);
        hq01Bericht.setCategorieen(lijstCategorieen);
        final AdHocZoekPersoonAntwoordBericht antwoordBericht = new AdHocZoekPersoonAntwoordBericht();
        antwoordBericht.setInhoud(null);

        Mockito.when(berichtenDao.leesBericht(1L)).thenReturn(hq01Bericht);
        Mockito.when(berichtenDao.leesBericht(2L)).thenReturn(antwoordBericht);
        Mockito.when(berichtenDao.bewaarBericht(Mockito.any(Ha01Bericht.class))).thenReturn(1L);

        subject.execute(parameters);
    }
}
