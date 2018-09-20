/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl.gbavoorwaarderegels;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaVoorwaardeOnvertaalbaarExceptie;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test van standaard gba voorwaarde regel.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/conversie-test.xml")
public class StandaardVoorwaardeRegelTest {

    @Inject
    private StandaardVoorwaardeRegel instance;

    /**
     * Test standaard regel enkelvoudige waarde voorkomend in lijst
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testGetBrpExpressieGA1() throws Exception {
        testVoorwaarde("08.09.10 GA1 0003 OFVGL 0005 OFVGL 0007", "bijhouding.bijhoudingspartij IN {3, 5, 7}");
        testVoorwaarde("08.09.10 GA1 0003", "bijhouding.bijhoudingspartij = 3");
        testVoorwaarde("04.05.10 GA1 0001", "ER_IS(RMAP(nationaliteiten, x, x.nationaliteit), v, v = 1)");
        testVoorwaarde("04.05.10 GA1 0001 OFVGL 0002", "ER_IS(RMAP(nationaliteiten, x, x.nationaliteit), v, v IN {1, 2})");
    }

    @Test
    public void testBrpExpressieOGA1() throws Exception {
        testVoorwaarde(
            "01.01.10 OGA1 123456789",
            "(NIET(identificatienummers.administratienummer = 123456789) OF IS_NULL(identificatienummers.administratienummer))");
        testVoorwaarde(
            "04.05.10 OGA1 0001",
            "(ER_IS(RMAP(nationaliteiten, x, x.nationaliteit), v, NIET(v = 1)) OF AANTAL(RMAP(nationaliteiten, x, x.nationaliteit)) = 0)");
        testVoorwaarde(
            "04.05.10 OGA1 0001 ENVGL 0002 ENVGL 0003",
            "(ER_IS(RMAP(nationaliteiten, x, x.nationaliteit), v, NIET(v IN {1, 2, 3})) OF AANTAL(RMAP(nationaliteiten, x, x.nationaliteit)) = 0)");
    }

    @Test(expected = GbaVoorwaardeOnvertaalbaarExceptie.class)
    public void testBrpExpressieGAA() throws Exception {
        testVoorwaarde("01.01.10 GAA 12234456 ENVGL 546474899", "Niet geïmplementeerd");
    }

    @Test
    public void testBrpExpressieOGAA() throws Exception {
        testVoorwaarde("08.09.10 OGAA 0003", "(NIET(bijhouding.bijhoudingspartij = 3) OF IS_NULL(bijhouding.bijhoudingspartij))");
        testVoorwaarde(
            "08.09.10 OGAA 0003 ENVGL 0005 ENVGL 0007",
            "(NIET (bijhouding.bijhoudingspartij IN {3, 5, 7}) OF IS_NULL(bijhouding.bijhoudingspartij))");
        testVoorwaarde("04.05.10 OGAA 0001", "ALLE(RMAP(nationaliteiten, x, x.nationaliteit), v, NIET(v = 1))");
        testVoorwaarde("04.05.10 OGAA 0001 ENVGL 0002", "ALLE(RMAP(nationaliteiten, x, x.nationaliteit), v, NIET(v IN {1, 2}))");
    }

    @Test
    public void testWildcard() throws Exception {
        testVoorwaarde(
            "08.11.60 GA1 \"7858/*\" OFVGL \"7859/*\" OFVGL \"7872/*\" OFVGL \"7875/*\"",
            "ER_IS(RMAP(adressen, x, x.postcode), v, v IN% {\"7858*\", \"7859*\", \"7872*\", \"7875*\"})");
        testVoorwaarde("08.11.60 GA1 \"9408/*\"", "ER_IS(RMAP(adressen, x, x.postcode), v, v %= \"9408*\")");
    }

    private void testVoorwaarde(final String gbaVoorwaarde, final String brpExpressie) throws GbaVoorwaardeOnvertaalbaarExceptie {
        final String result = instance.getBrpExpressie(gbaVoorwaarde);
        assertEquals(brpExpressie, result);
    }
}
