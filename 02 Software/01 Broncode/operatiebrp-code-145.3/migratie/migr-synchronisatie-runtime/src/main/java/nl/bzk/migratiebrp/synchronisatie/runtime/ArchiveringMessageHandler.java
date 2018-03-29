/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Richting;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBericht;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.logging.MDCProcessor;
import nl.bzk.algemeenbrp.util.common.serialisatie.JsonStringSerializer;
import nl.bzk.brp.archivering.domain.algemeen.ArchiveringOpdracht;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.factory.SyncBerichtFactory;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ArchiveringVerzoekBericht;
import nl.bzk.migratiebrp.util.common.logging.FunctioneleMelding;
import org.springframework.jms.UncategorizedJmsException;
import org.springframework.jms.core.JmsTemplate;

/**
 * Message handler voor archivering verzoeken. Deze worden 'vertaald' en doorgestuurd naar BRP.
 */
public final class ArchiveringMessageHandler implements MessageListener {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final Logger LOGGER = LoggerFactory.getBerichtVerkeerLogger();

    private final Destination gbaArchiefQueue;
    private final ConnectionFactory connectionFactory;
    private final DynamischeStamtabelRepository dynamischeStamtabelRepository;

    /**
     * Constructor.
     * @param gbaArchiefQueue gba archief queue
     * @param connectionFactory queue connection factory
     * @param dynamischeStamtabelRepository dynamischeStamtabelRepository voor stamgegevens
     */
    @Inject
    public ArchiveringMessageHandler(@Named("gbaArchief") final Destination gbaArchiefQueue,
                                     @Named("brpQueueConnectionFactory") final ConnectionFactory connectionFactory,
                                     final DynamischeStamtabelRepository dynamischeStamtabelRepository) {
        this.gbaArchiefQueue = gbaArchiefQueue;
        this.connectionFactory = connectionFactory;
        this.dynamischeStamtabelRepository = dynamischeStamtabelRepository;
    }

    @Override
    public void onMessage(final Message message) {
        LOG.info("Verwerken bericht");
        LOGGER.info("Lees bericht inhoud ...");
        final String berichtInhoud = bepaalBerichtInhoud(message);

        LOGGER.info("Parse bericht inhoud ...");
        final SyncBericht syncBericht = SyncBerichtFactory.SINGLETON.getBericht(berichtInhoud);
        if (syncBericht instanceof ArchiveringVerzoekBericht) {
            LOGGER.info("Converteren bericht ...");
            final ArchiveringOpdracht brpBericht = converteerVerzoek((ArchiveringVerzoekBericht) syncBericht);

            LOGGER.info("Versturen bericht naar BRP");
            verstuurVerzoekNaarBrp(brpBericht);
            LOGGER.info("Gereed");
        } else {
            throw new IllegalArgumentException("Onbekend type bericht ontvangen.");
        }
        LOG.info(FunctioneleMelding.SYNC_ARCHIVERING_VERWERKT);

    }

    private ArchiveringOpdracht converteerVerzoek(final ArchiveringVerzoekBericht syncBericht) {
        final ZonedDateTime
                tijdstipOntvangst =
                syncBericht.getTijdstipOntvangst() != null ? ZonedDateTime.ofInstant(syncBericht.getTijdstipOntvangst().toInstant(), ZoneId.systemDefault())
                        : null;
        final ZonedDateTime
                tijdstipVerzending =
                syncBericht.getTijdstipVerzending() != null ? ZonedDateTime.ofInstant(syncBericht.getTijdstipVerzending().toInstant(), ZoneId.systemDefault())
                        : null;

        final ZonedDateTime tijdstipRegistratie = tijdstipOntvangst != null ? tijdstipOntvangst : tijdstipVerzending;

        final ArchiveringOpdracht resultaat = new ArchiveringOpdracht(Richting.valueOf(syncBericht.getRichting().toString()), tijdstipRegistratie);
        resultaat.setSoortBericht(SoortBericht.parseIdentifier(syncBericht.getSoortBericht()));

        resultaat.setZendendePartijId(converteerNaarPartijId(syncBericht.getZendendePartij()));
        resultaat.setOntvangendePartijId(converteerNaarPartijId(syncBericht.getOntvangendePartij()));
        resultaat.setReferentienummer(syncBericht.getReferentienummer());
        resultaat.setCrossReferentienummer(syncBericht.getCrossReferentienummer());
        resultaat.setTijdstipOntvangst(tijdstipOntvangst);
        resultaat.setTijdstipVerzending(tijdstipVerzending);
        resultaat.setData(syncBericht.getData());

        return resultaat;
    }

    private Short converteerNaarPartijId(final String partijcode) {
        if (partijcode == null) {
            return null;
        }

        Partij partij = dynamischeStamtabelRepository.getPartijByCode(partijcode);
        if (partij == null) {
            throw new IllegalStateException("Geen partij gevonden.");
        }
        return partij.getId();
    }

    private void verstuurVerzoekNaarBrp(final ArchiveringOpdracht brpBericht) {
        final JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.send(gbaArchiefQueue, session -> {
            final String berichtTekst = new JsonStringSerializer().serialiseerNaarString(brpBericht);
            Message resultaat = session.createTextMessage(berichtTekst);
            MDCProcessor.registreerVerwerkingsCode(resultaat);
            return resultaat;
        });
    }

    /**
     * Lees bericht inhoud.
     * @param message message
     * @return inhoud
     */
    private String bepaalBerichtInhoud(final Message message) {
        final String result;
        try {
            if (message instanceof TextMessage) {
                result = ((TextMessage) message).getText();
            } else {
                throw new UncategorizedJmsException("Het JMS bericht is niet van het type TextMessage");
            }
        } catch (final JMSException e) {
            throw new UncategorizedJmsException("Het JMS bericht kon niet worden gelezen door een fout: ", e);
        }
        return result;
    }

}
