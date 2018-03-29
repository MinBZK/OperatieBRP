/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl.gbavoorwaarderegels;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/conversie-test.xml")
public class LandVoorwaardeRegelTest {

    @Inject
    private LandVoorwaardeRegel instance;

    private VoorwaardeRegelTestUtil testUtil;

    @Before
    public void initialize() {
        testUtil = new VoorwaardeRegelTestUtil(instance);
    }

    /**
     * Test of getBrpExpressie method, of class LandVoorwaardeRegel.
     */
    @Test
    public void testGetBrpExpressie() throws Exception {
        testUtil.testVoorwaarde("01.03.30 GA1 \"6030\"", "Persoon.Geboorte.LandGebiedCode E= \"6030\"");
    }

    @Test
    public void testFilterTrue() {
        assertTrue(instance.filter("01.03.30 GA1 \"6030\""));
    }

    @Test
    public void testFilterFalse() {
        assertFalse(instance.filter("01.03.20 GA1 \"6030\""));
    }
}
