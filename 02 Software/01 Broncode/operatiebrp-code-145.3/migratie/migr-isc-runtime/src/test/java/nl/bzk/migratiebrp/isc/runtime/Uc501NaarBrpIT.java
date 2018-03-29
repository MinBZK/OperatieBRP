/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Pf03Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Vb01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VrijBerichtAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VrijBerichtVerzoekBericht;
import org.junit.Test;

public class Uc501NaarBrpIT extends AbstractIT {
    private static final Logger LOG = LoggerFactory.getLogger();

    @Test
    public void testHappyFlow() throws BerichtInhoudException {
        LOG.info("Starting test");

        // Valideer
        controleerAlleProcessenBeeindigd();

        // Start
        final Lo3Bericht input = maakVb01Bericht("042901", "069901", "Happy flow vrij bericht");
        input.setMessageId("vb01-happy-001");
        putMessage(VOISC_ONTVANGST_QUEUE, input, 926756L, null);

        // Sync naar BRP
        final VrijBerichtVerzoekBericht vrijBerichtVerzoekBericht = expectMessage(SYNC_VERZOEK_QUEUE, VrijBerichtVerzoekBericht.class);
        final SyncBericht vrijBerichtAntwoordBericht = maakVrijBerichtAntwoordBericht(input, vrijBerichtVerzoekBericht, true);
        putMessage(SYNC_ANTWOORD_QUEUE, vrijBerichtAntwoordBericht);

        // Controleer
        controleerAlleProcessenBeeindigd();
    }

    @Test
    public void testBadFlow() throws BerichtInhoudException {
        LOG.info("Starting test");

        // Valideer
        controleerAlleProcessenBeeindigd();

        // Start
        final Lo3Bericht input = maakVb01Bericht("042901", "069901", "Bad flow vrij bericht");
        input.setMessageId("vb01-bad-001");
        putMessage(VOISC_ONTVANGST_QUEUE, input, 972756L, null);

        // Sync naar BRP
        final VrijBerichtVerzoekBericht vrijBerichtVerzoekBericht = expectMessage(SYNC_VERZOEK_QUEUE, VrijBerichtVerzoekBericht.class);
        final SyncBericht vrijBerichtAntwoordBericht = maakVrijBerichtAntwoordBericht(input, vrijBerichtVerzoekBericht, false);
        putMessage(SYNC_ANTWOORD_QUEUE, vrijBerichtAntwoordBericht);

        // Controleer op Pf03
        final Pf03Bericht pf03Bericht = expectMessage(VOISC_VERZENDEN_QUEUE, Pf03Bericht.class);

        // Controleer
        controleerAlleProcessenBeeindigd();
    }

    /*
     * *** Vb01
     * ***************************************************************************************
     */

    private Vb01Bericht maakVb01Bericht(final String verzenderCode, final String ontvangerCode, final String bericht) throws BerichtInhoudException {
        final Vb01Bericht vb01Bericht = new Vb01Bericht();
        vb01Bericht.setBronPartijCode(verzenderCode);
        vb01Bericht.setDoelPartijCode(ontvangerCode);
        vb01Bericht.setHeader(Lo3HeaderVeld.BERICHT, bericht);
        vb01Bericht.setHeader(Lo3HeaderVeld.LENGTE_BERICHT, Integer.toString(bericht.length()));
        return vb01Bericht;
    }

    /*
     * *** VRIJ BERICHT VERZOEK
     * ********************************************************************************************
     */

    protected VrijBerichtAntwoordBericht maakVrijBerichtAntwoordBericht(final Lo3Bericht vb01Bericht, final VrijBerichtVerzoekBericht vrijBerichtVerzoekBericht,
                                                                        boolean status) {
        final VrijBerichtAntwoordBericht antwoord = new VrijBerichtAntwoordBericht();
        antwoord.setCorrelationId(vrijBerichtVerzoekBericht.getMessageId());
        antwoord.setStatus(status);
        antwoord.setReferentienummer(vb01Bericht.getMessageId());
        return antwoord;
    }

}
