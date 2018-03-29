/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.MessageIdGenerator;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.factory.SyncBerichtFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.FoutredenType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.VerwerkToevalligeGebeurtenisAntwoordType;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

public class VerwerkToevalligeGebeurtenisAntwoordBerichtTest {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final String GEMEENTE_NAAR = "0599";
    private final SyncBerichtFactory factory = SyncBerichtFactory.SINGLETON;

    @Test
    public void testVertaal() throws IOException {
        final String berichtOrigineel =
                IOUtils.toString(VerwerkToevalligeGebeurtenisAntwoordBerichtTest.class.getResourceAsStream("verwerkToevalligeGebeurtenisAntwoord.xml"));
        final SyncBericht bericht = factory.getBericht(berichtOrigineel);

        final VerwerkToevalligeGebeurtenisAntwoordBericht verwerkToevalligeGebeurtenisAntwoordBericht =
                (VerwerkToevalligeGebeurtenisAntwoordBericht) bericht;
        assertEquals(StatusType.FOUT, verwerkToevalligeGebeurtenisAntwoordBericht.getStatus());
        assertEquals(null, verwerkToevalligeGebeurtenisAntwoordBericht.getStartCyclus());
        assertEquals(FoutredenType.V, verwerkToevalligeGebeurtenisAntwoordBericht.getFoutreden());
        assertEquals(GEMEENTE_NAAR, verwerkToevalligeGebeurtenisAntwoordBericht.getBijhoudingGemeenteCode());
    }

    @Test
    public void testFormat() throws BerichtInhoudException {
        final VerwerkToevalligeGebeurtenisAntwoordType verwerkToevalligeGebeurtenisAntwoordType = new VerwerkToevalligeGebeurtenisAntwoordType();
        verwerkToevalligeGebeurtenisAntwoordType.setStatus(StatusType.FOUT);
        verwerkToevalligeGebeurtenisAntwoordType.setFoutreden(FoutredenType.V);
        verwerkToevalligeGebeurtenisAntwoordType.setBijhoudingGemeenteCode(GEMEENTE_NAAR);
        final VerwerkToevalligeGebeurtenisAntwoordBericht syncBericht =
                new VerwerkToevalligeGebeurtenisAntwoordBericht(verwerkToevalligeGebeurtenisAntwoordType);

        LOG.info("Geformat: {}", syncBericht.format());
        assertEquals("VerwerkToevalligeGebeurtenisAntwoord", syncBericht.getBerichtType());
        assertEquals(StatusType.FOUT, syncBericht.getStatus());
        assertEquals(FoutredenType.V, syncBericht.getFoutreden());
        assertEquals(GEMEENTE_NAAR, syncBericht.getBijhoudingGemeenteCode());
    }

    @Test
    public void testFoutBericht() throws BerichtInhoudException {
        final Long ADMINISTRATIEVE_HANDELING_ID = Long.valueOf(1234L);
        final VerwerkToevalligeGebeurtenisAntwoordBericht bericht = new VerwerkToevalligeGebeurtenisAntwoordBericht();
        bericht.setStatus(StatusType.FOUT);
        bericht.setBijhoudingGemeenteCode(GEMEENTE_NAAR);
        bericht.setAdministratieveHandelingId(ADMINISTRATIEVE_HANDELING_ID);

        LOG.info("Geformat: {}", bericht.format());
        assertEquals("VerwerkToevalligeGebeurtenisAntwoord", bericht.getBerichtType());
        assertEquals(StatusType.FOUT, bericht.getStatus());
        assertEquals(GEMEENTE_NAAR, bericht.getBijhoudingGemeenteCode());
        assertNotNull(bericht.getAdministratieveHandelingId());
        assertEquals(ADMINISTRATIEVE_HANDELING_ID, bericht.getAdministratieveHandelingId());
    }

    @Test(expected = NullPointerException.class)
    public void testNullStatus() {
        final VerwerkToevalligeGebeurtenisAntwoordBericht bericht = new VerwerkToevalligeGebeurtenisAntwoordBericht();
        bericht.setStatus(null);
    }

    @Test
    public void testEquals() {
        final VerwerkToevalligeGebeurtenisAntwoordType verwerkToevalligeGebeurtenisAntwoordType = new VerwerkToevalligeGebeurtenisAntwoordType();
        verwerkToevalligeGebeurtenisAntwoordType.setStatus(StatusType.OK);
        final VerwerkToevalligeGebeurtenisAntwoordBericht verwerkToevalligeGebeurtenisAntwoordBerichtOrigineel =
                new VerwerkToevalligeGebeurtenisAntwoordBericht(verwerkToevalligeGebeurtenisAntwoordType);
        verwerkToevalligeGebeurtenisAntwoordBerichtOrigineel.setMessageId(MessageIdGenerator.generateId());
        final VerwerkToevalligeGebeurtenisAntwoordBericht verwerkToevalligeGebeurtenisAntwoordBerichtKopie =
                new VerwerkToevalligeGebeurtenisAntwoordBericht(verwerkToevalligeGebeurtenisAntwoordType);
        final VerwerkToevalligeGebeurtenisAntwoordBericht verwerkToevalligeGebeurtenisAntwoordBerichtObjectKopie =
                verwerkToevalligeGebeurtenisAntwoordBerichtOrigineel;
        final DeblokkeringAntwoordBericht deblokkeringAntwoordBericht = new DeblokkeringAntwoordBericht();

        verwerkToevalligeGebeurtenisAntwoordBerichtKopie.setMessageId(verwerkToevalligeGebeurtenisAntwoordBerichtOrigineel.getMessageId());
        verwerkToevalligeGebeurtenisAntwoordBerichtKopie.setCorrelationId(verwerkToevalligeGebeurtenisAntwoordBerichtOrigineel.getCorrelationId());

        assertTrue(verwerkToevalligeGebeurtenisAntwoordBerichtObjectKopie.equals(verwerkToevalligeGebeurtenisAntwoordBerichtOrigineel));
        assertFalse(verwerkToevalligeGebeurtenisAntwoordBerichtOrigineel.equals(deblokkeringAntwoordBericht));
        assertTrue(verwerkToevalligeGebeurtenisAntwoordBerichtKopie.equals(verwerkToevalligeGebeurtenisAntwoordBerichtOrigineel));
        assertEquals(verwerkToevalligeGebeurtenisAntwoordBerichtObjectKopie.hashCode(), verwerkToevalligeGebeurtenisAntwoordBerichtOrigineel.hashCode());
        assertEquals(verwerkToevalligeGebeurtenisAntwoordBerichtKopie.hashCode(), verwerkToevalligeGebeurtenisAntwoordBerichtOrigineel.hashCode());
        assertEquals(verwerkToevalligeGebeurtenisAntwoordBerichtKopie.toString(), verwerkToevalligeGebeurtenisAntwoordBerichtOrigineel.toString());
    }

    @Test
    public void testSettersEnGetters() {
        final VerwerkToevalligeGebeurtenisAntwoordBericht verwerkToevalligeGebeurtenisAntwoordBericht = new VerwerkToevalligeGebeurtenisAntwoordBericht();
        verwerkToevalligeGebeurtenisAntwoordBericht.setStatus(StatusType.FOUT);
        verwerkToevalligeGebeurtenisAntwoordBericht.setFoutreden(FoutredenType.V);
        verwerkToevalligeGebeurtenisAntwoordBericht.setBijhoudingGemeenteCode(GEMEENTE_NAAR);

        assertEquals(StatusType.FOUT, verwerkToevalligeGebeurtenisAntwoordBericht.getStatus());
        assertEquals(FoutredenType.V, verwerkToevalligeGebeurtenisAntwoordBericht.getFoutreden());
        assertEquals(GEMEENTE_NAAR, verwerkToevalligeGebeurtenisAntwoordBericht.getBijhoudingGemeenteCode());
    }

    @Test
    public void testBerichtSyntaxException() throws IOException {
        final String berichtOrigineel =
                IOUtils.toString(
                        VerwerkToevalligeGebeurtenisAntwoordBerichtTest.class.getResourceAsStream(
                                "verwerkToevalligeGebeurtenisAntwoordBerichtSyntaxException.xml"));

        final SyncBericht syncBericht = factory.getBericht(berichtOrigineel);
        Assert.assertTrue(syncBericht instanceof OngeldigBericht);
    }

}
