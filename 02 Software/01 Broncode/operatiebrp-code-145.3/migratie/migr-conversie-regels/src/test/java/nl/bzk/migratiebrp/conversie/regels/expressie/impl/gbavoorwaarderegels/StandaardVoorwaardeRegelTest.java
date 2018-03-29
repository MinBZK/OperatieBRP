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
 * Test van standaard gba voorwaarde regel.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/conversie-test.xml")
public class StandaardVoorwaardeRegelTest {

    @Inject
    private StandaardVoorwaardeRegel instance;

    private VoorwaardeRegelTestUtil testUtil;

    @Before
    public void initialize() {
        testUtil = new VoorwaardeRegelTestUtil(instance);
    }

    /**
     * Test standaard regel enkelvoudige waarde voorkomend in lijst
     */
    @Test
    public void testGetBrpExpressieGA1() throws Exception {
        testUtil.testVoorwaarde("08.09.10 GA1 0003 OFVGL 0005 OFVGL 0007", "Persoon.Bijhouding.PartijCode EIN {\"0003\", \"0005\", \"0007\"}");
        testUtil.testVoorwaarde("08.09.10 GA1 0003", "Persoon.Bijhouding.PartijCode E= \"0003\"");
        testUtil.testVoorwaarde("04.05.10 GA1 0001", "Persoon.Nationaliteit.NationaliteitCode E= \"0001\"");
        testUtil.testVoorwaarde("04.05.10 GA1 0001 OFVGL 0002", "Persoon.Nationaliteit.NationaliteitCode EIN {\"0001\", \"0002\"}");
    }

    @Test
    public void testBrpExpressieOGA1() throws Exception {
        testUtil.testVoorwaarde("01.01.10 OGA1 123456789", "NIET(Persoon.Identificatienummers.Administratienummer A= \"123456789\")");
        testUtil.testVoorwaarde("04.05.10 OGA1 0001", "NIET(Persoon.Nationaliteit.NationaliteitCode A= \"0001\")");
        testUtil.testVoorwaarde("04.05.10 OGA1 0001 ENVGL 0002 ENVGL 0003", "NIET(Persoon.Nationaliteit.NationaliteitCode AIN {\"0001\", \"0002\", \"0003\"})");
    }

    @Test(expected = GbaVoorwaardeOnvertaalbaarExceptie.class)
    public void testBrpExpressieGAA() throws Exception {
        testUtil.testVoorwaarde("01.01.10 GAA 12234456 ENVGL 546474899", "Niet geïmplementeerd");
    }

    @Test
    public void testBrpExpressieOGAA() throws Exception {
        testUtil.testVoorwaarde("08.09.10 OGAA 0003", "NIET(Persoon.Bijhouding.PartijCode E= \"0003\")");
        testUtil.testVoorwaarde("08.09.10 OGAA 0003 ENVGL 0005 ENVGL 0007", "NIET(Persoon.Bijhouding.PartijCode EIN {\"0003\", \"0005\", \"0007\"})");
        testUtil.testVoorwaarde("04.05.10 OGAA 0001", "NIET(Persoon.Nationaliteit.NationaliteitCode E= \"0001\")");
        testUtil.testVoorwaarde("04.05.10 OGAA 0001 ENVGL 0002", "NIET(Persoon.Nationaliteit.NationaliteitCode EIN {\"0001\", \"0002\"})");
    }

    @Test
    public void testWildcard() throws Exception {
        testUtil.testVoorwaarde(
                "08.11.60 GA1 \"7858/*\" OFVGL \"7859/*\" OFVGL \"7872/*\" OFVGL \"7875/*\"",
                "Persoon.Adres.Postcode EIN% {\"7858*\", \"7859*\", \"7872*\", \"7875*\"}");
        testUtil.testVoorwaarde("08.11.60 GA1 \"9408/*\"", "Persoon.Adres.Postcode E=% \"9408*\"");
    }

    @Test
    public void voorwaardeVergelijkingMetHistorischeRubriek() throws Exception {
        testUtil.testVoorwaarde("01.01.10 OGAA 51.01.10",
                "NIET(HISM(Persoon.Identificatienummers.Administratienummer) E= Persoon.Identificatienummers.Administratienummer)");

    }
}
