/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl;

import static org.junit.Assert.assertTrue;

import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.gbavoorwaarderegels.DatumVoorwaardeRegel;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.gbavoorwaarderegels.KomtVoorVoorwaardeRegel;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.gbavoorwaarderegels.StandaardVoorwaardeRegel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Testen van de AbstractGbaVoorwaardeRegel.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/conversie-test.xml")
public class GbaVoorwaardeRegelTest {

    @Inject
    private GbaVoorwaardeRegelFactory gbaVoorwaardeRegelFactory;

    /**
     * Test of maakGbaVoorwaardeRegel method, of class AbstractGbaVoorwaardeRegel.
     */
    @Test
    public void testMaakStandaardVoorwaardeRegel() {
        final String gbaVoorwaardeRegel = "01.01.20 GA1 003412345 OFVGL 005012345 OFVGL 017112345";
        final GbaVoorwaardeRegel result = gbaVoorwaardeRegelFactory.maakGbaVoorwaardeRegel(gbaVoorwaardeRegel);
        assertTrue(result instanceof StandaardVoorwaardeRegel);
    }

    /**
     * Test creatie DatumVoorwaardeRegel
     *
     * @throws nl.bzk.migratiebrp.conversie.expressie.impl.GbaVoorwaardeOnvertaalbaarExceptie
     */
    @Test
    public void testMaakDatumVoorwaardeRegel() throws GbaVoorwaardeOnvertaalbaarExceptie {
        final String gbaVoorwaardeRegel = "01.03.10 KDOG1 19.89.30 - 00290000";
        final GbaVoorwaardeRegel result = gbaVoorwaardeRegelFactory.maakGbaVoorwaardeRegel(gbaVoorwaardeRegel);
        assertTrue(result instanceof DatumVoorwaardeRegel);
    }

    /**
     * Test creatie KomtVoorVoorwaardeRegel
     *
     * @throws nl.bzk.migratiebrp.conversie.expressie.impl.GbaVoorwaardeOnvertaalbaarExceptie
     */
    @Test
    public void testMaakKomtVoorVoorwaardeRegel() throws GbaVoorwaardeOnvertaalbaarExceptie {
        final String gbaVoorwaardeRegel = "KNV 01.03.10";
        final GbaVoorwaardeRegel result = gbaVoorwaardeRegelFactory.maakGbaVoorwaardeRegel(gbaVoorwaardeRegel);
        assertTrue(result instanceof KomtVoorVoorwaardeRegel);
    }

}
