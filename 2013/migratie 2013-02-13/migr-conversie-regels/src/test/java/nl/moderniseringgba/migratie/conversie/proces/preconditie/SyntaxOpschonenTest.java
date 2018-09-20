/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.preconditie;

import java.util.List;

import javax.inject.Inject;

import junit.framework.Assert;
import nl.moderniseringgba.migratie.adapter.excel.ExcelAdapter;
import nl.moderniseringgba.migratie.adapter.excel.ExcelAdapterImpl;
import nl.moderniseringgba.migratie.adapter.excel.ExcelData;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaardeUtil;
import nl.moderniseringgba.migratie.conversie.proces.AbstractLoggingTest;

import org.junit.Test;

public class SyntaxOpschonenTest extends AbstractLoggingTest {

    private final ExcelAdapter excelAdapter = new ExcelAdapterImpl();

    @Inject
    private Lo3SyntaxControle syntax;

    @Test
    public void testOpschonenGeenFout() throws Exception {
        final List<ExcelData> data =
                excelAdapter.leesExcelBestand(SyntaxOpschonenTest.class
                        .getResourceAsStream("OpschonenTestGeenFouten.xls"));
        Assert.assertEquals(1, data.size());

        final List<Lo3CategorieWaarde> categorieen = data.get(0).getCategorieLijst();
        Assert.assertEquals(5, categorieen.size());

        final List<Lo3CategorieWaarde> check = syntax.controleer(categorieen);
        Assert.assertEquals(5, check.size());

        assertAantalWarnings(0);
    }

    @Test
    public void testOpschonenAfkappen() throws Exception {
        final List<ExcelData> data =
                excelAdapter.leesExcelBestand(SyntaxOpschonenTest.class
                        .getResourceAsStream("OpschonenTestAfkappen.xls"));
        Assert.assertEquals(1, data.size());

        final List<Lo3CategorieWaarde> categorieen = data.get(0).getCategorieLijst();
        Assert.assertEquals(6, categorieen.size());

        // Rue de la poepiedoepiedorriedompie 123332121321
        // 12345678901234567890123456789012345
        final String ongecontroleerd =
                Lo3CategorieWaardeUtil.getElementWaarde(categorieen, Lo3CategorieEnum.CATEGORIE_08, 0, 0,
                        Lo3ElementEnum.ELEMENT_1330);
        Assert.assertEquals("Rue de la poepiedoepiedorriedompie 123332121321", ongecontroleerd);

        final List<Lo3CategorieWaarde> check = syntax.controleer(categorieen);
        Assert.assertEquals(6, check.size());

        assertAantalWarnings(1);

        // Rue de la poepiedoepiedorriedompie
        // 12345678901234567890123456789012345
        final String gecontroleerd =
                Lo3CategorieWaardeUtil.getElementWaarde(check, Lo3CategorieEnum.CATEGORIE_08, 0, 0,
                        Lo3ElementEnum.ELEMENT_1330);
        Assert.assertEquals("Rue de la poepiedoepiedorriedompie ", gecontroleerd);
    }

    @Test
    public void testOpschonenFoutInOnjuist() throws Exception {
        final List<ExcelData> data =
                excelAdapter.leesExcelBestand(SyntaxOpschonenTest.class
                        .getResourceAsStream("OpschonenTestFoutInOnjuist.xls"));
        Assert.assertEquals(1, data.size());

        final List<Lo3CategorieWaarde> categorieen = data.get(0).getCategorieLijst();
        Assert.assertEquals(6, categorieen.size());

        final List<Lo3CategorieWaarde> check = syntax.controleer(categorieen);

        assertAantalWarnings(2); // Lengte warning en verwijder warning
        Assert.assertEquals(5, check.size());
    }

    @Test(expected = OngeldigePersoonslijstException.class)
    public void testOpschonenFoutInJuist() throws Exception {
        final List<ExcelData> data =
                excelAdapter.leesExcelBestand(SyntaxOpschonenTest.class
                        .getResourceAsStream("OpschonenTestFoutInJuist.xls"));
        Assert.assertEquals(1, data.size());

        final List<Lo3CategorieWaarde> categorieen = data.get(0).getCategorieLijst();
        Assert.assertEquals(5, categorieen.size());

        syntax.controleer(categorieen);
    }
}
