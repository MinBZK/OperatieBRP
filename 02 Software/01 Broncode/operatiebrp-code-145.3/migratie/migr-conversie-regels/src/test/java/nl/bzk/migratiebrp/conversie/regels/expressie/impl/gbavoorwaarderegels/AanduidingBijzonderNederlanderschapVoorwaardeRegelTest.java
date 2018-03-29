/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl.gbavoorwaarderegels;

import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaVoorwaardeOnvertaalbaarExceptie;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import org.junit.After;
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

    private VoorwaardeRegelTestUtil testUtil;

    @Before
    public void initialize() {
        testUtil = new VoorwaardeRegelTestUtil(instance);
    }

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
        testUtil.testVoorwaarde("04.65.10 GA1 \"B\"", "KV(Persoon.Indicatie.BehandeldAlsNederlander.Waarde)");
        testUtil.testVoorwaarde("04.65.10 GAA \"B\"", "KV(Persoon.Indicatie.BehandeldAlsNederlander.Waarde)");
        testUtil.testVoorwaarde("04.65.10 OGA1 \"B\"", "KNV(Persoon.Indicatie.BehandeldAlsNederlander.Waarde)");
        testUtil.testVoorwaarde("04.65.10 OGAA \"B\"", "KNV(Persoon.Indicatie.BehandeldAlsNederlander.Waarde)");
    }

    @Test
    public void testVastgesteldNietNederlander() throws Exception {
        testUtil.testVoorwaarde("04.65.10 GA1 \"V\"", "KV(Persoon.Indicatie.VastgesteldNietNederlander.Waarde)");
        testUtil.testVoorwaarde("04.65.10 GAA \"V\"", "KV(Persoon.Indicatie.VastgesteldNietNederlander.Waarde)");
        testUtil.testVoorwaarde("04.65.10 OGA1 \"V\"", "KNV(Persoon.Indicatie.VastgesteldNietNederlander.Waarde)");
        testUtil.testVoorwaarde("04.65.10 OGAA \"V\"", "KNV(Persoon.Indicatie.VastgesteldNietNederlander.Waarde)");
    }

    @Test(expected = GbaVoorwaardeOnvertaalbaarExceptie.class)
    public void testFoutieveCode() throws Exception {
        testUtil.testVoorwaarde("04.65.10 GA1 \"X\"", "");
    }

    @Test(expected = GbaVoorwaardeOnvertaalbaarExceptie.class)
    public void testFoutieveOperator() throws Exception {
        testUtil.testVoorwaarde("04.65.10 KD1 \"X\"", "");
    }

    @Test
    public void testgbaVoorwaardemeerDan3Delen() throws Exception {
        testUtil.testVoorwaarde("04.65.10 GA1 \"V\"", "KV(Persoon.Indicatie.VastgesteldNietNederlander.Waarde)");
        testUtil.testVoorwaarde("04.65.10 GA1 AA \"V\"", "(KNV(Persoon.Indicatie.BehandeldAlsNederlander.Waarde) EN KNV(Persoon.Indicatie.VastgesteldNietNederlander.Waarde))");
    }
}
