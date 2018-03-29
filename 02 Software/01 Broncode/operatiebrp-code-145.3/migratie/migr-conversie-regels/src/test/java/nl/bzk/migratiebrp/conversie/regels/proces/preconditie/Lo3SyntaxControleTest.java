/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.preconditie;

import java.util.Collections;
import nl.bzk.migratiebrp.conversie.model.exceptions.OngeldigePersoonslijstException;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unittest voor {@link Lo3SyntaxControle}.
 */
public class Lo3SyntaxControleTest {

    @Before
    public void setUp() {
        Logging.initContext();
    }

    @After
    public void tearDown() {
        Logging.destroyContext();
    }

    @Test
    public void testGeenStuurKaraktersInElement() throws OngeldigePersoonslijstException {
        final Lo3CategorieWaarde lo3CategorieWaarde = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
        lo3CategorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0240, "Achternaam");

        final Lo3SyntaxControle syntaxControle = new Lo3SyntaxControle();
        syntaxControle.controleer(Collections.singletonList(lo3CategorieWaarde));
    }

    @Test(expected = OngeldigePersoonslijstException.class)
    public void testLineFeedInElement() throws OngeldigePersoonslijstException {
        final Lo3CategorieWaarde lo3CategorieWaarde = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
        lo3CategorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0240, "Achter\nnaam");

        final Lo3SyntaxControle syntaxControle = new Lo3SyntaxControle();
        syntaxControle.controleer(Collections.singletonList(lo3CategorieWaarde));
    }

    @Test(expected = OngeldigePersoonslijstException.class)
    public void testCarriageReturnInElement() throws OngeldigePersoonslijstException {
        final Lo3CategorieWaarde lo3CategorieWaarde = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
        lo3CategorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0240, "Achter\rnaam");

        final Lo3SyntaxControle syntaxControle = new Lo3SyntaxControle();
        syntaxControle.controleer(Collections.singletonList(lo3CategorieWaarde));
    }

    @Test(expected = OngeldigePersoonslijstException.class)
    public void testFormFeedInElement() throws OngeldigePersoonslijstException {
        final Lo3CategorieWaarde lo3CategorieWaarde = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
        lo3CategorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0240, "Achter\fnaam");

        final Lo3SyntaxControle syntaxControle = new Lo3SyntaxControle();
        syntaxControle.controleer(Collections.singletonList(lo3CategorieWaarde));
    }
}
