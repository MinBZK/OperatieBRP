/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc1004.adres;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Xa01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Xf01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Xq01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AdHocZoekPersonenOpAdresAntwoordBericht;
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
 * Test voor MaakXa01BerichtAction.
 */
@RunWith(MockitoJUnitRunner.class)
public class MaakXa01BerichtActionTest {

    private static final String LO3_HEADER = "00000000Xa01";
    private static final String LO3_EMPTY = "00000000Xa0100000";

    private static final String LO3_BERICHT_INHOUD =
            "00701011650110010817238743501200092995889450210004Mart0240006Jansen03100081990010103200040599033000460300410001M6110001E811"
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
    private MaakXa01BerichtAction subject;
    @Mock
    private BerichtenDao berichtenDao;
    private Xq01Bericht xq01Bericht;
    @Captor
    private ArgumentCaptor<Xa01Bericht> berichtArgument;

    @Test
    public void testExecuteOk() throws BerichtSyntaxException, BerichtInhoudException {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", 1L);
        parameters.put(AdHocVragenConstanten.ZOEK_PERSOON_ADRES_ANTWOORD_BERICHT_SLEUTEL, 2L);
        parameters.put(AdHocVragenConstanten.XF01_FOUTREDEN, "G");
        xq01Bericht = new Xq01Bericht();
        xq01Bericht.setHeader(Lo3HeaderVeld.GEVRAAGDE_RUBRIEKEN, "010110010120");
        xq01Bericht.setHeader(Lo3HeaderVeld.IDENTIFICATIE, "P");
        final Lo3CategorieWaarde eerste = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
        eerste.addElement(Lo3ElementEnum.ELEMENT_0110, "1234567890");
        final List<Lo3CategorieWaarde> lijstCategorieen = new ArrayList<>();
        lijstCategorieen.add(eerste);
        xq01Bericht.setCategorieen(lijstCategorieen);
        final AdHocZoekPersonenOpAdresAntwoordBericht antwoordBericht = new AdHocZoekPersonenOpAdresAntwoordBericht();
        antwoordBericht.setInhoud(LO3_BERICHT);

        Mockito.when(berichtenDao.leesBericht(1L)).thenReturn(xq01Bericht);
        Mockito.when(berichtenDao.leesBericht(2L)).thenReturn(antwoordBericht);
        Mockito.when(berichtenDao.bewaarBericht(Mockito.any(Xf01Bericht.class))).thenReturn(1L);

        subject.execute(parameters);
        Mockito.verify(berichtenDao).bewaarBericht(berichtArgument.capture());
        Assert.assertEquals(
                "Waarde van identificerende categorieen moet overeenkomen",
                Lo3Inhoud.parseInhoud(LO3_BERICHT_INHOUD),
                berichtArgument.getValue().getCategorieen());
    }

    @Test
    public void testExecuteGeenResultaatRubrieken() throws BerichtSyntaxException, BerichtInhoudException {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", 1L);
        parameters.put(AdHocVragenConstanten.ZOEK_PERSOON_ADRES_ANTWOORD_BERICHT_SLEUTEL, 2L);
        parameters.put(AdHocVragenConstanten.XF01_FOUTREDEN, "G");
        xq01Bericht = new Xq01Bericht();
        xq01Bericht.setHeader(Lo3HeaderVeld.GEVRAAGDE_RUBRIEKEN, "010110010120");
        xq01Bericht.setHeader(Lo3HeaderVeld.IDENTIFICATIE, "P");
        final Lo3CategorieWaarde eerste = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
        eerste.addElement(Lo3ElementEnum.ELEMENT_0110, "1234567890");
        final List<Lo3CategorieWaarde> lijstCategorieen = new ArrayList<>();
        lijstCategorieen.add(eerste);
        xq01Bericht.setCategorieen(lijstCategorieen);
        final AdHocZoekPersonenOpAdresAntwoordBericht antwoordBericht = new AdHocZoekPersonenOpAdresAntwoordBericht();
        antwoordBericht.setInhoud(LO3_EMPTY);

        Mockito.when(berichtenDao.leesBericht(1L)).thenReturn(xq01Bericht);
        Mockito.when(berichtenDao.leesBericht(2L)).thenReturn(antwoordBericht);
        Mockito.when(berichtenDao.bewaarBericht(Mockito.any(Xa01Bericht.class))).thenReturn(1L);

        subject.execute(parameters);
        Mockito.verify(berichtenDao).bewaarBericht(berichtArgument.capture());
        Assert.assertTrue("Waarde van identificerende categorieen moet overeenkomen", berichtArgument.getValue().getCategorieen().isEmpty());
    }

    @Test
    public void testExecuteLegeResultaatRubrieken() throws BerichtSyntaxException, BerichtInhoudException {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", 1L);
        parameters.put(AdHocVragenConstanten.ZOEK_PERSOON_ADRES_ANTWOORD_BERICHT_SLEUTEL, 2L);
        parameters.put(AdHocVragenConstanten.XF01_FOUTREDEN, "G");
        xq01Bericht = new Xq01Bericht();
        xq01Bericht.setHeader(Lo3HeaderVeld.GEVRAAGDE_RUBRIEKEN, "010110010120");
        xq01Bericht.setHeader(Lo3HeaderVeld.IDENTIFICATIE, "P");
        final Lo3CategorieWaarde eerste = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
        eerste.addElement(Lo3ElementEnum.ELEMENT_0110, "1234567890");
        final List<Lo3CategorieWaarde> lijstCategorieen = new ArrayList<>();
        lijstCategorieen.add(eerste);
        xq01Bericht.setCategorieen(lijstCategorieen);
        final AdHocZoekPersonenOpAdresAntwoordBericht antwoordBericht = new AdHocZoekPersonenOpAdresAntwoordBericht();
        antwoordBericht.setInhoud(LO3_EMPTY);

        Mockito.when(berichtenDao.leesBericht(1L)).thenReturn(xq01Bericht);
        Mockito.when(berichtenDao.leesBericht(2L)).thenReturn(antwoordBericht);
        Mockito.when(berichtenDao.bewaarBericht(Mockito.any(Xa01Bericht.class))).thenReturn(1L);

        subject.execute(parameters);
        Mockito.verify(berichtenDao).bewaarBericht(berichtArgument.capture());
        Assert.assertTrue("Waarde van identificerende categorieen moet overeenkomen", berichtArgument.getValue().getCategorieen().isEmpty());
    }

    @Test(expected = IllegalStateException.class)
    public void testExecuteGeenSyntaxExceptionParsenRubrieken() throws BerichtSyntaxException, BerichtInhoudException {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", 1L);
        parameters.put(AdHocVragenConstanten.ZOEK_PERSOON_ADRES_ANTWOORD_BERICHT_SLEUTEL, 2L);
        parameters.put(AdHocVragenConstanten.XF01_FOUTREDEN, "G");
        xq01Bericht = new Xq01Bericht();
        xq01Bericht.setHeader(Lo3HeaderVeld.GEVRAAGDE_RUBRIEKEN, "010110010120");
        xq01Bericht.setHeader(Lo3HeaderVeld.IDENTIFICATIE, "P");
        final Lo3CategorieWaarde eerste = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
        eerste.addElement(Lo3ElementEnum.ELEMENT_0110, "1234567890");
        final List<Lo3CategorieWaarde> lijstCategorieen = new ArrayList<>();
        lijstCategorieen.add(eerste);
        xq01Bericht.setCategorieen(lijstCategorieen);
        final AdHocZoekPersonenOpAdresAntwoordBericht antwoordBericht = new AdHocZoekPersonenOpAdresAntwoordBericht();
        antwoordBericht.setInhoud("00000000Xa0100011010011020");

        Mockito.when(berichtenDao.leesBericht(1L)).thenReturn(xq01Bericht);
        Mockito.when(berichtenDao.leesBericht(2L)).thenReturn(antwoordBericht);
        Mockito.when(berichtenDao.bewaarBericht(Mockito.any(Xa01Bericht.class))).thenReturn(1L);

        subject.execute(parameters);
    }

    @Test
    public void testExecuteGeenInhoudZoekPersoonAntwoord() throws BerichtSyntaxException, BerichtInhoudException {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", 1L);
        parameters.put(AdHocVragenConstanten.ZOEK_PERSOON_ADRES_ANTWOORD_BERICHT_SLEUTEL, 2L);
        parameters.put(AdHocVragenConstanten.XF01_FOUTREDEN, "G");
        xq01Bericht = new Xq01Bericht();
        xq01Bericht.setHeader(Lo3HeaderVeld.GEVRAAGDE_RUBRIEKEN, "010110010120");
        xq01Bericht.setHeader(Lo3HeaderVeld.IDENTIFICATIE, "P");
        final Lo3CategorieWaarde eerste = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
        eerste.addElement(Lo3ElementEnum.ELEMENT_0110, "1234567890");
        final List<Lo3CategorieWaarde> lijstCategorieen = new ArrayList<>();
        lijstCategorieen.add(eerste);
        xq01Bericht.setCategorieen(lijstCategorieen);
        final AdHocZoekPersonenOpAdresAntwoordBericht antwoordBericht = new AdHocZoekPersonenOpAdresAntwoordBericht();
        antwoordBericht.setInhoud(null);

        Mockito.when(berichtenDao.leesBericht(1L)).thenReturn(xq01Bericht);
        Mockito.when(berichtenDao.leesBericht(2L)).thenReturn(antwoordBericht);
        Mockito.when(berichtenDao.bewaarBericht(Mockito.any(Xa01Bericht.class))).thenReturn(1L);

        subject.execute(parameters);
    }
}
