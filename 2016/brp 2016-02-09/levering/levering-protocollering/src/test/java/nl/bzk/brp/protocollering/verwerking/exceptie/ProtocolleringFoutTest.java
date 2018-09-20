/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.protocollering.verwerking.exceptie;

import org.junit.Assert;
import org.junit.Test;

public class ProtocolleringFoutTest {

    private static final String FOUT_TEKST = "test fout";

    @Test
    public void testProtocolleringFoutMetFoutMeldingEnOorzaak() {
        final String message = FOUT_TEKST;
        final Throwable oorzaak = new IllegalArgumentException();
        final ProtocolleringFout protocolleringFout = new ProtocolleringFout(message, oorzaak);

        Assert.assertEquals(message, protocolleringFout.getMessage());
        Assert.assertEquals(oorzaak, protocolleringFout.getCause());
    }

    @Test
    public void testProtocolleringFoutMetFoutMelding() {
        final String message = FOUT_TEKST;
        final ProtocolleringFout protocolleringFout = new ProtocolleringFout(message);

        Assert.assertEquals(message, protocolleringFout.getMessage());
    }

    @Test
    public void testProtocolleringFoutMetOorzaak() {
        final Throwable oorzaak = new IllegalAccessException();
        final ProtocolleringFout protocolleringFout = new ProtocolleringFout(oorzaak);

        Assert.assertEquals(oorzaak, protocolleringFout.getCause());
    }
}
