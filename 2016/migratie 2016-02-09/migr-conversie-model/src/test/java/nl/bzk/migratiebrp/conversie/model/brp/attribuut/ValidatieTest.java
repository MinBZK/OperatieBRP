/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.attribuut;

import java.lang.reflect.Constructor;

import org.junit.Test;

public class ValidatieTest {

    @Test(expected = AssertionError.class)
    public void testPrivateConstructor() throws Throwable {
        try {
            Constructor<Validatie> c = Validatie.class.getDeclaredConstructor();
            c.setAccessible(true);
            Validatie u = c.newInstance();
        } catch (Exception e) {
            throw e.getCause();
        }
    }

    @Test
    public void testControleerOpLegeWaardenNull() throws Throwable {
        Validatie.controleerOpLegeWaarden("message", null, null);

    }

    @Test
    public void testControleerOpLegeWaarden() throws Throwable {
        Validatie.controleerOpLegeWaarden("message", null, null);
        Validatie.controleerOpLegeWaarden("message","iets","nog iets");
        Validatie.controleerOpLegeWaarden("message");

    }

    /*
     * @Test(expected = NullPointerException.class) public void testControleerOpLegeWaardesNotNull() throws Throwable {
     * Validatie.controleerOpNullWaarden("message",);
     * 
     * }
     */
    @Test(expected = NullPointerException.class)
    public void testControleerOpNullWaardesNull() throws Throwable {
        Validatie.controleerOpNullWaarden("message", null, null);

    }

}
