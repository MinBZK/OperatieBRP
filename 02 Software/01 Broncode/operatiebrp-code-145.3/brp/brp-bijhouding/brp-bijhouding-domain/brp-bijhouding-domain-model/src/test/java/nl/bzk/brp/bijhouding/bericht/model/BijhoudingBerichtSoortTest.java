/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

/**
 * Testen voor {@link BijhoudingBerichtSoort}.
 */
public class BijhoudingBerichtSoortTest {

    @Test
    public void getAntwoordBijhoudingBerichtSoort() {
        assertEquals(
            BijhoudingBerichtSoort.REGISTREER_HUWELIJK_GP_ANTWOORD,
            BijhoudingBerichtSoort.REGISTREER_HUWELIJK_GP.getAntwoordBijhoudingBerichtSoort());
        assertEquals(
            BijhoudingBerichtSoort.CORRIGEER_HUWELIJK_GP_ANTWOORD,
            BijhoudingBerichtSoort.CORRIGEER_HUWELIJK_GP.getAntwoordBijhoudingBerichtSoort());
        assertEquals(
            BijhoudingBerichtSoort.ISC_REGISTREER_HUWELIJK_GP_ANTWOORD,
            BijhoudingBerichtSoort.ISC_REGISTREER_HUWELIJK_GP.getAntwoordBijhoudingBerichtSoort());
        assertEquals(
            BijhoudingBerichtSoort.ISC_REGISTREER_VERHUIZING_ANTWOORD,
            BijhoudingBerichtSoort.ISC_REGISTREER_VERHUIZING.getAntwoordBijhoudingBerichtSoort());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAntwoordBijhoudingBerichtSoortFout() {
        BijhoudingBerichtSoort.REGISTREER_HUWELIJK_GP_ANTWOORD.getAntwoordBijhoudingBerichtSoort();
    }

    @Test
    public void parseElementNaam() throws OngeldigeWaardeException {
        assertEquals(BijhoudingBerichtSoort.CORRIGEER_HUWELIJK_GP,
                BijhoudingBerichtSoort.parseElementNaam(BijhoudingBerichtSoort.CORRIGEER_HUWELIJK_GP.getElementNaam()));
    }

    @Test(expected = OngeldigeWaardeException.class)
    public void parseElementnaamFout() throws OngeldigeWaardeException {
        assertEquals(BijhoudingBerichtSoort.CORRIGEER_HUWELIJK_GP,
                BijhoudingBerichtSoort.parseElementNaam(BijhoudingBerichtSoort.CORRIGEER_HUWELIJK_GP_ANTWOORD.name()));
    }

    @Test
    public void getElementNamen(){
        assertFalse(BijhoudingBerichtSoort.getElementNamen().isEmpty());
    }

}
