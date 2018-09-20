/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.util.common.logging;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FunctioneleMeldingTest {

    @Test
    public void testGetCode() throws Exception {
        assertEquals(FunctioneleMelding.VOISC_STARTEN_APPLICATIE.getCode(), "VOISC001");

        assertEquals(FunctioneleMelding.VOISC_ONGELDIGE_PARAMETERS.getCode(), "VOISC002");

        assertEquals(FunctioneleMelding.VOISC_CONFIGUREREN_SSL.getCode(), "VOISC003");

        assertEquals(FunctioneleMelding.VOISC_STARTEN_JOBS.getCode(), "VOISC004");

        assertEquals(FunctioneleMelding.VOISC_STARTEN_QUEUELISTENERS.getCode(), "VOISC005");

        assertEquals(FunctioneleMelding.VOISC_APPLICATIE_CORRECT_GESTART.getCode(), "VOISC006");

        assertEquals(FunctioneleMelding.VOISC_CONNECTIE_MAILBOX_TESTEN.getCode(), "VOISC007");

        assertEquals(FunctioneleMelding.VOISC_CONFIGURATIE_MAILBOX_TESTEN.getCode(), "VOISC008");

        assertEquals(FunctioneleMelding.VOISC_CONNECTIE_MAILBOX_NIET_GETEST.getCode(), "VOISC009");

        assertEquals(FunctioneleMelding.VOISC_CONFIGURATIE_MAILBOX_NIET_GETEST.getCode(), "VOISC010");

    }

    @Test
    public void testGetOmschrijving() throws Exception {
        assertEquals(FunctioneleMelding.VOISC_CONFIGURATIE_MAILBOX_NIET_GETEST.getOmschrijving(), "Testen configuratie mailbox overgeslagen.");
    }
}
