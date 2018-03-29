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
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Xf01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Xq01Bericht;
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
 * Test voor MaakXf01BerichtAction.
 */
@RunWith(MockitoJUnitRunner.class)
public class MaakXf01BerichtActionTest {

    @InjectMocks
    private MaakXf01BerichtAction subject;

    @Mock
    private BerichtenDao berichtenDao;

    private Xq01Bericht xq01Bericht;

    @Captor
    private ArgumentCaptor<Xf01Bericht> berichtArgument;

    @Test
    public void testExecuteOk() throws BerichtInhoudException {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", 1L);
        parameters.put(AdHocVragenConstanten.XF01_FOUTREDEN, "G");
        xq01Bericht = new Xq01Bericht();
        xq01Bericht.setHeader(Lo3HeaderVeld.GEVRAAGDE_RUBRIEKEN, "010110010120");
        xq01Bericht.setHeader(Lo3HeaderVeld.IDENTIFICATIE, "P");
        final Lo3CategorieWaarde eerste = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
        eerste.addElement(Lo3ElementEnum.ELEMENT_0110, "1234567890");
        final List<Lo3CategorieWaarde> lijstCategorieen = new ArrayList<>();
        lijstCategorieen.add(eerste);
        xq01Bericht.setCategorieen(lijstCategorieen);

        Mockito.when(berichtenDao.leesBericht(1L)).thenReturn(xq01Bericht);
        Mockito.when(berichtenDao.bewaarBericht(Mockito.any(Xf01Bericht.class))).thenReturn(1L);

        subject.execute(parameters);
        Mockito.verify(berichtenDao).bewaarBericht(berichtArgument.capture());
        Assert.assertEquals(
                "Waarde van identificerende categorieen moet overeenkomen",
                xq01Bericht.getCategorieen(),
                berichtArgument.getValue().getCategorieen());
        Assert.assertEquals(
                "Waarde van gevraagde categorieen moet overeenkomen",
                xq01Bericht.getHeaderWaarde(Lo3HeaderVeld.GEVRAAGDE_RUBRIEKEN),
                berichtArgument.getValue().getHeaderWaarde(Lo3HeaderVeld.GEVRAAGDE_RUBRIEKEN));
        Assert.assertEquals("Waarde foutreden moet overeenkoment", "G", berichtArgument.getValue().getHeaderWaarde(Lo3HeaderVeld.FOUTREDEN));
    }

    @Test(expected = IllegalStateException.class)
    public void testExecuteGeenFoutreden() throws BerichtInhoudException {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", 1L);
        xq01Bericht = new Xq01Bericht();
        xq01Bericht.setHeader(Lo3HeaderVeld.GEVRAAGDE_RUBRIEKEN, "010110010120");
        xq01Bericht.setHeader(Lo3HeaderVeld.IDENTIFICATIE, "P");
        final Lo3CategorieWaarde eerste = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
        eerste.addElement(Lo3ElementEnum.ELEMENT_0110, "1234567890");
        final List<Lo3CategorieWaarde> lijstCategorieen = new ArrayList<>();
        lijstCategorieen.add(eerste);
        xq01Bericht.setCategorieen(lijstCategorieen);

        Mockito.when(berichtenDao.leesBericht(1L)).thenReturn(xq01Bericht);

        subject.execute(parameters);
    }

    @Test(expected = IllegalStateException.class)
    public void testExecuteLegeFoutreden() throws BerichtInhoudException {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", 1L);
        parameters.put(AdHocVragenConstanten.HF01_FOUTREDEN, "");
        xq01Bericht = new Xq01Bericht();
        xq01Bericht.setHeader(Lo3HeaderVeld.GEVRAAGDE_RUBRIEKEN, "010110010120");
        xq01Bericht.setHeader(Lo3HeaderVeld.IDENTIFICATIE, "P");
        final Lo3CategorieWaarde eerste = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
        eerste.addElement(Lo3ElementEnum.ELEMENT_0110, "1234567890");
        final List<Lo3CategorieWaarde> lijstCategorieen = new ArrayList<>();
        lijstCategorieen.add(eerste);
        xq01Bericht.setCategorieen(lijstCategorieen);

        Mockito.when(berichtenDao.leesBericht(1L)).thenReturn(xq01Bericht);

        subject.execute(parameters);
    }

}
