/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.junit.Assert;
import org.junit.Test;

public class Wa11BerichtFilterTest {

    private final VulBerichtFilter vulBerichtFilter = new VulBerichtFilter();
    private final Wa11BerichtFilter subject = new Wa11BerichtFilter(vulBerichtFilter);

    @Test
    public void test() {
        final Lo3CategorieWaarde cat01 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
        cat01.addElement(Lo3ElementEnum.ELEMENT_0110, "01.10");
        cat01.addElement(Lo3ElementEnum.ELEMENT_0210, "02.10");
        cat01.addElement(Lo3ElementEnum.ELEMENT_0220, "02.20");
        final List<Lo3CategorieWaarde> categorieen = Arrays.asList(cat01);

        final List<String> lo3Filterrubrieken = new ArrayList<>();
        lo3Filterrubrieken.add("01.04.10");
        lo3Filterrubrieken.add("01.02.20");
        lo3Filterrubrieken.add("01.03.10");
        lo3Filterrubrieken.add("01.01.20");
        lo3Filterrubrieken.add("51.02.20");

        final List<Lo3CategorieWaarde> categorieenGefiltered = subject.filter(null, null, null, null, categorieen, lo3Filterrubrieken);

        Assert.assertEquals(1, categorieenGefiltered.size());
        final Lo3CategorieWaarde catGefilterd = categorieenGefiltered.get(0);
        Assert.assertEquals(Lo3CategorieEnum.CATEGORIE_01, catGefilterd.getCategorie());
        Assert.assertEquals(null, catGefilterd.getElement(Lo3ElementEnum.ELEMENT_0110));
        Assert.assertEquals(null, catGefilterd.getElement(Lo3ElementEnum.ELEMENT_0210));
        Assert.assertEquals("02.20", catGefilterd.getElement(Lo3ElementEnum.ELEMENT_0220));
    }
}
