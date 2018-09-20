/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.impl;

import java.io.IOException;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.MessageIdGenerator;
import nl.bzk.migratiebrp.bericht.model.lo3.AbstractLo3BerichtTest;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import org.junit.Assert;
import org.junit.Test;

public class Av01BerichtTest extends AbstractLo3BerichtTest {

    private static final String A_NUMMER = "9876543210";
    private static final String BRON_GEMEENTE = "0600";
    private static final String DOEL_GEMEENTE = "0600";

    @Test
    public void testEmpty() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final String av01 = "00000000Av01000000";
        final Lo3Bericht parsed = getFactory().getBericht(av01);
        Assert.assertEquals(OngeldigeInhoudBericht.class, parsed.getClass());
    }

    @Test
    public void testCyclusEnGetters() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final Av01Bericht av01Bericht = new Av01Bericht();
        av01Bericht.setANummer(A_NUMMER);
        av01Bericht.setBronGemeente(BRON_GEMEENTE);
        av01Bericht.setDoelGemeente(DOEL_GEMEENTE);
        av01Bericht.setMessageId(MessageIdGenerator.generateId());
        testFormatAndParseBericht(av01Bericht);

        Assert.assertNotNull(av01Bericht.getStartCyclus());
        Assert.assertEquals("uc1003-verwijderen", av01Bericht.getStartCyclus());
        Assert.assertEquals(A_NUMMER, av01Bericht.getANummer());

        final Av01Bericht controleBericht = new Av01Bericht();
        controleBericht.setANummer(A_NUMMER);
        controleBericht.setBronGemeente(BRON_GEMEENTE);
        controleBericht.setDoelGemeente(DOEL_GEMEENTE);
        controleBericht.setMessageId(av01Bericht.getMessageId());

        Assert.assertEquals(av01Bericht.hashCode(), controleBericht.hashCode());
        Assert.assertEquals(av01Bericht.toString(), controleBericht.toString());
        Assert.assertTrue(av01Bericht.equals(av01Bericht));
        Assert.assertTrue(controleBericht.equals(av01Bericht));
        Assert.assertNull(av01Bericht.getGerelateerdeAnummers());

        Assert.assertFalse(av01Bericht.equals(new Lq01Bericht()));
    }
}
