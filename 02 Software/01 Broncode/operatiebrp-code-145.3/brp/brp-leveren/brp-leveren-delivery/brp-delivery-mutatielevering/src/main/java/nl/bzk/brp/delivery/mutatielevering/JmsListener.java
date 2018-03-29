/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.mutatielevering;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.logging.MDC;
import nl.bzk.algemeenbrp.util.common.serialisatie.JsonStringSerializer;
import nl.bzk.brp.domain.internbericht.admhndpublicatie.HandelingVoorPublicatie;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.service.algemeen.BrpServiceRuntimeException;
import nl.bzk.brp.service.algemeen.logging.LeveringVeld;
import nl.bzk.brp.service.mutatielevering.VerwerkHandelingService;
import org.springframework.stereotype.Component;

/**
 * Toehoorder voor berichten op een JMS queue/topic met administratieve handeling id's.
 */
@Component(value = "MutatieleveringMessageListener")
final class JmsListener implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private VerwerkHandelingService verwerkHandelingService;

    @Inject
    private MutatieLeveringInfoBean mutatieLeveringInfoBean;

    private final JsonStringSerializer serialiseerder = new JsonStringSerializer();

    private JmsListener() {
    }

    @Override
    public void onMessage(final Message message) {
        BrpNu.set(DatumUtil.nuAlsZonedDateTime());
        if (!(message instanceof TextMessage)) {
            LOGGER.error("Onverwacht berichttype. {} Dit bericht wordt genegeerd!", message);
            return;
        }
        final long startHandeling = System.currentTimeMillis();

        final Map<String, String> mdcMap = Maps.newHashMap();
        mdcMap.put(LeveringVeld.MDC_APPLICATIE_NAAM.getVeld(), "mutatielevering");
        MDC.voerUit(mdcMap, () -> verwerkHandeling(geefBerichtinhoud(message)));

        mutatieLeveringInfoBean.incrementHandeling(System.currentTimeMillis() - startHandeling);
    }

    private HandelingVoorPublicatie geefBerichtinhoud(final Message message) {
        final String berichtTekst;
        try {
            berichtTekst = ((TextMessage) message).getText();
            LOGGER.debug("Binnenkomende mutatielevering bericht: {}", berichtTekst);
            LOGGER.debug(
                    "Bericht info correlationid={},messageId={},redelivered?{},timestamp={}",
                    message.getJMSCorrelationID(),
                    message.getJMSMessageID(),
                    message.getJMSRedelivered(),
                    message.getJMSTimestamp());
        } catch (final JMSException e) {
            LOGGER.error("Het ontvangen van een JMS bericht met een Administratieve Handeling ID is mislukt.", e);
            throw new BrpServiceRuntimeException(e);
        }
        return serialiseerder.deserialiseerVanuitString(berichtTekst, HandelingVoorPublicatie.class);
    }

    private void verwerkHandeling(final HandelingVoorPublicatie handelingVoorPublicatie) {
        final long admhndId = handelingVoorPublicatie.getAdmhndId();
        Thread.currentThread().setName("Mutatielevering-AH-" + admhndId);
        LOGGER.info("Start verwerking van administratieve handeling {}", admhndId);

        final Map<String, String> mdcMap = Maps.newHashMap();
        mdcMap.put(LeveringVeld.MDC_ADMINISTRATIEVE_HANDELING.getVeld(), String.valueOf(admhndId));
        BrpNu.set(DatumUtil.nuAlsZonedDateTime());
        MDC.voerUit(mdcMap, () -> {
            try {
                verwerkHandelingService.verwerkAdministratieveHandeling(handelingVoorPublicatie);
            } catch (Exception e) {
                LOGGER.error(String.format("Onverwachte fout opgetreden bij verwerken mutatie [admhnd=%d]", admhndId), e);
                verwerkHandelingService.markeerHandelingAlsFout(handelingVoorPublicatie);
            } finally {
                MDC.remove(LeveringVeld.MDC_LEVERINGAUTORISATIEID);
                MDC.remove(LeveringVeld.MDC_AANGEROEPEN_DIENST);
                MDC.remove(LeveringVeld.MDC_PARTIJ_ID);
            }
        });
    }

}
