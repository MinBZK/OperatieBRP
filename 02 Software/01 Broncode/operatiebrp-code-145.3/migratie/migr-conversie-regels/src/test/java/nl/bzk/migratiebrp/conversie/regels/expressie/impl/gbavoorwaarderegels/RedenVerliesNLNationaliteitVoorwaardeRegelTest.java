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
public class RedenVerliesNLNationaliteitVoorwaardeRegelTest {

    @Inject
    private RedenVerliesNLNationaliteitVoorwaardeRegel instance;

    private VoorwaardeRegelTestUtil testUtil;

    @Before
    public void initialize() {
        testUtil = new VoorwaardeRegelTestUtil(instance);
    }

    /**
     * Test of vertaalWaardeVanRubriek method, of class RedenVerliesNLNationaliteitVoorwaardeRegel.
     */
    @Test
    public void testVertaalWaardeVanRubriek() throws GbaVoorwaardeOnvertaalbaarExceptie {
        testUtil.testVoorwaarde("04.64.10 GA1 034",
                "((MAP(MAP(Persoon.Nationaliteit.Standaard, x, HISM_LAATSTE(x)), y, y.RedenVerliesCode) E= \"034\" EN KNV(Persoon.Nationaliteit"
                        + ".DatumAanvangGeldigheid)) OF (MAP(MAP(Persoon.Nationaliteit.Standaard, x, HISM_LAATSTE(x)), y, y"
                        + ".MigratieRedenBeeindigenNationaliteit) E= \"034\" EN KNV(Persoon.Nationaliteit.DatumAanvangGeldigheid)) OF (MAP(MAP(Persoon.Indicatie"
                        + ".Staatloos.Standaard, x, HISM_LAATSTE(x)), y, y.MigratieRedenBeeindigenNationaliteit) E= \"034\" EN KNV(Persoon.Indicatie.Staatloos"
                        + ".DatumAanvangGeldigheid)))");
    }

    /**
     * Test of filter method, of class RedenVerkrijgingNLNationaliteitVoorwaardeRegel.
     */
    @Test
    public void testFilterTrue() {
        assertTrue(instance.filter("04.64.10 GA1 034"));
    }

    @Test
    public void testKVRegel() {
        assertTrue(instance.filter("KV 04.64.10"));
    }

    @Test
    public void testFilterFalse() {
        assertFalse(instance.filter("04.63.10 GA1 018"));
    }

}
