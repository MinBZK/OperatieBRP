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
public class KVFunctieTest {

    @Test
    public void testSignaturen() {
        assertExceptie("KV()", "De functie KV kan niet overweg met de argumenten: []");
        assertExceptie("KV(1)", "De functie KV kan niet overweg met de argumenten: [Getal]");
    }

    @Test
    public void testFunctie() throws ExpressieException {
        TestUtils.testEvaluatie("KV(NULL)", "NULL");
        TestUtils.testEvaluatie("KV({})", "ONWAAR");
        TestUtils.testEvaluatie("KV({1})", "WAAR");
        TestUtils.testEvaluatie("KV(Persoon.Identificatienummers.Burgerservicenummer)", "WAAR", ExpressietaalTestPersoon.PERSOONSLIJST);
        TestUtils.testEvaluatie("KV(Persoon.Adres.BuitenlandsAdresRegel6)", "ONWAAR", ExpressietaalTestPersoon.PERSOONSLIJST);

        Assert.assertEquals(ExpressieType.BOOLEAN, ExpressieParser.parse("KV({999})").getType(null));
    }
}
