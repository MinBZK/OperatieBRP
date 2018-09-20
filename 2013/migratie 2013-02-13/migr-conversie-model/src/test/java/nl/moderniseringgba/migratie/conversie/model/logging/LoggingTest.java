/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.logging;

import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.testutils.EqualsAndHashcodeTester;

import org.junit.Test;

public class LoggingTest {

    @Test
    public void test() throws Exception {
        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new LogRegel(new Lo3Herkomst(
                Lo3CategorieEnum.CATEGORIE_01, 1, 1), LogSeverity.INFO, LogType.PRECONDITIE, "PRE046", "Bla bla"),
                new LogRegel(new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 1, 1), LogSeverity.INFO,
                        LogType.PRECONDITIE, "PRE046", "Bla bla"), new LogRegel(new Lo3Herkomst(
                        Lo3CategorieEnum.CATEGORIE_01, 1, 1), LogSeverity.WARNING, LogType.BIJZONDERE_SITUATIE,
                        "BIJZ001", "Babbeldebabbel")

        );
    }
}
