/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.logging.listeners;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import nl.moderniseringgba.isc.esb.message.JMSConstants;
import nl.moderniseringgba.isc.esb.message.brp.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.sync.SyncBericht;
import nl.moderniseringgba.isc.esb.message.sync.SyncBerichtFactory;
import nl.moderniseringgba.isc.esb.message.sync.impl.LeesUitBrpAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.SynchroniseerNaarBrpAntwoordBericht;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;
import nl.moderniseringgba.migratie.logging.domein.entities.InitVullingLog;
import nl.moderniseringgba.migratie.logging.service.LoggingService;

/**
 * Verwerk een fout die terug komt van de sync naar BRP.
 */
public final class NaarBrpFoutMessageHandler implements MessageListener {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private LoggingService loggingService;

    /**
     * {@inheritDoc}
     */
    @Override
    public void onMessage(final Message message) {
        final String messageId;
        try {
            messageId = message.getStringProperty(JMSConstants.BERICHT_REFERENTIE);
            final String correlationId = message.getStringProperty(JMSConstants.CORRELATIE_REFERENTIE);
            if (messageId == null || "".equals(messageId)) {
                LOG.error("Bericht ontvangen zonder message-id!\n{}", message);
            } else {
                LOG.info("[Bericht {}]: Parse bericht ...", messageId);

                if (message instanceof TextMessage) {
                    verwerkBericht(((TextMessage) message).getText(), messageId, correlationId);

                } else {
                    LOG.error("[Bericht {}]: JMS bericht is niet van het type TextMessage", messageId);
                }
            }
        } catch (final JMSException e1) {
            LOG.error("Kan message-id niet lezen van bericht, of geen valide bericht!\n{}", message);
        }
    }

    /**
     * Verwerkt het ontvangen bericht.
     * 
     * @param bericht
     *            Het bericht dat verwerkt moet worden.
     * @param messageId
     *            Het messageId van het bericht
     */
    private void verwerkBericht(final String bericht, final String messageId, final String correlationId) {

        try {
            final SyncBericht syncBericht = SyncBerichtFactory.SINGLETON.getBericht(bericht);
            InitVullingLog log = null;
            if (syncBericht instanceof SynchroniseerNaarBrpAntwoordBericht) {
                final SynchroniseerNaarBrpAntwoordBericht response =
                        (SynchroniseerNaarBrpAntwoordBericht) syncBericht;
                log = retrieveLog(messageId, correlationId, response);
                if (log != null) {
                    fillLog(log, response.getFoutmelding(), response.getStatus());
                } else {
                    LOG.warn("Kon geen log vinden voor messageId: {}, correlationId: ", messageId, correlationId);
                }
            } else if (syncBericht instanceof LeesUitBrpAntwoordBericht) {
                final LeesUitBrpAntwoordBericht response = (LeesUitBrpAntwoordBericht) syncBericht;
                log = retrieveLog(messageId, correlationId, response);
                fillLog(log, response.getFoutmelding(), response.getStatus());
                // TODO } else if (syncBericht instanceof ErrorBericht) {
                // final ErrorBericht error = (ErrorBericht) syncBericht;
                // log = retrieveLog(messageId, correlationId, error);
                // fillLog(log, error.getFoutmelding(), StatusType.FOUT);
            }
            loggingService.createAndStoreDiff(log);
            // CHECKSTYLE:OFF - All exceptions have the same log.
        } catch (final Exception e) {
            // CHECKSTYLE:ON
            LOG.error("Error opgetreden bij het uitlezen van het response bericht.", e);
        }
    }

    /**
     * Haalt het log object op.
     * 
     * @param messageId
     *            MessageId wat de key zou zijn voor het log object.
     * @param bericht
     *            Het bericht wat binnenkomt.
     * @return InitVullingLog.
     */
    private InitVullingLog retrieveLog(final String messageId, final String correlationId, final SyncBericht bericht) {
        InitVullingLog log = null;
        try {
            log = loggingService.findLog(Long.parseLong(messageId));
        } catch (final NumberFormatException e) {
            try {
                log = loggingService.findLog(Long.parseLong(correlationId));
            } catch (final NumberFormatException e1) {
                LOG.warn("Messageid (" + messageId + ") en correlatieId (" + correlationId
                        + ") zijn niet wat we verwachten, geen long (anummer)");
                LOG.error("Bericht: " + bericht);
            }
        }
        return log;
    }

    /**
     * Vul de init vulling log.
     */
    private void fillLog(
            final InitVullingLog log,
            final String foutmelding,
            final nl.moderniseringgba.isc.esb.message.sync.generated.StatusType status) {
        log.setFoutmelding(foutmelding);
        log.setConversieResultaat(status.toString());
        log.setPreconditie(findPreconditie(foutmelding));
        log.setFoutCategorie(findCategorie(foutmelding));
    }

    /**
     * Vul de init vulling log.
     */
    private void fillLog(final InitVullingLog log, final String foutmelding, final StatusType status) {
        log.setFoutmelding(foutmelding);
        log.setConversieResultaat(status.toString());
        log.setPreconditie(findPreconditie(foutmelding));
        log.setFoutCategorie(findCategorie(foutmelding));
    }

    /**
     * Zoek het categorienummer in de melding.
     * 
     * @param melding
     *            De melding waar de fout categorie in kan staan.
     * @return categorienummer waar de fout in zit.
     */
    private Integer findCategorie(final String melding) {
        String categorie = null;
        if (melding != null) {
            final Pattern pattern = Pattern.compile("^Categorie (\\d{2})");
            final Matcher matcher = pattern.matcher(melding);
            if (matcher.find()) {
                categorie = matcher.group(1);
            }
        }
        if (categorie != null) {
            try {
                return Integer.parseInt(categorie);
            } catch (final NumberFormatException e) {
                LOG.debug("Gevonden categorienummer is geen getal.");
            }
        }
        return null;
    }

    /**
     * Find the preconditie in the error message.
     * 
     * @param melding
     *            The melding with the possible preconditie.
     * @return preconditie or null.
     */
    private String findPreconditie(final String melding) {
        String preconditie = null;
        if (melding != null) {
            final Pattern pattern = Pattern.compile("PRE\\d{3}");
            final Matcher matcher = pattern.matcher(melding);
            if (matcher.find()) {
                preconditie = matcher.group();
            }
        }
        return preconditie;
    }
}
