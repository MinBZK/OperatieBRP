/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl.gbavoorwaarderegels;

import javax.inject.Inject;
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
public class OpgeschortVoorwaardeRegelTest {

    @Inject
    private OpgeschortVoorwaardeRegel instance;

    private VoorwaardeRegelTestUtil testUtil;

    @Before
    public void initialize() {
        testUtil = new VoorwaardeRegelTestUtil(instance);
    }

    /**
     * Test of getBrpExpressie method, of class OpgeschortVoorwaardeRegel.
     * @throws java.lang.Exception ging niet goed
     */
    @Test
    public void testGetBrpExpressie() throws Exception {
        testUtil.testVoorwaarde("KNV 07.67.10", "Persoon.Bijhouding.NadereBijhoudingsaardCode E= \"A\"");
        testUtil.testVoorwaarde("KNV 07.67.20", "Persoon.Bijhouding.NadereBijhoudingsaardCode E= \"A\"");
        testUtil.testVoorwaarde("KV 07.67.10", "NIET(Persoon.Bijhouding.NadereBijhoudingsaardCode A= \"A\")");
        testUtil.testVoorwaarde("KV 07.67.20", "NIET(Persoon.Bijhouding.NadereBijhoudingsaardCode A= \"A\")");
    }
}
