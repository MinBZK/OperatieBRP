/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.runtime;

import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.BerichtOnbekendException;
import nl.moderniseringgba.isc.esb.message.JMSConstants;
import nl.moderniseringgba.isc.esb.message.sync.SyncBericht;
import nl.moderniseringgba.isc.esb.message.sync.SyncBerichtFactory;
import nl.moderniseringgba.isc.esb.message.sync.impl.BlokkeringInfoVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.BlokkeringVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.ConverteerNaarBrpVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.ConverteerNaarLo3VerzoekBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.DeblokkeringVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.LeesUitBrpVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.SynchronisatieStrategieVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;
import nl.moderniseringgba.migratie.operatie.Herhaal;
import nl.moderniseringgba.migratie.operatie.HerhaalException;
import nl.moderniseringgba.migratie.runtime.service.BlokkeringInfoVerzoekService;
import nl.moderniseringgba.migratie.runtime.service.BlokkeringVerzoekService;
import nl.moderniseringgba.migratie.runtime.service.ConverteerService;
import nl.moderniseringgba.migratie.runtime.service.DeblokkeringVerzoekService;
import nl.moderniseringgba.migratie.runtime.service.LeesUitBrpService;
import nl.moderniseringgba.migratie.runtime.service.SynchronisatieService;
import nl.moderniseringgba.migratie.runtime.service.SynchronisatieStrategieService;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * Verwerk een synchronisatie bericht.
 */
/*
 * CHECKSTYLE:OFF Class Fan-Out complexity is hier hoger dan 20, het verder opsplitsen van deze class maakt de code niet
 * leesbaarder
 */
public final class SynchronisatieMessageHandler implements MessageListener {
    // CHECKSTYLE:ON

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private SynchronisatieService synchronisatieService;

    @Inject
    private ConverteerService converteerService;

    @Inject
    private LeesUitBrpService leesUitBrpService;

    @Inject
    private BlokkeringVerzoekService blokkeringVerzoekService;

    @Inject
    private BlokkeringInfoVerzoekService blokkeringInfoVerzoekService;

    @Inject
    private DeblokkeringVerzoekService deblokkeringService;

    @Inject
    private SynchronisatieStrategieService synchronisatieStrategieService;

    private JmsTemplate jmsTemplate;

    private boolean startCyclus;

    private String inboundQueueName;

    private boolean isInitieelVullingProces;

    @Inject
    @Named("queueOutbound")
    private Destination destination;

    @Inject
    @Named("queueConnectionFactory")
    void setConnectionFactory(final ConnectionFactory connectionFactory) {
        jmsTemplate = new JmsTemplate(connectionFactory);
    }

    /*
     * ******************************************** Bean properties ***********************************************
     */
    /**
     * @return the startCyclus
     */
    public boolean isStartCyclus() {
        return startCyclus;
    }

    /**
     * @param startCyclus
     *            the startCyclus to set
     */
    public void setStartCyclus(final boolean startCyclus) {
        this.startCyclus = startCyclus;
    }

    /**
     * @param inboundQueueName
     *            the name of the inbound queue
     */
    public void setInboundQueueName(final String inboundQueueName) {
        this.inboundQueueName = inboundQueueName;
    }

    /**
     * @return the name of the inbound queue
     */
    public String getInboundQueueName() {
        return inboundQueueName;
    }

    public boolean isInitieelVullingProces() {
        return isInitieelVullingProces;
    }

    public void setInitieelVullingProces(final boolean isInitieelVullingProces) {
        this.isInitieelVullingProces = isInitieelVullingProces;
    }

    /*
     * **************************************** MessageListener methods ********************************************
     */
    @Override
    public void onMessage(final Message message) {
        try {
            final SyncBericht antwoord;
            LOG.info("[Start verwerken binnenkomend bericht ...");
            final String berichtReferentie = bepaalBerichtReferentie(message);
            LOG.info("[Bericht {}]: Lees bericht inhoud ...", berichtReferentie);
            final String berichtInhoud = bepaalBerichtInhoud(message);
            LOG.info("[Bericht {}]: Parse bericht inhoud ...", berichtReferentie);
            try {
                final SyncBericht bericht = SyncBerichtFactory.SINGLETON.getBericht(berichtInhoud);
                bericht.setMessageId(berichtReferentie);
                controleerBerichtEnRolMessageHandler(bericht);
                antwoord = verwerkBericht(bericht);
                stuurAntwoord(antwoord);
            } catch (final BerichtOnbekendException e) {
                LOG.info("[Bericht {}]: Bericht onbekend fout:", berichtReferentie, e);
                // TODO antwoord = new ErrorBericht(berichtReferentie, e.getMessage());
            } catch (final BerichtValidatieException bve) {
                LOG.error("Er is een fout opgetreden bij het afhandelen van een binnenkomend bericht.", bve);
                // TODO antwoord = new ErrorBericht(berichtReferentie, bve.getMessage());
            }
        } catch (final BerichtLeesException ble) {
            LOG.error("Er is een fout opgetreden bij het afhandelen van een binnenkomend bericht. "
                    + "Er is geen antwoord gestuurd.", ble);
        }
    }

    /*
     * **************************************** Package private methods ********************************************
     */
    /**
     * Handle the message.
     * 
     * @param bericht
     *            input
     * @return output
     * @throws BerichtOnbekendException
     *             In het geval van een onbekend berichttype.
     */
    // Note: package protected for test.
    SyncBericht verwerkBericht(final SyncBericht bericht) throws BerichtOnbekendException {
        final SyncBericht antwoord;
        if (bericht instanceof LeesUitBrpVerzoekBericht) {
            antwoord = leesUitBrpService.verwerkLeesUitBrpVerzoek((LeesUitBrpVerzoekBericht) bericht);
        } else if (bericht instanceof BlokkeringVerzoekBericht) {
            antwoord = blokkeringVerzoekService.verwerkBlokkeringVerzoek((BlokkeringVerzoekBericht) bericht);
        } else if (bericht instanceof BlokkeringInfoVerzoekBericht) {
            antwoord =
                    blokkeringInfoVerzoekService.verwerkBlokkeringInfoVerzoek((BlokkeringInfoVerzoekBericht) bericht);
        } else if (bericht instanceof DeblokkeringVerzoekBericht) {
            antwoord = deblokkeringService.verwerkDeblokkeringVerzoek((DeblokkeringVerzoekBericht) bericht);
        } else if (bericht instanceof SynchronisatieStrategieVerzoekBericht) {
            antwoord =
                    synchronisatieStrategieService
                            .verwerkSynchronisatieStrategieVerzoek((SynchronisatieStrategieVerzoekBericht) bericht);
        } else if (bericht instanceof ConverteerNaarLo3VerzoekBericht) {
            antwoord = converteerService.verwerkConverteerNaarLo3Verzoek((ConverteerNaarLo3VerzoekBericht) bericht);
        } else if (bericht instanceof ConverteerNaarBrpVerzoekBericht) {
            antwoord = converteerService.verwerkConverteerNaarBrpVerzoek((ConverteerNaarBrpVerzoekBericht) bericht);
        } else if (bericht instanceof SynchroniseerNaarBrpVerzoekBericht) {
            antwoord =
                    synchronisatieService.verwerkSynchroniseerNaarBrpVerzoek(
                            (SynchroniseerNaarBrpVerzoekBericht) bericht, getInboundQueueName(),
                            isInitieelVullingProces());
        } else {
            LOG.info("[Bericht {}]: Onbekend bericht ...", bericht.getMessageId());
            throw new BerichtOnbekendException("[Bericht {}]: Onbekend bericht type: " + bericht.getBerichtType());
        }
        return antwoord;
    }

    /*
     * ******************************************** Private methods ************************************************
     */
    private void controleerBerichtEnRolMessageHandler(final SyncBericht bericht) throws BerichtValidatieException {
        if (bericht.getStartCyclus() == null && isStartCyclus()) {
            throw new BerichtValidatieException(
                    "Deze MessageHandler is geconfigureerd om alleen berichten af te handelen "
                            + "die een nieuwe cyclus starten. Dit bericht start een nieuwe cyclus "
                            + "en is dus niet toegestaan.");
        }
    }

    private String bepaalBerichtInhoud(final Message message) throws BerichtLeesException {
        final String result;
        try {
            if (message instanceof TextMessage) {
                result = ((TextMessage) message).getText();
            } else {
                throw new BerichtLeesException("Het JMS bericht is niet van het type TextMessage");
            }
        } catch (final JMSException e) {
            throw new BerichtLeesException("Het JMS bericht kon niet worden gelezen door een fout: ", e);
        }
        return result;
    }

    private String bepaalBerichtReferentie(final Message message) throws BerichtLeesException {
        try {
            final String result = message.getStringProperty(JMSConstants.BERICHT_REFERENTIE);
            if (result == null || "".equals(result)) {
                throw new BerichtLeesException("De property '" + JMSConstants.BERICHT_REFERENTIE
                        + "' in het JMS bericht is leeg.");
            }
            return result;
        } catch (final JMSException e) {
            throw new BerichtLeesException("Er kon geen waarde gevonden worden voor de property '"
                    + JMSConstants.BERICHT_REFERENTIE + "' in het JMS bericht door een fout:", e);
        }
    }

    private void stuurAntwoord(final SyncBericht bericht) {
        try {
            Herhaal.herhaalOperatie(new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    jmsTemplate.send(destination, new MessageCreator() {
                        @Override
                        public Message createMessage(final Session session) throws JMSException {
                            try {
                                final Message message = session.createTextMessage(bericht.format());
                                message.setStringProperty(JMSConstants.BERICHT_REFERENTIE, bericht.getMessageId());
                                message.setStringProperty(JMSConstants.CORRELATIE_REFERENTIE,
                                        bericht.getCorrelationId());
                                return message;
                            } catch (final BerichtInhoudException exceptie) {
                                return null;
                            }
                        }
                    });
                    return null;
                }
            });
        } catch (final HerhaalException e) {
            LOG.error("Kon bericht met Id=" + bericht.getMessageId() + " niet verzenden.", e);
        }
    }

    /**
     * Fout bij het verwerken van JMS bericht.
     */
    @SuppressWarnings("serial")
    private final class BerichtLeesException extends Exception {

        public BerichtLeesException(final String message) {
            super(message);
        }

        public BerichtLeesException(final String message, final Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * Fout bij het valideren van Synchronisatie bericht.
     */
    @SuppressWarnings("serial")
    private final class BerichtValidatieException extends Exception {

        public BerichtValidatieException(final String message) {
            super(message);
        }
    }
}
