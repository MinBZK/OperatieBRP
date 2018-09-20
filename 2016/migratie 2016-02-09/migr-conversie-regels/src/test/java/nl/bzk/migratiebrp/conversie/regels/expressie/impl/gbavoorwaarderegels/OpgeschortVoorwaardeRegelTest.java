/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl.gbavoorwaarderegels;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaVoorwaardeOnvertaalbaarExceptie;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/conversie-test.xml")
public class OpgeschortVoorwaardeRegelTest {

    @Inject
    private OpgeschortVoorwaardeRegel instance;

    /**
     * Test of getBrpExpressie method, of class OpgeschortVoorwaardeRegel.
     * 
     * @throws java.lang.Exception
     *             ging niet goed
     */
    @Test
    public void testGetBrpExpressie() throws Exception {
        testVoorwaarde("KNV 07.67.10", "bijhouding.nadere_bijhoudingsaard = \"A\"");
        testVoorwaarde("KNV 07.67.20", "bijhouding.nadere_bijhoudingsaard = \"A\"");
        testVoorwaarde("KV 07.67.10", "bijhouding.nadere_bijhoudingsaard <> \"A\"");
        testVoorwaarde("KV 07.67.20", "bijhouding.nadere_bijhoudingsaard <> \"A\"");
    }

    private void testVoorwaarde(final String gbaVoorwaarde, final String brpExpressie) throws GbaVoorwaardeOnvertaalbaarExceptie {
        Assert.assertTrue(instance.filter(gbaVoorwaarde));
        final String result = instance.getBrpExpressie(gbaVoorwaarde);
        assertEquals(brpExpressie, result);
    }
}
