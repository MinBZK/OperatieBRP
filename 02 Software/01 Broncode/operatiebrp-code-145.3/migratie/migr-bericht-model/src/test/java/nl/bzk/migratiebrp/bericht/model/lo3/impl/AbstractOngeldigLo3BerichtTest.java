/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import org.junit.Test;

public class AbstractOngeldigLo3BerichtTest {

    private static final String MESSAGE_ID = "b355916a-9fd0-422f-827a-b106fbd016dd";
    private static final String BERICHTINHOUD = "Inhoud van het bericht.";
    private static final String MELDING = "Testmelding";
    private static final String BRON_GEMEENTE = "1904";
    private static final String DOEL_GEMEENTE = "1905";
    private static final String HEADER = "Testheader";

    @Test
    public void testSyntax() {
        final AbstractOngeldigLo3Bericht abstractOngeldigLo3Bericht = new OngeldigeSyntaxBericht(BERICHTINHOUD, MELDING);
        abstractOngeldigLo3Bericht.setMessageId(MESSAGE_ID);
        abstractOngeldigLo3Bericht.setHeader(Lo3HeaderVeld.BERICHT, HEADER);
        abstractOngeldigLo3Bericht.setDoelPartijCode(DOEL_GEMEENTE);
        abstractOngeldigLo3Bericht.setBronPartijCode(BRON_GEMEENTE);

        abstractOngeldigLo3Bericht.parse(BERICHTINHOUD);
        assertEquals(BERICHTINHOUD, abstractOngeldigLo3Bericht.format());

        assertEquals("OngeldigBericht", abstractOngeldigLo3Bericht.getBerichtType());
        assertNull(abstractOngeldigLo3Bericht.getStartCyclus());
        assertNull(abstractOngeldigLo3Bericht.getHeaderWaarde(Lo3HeaderVeld.BERICHT));
        assertEquals(BRON_GEMEENTE, abstractOngeldigLo3Bericht.getBronPartijCode());
        assertEquals(DOEL_GEMEENTE, abstractOngeldigLo3Bericht.getDoelPartijCode());
        assertEquals(MELDING, abstractOngeldigLo3Bericht.getMelding());
        assertEquals(BERICHTINHOUD, abstractOngeldigLo3Bericht.getBericht());
    }

}
