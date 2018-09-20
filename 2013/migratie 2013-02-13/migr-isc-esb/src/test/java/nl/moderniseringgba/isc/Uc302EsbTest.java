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
import nl.moderniseringgba.isc.esb.message.brp.impl.NotificatieAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.VerhuizingAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3BerichtFactory;
import nl.moderniseringgba.isc.esb.message.lo3.impl.NullBericht;
import nl.moderniseringgba.isc.esb.message.sync.SyncBericht;
import nl.moderniseringgba.isc.esb.message.sync.SyncBerichtFactory;
import nl.moderniseringgba.isc.esb.message.sync.impl.BlokkeringAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.DeblokkeringAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.SynchroniseerNaarBrpAntwoordBericht;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.junit.Assert;
import org.junit.Test;

public class Uc302EsbTest extends AbstractEsbTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final Lo3BerichtFactory lo3BerichtFactory = new Lo3BerichtFactory();
    private final BrpBerichtFactory brpBerichtFactory = BrpBerichtFactory.SINGLETON;
    private final SyncBerichtFactory syncBerichtFactory = SyncBerichtFactory.SINGLETON;

    // CHECKSTYLE:OFF - NCSS - Lang proces
    @Test
    public void testHappyFlow() throws BerichtSyntaxException, BerichtOnbekendException, BerichtInhoudException,
    // CHECKSTYLE:ON
            InterruptedException {
        // //////////////////////////////////////////////////////////////////
        // BRP WERELD
        // //////////////////////////////////////////////////////////////////

        final Bericht input = new Bericht();
        input.setMessageId(BerichtId.generateMessageId());
        input.setInhoud(readResourceAsString("uc302.xml"));
        input.setOriginator("0518");
        input.setRecipient("0599");

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

        // Verwacht: store
        final Bericht syncBericht = ontvangSyncBericht();
        LOG.info("Ontvangen sync bericht: " + syncBericht);
        Assert.assertNotNull(syncBericht);
        final SyncBericht parsedSyncBericht = syncBerichtFactory.getBericht(syncBericht.getInhoud());
        Assert.assertEquals("Store bericht verwacht", "Store", parsedSyncBericht.getBerichtType());

        // Verzend: store response
        final Bericht syncResponse = new Bericht();
        syncResponse.setMessageId(BerichtId.generateMessageId());
        syncResponse.setCorrelatieId(syncBericht.getMessageId());
        syncResponse.setInhoud(new SynchroniseerNaarBrpAntwoordBericht(parsedSyncBericht.getMessageId()).format());
        LOG.info("Verstuur sync response: " + syncResponse);
        verstuurSyncBericht(syncResponse);

        Thread.sleep(1000);

        // Verwacht: verhuisbericht
        final Bericht verhuisBericht = ontvangBrpBericht();
        LOG.info("Ontvangen verhuis bericht: " + verhuisBericht);
        Assert.assertNotNull("Geen verhuisbericht ontvangen", verhuisBericht);
        final BrpBericht parsedVerhuisBericht = brpBerichtFactory.getBericht(verhuisBericht.getInhoud());
        Assert.assertEquals("Verhuisbericht verwacht", "Verhuis", parsedVerhuisBericht.getBerichtType());

        // Verzend: bevestiging
        final Bericht verhuisAntwoord = new Bericht();
        verhuisAntwoord.setMessageId(BerichtId.generateMessageId());
        verhuisAntwoord.setCorrelatieId(verhuisBericht.getMessageId());
        verhuisAntwoord.setInhoud(new VerhuizingAntwoordBericht().format());
        LOG.info("Verstuur bevestiging (op verhuizing): " + verhuisAntwoord);
        verstuurBrpBericht(verhuisAntwoord);

        Thread.sleep(1000);

        // Verwacht: deblokkering
        final Bericht deblokkeringBericht = ontvangBrpBericht();
        LOG.info("Ontvangen deblokkering bericht: " + deblokkeringBericht);
        Assert.assertNotNull("Geen deblokkering bericht ontvangen", deblokkeringBericht);
        final BrpBericht parsedDeblokkeringBericht = brpBerichtFactory.getBericht(deblokkeringBericht.getInhoud());
        Assert.assertEquals("Deblokkering verwacht", "Deblokkering", parsedDeblokkeringBericht.getBerichtType());

        // Verzend: bevestiging
        final Bericht deblokkeringAntwoord = new Bericht();
        deblokkeringAntwoord.setMessageId(BerichtId.generateMessageId());
        deblokkeringAntwoord.setCorrelatieId(deblokkeringBericht.getMessageId());
        deblokkeringAntwoord.setInhoud(new DeblokkeringAntwoordBericht().format());
        LOG.info("Verstuur bevestiging (op deblokkering): " + deblokkeringAntwoord);
        verstuurBrpBericht(deblokkeringAntwoord);

        Thread.sleep(1000);

        // Verwacht: notificatie
        final Bericht notificatieBericht = ontvangBrpBericht();
        LOG.info("Ontvangen notificatie bericht: " + notificatieBericht);
        Assert.assertNotNull("Geen notificatie bericht ontvangen", notificatieBericht);
        final BrpBericht parsedNotificatieBericht = brpBerichtFactory.getBericht(notificatieBericht.getInhoud());
        Assert.assertEquals("Notificatie verwacht", "Notificatie", parsedNotificatieBericht.getBerichtType());
        Assert.assertEquals("Notificatie correlatie niet juist", input.getMessageId(),
                notificatieBericht.getCorrelatieId());

        // Verzend: bevestiging
        final Bericht bevestigingOpNotificatie = new Bericht();
        bevestigingOpNotificatie.setMessageId(BerichtId.generateMessageId());
        bevestigingOpNotificatie.setCorrelatieId(notificatieBericht.getMessageId());
        bevestigingOpNotificatie.setInhoud(new NotificatieAntwoordBericht().format());
        LOG.info("Verstuur bevestiging (op notificatie): " + bevestigingOpNotificatie);
        verstuurBrpBericht(bevestigingOpNotificatie);

        Thread.sleep(1000);

        // Verwacht: iv01
        final Bericht iv01Bericht = ontvangVospgBericht();
        LOG.info("Ontvangen iv01 bericht: " + iv01Bericht);
        Assert.assertNotNull("Geen iv01 bericht ontvangen", iv01Bericht);

        // Even TODO
        // final Lo3Bericht parsedIv01Bericht = lo3BerichtFactory.getBericht(iv01Bericht.getInhoud());
        // Assert.assertEquals("Iv01 bericht verwacht", "Iv01", parsedIv01Bericht.getBerichtType());
        // Assert.assertEquals("Iv01 correlatie niet juist", ib01Bericht.getMessageId(), iv01Bericht.getCorrelatieId());

        // Verzend: null
        final Bericht nullBericht = new Bericht();
        nullBericht.setMessageId(BerichtId.generateMessageId());
        nullBericht.setCorrelatieId(iv01Bericht.getMessageId());
        nullBericht.setInhoud(new NullBericht().format());
        LOG.info("Verstuur null: " + nullBericht);
        verstuurVospgBericht(nullBericht);
    }

}
