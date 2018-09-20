/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.MessageIdGenerator;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.factory.SyncBerichtFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.BlokkeringInfoVerzoekType;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

public class BlokkeringInfoVerzoekBerichtTest {

    private static final String A_NUMMER = "1234567890";

    private static final Logger LOG = LoggerFactory.getLogger();

    private final SyncBerichtFactory factory = SyncBerichtFactory.SINGLETON;

    @Test
    public void testVertaal() throws IOException {
        final String berichtOrigineel =
                IOUtils.toString(BlokkeringInfoVerzoekBerichtTest.class.getResourceAsStream("blokkeringInfoVerzoekBericht.xml"));
        final SyncBericht bericht = factory.getBericht(berichtOrigineel);

        final BlokkeringInfoVerzoekBericht blokkeringInfoVerzoekBericht = (BlokkeringInfoVerzoekBericht) bericht;
        assertEquals(A_NUMMER, blokkeringInfoVerzoekBericht.getANummer());
        assertEquals("BlokkeringInfoVerzoek", blokkeringInfoVerzoekBericht.getBerichtType());
        assertEquals(null, blokkeringInfoVerzoekBericht.getStartCyclus());
    }

    @Test
    public void testFormat() throws BerichtInhoudException {
        final BlokkeringInfoVerzoekType blokkeringInfoVerzoekType = new BlokkeringInfoVerzoekType();
        blokkeringInfoVerzoekType.setANummer(A_NUMMER);

        final BlokkeringInfoVerzoekBericht blokkeringInfoVerzoekBericht = new BlokkeringInfoVerzoekBericht(blokkeringInfoVerzoekType);
        final String geformat = blokkeringInfoVerzoekBericht.format();

        LOG.info("Geformat: {}", geformat);
        // controleer of geformat bericht weer geparsed kan worden
        final BlokkeringInfoVerzoekBericht format = (BlokkeringInfoVerzoekBericht) factory.getBericht(geformat);
        assertEquals("BlokkeringInfoVerzoek", format.getBerichtType());
    }

    @Test
    public void testEquals() {
        final BlokkeringInfoVerzoekType blokkeringInfoVerzoekType = new BlokkeringInfoVerzoekType();
        blokkeringInfoVerzoekType.setANummer(A_NUMMER);
        final BlokkeringInfoVerzoekBericht blokkeringInfoVerzoekBerichtOrigineel =
                new BlokkeringInfoVerzoekBericht(blokkeringInfoVerzoekType);
        blokkeringInfoVerzoekBerichtOrigineel.setMessageId(MessageIdGenerator.generateId());

        final BlokkeringInfoVerzoekBericht blokkeringInfoVerzoekBerichtKopie = new BlokkeringInfoVerzoekBericht(blokkeringInfoVerzoekType);
        final BlokkeringInfoVerzoekBericht blokkeringInfoVerzoekBerichtObjectKopie = blokkeringInfoVerzoekBerichtOrigineel;
        final BlokkeringInfoAntwoordBericht blokkeringInfoAntwoordBericht = new BlokkeringInfoAntwoordBericht();

        blokkeringInfoVerzoekBerichtKopie.setMessageId(blokkeringInfoVerzoekBerichtOrigineel.getMessageId());
        blokkeringInfoVerzoekBerichtKopie.setCorrelationId(blokkeringInfoVerzoekBerichtOrigineel.getCorrelationId());

        assertTrue(blokkeringInfoVerzoekBerichtObjectKopie.equals(blokkeringInfoVerzoekBerichtOrigineel));
        assertFalse(blokkeringInfoVerzoekBerichtOrigineel.equals(blokkeringInfoAntwoordBericht));
        assertTrue(blokkeringInfoVerzoekBerichtKopie.equals(blokkeringInfoVerzoekBerichtOrigineel));
        assertEquals(blokkeringInfoVerzoekBerichtObjectKopie.hashCode(), blokkeringInfoVerzoekBerichtOrigineel.hashCode());
        assertEquals(blokkeringInfoVerzoekBerichtKopie.hashCode(), blokkeringInfoVerzoekBerichtOrigineel.hashCode());
        assertEquals(blokkeringInfoVerzoekBerichtKopie.toString(), blokkeringInfoVerzoekBerichtOrigineel.toString());
    }

    @Test
    public void testSettersEnGetters() {
        final BlokkeringInfoVerzoekBericht blokkeringInfoVerzoekBericht = new BlokkeringInfoVerzoekBericht();
        final String ANUMMER = A_NUMMER;
        blokkeringInfoVerzoekBericht.setANummer(ANUMMER);

        assertEquals(ANUMMER, blokkeringInfoVerzoekBericht.getANummer());
    }

    @Test
    public void tesBerichtSyntaxException() throws IOException {
        final String berichtOrigineel =
                IOUtils.toString(BlokkeringInfoVerzoekBerichtTest.class.getResourceAsStream("blokkeringInfoVerzoekBerichtSyntaxExceptionBericht.xml"));

        final SyncBericht syncBericht = factory.getBericht(berichtOrigineel);
        Assert.assertTrue(syncBericht instanceof OngeldigBericht);
    }

}
