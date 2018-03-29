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
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Hf01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Hq01Bericht;
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
public class MaakHf01BerichtActionTest {

    @InjectMocks
    private MaakHf01BerichtAction subject;

    @Mock
    private BerichtenDao berichtenDao;

    private Hq01Bericht hq01Bericht;
    ;

    @Captor
    private ArgumentCaptor<Hf01Bericht> berichtArgument;

    @Test
    public void testExecuteOk() throws BerichtInhoudException {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", 1L);
        parameters.put(AdHocVragenConstanten.HF01_FOUTREDEN, "G");
        hq01Bericht = new Hq01Bericht();
        hq01Bericht.setHeader(Lo3HeaderVeld.GEVRAAGDE_RUBRIEKEN, "011010011020");
        final Lo3CategorieWaarde eerste = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
        eerste.addElement(Lo3ElementEnum.ELEMENT_0110, "1234567890");
        final List<Lo3CategorieWaarde> lijstCategorieen = new ArrayList<>();
        lijstCategorieen.add(eerste);
        hq01Bericht.setCategorieen(lijstCategorieen);

        Mockito.when(berichtenDao.leesBericht(1L)).thenReturn(hq01Bericht);
        Mockito.when(berichtenDao.bewaarBericht(Mockito.any(Hf01Bericht.class))).thenReturn(1L);

        subject.execute(parameters);
        Mockito.verify(berichtenDao).bewaarBericht(berichtArgument.capture());
        Assert.assertEquals(
                "Waarde van identificerende categorieen moet overeenkomen",
                hq01Bericht.getCategorieen(),
                berichtArgument.getValue().getCategorieen());
        Assert.assertEquals(
                "Waarde van gevraagde categorieen moet overeenkomen",
                hq01Bericht.getHeaderWaarde(Lo3HeaderVeld.GEVRAAGDE_RUBRIEKEN),
                berichtArgument.getValue().getHeaderWaarde(Lo3HeaderVeld.GEVRAAGDE_RUBRIEKEN));
        Assert.assertEquals("Waarde foutreden moet overeenkoment", "G", berichtArgument.getValue().getHeaderWaarde(Lo3HeaderVeld.FOUTREDEN));
    }

    @Test(expected = IllegalStateException.class)
    public void testExecuteGeenFoutreden() throws BerichtInhoudException {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", 1L);
        hq01Bericht = new Hq01Bericht();
        hq01Bericht.setHeader(Lo3HeaderVeld.GEVRAAGDE_RUBRIEKEN, "011010011020");
        final Lo3CategorieWaarde eerste = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
        eerste.addElement(Lo3ElementEnum.ELEMENT_0110, "1234567890");
        final List<Lo3CategorieWaarde> lijstCategorieen = new ArrayList<>();
        lijstCategorieen.add(eerste);
        hq01Bericht.setCategorieen(lijstCategorieen);

        Mockito.when(berichtenDao.leesBericht(1L)).thenReturn(hq01Bericht);

        subject.execute(parameters);
    }

    @Test(expected = IllegalStateException.class)
    public void testExecuteLegeFoutreden() throws BerichtInhoudException {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", 1L);
        parameters.put(AdHocVragenConstanten.HF01_FOUTREDEN, "");
        hq01Bericht = new Hq01Bericht();
        hq01Bericht.setHeader(Lo3HeaderVeld.GEVRAAGDE_RUBRIEKEN, "011010011020");
        final Lo3CategorieWaarde eerste = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
        eerste.addElement(Lo3ElementEnum.ELEMENT_0110, "1234567890");
        final List<Lo3CategorieWaarde> lijstCategorieen = new ArrayList<>();
        lijstCategorieen.add(eerste);
        hq01Bericht.setCategorieen(lijstCategorieen);

        Mockito.when(berichtenDao.leesBericht(1L)).thenReturn(hq01Bericht);

        subject.execute(parameters);
    }
}
