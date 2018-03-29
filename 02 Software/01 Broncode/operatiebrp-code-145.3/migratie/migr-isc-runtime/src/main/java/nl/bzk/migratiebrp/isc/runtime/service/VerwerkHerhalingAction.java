/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime.service;

import java.sql.Timestamp;
import java.time.Instant;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao.Direction;
import nl.bzk.migratiebrp.isc.runtime.message.Acties;
import nl.bzk.migratiebrp.isc.runtime.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;

/**
 * Verwerk herhaling.
 */
public final class VerwerkHerhalingAction implements Action {
    private static final Logger LOG = LoggerFactory.getLogger();

    private static final long TWEE_DAGEN = 2 * 24 * 60 * 60 * 1_000L;

    private String kanaal;

    @Autowired
    private BerichtenDao berichtenDao;
    private Service voiscOutboundService;

    /**
     * Zet de outbound service voor VOISC.
     * @param service de te zetten outbound service voor VOISC
     */
    @Required
    public void setVoiscOutboundService(final Service service) {
        voiscOutboundService = service;
    }

    @Override
    public void setKanaal(final String kanaal) {
        this.kanaal = kanaal;
    }

    @Override
    public boolean verwerk(final Message message) {
        final Long berichtId = message.getBerichtId();
        final String messageId = message.getMessageId();
        final String originator = message.getOriginator();
        final String recipient = message.getRecipient();

        LOG.info("[Bericht: {}]: verwerkHerhaling", berichtId);

        // Tel berichten obv messageId, originator, recipient, kanaal en richting
        final int count = berichtenDao.telBerichtenBehalveId(messageId, originator, recipient, kanaal, Direction.INKOMEND, berichtId);
        LOG.info("[Bericht: {}]: aantal berichten obv id, partijen, kanaal en richting = {}", berichtId, count);

        if (count == 0) {
            // Geen herhaalbericht
            LOG.info("[Bericht: {}]: geen herhaling", berichtId);
            return true;
        }

        // Zoek meest recente antwoord
        final nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht bericht = berichtenDao.zoekMeestRecenteAntwoord(messageId, originator, recipient, kanaal);

        // Datum worden in de database opgeslagen en uitgelezen met tijdzone 'UTC', daarom wordt er hieronder geen
        // controle dmv System.currentTimeInMillis gebruikt.
        final Long berichtUTCTime = bericht == null ? null : bericht.getTijdstip().getTime();

        final Long currentUTCTime = Instant.now().toEpochMilli();
        LOG.info(
                "[Bericht: {}]: meest recente antwoord {} (tijdstip herhaling (UTC)={}, tijdstip eerder bericht (UTC)={})",
                new Object[]{berichtId,
                        bericht == null ? null : bericht.getId(),
                        new Timestamp(currentUTCTime),
                        berichtUTCTime == null ? "geen recent antwoord gevonden" : new Timestamp(berichtUTCTime),});
        if (bericht != null && berichtUTCTime + TWEE_DAGEN < currentUTCTime) {
            // Stuur antwoord op herhaling
            final Long antwoordBerichtId =
                    berichtenDao.bewaar(
                            kanaal,
                            Direction.UITGAAND,
                            bericht.getMessageId(),
                            bericht.getCorrelationId(),
                            bericht.getBericht(),
                            bericht.getVerzendendePartij(),
                            bericht.getOntvangendePartij(),
                            null,
                            bericht.getRequestNonReceipt());
            berichtenDao.updateProcessInstance(antwoordBerichtId, bericht.getProcessInstance().getId());

            berichtenDao.updateActie(berichtId, Acties.ACTIE_HERHALING_BEANTWOORD);

            // Verstuur antwoord bericht
            final Message outboundMessage = new Message();
            outboundMessage.setContent(antwoordBerichtId.toString());
            voiscOutboundService.verwerk(outboundMessage);
        } else {
            // Geen antwoord of antwoord minder dan twee dagen geleden, negeren
            LOG.info(
                    "[Bericht: {}]: herhaling genegeerd (tijdstip herhaling (UTC)={}, tijdstip eerder bericht (UTC)={})",
                    new Object[]{berichtId, new Timestamp(currentUTCTime), bericht == null ? "geen recent antwoord" : bericht.getTijdstip(),});
            berichtenDao.updateActie(berichtId, Acties.ACTIE_HERHALING_GENEGEERD);
        }

        return false;
    }
}
