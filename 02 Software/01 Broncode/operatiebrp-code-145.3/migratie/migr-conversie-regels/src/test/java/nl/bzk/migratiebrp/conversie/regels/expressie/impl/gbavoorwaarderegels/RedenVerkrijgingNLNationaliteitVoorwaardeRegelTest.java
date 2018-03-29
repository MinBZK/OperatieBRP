/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */


package nl.bzk.migratiebrp.conversie.regels.expressie.impl.gbavoorwaarderegels;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaVoorwaardeOnvertaalbaarExceptie;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/conversie-test.xml")
public class RedenVerkrijgingNLNationaliteitVoorwaardeRegelTest {

    @Inject
    private RedenVerkrijgingNLNationaliteitVoorwaardeRegel instance;

    private VoorwaardeRegelTestUtil testUtil;

    @Before
    public void initialize() {
        testUtil = new VoorwaardeRegelTestUtil(instance);
    }

    /**
     * Test of vertaalWaardeVanRubriek method, of class RedenVerkrijgingNLNationaliteitVoorwaardeRegel.
     */
    @Test
    public void testVertaalWaardeVanRubriek() throws GbaVoorwaardeOnvertaalbaarExceptie {
        testUtil.testVoorwaarde(
                "04.63.10 GA1 018",
                "(Persoon.Nationaliteit.RedenVerkrijgingCode E= \"018\" OF Persoon.Nationaliteit.MigratieRedenOpnameNationaliteit E= \"018\" OF Persoon.Indicatie"
                        + ".Staatloos.MigratieRedenOpnameNationaliteit E= \"018\")");
    }

    /**
     * Test of filter method, of class RedenVerkrijgingNLNationaliteitVoorwaardeRegel.
     */
    @Test
    public void testFilterTrue() {
        assertTrue(instance.filter("04.63.10 GA1 018"));
    }

    @Test
    public void testFilterFalse() {
        assertFalse(instance.filter("04.64.10 GA1 034"));
    }
}
