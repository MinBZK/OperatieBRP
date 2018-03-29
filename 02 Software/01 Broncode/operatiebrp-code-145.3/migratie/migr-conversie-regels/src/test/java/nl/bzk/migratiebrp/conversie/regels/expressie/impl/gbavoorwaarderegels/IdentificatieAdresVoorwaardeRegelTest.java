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
 * test voor Identificatie adres voorwaarde regel.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/conversie-test.xml")
public class IdentificatieAdresVoorwaardeRegelTest {

    @Inject
    private StandaardVoorwaardeRegel instance;

    private VoorwaardeRegelTestUtil testUtil;

    @Before
    public void initialize() {
        testUtil = new VoorwaardeRegelTestUtil(instance);
    }

    /**
     * Test of getBrpExpressie method, of class IdentificatieAdresVoorwaardeRegel.
     */
    @Test
    public void testGetBrpExpressie() throws Exception {
        testUtil.testVoorwaarde("08.11.80 GA1 \"000123456789\"", "Persoon.Adres.IdentificatiecodeAdresseerbaarObject E= \"000123456789\"");
        testUtil.testVoorwaarde("08.11.90 GA1 \"000123456789\"", "Persoon.Adres.IdentificatiecodeNummeraanduiding E= \"000123456789\"");
    }
}