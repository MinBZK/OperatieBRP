/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.impl;

import java.io.IOException;
import org.junit.Assert;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.MessageIdGenerator;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import org.junit.Test;

public class NullBerichtTest {

    private static final String BRON_GEMEENTE = "1905";
    private static final String DOEL_GEMEENTE = "1904";
    private static final String CORRELATION_ID = "123484";
    private static final String MESSAGE_ID = "86451523";

    @Test
    public void testVertaal() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final NullBericht nullBericht = new NullBericht(CORRELATION_ID);
        nullBericht.parse("");
        Assert.assertEquals(CORRELATION_ID, nullBericht.getCorrelationId());
        Assert.assertEquals("", nullBericht.format());
        Assert.assertEquals("Null", nullBericht.getBerichtType());
        Assert.assertEquals(null, nullBericht.getStartCyclus());
    }

    @Test
    public void testGetterEnSetters() {
        final NullBericht nullBericht = new NullBericht();
        nullBericht.setBronPartijCode(BRON_GEMEENTE);
        nullBericht.setDoelPartijCode(DOEL_GEMEENTE);
        nullBericht.setCorrelationId(CORRELATION_ID);
        nullBericht.setMessageId(MESSAGE_ID);
        Assert.assertEquals(CORRELATION_ID, nullBericht.getCorrelationId());
        Assert.assertEquals(MESSAGE_ID, nullBericht.getMessageId());
        Assert.assertEquals(BRON_GEMEENTE, nullBericht.getBronPartijCode());
        Assert.assertEquals(DOEL_GEMEENTE, nullBericht.getDoelPartijCode());
        Assert.assertEquals(null, nullBericht.getHeaderWaarde(Lo3HeaderVeld.GEMEENTE));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetHeader() {
        final NullBericht nullBericht = new NullBericht();
        nullBericht.setHeader(Lo3HeaderVeld.GEMEENTE, BRON_GEMEENTE);
    }

    @Test
    public void testVergelijken() {
        final NullBericht nullBerichtOrigineel = new NullBericht(CORRELATION_ID);
        nullBerichtOrigineel.setMessageId(MessageIdGenerator.generateId());

        final NullBericht nullBerichtKopie = new NullBericht(CORRELATION_ID);
        final NullBericht nullBerichtObjectKopie = nullBerichtOrigineel;
        nullBerichtKopie.setMessageId(nullBerichtOrigineel.getMessageId());

        Assert.assertTrue(nullBerichtOrigineel.equals(nullBerichtKopie));
        Assert.assertTrue(nullBerichtOrigineel.equals(nullBerichtObjectKopie));
        Assert.assertFalse(nullBerichtOrigineel.equals(Integer.valueOf(1)));
        Assert.assertEquals(nullBerichtOrigineel.hashCode(), nullBerichtKopie.hashCode());
        Assert.assertEquals(nullBerichtOrigineel.toString(), nullBerichtKopie.toString());
    }
}
