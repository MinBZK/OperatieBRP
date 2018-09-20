/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.util.excel;

import static org.junit.Assert.assertNotNull;

import java.io.InputStream;
import java.util.List;
import junit.framework.Assert;
import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.junit.Test;

public class HerkomstTest {

    private final ExcelAdapter excelAdapter = new ExcelAdapterImpl();

    @Test
    public void test() throws ExcelAdapterException, Lo3SyntaxException {
        final InputStream excelBestand = ExcelAdapter.class.getResourceAsStream("/exceladapter/TestHerkomst.xls");
        assertNotNull(excelBestand);

        // Lees excel
        final List<ExcelData> excelDatas = excelAdapter.leesExcelBestand(excelBestand);
        Assert.assertEquals(1, excelDatas.size());

        final List<Lo3CategorieWaarde> categorieen = excelDatas.get(0).getCategorieLijst();
        Assert.assertEquals(15, categorieen.size());

        final Lo3CategorieWaarde cat04_0_0 = categorieen.get(3);
        Assert.assertEquals(Lo3CategorieEnum.CATEGORIE_04, cat04_0_0.getCategorie());
        Assert.assertEquals(0, cat04_0_0.getStapel());
        Assert.assertEquals(0, cat04_0_0.getVoorkomen());

        final Lo3CategorieWaarde cat54_0_1 = categorieen.get(4);
        Assert.assertEquals(Lo3CategorieEnum.CATEGORIE_54, cat54_0_1.getCategorie());
        Assert.assertEquals(0, cat54_0_1.getStapel());
        Assert.assertEquals(1, cat54_0_1.getVoorkomen());

        final Lo3CategorieWaarde cat54_0_2 = categorieen.get(5);
        Assert.assertEquals(Lo3CategorieEnum.CATEGORIE_54, cat54_0_2.getCategorie());
        Assert.assertEquals(0, cat54_0_2.getStapel());
        Assert.assertEquals(2, cat54_0_2.getVoorkomen());

        final Lo3CategorieWaarde cat54_0_3 = categorieen.get(6);
        Assert.assertEquals(Lo3CategorieEnum.CATEGORIE_54, cat54_0_3.getCategorie());
        Assert.assertEquals(0, cat54_0_3.getStapel());
        Assert.assertEquals(3, cat54_0_3.getVoorkomen());

        final Lo3CategorieWaarde cat04_1_0 = categorieen.get(7);
        Assert.assertEquals(Lo3CategorieEnum.CATEGORIE_04, cat04_1_0.getCategorie());
        Assert.assertEquals(1, cat04_1_0.getStapel());
        Assert.assertEquals(0, cat04_1_0.getVoorkomen());

        final Lo3CategorieWaarde cat54_1_1 = categorieen.get(8);
        Assert.assertEquals(Lo3CategorieEnum.CATEGORIE_54, cat54_1_1.getCategorie());
        Assert.assertEquals(1, cat54_1_1.getStapel());
        Assert.assertEquals(1, cat54_1_1.getVoorkomen());

        final Lo3CategorieWaarde cat54_1_2 = categorieen.get(9);
        Assert.assertEquals(Lo3CategorieEnum.CATEGORIE_54, cat54_1_2.getCategorie());
        Assert.assertEquals(1, cat54_1_2.getStapel());
        Assert.assertEquals(2, cat54_1_2.getVoorkomen());

        final Lo3CategorieWaarde cat04_2_0 = categorieen.get(10);
        Assert.assertEquals(Lo3CategorieEnum.CATEGORIE_04, cat04_2_0.getCategorie());
        Assert.assertEquals(2, cat04_2_0.getStapel());
        Assert.assertEquals(0, cat04_2_0.getVoorkomen());

        final Lo3CategorieWaarde cat54_2_1 = categorieen.get(11);
        Assert.assertEquals(Lo3CategorieEnum.CATEGORIE_54, cat54_2_1.getCategorie());
        Assert.assertEquals(2, cat54_2_1.getStapel());
        Assert.assertEquals(1, cat54_2_1.getVoorkomen());

        final Lo3CategorieWaarde cat54_2_2 = categorieen.get(12);
        Assert.assertEquals(Lo3CategorieEnum.CATEGORIE_54, cat54_2_2.getCategorie());
        Assert.assertEquals(2, cat54_2_2.getStapel());
        Assert.assertEquals(2, cat54_2_2.getVoorkomen());

    }

    @Test
    public void testLegeBlokken() throws ExcelAdapterException, Lo3SyntaxException {
        final InputStream excelBestand = ExcelAdapter.class.getResourceAsStream("/exceladapter/TestHerkomstMetLegeBlokken.xls");
        assertNotNull(excelBestand);

        // Lees excel
        final List<ExcelData> excelDatas = excelAdapter.leesExcelBestand(excelBestand);
        Assert.assertEquals(1, excelDatas.size());

        final List<Lo3CategorieWaarde> categorieen = excelDatas.get(0).getCategorieLijst();
        Assert.assertEquals(14, categorieen.size());

        final Lo3CategorieWaarde cat01_0_0 = categorieen.get(0);
        Assert.assertEquals(Lo3CategorieEnum.CATEGORIE_01, cat01_0_0.getCategorie());
        Assert.assertEquals(0, cat01_0_0.getStapel());
        Assert.assertEquals(0, cat01_0_0.getVoorkomen());

        final Lo3CategorieWaarde cat02_0_0 = categorieen.get(1);
        Assert.assertEquals(Lo3CategorieEnum.CATEGORIE_02, cat02_0_0.getCategorie());
        Assert.assertEquals(0, cat02_0_0.getStapel());
        Assert.assertEquals(0, cat02_0_0.getVoorkomen());

        final Lo3CategorieWaarde cat03_0_0 = categorieen.get(2);
        Assert.assertEquals(Lo3CategorieEnum.CATEGORIE_03, cat03_0_0.getCategorie());
        Assert.assertEquals(0, cat03_0_0.getStapel());
        Assert.assertEquals(0, cat03_0_0.getVoorkomen());

        final Lo3CategorieWaarde cat04_0_0 = categorieen.get(3);
        Assert.assertEquals(Lo3CategorieEnum.CATEGORIE_04, cat04_0_0.getCategorie());
        Assert.assertEquals(0, cat04_0_0.getStapel());
        Assert.assertEquals(0, cat04_0_0.getVoorkomen());

        final Lo3CategorieWaarde cat54_0_1 = categorieen.get(4);
        Assert.assertEquals(Lo3CategorieEnum.CATEGORIE_54, cat54_0_1.getCategorie());
        Assert.assertEquals(0, cat54_0_1.getStapel());
        Assert.assertEquals(1, cat54_0_1.getVoorkomen());

        final Lo3CategorieWaarde cat54_0_2 = categorieen.get(5);
        Assert.assertEquals(Lo3CategorieEnum.CATEGORIE_54, cat54_0_2.getCategorie());
        Assert.assertEquals(0, cat54_0_2.getStapel());
        Assert.assertEquals(2, cat54_0_2.getVoorkomen());

        final Lo3CategorieWaarde cat54_0_3 = categorieen.get(6);
        Assert.assertEquals(Lo3CategorieEnum.CATEGORIE_54, cat54_0_3.getCategorie());
        Assert.assertEquals(0, cat54_0_3.getStapel());
        Assert.assertEquals(3, cat54_0_3.getVoorkomen());

        final Lo3CategorieWaarde cat04_1_0 = categorieen.get(7);
        Assert.assertEquals(Lo3CategorieEnum.CATEGORIE_04, cat04_1_0.getCategorie());
        Assert.assertEquals(1, cat04_1_0.getStapel());
        Assert.assertEquals(0, cat04_1_0.getVoorkomen());

        final Lo3CategorieWaarde cat54_1_1 = categorieen.get(8);
        Assert.assertEquals(Lo3CategorieEnum.CATEGORIE_54, cat54_1_1.getCategorie());
        Assert.assertEquals(1, cat54_1_1.getStapel());
        Assert.assertEquals(1, cat54_1_1.getVoorkomen());

        final Lo3CategorieWaarde cat54_1_2 = categorieen.get(9);
        Assert.assertEquals(Lo3CategorieEnum.CATEGORIE_54, cat54_1_2.getCategorie());
        Assert.assertEquals(1, cat54_1_2.getStapel());
        Assert.assertEquals(2, cat54_1_2.getVoorkomen());

        final Lo3CategorieWaarde cat04_2_0 = categorieen.get(10);
        Assert.assertEquals(Lo3CategorieEnum.CATEGORIE_04, cat04_2_0.getCategorie());
        Assert.assertEquals(2, cat04_2_0.getStapel());
        Assert.assertEquals(0, cat04_2_0.getVoorkomen());

        final Lo3CategorieWaarde cat54_2_2 = categorieen.get(11);
        Assert.assertEquals(Lo3CategorieEnum.CATEGORIE_54, cat54_2_2.getCategorie());
        Assert.assertEquals(2, cat54_2_2.getStapel());
        Assert.assertEquals(1, cat54_2_2.getVoorkomen());

        final Lo3CategorieWaarde cat07_0_0 = categorieen.get(12);
        Assert.assertEquals(Lo3CategorieEnum.CATEGORIE_07, cat07_0_0.getCategorie());
        Assert.assertEquals(0, cat07_0_0.getStapel());
        Assert.assertEquals(0, cat07_0_0.getVoorkomen());

        final Lo3CategorieWaarde cat08_0_0 = categorieen.get(13);
        Assert.assertEquals(Lo3CategorieEnum.CATEGORIE_08, cat08_0_0.getCategorie());
        Assert.assertEquals(0, cat08_0_0.getStapel());
        Assert.assertEquals(0, cat08_0_0.getVoorkomen());
    }
}
