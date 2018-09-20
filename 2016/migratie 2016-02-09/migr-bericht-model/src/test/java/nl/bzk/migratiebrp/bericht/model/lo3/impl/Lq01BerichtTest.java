/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.impl;

import java.io.IOException;
import junit.framework.Assert;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.MessageIdGenerator;
import nl.bzk.migratiebrp.bericht.model.lo3.AbstractLo3BerichtTest;
import org.junit.Test;

public class Lq01BerichtTest extends AbstractLo3BerichtTest {

    private static final String A_NUMMER = "9876543210";
    private static final String BRON_GEMEENTE = "0600";
    private static final String DOEL_GEMEENTE = "0599";

    @Test(expected = IllegalStateException.class)
    public void testEmpty() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final Lq01Bericht bericht = new Lq01Bericht();
        bericht.setMessageId(MessageIdGenerator.generateId());
        testFormatAndParseBericht(bericht);
    }

    @Test
    public void test() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final Lq01Bericht bericht = new Lq01Bericht();
        bericht.setMessageId(MessageIdGenerator.generateId());
        bericht.setANummer(A_NUMMER);
        bericht.setBronGemeente(BRON_GEMEENTE);
        bericht.setDoelGemeente(DOEL_GEMEENTE);

        testFormatAndParseBericht(bericht);
    }

    @Test
    public void testCyclusEnGetters() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final Lq01Bericht bericht = new Lq01Bericht();
        bericht.setMessageId(MessageIdGenerator.generateId());
        bericht.setANummer(A_NUMMER);
        bericht.setBronGemeente(BRON_GEMEENTE);
        bericht.setDoelGemeente(DOEL_GEMEENTE);

        testFormatAndParseBericht(bericht);

        Assert.assertEquals(null, bericht.getStartCyclus());
        Assert.assertEquals(A_NUMMER, bericht.getANummer());

        final Lq01Bericht controleBericht = new Lq01Bericht();
        controleBericht.setANummer(A_NUMMER);
        controleBericht.setBronGemeente(BRON_GEMEENTE);
        controleBericht.setDoelGemeente(DOEL_GEMEENTE);
        controleBericht.setMessageId(bericht.getMessageId());

        Assert.assertTrue(bericht.equals(bericht));
        Assert.assertTrue(controleBericht.equals(bericht));

        Assert.assertFalse(bericht.equals(new Lf01Bericht()));
    }

}
