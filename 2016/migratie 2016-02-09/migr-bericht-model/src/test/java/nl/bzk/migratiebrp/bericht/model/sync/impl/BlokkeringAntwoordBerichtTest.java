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
import nl.bzk.migratiebrp.bericht.model.sync.generated.BlokkeringAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.PersoonsaanduidingType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

public class BlokkeringAntwoordBerichtTest {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final String PROCES_ID = "1234";
    private static final String GEMEENTE_NAAR = "0600";

    private final SyncBerichtFactory factory = SyncBerichtFactory.SINGLETON;

    @Test
    public void testVertaal() throws IOException {
        final String berichtOrigineel = IOUtils.toString(BlokkeringAntwoordBerichtTest.class.getResourceAsStream("blokkeringAntwoordBericht.xml"));
        final SyncBericht bericht = factory.getBericht(berichtOrigineel);

        final BlokkeringAntwoordBericht blokkeringAntwoordBericht = (BlokkeringAntwoordBericht) bericht;
        assertEquals("BlokkeringAntwoord", blokkeringAntwoordBericht.getBerichtType());
        assertEquals(StatusType.OK, blokkeringAntwoordBericht.getStatus());
        assertEquals(PersoonsaanduidingType.VERHUIZEND_VAN_LO_3_NAAR_BRP, blokkeringAntwoordBericht.getPersoonsaanduiding());
        assertEquals(null, blokkeringAntwoordBericht.getStartCyclus());
    }

    @Test
    public void testFormat() throws BerichtInhoudException {
        final BlokkeringAntwoordType blokkeringAntwoordType = new BlokkeringAntwoordType();
        blokkeringAntwoordType.setStatus(StatusType.FOUT);
        final BlokkeringAntwoordBericht blokkeringBericht = new BlokkeringAntwoordBericht(blokkeringAntwoordType);

        LOG.info("Geformat: {}", blokkeringBericht.format());
        assertEquals("BlokkeringAntwoord", blokkeringBericht.getBerichtType());
        assertEquals(StatusType.FOUT, blokkeringBericht.getStatus());
    }

    @Test
    public void testFoutBericht() throws BerichtInhoudException {
        final BlokkeringAntwoordBericht blokkeringBericht = new BlokkeringAntwoordBericht();
        blokkeringBericht.setStatus(StatusType.FOUT);
        blokkeringBericht.setGemeenteNAar(GEMEENTE_NAAR);
        blokkeringBericht.setProcessId(PROCES_ID);
        blokkeringBericht.setToelichting(PersoonsaanduidingType.VERHUIZEND_VAN_BRP_NAAR_LO_3_RNI);

        LOG.info("Geformat: {}", blokkeringBericht.format());
        assertEquals("BlokkeringAntwoord", blokkeringBericht.getBerichtType());
        assertEquals(StatusType.FOUT, blokkeringBericht.getStatus());
        assertEquals(GEMEENTE_NAAR, blokkeringBericht.getGemeenteNaar());
        assertEquals(PROCES_ID, blokkeringBericht.getProcessId());
        assertEquals(PersoonsaanduidingType.VERHUIZEND_VAN_BRP_NAAR_LO_3_RNI, blokkeringBericht.getPersoonsaanduiding());
    }

    @Test
    public void testEquals() {
        final BlokkeringAntwoordType blokkeringAntwoordType = new BlokkeringAntwoordType();
        blokkeringAntwoordType.setStatus(StatusType.OK);
        final BlokkeringAntwoordBericht blokkeringAntwoordBerichtOrigineel = new BlokkeringAntwoordBericht(blokkeringAntwoordType);
        blokkeringAntwoordBerichtOrigineel.setMessageId(MessageIdGenerator.generateId());

        final BlokkeringAntwoordBericht blokkeringAntwoordBerichtKopie = new BlokkeringAntwoordBericht(blokkeringAntwoordType);
        final BlokkeringAntwoordBericht blokkeringAntwoordBerichtObjectKopie = blokkeringAntwoordBerichtOrigineel;
        final DeblokkeringAntwoordBericht deblokkeringAntwoordBericht = new DeblokkeringAntwoordBericht();

        blokkeringAntwoordBerichtKopie.setMessageId(blokkeringAntwoordBerichtOrigineel.getMessageId());
        blokkeringAntwoordBerichtKopie.setCorrelationId(blokkeringAntwoordBerichtOrigineel.getCorrelationId());

        assertTrue(blokkeringAntwoordBerichtObjectKopie.equals(blokkeringAntwoordBerichtOrigineel));
        assertFalse(blokkeringAntwoordBerichtOrigineel.equals(deblokkeringAntwoordBericht));
        assertTrue(blokkeringAntwoordBerichtKopie.equals(blokkeringAntwoordBerichtOrigineel));
        assertEquals(blokkeringAntwoordBerichtObjectKopie.hashCode(), blokkeringAntwoordBerichtOrigineel.hashCode());
        assertEquals(blokkeringAntwoordBerichtKopie.hashCode(), blokkeringAntwoordBerichtOrigineel.hashCode());
        assertEquals(blokkeringAntwoordBerichtKopie.toString(), blokkeringAntwoordBerichtOrigineel.toString());
    }

    @Test
    public void testBerichtSyntaxException() throws IOException {
        final String berichtOrigineel =
                IOUtils.toString(BlokkeringAntwoordBerichtTest.class.getResourceAsStream("blokkeringAntwoordBerichtSyntaxExceptionBericht.xml"));

        final SyncBericht syncBericht = factory.getBericht(berichtOrigineel);
        Assert.assertTrue(syncBericht instanceof OngeldigBericht);
    }

}
