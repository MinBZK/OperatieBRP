/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.naarbrp.verwerker.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import nl.bzk.algemeenbrp.util.common.logging.MDCProcessor;
import nl.bzk.migratiebrp.bericht.model.JMSConstants;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBerichtType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.Protocollering;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ProtocolleringBericht;
import nl.bzk.migratiebrp.init.naarbrp.domein.ConversieResultaat;
import nl.bzk.migratiebrp.init.naarbrp.repository.ProtocolleringRepository;
import nl.bzk.migratiebrp.init.naarbrp.verwerker.BerichtVerwerker;
import org.springframework.jms.core.JmsTemplate;

/**
 * Verstuurt ProtocolleringBerichten.
 */
public final class VerzendProtocolleringBerichtVerwerker implements BerichtVerwerker<ProtocolleringBericht> {

    private static final String BERICHTEN_ZIJN_AL_VERZONDEN = "Berichten zijn al verzonden";
    private static final String MSG_ID_FORMAT = "%s-%s";

    private final Destination destination;
    private final JmsTemplate jmsTemplate;
    private final ProtocolleringRepository protocolleringRepository;

    private final AtomicLong aantalVerzonden = new AtomicLong(0);
    private boolean verzonden;

    private final List<ProtocolleringBericht> berichten;

    /**
     * Constructor.
     * @param destination {@link Destination} queue waarop berichten geplaatst moeten worden.
     * @param jmsTemplate {@link JmsTemplate}
     * @param protocolleringRepository De ProtocolleringRepository waaruit de protocollering data moet worden gelezen
     */
    public VerzendProtocolleringBerichtVerwerker(final Destination destination, final JmsTemplate jmsTemplate,
                                                 final ProtocolleringRepository protocolleringRepository) {
        berichten = new ArrayList<>();
        this.destination = destination;
        this.jmsTemplate = jmsTemplate;
        this.protocolleringRepository = protocolleringRepository;
    }

    @Override
    public void voegBerichtToe(final ProtocolleringBericht bericht) {
        if (verzonden) {
            throw new java.lang.IllegalStateException(BERICHTEN_ZIJN_AL_VERZONDEN);
        }
        berichten.add(bericht);
    }

    @Override
    public int aantalBerichten() {
        return berichten.size();
    }

    @Override
    public long aantalVerzonden() {
        return aantalVerzonden.get();
    }

    @Override
    public void verwerkBerichten() {
        if (verzonden) {
            throw new java.lang.IllegalStateException(BERICHTEN_ZIJN_AL_VERZONDEN);
        }
        if (!berichten.isEmpty()) {
            jmsTemplate.execute(destination, (session, producer) -> {
                for (final ProtocolleringBericht bericht : berichten) {
                    MDCProcessor.startVerwerking().run(() ->
                       producer.send(destination, createMessage(session, bericht))
                    );
                }
                int verzondenTeller = 0;
                final List<Long> verzondenActiviteiten = new ArrayList<>();
                for (final ProtocolleringBericht bericht : berichten) {
                    for (final Protocollering protocollering : bericht.getProtocollering()) {
                        verzondenTeller++;
                        verzondenActiviteiten.add(protocollering.getActiviteitId());
                    }
                }
                protocolleringRepository.updateStatussen(verzondenActiviteiten, ConversieResultaat.VERZONDEN);
                aantalVerzonden.addAndGet(verzondenTeller);
                return null;
            });
        }
        verzonden = true;
    }

    private Message createMessage(final Session session, final ProtocolleringBericht bericht) throws JMSException {
        final Message message = session.createTextMessage(bericht.format());
        MDCProcessor.registreerVerwerkingsCode(message);
        message.setStringProperty(JMSConstants.BERICHT_REFERENTIE,
                String.format(MSG_ID_FORMAT, SyncBerichtType.PROTOCOLLERING.getType(), bericht.getMessageId()));
        return message;
    }
}
