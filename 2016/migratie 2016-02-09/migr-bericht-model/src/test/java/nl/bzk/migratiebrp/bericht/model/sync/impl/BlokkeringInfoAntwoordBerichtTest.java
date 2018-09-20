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
import nl.bzk.migratiebrp.bericht.model.sync.generated.BlokkeringInfoAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.PersoonsaanduidingType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

public class BlokkeringInfoAntwoordBerichtTest {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final String GEMEENTE_NAAR = "0599";
    private final SyncBerichtFactory factory = SyncBerichtFactory.SINGLETON;

    @Test
    public void testVertaal() throws IOException {
        final String berichtOrigineel = IOUtils.toString(BlokkeringInfoAntwoordBerichtTest.class.getResourceAsStream("blokkeringInfoAntwoordBericht.xml"));
        final SyncBericht bericht = factory.getBericht(berichtOrigineel);

        final BlokkeringInfoAntwoordBericht blokkeringInfoAntwoordBericht = (BlokkeringInfoAntwoordBericht) bericht;
        assertEquals(PersoonsaanduidingType.VERHUIZEND_VAN_LO_3_NAAR_BRP, blokkeringInfoAntwoordBericht.getPersoonsaanduiding());
        assertEquals("1234567890", blokkeringInfoAntwoordBericht.getProcessId());
        assertEquals(StatusType.OK, blokkeringInfoAntwoordBericht.getStatus());
        assertEquals(null, blokkeringInfoAntwoordBericht.getStartCyclus());
    }

    @Test
    public void testFormat() throws BerichtInhoudException {
        final BlokkeringInfoAntwoordType blokkeringInfoAntwoordType = new BlokkeringInfoAntwoordType();
        blokkeringInfoAntwoordType.setStatus(StatusType.FOUT);
        blokkeringInfoAntwoordType.setGemeenteNaar(GEMEENTE_NAAR);
        final BlokkeringInfoAntwoordBericht blokkeringBericht = new BlokkeringInfoAntwoordBericht(blokkeringInfoAntwoordType);

        LOG.info("Geformat: {}", blokkeringBericht.format());
        assertEquals("BlokkeringInfoAntwoord", blokkeringBericht.getBerichtType());
        assertEquals(StatusType.FOUT, blokkeringBericht.getStatus());
        assertEquals(GEMEENTE_NAAR, blokkeringBericht.getGemeenteNaar());
    }

    @Test
    public void testFoutBericht() throws BerichtInhoudException {
        final BlokkeringInfoAntwoordBericht blokkeringBericht = new BlokkeringInfoAntwoordBericht();
        blokkeringBericht.setStatus(StatusType.FOUT);
        blokkeringBericht.setGemeenteNaar(GEMEENTE_NAAR);

        LOG.info("Geformat: {}", blokkeringBericht.format());
        assertEquals("BlokkeringInfoAntwoord", blokkeringBericht.getBerichtType());
        assertEquals(StatusType.FOUT, blokkeringBericht.getStatus());
        assertEquals(GEMEENTE_NAAR, blokkeringBericht.getGemeenteNaar());
    }

    @Test
    public void testEquals() {
        final BlokkeringInfoAntwoordType blokkeringInfoAntwoordType = new BlokkeringInfoAntwoordType();
        blokkeringInfoAntwoordType.setStatus(StatusType.OK);
        final BlokkeringInfoAntwoordBericht blokkeringInfoAntwoordBerichtOrigineel = new BlokkeringInfoAntwoordBericht(blokkeringInfoAntwoordType);
        blokkeringInfoAntwoordBerichtOrigineel.setMessageId(MessageIdGenerator.generateId());
        final BlokkeringInfoAntwoordBericht blokkeringInfoAntwoordBerichtKopie = new BlokkeringInfoAntwoordBericht(blokkeringInfoAntwoordType);
        final BlokkeringInfoAntwoordBericht blokkeringInfoAntwoordBerichtObjectKopie = blokkeringInfoAntwoordBerichtOrigineel;
        final DeblokkeringAntwoordBericht deblokkeringAntwoordBericht = new DeblokkeringAntwoordBericht();

        blokkeringInfoAntwoordBerichtKopie.setMessageId(blokkeringInfoAntwoordBerichtOrigineel.getMessageId());
        blokkeringInfoAntwoordBerichtKopie.setCorrelationId(blokkeringInfoAntwoordBerichtOrigineel.getCorrelationId());

        assertTrue(blokkeringInfoAntwoordBerichtObjectKopie.equals(blokkeringInfoAntwoordBerichtOrigineel));
        assertFalse(blokkeringInfoAntwoordBerichtOrigineel.equals(deblokkeringAntwoordBericht));
        assertTrue(blokkeringInfoAntwoordBerichtKopie.equals(blokkeringInfoAntwoordBerichtOrigineel));
        assertEquals(blokkeringInfoAntwoordBerichtObjectKopie.hashCode(), blokkeringInfoAntwoordBerichtOrigineel.hashCode());
        assertEquals(blokkeringInfoAntwoordBerichtKopie.hashCode(), blokkeringInfoAntwoordBerichtOrigineel.hashCode());
        assertEquals(blokkeringInfoAntwoordBerichtKopie.toString(), blokkeringInfoAntwoordBerichtOrigineel.toString());
    }

    @Test
    public void testSettersEnGetters() {
        final BlokkeringInfoAntwoordBericht blokkeringInfoAntwoordBericht = new BlokkeringInfoAntwoordBericht();
        final String PROCESS_ID = "123456";
        blokkeringInfoAntwoordBericht.setStatus(StatusType.FOUT);
        blokkeringInfoAntwoordBericht.setPersoonsaanduiding(PersoonsaanduidingType.VERHUIZEND_VAN_BRP_NAAR_LO_3_GBA);
        blokkeringInfoAntwoordBericht.setProcessId(PROCESS_ID);

        assertEquals(StatusType.FOUT, blokkeringInfoAntwoordBericht.getStatus());
        assertEquals(PersoonsaanduidingType.VERHUIZEND_VAN_BRP_NAAR_LO_3_GBA, blokkeringInfoAntwoordBericht.getPersoonsaanduiding());
        assertEquals(PROCESS_ID, blokkeringInfoAntwoordBericht.getProcessId());
    }

    @Test
    public void testBerichtSyntaxException() throws IOException {
        final String berichtOrigineel =
                IOUtils.toString(BlokkeringInfoAntwoordBerichtTest.class.getResourceAsStream("blokkeringInfoAntwoordBerichtSyntaxExceptionBericht.xml"));

        final SyncBericht syncBericht = factory.getBericht(berichtOrigineel);
        Assert.assertTrue(syncBericht instanceof OngeldigBericht);
    }

}
