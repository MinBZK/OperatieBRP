/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.lo3.AbstractLo3BerichtTestBasis;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class OnbekendBerichtTest extends AbstractLo3BerichtTestBasis {

    private static final String BRON_GEMEENTE = "1905";
    private static final String DOEL_GEMEENTE = "1904";
    private static final String CORRELATION_ID = "123484";
    private static final String MESSAGE_ID = "86451523";

    @Test
    public void testVertaal() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final String av01 = "00000000Av01000000";
        final OnbekendBericht onbekendBericht = new OnbekendBericht(av01, "deze is leeg...");
        onbekendBericht.parse("");
        Assert.assertEquals(av01, onbekendBericht.format());
        Assert.assertEquals("OnbekendBericht", onbekendBericht.getBerichtType());
        Assert.assertEquals(null, onbekendBericht.getStartCyclus());
    }

    @Test
    public void testSafeVertaal() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final String av01 = "00000000Av01000000";
        final Lo3Bericht lo3Bericht = getFactory().getBericht(av01);
        final OnbekendBericht onbekendBericht = new OnbekendBericht(lo3Bericht);
        onbekendBericht.parse("");
        Assert.assertEquals(av01, onbekendBericht.format());
        Assert.assertEquals("OnbekendBericht", onbekendBericht.getBerichtType());
        Assert.assertEquals(null, onbekendBericht.getStartCyclus());
    }

    @Test
    public void testBerichtInhoudException() throws BerichtInhoudException {
        final Lo3Bericht lo3Bericht = Mockito.mock(Lo3Bericht.class);
        Mockito.when(lo3Bericht.format()).thenThrow(new BerichtInhoudException("Stuk"));
        final OnbekendBericht onbekendBericht = new OnbekendBericht(lo3Bericht);
        Assert.assertNull(onbekendBericht.format());
    }

    @Test
    public void testGetterEnSetters() throws IOException, BerichtInhoudException, ClassNotFoundException {
        final String av01 = "00000000Av01000000";
        final Lo3Bericht lo3Bericht = getFactory().getBericht(av01);
        final OnbekendBericht onbekendBericht = new OnbekendBericht(lo3Bericht);
        onbekendBericht.setBronPartijCode(BRON_GEMEENTE);
        onbekendBericht.setDoelPartijCode(DOEL_GEMEENTE);
        onbekendBericht.setCorrelationId(CORRELATION_ID);
        onbekendBericht.setMessageId(MESSAGE_ID);
        Assert.assertEquals(CORRELATION_ID, onbekendBericht.getCorrelationId());
        Assert.assertEquals(MESSAGE_ID, onbekendBericht.getMessageId());
        Assert.assertEquals(BRON_GEMEENTE, onbekendBericht.getBronPartijCode());
        Assert.assertEquals(DOEL_GEMEENTE, onbekendBericht.getDoelPartijCode());
        Assert.assertNull(onbekendBericht.getHeaderWaarde(Lo3HeaderVeld.GEMEENTE));
        Assert.assertNull(onbekendBericht.getHeader());
        Assert.assertEquals(Collections.emptyList(), onbekendBericht.getHeaderWaarden(Lo3HeaderVeld.BERICHT));
        Assert.assertEquals(Collections.emptyList(), onbekendBericht.getGerelateerdeInformatie().getaNummers());
        Assert.assertEquals(Arrays.asList(BRON_GEMEENTE, DOEL_GEMEENTE), onbekendBericht.getGerelateerdeInformatie().getPartijen());
        Assert.assertEquals(Collections.emptyList(), onbekendBericht.getGerelateerdeInformatie().getAdministratieveHandelingIds());
    }
}
