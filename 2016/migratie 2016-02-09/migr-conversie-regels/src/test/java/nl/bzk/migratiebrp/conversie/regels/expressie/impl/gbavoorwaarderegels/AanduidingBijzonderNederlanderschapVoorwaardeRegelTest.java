/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl.gbavoorwaarderegels;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaVoorwaardeOnvertaalbaarExceptie;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/conversie-test.xml")
public class AanduidingBijzonderNederlanderschapVoorwaardeRegelTest {

    @Inject
    private AanduidingBijzonderNederlanderschapVoorwaardeRegel instance;

    @Before
    public void setup() {
        Logging.initContext();
    }

    @After
    public void destroy() {
        Logging.destroyContext();
    }

    @Test
    public void testBehandeldAlsNederlander() throws Exception {
        testVoorwaarde("04.65.10 GA1 \"B\"", "NIET IS_NULL(indicatie.behandeld_als_nederlander)");
        testVoorwaarde("04.65.10 GAA \"B\"", "NIET IS_NULL(indicatie.behandeld_als_nederlander)");
        testVoorwaarde("04.65.10 OGA1 \"B\"", "IS_NULL(indicatie.behandeld_als_nederlander)");
        testVoorwaarde("04.65.10 OGAA \"B\"", "IS_NULL(indicatie.behandeld_als_nederlander)");
    }

    @Test
    public void testVastgesteldNietNederlander() throws Exception {
        testVoorwaarde("04.65.10 GA1 \"V\"", "NIET IS_NULL(indicatie.vastgesteld_niet_nederlander)");
        testVoorwaarde("04.65.10 GAA \"V\"", "NIET IS_NULL(indicatie.vastgesteld_niet_nederlander)");
        testVoorwaarde("04.65.10 OGA1 \"V\"", "IS_NULL(indicatie.vastgesteld_niet_nederlander)");
        testVoorwaarde("04.65.10 OGAA \"V\"", "IS_NULL(indicatie.vastgesteld_niet_nederlander)");
    }

    @Test(expected = GbaVoorwaardeOnvertaalbaarExceptie.class)
    public void testFoutieveCode() throws Exception {
        testVoorwaarde("04.65.10 GA1 \"X\"", "");
    }

    @Test(expected = GbaVoorwaardeOnvertaalbaarExceptie.class)
    public void testFoutieveOperator() throws Exception {
        testVoorwaarde("04.65.10 KD1 \"X\"", "");
    }

    private void testVoorwaarde(final String gbaVoorwaarde, final String brpExpressie) throws GbaVoorwaardeOnvertaalbaarExceptie {
        final String result = instance.getBrpExpressie(gbaVoorwaarde);
        Assert.assertTrue(instance.filter(gbaVoorwaarde));
        assertEquals(brpExpressie, result);
    }
}
