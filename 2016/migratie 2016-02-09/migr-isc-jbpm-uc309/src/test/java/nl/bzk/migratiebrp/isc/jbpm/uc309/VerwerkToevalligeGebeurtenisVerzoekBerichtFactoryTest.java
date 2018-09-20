/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc309;

import nl.bzk.migratiebrp.bericht.model.lo3.impl.Tb02Bericht;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class VerwerkToevalligeGebeurtenisVerzoekBerichtFactoryTest {

    private final Tb02Factory tb02Factory = new Tb02Factory();
    private VerwerkToevalligeGebeurtenisVerzoekBerichtFactory factory;

    @Before
    public void setUp() {
        factory = new VerwerkToevalligeGebeurtenisVerzoekBerichtFactory();
    }

    @Test
    public void testFactorySluiting() throws Exception {
        final Tb02Bericht sluiting = tb02Factory.maakTb02Bericht(Tb02Factory.Soort.SLUITING);
        assertTrue("Aangemaakte class dient een ToevalligeGebeurtenisSluiting te zijn", factory.maakVerwerker(sluiting) instanceof ToevalligeGebeurtenisSluiting);
    }

    @Test
    public void testFactoryOntbinding() throws Exception {
        final Tb02Bericht sluiting = tb02Factory.maakTb02Bericht(Tb02Factory.Soort.ONTBINDING);
        assertTrue("Aangemaakte class dient een ToevalligeGebeurtenisOntbinding te zijn", factory.maakVerwerker(sluiting) instanceof ToevalligeGebeurtenisOntbinding);
    }
}