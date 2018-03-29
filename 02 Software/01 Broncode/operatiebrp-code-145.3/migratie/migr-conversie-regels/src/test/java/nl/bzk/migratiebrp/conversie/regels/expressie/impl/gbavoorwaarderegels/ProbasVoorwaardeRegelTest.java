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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/conversie-test.xml")
public class ProbasVoorwaardeRegelTest {

    @Inject
    private ProbasVoorwaardeRegel instance;

    private VoorwaardeRegelTestUtil testUtil;

    @Before
    public void initialize() {
        testUtil = new VoorwaardeRegelTestUtil(instance);
    }

    @Test
    public void testGetBrpExpressie() throws Exception {
        testUtil.testVoorwaarde("04.82.30 GA1 \"pRoBa\"", "KV(Persoon.Indicatie.BijzondereVerblijfsrechtelijkePositie.Waarde)");
        testUtil.testVoorwaarde("04.82.30 OGA1 \"pRoBa\"", "KNV(Persoon.Indicatie.BijzondereVerblijfsrechtelijkePositie.Waarde)");

        testUtil.testVoorwaarde(
                "04.82.30 OGAA \"proba/*\" ENVGL \"Proba/*\" ENVGL \"PRoba/*\" ENVGL \"PROba/*\" ENVGL \"PROBa/*\" ENVGL \"PROBA/*\" ENVGL \"pROBA/*\" ENVGL "
                        + "\"/?proba/*\" ENVGL \"/?Proba/*\" ENVGL \"/?PRoba/*\" ENVGL \"/?PROba/*\" ENVGL \"/?PROBa/*\" ENVGL \"/?PROBA/*\" ENVGL "
                        + "\"/?pROBA/*\"",
                "KNV(Persoon.Indicatie.BijzondereVerblijfsrechtelijkePositie.Waarde)");
    }

}
