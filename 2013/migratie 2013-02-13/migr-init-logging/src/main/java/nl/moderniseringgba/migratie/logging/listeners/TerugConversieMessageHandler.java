/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.logging.listeners;

import java.util.List;

import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import nl.moderniseringgba.isc.esb.message.JMSConstants;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Inhoud;
import nl.moderniseringgba.isc.esb.message.lo3.format.Lo3PersoonslijstFormatter;
import nl.moderniseringgba.isc.esb.message.sync.SyncBericht;
import nl.moderniseringgba.isc.esb.message.sync.SyncBerichtFactory;
import nl.moderniseringgba.isc.esb.message.sync.impl.LeesUitBrpAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.SynchroniseerNaarBrpAntwoordBericht;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;
import nl.moderniseringgba.migratie.logging.domein.entities.InitVullingLog;
import nl.moderniseringgba.migratie.logging.service.LoggingService;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Verwerk een terug conversie bericht.
 */
public final class TerugConversieMessageHandler implements MessageListener {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private LoggingService loggingService;

    /**
     * {@inheritDoc}
     */
    @Override
    public void onMessage(final Message message) {
        try {
            final String messageId = message.getStringProperty(JMSConstants.BERICHT_REFERENTIE);
            final String correlationId = message.getStringProperty(JMSConstants.CORRELATIE_REFERENTIE);
            if (messageId == null || "".equals(messageId)) {
                LOG.error("Bericht ontvangen zonder message-id!\n{}", message);
            } else {
                LOG.info("[Bericht {}]: Parse bericht ...", messageId);
                verwerkBericht(message, messageId, correlationId);
            }
        } catch (final JMSException e1) {
            LOG.error("Kan message-id niet lezen van bericht, of geen valide bericht!\n{}", message);
        }
    }

    /**
     * Verwerk het bericht en persist de log.
     */
    @Transactional(value = "loggingTransactionManager", propagation = Propagation.REQUIRED)
    private void verwerkBericht(final Message message, final String messageId, final String correlationId)
            throws JMSException {
        final String jmsBericht;
        if (message instanceof TextMessage) {
            jmsBericht = ((TextMessage) message).getText();
            InitVullingLog log = null;
            Long anummer = null;
            try {
                anummer = Long.parseLong(messageId);
            } catch (final NumberFormatException e) {
                try {
                    anummer = Long.parseLong(correlationId);
                } catch (final NumberFormatException e1) {
                    LOG.warn("Messageid (" + messageId + ") en correlatieId (" + correlationId
                            + ") zijn niet wat we verwachten, geen long (anummer)");
                    LOG.error("Bericht: " + jmsBericht);
                }
            }
            if (anummer != null) {
                LOG.info("Find log met anummer: " + anummer);
                try {
                    log = loggingService.findLog(anummer);
                    addLo3Bericht(log, jmsBericht);
                    addFoutmelding(log, jmsBericht);
                    loggingService.createAndStoreDiff(log);
                } catch (final EmptyResultDataAccessException e) {
                    LOG.warn("Log met anummer: " + anummer
                            + " is niet gevonden. Komt waarschijnlijk uit multirealiteit.");
                }
            }
        } else {
            LOG.error("[Bericht {}]: JMS bericht is niet van het type TextMessage", messageId);
        }
    }

    /**
     * Voeg het LO3Bericht toe aan de log als die aanwezig is.
     */
    private void addLo3Bericht(final InitVullingLog log, final String jmsBericht) {
        try {
            final SyncBericht syncBericht = SyncBerichtFactory.SINGLETON.getBericht(jmsBericht);
            if (syncBericht instanceof LeesUitBrpAntwoordBericht) {
                final LeesUitBrpAntwoordBericht response = (LeesUitBrpAntwoordBericht) syncBericht;
                final List<Lo3CategorieWaarde> categorieen =
                        new Lo3PersoonslijstFormatter().format(response.getLo3Persoonslijst());
                log.setBrpLo3Bericht(Lo3Inhoud.formatInhoud(categorieen));
            }
            // CHECKSTYLE:OFF - All exceptions have the same log.
        } catch (final Exception e) {
            // CHECKSTYLE:ON
            LOG.error("Error opgetreden bij het uitlezen van het QueryResponseBericht.", e);
        }
    }

    /**
     * Voeg de foutmelding toe aan de log als die aanwezig is.
     */
    private void addFoutmelding(final InitVullingLog log, final String jmsBericht) {
        try {
            final SyncBericht syncBericht = SyncBerichtFactory.SINGLETON.getBericht(jmsBericht);
            if (syncBericht instanceof SynchroniseerNaarBrpAntwoordBericht) {
                final SynchroniseerNaarBrpAntwoordBericht response =
                        (SynchroniseerNaarBrpAntwoordBericht) syncBericht;
                log.setFoutmelding(response.getFoutmelding());
                // TODO } else if (syncBericht instanceof ErrorBericht) {
                // final ErrorBericht error = (ErrorBericht) syncBericht;
                // log.setFoutmelding(error.getFoutmelding());
            }
            // CHECKSTYLE:OFF - All exceptions have the same log.
        } catch (final Exception e) {
            // CHECKSTYLE:ON
            LOG.error("Error opgetreden bij het uitlezen van het response bericht.", e);
        }
    }

}
