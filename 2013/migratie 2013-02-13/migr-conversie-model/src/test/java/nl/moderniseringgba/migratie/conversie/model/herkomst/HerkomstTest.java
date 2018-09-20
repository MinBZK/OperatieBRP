/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.herkomst;

import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.testutils.EqualsAndHashcodeTester;

import org.junit.Test;

public class HerkomstTest {

    @Test
    public void test() throws Exception {
        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 1, 1),
                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 1, 1), new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_02,
                        2, 2)

        );
    }
}
