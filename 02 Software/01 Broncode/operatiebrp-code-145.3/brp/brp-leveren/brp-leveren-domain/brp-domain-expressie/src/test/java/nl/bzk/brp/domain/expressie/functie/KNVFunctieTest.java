/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.functie;

import static nl.bzk.brp.domain.expressie.util.TestUtils.assertExceptie;

import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.expressie.ExpressieType;
import nl.bzk.brp.domain.expressie.parser.ExpressieParser;
import nl.bzk.brp.domain.expressie.util.ExpressietaalTestPersoon;
import nl.bzk.brp.domain.expressie.util.TestUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 */
public class KNVFunctieTest {

    @Test
    public void testSignaturen() {
        assertExceptie("KNV()", "De functie KNV kan niet overweg met de argumenten: []");
        assertExceptie("KNV(1)", "De functie KNV kan niet overweg met de argumenten: [Getal]");
    }

    @Test
    public void testFunctie() throws ExpressieException {
        TestUtils.testEvaluatie("KNV(NULL)", "NULL");
        TestUtils.testEvaluatie("KNV({})", "WAAR");
        TestUtils.testEvaluatie("KNV({1})", "ONWAAR");
        TestUtils.testEvaluatie("KNV(Persoon.Identificatienummers.Burgerservicenummer)", "ONWAAR", ExpressietaalTestPersoon.PERSOONSLIJST);
        TestUtils.testEvaluatie("KNV(Persoon.Adres.BuitenlandsAdresRegel6)", "WAAR", ExpressietaalTestPersoon.PERSOONSLIJST);

        Assert.assertEquals(ExpressieType.BOOLEAN, ExpressieParser.parse("KNV({999})").getType(null));
    }
}
