/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.stappen.administratievehandeling.integratie;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import nl.bzk.brp.model.internbericht.AdministratieveHandelingVerwerktOpdracht;
import nl.bzk.brp.serialisatie.JsonStringSerializer;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Toehoorder voor berichten op een JMS queue/topic met administratieve handeling id's.
 */
@Component
public class AdministratieveHandelingListener implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private final JsonStringSerializer<AdministratieveHandelingVerwerktOpdracht> serialiseerder = new
        JsonStringSerializer<>(AdministratieveHandelingVerwerktOpdracht.class);

    private Long administratieveHandelingId;

    @Override
    public void onMessage(final Message message) {
        try {
            final AdministratieveHandelingVerwerktOpdracht verwerkingsOpdracht = serialiseerder
                .deserialiseerVanuitString(((TextMessage) message).getText());
            administratieveHandelingId = verwerkingsOpdracht.getAdministratieveHandelingId();
        } catch (JMSException e) {
            LOGGER.error("Het ontvangen van een JMS bericht met een Administratieve Handeling ID is mislukt.", e);
        }
    }

    public Long getAdministratieveHandelingId() {
        return administratieveHandelingId;
    }

}
