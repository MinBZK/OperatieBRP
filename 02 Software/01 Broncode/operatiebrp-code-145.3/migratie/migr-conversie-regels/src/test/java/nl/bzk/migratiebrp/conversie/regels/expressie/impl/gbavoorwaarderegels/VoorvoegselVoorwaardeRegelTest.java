/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl.gbavoorwaarderegels;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/conversie-test.xml")
public class VoorvoegselVoorwaardeRegelTest {

    @Inject
    private VoorvoegselVoorwaardeRegel instance;

    private VoorwaardeRegelTestUtil testUtil;

    @Before
    public void initialize() {
        testUtil = new VoorwaardeRegelTestUtil(instance);
    }

    @Test
    public void testVoorvoegselPersoonEenGelijk() throws Exception {
        testUtil.testVoorwaarde("01.02.30 GA1 \"van der\"", "(Persoon.SamengesteldeNaam.Voorvoegsel E= \"van der\" EN Persoon.SamengesteldeNaam.Scheidingsteken E= \" \")");
    }

    @Test
    public void testVoorvoegselPersoonEenGelijkHuwelijksePartner() throws Exception {
        testUtil.testVoorwaarde("05.02.30 GA1 \"van der\"", "((GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam.Voorvoegsel E= \"van der\" EN GerelateerdeHuwelijkspartner.Persoon"
                + ".SamengesteldeNaam.Scheidingsteken E= \" \") OF (GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam.Voorvoegsel E= \"van der\" EN"
                + " GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam.Scheidingsteken E= \" \"))");
    }

    @Test
    public void testVoorvoegselPersoonEenOngelijk() throws Exception {
        testUtil.testVoorwaarde("01.02.30 OGA1 \"van der\"", "(NIET(Persoon.SamengesteldeNaam.Voorvoegsel A= \"van der\") OF NIET(Persoon.SamengesteldeNaam"
                + ".Scheidingsteken A= \" \"))");
    }

    @Test
    public void testVoorvoegselPersoonAlleOngelijk() throws Exception {
        testUtil.testVoorwaarde("01.02.30 OGAA \"van der\"", "(NIET(Persoon.SamengesteldeNaam.Voorvoegsel E= \"van der\") OF NIET(Persoon.SamengesteldeNaam"
                + ".Scheidingsteken E= \" \"))");
    }

    /**
     * Test of volgorde method, of class GemeenteVoorwaardeRegel.
     */
    @Test
    public void testVolgorde() {
        assertEquals(500, instance.volgorde());
    }

    /**
     * Test of filter method, of class PartijVoorwaardeRegel.
     */
    @Test
    public void testFilterTrue() {
        assertTrue(instance.filter("01.02.30 GA1 \"van der\""));
    }

    @Test
    public void testFilterFalse() {
        assertFalse(instance.filter("01.04.10 GD1 19940101"));
    }
}