/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.logging;

import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.model.testutils.EqualsAndHashcodeTester;
import org.junit.Test;

public class LoggingTest {

    @Test
    public void test() throws NoSuchMethodException, IllegalAccessException {
        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new LogRegel(
                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 1, 1),
                LogSeverity.INFO,
                SoortMeldingCode.PRE046,
                null), new LogRegel(new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 1, 1), LogSeverity.INFO, SoortMeldingCode.PRE046, null), new LogRegel(
                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 1, 1),
                LogSeverity.WARNING,
                SoortMeldingCode.BIJZ_CONV_LB004,
                null)

        );
    }
}
