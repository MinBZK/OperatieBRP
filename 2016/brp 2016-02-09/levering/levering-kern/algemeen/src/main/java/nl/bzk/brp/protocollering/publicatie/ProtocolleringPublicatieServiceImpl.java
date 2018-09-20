/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.protocollering.publicatie;

import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.hisvolledig.SerialisatieExceptie;
import nl.bzk.brp.model.internbericht.ProtocolleringOpdracht;
import nl.bzk.brp.serialisatie.JsonStringSerializer;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;


/**
 * Publiceer de protocolleringgegevens naar de geconfigureerde queue.
 */
@Service
public class ProtocolleringPublicatieServiceImpl implements ProtocolleringPublicatieService {

    private static final Logger                                LOGGER         = LoggerFactory.getLogger();

    @Inject
    private JmsTemplate                                        publiceerProtocolleringTemplate;

    private final JsonStringSerializer<ProtocolleringOpdracht> serialiseerder = new JsonStringSerializer<>(
                                                                                      ProtocolleringOpdracht.class);

    @Override
    public final void publiceerProtocolleringGegevens(final ProtocolleringOpdracht protocolleringOpdracht) {

        if (protocolleringOpdracht == null || !protocolleringOpdracht.isValide()) {
            throw new ProtocolleringPublicatieMisluktExceptie("Het protocolleringOpdracht object is niet valide.");
        }

        final MessageCreator protocolleringBericht = maakProtocolleringBericht(protocolleringOpdracht);
        publiceerProtocolleringTemplate.send(protocolleringBericht);
    }

    /**
     * Maak een JMS bericht aan met de soort levering als JMS Type, een bron en de protocolleringgegevens als JSON
     * body.
     *
     * @param protocolleringOpdracht de gegevens die in het protocollering bericht moeten worden doorgegegeven.
     * @return een MessageCreator instantie.
     */
    private MessageCreator maakProtocolleringBericht(final ProtocolleringOpdracht protocolleringOpdracht) {
        return new MessageCreator() {

            @Override
            public final Message createMessage(final Session session) throws JMSException {
                final TextMessage protocolleringBericht = session.createTextMessage();
                try {
                    protocolleringBericht.setText(serialiseerder.serialiseerNaarString(protocolleringOpdracht));
                    LOGGER.debug("Protocolleringbericht gemaakt: {}", protocolleringBericht.getText());
                } catch (final SerialisatieExceptie serialisatieExceptie) {
                    throw new ProtocolleringPublicatieMisluktExceptie(
                            "Het serialiseren van het protocolleringOpdracht object is mislukt.", serialisatieExceptie);
                }
                return protocolleringBericht;
            }
        };
    }

}
