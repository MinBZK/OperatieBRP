/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.format;

import java.util.Arrays;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test voor {@link AbstractAgFormatter}
 */
@RunWith(MockitoJUnitRunner.class)
public class AgFormatterTest {

    private static final String ELEMENT_0110_WAARDE = "3450924321";
    private static final String ELEMENT_0310_WAARDE = "19770101";
    private static final String ELEMENT_0910_WAARDE = "0518";

    @InjectMocks
    private Ag31Formatter subject;

    @Test
    public void test() {
        final Lo3CategorieWaarde categorie01 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
        categorie01.addElement(Lo3ElementEnum.ELEMENT_0110, ELEMENT_0110_WAARDE);
        categorie01.addElement(Lo3ElementEnum.ELEMENT_0310, ELEMENT_0310_WAARDE);
        final Lo3CategorieWaarde categorie08 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_08, 0, 0);
        categorie08.addElement(Lo3ElementEnum.ELEMENT_0910, ELEMENT_0910_WAARDE);

        final List<Lo3CategorieWaarde> categorieen = Arrays.asList(categorie01, categorie08);
        final String resultaat = subject.maakPlatteTekst(null, categorieen, categorieen);

        final String verwachteResultaat = "00000000Ag31A000000000005301032011001034509243210310008197701010801109100040518";
        Assert.assertEquals(verwachteResultaat, resultaat);
    }

    @Test
    public void testHeader() {
        final Lo3CategorieWaarde categorie01 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
        categorie01.addElement(Lo3ElementEnum.ELEMENT_0110, ELEMENT_0110_WAARDE);
        categorie01.addElement(Lo3ElementEnum.ELEMENT_0310, ELEMENT_0310_WAARDE);
        final Lo3CategorieWaarde categorie07 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_07, 0, 0);
        categorie07.addElement(Lo3ElementEnum.ELEMENT_6710, "20140101");
        categorie07.addElement(Lo3ElementEnum.ELEMENT_6720, "O");
        final Lo3CategorieWaarde categorie08 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_08, 0, 0);
        categorie08.addElement(Lo3ElementEnum.ELEMENT_0910, ELEMENT_0910_WAARDE);

        final List<Lo3CategorieWaarde> categorieen = Arrays.asList(categorie01, categorie07, categorie08);
        final List<Lo3CategorieWaarde> categorieenGefilterd = Arrays.asList(categorie01, categorie08);
        final String resultaat = subject.maakPlatteTekst(null, categorieen, categorieenGefilterd);

        final String verwachteResultaat = "00000000Ag31O201401010005301032011001034509243210310008197701010801109100040518";
        Assert.assertEquals(verwachteResultaat, resultaat);
    }

}
