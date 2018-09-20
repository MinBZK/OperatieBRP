/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.format;

import java.util.List;
import junit.framework.Assert;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.junit.Test;

public class Lo3CategorieWaardeFormatterTest {

    private static final String FOUTMELDING_1E_WAARDE = "De waarde van het eerste element klopt niet.";
    private static final String FOUTMELDING_2E_WAARDE = "De waarde van het tweede element klopt niet.";
    private static final String ANUMMER = "123456789";
    private static final String BSN = "987654321";

    private final Lo3CategorieWaardeFormatter lo3Formatter = new Lo3CategorieWaardeFormatter();

    @Test
    public void testFormat() {
        lo3Formatter.categorie(null);
        lo3Formatter.element(Lo3ElementEnum.ELEMENT_0110, Lo3Format.format(ANUMMER));
        lo3Formatter.categorie(Lo3CategorieEnum.CATEGORIE_01);
        lo3Formatter.element(Lo3ElementEnum.ELEMENT_0110, null);
        lo3Formatter.element(Lo3ElementEnum.ELEMENT_0110, ANUMMER);
        lo3Formatter.element(Lo3ElementEnum.ELEMENT_0120, BSN);

        final List<Lo3CategorieWaarde> lo3CategorieWaarde = lo3Formatter.getList();
        Assert.assertNotNull("Lijst van elementen zou niet null moeten zijn.", lo3CategorieWaarde);
        Assert.assertEquals("Het verwacht aantal categoriewaarden klopt niet (null en Categorie01).", 2, lo3CategorieWaarde.size());
        Assert.assertEquals(FOUTMELDING_1E_WAARDE, null, lo3CategorieWaarde.get(0).getCategorie());
        Assert.assertEquals(FOUTMELDING_1E_WAARDE, Lo3CategorieEnum.CATEGORIE_01, lo3CategorieWaarde.get(1).getCategorie());
        Assert.assertEquals(FOUTMELDING_1E_WAARDE, ANUMMER, lo3CategorieWaarde.get(0).getElementen().get(Lo3ElementEnum.ELEMENT_0110));
        Assert.assertEquals(FOUTMELDING_1E_WAARDE, ANUMMER, lo3CategorieWaarde.get(1).getElementen().get(Lo3ElementEnum.ELEMENT_0110));
        Assert.assertEquals(FOUTMELDING_2E_WAARDE, BSN, lo3CategorieWaarde.get(1).getElementen().get(Lo3ElementEnum.ELEMENT_0120));
    }

    @Test(expected = IllegalStateException.class)
    public void testFormatException() {
        lo3Formatter.element(null, null);
    }

}
