/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Vb01Bericht;
import nl.bzk.migratiebrp.bericht.model.notificatie.NotificatieBericht;
import nl.bzk.migratiebrp.bericht.model.notificatie.factory.NotificatieBerichtFactory;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class Uc501NaarGbaIT extends AbstractIT {
    private static final Logger LOG = LoggerFactory.getLogger();

    @Test
    public void testHappyFlow() throws BerichtInhoudException, IOException {
        LOG.info("Starting test");

        // Valideer
        controleerAlleProcessenBeeindigd();

        // Start
        NotificatieBericht input = NotificatieBerichtFactory.SINGLETON.getBericht(readFile("brp-naar-gba-happy-001-input.xml"));
        input.setMessageId("brp-naar-gba-happy-001");
        putMessage(NOTIFICATIE_QUEUE, input, 926756234L, null);

        // Controleer Vb01 bericht op VOISC kanaal.
        final Vb01Bericht output = expectMessage(VOISC_VERZENDEN_QUEUE, Vb01Bericht.class);

        LOG.info("\n\n\nBERICHT = {}\n\n\n", output.getHeaderWaarde(Lo3HeaderVeld.BERICHT));
        assertEquals("Hartelijk gefeliciteerd!", output.getHeaderWaarde(Lo3HeaderVeld.BERICHT));
        assertEquals("069901", output.getBronPartijCode());
        assertEquals("071701", output.getDoelPartijCode());

        // Controleer
        controleerAlleProcessenBeeindigd();
    }

    private String readFile(final String filename) {
        try {
            return IOUtils.toString(this.getClass().getResourceAsStream(filename), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
