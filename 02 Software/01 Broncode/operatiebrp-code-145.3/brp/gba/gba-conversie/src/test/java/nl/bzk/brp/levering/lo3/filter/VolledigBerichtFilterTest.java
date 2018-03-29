/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.filter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.junit.Assert;
import org.junit.Test;

public class VolledigBerichtFilterTest {

    private static final String WAARDE_010220 = "waarde010220";
    private final VulBerichtFilter subject = new VulBerichtFilter();

    @Test
    public void test() {
        final Lo3CategorieWaarde cat01 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
        cat01.addElement(Lo3ElementEnum.ELEMENT_0210, "waarde010210");
        cat01.addElement(Lo3ElementEnum.ELEMENT_0220, WAARDE_010220);

        final Lo3CategorieWaarde cat02 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_02, 0, 0);
        cat01.addElement(Lo3ElementEnum.ELEMENT_0210, "waarde020210");

        final List<Lo3CategorieWaarde> categorieen = Arrays.asList(cat01, cat02);

        final List<String> rubrieken = Arrays.asList("01.02.20", "01.02.30", "03.02.10");

        final List<Lo3CategorieWaarde> gefilterd = subject.filter(null, null, null, null, categorieen, rubrieken);
        Assert.assertEquals(1, gefilterd.size());
        final Lo3CategorieWaarde filt01 = gefilterd.get(0);
        Assert.assertEquals(1, filt01.getElementen().size());
        Assert.assertEquals(WAARDE_010220, filt01.getElement(Lo3ElementEnum.ELEMENT_0220));
    }

    @Test
    public void testKindMetHuwelijk() {
        final Lo3CategorieWaarde cat01 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
        cat01.addElement(Lo3ElementEnum.ELEMENT_0210, "waarde010210");
        cat01.addElement(Lo3ElementEnum.ELEMENT_0220, WAARDE_010220);

        final Lo3CategorieWaarde cat02 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_02, 0, 0);
        cat01.addElement(Lo3ElementEnum.ELEMENT_0210, "waarde020210");

        final Lo3CategorieWaarde cat09 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_09, 0, 0);
        cat09.addElement(Lo3ElementEnum.ELEMENT_0210, "waarde090210");
        cat09.addElement(Lo3ElementEnum.ELEMENT_0230, "waarde090230");
        cat09.addElement(Lo3ElementEnum.ELEMENT_0310, "waarde090310");
        cat09.addElement(Lo3ElementEnum.ELEMENT_0320, "waarde090320");
        cat09.addElement(Lo3ElementEnum.ELEMENT_0330, "waarde090330");

        final List<Lo3CategorieWaarde> categorieen = Arrays.asList(cat01, cat02, cat09);

        final List<String> rubrieken = Arrays.asList("01.02.20", "01.02.30", "03.02.10", "09.02.10", "09.02.30", "09.03.10", "09.03.20", "09.03.30");

        final List<Lo3CategorieWaarde> gefilterd = subject.filter(null, null, null, null, categorieen, rubrieken);
        Assert.assertEquals(2, gefilterd.size());
        final Lo3CategorieWaarde filt01 = gefilterd.get(0);
        Assert.assertEquals(1, filt01.getElementen().size());
        Assert.assertEquals(WAARDE_010220, filt01.getElement(Lo3ElementEnum.ELEMENT_0220));
        final Lo3CategorieWaarde filt02 = gefilterd.get(1);
        Assert.assertEquals(5, filt02.getElementen().size());
    }

    @Test
    public void testEmpty() {
        Assert.assertEquals(0, subject.filter(null, null, null, null, null, null).size());
        Assert.assertEquals(0, subject.filter(null, null, null, null, null, Collections.<String>emptyList()).size());
    }

}
