/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.jms;

import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import nl.bzk.brp.levering.mutatielevering.jmx.MutatieLeveringInfoBean;
import nl.bzk.brp.levering.mutatielevering.service.AdministratieveHandelingVerwerkerService;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.logging.MDC;
import nl.bzk.brp.logging.MDCVeld;
import nl.bzk.brp.model.internbericht.AdministratieveHandelingVerwerktOpdracht;
import nl.bzk.brp.serialisatie.JsonStringSerializer;

import org.springframework.stereotype.Component;


/**
 * Toehoorder voor berichten op een JMS queue/topic met administratieve handeling id's.
 */
@Component
public class AdministratieveHandelingListener implements MessageListener {

    private static final Logger                                                  LOGGER         = LoggerFactory
                                                                                                        .getLogger();

    @Inject
    private AdministratieveHandelingVerwerkerService                             administratieveHandelingVerwerkerService;

    @Inject
    private MutatieLeveringInfoBean mutatieLeveringInfoBean;

    private final JsonStringSerializer<AdministratieveHandelingVerwerktOpdracht> serialiseerder =
                                                                                                    new JsonStringSerializer<>(
                                                                                                            AdministratieveHandelingVerwerktOpdracht.class);

    @Override
    public final void onMessage(final Message message) {
        MDC.put(MDCVeld.MDC_APPLICATIE_NAAM, "mutatielevering");
        if (message instanceof TextMessage) {
            try {
                final String berichtTekst = ((TextMessage) message).getText();
                LOGGER.debug("Binnenkomende mutatielevering bericht: {}", berichtTekst);
                LOGGER.debug("Bericht info correlationid={},messageId={},redelivered?{},timestamp={}",
                        message.getJMSCorrelationID(), message.getJMSMessageID(), message.getJMSRedelivered(),
                        message.getJMSTimestamp());

                final long startHandeling = System.currentTimeMillis();
                final AdministratieveHandelingVerwerktOpdracht verwerkingsOpdracht =
                    serialiseerder.deserialiseerVanuitString(berichtTekst);

                administratieveHandelingVerwerkerService.verwerkAdministratieveHandeling(verwerkingsOpdracht
                        .getAdministratieveHandelingId());

                mutatieLeveringInfoBean.incrementHandeling(System.currentTimeMillis() - startHandeling);
            } catch (final JMSException e) {
                LOGGER.error("Het ontvangen van een JMS bericht met een Administratieve Handeling ID is mislukt.", e);
            }
        } else {
            LOGGER.error("Bericht van de queue is van een onverwacht type. {} Dit bericht wordt genegeerd!", message);
        }
        MDC.remove(MDCVeld.MDC_APPLICATIE_NAAM);
    }
}
