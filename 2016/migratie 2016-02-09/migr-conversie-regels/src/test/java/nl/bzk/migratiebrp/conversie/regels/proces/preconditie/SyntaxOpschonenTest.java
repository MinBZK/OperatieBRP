/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.preconditie;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.inject.Inject;
import junit.framework.Assert;
import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;
import nl.bzk.migratiebrp.conversie.model.exceptions.OngeldigePersoonslijstException;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaardeUtil;
import nl.bzk.migratiebrp.conversie.regels.proces.AbstractLoggingTest;
import nl.bzk.migratiebrp.util.excel.ExcelAdapter;
import nl.bzk.migratiebrp.util.excel.ExcelAdapterException;
import nl.bzk.migratiebrp.util.excel.ExcelAdapterImpl;
import nl.bzk.migratiebrp.util.excel.ExcelData;
import org.junit.Test;

public class SyntaxOpschonenTest extends AbstractLoggingTest {

    private final ExcelAdapter excelAdapter = new ExcelAdapterImpl();

    @Inject
    private Lo3SyntaxControle syntax;

    @Test
    public void testOpschonenGeenFout() throws IOException, ExcelAdapterException, Lo3SyntaxException, OngeldigePersoonslijstException {
        try (final InputStream testDataStream = SyntaxOpschonenTest.class.getResourceAsStream("OpschonenTestGeenFouten.xls")) {
            final List<ExcelData> data = excelAdapter.leesExcelBestand(testDataStream);
            Assert.assertEquals(1, data.size());

            final List<Lo3CategorieWaarde> categorieen = data.get(0).getCategorieLijst();
            Assert.assertEquals(5, categorieen.size());

            final List<Lo3CategorieWaarde> check = syntax.controleer(categorieen);
            Assert.assertEquals(5, check.size());

            assertAantalWarnings(0);
        }
    }

    @Test
    public void testOpschonenAfkappen() throws IOException, ExcelAdapterException, Lo3SyntaxException, OngeldigePersoonslijstException {
        try (final InputStream testDataStream = SyntaxOpschonenTest.class.getResourceAsStream("OpschonenTestAfkappen.xls")) {
            final List<ExcelData> data = excelAdapter.leesExcelBestand(testDataStream);
            Assert.assertEquals(1, data.size());

            final List<Lo3CategorieWaarde> categorieen = data.get(0).getCategorieLijst();
            Assert.assertEquals(6, categorieen.size());

            final String ongecontroleerd =
                    Lo3CategorieWaardeUtil.getElementWaarde(categorieen, Lo3CategorieEnum.CATEGORIE_08, 0, 0, Lo3ElementEnum.ELEMENT_1330);
            Assert.assertEquals("Rue de la poepiedoepiedorriedompie 123332121321", ongecontroleerd);

            final List<Lo3CategorieWaarde> check = syntax.controleer(categorieen);
            Assert.assertEquals(6, check.size());

            assertAantalWarnings(1);

            final String gecontroleerd = Lo3CategorieWaardeUtil.getElementWaarde(check, Lo3CategorieEnum.CATEGORIE_08, 0, 0, Lo3ElementEnum.ELEMENT_1330);
            Assert.assertEquals("Rue de la poepiedoepiedorriedompie ", gecontroleerd);
        }
    }

    @Test(expected = OngeldigePersoonslijstException.class)
    public void testOpschonenFoutInJuist() throws IOException, ExcelAdapterException, Lo3SyntaxException, OngeldigePersoonslijstException {
        try (final InputStream testDataStream = SyntaxOpschonenTest.class.getResourceAsStream("OpschonenTestFoutInJuist.xls")) {
            final List<ExcelData> data = excelAdapter.leesExcelBestand(testDataStream);
            Assert.assertEquals(1, data.size());

            final List<Lo3CategorieWaarde> categorieen = data.get(0).getCategorieLijst();
            Assert.assertEquals(5, categorieen.size());

            syntax.controleer(categorieen);
        }
    }
}
