/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging.gba.ws.model;

import static nl.bzk.brp.delivery.bevraging.gba.ws.Vragen.param;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.brp.delivery.bevraging.gba.ws.Vragen;
import nl.bzk.brp.delivery.bevraging.gba.ws.vraag.VraagPL;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test klasse voor de vraagPL util.
 */
public class VraagPLWebserviceVraagBerichtTest {

    private static final String A_NUMMER = "010110";
    private static final String BSN = "010120";
    private static final String ZOEK_ARGUMENT_A_NUMMER = "1324567890";
    private static final String ZOEK_ARGUMENT_BSN = "987654321";

    @Test
    public void testLeegBericht() {
        VraagPL legeVraagPL = new VraagPL();
        VraagPLWebserviceVraagBericht leegVraagPLWebserviceVraagBericht = new VraagPLWebserviceVraagBericht(legeVraagPL);
        Assert.assertEquals(Collections.emptyList(), leegVraagPLWebserviceVraagBericht.getZoekCriteria());
        Assert.assertEquals(Collections.emptyList(), leegVraagPLWebserviceVraagBericht.getZoekRubrieken());
        Assert.assertEquals(SoortDienst.ZOEK_PERSOON, leegVraagPLWebserviceVraagBericht.getSoortDienst());
    }

    @Test
    public void testGevuldBericht() {
        VraagPL vraagPL = Vragen.vraagPL(Arrays.asList(param(10110, "1234567890"), param(10120, "98765321")));
        VraagPLWebserviceVraagBericht vraagBericht = new VraagPLWebserviceVraagBericht(vraagPL);

        Map<Lo3ElementEnum, String> elementMapCategorie01 = new TreeMap<>();
        elementMapCategorie01.put(Lo3ElementEnum.ELEMENT_0110, ZOEK_ARGUMENT_A_NUMMER);
        elementMapCategorie01.put(Lo3ElementEnum.ELEMENT_0120, ZOEK_ARGUMENT_BSN);
        Lo3CategorieWaarde referentieCategorieWaarde = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0, elementMapCategorie01);
        Assert.assertEquals(Collections.EMPTY_LIST, vraagBericht.getGevraagdeRubrieken());
        Assert.assertEquals(Collections.singletonList(referentieCategorieWaarde), vraagBericht.getZoekCriteria());
        Assert.assertEquals(Arrays.asList(A_NUMMER, BSN), vraagBericht.getZoekRubrieken());
        Assert.assertEquals(SoortDienst.ZOEK_PERSOON, vraagBericht.getSoortDienst());
    }

    @Test
    public void testGeenAnummer() {
        VraagPL vraagPL = Vragen.vraagPL(Collections.singletonList(param(10120, ZOEK_ARGUMENT_BSN)));
        VraagPLWebserviceVraagBericht VraagPLWebserviceVraagBericht = new VraagPLWebserviceVraagBericht(vraagPL);
        Map<Lo3ElementEnum, String> elementMapCategorie01 = new TreeMap<>();
        elementMapCategorie01.put(Lo3ElementEnum.ELEMENT_0120, ZOEK_ARGUMENT_BSN);
        Lo3CategorieWaarde referentieCategorieWaarde = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0, elementMapCategorie01);
        Assert.assertEquals(Collections.singletonList(referentieCategorieWaarde), VraagPLWebserviceVraagBericht.getZoekCriteria());
        Assert.assertEquals(Collections.singletonList(BSN), VraagPLWebserviceVraagBericht.getZoekRubrieken());
        Assert.assertEquals(SoortDienst.ZOEK_PERSOON, VraagPLWebserviceVraagBericht.getSoortDienst());
    }

    @Test
    public void testGeenBsn() {
        VraagPL vraagPL = Vragen.vraagPL(Collections.singletonList(param(10110, ZOEK_ARGUMENT_A_NUMMER)));
        VraagPLWebserviceVraagBericht VraagPLWebserviceVraagBericht = new VraagPLWebserviceVraagBericht(vraagPL);
        Map<Lo3ElementEnum, String> elementMapCategorie01 = new TreeMap<>();
        elementMapCategorie01.put(Lo3ElementEnum.ELEMENT_0120, ZOEK_ARGUMENT_BSN);
        Lo3CategorieWaarde referentieCategorieWaarde = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0, elementMapCategorie01);
        Assert.assertEquals(Collections.singletonList(referentieCategorieWaarde), VraagPLWebserviceVraagBericht.getZoekCriteria());
        Assert.assertEquals(Collections.singletonList(A_NUMMER), VraagPLWebserviceVraagBericht.getZoekRubrieken());
        Assert.assertEquals(SoortDienst.ZOEK_PERSOON, VraagPLWebserviceVraagBericht.getSoortDienst());
    }

    @Test(expected = IllegalArgumentException.class)
    public void persoonsvraagPLOngeldigeRubriek() {
        VraagPL vraagPL = Vragen.vraagPL(Collections.singletonList(param(990110, "1234567890")));
        new VraagPLWebserviceVraagBericht(vraagPL);
    }

    @Test(expected = IllegalArgumentException.class)
    public void persoonsvraagPLTeKorteRubriek() {
        VraagPL vraagPL = Vragen.vraagPL(Collections.singletonList(param(1011, "1234567890")));
        new VraagPLWebserviceVraagBericht(vraagPL);
    }

}
