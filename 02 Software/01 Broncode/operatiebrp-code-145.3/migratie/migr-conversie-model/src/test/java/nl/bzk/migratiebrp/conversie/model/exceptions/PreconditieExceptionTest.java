/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.exceptions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import org.junit.Test;

public class PreconditieExceptionTest {

    @Test
    public void testConstructors_1() {

        List<String> groepen = new ArrayList<>();
        groepen.add("1");
        groepen.add("3");
        groepen.add("2");
        PreconditieException e = new PreconditieException("naam", groepen);
        assertEquals(3, e.getGroepen().size());
        assertEquals("naam", e.getPreconditieNaam());
    }

    @Test
    public void testConstructors_2() {

        List<String> groepen = new ArrayList<>();
        groepen.add("1");
        groepen.add("3");
        groepen.add("2");
        groepen.add("4");
        PreconditieException e = new PreconditieException(SoortMeldingCode.AFN001, groepen.toArray(new String[groepen.size()]));
        assertEquals(4, e.getGroepen().size());
        assertEquals("AFN001", e.getPreconditieNaam());
    }

    @Test
    public void testConstructors_3() {
        PreconditieException e = new PreconditieException(SoortMeldingCode.PRE001, "5");
        assertEquals(1, e.getGroepen().size());
        assertEquals("PRE001", e.getPreconditieNaam());
    }

    @Test
    public void testEquals() {
        PreconditieException e1 = new PreconditieException(SoortMeldingCode.PRE001, "5");
        List<String> groepen2 = new ArrayList<>();
        List<String> groepen3 = new ArrayList<>();
        groepen2.add("5");
        PreconditieException e2 = new PreconditieException(SoortMeldingCode.PRE001, groepen2.toArray(new String[groepen2.size()]));
        groepen3.add("2");
        PreconditieException e3 = new PreconditieException(SoortMeldingCode.PRE001, groepen3.toArray(new String[groepen3.size()]));
        PreconditieException e4 = new PreconditieException(SoortMeldingCode.AFN001, groepen2.toArray(new String[groepen2.size()]));
        assertEquals(false, e1.equals(getException()));
        assertTrue(e1.equals(e2));
        assertFalse(e1.equals(e3));
        assertFalse(e1.equals(e4));
        assertTrue(0 < e1.hashCode());
    }

    private Exception getException() {
        return new OngeldigePersoonslijstException("test");
    }

}
