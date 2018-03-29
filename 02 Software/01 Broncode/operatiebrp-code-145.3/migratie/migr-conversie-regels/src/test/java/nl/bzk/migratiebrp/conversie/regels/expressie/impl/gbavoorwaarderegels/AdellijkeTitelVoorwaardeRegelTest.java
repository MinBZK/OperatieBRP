/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl.gbavoorwaarderegels;

import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaVoorwaardeOnvertaalbaarExceptie;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test van de AdellijkeTitelVoorwaardeRegel.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/conversie-test.xml")
public class AdellijkeTitelVoorwaardeRegelTest {

    private static final String GEEN_UITKOMST = "GEEN UITKOMST";

    private VoorwaardeRegelTestUtil testUtil;

    @Before
    public void initialize() {
        testUtil = new VoorwaardeRegelTestUtil(instance);
    }

    @Inject
    private AdellijkeTitelVoorwaardeRegel instance;

    @Test
    public void testAdellijkeTitelKomtVoorOfNiet() throws Exception {
        testUtil.testVoorwaarde("KNV 01.02.20", "(KNV(Persoon.SamengesteldeNaam.AdellijkeTitelCode) EN KNV(Persoon.SamengesteldeNaam.PredicaatCode))");
        testUtil.testVoorwaarde("KV 01.02.20", "(KV(Persoon.SamengesteldeNaam.AdellijkeTitelCode) OF KV(Persoon.SamengesteldeNaam.PredicaatCode))");
    }

    @Test(expected = GbaVoorwaardeOnvertaalbaarExceptie.class)
    public void testGoedeNietVertaaldeRubriek020220() throws Exception {
        testUtil.testVoorwaarde("KV 02.02.20", GEEN_UITKOMST);
    }

    @Test(expected = GbaVoorwaardeOnvertaalbaarExceptie.class)
    public void testGoedeNietVertaaldeRubriek030220() throws Exception {
        testUtil.testVoorwaarde("KV 03.02.20", GEEN_UITKOMST);
    }

    @Test(expected = GbaVoorwaardeOnvertaalbaarExceptie.class)
    public void testGoedeNietVertaaldeRubriek050220() throws Exception {
        testUtil.testVoorwaarde("KV 05.02.20", GEEN_UITKOMST);
    }

    @Test(expected = GbaVoorwaardeOnvertaalbaarExceptie.class)
    public void testGoedeNietVertaaldeRubriek090220() throws Exception {
        testUtil.testVoorwaarde("KV 09.02.20", GEEN_UITKOMST);
    }

    @Test(expected = GbaVoorwaardeOnvertaalbaarExceptie.class)
    public void testGoedeNietVertaaldeRubriek510220() throws Exception {
        testUtil.testVoorwaarde("KV 51.02.20", GEEN_UITKOMST);
    }

    @Test(expected = GbaVoorwaardeOnvertaalbaarExceptie.class)
    public void testGoedeNietVertaaldeRubriek520220() throws Exception {
        testUtil.testVoorwaarde("KNV 52.02.20", GEEN_UITKOMST);
    }

    @Test(expected = GbaVoorwaardeOnvertaalbaarExceptie.class)
    public void testGoedeNietVertaaldeRubriek530220() throws Exception {
        testUtil.testVoorwaarde("KNV 53.02.20", GEEN_UITKOMST);
    }

    @Test(expected = GbaVoorwaardeOnvertaalbaarExceptie.class)
    public void testGoedeNietVertaaldeRubriek550220() throws Exception {
        testUtil.testVoorwaarde("KNV 55.02.20", GEEN_UITKOMST);
    }

    @Test(expected = GbaVoorwaardeOnvertaalbaarExceptie.class)
    public void testGoedeNietVertaaldeRubriek590220() throws Exception {
        testUtil.testVoorwaarde("KV 59.02.20", GEEN_UITKOMST);
    }

    @Test
    public void vergelijkingMetHistorischeCategorieOGAA() throws Exception {
        testUtil.testVoorwaarde("01.02.20 OGAA 51.02.20",
                "(NIET(HISM(Persoon.SamengesteldeNaam.AdellijkeTitelCode) E= Persoon.SamengesteldeNaam.AdellijkeTitelCode) EN "
                        + "NIET(HISM(Persoon.SamengesteldeNaam.PredicaatCode) E= Persoon.SamengesteldeNaam.PredicaatCode))");
    }

    @Test
    public void vergelijkingMetHistorischeCategorieOGA1() throws Exception {
        testUtil.testVoorwaarde("01.02.20 OGA1 51.02.20",
                "(NIET(HISM(Persoon.SamengesteldeNaam.AdellijkeTitelCode) A= Persoon.SamengesteldeNaam.AdellijkeTitelCode) EN NIET(HISM(Persoon.SamengesteldeNaam.PredicaatCode) A= Persoon.SamengesteldeNaam.PredicaatCode))");
    }


    @Test
    public void vergelijkingMetHistorischeCategorieGA1() throws Exception {
        testUtil.testVoorwaarde("01.02.20 GA1 51.02.20",
                "(HISM(Persoon.SamengesteldeNaam.AdellijkeTitelCode) E= Persoon.SamengesteldeNaam.AdellijkeTitelCode EN HISM(Persoon.SamengesteldeNaam.PredicaatCode) E= Persoon.SamengesteldeNaam.PredicaatCode)");
    }

    @Test(expected = GbaVoorwaardeOnvertaalbaarExceptie.class)
    public void vergelijkingMetHistorischeCategorieOGA2() throws Exception {
        testUtil.testVoorwaarde("01.02.20 OGA2 51.02.20","");
    }

    @Test
    public void vergelijkingMetHistorischeCategorieOverigAdelijkeTitel() throws Exception {
        testUtil.testVoorwaarde("01.02.20 OGAA BS",
                "(Persoon.SamengesteldeNaam.AdellijkeTitelCode E= B EN Persoon.Geslachtsaanduiding.Code E= V)");
    }

    @Test
    public void vergelijkingMetHistorischeCategorieOverigPredicaat() throws Exception {
        testUtil.testVoorwaarde("01.02.20 OGAA JH",
                "(Persoon.SamengesteldeNaam.PredicaatCode E= J EN Persoon.Geslachtsaanduiding.Code E= M)");
    }
}
