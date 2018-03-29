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
import nl.bzk.migratiebrp.bericht.model.sync.generated.DeblokkeringAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

public class DeblokkeringAntwoordBerichtTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final SyncBerichtFactory factory = SyncBerichtFactory.SINGLETON;

    @Test
    public void testVertaal() throws IOException {
        final String berichtOrigineel =
                IOUtils.toString(DeblokkeringAntwoordBerichtTest.class.getResourceAsStream("deblokkeringAntwoordBericht.xml"));
        final SyncBericht bericht = factory.getBericht(berichtOrigineel);

        final DeblokkeringAntwoordBericht deblokkeringAntwoordBericht = (DeblokkeringAntwoordBericht) bericht;
        assertEquals("DeblokkeringAntwoord", deblokkeringAntwoordBericht.getBerichtType());
        assertEquals(StatusType.OK, deblokkeringAntwoordBericht.getStatus());
        assertEquals(null, deblokkeringAntwoordBericht.getStartCyclus());

    }

    @Test
    public void testFormat() throws BerichtInhoudException {
        final DeblokkeringAntwoordType deblokkeringAntwoordType = new DeblokkeringAntwoordType();
        deblokkeringAntwoordType.setStatus(StatusType.FOUT);
        final DeblokkeringAntwoordBericht deblokkeringAntwoordBericht = new DeblokkeringAntwoordBericht(deblokkeringAntwoordType);

        LOG.info("Geformat: {}", deblokkeringAntwoordBericht.format());
        assertEquals("DeblokkeringAntwoord", deblokkeringAntwoordBericht.getBerichtType());
        assertEquals(StatusType.FOUT, deblokkeringAntwoordBericht.getStatus());
    }

    @Test
    public void testEquals() {
        final DeblokkeringAntwoordType deblokkeringAntwoordType = new DeblokkeringAntwoordType();
        deblokkeringAntwoordType.setStatus(StatusType.OK);
        final DeblokkeringAntwoordBericht deblokkeringAntwoordBerichtOrigineel = new DeblokkeringAntwoordBericht(deblokkeringAntwoordType);
        deblokkeringAntwoordBerichtOrigineel.setMessageId(MessageIdGenerator.generateId());
        final DeblokkeringAntwoordBericht deblokkeringAntwoordBerichtKopie = new DeblokkeringAntwoordBericht(deblokkeringAntwoordType);
        final DeblokkeringAntwoordBericht deblokkeringAntwoordBerichtObjectKopie = deblokkeringAntwoordBerichtOrigineel;
        final BlokkeringAntwoordBericht blokkeringAntwoordBericht = new BlokkeringAntwoordBericht();

        deblokkeringAntwoordBerichtKopie.setMessageId(deblokkeringAntwoordBerichtOrigineel.getMessageId());
        deblokkeringAntwoordBerichtKopie.setCorrelationId(deblokkeringAntwoordBerichtOrigineel.getCorrelationId());

        assertTrue(deblokkeringAntwoordBerichtObjectKopie.equals(deblokkeringAntwoordBerichtOrigineel));
        assertFalse(deblokkeringAntwoordBerichtOrigineel.equals(blokkeringAntwoordBericht));
        assertTrue(deblokkeringAntwoordBerichtKopie.equals(deblokkeringAntwoordBerichtOrigineel));
        assertEquals(deblokkeringAntwoordBerichtObjectKopie.hashCode(), deblokkeringAntwoordBerichtOrigineel.hashCode());
        assertEquals(deblokkeringAntwoordBerichtKopie.hashCode(), deblokkeringAntwoordBerichtOrigineel.hashCode());
        assertEquals(deblokkeringAntwoordBerichtKopie.toString(), deblokkeringAntwoordBerichtOrigineel.toString());
    }

    @Test
    public void testFoutBericht() throws BerichtInhoudException {
        final DeblokkeringAntwoordBericht deblokkeringAntwoordBericht = new DeblokkeringAntwoordBericht();
        deblokkeringAntwoordBericht.setStatus(StatusType.FOUT);

        LOG.info("Geformat: {}", deblokkeringAntwoordBericht.format());
        assertEquals("DeblokkeringAntwoord", deblokkeringAntwoordBericht.getBerichtType());
        assertEquals(StatusType.FOUT, deblokkeringAntwoordBericht.getStatus());
    }

    @Test
    public void testBerichtSyntaxException() throws IOException {
        final String berichtOrigineel =
                IOUtils.toString(DeblokkeringAntwoordBerichtTest.class.getResourceAsStream("deblokkeringAntwoordBerichtSyntaxExceptionBericht.xml"));

        final SyncBericht syncBericht = factory.getBericht(berichtOrigineel);
        Assert.assertTrue(syncBericht instanceof OngeldigBericht);
    }

}
