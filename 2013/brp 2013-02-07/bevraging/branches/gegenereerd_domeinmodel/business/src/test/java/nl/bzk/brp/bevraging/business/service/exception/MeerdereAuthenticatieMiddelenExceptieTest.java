/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.service.exception;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;


/**
 * Unit test voor de {@link MeerdereAuthenticatieMiddelenExceptie} class.
 */
public class MeerdereAuthenticatieMiddelenExceptieTest {

    @Test
    public void testMeerdereAuthenticatieMiddelenExceptie() {
        MeerdereAuthenticatieMiddelenExceptie exceptie = new MeerdereAuthenticatieMiddelenExceptie();
        assertNull(exceptie.getMessage());
        assertNull(exceptie.getCause());
    }

    @Test
    public void testMeerdereAuthenticatieMiddelenExceptieString() {
        MeerdereAuthenticatieMiddelenExceptie exceptie = new MeerdereAuthenticatieMiddelenExceptie("Test");
        assertEquals("Test", exceptie.getMessage());
        assertNull(exceptie.getCause());
    }

    @Test
    public void testMeerdereAuthenticatieMiddelenExceptieStringThrowable() {
        MeerdereAuthenticatieMiddelenExceptie exceptie = new MeerdereAuthenticatieMiddelenExceptie("Test", new Throwable());
        assertEquals("Test", exceptie.getMessage());
        assertNotNull(exceptie.getCause());
    }

    @Test
    public void testMeerdereAuthenticatieMiddelenExceptieThrowable() {
        MeerdereAuthenticatieMiddelenExceptie exceptie = new MeerdereAuthenticatieMiddelenExceptie(new Throwable("Test"));
        assertEquals("java.lang.Throwable: Test", exceptie.getMessage());
        assertNotNull(exceptie.getCause());
    }

}
