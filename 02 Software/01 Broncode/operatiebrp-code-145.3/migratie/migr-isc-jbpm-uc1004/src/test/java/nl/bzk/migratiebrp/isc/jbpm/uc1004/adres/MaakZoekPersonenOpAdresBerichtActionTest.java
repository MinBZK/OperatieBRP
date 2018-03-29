/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc1004.adres;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Xq01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AdHocZoekPersonenOpAdresVerzoekBericht;
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
 * Test voor MaakzoekPersonenOpAdresBerichtAction.
 */
@RunWith(MockitoJUnitRunner.class)
public class MaakZoekPersonenOpAdresBerichtActionTest {

    @InjectMocks
    private MaakZoekPersonenOpAdresBerichtAction subject;

    @Mock
    private BerichtenDao berichtenDao;

    @Captor
    private ArgumentCaptor<AdHocZoekPersonenOpAdresVerzoekBericht> berichtArgument;

    @Test
    public void testExecuteOk() throws BerichtInhoudException {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", 1L);
        final Xq01Bericht xq01Bericht = new Xq01Bericht();
        xq01Bericht.setBronPartijCode("1601");
        xq01Bericht.setHeader(Lo3HeaderVeld.GEVRAAGDE_RUBRIEKEN, "010110010120");
        xq01Bericht.setHeader(Lo3HeaderVeld.ADRESFUNCTIE, "A");
        xq01Bericht.setHeader(Lo3HeaderVeld.IDENTIFICATIE, "P");
        final Lo3CategorieWaarde eerste = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
        eerste.addElement(Lo3ElementEnum.ELEMENT_0110, "1234567890");
        final List<Lo3CategorieWaarde> lijstCategorieen = new ArrayList<>();
        lijstCategorieen.add(eerste);
        xq01Bericht.setCategorieen(lijstCategorieen);

        Mockito.when(berichtenDao.leesBericht(1L)).thenReturn(xq01Bericht);
        Mockito.when(berichtenDao.bewaarBericht(Mockito.any(AdHocZoekPersonenOpAdresVerzoekBericht.class))).thenReturn(1L);

        final Map<String, Object> resultaat = subject.execute(parameters);
        Assert.assertTrue("Sleutel zoekPersonenOpAdresVerzoek moet voorkomen", resultaat.containsKey("zoekPersonenOpAdresVerzoek"));
        Assert.assertEquals("Waarde moet overeenkomen", Long.valueOf("1"), resultaat.get("zoekPersonenOpAdresVerzoek"));
        Mockito.verify(berichtenDao).bewaarBericht(berichtArgument.capture());

        assertThat("Waarde van gevraagde categorieen moet overeenkomen", berichtArgument.getValue().getGevraagdeRubrieken(), contains("010110", "010120"));
        Assert.assertEquals("Waarde partij moet overeenkoment", "1601", berichtArgument.getValue().getPartijCode());
        Assert.assertEquals(
                "Waarde van identificerende categorieen moet overeenkomen",
                "000220101701100101234567890",
                berichtArgument.getValue().getIdentificerendeGegevens());
    }

}
