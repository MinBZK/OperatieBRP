/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.protocollering.verwerking;

import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.logging.MDC;
import nl.bzk.brp.logging.MDCVeld;
import nl.bzk.brp.model.internbericht.ProtocolleringOpdracht;
import nl.bzk.brp.protocollering.verwerking.exceptie.ProtocolleringFout;
import nl.bzk.brp.protocollering.verwerking.service.ProtocolleringVerwerkingService;
import nl.bzk.brp.serialisatie.JsonStringSerializer;

import org.springframework.stereotype.Component;


/**
 * Deze listener luistert naar de queue waarop protocolleringsverzoeken staan.
 */
@Component
public class ProtocolleringBerichtVerwerker implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private ProtocolleringVerwerkingService                    protocolleringVerwerkingService;

    private final JsonStringSerializer<ProtocolleringOpdracht> serialiseerder = new JsonStringSerializer<>(
                                                                                      ProtocolleringOpdracht.class);

    /**
     * De methode die aangeroepen wordt bij een inkomend JMS bericht.
     *
     * @param message Het JMS bericht.
     */
    @Override
    public final void onMessage(final Message message) {
        if (message instanceof TextMessage) {
            try {
                final String berichtTekst = ((TextMessage) message).getText();
                LOGGER.debug("Binnenkomende protocollering bericht: {}", berichtTekst);
                LOGGER.debug("Bericht info correlationid={},messageId={},redelivered?{},timestamp={}",
                        message.getJMSCorrelationID(), message.getJMSMessageID(), message.getJMSRedelivered(),
                        message.getJMSTimestamp());

                final ProtocolleringOpdracht protocolleringOpdracht =
                    serialiseerder.deserialiseerVanuitString(berichtTekst);

                MDC.put(MDCVeld.MDC_ADMINISTRATIEVE_HANDELING,
                        String.valueOf(protocolleringOpdracht.getLevering().getAdministratieveHandelingId()));
                MDC.put(MDCVeld.MDC_APPLICATIE_NAAM, "protocollering-verwerker");
                MDC.put(MDCVeld.MDC_TOEGANG_LEVERINGSAUTORISATIE_ID,
                        String.valueOf(protocolleringOpdracht.getLevering().getToegangLeveringsautorisatieId()));

                protocolleringVerwerkingService.slaProtocolleringOp(protocolleringOpdracht);

                LOGGER.info("Protocollering geslaagd.");
            } catch (final JMSException ex) {
                LOGGER.error("Algemene JMS fout.", ex);
                throw new ProtocolleringFout(ex);
            } catch (final ProtocolleringFout ex) {
                LOGGER.error("Fout bij protocollering. Foutmelding: {}", ex.getMessage());
                throw ex;
            } catch (final Exception ex) {
                LOGGER.error("Onbekende fout bij protocollering.", ex);
                throw new ProtocolleringFout(ex);
            } finally {
                MDC.remove(MDCVeld.MDC_ADMINISTRATIEVE_HANDELING);
                MDC.remove(MDCVeld.MDC_APPLICATIE_NAAM);
                MDC.remove(MDCVeld.MDC_TOEGANG_LEVERINGSAUTORISATIE_ID);
            }
        } else {
            LOGGER.error("Bericht van de queue is van een onverwacht type. {} Dit bericht wordt genegeerd!", message);
        }
    }

}
