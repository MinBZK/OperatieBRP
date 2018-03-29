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
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.factory.SyncBerichtFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.DeblokkeringVerzoekType;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

public class DeblokkeringVerzoekBerichtTest {

    private static final String PROCES_ID = "1232";
    private static final String A_NUMMER = "1234567890";
    private static final String GEMEENTE_REGISTRATIE = "060001";

    private static final Logger LOG = LoggerFactory.getLogger();

    private final SyncBerichtFactory factory = SyncBerichtFactory.SINGLETON;

    @Test
    public void testVertaal() throws IOException {
        final String berichtOrigineel = IOUtils.toString(DeblokkeringVerzoekBerichtTest.class.getResourceAsStream("deblokkeringVerzoekBericht.xml"));
        final SyncBericht bericht = factory.getBericht(berichtOrigineel);

        final DeblokkeringVerzoekBericht deblokkeringVerzoekBericht = (DeblokkeringVerzoekBericht) bericht;
        assertEquals(A_NUMMER, deblokkeringVerzoekBericht.getANummer());
        assertEquals(null, deblokkeringVerzoekBericht.getStartCyclus());

    }

    @Test
    public void testFormat() throws BerichtInhoudException {
        final DeblokkeringVerzoekType deblokkeringVerzoekType = new DeblokkeringVerzoekType();
        deblokkeringVerzoekType.setANummer(A_NUMMER);
        deblokkeringVerzoekType.setProcessId(PROCES_ID);
        deblokkeringVerzoekType.setGemeenteRegistratie(GEMEENTE_REGISTRATIE);

        final DeblokkeringVerzoekBericht deblokkeringVerzoekBericht = new DeblokkeringVerzoekBericht(deblokkeringVerzoekType);
        final String geformat = deblokkeringVerzoekBericht.format();

        LOG.info("Geformat: {}", geformat);
        // controleer of geformat bericht weer geparsed kan worden
        final DeblokkeringVerzoekBericht format = (DeblokkeringVerzoekBericht) factory.getBericht(geformat);
        assertEquals("DeblokkeringVerzoek", format.getBerichtType());
        assertEquals(A_NUMMER, format.getANummer());
        assertEquals(PROCES_ID, format.getProcessId());
        assertEquals(GEMEENTE_REGISTRATIE, format.getGemeenteRegistratie());
    }

    @Test
    public void testEquals() {
        final DeblokkeringVerzoekType deblokkeringVerzoekType = new DeblokkeringVerzoekType();
        deblokkeringVerzoekType.setANummer(A_NUMMER);
        deblokkeringVerzoekType.setProcessId(PROCES_ID);

        final DeblokkeringVerzoekBericht deblokkeringVerzoekBerichtOrigineel = new DeblokkeringVerzoekBericht(deblokkeringVerzoekType);
        final DeblokkeringVerzoekBericht deblokkeringVerzoekBerichtKopie = new DeblokkeringVerzoekBericht(deblokkeringVerzoekType);
        final DeblokkeringVerzoekBericht deblokkeringVerzoekBerichtObjectKopie = deblokkeringVerzoekBerichtOrigineel;
        final DeblokkeringAntwoordBericht deblokkeringAntwoordBericht = new DeblokkeringAntwoordBericht();

        deblokkeringVerzoekBerichtKopie.setMessageId(deblokkeringVerzoekBerichtOrigineel.getMessageId());
        deblokkeringVerzoekBerichtKopie.setCorrelationId(deblokkeringVerzoekBerichtOrigineel.getCorrelationId());

        assertTrue(deblokkeringVerzoekBerichtObjectKopie.equals(deblokkeringVerzoekBerichtOrigineel));
        assertFalse(deblokkeringVerzoekBerichtOrigineel.equals(deblokkeringAntwoordBericht));
        assertTrue(deblokkeringVerzoekBerichtKopie.equals(deblokkeringVerzoekBerichtOrigineel));
        assertEquals(deblokkeringVerzoekBerichtObjectKopie.hashCode(), deblokkeringVerzoekBerichtOrigineel.hashCode());
        assertEquals(deblokkeringVerzoekBerichtKopie.hashCode(), deblokkeringVerzoekBerichtOrigineel.hashCode());
        assertEquals(deblokkeringVerzoekBerichtKopie.toString(), deblokkeringVerzoekBerichtOrigineel.toString());
    }

    @Test
    public void testSettersEnGetters() {
        final DeblokkeringVerzoekBericht deblokkeringVerzoekBericht = new DeblokkeringVerzoekBericht();
        final String ANUMMER = A_NUMMER;
        final String PROCESS_ID = "123456";
        deblokkeringVerzoekBericht.setANummer(ANUMMER);
        deblokkeringVerzoekBericht.setProcessId(PROCESS_ID);
        deblokkeringVerzoekBericht.setGemeenteRegistratie(GEMEENTE_REGISTRATIE);

        assertEquals(ANUMMER, deblokkeringVerzoekBericht.getANummer());
        assertEquals(PROCESS_ID, deblokkeringVerzoekBericht.getProcessId());
        assertEquals(GEMEENTE_REGISTRATIE, deblokkeringVerzoekBericht.getGemeenteRegistratie());
    }

    @Test
    public void tesBerichtSyntaxException() throws IOException {
        final String berichtOrigineel =
                IOUtils.toString(BlokkeringVerzoekBerichtTest.class.getResourceAsStream("blokkeringVerzoekBerichtSyntaxExceptionBericht.xml"));

        final SyncBericht syncBericht = factory.getBericht(berichtOrigineel);
        Assert.assertTrue(syncBericht instanceof OngeldigBericht);
    }

}
