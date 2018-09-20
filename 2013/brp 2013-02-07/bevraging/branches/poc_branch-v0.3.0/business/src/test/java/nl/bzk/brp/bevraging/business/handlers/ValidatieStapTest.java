/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.handlers;

import static org.junit.Assert.assertTrue;

import org.junit.Test;


/**
 * Unit test voor de {@link ValidatieStap} class.
 */
public class ValidatieStapTest {

    @Test
    public void test() {
        ValidatieStap stap = new ValidatieStap();
        assertTrue(stap.voerVerwerkingsStapUitVoorBericht(null, null, null));
    }

}
