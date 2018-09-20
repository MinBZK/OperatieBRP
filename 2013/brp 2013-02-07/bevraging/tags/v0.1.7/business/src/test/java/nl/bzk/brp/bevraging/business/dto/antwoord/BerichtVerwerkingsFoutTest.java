/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.dto.antwoord;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;


/**
 * Unit test voor de {@link BerichtVerwerkingsFout} class.
 */
public class BerichtVerwerkingsFoutTest {

    @Test
    public void testConstructorCodeEnZwaarte() {
        BerichtVerwerkingsFout fout =
            new BerichtVerwerkingsFout(BerichtVerwerkingsFoutCode.FUNCTIONELE_AUTORISATIE_FOUT,
                    BerichtVerwerkingsFoutZwaarte.WAARSCHUWING);

        assertEquals(BerichtVerwerkingsFoutCode.FUNCTIONELE_AUTORISATIE_FOUT.getCode(), fout.getCode());
        assertEquals(BerichtVerwerkingsFoutZwaarte.WAARSCHUWING, fout.getZwaarte());
        assertEquals(BerichtVerwerkingsFoutCode.FUNCTIONELE_AUTORISATIE_FOUT.getStandaardBericht(), fout.getMelding());
    }

    @Test
    public void testConstructorCodeNullEnZwaarte() {
        BerichtVerwerkingsFout fout = new BerichtVerwerkingsFout(null, BerichtVerwerkingsFoutZwaarte.FOUT);

        assertEquals("<ONBEKEND>", fout.getCode());
        assertEquals(BerichtVerwerkingsFoutZwaarte.FOUT, fout.getZwaarte());
        assertNull(fout.getMelding());
    }

    @Test
    public void testConstructorZonderConcatenatie() {
        BerichtVerwerkingsFout fout;

        fout =
            new BerichtVerwerkingsFout(BerichtVerwerkingsFoutCode.ONBEKENDE_FOUT,
                    BerichtVerwerkingsFoutZwaarte.SYSTEEM, "Test");
        assertEquals(BerichtVerwerkingsFoutCode.ONBEKENDE_FOUT.getCode(), fout.getCode());
        assertEquals(BerichtVerwerkingsFoutZwaarte.SYSTEEM, fout.getZwaarte());
        assertEquals("Test", fout.getMelding());

        fout =
            new BerichtVerwerkingsFout(BerichtVerwerkingsFoutCode.ONBEKENDE_FOUT, BerichtVerwerkingsFoutZwaarte.INFO,
                    "Test2", false);
        assertEquals(BerichtVerwerkingsFoutCode.ONBEKENDE_FOUT.getCode(), fout.getCode());
        assertEquals(BerichtVerwerkingsFoutZwaarte.INFO, fout.getZwaarte());
        assertEquals("Test2", fout.getMelding());
    }

    @Test
    public void testConstructorMetConcatenatie() {
        BerichtVerwerkingsFout fout;

        fout =
            new BerichtVerwerkingsFout(BerichtVerwerkingsFoutCode.ONBEKENDE_FOUT,
                    BerichtVerwerkingsFoutZwaarte.SYSTEEM, "Test", true);
        assertEquals(BerichtVerwerkingsFoutCode.ONBEKENDE_FOUT.getCode(), fout.getCode());
        assertEquals(BerichtVerwerkingsFoutZwaarte.SYSTEEM, fout.getZwaarte());
        assertEquals("Onbekende fout opgetreden: Test", fout.getMelding());
    }
}
