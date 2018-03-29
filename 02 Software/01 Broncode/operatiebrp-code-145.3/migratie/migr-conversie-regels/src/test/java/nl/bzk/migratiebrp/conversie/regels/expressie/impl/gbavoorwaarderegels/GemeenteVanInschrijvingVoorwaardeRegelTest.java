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
public class GemeenteVanInschrijvingVoorwaardeRegelTest {

    @Inject
    private GemeenteVanInschrijvingVoorwaardeRegel instance;

    private VoorwaardeRegelTestUtil testUtil;

    @Before
    public void initialize() {
        testUtil = new VoorwaardeRegelTestUtil(instance);
    }

    /**
     * Test of vertaalWaardeVanRubriek method, of class PartijVoorwaardeRegel.
     */
    @Test
    public void testVertaalWaardeVanRubriek() throws GbaVoorwaardeOnvertaalbaarExceptie {
        testUtil.testVoorwaarde("08.09.10 GA1 0626", "Persoon.Bijhouding.PartijCode E= \"062601\"");
    }

    @Test
    public void testHistorischeCategorie() throws Exception {
        testUtil.testVoorwaarde("58.09.10 GA1 0626", "HISM(Persoon.Bijhouding.PartijCode) E= \"062601\"");
    }

    @Test
    public void testHistorischeRNICategorie() throws Exception {
        testUtil.testVoorwaarde("58.09.10 GA1 1999",
                "(HISM(Persoon.Bijhouding.PartijCode) E= \"199901\" EN NIET((HISM(Persoon.Bijhouding.PartijCode) A= \"199901\" EN AANTAL(HISM(Persoon.Bijhouding"
                        + ".PartijCode)) = 1 EN KNV(HISM(Persoon.Adres.BuitenlandsAdresRegel1)))))");
    }

    @Test
    public void testOngelijkRNIHistorischeCategorie() throws Exception {
        testUtil.testVoorwaarde("58.09.10 OGA1 1999",
                "(NIET(HISM(Persoon.Bijhouding.PartijCode) A= \"199901\") OF (HISM(Persoon.Bijhouding.PartijCode) A= \"199901\" EN AANTAL(HISM(Persoon.Bijhouding"
                        + ".PartijCode)) = 1 EN KNV(HISM(Persoon.Adres.BuitenlandsAdresRegel1))))");
    }

    @Test
    public void testKomtVoor() throws Exception {
        testUtil.testVoorwaarde("KV 08.09.10", "KV(Persoon.Bijhouding.PartijCode)");
    }

    @Test
    public void testKomtNietVoor() throws Exception {
        testUtil.testVoorwaarde("KNV 08.09.10", "KNV(Persoon.Bijhouding.PartijCode)");
    }

    @Test
    public void testKomtVoorHistorisch() throws Exception {
        testUtil.testVoorwaarde("KV 58.09.10", "KV(HISM(Persoon.Adres.GemeenteCode))");
    }

    @Test
    public void testKomtNietVoorHistorisch() throws Exception {
        testUtil.testVoorwaarde("KNV 58.09.10", "KNV(HISM(Persoon.Adres.GemeenteCode))");
    }

    /**
     * Test of volgorde method, of class PartijVoorwaardeRegel.
     */
    @Test
    public void testVolgorde() {
        assertEquals(100, instance.volgorde());
    }

    /**
     * Test of filter method, of class PartijVoorwaardeRegel.
     */
    @Test
    public void testFilterTrue() {
        assertTrue(instance.filter("08.09.10 GA1 0626"));
    }

    @Test
    public void testFilterFalse() {
        assertFalse(instance.filter("01.04.10 GD1 19940101"));
    }
}
