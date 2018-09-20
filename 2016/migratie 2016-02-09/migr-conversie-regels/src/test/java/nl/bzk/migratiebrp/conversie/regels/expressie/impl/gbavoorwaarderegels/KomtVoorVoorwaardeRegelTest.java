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
 * Test van de KomtVoorVoorwaarde Regel.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/conversie-test.xml")
public class KomtVoorVoorwaardeRegelTest {

    @Inject
    private KomtVoorVoorwaardeRegel instance;

    /**
     * Test KomtVoorVoorwaardeRegel komt niet voor normale waarde.
     *
     * @throws java.lang.Exception
     *             is fout
     */
    @Test
    public void testKomtVoorVoorwaardeRegelKomtNietVoor() throws Exception {
        testVoorwaarde("KNV 01.03.10", "IS_NULL(geboorte.datum)");
    }

    @Test
    public void testKNVLijst() throws Exception {
        testVoorwaarde(
            "KNV 05.06.10",
            "(ALLE(RMAP(HUWELIJKEN(), x, x.datum_aanvang), v, IS_NULL(v))) EN (ALLE(RMAP(PARTNERSCHAPPEN(), x, x.datum_aanvang), v, IS_NULL(v)))");
    }

    @Test
    public void testNationaliteitUitzondering() throws Exception {
        testVoorwaarde("KV 04.05.10", "(ER_IS(RMAP(nationaliteiten, x, x.nationaliteit), v, NIET IS_NULL(v))) OF (NIET IS_NULL(indicatie.staatloos))");
        testVoorwaarde("KNV 04.05.10", "(ALLE(RMAP(nationaliteiten, x, x.nationaliteit), v, IS_NULL(v))) EN (IS_NULL(indicatie.staatloos))");
    }

    /**
     * Test KomtVoorVoorwaardeRegel komt voor normale waarde.
     *
     * @throws java.lang.Exception
     *             is fout
     */
    @Test
    public void testKomtVoorVoorwaardeRegelKomtVoor() throws Exception {
        testVoorwaarde("KV 01.03.10", "NIET IS_NULL(geboorte.datum)");
        testVoorwaarde("KV 01.01.10", "NIET IS_NULL(identificatienummers.administratienummer)");
    }

    /**
     * Test KomtVoorVoorwaardeRegel komt niet voor lijst waarde.
     *
     * @throws java.lang.Exception
     *             is fout
     */
    @Test
    public void testKomtVoorVoorwaardeRegelLijstKomtNietVoor() throws Exception {
        testVoorwaarde("KNV 08.11.60", "ALLE(RMAP(adressen, x, x.postcode), v, IS_NULL(v))");
    }

    /**
     * Test KomtVoorVoorwaardeRegel komt voor lijst waarde.
     *
     * @throws java.lang.Exception
     *             is fout
     */
    @Test
    public void testKomtVoorVoorwaardeRegelLijstKomtVoor() throws Exception {
        testVoorwaarde("KV 08.11.60", "ER_IS(RMAP(adressen, x, x.postcode), v, NIET IS_NULL(v))");
    }

    private void testVoorwaarde(final String gbaVoorwaarde, final String brpExpressie) throws GbaVoorwaardeOnvertaalbaarExceptie {
        Assert.assertTrue(instance.filter(gbaVoorwaarde));
        final String result = instance.getBrpExpressie(gbaVoorwaarde);
        assertEquals(brpExpressie, result);
    }
}
