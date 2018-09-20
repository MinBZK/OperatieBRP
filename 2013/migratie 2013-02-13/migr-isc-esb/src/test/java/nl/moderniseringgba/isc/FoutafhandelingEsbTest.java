/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc;

import nl.moderniseringgba.isc.esb.message.BerichtId;
import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.BerichtOnbekendException;
import nl.moderniseringgba.isc.esb.message.BerichtSyntaxException;
import nl.moderniseringgba.isc.esb.message.brp.BrpBericht;
import nl.moderniseringgba.isc.esb.message.brp.BrpBerichtFactory;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3BerichtFactory;
import nl.moderniseringgba.isc.esb.message.sync.SyncBericht;
import nl.moderniseringgba.isc.esb.message.sync.SyncBerichtFactory;
import nl.moderniseringgba.isc.esb.message.sync.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.sync.impl.BlokkeringAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.SynchroniseerNaarBrpAntwoordBericht;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.junit.Assert;
import org.junit.Test;

public class FoutafhandelingEsbTest extends AbstractEsbTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final Lo3BerichtFactory lo3BerichtFactory = new Lo3BerichtFactory();
    private final BrpBerichtFactory brpBerichtFactory = BrpBerichtFactory.SINGLETON;
    private final SyncBerichtFactory syncBerichtFactory = SyncBerichtFactory.SINGLETON;

    /**
     * Deze test loopt via UC302 om de foutafhandeling aan te roepen.
     */
    @Test
    public void test() throws BerichtSyntaxException, BerichtOnbekendException, BerichtInhoudException,
            InterruptedException {
        // //////////////////////////////////////////////////////////////////
        // BRP WERELD
        // //////////////////////////////////////////////////////////////////

        final Bericht input = new Bericht();
        input.setMessageId(BerichtId.generateMessageId());
        input.setInhoud(readResourceAsString("uc302.xml"));

        LOG.info("Verstuur BRP verhuisbericht: " + input);
        verstuurBrpBericht(input);

        Thread.sleep(1000);

        // Verwacht: BRP blokkering
        final Bericht blokkeringBericht = this.ontvangBrpBericht();
        LOG.info("Ontvangen blokkering bericht: " + blokkeringBericht);
        Assert.assertNotNull("Geen BRP blokkering bericht ontvangen", blokkeringBericht);
        final BrpBericht parsedBlokkeringBericht = brpBerichtFactory.getBericht(blokkeringBericht.getInhoud());
        Assert.assertEquals("Blokkering bericht verwacht maar niet ontvangen.", "Blokkering",
                parsedBlokkeringBericht.getBerichtType());

        // Verzend: blokkering antwoord
        final Bericht blokkeringAntwoord = new Bericht();
        blokkeringAntwoord.setMessageId(BerichtId.generateMessageId());
        blokkeringAntwoord.setCorrelatieId(blokkeringBericht.getMessageId());
        blokkeringAntwoord.setInhoud(new BlokkeringAntwoordBericht().format());
        LOG.info("Verstuur blokkeringAntwoord: " + blokkeringAntwoord);
        verstuurBrpBericht(blokkeringAntwoord);

        Thread.sleep(1000);

        // Verwacht: ii01
        final Bericht ii01Bericht = ontvangVospgBericht();
        LOG.info("Ontvangen ii01 bericht: " + ii01Bericht);
        Assert.assertNotNull("Geen ii01 bericht ontvangen", ii01Bericht);
        final Lo3Bericht parsedIi01Bericht = lo3BerichtFactory.getBericht(ii01Bericht.getInhoud());
        Assert.assertEquals("Ii01 bericht verwacht", "Ii01", parsedIi01Bericht.getBerichtType());

        // Verzend: ib01
        final Bericht ib01Bericht = new Bericht();
        ib01Bericht.setMessageId(BerichtId.generateMessageId());
        ib01Bericht.setCorrelatieId(ii01Bericht.getMessageId());
        ib01Bericht.setInhoud(readResourceAsString("ib01.txt"));
        LOG.info("Verstuur ib01: " + ib01Bericht);
        verstuurVospgBericht(ib01Bericht);

        Thread.sleep(1000);

        // Verwacht: sync
        final Bericht syncBericht = ontvangSyncBericht();
        LOG.info("Ontvangen sync bericht: " + syncBericht);
        Assert.assertNotNull(syncBericht);
        final SyncBericht parsedSyncBericht = syncBerichtFactory.getBericht(syncBericht.getInhoud());
        Assert.assertEquals("Store bericht verwacht", "Store", parsedSyncBericht.getBerichtType());

        // Verzend: null
        final Bericht syncResponse = new Bericht();
        syncResponse.setMessageId(BerichtId.generateMessageId());
        syncResponse.setCorrelatieId(syncBericht.getMessageId());

        final SynchroniseerNaarBrpAntwoordBericht syncResponseBericht =
                new SynchroniseerNaarBrpAntwoordBericht("23165321");
        syncResponseBericht.setStatus(StatusType.FOUT);
        syncResponseBericht.setFoutmelding("Fout is is fout gegaan. Stuk is stuk gegaan. Algehele ellende");
        syncResponse.setInhoud(syncResponseBericht.format());
        LOG.info("Verstuur sync response: " + syncResponse);
        verstuurSyncBericht(syncResponse);

        // Verwacht: deblokkering
        // Verwacht: notificatie
        // Verwacht: pf01
    }

}
