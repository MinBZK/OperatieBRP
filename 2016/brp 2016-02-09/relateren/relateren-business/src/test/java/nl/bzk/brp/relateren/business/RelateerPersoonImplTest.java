/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.relateren.business;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Testen van RelateerPersoonImpl
 */
public class RelateerPersoonImplTest {

    private RelateerPersoon relateerder = new RelateerPersoonImpl();

    @Test
    public void testRelateerPersoon(){
        assertFalse(relateerder.relateerOpBasisVanID(1L));
    }
}