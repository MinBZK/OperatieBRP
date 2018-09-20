/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl.gbavoorwaarderegels;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaVoorwaardeOnvertaalbaarExceptie;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test van de AdellijkeTitelVoorwaardeRegel.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/conversie-test.xml")
public class AdellijkeTitelVoorwaardeRegelTest {

    private static final String GEEN_UITKOMST = "GEEN UITKOMST";

    @Inject
    private AdellijkeTitelVoorwaardeRegel instance;

    @Test
    public void testAdellijkeTitelKomtVoorOfNiet() throws Exception {
        testVoorwaarde("KNV 01.02.20", "(IS_NULL(samengestelde_naam.adellijke_titel) EN IS_NULL(samengestelde_naam.predicaat))");
        testVoorwaarde("KV 01.02.20", "(NIET IS_NULL(samengestelde_naam.adellijke_titel) OF NIET IS_NULL(samengestelde_naam.predicaat))");
    }

    @Test(expected = GbaVoorwaardeOnvertaalbaarExceptie.class)
    public void testGoedeNietVertaaldeRubriek020220() throws Exception {
        testVoorwaarde("KV 02.02.20", GEEN_UITKOMST);
    }

    @Test(expected = GbaVoorwaardeOnvertaalbaarExceptie.class)
    public void testGoedeNietVertaaldeRubriek030220() throws Exception {
        testVoorwaarde("KV 03.02.20", GEEN_UITKOMST);
    }

    @Test(expected = GbaVoorwaardeOnvertaalbaarExceptie.class)
    public void testGoedeNietVertaaldeRubriek050220() throws Exception {
        testVoorwaarde("KV 05.02.20", GEEN_UITKOMST);
    }

    @Test(expected = GbaVoorwaardeOnvertaalbaarExceptie.class)
    public void testGoedeNietVertaaldeRubriek090220() throws Exception {
        testVoorwaarde("KV 09.02.20", GEEN_UITKOMST);
    }

    @Test(expected = GbaVoorwaardeOnvertaalbaarExceptie.class)
    public void testGoedeNietVertaaldeRubriek510220() throws Exception {
        testVoorwaarde("KV 51.02.20", GEEN_UITKOMST);
    }

    @Test(expected = GbaVoorwaardeOnvertaalbaarExceptie.class)
    public void testGoedeNietVertaaldeRubriek520220() throws Exception {
        testVoorwaarde("KNV 52.02.20", GEEN_UITKOMST);
    }

    @Test(expected = GbaVoorwaardeOnvertaalbaarExceptie.class)
    public void testGoedeNietVertaaldeRubriek530220() throws Exception {
        testVoorwaarde("KNV 53.02.20", GEEN_UITKOMST);
    }

    @Test(expected = GbaVoorwaardeOnvertaalbaarExceptie.class)
    public void testGoedeNietVertaaldeRubriek550220() throws Exception {
        testVoorwaarde("KNV 55.02.20", GEEN_UITKOMST);
    }

    @Test(expected = GbaVoorwaardeOnvertaalbaarExceptie.class)
    public void testGoedeNietVertaaldeRubriek590220() throws Exception {
        testVoorwaarde("KV 59.02.20", GEEN_UITKOMST);
    }

    private void testVoorwaarde(final String gbaVoorwaarde, final String brpExpressie) throws GbaVoorwaardeOnvertaalbaarExceptie, IOException {
        final String result = instance.getBrpExpressie(gbaVoorwaarde);
        assertEquals(brpExpressie, result);
    }
}
