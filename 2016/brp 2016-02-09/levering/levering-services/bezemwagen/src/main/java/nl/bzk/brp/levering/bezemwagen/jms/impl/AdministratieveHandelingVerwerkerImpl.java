/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.bezemwagen.jms.impl;

import java.math.BigInteger;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import nl.bzk.brp.levering.bezemwagen.jms.AdministratieveHandelingVerwerker;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.logging.MDC;
import nl.bzk.brp.logging.MDCVeld;
import nl.bzk.brp.model.internbericht.AdministratieveHandelingVerwerktOpdracht;
import nl.bzk.brp.serialisatie.JsonStringSerializer;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;


/**
 * De implementatie van de interface AdministratieveHandelingVerwerker.
 */
@Component
public class AdministratieveHandelingVerwerkerImpl implements AdministratieveHandelingVerwerker {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    /**
     * De mutatie administratieve handeling template voor JMS connectie.
     */
    @Inject
    @Named("mutatieAdministratieveHandelingTemplate")
    private JmsTemplate mutatieAdministratieveHandelingTemplate;

    private final JsonStringSerializer<AdministratieveHandelingVerwerktOpdracht> serialiseerder = new JsonStringSerializer<>(
        AdministratieveHandelingVerwerktOpdracht.class);

    @Override
    public final void plaatsAdministratieveHandelingenOpQueue(final List<BigInteger> onverwerkteAdministratieveHandelingen) {
        for (final BigInteger administratieveHandelingId : onverwerkteAdministratieveHandelingen) {
            MDC.put(MDCVeld.MDC_ADMINISTRATIEVE_HANDELING, String.valueOf(administratieveHandelingId));

            final MessageCreator messageCreator = new MessageCreator() {
                @Override
                public final Message createMessage(final Session session) throws JMSException {
                    final TextMessage message = session.createTextMessage();

                    final AdministratieveHandelingVerwerktOpdracht verwerkingsOpdracht = new
                        AdministratieveHandelingVerwerktOpdracht(
                        administratieveHandelingId.longValue(), null, null);
                    message.setText(serialiseerder.serialiseerNaarString(verwerkingsOpdracht));

                    LOGGER.debug("Message met AHid '{}' wordt op de queue gezet ter verwerking.",
                        administratieveHandelingId.longValue());

                    return message;
                }
            };

            mutatieAdministratieveHandelingTemplate.send(messageCreator);

            LOGGER.debug("Administratieve handeling gepubliceerd");
        }
    }

}
