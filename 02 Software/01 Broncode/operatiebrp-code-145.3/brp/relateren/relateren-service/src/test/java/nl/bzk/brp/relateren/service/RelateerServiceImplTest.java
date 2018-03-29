/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.relateren.service;

import static org.junit.Assert.*;

import nl.bzk.brp.relateren.business.RelateerPersoonImpl;
import org.junit.Test;

/**
 * Test de RelateerServiceImpl.
 */
public class RelateerServiceImplTest {

    private RelateerService service = new RelateerServiceImpl(new RelateerPersoonImpl());

    /**
     * Test verwerkPersoonRelateerBericht.
     */
    @Test
    public void testVerwerkPersoonRelateerBericht() {
        //1.Expected

        //2.Execute

        //3.Verify
    }

    /**
     * Test verwerkPersoonRelateerBericht met null input.
     */
    @Test(expected = NullPointerException.class)
    public void testVerwerkPersoonRelateerBerichtNull() {
        service.verwerkPersoonRelateerBericht(null);
    }
}