/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3;

import java.util.List;
import junit.framework.Assert;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import org.junit.Test;

public class Lo3InhoudCategorieTest {

    // private static final String VOORVOEG_NUL = "0";
    private static final String ANUMMER = "123456789";
    private static final String BSN = "9875612312";

    @Test
    public void test() {

        final Lo3InhoudCategorie lo3InhoudCategorie = new Lo3InhoudCategorie(Lo3CategorieEnum.CATEGORIE_01.getCategorie());

        Assert.assertEquals("De categorie komt niet overeen.", Lo3CategorieEnum.CATEGORIE_01.getCategorie(), lo3InhoudCategorie.getCategorie());

        Assert.assertNull("Gevonden element zou null moeten zijn.", lo3InhoudCategorie.getElement(Lo3ElementEnum.ELEMENT_0110.getElementNummer()));

        lo3InhoudCategorie.addElement(null);

        final Lo3InhoudElement lo3InhoudElement0110 = new Lo3InhoudElement(Lo3ElementEnum.ELEMENT_0110, ANUMMER);
        final Lo3InhoudElement lo3InhoudElement0120 = new Lo3InhoudElement(Lo3ElementEnum.ELEMENT_0120, BSN);

        lo3InhoudCategorie.addElement(lo3InhoudElement0110);
        lo3InhoudCategorie.addElement(lo3InhoudElement0120);

        final List<Lo3InhoudElement> lo3InhoudElementenLijst = lo3InhoudCategorie.getElementen();

        Assert.assertEquals("Het aantal elementen klopt niet.", 3, lo3InhoudElementenLijst.size());
        Assert.assertNull("Het eerste element dient null te zijn.", lo3InhoudElementenLijst.get(0));
        Assert.assertEquals(
            "De waarde van het element 0110 klopt niet.",
            Lo3ElementEnum.ELEMENT_0110.getElementNummer(),
            lo3InhoudElementenLijst.get(1).getElement());
        Assert.assertEquals(
            "De waarde van het element 0120 klopt niet.",
            Lo3ElementEnum.ELEMENT_0120.getElementNummer(),
            lo3InhoudElementenLijst.get(2).getElement());
        Assert.assertEquals("De inhoud van het element 0110 klopt niet.", lo3InhoudElement0110.getInhoud(), lo3InhoudElementenLijst.get(1).getInhoud());
        Assert.assertEquals("De inhoud van het element 0120 klopt niet.", lo3InhoudElement0120.getInhoud(), lo3InhoudElementenLijst.get(2).getInhoud());
        Assert.assertEquals(
            "Het element 0110 klopt niet.",
            lo3InhoudElement0110,
            lo3InhoudCategorie.getElement(Lo3ElementEnum.ELEMENT_0110.getElementNummer()));
        Assert.assertEquals(
            "Het element 0120 klopt niet.",
            lo3InhoudElement0120,
            lo3InhoudCategorie.getElement(Lo3ElementEnum.ELEMENT_0120.getElementNummer()));

        final byte[] inhoudAlsByteWeergave = lo3InhoudCategorie.getBytes();
        Assert.assertEquals("Inhoud komt niet overeen.", "010340110010012345678901200109875612312", new String(inhoudAlsByteWeergave));
        final byte[] inhoudAlsByteWeergaveTweedeOpvraging = lo3InhoudCategorie.getBytes();
        Assert.assertEquals("Inhoud komt niet overeen.", "010340110010012345678901200109875612312", new String(inhoudAlsByteWeergaveTweedeOpvraging));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgumentException() {
        new Lo3InhoudCategorie("FOUTIEF");
    }
}
