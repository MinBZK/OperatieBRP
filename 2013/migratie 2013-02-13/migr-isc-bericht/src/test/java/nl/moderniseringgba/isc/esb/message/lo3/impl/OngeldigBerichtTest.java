/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.lo3.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3HeaderVeld;

import org.junit.Test;

public class OngeldigBerichtTest {

    private static final String MESSAGE_ID = "b355916a-9fd0-422f-827a-b106fbd016dd";
    private static final String BERICHTINHOUD = "Inhoud van het bericht.";
    private static final String MELDING = "Testmelding";
    private static final String BRON_GEMEENTE = "1904";
    private static final String DOEL_GEMEENTE = "1905";
    private static final String HEADER = "Testheader";
    private static final String STRING_REPRESENTATIE_BERICHT = "OngeldigBericht[messageId=" + MESSAGE_ID
            + ",correlationId=<null>,bericht=Inhoud van het bericht.,melding=Testmelding]";

    @Test
    public void testVertaal() throws Exception {
        final OngeldigBericht ongeldigBericht = new OngeldigBericht(BERICHTINHOUD, MELDING);
        ongeldigBericht.setMessageId(MESSAGE_ID);
        ongeldigBericht.setHeader(Lo3HeaderVeld.BERICHT, HEADER);
        ongeldigBericht.setDoelGemeente(DOEL_GEMEENTE);
        ongeldigBericht.setBronGemeente(BRON_GEMEENTE);

        ongeldigBericht.parse(BERICHTINHOUD);
        assertNull(ongeldigBericht.format());
        assertEquals(STRING_REPRESENTATIE_BERICHT, ongeldigBericht.toString());

        assertEquals("OngeldigBericht", ongeldigBericht.getBerichtType());
        assertNull(ongeldigBericht.getStartCyclus());
        assertNull(ongeldigBericht.getHeader(Lo3HeaderVeld.BERICHT));
        assertEquals(BRON_GEMEENTE, ongeldigBericht.getBronGemeente());
        assertEquals(DOEL_GEMEENTE, ongeldigBericht.getDoelGemeente());
        assertEquals(MELDING, ongeldigBericht.getMelding());
        assertEquals(BERICHTINHOUD, ongeldigBericht.getBericht());
    }
}
