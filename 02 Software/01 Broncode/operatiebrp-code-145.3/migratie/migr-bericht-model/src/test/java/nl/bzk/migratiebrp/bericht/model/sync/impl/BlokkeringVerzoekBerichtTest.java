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
import nl.bzk.migratiebrp.bericht.model.sync.generated.BlokkeringVerzoekType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.PersoonsaanduidingType;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

public class BlokkeringVerzoekBerichtTest {

    private static final String A_NUMMER = "1234567890";
    private static final String GEMEENTE_NAAR = "190501";
    private static final String GEMEENTE_REGISTRATIE = "190401";

    private static final Logger LOG = LoggerFactory.getLogger();

    private final SyncBerichtFactory factory = SyncBerichtFactory.SINGLETON;

    @Test
    public void testVertaal() throws IOException {
        final String berichtOrigineel = IOUtils.toString(BlokkeringVerzoekBerichtTest.class.getResourceAsStream("blokkeringVerzoekBericht.xml"));
        final SyncBericht bericht = factory.getBericht(berichtOrigineel);

        final BlokkeringVerzoekBericht blokkeringBericht = (BlokkeringVerzoekBericht) bericht;
        assertEquals(A_NUMMER, blokkeringBericht.getANummer());
        assertEquals(null, blokkeringBericht.getStartCyclus());
    }

    @Test
    public void testFormat() throws BerichtInhoudException {
        final BlokkeringVerzoekType blokkeringVerzoekType = new BlokkeringVerzoekType();
        blokkeringVerzoekType.setANummer(A_NUMMER);
        blokkeringVerzoekType.setProcessId("1232");
        blokkeringVerzoekType.setGemeenteNaar(GEMEENTE_NAAR);
        blokkeringVerzoekType.setGemeenteRegistratie(GEMEENTE_REGISTRATIE);
        blokkeringVerzoekType.setPersoonsaanduiding(PersoonsaanduidingType.VERHUIZEND_VAN_BRP_NAAR_LO_3_GBA);

        final BlokkeringVerzoekBericht blokkeringBericht = new BlokkeringVerzoekBericht(blokkeringVerzoekType);
        final String geformat = blokkeringBericht.format();

        LOG.info("Geformat: {}", geformat);
        // controleer of geformat bericht weer geparsed kan worden
        final BlokkeringVerzoekBericht format = (BlokkeringVerzoekBericht) factory.getBericht(geformat);
        assertEquals("BlokkeringVerzoek", format.getBerichtType());
    }

    @Test
    public void testEquals() {
        final BlokkeringVerzoekType blokkeringVerzoekType = new BlokkeringVerzoekType();
        blokkeringVerzoekType.setANummer(A_NUMMER);
        blokkeringVerzoekType.setProcessId("1232");
        blokkeringVerzoekType.setPersoonsaanduiding(PersoonsaanduidingType.VERHUIZEND_VAN_BRP_NAAR_LO_3_GBA);

        final BlokkeringVerzoekBericht blokkeringVerzoekBerichtOrigineel = new BlokkeringVerzoekBericht(blokkeringVerzoekType);
        blokkeringVerzoekBerichtOrigineel.setMessageId(MessageIdGenerator.generateId());

        final BlokkeringVerzoekBericht blokkeringVerzoekBerichtKopie = new BlokkeringVerzoekBericht(blokkeringVerzoekType);
        final BlokkeringVerzoekBericht blokkeringVerzoekBerichtObjectKopie = blokkeringVerzoekBerichtOrigineel;
        final BlokkeringAntwoordBericht blokkeringAntwoordBericht = new BlokkeringAntwoordBericht();

        blokkeringVerzoekBerichtKopie.setMessageId(blokkeringVerzoekBerichtOrigineel.getMessageId());
        blokkeringVerzoekBerichtKopie.setCorrelationId(blokkeringVerzoekBerichtOrigineel.getCorrelationId());

        assertTrue(blokkeringVerzoekBerichtObjectKopie.equals(blokkeringVerzoekBerichtOrigineel));
        assertFalse(blokkeringVerzoekBerichtOrigineel.equals(blokkeringAntwoordBericht));
        assertTrue(blokkeringVerzoekBerichtKopie.equals(blokkeringVerzoekBerichtOrigineel));
        assertEquals(blokkeringVerzoekBerichtObjectKopie.hashCode(), blokkeringVerzoekBerichtOrigineel.hashCode());
        assertEquals(blokkeringVerzoekBerichtKopie.hashCode(), blokkeringVerzoekBerichtOrigineel.hashCode());
        assertEquals(blokkeringVerzoekBerichtKopie.toString(), blokkeringVerzoekBerichtOrigineel.toString());
    }

    @Test
    public void testSettersEnGetters() {
        final BlokkeringVerzoekBericht blokkeringVerzoekBericht = new BlokkeringVerzoekBericht();
        final String ANUMMER = A_NUMMER;
        final String PROCESS_ID = "123456";
        blokkeringVerzoekBericht.setANummer(ANUMMER);
        blokkeringVerzoekBericht.setPersoonsaanduiding(PersoonsaanduidingType.VERHUIZEND_VAN_BRP_NAAR_LO_3_GBA);
        blokkeringVerzoekBericht.setProcessId(PROCESS_ID);
        blokkeringVerzoekBericht.setGemeenteNaar(GEMEENTE_NAAR);
        blokkeringVerzoekBericht.setGemeenteRegistratie(GEMEENTE_REGISTRATIE);

        assertEquals(ANUMMER, blokkeringVerzoekBericht.getANummer());
        assertEquals(PersoonsaanduidingType.VERHUIZEND_VAN_BRP_NAAR_LO_3_GBA, blokkeringVerzoekBericht.getPersoonsaanduiding());
        assertEquals(PROCESS_ID, blokkeringVerzoekBericht.getProcessId());
        assertEquals(GEMEENTE_NAAR, blokkeringVerzoekBericht.getGemeenteNaar());
        assertEquals(GEMEENTE_REGISTRATIE, blokkeringVerzoekBericht.getGemeenteRegistratie());
    }

    @Test
    public void tesBerichtSyntaxException() throws IOException {
        final String berichtOrigineel =
                IOUtils.toString(BlokkeringVerzoekBerichtTest.class.getResourceAsStream("blokkeringVerzoekBerichtSyntaxExceptionBericht.xml"));

        final SyncBericht syncBericht = factory.getBericht(berichtOrigineel);
        Assert.assertTrue(syncBericht instanceof OngeldigBericht);
    }

}
