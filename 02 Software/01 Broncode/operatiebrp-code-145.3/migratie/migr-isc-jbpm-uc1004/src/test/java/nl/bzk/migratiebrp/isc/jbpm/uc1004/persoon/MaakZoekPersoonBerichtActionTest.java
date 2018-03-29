/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc1004.persoon;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Hq01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AdHocZoekPersoonVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
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
public class MaakZoekPersoonBerichtActionTest {

    @InjectMocks
    private MaakZoekPersoonBerichtAction subject;

    @Mock
    private BerichtenDao berichtenDao;

    @Captor
    private ArgumentCaptor<AdHocZoekPersoonVerzoekBericht> berichtArgument;

    @Test
    public void testExecuteOk() throws BerichtInhoudException {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", 1L);
        final Hq01Bericht hq01Bericht = new Hq01Bericht();
        hq01Bericht.setBronPartijCode("1601");
        hq01Bericht.setHeader(Lo3HeaderVeld.GEVRAAGDE_RUBRIEKEN, "011010011020");
        final Lo3CategorieWaarde eerste = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
        eerste.addElement(Lo3ElementEnum.ELEMENT_0110, "1234567890");
        final List<Lo3CategorieWaarde> lijstCategorieen = new ArrayList<>();
        lijstCategorieen.add(eerste);
        hq01Bericht.setCategorieen(lijstCategorieen);

        Mockito.when(berichtenDao.leesBericht(1L)).thenReturn(hq01Bericht);
        Mockito.when(berichtenDao.bewaarBericht(Mockito.any(AdHocZoekPersoonVerzoekBericht.class))).thenReturn(1L);

        final Map<String, Object> resultaat = subject.execute(parameters);
        Assert.assertTrue("Sleutel zoekPersoonVerzoek moet voorkomen", resultaat.containsKey("zoekPersoonVerzoek"));
        Assert.assertEquals("Waarde moet overeenkomen", Long.valueOf("1"), resultaat.get("zoekPersoonVerzoek"));
        Mockito.verify(berichtenDao).bewaarBericht(berichtArgument.capture());

        assertThat("Waarde van gevraagde categorieen moet overeenkomen", berichtArgument.getValue().getGevraagdeRubrieken(), contains("011010", "011020"));
        Assert.assertEquals("Waarde partij moet overeenkoment", "1601", berichtArgument.getValue().getPartijCode());
        Assert.assertEquals(
                "Waarde van identificerende categorieen moet overeenkomen",
                "000220101701100101234567890",
                berichtArgument.getValue().getPersoonIdentificerendeGegevens());
    }

}
