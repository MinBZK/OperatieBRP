/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl.gbavoorwaarderegels;

import static org.junit.Assert.assertEquals;
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
public class GemeenteVoorwaardeRegelTest {

    @Inject
    private GemeenteVoorwaardeRegel instance;

    private VoorwaardeRegelTestUtil testUtil;

    @Before
    public void initialize() {
        testUtil = new VoorwaardeRegelTestUtil(instance);
    }

    /**
     * Test of vertaalWaardeVanRubriek method, of class GemeenteVoorwaardeRegel.
     */
    @Test
    public void testVertaalWaardeVanRubriek() throws GbaVoorwaardeOnvertaalbaarExceptie {
        testUtil.testVoorwaarde("01.03.20 GA1 0626", "(Persoon.Geboorte.BuitenlandsePlaats E= 0626 OF Persoon.Geboorte.OmschrijvingLocatie E="
                + " 0626 OF Persoon.Geboorte.Woonplaatsnaam E= 0626 OF Persoon.Geboorte.GemeenteCode E= 0626)");
    }

    @Test
    public void testVertaalWaardeVanRubriekBuitenlandsePlaats() throws GbaVoorwaardeOnvertaalbaarExceptie {
        testUtil.testVoorwaarde("01.03.20 GA1 \"Den Haag\"", "(Persoon.Geboorte.BuitenlandsePlaats E= \"Den Haag\" OF Persoon.Geboorte.OmschrijvingLocatie E="
                + " \"Den Haag\" OF Persoon.Geboorte.Woonplaatsnaam E= \"Den Haag\")");
    }

    /**
     * Test of volgorde method, of class GemeenteVoorwaardeRegel.
     */
    @Test
    public void testVolgorde() {
        assertEquals(500, instance.volgorde());
    }

    /**
     * Test of filter method, of class PartijVoorwaardeRegel.
     */
    @Test
    public void testFilterTrue() {
        assertTrue(instance.filter("01.03.20 GA1 0626"));
    }

    @Test
    public void testFilterFalse() {
        assertFalse(instance.filter("01.04.10 GD1 19940101"));
    }

}
