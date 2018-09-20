/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatiecontrole.jms.impl;

import java.math.BigInteger;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import nl.bzk.brp.levering.mutatiecontrole.jms.AdministratieveHandelingVerwerker;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;


/**
 * De implementatie van de interface AdministratieveHandelingVerwerker.
 */
@Component
public class AdministratieveHandelingVerwerkerImpl implements AdministratieveHandelingVerwerker {

    /** De mutatie administratieve handeling template voor JMS connectie. */
    @Inject
    @Named("mutatieAdministratieveHandelingTemplate")
    private JmsTemplate mutatieAdministratieveHandelingTemplate;

    /**
     * De sleutel van administratieve handeling in het JMS bericht.
     */
    public static final String          JMS_MESSAGE_ADMINISTRATIEVE_HANDELING_ID = "administratieveHandelingId";

    /*
     * (non-Javadoc)
     *
     * @see
     * nl.bzk.brp.levering.mutatiecontrole.jms.AdministratieveHandelingVerwerker#plaatsAdministratieveHandelingenOpQueue
     * (java.util.List)
     */
    @Override
    public void plaatsAdministratieveHandelingenOpQueue(final List<BigInteger> onverwerkteAdministratieveHandelingen) {
        for (final BigInteger administratieveHandelingId : onverwerkteAdministratieveHandelingen) {

            MessageCreator messageCreator = new MessageCreator() {
                @Override
                public Message createMessage(final Session session) throws JMSException {
                    Message message = session.createMessage();
                    message.setLongProperty(JMS_MESSAGE_ADMINISTRATIEVE_HANDELING_ID,
                            administratieveHandelingId.longValue());
                    return message;
                }
            };

            mutatieAdministratieveHandelingTemplate.send(messageCreator);
        }
    }

}
